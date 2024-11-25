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

input[type="text"], select, input[type="date"], input[type="number"] {
	display: block;
	margin: 10px 0;
	padding: 10px;
	width: 300px;
}

input[type="submit"] {
	padding: 10px;
	width: 300px;
	background-color: #007BFF;
	color: white;
	border: none;
	cursor: pointer;
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
    <a href="./train">Back</a>
    <%
    }
    else if(((String)session.getAttribute("userRole")).equals("user")){
    	%>
    <a href="./user">Back</a>
    	
    	<%
    }
    %>
    
    
    <%
            int pageno = 1; 
            int amount = 5;
            int totalPages = 0;
            TrainsDAO trainsdao = new TrainsDAO();
			ArrayList<Train> trainList = new ArrayList<Train>();
			
			if (request.getParameter("tname") != null && request.getParameter("tname") != "" ) {
				if(request.getParameter("page") != null){
					pageno = Integer.parseInt(request.getParameter("page"));
				}
				trainList = trainsdao.searchTrain(request.getParameter("tname"), (pageno - 1) * amount,amount);
				out.println((pageno - 1) * amount + " - "
				+ Math.min(pageno * amount, trainsdao.getAmountOfDataSearch(request.getParameter("tname"))) + " of "
				+ trainsdao.getAmountOfDataSearch(request.getParameter("tname")));	
				int totalRecords = trainsdao.getAmountOfDataSearch(request.getParameter("tname"));
				totalPages = (int) Math.ceil(totalRecords * 1.0 / amount);
		         
		         
			}
			else{
				if (request.getParameter("page") != null) {
					pageno = Integer.parseInt(request.getParameter("page"));
				} 

				trainList = trainsdao.getPage((pageno - 1) * amount, amount);
				out.println((pageno - 1) * amount + " - " + Math.min(pageno * amount, trainsdao.getAmountOfData()) + " of "
				+ trainsdao.getAmountOfData());
				int totalRecords = trainsdao.getAmountOfData();
				totalPages = (int) Math.ceil(totalRecords * 1.0 / amount);
				
			}
				

           
			%>

    	<form action="./trainview.jsp">
		<br> <label for="stname">Train Name:</label><br> <input
			type="text" id="tname" name="tname"> <input
			type="submit" value="Search">
	</form>
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

        %>
        </tbody>
    </table>

	<div class="pagination">
		<%
		if (pageno > 1) {
		%>
		<a
			href="trainnview.jsp?page=<%=pageno - 1%>&tname=
		<%if (request.getParameter("tname") == null) {%>
			
			<%} else {%>
			<%=request.getParameter("tname")%>
			<%}%>">Previous</a>
		<%
		}

		for (int i = 1; i <= totalPages; i++) {
		%>
		<a
			href="trainview.jsp?page=<%=i%>&tname=
<%if (request.getParameter("tname") == null) {%>
			
			<%} else {%>
			<%=request.getParameter("tname")%>
			<%}%>"><%=i%></a>
		<%
		}

		if (pageno < totalPages) {
		%>
		<a
			href="trainview.jsp?page=<%=pageno + 1%>&tname=
		<%if (request.getParameter("tname") == null) {%>
			
			<%} else {%>
			<%=request.getParameter("tname")%>
			<%}%>
		">Next</a>
		<%
		}
		%>
    </div>
</body>
</html>
