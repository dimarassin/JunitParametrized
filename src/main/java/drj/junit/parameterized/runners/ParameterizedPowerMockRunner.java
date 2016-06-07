package drj.junit.parameterized.runners;

import drj.junit.parameterized.utils.ParameterizedClassGenerator;
import org.powermock.modules.junit4.PowerMockRunner;

public class ParameterizedPowerMockRunner extends PowerMockRunner {

    public ParameterizedPowerMockRunner(Class<?> testClass) throws Exception {
        super(ParameterizedClassGenerator.createParametrizedClass(testClass));
    }
}
