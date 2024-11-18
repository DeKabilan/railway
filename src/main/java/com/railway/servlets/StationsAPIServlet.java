package com.railway.servlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.railway.dao.StationsDAO;
import com.railway.decorator.ErrorDecorator;
import com.railway.decorator.StationDecorator;
import com.railway.handlers.AuthenticationHandler;
import com.railway.handlers.StationHandler;
import com.railway.model.Station;
import com.railway.utils.CustomExceptions;
import com.railway.utils.RequestBodyExtracter;

@WebServlet("/api/stations")
public class StationsAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ErrorDecorator errordecorator = new ErrorDecorator();
	StationsDAO stationsdao = new StationsDAO();
	StationDecorator stationdecorator = new StationDecorator();
	StationHandler stationhandler = new StationHandler();
	JSONParser parser = new JSONParser();
	AuthenticationHandler authenticationhandler = new AuthenticationHandler();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		try {
			try {
				if (request.getParameter("code") != null && !request.getParameter("code").isEmpty()) {
					ArrayList<Station> station = new ArrayList<Station>();
					if (stationsdao.getStation(request.getParameter("code")).getName().isEmpty()) {
						throw new CustomExceptions(CustomExceptions.Exceptions.STATION_DOESNT_EXIST);
					}
					station.add(stationsdao.getStation(request.getParameter("code")));
					response.getWriter().append(stationdecorator.decorate(station).toJSONString());
					return;

				}
				response.getWriter().append(stationdecorator.decorate(stationsdao.getEveryStation()).toJSONString());
				return;

			} catch (CustomExceptions ce) {
				response.getWriter().append(errordecorator.decorate(ce.getException()).toJSONString());
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		try {
			try {
				String username = request.getHeader("username");
				String password = request.getHeader("password");
				if (authenticationhandler.validateAdmin(username, password)) {
					JSONObject json = new RequestBodyExtracter().extract(request);
					String name = (String) json.get("name");
					String code = (String) json.get("code");
					if (code == null || name == null || code.length() != 3) {
						throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
					}
					if (stationsdao.isStationExist(code) || stationsdao.isStationExist(name)) {
						throw new CustomExceptions(CustomExceptions.Exceptions.STATION_ALREADY_EXISTS);
					}
					stationhandler.handleCreate(name, code);
					throw new CustomExceptions(CustomExceptions.Exceptions.OPERATION_SUCCESSFULL);

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

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		try {
			try {
				String username = request.getHeader("username");
				String password = request.getHeader("password");
				if (authenticationhandler.validateAdmin(username, password)) {
					if (request.getParameter("code") == null || ((String) request.getParameter("code")).length() != 3) {
						throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
					}
					String code = (String) request.getParameter("code");
					if (!stationsdao.isStationExist(code)) {
						throw new CustomExceptions(CustomExceptions.Exceptions.STATION_DOESNT_EXIST);
					}
					stationhandler.handleDelete(code);
					throw new CustomExceptions(CustomExceptions.Exceptions.OPERATION_SUCCESSFULL);

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

	protected void doPut(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		try {
			try {
				String username = request.getHeader("username");
				String password = request.getHeader("password");
				if (authenticationhandler.validateAdmin(username, password)) {
					JSONObject json = new RequestBodyExtracter().extract(request);
					if (json.get("oldcode") == null || ((String) json.get("oldcode")).length() != 3) {
						throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
					}
					String oldCode = (String) json.get("oldcode");
					String newCode = (String) json.get("code");
					String newName = (String) json.get("name");
					if (!stationsdao.isStationExist(oldCode)) {
						throw new CustomExceptions(CustomExceptions.Exceptions.STATION_DOESNT_EXIST);
					}
					if (newCode != null && newCode.length() != 3) {
						throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
					}
					if (newName != null && stationsdao.isStationExist(newName)) {
						throw new CustomExceptions(CustomExceptions.Exceptions.STATION_ALREADY_EXISTS);
					}
					stationhandler.handleUpdate(oldCode, newCode, newName);
					throw new CustomExceptions(CustomExceptions.Exceptions.OPERATION_SUCCESSFULL);

				}
				throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
			} catch (CustomExceptions ce) {
				response.getWriter().append(errordecorator.decorate(ce.getException()).toJSONString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
