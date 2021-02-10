<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %> 
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="<c:url value="/static/css/front.css" />" rel="stylesheet">
<title>Insert title here</title>
</head>
<body>
<body style="background-color: #fdf5e6"/>

<div class="register">
	<h1>Registration</h1>
		<h3>${status}</h3>
		<sf:form modelAttribute="reg">				
			<p>Company name: <sf:input type="text" path="companyName" required="required"/></p>
			<p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;VAT number: <sf:input type="text" path="VAT_Number" required="required"/></p>
			<p>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;Seat: <sf:input path="seat" required="required"/></p> 
			<p>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;City: <sf:input path="city" required="required" /></p>
			<p>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;MOL: <sf:input path="MOL" required="required" /></p>
			<p>&ensp;&ensp;&ensp;&ensp;&ensp; Password: <sf:input type="password" path="password" required="required"/></p>
			<p style="margin-left:-13px">Confirm password: <sf:input type="password" path="confirmPassword" required="required"/></p>
				
			
			<input type="submit" value="Submit" class="registerButton">
		</sf:form>

<p><a href="index.jsp" class="registerButton">Back to home page</a></p>
</div>

</body>
</html>