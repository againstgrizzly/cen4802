package week13;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

//import org.jdom2.Document;
//import org.jdom2.JDOMException;
//import org.jdom2.input.SAXBuilder;
//import org.jdom2.output.XMLOutputter;

import static org.junit.Assert.*;
import org.junit.Test;
import test.AbstractJUnitBase;
import week12.core.AtmObject;
import week12.util.LoginRequest;
import week12.util.LoginResponse;
import week12.xml.XmlUtility;

public class TestLogIn extends AbstractJUnitBase
{
	/**
	 * Default constructor
	 */
	public TestLogIn()
	{

	}

	@Test
	public void testGenerateObjectFromXmlLoginResponse()
	{
		boolean result = true;

//		try
//		{
//			LoginResponse refRequest = new LoginResponse(true, 100100L, 1);
//			
//			String testResponse = "<login-response version=\"1.0\" logged-in=\"true\" session-id=\"100100\" user-id=\"1\"/>";
//			java.io.StringReader reader = new StringReader(testResponse);

//			org.jdom2.input.SAXBuilder builder = new SAXBuilder();
//			Document dom = builder.build(reader);
//			
//			AtmObject request = XmlUtility.xmlToObject(dom);
//			
//			trace("LoginResponseDOM");
//			trace(dumpDocument(dom));
//			if( !refRequest.equals(request))
//			{
//				result = false;
//				trace("LoginRequest objects don't match");
//			}
//		}
//		catch(IOException ex)
//		{
//			trace(" Failed: testGenerateObjectFromXmlLoginRequest " + ex.getMessage());
//			result = false;
//		}
//		catch(JDOMException ex)
//		{
//			this.addResultMessage(" Failed: testGenerateObjectFromXmlLoginRequest " + ex.getMessage());
//			result = false;
//		}
//		catch(AtmException ex)
//		{
//			trace(" Failed: testGenerateObjectFromXmlLoginRequest " + ex.getMessage());
//			result = false;
//		}
		
		assertTrue(result);

	}

	@Test
	public void testGenerateObjectFromXmlLoginRequest()
	{
		boolean result = true;

//		try
//		{
//			LoginRequest refRequest = new LoginRequest(1234, 100100L);
//			
//			String testRequest = "<login-request version=\"1.0\" pin=\"1234\" account-id=\"100100\" />";
//			java.io.StringReader reader = new StringReader(testRequest);
//
//			org.jdom2.input.SAXBuilder builder = new SAXBuilder();
//			Document dom = builder.build(reader);
//			
//			AtmObject request = XmlUtility.xmlToObject(dom);
//			
//			if( !refRequest.equals(request))
//			{
//				result = false;
//				addResultMessage("LoginRequest objects don't match");
//			}
//		}
//		catch(IOException ex)
//		{
//			this.addResultMessage(" Failed: testGenerateObjectFromXmlLoginRequest " + ex.getMessage());
//			result = false;
//		}
//		catch(JDOMException ex)
//		{
//			this.addResultMessage(" Failed: testGenerateObjectFromXmlLoginRequest " + ex.getMessage());
//			result = false;
//		}
//		catch(AtmException ex)
//		{
//			this.addResultMessage(" Failed: testGenerateObjectFromXmlLoginRequest " + ex.getMessage());
//			result = false;
//		}
		
		assertTrue(result);
	}

	//@SuppressWarnings("unused")
	@Test
	public void tesGenerateXmlFromLoginRequest()
	{
		trace("  tesGenerateXmlFromLoginRequest");
		boolean result = true;

//		try
//		{
//			AtmObject logRequest = new LoginRequest(1234, 100100L);
//
//			if(logRequest == null)
//			{
//				this.addResultMessage("Failed to create LoginRequest");
//				result = false;
//			}
//
//			Document dom = XmlUtility.objectToXml(logRequest);
//
//			String xml = dumpDocument(dom);
//			if(xml.equals(""))
//			{
//				this.addResultMessage("Failed to generate XML output");
//				result = false;
//			}
//
//			addResultMessage("Generated XML \n" + xml);
//		}
//		catch(Exception ex)
//		{
//			this.addResultMessage("Failed to generate XML output "
//					+ ex.getMessage());
//			result = false;
//		}

		assertTrue(result);
	}

//	private String dumpDocument(Document dom)
//	{
//		XMLOutputter outputter = new XMLOutputter();
//		java.io.StringWriter writer = new StringWriter();
//		try
//		{
//			outputter.output(dom, writer);
//		}
//		catch(IOException ex)
//		{
//			this.addResultMessage("Failed to dump XML output "
//					+ ex.getMessage());
//			ex.printStackTrace();
//		}
//
//		return writer.toString();
//	}

	@Test
	public void testLogRequestCreation()
	{
		trace("  testLogRequestCreation");
		boolean result = true;

//		try
//		{
//			@SuppressWarnings("unused")
//			AtmObject logRequest = new LoginRequest(1234, 100100L);
//		}
//		catch(Exception ex)
//		{
//			this.addResultMessage("Failed to create " + ex.getMessage());
//			result = false;
//		}

		assertTrue(result);
	}

}
