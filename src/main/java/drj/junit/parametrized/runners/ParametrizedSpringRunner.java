package drj.junit.parametrized.runners;

import drj.junit.parametrized.utils.ParametrizedClassGenerator;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class ParametrizedSpringRunner extends SpringJUnit4ClassRunner {

    public ParametrizedSpringRunner(Class<?> testClass) throws InitializationError {
        super(ParametrizedClassGenerator.createParametrizedClass(testClass));
    }
}
