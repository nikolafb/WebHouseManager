package com.example.modle;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.entity.Building;
import com.example.model.BuildingManager;

class BuildingManagerTest {

	private BuildingManager bm=BuildingManager.getInstance();
	
	private Building one=new Building(1,"City", "Address", 1, "Builduparea","[non]", 0,1);
	private Building two=new Building(2,"City1", "Address1", 2, "Builduparea1","[non]",1, 1);
	private Building three=new Building(3,"City2", "Address1", 3, "Builduparea2","[non]",0, 2);
	private Building four=new Building(4,"City3", "Address1", 4, "Builduparea3","[non]",0, 1);
	
	private ArrayList<Building> list=new ArrayList<Building>();
	
	@BeforeEach
	void addBulding() {
		//bm.removeAllBuildings();
		bm.addBuilding(1, one);
		bm.addBuilding(2, two);
		bm.addBuilding(3, three);
		bm.addBuilding(4, four);
		
		list.add(one);
		list.add(two);
		list.add(four);	
	}
	
	@Test
	void buildingsOnThisCompanyTest() {
		assertEquals(list, bm.buildingsOnThisCompany(1));
	}
	
	@Test
	void checkIfThisCompnayIdServeThisBuildingIdTest() {
		assertTrue(bm.checkIfThisCompnayIdServeThisBuildingId(1, 1));
		assertFalse(bm.checkIfThisCompnayIdServeThisBuildingId(1, 3));
	}
	
	@Test
	void returnOnlyOneBuildingByBuildingIdTest() {
		assertNull(bm.returnOnlyOneBuildingByBuildingId(-1));
		assertEquals(four, bm.returnOnlyOneBuildingByBuildingId(4));
		assertNotEquals(three, bm.returnOnlyOneBuildingByBuildingId(4));
	}
	
	@Test
	void returnBuildingsOfCompanyIdTest() {
		assertEquals(list, bm.returnBuildingsOfCompanyId(1));
		assertNotEquals(list, bm.returnBuildingsOfCompanyId(2));
	}
	
}
