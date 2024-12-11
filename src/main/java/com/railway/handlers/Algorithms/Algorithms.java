package com.railway.handlers.Algorithms;

import com.railway.handlers.Algorithms.SeatTypes.AC;
import com.railway.handlers.Algorithms.SeatTypes.NONAC;
import com.railway.handlers.Algorithms.SeatTypes.Seat;
import com.railway.model.Ticket;

public enum Algorithms {

    ODD_FIRST {
        @Override
        public String getSeatNo(Ticket ticket) {
            return allocateSeat(ticket, "odd");
        }
    },

    EVEN_FIRST {
        @Override
        public String getSeatNo(Ticket ticket) {
            return allocateSeat(ticket, "even");
        }
    },

    ORDERED {
        @Override
        public String getSeatNo(Ticket ticket) {
            return allocateSeat(ticket, "order");
        }
    },

    SCATTERRED {
        @Override
        public String getSeatNo(Ticket ticket) {
            return allocateSeat(ticket, "scatter");
        }
    };
    public abstract String getSeatNo(Ticket ticket);

    
    private static String allocateSeat(Ticket ticket, String type) {
    	Seat seat = null;
        switch(ticket.getType()) {
        case "ACseats":
        	seat = new AC(ticket);
        	break;
        case "NONACseats":
        	seat = new NONAC(ticket);
        	break;
        }
        return findSeat(seat, type);
    }

    private static String findSeat(Seat seat, String type) {
    	switch(type) {
        case "odd":
        	return new OddFirst().allocateSeats(seat);
        case "even":
        	return new EvenFirst().allocateSeats(seat);
        case "order":
        	return new Ordered().allocateSeats(seat);
        case "scatter":
        	return new Scattered().allocateSeats(seat);
        }
        return "Seat Not Allocated";
    }
}


