<%@ page language="java" pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<%
	String contextPath=request.getContextPath().toString();
	String provinces=application.getAttribute("APP_PROVINCES").toString(); 
%>
<script type="text/javascript">
	var contextPath="<%=contextPath%>";
	var provinces="<%=provinces%>";
</script>
<link rel="shortcut icon" href="<%=contextPath%>/assets/images/logos/favicon.ico" />
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/common.css"/>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/bootstrap/css/bootstrap.css"/>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/main.css"/>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/lib/jquery-ui.min.css" />
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/public-header.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/public-date.css" />
<link  type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/public-table.css" />

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/lib/pagination.css"/>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/lib/jquery.toast.min.css"/>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/lib/magnific-popup.css"/>

<link  type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/public-dialog.css" />

<script type="text/javascript" src="<%=contextPath%>/assets/js/lib/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/lib/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/lib/ejs.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/lib/seajs.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/lib/seajs-preload.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/lib/jquery.ui.datepicker-zh-cn.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/lib/underscore.min.js"></script>
