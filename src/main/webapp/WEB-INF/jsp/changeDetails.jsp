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
<title>Change details</title>
</head>
<body>
	
	<!-- change for apartment -->
	<c:if test="${requestForChangeApartmentDetails==true}">
		<div class="addApartment">
		<h1>Change apartment details</h1>
		<h1>${changeApartmentDetailsStatus}</h1>
		<sf:form modelAttribute="changeDetails">				
			<p>Floor: <sf:input type="number" path="floor"  maxlength="3" min="1" value="${apartment.floor}"/></p>
			<p>Apartment number: <sf:input type="number" path="apartmentNumber" maxlength="4" min="1" value="${apartment.apartmentNumber}"/></p>
			<p>Owner: <sf:input type="text" path="owner" required="required"  value="${apartment.owner}"/></p>
			<p>Members: <sf:input type="number" path="members" maxlength="2" min="1" value="${apartment.members}"/></p>
			<p>Pet: <sf:checkbox path="hasPet" itemValue="False" /></p>
			<p>Kids: <sf:input type="number" path="kids" maxlength="2" min="0" value="${apartment.kids}"/></p>
			
			<input type="submit" value="Submit" class="dashboardButtons" >  <a href="dashboard" class="dashboardButtons">Back</a> <!-- fix back button -->
		</sf:form>
		</div>
	</c:if>
	
		<!-- change for employee -->
		<c:if test="${prepForChangeEmployeeDetails==true}">
		<div class="addApartment">
    			<h1>Change employee details</h1>
    			<h1>${changeApartmentDetailsStatus}</h1>
					<sf:form modelAttribute="changeEmpDetails">				
						<p>First name: <sf:input type="text" path="fName"  value="${employee.fName}" required="required"/></p>
						<p>Last name: <sf:input type="text" path="lName"  value="${employee.lName}" required="required"/></p>
						<p>Local address: <sf:input type="text" path="localAddress" required="required"  value="${employee.localAddress}"/></p>
						
						<input type="submit" value="Submit" class="dashboardButtons"> <a href="dashboard" class="dashboardButtons">Back</a>
					</sf:form>
					</div>
    		</c:if>
    		
    		
    		<!-- change price -->
    		<c:if test="${changePriceRequest==true}">
    		<div class="addApartment">
    			<h1>Change price</h1>
					<sf:form modelAttribute="changePrice">				
						<p>Price for area: <sf:input type="number" path="priceArea"   value="${company.priceArea}" min="0.1" required="required" pattern="[+]?([0-9]*[.,])?[0-9]+"/></p>
						<p>Price for pet: <sf:input type="number" path="pricePet"    value="${company.pricePet}" min="0.1" required="required" pattern="[+]?([0-9]*[.,])?[0-9]+"/></p>
						<p>Price for person: <sf:input type="number" path="pricePerson"  value="${company.pricePerson}" min="0.1" required="required" pattern="[+]?([0-9]*[.,])?[0-9]+"/></p>
						
						<input type="submit" value="Submit" class="dashboardButtons"> <a href="dashboard" class="dashboardButtons">Back</a>
					</sf:form>
					</div>
    		</c:if>
    		
    		

	
	
</body>
</html>