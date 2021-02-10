<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %> 
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<body style="background-color: #fdf5e6"/>
<link href="<c:url value="/static/css/front.css" />" rel="stylesheet">
<title>Login</title>
</head>
<body>
<div class="login">
		<h1>Login</h1>
			<h3>${loginStatus}</h3>
			<sf:form modelAttribute="log">				
				<p>Company VAT number: <sf:input type="text" path="VAT_Number" required="required"/></p>
				<p>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&nbsp;Password:<sf:input type="password" path="password" required="required"/></p>
				
				<input type="submit" value="Login" class="loginButton">
			</sf:form>
	<br>
	<a href="index.jsp" class="loginButton">Back to home page</a>
</div>
</body>
</html>