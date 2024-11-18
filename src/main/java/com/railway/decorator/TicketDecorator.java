package com.railway.decorator;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.railway.dao.TicketsDAO;
import com.railway.model.Ticket;
import com.railway.model.TicketBatch;

public class TicketDecorator {
	TicketsDAO ticketsdao = new TicketsDAO();
	public JSONObject decorate(String username, ArrayList<TicketBatch> ticketBatchList) {
		JSONObject result = new JSONObject();
		JSONArray allBatches = new JSONArray();
		result.put("USERNAME",username);
		for(TicketBatch ticketBatch : ticketBatchList ) {
			ArrayList<Ticket> ticketList = ticketsdao.getTicket(ticketBatch);
			JSONObject batch = new JSONObject();
			batch.put("TRAIN_NAME", ticketBatch.getTrain());
			batch.put("SOURCE", ticketBatch.getSource());
			batch.put("DESTINATION", ticketBatch.getDestination());
			batch.put("BOOK_DATE", ticketBatch.getBookDate());
			batch.put("TRAVEL_DATE", ticketBatch.getTravelDate());
			batch.put("TRAVEL_DATE", ticketBatch.getTravelDate());
			batch.put("TOTAL_COST", ticketBatch.getCost());
			JSONArray passengerList = new JSONArray();
			for(Ticket ticket : ticketList) {
				JSONObject passenger = new JSONObject();
				passenger.put("NAME", ticket.getName());
				passenger.put("AGE", ticket.getAge());
				passenger.put("EMAIL", ticket.getMail());
				passenger.put("TYPE", ticket.getType());
				passenger.put("SEAT_NUMBER", ticket.getSeatNo());
				passengerList.add(passenger);
			}
			batch.put("PASSENGERS", passengerList);
			allBatches.add(batch);
		}
		result.put("DATA", allBatches);
		return result;
		
	}
}
