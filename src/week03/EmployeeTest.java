package week03;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeTest
{

	@Test
	public void defaultConstructorTest()
	{
		Employee employee = new Employee();
		
		String expFirstname = "New First";
		String expLastname = "New Last";
		double expSalary = 0.0;
		
		assertEquals(expFirstname, employee.getFirstName());
		assertEquals(expLastname, employee.getLastName());
		assertEquals(expSalary, employee.getSalary(), 1e-6);
	}
	
	@Test
    public void twoParamConstructorTest()
    {
		String expFirstname = "Bobby";
		String expLastname = "Flay";
		double expSalary = 0.0;
		
        Employee employee = new Employee(expFirstname, expLastname);
		
		assertEquals(expFirstname, employee.getFirstName());
		assertEquals(expLastname, employee.getLastName());
		assertEquals(expSalary, employee.getSalary(), 1e-6);
    }
	
	@Test
    public void threeParamConstructorTest()
    {
		String expFirstname = "Bobby";
		String expLastname = "Flay";
		double expSalary = 123.0;
		
        Employee employee = new Employee(expFirstname, expLastname, expSalary);
		
		assertEquals(expFirstname, employee.getFirstName());
		assertEquals(expLastname, employee.getLastName());
		assertEquals(expSalary, employee.getSalary(), 1e-6);
    }
	
	@Test
	public void setFirstNameTest()
	{
		String expFirstname = "Bobby";
		
        Employee employee = new Employee();
        employee.setFirstName(expFirstname);
		
		assertEquals(expFirstname, employee.getFirstName());
	}
	
	@Test
	public void setLastNameTest()
	{
		String expLastname = "Flay";
		
        Employee employee = new Employee();
        employee.setLastName(expLastname);
		
		assertEquals(expLastname, employee.getLastName());
	}
	
	@Test
	public void setSalaryTest()
	{
		double salary = 123.0;
		
        Employee employee = new Employee();
        employee.setSalary(salary);
		
		assertEquals(salary, employee.getSalary(), 1e-6);
	}
	
	@Test
	public void getDisplayNameTest()
	{
		String fn = "Bobby";
		String ln = "Flay";
		double salary = 123.0;
		
        Employee employee = new Employee(fn, ln, salary);
		
        String expDisplayName = "Flay, Bobby";
        assertEquals(expDisplayName, employee.getDisplayName());
	}
	
	@Test
	public void getFormattedSalaryTest()
	{
		String fn = "Bobby";
		String ln = "Flay";
		double salary = 123.123;
		
        Employee employee = new Employee(fn, ln, salary);
		
        String expFormattedSalary = "123.12";
        assertEquals(expFormattedSalary, employee.getFormattedSalary());
        
        // Set a salary that'll round up
        double salary1 = 123.128;
        
        employee.setSalary(salary1);
        
        String expFormattedSalary1 = "123.13";
        assertEquals(expFormattedSalary1, employee.getFormattedSalary());
	}
	
	@Test
	public void nominalEmployeeEqualsTest()
	{
		Employee e1 = new Employee("Bobby", "Flay", 123.321);
		Employee e2 = new Employee("Bobby", "Flay", 123.321);
		
		assertEquals(e1, e2);
	}
	
	@Test
	public void firstNameBadEmployeeEqualsTest()
	{
		Employee e1 = new Employee("Bbby", "Flay", 123.321);
		Employee e2 = new Employee("Bobby", "Flay", 123.321);
		
		assertNotEquals(e1, e2);
	}
	
	@Test
	public void lastNameBadEmployeeEqualsTest()
	{
		Employee e1 = new Employee("Bobby", "Fay", 123.321);
		Employee e2 = new Employee("Bobby", "Flay", 123.321);
		
		assertNotEquals(e1, e2);
	}
	
	@Test
	public void salaryBadEmployeeEqualsTest()
	{
		Employee e1 = new Employee("Bobby", "Flay", 113.321);
		Employee e2 = new Employee("Bobby", "Flay", 123.321);
		
		assertNotEquals(e1, e2);
	}
	
	@Test
	public void toStringTest()
	{
		Employee e1 = new Employee("Bobby", "Flay", 113.321);
		
		String exp = "Flay, Bobby Salary: $113.32";
		String act = e1.toString();
		assertEquals(exp, act);
	}

}
