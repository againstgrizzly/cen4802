package week12;

import java.util.ArrayList;
import java.util.List;

//import week08.test.JUnitAccountTest;
import week12.test.JUnitAccessTest;
import week12.test.JUnitUserTest;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * This test harness runs the JUnit test cases.
 * Used by the evaluation environment, so your tests must pass this.
 * 
 * @author Scott LaChance
 *
 */
public class TestHarnessJUnit
{
	public static void main(String[] args)
	{
		//Result accountResult = org.junit.runner.JUnitCore.runClasses(JUnitAccountTest.class);
		Result accountResult = org.junit.runner.JUnitCore.runClasses(JUnitAccessTest.class);
		Result userResult = org.junit.runner.JUnitCore.runClasses(JUnitUserTest.class);
		int failCount = accountResult.getFailureCount() + userResult.getFailureCount();
		if( failCount > 0 )
		{
			List<Failure> accountFailures = accountResult.getFailures();
			List<Failure> userFailures = userResult.getFailures();
			List<Failure> failures = new ArrayList<Failure>();
			failures.addAll(accountFailures);
			failures.addAll(userFailures);
			for(Failure fail : failures)
			{
				String msg = String.format("FAILED: %s - %s", fail.getTestHeader(), fail.getMessage());
				trace(msg);
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
