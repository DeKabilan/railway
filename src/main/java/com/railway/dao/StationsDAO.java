package com.railway.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.railway.decorator.JSONDecorator;
import com.railway.model.Station;

public class StationsDAO {
	JSONDecorator json = new JSONDecorator();

	public Station getStation(String code) {
		Station stationFromDB = new Station();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC", "dekabilan", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Stations WHERE Code = \'" + code + "\'");
			System.out.println(rs);
			if (rs.next()) {
				stationFromDB.setCode(rs.getString("Code"));
				stationFromDB.setName(rs.getString("Name"));
			} else
				stationFromDB.setCode("null");
		} catch (Exception e) {
			System.out.println(e);
		}
		return stationFromDB;
	}

	public void JSONtoDB(String path) {
		JSONArray array = json.JSONtoArray(path);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC", "dekabilan", "password");
			String insertQuery = "INSERT IGNORE INTO Stations (Name,Code) VALUES (?,?) ON DUPLICATE KEY UPDATE Name = VALUES(Name),Code = VALUES(Code);";
			PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				preparedStatement.setString(1, (String) obj.get("name"));
				preparedStatement.setString(2, (String) obj.get("code"));
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public Boolean isStationExist(String code) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC", "dekabilan", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Stations WHERE Code = \'" + code + "\'");
			if (rs.next()) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public void createStation(String name, String code) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC", "dekabilan", "password");
			Statement st = con.createStatement();
			st.execute("INSERT INTO Stations (Name,Code) VALUES (\"" + name + "\",\"" + code + "\")");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void deleteStation(String code) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC", "dekabilan", "password");
			Statement st = con.createStatement();
			st.execute("DELETE FROM Stations WHERE Code = \"" + code + "\"");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void updateStation(String code, String newCode, String newName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC", "dekabilan", "password");
			Statement st = con.createStatement();
			st.execute("UPDATE Stations SET Code = \"" + newCode + "\", Name = \"" + newName + "\" WHERE Code = \""
					+ code + "\"");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public int getAmountOfData() {
		int result = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC", "dekabilan", "password");
			String query = "SELECT COUNT(*) as count FROM Stations;";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt("count");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public ArrayList<Station> getPage(int pageno, int amount) {
		ArrayList<Station> result = new ArrayList<Station>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC", "dekabilan", "password");
			String query = "SELECT * FROM Stations ORDER BY Code LIMIT ? OFFSET ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, amount);
			ps.setInt(2, pageno);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Station station = new Station();
				station.setCode(rs.getString("Code"));
				station.setName(rs.getString("Name"));
				result.add(station);

			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public ArrayList<Station> getAllStations() {
		ArrayList<Station> result = new ArrayList<Station>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC", "dekabilan", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Stations");
			while (rs.next()) {
				Station station = new Station();
				station.setCode(rs.getString("Code"));
				station.setCode(rs.getString("Name"));
				result.add(station);

			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
}
