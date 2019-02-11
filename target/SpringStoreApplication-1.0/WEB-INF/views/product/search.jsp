<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
<jsp:include page="/WEB-INF/views/include/head-include.jsp" />
</head>
<body>
	<div class="container">
	<h2>Search Products</h2>
	<div class="form-group form">
	<form:form name="input"  method="get"
		modelAttribute="product" action="products">
		<div>
			<label>Name: </label>
			<form:input type="text" path="name" class="form-control"  />
			<form:errors path="name" />
		</div>
		<div>
			<label>Category:</label>
			<form:select path="category.id" class="form-control" >
				<form:option path="category.id" value="0" label="--- All Categories ---"/>
				<form:options path="category.id" items="${categories}" itemLabel="name" itemValue="id" />
			</form:select>
		</div>
		<div>
			<input type="submit" value="Submit" class="form-control">
		</div>


	</form:form>
	</div>
	
	<h2>Search Results:</h2>
	
	<c:forEach var="product" items="${results}">
		<c:out value="${product.name}"/> <br/>
	</c:forEach>
	</div>

</body>
</html>