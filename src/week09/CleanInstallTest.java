package week09;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
//import java.io.IOException;

public class CleanInstallTest
{
	private static CleanInstallTest app;

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		app = new CleanInstallTest();
		app.run(args);
	}

	private CleanInstallTest()
	{
		m_installFolderFilter = new InstallFolderFilter();
	}

	private void run(String[] args)
	{
		try
		{
			processCommandline(args);
			cleanup();

		}
		catch(Exception ex)
		{
			trace(ex.getMessage());
		}
	}

	/**
	 * -root identifies the path to the evaluation folder
	 * 
	 * @param args
	 */
	private void processCommandline(String[] args)
	{
		int len = args.length;
		for(int i = 0; i < len; i++)
		{
			if(args[i].toLowerCase().startsWith("-root"))
			{
				m_evalFolder = args[i + 1].trim();
				i++;
				trace("Setting eval folder: " + m_evalFolder);
			}
		}
	}

	/**
	 * remove any previous install folders and content
	 */
	private void cleanup()
	{
		File evalFolder = new File(m_evalFolder);
		// try
		// {
		if(evalFolder.exists())
		{
			// get student folders
			File[] studenFolders = evalFolder.listFiles();
			try
			{
				for(File studentFolder : studenFolders)
				{
					// get the src Folder
					File srcFolder;

					trace("Cleanup up student folder: " + studentFolder.getName());
					srcFolder = new File(
							studentFolder.getCanonicalFile() + "\\src");

					File[] foldersToDelete = srcFolder
							.listFiles(m_installFolderFilter);

					for(File deleteFolder : foldersToDelete)
					{
						File[] filesToDelete = deleteFolder.listFiles();
						for(File deleteFile : filesToDelete)
						{
							trace("deleting file: " + deleteFile.getName());
							deleteFile.delete();
						}
						trace("deleting folder: " + deleteFolder.getName());
						deleteFolder.delete();
					}
				}
			}
			catch(IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void trace(String msg)
	{
		System.out.println(msg);
	}

	private String m_evalFolder = "C:\\projects\\school\\ucf\\CEN4802\\2015-fall\\cen4802\\assignment_eval\\week09";
	private FileFilter m_installFolderFilter;

	private class InstallFolderFilter implements FileFilter
	{

		@Override
		public boolean accept(File pathname)
		{
			boolean result = false;
			if(pathname.isDirectory() && pathname.getName().equals("install"))
			{
				result = true;
			}
			return result;
		}

	}
}
