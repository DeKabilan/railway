package com.railway.servlets;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.railway.handlers.StationHandler;
import com.railway.handlers.TrainHandler;
import com.railway.model.Station;
import com.railway.model.Train;
import com.railway.utils.CustomExceptions;

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
				response.getWriter().append("Served at: ").append(request.getContextPath());
				String path = request.getServletPath();
				String role = (String) session.getAttribute("userRole");
				if (role.equals("admin")) {

					if (path.equals("/admin")) {
						RequestDispatcher rd = request.getRequestDispatcher("adminStations.jsp");
						rd.forward(request, response);
						return;
					}

					else if (path.equals("/train")) {
						String type = request.getParameter("type");
						if (type != null) {
							switch (type) {
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

					else if (path.equals("/station")) {
						String type = request.getParameter("type");
						if (type != null) {
							switch (type) {
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
					throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
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
		try {
			try {
				HttpSession session = request.getSession();
				Train train = new Train();
				Station station = new Station();
				String path = request.getServletPath();
				String role = (String) session.getAttribute("userRole");
				if (role.equals("admin")) {

					if (path.equals("/train")) {
						String type = request.getParameter("type");
						if (type != null) {
							switch (type) {
							case "update":
								String oldtname = request.getParameter("oldtname");
								String tname = request.getParameter("tname");
								String algorithm = request.getParameter("tseatalgo");
								String source = request.getParameter("tsource");
								String destination = request.getParameter("tdestination");
								String strDeparture = request.getParameter("tdeparture");
								String strArrival = request.getParameter("tarrival");
								ArrayList<String> periodicity = new ArrayList<String>();
								String[] period = request.getParameterValues("days[]");
								if(period!=null) {
									for(String days : period) {
										periodicity.add(days);
									}
								}
								String intermediate = request.getParameter("tintermediate");
								String acNoStr = request.getParameter("tacno");
								String acSeatsStr = request.getParameter("tacseats");
								String acCostStr = request.getParameter("taccost");
								String nonAcNoStr = request.getParameter("tnonacno");
								String nonAcCostStr = request.getParameter("tnonaccost");
								String nonAcSeatsStr = request.getParameter("tnonacseats");
								train = trainhandler.handleUpdate(oldtname, tname, algorithm, source, destination,
										strDeparture, strArrival, periodicity, intermediate, acNoStr, acSeatsStr,
										acCostStr, nonAcNoStr, nonAcSeatsStr, nonAcCostStr);
								train.setID(-1);
								session.setAttribute("Train", train);
								break;
							case "create":
								String name = (String) request.getParameter("tname");
								String tseatalgo = (String) request.getParameter("tseatalgo");
								source = (String) request.getParameter("tsource");
								destination = (String) request.getParameter("tdestination");
								strDeparture = (String) request.getParameter("tdeparture");
								strArrival = (String) request.getParameter("tarrival");
								periodicity = new ArrayList<String>();
								period = request.getParameterValues("days[]");
								if(period!=null) {
									for(String days : period) {
										periodicity.add(days);
									}
								}
								intermediate = (String) request.getParameter("tintermediate");
								Integer ACno = Integer.parseInt(request.getParameter("tacno"));
								Integer ACseats = Integer.parseInt(request.getParameter("tacseats"));
								Integer ACcost = Integer.parseInt(request.getParameter("taccost"));
								Integer NONACno = Integer.parseInt(request.getParameter("tnonacno"));
								Integer NONACseats = Integer.parseInt(request.getParameter("tnonacseats"));
								Integer NONACcost = Integer.parseInt(request.getParameter("tnonaccost"));
								train = trainhandler.handleCreate(name, tseatalgo, source, destination, strDeparture,
										strArrival, periodicity, intermediate, ACno, ACseats, ACcost, NONACno,
										NONACseats, NONACcost);
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

					else if (path.equals("/station")) {
						String type = request.getParameter("type");

						switch (type) {
						case "create":
							station = stationhandler.handleCreate(request.getParameter("stname"),
									request.getParameter("stcode"));
							session.setAttribute("Station", station);
							break;
						case "update":
							station = stationhandler.handleUpdate(request.getParameter("stcodeold"),
									request.getParameter("stcodenew"), request.getParameter("stnamenew"));
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

				else {
					throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
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

}
