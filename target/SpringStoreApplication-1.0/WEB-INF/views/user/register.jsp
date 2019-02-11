<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/views/include/head-include.jsp" />
<title>Register</title>
</head>
<body>
<h1>
	Spring Chocolate Store  
</h1>
	<div class="container">
	<form:form class="form-group form" name="input" method="post" action="users" modelAttribute="user">
	<form:errors path="name" />
	Username: <form:input class="form-control" type="text" path="name" /><br/>
	<form:errors path="password" />
	Password: <form:input class="form-control" type="text" path="password" /><br/>
	<form:errors path="email" />
	Email: <form:input class="form-control" type="text" path="email" /><br/>
	<form:errors path="firstName" />
	First Name: <form:input class="form-control" type="text" path="firstName" /><br/>
	<form:errors path="lastName" />
	Last Name: <form:input class="form-control" type="text" path="lastName" /><br/>

	<input class="form-control" type="submit" value="Submit">


	</form:form>
	</div>

</body>
</html>