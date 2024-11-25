package com.railway.handlers.Algorithms;

public class OddFirst implements SeatAllocator{
	public String allocateSeats(int ticketNo, int compartments, int seatsPerCompartment, String type) {
		int currentSeat = 0;
		for (int pass = 0; pass < 2; pass++) {
			for (int i = 1; i <= compartments; i++) {
				for (int j = (pass == 0) ? 1 : 2; j <= seatsPerCompartment; j += 2) {
					currentSeat++;
					if (currentSeat == ticketNo) {
						return String.format(type + "-" + i + "-" + j);
					}
				}
			}
		}
		return "Seat Not Allocated";

	}
}
