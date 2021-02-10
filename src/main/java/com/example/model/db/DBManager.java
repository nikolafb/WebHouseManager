package com.example.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



final class DBManager {

	private static DBManager instance;
	private Connection connection;
	
	private static final String DBIP="127.0.0.1";
	private static final String DBPort="3306";
	private static final String DBName="housemanager";
	private static final String URL="jdbc:mysql://"+DBIP+":"+DBPort+"/"+DBName;
	private static final String USER="root";
	private static final String PASSWORD="Kolio99!";
	
	private DBManager() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Class.forName load status: FAIL !");
			e.printStackTrace();
			
		}
		try {
			connection= DriverManager.getConnection(URL,USER,PASSWORD);
		} catch (SQLException e) {
			System.out.println("Connection to DB status= fail!");
			e.printStackTrace();
		}
		
	}
	
	 synchronized static DBManager getInstance() {
		if(instance==null) {
			instance=new DBManager();
		}
		return instance;
	}
	
	 Connection getConnection()  {
		 if(connection==null) {
			 System.err.println("getConnection form DBManager is null!");
		 }
		return connection;
	}
}

