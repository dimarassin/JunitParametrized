package drj.junit.parametrized.runners;

import drj.junit.parametrized.utils.ParametrizedClassGenerator;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class ParametrizedRunner extends BlockJUnit4ClassRunner {

    public ParametrizedRunner(Class<?> testClass) throws InitializationError {
        super(ParametrizedClassGenerator.createParametrizedClass(testClass));
    }
}
