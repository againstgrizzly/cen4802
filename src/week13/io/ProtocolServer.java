package week13.io;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import week13.AtmException;

/**
 * Implements the server listener
 * When clients request a connection it creates a 
 * ProtocolServerSession instance and sets up
 * to listen for another connection.
 * 
 * @author Scott LaChance
 *
 */
public class ProtocolServer implements Runnable
{
	private boolean m_threadTerminated = false;
	/**
	 * Constructor for
	 * 
	 * @param conn
	 */
	public ProtocolServer(ServerSocket conn, File destDirectory)
			throws IOException
	{
		this(conn, destDirectory, null);
	}

	public ProtocolServer(ServerSocket conn, File destDirectory,
			MessageDelegate delegate) throws IOException
	{
		m_server = conn;
		m_destDirectory = destDirectory;
		m_delegate = delegate;
		m_activeSessions = new ArrayList<Thread>();
	}

	
//	public String listSession()
//	{
//		String msg = "";
//		for(Thread session : m_activeSessions)
//		{
//			
//			msg = String.format("%s\n",  session.getName());
//		}
//		
//		return msg;
//	}
	
	/**
	 * Closes the server listener
	 */
	public void close()
	{
		try
		{
			trace(" - closing server");
			synchronized(this)
			{
				m_threadTerminated = true;
				m_server.close();
			}			
		}
		catch(IOException e)
		{
			m_delegate.trace(e.getMessage());
		}
	}
	
//	public void killSessions()
//	{
//		for(Thread session : m_activeSessions)
//		{
//			
//		}
//	}
	
	/**
	 * Required override for implementing the Runnable interface
	 */
	@Override
	public void run()
	{
		waitingForConnection();
	}
	
	/**
	 * Server waits until a client connects to it.
	 * Then we create a separate thread for that connection and go back to wait.
	 * 
	 * @throws IOExeption
	 */
	private void waitingForConnection()
	{		
		boolean ioExceptionTerminated = false;
		while(true)
		{
			Socket connection;
			try
			{
				String inet = m_server.getInetAddress().toString();
				String info =
						String.format(" - waiting for next connection on port: %s, "
								,inet);
				trace(info);
					
				connection = m_server.accept();
				trace(" - accepted for connection request");
				
				// dispatch to a processing thread
				ProtocolServerSession session = 
						new ProtocolServerSession(connection, m_destDirectory, m_delegate);
				Thread newThread = new Thread(session);
				m_activeSessions.add(newThread);
				//newThread.setName(session.getName());
				newThread.start();
				String msg = String.format(
						" - New Session: thread id %d, Name: %s", newThread.getId(), newThread.getName());
				trace(msg);
			}
			catch(AtmException ex)
			{
				trace(" - AtmException has terminated server thread");
				ioExceptionTerminated = true;
			}
			catch(IOException ex)
			{
				trace(" - IOException has terminated server thread");
				ioExceptionTerminated = true;
			}
			
			if( m_threadTerminated || ioExceptionTerminated)
			{
				String who = ioExceptionTerminated ? "System " : "User";
				trace(
					String.format("- %s has terminated server thread", who));
				break;
			}
		}
	}

	/**
	 * Helper method to trace behavior.
	 * Uses a timestamp in milliseconds to allow for identifying the 
	 * message sequence and the thread id in order to distinguish 
	 * the messages.
	 * @param msg
	 */
	private void trace(String msg)
	{
		String threadID = Long.toString(Thread.currentThread().getId());
		DateFormat dtf = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss:SSS");
		Date dt = new Date();
		String fmt = String.format("%s Host Server: %s - %s", dtf.format(dt), threadID, msg);
		
		if( m_delegate != null )
		{
			m_delegate.trace(fmt);
		}
		else
		{
			System.out.println(fmt);
		}
	}

	/** The socket over which session communications occur */
	private ServerSocket m_server;
	private File m_destDirectory;
	private MessageDelegate m_delegate;
	private List<Thread> m_activeSessions;


}
