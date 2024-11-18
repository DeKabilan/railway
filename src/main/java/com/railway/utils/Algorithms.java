package com.railway.utils;

import com.railway.dao.TrainsDAO;
import com.railway.model.Ticket;
import com.railway.model.Train;

public enum Algorithms {

    ODD_FIRST {
        @Override
        public String getSeatNo(Ticket ticket) {
            return allocateSeat(ticket, true, false);
        }
    },

    EVEN_FIRST {
        @Override
        public String getSeatNo(Ticket ticket) {
            return allocateSeat(ticket, false, true);
        }
    },

    ORDERED {
        @Override
        public String getSeatNo(Ticket ticket) {
            return allocateSeat(ticket, false, false);
        }
    },

    SCATTERRED {
        @Override
        public String getSeatNo(Ticket ticket) {
            return allocateSeat(ticket, false, false);
        }
    };

    public abstract String getSeatNo(Ticket ticket);

    private static String allocateSeat(Ticket ticket, boolean oddFirst, boolean evenFirst) {
        TrainsDAO trainsDAO = new TrainsDAO();
        Train train = ticket.getTrain();
        int totalSeats = ticket.getType().equals("ACseats")
                ? train.getACCompartmentNo() * train.getACCompartmentSeats()
                : train.getNONACCompartmentNo() * train.getNONACCompartmentSeats();
        int remainingSeats = trainsDAO.getSeats(ticket.getType(), train.getName());
        int ticketNo = 1 + (totalSeats - remainingSeats);

        int compartments = ticket.getType().equals("ACseats") ? train.getACCompartmentNo() : train.getNONACCompartmentNo();
        int seatsPerCompartment = ticket.getType().equals("ACseats") ? train.getACCompartmentSeats() : train.getNONACCompartmentSeats();

        String compartmentPrefix = ticket.getType().equals("ACseats") ? "AC" : "NONAC";

        return findSeat(ticketNo, compartments, seatsPerCompartment, compartmentPrefix, oddFirst, evenFirst);
    }

    private static String findSeat(int ticketNo, int compartments, int seatsPerCompartment, String prefix, boolean oddFirst, boolean evenFirst) {
        int currentSeat = 0;

        if (oddFirst || evenFirst) {
            for (int pass = 0; pass < 2; pass++) {
                for (int i = 1; i <= compartments; i++) {
                    for (int j = (pass == 0) == oddFirst ? 1 : 2; j <= seatsPerCompartment; j += 2) {
                        currentSeat++;
                        if (currentSeat == ticketNo) {
                            return String.format(prefix+"-"+i+"-"+j);
                        }
                    }
                }
            }
        } else {
            for (int i = 1; i <= compartments; i++) {
                for (int j = 1; j <= seatsPerCompartment; j++) {
                    currentSeat++;
                    if (currentSeat == ticketNo) {
                        return String.format(prefix+"-"+i+"-"+j);
                    }
                }
            }
        }

        return "";
    }
}


