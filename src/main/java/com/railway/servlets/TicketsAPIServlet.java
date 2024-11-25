package com.railway.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.railway.dao.TicketsDAO;
import com.railway.dao.TrainsDAO;
import com.railway.decorator.ErrorDecorator;
import com.railway.decorator.TicketDecorator;
import com.railway.handlers.AuthenticationHandler;
import com.railway.handlers.TicketHandler;
import com.railway.model.Train;
import com.railway.utils.CustomExceptions;
import com.railway.utils.RequestBodyExtracter;

@WebServlet("/api/tickets")
public class TicketsAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TicketDecorator ticketdecorator = new TicketDecorator();
	TicketsDAO ticketsdao = new TicketsDAO();
	TrainsDAO trainsdao = new TrainsDAO();
	TicketHandler tickethandler = new TicketHandler();
	ErrorDecorator errordecorator = new ErrorDecorator();
	AuthenticationHandler authenticationhandler = new AuthenticationHandler();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		try {
			try {
				String username = request.getHeader("username");
				String password = request.getHeader("password");
				if (authenticationhandler.validateUser(username, password)) {
					response.getWriter().append(
							ticketdecorator.decorate(username, ticketsdao.getTicketBatch(username)).toJSONString());
					return;
				}
				throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
			} catch (CustomExceptions ce) {
				response.getWriter().append(errordecorator.decorate(ce.getException()).toJSONString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		try {
			try {
				String username = request.getHeader("username");
				String password = request.getHeader("password");
				if (authenticationhandler.validateUser(username, password)) {
					JSONObject json = new RequestBodyExtracter().extract(request);
					String trainName = (String) json.get("train");
					String source = (String) json.get("source");
					String destination = (String) json.get("destination");
					String dateOfTravel = (String) json.get("date");
					String type = (String) json.get("type");
					ArrayList<JSONObject> passengers = (ArrayList<JSONObject>) json.get("passengers");
					if(!(type!=null && trainName!=null && source!=null && destination!=null && dateOfTravel!=null && passengers!=null)) {
						throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
					}
					if (!trainsdao.isTrainExist(trainName)) {
						throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_DOESNT_EXIST);
					}
					if (!type.equals("AC") && !type.equals("NONAC")) {
						throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_COMPARTMENT_TYPE);
					}
					LocalDate Date = LocalDate.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String currentDate = Date.format(formatter);
					try {
						LocalDate.parse(dateOfTravel, formatter);
					} catch (Exception e) {
						throw new CustomExceptions(CustomExceptions.Exceptions.DATE_NOT_VALID);
					}
					for (JSONObject passenger : passengers) {
						if (passenger.get("name") == null || ((String) passenger.get("name")).isEmpty()) {
							throw new CustomExceptions(CustomExceptions.Exceptions.NAME_IS_EMPTY);

						}
					}
					ArrayList<Train> trainList = trainsdao.searchTrain(source, destination, dateOfTravel, currentDate,
							0);
					for (Train train : trainList) {
						if (train.getName().equals(trainName)) {
							if(!trainsdao.ifSeatDayExist(dateOfTravel, trainName)) {
								trainsdao.createSeat(dateOfTravel, train.getACCompartmentNo()*train.getACCompartmentSeats(), train.getNONACCompartmentNo()*train.getNONACCompartmentSeats(), trainName);
							}
							if (type.equals("AC")) {
								if (!(trainsdao.getSeats("ACseats", trainName,dateOfTravel) > passengers.size())) {
									throw new CustomExceptions(CustomExceptions.Exceptions.NO_SEATS_LEFT);
								}
								trainsdao.updateSeats("ACseats", trainName,
										trainsdao.getSeats("ACseats", trainName,dateOfTravel) - 1,dateOfTravel);
							} else {
								if (!(trainsdao.getSeats("NONACseats", trainName,dateOfTravel) > passengers.size())) {
									throw new CustomExceptions(CustomExceptions.Exceptions.NO_SEATS_LEFT);
								}
								trainsdao.updateSeats("NONACseats", trainName,
										trainsdao.getSeats("NONACseats", trainName,dateOfTravel) - 1,dateOfTravel);
							}
							
							tickethandler.createTicket(username, trainName, type, currentDate, dateOfTravel,
									passengers);
							throw new CustomExceptions(CustomExceptions.Exceptions.OPERATION_SUCCESSFULL);

						}
					}
					throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_DOESNT_EXIST);

				}
				throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
			} catch (CustomExceptions ce) {
				response.getWriter().append(errordecorator.decorate(ce.getException()).toJSONString());
				return;
			}

		} catch (Exception e) {
			try {
				throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
			} catch (CustomExceptions ce) {
				response.getWriter().append(errordecorator.decorate(ce.getException()).toJSONString());
			}
		}
		
	}
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		try {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_ENDPOINT);

		} catch (CustomExceptions ce) {
			resp.getWriter().append(errordecorator.decorate(ce.getException()).toJSONString());
			return;
		}
	}
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		try {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_ENDPOINT);

		} catch (CustomExceptions ce) {
			resp.getWriter().append(errordecorator.decorate(ce.getException()).toJSONString());
			return;
		}
	}

}
