package drj.junit.parameterized.runners;

import org.junit.Test;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ParameterizedTest {

    @AliasFor("dataProvider")
    String value() default "";

    @AliasFor("value")
    String dataProvider() default "";

    Class<? extends Throwable> expected() default Test.None.class;

    long timeout() default 0L;
}
