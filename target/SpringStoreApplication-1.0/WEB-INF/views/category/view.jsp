<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>View Category</title>
<jsp:include page="/WEB-INF/views/include/head-include.jsp" />
</head>
<body>
	<div class="container">
	<h2>View Category</h2>
	<div><strong> Name: </strong></div>
	<div> ${category.name} </div>
	<div><strong> Description: </strong> </div>
	<div> ${category.description}</div>
	

	<a href="categories/${category.id}/edit">Edit Category</a>
	

	<h2>Products</h2>
	
	<c:forEach var="item" items="${category.products}">
		<c:out value="${item.name}"/> <br/>
	</c:forEach> 
	</div>


</body>
</html>