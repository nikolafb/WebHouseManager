package com.example.modle;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.entity.Apartment;
import com.example.model.ApartmentManager;

class ApartmentManagerTest {

	private ApartmentManager am=ApartmentManager.getInstance();
	
	private Apartment one=new Apartment(1, 2, 3, 123.123, "Test One", 4, true, 1, -1);
	private Apartment two=new Apartment(2, 3, 4, 234.123, "Test two", 5, false, 2, -1);
	private Apartment three=new Apartment(3, 4, 5, 345.123, "Test three", 6, true, 3, -2);
	private Apartment four=new Apartment(4, 5, 6, 456.123, "Test four", 7, false, 4, 3);
	private Apartment five=new Apartment(5, 5, 6, 456.123, "Test five", 1, false, 1, 3);
	
	@BeforeEach
	void addApartment() {
		am.addApartment(1, one);
		am.addApartment(2, two);
		am.addApartment(3, three);
		am.addApartment(4, four);
		am.addApartment(5, five);
	}
	
	@Test
	void returnApartmentDetailsTest() {
		assertEquals(one, am.returnApartmentDetails(1));
		assertEquals(two, am.returnApartmentDetails(2));
		assertEquals(three, am.returnApartmentDetails(3));
		assertEquals(four, am.returnApartmentDetails(4));
		assertNotEquals(two, am.returnApartmentDetails(-2));
		assertNull(am.returnApartmentDetails(-2));
	}
	
	@Test
	void returnApartmentsWithBuildingIdSortedByOwnerAndKids() {
		ArrayList<Apartment> list=new ArrayList<Apartment>();
		list.add(one);
		list.add(two);
		Collections.sort(list,Comparator.comparing(Apartment::getOwner)
				.thenComparingInt(Apartment::getKids));
		Collections.reverse(list);
		assertEquals(list, am.returnApartmentsWithBuildingIdSortedByOwnerAndKids(-1));
		list.add(three);
		assertNotEquals(list, am.returnApartmentsWithBuildingIdSortedByOwnerAndKids(-1));
	}
	
	@Test
	void returnApartmentsWithBuildingIdTest() {
		ArrayList<Apartment> list=new ArrayList<Apartment>();
		list.add(one);
		list.add(two);
		assertEquals(list, am.returnApartmentsWithBuildingId(-1));
		list.clear();
		list.add(three);
		assertEquals(list, am.returnApartmentsWithBuildingId(-2));
	}
	
	@Test
	void ifThisapartmentNumberAlreadyExistsTest() {
		assertEquals(true, am.ifThisapartmentNumberAlreadyExists(5));
		assertEquals(false, am.ifThisapartmentNumberAlreadyExists(-5));
	}
	
	@Test
	void changeApartmentDetailsTest() {
		Apartment old=new Apartment(0, 5, 6, 456.123, "Test four", 7, false, 4, 3);
		Apartment newApartment=new Apartment(-1, 2, 3, 456.123, "Test Test True", 1, false, 1, 3);
		assertTrue(am.changeApartmentDetails(newApartment, -2));
		am.addApartment(0, old);
		am.addApartment(-1, newApartment);
		assertFalse(am.changeApartmentDetails(newApartment, -1));		
	}
	
	
	@Test
	void setLastApartmentgIdTest() {
		ApartmentManager.setLastApartmentgId(100000);
		assertEquals(100000, ApartmentManager.getLastApartmentgId());
	}
	
	
}
