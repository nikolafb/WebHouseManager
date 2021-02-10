package com.example.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.db.DBManager;

public class DBManagerTest {

	private static final String DBIP="127.0.0.1";
	private static final String DBPort="3306";
	private static final String DBName="housemanager";
	private static final String URL="jdbc:mysql://"+DBIP+":"+DBPort+"/"+DBName;
	private static final String USER="root";
	private static final String PASSWORD="Kolio99!";
	private Connection connection;
	
	@BeforeEach
	void loadDriver() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection= DriverManager.getConnection(URL,USER,PASSWORD);
	}
	
	@Test
	void getConection() {
		assertNotNull(connection);
	}
	
	@Test
	void conncetion() {
		assertNotNull(DBManager.getInstance().getConnection());
	}
	
}
