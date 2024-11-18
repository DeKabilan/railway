package com.railway.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.railway.decorator.PDFDecorator;
import com.railway.handlers.DownloadHandler;
import com.railway.utils.CustomExceptions;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	PDFDecorator pdfdecorator = new PDFDecorator();
	DownloadHandler download = new DownloadHandler();
	private static final long serialVersionUID = 1L;
	private String PDF_DIRECTORY = "/home/kabilan-22527/eclipse-workspace/railway/src/main/resources";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		download.downloadPDFtoLocal(Integer.parseInt(request.getParameter("groupid")));
		HttpSession session = request.getSession();
		try {
			try {
				if (session.getAttribute("userRole")==null || !((String) session.getAttribute("userRole") == "user")) {
					throw new CustomExceptions(CustomExceptions.Exceptions.ACCESS_DENIED);
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
			} catch (CustomExceptions ce) {
				RequestDispatcher rd = request.getRequestDispatcher("errornoaccess.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
