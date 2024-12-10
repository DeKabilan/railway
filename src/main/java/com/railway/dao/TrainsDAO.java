package com.railway.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.railway.model.Station;
import com.railway.model.Train;
import com.railway.utils.DAOConnection;
import com.railway.utils.JSONFormatter;

public class TrainsDAO {
	DAOConnection connection = new DAOConnection();
	Connection con = connection.getConnection();
	//------------Get Train as Model from DB------------
	
	public Boolean isTrainExist(String Name) {
		try {
			String query = "SELECT * FROM Trains WHERE TrainName = ?";
			PreparedStatement pst1 = con.prepareStatement(query);
			pst1.setString(1, Name);
			ResultSet rs = pst1.executeQuery();
			if(rs.next()) {
				return true; 
			}
			return false;
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public int getAmountOfData() {
		int result = 0;
		try {
			String query = "SELECT COUNT(*) as count FROM Trains;";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("count");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	public ArrayList<Train> getPage(int pageno, int amount){
		ArrayList<Train> result = new ArrayList<Train>();
		try {
			String query = "SELECT TrainName FROM Trains ORDER BY TrainName LIMIT ? OFFSET ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1,amount);
			ps.setInt(2, pageno);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Train train = this.getTrain(rs.getString("TrainName"));
				result.add(train);				
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	
	public ArrayList<Train> getAllTrains(){
		ArrayList<Train> trainList = new ArrayList<Train>();
		try {
			String query = "SELECT TrainName from Trains;";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				trainList.add(this.getTrain(rs.getString("TrainName")));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return trainList;
	}
	
	public Train getTrain(String Name) {
		Train TrainFromDB = new Train();
		try{
			int TrainID = -1;
			int RouteID = -1;
			String query = "SELECT * FROM Trains WHERE TrainName = ?";
			PreparedStatement pst1 = con.prepareStatement(query);
			pst1.setString(1, Name);
			ResultSet rs = pst1.executeQuery();
			if(rs.next()) {
				TrainID = rs.getInt("TrainID");
				TrainFromDB.setName(rs.getString("TrainName"));
				TrainFromDB.setID(rs.getInt("TrainID"));
				TrainFromDB.setDeparture(rs.getString("Departure"));
				TrainFromDB.setArrival(rs.getString("Arrival"));
				TrainFromDB.setSeatAlgorithm((String)rs.getString("SeatAlgorithm"));

			}
			else {
				return new Train();
			}
			query = "SELECT * FROM Routes WHERE TrainID = ?";
			PreparedStatement pst2 = con.prepareStatement(query);
			pst2.setInt(1, TrainID);
			ResultSet rs1 = pst2.executeQuery();
			
			if(rs1.next()) {
				RouteID = rs1.getInt("RouteID");
				TrainFromDB.setSource(rs1.getString("Source"));
				TrainFromDB.setDestination(rs1.getString("Destination"));
			}
			query = "SELECT * FROM Stops WHERE RouteID = ?";
			PreparedStatement pst3 = con.prepareStatement(query);
			pst3.setInt(1, RouteID);
			ResultSet rs2 = pst3.executeQuery();
			ArrayList<String> intermediate = new ArrayList<String>();
			while(rs2.next()) {
				intermediate.add(rs2.getString("StopCode"));
			}
			TrainFromDB.setIntermediate(intermediate);
			
			query = "SELECT * FROM Periodicity WHERE TrainID = ?";
			PreparedStatement pst4 = con.prepareStatement(query);
			pst4.setInt(1, TrainID);
			ResultSet rs3 = pst4.executeQuery();
			ArrayList<String> periodicity = new ArrayList<String>();
			while(rs3.next()) {
				periodicity.add(rs3.getString("Day"));
			}
			TrainFromDB.setPeriodicity(periodicity);
			
			query = "SELECT * FROM Compartments WHERE TrainID = ?";
			PreparedStatement pst5 = con.prepareStatement(query);
			pst5.setInt(1, TrainID);
			ResultSet rs4 = pst5.executeQuery();
			while(rs4.next()) {
				if(rs4.getString("Type").equals("AC")) {
					TrainFromDB.setACCompartmentCost(rs4.getInt("Cost"));
					TrainFromDB.setACCompartmentNo(rs4.getInt("NoOfCompartments"));
					TrainFromDB.setACCompartmentSeats(rs4.getInt("NoOfSeats"));
				}
				else if(rs4.getString("Type").equals("NONAC")) {
					TrainFromDB.setNONACCompartmentCost(rs4.getInt("Cost"));
					TrainFromDB.setNONACCompartmentNo(rs4.getInt("NoOfCompartments"));
					TrainFromDB.setNONACCompartmentSeats(rs4.getInt("NoOfSeats"));
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		return TrainFromDB;
	}
	
	
    public void JSONtoDB(String path) {
    	JSONFormatter jsondec = new JSONFormatter();
    	for(Train trains : jsondec.trainsToArray(path)) {
    		this.createTrain(trains);
    	}
    }
  
//    
   public Integer getRemainingSeats(String trainName, String compartment) {
	   try {
		   String query = "SELECT * FROM Seats JOIN Trains ON Seats.TrainID = Trains.TrainID WHERE TrainName = ?;";
		   PreparedStatement pst = con.prepareStatement(query);
		   pst.setString(1, trainName);
		   ResultSet rs = pst.executeQuery();
		   if(rs.next()) {
			  return rs.getInt(compartment);
		   }
		   else return 0;
	   }
	   catch(Exception e) {
		   System.out.println(e);
	   }
	return null;
   }
    
   //---------Search Train-------------
    
    public ArrayList<Train> searchTrain(String source, String destination, String dateString, String today, int hour){
    	if(dateString == null || dateString.equals("")) {
    		return new ArrayList<Train>();
    	}
    	ArrayList<Train> result = new ArrayList<Train>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        String dayOfWeek = date.getDayOfWeek().name().substring(0,3);
    	try {
    		 StringBuilder query = new StringBuilder(
    		            "SELECT DISTINCT TrainName, Departure, Arrival FROM (" +
    		            "    WITH combination AS (" +
    		            "        SELECT Trains.TrainID, TrainName, Routes.RouteID, Source, Destination, Arrival, Departure " +
    		            "        FROM Trains " +
    		            "        JOIN Routes ON Trains.TrainID = Routes.TrainID" +
    		            "    ) " +
    		            "    SELECT DISTINCT TrainName, combination.RouteID, Source, Destination, Isource, Idestination, Departure, Arrival " +
    		            "    FROM combination " +
    		            "    JOIN (" +
    		            "        SELECT s1.StopCode AS Isource, s1.RouteID, s2.StopCode AS Idestination " +
    		            "        FROM Stops AS s1 " +
    		            "        JOIN Stops s2 ON s1.RouteID = s2.RouteID " +
    		            "        WHERE s1.Sequence <= s2.Sequence" +
    		            "    ) AS intermediate " +
    		            "    ON combination.RouteID = intermediate.RouteID" +
    		            ") result " +
    		            "WHERE " +
    		            "((Source = ? AND Destination = ?) OR " +
    		            "(Source = ? AND Isource = ?) OR " +
    		            "(Source = ? AND Idestination = ?) OR " +
    		            "(Isource = ? AND Idestination = ?) OR " +
    		            "(Isource = ? AND Destination = ?))"
    		        );			
    		 if(dateString.equals(today)) {
				if(hour>6) {
					query.append(" AND Departure>\"06:00\"");
				}
				else if(hour>12) {
					query.append(" AND Departure>\"12:00\"");
				}
				else if(hour>16) {
					query.append(" AND Departure>\"16:00\"");
				}
			}
    		
			PreparedStatement ps = con.prepareStatement(query.toString());
    		ps.setString(1, source);
    		ps.setString(2, destination);
    		ps.setString(3, source);
    		ps.setString(4, destination);
    		ps.setString(5, source);
    		ps.setString(6, destination);
    		ps.setString(7, source);
    		ps.setString(8, destination);
    		ps.setString(9, source);
    		ps.setString(10, destination);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			Train train = this.getTrain(rs.getString("TrainName"));
    			for(String days : train.getPeriodicity()) {
    				if(days.equals(dayOfWeek)) {
    					result.add(train);
    				}
    			}
    		}    		
    		
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    	return result;
    }
    
    
	//----------------Insert Train from Model into DB----------
	public void createTrain(Train TrainObject) {
		try {
			con.setAutoCommit(false);
			String query = "INSERT INTO Trains (TrainName, Departure, Arrival, SeatAlgorithm) VALUES (?,?,?,?)";
			PreparedStatement pst1 = con.prepareStatement(query);
			pst1.setString(1, TrainObject.getName());
			pst1.setString(2, TrainObject.getDeparture());
			pst1.setString(3, TrainObject.getArrival());
			pst1.setString(4, TrainObject.getSeatAlgorithm().name());
			pst1.execute();
			query = "SELECT TrainID FROM Trains WHERE TrainName = ?";
			PreparedStatement pst2 = con.prepareStatement(query);
			pst2.setString(1, TrainObject.getName());
			ResultSet rs = pst2.executeQuery();
			rs.next();
			int TrainID = rs.getInt("TrainID");
			query = "INSERT INTO Routes (TrainID,Source,Destination) VALUES (?,?,?);";
			PreparedStatement pst3 = con.prepareStatement(query);
			pst3.setInt(1, TrainID);
			pst3.setString(2, TrainObject.getSource());
			pst3.setString(3, TrainObject.getDestination());
			pst3.execute();
			query = "SELECT RouteID FROM Routes WHERE TrainID = ?";
			PreparedStatement pst4 = con.prepareStatement(query);
			pst4.setInt(1, TrainID);
			ResultSet rs1 = pst4.executeQuery();
			rs1.next();
			int RouteID = rs1.getInt("RouteID");
			query = "INSERT INTO Stops (StopCode, RouteID, Sequence) VALUES (?,?,?);";
			ArrayList<String> intermediate = TrainObject.getIntermediate();
			PreparedStatement pst5 = con.prepareStatement(query);
			int i=1;
			for(String code:intermediate) {
				pst5.setString(1, code);
				pst5.setInt(2, RouteID);
				pst5.setInt(3, i);
				pst5.addBatch();
				i++;
			}
			pst5.executeBatch();
			
			query = "INSERT INTO Periodicity (TrainID, Day) VALUES (?,?);";
			ArrayList<String> period = TrainObject.getPeriodicity();
			PreparedStatement pst6 = con.prepareStatement(query);
			for(String day:period) {
				pst6.setString(2, day);
				pst6.setInt(1, TrainID);
				pst6.addBatch();
			}
			pst6.executeBatch();
			
			query = "INSERT INTO Compartments (TrainID, Type, NoOfCompartments, NoOfSeats, Cost) VALUES (?,?,?,?,?);";
			PreparedStatement pst7 = con.prepareStatement(query);
			pst7.setInt(1, TrainID);
			pst7.setString(2, "AC");
			pst7.setInt(3, TrainObject.getACCompartmentNo());
			pst7.setInt(4, TrainObject.getACCompartmentSeats());
			pst7.setInt(5, TrainObject.getACCompartmentCost());
			pst7.addBatch();
			pst7.setInt(1, TrainID);
			pst7.setString(2, "NONAC");
			pst7.setInt(3, TrainObject.getNONACCompartmentNo());
			pst7.setInt(4, TrainObject.getNONACCompartmentSeats());
			pst7.setInt(5, TrainObject.getNONACCompartmentCost());
			pst7.addBatch();
			pst7.executeBatch();

			
			con.commit();
			
		}
		catch(Exception e) {
			System.out.println(e);
			 if (con != null) {
				 
	                try {
	                    con.rollback();
	                } 
	                catch (Exception rollbackEx) {
	                    rollbackEx.printStackTrace();
	                    System.out.println(e);
	                }
	            }
			}
		}
	
	
	
	//-------------Delete Train from DB-------------
	
	public void deleteTrain(String TrainName) {
		
	        try {
	            con.setAutoCommit(false); 

	            String query = "DELETE FROM Compartments WHERE TrainID = (SELECT TrainID FROM Trains WHERE TrainName = ?)";
	            try (PreparedStatement pst1 = con.prepareStatement(query)) {
	                pst1.setString(1, TrainName);
	                pst1.executeUpdate();
	            }

	            query = "DELETE FROM Stops WHERE RouteID = (SELECT RouteID FROM Routes WHERE TrainID = (SELECT TrainID FROM Trains WHERE TrainName = ?))";
	            try (PreparedStatement pst2 = con.prepareStatement(query)) {
	                pst2.setString(1, TrainName);
	                pst2.executeUpdate();
	            }

	            query = "DELETE FROM Periodicity WHERE TrainID = (SELECT TrainID FROM Trains WHERE TrainName = ?)";
	            try (PreparedStatement pst3 = con.prepareStatement(query)) {
	                pst3.setString(1, TrainName);
	                pst3.executeUpdate();
	            }

	            query = "DELETE FROM Routes WHERE TrainID = (SELECT TrainID FROM Trains WHERE TrainName = ?)";
	            try (PreparedStatement pst4 = con.prepareStatement(query)) {
	                pst4.setString(1, TrainName);
	                pst4.executeUpdate();
	            }

	            query = "DELETE FROM Trains WHERE TrainName = ?";
	            try (PreparedStatement pst5 = con.prepareStatement(query)) {
	                pst5.setString(1, TrainName);
	                pst5.executeUpdate();
	            }

	            con.commit();
	        } catch (Exception e) {
	            System.out.println("SQL Exception: " + e.getMessage());
	            try {
	                con.rollback();
	            } 
	            catch (Exception rollbackEx) {
	                System.out.println("Rollback Exception: " + rollbackEx.getMessage());
	                }
	            }
	        }
	
	
	
	//-------------Update Train using TrainName from DB with Train Model----------
	
	public void updateTrain(String TrainName, Train TrainObject) {
	    try {
	        con.setAutoCommit(false);
	        String query = "SELECT TrainID FROM Trains WHERE TrainName = ?";
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setString(1,TrainName);
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	        int TrainID = rs.getInt("TrainID");
	        
	        query = "DELETE FROM Periodicity WHERE TrainID = ?";
	        PreparedStatement pst5 = con.prepareStatement(query);
	        pst5.setInt(1, TrainID);
	        pst5.executeUpdate();
	        
	        query = "INSERT INTO Periodicity (TrainID, Day) VALUES (?, ?)";
	        ArrayList<String> period = TrainObject.getPeriodicity();
	        PreparedStatement pst6 = con.prepareStatement(query);
	        	for (String day : period) {
	        		pst6.setInt(1, TrainID);
	        		pst6.setString(2, day);
	        		pst6.addBatch();
	        	}
	        	pst6.executeBatch();
	        
	        query = "UPDATE Compartments SET NoOfCompartments = ?, NoOfSeats = ?, Cost = ? WHERE TrainID = ? AND Type = ?";
	        PreparedStatement pst7 = con.prepareStatement(query);
	        	pst7.setInt(1, TrainObject.getACCompartmentNo());
	        	pst7.setInt(2, TrainObject.getACCompartmentSeats());
	        	pst7.setInt(3, TrainObject.getACCompartmentCost());
	        	pst7.setInt(4, TrainID);
	        	pst7.setString(5, "AC");
	        	pst7.executeUpdate();
	        	
	        	pst7.setInt(1, TrainObject.getNONACCompartmentNo());
	        	pst7.setInt(2, TrainObject.getNONACCompartmentSeats());
	        	pst7.setInt(3, TrainObject.getNONACCompartmentCost());
	        	pst7.setInt(4, TrainID);
	        	pst7.setString(5, "NONAC");
	        	pst7.executeUpdate();
	        
	        
	        query = "DELETE FROM Stops WHERE RouteID = (SELECT RouteID FROM Routes WHERE TrainID = ?)";
	        PreparedStatement pst3 = con.prepareStatement(query);
	        	pst3.setInt(1, TrainID);
	        	pst3.executeUpdate();
	        query = "INSERT INTO Stops (StopCode, RouteID) VALUES (?, (SELECT RouteID FROM Routes WHERE TrainID = ?))";
	        ArrayList<String> intermediate = TrainObject.getIntermediate();
	       PreparedStatement pst4 = con.prepareStatement(query);
	        	for (String code : intermediate) {
	        		pst4.setString(1, code);
	        		pst4.setInt(2, TrainID);
	        		pst4.addBatch();
	        	}
	        	pst4.executeBatch();
	        
	        query = "UPDATE Routes SET Source = ?, Destination = ? WHERE TrainID = ?";
	        PreparedStatement pst2 = con.prepareStatement(query);
	        	pst2.setString(1, TrainObject.getSource());
	        	pst2.setString(2, TrainObject.getDestination());
	        	pst2.setInt(3, TrainID);
	        	pst2.executeUpdate();
	        
	        query = "UPDATE Trains SET Departure = ?, Arrival = ?, SeatAlgorithm = ?, TrainName = ? WHERE TrainID = ?";
	        PreparedStatement pst1 = con.prepareStatement(query);
	            pst1.setString(1, TrainObject.getDeparture());
	            pst1.setString(2, TrainObject.getArrival());
	            pst1.setString(3, TrainObject.getSeatAlgorithm().name());
	            pst1.setString(4, TrainObject.getName());
	            pst1.setInt(5, TrainID);
	            pst1.executeUpdate();


	        con.commit();
	    } catch (Exception e) {
	        if (con != null) {
	            try {
	            	System.out.println("rollback");
	                con.rollback();
	            } catch (Exception rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	    }
	}
	
	public int getSeats(String compartment, String trainName, String day) {
		int result = 0;
		try {
	       String query = "SELECT * FROM Seats WHERE TrainID = (SELECT TrainID FROM Trains WHERE TrainName = ? and Day = ?)";
	       PreparedStatement pst = con.prepareStatement(query);
	       pst.setString(1, trainName);
	       pst.setString(2, day);
	       ResultSet rs = pst.executeQuery();
	       if(rs.next()) {
	    	   result = rs.getInt(compartment);  
	       }
	       return result;
	       }
		catch(Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	
	public void updateSeats(String compartment, String trainName, int newSeats, String day) {
		try {
	       String query = "UPDATE Seats\n"
	       		+ "SET "+compartment+" = "+newSeats+"\n"
	       		+ "WHERE TrainID = (SELECT TrainID FROM Trains WHERE TrainName = ?) and Day = ?;";
	       PreparedStatement pst = con.prepareStatement(query);
	       pst.setString(1, trainName);
	       pst.setString(2, day);
	       pst.executeUpdate();
	       }
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	public ArrayList<Train> additionalFilters(String source, String destination, String departure, String arrival, String compartment, String dateString, String today, int hour) {
		ArrayList<Train> result = new ArrayList<>();
		ArrayList<Train> finalResult = new ArrayList<>();

        try {
        	StringBuilder query = new StringBuilder("SELECT DISTINCT TrainName, Departure, Arrival FROM\n"
        			+ "(WITH combination as (SELECT Trains.TrainID , TrainName, Routes.RouteID, Source, Destination, Arrival, Departure FROM Trains Join Routes on Trains.TrainID = Routes.TrainID)\n"
        			+ "SELECT TrainName, combination.RouteID, Source, Destination,Isource,Idestination, Departure, Arrival FROM combination JOIN \n"
        			+ "(SELECT s1.Stopcode as Isource, s1.RouteID, s2.StopCode as Idestination FROM Stops as s1 JOIN Stops s2 ON s1.RouteID = s2.RouteID WHERE s1.Sequence <= s2.Sequence) as intermediate\n"
        			+ "ON combination.RouteID = intermediate.RouteID) result \n"
        			+ "WHERE ((Source = ? and Destination = ?) or (Source = ? and Isource = ? ) or (Source = ? and Idestination = ?) or (Isource = ? and Idestination = ?) or (Isource = ? and Destination = ?))");
        	
        	if(dateString != null && !dateString.isEmpty() && dateString.equals(today)) {
        	
				if(hour>=6) {
					query.append(" AND Departure>\"0600\"");
				}
				else if(hour>=12) {
					query.append(" AND Departure>\"1200\"");
				}
				else if(hour>=16) {
					query.append(" AND Departure>\"1600\"");
				}
			}
        	if (departure != null && !departure.isEmpty()) {
        		switch (departure.toLowerCase()) {
        		case "morning":
        			query.append(" AND (Departure>=\"06:00\" AND Departure <\"12:00\")");
        			break;
        		case "afternoon":
        			query.append(" AND (Departure>=\"12:00\" AND Departure <\"18:00\")");
        			break;
        		case "evening":
        			query.append(" AND (Departure>=\"18:00\" AND Departure <\"24:00\")");
        			break;
        		case "night":
        			query.append(" AND (Departure>=\"00:00\" AND Departure <\"06:00\")");
        			break;
        		default:
        			break;
        		}
        	}
        	
        	
        	if (arrival != null && !arrival.isEmpty()) {
        		switch (arrival.toLowerCase()) {
        		case "morning":
        			query.append(" AND (Arrival>=\"06:00\" AND Arrival <\"12:00\")");
        			break;
        		case "afternoon":
        			query.append(" AND (Arrival>=\"12:00\" AND Arrival <\"18:00\")");
        			break;
        		case "evening":
        			query.append(" AND (Arrival>=\"18:00\" AND Arrival <\"24:00\")");
        			break;
        		case "night":
        			query.append(" AND (Arrival>=\"00:00\" AND Arrival <\"06:00\")");
        			break;
        		default:
        			break;
        		}
        		
        	}
        	
        	PreparedStatement pst = con.prepareStatement(query.toString());
    		pst.setString(1, source);
    		pst.setString(2, destination);
    		pst.setString(3, source);
    		pst.setString(4, destination);
    		pst.setString(5, source);
    		pst.setString(6, destination);
    		pst.setString(7, source);
    		pst.setString(8, destination);
    		pst.setString(9, source);
    		pst.setString(10, destination);
        	ResultSet rs = pst.executeQuery();
        	if (compartment != null && !compartment.isEmpty()) {
        		while(rs.next()) {
        			Train train = this.getTrain(rs.getString("TrainName"));
        			switch (compartment) {
    				case "ACseats":
    					if(train.getACCompartmentNo()!=0) {
    						result.add(train);
    						}
    						
    					break;
    				case "NONACseats":
    					if(train.getNONACCompartmentNo()!=0) {
    						result.add(train);
    					}
    					break;
    				default:
    					break;
    				}
        			
        		}
        		if(dateString != null && !dateString.isEmpty()) {
        	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	        LocalDate date = LocalDate.parse(dateString, formatter);
        	        String dayOfWeek = date.getDayOfWeek().name().substring(0,3);
        			for(Train train: result) {
        				for(String days: train.getPeriodicity()) {
        					if(days.equals(dayOfWeek)) {
        						finalResult.add(train);
        					}
        				}
        			}
        			return finalResult;
        		}
        		return result;
        		
        		
        	}      	
        while(rs.next()) {
        	Train train = this.getTrain(rs.getString("TrainName"));
			result.add(train);
        }
        if(dateString != null && !dateString.isEmpty()) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate date = LocalDate.parse(dateString, formatter);
	        String dayOfWeek = date.getDayOfWeek().name().substring(0,3);
			for(Train train: result) {
				for(String days: train.getPeriodicity()) {
					if(days.equals(dayOfWeek)) {
						finalResult.add(train);
					}
				}
			}
			return finalResult;
		}
        return result;
        }
		catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	
	public Boolean ifSeatDayExist(String day,String TrainName) throws SQLException {
		String query = "SELECT * FROM Seats WHERE Day = ? and TrainID = (SELECT TrainID from Trains WHERE TrainName = ?)" ;
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1, day);
		pst.setString(2, TrainName);
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			return true;
		}
		return false;
	}
	public void createSeat(String day, int ACseats, int NONACseats, String TrainName) throws SQLException {
			String query = "INSERT INTO Seats (TrainID, Day, ACseats, NONACseats) VALUES ((SELECT TrainID from Trains WHERE TrainName = ?),?,?,?);";
			PreparedStatement pst8 = con.prepareStatement(query);
			pst8.setString(1, TrainName);
			pst8.setString(2, day);
			pst8.setInt(3, ACseats);
			pst8.setInt(4, NONACseats);
			pst8.execute();
			
		}
	
	public int getAmountOfDataSearch(String trainName) {
		int result = 0;
		try {
			String query = "SELECT COUNT(*) as count FROM Trains WHERE TrainName LIKE ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "%"+trainName+"%");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt("count");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	public ArrayList<Train> searchTrain(String trainName, int pageNumber, int amount) {
	    ArrayList<Train> trainList = new ArrayList<Train>();
	    String query = "SELECT * FROM Trains WHERE TrainName LIKE ? ORDER BY TrainName LIMIT ? OFFSET ?";
	    
	    try {
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setString(1, "%" + trainName + "%"); 
	        ps.setInt(2, amount);
	        ps.setInt(3,pageNumber);

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	
	            trainList.add(this.getTrain(rs.getString("trainName")));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return trainList;
	    
	}
}
	

	