package bem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by nizienko on 21.11.2016.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface MixedBlock {
    String block() default "";
    String element() default "";
    Modifier[] modifiers() default {};
}
