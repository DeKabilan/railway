package com.railway.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.railway.handlers.StationHandler;
import com.railway.handlers.TrainHandler;
import com.railway.model.Station;
import com.railway.model.Train;
import com.railway.utils.CustomExceptions;
import com.railway.utils.RequestBodyExtracter;

@WebServlet({ "/admin", "/station", "/train" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StationHandler stationhandler = new StationHandler();
	TrainHandler trainhandler = new TrainHandler();

	protected void doGet(HttpServletRequest request, HttpServletResponse response){

		try {
			try {
				HttpSession session = request.getSession();
				if(session.getAttribute("userRole")==null){
					throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);					
				}
				Train train = new Train();
				Station station = new Station();
				session.setAttribute("Train", train);
				session.setAttribute("Station", station);
				String path = request.getServletPath();
				String role = (String) session.getAttribute("userRole");
				if (role.equals("admin")) {

					if (path.equals("/admin")) {
						RequestDispatcher rd = request.getRequestDispatcher("adminStations.jsp");
						rd.forward(request, response);
						return;
					}
					else if (path.equals("/train")) {
						RequestDispatcher rd = request.getRequestDispatcher("adminTrains.jsp");
						rd.forward(request, response);
						return;
					}

					else if (path.equals("/station")) {
						RequestDispatcher rd = request.getRequestDispatcher("adminStations.jsp");
						rd.forward(request, response);
						return;
					}
				}
			} catch (CustomExceptions ce) {
				RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
				rd.forward(request, response);
				return;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {
			try {
		        if (!"admin".equals(session.getAttribute("userRole"))) {
		            throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
		        }
		        JSONObject json = new RequestBodyExtracter().extract(request);
		        String type = (String)json.get("type");
		        if(type.equals("station")) {
		        	String stcodec = (String)json.get("stcodec");
			        String stnamec = (String)json.get("stnamec");
			        Station station = stationhandler.handleCreate(stnamec, stcodec);
			        session.setAttribute("Station", station);
			        response.getWriter().write("<br>"+station.getMessage());
		        }
		        else if(type.equals("train")){

		            String tname = (String) json.get("name");
		            String algorithm = (String) json.get("algorithm");
		            String source = (String) json.get("source");
		            String destination = (String) json.get("destination");
		            String strDeparture = (String) json.get("departure");
		            String strArrival = (String) json.get("arrival");

		            ArrayList<String> periodicity = (ArrayList<String>)(JSONArray)json.get("periodicity");

		            String intermediate = (String) json.get("intermediate");

		            String acNo = (String) json.get("acno");
		            String acSeats = (String) json.get("acseat");
		            String acCost = (String) json.get("accost");
		            String nonAcNo = (String) json.get("nonacno");
		            String nonAcSeats = (String) json.get("nonacseat");
		            String nonAcCost = (String) json.get("nonaccost");
		            
		            Train train = trainhandler.handleCreate(tname, algorithm, source, destination, strDeparture, strArrival, periodicity, intermediate, 
		            		Integer.parseInt(acNo), Integer.parseInt(acSeats), Integer.parseInt(acCost), Integer.parseInt(nonAcNo), Integer.parseInt(nonAcSeats), Integer.parseInt(nonAcCost));
		            session.setAttribute("Train", train);
		            response.getWriter().write("<br>"+train.getText());
		        }
				
			} catch (CustomExceptions ce) {
				RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
				rd.forward(request, response);
				return;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    HttpSession session = request.getSession();

	    try {
	        if (!"admin".equals(session.getAttribute("userRole"))) {
	            throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
	        }

	        JSONObject json = new RequestBodyExtracter().extract(request);
	        String type = (String)json.get("type");
	        if(type.equals("station")) {
	        	String stcodeold = (String)json.get("stcodeold");
		        String stcodenew = (String)json.get("stcodenew");
		        String stnamenew = (String)json.get("stnamenew");
		        Station station = stationhandler.handleUpdate(stcodeold, stcodenew, stnamenew);
		        session.setAttribute("Station", station);
		        response.getWriter().write("<br>"+station.getMessage());
	        	
	        }
	        
	        else if(type.equals("train")) {
	        	String toldname = (String) json.get("oldname");
	            String tname = (String) json.get("name");
	            String algorithm = (String) json.get("algorithm");
	            String source = (String) json.get("source");
	            String destination = (String) json.get("destination");
	            String strDeparture = (String) json.get("departure");
	            String strArrival = (String) json.get("arrival");

	            ArrayList<String> periodicity = (ArrayList<String>)(JSONArray)json.get("periodicity");

	            String intermediate = (String) json.get("intermediate");

	            String acNo = (String) json.get("acno");
	            String acSeats = (String) json.get("acseat");
	            String acCost = (String) json.get("accost");
	            String nonAcNo = (String) json.get("nonacno");
	            String nonAcSeats = (String) json.get("nonacseat");
	            String nonAcCost = (String) json.get("nonaccost");
	            Train train = trainhandler.handleUpdate(toldname, tname, algorithm, source, destination, strDeparture, strArrival, periodicity, intermediate, 
	            		acNo, acSeats, acCost, nonAcNo, nonAcSeats, nonAcCost);
	            session.setAttribute("Train", train);
	            response.getWriter().write("<br>"+train.getText());
	            return;
	        }


	    } catch (Exception e) {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Error occurred: " + e.getMessage());
	    }
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    HttpSession session = request.getSession();

	    try {
	        if (!"admin".equals(session.getAttribute("userRole"))) {
	            throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
	        }

	        JSONObject json = new RequestBodyExtracter().extract(request);
	        String type = (String)json.get("type");
	        if(type.equals("station")) {
		        String stcode = (String)json.get("stcoded");
		        Station station = stationhandler.handleDelete(stcode);
		        session.setAttribute("Station", station);
		        response.getWriter().write("<br>"+station.getMessage());
	        	
	        }
	        else if(type.equals("train")) {
	        	String tnamed = (String)json.get("tnamed");
		        Train train = trainhandler.handleDelete(tnamed);
		        session.setAttribute("Train", train);
		        response.getWriter().write("<br>"+train.getText());
	        	
	        	
	        }


	    } catch (Exception e) {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Error occurred: " + e.getMessage());
	    }
	}


}
