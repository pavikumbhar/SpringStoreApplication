<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/views/include/head-include.jsp" />
<title>
    <tiles:insertAttribute name="title" ignore="true"></tiles:insertAttribute>
</title>
</head>
<body>
	<div>
            <tiles:insertAttribute name="menu"></tiles:insertAttribute>
    </div>
	<div>
            <tiles:insertAttribute name="header"></tiles:insertAttribute>
    </div>
    
    <div>
    	<tiles:insertAttribute name="body"></tiles:insertAttribute>
    </div>
    <div>
    	<tiles:insertAttribute name="footer"></tiles:insertAttribute>
    </div>
</body>
</html>