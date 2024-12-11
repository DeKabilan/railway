package com.railway.handlers.Algorithms;

import com.railway.handlers.Algorithms.SeatTypes.Seat;

public class Ordered implements SeatAllocator{
	public String allocateSeats(Seat seat) {
		int currentSeat = 0;
        for (int i = 1; i <= seat.getCompartments(); i++) {
            for (int j = 1; j <= seat.getSeatsPerCompartment(); j++) {
                currentSeat++;
                if (currentSeat == seat.getTicketNo()) {
                    return String.format(seat.getCompartmentPrefix()+"-"+i+"-"+j);
                }
            }
        }
		return "Seat Not Allocated";

	}
}
