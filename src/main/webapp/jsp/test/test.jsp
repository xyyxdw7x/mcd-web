<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>测试页面</title>
<%@ include file="../../jsp/common/head.jsp" %>
</head>
<body>
	<p>我是一个简单的测试111dsadsa页面w我是主体</p>
	<%
		String testExtendsUrl="provinces/"+provinces+"/test_extends.jsp";
	%>
	<jsp:include page="<%=testExtendsUrl%>"></jsp:include>
</body>
</html>