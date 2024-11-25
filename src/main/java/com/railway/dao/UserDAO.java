package com.railway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.railway.model.User;
import com.railway.utils.DAOConnection;
public class UserDAO {
	
	DAOConnection connection = new DAOConnection();
	
	
	//------Get User Data------
	
	public User getUser(String email) {
		User userFromDB = new User();
		Connection con = connection.getConnection();
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Users WHERE Email = \'"+email+"\'");
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
		Connection con = connection.getConnection();
		try{
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
		return false;
	}
	
	//--------Create User--------
	
	public void createUser(String email,String pass) {
		Connection con = connection.getConnection();
		try{
			Statement st = con.createStatement();
			st.execute("INSERT INTO Users (Email,Password,Role) VALUES (\""+email+"\",\""+ pass+"\", 1)");
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
}
