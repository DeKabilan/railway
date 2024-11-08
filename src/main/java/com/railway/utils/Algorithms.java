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
			int ticketNo;

			switch (ticket.getType()) {
			case "ACseats":
				ticketNo = 1 + (ACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
				int currseat = 0;
				for (int i = 1; i < train.getACCompartmentNo()+ 1; i++) {
					for (int j = 1; j <= train.getACCompartmentSeats(); j += 2) {
						currseat += 1;
						if (currseat == ticketNo) {
							result = "AC-" + i + "-" + j;
							break;
						}
					}
				}
				for (int i = 1; i < train.getACCompartmentNo() + 1; i++) {
					for (int j = 2; j <= train.getACCompartmentSeats(); j += 2) {
						currseat += 1;
						if (currseat == ticketNo) {
							
							result = "AC-" + i + "-" + j;
							break;
						}
					}
				}
				break;

			case "NONACseats":
				ticketNo = 1 + (NONACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
				currseat = 0;
				for (int i = 1; i < train.getNONACCompartmentNo()+ 1; i++) {
					for (int j = 1; j <= train.getNONACCompartmentSeats(); j += 2) {
						currseat += 1;
						if (currseat == ticketNo) {
							result = "NONAC-" + i + "-" + j;
							break;
						}
					}
				}
				for (int i = 1; i < train.getNONACCompartmentNo() + 1; i++) {
					for (int j = 2; j <= train.getNONACCompartmentSeats(); j += 2) {
						currseat += 1;
						if (currseat == ticketNo) {
							
							result = "NONAC-" + i + "-" + j;
							break;

						}
					}
				}
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
		    int ticketNo;

		    switch (ticket.getType()) {
		        case "ACseats":
	
		            ticketNo = 1 + (ACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
		            int currseatAC = 0;
		            

		            for (int i = 1; i <= train.getACCompartmentNo(); i++) {
		                for (int j = 1; j <= train.getACCompartmentSeats(); j++) {
		                    currseatAC += 1;
		                    if (currseatAC == ticketNo) {
		                        result = "AC-" + i + "-" + j;
		                        return result; 
		                    }
		                }
		            }
		            break;

		        case "NONACseats":
		      
		            ticketNo = 1 + (NONACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
		            int currseatNONAC = 0;
		            
		           
		            for (int i = 1; i <= train.getNONACCompartmentNo(); i++) {
		                for (int j = 1; j <= train.getNONACCompartmentSeats(); j++) {
		                    currseatNONAC += 1;
		                    if (currseatNONAC == ticketNo) {
		                        result = "NONAC-" + i + "-" + j;
		                        return result;
		                    }
		                }
		            }
		            break;
		    }
		    return result;
		}
	},
	EVEN_FIRST {
		public String getSeatNo(Ticket ticket) {
			TrainsDAO trainsdao = new TrainsDAO();
			String result = "";
			Train train = ticket.getTrain();
			int ACseats = train.getACCompartmentNo() * train.getACCompartmentSeats();
			int NONACseats = train.getNONACCompartmentNo() * train.getNONACCompartmentSeats();
			int ticketNo;

			switch (ticket.getType()) {
			case "ACseats":
				ticketNo = 1 + (ACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
				int currseat = 0;
				for (int i = 1; i < train.getACCompartmentNo() + 1; i++) {
					for (int j = 2; j <= train.getACCompartmentSeats(); j += 2) {
						currseat += 1;
						if (currseat == ticketNo) {
							
							result = "AC-" + i + "-" + j;
							break;
						}
					}
				}
				for (int i = 1; i < train.getACCompartmentNo()+ 1; i++) {
					for (int j = 1; j <= train.getACCompartmentSeats(); j += 2) {
						currseat += 1;
						if (currseat == ticketNo) {
							result = "AC-" + i + "-" + j;
							break;
						}
					}
				}
				break;

			case "NONACseats":
				ticketNo = 1 + (NONACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
				currseat = 0;
				for (int i = 1; i < train.getNONACCompartmentNo() + 1; i++) {
					for (int j = 2; j <= train.getNONACCompartmentSeats(); j += 2) {
						currseat += 1;
						if (currseat == ticketNo) {
							
							result = "NONAC-" + i + "-" + j;
							break;
							
						}
					}
				}
				for (int i = 1; i < train.getNONACCompartmentNo()+ 1; i++) {
					for (int j = 1; j <= train.getNONACCompartmentSeats(); j += 2) {
						currseat += 1;
						if (currseat == ticketNo) {
							result = "NONAC-" + i + "-" + j;
							break;
						}
					}
				}
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
		    int ticketNo;

		    switch (ticket.getType()) {
		        case "ACseats":
		            ticketNo = 1 + (ACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
		            int currseatAC = 0;
		            
		            for (int i = 1; i <= train.getACCompartmentNo(); i++) {
		                for (int j = 1; j <= train.getACCompartmentSeats(); j++) {
		                    currseatAC += 1;
		                    if (currseatAC == ticketNo) {
		                        result = "AC-" + i + "-" + j;
		                        return result; 
		                    }
		                }
		            }
		            break;

		        case "NONACseats":
		            ticketNo = 1 + (NONACseats - trainsdao.getSeats(ticket.getType(), train.getName()));
		            int currseatNONAC = 0;
		            
		            for (int i = 1; i <= train.getNONACCompartmentNo(); i++) {
		                for (int j = 1; j <= train.getNONACCompartmentSeats(); j++) {
		                    currseatNONAC += 1;
		                    if (currseatNONAC == ticketNo) {
		                        result = "NONAC-" + i + "-" + j;
		                        return result; 
		                    }
		                }
		            }
		            break;
		    }
		    return result;
		}
	};

	public abstract String getSeatNo(Ticket ticket);
}
