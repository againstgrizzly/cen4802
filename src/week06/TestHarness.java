package week06;

import java.util.List;

import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import test.AbstractTestHarness;

/**
 * This class executes the JUnit Test specified from the command line
 * This will be used by the reference system for testing your code.
 *
 * @author Scott LaChance
 *
 */
public class TestHarness extends AbstractTestHarness
{

    public static void main(String[] args)
    {
        new TestHarness().runTests();
    }

    /**
     * Implements the base class abstract method
     */
    protected void runTests()
    {
        try
        {
            boolean appTest = executeTest(JUnitWeek06.class);
            boolean result = appTest;//&& testData && testLogging && testLogin && testJavadoc;

            trace(result ? "Tests Passed" : "Tests Failed");
        }
        catch(Exception ex)
        {
            trace(ex.getMessage());
        }
    }
}
