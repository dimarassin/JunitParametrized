package drj.junit.parameterized.runners;

import drj.junit.parameterized.utils.ParameterizedClassGenerator;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class ParameterizedSpringRunner extends SpringJUnit4ClassRunner {

    public ParameterizedSpringRunner(Class<?> testClass) throws InitializationError {
        super(ParameterizedClassGenerator.createParametrizedClass(testClass));
    }
}
