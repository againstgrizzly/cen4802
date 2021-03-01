package week08;

import test.AbstractJUnitBase;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import org.junit.Test;

public class Week08JUnitTest extends AbstractJUnitBase
{
	String[] m_extensions = { "txt", "docx", "pdf" };
	FilenameFilter m_filter;
	LookupItem[] m_checks = { 
			new LookupItem(new String[]{"test driven development","test-driven development","tdd"}, false),
			new LookupItem(new String[]{"combinatorial"}, false) };
	boolean m_result = true;
	boolean m_isTxt = false;

	public Week08JUnitTest()
	{
		super();
		m_filter = new TextFileFilter(m_extensions);

	}

	/**
	 * Tests the Die class exhaustively Needs to
	 */
	@Test
	public void testSubmissionRequirements()
	{
		trace("- testSubmissionRequirements --");
		try
		{
			String srcPath = initFolderPath("week08");
			trace(" -- srcPath: " + srcPath);
			File fileText = getTestFile(srcPath);
			evaluateFile(fileText);
			processResults();
			// trace(" -- dummping ");
			dump();
			String result = "content: " + m_result + "; submission: " + m_isTxt;
			trace(result);
			assertTrue("Didn't find all required content", m_result && m_isTxt);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	private void processResults()
	{
		trace("-- processResults --");
		for(LookupItem item : m_checks)
		{
			if(!item.found)
			{
				m_result = false;
			}
		}

	}

	private void evaluateFile(File fileText) throws IOException
	{
		trace("-- evaluateFile: " + fileText.getAbsolutePath());
		FileReader reader = new FileReader(fileText);
		BufferedReader buffer = new BufferedReader(reader);

		String line = "";
		int lineNumber = 0;
		while((line = buffer.readLine()) != null)
		{
			lineNumber++;
			processLine(line);
		}

		buffer.close();
	}

	private void processLine(String line)
	{
		// trace("-- processLine: ");
		// trace("-- " + line);
		String evalLine = line.toLowerCase();

		// find the lookup item and update it
		for(LookupItem item : m_checks)
		{
			// trace(" --- item: " + item.toString());
			for(String key : item.keys)
			{
				if(evalLine.contains(key.toLowerCase()))
				{
					item.found = true;
					break;
				}
			}
		}

		// trace("-- end processLine: ");
	}

	private File getTestFile(String srcPath) throws IOException
	{
		File file = null;
		File curPath = new File(srcPath);
		File[] files = curPath.listFiles(m_filter);

		for(File f : files)
		{
			trace(" -- processing file: " + f.getName());
			file = f;
			break;
		}

		return file;
	}

	private void dump()
	{
		trace(" -- dump --");
		for(LookupItem item : m_checks)
		{
			trace(item.toString());
		}
	}

	class TextFileFilter implements FilenameFilter
	{
		public TextFileFilter(String[] extensions)
		{
			m_extensions = extensions;
		}

		@Override
		public boolean accept(File dir, String name)
		{
			boolean result = false;

			for(String ext : m_extensions)
			{

				if(name.endsWith(ext))
				{
					trace(" -- found: " + name);
					if(ext.equals("txt"))
					{
						m_isTxt = true;
					}
					
					result = true;
				}

			}

			return result;
		}

		String[] m_extensions;

	}

	class LookupItem
	{
		String[] keys;
		boolean found = false;
		String[] defaultList = { "" };

		LookupItem()
		{
			//this(defaultList, false);
		}

		LookupItem(String[] keys, boolean found)
		{
			this.keys = keys;
			this.found = found;
		}

		@Override
		public String toString()
		{
			StringBuilder builder = new StringBuilder();
			builder.append("[");
			for(String key : keys)
			{
				builder.append(key + ":" + found);
			}
			builder.append("]");
			return builder.toString();
		}
	}
}
