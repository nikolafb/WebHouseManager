package com.example.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import com.example.entity.Building;
import com.example.model.db.BuildingDAO;

public class BuildingManager {

	private static BuildingManager instance;
	private static int lastBuildingId=1;
	private ConcurrentNavigableMap<Integer,Building> buildings=new ConcurrentSkipListMap<Integer,Building>();//KEY-> buildingId; VALUE-> Building
	
	
	private BuildingManager() {
		for(Building b:BuildingDAO.getInstance().getAllBuildings()) {
			if(lastBuildingId<b.getBuildingId()) {
				lastBuildingId=b.getBuildingId();
			}
			if(b.getRemoved()!=1)
			buildings.put(b.getBuildingId(), b);
		}
	}
	
	public static synchronized BuildingManager getInstance() {
		if(instance==null) {
			instance=new BuildingManager();
		}
		return instance;
	}
	//synchronized collection
	public void addBuilding(int buildingId,Building building) {
		buildings.put(buildingId, building);
	}
	
	
	public synchronized ArrayList<Building> buildingsOnThisCompany(int companyId) {
		ArrayList<Building> list=new ArrayList<Building>();
		for(Iterator<Entry<Integer, Building>> it=buildings.entrySet().iterator();it.hasNext();) {
			Entry<Integer, Building> e=it.next();
			if(e.getValue().getCompanyId()==companyId){
				list.add(e.getValue());
			}
		}
		return list;
	}
	
	public synchronized boolean checkIfThisCompnayIdServeThisBuildingId(int companyId, int buildingId) {
		for(Iterator<Entry<Integer, Building>> it=buildings.entrySet().iterator();it.hasNext();) {
			Entry<Integer, Building> e=it.next();
			if(e.getKey()==buildingId) {
				if(companyId==e.getValue().getCompanyId()) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		
		return false;
	}
	
	
	
	//for apartment controller -> returnBuildings
	public synchronized Building returnOnlyOneBuildingByBuildingId(int buildingId) {
		for(Iterator<Entry<Integer, Building>> it=buildings.entrySet().iterator();it.hasNext();) {
			Entry<Integer, Building> e=it.next();
			if(e.getKey()==buildingId) {
				return e.getValue();
			}
		}
		return null ;
	}
	
	//removing building by buidling id
	public void removeThisBuildng(int buildingId) {
		 buildings.remove(buildingId);
	}
	
	
	
	//all buildings that some company serve
	public synchronized ArrayList<Building> returnBuildingsOfCompanyId(int companyId) {
		ArrayList<Building> list=new ArrayList<Building>();
		for(Iterator<Entry<Integer, Building>> it=buildings.entrySet().iterator();it.hasNext();) {
			Entry<Integer, Building> e=it.next();
			if(e.getValue().getCompanyId()==companyId) {
				list.add(e.getValue());
			}
		}
		return list;
	}
	
	
	
	
	public int getLastBuildingId() {
		return lastBuildingId;
	}

	public synchronized static void setLastBuildingId(int lastBuildingId) {
		BuildingManager.lastBuildingId = lastBuildingId;
	}
	
	
	
	
}
