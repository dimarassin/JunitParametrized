# JunitParameterized
Allows parameterized tests with JUnit in a non static way (TestNG style)

# Supported Runners
- ParameterizedRunner
- ParameterizedSpringRunner
- ParameterizedMockitoRunner
- ParameterizedPowerMockRunner

# Example
     @RunWith(ParametrizedRunner.class)
     public class ParameterizedRunnerTest {
 
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