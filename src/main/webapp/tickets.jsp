<%@ page import="java.util.ArrayList" %>
<%@ page import="com.railway.model.Ticket" %>
<%@ page import="com.railway.dao.TrainsDAO" %>
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
        input[type="submit"] {
	padding: 10px;
	width: 300px;
	background-color: #007BFF;
	color: white;
	border: none;
	cursor: pointer;
}
        .pagination {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h2>Ticket List</h2>
    <%
    ArrayList<Ticket> ticketList = (ArrayList<Ticket>)session.getAttribute("ticketList");
    %>
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Age</th>
                <th>Email</th>
                <th>Compartment</th>
                <th>Seat No</th>
            </tr>
        </thead>
        <tbody>
        <%
        for(Ticket ticket : ticketList){
        	
        %>
            <tr>
                <td><%= ticket.getName() %></td>
                <td><%= ticket.getAge() %></td>
            <%
            if(ticket.getMail()=="" || ticket.getMail() == null){
            	%>
                 <td>Email Not Provided</td>
            <%
            }
            else{
            	%>
            	<td><%= ticket.getMail() %></td>
            <%
            }
            %>	
            
            <%
            if(ticket.getType().equals("ACseats")){
            	%>
                 <td>AC</td>
            <%
            }
            else{
            	%>
            	<td>NONAC</td>
            	<%
            }
            	%>
                <td><%= ticket.getSeatNo() %></td>
            </tr>
         <%
        	}
         %>
        </tbody>
    </table>
    <br>
    <%
	out.println("Total Cost: Rs. "+ (Integer)session.getAttribute("cost")+"/-");
    %>
    <br>
    <br>
    <form action = "./pay">
    <input type = submit value = "Book">
    </form>
    <br>
    <form action = "./cancel">
    <input type = submit value = "Cancel">
    </form>
</body>
</html>
