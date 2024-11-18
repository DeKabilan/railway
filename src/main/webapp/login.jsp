<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<title>Login</title>
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

input[type="text"], select, input[type="date"], input[type="number"], input[type="email"], input[type="password"] {
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
<body>
<h2>Login</h2>
<p><%
String message = (String)request.getAttribute("result");
out.println(message);
%></p>
<form action = "login" method="POST">
	<p>EmailID</p>
	<input type="email" id="email" name="email" required>
	<p>Password</p>
	<input type="password" name="password" required>
	<input type="hidden" name="type" value="login" required><br>
	<input type="submit" value="Login">
</form>
	<a href = "/railway/register">Click to Register</a>
</body>
</html>

