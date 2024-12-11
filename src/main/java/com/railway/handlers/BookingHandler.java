package com.railway.handlers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.railway.dao.TrainsDAO;
import com.railway.model.Ticket;
import com.railway.model.Train;

public class BookingHandler {
	TrainsDAO trainsdao = new TrainsDAO();
	TicketHandler tickethandler = new TicketHandler();
	
	public ArrayList<Ticket> bookTicket(String source, String destination, HttpServletRequest request){
		HttpSession session = request.getSession();
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
		String travelDate = (String) session.getAttribute("travelDate");
		Train train = (Train) session.getAttribute("train");
		int oldSeatsAC = trainsdao.getSeats("ACseats", train.getName(),travelDate);
		int oldSeatsNONAC = trainsdao.getSeats("NONACseats", train.getName(),travelDate);
		session.setAttribute("oldac", oldSeatsAC);
		session.setAttribute("oldnonac", oldSeatsNONAC);
		int cost = 0;
		for (int i = 0; i < Integer.parseInt((String) session.getAttribute("seats")); i++) {
			Ticket ticket = new Ticket();
			train.setSource(source);
			train.setDestination(destination);
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
						trainsdao.getSeats(ticket.getType(), ticket.getTrain().getName(),travelDate) - 1,travelDate);
			}
			ticketList.add(ticket);
		}
		session.setAttribute("cost", cost);
		session.setAttribute("ticketList", ticketList);
		return ticketList;
	}
}
