package com.railway.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {
			try {
				HttpSession session = request.getSession();
				if (!"admin".equals((String) session.getAttribute("userRole"))) {
					throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
				}
				Train train = new Train();
				Station station = new Station();
				session.setAttribute("Train", train);
				session.setAttribute("Station", station);
				String path = request.getServletPath();
				if (path.equals("/admin") || path.equals("/station")) {
					RequestDispatcher rd = request.getRequestDispatcher("adminStations.jsp");
					rd.forward(request, response);
					return;
				} else if (path.equals("/train")) {
					RequestDispatcher rd = request.getRequestDispatcher("adminTrains.jsp");
					rd.forward(request, response);
					return;
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
				if (!"admin".equals((String)session.getAttribute("userRole"))) {
					RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
					rd.forward(request, response);
				}
				JSONObject json = new RequestBodyExtracter().extract(request);
				String type = (String) json.get("type");
				if (type.equals("station")) {
					String stcodec = (String) json.get("stcodec");
					String stnamec = (String) json.get("stnamec");
					Station station = stationhandler.handleCreate(stnamec, stcodec);
					session.setAttribute("Station", station);
					response.getWriter().write("<br>" + station.getMessage());
				} else if (type.equals("train")) {
					Train train = new Train();
					train.setName((String) json.get("name"));
					train.setSeatAlgorithm((String) json.get("algorithm"));
					train.setSource((String) json.get("source"));
					train.setDestination((String) json.get("destination"));
					train.setDeparture((String) json.get("departure"));
					train.setArrival((String) json.get("arrival"));
					train.setPeriodicity((ArrayList<String>) (JSONArray) json.get("periodicity"));
					train.setIntermediate(new ArrayList<String>(Arrays.asList(((String) json.get("intermediate")).split(","))));
					train.setACCompartmentNo(Integer.parseInt((String) json.get("acno")));
					train.setACCompartmentSeats(Integer.parseInt((String) json.get("acseat")));
					train.setACCompartmentCost(Integer.parseInt((String) json.get("accost")));
					train.setNONACCompartmentNo(Integer.parseInt((String) json.get("nonacno")));
					train.setNONACCompartmentSeats(Integer.parseInt((String) json.get("nonacseat")));
					train.setNONACCompartmentCost(Integer.parseInt((String) json.get("nonaccost")));

					trainhandler.handleCreate(train);
					response.getWriter().write("<br>" + "Train Created Successfully");
				}

			} catch (CustomExceptions ce) {
				response.getWriter().write("<br>" + ce.getMessage());
				return;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		try {
			if (!"admin".equals((String)session.getAttribute("userRole"))) {
				RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
				rd.forward(request, response);
			}
			JSONObject json = new RequestBodyExtracter().extract(request);
			String type = (String) json.get("type");
			if (type.equals("station")) {
				String stcodeold = (String) json.get("stcodeold");
				String stcodenew = (String) json.get("stcodenew");
				String stnamenew = (String) json.get("stnamenew");
				Station station = stationhandler.handleUpdate(stcodeold, stcodenew, stnamenew);
				session.setAttribute("Station", station);
				response.getWriter().write("<br>" + station.getMessage());
			} else if (type.equals("train")) {

				String toldname = (String) json.get("oldname");
				Train train = new Train();
				train.setName((String) json.get("name"));
				train.setSeatAlgorithm((String) json.get("algorithm"));
				train.setSource((String) json.get("source"));
				train.setDestination((String) json.get("destination"));
				train.setDeparture((String) json.get("departure"));
				train.setArrival((String) json.get("arrival"));
				train.setPeriodicity((ArrayList<String>) (JSONArray) json.get("periodicity"));
				train.setIntermediate(new ArrayList<String>());
				if(json.get("intermediate")!="") {
					train.setIntermediate(new ArrayList<String>(Arrays.asList(((String) json.get("intermediate")).split(","))));
				}
				train.setACCompartmentNo(Integer.parseInt((String) json.get("acno")==""? "-1":(String) json.get("acno")));
				train.setACCompartmentSeats(Integer.parseInt((String) json.get("acseat")==""? "-1":(String) json.get("acno")));
				train.setACCompartmentCost(Integer.parseInt((String) json.get("accost")==""? "-1":(String) json.get("acno")));
				train.setNONACCompartmentNo(Integer.parseInt((String) json.get("nonacno")==""? "-1":(String) json.get("acno")));
				train.setNONACCompartmentSeats(Integer.parseInt((String) json.get("nonacseat")==""? "-1":(String) json.get("acno")));
				train.setNONACCompartmentCost(Integer.parseInt((String) json.get("nonaccost")==""? "-1":(String) json.get("acno")));
				trainhandler.handleUpdate(toldname, train);
				session.setAttribute("Train", train);
				response.getWriter().write("<br>" + "Train Updated Successfully");
				return;
			}

		} catch (Exception ce) {
			response.getWriter().write("<br>" + ce.getMessage());
			return;
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();

		try {
			if (!"admin".equals((String)session.getAttribute("userRole"))) {
				throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
			}

			JSONObject json = new RequestBodyExtracter().extract(request);
			String type = (String) json.get("type");
			if (type.equals("station")) {
				String stcode = (String) json.get("stcoded");
				Station station = stationhandler.handleDelete(stcode);
				session.setAttribute("Station", station);
				response.getWriter().write("<br>" + station.getMessage());

			} else if (type.equals("train")) {
				String tnamed = (String) json.get("tnamed");
				Train train = trainhandler.handleDelete(tnamed);
				session.setAttribute("Train", train);
				response.getWriter().write("<br>" + train.getText());

			}

		} catch (Exception e) {
			response.getWriter().write("Error occurred: " + e.getMessage());
		}
	}

}
