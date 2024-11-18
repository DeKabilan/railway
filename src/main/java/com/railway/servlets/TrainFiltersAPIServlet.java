package com.railway.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.railway.dao.TrainsDAO;
import com.railway.decorator.ErrorDecorator;
import com.railway.decorator.TrainDecorator;
import com.railway.utils.CustomExceptions;

@WebServlet("/api/trains/filters")
public class TrainFiltersAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TrainsDAO trainsdao = new TrainsDAO();
	TrainDecorator traindecorator = new TrainDecorator();
	ErrorDecorator errordecorator = new ErrorDecorator();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			try {
				if (request.getParameter("source") == null || request.getParameter("source").isEmpty()) {
					throw new CustomExceptions(CustomExceptions.Exceptions.SOURCE_CANT_BE_EMPTY);
				}
				if (request.getParameter("destination") == null || request.getParameter("source").isEmpty()) {
					throw new CustomExceptions(CustomExceptions.Exceptions.DESTINATION_CANT_BE_EMPTY);
				}

				response.getWriter()
						.append(traindecorator.decorate(trainsdao.additionalFilters(request.getParameter("source"),
								request.getParameter("destination"), request.getParameter("departure"),
								request.getParameter("arrival"), request.getParameter("compartment"), "", "", 0))
								.toJSONString());
				return;
			} catch (CustomExceptions ce) {
				response.getWriter().append(ce.getCode() + " : " + ce.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
