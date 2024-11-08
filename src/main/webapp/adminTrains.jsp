<%@ page import="com.railway.model.Train" %>
<%@ page import="com.railway.dao.StationsDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Train Management</title>
    <style>
        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }
        input[type="number"] {
            -moz-appearance: textfield;
        }
    
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

input[type="text"], select, input[type="date"], input[type="number"],input[type="time"] {
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

<h2>Train Management</h2>
<nav>
    <a href="/railway/station">Click for Stations</a><br>
        <a href="/railway/trainview.jsp">Click to View All Trains</a><br>
    <a href="/railway/login">Click to Logout</a>
</nav>

<p>
    <%
        Train train = (Train) session.getAttribute("Train");
        if (train.getID() == -1) {
            out.println(train.getText());
            session.setAttribute("outputTrain", new Train());
        } else {
            out.println("Name: " + train.getName() + "<br>");
            out.println("Source: " + train.getSource() + "<br>");
            out.println("Destination: " + train.getDestination() + "<br>");
            out.println("Departure: " + train.getDeparture() + "<br>");
            out.println("Arrival: " + train.getArrival() + "<br>");
            out.println("Periodicity: " + train.getPeriodicity() + "<br>");
            out.println("Intermediate: " + train.getIntermediate() + "<br>");
            if (train.getACCompartmentNo() != 0) {
                out.println("--AC COACHES--" + "<br>");
                out.println("Coaches: " + train.getACCompartmentNo() + "<br>");
                out.println("Seats: " + train.getACCompartmentSeats() + "<br>");
                out.println("Price: Rs. " + train.getACCompartmentCost() + "/-<br>");
            }
            if (train.getNONACCompartmentNo() != 0) {
                out.println("--Non-AC COACHES--" + "<br>");
                out.println("Coaches: " + train.getNONACCompartmentNo() + "<br>");
                out.println("Seats: " + train.getNONACCompartmentSeats() + "<br>");
                out.println("Price: Rs. " + train.getNONACCompartmentCost() + "/-<br>");
            }
        }
    %>
</p>

<h2>Read Train</h2>
<form action="train" method="GET">
    <label for="tname">Train Name:</label><br>
    <input type="text" id="tname" name="tname" required><br>
    <input type="hidden" name="type" value="read">
    <input type="submit" value="Read Train">
</form>

<h2>Create Train</h2>
<form action="train" method="POST">
    <label for="tname">Train Name:</label><br>
    <input type="text" id="tname" name="tname" required><br>
    <label for="tsource">Source:</label><br>
    <input type="text" id="tsource" name="tsource" required><br>
    <label for="tdestination">Destination:</label><br>
    <input type="text" id="tdestination" name="tdestination" required><br>
    
    <label for="tseatalgo">Seat Algorithm:</label> 
    <select id="tseatalgo" name="tseatalgo" required>
			<option value="">Select Algorithm</option>
			<option value="SCATTERRED">Scattereed</option>
			<option value="ORDERED">Ordered</option>
			<option value="EVEN_FIRST">Even First</option>
			<option value="ODD_FIRST">Odd First</option>
	</select> <br>
  
    <label for="tdeparture">Departure:</label><br>
    <input type="time" id="tdeparture" name="tdeparture" required><br>
    <label for="tarrival">Arrival:</label><br>
    <input type="time" id="tarrival" name="tarrival" required><br>
    <label for="tperiodicity">Periodicity (comma to separate):</label><br>
    <input type="text" id="tperiodicity" name="tperiodicity" required><br>
    <label for="tintermediate">Intermediate Stops (comma to separate):</label><br>
    <input type="text" id="tintermediate" name="tintermediate" required><br>
    <label for="tacno">AC coach amount:</label><br>
    <input type="number" id="tacno" name="tacno" required><br>
    <label for="tacseats">AC Seats in each coach:</label><br>
    <input type="number" id="tacseats" name="tacseats" required><br>
    <label for="taccost">AC Seat Cost:</label><br>
    <input type="number" id="taccost" name="taccost" required><br>
    <label for="tnonacno">NON-AC coach amount:</label><br>
    <input type="number" id="tnonacno" name="tnonacno" required><br>
    <label for="tnonacseats">NON-AC Seats in each coach:</label><br>
    <input type="number" id="tnonacseats" name="tnonacseats" required><br>
    <label for="tnonaccost">NON-AC Seat Cost:</label><br>
    <input type="number" id="tnonaccost" name="tnonaccost" required><br>
    <input type="hidden" name="type" value="create">
    <input type="submit" value="Create Train">
</form>

<h2>Update Train</h2>
<form action="train" method="POST">
    <label for="oldtname">Train to Change*:</label><br>
    <input type="text" id="oldtname" name="oldtname" required><br>
    <label for="tname">Train Name:</label><br>
    <input type="text" id="tname" name="tname"><br>
    <label for="tsource">Source:</label><br>
    <input type="text" id="tsource" name="tsource"><br>
    <label for="tdestination">Destination:</label><br>
    <input type="text" id="tdestination" name="tdestination"><br>
    <label for="tseatalgo">Seat Algorithm:</label> 
    
    <select id="tseatalgo" name="tseatalgo">
			<option value="">Select Algorithm</option>
			<option value="SCATTERRED">Scattereed</option>
			<option value="ORDERED">Ordered</option>
			<option value="EVEN_FIRST">Even First</option>
			<option value="ODD_FIRST">Odd First</option>
	</select> <br>

    <label for="tdeparture">Departure:</label><br>
    <input type="time" id="tdeparture" name="tdeparture"><br>
    <label for="tarrival">Arrival:</label><br>
    <input type="time" id="tarrival" name="tarrival"><br>
    <label for="tperiodicity">Periodicity (comma to separate):</label><br>
    <input type="text" id="tperiodicity" name="tperiodicity"><br>
    <label for="tintermediate">Intermediate Stops (comma to separate):</label><br>
    <input type="text" id="tintermediate" name="tintermediate"><br>
    <label for="tacno">AC coach amount:</label><br>
    <input type="number" id="tacno" name="tacno"><br>
    <label for="tacseats">AC Seats in each coach:</label><br>
    <input type="number" id="tacseats" name="tacseats"><br>
    <label for="taccost">AC Seat Cost:</label><br>
    <input type="number" id="taccost" name="taccost"><br>
    <label for="tnonacno">NON-AC coach amount:</label><br>
    <input type="number" id="tnonacno" name="tnonacno"><br>
    <label for="tnonacseats">NON-AC Seats in each coach:</label><br>
    <input type="number" id="tnonacseats" name="tnonacseats"><br>
    <label for="tnonaccost">NON-AC Seat Cost:</label><br>
    <input type="number" id="tnonaccost" name="tnonaccost"><br>
    <input type="hidden" name="type" value="update">
    <input type="submit" value="Update Train">
</form>

<h2>Delete Train</h2>
<form action="train" method="POST">
    <label for="tname">Train Name:</label><br>
    <input type="text" id="tname" name="tname" required><br>
    <input type="hidden" name="type" value="delete">
    <input type="submit" value="Delete Train">
</form>

</body>
</html>
