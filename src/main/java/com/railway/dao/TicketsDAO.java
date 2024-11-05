package com.railway.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.railway.model.Station;
import com.railway.model.Ticket;
import com.railway.model.TicketBatch;
import com.railway.model.Train;

public class TicketsDAO {
	
	
	
	public void createTicketBatch(ArrayList<Ticket> ticketList, String user) {
		try {
			int cost = 0;
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC","dekabilan","password");
			Ticket ticket = ticketList.get(0);
			Train train = ticket.getTrain();
			String query = "INSERT INTO TicketGroup (TrainID, User, Source, Destination, BookDate, TravelDate) VALUES\n"
					+ "(?,?,?,?,?,?);";
			PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1,train.getID());
			pst.setString(2,user);
			pst.setString(3,train.getSource());
			pst.setString(4,train.getDestination());
			pst.setString(5,ticket.getBookDate());
			pst.setString(6,ticket.getTravelDate());

			pst.executeUpdate(); 

			ResultSet generatedKeys = pst.getGeneratedKeys();
			int batchID = 0;
			if (generatedKeys.next()) {
			    batchID = generatedKeys.getInt(1); 
			}
			query = "INSERT INTO Tickets (GroupID,Name,Age,Email,Compartment,SeatNo) VALUES "
					+ "(?,?,?,?,?,?)";
			PreparedStatement pst1 = con.prepareStatement(query);
			for(Ticket eachTicket : ticketList) {
				pst1.setInt(1, batchID);
				pst1.setString(2,eachTicket.getName());
				pst1.setInt(3, eachTicket.getAge());
				pst1.setString(4, eachTicket.getMail());
				pst1.setString(5,eachTicket.getType());
				pst1.setString(6,eachTicket.getSeatNo());
				pst1.addBatch();
				if(eachTicket.getType().equals("ACseats")) {
					cost+=train.getACCompartmentCost();
				}
				else {
					cost+=train.getNONACCompartmentCost();
				}
				
			}
			pst1.executeBatch();
			query = "UPDATE TicketGroup SET Cost = ? WHERE GroupID = ?;";
			PreparedStatement pst2 = con.prepareStatement(query);
			pst2.setInt(1, cost);
			pst2.setInt(2,batchID);
			pst2.executeUpdate();			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public ArrayList<TicketBatch> getBatches(String username) {
		ArrayList<TicketBatch> result = new ArrayList<TicketBatch>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC", "dekabilan", "password");
			String query = "SELECT * FROM TicketGroup WHERE User  = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TicketBatch ticketbatch = new TicketBatch();
				ticketbatch.setBatchID(rs.getInt("GroupID"));
				ticketbatch.setBatchID(rs.getInt("GroupID"));
				int trainID = rs.getInt("TrainID");
				query = "SELECT TrainName as tn FROM Trains WHERE TrainID = ?";
				PreparedStatement pst2 = con.prepareStatement(query);
				pst2.setInt(1, trainID);
				ResultSet rs2 = pst2.executeQuery();
				if(rs2.next()) {
					ticketbatch.setTrain(rs2.getString("tn"));
				}
				ticketbatch.setCost(rs.getInt("Cost"));
				ticketbatch.setSource(rs.getString("Source"));
				ticketbatch.setDestination(rs.getString("Destination"));
				ticketbatch.setUser(rs.getString("User"));
				ticketbatch.setBookDate(rs.getString("BookDate"));
				ticketbatch.setTravelDate(rs.getString("TravelDate"));
				ArrayList<Integer> tickets = new ArrayList<Integer>();
				query = "SELECT * FROM Tickets WHERE GroupID  = ?;";
				PreparedStatement pst1 = con.prepareStatement(query);
				pst1.setInt(1, rs.getInt("GroupID"));
				ResultSet rs1 = pst1.executeQuery();pst2.setInt(1, trainID);
				while(rs1.next()) {
					tickets.add(rs1.getInt("TicketID"));
				}
				ticketbatch.setTickets(tickets);
				result.add(ticketbatch);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	
	
	public ArrayList<TicketBatch> getPage(String user,int pageno, int amount) {
		ArrayList<TicketBatch> result = new ArrayList<TicketBatch>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC", "dekabilan", "password");
			String query = "SELECT * FROM TicketGroup WHERE User = ? ORDER BY GroupID DESC LIMIT ? OFFSET ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user);
			ps.setInt(2,amount);
			ps.setInt(3,pageno);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TicketBatch ticketbatch = new TicketBatch();
				ticketbatch.setBatchID(rs.getInt("GroupID"));
				int trainID = rs.getInt("TrainID");
				query = "SELECT TrainName as tn FROM Trains WHERE TrainID = ?";
				PreparedStatement pst2 = con.prepareStatement(query);
				pst2.setInt(1, trainID);
				ResultSet rs2 = pst2.executeQuery();
				if(rs2.next()) {
					ticketbatch.setTrain(rs2.getString("tn"));
				}
				ticketbatch.setCost(rs.getInt("Cost"));
				ticketbatch.setSource(rs.getString("Source"));
				ticketbatch.setDestination(rs.getString("Destination"));
				ticketbatch.setUser(rs.getString("User"));
				ticketbatch.setBookDate(rs.getString("BookDate"));
				ticketbatch.setTravelDate(rs.getString("TravelDate"));
				ArrayList<Integer> tickets = new ArrayList<Integer>();
				query = "SELECT * FROM Tickets WHERE GroupID  = ?;";
				PreparedStatement pst1 = con.prepareStatement(query);
				pst1.setInt(1, rs.getInt("GroupID"));
				ResultSet rs1 = pst1.executeQuery();
				while(rs1.next()) {
					tickets.add(rs1.getInt("TicketID"));
				}
				ticketbatch.setTickets(tickets);
				result.add(ticketbatch);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	public int getAmountOfData() {
		int result = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC", "dekabilan", "password");
			String query = "SELECT COUNT(*) as count FROM TicketGroup;";
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
}
