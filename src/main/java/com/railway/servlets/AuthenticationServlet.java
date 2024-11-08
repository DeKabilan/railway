package com.railway.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.railway.handlers.AuthenticationHandler;
import com.railway.utils.CustomExceptions;

@WebServlet({ "/login", "/", "/register", "/logout" })
public class AuthenticationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	AuthenticationHandler loginhandler = new AuthenticationHandler();
//	TrainsDAO trainsdao = new TrainsDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		trainsdao.trainListToDB("/home/kabilan-22527/eclipse-workspace/railway/src/main/java/resources/trains.json");
		HttpSession session = request.getSession();
		LocalDate currentDate = LocalDate.now();
		int hour = LocalTime.now().getHour();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = currentDate.format(formatter);
		session.setAttribute("today", date);
		session.setAttribute("hour", hour);
		String path = request.getServletPath();
		String Result = "";
		session.setAttribute("userRole", "null");
		session.setAttribute("userName", "null");
		request.setAttribute("result", Result);
		response.getWriter().append("Served at: ").append(request.getContextPath());
		if ("/register".equals(path)) {
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String Result = "";
		try {
			HttpSession session = request.getSession();
			session.setAttribute("userRole", "null");
			String type = request.getParameter("type");

			if (type.equals("login")) {

				try {
					String email = request.getParameter("email");
					String password = request.getParameter("password");
					if (loginhandler.validateAdmin(email, password)) {
						session.setAttribute("userRole", "admin");
						session.setAttribute("userName", email);
						response.sendRedirect("./admin");
						return;
					} else if (loginhandler.validateUser(email, password)) {
						session.setAttribute("userRole", "user");
						session.setAttribute("userName", email);
						response.sendRedirect("./user");
						return;
					} else {
						throw new CustomExceptions(CustomExceptions.Exceptions.USER_OR_PASSWORD_INCORRECT);

					}
				} catch (CustomExceptions ce) {
					Result = ce.getException().getMessage();
					request.setAttribute("result", Result);
					RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
					rd.forward(request, response);
					return;
				}

			}

			if (type.equals("register")) {
				try {
					String email = request.getParameter("email");
					String password = request.getParameter("password");
					if (loginhandler.registerUser(email, password)) {
						Result = "Account Created Successfully";
						request.setAttribute("result", Result);
						RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
						rd.forward(request, response);
						return;
					} else {
						throw new CustomExceptions(CustomExceptions.Exceptions.USER_ALREADY_EXISTS);
					}

				} catch (CustomExceptions ce) {
					Result = ce.getException().getMessage();
					request.setAttribute("result", Result);
					RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
					rd.forward(request, response);
					return;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// Logger
			// error response
		}

	}
}
