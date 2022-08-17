<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1><c:out value="${event.getName() }"></c:out></h1><br>
	<h1>Edit Event:</h1>
	<form:form action="/events/${idEvent}/edit" method="post">
	
		<label for="name">Name</label>
		<input id="name" name="name" type="text" value="${event.getName() }">
		
		<label for="dateEvent">Date</label>
		<input id="dateEvent" name="dateEvent" type="date" value="${event.getDateEvent() }">
		
		<label for="location">Location</label>
		<input id="location" name="location" type="text" value="${event.getLocation() }">
		
		<button type="submit">Edit Event</button>
		
	</form:form> 
</body>
</html>