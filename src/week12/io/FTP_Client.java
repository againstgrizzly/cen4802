package week12.io;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Launches the protocol client session instance.
 * Need to launch the server session first for a successful client session launch
 * @author Scott
 *
 */
public class FTP_Client
{
	public static void main(String[] args)
	{
		String[] m_testFiles = { "testOne.txt", "testTwo.txt" };
		String assignemtTestFilePath = "\\..\\src\\week10\\";
		Path currentRelativePath = Paths.get("");
		String curPath = currentRelativePath.toAbsolutePath().toString();
		System.out.println(curPath);
		String testPath = curPath + assignemtTestFilePath;
		try
		{
			List<File> files = new ArrayList<File>();
			for(String s : m_testFiles)
			{
				File testFile = new File(testPath + s);
				files.add(testFile);
				System.out.println("Setting up test file " + testFile.getAbsolutePath());
			}
			
			ProtocolClientSession client = new ProtocolClientSession(files,
					new MessageDelegate());
			client.run();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
