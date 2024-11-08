package com.railway.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String PDF_DIRECTORY = "/home/kabilan-22527/eclipse-workspace/railway/src/main/java/resources";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(!((String)session.getAttribute("userRole") == "user")) {
			RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
			rd.forward(request, response);
			return;
		}
		 String fileName = "ticket.pdf";
	        File file = new File(PDF_DIRECTORY, fileName);
	        if (file.exists()) {
	            response.setContentType("application/pdf");
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

	            try (FileInputStream fileInputStream = new FileInputStream(file);
	                 ServletOutputStream outputStream = response.getOutputStream()) {

	                byte[] buffer = new byte[1024];
	                int bytesRead;
	                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
	                    outputStream.write(buffer, 0, bytesRead);
	                }

	                outputStream.flush();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}

}
