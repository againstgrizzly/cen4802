package week02;

import java.util.Random;

/**
 * This class simulates a dice
 * It utilizes the Random class to generate a random number
 * This version will automatically roll a die when it is created.
 * 
 * @author Scott LaChance
 *
 */
class DieVer2
{
	/**
	 * Default Constructor
	 */
	public DieVer2()
	{
		random = new Random();
		roll();
	}

    /**
     * Rolls the die randomly and stores the result.
     */
	public void roll()
	{
		number = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
	}
	
    /**
     * Returns the number on this dice
     * @return The rolled number
     */
	public int getNumber()
	{
		return number;
	}

	// Data Members

	// the largest number on a dice
	private static final int MAX_NUMBER = 6;

	// the smallest number on a dice
	private static final int MIN_NUMBER = 1;

	private int number;

	private Random random;

}