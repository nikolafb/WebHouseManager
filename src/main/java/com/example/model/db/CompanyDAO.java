 package com.example.model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.example.entity.Company;

public final class CompanyDAO {

	private static CompanyDAO instance;
	private static final String TABLE_NAME="housemanager.companies";
	
	private CompanyDAO(){}
	
	public synchronized static final CompanyDAO getInstance() {
		if(instance==null) {
			instance=new CompanyDAO();
		}
		return instance;
	}
	
	
	public Set<Company> getAllCompanies(){
		Set<Company> companies=new HashSet<Company>();
		Statement st=null;
		try {
			st=DBManager.getInstance().getConnection().createStatement();
			ResultSet result=st.executeQuery("select companyId,companyName,VAT_Number,seat,city,MOL,priceArea,pricePet,pricePerson,profit,password from "+TABLE_NAME+';');
			while(result.next()) {
				companies.add(new Company(result.getInt("companyId"),
									 result.getString("companyName"), 
									 result.getString("VAT_Number"), 
									 result.getString("seat"), 
									 result.getString("city"), 
									 result.getString("MOL"),
									 result.getDouble("priceArea"),
									 result.getDouble("pricePet"),
									 result.getDouble("pricePerson"),
									 result.getDouble("profit"),
									 result.getString("password")));
				}
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("CompanyDAO statment thrown exception!");
		}
		finally {
			try {
				st.close();
			}catch(SQLException e) {
				System.err.println("st.close() throw exception! CompanyDAO in getAllCompanies()");
			}
		}
		System.out.println("Companies loaded successfully");
		return companies;
	}
	
	public synchronized boolean saveCompany(Company company) {
		PreparedStatement prepStatement=null;
		try {
			prepStatement=DBManager.getInstance().getConnection().prepareStatement("insert into "+TABLE_NAME+"(companyName,VAT_Number,seat,city,MOL,password) values(?,?,?,?,?,?);");
			prepStatement.setString(1, company.getCompanyName());
			prepStatement.setString(2, company.getVAT_Number());
			prepStatement.setString(3, company.getSeat());
			prepStatement.setString(4, company.getCity());
			prepStatement.setString(5, company.getMOL());
			prepStatement.setString(6, company.getPassword());
			prepStatement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("CompanyDAO statment throw exception! in saveCompany()\n Company is not saved!");
		}
		finally {
			try {
				prepStatement.close();
			}catch(SQLException e) {
				System.err.println("prepStatment is not closed()! CompanyDAO in saveComapany()!");
			}
		}
		System.out.println("Company add successully!");
		return true;
	}
	
	
	public synchronized boolean changePrice(Company company,int companyId) {
		PreparedStatement prepStatement=null;
		try {
			prepStatement=DBManager.getInstance().getConnection().prepareStatement("update "+TABLE_NAME+" set priceArea=?,pricePet=?,pricePerson=? where companyId="+companyId+';');
			prepStatement.setDouble(1, company.getPriceArea());
			prepStatement.setDouble(2, company.getPricePet());
			prepStatement.setDouble(3, company.getPricePerson());
			prepStatement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("\nCompanyDAO statment throw exception! in changePrice()\n");
		}
		finally {
			try {
				prepStatement.close();
			}catch(SQLException e) {
				System.err.println("prepStatment is not closed()! CompanyDAO in changePrice()!");
			}
		}
		return true;
	}
	
	public synchronized boolean addProfit(Company company) {
		PreparedStatement prepStatement=null;
		try {
			prepStatement=DBManager.getInstance().getConnection().prepareStatement("update "+TABLE_NAME+" set profit=? where companyId="+company.getComapnyId()+';');
			prepStatement.setDouble(1, company.getProfit());
			prepStatement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("\nCompanyDAO statment throw exception! in addProfit()\n");
		}
		finally {
			try {
				prepStatement.close();
			}catch(SQLException e) {
				System.err.println("prepStatment is not closed()! CompanyDAO in addProfit()!");
			}
		}
		return true;
	}
	
}
