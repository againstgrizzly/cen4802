package week05;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

public class Week05JUnitTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testMultipleDie()
	{
		
		List<Die> dieList = Arrays.asList(new Die(true),
				new Die(true),
				new Die(true),
				new Die(true),
				new Die(true),
				new Die(true),
				new Die(true),
				new Die(true),
				new Die(true));
		
		int val = dieList.get(0).getNumber();
		
		boolean found_diff = false;
		for (int i = 1; i < dieList.size(); i++)
		{
			if (dieList.get(i).getNumber() != val)
			{
				found_diff = true;
			}
		}
		
		Assert.assertTrue(found_diff);
	}
	
	@Test
	public void testRandomDie()
	{
		// Run 1000 die and get their values
		List<Integer> dieValues = new ArrayList<>();
		for (int i = 0; i < 1000; i++)
		{
			Die die = new Die(true);
			
			// Check that die has non zero value
			assertNotEquals(0, die.getNumber());
			dieValues.add(new Die(true).getNumber());
		}
		
		// Make sure each possible value was recorded and get its count
		int count_1 = Collections.frequency(dieValues, 1);
		int count_2 = Collections.frequency(dieValues, 2);
		int count_3 = Collections.frequency(dieValues, 3);
		int count_4 = Collections.frequency(dieValues, 4);
		int count_5 = Collections.frequency(dieValues, 5);
		int count_6 = Collections.frequency(dieValues, 6);
		
		// Fail if count is zero for any of them
		if (count_1 == 0 || count_2 == 0 ||
		    count_3 == 0 || count_4 == 0 ||
		    count_5 == 0 || count_6 == 0)
		{
			fail();
		}
		
		
	}

}
