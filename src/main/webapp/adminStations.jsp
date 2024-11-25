<%@ page import="com.railway.model.Station"%>
<%@ page import="com.railway.dao.StationsDAO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Train Management</title>
<script>
     	function clearAllResponse(){
     		document.getElementById("response").innerHTML = "";
     		document.getElementById("response1").innerHTML = "";
     		document.getElementById("response2").innerHTML = "";
     	}
     	
     	function sendPostRequest(event) {
            event.preventDefault();

            const stcodec = document.getElementById("stcodec").value;
            const stnamec = document.getElementById("stnamec").value;
			clearAllResponse();
            const data = {
                stcodec: stcodec,
                stnamec: stnamec,
                "type":"station"
            };

            fetch("station", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            })
            .then(response => response.text())
            .then(result => {
                document.getElementById("response2").innerHTML = result;
            })
        }
     	
        function sendPutRequest(event) {
            event.preventDefault();

            const stcodeold = document.getElementById("stcodeold").value;
            const stcodenew = document.getElementById("stcodenew").value;
            const stnamenew = document.getElementById("stnamenew").value;
			clearAllResponse();
            const data = {
                stcodeold: stcodeold,
                stcodenew: stcodenew,
                stnamenew: stnamenew,
                "type":"station"
            };

            fetch("station", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            })
            .then(response => response.text())
            .then(result => {
                document.getElementById("response").innerHTML = result;
            })
        }
        
        
        function sendDeleteRequest(event) {
            event.preventDefault();

            const stcoded = document.getElementById("stcoded").value;
            clearAllResponse();
            
            const data = {
                stcoded : stcoded,
                "type":"station"
            };

            fetch("station", {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            })
            .then(response => response.text())
            .then(result => {
                document.getElementById("response1").innerHTML = result;
            })
        }
    </script>
<style>
input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
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
		<a href="/railway/train">Click for Trains</a><br> <a
			href="/railway/stationview.jsp">Click to Search Station</a><br>
		<a href="/railway/trainview.jsp">Click to Search Trains</a><br> <a
			href="/railway/login">Click to Logout</a>
	</nav>

	<%
// Fetch the list of station codes from DAO
    StationsDAO stationsDAO = new StationsDAO();
    ArrayList<String> stationCodes = new ArrayList<String>();
%>

	<h2>Create Station</h2>
	<form onsubmit="sendPostRequest(event)">
		<label for="stcode">Station Code:</label><br> <input type="text"
			id="stcodec" name="stcodec" maxlength="3" required><br>
		<label for="stname">Station Name:</label><br> <input type="text"
			id="stnamec" name="stnamec" required><br> <input
			type="hidden" name="type" value="create"> <input
			type="submit" value="Create Station">
		<div id="response2"></div>
	</form>
	<br>
	<div id="response2"></div>

	<h1>Update Station</h1>
	<form onsubmit="sendPutRequest(event)">
		<label for="stcodeold">Current Station Code:</label><br> <input
			type="text" id="stcodeold" name="stcodeold" required><br>
		<label for="stcodenew">New Station Code:</label><br> <input
			type="text" id="stcodenew" name="stcodenew" maxlength="3" required><br>
		<label for="stnamenew">New Station Name:</label><br> <input
			type="text" id="stnamenew" name="stnamenew" required><br>
		<input type="submit" value="Update Station">
	</form>
	<div id="response"></div>

	<h1>Delete Station</h1>
	<form onsubmit="sendDeleteRequest(event)">
		<label for="stcode">Station Code:</label><br> <input type="text"
			id="stcoded" name="stcoded" required><br> <input
			type="submit" value="Delete Station">
	</form>
	<div id="response1"></div>

</body>
</html>
