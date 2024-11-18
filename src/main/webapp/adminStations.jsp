<%@ page import="com.railway.model.Station" %>
<%@ page import="com.railway.dao.StationsDAO"%>
<%@ page import="java.util.ArrayList" %>
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

<h2>Station Management</h2>
<nav>
    <a href="/railway/train">Click for Trains</a><br>
    <a href="/railway/stationview.jsp">Click to Search Station</a><br>
     <a href="/railway/trainview.jsp">Click to View All Trains</a><br>
    <a href="/railway/login">Click to Logout</a>
</nav>

<p>
    <%
        Station station = (Station) session.getAttribute("Station");
        if (station.getCode() == "") {
            out.println(station.getMessage());
            session.setAttribute("Station", new Station());
        } else {
            out.println("Code: " + station.getCode() + "<br>");
            out.println("Name: " + station.getName() + "<br>");
        }
    %>
</p>

<%
    // Fetch the list of station codes from DAO
    StationsDAO stationsDAO = new StationsDAO();
    ArrayList<String> stationCodes = new ArrayList<String>();
%>

<h2>Create Station</h2>
<form action="station" method="POST">
    <label for="stcode">Station Code:</label><br>
    <input type="text" id="stcode" name="stcode" maxlength="3" required><br>
    <label for="stname">Station Name:</label><br>
    <input type="text" id="stname" name="stname" required><br>
    
    <input type="hidden" name="type" value="create">
    <input type="submit" value="Create Station">
</form>

<h2>Update Station</h2>
<form action="station" method="POST">
    <label for="stcodeold">Current Station Code:</label><br>
    <input type ="text" id="stcodeold" name="stcodeold" required><br>
    <label for="stcodenew">New Station Code:</label><br>
    <input type="text" id="stcodenew" name="stcodenew" maxlength="3" required><br>
    <label for="stnamenew">New Station Name:</label><br>
    <input type="text" id="stnamenew" name="stnamenew" required><br>
    
    <input type="hidden" name="type" value="update">
    <input type="submit" value="Update Station">
</form>

<h2>Delete Station</h2>
<form action="station" method="GET">
    <label for="stcode">Station Code:</label><br>
    <input type ="text" id="stcode" name="stcode" required><br>
    <input type="hidden" name="type" value="delete">
    <input type="submit" value="Delete Station">
</form>

</body>
</html>
