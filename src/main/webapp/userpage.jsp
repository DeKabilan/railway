<%@ page import="com.railway.model.Train"%>
<%@ page import="com.railway.dao.TrainsDAO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>Dashboard</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

a {
	color: #007BFF;
	text-decoration: none;
	margin-right: 15px;
}

a:hover {
	text-decoration: underline;
}

h1 {
	color: #333;
}

form {
	margin-top: 20px;
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
</style>
</head>
<body>
	<h1>User Dashboard</h1>
	<a href="/railway/stationview.jsp">Click to Search Stations</a><br>
	<a href="/railway/trainview.jsp">Click to View All Trains</a><br>
	<a href="./history">Click to View Tickets</a><br>
	<a href="./login">Click to logout</a>
	<form action="./user" method="POST">
		<h2>Search Trains</h2>

		<label for="fromStation">From Station:</label> <input type="text"
			id="fromStation" name="fromStation" placeholder="Enter from station"
			required> <label for="toStation">To Station:</label> <input
			type="text" id="toStation" name="toStation"
			placeholder="Enter to station" required> <label
			for="travelDate">Date:</label> 
			<input type="date" id="travelDate" name="travelDate" value ="<%=(String)session.getAttribute("today") %>" required min = "<%=(String)session.getAttribute("today") %>">
			<input type="submit" value="Search Trains">
	</form>
	<%
	TrainsDAO trainsdao = new TrainsDAO();
	session.setAttribute("travelDate", (String)request.getParameter("travelDate"));
	ArrayList<Train> trainList = trainsdao.searchTrain((String)request.getParameter("fromStation"), (String)request.getParameter("toStation"),
	                		(String)request.getParameter("travelDate"),(String)session.getAttribute("today"),(Integer)session.getAttribute("hour"));
	session.setAttribute("source",(String)request.getParameter("fromStation"));
	session.setAttribute("destination",(String)request.getParameter("toStation"));
	session.setAttribute("trainList", trainList);
	session.setAttribute("date",(String)request.getParameter("travelDate"));
	%>
	<%
	if (trainList.size() != 0){
		session.setAttribute("trainList",trainList);
		response.sendRedirect("./search");
	}
	else{
		if((Boolean)session.getAttribute("firstTime")){
		session.setAttribute("firstTime",false);
		}
		else{
		out.println("<br>No Trains Found");
		}
	}
      %>

</body>
</html>
