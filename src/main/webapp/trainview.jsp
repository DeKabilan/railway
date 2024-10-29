<%@ page import="java.util.ArrayList" %>
<%@ page import="com.railway.model.Train" %>
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
        .pagination {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h2>Station List</h2>
    <a href="./train">Back to Admin Page</a>
    <a href="./user">Back to User Page</a>
    
    <table>
        <thead>
            <tr>
                <th>Train Name</th>
                <th>Source</th>
                <th>Destination</th>
                <th>Departure</th>
                <th>Arrival</th>
            </tr>
        </thead>
        <tbody>
        <%
            int pageno = 1; 
            int amount = 5;
            TrainsDAO trainsdao = new TrainsDAO();

            if (request.getParameter("page") != null) {
                pageno = Integer.parseInt(request.getParameter("page"));
            }

            ArrayList<Train> trainList = trainsdao.getPage((pageno - 1) * amount, amount);
            
            out.println((pageno - 1) * amount + " - " + 
                        Math.min(pageno * amount, trainsdao.getAmountOfData()) + 
                        " of " + trainsdao.getAmountOfData());
			%>
			
			<%
            if (trainList != null && !trainList.isEmpty()) {
                for (Train train : trainList) {
                	
        %>
            <tr>
                <td><%= train.getName() %></td>
                <td><%= train.getSource() %></td>
                 <td><%= train.getDestination() %></td>
                <td><%= train.getDeparture() %></td>
                <td><%= train.getArrival() %></td>
            </tr>
        <%
                }
            }
            int totalRecords = trainsdao.getAmountOfData();
            int totalPages = (int) Math.ceil(totalRecords * 1.0 / amount);
        %>
        </tbody>
    </table>

    <div class="pagination">
        <%
            if (pageno > 1) {
        %>
            <a href="trainview.jsp?page=<%= pageno - 1 %>">Previous</a>
        <%
            }

            for (int i = 1; i <= totalPages; i++) {
        %>
            <a href="trainview.jsp?page=<%= i %>"><%= i %></a> 
        <%
            }

            if (pageno < totalPages) { 
        %>
            <a href="trainview.jsp?page=<%= pageno + 1 %>">Next</a>
        <%
            }
        %>
    </div>
</body>
</html>
