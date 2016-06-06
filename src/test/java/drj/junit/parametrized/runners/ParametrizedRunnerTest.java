package drj.junit.parametrized.runners;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(ParametrizedRunner.class)
public class ParametrizedRunnerTest {

    @Rule
    public TestName name = new TestName();

    @Test
    public void regularJunitTest() {
        assertTrue(true);
    }

    public Object[][] singleStringData() {
        return new Object[][]{
                {"val1"},
                {"val2"},
                {"val3"},
        };
    }

    @ParametrizedTest("singleStringData")
    public void singleString(String val) {
        System.out.println(name.getMethodName() + ": " + val);
    }

    public Object[][] primitiveData() {
        return new Object[][]{
                {1, 2, true},
                {7, 4, false}
        };
    }

    @ParametrizedTest("primitiveData")
    public void primitives(int val1, long val2, boolean expected) {
        System.out.println(String.format("%s: %d < %d => %b", name.getMethodName(), val1, val2, expected));
        assertThat(val1 < val2, is(expected));
    }
}
