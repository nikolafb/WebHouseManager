package com.example.entity;


public class Company {

	private int comapnyId;
	private String companyName;
	private String VAT_Number;
	private String seat;
	private String city;
	private String MOL;
	private String password;
	private String confirmPassword;
	private double priceArea=0.1;
	private double pricePet=0.1;
	private double pricePerson=0.1;
	private double profit=0;
	
	public Company() {}
	
	
	
	public Company(double priceArea, double pricePet, double pricePerson) {
		this.priceArea = priceArea;
		this.pricePet = pricePet;
		this.pricePerson = pricePerson;
	}



	public Company(String companyName,String VAT_Number,String seat,String city,String MOL,String password) {
			this.companyName=companyName;
			this.VAT_Number=VAT_Number;
			this.seat=seat;
			this.city=city;
			this.MOL=MOL;
			this.password=password;
	}

	public Company(int companyId,String companyName,String VAT_Number,String seat,String city,String MOL,double priceArea,double pricePet,double pricePerson,double profit,String password) {
		this.comapnyId=companyId;
		this.companyName=companyName;
		this.VAT_Number=VAT_Number;
		this.seat=seat;
		this.city=city;
		this.MOL=MOL;
		this.priceArea=priceArea;
		this.pricePet=pricePerson;
		this.pricePerson=pricePerson;
		this.profit=profit;
		this.password=password;
}
	
	
	
	
	public int getComapnyId() {
		return comapnyId;
	}

	public void setComapnyId(int comapnyId) {
		this.comapnyId = comapnyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getVAT_Number() {
		return VAT_Number;
	}

	public void setVAT_Number(String vAT_Number) {
		VAT_Number = vAT_Number;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMOL() {
		return MOL;
	}

	public void setMOL(String mOL) {
		MOL = mOL;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	
	public double getPriceArea() {
		return priceArea;
	}



	public void setPriceArea(double priceArea) {
		this.priceArea = priceArea;
	}



	public double getPricePet() {
		return pricePet;
	}



	public void setPricePet(double pricePet) {
		this.pricePet = pricePet;
	}



	public double getPricePerson() {
		return pricePerson;
	}



	public void setPricePerson(double pricePerson) {
		this.pricePerson = pricePerson;
	}



	public double getProfit() {
		return profit;
	}



	public void setProfit(double profit) {
		this.profit = profit;
	}



	@Override
	public String toString() {
		return "Company [comapnyId=" + comapnyId + ", companyName=" + companyName + ", VAT_Number=" + VAT_Number
				+ ", seat=" + seat + ", city=" + city + ", MOL=" + MOL + ", password=" + password + ", confirmPassword="
				+ confirmPassword + "]";
	}

	
}
