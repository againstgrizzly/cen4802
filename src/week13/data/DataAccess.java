package week13.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.logging.Logger;

import week13.app.Account;
import week13.app.User;
import week13.util.AtmLogger;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Provides the interface to the datastore For this implementation that is a
 * MySql database
 * 
 * @author scottl
 *
 */
public class DataAccess
{
	private final static Logger logger = Logger
			.getLogger(AtmLogger.ATM_LOGGER + "." + DataAccess.class.getName());

	/**
	 * Singleton pattern implementation.
	 * 
	 * @return DataAccess instance #throws AtmDataException on error
	 */
	public static DataAccess getInstance() throws AtmDataException
	{
		// hand off to parameterized method
		return DataAccess.getInstance("root", "root");
	}

	/**
	 * Parameterized version to support testing and configuration
	 * 
	 * @return
	 * @throws AtmDataException
	 */
	public static DataAccess getInstance(String user, String password)
			throws AtmDataException
	{
		if(m_data == null)
		{
			m_data = new DataAccess(user, password); // for this class, update
														// the user/password if
														// needed for your local
														// instance
		}

		return m_data;
	}
	
	/**
	 * Adds or updates an existing user If the account is new, the returned account
	 * object has an updated system id and is fully valid for follow on use
	 * 
	 * @param user
	 *            Account to save
	 * @return Updated Account reference
	 * @throws AtmDataException
	 */
	public Account saveAccount(Account account) throws AtmDataException
	{
		Account returnedAccount = null;

		//
		// determine if this is an insert or update
		//
		if(account.getAccountId() == -1)
		{
			// insert the user, user is new
			returnedAccount = insertAccount(account);
		}
		else
		{
			Account existingAccount = this.getAccountById(account.getAccountId());
			if(existingAccount == null)
			{
				returnedAccount = insertAccount(account); 
			}
			else
			{
				returnedAccount = updateAccount(account);
			}
		}
		
		return returnedAccount;
	}

	/**
	 * Update a Account set of data
	 * 
	 * @param Account
	 *            Account to update
	 * @throws AtmDataException
	 */
	private Account updateAccount(Account account) throws AtmDataException
	{
		Statement updateStmt = null;
		String updateTime = m_formatter.format(new java.util.Date());
		String sql = String.format(UPDATE_ACCOUNT_SQL, account.getName(),
				account.getBalance(), updateTime, account.getAccountId());
		try
		{
			if(!m_conn.isClosed())
			{
				updateStmt = m_conn.createStatement();
				updateStmt.execute(sql);
			}
		}
		catch(SQLException ex)
		{
			logger.severe("Error updating account - " + ex.getMessage());
			throw new AtmDataException(
					"Error updating account - " + ex.getMessage(), ex);
		}

		return account;
	}

	/**
	 * Insert a new Account.
	 * 
	 * @param user
	 *            the Account reference to add
	 * @return updated Account reference with new ID
	 */
	private Account insertAccount(Account account) throws AtmDataException
	{
		Account newAccount = null;
		Statement insertStmt = null;
		Statement lastIndex = null;
		String updateTime = m_formatter.format(new java.util.Date());
		String sql = "";
		
		try
		{
			sql = String.format(INSERT_ACCOUNT_SQL,
					account.getUser().getUserId(), account.getName(),account.getBalance(), updateTime);
		}
		catch(IllegalFormatException ex)
		{
			logger.severe("Error formatting account insert SQL- \n" + ex.getMessage() );
			throw new AtmDataException("Error formatting account insert SQL", ex);
		}
				
		try
		{
			if(!m_conn.isClosed())
			{
				insertStmt = m_conn.createStatement();
				insertStmt.execute(sql);

				lastIndex = m_conn.createStatement();

				// get the newly created ID
				ResultSet rs = lastIndex.executeQuery(m_LAST_INSERT_ID);
				while(rs.next())
				{
					int id = rs.getInt(1);
					newAccount = getAccountById(id);
				}
			}
		}
		catch(SQLException ex)
		{
			logger.severe("Error inserting account - " + ex.getMessage());
			throw new AtmDataException(
					"Error inserting account - " + ex.getMessage(), ex);
		}		

		return newAccount;
	}

