package com.example.entity;

public class Apartment {
	private int apartmentId;
	private int floor;
	private int apartmentNumber;
	private double area;
	private String owner;
	private int members;
	private boolean hasPet;
	private int kids;
	private int buildingId;
	
	public Apartment() {}
	
	public Apartment(int floor,int apartmentNumber,double area,String owner,int members,boolean hasPet,int kids,int buildingId){
			this.floor=floor;
			this.apartmentNumber=apartmentNumber;
			this.area=area;
			this.owner=owner;
			this.members=members;
			this.hasPet=hasPet;
			this.kids=kids;
			this.buildingId=buildingId;
	}

	public Apartment(int apartmentId,int floor,int apartmentNumber,double area,String owner,int members,boolean hasPet,int kids,int buildingId){
		this.apartmentId=apartmentId;
		this.floor=floor;
		this.apartmentNumber=apartmentNumber;
		this.area=area;
		this.owner=owner;
		this.members=members;
		this.hasPet=hasPet;
		this.kids=kids;
		this.buildingId=buildingId;
}
	
	
	public void changeOwner(String name,int members,boolean hasPet,int kids) {
			this.owner=name;
			this.members=members;
			this.hasPet=hasPet;
			this.kids=kids;
	}
	
	//remove apartment
	public void deleteAparment() {
		owner=null;
		members=0;
		hasPet=false;
		kids=0;
	}
	
	
	
	public String getOwner() {
		return owner;
	}
	public int getMembers() {
		return members;
	}
	public boolean isHasPet() {
		return hasPet;
	}
	public int getKids() {
		return kids;
	}
	public double getArea() {
		return area;
	}
	public int getFloor() {
		return floor;
	}
	public int getApartmentNumber() {
		return apartmentNumber;
	}

	
	public void setFloor(int floor) {
		this.floor = floor;
	}


	public void setApartmentNumber(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}


	public void setArea(double area) {
		this.area = area;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public void setMembers(int members) {
		this.members = members;
	}


	public void setHasPet(boolean hasPet) {
		this.hasPet = hasPet;
	}


	public void setKids(int kids) {
		this.kids = kids;
	}

	public int getApartmentId() {
		return apartmentId;
	}
	public int getBuildingId() {
		return buildingId;
	}
	public void setApartmentId(int apartmentId) {
		this.apartmentId = apartmentId;
	}
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}
	
	@Override
	public String toString() {
		return "Apartment [floor=" + floor + ", apartmentNumber=" + apartmentNumber + ", area=" + area + ", owner="
				+ owner + ", members=" + members + ", hasPet=" + hasPet + ", kids="
				+ kids + "]";
	}
	
	
	
}
