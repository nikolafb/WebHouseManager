<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
     <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="<c:url value="/static/css/front.css" />" rel="stylesheet">
<body style="background-color: #fdf5e6"/>
<title>Add apartment</title>
</head>
<body>
	
	<div class="addApartment">
	<h1>Add new apartment</h1>
		<h1>${status}</h1>
		<sf:form modelAttribute="addApartment">				
			<p>Floor: <sf:input type="number" path="floor" maxlength="3" min="1" value="1"/></p>
			<p>Apartment number: <sf:input type="number" path="apartmentNumber" maxlength="4" min="1" value="1"/></p>
			<p>Area: <sf:input type="text" path="area" required="required"/></p>
			<p>Owner: <sf:input type="text" path="owner" required="required" /></p>
			<p>Members: <sf:input type="number" path="members" maxlength="2" min="1" value="1"/></p>
			<p>Pet: <sf:checkbox path="hasPet" itemValue="False"/></p>
			<p>Kids: <sf:input type="number" path="kids" maxlength="2" min="0"/></p>
				
			
			<input type="submit" value="Submit" class="dashboardButtons"> <a href="dashboard" class="dashboardButtons">Back</a>
		</sf:form>
		</div>

</body>
</html>