package week13.io;

/**
 * This is used as a callback mechanism. Used by the JUnit test and will be used
 * by the GUI
 * 
 * @author Scott LaChance
 *
 */
public class MessageDelegate
{
	/**
	 * Default constructor
	 */
	public MessageDelegate()
	{
		this(null);
	}
	
	/**
	 * Custom constructor
	 * @param gui
	 */
	public MessageDelegate(ServerGUI gui)
	{
		m_logTo = gui;
	}

	/**
	 * Thread safe callback
	 * 
	 * @param msg
	 */
	public void trace(String msg)
	{
		synchronized (this)
		{
			if(m_logTo == null)
			{
				System.out.println(msg);
			}
			else
			{
				m_logTo.logMessage(msg);
			}
		}
	}

	private ServerGUI m_logTo;
}
