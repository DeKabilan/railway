package com.railway.handlers.Algorithms.SeatTypes;

import com.railway.dao.TrainsDAO;
import com.railway.model.Ticket;
import com.railway.model.Train;

public class AC implements Seat{
	TrainsDAO trainsdao= new TrainsDAO();

	int totalSeats;
	int remainingSeats;
	int ticketNo;
	int compartments;
	int seatsPerCompartment;
	String compartmentPrefix = "AC";
	
	public AC(Ticket ticket) {
        Train train = ticket.getTrain();
        this.compartments = train.getACCompartmentNo();
        this.seatsPerCompartment = train.getACCompartmentSeats();
        this.totalSeats = this.compartments*this.seatsPerCompartment;
        this.remainingSeats = trainsdao.getSeats(ticket.getType(), train.getName(),ticket.getTravelDate());
        this.ticketNo = 1 + (totalSeats - remainingSeats);
	}
	
	public int getTotalSeats() {
		return totalSeats;
	}

	public int getRemainingSeats() {
		return remainingSeats;
	}

	public int getTicketNo() {
		return ticketNo;
	}

	public int getCompartments() {
		return compartments;
	}

	public int getSeatsPerCompartment() {
		return seatsPerCompartment;
	}

	public String getCompartmentPrefix() {
		return compartmentPrefix;
	}
}