	/**
	 * Private Default Constructor
	 * 
	 * @param userName
	 *            the data access user name
	 * @param userPassword
	 *            the data access password
	 */
	private DataAccess(String userName, String userPassword)
			throws AtmDataException
	{
		m_formatter = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		AtmLogger.addAtmHandler(logger);
		this.m_connectionString = String.format(m_CONN_FMT, userName,
				userPassword);

		connect();

		logger.info("Successfully connected to database: " + m_conn.toString());
	}

	// Account methods
	/**
	 * Removes an account from the system.
	 * 
	 * @param account
	 *            Account to remove
	 * @return The Account that was removed
	 * @throws AtmDataException
	 */
	public Account removeAccount(Account account) throws AtmDataException
	{
		Account accountExists = getAccountById(account.getAccountId());
		Account deletedAccount = null;
		Statement deleteStatement = null;

		if(accountExists != null)
		{
			String sql = String.format(DELETE_ACCOUNT_BY_ID_SQL,
					accountExists.getAccountId());
			try
			{
				if(!m_conn.isClosed())
				{
					deleteStatement = m_conn.createStatement();
					deleteStatement.execute(sql);
					int count = deleteStatement.getUpdateCount();
					if( count > 0 )
					{
						deletedAccount = accountExists;
					}
				}
			}
			catch(SQLException ex)
			{
				logger.info("Error removing user - " + ex.getMessage());
				throw new AtmDataException("Error removing user;", ex);
			}
		}
		return deletedAccount;
	}

	/**
	 * Retrieves an account by its ID
	 * 
	 * @param accountId
	 *            The account id
	 * @return An Account or null if not found
	 */
	public Account getAccountById(long accountId) throws AtmDataException
	{
		Account foundAccount = null;
		Statement selectStmt = null;
		try
		{
			// prepare the sql
			String sql = String.format(SELECT_ACCOUNT_SQL, accountId);

			Connection conn = getOpenConnection();
			selectStmt = conn.createStatement();
			ResultSet rs = selectStmt.executeQuery(sql);

			while(rs.next())
			{
				long id = rs.getLong("id");
				long userId = rs.getLong("user_id");
				String accountName = rs.getString("name");
				double balance = rs.getFloat("balance");

				// get the User
				User accountUser = getUserById(userId);

				foundAccount = new Account(id, accountUser, accountName,
						balance);
			}
		}
		catch(SQLException ex)
		{
			logger.severe("Error getting user by ID - " + ex.getMessage());
			throw new AtmDataException(
					"Error retrieving user - " + ex.getMessage(), ex);
		}

		return foundAccount;

	}

	/**
	 * List of accounts This results in an additional query for the account user
	 * based on the user id.
	 * 
	 * @return List of accounts
	 * @throws AtmDataException
	 */
	public List<Account> getAccounts() throws AtmDataException
	{
		List<Account> accounts = new ArrayList<Account>();

		ResultSet resultSet = null;
		Statement queryStatement = null;
		try
		{
			queryStatement = m_conn.createStatement();
			resultSet = queryStatement.executeQuery(SELECT_ALL_ACCOUNTS_SQL);
			while(resultSet.next())
			{
				long accountId = resultSet.getLong("id");
				long userId = resultSet.getLong("user_id");
				String accountName = resultSet.getString("name");
				double balance = resultSet.getFloat("balance");

				// get the User
				User accountUser = getUserById(userId);

				accounts.add(new Account(accountId, accountUser, accountName,
						balance));
			}
		}
		catch(SQLException ex)
		{
			// log error
			logger.severe("Error getting accounts - " + ex.getMessage());
			throw new AtmDataException(ex);
		}

		return accounts;
	}

	/**
	 * Removes a user from the system. Verify the user exists before removing.
	 * 
	 * @param user
	 *            The user to remove
	 * @return The user removed. Allows for any post processing by the caller.
	 * 
	 * @throws AtmDataException
	 *             on error
	 */
	public User removeUser(User user) throws AtmDataException
	{
		User userExists = getUserById(user.getUserId());
		User deletedUser = null;
		Statement deleteStatement = null;

		if(userExists != null)
		{
			String sql = String.format(DELETE_USER_BY_ID_SQL,
					userExists.getUserId());
			try
			{
				if(!m_conn.isClosed())
				{
					deleteStatement = m_conn.createStatement();
					if(deleteStatement.execute(sql))
					{
						deletedUser = userExists;
					}
				}
			}
			catch(SQLException ex)
			{
				logger.info("Error removing user - " + ex.getMessage());
				throw new AtmDataException("Error removing user;", ex);
			}
		}

		return deletedUser;
	}

