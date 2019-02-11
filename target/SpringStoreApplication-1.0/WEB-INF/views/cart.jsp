<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/WEB-INF/views/include/head-include.jsp" />
<title>Cart</title>
</head>
<body>
<div class="container">
<h2>Your cart</h2>
<form action='cart/placeOrder' method="post">
${cartMessage}
<table class="table">
	<tr>
		<th> Item </th>
		<th> Quantity </th>
		<th> Price </th>
	</tr>
	<c:forEach var="item" items="${cart.contents}">
	<tr>
		<td><c:out value="${item.key.name}"/> </td>
		<td><c:out value="${item.value}"/> </td>
		<td>$<c:out value="${item.key.price * item.value}"/> </td>
	</tr>
	</c:forEach>
	
	<tr></tr>
	<tr>
		<td>Total</td>
		<td></td>
		<td>$${cart.totalCost}</td>
	</tr>
</table>
<input type="submit" value="Place Order"></input>
</form>

</div>
</body>
</html>