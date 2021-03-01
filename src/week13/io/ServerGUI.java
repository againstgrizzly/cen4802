package week13.io;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


//3/27/2015
//The purpose of this assignment is to create a client and server application that communicate using sockets, 
//input/output streams, Swing graphical user interface components, and dialogs. The application will transfer text 
//files over a network connection. The application will be a very simple version of a file transfer protocol (FTP). 

public class ServerGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private int m_port = 1001; 
	private ServerSocket m_serverConn;
	private ProtocolServer m_server;
	private String m_curPath = "";
	private File m_testDir;
	private String m_testDirPath = "";
	private MessageDelegate m_delegate;
	private String m_assignemtTestFilePath = "\\src\\week10\\";
	
	private File m_destDir; // destination folder to copy to

	public ServerGUI()
	{
		super("FTP Server");
		initialize();
	}

	/**
	 * Sets up the paths and components of the server GUI
	 * ServerGUI uses the ProtocolServer.
	 * MessageDelegate bridges the feedback to from the
	 * ProtocolServer to the GUI.
	 */
	private void initialize()
	{
		initGui(); 					// sets up the visual part
		initHandlers(); 			// hooks up the event handlers
		m_curState = STATE.READY;	// sets the initial GUI state
		try
		{
			// Get current location 
			Path currentRelativePath = Paths.get("");
			m_curPath = currentRelativePath.toAbsolutePath().toString();
			new File(m_curPath);
			
			// build up the test file list
			m_delegate = new MessageDelegate(this);
			m_testDirPath = m_curPath + m_assignemtTestFilePath + "testdir";
			m_testDir = new File(m_testDirPath);
			logMessage("Test Directory: " + m_testDir.getAbsolutePath());
			m_serverConn = new ServerSocket(m_port);
			m_server = new ProtocolServer(m_serverConn, m_testDir, m_delegate);
		}
		catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Main GUI entry point
	 * @param args
	 */
	public static void main(String[] args)
	{
		ServerGUI serverGui = new ServerGUI();
		serverGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serverGui.setVisible(true);
		serverGui.m_server.run();
	}
	
	// start waiting for a Connection from a Client when App runs
//	public void startRunning()
//	{
//		try
//		{
//			//server = new ServerSocket(21); // port number of server
//
//			while(true)
//			{
//				try
//				{
//					waitingForConnection(); // start up and connect
////					setUpStreams(); // connect stream between 2 computers
////					whileConnected(); // allows communication back and forth
//				}
//				catch(EOFException eofExc)
//				{
//					showMessage("\n Server ended the connection!"); // end
//																	// connection
//				}
//				finally
//				{
//					closeSocket(); // close connection
//				}
//			}
//		}
//		catch(IOException ioExc)
//		{
//			ioExc.printStackTrace();
//		}
//	}

	// method to Wait for a Connection when app starts
	// Set isCLosed to true before Connection and false once connection is made
	// (for Exit event handling)
	private void waitingForConnection() throws IOException
	{
		isClosed = true;
		m_curState = STATE.WAITING;
		
		showMessage(
				"\nServer waiting for connections.\nTransferred Files will be in C:\\ unless you change this destination");
		logMessage("Waiting");
		server.accept();
		
		// a connection has been established, create a session for it
//		ProtocolServerSession newSession = new ProtocolServerSession(connection, m_destFile);
//		showMessage("\nServer connected to " + connection.getInetAddress().getHostName());
//		logMessage("Connected");
//		
		// start the session in its own thread
//		new Thread(newSession).start();
		
		isClosed = false;
		m_curState = STATE.CONNECTED;
	}

	// set up the Streams to the Client used for receiving and sending data
//	private void setUpStreams() throws IOException
//	{
//		output = new ObjectOutputStream(connection.getOutputStream());
//		myServerOutput = new PrintWriter(output);
//		myServerOutput.flush();
//		input = new ObjectInputStream(connection.getInputStream());
//		ipStreamReader = new InputStreamReader(input);
//		myServerInput = new BufferedReader(ipStreamReader);
//	}

	// while there is a Connection start reading strings from Client and Write a
	// file
	// Or if the String states Disconnected then call the method again to wait
	// for a Connection
	private void whileConnected() throws IOException
	{
		try
		{
			messageIn = myServerInput.readLine(); // read message from Client

			if(messageIn.equals("Disconnected from server"))
			{ // Return if there is no Connection and Wait again
				return;
			}
			else
			{ // if not Disconnected message, set the File path to JFileChooser
				// Selection
				if(fc.getSelectedFile() != null)
				{
					filename = fc.getSelectedFile().getAbsolutePath() + "\\"
							+ messageIn;
				}
				else
				{
					filename = "C:\\" + messageIn; // or the Default Path if not
													// set by User
				}
			}

			file = Paths.get(filename); // Create a file Path String
			Charset charset = Charset.forName("UTF-8"); // read Characters in
														// File

			try (BufferedWriter writer = Files.newBufferedWriter(file, charset))
			{ // Create a File Writer

				while((messageIn = myServerInput.readLine()) != null)
				{ // read Strings from Client and Write to File
					if(messageIn.equals("Disconnected from server"))
					{
						return;
					}
					else if(messageIn.contains("Done sending"))
					{
						showMessage("\nFile transferred to: " + filename);
						writer.flush();
						writer.close();
						whileConnected();
					}
					else
					{
						writer.write(messageIn, 0, messageIn.length());
						writer.newLine();
					}
				}
			}
			catch(IOException x)
			{
				x.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Close connection method
//	private void closeSocket()
//	{
//		try
//		{
//			output.close(); // close stream to
//			input.close(); // close stream from
//			connection.close(); // close connection between 2 computers
//		}
//		catch(IOException ioEx)
//		{
//			ioEx.printStackTrace();
//		}
//	}

	/**
	 * Updates the display asynchronously
	 * 
	 * @param text Text to display
	 */
	private void showMessage(final String text)
	{
		// updates the gui everytime new text is added
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				m_display.append(String.format("%s\n", text));
			}
		});
	}

	/**
	 * Updates the log msg asynchronously
	 * 
	 * @param text Text to display
	 */
	void logMessage(final String text)
	{
		// updates the gui everytime new text is added
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				log.append(text);
				log.append("\n");
			}
		});
	}


	
	private void initGui()
	{
		Container cont = getContentPane();

		m_display = new JTextArea();
		log = new JTextArea();
		textGroup.setLayout(new GridLayout(0, 2, 50, 50));

		textGroup.add(new JScrollPane(m_display));
		textGroup.add(new JScrollPane(log));
		
		m_display.setFont(fontText);
		log.setFont(fontText);

		cont.add(textGroup, BorderLayout.CENTER);
		
		destination.setFont(fontButton);
		help.setFont(fontButton);
		exit.setFont(fontButton);
		btnGroup.setLayout(new GridLayout(0, 3, 50, 50));
		btnGroup.add(destination);
		btnGroup.add(help);
		btnGroup.add(exit);
		cont.add(btnGroup, BorderLayout.SOUTH);
		
		try
		{
			UIManager.setLookAndFeel(
			        UIManager.getCrossPlatformLookAndFeelClassName());
//			UIManager.setLookAndFeel(
//			        UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(InstantiationException e)
		{
			e.printStackTrace();
		}
		catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch(UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}		

		setSize(1200, 800);		
		this.setFont(fontText);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	private void initHandlers()
	{
		// the close window button
		addWindowListener(new WindowAdapter()
		{ // WindowEvent handler

			@Override
			public void windowClosing(WindowEvent e)
			{
				if(isClosed)
				{
					try
					{
						m_serverConn.close();
					}
					catch(IOException e1)
					{						
						e1.printStackTrace();
					}
					
					System.exit(-1);
				}
				else
				{
					showMessage(
							"\nA client is connected. Have the client disconnect first and then close this server.");
				}
			}
		});

		// select a file from JFileChooser
		destination.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				displayDestinationChooserDialog();
			}
		});

		// instructions for FTP
		help.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null,
						"1. Start the server before trying to transfer any files."
								+ "\n2. Set the destination location for transferred files."
								+ "\nThis is folder on the server where the transferred file"
								+ "\nshould be placed."
								+ "\n3. You can use the client to transfer files to this location.",
						"FTP Server Help", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// handle events for exiting if Connection is open/closed
		exit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(isClosed)
				{
					System.exit(-1);
				}
				else
				{
					showMessage(
							"\nA client is connected. Have the client disconnect first and then close this server.");
				}
			}
		});
	}
	
	private void displayDestinationChooserDialog()
	{
		showMessage("Test dir: " + m_testDir.getAbsolutePath());
		fc.setCurrentDirectory(m_testDir);
		fc.setSelectedFile(m_testDir);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setMultiSelectionEnabled(false);
		result = fc.showOpenDialog(null);

		if(result == JFileChooser.APPROVE_OPTION)
		{					
			m_destDir = fc.getSelectedFile(); 
			String msg = String.format("Will transfer files to %s", m_destDir.getAbsolutePath());
			showMessage(msg);
			//updateSelectedFiles(m_destFiles);
		}
		else if(result == JFileChooser.CANCEL_OPTION)
		{
			showMessage(
					"\nTransferred Files will be in C:\\ unless you change this destination");
		}		
	}
	
	enum STATE
	{
		READY,
		WAITING,
		CONNECTED
	}
	private JTextArea m_display;
	private JTextArea log;
	private JButton destination = new JButton("Destination");
	private JButton help = new JButton("Help");
	private JButton exit = new JButton("Exit");
	private JPanel btnGroup = new JPanel();
	private JPanel textGroup = new JPanel();
	
	private Font fontButton = new Font("Verdana", Font.BOLD, 20);
	private Font fontText = new Font("Verdana", Font.BOLD, 24);

	JFileChooser fc = new JFileChooser(); // used to selected file
	int result; // selection of file Approve/Cancel
	String filename; // holds the filename to create a file
	String messageIn = null; // holds strings from Client input
	boolean isClosed = false; // used to determine connection in event handling
	STATE m_curState;
	private ServerSocket server;
	OutputStream output;
	PrintWriter myServerOutput; // used for character based output
	InputStream input;
	InputStreamReader ipStreamReader;
	BufferedReader myServerInput; // used for character based input
	Path file; // used to create a file path
}
