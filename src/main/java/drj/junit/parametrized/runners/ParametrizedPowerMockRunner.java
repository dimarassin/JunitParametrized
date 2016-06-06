package drj.junit.parametrized.runners;

import drj.junit.parametrized.utils.ParametrizedClassGenerator;
import org.powermock.modules.junit4.PowerMockRunner;

public class ParametrizedPowerMockRunner extends PowerMockRunner {

    public ParametrizedPowerMockRunner(Class<?> testClass) throws Exception {
        super(ParametrizedClassGenerator.createParametrizedClass(testClass));
    }
}
