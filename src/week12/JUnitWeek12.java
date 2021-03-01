package week12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import test.AbstractJUnitBase;

public class JUnitWeek12 extends AbstractJUnitBase
{
	private static String txtPattern = "txt";
	private static int maxDepth = 1;

	private static String packageString = "week10\\";
	private static String srcString = "\\src\\";
	private static String binString = "\\bin\\";

	private static String installerExePath = "";
	private static String installerNsiPath = "";
	private static String installDir = "";
	private static String binDir = "";
	private static String helloWorldClassFilePath = "";

	private static File reportTextFile;
	private static File sourceDirectory;
	private static File binDirectory;
	

	//String[] expectedFileNames = { installerExeInput, installerNsiInput };

	private static String makeNsisExe = "C:/Program Files (x86)/NSIS/makensis.exe";

	public JUnitWeek12()
	{
		File curDirectory = new File(".");

		try
		{
			//trace(curDirectory.getCanonicalPath());
			sourceDirectory = getSrcDirectory(curDirectory);
			binDirectory = getBinDirectory(curDirectory);
			String srcPath = sourceDirectory.getCanonicalPath();

			installDir = sourceDirectory.getCanonicalPath() +"\\install";
			
			reportTextFile = findTextFile(sourceDirectory, txtPattern);
			
			dumpConfiguration();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
		
	/**
	 * This needs to coordinate the sequence Verify the files, compile, verify
	 * installer, run installer
	 */
	@Test
	public void checkTextFile()
	{
		trace(" -- checkTextFile");
		cleanup();
		
		if(!verifyTextFileExists()) // checks the installer was created
		{
			fail("verifyTextFileExists: text file not found");
		}

		if(!verifyTextFileContents())
		{
			fail("verifyTextFileContents failed");
		}
	}
	
	/**
	 * remove any previous install folders and content
	 */
	private void cleanup()
	{
		
	}

	private boolean verifyTextFileContents()
	{
		boolean result = false;
		
		trace("  -- verifyTextFileContents");
		
		if(!reportTextFile.exists())
		{
			trace(" TextFile folder doesn't exist: " + installDir);
		}
		else
		{
			BufferedReader reader = null;
			
			try
			{
				FileReader fis = new FileReader(reportTextFile);
				reader = new BufferedReader(fis);
				StringBuilder buffer = new StringBuilder();
				String line = "";
				
				while((line = reader.readLine()) != null)
				{
					buffer.append(line);
					buffer.append('\n');
				}
				
				String text = buffer.toString();
				
				result = validateContent(text);
				
			}
			catch(IOException ex)
			{
				
			}
			finally
			{
				try{reader.close();}catch(Exception ex){}
			}
		}
		
		return result;
	}
	
	private boolean validateContent(String text)
	{
		String normalizedText = text.toLowerCase();
		boolean systemV = false;
		boolean architecture = false;
		boolean swIntegration = false;
		boolean cm = false;
		boolean regression = false;
		int score = 30;
		
		systemV = normalizedText.contains("system v");
		architecture = normalizedText.contains("architecture");
		swIntegration = normalizedText.contains("software integration");
		cm = normalizedText.contains("configuration management");
		regression = normalizedText.contains("regression test");
		
		if(!systemV)
		{
			trace("   * missing system v content");
			score -= 6;
		}
		
		if(!architecture)
		{
			trace("   * missing architecture content");
			score -= 6;
		}
		
		if(!swIntegration)
		{
			trace("   * missing software integration content");
			score -= 6;
		}
		
		if(!cm)
		{
			trace("   * missing configuration management content content");
			score -= 6;
		}
		
		if(!regression)
		{
			trace("   * missing regression testing content");
			score -= 6;
		}
		
		trace("content score: " + score);
		return systemV && swIntegration && cm && regression;
	}

	/**
	 * Verifies the installer was created during compilation
	 * @return true if it exists, otherwise false
	 */
	private boolean verifyTextFileExists()
	{
		trace("  -- verifyTextFileExists");
		boolean textFilePresentd = true;

		if(!reportTextFile.exists())
		{
			textFilePresentd = false;
		}

		return textFilePresentd;
	}


	/**
	 * when running in the IDE the parent directory is different than when
	 * running from the commandline for evaluation. This method detects that and
	 * applies the following hueristic
	 * 
	 * If the parent is bin, then find the sibling src. If the parent is not bin
	 * (IDE), then add src and the package as a parent
	 * 
	 * @return
	 */
	private File getSrcDirectory(File curDir)
	{
		trace("getSrcDirectory");
		File result = curDir;
		try
		{
			String path = curDir.getCanonicalPath();
			trace("  " + path);
			String srcDir = "";
			if(path.endsWith("bin")) // command line run
			{
				// get the parent of this folder
				// and create the src folder path
				int endIndex = path.length() - "bin".length();
				String parentPath = path.substring(0, endIndex);
				trace("bin parentPath: " + parentPath);

				srcDir = parentPath + srcString;
			}
			else // IDE run (has the package in the path
			{
				srcDir = path + srcString + packageString;
			}

			result = new File(srcDir);
			installDir = result.getCanonicalPath();
			trace("Normalized source directory: " + installDir);
		}
		catch(IOException e)
		{
			trace(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	private File getBinDirectory(File curDir)
	{
		trace("getBinDirectory");
		File result = curDir;
		try
		{
			String path = curDir.getCanonicalPath();
			trace("  " + path);
			if(!path.endsWith("bin")) 
			{
				binDir = path + binString;
				result = new File(binDir);
			}

			binDir = result.getCanonicalPath();
			trace("Normalized bin directory: " + binDir);			
		}
		catch(IOException e)
		{
			trace(e.getMessage());
			e.printStackTrace();
		}		
		
		return result;
	}

	private void dumpConfiguration()
	{
		trace("String configuration values");

		try
		{
			trace("File configuration values");
			trace("text txt File: " + reportTextFile.getCanonicalPath());		
			trace("installer dir File: " + sourceDirectory.getCanonicalPath());
		}
		catch(IOException e)
		{
			trace("Error getting File configuration values");
			e.printStackTrace();
		}
	}
	
	private String getCommandLineResponse(Process pr)
	{
		BufferedReader input = null;
		StringBuilder cmdLineInfo = new StringBuilder();

		// dump the command line response
		input = new BufferedReader(
				new InputStreamReader(pr.getInputStream()));

		String line = null;
		try
		{
			while((line = input.readLine()) != null)
			{
				System.out.println(line);
				cmdLineInfo.append(line);
				cmdLineInfo.append('\n');
			}
			
			int exitVal = pr.waitFor();
			trace("Exited with error code " + exitVal);
		}
		catch(IOException e)
		{
			trace(e.getMessage());
			e.printStackTrace();
		}
		catch(InterruptedException e)
		{
			trace(e.getMessage());
			e.printStackTrace();
		}

		return cmdLineInfo.toString();
	}

	private File findTextFile(File srcDir, String pattern)
	{
		File txtFile = null;
		File[] list = srcDir.listFiles(new TextFilter());
		
		if(list.length > 0)
		{
			txtFile = list[0];
		}

		return txtFile;
	}
	
	class TextFilter implements FilenameFilter
	{

		@Override
		public boolean accept(File arg0, String arg1)
		{
			boolean result = false;
			if( arg1.endsWith(txtPattern))
			{
				result = true;
			}
			
			return result;
		}
		
	}
}
