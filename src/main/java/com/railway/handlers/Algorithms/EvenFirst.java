package com.railway.handlers.Algorithms;

import com.railway.handlers.Algorithms.SeatTypes.Seat;

public class EvenFirst implements SeatAllocator {
	public String allocateSeats(Seat seat) {
		int currentSeat = 0;
		for (int pass = 0; pass < 2; pass++) {
			for (int i = 1; i <= seat.getCompartments(); i++) {
				for (int j = (pass == 0) ? 2 : 1; j <= seat.getSeatsPerCompartment(); j += 2) {
					currentSeat++;
					if (currentSeat == seat.getTicketNo()) {
						return String.format(seat.getCompartmentPrefix() + "-" + i + "-" + j);
					}
				}
			}
		}
		return "Seat Not Allocated";

	}
}
