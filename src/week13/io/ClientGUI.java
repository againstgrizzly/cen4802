package week13.io;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

//3/27/2015
//The purpose of this assignment is to create a client and server application that communicate using sockets, 
//input/output streams, Swing graphical user interface components, and dialogs. The application will transfer text 
//files over a network connection. The application will be a very simple version of a file transfer protocol (FTP). 

public class ClientGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JTextArea display; // A text area to display activity and event
								// messages for the client.
	private JButton serverConnect = new JButton("Server Connect"); // JButtons
	private JButton selectFile = new JButton("Select File"); //
	private JButton transfer = new JButton("Transfer File"); //
	private JButton serverDisconnect = new JButton("Server Disconnect");//
	private JButton help = new JButton("Help"); //
	private JButton exit = new JButton("Exit"); //
	private JPanel btnGroup = new JPanel(); // add buttons to JPanel

	JFileChooser fc = new JFileChooser(); // File Chooser
	FileNameExtensionFilter filter; // filter Text files
	BufferedReader br; // read a File
	File file; // set selected file to File
	private String serverIP; // holds IP address from constructor
	private String message, filename;
	int result; // holds result on JFileChooser approve or cancel

	Socket connection;
	ObjectOutputStream output;
	PrintWriter myClientOutput; // used for character based output
	ObjectInputStream input;
	InputStreamReader ipStreamReader;

	public ClientGUI(String host)
	{
		super("FTP Client"); // app title bar
		serverIP = host; // server IP address passed in to constructor
		Container cont = getContentPane();
		display = new JTextArea();
		cont.add(display, BorderLayout.CENTER);
		cont.add(new JScrollPane(display), BorderLayout.CENTER);

		btnGroup.setLayout(new GridLayout(0, 6, 30, 30));
		btnGroup.add(serverConnect);
		btnGroup.add(selectFile);
		btnGroup.add(transfer);
		btnGroup.add(serverDisconnect);
		btnGroup.add(help);
		btnGroup.add(exit);
		cont.add(btnGroup, BorderLayout.SOUTH);

		// WindowClosing handler
		// if connection is closed exit OK, if not display a message to
		// disconnect from server first.
		// if client clicks exit when app starts without any initial connection
		// NullPointer is thrown, exit app
		addWindowListener(new WindowAdapter()
		{

			@Override
			public void windowClosing(WindowEvent arg0)
			{

				try
				{
					if(connection.isClosed())
					{
						System.exit(0);
					}
					else
					{
						showMessage(
								"\nPlease close down server before exiting");
					}
				}
				catch(NullPointerException ex)
				{
					System.exit(-1);
				}
			}

		});

		// connect to server
		serverConnect.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				startRunning(); // connect to server method
			}

		});

		selectFile.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				filter = new FileNameExtensionFilter( // filter only Text files
														// with these extensions
						"Text", "txt", "htm", "html", "xml");
				fc.setFileFilter(filter);
				fc.setMultiSelectionEnabled(false); // cannot select multiple
													// files at once
				result = fc.showOpenDialog(null); // get int returned from
													// either adding file or
													// canceling JFileChooser

				// test result if file is selected add the file name to the
				// display
				// or if JFileChooser is cancelled show message
				if(result == JFileChooser.APPROVE_OPTION)
				{

					file = fc.getSelectedFile();
					filename = file.getAbsolutePath();
					showMessage("\nFile to transfer is " + filename);

				}
				else if(result == JFileChooser.CANCEL_OPTION)
				{
					showMessage(
							"\nSave command cancelled by user - no file selected");
				}
			}

		});

		transfer.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					if(connection.isClosed())
					{
						showMessage(
								"\nNo connection to server. Connect to server before trying to transfer a file");
					}
					else
					{
						try
						{
							if(filename.equals(""))
							{ // if there is no File selected ShowMessage in
								// Exception thrown

							}
							else
							{
								try
								{
									BufferedReader br = new BufferedReader(
											new FileReader(filename)); // create
																		// a
																		// BufferedReader
																		// to
																		// read
																		// file
									String str;
									showMessage("\nFile being transferred is: "
											+ filename);
									myClientOutput.println(
											fc.getSelectedFile().getName()); // send
																				// the
																				// name
																				// of
																				// file
																				// to
																				// Server
									myClientOutput.flush();

									while((str = br.readLine()) != null)
									{ // read each String in file

										myClientOutput.println(str); // send
																		// each
																		// String
																		// in
																		// file
																		// to
																		// Server
										myClientOutput.flush();

									}
									br.close(); // close BufferedReader
									myClientOutput.println("Done sending"); // tell
																			// Server
																			// Client
																			// is
																			// done
																			// Sending
																			// a
																			// file
									myClientOutput.flush();

									showMessage(
											"\nDone sending file " + filename);

								}
								catch(FileNotFoundException e1)
								{

									e1.printStackTrace();

								}
								catch(IOException ioEx)
								{

									ioEx.printStackTrace();
								}
							}
						}
						catch(NullPointerException ex)
						{
							showMessage(
									"\nNo file selected to transfer - select a file first");
						}
					}
				}
				catch(NullPointerException ne)
				{
					showMessage(
							"\nNo connection to server. Connect to server before trying to transfer a file");
				}
			}
		});

		// Disconnect from Server
		serverDisconnect.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				closeSocket(); // disconnect from server method

			}
		});

		// display FTP Client help steps with JOptionPane
		help.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, m_helpText,
						"FTP Client Help", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// exit the app if there is no connection to a Server
		exit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// if connection is closed exit OK, if not display a message to
				// disconnect from server first.
				// if client clicks exit when app starts without any initial
				// connection NullPointer is thrown, exit app
				try
				{
					if(connection.isClosed())
					{
						System.exit(0);
					}
					else
					{
						showMessage(
								"\nPlease close down server before exiting");
					}
				}
				catch(NullPointerException ex)
				{
					System.exit(-1);
				}

			}
		});
		
		// this is overridden by the close window handler.
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(1000, 400);
		setVisible(true);

	}

	// connect to server
	public void startRunning()
	{

		try
		{
			connection = new Socket(InetAddress.getByName(serverIP), 21); // need
																			// IP
																			// and
																			// port
																			// #
			showMessage("\nConnected to server"); // add message to display that
													// client is connected
			message = "Connected to server";
			output = new ObjectOutputStream(connection.getOutputStream()); // set
																			// up
																			// streams
																			// to
																			// send
																			// info
			output.flush(); // flush out any trailing bytes
			input = new ObjectInputStream(connection.getInputStream()); // set
																		// up
																		// streams
																		// to
																		// receive
																		// info
			myClientOutput = new PrintWriter(output);

		}
		catch(Exception e)
		{
			showMessage("\nUnable to connect to server - did you start it?"); // if
																				// no
																				// server
																				// is
																				// available
																				// show
																				// message
		}

	}

	// close the streams and sockets
	private void closeSocket()
	{
		try
		{
			if(!connection.isClosed())
			{ // if connection is open, show message and close streams
				try
				{

					message = "Disconnected from server"; // message to be sent
															// to server

					myClientOutput.println(message); // send message to server
														// that client is
														// disconnected
					myClientOutput.flush(); // flush bytes through

					output.close(); // close output stream
					input.close(); // close input stream
					connection.close(); // close connection socket
					myClientOutput.close(); // close Printwriter
					showMessage("\nDisconnected from server"); // show message
																// that client
																// is
																// disconnected

				}
				catch(Exception e)
				{
					showMessage("\nConnection not open - no need to close it");
				}
			}
			else
			{
				showMessage("\nConnection not open - no need to close it"); // if
																			// client
																			// tries
																			// to
																			// disconnect
																			// without
																			// being
																			// connect
																			// show
																			// message
			}
		}
		catch(NullPointerException npEx)
		{
			showMessage("\nConnection not open - no need to close it");
		}
	}

	// change/update JTextArea
	private void showMessage(final String m)
	{

		SwingUtilities.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{

				display.append(m); // add each String "message" to display area

			}

		});
	}

	private static String m_helpText = "1. Start the server before trying to transfer any files."
			+ "\n(Set the destination directory on the server after starting it.)"
			+ "\n2. Connect to the server with the Server Connect Button."
			+ "\n3. Select the file you want to transfer with the Select File button."
			+ "\n4. Transfer the file with the Transfer File button."
			+ "\n5. Repeat until done (Select another file and transfer it)"
			+ "\n6. Close the connection to the server with the Server Disconnect button."
			+ "\n7. Exit the client application with the Exit button.";
}
