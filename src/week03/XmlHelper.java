package week03;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Provides support for serializing the Employees
 * 
 * @author scottl
 *
 */
public class XmlHelper
{
	/** 
	 * Default constructor
	 */
	public XmlHelper()
	{
		
	}
	/**
	 * Saves an employee to an xml file
	 * 
	 * <p>To persist the xml to a file we need to use java.io
	 *  * The method works by creating a default document from the default XML 
	 * It then builds the document adding one employee at a time.
	 * Finally it saves the XML using the transform classes to file.</p>
	 * 
	 * <p>The sequence for using the classes is the following:<br/>
	 * <ul>
	 * <li>Use DocumentBuilderFactory to create a new factory instance()</li>
	 * <li>Create a new File instance with the file name as defined in the UML, m_EMPLOYEE_XML_FILENAME</li>
	 * <li>Create a ByteArrayInputStream from the default XML string m_INITIAL_XML<br/>
	 * <code>
	 * &lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;
	 * &lt;employees/&gt;
	 * </code>
	 * </p></li>
	 * <li>Create a FileOutputStream using the File instance for the employee)</li>
	 * <li>Use the DocumentBuilderFactory instance to create a new DocumentBuilder;)</li>
	 * <li>Use the DocumentBuilder instance to parse the ByteArrayInputStream.</li>
	 * </ul>
	 * <i>This creates a Document instance.</i>
	 * <br/>
	 * </p>
	 * Get the root element of the Document instance)<br/>
	 * 
	 * <ul>
	 * <li>Now for each employee in the list, create the XML entry for the employee</li>
	 * <li>Use the Document instance to create a new "employee" Element.</li>
	 * <li>Then create elements for First, Last and Salary.)</li>
	 * <li>Append these to the employee element.</li>
	 * <li>Append the employee element to the Document instance</li>
	 * <li>Set the text content for First, Last and Salary using the Employee data.</li>	 
	 * </ul>
	 * <p><i>This creates the XML representation of the data.</i></p>
	 * <p>
	 * Now save to an XML file.<br/>
	 * <ul>
	 * <li>Create a TransformerFactory instance</li>
	 * <li>Use the TransformerFactory to create a Transformer instance</li>
	 * <li>Set the Transform instance properties as follows:<br/>
	 * 		transformer.setOutputProperty(OutputKeys.INDENT, "yes");<br/>
	 *		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");</li>
	 * <li>This pretty prints the XML when it is saved.</li>
	 * <li>Create a DOMSource and pass in the Document instance</li>
	 * <li>Create a StreamResult instance using the output file instance</li>
	 * <li>Use the Transformer instance to transform the source to the result<br/></li>
	 * </ul>
	 *  </p>
	 * @see File
	 * @see FileOutputStream
	 * @see ByteArrayInputStream
	 * 
	 *      Uses the following java XML classes
	 * @see DocumentBuilderFactory
	 * @see DocumentBuilder
	 * @see Document
	 * @see Element
	 * 
	 *      To save as XML we use the transformer classes
	 * @see TransformerFactory
	 * @see Transformer
	 * @see DOMSource
	 * @see StreamResult
	 * 
	 * 
	 * @param list
	 *            List of Employees to save
	 * @throws CollectionsAppDataException
	 *             On error;
	 */
	public void saveEmployeeList(List<Employee> list)
			throws Exception
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		File outFile = new File(m_EMPLOYEE_XML_FILENAME);
		FileOutputStream sOut = null;
		ByteArrayInputStream inStream;

		try
		{
			// Create an InputStream from the default XML
			inStream = new ByteArrayInputStream(m_INITIAL_XML.getBytes("UTF-8"));
			sOut = new FileOutputStream(outFile);			
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inStream);
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			for(Employee emp : list)
			{
				Element employeeElement = doc.createElement("employee");
				Element first = doc.createElement("First");
				Element last = doc.createElement("Last");
				Element salary = doc.createElement("Salary");
				employeeElement.appendChild(first);
				employeeElement.appendChild(last);
				employeeElement.appendChild(salary);
				first.setTextContent(emp.getFirstName());
				last.setTextContent(emp.getLastName());
				salary.setTextContent(String.format("%f", emp.getSalary()));

				root.appendChild(employeeElement);
			}

