package week13.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * This is the client for a chat application. It provides an example for
 * exchanging data between two end points The Socket class is the TCP
 * implementation. This implementation supports multiple client connections,
 * similar to how a full ATM system might need to be implemented to allow
 * multiple ATMs to communicate with the bank server.
 * 
 * @author scottl
 * 
 */
public class TcpClient
{
	/**
	 * Entry point for the class. This example puts a lot of code in the main.
	 * Suggest refactoring if you use this as a starting point and NOT having it
	 * in main.
	 * 
	 * @param args
	 *            command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		new TcpClient().run();
	}
	
	public void run() throws IOException
	{
		initializeClient();
		startSession();
	}

	public void sendMessage(BufferedReader in, PrintWriter out)
			throws IOException
	{
		String msgToSend = "";
		String serverResponse = "";
		String msgResponse = "";
		while(true)
		{
			System.out.print("Enter your message: ");
			msgToSend = m_stdIn.readLine();
			if( msgToSend.equals(""))
			{
				break; // all done
			}
			out.println("MSG " + msgToSend);
			msgResponse = in.readLine(); // response to our message
			if( msgResponse.contains("SERVER"))
			{
				// unexpected server response
				log("Unexpecteed Server response: " + serverResponse);
			}
			else
			{
				serverResponse = in.readLine(); // server status response
				log("Message response: " + msgResponse);
				log("Server response: " + serverResponse);
				if(!"SERVER: OK".equals(serverResponse))
				{
					log("Error processing message: " + serverResponse);				
				}
			}
		}
	}

	/**
	 * Read in a nickname from stdin and attempt to authenticate with the server
	 * by sending a NICK command to @out. If the response from @in is not equal
	 * to "OK" go back and read a nickname again
	 * 
	 * @param in
	 *            BufferedReader reference
	 * @param out
	 *            The output stream.
	 */
	private String getNick(BufferedReader in, PrintWriter out)
			throws IOException
	{
		log("getNick");
		boolean run = true;

		System.out.print("Enter your nick: ");
		String msg = m_stdIn.readLine();

		if(msg.equals(""))
		{
			run = false;
		}

		out.println("NICK " + msg);
		String serverResponse = in.readLine();
		if("SERVER: OK".equals(serverResponse))
		{
			return msg;
		}

		System.out.println(serverResponse);
		if(run)
		{
			return getNick(in, out);
		}
		else
		{
			sendMessage(in, out);
		}
		
		return "";
	}

	private void initializeClient()
	{
		try
		{
			log("initializeClient");
			m_server = new Socket(m_host, m_port);

			m_stdIn = new BufferedReader(new InputStreamReader(System.in));

			/* obtain an output stream to the server... */
			m_writer = new PrintWriter(m_server.getOutputStream(), true);

			/* ... and an input stream */
			m_reader = new BufferedReader(new InputStreamReader(
					m_server.getInputStream()));

			//m_nick = getNick(m_reader, m_writer);
		}
		catch(IOException ex)
		{
			System.err.println(ex);
			System.exit(1);
		}
	}

	private void startSession() throws IOException
	{
		log("enter startSession");
		//m_nick = 
		getNick(m_reader, m_writer);

		sendMessage(m_reader, m_writer);
	
		log("exit startSession");
	}
	
	private void log(String msg)
	{
		System.out.println(msg);
	}
	

	// Private data
	private static int m_port = 1001; /* port to connect to */
	private static String m_host = "localhost"; /* host to connect to */

	private static BufferedReader m_stdIn;

	//private static String m_nick;
	private static Socket m_server = null;

	private static BufferedReader m_reader;
	private static PrintWriter m_writer;
}

