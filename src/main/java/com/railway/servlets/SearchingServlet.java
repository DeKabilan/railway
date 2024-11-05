
package com.railway.servlets;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.railway.dao.TrainsDAO;
import com.railway.model.Train;


@WebServlet({"/search","/book"})
public class SearchingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TrainsDAO trainsdao = new TrainsDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at GET: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		if(!((String)session.getAttribute("userRole") == "user")) {
			RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
			rd.forward(request, response);
			return;
		}
		String path = request.getServletPath();
		if(path.equals("/search")) {
			RequestDispatcher rd = request.getRequestDispatcher("search.jsp");
			rd.forward(request, response);
			return;
			
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		doGet(request, response);
		String path = request.getServletPath();
		if(path.equals("/search")) {
			RequestDispatcher rd = request.getRequestDispatcher("search.jsp");
			rd.forward(request, response);
			return;
			
		}
		if(path.equals("/book")) {
			Train train = trainsdao.getTrain((String)request.getParameter("train"));
			session.setAttribute("train",train);
			session.setAttribute("seats", request.getParameter("numOfTravelers"));

			RequestDispatcher rd = request.getRequestDispatcher("book.jsp");
			rd.forward(request, response);
			return;
			}

	}

}
