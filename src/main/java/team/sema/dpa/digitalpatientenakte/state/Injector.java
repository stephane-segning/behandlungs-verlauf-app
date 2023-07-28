package team.sema.dpa.digitalpatientenakte.state;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.SearchConfig;
import team.sema.dpa.digitalpatientenakte.views.ScreensController;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class Injector {
    private static Injector injector;
    private final MultiValuedMap<Class<?>, Class<?>> diMap;
    private final ConcurrentMap<Class<?>, Object> applicationScope;

    private Injector() {
        diMap = new HashSetValuedHashMap<>();
        applicationScope = new ConcurrentHashMap<>();
    }

    /**
     * Start application
     *
     * @param mainClass main class of application
     */
    public static void startApplication(Class<?> mainClass) {
        try {
            synchronized (Injector.class) {
                if (injector == null) {
                    injector = new Injector();
                    injector.initFramework(mainClass);
                    injector.initViewController(mainClass);
                    injector.triggerAllInit();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static <T> T getService(Class<T> classz) {
        try {
            return injector.getBeanInstance(classz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static List<Class<?>> getAllInterfaces(Class<?> clazz) {
        List<Class<?>> interfaceList = new ArrayList<>();

        while (clazz != null) {
            Class<?>[] interfaces = clazz.getInterfaces();

            interfaceList.addAll(Arrays.asList(interfaces));

            // Get interfaces of the interfaces
            for (Class<?> anInterface : interfaces) {
                interfaceList.addAll(getAllInterfaces(anInterface));
            }

            // Move on to the superclass
            clazz = clazz.getSuperclass();
        }

        return interfaceList;
    }

    public static List<Class<?>> getAllSuperClasses(Class<?> clazz) {
        List<Class<?>> classList = new ArrayList<>();

        Class<?> superclass = clazz.getSuperclass();

        while (superclass != null) {
            classList.add(superclass);
            superclass = superclass.getSuperclass();
        }

        return classList;
    }

    private void initViewController(Class<?> mainClass) throws IOException {
        final var screenController = getService(ScreensController.class);
        final var components = applicationScope.values();
        for (final var component : components) {
            final var classz = component.getClass();
            if (!classz.isAnnotationPresent(ViewController.class)) {
                continue;
            }

            final var view = classz.getAnnotation(ViewController.class);
            final var loader = new FXMLLoader(mainClass.getResource(view.view()));
            loader.setControllerFactory(c -> component);

            final var loadScreen = (Parent) loader.load();
            screenController.addScreen(view.name(), loadScreen, component);
        }
    }

    private void triggerAllInit() throws InvocationTargetException, IllegalAccessException {
        final var components = applicationScope.values();
        for (final var component : components) {
            invokeInitMethod(component);
        }
    }

    /**
     * initialize the injector framework
     */
    private void initFramework(Class<?> mainClass)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, InvocationTargetException {
        final var classes = getClasses(mainClass.getPackage().getName(), true);
        final var componentConatiner = ComponentContainer.getInstance();
        final var classHunter = componentConatiner.getClassHunter();
        final var packageRelPath = mainClass.getPackage().getName().replace(".", "/");
        try (ClassHunter.SearchResult result = classHunter.findBy(
                SearchConfig.forResources(
                        packageRelPath
                ).by(ClassCriteria.create().allThoseThatMatch(cls -> cls.getAnnotation(Component.class) != null))
        )) {
            Collection<Class<?>> types = result.getClasses();
            for (final var implementationClass : types) {
                processBeanMethods(implementationClass);
            }

            // First pass: create all instances (without injection)
            for (final var classz : classes) {
                if (classz.isAnnotationPresent(Component.class)) {
                    registerClass(classz);
                    Object classInstance = createInstanceWithEmptyConstructor(classz);
                    applicationScope.put(classz, classInstance);
                }
            }

            // Second pass: inject dependencies
            for (final var entry : applicationScope
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().getPackageName().startsWith(mainClass.getPackageName()))
                    .filter(entry -> entry.getKey().getDeclaredConstructors().length > 0 && entry.getKey().getDeclaredConstructors()[0].getParameterCount() != 0)
                    .sorted(Comparator.comparingInt(e -> e.getKey().getDeclaredConstructors()[0].getParameterCount()))
                    .toList()) {
                final var classz = entry.getKey();
                var value = entry.getValue();

                final var constructors = classz.getDeclaredConstructors();
                final var constructor = constructors[0];
                int parameterCount = constructor.getParameterCount();
                if (parameterCount > 0) {
                    value = createInstanceWithFullConstructor(constructor);
                    entry.setValue(value);
                    InjectionUtil.autowire(this, classz, value);
                }
            }
        }
    }

    private void processBeanMethods(Class<?> classz) throws InvocationTargetException, IllegalAccessException {
        for (final var method : classz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Bean.class)) {
                System.out.println("Processing @Bean method " + method.getName());
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new IllegalArgumentException("@Bean method " + method.getName() + " must be static");
                }
                final var bean = method.invoke(null);
                applicationScope.put(bean.getClass(), bean);
                registerClass(bean.getClass());
            }
        }
    }

    private Object createInstanceWithEmptyConstructor(Class<?> classz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        final var constructor = classz.getConstructors()[0];  // Assume only one constructor.
        return constructor.newInstance(new Object[constructor.getParameterCount()]);
    }

    private Object createInstanceWithFullConstructor(Constructor<?> constructor) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = getBeanInstance(parameterTypes[i]);
        }
        return constructor.newInstance(parameters);
    }

    private void invokeInitMethod(Object instance) throws InvocationTargetException, IllegalAccessException {
        for (final var method : instance.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(instance);
            }
        }
    }

    private void registerClass(Class<?> classz) {
        diMap.put(classz, classz);

        for (Class<?> iface : getAllInterfaces(classz)) {
            diMap.put(classz, iface);
        }

        for (Class<?> iface : getAllSuperClasses(classz)) {
            diMap.put(classz, iface);
        }
    }

    /**
     * Get all the classes for the input package
     */
    public Class<?>[] getClasses(String packageName, boolean recursive) throws ClassNotFoundException, IOException {
        ComponentContainer componentConatiner = ComponentContainer.getInstance();
        ClassHunter classHunter = componentConatiner.getClassHunter();
        String packageRelPath = packageName.replace(".", "/");
        SearchConfig config = SearchConfig.forResources(
                packageRelPath
        );
        if (!recursive) {
            config.findInChildren();
        }

        try (ClassHunter.SearchResult result = classHunter.findBy(config)) {
            Collection<Class<?>> classes = result.getClasses();
            return classes.toArray(new Class[classes.size()]);
        }
    }


    /**
     * Create and Get the Object instance of the implementation class for input
     * interface service
     */
    @SuppressWarnings("unchecked")
    private <T> T getBeanInstance(Class<T> interfaceClass) throws InstantiationException, IllegalAccessException {
        return (T) getBeanInstance(interfaceClass, null, null);
    }

    /**
     * Overload getBeanInstance to handle qualifier and autowire by type
     */
    public <T> Object getBeanInstance(Class<T> interfaceClass, String fieldName, String qualifier)
            throws InstantiationException, IllegalAccessException {
        final var implementationClass = getImplimentationClass(interfaceClass, fieldName, qualifier);

        if (applicationScope.containsKey(implementationClass)) {
            return applicationScope.get(implementationClass);
        }

        // final var service = implementationClass.newInstance();
        // applicationScope.put(implementationClass, service);
        // return service;
        throw new RuntimeException("No implementation found for interface " + interfaceClass.getName());
    }

    /**
     * Get the name of the implimentation class for input interface service
     */
    private Class<?> getImplimentationClass(Class<?> interfaceClass, final String fieldName, final String qualifier) {
        Set<Entry<Class<?>, Class<?>>> implementationClasses = diMap.entries().stream()
                .filter(entry -> entry.getValue() == interfaceClass).collect(Collectors.toSet());
        String errorMessage;
        if (implementationClasses.size() == 0) {
            errorMessage = "no implementation found for interface " + interfaceClass.getName();
        } else if (implementationClasses.size() == 1) {
            Optional<Entry<Class<?>, Class<?>>> optional = implementationClasses.stream().findFirst();
            return optional.get().getKey();
        } else {
            final String findBy = (qualifier == null || qualifier.trim().length() == 0) ? fieldName : qualifier;
            Optional<Entry<Class<?>, Class<?>>> optional = implementationClasses.stream()
                    .filter(entry -> entry.getKey().getSimpleName().equalsIgnoreCase(findBy)).findAny();
            if (optional.isPresent()) {
                return optional.get().getKey();
            } else {
                errorMessage = "There are " + implementationClasses.size() + " of interface " + interfaceClass.getName()
                        + " Expected single implementation or make use of @CustomQualifier to resolve conflict";
            }
        }

        throw new RuntimeException(new Error(errorMessage));
    }
}
