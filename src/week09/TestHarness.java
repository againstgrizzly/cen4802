package week09;

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
		trace("TestHarness v2");
		Result result = org.junit.runner.JUnitCore.runClasses(JUnitWeek09.class);
		int failCount = result.getFailureCount();
		int testsRun = result.getRunCount();
		if( failCount > 0 )
		{
			List<Failure> failures = result.getFailures();
			for(Failure fail : failures)
			{
				String msg = String.format("FAILED %d tests failed out of %d tests", failCount, testsRun);
				trace(msg);
				trace(fail.getMessage());
			}
		}
		else
		{
			String msg = String.format("SUCCESS %d tests", testsRun);
			trace(msg);
		}
	}

	private static void trace(String msg)
	{
		System.out.println(msg);
	}
}
