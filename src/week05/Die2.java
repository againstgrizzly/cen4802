package week05;

import java.util.Random;
 
/**
 * This class takes a constructor paramter to determine if it should roll or not
 * 
 * @author scottl
 * 
 */
public class Die2
{
    /**
     * Constructor
     * 
     * @param roll
     *            true to roll die, otherwise initialize to NO_NUMBER;
     */
    public Die2(boolean roll)
    {
        random = new Random();

        if(roll)
        {
            roll();
        }
        else
        {
            number = NO_NUMBER;
        }
    }

    // Rolls the dice
    public void roll()
    {
        number = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
    }

    // Returns the number on this dice
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
