<%@ page import="java.util.ArrayList" %>
<%@ page import="com.railway.model.Station" %>
<%@ page import="com.railway.dao.StationsDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Station List</title>
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
        	width: 30%;
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
    <%
    if(((String)session.getAttribute("userRole")).equals("admin")){
    %>
    <a href="./station">Back</a>
    <%
    }
    else if(((String)session.getAttribute("userRole")).equals("user")){
    	%>
    <a href="./user">Back</a>
    	
    	<%
    }
    %>
    
    <table>
        <thead>
            <tr>
                <th>Code</th>
                <th>Name</th>
            </tr>
        </thead>
        <tbody>
        <%
            int pageno = 1; 
            int amount = 100;
            StationsDAO stationsdao = new StationsDAO();

            if (request.getParameter("page") != null) {
                pageno = Integer.parseInt(request.getParameter("page"));
            }

            ArrayList<Station> stationList = stationsdao.getPage((pageno - 1) * amount, amount);
            
            out.println((pageno - 1) * amount + " - " + 
                        Math.min(pageno * amount, stationsdao.getAmountOfData()) + 
                        " of " + stationsdao.getAmountOfData());
			%>
			
			<%
            if (stationList != null && !stationList.isEmpty()) {
                for (Station station : stationList) {
                	
        %>
            <tr>
                <td><%= station.getCode() %></td>
                <td><%= station.getName() %></td>
            </tr>
        <%
                }
            }
            int totalRecords = stationsdao.getAmountOfData();
            int totalPages = (int) Math.ceil(totalRecords * 1.0 / amount);
        %>
        </tbody>
    </table>

    <div class="pagination">
        <%
            if (pageno > 1) {
        %>
            <a href="stationview.jsp?page=<%= pageno - 1 %>">Previous</a>
        <%
            }

            for (int i = 1; i <= totalPages; i++) {
        %>
            <a href="stationview.jsp?page=<%= i %>"><%= i %></a> 
        <%
            }

            if (pageno < totalPages) { 
        %>
            <a href="stationview.jsp?page=<%= pageno + 1 %>">Next</a>
        <%
            }
        %>
    </div>
</body>
</html>
