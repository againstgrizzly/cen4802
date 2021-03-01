package week05;

import java.util.List;

import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * This class executes the JUnit Test specified from the command line
 * This will be used by the reference system for testing your code.
 * 
 * @author Scott LaChance
 *
 */
public class TestHarness
{
	public static void main(String[] args)
	{
		Result result = org.junit.runner.JUnitCore.runClasses(Week05JUnitTest.class);
		int failCount = result.getFailureCount();
		if( failCount > 0 )
		{
			List<Failure> failures = result.getFailures();
			for(Failure fail : failures)
			{
				trace("Test Harness FAILED: " + fail.getMessage());
			}
		}
		else
		{
			trace("SUCCESS");
		}
	}

	private static void trace(String msg)
	{
		System.out.println(msg);
	}
}
