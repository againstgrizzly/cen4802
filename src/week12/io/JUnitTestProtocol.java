package week12.io;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.State;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import junit.framework.TestCase;
import week12.AtmException;

public class JUnitTestProtocol extends TestCase
{
	private int m_port = 1001;
	private String m_curPath = "";
	private String assignemtTestFilePath = "\\src\\week10\\";

	@Override
	protected void setUp() throws Exception
	{
		m_delegate = new MessageDelegate();
		m_serverConn = new ServerSocket(m_port);

		Path currentRelativePath = Paths.get("");
		m_curPath = currentRelativePath.toAbsolutePath().toString();
		trace("Relative directory path: " + m_curPath);
	};
//	@Before
//	public void preTestSetup()
//	{
//		try
//		{
//			String separator = File.pathSeparator;
//			m_delegate = new MessageDelegate();
//			m_serverConn = new ServerSocket(m_port);
//
//			Path currentRelativePath = Paths.get("");
//			m_curPath = currentRelativePath.toAbsolutePath().toString();
//			trace("Relative directory path: " + m_curPath);
//		}
//		catch(Exception ex)
//		{
//			trace("Error setting up tests: " + ex.getMessage());
//		}
//	}

	/**
	 * Tests the Client and Server implementation of the file transfer protocol
	 */
	@Test
	public void testProtocol()
	{
		boolean bSuccess = true;
		long TIMEOUT = 30000;

		try
		{
			// build up the test file list
			m_testDirPath = m_curPath + assignemtTestFilePath + "testdir";
			m_testDir = new File(m_testDirPath);
			trace("Test directory path: " + m_testDirPath);
			String testPath = m_curPath + assignemtTestFilePath;
			List<File> files = new ArrayList<File>();
			for(String s : m_testFiles)
			{
				File testFile = new File(testPath + s);
				files.add(testFile);
			}

			synchronized (this)
			{
				// Launch the server protocol class in a thread
				m_server = new ProtocolServer(m_serverConn, m_testDir,
						m_delegate);
				// trace(m_server.listSession());
				Thread serverThread = new Thread(m_server);
				serverThread.start();

				// Launch the client protocol class in a thread
				m_clientSession = new ProtocolClientSession(files, m_delegate);
				trace(m_clientSession.dumpState());
				Thread clientThread = new Thread(m_clientSession);
				clientThread.start();

				boolean fServerDone = false;
				boolean fClientDone = false;
				boolean fDone = fServerDone & fClientDone;

				// timeout tracking
				long elapsedTime = 0;
				long maxTime = TIMEOUT;
				long startTime = System.currentTimeMillis();
				long endTime = startTime;

				while(!fDone)
				{
					elapsedTime = endTime - startTime;
					if(elapsedTime > maxTime)
					{
						trace("timeout reached");
						bSuccess = false;
						break;
					}

					State serverState = serverThread.getState();
					State clientState = clientThread.getState();

					// poll every second
					this.wait(1000);

					switch(serverState)
					{
						case TERMINATED:
							fServerDone = true;
							break;
						case BLOCKED:
							break;
						case NEW:
							break;
						case RUNNABLE:
							break;
						case TIMED_WAITING:
							break;
						case WAITING:
							break;
					}

					switch(clientState)
					{
						case TERMINATED:
							fClientDone = true;
							break;
						case BLOCKED:
							break;
						case NEW:
							break;
						case RUNNABLE:
							break;
						case TIMED_WAITING:
							break;
						case WAITING:
							break;
					}

					// update status
					fDone = fServerDone & fClientDone;
					endTime = System.currentTimeMillis();
				}
			}
		}
		catch(AtmException ex)
		{
			ex.printStackTrace();
			fail(ex.getMessage());
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			fail(ex.getMessage());
		}
		catch(InterruptedException ex)
		{
			ex.printStackTrace();
			fail(ex.getMessage());
		}
		finally
		{
			m_server.close();
		}

		assertTrue(bSuccess);
	}

	private void trace(String msg)
	{
		System.out.println(msg);
	}

	// two test files
	private static String[] m_testFiles = { "testOne.txt", "testTwo.txt" };
	private MessageDelegate m_delegate;
	private ProtocolServer m_server;
	private ProtocolClientSession m_clientSession;
	private ServerSocket m_serverConn;
	private File m_testDir;
	private String m_testDirPath = "";
}
