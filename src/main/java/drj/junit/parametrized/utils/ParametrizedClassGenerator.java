package drj.junit.parametrized.utils;

import drj.junit.parametrized.runners.ParametrizedTest;
import org.apache.commons.io.FileUtils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static drj.junit.parametrized.utils.ParametrizedMethodGenerator.createMethod;

public class ParametrizedClassGenerator {

    public static Class<?> createParametrizedClass(Class<?> testClass) {
        List<Method> parametrizedTests = resolveParametrizedTests(testClass);

        return parametrizedTests.isEmpty() ? testClass : createParametrizedClass(testClass, parametrizedTests);
    }

    private static List<Method> resolveParametrizedTests(Class<?> testClass) {
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(ParametrizedTest.class))
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

        parametrizedTests.forEach(test -> source.append(createMethod(test, calculatePermutations(testClass, test))));

        source.append("\n}");

        return source.toString();
    }

    private static int calculatePermutations(Class<?> testClass, Method test) {
        try {
            Method dataProvider = testClass.getDeclaredMethod(resolveDataProvider(test));
            return ((Object[][]) dataProvider.invoke(testClass.newInstance())).length;
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to ");
        }
    }

    private static String resolveDataProvider(Method test) {
        return test.getAnnotation(ParametrizedTest.class).value();
    }
}
