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

	<div class="register" style="display: inline-block;">
    <h1>Register!</h1>
    <div style="border: 2px solid black; height: 800px; width: 500px;display: inline-block;">
    <p><c:out value="${success }"></c:out></p>
    <p><form:errors path="user.*"/></p>
    
    <form:form method="POST" action="/registration" modelAttribute="user">
        
         <p>
            <form:label path="firstName">First Name</form:label>
            <form:input type="text" path="firstName"/>
        </p>
        <p>
            <form:label path="lastName">Last Name</form:label>
            <form:input type="text" path="lastName"/>
        </p>
        <p>
            <form:label path="email">Email:</form:label>
            <form:input type="email" path="email"/>
        </p>
        
        <p>
            <form:label path="location">Location</form:label>
            <form:input type="text" path="location"/>
        </p>
        <p>
            <form:label path="password">Password:</form:label>
            <form:password path="password"/>
        </p>
        <p>
            <form:label path="passwordConfirmation">Password Confirmation:</form:label>
            <form:password path="passwordConfirmation"/>
        </p>
        <input type="submit" value="Register!"/>
    </form:form>
	</div>
	</div>

	<div class="login" style="display: inline-block; margin-top: 0">
	<h1 style="margin-left: 200px">Login</h1>
	<div style="border: 2px solid black; height: 500px; width: 500px;display: inline-block;margin-left: 200px">
    
    <p><c:out value="${error}" /></p>
    <form method="post" action="/login">
        <p>
            <label for="email">Email</label>
            <input type="text" id="email" name="email"/>
        </p>
        <p>
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
        </p>
        <input type="submit" value="Login!"/>
    </form>    
	</div>
	</div>



</body>
</html>