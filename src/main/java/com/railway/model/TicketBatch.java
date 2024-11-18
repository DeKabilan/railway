package com.railway.model;

import java.util.ArrayList;

public class TicketBatch {
	int batchID = -1;
	ArrayList<Integer> tickets = new ArrayList<Integer>();
	String user = "";
	int cost = -1;
	String Train = "";
	String source = "";
	String destination = "";
	String travelDate = "";
	String bookDate = "";

	public String getTrain() {
		return Train;
	}

	public void setTrain(String train) {
		Train = train;
	}

	public int getBatchID() {
		return batchID;
	}

	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}

	public ArrayList<Integer> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Integer> tickets) {
		this.tickets = tickets;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookingDate) {
		this.bookDate = bookingDate;
	}

	@Override
	public String toString() {
		return "TicketBatch [batchID=" + batchID + ", tickets=" + tickets + ", user=" + user + ", cost=" + cost
				+ ", source=" + source + ", destination=" + destination + ", travelDate=" + travelDate
				+ ", bookingDate=" + bookDate + "]";
	}

}
