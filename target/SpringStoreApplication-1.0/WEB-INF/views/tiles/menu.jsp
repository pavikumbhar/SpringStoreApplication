<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div style="float:right">
<c:choose>
	<c:when test = "${empty loggedInUser.name}">
		<a href="users?register">Register</a> &nbsp;
		<a href="login">Login</a>
	</c:when>
	<c:otherwise>
		Hello ${loggedInUser.firstName}! &nbsp;
		<a href="logout">Logout</a>
	</c:otherwise>
</c:choose>
       <a href="?lang=mr" class="btn btn-danger btn-xs"><spring:message code="app.language.mr"/></a> |
       <a href="?lang=en" class="btn btn-primary btn-xs"><spring:message code="app.language.en"/></a> | 
       
       <br>
       Theme
       <br>
       <a href="?theme=black" class="btn btn-danger btn-xs">black</a> |
        <a href="?theme=chocolate" class="btn btn-warning btn-xs">chocolate</a> |
        <a href="?theme=default" class="btn btn-default btn-xs">default</a> |  
	
	                     

</div>
