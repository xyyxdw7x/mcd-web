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
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/common.css"/>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/bootstrap/css/bootstrap.css"/>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/main.css"/>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/public-header.css"/>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/plugins/simplePagination.css"/>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/jqueryui/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/assets/css/provinces/jx/public-date.css" />

<script type="text/javascript" src="<%=contextPath%>/assets/js/jquery/jquery-3.1.0.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/jquery/jQueryUI/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/ejs/ejs.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/common/template-native.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/plugins/jquery.simplePagination.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/plugins/pagination.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/plugins/purl.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/plugins/xdate.min.js"></script>