<%@ page import="com.railway.model.Train"%>
<%@ page import="com.railway.dao.StationsDAO"%>
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

            const tname = document.getElementById("tnamec").value;
            const talgorithm = document.getElementById("tseatalgoc").value;
            const tsource = document.getElementById("tsourcec").value;
            const tdestination = document.getElementById("tdestinationc").value;
            const tdeparture = document.getElementById("tdeparturec").value;
            const tarrival = document.getElementById("tarrivalc").value;

            const periodicity = [];
            const days = document.querySelectorAll('input[name="days[]"]:checked');
            days.forEach(day => {
                periodicity.push(day.value);
            });

            const intermediate = document.getElementById("tintermediatec").value;
            const acNoStr = document.getElementById("tacnoc").value;
            const acSeatsStr = document.getElementById("tacseatsc").value;
            const acCostStr = document.getElementById("taccostc").value;
            const nonAcNoStr = document.getElementById("tnonacnoc").value;
            const nonAcSeatsStr = document.getElementById("tnonacseatsc").value;
            const nonAcCostStr = document.getElementById("tnonaccostc").value;
			clearAllResponse();
            const data = {
                "name": tname,
                "algorithm": talgorithm,
                "source": tsource,
                "destination": tdestination,
                "departure": tdeparture,
                "arrival": tarrival,
                "periodicity": periodicity,
                "intermediate": intermediate,
                "acno": acNoStr,
                "acseat": acSeatsStr,
                "accost": acCostStr,
                "nonacno": nonAcNoStr,
                "nonacseat": nonAcSeatsStr,
                "nonaccost": nonAcCostStr,
                "type": "train"
            };

            fetch("train", {
                method: "POST",
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
     	
     	
     	
     	function sendPutRequest(event) {
            event.preventDefault(); 

            const toldname = document.getElementById("toldnameu").value;
            const tname = document.getElementById("tnameu").value;
            const talgorithm = document.getElementById("tseatalgou").value;
            const tsource = document.getElementById("tsourceu").value;
            const tdestination = document.getElementById("tdestinationu").value;
            const tdeparture = document.getElementById("tdepartureu").value;
            const tarrival = document.getElementById("tarrivalu").value;

            const periodicity = [];
            const days = document.querySelectorAll('input[name="daysu[]"]:checked');
            days.forEach(day => {
                periodicity.push(day.value);
            });

            const intermediate = document.getElementById("tintermediateu").value;
            const acNoStr = document.getElementById("tacnou").value;
            const acSeatsStr = document.getElementById("tacseatsu").value;
            const acCostStr = document.getElementById("taccostu").value;
            const nonAcNoStr = document.getElementById("tnonacnou").value;
            const nonAcSeatsStr = document.getElementById("tnonacseatsu").value;
            const nonAcCostStr = document.getElementById("tnonaccostu").value;
            clearAllResponse();
            const data = {
            	"oldname": toldname,
                "name": tname,
                "algorithm": talgorithm,
                "source": tsource,
                "destination": tdestination,
                "departure": tdeparture,
                "arrival": tarrival,
                "periodicity": periodicity,
                "intermediate": intermediate,
                "acno": acNoStr,
                "acseat": acSeatsStr,
                "accost": acCostStr,
                "nonacno": nonAcNoStr,
                "nonacseat": nonAcSeatsStr,
                "nonaccost": nonAcCostStr,
                "type": "train"
            };

            fetch("train", {
                method: "PUT",
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
        
        function sendDeleteRequest(event) {
            event.preventDefault();

            const tnamed = document.getElementById("tnamed").value;
            clearAllResponse();
            
            const data = {
                tnamed : tnamed,
                "type":"train"
            };

            fetch("train", {
                method: "DELETE",
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
        </script>
<style>
input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
	-webkit-appearance: none;
	margin: 0;
}

input[type="number"] {
	-moz-appearance: textfield;
}

fieldset {
	width: 300px;
	padding: 10px;
	border: 1px solid #ddd;
	margin-bottom: 10px;
}

legend {
	font-weight: bold;
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

input[type="text"], select, input[type="date"], input[type="number"],
	input[type="time"] {
	display: block;
	margin: 10px 0;
	padding: 10px;
	width: 300px;
}

input[type="submit"] {
	padding: 10px; SELECT * FROM IRTC.Compartments;
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
		<a href="/railway/station">Click for Stations</a><br> <a
			href="/railway/stationview.jsp">Click to Search Station</a><br>
		<a href="/railway/trainview.jsp">Click to search Trains</a><br> <a
			href="/railway/login">Click to Logout</a>
	</nav>





	<h2>Create Train</h2>
	<form onsubmit="sendPostRequest(event)">
		<label for="tname">Train Name:</label><br> <input type="text"
			id="tnamec" name="tname" required><br> <label
			for="tsource">Source:</label><br> <input type="text"
			id="tsourcec" name="tsource" required><br> <label
			for="tdestination">Destination:</label><br> <input type="text"
			id="tdestinationc" name="tdestination" required><br> <label
			for="tseatalgo">Seat Algorithm:</label> <select id="tseatalgoc"
			name="tseatalgo" required>
			<option value="">Select Algorithm</option>
			<option value="SCATTERRED">Scattereed</option>
			<option value="ORDERED">Ordered</option>
			<option value="EVEN_FIRST">Even First</option>
			<option value="ODD_FIRST">Odd First</option>
		</select> <br> <label for="tdeparture">Departure:</label><br> <input
			type="time" id="tdeparturec" name="tdeparture" required><br>
		<label for="tarrival">Arrival:</label><br> <input type="time"
			id="tarrivalc" name="tarrival" required><br>
		<fieldset>
			<legend>Periodicity</legend>
			<label><input type="checkbox" name="days[]" value="MON">
				Monday</label> <label><input type="checkbox" name="days[]"
				value="TUE"> Tuesday</label> <label><input type="checkbox"
				name="days[]" value="WED"> Wednesday</label><br> <label><input
				type="checkbox" name="days[]" value="THU"> Thursday</label> <label><input
				type="checkbox" name="days[]" value="FRI"> Friday</label> <label><input
				type="checkbox" name="days[]" value="SAT"> Saturday</label><br>
			<label><input type="checkbox" name="days[]" value="SUN">
				Sunday</label> <br>
			<br>
		</fieldset>
		<br> <label for="tintermediate">Intermediate Stops (comma
			to separate):</label><br> <input type="text" id="tintermediatec"
			name="tintermediate" required><br> <label for="tacno">AC
			coach amount:</label><br> <input type="number" id="tacnoc" name="tacno"
			required><br> <label for="tacseats">AC Seats in
			each coach:</label><br> <input type="number" id="tacseatsc"
			name="tacseats" required><br> <label for="taccost">AC
			Seat Cost:</label><br> <input type="number" id="taccostc" name="taccost"
			required><br> <label for="tnonacno">NON-AC coach
			amount:</label><br> <input type="number" id="tnonacnoc" name="tnonacno"
			required><br> <label for="tnonacseats">NON-AC
			Seats in each coach:</label><br> <input type="number" id="tnonacseatsc"
			name="tnonacseats" required><br> <label for="tnonaccost">NON-AC
			Seat Cost:</label><br> <input type="number" id="tnonaccostc"
			name="tnonaccost" required><br> <input type="submit"
			value="Create Train">
	</form>
	<div id="response"></div>

	<h2>Update Train</h2>
	<form onsubmit="sendPutRequest(event)">
		<label for="toldname">Old Train Name:</label><br> <input
			type="text" id="toldnameu" name="tname"><br> <label
			for="tname">Train Name:</label><br> <input type="text"
			id="tnameu" name="tname"><br> <label for="tsource">Source:</label><br>
		<input type="text" id="tsourceu" name="tsource"><br> <label
			for="tdestination">Destination:</label><br> <input type="text"
			id="tdestinationu" name="tdestination"><br> <label
			for="tseatalgo">Seat Algorithm:</label> <select id="tseatalgou"
			name="tseatalgo">
			<option value="">Select Algorithm</option>
			<option value="SCATTERRED">Scattereed</option>
			<option value="ORDERED">Ordered</option>
			<option value="EVEN_FIRST">Even First</option>
			<option value="ODD_FIRST">Odd First</option>
		</select> <br> <label for="tdeparture">Departure:</label><br> <input
			type="time" id="tdepartureu" name="tdeparture"><br> <label
			for="tarrival">Arrival:</label><br> <input type="time"
			id="tarrivalu" name="tarrival"><br>
		<fieldset>
			<legend>Periodicity</legend>
			<label><input type="checkbox" name="daysu[]" value="MON">
				Monday</label> <label><input type="checkbox" name="daysu[]"
				value="TUE"> Tuesday</label> <label><input type="checkbox"
				name="daysu[]" value="WED"> Wednesday</label><br> <label><input
				type="checkbox" name="daysu[]" value="THU"> Thursday</label> <label><input
				type="checkbox" name="daysu[]" value="FRI"> Friday</label> <label><input
				type="checkbox" name="daysu[]" value="SAT"> Saturday</label><br>
			<label><input type="checkbox" name="daysu[]" value="SUN">
				Sunday</label> <br>
			<br>
		</fieldset>
		<br> <label for="tintermediate">Intermediate Stops (comma
			to separate):</label><br> <input type="text" id="tintermediateu"
			name="tintermediate"><br> <label for="tacno">AC
			coach amount:</label><br> <input type="number" id="tacnou" name="tacno"><br>
		<label for="tacseats">AC Seats in each coach:</label><br> <input
			type="number" id="tacseatsu" name="tacseats"><br> <label
			for="taccost">AC Seat Cost:</label><br> <input type="number"
			id="taccostu" name="taccost"><br> <label for="tnonacno">NON-AC
			coach amount:</label><br> <input type="number" id="tnonacnou"
			name="tnonacno"><br> <label for="tnonacseats">NON-AC
			Seats in each coach:</label><br> <input type="number" id="tnonacseatsu"
			name="tnonacseats"><br> <label for="tnonaccost">NON-AC
			Seat Cost:</label><br> <input type="number" id="tnonaccostu"
			name="tnonaccost"><br> <input type="submit"
			value="Update Train">
	</form>
	<div id="response1"></div>

	<h2>Delete Train</h2>
	<form onsubmit="sendDeleteRequest(event)">
		<label for="tname">Train Name:</label><br> <input type="text"
			id="tnamed" name="tnamed" required><br> <input
			type="submit" value="Delete Train">
	</form>
	<div id="response2"></div>

</body>
</html>