			// Write the parsed document to an xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(outFile);
			transformer.transform(source, result);
		}

		catch(ParserConfigurationException ex)
		{
			throw new Exception("Error saving data", ex);
		}
		catch(DOMException ex)
		{
			ex.printStackTrace();
			throw new Exception("Error creating XML DOM", ex);
		}
		catch(SAXException ex)
		{
			ex.printStackTrace();
			throw new Exception("Error saving data", ex);
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
			throw new Exception("Error saving data", ex);
		}
		catch(UnsupportedEncodingException ex)
		{
			ex.printStackTrace();
			throw new Exception("Error saving data", ex);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			throw new Exception("Error saving data", ex);
		}
		catch(TransformerConfigurationException ex)
		{
			ex.printStackTrace();
			throw new Exception("Error saving to xml", ex);
		}
		catch(TransformerException ex)
		{
			ex.printStackTrace();
			throw new Exception("Error saving to xml", ex);
		}
		finally
		{
			if(sOut != null)
				try
				{
					sOut.close();
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
		}
	}

	/**
	 * This reads the source XML file and returns the list of employees. There
	 * is no caching in this implementation, so it won't scale to thousands when
	 * it comes to performance.
	 * 
	 * @see File
	 * @see FileInputStream
	 * 
	 *      Uses the following java XML classes
	 * @see DocumentBuilderFactory
	 * @see DocumentBuilder
	 * @see Document
	 * @see Element
	 * 
	 * @return List of employees from the saved file.
	 * @throws CollectionsAppDataException 
	 */
	public List<Employee> getEmployeesList() throws Exception
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		List<Employee> employeeList = new ArrayList<Employee>();

		File fileIn = new File(m_EMPLOYEE_XML_FILENAME);
		FileInputStream inStream = null;
		
		try
		{
			inStream = new FileInputStream(fileIn);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inStream);

			Element root = doc.getDocumentElement();
			employeeList = getEmployeesFromXml(root);
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
			throw new Exception("Error loading data", ex);
		}
		catch(ParserConfigurationException ex)
		{
			ex.printStackTrace();
			throw new Exception("Error creating XML DOM", ex);
		}
		catch(SAXException ex)
		{
			ex.printStackTrace();
			throw new Exception("Error creating XML DOM", ex);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			throw new Exception("Error creating XML DOM", ex);
		}
		finally
		{
			if(inStream != null)
			try
			{
				inStream.close();
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
		

		return employeeList;
	}
	
	/**
	 * Starting from the root node, get the Employee elements using XPath and
	 * generate marshal the XML data to Employee objects.
	 * 
	 * @param root The root of the XML DOM
	 * @return the list of Employees
	 * @throws CollectionsAppDataException 
	 */
	private List<Employee> getEmployeesFromXml(Element root) throws Exception
	{
		List<Employee> employeeList = new ArrayList<Employee>();
		
		NodeList list = root.getElementsByTagName("employee");
		for(int i = 0; i < list.getLength(); i++)
		{
			Element curElement = (Element)list.item(i);
			Element firstElements = (Element)curElement.getElementsByTagName("First").item(0);
			Element lastElements = (Element)curElement.getElementsByTagName("Last").item(0);
			Element salaryElements = (Element)curElement.getElementsByTagName("Salary").item(0);
			
			String first = firstElements.getTextContent();
			String last = lastElements.getTextContent();
			String salaryString = salaryElements.getTextContent();
			try
			{
				Double salary = Double.parseDouble(salaryString);
				Employee newEmployee = new Employee(first, last, salary);
				employeeList.add(newEmployee);
			}
			catch(NumberFormatException ex)
			{
				throw new Exception("Error loading data", ex);
			}
		}
		
		return employeeList;
	}

	private static String m_INITIAL_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<employees/>";
	
	private static String m_EMPLOYEE_XML_FILENAME = "employees.xml";
}
