package week02;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import test.AbstractJUnitBase;
import test.TestResult;
import test.TestResultDetail;
import test.javadoc.ConstructorTestData;
import test.javadoc.FileTestData2;
import test.javadoc.JUnitJavadocValidationUtility2;
import test.javadoc.MethodTestData2;

/**
 * Tests the Javadoc in the source file.
 * 
 * @author Scott
 *
 */
public class JUnitJavadocValidation extends AbstractJUnitBase
{
	public JUnitJavadocValidation()
	{
		m_stream = System.out; // Standard out
		List<FileTestData2> testFiles = new ArrayList<FileTestData2>();
		
		// Die.java
		List<ConstructorTestData> constructors = new ArrayList<ConstructorTestData>();
		constructors.add(new ConstructorTestData("Die", 0, "public"));
		
		List<MethodTestData2> methods = new ArrayList<MethodTestData2>();
				
		methods.add(new MethodTestData2("getNumber", "", "int","public"));
		methods.add(new MethodTestData2("roll", "", "void","public"));
		
		testFiles.add(new FileTestData2("week02", "Die.java", methods, constructors));
		
		// DieVer2.java
		List<ConstructorTestData> constructors2 = new ArrayList<ConstructorTestData>();
		constructors2.add(new ConstructorTestData("DieVer2", 0, "public"));
		
		List<MethodTestData2> methods2 = new ArrayList<MethodTestData2>();
				
		methods2.add(new MethodTestData2("getNumber", "", "int","public"));
		methods2.add(new MethodTestData2("roll", "", "void","public"));
		
		testFiles.add(new FileTestData2("week02", "DieVer2.java", methods2, constructors2));
		
		// DieVer3.java		
		List<ConstructorTestData> constructors3 = new ArrayList<ConstructorTestData>();
		constructors3.add(new ConstructorTestData("DieVer3", 1, "public"));
		
		List<MethodTestData2> methods3 = new ArrayList<MethodTestData2>();
				
		methods3.add(new MethodTestData2("getNumber", "", "int","public"));
		methods3.add(new MethodTestData2("roll", "", "void","public"));
		
		testFiles.add(new FileTestData2("week02", "DieVer3.java", methods3, constructors3));
		
		// ManySidedDie.java		
		List<ConstructorTestData> constructors4 = new ArrayList<ConstructorTestData>();
		constructors4.add(new ConstructorTestData("ManySidedDie", 2, "public"));
		
		List<MethodTestData2> methods4 = new ArrayList<MethodTestData2>();
				
		methods4.add(new MethodTestData2("getNumber", "", "int","public"));
		methods4.add(new MethodTestData2("roll", "", "void","public"));
		
		testFiles.add(new FileTestData2("week02", "ManySidedDie.java", methods4, constructors4));
		
		
		
		m_validator = new JUnitJavadocValidationUtility2("Week02 Javadoc Test",
				testFiles);
	}

	@Test
	public void testJavadoc()
	{
		trace("** Validating Javadoc **");

		// Update these values for each assignment
		// m_packageName = "week02";
		TestResult result = m_validator.runTest();
		StringBuilder details = new StringBuilder();
		if(!result.passed())
		{			
			List<TestResultDetail> detailList = result.getTestResultDetails();
			for(TestResultDetail detail : detailList)
			{
				trace(detail.testDetails());
				details.append(detail.testDetails());
				details.append(CRLF);
			}
		}
		trace(String.format("** Test result: %s **", result.passed() ? "Passed" : "Failed"));
		assertTrue(details.toString(), result.passed());
	}
}
