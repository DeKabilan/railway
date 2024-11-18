package com.railway.utils;
import java.sql.Connection;
import java.sql.DriverManager;
public class DAOConnection {
	
	Connection connection = null;
	
	public Connection getConnection() {
		if(connection == null) {
			initializeConnection();
		}
		return connection;
	}
	
	private void initializeConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC","dekabilan","password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
