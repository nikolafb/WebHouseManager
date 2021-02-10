package com.example.model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.example.entity.Employee;

public final class EmployeeDAO {

	private static EmployeeDAO instance;
	private static final String TABLE_NAME="housemanager.employees";
	
	private EmployeeDAO() {}
	
	
	public synchronized static final EmployeeDAO getInstance() {
		if(instance==null) {
			instance=new EmployeeDAO();
		}
		return instance;
	}
	
	public Set<Employee> getAllEmployees(){
		Set<Employee> employees=new HashSet<Employee>();
		Statement st=null;
		try {
			st=DBManager.getInstance().getConnection().createStatement();
			ResultSet result=st.executeQuery("select employeeId,fName,lName,IDNumber,cityOfBirth,dateOfBirth,localAddress,collectedTaxes,dateOfCollectingTaxes,companyId from "+TABLE_NAME+';');
			while(result.next()) {
				employees.add(new Employee(result.getInt("employeeId"),
											result.getString("fName"),
											result.getString("lName"),
											result.getString("IDNumber"),
											result.getString("cityOfBirth"),
											result.getString("dateOfBirth"),
											result.getString("localAddress"),
											result.getDouble("collectedTaxes"),
											result.getString("dateOfCollectingTaxes"), 
											result.getInt("companyId")));
						}
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("EmployeeDAO statment thrown exception!");
		}finally {
			try {
				st.close();
			}catch(SQLException e) {
				System.err.println("st.close() throw exception! EmployeeDAO in getAllEmployees()");
				
			}
		}
		System.out.println("Employee loaded successfully");
		return employees;
	}
	
	
	public synchronized boolean saveEmployee(Employee employee) {
		PreparedStatement prepStatement=null;
		try {
			prepStatement=DBManager.getInstance().getConnection().prepareStatement("insert into "+TABLE_NAME+"(fName,lName,IDNumber,cityOfBirth,dateOfBirth,localAddress,collectedTaxes,dateOfCollectingTaxes,companyId) values(?,?,?,?,?,?,?,?,?)");
			prepStatement.setString(1, employee.getfName());
			prepStatement.setString(2, employee.getlName());
			prepStatement.setString(3, employee.getIDNumber());
			prepStatement.setString(4, employee.getCityOfBirth());
			prepStatement.setString(5, employee.getDateOfBirth());
			prepStatement.setString(6, employee.getLocalAddress());
			prepStatement.setDouble(7, employee.getCollectedTaxes());
			prepStatement.setString(8, employee.getDateOfCollectingTaxes());
			prepStatement.setInt(9, employee.getCompanyId());
			prepStatement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("EmployeeDAO statment throw exception! in saveEmployee()\n Employee is not saved!");
		}
		finally {
			try {
				prepStatement.close();
			}catch(SQLException e) {
				System.err.println("prepStatment is not closed()! EmployeeDAO in saveEmployee()!");
			}
		}
		System.out.println("Employee add successully!");
		return true;
	}
		
		
	
	public synchronized boolean changeEmployeeDetails(int employeeId,Employee employee) {
		PreparedStatement prepStatement=null;
		try {
			prepStatement=DBManager.getInstance().getConnection().prepareStatement("update "+TABLE_NAME+" set fName=?, lName=?, localAddress=? where employeeId='"+employeeId+"' ;");
			prepStatement.setString(1,employee.getfName());
			prepStatement.setString(2,employee.getlName());
			prepStatement.setString(3, employee.getLocalAddress());
			prepStatement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("EmployeeDAO statment throw exception! in changeEmployeeDetails()----> Employee is not saved!");
		}
		finally {
			try {
				prepStatement.close();
			}catch (SQLException e) {
				System.err.println("prepStatment is not closed()! EmployeeDAO in changeEmployeeDetails()!");
			}
		}
		System.out.println("changeEmplyeeDetails add successully!");
		return true;
	}
	
	
	public synchronized boolean deleteEmployee(int employeeId) {
		PreparedStatement prepStatement=null;
		try {
			prepStatement=DBManager.getInstance().getConnection().prepareStatement("delete from "+TABLE_NAME+" where employeeId='"+employeeId+"' ;");
			prepStatement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("EmployeeDAO statment throw exception! in deleteEmployee()----> Employee is not deleted!");
		}
		finally {
			try {
				prepStatement.close();
			}catch (SQLException e) {
				System.err.println("prepStatment is not closed()! EmployeeDAO in deleteEmployee()!");
			}
		}
		return true;
	}
	
	public synchronized boolean collectedTaxes(int employeeId,Employee emp) {
		PreparedStatement prepStatement=null;
		try {
			prepStatement=DBManager.getInstance().getConnection().prepareStatement("update "+TABLE_NAME+" set collectedTaxes=?,dateOfCollectingTaxes=? where employeeId='"+employeeId+"' ;");
			prepStatement.setDouble(1, emp.getCollectedTaxes());
			prepStatement.setString(2, emp.getDateOfCollectingTaxes());
			prepStatement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("EmployeeDAO statment throw exception! in deleteEmployee()----> Employee is not deleted!");
		}
		finally {
			try {
				prepStatement.close();
			}catch (SQLException e) {
				System.err.println("prepStatment is not closed()! EmployeeDAO in deleteEmployee()!");
			}
		}
		return true;
	}
	
	
}
