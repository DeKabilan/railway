
package com.railway.servlets;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(!((String)session.getAttribute("userRole")).equals("user")) {
			RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
			rd.forward(request, response);
			return;
		}
		session.setAttribute("firstTime", true);
		RequestDispatcher rd = request.getRequestDispatcher("userpage.jsp");
		rd.forward(request, response);
		return;
		}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("userpage.jsp");
		rd.forward(request, response);
		return;
	}

}
