package drj.junit.parametrized.runners;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ParametrizedTest {

    String value() default "";

    String dataProvider() default "";

    Class<? extends Throwable> expected() default Test.None.class;

    long timeout() default 0L;
}
