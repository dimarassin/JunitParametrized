package drj.junit.parametrized.utils;

import drj.junit.parametrized.runners.ParametrizedTest;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParametrizedMethodGenerator {

    public static String createMethod(Method test, int permutations) {
        StringBuilder source = new StringBuilder();

        IntStream.range(0, permutations).forEach(p ->
                source.append("\n@").append(Test.class.getName())
                        .append(resolveAnnotations(test))
                        .append("\npublic void ").append(test.getName()).append("_").append(p).append("() throws Exception {")
                        .append("\n\t").append(TestUtils.class.getName())
                        .append('.').append("resolveMethod(this, \"").append(test.getName())
                        .append("\", \"")
                        .append(Arrays.toString(test.getParameterTypes()))
                        .append("\").invoke(this, ").append(resolveDataProvider(test)).append("()[").append(p).append("]);")
                        .append("\n}")
        );

        return source.toString();
    }

    private static String resolveAnnotations(Method test) {
        StringBuilder source = new StringBuilder();

        Arrays.stream(test.getDeclaredAnnotations())
                .filter(a -> a.annotationType() != Test.class)
                .forEach(a -> source.append("\n@")
                        .append(a.annotationType().getName())
                        .append("(")
                        .append(resolveAnnotationValues(a))
                        .append(")"));

        return source.toString();
    }

    private static String resolveAnnotationValues(Annotation annotation) {
        return Arrays.stream(annotation.annotationType().getDeclaredMethods())
                .map(m -> m.getName() + "=" + toAnnotationValue(annotation, m))
                .filter(v -> v != null)
                .collect(Collectors.joining(", "));
    }

    private static String toAnnotationValue(Annotation annotation, Method method) {
        try {
            return Optional.ofNullable(method.invoke(annotation))
                    .filter(v -> v != null)
                    .map(ParametrizedMethodGenerator::annotationValueToString)
                    .orElse(null);
        } catch (Exception ex) {
            throw new IllegalStateException(String.format("Failed to resolve value %s.%s",
                    method.getDeclaringClass().getSimpleName(), method.getName()));
        }
    }

    private static String annotationValueToString(Object value) {
        if (value instanceof String) {
            return "\"" + value + "\"";
        } else if (value instanceof Class) {
            return ((Class) value).getCanonicalName() + ".class";
        } else {
            return value.toString();
        }
    }


    private static String resolveDataProvider(Method test) {
        return test.getAnnotation(ParametrizedTest.class).value();
    }
}
