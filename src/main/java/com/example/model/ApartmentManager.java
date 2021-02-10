package com.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import com.example.entity.Apartment;
import com.example.entity.Employee;
import com.example.model.db.ApartmentDAO;



public class ApartmentManager {
	
	private static ApartmentManager instance;
	private static int lastApartmentgId=1;
	private ConcurrentNavigableMap<Integer,Apartment> aparmets=new ConcurrentSkipListMap<Integer,Apartment>();
	
	private ApartmentManager() {
		for(Apartment a:ApartmentDAO.getInstance().getAllApartments()) {
			if(lastApartmentgId<a.getApartmentId()) {
				lastApartmentgId=a.getApartmentId();
			}
			System.out.println(a);
			aparmets.put(a.getApartmentId(), a);
		}
	}
	
	public static synchronized ApartmentManager getInstance() {
		if(instance==null) {
			instance=new ApartmentManager();
		}
		return instance;
	}
	
	
	public void addApartment(int apartmentID,Apartment apartment) {
		aparmets.put(apartmentID, apartment);
		lastApartmentgId++;
	}
	
	public synchronized boolean ifThisapartmentNumberAlreadyExists(int apartmentNumber) {
		for(Apartment a:aparmets.values()) {
			if(a.getApartmentNumber()==apartmentNumber) {
				return true;
			}
		}
		return false;
	}
	
	//find apartments with building id
	public synchronized ArrayList<Apartment> returnApartmentsWithBuildingId(int buildingId) {
		ArrayList<Apartment> list=new ArrayList<Apartment>();
		for(Apartment apartment:aparmets.values()) {
			if(apartment.getBuildingId()==buildingId) {
				list.add(apartment);
			}
			
		}
		return list;
	}
	
	
	//find apartments with building id and soreted it by onew and kids
		public synchronized ArrayList<Apartment> returnApartmentsWithBuildingIdSortedByOwnerAndKids(int buildingId) {
			ArrayList<Apartment> list=new ArrayList<Apartment>();
			for(Apartment apartment:aparmets.values()) {
				if(apartment.getBuildingId()==buildingId) {
					list.add(apartment);
				}
				
			}
			Collections.sort(list,Comparator.comparing(Apartment::getOwner)
					.thenComparingInt(Apartment::getKids));
			Collections.reverse(list);
			return list;
		}
	
	//return apartment obj by apartmentId
	public Apartment returnApartmentDetails(int apartmentId) {
		for(Iterator<Entry<Integer, Apartment>> it=aparmets.entrySet().iterator();it.hasNext();) {
			Entry<Integer, Apartment> e=it.next();
			if (e.getKey()==apartmentId) {
				return e.getValue();
			}
			
		}
		return null;
	}
	
	//change apartment 
	public synchronized boolean changeApartmentDetails(Apartment newApartment,int apartmentId) {
		for(Iterator<Entry<Integer, Apartment>> it=aparmets.entrySet().iterator();it.hasNext();) {
			Entry<Integer, Apartment> e=it.next();
			if (e.getKey()==apartmentId) {
				e.getValue().setApartmentId(apartmentId);
				e.getValue().setFloor(newApartment.getFloor());
				e.getValue().setApartmentNumber(newApartment.getApartmentNumber());
				e.getValue().setArea(e.getValue().getArea());
				e.getValue().setOwner(newApartment.getOwner());
				e.getValue().setMembers(newApartment.getMembers());
				e.getValue().setHasPet(newApartment.isHasPet());
				e.getValue().setKids(newApartment.getKids());
				e.getValue().setBuildingId(e.getValue().getBuildingId());
				return false;
			}
			
		}
		return true;
	}
	
	 
	
	public static int getLastApartmentgId() {
		return lastApartmentgId;
	}
	public static void setLastApartmentgId(int lastApartmentgId) {
		ApartmentManager.lastApartmentgId = lastApartmentgId;
	}
}