	/**
	 * Adds or updates an existing user If the user is new, the returned user
	 * object has an updated system id and is fully valid for follow on use
	 * 
	 * @param user
	 *            User to save
	 * @return Updated user reference
	 * @throws AtmDataException
	 */
	public User saveUser(User user) throws AtmDataException
	{
		User returnedUser = null;

		//
		// determine if this is an insert or update
		//
		if(user.getUserId() == -1)
		{
			// insert the user, user is new
			returnedUser = insertUser(user);
		}
		else
		{
			User existingUser = this.getUserById(user.getUserId());
			if(existingUser == null)
			{
				returnedUser = insertUser(user); // user doesn't exist by that
													// ID, create user
			}
			else
			{
				returnedUser = updateUser(user);
			}
		}

		return returnedUser;
	}

	/**
	 * Retrieve the list of Users
	 * 
	 * @return List of Users
	 * @throws AtmDataException
	 */
	public List<User> getUsers() throws AtmDataException
	{
		List<User> userList = new ArrayList<User>();
		ResultSet resultSet = null;

		Statement queryStatement = null;
		try
		{
			queryStatement = m_conn.createStatement();
			resultSet = queryStatement.executeQuery(SELECT_ALL_USERS_SQL);

			while(resultSet.next())
			{
				long userId = resultSet.getLong("id");
				String first = resultSet.getString("first_name");
				String last = resultSet.getString("last_name");

				userList.add(new User(userId, first, last));
			}
		}
		catch(SQLException ex)
		{
			// log error
			logger.severe("Error getting users - " + ex.getMessage());
			throw new AtmDataException(ex);
		}

		return userList;
	}

	/**
	 * Retrieves an existing user from the datastore
	 * 
	 * @param id
	 * @return User instance or null if not found
	 * @throws AtmDataException
	 */
	public User getUserById(long id) throws AtmDataException
	{
		User foundUser = null;
		Statement selectStmt = null;
		try
		{
			// prepare the sql
			String sql = String.format(SELECT_USER_SQL_FMT, id);

			Connection conn = getOpenConnection();
			selectStmt = conn.createStatement();
			ResultSet rs = selectStmt.executeQuery(sql);

			while(rs.next())
			{
				int userId = rs.getInt(1);
				String first = rs.getString(2);
				String last = rs.getString(3);

				foundUser = new User(userId, first, last);
			}
		}
		catch(SQLException ex)
		{
			logger.severe("Error getting user by ID - " + ex.getMessage());
			throw new AtmDataException(
					"Error retrieving user - " + ex.getMessage(), ex);
		}

		return foundUser;
	}

	/**
	 * Update a user set of data
	 * 
	 * @param user
	 *            User to update
	 * @throws AtmDataException
	 */
	private User updateUser(User user) throws AtmDataException
	{
		Statement updateStmt = null;
		String updateTime = m_formatter.format(new java.util.Date());
		String sql = String.format(UPDATE_USER_SQL, user.getFirstName(),
				user.getLastName(), updateTime, user.getUserId());
		try
		{
			if(!m_conn.isClosed())
			{
				updateStmt = m_conn.createStatement();
				updateStmt.execute(sql);
			}
		}
		catch(SQLException ex)
		{
			logger.severe("Error updating user - " + ex.getMessage());
			throw new AtmDataException(
					"Error updating user - " + ex.getMessage(), ex);
		}

		return user;
	}

