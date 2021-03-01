package week01;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestHelloWorld
{
	/**
	 * Default constructor
	 */
	public TestHelloWorld()
	{
	}

	@Test
	public void runTest()
	{
		boolean result = true;
		HelloWorld hello = new HelloWorld();
		
		String msg = hello.display();
		
//		trace("Results from executing display() method: \n");
//		trace("\n\n");
		
        if(msg.length() > 1 && msg.contains("Hello World from"))
        {
        	result = true;        	
        }
        else
        {
        	result = false;
        	//trace("Message doesn't match requirements - expected Hello World from <your name>");
        }    
        
        assertTrue("Message doesn't match requirements - expected Hello World from <your name>", result);


	}

	static private void trace(String msg)
	{
		System.out.println(msg);
	}
}
