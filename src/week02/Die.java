package week02;

import java.util.Random;

/**
 * This class simulates a dice
 * It utilizes the Random class to generate a random number
 * 
 * @author Scott LaChance
 *
 */
public class Die
{
    /**
     * Default constructor
     */
    public Die()
    {
        random = new Random();
        number = NO_NUMBER;
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

    // to represent a dice that is not yet rolled
    private static final int NO_NUMBER = 0;

    private int number;

    private Random random;
}