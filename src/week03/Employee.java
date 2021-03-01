package week03;

import java.text.DecimalFormat;

/**
 * Represents an employee
 *
 * @author scottl
 *
 */
public class Employee
{
	/**
	 * Default constructor
	 */
	public Employee()
	{
		this("New First", "New Last");
	}

	/**
	 * Overloaded constructor
	 *
	 * @param first
	 *            First name
	 * @param last
	 *            Last name
	 */
	public Employee(String first, String last)
	{
		this(first, last, 0.0);
	}

	/**
	 * Parameterized constructor
	 *
	 * @param first
	 *            First name
	 * @param last
	 *            Last name
	 * @param salary
	 *            Salary
	 */
	public Employee(String first, String last, double salary)
	{
		m_first = first;
		m_last = last;
		m_salary = salary;
		m_decimalFormatter = new DecimalFormat(MONEY_PATTERN);
	}

	/** Setter for first name */
	public void setFirstName(String first)
	{
		m_first = first;
	}

	/** Setter for last name */
	public void setLastName(String last)
	{
		m_last = last;
	}

	/** Setter for last name */
	public void setSalary(double salary)
	{
		m_salary = salary;
	}

	/** Getter for first name */
	public String getFirstName()
	{
		return m_first;
	}

	/** Getter for last name */
	public String getLastName()
	{
		return m_last;
	}

	/**
	 * Returns a formatted display name For example: LaChance, Scott Display
	 * format is last, first.
	 *
	 * @return Formatted display name as last, first
	 */
	public String getDisplayName()
	{
		return String.format("%s, %s", m_last, m_first);
	}

	/** Getter for salary */
	public double getSalary()
	{
		return m_salary;
	}

	/**
	 * Provides the Salary as a string in the following format: "###,##0.00"
	 * Examples: 5,005.65, 0.65, 5.85, 202,333.99
	 *
	 * @return Formated salary string
	 */
	public String getFormattedSalary()
	{
		return m_decimalFormatter.format(getSalary());
	}
	
	@Override
	public int hashCode() 
	{
		int arbitraryPrimeNumber = 31;
		
		int newHash = this.getFirstName().hashCode() * arbitraryPrimeNumber
				+ this.getLastName().hashCode() * arbitraryPrimeNumber
				+ this.getFormattedSalary().hashCode() * arbitraryPrimeNumber;
		
		return newHash;
	};

	/**
	 * This override compares two Employee instances. They are equal if the
	 * first, last and salary data elements are equal.
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		if(obj instanceof Employee)
		{
			Employee rhs = (Employee)obj;
			if(this.getFirstName().equals(rhs.getFirstName())
					&& this.getLastName().equals(rhs.getLastName())
					&& this.getFormattedSalary().equals(
							rhs.getFormattedSalary())) // This helps avoid
														// issues with double
														// precision
			{
				result = true;
			}
		}

		return result;
	}

	/**
	 * Formats the content of the Employee object.
	 * "LaChance, Scott Salary: $5,005.65"
	 */
	@Override
	public String toString()
	{
		String output = m_decimalFormatter.format(getSalary());
		return String.format("%s Salary: $%s", getDisplayName(), output);
	}

	/** First name attribute */
	private String m_first; 

	/** Last name attribute */
	private String m_last;

	/** Salary attribute */
	private double m_salary;

	/** The format for the salary */
	private static String MONEY_PATTERN = "###,##0.00";

	/** Formatter instance for formatting the salary */
	private DecimalFormat m_decimalFormatter;
}
