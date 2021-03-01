package week02;

import static org.junit.Assert.*;

import org.junit.Test;
import test.AbstractJUnitBase;

public class TestCaseDie extends AbstractJUnitBase
{
	/**
	 * Default constructor
	 */
	public TestCaseDie()
	{		
	}

	@Test
	public void runTest()
	{
		Die one;
		Die two;
		Die three;

        one = new Die();
        two = new Die();
        three = new Die();

        one.roll();
        two.roll();
        three.roll();

        if(one.getNumber() == two.getNumber() && two.getNumber() == three.getNumber())
        {
        	fail("Expected different numbers");
        }
        
        trace("Results are " + one.getNumber() + "  "
                + two.getNumber() + "  " + three.getNumber());
	}
}
