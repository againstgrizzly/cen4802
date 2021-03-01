package week02;

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
	 * Implements the baseclass abstract method
	 */
	protected void runTests()
	{
		try
		{
			boolean testDie = executeTest(TestCaseDie.class);
			boolean testDie2 = executeTest(TestCaseDieV2.class);
			boolean testDieMany = executeTest(TestCaseDieMany.class);
			boolean testJavadoc = executeTest(JUnitJavadocValidation.class);
			boolean result = testDie && testDie2 && testDieMany && testJavadoc;

			trace(result ? "Tests Passed" : "Tests Failed");
		}
		catch(Exception ex)
		{
			trace(ex.getMessage());
		}
	}
}