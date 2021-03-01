package week12.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

//import org.jdom2.Document;

import week12.AtmException;
import week12.core.AtmObject;
import week12.xml.XmlUtility;

/**
 * Manages the client session for the transfer protocol Using the provided
 * inputs, it connects to the server, creates a session and transfers over the
 * files
 * 
 * @author Scott
 *
 */
public class ProtocolClientSession extends AbstractProtocol
{
//	/**
//	 * List of files to copy to server
//	 * 
//	 * @param files
//	 */
//	public ProtocolClientSession() throws AtmException
//	{
//		this(null, null); // defer the connection to when the
//										// runProtocol is called
//	}
	
	/**
	 * Parameterized constructor
	 * 
	 * @param socket TCP socket to use
	 * @param delegate Used for callback status
	 * @throws AtmException 
	 */
	public ProtocolClientSession(Socket socket, MessageDelegate delegate) throws AtmException
	{
		super(socket, "Client Session");
		// eat the delegate for this assignment
	}
	
	/**
	 * Parameterized constructor
	 * 
	 * @param socket TCP socket to use
	 * @param delegate Used for callback status
	 * @throws AtmException 
	 */
	public ProtocolClientSession(List<File> files, MessageDelegate delegate) throws AtmException
	{
		super(null, "Client Session");
		// eat the delegate for this assignment
	}

	/**
	 * Implements the base class abstract method Sets up the connection with the
	 * server and transfers the files
	 */
	@Override
	public void runProtocol() throws ProtocolException, AtmException
	{
		trace(" - Starting client");

		// need to connect and set the AbstractProtocol connection attribute
		// m_connection = new Socket(m_host, m_port);
		try
		{
			setupConnection(new Socket(m_host, m_port));
//			if(establishSession())
//			{
//				
//			}
		}
		catch(IOException ex)
		{
			throw new AtmException(ex);
		}

		// /* obtain an output stream to the server... */
		// m_writer = new PrintWriter(m_connection.getOutputStream(), true);
		//
		// /* ... and an input stream */
		// m_reader = new BufferedReader(
		// new InputStreamReader(m_connection.getInputStream()));

		// trace(" - connecting, setting up streams");

//		if(establishSession())
//		{
//			// transferFiles();
//
//			// trace("Ending session");
//			// m_writer.println(END_SESSION);
//			// m_writer.flush();
//			// String response = m_reader.readLine();
//			// response = response == null ? "No final response from server" :
//			// response;
//			// trace("Server response " + response);
//		}
	}

	/**
	 * Transfers the list of files. SESSION has been established and this will
	 * create individual FILE sessions.
	 * 
	 * @throws ProtocolException
	 * @throws IOException
	 */
	private void transferData(AtmObject obj)
			throws ProtocolException, AtmException
	{
		trace("transferring data");
		String info = String.format("transfering %d files", m_files.size());
		trace(info);
//		for(File f : m_files)
//		{
			trace("sending " + START_XFR);
			sendData(START_XFR);
			if(getOkResponse())
			{
				// convert the AtmObject to an XML string
//				Document dom = XmlUtility.objectToXml(obj);
//				String xml = XmlUtility.xmlDocumentToString(dom);
//				sendData(xml);
				if(getOkResponse()) // wait for the ACK
				{
					trace("Sent over data successfully");
//					String line = "";
//					while((line = fileReader.readLine()) != null)
//					{
//						trace("Sending over data");
//						String dataLIne = DATA + line;
//
//						// send the data to the server
//						m_writer.println(dataLIne);
//						m_writer.flush();
//					}

					sendData(END_XFR);
					if(getOkResponse())
					{
						trace("file successfully sent");
					}
				}
				else
				{
					//throw new ProtocolException("Client message failed: " + xml);
					throw new ProtocolException("Client message failed: ");
				}
			}
			else
			{
				trace("Didn't get an OK from the server");
			}
//		}
	}

	/**
	 * Waits for a response from the server.
	 * 
	 * @return true on an OK response, otherwise false
	 * @throws ProtocolException
	 */
	private boolean getOkResponse() throws ProtocolException
	{
		boolean isOk = false;
		try
		{
			String response = m_reader.readLine();
			isOk = OK.equals(response.trim());
		}
		catch(IOException ex)
		{
			throw new ProtocolException(
					"Error getting a response from the server", ex);
		}
		return isOk;
	}

	/**
	 * Sends the START_SESSION to the server and waits for a response
	 * 
	 * @return true if server accepts the session request
	 * @throws ProtocolException
	 * @throws IOException
	 */
	private boolean establishSession() throws ProtocolException
	{
		boolean sessionSetup = false;

		try
		{
			trace("Starting session");
			m_writer.println(START_SESSION);
			m_writer.flush();
			trace("Sent " + START_SESSION);
			String response = m_reader.readLine();
			trace("Server response " + response);
			sessionSetup = OK.equals(response.trim());
			return sessionSetup;
		}
		catch(IOException ex)
		{
			throw new ProtocolException("Error establishing session", ex);
		}
	}

	/** List of files to copy */
	private List<File> m_files;

	/** The reader to receive messages from the server */
	private BufferedReader m_reader;

	/** The writer to send messages to the server */
	private PrintWriter m_writer;

	// Configuration settings for the server
	private static String m_host = "localhost";
	private static int m_port = 1001;
}
