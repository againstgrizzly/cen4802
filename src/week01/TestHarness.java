package week01;

import test.AbstractTestHarness;


/**
 * File: TestHarness.java
 * @author Scott LaChance
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
			boolean javadocTest = executeTest(JUnitJavadocValidation.class);
			boolean helloWorldTest = executeTest(TestHelloWorld.class);
			boolean result = javadocTest && helloWorldTest;

			trace(result ? "Tests Passed" : "Tests Failed");
		}
		catch(Exception ex)
		{
			trace(ex.getMessage());
		}
	}
}