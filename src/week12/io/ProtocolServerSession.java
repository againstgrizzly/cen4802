package week12.io;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

//import org.jdom2.Document;

import week12.AtmException;
import week12.app.AtmApplication;
import week12.core.AtmObject;
import week12.xml.XmlUtility;

/**
 * Implements the server protocol for an accepted connection from a client
 * 
 * @author Scott
 *
 */
public class ProtocolServerSession extends AbstractProtocol
{
	/**
	 * Constructor to initialize this protocol session.
	 * 
	 * @param conn
	 *            Socket created by accept
	 * @param delegate The callback class for status
	 */
	public ProtocolServerSession(Socket conn, File destinationFolder, MessageDelegate delegate) throws AtmException
	{
		super(conn, "Server Session");

		trace("Server protocol session initialized");
	}

	/**
	 * Implements the base class abstract method
	 */
	@Override
	protected void runProtocol() throws AtmException
	{
		trace("runProtocol()");
		whileConnected();
	}

	/**
	 * Processes the file transfer protocol Only supports text file transfers
	 * Only supports UTF-8 (8-bit) characters.
	 * 
	 * Protocol Commands: 
	 * 
	 * data transfer 
	 * START_TRANSFER - starts a data transfer (XML text exchange)
	 * END_TRANSFER - end of data transfer (no more XML text exchange) 
	 * CLOSE_SESSION - initiates closing the current session
	 * 
	 * @throws IOException
	 */
	private void whileConnected() throws AtmException
	{
		trace("Starting whileConnected()");
		try
		{
			trace("Waiting for message");
			// listen for the client to START the session
			String messageIn = m_reader.readLine();
			trace("message read: " + messageIn);
			if(messageIn.trim().equals(START_SESSION))
			{
				trace(" - Starting server session");
				m_writer.println(OK);

				// Return if there is no Connection and Wait again
				processSession();
			}
			else if(messageIn.trim().equals(END_SESSION))
			{
				// session over
				trace(" - Ending session");
				return;
			}
			else
			{
				trace(" - Invalid message to start session, terminating.");
			}
		}
		catch(IOException ex)
		{
			trace("Error: " + ex.getMessage());
			throw new AtmException(ex);
		}
		catch(Exception ex)
		{
			trace("Error: " + ex.getMessage());
			throw new AtmException(ex);
		}
		finally
		{
			// Finished on server
//			m_reader.close();
//			m_writer.close();
			//client.close();
		}
	}

	/**
	 * Implements the protocol for transferring files.
	 * 
	 */
	private void processSession()
		throws ProtocolException, AtmException
	{
		boolean fDone = false;
		try
		{
			while(!fDone)
			{
				// listen for client to send data
				String dataIn = m_reader.readLine();
				trace("handling client command " + dataIn);
				if(dataIn.trim().equals(START_XFR))
				{
					trace("starting a file transfer request");
					sendOK();
					//m_writer.println(OK); // send the ack
					
					// get the file name
					dataIn = m_reader.readLine();
					
					// create the AtmObject
//					Document inDom = XmlUtility.xmlDocumentFromXmlString(dataIn.trim());
//					AtmObject obj = XmlUtility.xmlToObject(inDom);
//					
//					AtmObject response = AtmApplication.getInstance().processAtmObject(obj);
//					
//					Document xmlResponse = XmlUtility.objectToXml(response);
//					String xmlResponseString = XmlUtility.xmlDocumentToString(xmlResponse);
//					sendData(xmlResponseString);
					// process the object
//					if(dataIn.trim().startsWith(FILE_NAME))
//					{
//						// get the file name
//						String data = dataIn.trim();
//						//String fileName = data.trim();	
////						String path = m_destDirectory.getAbsolutePath()
////								+ "\\" + fileName;
////						trace("Preparing to copy file: " + fileName + 
////								" to " + path);
//						
////						destFile = new File(path);
////						FileWriter writer = new FileWriter(destFile);
////						destWriter = new BufferedWriter(writer);
////						
//						trace("created destination file");
//						m_writer.println(OK); // send the ack
//					}
				}
//				else if(messageIn.trim().startsWith(DATA))
//				{
//					trace("writing data to destination file");
//					// don't trim now. We want whitespace if it's there.
//					String data = messageIn.substring(DATA.length());
//					destWriter.write(data);
//					destWriter.newLine();
//				}
//				else if(messageIn.trim().equals(END_FILE))
//				{
//					destWriter.flush();
//					destWriter.close();
//					m_writer.println(OK); // send the ack
//				}
//				else if(messageIn.trim().equals(END_SESSION))
//				{
//					trace(" - Ending session");
//					m_reader.close();
//					m_writer.println(OK); // send the ack
//					break;
//				}
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			trace("Error copying file" + ex.getMessage());
			throw new ProtocolException("Error copying file", ex);			
		}
	}
	
	/** The client socket reference */
	//public Socket client;
}
