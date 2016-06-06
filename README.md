# JunitParametrized
Allows parametrized tests with JUnit in a non static way (TestNG style)

# Supported Runners
- ParametrizedRunner
- ParametrizedSpringRunner
- ParametrizedMockitoRunner
- ParametrizedPowerMockRunner

# Example
     @RunWith(ParametrizedRunner.class)
     public class ParametrizedRunnerTest {
 
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
             System.out.println("Val: " + val);
         }
    }