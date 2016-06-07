package drj.junit.parameterized.runners;

import drj.junit.parameterized.utils.ParameterizedClassGenerator;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class ParameterizedRunner extends BlockJUnit4ClassRunner {

    public ParameterizedRunner(Class<?> testClass) throws InitializationError {
        super(ParameterizedClassGenerator.createParametrizedClass(testClass));
    }
}
