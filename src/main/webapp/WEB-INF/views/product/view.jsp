<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>View product</title>
<jsp:include page="/WEB-INF/views/include/head-include.jsp" />
</head>
<body>
	<div class="container">
	<h2>View Product</h2>
	<div><strong> Name: </strong></div>
	<div> ${product.name} </div>
	<div><strong> Description: </strong> </div>
	<div> ${product.description}</div>
	

	<a href="products/${product.id}/edit">Edit product</a>
	</div>

<%-- <h3>Products</h3>

<c:forEach var="item" items="${category.products}">
	<c:out value="${item.name}"/> <br/>
</c:forEach>
 --%>

</body>
</html>