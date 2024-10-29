package com.railway.handlers;

import com.railway.model.Ticket;


public class TicketHandler {
	
	public String  getSeatNo(Ticket ticket) {
		return ticket.getTrain().getSeatAlgorithm().getSeatNo(ticket);
		
		
	}
}

