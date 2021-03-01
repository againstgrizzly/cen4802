package week08;

import test.AbstractTestHarness;


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
	 * Implements the base class abstract method
	 */
	protected void runTests()
	{
		try
		{
			boolean reverseTest = executeTest(JUnitTest.class);
			boolean submissionTest = executeTest(Week08JUnitTest.class);
			
			boolean result = reverseTest && submissionTest;

			trace(result ? "PASSED" : "FAILED");
		}
		catch(Exception ex)
		{
			trace(ex.getMessage());
		}
	}
}