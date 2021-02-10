package com.example.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;


import com.example.entity.Building;
import com.example.entity.Company;
import com.example.entity.Employee;
import com.example.model.db.CompanyDAO;

public class CompanyManager {

	private static CompanyManager instance;
	private static int lastCompanyId=1;
	private ConcurrentNavigableMap<Integer,Company> companies=new ConcurrentSkipListMap<Integer,Company>();
	private ConcurrentNavigableMap<Double,Company> sortedByProfit=new ConcurrentSkipListMap<Double,Company>(new Comparator<Double>() {
		
		@Override
		public int compare(Double o1, Double o2) {
			return Double.compare(o1, o2);
		}
	}).descendingMap();
	
	
	private CompanyManager() {
		for(Company c:CompanyDAO.getInstance().getAllCompanies()) {
			if(lastCompanyId<c.getComapnyId()) {
				lastCompanyId=c.getComapnyId();
			}
			System.out.println(c);
			companies.put(c.getComapnyId(), c);
			sortedByProfit.put(c.getProfit(), c);
		}
	}
	
	public static synchronized CompanyManager getInstance() {
		if(instance==null) {
			instance=new CompanyManager();
		}
		return instance;
	}
	
	public boolean ifThisVAT_NumberExists(String VAT_Number) {
		for(Company c: companies.values()) {
			if(VAT_Number.equals(c.getVAT_Number())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isThisCompanyNameExists(String companyName) {
		for(Company c: companies.values()) {
			if(companyName.equals(c.getVAT_Number())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean prepForLogin(String VAT_Number,String password) {
		for(Company c:companies.values()) {
			if(VAT_Number.equals(c.getVAT_Number())) {
				if(password.equals(c.getPassword())) {
					return true;
				}
				else
					return false;
			}
		}
		return false;
	}
	
	//synchronized collection
	public boolean addNewCompany(int companyId,Company c) {
		companies.put(companyId, c);
		return true;
	}
	
	public synchronized boolean checkPassword(Company company,String password) {
		for(Company c:companies.values()) {
			if(c.getComapnyId()==company.getComapnyId()) {
				if(c.getPassword().equals(password))
					return true;
				else
					return false;
			}
			
		}
		return false;
	}
	
	
	public int returnCompanyId(String VAT_Number) {
		for(Iterator<Entry<Integer,Company>> it=companies.entrySet().iterator();it.hasNext();) {
			Entry<Integer,Company> e=it.next();
			if(VAT_Number.equals(e.getValue().getVAT_Number())) {
				return e.getKey();
			}
		}
		return -1;
	}
	
	//calculate buildings 
	public synchronized void calculateBuildings(int companyId) {
		ArrayList<Building> buildings=new ArrayList<Building>();
		buildings=BuildingManager.getInstance().returnBuildingsOfCompanyId(companyId);
		System.err.println("Buildings: "+buildings);
		
		ArrayList<Employee> employees=new ArrayList<Employee>();
		employees=EmployeeManager.getInstance().returnEmployeesInThisCopany(companyId);
		System.err.println("Employees: "+employees);
		
		for (int i = 0; i < buildings.size(); i++) {
			System.out.println("1:   Building: "+buildings.get(i));
			int bul=employees.get(0).getEmployeeBuildings().size();
			Employee emp=employees.get(0);
			for (int j = 1; j < employees.size(); j++) {
				System.out.println("2:   Building: "+buildings.get(i)+"    Emloyee: "+employees.get(j));
				if(bul>employees.get(j).getEmployeeBuildings().size()) {
					bul=employees.get(j).getEmployeeBuildings().size();
					emp=employees.get(j);
					System.out.println("3!!!:   Building: "+buildings.get(i)+"    Emloyee: "+employees.get(j));
				}
				
			}
			emp.setEmployeeBuildings(buildings.get(i));
		}
		
	}
	
	//return the company with this vat number
	public synchronized Company returnCompanyByVATNumber(String VAT_Number) {
		
		for(Company c:companies.values()) {
			if(c.getVAT_Number().equals(VAT_Number)) {
				return c;
			}
		}
		return null;
	}
	
	public Map<Double, Company> getSortedByProfit() {
		return Collections.unmodifiableMap(sortedByProfit);
	}
	
	public int getLastCompanyId() {
		return lastCompanyId;
	}
	public synchronized void setLastCompanyId(int lastCompanyId) {
		CompanyManager.lastCompanyId = lastCompanyId;
	}

	

	
}
