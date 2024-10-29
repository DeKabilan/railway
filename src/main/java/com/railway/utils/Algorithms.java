package com.railway.utils;

import com.railway.dao.TrainsDAO;
import com.railway.model.Ticket;
import com.railway.model.Train;

public enum Algorithms {
	ODD_FIRST {
		@Override
		public String getSeatNo(Ticket ticket) {
			TrainsDAO trainsdao = new TrainsDAO();
			String result = "";
			Train train = ticket.getTrain();
			int ACseats = train.getACCompartmentNo() * train.getACCompartmentSeats();
			int NONACseats = train.getNONACCompartmentNo() * train.getNONACCompartmentSeats();
			switch (ticket.getType()) {
			case ("ACseats"):
				int ticketNo = 1 + (ACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
				int seat = (ticketNo * 2) - 1;
				int compartmentNo = seat / train.getACCompartmentSeats();
				int seatNo = seat % train.getACCompartmentSeats();
				result = "AC-" + (1 + compartmentNo) + "-" + seatNo;
				break;
			case ("NONACseats"):
				ticketNo = 1 + (NONACseats - trainsdao.getSeats(ticket.getType(), train.getName()));

				seat = (ticketNo * 2) - 1;
				if (seat > NONACseats) {
					seat = (seat % NONACseats) + 1;
				}
				compartmentNo = seat / train.getNONACCompartmentSeats();
				seatNo = seat % train.getNONACCompartmentSeats();
				result = "NONAC-" + (1 + compartmentNo) + "-" + seatNo;
				break;

			}
			return result;
		}
	},
	ORDERED {
		@Override
		public String getSeatNo(Ticket ticket) {
			TrainsDAO trainsdao = new TrainsDAO();
			String result = "";
			Train train = ticket.getTrain();
			int ACseats = train.getACCompartmentNo() * train.getACCompartmentSeats();
			int NONACseats = train.getNONACCompartmentNo() * train.getNONACCompartmentSeats();
			switch (ticket.getType()) {
			case ("ACseats"):
				int ticketNo = 1 + (ACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
				int seat = ticketNo;
				int compartmentNo = seat / train.getACCompartmentSeats();
				int seatNo = seat % train.getACCompartmentSeats();
				result = "AC-" + (1 + compartmentNo) + "-" + seatNo;
				break;
			case ("NONACseats"):
				ticketNo = 1 + (NONACseats - trainsdao.getSeats(ticket.getType(), train.getName()));

				seat = ticketNo;
				compartmentNo = seat / train.getNONACCompartmentSeats();
				seatNo = seat % train.getNONACCompartmentSeats();
				result = "NONAC-" + (1 + compartmentNo) + "-" + seatNo;
				break;

			}
			return result;
		}
	},
	EVEN_FIRST {
		@Override
		public String getSeatNo(Ticket ticket) {
			TrainsDAO trainsdao = new TrainsDAO();
			String result = "";
			Train train = ticket.getTrain();
			int ACseats = train.getACCompartmentNo() * train.getACCompartmentSeats();
			int NONACseats = train.getNONACCompartmentNo() * train.getNONACCompartmentSeats();
			switch (ticket.getType()) {
			case ("ACseats"):
				int ticketNo = 1 + (ACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
				int seat = (ticketNo * 2);
				if (seat > ACseats) {
					seat = (seat % ACseats) - 1;
				}
				int compartmentNo = seat / train.getACCompartmentSeats();
				int seatNo = seat % train.getACCompartmentSeats();
				result = "AC-" + (1 + compartmentNo) + "-" + seatNo;
				break;

			case ("NONACseats"):
				ticketNo = 1 + (NONACseats - trainsdao.getSeats(ticket.getType(), train.getName()));

				seat = (ticketNo * 2);
				if (seat > NONACseats) {
					seat = (seat % NONACseats) - 1;
				}
				compartmentNo = seat / train.getNONACCompartmentSeats();
				seatNo = seat % train.getNONACCompartmentSeats();
				result = "NONAC-" + (1 + compartmentNo) + "-" + seatNo;
				break;

			}
			return result;
		}
	},
	SCATTERRED {
		@Override
		public String getSeatNo(Ticket ticket) {
			TrainsDAO trainsdao = new TrainsDAO();
			String result = "";
			Train train = ticket.getTrain();
			int ACseats = train.getACCompartmentNo() * train.getACCompartmentSeats();
			int NONACseats = train.getNONACCompartmentNo() * train.getNONACCompartmentSeats();
			switch (ticket.getType()) {
			case ("ACseats"):
				int ticketNo = 1 + (ACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
				int seat = ticketNo;
				int compartmentNo = seat / train.getACCompartmentSeats();
				int seatNo = seat % train.getACCompartmentSeats();
				result = "AC-" + (1 + compartmentNo) + "-" + seatNo;
				break;

			case ("NONACseats"):
				ticketNo = 1 + (NONACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
				seat = ticketNo;
				compartmentNo = seat / train.getNONACCompartmentSeats();
				seatNo = seat % train.getNONACCompartmentSeats();
				result = "NONAC-" + (1 + compartmentNo) + "-" + seatNo;
				break;

			}
			return result;
		}
	};

	public String getSeatNo(Ticket ticket) {
		return null;
	}
}