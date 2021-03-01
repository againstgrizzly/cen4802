package week03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * This class executes the JUnit Test specified from the command line This will
 * be used by the reference system for testing your code.
 * 
 * @author Scott LaChance
 *
 */
public class TestHarness
{
	public static void main(String[] args)
	{
		trace(" testing student JUnit execution");
		boolean success1 = testEmployeeTestJUnit();

		boolean success2 = inspectEmployeeTestJunit();

		if(success1 && success2)
		{
			trace("****** Test Success!");
		}
		else
		{
			trace("****** Test failed!");
		}
	}

	/**
	 * This opens the employee test file and looks for all the methods that need
	 * to be tested.
	 * 
	 * @return true if all the Employee methods are executed, otherwise false
	 */
	private static boolean inspectEmployeeTestJunit()
	{
		boolean success = true;

		FileReader fileReader = null;
		BufferedReader reader = null;
		try
		{
			// read in the file into memory
			//File employeeTestFile = new File("src/week03/EmployeeTest.java");
			File employeeTestFile = new File("../src/EmployeeTest.java");
			if(employeeTestFile.exists())
			{
				fileReader = new FileReader(employeeTestFile);
				reader = new BufferedReader(fileReader);
				StringBuilder buffer = new StringBuilder();
				String line = "";
				while((line = reader.readLine()) != null)
				{
					buffer.append(line);
					buffer.append('\n');
				}

				success = inspectFile(buffer);
			}
			else
			{
				String msg = String.format("File doesn't exist at '%s'", employeeTestFile.getCanonicalPath());
				trace(msg);
				success = false;
			}
		}
		catch(IOException ex)
		{
			trace(ex.getMessage());
			success = false;
		}
		finally
		{
			if( reader != null )try{reader.close();}catch(IOException ex){}
		}
		return success;
	}

	private static boolean inspectFile(StringBuilder buffer)
	{
		boolean success = true;

		// Look for the following strings
		ArrayList<String> testsToFind = new ArrayList<String>();
		testsToFind.add("getFirstName(");
		testsToFind.add("getLastName(");
		testsToFind.add("setFirstName(");
		testsToFind.add("setLastName(");
		testsToFind.add("getFirstName(");
		testsToFind.add("getSalary(");
		testsToFind.add("getFormattedSalary(");
		testsToFind.add("Employee()");

		String twoParameterConstructorRegEx = ".*Employee\\(\".*\", \".*\"\\).*";
		String threeParameterConstructorRegEx =  ".*Employee\\(\".*\", \".*\", .*\\).*" ;

		String text = buffer.toString();

		for(String s : testsToFind)
		{
			if(!text.contains(s))
			{
				String msg = String.format("test doesn't execute '%s'", s);
				trace(msg);
				success = false;
			}
		}

		// Check the constructors
		trace("testing two parameter constructor");
		Pattern p1 = Pattern.compile(twoParameterConstructorRegEx);
		Matcher m = p1.matcher(text);
		if(!m.find())
		{
			String msg = String.format(
					"failed to match parameterized constructor '%s'",
					twoParameterConstructorRegEx);
			trace(msg);
			success = false;
		}
		
		trace("testing three parameter constructor");
		Pattern p2 = Pattern.compile(threeParameterConstructorRegEx);
		m = p2.matcher(text);
		if(!m.find())
		{
			String msg = String.format(
					"failed to match parameterized constructor '%s'",
					threeParameterConstructorRegEx);
			trace(msg);
			success = false;
		}

		if(!success)
		{
			// dump the text for analysis
			trace(text);
		}

		return success;
	}

	private static boolean testEmployeeTestJUnit()
	{
		boolean success = true;
		Result result = org.junit.runner.JUnitCore
				.runClasses(EmployeeTest.class);
		int failCount = result.getFailureCount();
		if(failCount > 0)
		{
			List<Failure> failures = result.getFailures();
			for(Failure fail : failures)
			{
				trace("FAILED: " + fail.getMessage());
				success = false;
			}
		}

		return success;
	}

	private static void trace(String msg)
	{
		System.out.println(msg);
	}
}
