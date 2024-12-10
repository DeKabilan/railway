package com.railway.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.railway.model.Ticket;
import com.railway.model.TicketBatch;
import com.railway.model.Train;
import com.railway.utils.DAOConnection;

public class TicketsDAO {
	DAOConnection connection = new DAOConnection();
	Connection con = connection.getConnection();
	public void createTicketBatch(ArrayList<Ticket> ticketList, String user) {
		try {
			int cost = 0;
			Ticket ticket = ticketList.get(0);
			Train train = ticket.getTrain();
			String query = "INSERT INTO TicketGroup (TrainName, User, Source, Destination, BookDate, TravelDate) VALUES\n"
					+ "(?,?,?,?,?,?);";
			PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, train.getName());
			pst.setString(2, user);
			pst.setString(3, train.getSource());
			pst.setString(4, train.getDestination());
			pst.setString(5, ticket.getBookDate());
			pst.setString(6, ticket.getTravelDate());

			pst.executeUpdate();

			ResultSet generatedKeys = pst.getGeneratedKeys();
			int batchID = 0;
			if (generatedKeys.next()) {
				batchID = generatedKeys.getInt(1);
			}
			query = "INSERT INTO Tickets (GroupID,Name,Age,Email,Compartment,SeatNo) VALUES " + "(?,?,?,?,?,?)";
			PreparedStatement pst1 = con.prepareStatement(query);
			for (Ticket eachTicket : ticketList) {
				pst1.setInt(1, batchID);
				pst1.setString(2, eachTicket.getName());
				pst1.setInt(3, eachTicket.getAge());
				if (eachTicket.getMail() == "") {
					pst1.setString(4, "Not Provided");		
				} else {
					pst1.setString(4, eachTicket.getMail());
				}
				pst1.setString(5, eachTicket.getType());
				pst1.setString(6, eachTicket.getSeatNo());
				pst1.addBatch();
				if (eachTicket.getType().equals("ACseats")) {
					cost += train.getACCompartmentCost();
				} else {
					cost += train.getNONACCompartmentCost();
				}

			}
			pst1.executeBatch();
			query = "UPDATE TicketGroup SET Cost = ? WHERE GroupID = ?;";
			PreparedStatement pst2 = con.prepareStatement(query);
			pst2.setInt(1, cost);
			pst2.setInt(2, batchID);
			pst2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void createTicket(Train train, String user, String type,String bookDate, String travelDate,ArrayList<JSONObject> passengers) {
		try {
			int cost = 0;
			String query = "INSERT INTO TicketGroup (TrainName, User, Source, Destination, BookDate, TravelDate) VALUES\n"
					+ "(?,?,?,?,?,?);";
			PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, train.getName());
			pst.setString(2, user);
			pst.setString(3, train.getSource());
			pst.setString(4, train.getDestination());
			pst.setString(5, bookDate);
			pst.setString(6, travelDate);

			pst.executeUpdate();

			ResultSet generatedKeys = pst.getGeneratedKeys();
			int batchID = 0;		
			if (generatedKeys.next()) {
				batchID = generatedKeys.getInt(1);
			}
			query = "INSERT INTO Tickets (GroupID,Name,Age,Email,Compartment,SeatNo) VALUES " + "(?,?,?,?,?,?)";
			PreparedStatement pst1 = con.prepareStatement(query);
			for (JSONObject passenger : passengers) {
				Ticket ticket = new Ticket();
				ticket.setTrain(train);
				ticket.setType(type);
				ticket.setTravelDate(travelDate);
				pst1.setInt(1, batchID);
				pst1.setString(2, (String)passenger.get("name"));
				pst1.setInt(3, Integer.parseInt((String)passenger.get("age")));
				if ((String)passenger.get("email") != null && !((String)passenger.get("email")).isEmpty()) {
					pst1.setString(4, (String)passenger.get("email"));
				} else {
					pst1.setString(4, "Not Provided");
				}
				pst1.setString(5, type);
				pst1.setString(6, train.getSeatAlgorithm().getSeatNo(ticket));
				pst1.addBatch();
				if (ticket.getType().equals("ACseats")) {
					cost += train.getACCompartmentCost();
				} else {
					cost += train.getNONACCompartmentCost();
				}

			}
			pst1.executeBatch();
			query = "UPDATE TicketGroup SET Cost = ? WHERE GroupID = ?;";
			PreparedStatement pst2 = con.prepareStatement(query);
			pst2.setInt(1, cost);
			pst2.setInt(2, batchID);
			pst2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Ticket> getTicket(TicketBatch ticketbatch) {
		try {
			TrainsDAO trainsdao = new TrainsDAO();
			ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
			for (int ticketID : ticketbatch.getTickets()) {
				String query = "SELECT * FROM Tickets WHERE TicketID = ?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, ticketID);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Ticket ticket = new Ticket();
					ticket.setTrain(trainsdao.getTrain(ticketbatch.getTrain()));
					ticket.setSeatNo(rs.getString("SeatNo"));
					ticket.setMail(rs.getString("Email"));
					ticket.setName(rs.getString("Name"));
					ticket.setAge(rs.getInt("Age"));
					switch(rs.getString("Compartment")) {
					case ("ACseats"):
						ticket.setType("AC");
						break;
					case ("NONACseats"):
						ticket.setType("NONAC");
						break;
					}
					ticket.setBookDate(ticketbatch.getBookDate());
					ticket.setTravelDate(ticketbatch.getTravelDate());
					ticketList.add(ticket);
				}
			}
			return ticketList;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	public TicketBatch getBatch(int ID) {
		TicketBatch ticketbatch = new TicketBatch();
		try {
			String query = "SELECT * FROM TicketGroup WHERE GroupID  = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, ID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ticketbatch.setBatchID(rs.getInt("GroupID"));
				ticketbatch.setUser(rs.getString("User"));
				ticketbatch.setTrain(rs.getString("TrainName"));
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
				while (rs1.next()) {
					tickets.add(rs1.getInt("TicketID"));
				}
				ticketbatch.setTickets(tickets);

			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return ticketbatch;
	}

	public ArrayList<TicketBatch> getPage(String user, int pageno, int amount) {
		ArrayList<TicketBatch> result = new ArrayList<TicketBatch>();
		try {
			String query = "SELECT * FROM TicketGroup WHERE User = ? ORDER BY GroupID DESC LIMIT ? OFFSET ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user);
			ps.setInt(2, amount);
			ps.setInt(3, pageno);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TicketBatch ticketbatch = new TicketBatch();
				ticketbatch.setBatchID(rs.getInt("GroupID"));
				ticketbatch.setTrain(rs.getString("TrainName"));
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
				while (rs1.next()) {
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
	
	
	public ArrayList<TicketBatch> getTicketBatch(String user) {
		ArrayList<TicketBatch> result = new ArrayList<TicketBatch>();
		try {
			String query = "SELECT * FROM TicketGroup WHERE User = ? ORDER BY GroupID DESC;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TicketBatch ticketbatch = new TicketBatch();
				ticketbatch.setBatchID(rs.getInt("GroupID"));
				ticketbatch.setTrain(rs.getString("TrainName"));
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
				while (rs1.next()) {
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

	
	
	public int getAmountOfData(String user) {
		int result = 0;
		try {
			String query = "SELECT COUNT(*) as count FROM TicketGroup WHERE User = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user);
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
