package com.railway.handlers.Algorithms.SeatTypes;


public interface Seat {
	public int getTotalSeats();
	public int getRemainingSeats();
	public int getTicketNo();
	public int getCompartments();
	public int getSeatsPerCompartment();
	public String getCompartmentPrefix();
	
}
