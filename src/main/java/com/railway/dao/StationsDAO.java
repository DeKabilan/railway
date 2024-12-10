package com.railway.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.railway.model.Station;
import com.railway.utils.DAOConnection;
import com.railway.utils.JSONFormatter;

public class StationsDAO {
	JSONFormatter json = new JSONFormatter();
	DAOConnection connection = new DAOConnection();
	Connection con = connection.getConnection();

	public Station getStation(String code) {
		Station stationFromDB = new Station();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT * FROM Stations WHERE Code = \'" + code + "\' OR Name = \"" + code + "\"");
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
		ArrayList<Station> stationList = new JSONFormatter().stationstoArray(path);

		try {
			String insertQuery = "INSERT IGNORE INTO Stations (Name,Code) VALUES (?,?) ON DUPLICATE KEY UPDATE Name = VALUES(Name),Code = VALUES(Code);";
			PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
			for (Station station : stationList) {
				preparedStatement.setString(1, station.getName());
				preparedStatement.setString(2, station.getCode());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public Boolean isStationExist(String code) {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT * FROM Stations WHERE Code = \'" + code + "\' OR Name = \"" + code + "\"");
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

			Statement st = con.createStatement();
			st.execute("INSERT INTO Stations (Name,Code) VALUES (\"" + name + "\",\"" + code + "\")");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void deleteStation(String code) {
		try {
			Statement st = con.createStatement();
			st.execute("DELETE FROM Stations WHERE Code = \"" + code + "\"");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void updateStation(String code, String newCode, String newName) {
		try {
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
			String query = "SELECT * FROM Stations ORDER BY Name LIMIT ? OFFSET ?";
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

	public int getAmountOfDataSearch(String stationName) {
		int result = 0;
		try {
			String query = "SELECT COUNT(*) as count FROM Stations WHERE Name LIKE ? or Code Like ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "%" + stationName + "%");
			ps.setString(2, "%" + stationName + "%");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt("count");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public ArrayList<Station> searchStation(String stationName, int pageNumber, int amount) {
		ArrayList<Station> stationList = new ArrayList<Station>();
		String query = "SELECT * FROM Stations WHERE Name LIKE ? or Code LIKE ? ORDER BY Name LIMIT ? OFFSET ?";

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "%" + stationName + "%");
			ps.setString(2, "%" + stationName + "%");
			ps.setInt(3, amount);
			ps.setInt(4, pageNumber);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Station station = new Station();
				station.setCode(rs.getString("Code"));
				station.setName(rs.getString("Name"));
				stationList.add(station);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stationList;

	}

	public ArrayList<Station> getEveryStation() {
		ArrayList<Station> stationList = new ArrayList<Station>();
		try {
			String query = "SELECT * FROM Stations";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Station station = new Station();
				station.setName(rs.getString("Name"));
				station.setCode(rs.getString("Code"));
				stationList.add(station);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stationList;
	}

}
