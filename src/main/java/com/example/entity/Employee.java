package com.example.entity;

import java.util.ArrayList;

public class Employee {
	
	private int employeeId;
	private String fName;
	private String lName;
	private String IDNumber;
	private String cityOfBirth;
	private String dateOfBirth;
	private String localAddress;
	private double collectedTaxes;
	private String dateOfCollectingTaxes;
	private int companyId;
	
	private ArrayList<Building> employeeBuildings=new ArrayList<Building>();
	
	public Employee() {}

	
	public Employee(int employeeId, String fName, String lName, String IDNumber, String cityOfBirth, String dateOfBirth,
			String localAddress, double collectedTaxes, String dateOfCollectingTaxes, int companyId) {
		this.employeeId = employeeId;
		this.fName = fName;
		this.lName = lName;
		this.IDNumber = IDNumber;
		this.cityOfBirth = cityOfBirth;
		this.dateOfBirth = dateOfBirth;
		this.localAddress = localAddress;
		this.collectedTaxes = collectedTaxes;
		this.dateOfCollectingTaxes = dateOfCollectingTaxes;
		this.companyId = companyId;
	}



	public Employee(String fName, String lName, String IDNumber, String cityOfBirth, String dateOfBirth,
			String localAddress, double collectedTaxes, String dateOfCollectingTaxes, int companyId) {
		this.fName = fName;
		this.lName = lName;
		this.IDNumber = IDNumber;
		this.cityOfBirth = cityOfBirth;
		this.dateOfBirth = dateOfBirth;
		this.localAddress = localAddress;
		this.collectedTaxes = collectedTaxes;
		this.dateOfCollectingTaxes = dateOfCollectingTaxes;
		this.companyId = companyId;
	}



	public int getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}



	public String getfName() {
		return fName;
	}



	public void setfName(String fName) {
		this.fName = fName;
	}



	public String getlName() {
		return lName;
	}



	public void setlName(String lName) {
		this.lName = lName;
	}



	public String getIDNumber() {
		return IDNumber;
	}



	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}



	public String getCityOfBirth() {
		return cityOfBirth;
	}



	public void setCityOfBirth(String cityOfBirth) {
		this.cityOfBirth = cityOfBirth;
	}



	public String getDateOfBirth() {
		return dateOfBirth;
	}



	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}



	public String getLocalAddress() {
		return localAddress;
	}



	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}



	public double getCollectedTaxes() {
		return collectedTaxes;
	}



	public void setCollectedTaxes(double collectedTaxes) {
		this.collectedTaxes = collectedTaxes;
	}



	public String getDateOfCollectingTaxes() {
		return dateOfCollectingTaxes;
	}



	public void setDateOfCollectingTaxes(String dateOfCollectingTaxes) {
		this.dateOfCollectingTaxes = dateOfCollectingTaxes;
	}



	public int getCompanyId() {
		return companyId;
	}



	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public ArrayList<Building> getEmployeeBuildings() {
		return employeeBuildings;
	}
	
	public synchronized void setEmployeeBuildings(Building b) {
		employeeBuildings.add(b);
	}
	
	public synchronized void removeAllFormList() {
		employeeBuildings.clear();
	}
	
	public int getEmpleeBuildingsSize() {
		return employeeBuildings.size();
	}
	
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", fName=" + fName + ", lName=" + lName + ", IDNumber=" + IDNumber
				+ ", cityOfBirth=" + cityOfBirth + ", dateOfBirth=" + dateOfBirth + ", localAddress=" + localAddress
				+ ", collectedTaxes=" + collectedTaxes + ", dateOfCollectingTaxes=" + dateOfCollectingTaxes
				+ ", companyId=" + companyId + ", employeeBuildings=" + employeeBuildings + "]";
	}
	

	
	
}
