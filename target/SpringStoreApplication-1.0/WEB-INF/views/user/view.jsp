<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>View Profile</title>
<jsp:include page="/WEB-INF/views/include/head-include.jsp" />
</head>
<body>
<h1>
	Spring Chocolate Store  
</h1>
<div class="container">
	<h2>View Profile</h2>
	<div> Name:</div>
	<div> ${user.firstName} ${user.lastName} </div>
	<div> Email: </div>
	<div> ${user.email}</div>

	<a href="users/${user.name}/edit">Edit Profile</a>
</div>
</body>
</html>