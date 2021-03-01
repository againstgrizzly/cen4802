package week12.xml;

import week12.AtmException;
import week12.core.AtmObject;
import week12.util.AtmLogger;
import week12.util.LoginRequest;
import week12.util.LoginResponse;

import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Logger;

//import org.jdom2.Attribute;
//import org.jdom2.Document;
//import org.jdom2.Element;
//import org.jdom2.JDOMException;
//import org.jdom2.input.SAXBuilder;
//import org.jdom2.output.XMLOutputter;

/**
 * Provides a set of utility methods to manage XML
 * 
 * @author scottl
 * 
 */
public class XmlUtility
{
	protected final static Logger logger = Logger
			.getLogger(AtmLogger.ATM_LOGGER + "." + XmlUtility.class.getName());
	
	/**
	 * Serializes an AtmObject reference to an XML document
	 * 
	 * @param obj
	 *            AtmObject to serialize
	 * @return XML Document
	 */
//	public static Document objectToXml(AtmObject obj) throws AtmException
//	{
//		Document dom = null;
//
//		if(obj instanceof LoginRequest)
//		{
//			dom = generateLoginRequestXml((LoginRequest)obj);
//		}
//		else if( obj instanceof LoginResponse)
//		{
//			dom = generateLoginResponseXml((LoginResponse)obj);
//		}
//		else
//		{
//			throw new AtmException("Unknown AtmObject");
//		}
//
//		return dom;
//	}

	/**
	 * Takes an XML document and converts it to the appropriate ATM domain
	 * object.
	 * 
	 * @param dom
	 *            Document reference
	 * @return Appropriate ATM domain object
	 */
//	public static AtmObject xmlToObject(Document dom)
//		throws AtmException
//	{
//		AtmObject atmObject = null;
//
//		// get the root and determine the type of object
//		Element root = dom.getRootElement();
//		String elementName = root.getName();
//		try
//		{
//			// extend this section to build the appropriate object
//			if(elementName.equals("login-request"))
//			{
//				// generate the LoginRequest object
//				atmObject = getLoginObject(root);
//			}
//			// extend this section to build the appropriate object
//			else if(elementName.equals("login-response"))
//			{
//				// generate the LoginResponse object
//				atmObject = getLoginResponseObject(root);
//			}
//		}
//		catch(NumberFormatException ex)
//		{
//			throw new AtmException(ex);
//		}
//
//		return atmObject;
//	}

	/**
	 * Generates an XML Document from an XML string.
	 * Used by the communications protocol
	 * 
	 * @param xml XML string to create a document for
	 * @return Document
	 * @throws AtmException
	 */
//	public static Document xmlDocumentFromXmlString(String xml) throws AtmException
//	{
//		SAXBuilder builder = new SAXBuilder();
//		Document dom = null;
////		XMLOutputter outputter = new XMLOutputter();
////		java.io.StringWriter writer = new StringWriter();
//		try
//		{
//			dom = builder.build(xml);
//		}
//		catch(JDOMException | IOException ex)
//		{
//			logger.severe("Failed to dump XML output " + ex.getMessage());
//			throw new AtmException(ex);
//		}
//
//		return dom;
//	}

	/**
	 * Generates a string representation of the XML.
	 * Used by the communications protocol
	 * @param dom The XML Document to convert
	 * @return String representation of the document
	 */
//	public static String xmlDocumentToString(Document dom)
//	{
//		XMLOutputter outputter = new XMLOutputter();
//		java.io.StringWriter writer = new StringWriter();
//		try
//		{
//			outputter.output(dom, writer);
//		}
//		catch(IOException ex)
//		{
//			logger.severe("Failed to dump XML output " + ex.getMessage());
//		}
//
//		return writer.toString();
//	}

//	private static AtmObject getLoginResponseObject(Element root)
//			throws NumberFormatException
//	{
//		String loggedInString = root.getAttributeValue("logged-in");
//		String sessionIdString = root.getAttributeValue("session-id");
//		String userIdString = root.getAttributeValue("user-id");
//		boolean loggedIn = Boolean.parseBoolean(loggedInString);
//		int accountId = Integer.parseInt(sessionIdString);
//		int userId = Integer.parseInt(userIdString);
//		
//		return new LoginResponse(loggedIn, accountId, userId);
//	}
//	
//	private static AtmObject getLoginObject(Element root)
//			throws NumberFormatException
//	{
//		String pinString = root.getAttributeValue("pin");
//		String accountIdString = root.getAttributeValue("account-id");
//		int pin = Integer.parseInt(pinString);
//		int accountId = Integer.parseInt(accountIdString);
//		return new LoginRequest(pin, accountId);
//	}

//	private static Document generateLoginRequestXml(LoginRequest request)
//	{
//		Document dom = null;
//
//		// build the root element
//		Element root = new Element("login-request");
//		Attribute version = new Attribute("version", "1.0");
//		root.setAttribute(version);
//		String pinFmt = String.format("%d", request.getPin());
//		Attribute pin = new Attribute("pin", pinFmt);
//		root.setAttribute(pin);
//		String accountFmt = String.format("%d", request.getAccountId());
//		Attribute account = new Attribute("account-id", accountFmt);
//		root.setAttribute(account);
//
//		dom = new Document(root);
//
//		return dom;
//
//	}
	

	/**
	 * 
	 * @param response
	 * @return
	 */
//	private static Document generateLoginResponseXml(LoginResponse response)
//	{
//		Document dom = null;
//
//		// build the root element
//		Element root = new Element("login-response");
//		Attribute version = new Attribute("version", "1.0");
//		root.setAttribute(version);
//		String loggedInFmt = response.getLoggedIn().toString();
//		Attribute loggedIn = new Attribute("logged-in", loggedInFmt);
//		root.setAttribute(loggedIn);
//		String sessionFmt = String.format("%d", response.getSessionId());
//		Attribute account = new Attribute("account-id", sessionFmt);
//		root.setAttribute(account);
//		String userFmt = response.getLoggedIn().toString();
//		Attribute user = new Attribute("user-id", userFmt);
//		root.setAttribute(user);
//
//		dom = new Document(root);
//
//		return dom;
//
//	}
}
