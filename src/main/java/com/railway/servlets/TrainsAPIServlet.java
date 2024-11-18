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
					String name = (String) json.get("name");
					String tseatalgo = (String) json.get("seatalgo");
					String source = (String) json.get("source");
					String destination = (String) json.get("destination");
					String strDeparture = (String) json.get("departure");
					String strArrival = (String) json.get("arrival");
					JSONArray periodicity = (JSONArray) json.get("periodicity");

					String intermediate = (String) json.get("intermediate");
					Integer ACno = ((Long) json.get("acno")).intValue();
					Integer ACseats = ((Long) json.get("acseats")).intValue();
					Integer ACcost = ((Long) json.get("accost")).intValue();
					Integer NONACno = ((Long) json.get("nonacno")).intValue();
					Integer NONACseats = ((Long) json.get("nonacseats")).intValue();
					Integer NONACcost = ((Long) json.get("nonaccost")).intValue();
					if (trains.isTrainExist(name)) {
						throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_ALREADY_EXISTS);
					}
					if (!stationsdao.isStationExist(source)) {
						throw new CustomExceptions(CustomExceptions.Exceptions.SOURCE_DOESNT_EXIST);
					}

					if (!stationsdao.isStationExist(destination)) {
						throw new CustomExceptions(CustomExceptions.Exceptions.DESTINATION_DOESNT_EXIST);
					}
					if (periodicity.size() == 0) {
						throw new CustomExceptions(CustomExceptions.Exceptions.PERIODICITY_IS_EMPTY);
					}
					String[] listInter = intermediate.split(",");
					ArrayList<String> itemList = new ArrayList<>(Arrays.asList(listInter));
					for (String stops : itemList) {
						if (!stationsdao.isStationExist(stops)) {
							throw new CustomExceptions(CustomExceptions.Exceptions.STOP_DOESNT_EXIST);
						}
					}

					Train train = trainhandler.handleCreate(name, tseatalgo, source, destination, strDeparture,
							strArrival, (ArrayList<String>) periodicity, intermediate, ACno, ACseats, ACcost, NONACno,
							NONACseats, NONACcost);
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
					String oldName = (String) json.get("oldname");
					String name = (String) json.get("name");
					String tseatalgo = (String) json.get("seatalgo");
					String source = (String) json.get("source");
					String destination = (String) json.get("destination");
					String strDeparture = (String) json.get("departure");
					String strArrival = (String) json.get("arrival");
					JSONArray periodicity = (JSONArray) json.get("periodicity");

					String intermediate = (String) json.get("intermediate");
					String ACno = String.valueOf(((Long) json.get("acno")).intValue());
					String ACseats = String.valueOf(((Long) json.get("acseats")).intValue());
					String ACcost = String.valueOf(((Long) json.get("accost")).intValue());
					String NONACno = String.valueOf(((Long) json.get("nonacno")).intValue());
					String NONACseats = String.valueOf(((Long) json.get("nonacseats")).intValue());
					String NONACcost = String.valueOf(((Long) json.get("nonaccost")).intValue());
					if (oldName == null || oldName.isEmpty()) {
						throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
					} else {
						if (!trains.isTrainExist(oldName)) {
							throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_DOESNT_EXIST);
						}
					}
					if (source != null && !source.isEmpty()) {
						if (!stationsdao.isStationExist(source)) {
							throw new CustomExceptions(CustomExceptions.Exceptions.SOURCE_DOESNT_EXIST);
						}
					}

					if (destination != null && !destination.isEmpty()) {
						if (!stationsdao.isStationExist(destination)) {
							throw new CustomExceptions(CustomExceptions.Exceptions.DESTINATION_DOESNT_EXIST);
						}
					}
					if (intermediate != null && !intermediate.isEmpty()) {
						String[] listInter = intermediate.split(",");
						ArrayList<String> itemList = new ArrayList<>(Arrays.asList(listInter));
						for (String stops : itemList) {
							if (!stationsdao.isStationExist(stops)) {
								throw new CustomExceptions(CustomExceptions.Exceptions.STOP_DOESNT_EXIST);
							}
						}
					}

					Train train = trainhandler.handleUpdate(oldName, name, tseatalgo, source, destination, strDeparture,
							strArrival, (ArrayList<String>) periodicity, intermediate, ACno, ACseats, ACcost, NONACno,
							NONACseats, NONACcost);
					response.getWriter().append(train.getText());
					return;

				}
				throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
			} catch (CustomExceptions ce) {
				throw new CustomExceptions(CustomExceptions.Exceptions.OPERATION_SUCCESSFULL);
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
