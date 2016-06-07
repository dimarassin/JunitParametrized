package drj.junit.parameterized.utils;

import java.lang.reflect.Method;

public class TestUtils {

    public static Method resolveMethod(Object testInst, String methodName, String parameterTypes) {
        for (Method method: testInst.getClass().getSuperclass().getMethods()) {
            if (method.getName().equals(methodName) &&
                    java.util.Arrays.toString(method.getParameterTypes()).equals(parameterTypes)) {
                return method;
            }
        }
        throw new IllegalStateException("Failed to resolve test method: " + methodName + "(" + parameterTypes + ")");
    }
}
