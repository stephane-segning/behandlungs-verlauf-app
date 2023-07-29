package team.sema.dpa.digitalpatientenakte.state;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.burningwave.core.classes.FieldCriteria;

import java.lang.reflect.Field;

import static org.burningwave.core.assembler.StaticComponentContainer.Fields;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InjectionUtil {

    /**
     * Perform injection recursively, for each service inside the Client class
     */
    public static void autowire(Injector injector, Class<?> classz, Object classInstance) {
        final var fields = Fields.findAllAndMakeThemAccessible(
                FieldCriteria.forEntireClassHierarchy().allThoseThatMatch(field ->
                        field.isAnnotationPresent(Autowired.class)
                ),
                classz
        );
        for (Field field : fields) {
            String qualifier = field.isAnnotationPresent(Qualifier.class)
                    ? field.getAnnotation(Qualifier.class).value()
                    : null;
            System.out.println("Looking for an instance of " + field.getType().getName() + " with qualifier " + qualifier + " for field " + field.getName() + " in class " + classz.getName() + " with instance " + classInstance);
            Object fieldInstance = injector.getBeanInstance(field.getType(), field.getName(), qualifier);
            System.out.println("Found instance " + fieldInstance);
            Fields.setDirect(classInstance, field, fieldInstance);
            autowire(injector, fieldInstance.getClass(), fieldInstance);
        }
    }

}
