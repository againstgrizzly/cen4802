package week12.util;

import week12.core.AtmObject;

/**
 * Encapsulates the login request information
 * @author scottl
 *
 */
public class LoginResponse extends AtmObject
{
	/**
	 * Default constructor
	 * 
	 * @param userId the user id
	 */
	public LoginResponse(long userId)
	{
		this(false, -1, userId);
	}
	
	/**
	 * Constructor
	 * @param loggedIn true if logged in successfully, false otherwise
	 * @param sessionId - SessionId token)
	 */
	public LoginResponse(boolean loggedIn, long sessionId, long userId)
	{
		m_loggedIn = loggedIn;
		m_sessionId = sessionId;
		m_userId = userId;
	}

	/**
	 * @return the m_pin
	 */
	public Boolean getLoggedIn()
	{
		return m_loggedIn;
	}
	
	/**
	 * @return the m_accountId
	 */
	public long getSessionId()
	{
		return m_sessionId;
	}
	
	/**
	 * @return the m_userId
	 */
	public long getUserId()
	{
		return m_userId;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		if( obj instanceof LoginResponse)
		{
			LoginResponse rhs = (LoginResponse)obj;
			
			if( this.getLoggedIn() == rhs.getLoggedIn() &&
				this.getSessionId() == rhs.getSessionId() &&
				this.getUserId() == rhs.getUserId())
			{
				result = true;
			}
		}
		
		return result;
	}

	private Boolean m_loggedIn;
	private long m_sessionId; 
	private long m_userId;
}
