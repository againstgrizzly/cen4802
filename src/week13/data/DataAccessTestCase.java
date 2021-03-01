package week13.data;

import static org.junit.Assert.*;
import org.junit.Test;
import test.AbstractJUnitBase;

import java.sql.Connection;
import java.util.List;

import week12.app.Account;
import week12.data.AtmDataException;
import week12.data.DataAccess;
import week12.app.User;

/**
 * Implements methods to test the DataAccess class
 * 
 * @author Scott
 *
 */
public class DataAccessTestCase extends AbstractJUnitBase
{
	/**
	 * Test case for data access
	 */
	public DataAccessTestCase()
	{
	}

//	@Override
//	protected boolean runTest()
//	{
//		boolean result1 = testOpenDataAccess();
//		boolean result2 = testAddRemoveUser();
//		boolean result3 = testUpdateUser();
//		boolean result4 = testOpenDataAccessWithParameters();
//		boolean result5 = testAccounts();
//
//		return result1 && result2 && result3 && result4 && result5;
//	}

	@Test
	public void testAccounts()
	{
		boolean result = true;
		DataAccess da = null;
		try
		{
			da = getDataAccess();
			da.connect();
			Connection conn = da.getConnection();

			trace("Connected to database: " + conn.toString());
			
			trace(" Test account user - create user first...");
			List<Account> beforeAccountList = da.getAccounts();
			User user = new User("FirstTest", "LastTest");
			User addedUser = da.saveUser(user);
			if(addedUser == null)
			{
				trace(" *** Failed to add user");
				fail(" *** Failed to add user"); // early exit
			}
			
			trace(" Test account next ...");
			Account newAccount = new Account(addedUser, "Test Account", 50.00);
			Account addedAccount = da.saveAccount(newAccount);
			List<Account> afterAccountList = da.getAccounts();
			
			if( beforeAccountList.size() != afterAccountList.size()-1)
			{
				trace(" *** Failed to add account");
				fail(" *** Failed to add account"); // early exit
			}
			
			// clean up;
			da.removeAccount(addedAccount);
			afterAccountList = da.getAccounts();
			if( beforeAccountList.size() != afterAccountList.size())
			{
				trace(" *** Failed to remove account");
				fail(" *** Failed to remove account"); // early exit
			}
			
			da.removeUser(addedUser);
			
			trace("Test accounts complete");
		}
		catch(AtmDataException ex)
		{
			trace(ex.getMessage());
			ex.getStackTrace();
			result = false;
		}
		catch(Exception ex)
		{
			trace(ex.getMessage());
			ex.printStackTrace();
			result = false;
		}
		finally
		{
			if(da != null)
				da.close();
		}
		
		assertTrue(result);
	}

	@Test
	public void testOpenDataAccessWithParameters()
	{
		boolean result = true;
		DataAccess da = null;
		try
		{
			da = DataAccess.getInstance("root", "root");
			da.connect();
			Connection conn = da.getConnection();

			trace("Connected to database: " + conn.toString());
		}
		catch(AtmDataException ex)
		{
			trace(ex.getMessage());
			ex.getStackTrace();
			result = false;
		}
		catch(Exception ex)
		{
			trace(ex.getMessage());
			ex.getStackTrace();
			result = false;
		}
		finally
		{
			if(da != null)
				da.close();
		}

		assertTrue(result);
	}
	
	@Test
	public void testOpenDataAccess()
	{
		boolean result = true;
		DataAccess da = null;
		try
		{
			da = getDataAccess();
			Connection conn = da.getConnection();

			trace("Connected to database: " + conn.toString());
		}
		catch(AtmDataException ex)
		{
			trace(ex.getMessage());
			ex.getStackTrace();
			result = false;
		}
		catch(Exception ex)
		{
			trace(ex.getMessage());
			ex.getStackTrace();
			result = false;
		}
		finally
		{
			if(da != null)
				da.close();
		}

		assertTrue(result);
	}

	private DataAccess getDataAccess() throws AtmDataException
	{
		DataAccess da = null;
		da = DataAccess.getInstance();

		return da;
	}

	@Test
	public void testAddRemoveUser()
	{
		boolean result = true;
		DataAccess da = null;
		try
		{
			da = getDataAccess();
			da.connect();
			Connection conn = da.getConnection();

			trace("Connected to database: " + conn.toString());

			trace(" Test adding user ...");
			List<User> beforeUserList = da.getUsers();
			User user = new User("FirstTest", "LastTest");
			User addedUser = da.saveUser(user);
			if(addedUser == null)
			{
				trace(" *** Failed to add user");
				fail(" *** Failed to add user"); // early exit
			}

			trace("User saved: " + addedUser.toString());

			List<User> afterUserList = da.getUsers();

			if(beforeUserList.size() < afterUserList.size())
			{
				String msg = String.format("successful save: Count=%d",
						afterUserList.size());
				trace(msg);
				dumpList(afterUserList);

				da.removeUser(addedUser);
			}
			else
			{
				trace(" failed to save user");
				result = false;
			}

			trace("Test adding user complete");
		}
		catch(AtmDataException ex)
		{
			trace(ex.getMessage());
			result = false;
		}
		finally
		{
			if(da != null)
				da.close();
		}

		assertTrue(result);
	}
	
	@Test
	public void testUpdateUser()
	{
		boolean result = true;
		User newUser = new User("Update", "User");
		DataAccess da = null;
		try
		{
			da = getDataAccess();
			da.connect();
			User addedUser = da.saveUser(newUser);
			if(addedUser == null)
			{
				trace(" *** Failed to add user");
				fail(" *** Failed to add user"); // early exit
			}
			
			trace(addedUser.toString());
			
			// modify user
			addedUser.setFirstName("Modified");
			trace(addedUser.toString());
			
			da.saveUser(addedUser);
			
			User getUser = da.getUserById(addedUser.getUserId());
			if(!getUser.getFirstName().equals(addedUser.getFirstName()))
			{
				trace(" *** Failed to modify user");
				fail(" *** Failed to modify user"); // early exit
			}
			
			da.removeUser(addedUser);
		}
		catch(AtmDataException ex)
		{
			trace(ex.getMessage());
			result = false;
		}
		finally
		{
			if(da != null)
				da.close();
		}
		
		assertTrue(result);
	}

	private void dumpList(List<User> list)
	{
		for(User user : list)
		{
			trace(user.toString());
		}
	}

}
