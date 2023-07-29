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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;
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
     * @return
     */
    public static Injector startApplication(Class<?> mainClass) {
        try {
            synchronized (Injector.class) {
                if (injector == null) {
                    injector = new Injector();
                    injector.initFramework(mainClass);
                }
            }
            return injector;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
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

    private void initViewController(Class<?> mainClass) throws IOException {
        final var screenController = getService(ScreensController.class);
        final var viewComponents = applicationScope.values().stream().filter(i -> i.getClass().isAnnotationPresent(ViewController.class)).collect(Collectors.toSet());
        for (final var component : viewComponents) {
            final var classz = component.getClass();
            final var view = classz.getAnnotation(ViewController.class);
            final var loader = new FXMLLoader(mainClass.getResource(view.view()));
            loader.setControllerFactory(c -> component);

            final var loadScreen = (Parent) loader.load();
            screenController.addScreen(view.name(), loadScreen, component);
        }
        System.out.println("Loaded screens: " + viewComponents.size());
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
    private void initFramework(Class<?> mainClass) throws IllegalAccessException, InvocationTargetException, IOException {
        final var classes = getClasses(mainClass.getPackage().getName(), true);
        final var componentConatiner = ComponentContainer.getInstance();
        final var classHunter = componentConatiner.getClassHunter();
        final var packageRelPath = mainClass.getPackage().getName().replace(".", "/");
        try (ClassHunter.SearchResult result = classHunter.findBy(
                SearchConfig.forResources(
                        packageRelPath
                ).by(ClassCriteria.create().allThoseThatMatch(cls -> cls.getAnnotation(Component.class) != null))
        )) {
            for (final var classz : classes) {
                registerClass(classz);
            }

            Collection<Class<?>> types = result.getClasses();
            for (final var implementationClass : types) {
                processBeanMethods(implementationClass);
            }

            // First pass: create all instances (without injection)
            for (final var classz : classes) {
                if (classz.isAnnotationPresent(Component.class)) {
                    Object classInstance = createInstanceWithEmptyConstructor(classz);
                    applicationScope.put(classz, classInstance);
                }
            }

            for (final var entry : applicationScope.entrySet()) {
                InjectionUtil.autowire(this, entry.getKey(), entry.getValue());
            }

            initViewController(mainClass);
            triggerAllInit();
        }
    }

    private void processBeanMethods(Class<?> classz) throws InvocationTargetException, IllegalAccessException {
        for (final var method : classz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Bean.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new IllegalArgumentException("@Bean method " + method.getName() + " must be static");
                }
                final var bean = method.invoke(null);
                applicationScope.put(bean.getClass(), bean);
                diMap.put(bean.getClass(), method.getReturnType());
            }
        }
    }

    private Object createInstanceWithEmptyConstructor(Class<?> classz) {
        try {
            final var constructor = classz.getConstructors()[0];  // Assume only one constructor.
            return constructor.newInstance(new Object[constructor.getParameterCount()]);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + classz.getName(), e);
        }
    }

    private void invokeInitMethod(Object instance) throws InvocationTargetException, IllegalAccessException {
        for (final var method : instance.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(instance);
            }
        }
    }

    private void registerClass(Class<?> implementationClass) {
        final var interfaces = implementationClass.getInterfaces();
        if (interfaces.length == 0) {
            diMap.put(implementationClass, implementationClass);
        } else {
            for (Class<?> iface : interfaces) {
                diMap.put(implementationClass, iface);
            }
        }
    }

    /**
     * Get all the classes for the input package
     */
    public Class<?>[] getClasses(String packageName, boolean recursive) {
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
    private <T> T getBeanInstance(Class<T> interfaceClass) {
        return getBeanInstance(interfaceClass, null, null);
    }

    /**
     * Overload getBeanInstance to handle qualifier and autowire by type
     */
    public <T> T getBeanInstance(Class<T> interfaceClass, String fieldName, String qualifier) {
        final var implementationClass = getImplimentationClass(interfaceClass, fieldName, qualifier);
        System.out.println("Getting instance for: " + implementationClass.getName());

        if (applicationScope.containsKey(implementationClass)) {
            System.out.println("Returning existing instance for: " + implementationClass.getName());
            return interfaceClass.cast(applicationScope.get(implementationClass));
        }

        throw new RuntimeException("No instance found for: " + implementationClass.getName());
    }

    /**
     * Get the name of the implimentation class for input interface service
     */
    private Class<?> getImplimentationClass(Class<?> interfaceClass, final String fieldName, final String qualifier) {
        final var implementationClasses = diMap.entries().stream()
                .filter(entry -> (!entry.getKey().isInterface()) && entry.getValue() == interfaceClass)
                .map(Entry::getKey)
                .collect(Collectors.toSet());
        String errorMessage;
        if (implementationClasses.size() == 0) {
            errorMessage = "no implementation found for interface " + interfaceClass.getName();
        } else if (implementationClasses.size() == 1) {
            final var optional = implementationClasses.stream().findFirst();
            return optional.get();
        } else {
            final String findBy = (qualifier == null || qualifier.trim().length() == 0) ? fieldName : qualifier;
            final var optional = implementationClasses.stream()
                    .filter(entry -> entry.getSimpleName().equalsIgnoreCase(findBy)).findAny();
            if (optional.isPresent()) {
                return optional.get();
            } else {
                errorMessage = "There are " + implementationClasses.size() + " of interface " + interfaceClass.getName()
                        + " Expected single implementation or make use of @CustomQualifier to resolve conflict";
            }
        }

        throw new RuntimeException(new Error(errorMessage));
    }
}
