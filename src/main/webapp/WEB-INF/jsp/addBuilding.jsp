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
<title>Add new building</title>
</head>
<body>

	<div class="addApartment">
	<h1>Add new building</h1>
		<h1>${status}</h1>
		<sf:form modelAttribute="add">				
			<p>City: <sf:input type="text" path="city" required="required"/></p>
			<p>Address:<sf:input type="text" path="address" required="required"/></p>
			<p>Floors:<sf:input type="number" path="floors" maxlength="3" min="1" value="1"/></p>
			<p>Build up area:<sf:input type="text" path="builtUpArea" required="required" pattern="[+]?([0-9]*[.,])?[0-9]+"/></p>
			
			<input type="submit" value="Submit" class="dashboardButtons"> <a href="back" class="dashboardButtons">Back</a>
		</sf:form>
	</div>
	
	
		

</body>
</html>