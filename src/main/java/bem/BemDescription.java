package bem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by nizienko on 15.11.2016.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface BemDescription {
    String block() default "";
    String element() default "";
    Modifier[] modifiers() default {};
    boolean noAncestors() default false;
    String hasText() default "";
    MixedBlock[] mixed() default {};

    // modifiers = @bem.Modifier(key = "name", value = "some_value")
    String m_name() default "";
}
