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
<title>Delete</title>
</head>
<body>

		
	<!-- removing bulding -->
		<c:if test="${deleteBuildingReguest==true}">
		<div class="addApartment">
			<h1>Delete building</h1>
				<h3>Enter your password</h3>
				<h1>${requestToDeleteBuildingStatus }</h1>
				<sf:form modelAttribute="requestToDeleteBuilding">				
					Password: <sf:input type="password" path="password" required="required"/><br>
					Confirm password: <sf:input type="password" path="confirmPassword" required="required"/><br>
					<input type="submit" value="Delete" class="buttonsForDelete">
					<a href=requestToDeleteBuildingCancel class="buttonsForCancel">Cancel</a>
				</sf:form>
			</div>
		</c:if>
		
		<!-- remove employee -->
		<c:if test="${fireEmployeeRequest==true}">
		<div class="addApartment">
			<h1>Fire Employee</h1>
				<h3>Enter your password</h3>
				<h1>${fireEmployeeStats}</h1>
				<sf:form modelAttribute="confPass">				
					Password: <sf:input type="password" path="password" required="required"/><br>
					Confirm password: <sf:input type="password" path="confirmPassword" required="required"/><br>
					<input type="submit" value="Delete" class="buttonsForDelete">
					<a href=backToDashboard class="buttonsForCancel">Cancel</a>
				</sf:form>
		</div>
		</c:if>

	
</body>
</html>