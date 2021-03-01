package week12.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import week12.util.AtmLogger;
import week12.AtmException;

/**
 * This class provides a basic framework for the protocol. 
 * 
 * It defines the methods subclasss must implement to support the run method
 * defined by the Runnable interface.
 * 
 * @author Scott LaChance
 *
 */
public abstract class AbstractProtocol implements Runnable
{
	protected final static Logger logger = Logger
			.getLogger(AtmLogger.ATM_LOGGER + "." + AbstractProtocol.class.getName());
	
	enum SESSION_STATE {CONNECTING, CONNECTED_SESSION, CLOSED_SESSION, INVALID }
	
	protected SESSION_STATE m_curState = SESSION_STATE.INVALID;
	
	/**
	 * Constructor
	 * 
	 * @param delegate
	 *            The delegate for reporting status
	 */
	protected AbstractProtocol(Socket conn, String name) throws AtmException
	{
//		m_connection = conn;
		m_name = name;
		setupConnection(conn);
//		
//		try
//		{
//			/* obtain an input stream to this client ... */
//			m_reader = new BufferedReader(new InputStreamReader(
//					m_connection.getInputStream()));
//
//			/* ... and an output stream to the same client */
//			m_writer = new PrintWriter(m_connection.getOutputStream(), true);
//		}
//		catch(IOException ex)
//		{
//			logger.severe(ex.getMessage());
//			System.err.println(ex);
//			throw new AtmException(ex);
//		}
	}

	/**
	 * Subclasses must implement this method
	 */
	protected abstract void runProtocol() throws ProtocolException, AtmException;

	protected void setupConnection(Socket conn) throws AtmException
	{
		m_connection = conn;
		
		try
		{
			/* obtain an input stream to this client ... */
			m_reader = new BufferedReader(new InputStreamReader(
					m_connection.getInputStream()));

			/* ... and an output stream to the same client */
			m_writer = new PrintWriter(m_connection.getOutputStream(), true);
		}
		catch(IOException ex)
		{
			logger.severe(ex.getMessage());
			System.err.println(ex);
			throw new AtmException(ex);
		}
	}
	
	/**
	 * Returns the current state of the protocol session
	 * 
	 * @return
	 */
	public String dumpState()
	{
		String msg = "No connection";
		if(m_connection != null)
		{
			String port = Integer.toString(m_connection.getPort());
			msg = String.format("Hostname: %s, ",m_connection.getInetAddress().getHostName());
			msg += String.format("Port: %s, Host Address: %s\n", port,
					m_connection.getInetAddress().getHostAddress());
		}

		return msg;
	}

	/**
	 * Base implementation to be used by subclasses
	 * Formats the name of the protocol instance and the thread ID with the message
	 * @param msg
	 */
	protected void trace(String msg)
	{
		String threadID = Long.toString(Thread.currentThread().getId());
		//DateFormat dtf = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss:SSS");
		//Date dt = new Date();
//		String fmt = String.format("%s %s :%s - %s", dtf.format(dt), m_name,
//				threadID, msg);

		String fmt = String.format("%s :%s - %s", m_name,
				threadID, msg);
//		if(m_delegate != null)
//		{
//			m_delegate.trace(fmt);
//		}
		
		logger.info(fmt);
	}

	/**
	 * Implements the Runnable interface method and delegates to the subclass
	 */
	@Override
	public void run()
	{
		try
		{
			trace("Starting thread");
			runProtocol();
		}
		catch(AtmException e)
		{
			trace(e.getMessage());
		}
		catch(Exception e)
		{
			trace(e.getMessage());
		}
	}
	
	protected void accept()
	{
		m_curState = SESSION_STATE.CONNECTED_SESSION;
	}
	
	protected void closeSession()
	{
		m_curState = SESSION_STATE.CLOSED_SESSION;
	}
	
	protected void finish()
	{
		m_curState = SESSION_STATE.INVALID;
	}
	
	protected void sendData(String data)
	{
		m_writer.println(data);
	}

//	public void close()
//	{
//		closeSocket();
//	}

//	// Close connection method
//	protected void closeSocket()
//	{
//		try
//		{
//			m_outputStream.close(); // close stream to
//			m_inputStream.close(); // close stream from
//			m_connection.close(); // close connection between 2 computers
//		}
//		catch(IOException ioEx)
//		{
//			ioEx.printStackTrace();
//		}
//	}

//	// set up the Streams to the Client used for receiving and sending data
//	protected void setUpStreams() throws IOException
//	{
//		if(m_connection != null)
//		{
//			m_outputStream = new ObjectOutputStream(
//					m_connection.getOutputStream());
//			m_outputWriter = new PrintWriter(m_outputStream);
//			m_outputWriter.flush();
//			m_inputStream = new ObjectInputStream(
//					m_connection.getInputStream());
//			m_inputReader = new InputStreamReader(m_inputStream);
//			m_reader = new BufferedReader(m_inputReader);
//			trace("completed setUpStreams()");
//		}
//	}

//	/**
//	 * Sends the start session message
//	 * 
//	 * @throws IOException
//	 */
//	protected void startSession() throws ProtocolException, IOException
//	{
//		trace("Starting session");
//		m_outputWriter.print(START_SESSION);
//		m_outputWriter.flush();
//		
//		trace("waiting for response");
//		String response = m_reader.readLine().trim();
//		trace("waiting for response");
//		if(!response.equals(OK))
//		{
//			String msg = String.format("Failed to start session Response: %s",
//					response);
//			trace(msg);
//			throw new ProtocolException(msg);
//		}
//		else
//		{
//			trace("Received OK for session start");
//		}
//	}

//	protected void closeSession() throws ProtocolException, IOException
//	{
//		trace("Closing session");
//		m_outputWriter.print(END_SESSION);
//
//		String response = m_reader.readLine().trim();
//
//		if(!response.equals(OK))
//		{
//			String msg = String.format("Failed to end session Response: %s",
//					response);
//			trace(msg);
//			throw new ProtocolException(msg);
//		}
//		else
//		{
//			trace("Received OK for session closed");
//		}
//	}
	
	protected void sendOK()
	{
		trace(" - Sending OK");
		m_writer.println(OK);
	}

	protected String m_name = "";

	protected Socket m_connection;
	protected ObjectOutputStream m_outputStream;
	//protected PrintWriter m_outputWriter;
	protected ObjectInputStream m_inputStream;
	protected InputStreamReader m_inputReader;
	//protected BufferedReader m_reader;
	
	/** Receives input from the client */
	protected BufferedReader m_reader;
	
	/** Sends data to the client */
	protected PrintWriter m_writer;	

	// Commands
	protected static String START_SESSION = "START_SESSION";
	protected static String START_XFR = "START_XFR";
	protected static String FILE_NAME = "FILE_NAME:";
	protected static String DATA = "DATA:";
	protected static String END_XFR = "END_XFR";
	protected static String END_SESSION = "END_SESSION";

	// Responses
	protected static String OK = "OK";
	//private MessageDelegate m_delegate;
}
