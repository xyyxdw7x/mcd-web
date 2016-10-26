<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>应用库</title>
    <%@ include file="../../jsp/common/head.jsp" %>
    <link rel="shortcut icon" href="<%=contextPath%>/assets/images/logos/favicon.ico" />
    <!-- css内容 -->
    <link href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/app/app.css" rel="stylesheet" type="text/css"><!-- 内容样式 -->
    <link href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/app/app_table.css" rel="stylesheet" type="text/css"><!-- 内容table样式 -->
    <link href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/app/app_dialog.css" rel="stylesheet" type="text/css"><!-- 内容弹窗样式 -->

    <!--js内容-->
    <script src="<%=contextPath%>/assets/js/app/provinces/<%=provinces%>/appChoose.js" type="text/javascript"></script><!--contentChoose.js 选择菜单的视图-->
    <script src="<%=contextPath%>/assets/js/app/provinces/<%=provinces%>/appList.js" type="text/javascript"></script><!--contentList.js 列表数据的视图-->
    <script src="<%=contextPath%>/assets/js/app/provinces/<%=provinces%>/appDetail.js" type="text/javascript"></script><!--contentDetail.js 弹窗数据的视图-->

</head>
<body>
	<% String appUrl="provinces/"+provinces+"/appmanage_extends.jsp"; %>
	<%@ include file="../../jsp/common/header.jsp" %>
	<jsp:include page="<%=appUrl%>"></jsp:include>
</html>