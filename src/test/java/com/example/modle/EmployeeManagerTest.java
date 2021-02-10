package com.example.modle;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.entity.Employee;
import com.example.model.EmployeeManager;

class EmployeeManagerTest {

	EmployeeManager em=EmployeeManager.getInstance();
	private Employee one=new Employee(1, "one", "one", "one", "one", "one", "one", 0, "one", 1);
	private Employee two=new Employee(2, "two", "two", "two", "two", "two", "two", 0, "two", 2);
	private Employee three=new Employee(3, "three", "three", "three", "three", "three", "three", 0, "three", 1);
	
	private ArrayList<Employee> list=new ArrayList<Employee>();
	
	@BeforeEach
	void addEmp() {
		em.removeAllemployees();
		em.addEmployee(1, one);
		em.addEmployee(2, two);
		em.addEmployee(3, three);
		
		list.add(one);
		list.add(two);
		list.add(three);
	}
	
	@Test
	void getAllEmployeesByCompanyIdTest() {
		list.remove(two);
		Collections.reverse(list);
		assertEquals(list, em.getAllEmployeesByCompanyId(1));
		
	}
	
	@Test
	void returnEmployeeDetailsByEmployeeIdTest() {
		assertEquals(one, em.returnEmployeeDetailsByEmployeeId(1));
		assertNotEquals(one, em.returnEmployeeDetailsByEmployeeId(2));
		assertNull(em.returnEmployeeDetailsByEmployeeId(-1));
	}

	@Test
	void returnTrueIfThisEmployeeThisCompanyIdTest() {
		assertTrue(em.returnTrueIfThisEmployeeThisCompanyId(1, 1));
		assertFalse(em.returnTrueIfThisEmployeeThisCompanyId(1, 2));
		assertFalse(em.returnTrueIfThisEmployeeThisCompanyId(2, 1));
	}
	
	@Test
	void changeEmployeeDetailsTest() {
		assertEquals(one, em.changeEmployeeDetails(1, one));
		assertNotEquals(one, em.changeEmployeeDetails(2, one));
		assertNotEquals(one, em.changeEmployeeDetails(1, two));
		assertNotEquals(two, em.changeEmployeeDetails(2, one));
		assertNotEquals(two, em.changeEmployeeDetails(1, two));
		assertNull(em.changeEmployeeDetails(-1, one));
		assertNull(em.changeEmployeeDetails(1, new Employee(30000, "three", "three", "three", "three", "three", "three", 0, "three", 1)));
	}
	
	@Test
	void returnEmployeesInThisCopanyTest() {
		list.remove(two);
		Collections.reverse(list);
		assertEquals(list, em.returnEmployeesInThisCopany(1));
		assertTrue(em.returnEmployeesInThisCopany(1312).size()==0);
	}
	
	
	
}
