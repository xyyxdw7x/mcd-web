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
<link  type="text/css" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/main.css" rel="stylesheet"  />
<link  type="text/css" href="<%=contextPath%>/assets/css/common.css" rel="stylesheet" rel="stylesheet" />
<link  type="text/css" href="<%=contextPath%>/assets/css/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link  type="text/css" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/public-header.css" rel="stylesheet"  />
<link type="text/css" href="<%=contextPath%>/assets/css/easyui/themes/default/easyui.css" rel="stylesheet" />
<link type="text/css" href="<%=contextPath%>/assets/css/easyui/themes/icon.css" rel="stylesheet" />
<link type="text/css" href="<%=contextPath%>/assets/css/plugins/simplePagination.css" rel="stylesheet" />
<link type="text/css" href="<%=contextPath%>/assets//jqueryeasyui/themes/icon.css" rel="stylesheet" />
<script type="text/javascript" src="<%=contextPath%>/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/ejs/ejs.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/common/template-native.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/plugins/jquery.simplePagination.js"></script>


