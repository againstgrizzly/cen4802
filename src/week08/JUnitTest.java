package week08;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import test.AbstractJUnitBase;

public class JUnitTest extends AbstractJUnitBase
{
	private String input1 = "ab";
	private String input2 = "xyz";
	private String input3 = "apple";
	private String input4 = "abc";
	private List<String> expected1 = new ArrayList<String>(){{
		add("ab");
		add("ba");
		}};
	private List<String> expected2 = new ArrayList<String>(){{
		add("xyz");
		add("xzy");
		add("yxz");
		add("yzx");
		add("zxy");
		add("zyx");
		}};
	private List<String> expected3 = new ArrayList<String>(){{
		add("apple");
		add("appel");
		add("aplpe");
		add("aplep");
		add("apepl");
		add("apelp");
		add("apple");
		add("appel");
		add("aplpe");
		add("aplep");
		add("apepl");
		add("apelp");
		add("alppe");
		add("alpep");
		add("alppe");
		add("alpep");
		add("alepp");
		add("alepp");
		add("aeppl");
		add("aeplp");
		add("aeppl");
		add("aeplp");
		add("aelpp");
		add("aelpp");
		add("paple");
		add("papel");
		add("palpe");
		add("palep");
		add("paepl");
		add("paelp");
		add("ppale");
		add("ppael");
		add("pplae");
		add("pplea");
		add("ppeal");
		add("ppela");
		add("plape");
		add("plaep");
		add("plpae");
		add("plpea");
		add("pleap");
		add("plepa");
		add("peapl");
		add("pealp");
		add("pepal");
		add("pepla");
		add("pelap");
		add("pelpa");
		add("paple");
		add("papel");
		add("palpe");
		add("palep");
		add("paepl");
		add("paelp");
		add("ppale");
		add("ppael");
		add("pplae");
		add("pplea");
		add("ppeal");
		add("ppela");
		add("plape");
		add("plaep");
		add("plpae");
		add("plpea");
		add("pleap");
		add("plepa");
		add("peapl");
		add("pealp");
		add("pepal");
		add("pepla");
		add("pelap");
		add("pelpa");
		add("lappe");
		add("lapep");
		add("lappe");
		add("lapep");
		add("laepp");
		add("laepp");
		add("lpape");
		add("lpaep");
		add("lppae");
		add("lppea");
		add("lpeap");
		add("lpepa");
		add("lpape");
		add("lpaep");
		add("lppae");
		add("lppea");
		add("lpeap");
		add("lpepa");
		add("leapp");
		add("leapp");
		add("lepap");
		add("leppa");
		add("lepap");
		add("leppa");
		add("eappl");
		add("eaplp");
		add("eappl");
		add("eaplp");
		add("ealpp");
		add("ealpp");
		add("epapl");
		add("epalp");
		add("eppal");
		add("eppla");
		add("eplap");
		add("eplpa");
		add("epapl");
		add("epalp");
		add("eppal");
		add("eppla");
		add("eplap");
		add("eplpa");
		add("elapp");
		add("elapp");
		add("elpap");
		add("elppa");
		add("elpap");
		add("elppa");
		}};
	private List<String> expected4 = new ArrayList<String>(){{
		add("abc");
		add("acb");
		add("bac");
		add("bca");
		add("cab");
		add("cba");
		}};

	/**
	 * Constructor
	 */
	public JUnitTest()
	{
		m_stream = System.out; // Standard out
	}

	@Test
	public void testPermutationsAB()
	{
		trace("########### testPermutationsAB ###########");
		
		List<String> actuals = PermutationString.getPerumutaionts(input1);
		
		evaluate(expected1,actuals);
	}
	
//	@Test
	public void testPermutationsXYZ()
	{
		trace("########### testPermutationsXYZ ###########");
		
		List<String> actuals = PermutationString.getPerumutaionts(input2);
		
		evaluate(expected2,actuals);
	}
	
//	@Test
	public void testPermutationsApple()
	{
		trace("########### testPermutationsApple ###########");
		
		List<String> actuals = PermutationString.getPerumutaionts(input3);
		
		evaluate(expected3,actuals);
	}
	
	@Test
	public void testPermutationsAbc()
	{
		trace("########### testPermutationsAbc ###########");
		
		List<String> actuals = PermutationString.getPerumutaionts(input4);
		
		evaluate(expected4,actuals);
	}
	
	private void evaluate(List<String> expected, List<String> actuals)
	{
		for(String actual : actuals)
		{
			trace("Output: " + actual);
		}
		
		assertEquals(String.format("expected size: %d, actual size %d", expected.size(), actuals.size()),expected.size(), actuals.size());
		boolean result = true;
		StringBuilder buffer = new StringBuilder();
		
		for(int i = 0; i < expected1.size();i++)
		{
			if(!expected.get(i).equals(actuals.get(i)))
			{
				result = false;
				buffer.append(String.format("mismatched results: expected %s, actual: %s", expected.get(i),actuals.get(i)));
				buffer.append(System.getProperty("line.separator"));
			}
		}
		
		assertTrue(buffer.toString(), result);
	}
}
