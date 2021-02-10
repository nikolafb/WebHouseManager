package com.example.entity;


public class Building {
	private int buildingId;
	private String city;
	private String address;
	private int floors;
	private String builtUpArea;
	private String dateOfCollectingTaxes="[non]";
	private int removed;
	private int companyId;
	
	public Building() {}
	
	public Building(String city, String address, int floors, String builtUpArea,int companyId) {
		this.city = city;
		this.address = address;
		this.floors = floors;
		this.builtUpArea = builtUpArea;
		this.companyId=companyId;
	}
	
//	public Building(int buildingId,String city, String address, int floors, String builtUpArea,String dateOfCollectingTaxes,int companyId) {
//		this.buildingId=buildingId;
//		this.city = city;
//		this.address = address;
//		this.floors = floors;
//		this.builtUpArea = builtUpArea;
//		this.dateOfCollectingTaxes=dateOfCollectingTaxes;
//		this.companyId=companyId;
//	}
	
	public Building(int buildingId,String city, String address, int floors, String builtUpArea,String dateOfCollectingTaxes,int removed,int companyId) {
		this.buildingId=buildingId;
		this.city = city;
		this.address = address;
		this.floors = floors;
		this.builtUpArea = builtUpArea;
		this.dateOfCollectingTaxes=dateOfCollectingTaxes;
		this.removed=removed;
		this.companyId=companyId;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getFloors() {
		return floors;
	}
	public void setFloors(int floors) {
		this.floors = floors;
	}
	public String getBuiltUpArea() {
		return builtUpArea;
	}
	public void setBuiltUpArea(String builtUpArea) {
		this.builtUpArea = builtUpArea;
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getRemoved() {
		return removed;
	}
	public void setRemoved(int removed) {
		this.removed = removed;
	}
	public String getDateOfCollectingTaxes() {
		return dateOfCollectingTaxes;
	}
	public void setDateOfCollectingTaxes(String dateOfCollectingTaxes) {
		this.dateOfCollectingTaxes = dateOfCollectingTaxes;
	}
	
	@Override
	public String toString() {
		return "City: " + city + " address: " + address + " floors: " + floors
				+ ", builtUpArea: " + builtUpArea + ", companyId=" + companyId;
	}
	
}
