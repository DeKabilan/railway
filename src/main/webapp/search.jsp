<%@ page import="com.railway.model.Train"%>
<%@ page import="com.railway.dao.TrainsDAO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>Booking Page</title>
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
	<%
	ArrayList<Train> trainList = (ArrayList<Train>)session.getAttribute("trainList");
	%>
	<h1>Available Trains</h1>
	<div id="trainOptions">
		<form action="./book" method = "POST">

			<label for="availableTrains">Select a Train:</label> <select
				id="availableTrains" name="availableTrains" required>
				<option value="">Select a Train</option>


				<%  for (Train train : trainList) {
                	String name = train.getName();
            %>
				<option value="<%= name %>"><%= name %></option>
				<% 
           
                } 
            %>
			</select> <label for="compartment">Compartment:</label> <select
				id="compartment" name="compartment" required>
				<option value="">Select Compartment</option>
				<option value="ACseats">AC</option>
				<option value="NONACseats">Non AC</option>
			</select> <label for="numTravellers">No of Travellers:</label> <input
				type="number" id="numTravellers" name="numTravellers" min="1"
				max="5" required> <input type="submit" value="Book Train">
		</form>
	</div>
			<%
		if(!(session.getAttribute("seatStatus") == null)){
			out.println("<br>"+session.getAttribute("seatStatus"));
			session.setAttribute("seatStatus",null);
		}
		%>

</body>
</html>
