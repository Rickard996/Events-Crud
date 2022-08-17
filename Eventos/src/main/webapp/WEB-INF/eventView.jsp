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

	<div style="width: 40%; height: 40%;display: inline-block;">
	<h1><c:out value="${event.getName() }"></c:out></h1>
	<h3>Host: <c:out value="${event.getHostName() }"></c:out></h3>
	<h3>Date: <c:out value="${event.getDateEvent() }"></c:out></h3>
	<h3>Location:<c:out value="${event.getLocation() }"></c:out></h3>
	<h3>People who are attending this event: <c:out value="${cantidadAsistentes }"></c:out></h3>

	<table>
		<thead>
			<tr>
				<th>Name</th>
				<th>Location</th>
			</tr>
		</thead>
		<tbody>
		
			<c:forEach items="${usersInEvent }" var="user">
				<tr>
					<td><c:out value="${user.getFirstName()}"></c:out> <c:out value="${user.getLastName()}"></c:out></td>
					<td><c:out value="${user.getLocation() }"></c:out></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>

	<div style="width: 40%; height: 40%;display: inline-block;">
	
		<h1>Message Wall</h1>
		<select multiple class="form-control" style="width: 400px;height: 120px;">
			<c:forEach items="${messages}" var="message">
				<option><c:out value="${message.getSubject()}"></c:out>
				
				</option>
			</c:forEach>
		</select>
		<br>
		<br>
		<form:form action="/events/${event.getId() }" method="post">
			<label for="subject"></label>
			<input id="subject" name="subject" type="text" style="width: 400px; height: 50px;">
			<button type="submit">Submit</button>
		</form:form>
	
	</div>



</body>
</html>