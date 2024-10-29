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

input[type="text"], select, input[type="date"], input[type="number"],input[type="email"] {
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
	<h1>Book Tickets</h1>
	<div id="trainOptions">
		<form action="./ticket" method = "POST">
			<%
			for(Integer i = 0; i<(Integer)session.getAttribute("seats");i++){
			out.println("<h2>Ticket "+(i+1)+"</h2>");
			%>
			<label for="name<%=i%>">Name:</label> 
			<input type="text" id="name<%=i%>" name="name<%=i%>" required> 
			<label for="age<%=i%>">Age:</label> 
			<input type="number" id="age<%=i%>" name="age<%=i%>" min="1" max="100" required> 
			<label for="email<%=i%>">Email:</label> 
			<input type="email" id="email<%=i%>" name="email<%=i%>"> <br><br>
			<%
			}%>
			<input type="submit" value="Create Ticket">
		</form>
	</div>

</body>
</html>
