package com.railway.servlets;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.railway.dao.StationsDAO;
import com.railway.dao.TrainsDAO;
import com.railway.handlers.StationHandler;
import com.railway.handlers.TrainHandler;
import com.railway.model.Station;
import com.railway.model.Train;

@WebServlet({"/admin","/station","/train"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StationHandler stationhandler = new StationHandler();
	TrainHandler trainhandler = new TrainHandler();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StationsDAO stationsdao = new StationsDAO();
		TrainsDAO trainsdao =new TrainsDAO();
//		stationsdao.JSONtoDB("/home/kabilan-22527/eclipse-workspace/railway/src/main/resources/stations.json");
//		trainsdao.trainListToDB("/home/kabilan-22527/eclipse-workspace/railway/src/main/resources/trains.json");
//		System.out.println(trainsdao.getTrain("PDY", "CNG", "0000", "2400"));
		
		HttpSession session = request.getSession();
		Train train = new Train();
		Station station = new Station();
		session.setAttribute("Train", train);
		session.setAttribute("Station", station);
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String path = request.getServletPath();
		String role = (String)session.getAttribute("userRole");
		if(role.equals("admin")) {
		
			if(path.equals("/admin")) {
				RequestDispatcher rd = request.getRequestDispatcher("adminStations.jsp");
				rd.forward(request, response);
				return;
			}
			

			else if(path.equals("/train")) {
				String type = request.getParameter("type");
				if (type!=null) {
					switch(type) {
					case "read":
						train = trainhandler.handleRead(request.getParameter("tname"));
						session.setAttribute("Train", train);
						break;
					case "delete":
						train = trainhandler.handleDelete(request.getParameter("tname"));
						train.setID(-1);
						session.setAttribute("Train", train);
						break;
					default:
						break;
					}
				}
				
				RequestDispatcher rd = request.getRequestDispatcher("adminTrains.jsp");
				rd.forward(request, response);
				return;
			}
			
			
			
			else if(path.equals("/station")) {
				String type = request.getParameter("type");
				if(type!=null) {
					switch(type) {
					case "read":
						station = stationhandler.handleRead(request.getParameter("stcode"));
						session.setAttribute("Station", station);
						break;
					case "delete":
						station = stationhandler.handleDelete(request.getParameter("stcode"));
						session.setAttribute("Station", station);
						break;
					default:
						break;
					}
				}
				RequestDispatcher rd = request.getRequestDispatcher("adminStations.jsp");
				rd.forward(request, response);
				return;
			}
		}
		
		else {
			RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
			rd.forward(request, response);
			return;
		}
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Train train = new Train();
		Station station = new Station();
		session.setAttribute("Train", train);
		session.setAttribute("Station", station);
		String path = request.getServletPath();
		String role = (String)session.getAttribute("userRole");
		if(role.equals("admin")) {
			
			
			if(path.equals("/train")) {
				String type = request.getParameter("type");
				if (type!=null) {
					switch(type) {
					case "update":
						train = trainhandler.handleUpdate(request);
						train.setID(-1);
						session.setAttribute("Train", train);
						break;
					case "create":
						train = trainhandler.handleCreate(request);
						train.setID(-1);
						session.setAttribute("Train", train);
						break;
					default:
						break;
					}
				}
				
				RequestDispatcher rd = request.getRequestDispatcher("adminTrains.jsp");
				rd.forward(request, response);
				return;
			}
			
			
			else if(path.equals("/station")) {			
				String type = request.getParameter("type");
				
				switch(type) {
				case "create":
					station = stationhandler.handleCreate(request.getParameter("stname"),request.getParameter("stcode"));
					session.setAttribute("Station", station);
					break;
				case "update":
					station = stationhandler.handleUpdate(request.getParameter("stcodeold"),request.getParameter("stcodenew"),request.getParameter("stnamenew"));
					session.setAttribute("Station", station);
					break;
				default:
					break;
				}
				
				RequestDispatcher rd = request.getRequestDispatcher("adminStations.jsp");
				rd.forward(request, response);
				return;
			}
		}
		
		else{
			RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
			rd.forward(request, response);
			return;
		}
	}

}
