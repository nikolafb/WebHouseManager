package com.example.modle;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.entity.Building;
import com.example.entity.Company;
import com.example.entity.Employee;
import com.example.model.BuildingManager;
import com.example.model.CompanyManager;
import com.example.model.EmployeeManager;

class CompanyManagerTest {

	private CompanyManager cm=CompanyManager.getInstance();
	
	private Company one=new Company(1, "one", "one", "one", "one", "one", 1, 1, 1, 1, "pass1");
	private Company two=new Company(2, "two", "two", "two", "two", "two", 2, 2, 2, 2, "pass2");
	private Company three=new Company(3, "three", "three", "three", "three", "three", 3, 3, 3, 3, "pass3");
	private Company four=new Company(4, "four", "four", "four", "four", "four", 4, 4, 4, 4, "pass4");
	
	private ArrayList<Company> list=new ArrayList<Company>();
	
	@BeforeEach
	void addCompany() {
		cm.addNewCompany(1, one);
		cm.addNewCompany(2, two);
		cm.addNewCompany(3, three);
		cm.addNewCompany(4, four);
		
		list.add(one);
		list.add(two);
		list.add(three);
		list.add(four);
		
	}
	
	@Test
	void ifThisVAT_NumberExistsTest() {
		assertTrue(cm.ifThisVAT_NumberExists(one.getVAT_Number()));
	}
	
	@Test
	void isThisCompanyNameExistsTest() {
		assertTrue(cm.isThisCompanyNameExists(three.getCompanyName()));
	}
	
	@Test
	void prepForLoginTest() {
		assertTrue(cm.prepForLogin(one.getVAT_Number(), one.getPassword()));
		assertFalse(cm.prepForLogin(two.getVAT_Number(), one.getPassword()));
		assertFalse(cm.prepForLogin(one.getVAT_Number(), two.getPassword()));
	}
	
	@Test
	void checkPasswordTest() {
		assertTrue(cm.checkPassword(one,one.getPassword()));
		assertFalse(cm.checkPassword(one,"ads"));
		assertFalse(cm.checkPassword(two,one.getPassword()));
	}
	
	@Test
	void returnCompanyIdTest() {
		assertEquals(-1, cm.returnCompanyId("1"));
		assertEquals(1, cm.returnCompanyId("one"));
		assertEquals(1, cm.returnCompanyId(one.getVAT_Number()));
	}
	
	@Test
	void calculateBuildingsTest() {
		ArrayList<Employee> employees=new ArrayList<Employee>();
		
		Employee empOne=new Employee(1, "Test1", "Test1", "Test1", "Test1", "Test1", "Test1", 0, "Test1", 1);
		Employee empTwo=new Employee(2, "Test2", "Test2", "Test2", "Test2", "Test2", "Test2", 0, "Test2", 1);
		employees.add(empOne);
		employees.add(empTwo);
		
		EmployeeManager.getInstance().addEmployee(12, new Employee(1, "Test1", "Test1", "Test1", "Test1", "Test1", "Test1", 0, "Test1", 1));
		EmployeeManager.getInstance().addEmployee(22, new Employee(2, "Test2", "Test2", "Test2", "Test2", "Test2", "Test2", 0, "Test2", 1));
		
		BuildingManager.getInstance().addBuilding(12, new Building(1, "Test1Building", "Test1Building", 2, "Test1Building", "Test1Building", 0, 1));
		BuildingManager.getInstance().addBuilding(21, new Building(2, "Test2Building", "Test2Building", 2, "Test2Building", "Test2Building", 0, 1));
		
		cm.calculateBuildings(1);
		
		for (int i = 0; i < employees.size(); i++) {
			System.out.println(employees.get(i).getEmployeeBuildings());
		}
		
	}
	
	@Test
	void returnCompanyByVATNumberTest() {
		assertEquals(one, cm.returnCompanyByVATNumber(one.getVAT_Number()));
		assertNotEquals(one, cm.returnCompanyByVATNumber(two.getVAT_Number()));
	}
	
	
}
