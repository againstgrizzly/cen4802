package week08;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PermutationString
{
	public static List<String> getPerumutaionts(String word)
	{
		trace("test driven development:falsetest-driven development:falsetdd:"); //test driven development:falsetest-driven development:falsetdd:
		trace("combinatorial");
		return permutate(new ArrayList<String>(), "", word, 0);
	}

	private static List<String> permutate(ArrayList<String> list, String perm, String word, int level)
	{
		trace(getPad(level) + "Level in: " + level);
		trace(getPad(level) + "Input word: " + word);
		trace(getPad(level) + "Input perm: " + perm);
		
		if (word.isEmpty())
		{
			trace(getPad(level) + "adding: " + perm);
			list.add(perm + word);
		}

		for (int i = 0; i < word.length(); i++)
		{
			permutate(list, perm + word.charAt(i), word.substring(0, i) + word.substring(i + 1, word.length()),
					level + 1);
		}
        
		trace(getPad(level) + "Level out: " + level);
		return list;
	}

	private static void trace(String msg)
	{
		String path = System.getProperty("user.dir") + "/src/week08/log.txt";
		
		try
		{
			FileWriter fileWriter;
			fileWriter = new FileWriter(path, true);
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    printWriter.println(msg);  //New line
		    printWriter.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Set true for append mode

	}

	private static String getPad(int level)
	{
		String pad = "";
		for (int i = 0; i < level; i++)
		{
			pad += "-";
		}

		return pad;
	}
}
