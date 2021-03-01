package week12.app;

import week12.core.AtmObject;
import week12.util.LoginRequest;
import week12.util.LoginResponse;

/**
 * This class encapsulates the business logic of the application
 * It dispatches the AtmObjects to the appropriate handlers.
 * 
 * @author Scott LaChance
 *
 */
public class AtmApplication
{
	public static AtmApplication m_app;
	/** 
	 * Private constructor
	 */
	private AtmApplication()
	{
		
	}
	
	public static synchronized AtmApplication getInstance()
	{
		if( m_app == null )
		{
			m_app = new AtmApplication();
		}
		
		return m_app;
	}
	/**
	 * This is the application entry point for 
	 * AtmObject interactions such as Login
	 * @param obj An AtmObject instance to process
	 */
	public AtmObject processAtmObject(AtmObject obj)
	{
		AtmObject response = null;
		
		if(obj instanceof LoginRequest) // server handles this
		{
			LoginRequest request = (LoginRequest)obj;
			// verify the request
			response = new LoginResponse(request.getAccountId());
		}
		else if(obj instanceof LoginResponse) // client handles this
		{
			
		}			
		
		return response;
	}
}
