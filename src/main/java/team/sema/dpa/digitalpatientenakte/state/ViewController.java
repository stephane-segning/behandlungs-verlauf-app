package team.sema.dpa.digitalpatientenakte.state;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewController {
    String name();

    String view();
}
