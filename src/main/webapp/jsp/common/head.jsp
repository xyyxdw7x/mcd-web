<%@ page language="java" pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<%
	String contextPath=request.getContextPath().toString();
	String provinces=application.getAttribute("APP_PROVINCES").toString(); 
%>
<link  type="text/css" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/main.css" rel="stylesheet"  />
<script type="text/javascript" src="<%=contextPath%>/assets/js/jquery/jquery-3.1.0.min.js"></script>
