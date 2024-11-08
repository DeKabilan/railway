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
</style>
</head>
<body>
	<h1>Available Trains</h1>
	<a href="./user">Back</a>
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
				id="compartment" name="compartment">
				<option value="">Select Compartment</option>
				<option value="ACseats">AC</option>
				<option value="NONACseats">Non AC</option>
			</select>
			 <input type="submit" value="Filter Train">
		</form>
	</div>
	        <%
            TrainsDAO trainsdao = new TrainsDAO();	
            ArrayList<Train> trainList = trainsdao.additionalFilters((String)session.getAttribute("source"), (String)session.getAttribute("destination"),
            		request.getParameter("departureTime"), request.getParameter("arrivalTime"), request.getParameter("compartment"), (String)session.getAttribute("date"),
            		(String)session.getAttribute("today"),(Integer)session.getAttribute("hour"));
            if (trainList != null && !trainList.isEmpty()) {
            
			%>
	    <table>
        <thead>
            <tr>
                <th>Train Name</th>
                <th>Source</th>
                <th>Destination</th>
                <th>Departure</th>
                <th>Arrival</th>
                <th>AC Seats Left</th>
                <th>NON AC Seats Left</th>
            </tr>
        </thead>
        <tbody>

			
			<%
            
                for (Train train : trainList) {
                	
        %>
            <tr>
                <td><%= train.getName() %></td>
                <td><%= train.getSource() %></td>
                 <td><%= train.getDestination() %></td>
                <td><%= train.getDeparture() %></td>
                <td><%= train.getArrival() %></td>
                <td><%= trainsdao.getSeats("ACseats",train.getName()) %></td>
                <td><%= trainsdao.getSeats("NONACseats",train.getName()) %></td>
            </tr>
        <%
                }
		%>
        </tbody>
    	</table>
    			<br>
			<form action="./book" method = "POST">
			<label for="train">Trains:</label>
			<select id="train" name="train" required>
			<option value="">Select Time</option>
			<%
            
                for (Train train : trainList) {
                	
        %>
                <option value="<%= train.getName() %>"><%= train.getName() %></option>


        <%
                }
			%>
		</select> 
			<label for="numOfTravelers">No Of Travelers:</label>
			<input type="number" id="numOfTravelers" name="numOfTravelers" min = 1 max = 5 required>
			 <label for="compartment">Compartment:</label>
			  <select id="compartment" name="compartment" required>
				<option value="">Select Compartment</option>

				<option value="ACseats">AC</option>

				<option value="NONACseats">Non AC</option>
			</select>
			 <input type="submit" value="Select Train">
		</form>
		<%	
			if(((String)session.getAttribute("seatmessage"))!=null){
				out.println(session.getAttribute("seatmessage"));
			}
			session.setAttribute("seatmessage","");
            }
            else{
            	out.println("<br> No Trains Fround");
            }
        %>

</body>
</html>
