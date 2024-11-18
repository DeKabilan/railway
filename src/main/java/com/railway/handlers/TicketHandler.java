package com.railway.handlers;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.railway.dao.TicketsDAO;
import com.railway.dao.TrainsDAO;
import com.railway.model.Ticket;
import com.railway.model.Train;

public class TicketHandler {
TrainsDAO trainsdao = new TrainsDAO();
TicketsDAO ticketsdao = new TicketsDAO();

	public String getSeatNo(Ticket ticket) {
		return ticket.getTrain().getSeatAlgorithm().getSeatNo(ticket);

	}
	
	public void cancelTicket(Train train, Integer oldac,Integer oldnonac) {
		trainsdao.updateSeats("NONACseats", train.getName(),oldnonac);
		trainsdao.updateSeats("ACseats", train.getName(),oldac);
	}
	
	public void createTicket(String username, ArrayList<Ticket> ticketList) {
		ticketsdao.createTicketBatch(ticketList,username);
	}
	

	public void createTicket(String username, String TrainName, String compartment, String bookDate, String travelDate, ArrayList<JSONObject> passengers) {
		if(compartment.equals("AC")) {
			ticketsdao.createTicket(trainsdao.getTrain(TrainName), username, "ACseats", bookDate, travelDate, passengers);
			
		}
		else {
			ticketsdao.createTicket(trainsdao.getTrain(TrainName), username, "NONACseats", bookDate, travelDate, passengers);
		}
	}
	
	public Boolean canBook(String compartment, Train train, Integer numOfTravelers) {
		if (trainsdao.getSeats(compartment, train.getName()) < numOfTravelers) {
			return true;
		}
		return false;
	}
}
