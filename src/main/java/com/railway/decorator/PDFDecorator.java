package com.railway.decorator;

import java.io.FileNotFoundException;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.railway.dao.StationsDAO;
import com.railway.dao.TicketsDAO;
import com.railway.dao.TrainsDAO;
import com.railway.model.Ticket;
import com.railway.model.TicketBatch;

public class PDFDecorator {

    public void createTicket(TicketBatch batch) {
    	TicketsDAO ticketsdao = new TicketsDAO();
    	StationsDAO stationsdao = new StationsDAO();
    	TrainsDAO trainsdao = new TrainsDAO();
        String outputPath = "/home/kabilan-22527/eclipse-workspace/railway/src/main/java/resources/ticket.pdf";
        
        try {
            PdfWriter writer = new PdfWriter(outputPath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);
            
            Paragraph title = new Paragraph("Railway Ticket")
                .setFontSize(15)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            document.add(createTicketInfo("TRAIN: "+batch.getTrain()));
            document.add(createTicketInfo("DEPARTURE TIME: "+trainsdao.getTrain(batch.getTrain()).getDeparture()));
            document.add(createTicketInfo("SOURCE: "+stationsdao.getStation(batch.getSource()).getName().toUpperCase()));
            document.add(createTicketInfo("DESTINATION: "+stationsdao.getStation(batch.getDestination()).getName().toUpperCase()));
            document.add(createTicketInfo("COST: Rs."+batch.getCost()+"/-"));
            int i = 0;
            for(Ticket ticket : ticketsdao.getTicket(batch)){
            	i+=1;
            	document.add(createTicketInfo("-------------------------------------------------------"));
            	document.add(new Paragraph("Passenger "+i ).setFontSize(10).setBold());
            	document.add(createTicketInfo("NAME: "+ticket.getName()));
            	document.add(createTicketInfo("AGE: "+ticket.getAge()));
            	document.add(createTicketInfo("EMAIL: "+ticket.getMail()));
            	document.add(createTicketInfo("SEAT: "+ticket.getSeatNo()));
            }
            document.add(createTicketInfo("-------------------------------------------------------"));
            document.add(createTicketInfo("BOOK DATE: "+batch.getBookDate()));
            document.add(createTicketInfo("TRAVEL DATE: "+batch.getTravelDate()));
            Paragraph footer = new Paragraph("Thank you for Travelling!")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER);
            document.add(footer);
            
            document.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Paragraph createTicketInfo(String text) {
        return new Paragraph(new Text(text)
            .setFontSize(8));
    }
}
