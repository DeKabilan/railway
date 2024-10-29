package com.railway.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.railway.dao.TrainsDAO;
import com.railway.handlers.TicketHandler;
import com.railway.model.Ticket;
import com.railway.model.Train;


@WebServlet("/ticket")
public class TicketServlet extends HttpServlet {
	TrainsDAO trainsdao = new TrainsDAO();
	private static final long serialVersionUID = 1L;
	TicketHandler tickethandler = new TicketHandler();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		doGet(request, response);
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();

		for(int i = 0; i<(Integer)session.getAttribute("seats");i++) {
			Ticket ticket = new Ticket();
			Train train = (Train)session.getAttribute("train");
			ticket.setName(request.getParameter("name"+i));
			ticket.setAge(Integer.parseInt(request.getParameter("age"+i)));
			if((request.getParameter("email"+i)!="") || !(request.getParameter("email"+i).equals(null)) ) {
				ticket.setMail(request.getParameter("email"+i));
			}
	        LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formattedDate = currentDate.format(formatter);
	        ticket.setTrain(train);
	        ticket.setBookDate(formattedDate);
	        ticket.setTravelDate((String)session.getAttribute("travelDate"));
	        ticket.setType((String)session.getAttribute("compartment"));
	        ticket.setSeatNo(tickethandler.getSeatNo(ticket));
	        trainsdao.updateSeats(ticket.getType(), ticket.getTrain().getName(), trainsdao.getSeats(ticket.getType(), ticket.getTrain().getName())-1);
	        System.out.println(ticket);
	        ticketList.add(ticket);
		}
		
	}

}
