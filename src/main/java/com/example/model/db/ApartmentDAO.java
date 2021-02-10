package com.example.model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.example.entity.Apartment;

public final class ApartmentDAO {
	
	private static ApartmentDAO instance;
	private static final String TABLE_NAME="housemanager.apartments";
	
	private ApartmentDAO() {}
	
	public synchronized static final ApartmentDAO getInstance() {
		if(instance==null) {
			instance=new ApartmentDAO();
		}
		return instance;
	}
	
	public Set<Apartment> getAllApartments(){
		Set<Apartment> apartments=new HashSet<Apartment>();
		Statement st=null;
		try {
			st=DBManager.getInstance().getConnection().createStatement();
			ResultSet result=st.executeQuery("select apartmentId,floor,apartmentNumber,area,owner,members,hasPet,kids,buildingId from "+TABLE_NAME+';');
			while(result.next()) {
				apartments.add(new Apartment(result.getInt("apartmentId"),
											result.getInt("floor"),
											result.getInt("apartmentNumber"),
											result.getDouble("area"),
											result.getString("owner"), 
											result.getInt("members"),
											result.getBoolean("hasPet"),
											result.getInt("kids"),
											result.getInt("buildingId")));
				}
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("ApartmentDAO statment thrown exception!");
		}finally {
			try {
				st.close();
			}catch(SQLException e) {
				System.err.println("st.close() throw exception! AparmentDAO in getAllApartments()");
				
			}
		}
		System.out.println("Aparments loaded successfully");
		return apartments;
	}
	
	
	public synchronized boolean saveApartment(Apartment apartment) {
		PreparedStatement prepStatement=null;
		try {
			prepStatement=DBManager.getInstance().getConnection().prepareStatement("insert into "+TABLE_NAME+" (floor,apartmentNumber,area,owner,members,hasPet,kids,buildingId)  values(?,?,?,?,?,?,?,?)");
			prepStatement.setInt(1, apartment.getFloor());
			prepStatement.setInt(2, apartment.getApartmentNumber());
			prepStatement.setDouble(3, apartment.getArea());
			prepStatement.setString(4, apartment.getOwner());
			prepStatement.setInt(5, apartment.getMembers());
			prepStatement.setBoolean(6, apartment.isHasPet());
			prepStatement.setInt(7, apartment.getKids());
			prepStatement.setInt(8, apartment.getBuildingId());
			prepStatement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("ApartmentDAO statment throw exception! in saveApartment()\n Apartment is not saved!");
		}
		finally {
			try {
				prepStatement.close();
			}catch (SQLException e) {
				System.err.println("prepStatment is not closed()! ApartmentDAO in saveApatment()!");
			}
		}
		System.out.println("Apartment add successully!");
		return true;
	}
	
	
	public synchronized boolean changeApartmentDetails(Apartment apartment,int apartmentId) {
		PreparedStatement prepStatement=null;
		try {
			prepStatement=DBManager.getInstance().getConnection().prepareStatement("update "+TABLE_NAME+" set floor=?,apartmentNumber=?,owner=?,members=?,hasPet=?,kids=? where apartmentId='"+apartmentId+"' ;");
			prepStatement.setInt(1, apartment.getFloor());
			prepStatement.setInt(2, apartment.getApartmentNumber());
			prepStatement.setString(3, apartment.getOwner());
			prepStatement.setInt(4, apartment.getMembers());
			prepStatement.setBoolean(5, apartment.isHasPet());
			prepStatement.setInt(6, apartment.getKids());
			prepStatement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("ApartmentDAO statment throw exception! in changeApartmentDetails()----> Apartment is not saved!");
		}
		finally {
			try {
				prepStatement.close();
			}catch (SQLException e) {
				System.err.println("prepStatment is not closed()! ApartmentDAO in changeApartmentDetails()!");
			}
		}
		System.out.println("changeApartmentDetails add successully!");
		return true;
	}
	
	
}
