package com.railway.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.railway.decorator.ErrorDecorator;
import com.railway.utils.CustomExceptions;

@WebServlet("/api/*")
public class FallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ErrorDecorator errordecorator = new ErrorDecorator();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
}