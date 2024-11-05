<%@ page import="java.util.ArrayList" %>
<%@ page import="com.railway.model.TicketBatch" %>
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
    <a href="./user">Back to Dashboard</a>
    
    <table>
        <thead>
            <tr>
                <th>Train</th>
                <th>Passengers</th>
                <th>Cost</th>
                <th>Source</th>
                <th>Destination</th>
                <th>Book Date</th>
                <th>Travel Date</th>
                <th>View Ticket</th>
            </tr>
        </thead>
        <tbody>
        <%
            int pageno = 1; 
            int amount = 5;
            TicketsDAO ticketsdao = new TicketsDAO();

            if (request.getParameter("page") != null) {
                pageno = Integer.parseInt(request.getParameter("page"));
            }

            ArrayList<TicketBatch> ticketBatchList = ticketsdao.getPage((String)session.getAttribute("userName"),(pageno - 1) * amount, amount);
            
            out.println((pageno - 1) * amount + " - " + 
                        Math.min(pageno * amount, ticketsdao.getAmountOfData()) + 
                        " of " + ticketsdao.getAmountOfData());
			%>
			
			<%
            if (ticketBatchList != null && !ticketBatchList.isEmpty()) {
                for (TicketBatch ticket : ticketBatchList) {
                	
        %>
            <tr>
                <td><%= ticket.getTrain() %></td>
                <td><%= ticket.getTickets().size() %></td>
                <td><%= ticket.getCost() %></td>
                <td><%= ticket.getSource() %></td>
                 <td><%= ticket.getDestination() %></td>
                <td><%= ticket.getBookDate() %></td>
                <td><%= ticket.getTravelDate() %></td>
                 <td> <a href="ticketview.jsp?groupid=<%=ticket.getBatchID()%>">View Ticket</a></td>
            </tr>
        <%
                }
            }
            int totalRecords = ticketsdao.getAmountOfData();
            int totalPages = (int) Math.ceil(totalRecords * 1.0 / amount);
        %>
        </tbody>
    </table>

    <div class="pagination">
        <%
            if (pageno > 1) {
        %>
            <a href="history.jsp?page=<%= pageno - 1 %>">Previous</a>
        <%
            }

            for (int i = 1; i <= totalPages; i++) {
        %>
            <a href="history.jsp?page=<%= i %>&"><%= i %></a> 
        <%
            }

            if (pageno < totalPages) { 
        %>
            <a href="history.jsp?page=<%= pageno + 1 %>">Next</a>
        <%
            }
        %>
    </div>
</body>
</html>
