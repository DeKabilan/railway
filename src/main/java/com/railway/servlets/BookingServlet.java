
package com.railway.servlets;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.railway.dao.TrainsDAO;
import com.railway.handlers.TicketHandler;
import com.railway.model.Ticket;
import com.railway.model.Train;
import com.railway.utils.CustomExceptions;

@WebServlet({ "/search", "/book", "/history","/cost" })
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TrainsDAO trainsdao = new TrainsDAO();
	TicketHandler tickethandler = new TicketHandler();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			try {
				HttpSession session = request.getSession();
				session.setAttribute("ticketType", (String) request.getParameter("compartment"));
				session.setAttribute("seatmessage", "");
				String path = request.getServletPath();
				if (session.getAttribute("userRole")==null || !(((String) session.getAttribute("userRole")).equals("user"))) {
					throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
				}
				if (path.equals("/search")) {
					session.setAttribute("ticketType", (String) request.getParameter("compartment"));
					RequestDispatcher rd = request.getRequestDispatcher("search.jsp");
					rd.forward(request, response);
					return;

				}
				if (path.equals("/history")) {
					RequestDispatcher rd = request.getRequestDispatcher("history.jsp");
					rd.forward(request, response);
					return;

				}
				if (path.equals("/cost")) {
					RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
					rd.forward(request, response);
					return;

				}
				if (path.equals("/book")) {
					RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
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
		try {
			HttpSession session = request.getSession();
			try {
				String path = request.getServletPath();
				if (path.equals("/search")) {
					RequestDispatcher rd = request.getRequestDispatcher("search.jsp");
					rd.forward(request, response);
					return;

				}
				if (path.equals("/book")) {
					Train train = trainsdao.getTrain((String) request.getParameter("train"));
					session.setAttribute("train", train);
					session.setAttribute("seats", request.getParameter("numOfTravelers"));
					session.setAttribute("ticketType2", request.getParameter("compartment"));
					if (tickethandler.canBook((String) request.getParameter("compartment"), train, Integer.parseInt(request.getParameter("numOfTravelers")))) {
						throw new CustomExceptions(CustomExceptions.Exceptions.NO_SEATS_LEFT);
					}
					RequestDispatcher rd = request.getRequestDispatcher("book.jsp");
					rd.forward(request, response);
					return;
				}
				if(path.equals("/cost")){
					ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
					Train train = (Train) session.getAttribute("train");
					int oldSeatsAC = trainsdao.getSeats("ACseats", train.getName());
					int oldSeatsNONAC = trainsdao.getSeats("NONACseats", train.getName());
					session.setAttribute("oldac", oldSeatsAC);
					session.setAttribute("oldnonac", oldSeatsNONAC);
					int cost = 0;
					for (int i = 0; i < Integer.parseInt((String) session.getAttribute("seats")); i++) {
						Ticket ticket = new Ticket();
						train.setSource((String) session.getAttribute("source"));
						train.setDestination((String) session.getAttribute("destination"));
						ticket.setName(request.getParameter("name" + i));
						ticket.setAge(Integer.parseInt(request.getParameter("age" + i)));
						if ((request.getParameter("email" + i) != "") || !(request.getParameter("email" + i).equals(null))) {
							ticket.setMail(request.getParameter("email" + i));
						}
						ticket.setType((String) session.getAttribute("ticketType2"));
						if (ticket.getType().equals("ACseats")) {
							cost += train.getACCompartmentCost();
						} else {
							cost += train.getNONACCompartmentCost();
						}
						LocalDate currentDate = LocalDate.now();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						String formattedDate = currentDate.format(formatter);
						ticket.setTrain(train);
						ticket.setBookDate(formattedDate);
						ticket.setTravelDate((String) session.getAttribute("travelDate"));
						if (ticket.getAge() <= 10) {
							ticket.setSeatNo("CHILDREN");
						} else {
							ticket.setSeatNo(tickethandler.getSeatNo(ticket));
							trainsdao.updateSeats(ticket.getType(), ticket.getTrain().getName(),
									trainsdao.getSeats(ticket.getType(), ticket.getTrain().getName()) - 1);
						}
						ticketList.add(ticket);
					}
					session.setAttribute("cost", cost);
					session.setAttribute("ticketList", ticketList);
					RequestDispatcher rd = request.getRequestDispatcher("tickets.jsp");
					rd.forward(request, response);
					return;
				}
			} catch (CustomExceptions ce) {
				session.setAttribute("ticketType", "");
				session.setAttribute("seatmessage", ce.getMessage());
				RequestDispatcher rd = request.getRequestDispatcher("search.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
