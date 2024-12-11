
package com.railway.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.railway.dao.TrainsDAO;
import com.railway.handlers.BookingHandler;
import com.railway.handlers.TicketHandler;
import com.railway.model.Train;
import com.railway.utils.CustomExceptions;

@WebServlet({ "/search", "/book", "/history","/cost" })
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TrainsDAO trainsdao = new TrainsDAO();
	TicketHandler tickethandler = new TicketHandler();
	BookingHandler bookinghandler = new BookingHandler();
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
				if (path.equals("/cost") || path.equals("/book")) {
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
					String travelDate = (String)session.getAttribute("travelDate");
					session.setAttribute("ticketType2", request.getParameter("compartment"));
					if (tickethandler.canBook((String) request.getParameter("compartment"), train, Integer.parseInt(request.getParameter("numOfTravelers")),travelDate)) {
						throw new CustomExceptions(CustomExceptions.Exceptions.NO_SEATS_LEFT);
					}
					RequestDispatcher rd = request.getRequestDispatcher("book.jsp");
					rd.forward(request, response); 
					return;
				}
				if(path.equals("/cost")){
					bookinghandler.bookTicket((String)session.getAttribute("source"),(String)session.getAttribute("destination"), request);
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
