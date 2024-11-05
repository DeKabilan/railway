package com.railway.servlets;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.railway.dao.TicketsDAO;
import com.railway.dao.TrainsDAO;
import com.railway.dao.UserDAO;
import com.railway.handlers.AuthenticationHandler;


@WebServlet({"/login","/", "/register","/logout"})
public class AuthenticationServlet extends HttpServlet {
	UserDAO userdao = new UserDAO();
	TicketsDAO ticketsdao = new TicketsDAO();

	
	private static final long serialVersionUID = 1L;
	AuthenticationHandler loginhandler = new AuthenticationHandler();
	TrainsDAO trainsdao = new TrainsDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		trainsdao.trainListToDB("/home/kabilan-22527/eclipse-workspace/railway/src/main/java/resources/trains.json");
		
		String path = request.getServletPath();
		HttpSession session = request.getSession();
		String Result = "";
		session.setAttribute("userRole","null");
		session.setAttribute("userName","null");
		request.setAttribute("result",Result);
		response.getWriter().append("Served at: ").append(request.getContextPath());
		if("/register".equals(path)) {
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);	
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
		return;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String Result = "";
		session.setAttribute("userRole","null");
		String type = request.getParameter("type");
		if(type.equals("login")) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			if(loginhandler.validateAdmin(email, password)) {
				session.setAttribute("userRole","admin");
				session.setAttribute("userName",email);
				response.sendRedirect("./admin");
				return;
			}
			else if(loginhandler.validateUser(email, password)) {
				session.setAttribute("userRole","user");
				session.setAttribute("userName",email);
				response.sendRedirect("./user");
				return;
			}
			else {
					Result = "Username or Password is Incorrect";
					
			}
		}
		
		if(type.equals("register")) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			if(loginhandler.registerUser(email,password)) {
				Result = "Account Created Successfully";
			}
			else {
				Result = "User with Email ID already Exists";
			}
			request.setAttribute("result",Result);
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
			return;
		}
		
		request.setAttribute("result",Result);
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}
	}

