<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/WEB-INF/views/include/head-include.jsp" />
<title>Create Category</title>
</head>
<body>
<h1>
	Spring Chocolate Store  
</h1>
	<div class="container">
	
	<h2>Create Category</h2>
	<div class="form-group form">
	<form:form name="input"  method="post"
		modelAttribute="category" action="categories">
		<div>
			<label>Name: </label>
			<form:input class="form-control" type="text" path="name" />
			<form:errors path="name" />
		</div>
		<div>
			<label>Description: </label>
			<form:textarea class="form-control" path="description" />
			<form:errors path="description" />
		</div>
		<div>
			<input class="form-control" type="submit" value="Submit">
		</div>
	</form:form>
	</div>
	</div>
</body>
</html>