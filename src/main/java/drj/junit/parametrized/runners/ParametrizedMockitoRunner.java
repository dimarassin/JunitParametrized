package drj.junit.parametrized.runners;

import drj.junit.parametrized.utils.ParametrizedClassGenerator;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;

public class ParametrizedMockitoRunner extends MockitoJUnitRunner {

    public ParametrizedMockitoRunner(Class<?> testClass) throws InvocationTargetException {
        super(ParametrizedClassGenerator.createParametrizedClass(testClass));
    }
}
