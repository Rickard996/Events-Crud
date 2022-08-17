<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>   
<!DOCTYPE html>
<html>
<head>

<link
href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
rel="stylesheet"
integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
crossorigin="anonymous">
<script
src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
crossorigin="anonymous"></script>


<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1 style="display: inline-block;">Welcome, <c:out value="${user.firstName}" /></h1>
	<a href="/logout" style="margin-left: 75%; ">Logout</a>

	<p>Here are some of the events in your location:</p>

	<table class="table table-dark">
		<thead>
			<tr>
				<th scope="col">Name</th>
				<th scope="col">Date</th>
				<th scope="col">Location</th>
				<th scope="col">Host</th>
				<th scope="col">Action/Status</th>
			</tr>
		</thead>
	
		<tbody>
		
			<c:forEach items="${eventsSameState }" var="event">
			<tr>
				<td scope="row"><a href="/events/${event.getId() }"><c:out value="${event.getName() }"></c:out></a></td>
				<td><c:out value="${event.getDateEvent() }"></c:out></td>
				<td><c:out value="${event.getLocation() }"></c:out></td>
				<td><c:out value="${event.getHostName() }"></c:out></td>
				
				<td><c:if test="${nameUser.trim().equals(event.getHostName().trim())}">
				<a href="/events/${event.getId()}/edit">Edit</a>  <a href="/delete/${event.getId() }">Delete</a>
    					</c:if> 
    					
    				<c:if test="${!event.getUsers().contains(user) && !nameUser.trim().equals(event.getHostName().trim())}"><a href="/join/${event.getId()}">Join</a>
    					</c:if>
    				<c:if test="${event.getUsers().contains(user) && !nameUser.trim().equals(event.getHostName().trim())}"><p>Joining</p><a href="/cancelJoin/${event.getId() }">Cancel</a>
    					</c:if>
    					</td></tr>
    			
    					
    					
			</c:forEach>
		</tbody>
	</table>
	
	<p>Here are some of the events in other locations:</p>
	
	<table class="table table-dark">
		<thead>
			<tr>
				<th scope="col">Name</th>
				<th scope="col">Date</th>
				<th scope="col">Location</th>
				<th scope="col">Host</th>
				<th scope="col">Action</th>
			</tr>
		</thead>
	
		<tbody>
		
			<c:forEach items="${eventosOtherState }" var="event">
			<tr>
				<td scope="row"><a href="/events/${event.getId() }"><c:out value="${event.getName() }"></c:out></a></td>
				<td><c:out value="${event.getDateEvent() }"></c:out></td>
				<td><c:out value="${event.getLocation() }"></c:out></td>
				<td><c:out value="${event.getHostName() }"></c:out></td>
				<td><c:if test="${nameUser.trim().equals(event.getHostName().trim())}">
				<a href="/events/${event.getId()}/edit">Edit</a>  <a href="/delete/${event.getId() }">Delete</a>
    					</c:if> 
    				
    					
    				<c:if test="${!event.getUsers().contains(user) && !nameUser.trim().equals(event.getHostName().trim())}"><a href="/join/${event.getId()}">Join</a>
    					</c:if>
    				<c:if test="${event.getUsers().contains(user) && !nameUser.trim().equals(event.getHostName().trim())}"><p>Joining</p><a href="/cancelJoin/${event.getId() }">Cancel</a>
    					</c:if>
    					</td></tr>
			</c:forEach>
		</tbody>
	</table>
	
	<br>
	<br>
	<br>
	
	<h1>Create a new Event!</h1>
	
	<form:form action="/event/new" method="post" modelAttribute="event">
	
		<label for="name">Name</label>
		<input id="name" name="name" type="text">
		
		<label for="dateEvent">Date</label>
		<input id="dateEvent" name="dateEvent" type="date">
		
		<label for="location">Location</label>
		<input id="location" name="location" type="text">
		
		<button type="submit">Create Event</button>
		
	</form:form>
	
	<br>
	<br>
	<br>
	
	
	
	
	
	
	
	<!-- busca si hay attributes en el Model, para desplegar un logout message o un error message -->
 <!--   <c:if test="${logoutMessage != null}">
        <c:out value="${logoutMessage}"></c:out>
    </c:if>
    <h1>Login</h1>
    <c:if test="${errorMessage != null}">
        <c:out value="${errorMessage}"></c:out>
    </c:if>   -->



</body>
</html>