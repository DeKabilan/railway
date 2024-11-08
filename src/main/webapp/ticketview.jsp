<%@ page import="java.util.ArrayList" %>
<%@ page import="com.railway.model.TicketBatch" %>
<%@ page import="com.railway.model.Ticket" %>
<%@ page import="com.railway.decorator.PDFDecorator" %>
<%@ page import="com.railway.dao.TicketsDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Train List</title>
    <style>
    
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            color: #333;
        }
        a {
            color: #007bff;
            text-decoration: none;
            margin-right: 15px;
        }
        a:hover {
            text-decoration: underline;
        }
        table {
            width: 100%;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        .pagination {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h2>Ticket List</h2>
    <a href="./history.jsp">Go Back</a>
    
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Age</th>
                <th>Mail</th>
                <th>Seat</th>
                <th>Book Date</th>
                <th>Travel Date</th>
            </tr>
        </thead>
        <tbody>
        <%
        	PDFDecorator pdfdecorator = new PDFDecorator();
            TicketsDAO ticketsdao = new TicketsDAO();
            Integer groupid = Integer.parseInt(request.getParameter("groupid"));
            ArrayList<Ticket> ticketList = ticketsdao.getTicket(ticketsdao.getBatch(groupid));
            pdfdecorator.createTicket(ticketsdao.getBatch(groupid));
			%>
			
			<%
            if (ticketList != null && !ticketList.isEmpty()) {
                for (Ticket ticket : ticketList) {
                	
        %>
            <tr>
                <td><%= ticket.getName() %></td>
                <td><%= ticket.getAge() %></td>
                
                <%
                if(ticket.getMail()=="" || ticket.getMail()==null){
                %>
                	 <td>Not Provided</td>
                <%	
                }
                else{
               %>
               	 <td><%= ticket.getMail() %></td>
               <%	             	
                }
                %>
                <td><%= ticket.getSeatNo() %></td>
               	<td><%= ticket.getBookDate() %></td>
                <td><%= ticket.getTravelDate() %></td>
            </tr>
        <%
                }
            }
            out.println("<br>");
           
        %>
        </tbody>
    </table>
    <br>
	<a href="<%= request.getContextPath() %>/download">Download Ticket</a>
</body>
</html>