	/**
	 * Insert a new User.
	 * 
	 * @param user
	 *            the User reference to add
	 * @return updated User reference with new ID
	 */
	private User insertUser(User user) throws AtmDataException
	{
		User newUser = null;
		Statement insertStmt = null;
		Statement lastIndex = null;
		String updateTime = m_formatter.format(new java.util.Date());
		String sql = String.format(INSERT_USER_SQL, user.getFirstName(),
				user.getLastName(), updateTime);

		try
		{
			if(!m_conn.isClosed())
			{
				insertStmt = m_conn.createStatement();
				insertStmt.execute(sql);

				lastIndex = m_conn.createStatement();

				// get the newly created ID
				ResultSet rs = lastIndex.executeQuery(m_LAST_INSERT_ID);
				while(rs.next())
				{
					int id = rs.getInt(1);
					newUser = getUserById(id);
				}
			}
		}
		catch(SQLException ex)
		{
			logger.severe("Error inserting user - " + ex.getMessage());
			throw new AtmDataException(
					"Error inserting user - " + ex.getMessage(), ex);
		}

		return newUser;
	}

	/**
	 * Connect to the datastore
	 * 
	 * @throws AtmDataException
	 */
	public void connect() throws AtmDataException
	{
		try
		{
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");

			// setup the connection with the DB.
			m_conn = DriverManager.getConnection(m_connectionString);
		}
		catch(SQLException ex)
		{
			// log exception
			logger.info("Error getting connection - " + ex.getMessage());
			throw new AtmDataException("Error getting connection - ", ex);
		}
		catch(Exception ex)
		{
			// log exception
			logger.info("Error getting connection - " + ex.getMessage());
			System.out.println(ex.getMessage());
			throw new AtmDataException(ex);
		}
	}

	public void close()
	{
		try
		{
			Connection m_conn = getConnection();
			m_conn.close();
		}
		catch(SQLException ex)
		{
			// eat any exceptions at this phase
		}
	}

	/**
	 * Returns the connection instance
	 * 
	 * @return
	 */
	public Connection getConnection()
	{
		return m_conn;
	}

	/**
	 * Retrieves the open connection instance
	 * 
	 * @return the open connection instance
	 * @throws AtmDataException
	 *             if connection is not open or doesn't exist
	 */
	private Connection getOpenConnection() throws AtmDataException
	{
		try
		{
			if(m_conn == null || m_conn.isClosed())
			{
				String msg = m_conn == null ? "Connection is invalid (null)"
						: "Connection is closed";
				logger.info(msg);
				throw new AtmDataException(msg);
			}

		}
		catch(SQLException ex)
		{
			logger.info("Error with isClosed() method - " + ex.getMessage());
			throw new AtmDataException("Error with isClosed() method");
		}

		return m_conn;
	}

	/** DataAccess reference */
	private static DataAccess m_data;

	/** Formatted connection string. Updated at initialization */
	private String m_connectionString;

	/** Connection string format */
	private String m_CONN_FMT = "jdbc:mysql://localhost:3306/atm?user=%s&password=%s";

	/** Local connection reference */
	private Connection m_conn = null;

	/** Used for the timestamp when updating datastore */
	private SimpleDateFormat m_formatter;

	// Query templates
	private String INSERT_USER_SQL = "INSERT INTO atm.user (first_name,last_name,last_update) values ('%s', '%s','%s')";
	private String UPDATE_USER_SQL = "UPDATE atm.user SET first_name='%s',last_name='%s',last_update='%s' WHERE id=%d";
	private String SELECT_USER_SQL_FMT = "SELECT id, first_name, last_name from atm.user WHERE id=%d";
	private String SELECT_ALL_USERS_SQL = "SELECT id, first_name, last_name from atm.user";
	private String DELETE_USER_BY_ID_SQL = "DELETE from atm.user WHERE ID=%d";

	// account templates
	private String INSERT_ACCOUNT_SQL = "INSERT INTO atm.account (user_id,name,balance,last_update) values (%d,'%s', %f,'%s')";
	private String UPDATE_ACCOUNT_SQL = "UPDATE atm.account SET user_id=%d, name='%s',balance=%f,last_update='%s' WHERE id=%d";
	private String SELECT_ACCOUNT_SQL = "SELECT id, user_id, name, balance from atm.account WHERE id=%d";
	private String SELECT_ALL_ACCOUNTS_SQL = "SELECT id, user_id, name, balance from atm.account";
	private String DELETE_ACCOUNT_BY_ID_SQL = "DELETE from atm.account WHERE id=%d";

	// Used to retrieve the auto-generated ID when inserting a new user or
	// account.
	private static String m_LAST_INSERT_ID = "SELECT LAST_INSERT_ID();";
}
