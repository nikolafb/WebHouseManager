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
<title>Hire an employee</title>
</head>
<body>
	<div class="addApartment">
	<h1>Hire an employee</h1>
		<h1>${hireEmployeeStatus}</h1>
		<sf:form modelAttribute="addEmp">				
			<p>First name: <sf:input type="text" path="fName" required="required"/></p>
			<p>Last name: <sf:input type="text" path="lName"   required="required"/></p>
			<p>ID number: <sf:input type="text" path="IDNumber" required="required" /></p> 
			<p>City of birth:<sf:input type="text" path="cityOfBirth" required="required" /></p>
			<p>Date of birth:<sf:input type="text" path="dateOfBirth" maxlength="10" required="required"/></p>
			<p>Local address:<sf:input type="text" path="localAddress" required="required" /></p>
			
			<input type="submit" value="Submit" class="dashboardButtons"> <a href="dashboard" class="dashboardButtons">Back</a>
		</sf:form>
		</div>
	
	

</body>
</html>