<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="/static/css/front.css" />" rel="stylesheet">
<meta charset="ISO-8859-1">
<title>Dashboard</title> 
</head>
<body>
<body style="background-color: #fdf5e6"/>
<h1 style="margin-left:1.2%">Welcome ${companyName} ${companyProfitStatus}</h1> 
<!--<c:out value="${sessionScope.VAT_Number}"/>-->
<%if(session.getAttribute("VAT_Number")==null){response.sendRedirect("index.jsp");return ;}%>
<%String username=(String)session.getAttribute("VAT_Number");
	//System.out.print(username);%>
	<div class="showPricesAndCollectedTaxesStatus">
	<c:if test="${showPrices==true}" >
		<h2>Price for area: ${priceForArea}    Price for pet: ${priceForPet}     Price for person: ${priceForPerson}</h2>
	</c:if>
	
	<h1>${collectiongTaxesStatus}</h1><br>
	</div>
	
	<div class="showButtons">
	<c:if test="${not empty companyName}"><a href="static/paidTaxes/${companyName}.txt" target="_blank" class="dashboardButtons">${showAllCollectedTaxes}</a></c:if>
	
	<c:if test="${not empty changeCompanyPrice}"><a href="changePrice" class="dashboardButtons">${changeCompanyPrice}</a></c:if>
	
	<c:if test="${not empty changeCompanyPrice}"><a href="changePrice" class="dashboardButtons" >${changeCompanyPrice}</a></c:if>
	
	<c:if test="${not empty addNewBuilding}"><a href="createBuilding" class="dashboardButtons">${addNewBuilding}</a></c:if>
	
	<c:if test="${not empty hireEmployee}"><a href="hireEmployee" class="dashboardButtons">${hireEmployee}</a></c:if>
	
	<c:if test="${not empty showAllBuildings}"><a href="showBuildings" class="dashboardButtons">${showAllBuildings}</a></c:if>
	
	<c:if test="${not empty showAllEmployees}"><a href="showEmployees" class="dashboardButtons">${showAllEmployees}</a></c:if>
	
	<c:if test="${not empty showEmployeesByNameAndBuildings}"><a href="showEmployeesByNameAndBuildings" class="dashboardButtons">${showEmployeesByNameAndBuildings}</a></c:if>
	
	<c:if test="${not empty sortByProft}"><a href="showCompaniesByProfit" class="dashboardButtons">${sortByProft}</a></c:if>
	
	<c:if test="${not empty hideBuildings}"><a href="hideBuildings" class="dashboardButtons">${hideBuildings}</a></c:if>
	
	<c:if test="${not empty hideApartments}"><a href="hideApartments" class="dashboardButtons" >${hideApartments}</a></c:if>
	
	<c:if test="${not empty hideAllEmployees}"><a href="hideEmployees" class="dashboardButtons">${hideAllEmployees}</a></c:if>
	
	<c:if test="${not empty hideAllCompanies}"><a href="hideCompaniesByProfit" class="dashboardButtons">${hideAllCompanies}</a></c:if>
	
	<c:if test="${not empty returnEmployeesByNameAndByBuildings}"><a href="backToDash" class="dashboardButtons">${returnEmployeesByNameAndByBuildings}</a></c:if>
	
	<c:if test="${not empty backToEmployeeInforamtion}"><a href="showEmployees" class="dashboardButtons">${backToEmployeeInforamtion}</a></c:if>
	
	<c:if test="${not empty hideAll}"><a href="hideAll" class="dashboardButtons">${hideAll}</a></c:if>
	</div>
	
	<div class="showInfo">
	<c:forEach items="${buildings}" var="buildings">
			<p>City: ${buildings.city}  &nbsp;&nbsp;&nbsp;&nbsp;  Address: ${buildings.address}</p>
			<p>Floors: ${buildings.floors}   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Build up area: ${buildings.builtUpArea} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The day of payment: ${buildings.dateOfCollectingTaxes}</p>
			
			<c:if test="${not empty buildings.buildingId}"><a href="apartment/${buildings.buildingId}" class="dashboardButtonsShowBuildings">Add apartment</a> </c:if>
			<c:if test="${not empty buildings.buildingId}"><a href="showApartments/${buildings.buildingId}" class="dashboardButtonsShowBuildings">Show apartments</a></c:if>
			<c:if test="${not empty buildings.buildingId}"><a href="removeBuilding/${buildings.buildingId}" class="dashboardButtonsShowBuildings">Remove this building</a></c:if>
			<br><br><br>
		</c:forEach>
		</div>


				<!-- goes to   returnBuildings  -->
			<div class="showInfo">
			<c:if test="${buildingInfo==true}">
			
			<h3>Building info &emsp;&emsp;&emsp;&emsp; <c:if test="${not empty building.buildingId}"><a href="sortByNameAndKids/${building.buildingId}" class="dashboardButtons">Sort by name and kids</a></c:if></h3> 
				<h4>City: ${building.city} &emsp; Address: ${building.address}  &emsp; Floors: ${building.floors} &emsp;  Build up area: ${building.builtUpArea} &emsp; Date of collecting taxes: ${building.dateOfCollectingTaxes}</h4>
			
				<c:forEach items="${apartments}" var="apartments">
					<p>Apartment on floor: ${apartments.floor} &nbsp;&nbsp;&nbsp;&nbsp;   Apartment number: ${apartments.apartmentNumber} &nbsp;&nbsp;&nbsp;&nbsp; Area: ${apartments.area} &nbsp;&nbsp;&nbsp;&nbsp; Owner:${apartments.owner} &nbsp;&nbsp;&nbsp;&nbsp; Members: ${apartments.members} &nbsp;&nbsp;&nbsp;&nbsp;  Pet: ${apartments.hasPet} &nbsp;&nbsp;&nbsp;&nbsp; Kids: ${apartments.kids}</p>
					<c:if test="${not empty apartments.apartmentId}"><a href="changeApartmentDetails/${apartments.apartmentId }" class="dashboardButtonsShowBuildings">Change apartment details</a></c:if>
					<br><br><br>
				</c:forEach>	
			</c:if>
			</div>
					
			<!-- show all employees  -->
				<div class="showInfo">
				<c:forEach items="${employees}" var="employees">
					<p>Name: ${employees.fName}  ${employees.lName} &nbsp;&nbsp;&nbsp; ID number: ${employees.IDNumber} &nbsp;&nbsp;&nbsp; City of birth: ${employees.cityOfBirth}  &nbsp;&nbsp;&nbsp; Date of birth: ${employees.dateOfBirth} &nbsp;&nbsp;&nbsp; Local address: ${employees.localAddress}</p>
					<p>Total collected taxes: ${employees.collectedTaxes}BGN &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Last date of collecting taxes: ${employees.dateOfCollectingTaxes}</p>
					
					<c:if test="${not empty employees.employeeId}"><a href="changeEmployeeDetails/${employees.employeeId}" class="dashboardButtonsShowBuildings">Change employee details</a></c:if>
					<c:if test="${not empty employees.employeeId}"><a href="fireEmployee/${employees.employeeId}" class="dashboardButtonsShowBuildings">Fire this employee</a></c:if>
					<c:if test="${not empty employees.employeeId}"><a href="employeeAndBuildings/${employees.employeeId}" class="dashboardButtonsShowBuildings">Show the buildings on this employee</a></c:if>
					<br><br>
				</c:forEach>
				</div>
    		
    		<!-- show employee and its buildings  -->
    		<div class="showInfo">
    			<c:if test="${showEployeeDetailsAndBuildingsInfo==true}">
    				<h3>Employee info</h3>
    				<p>Name: ${employeeDetails.fName}  ${employeeDetails.lName} &nbsp;&nbsp;&nbsp; ID number: ${employeeDetails.IDNumber} &nbsp;&nbsp;&nbsp; City of birth: ${employeeDetails.cityOfBirth}  &nbsp;&nbsp;&nbsp; Date of birth: ${employeeDetails.dateOfBirth} &nbsp;&nbsp;&nbsp; Local address: ${employeeDetails.localAddress}</p>
					<p>Total collected taxes: ${employeeDetails.collectedTaxes}BGN &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Last date of collecting taxes: ${employeeDetails.dateOfCollectingTaxes}</p>
						<br>
						<c:forEach items="${showEmployeeBuildings}" var="buildings">
							<h3>Building info</h3>
							<p>City: ${buildings.city}  &nbsp;&nbsp;&nbsp;&nbsp;  Address: ${buildings.address} &nbsp;&nbsp;&nbsp;&nbsp; Floors: ${buildings.floors} </p>
							<p>Build up area: ${buildings.builtUpArea}  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; The day of payment: ${buildings.dateOfCollectingTaxes}</p>
							<c:if test="${not empty buildings.buildingId}"><a href="collectTaxForBuilding/${buildings.buildingId}/${employeeDetails.employeeId}" class="dashboardButtonsShowBuildings" >Collect tax for this building</a><!-- employee controller --></c:if>
							<br><br>
						</c:forEach>
				</c:if>
				</div>
				
				
				<!-- show all companies by profit-->
				<div class="showInfo">
				<c:forEach items="${showCompaniesSortByProft}" var="company">
							<p>Profit: ${company.value.profit} &nbsp;&nbsp;&nbsp;&nbsp; Company name: ${company.value.companyName} &nbsp;&nbsp;&nbsp;&nbsp; city: ${company.value.city} &nbsp;&nbsp;&nbsp;&nbsp; address: ${company.value.seat}</p>
							<br><br>
						</c:forEach>
				</div>
				
				
				<!-- show employees and buildings -->
				<div class="showInfo">
				<c:forEach items="${employeesByNameAndBuildings}" var="employees">
							<p>Name: ${employees.fName}  ${employees.lName} &nbsp;&nbsp;&nbsp; ID number: ${employees.IDNumber} &nbsp;&nbsp;&nbsp; City of birth: ${employees.cityOfBirth}  &nbsp;&nbsp;&nbsp; Date of birth: ${employees.dateOfBirth} &nbsp;&nbsp;&nbsp; Local address: ${employees.localAddress}</p>
							<p>Total collected taxes: ${employees.collectedTaxes}BGN &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Last date of collecting taxes: ${employees.dateOfCollectingTaxes} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; buildings:${employees.empleeBuildingsSize}</p>
						<br><br>
				</c:forEach>
				</div>
				
				
				<!-- goes to   returnApartmentsSortedByNameAndKids  -->
			<div class="showInfo">
			<c:if test="${buildingInfoSorted==true}">
			<h3>Building info &emsp;&emsp;&emsp;&emsp; </h3> 
				
				<a href="backShowBuildings" class="dashboardButtonsSortedByNameAndBuildings" >Back to buildings</a> &emsp;&emsp; <a href="backToDash" class="dashboardButtonsSortedByNameAndBuildings">Hide all</a>
				<h4>City: ${building.city} &emsp; Address: ${building.address}  &emsp; Floors: ${building.floors} &emsp;  Build up area: ${building.builtUpArea} &emsp; Date of collecting taxes: ${building.dateOfCollectingTaxes}</h4><br>
				
				<c:forEach items="${apartments}" var="apartments">
					<p>Owner:${apartments.owner} &nbsp;&nbsp;&nbsp;&nbsp;  Kids: ${apartments.kids} &nbsp;&nbsp;&nbsp;&nbsp; Apartment on floor: ${apartments.floor} &nbsp;&nbsp;&nbsp;&nbsp;   Apartment number: ${apartments.apartmentNumber} &nbsp;&nbsp;&nbsp;&nbsp; Area: ${apartments.area} &nbsp;&nbsp;&nbsp;&nbsp;  Members: ${apartments.members} &nbsp;&nbsp;&nbsp;&nbsp;  Pet: ${apartments.hasPet} </p>
				</c:forEach>	
			</c:if>
			</div>	
				

</body>
</html>