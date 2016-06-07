package drj.junit.parameterized.runners;

import drj.junit.parameterized.utils.ParameterizedClassGenerator;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;

public class ParameterizedMockitoRunner extends MockitoJUnitRunner {

    public ParameterizedMockitoRunner(Class<?> testClass) throws InvocationTargetException {
        super(ParameterizedClassGenerator.createParametrizedClass(testClass));
    }
}
