package com.railway.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.railway.dao.StationsDAO;
import com.railway.dao.TrainsDAO;
import com.railway.decorator.ErrorDecorator;
import com.railway.decorator.TrainDecorator;
import com.railway.handlers.AuthenticationHandler;
import com.railway.handlers.TrainHandler;
import com.railway.handlers.APIValidators.EndpointValidator;
import com.railway.handlers.APIValidators.TrainEndpointValidator;
import com.railway.model.Train;
import com.railway.utils.CustomExceptions;
import com.railway.utils.RequestBodyExtracter;

@WebServlet("/api/trains")
public class TrainsAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TrainsDAO trainsdao = new TrainsDAO();
	TrainDecorator traindecorator = new TrainDecorator();
	TrainHandler trainhandler = new TrainHandler();
	ErrorDecorator errordecorator = new ErrorDecorator();
	StationsDAO stationsdao = new StationsDAO();
	TrainsDAO trains = new TrainsDAO();
	EndpointValidator trainEndpointValidator = new TrainEndpointValidator();
	AuthenticationHandler authenticationhandler = new AuthenticationHandler();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		try {
			try {
				if (request.getParameter("name") != null && !request.getParameter("name").isEmpty()) {
					ArrayList<Train> train = new ArrayList<Train>();
					if (!trainsdao.isTrainExist(request.getParameter("name"))) {
						throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_DOESNT_EXIST);
					}
					train.add(trainsdao.getTrain(request.getParameter("name")));
					response.getWriter().append(traindecorator.decorate(train).toJSONString());
					return;

				}
				response.getWriter().append(traindecorator.decorate(trainsdao.getAllTrains()).toJSONString());

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
				if (authenticationhandler.validateAdmin(username, password)) {
					JSONObject json = new RequestBodyExtracter().extract(request);
					trainEndpointValidator.validate(json);
					Train train = new Train();
					train.setName((String) json.get("name"));
					train.setSeatAlgorithm((String) json.get("seatalgo"));
					train.setSource((String) json.get("source"));
					train.setDestination((String) json.get("destination"));
					train.setDeparture((String) json.get("departure"));
					train.setArrival((String) json.get("arrival"));
					train.setPeriodicity((ArrayList<String>) (JSONArray) json.get("periodicity"));
					train.setIntermediate(new ArrayList<String>(Arrays.asList(((String) json.get("intermediate")).split(","))));
					train.setACCompartmentNo(((Long) json.get("acno")).intValue());
					train.setACCompartmentSeats(((Long) json.get("acseats")).intValue());
					train.setACCompartmentCost(((Long) json.get("accost")).intValue());
					train.setNONACCompartmentNo(((Long) json.get("nonacno")).intValue());
					train.setNONACCompartmentSeats(((Long) json.get("nonacseats")).intValue());
					train.setNONACCompartmentCost(((Long) json.get("nonaccost")).intValue());

					trainhandler.handleCreate(train);
					throw new CustomExceptions(CustomExceptions.Exceptions.OPERATION_SUCCESSFULL);

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

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		try {
			try {
				String username = request.getHeader("username");
				String password = request.getHeader("password");
				if (authenticationhandler.validateAdmin(username, password)) {
					JSONObject json = new RequestBodyExtracter().extract(request);
					trainEndpointValidator.validate(json);
					Train train = new Train();
					String oldname = (String)json.get("oldname");
					train.setName((String) json.get("name"));
					train.setSeatAlgorithm((String) json.get("seatalgo"));
					train.setSource((String) json.get("source"));
					train.setDestination((String) json.get("destination"));
					train.setDeparture((String) json.get("departure"));
					train.setArrival((String) json.get("arrival"));
					train.setPeriodicity((ArrayList<String>) (JSONArray) json.get("periodicity"));
					train.setIntermediate(new ArrayList<String>(Arrays.asList(((String) json.get("intermediate")).split(","))));
					train.setACCompartmentNo(((Long) json.get("acno")).intValue());
					train.setACCompartmentSeats(((Long) json.get("acseats")).intValue());
					train.setACCompartmentCost(((Long) json.get("accost")).intValue());
					train.setNONACCompartmentNo(((Long) json.get("nonacno")).intValue());
					train.setNONACCompartmentSeats(((Long) json.get("nonacseats")).intValue());
					train.setNONACCompartmentCost(((Long) json.get("nonaccost")).intValue());
					trainhandler.handleUpdate(oldname, train);
					response.getWriter().append(train.getText());
					throw new CustomExceptions(CustomExceptions.Exceptions.OPERATION_SUCCESSFULL);
				}
				else {
					throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
				}
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

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		try {
			try {
				String username = request.getHeader("username");
				String password = request.getHeader("password");
				if (authenticationhandler.validateAdmin(username, password)) {
					if (request.getParameter("name") != null && !request.getParameter("name").isEmpty()) {
						if (!trains.isTrainExist(request.getParameter("name"))) {
							throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_DOESNT_EXIST);
						}
						trainhandler.handleDelete(request.getParameter("name"));
						throw new CustomExceptions(CustomExceptions.Exceptions.OPERATION_SUCCESSFULL);
					}
					throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
				}
				throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);

			} catch (CustomExceptions ce) {
				response.getWriter().append(errordecorator.decorate(ce.getException()).toJSONString());
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
