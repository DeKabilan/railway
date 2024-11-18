package com.railway.handlers;

import com.railway.dao.TicketsDAO;
import com.railway.decorator.PDFDecorator;

public class DownloadHandler {
	PDFDecorator pdfdecorator = new PDFDecorator();
	TicketsDAO ticketsdao = new TicketsDAO();
	
	public void downloadPDFtoLocal(Integer groupid) {
		pdfdecorator.createTicket(ticketsdao.getBatch(groupid));
	}
}
