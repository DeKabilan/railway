package com.railway.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.railway.handlers.TicketHandler;
import com.railway.model.Ticket;
import com.railway.model.Train;


@WebServlet({"/cost"})
public class BookingServlet extends HttpServlet {
	TrainsDAO trainsdao = new TrainsDAO();
	private static final long serialVersionUID = 1L;
	TicketHandler tickethandler = new TicketHandler();
	TicketsDAO ticketsdao = new TicketsDAO();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		if(!((String)session.getAttribute("userRole")).equals("user")) {
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		doGet(request, response);
		String path = request.getServletPath();
		System.out.println(path+": TicketServlet");
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
		Train train = (Train)session.getAttribute("train");
		int oldSeatsAC = trainsdao.getSeats("ACseats",train.getName());
		int oldSeatsNONAC = trainsdao.getSeats("NONACseats",train.getName());
		session.setAttribute("oldac", oldSeatsAC);
		session.setAttribute("oldnonac", oldSeatsNONAC);
		int cost = 0;
		for(int i = 0; i<Integer.parseInt((String)session.getAttribute("seats"));i++) {
			Ticket ticket = new Ticket();
			train.setSource((String)session.getAttribute("source"));
			train.setDestination((String)session.getAttribute("destination"));
			ticket.setName(request.getParameter("name"+i));
			ticket.setAge(Integer.parseInt(request.getParameter("age"+i)));
			if((request.getParameter("email"+i)!="") || !(request.getParameter("email"+i).equals(null)) ) {
				ticket.setMail(request.getParameter("email"+i));
			}
			ticket.setType((String)session.getAttribute("ticketType"));
			if(ticket.getType().equals("ACseats")) {
				cost+=train.getACCompartmentCost();
			}
			else {
				cost+=train.getNONACCompartmentCost();
			}
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = currentDate.format(formatter);
			ticket.setTrain(train);
			ticket.setBookDate(formattedDate);
			ticket.setTravelDate((String)session.getAttribute("travelDate"));
			if(ticket.getAge()<=10) {
				ticket.setSeatNo("CHILDREN");
			}
			else {
				ticket.setSeatNo(tickethandler.getSeatNo(ticket));
				trainsdao.updateSeats(ticket.getType(), ticket.getTrain().getName(), trainsdao.getSeats(ticket.getType(), ticket.getTrain().getName())-1);
			}
			ticketList.add(ticket);
		}
		session.setAttribute("cost", cost);
		session.setAttribute("ticketList", ticketList);
		RequestDispatcher rd = request.getRequestDispatcher("tickets.jsp");
		rd.forward(request, response);
		return;
			
	}
		
}

