package week06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class JUnitWeek06
{
	private static String allPairsInput = "week06-allpairs-input.txt";
	private static String allPictInput = "week06-pict-input.txt";
	private static String allPairsResults = "week06-allpairs-results.txt";
	private static String allPictResults = "week06-pict-results.txt";
	private static String allSummary = "week06-summary.txt";

	private static String packageString = "week06/";
	private static String srcString = "/src/";

	private static String allPairsInputPath = "";
	private static String allPictInputPath = "";
	private static String allPairsResultsPath = "";
	private static String allPictResultsPath = "";
	private static String allSummaryPath = "";

	private static File allPairsInputFile;
	private static File allPictInputFile;
	private static File allPairsResultsFile;
	private static File allPictResultsFile;
	private static File allSummarytFile;

	String[] expectedFileNames = { allPairsInput, allPictInput, allPairsResults,
			allPictResults, allSummary };

	@BeforeClass
	public static void setUpFiles()
	{
		File curDirectory = new File(".");

		try
		{
			trace(curDirectory.getCanonicalPath());
			File srcDir = getSrcDirectory(curDirectory);
			String srcPath = srcDir.getCanonicalPath();
			allPairsInputPath = srcPath + "/" + allPairsInput;
			allPictInputPath = srcPath + "/" + allPictInput;
			allPairsResultsPath = srcPath + "/" + allPairsResults;
			allPictResultsPath = srcPath + "/" + allPictResults;
			allSummaryPath = srcPath + "/" + allSummary;

			allPairsInputFile = new File(allPairsInputPath);
			allPictInputFile = new File(allPictInputPath);
			allPairsResultsFile = new File(allPairsResultsPath);
			allPictResultsFile = new File(allPictResultsPath);
			allSummarytFile = new File(allSummaryPath);
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void verifySummaryFile()
	{
		String combinationSequence = "combinations: 600";
		String allpairsSequence = "allpairs: 27";
		String pictSequence = "pict: 26";
		int expectedGroupCount = 7;
		if(allSummarytFile.exists())
		{
			FileReader fileReader = null;

			// Open the file and verify all the internal pieces are there
			try
			{
				fileReader = new FileReader(allSummarytFile);
				String fileContent = getContentFromFileReader(fileReader).trim();

				boolean combo = fileContent.contains(combinationSequence);
				boolean pairs = fileContent.contains(allpairsSequence);
				boolean pict = fileContent.contains(pictSequence);
				if( !(combo && pairs && pict) )
				{
					String msg = String.format(
							"Didn't get the expected summary results. Expected %s\n%s\n%s",combinationSequence,allpairsSequence,pictSequence);
							
					fail(msg);
				}
			}
			catch(IOException ex)
			{
				fail(ex.getMessage());
			}
			catch(IllegalStateException | IndexOutOfBoundsException ex)
			{
				fail(ex.getMessage());				
			}
			finally
			{
				if(fileReader != null)
					try
					{
						fileReader.close();
					}
					catch(IOException ex)
					{
					}
			}
		}
	}

	@Test
	public void verifyFilesSubmitted()
	{
		boolean fileExists = true;
		File curDirectory = new File(".");
		try
		{
			if(!allPairsInputFile.exists())
			{
				trace("Not found: " + allPairsInputFile.getCanonicalPath());
				fileExists = false;
			}

			// if(!allPictInputFile.exists())
			// {
			// 	trace("Not found: " + allPictInputFile.getCanonicalPath());
			// 	fileExists = false;
			// }
			if(!allPairsResultsFile.exists())
			{
				trace("Not found: " + allPairsResultsFile.getCanonicalPath());
				fileExists = false;
			}

			//if(!allPictResultsFile.exists())
			//{
			//	trace("Not found: " + allPictResultsFile.getCanonicalPath());
			//	fileExists = false;
			//}

			if(!allSummarytFile.exists())
			{
				trace("Not found: " + allSummarytFile.getCanonicalPath());
				fileExists = false;
			}

		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			fail(ex.getMessage());
		}

		if(!fileExists)
		{
			fail("File(s) not found");
		}
	}

	@Test
	public void testPictOutput()
	{
		String reference = "S5	W3	Yes	T5	P2";

		if(allPictResultsFile.exists())
		{
			FileReader fileReader = null;

			// Open the file and verify all the internal pieces are there
			try
			{
				fileReader = new FileReader(allPictResultsFile);
				String fileContent = getContentFromFileReader(fileReader);
				String[] lines = fileContent.split("\r\n");
				int count = lines.length;
				boolean expected = fileContent.contains(reference);
				boolean lineCount = count == 27;
				if(!expected)
				{
					String msg = String.format(
							"Didn't get the expected PICT results. Expected text: %s, line count %d",
							expected, lineCount);
					fail(msg);
				}
			}
			catch(IOException ex)
			{
				fail(ex.getMessage());
			}
			finally
			{
				if(fileReader != null)
					try
					{
						fileReader.close();
					}
					catch(IOException ex)
					{
					}
			}
		}
	}

	@Test
	public void testAllPairsOutput()
	{
		String reference = "27	S3	~W3	~Yes	T3	~P3	1";
		String shouldntSee = "28	";
		if(allPairsResultsFile.exists())
		{
			FileReader fileReader = null;

			// Open the file and verify all the internal pieces are there
			try
			{
				fileReader = new FileReader(allPairsResultsFile);
				String fileContent = getContentFromFileReader(fileReader);
				boolean expected = fileContent.contains(reference);
				boolean unexpected = fileContent.contains(shouldntSee);
				if(!expected || unexpected)
				{
					String msg = String.format(
							"Didn't get the expected AllPairs results. Expected text: %s, Unexpected: %s",
							expected, unexpected);
					fail(msg);
				}
			}
			catch(IOException ex)
			{
				fail(ex.getMessage());
			}
			finally
			{
				if(fileReader != null)
					try
					{
						fileReader.close();
					}
					catch(IOException ex)
					{
					}
			}
		}
	}

	@Test
	public void testPictInput()
	{
		if(allPictInputFile.exists())
		{
			FileReader fileReader = null;
			try
			{
				fileReader = new FileReader(allPictInputFile);
				verifyInputContent(fileReader);
			}
			catch(IOException ex)
			{
				fail(ex.getMessage());
			}
			finally
			{
				if(fileReader != null)
					try
					{
						fileReader.close();
					}
					catch(IOException ex)
					{
					}
			}
		}
	}

	@Test
	public void testAllPairsInput()
	{
		if(allPairsInputFile.exists())
		{
			FileReader fileReader = null;

			// Open the file and verify all the internal pieces are there
			try
			{
				fileReader = new FileReader(allPairsInputFile);
				verifyInputContent(fileReader);
			}
			catch(IOException ex)
			{
				fail(ex.getMessage());
			}
			finally
			{
				if(fileReader != null)
					try
					{
						fileReader.close();
					}
					catch(IOException ex)
					{
					}
			}
		}
	}

	private String getContentFromFileReader(FileReader fileReader)
			throws IOException
	{
		BufferedReader reader = new BufferedReader(fileReader);
		String line = "";
		StringBuilder builder = new StringBuilder();
		while((line = reader.readLine()) != null)
		{
			builder.append(line + "\n");
		}

		return builder.toString();
	}

	private boolean verifyInputContent(FileReader fileReader) throws IOException
	{
		boolean result = true;
		// BufferedReader reader = new BufferedReader(fileReader);
		// String line = "";
		// StringBuilder builder = new StringBuilder();
		// while((line = reader.readLine()) != null)
		// {
		// builder.append(line + "\n");
		// }
		String fileContent = getContentFromFileReader(fileReader);// builder.toString();
		// verifyInputContent(fileContent);

		boolean sensors = fileContent.contains("S1")
				&& fileContent.contains("S2") && fileContent.contains("S3")
				&& fileContent.contains("S4") && fileContent.contains("S5");
		boolean weapons = fileContent.contains("W1")
				&& fileContent.contains("W2") && fileContent.contains("W3")
				&& fileContent.contains("W4");
		boolean dataLink = fileContent.contains("Yes")
				&& fileContent.contains("No");
		boolean targets = fileContent.contains("T1")
				&& fileContent.contains("T2") && fileContent.contains("T3")
				&& fileContent.contains("T4") && fileContent.contains("T5");
		boolean platforms = fileContent.contains("P1")
				&& fileContent.contains("P2") && fileContent.contains("P3");

		if(!(sensors && weapons && dataLink && targets && platforms))
		{
			String msg = "";
			msg = sensors ? "" : "Missing sensor value\n";
			msg = weapons ? "" : "Missing weapons value\n";
			msg = dataLink ? "" : "Missing dataLink value\n";
			msg = targets ? "" : "Missing targets value\n";
			msg = platforms ? "" : "Missing platforms value\n";
			fail(msg);
		}

		return result;
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
	private static File getSrcDirectory(File curDir)
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

			trace("Normalized source directory: " + result.getCanonicalPath());
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private static void trace(String msg)
	{
		System.out.println(msg);
	}

}
