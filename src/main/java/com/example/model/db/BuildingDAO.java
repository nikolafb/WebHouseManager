package com.example.model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.example.entity.Building;

public final class BuildingDAO {

	private static BuildingDAO instance;
	private static final String TABLE_NAME="housemanager.buildings";
	
	private BuildingDAO() {}
	
	public synchronized static final BuildingDAO getInstance() {
		if(instance==null) {
			instance=new BuildingDAO();
		}
		return instance;
	}
	
	public Set<Building> getAllBuildings(){
		Set<Building> buildings=new HashSet<Building>();
		Statement st=null;
		try {
			st=DBManager.getInstance().getConnection().createStatement();
			ResultSet result=st.executeQuery("select buildingId,city,address,floors,buildUpArea,dateOfCollectingTaxes,removed,companyId from "+TABLE_NAME+';');
			while(result.next()) {
				buildings.add(new Building(result.getInt("buildingId"),
											result.getString("city"),
											result.getString("address"),
											result.getInt("floors"),
											result.getString("buildUpArea"),
											result.getString("dateOfCollectingTaxes"),
											result.getInt("removed"),
											result.getInt("companyId")));
				}
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("BuildingDAO statment thrown exception!");
			}
			finally {
				try {
					st.close();
				}catch(SQLException e) {
					System.err.println("st.close() throw exception! BuildingDAO in getAllBuildings()");
				}
			}
			System.out.println("Buildings loaded successfully");
			return buildings;
		}
	
	public synchronized boolean saveBuilding(Building building) {
		PreparedStatement prepStatement=null;
		try {
			prepStatement=DBManager.getInstance().getConnection().prepareStatement("insert into "+TABLE_NAME+" (city,address,floors,buildUpArea,companyId) values(?,?,?,?,?);");
			prepStatement.setString(1, building.getCity());
			prepStatement.setString(2, building.getAddress());
			prepStatement.setInt(3, building.getFloors());
			prepStatement.setString(4, building.getBuiltUpArea());
			prepStatement.setInt(5, building.getCompanyId());
			prepStatement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("BuldingDAO statment throw exception! in saveBuilding()\n Building is not saved!");
		}
		finally {
			try {
				prepStatement.close();
			}catch(SQLException e) {
				System.err.println("prepStatment is not closed()! BuildingDAO in saveBuilding()!");
			}
		}
		System.out.println("Building add successully!");
		return true;
	}
		
		
	public synchronized boolean removeBuilding(int buildingId) {
		PreparedStatement prepStatement=null;
		try {
			prepStatement=DBManager.getInstance().getConnection().prepareStatement("update "+TABLE_NAME+" set removed=1 where buildingId="+buildingId+';');
			prepStatement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("BuldingDAO statment throw exception! in removeBuilding()\n Building is not removed!");
		}
		finally {
			try {
				prepStatement.close();
			}catch(SQLException e) {
				System.err.println("prepStatment is not closed()! BuildingDAO in removeBuilding()!");
			}
		}
		System.out.println("Building removed successully!");
		return true;
	}
	
	
	public synchronized boolean dateOfCollectingTaxes(int buildingId,String date) {
		PreparedStatement prepStatement=null;
		try {
			prepStatement=DBManager.getInstance().getConnection().prepareStatement("update "+TABLE_NAME+" set dateOfCollectingTaxes=? where buildingId="+buildingId+';');
			prepStatement.setString(1, date);
			prepStatement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("BuldingDAO statment throw exception! in dateOfCollectingTaxes()");
		}
		finally {
			try {
				prepStatement.close();
			}catch(SQLException e) {
				System.err.println("prepStatment is not closed()! BuildingDAO in dateOfCollectingTaxes()!");
			}
		}
		return true;
	}
	
	
}
