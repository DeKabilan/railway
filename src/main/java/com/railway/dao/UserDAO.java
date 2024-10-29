package com.railway.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.railway.model.User;
public class UserDAO {
	
	//------Get User Data------
	
	public User getUser(String email) {
		User userFromDB = new User();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC","dekabilan","password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Users WHERE Email = \'"+email+"\'");
			System.out.println(rs);
			if(rs.next()) {
				userFromDB.setEmail(rs.getString("Email"));
				if(rs.getInt("Role") == 0) {
					userFromDB.setRole("admin");
				}
				else {
					userFromDB.setRole("user");
				}
				userFromDB.setPassword(rs.getString("Password"));
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		return userFromDB;
	}
	
	
	//-----Check if User Exist-------
	
	
	public Boolean isUserExist(String email) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC","dekabilan","password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Users WHERE Email = \'"+email+"\'");
			if(rs.next()) {
				return true;
			}
			else return false;
		}
		catch(Exception e){
			System.out.println(e);
		}
		System.out.println("End");
		return false;
	}
	
	//--------Create User--------
	
	public void createUser(String email,String pass) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC","dekabilan","password");
			Statement st = con.createStatement();
			System.out.println("INSERT INTO Users (Email,Password,Role) VALUES (\""+email+"\",\""+ pass+"\", 1)");
			st.execute("INSERT INTO Users (Email,Password,Role) VALUES (\""+email+"\",\""+ pass+"\", 1)");
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
}
