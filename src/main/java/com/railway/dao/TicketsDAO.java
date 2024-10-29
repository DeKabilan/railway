//package com.railway.dao;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
//
//import com.railway.model.Ticket;
//
//public class TicketsDAO {
//	
//	
//	
//	public void createTicketBatch(ArrayList<Ticket> ticketList, String user, String compartment) {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRTC","dekabilan","password");
//			Ticket ticket = ticketList.get(0);
//			ticket.getTrainName();
//			String query = "INSERT INTO TicketGroup (TrainID, User, Cost, Source, Destination, BookDate, TravelDate)\n"
//					+ "SELECT \n"
//					+ "    t.TrainID,\n"
//					+ "    ?,\n"
//					+ "    c.Cost * ?,\n"
//					+ "    ?,\n"
//					+ "    ?,\n"
//					+ "    ?,\n"
//					+ "    ?\n"
//					+ "FROM \n"
//					+ "    Trains t\n"
//					+ "JOIN \n"
//					+ "    Compartments c ON c.TrainName = t.TrainName AND c.Type = ?\n"
//					+ "WHERE \n"
//					+ "    t.TrainName = ?;";
//			PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//			pst.setString(1,user);
//			pst.setInt(2,ticketList.size());
//			pst.setString(3, ticket.getSource());
//			pst.setString(4, ticket.getDestination());
//			pst.setString(5, ticket.getBookDate());
//			pst.setString(6, ticket.getTravelDate());
//			pst.setString(7, compartment);
//			pst.setString(8,ticket.getTrainName());
//			
//			pst.executeUpdate(); 
//
//			ResultSet generatedKeys = pst.getGeneratedKeys();
//			int batchID = 0;
//			if (generatedKeys.next()) {
//			    batchID = generatedKeys.getInt(1); 
//			}
//			System.out.println(batchID);
//			
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//}
