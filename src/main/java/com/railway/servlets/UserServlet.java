
package com.railway.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.railway.handlers.TicketHandler;
import com.railway.model.Ticket;
import com.railway.model.Train;
import com.railway.utils.CustomExceptions;

@WebServlet({ "/cancel", "/pay", "/user" })
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TicketHandler tickethandler = new TicketHandler();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			try {
				HttpSession session = request.getSession();
				if (session.getAttribute("userRole") == null
						|| !(((String) session.getAttribute("userRole")).equals("user"))) {
					throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
				}
				String path = (String) request.getServletPath();
				if (path.equals("/cancel")) {
					String travelDate = (String) session.getAttribute("travelDate");
					tickethandler.cancelTicket((Train) session.getAttribute("train"),
							(Integer) session.getAttribute("oldnonac"), (Integer) session.getAttribute("oldac"),
							travelDate);
					RequestDispatcher rd = request.getRequestDispatcher("cancelled.jsp");
					rd.forward(request, response);
					return;
				}
				if (path.equals("/pay")) {
					tickethandler.createTicket((String) session.getAttribute("userName"),
							(ArrayList<Ticket>) session.getAttribute("ticketList"));
					RequestDispatcher rd = request.getRequestDispatcher("booked.jsp");
					rd.forward(request, response);
					return;
				}
				session.setAttribute("firstTime", true);
				session.setAttribute("travelDate", request.getParameter("travelDate"));

				RequestDispatcher rd = request.getRequestDispatcher("userpage.jsp");
				rd.forward(request, response);
				return;
			} catch (CustomExceptions ce) {

				RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
				rd.forward(request, response);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("userpage.jsp");
		rd.forward(request, response);
		return;
	}

}
