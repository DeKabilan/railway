<%@ page import="com.railway.model.Train"%>
<%@ page import="com.railway.dao.TrainsDAO"%>
<%@ page import="com.railway.utils.Filters"%>
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
	<h1>Available Trains</h1>
	<div id="trainOptions">
		<form action="./search" method = "GET">
			<label for="departureTime">Departure
			Time:</label> <select id="departureTime" name="departureTime">
			<option value="">Select Time</option>
			<option value="Morning">Morning (6 AM - 12 PM)</option>
			<option value="Afternoon">Afternoon (12 PM - 6 PM)</option>
			<option value="Evening">Evening (6 PM - 12 AM)</option>
			<option value="Night">Night (12 AM - 6 AM)</option>
		</select> <label for="arrivalTime">Arrival Time:</label> <select
			id="arrivalTime" name="arrivalTime">
			<option value="">Select Time</option>
			<option value="Morning">Morning (6 AM - 12 PM)</option>
			<option value="Afternoon">Afternoon (12 PM - 6 PM)</option>
			<option value="Evening">Evening (6 PM - 12 AM)</option>
			<option value="Night">Night (12 AM - 6 AM)</option>
		</select>
		<label for="compartment">Compartment:</label> <select
				id="compartment" name="compartment" required>
				<option value="">Select Compartment</option>
				<option value="ACseats">AC</option>
				<option value="NONACseats">Non AC</option>
			</select>
			 <input type="submit" value="Filter Train">
		</form>
	</div>
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
			Filters filters = new Filters();
           	
            ArrayList<Train> allTrains = (ArrayList<Train>)session.getAttribute("trainList");
            ArrayList<Train> trainList = filters.additionalFilters(allTrains, request.getParameter("departureTime"), request.getParameter("arrivalTime"));
            System.out.println(trainList);
            System.out.println(allTrains);
            System.out.println(request.getParameter("departureTime"));
            
            
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
        %>
        </tbody>
    </table>

</body>
</html>
