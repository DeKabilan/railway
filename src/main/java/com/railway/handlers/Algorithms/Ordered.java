package com.railway.handlers.Algorithms;

public class Ordered implements SeatAllocator{
	public String allocateSeats(int ticketNo, int compartments, int seatsPerCompartment, String type) {
		int currentSeat = 0;
        for (int i = 1; i <= compartments; i++) {
            for (int j = 1; j <= seatsPerCompartment; j++) {
                currentSeat++;
                if (currentSeat == ticketNo) {
                    return String.format(type+"-"+i+"-"+j);
                }
            }
        }
		return "Seat Not Allocated";

	}
}
