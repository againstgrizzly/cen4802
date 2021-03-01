package week12;

import test.AbstractTestHarness;
import week12.app.AccountTestCase;
import week12.app.UserTestCase;
import week12.data.DataAccessTestCase;

/**
 * File: TestHarness.java
 */
class TestHarness extends AbstractTestHarness
{

	public static void main(String[] args)
	{
		new TestHarness().runTests();

	}

	/**
	 * Implements the baseclass abstract method
	 */
	protected void runTests()
	{
		try
		{
			boolean test1 = executeTest(AccountTestCase.class);
			boolean test2 = executeTest(UserTestCase.class);
			boolean test3 = executeTest(DataAccessTestCase.class);
			boolean test4 = executeTest(TestLogging.class);
			boolean test5 = executeTest(TestLogIn.class);
			boolean result = test1 && test2 && test3 && test4 && test5;

			trace(result ? "Tests Passed" : "Tests Failed");
		}
		catch(Exception ex)
		{
			trace(ex.getMessage());
		}
	}
}