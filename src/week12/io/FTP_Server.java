package week12.io;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Commandline entry point for testing the Server
 * Launch this before the FTP Client
 *  
 * @author Scott
 *
 */
public class FTP_Server
{
	public static void main(String[] args)
	{
		int port = 1001; 
		
		// the working folder turns out to be the bin folder
		// so account for it in the path
		String assignemtTestFilePath = "\\..\\src\\week10\\";
		Path currentRelativePath = Paths.get("");
		String curPath = currentRelativePath.toAbsolutePath().toString();
		String testDirPath = curPath + assignemtTestFilePath + "testdir";
		File testDir = new File(testDirPath);

		try
		{
			ServerSocket serverConn = new ServerSocket(port);
			ProtocolServer server = new ProtocolServer(serverConn, testDir, new MessageDelegate());
			server.run();
		}
		catch(IOException e)
		{
			System.out.println("Error launching TCP server");
			e.printStackTrace();
		}
	}
}
