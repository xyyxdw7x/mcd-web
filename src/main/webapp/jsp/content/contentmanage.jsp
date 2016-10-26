<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>内容库</title>
    <%@ include file="../../jsp/common/head.jsp" %>
    <link rel="shortcut icon" href="<%=contextPath%>/assets/images/logos/favicon.ico" />
    <!-- css内容 -->
    <link href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/content/content.css" rel="stylesheet" type="text/css"><!-- 内容样式 -->
    <link href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/content/content_table.css" rel="stylesheet" type="text/css"><!-- 内容table样式 -->
    <link href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/content/content_dialog.css" rel="stylesheet" type="text/css"><!-- 内容弹窗样式 -->

    <!--js内容-->
    <script src="<%=contextPath%>/assets/js/content/provinces/<%=provinces%>/contentChoose.js" type="text/javascript"></script><!--contentChoose.js 选择菜单的视图-->
    <script src="<%=contextPath%>/assets/js/content/provinces/<%=provinces%>/contentList.js" type="text/javascript"></script><!--contentList.js 列表数据的视图-->
    <script src="<%=contextPath%>/assets/js/content/provinces/<%=provinces%>/contentDetail.js" type="text/javascript"></script><!--contentDetail.js 弹窗数据的视图-->
    
</head>
<body>
	<% String contentUrl="provinces/"+provinces+"/contentmanage_extends.jsp"; %>
	<%@ include file="../../jsp/common/header.jsp" %>
	<jsp:include page="<%=contentUrl%>"></jsp:include>
</html>