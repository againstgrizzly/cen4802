package week12.app;

import static org.junit.Assert.*;
import org.junit.Test;
import test.AbstractJUnitBase;

/**
 * Tests the account class
 * @author Scott
 *
 */
public class AccountTestCase extends AbstractJUnitBase
{
	public AccountTestCase()
	{
		
	}

	@Test
	public void testAccountClassEquals()
	{
		trace("Testing User class and equals");
		boolean equalTest = true;
		
		User testUser = new User();
		
		// simple user creation
		Account account = new Account();
		User accountUser = account.getUser();
		
		trace("Default account user: " + accountUser.toString());
		
		Account account2 = new Account();						
		//trace(account2.toString());
		
		// expect to be same
		if( !account.equals(account2))
		{
			equalTest = false;
		}
		
		account2.setName("Jim's Checking");
		account2.setBalance(125.52);
		account2.setAccountId(1);					
		trace(account2.toString());

		// expect to be different
		if( account.equals(account2))
		{
			equalTest = false;
		}
		
		Account account3 = new Account(account2.getAccountId(), account2.getUser(), account2.getName(), account2.getBalance());		
		trace(account3.toString());
		
		// expect to be same
		if( !account3.equals(account2))
		{
			trace("Accounts different, expected to be the same");
			equalTest = false;
		}
		
		// assign a new user
		account3.setUser(testUser);
		User assignedUSer = account3.getUser();
		if( !testUser.equals(assignedUSer))
		{
			trace("Assigned user failed");
			equalTest = false;
		}
		
		if(equalTest)
		{
			trace("Account.equals test passed");
		}
		else
		{
			trace("Account.equals test failed");
		}
		
		assertTrue(equalTest);
	}

}
