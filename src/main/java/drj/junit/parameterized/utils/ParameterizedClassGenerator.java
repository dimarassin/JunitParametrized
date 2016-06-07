package drj.junit.parameterized.utils;

import drj.junit.parameterized.runners.ParameterizedTest;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParameterizedClassGenerator {

    public static Class<?> createParametrizedClass(Class<?> testClass) {
        List<Method> parametrizedTests = resolveParametrizedTests(testClass);

        return parametrizedTests.isEmpty() ? testClass : createParametrizedClass(testClass, parametrizedTests);

    }

    private static List<Method> resolveParametrizedTests(Class<?> testClass) {
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(ParameterizedTest.class))
                .collect(Collectors.toList());
    }

    private static Class<?> createParametrizedClass(Class<?> testClass, List<Method> parametrizedTests) {
        try {
            File sourceFile = createSourceFile(testClass, parametrizedTests);
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, sourceFile.getPath());
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{sourceFile.getParentFile().toURI().toURL()});
            return Class.forName(testClass.getSimpleName(), true, classLoader);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to run parametrized test", ex);
        }
    }

    private static File createSourceFile(Class<?> testClass, List<Method> parametrizedTests) throws Exception {
        File parentFolder = new File(testClass.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile();
        File sourceFile = new File(parentFolder, String.format("generated/%s.java", testClass.getSimpleName()));
        FileUtils.writeStringToFile(sourceFile, createSource(testClass, parametrizedTests));
        return sourceFile;
    }

    private static String createSource(Class<?> testClass, List<Method> parametrizedTests) throws Exception {
        StringBuilder source = new StringBuilder("public class ").append(testClass.getSimpleName())
                .append(" extends ").append(testClass.getName()).append("{");

        Object testInst = testClass.newInstance();

        parametrizedTests.forEach(test -> appendTest(source, testInst, test));

        source.append("\n}");

        return source.toString();
    }

    private static void appendTest(StringBuilder source, Object testInst, Method test) {
        String dataProvider = resolveDataProvider(test);
        int permutations = calculatePermutations(testInst, dataProvider);

        IntStream.range(0, permutations).forEach(p ->
                source.append("\n@").append(Test.class.getName())
                        .append("\npublic void ").append(test.getName()).append("_").append(p).append("() throws Exception {")
                        .append("\n\t").append(TestUtils.class.getName())
                        .append('.').append("resolveMethod(this, \"").append(test.getName())
                        .append("\", \"")
                        .append(Arrays.toString(test.getParameterTypes()))
                        .append("\").invoke(this, ").append(dataProvider).append("()[").append(p).append("]);")
                        .append("\n}")
        );
    }

    private static String resolveDataProvider(Method test) {
        return test.getAnnotation(ParameterizedTest.class).value();
    }

    private static int calculatePermutations(Object testInst, String dataProviderName) {
        try {
            Method dataProvider = testInst.getClass().getDeclaredMethod(dataProviderName);
            return ((Object[][]) dataProvider.invoke(testInst)).length;
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to ");
        }
    }
}
