/**
 * XmlHelperTest.java
 * 
 * COP4802 Advanced Java
 */
package week03;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

//import junit.framework.TestCase;

/**
 * @author scottl
 *
 */
public class XmlHelperTest
{ 
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(new Employee("Scott", "LaChance", 35000.00));
		XmlHelper helper = new XmlHelper();
		try
		{
			trace("Testing save");
			helper.saveEmployeeList(employees);
		}
		catch(Exception e)
		{
			String msg = "Failed to setup xml test file" + e.getMessage();
			fail(msg);			
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	/**
	 * Test method for {@link week03.XmlHelper#saveEmployeeList(java.util.List)}
	 * .
	 */
	@Test
	public void testSaveEmployeeList()
	{
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(new Employee("Scott", "LaChance", 35000.00));
		employees.add(new Employee("Jodi", "Boeddeker", 45000.00));
		employees.add(new Employee("Jim", "Taubitz", 65000.00));
		employees.add(new Employee("Jeff", "Nichols", 6522.45));

		XmlHelper helper = new XmlHelper();
		try
		{
			trace("Testing save");
			helper.saveEmployeeList(employees);

			trace("Testing load");
			// try to read the data
			List<Employee> list = helper.getEmployeesList();
			if(employees.size() != list.size())
			{
				fail("Sizes don't match for test data vs actual data");
			}
			else
			{
				String msg = "";
				boolean fSuccess = true;
				for(int i = 0; i < employees.size(); i++)
				{
					Employee expected = employees.get(i);
					Employee actual = list.get(i);
					if(!expected.equals(actual))
					{
						fSuccess = false;
						msg += String.format(
								"Expected: %s, found %s not equal\n",
								expected.toString(), actual.toString());
					}
				}

				if(!fSuccess)
				{
					fail("Error comparing expected with actual data\n" + msg);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This test verifies the XmlHelper creates the XML
	 * IAW with the specified schema
	 * 
	 */
	@Test
	public void testXmlSchemaValidation()
	{
		String schemaFileName = "src/week03/employees.xsd";
		File file = new File(schemaFileName);
		try
		{
			trace(file.getCanonicalPath());
		}
		catch(IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String xmlFileName = "employees.xml";
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(true);
		
		try
		{	
			// define the type of schema - we use W3C:
	      String schemaLang = "http://www.w3.org/2001/XMLSchema";
	 
	      // get validation driver:
	      SchemaFactory factory = SchemaFactory.newInstance(schemaLang);
	 
	      // create schema by reading it from an XSD file:
	      Schema schema = factory.newSchema(new StreamSource(schemaFileName));
	      Validator validator = schema.newValidator();
	 
	      // at last perform validation:
	      validator.validate(new StreamSource(xmlFileName));
		}
		catch(IllegalArgumentException x)
		{
			String msg = "Error: JAXP DocumentBuilderFactory attribute "
					+ "not recognized: " + JAXP_SCHEMA_LANGUAGE + " " + x.getMessage();
			trace(msg);
			trace("Check to see if parser conforms to JAXP spec.");
			fail(msg);
		}
		catch(SAXException ex)
		{
			String msg = "Error: SAXException: " + ex.getMessage();
			trace(msg);
			fail(msg);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Writes message to standard out
	 * 
	 * @param msg
	 *            The message to write
	 */
	private void trace(String msg)
	{
		System.out.println(msg);
	}

	static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
}
