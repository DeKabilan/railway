package com.railway.handlers.Algorithms;

import com.railway.dao.TrainsDAO;
import com.railway.model.Ticket;
import com.railway.model.Train;

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
        TrainsDAO trainsDAO = new TrainsDAO();
        Train train = ticket.getTrain();
        int totalSeats = ticket.getType().equals("ACseats")
                ? train.getACCompartmentNo() * train.getACCompartmentSeats()
                : train.getNONACCompartmentNo() * train.getNONACCompartmentSeats();
        int remainingSeats = trainsDAO.getSeats(ticket.getType(), train.getName(),ticket.getTravelDate());
        int ticketNo = 1 + (totalSeats - remainingSeats);
        int compartments = ticket.getType().equals("ACseats") ? train.getACCompartmentNo() : train.getNONACCompartmentNo();
        int seatsPerCompartment = ticket.getType().equals("ACseats") ? train.getACCompartmentSeats() : train.getNONACCompartmentSeats();
        String compartmentPrefix = ticket.getType().equals("ACseats") ? "AC" : "NONAC";
        return findSeat(ticketNo, compartments, seatsPerCompartment, compartmentPrefix, type);
    }

    private static String findSeat(int ticketNo, int compartments, int seatsPerCompartment, String prefix, String type) {
    	switch(type) {
        case "odd":
        	return new OddFirst().allocateSeats(ticketNo, compartments, seatsPerCompartment, prefix);
        case "even":
        	return new EvenFirst().allocateSeats(ticketNo, compartments, seatsPerCompartment, prefix);
        case "order":
        	return new Ordered().allocateSeats(ticketNo, compartments, seatsPerCompartment, prefix);
        case "scatter":
        	return new Scattered().allocateSeats(ticketNo, compartments, seatsPerCompartment, prefix);
        }
        return "Seat Not Allocated";
    }
}


