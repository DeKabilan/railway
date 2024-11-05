
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

import com.railway.dao.TicketsDAO;
import com.railway.dao.TrainsDAO;
import com.railway.model.Ticket;
import com.railway.model.Train;


@WebServlet({"/cancel","/pay","/user"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		TrainsDAO trainsdao = new TrainsDAO();
		TicketsDAO ticketsdao = new TicketsDAO();
		if(!((String)session.getAttribute("userRole")).equals("user")) {
			RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
			rd.forward(request, response);
			return;
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String path = (String)request.getServletPath();
		System.out.println(path);
		if(path.equals("/cancel")) {
			trainsdao.updateSeats("NONACseats", ((Train)session.getAttribute("train")).getName(),((Integer)session.getAttribute("oldnonac")) );
			trainsdao.updateSeats("ACseats", ((Train)session.getAttribute("train")).getName(),(Integer)session.getAttribute("oldac"));
			RequestDispatcher rd = request.getRequestDispatcher("cancelled.jsp");
			rd.forward(request, response);
			return;
		}
		if(path.equals("/pay")) {
			ticketsdao.createTicketBatch((ArrayList<Ticket>)session.getAttribute("ticketList"), (String)session.getAttribute("userName"));
			RequestDispatcher rd = request.getRequestDispatcher("booked.jsp");
			rd.forward(request, response);
			return;
		}
		session.setAttribute("firstTime", true);
		RequestDispatcher rd = request.getRequestDispatcher("userpage.jsp");
		rd.forward(request, response);
		return;
		}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("userpage.jsp");
		rd.forward(request, response);
		return;
	}

}
