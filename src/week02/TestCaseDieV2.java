package week02;

import static org.junit.Assert.*;

import org.junit.Test;
import test.AbstractJUnitBase;

public class TestCaseDieV2 extends AbstractJUnitBase
{
	public TestCaseDieV2()
	{
	}
	
	@Test
	public void runTest()
	{
		DieVer2 one;
		DieVer2 two;
		DieVer2 three;
		DieVer2 four;

        one = new DieVer2();
        two = new DieVer2();
        three = new DieVer2();
        four = new DieVer2();
      
        if(one.getNumber() == two.getNumber() && two.getNumber() == three.getNumber() && three.getNumber() == four.getNumber())
        {
        	fail("Expected different numbers:" + one.getNumber() + ", " + two.getNumber() + ", " + three.getNumber()+ ", " + four.getNumber());
        }
	}
}
