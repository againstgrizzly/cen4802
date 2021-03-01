package week02;

import static org.junit.Assert.*;

import org.junit.Test;
import test.AbstractJUnitBase;

public class TestCaseDieMany extends AbstractJUnitBase
{

	public TestCaseDieMany()
	{

	}

	@Test
	public void runTest()
	{
		boolean result = true;
		StringBuilder builder = new StringBuilder();
		
		ManySidedDie one = new ManySidedDie(8, false);
		ManySidedDie two = new ManySidedDie(12, true);
		ManySidedDie three = new ManySidedDie(20, false);

		try
		{

			do
			{
				one.roll();
				three.roll(); // explicit method call
				String info = String.format(" run %d: %d, %d, %d", m_runs,
						one.getNumber(), two.getNumber(), three.getNumber());
				trace(info);
				if(one.getNumber() == two.getNumber()
						&& two.getNumber() == three.getNumber())
				{
					result = false;
					builder.append("Expected different numbers:" + one.getNumber()
							+ ", " + two.getNumber() + ", " + three.getNumber() + "\n");
				}

				two.roll();
			}
			while(m_runs++ < 10);
			
			if( !result )
			{
				fail(builder.toString());
			}
		}
		catch(Exception ex)
		{
			fail("DieV2TestCase Exception" + ex.getMessage());
		}
	}
	
	private int m_runs = 0;
}
