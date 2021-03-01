package week04;

import java.util.Random;

/**
 * This is a guided programming assignment
 * It will use enumerations, arrays, selection and repetition.
 * 
 * @author scottl
 *
 */
public class LasVegasGame
{	
	public enum GUESS
	{
		BETWEEN,
		NOT_BETWEEN
	}
	/**
	 * Constructor
	 */
	public LasVegasGame()
	{
		m_random = new Random();
	}
	
	/**
	 * Starts a new session by resetting the internal state.
	 */
	public void startSession()
	{
		m_lowInt = 0;  // low number
	    m_hiInt = 0;   // high number
	    m_nextInt = 0; // next random number
	    m_winners = 0; // number of wins
	    m_losers = 0;  // number of losses
	}
	
	/**
	 * Starts a new guessing session
	 * @return An array of two ints. int[0] = lo, int[1]=hi
	 */
	public int[] startGame()
	{
		generateRandomInts();
		int[] range = new int[]{m_lowInt, m_hiInt};
		return range;
	}
	
	/**
	 * This method uses the &&, !, || and enumerations to determine 
	 * whether the guess is a winner or not.
	 * @param guess
	 * @return
	 */
	public boolean guess(GUESS guess)
	{
		m_nextInt = m_random.nextInt(MAX_RANGE);
		if(((m_lowInt < m_nextInt && m_nextInt < m_hiInt) && guess == GUESS.BETWEEN)
                || (!(m_lowInt < m_nextInt && m_nextInt < m_hiInt) && guess == GUESS.NOT_BETWEEN))
        {
            m_winners++;
            m_gameWon = true;
        }
        else
        {
            m_losers++;
            m_gameWon = false;
        }
		
        return m_gameWon; 
	}

    /**
     * Returns the summary of wins and losses
     * 
     * @return Formatted string of wins and losses
     */
    public String endGame()
    {
        return String.format(END_GAME, m_winners, m_losers);
    }
    
    /**
     * Returns the number of winners counted by the game
     * @return
     */
    public int getWinnerCount()
    {
    	return m_winners;
    }
    
    /** retruns the number of losses counted by the game. */
    public int getLosersCount()
    {
    	return m_losers;
    }
	
    /**
     * Generates a set of two random integers used to define the range.
     */
	 private void generateRandomInts()
	    {
	        int numA = m_random.nextInt(MAX_RANGE);
	        int numB = m_random.nextInt(MAX_RANGE);
	        if(numA > numB)
	        {
	            m_lowInt = numB;
	            m_hiInt = numA;
	        }
	        else
	        {
	            m_lowInt = numA;
	            m_hiInt = numB;
	        }
	    }
	
	private int m_lowInt = 0;  // low number
    private int m_hiInt = 0;   // high number
    private int m_nextInt = 0; // next random number
    private int m_winners = 0; // number of wins
    private int m_losers = 0;  // number of losses
    private boolean m_gameWon; // Indicates if
    private Random m_random;
    private static final int MAX_RANGE = 100; // max range 
    private static final String END_GAME = "Winners: %d, Losers: %d";
}
