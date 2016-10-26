<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>产品库</title>
    <%@ include file="../../jsp/common/head.jsp" %>
    <link rel="shortcut icon" href="<%=contextPath%>/assets/images/logos/favicon.ico" />
    <!-- css内容 -->
    <link href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/plan/plan.css" rel="stylesheet" type="text/css"><!-- 产品样式 -->
    <link href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/plan/plan_table.css" rel="stylesheet" type="text/css"><!-- 产品table样式 -->
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/plan/plan_dialog.css" type="text/css"><!-- 产品弹窗样式 -->

    <!--js内容-->
    <script src="<%=contextPath%>/assets/js/plan/provinces/<%=provinces%>/planChoose.js" type="text/javascript"></script><!--planChoose.js 选择菜单的视图-->
    <script src="<%=contextPath%>/assets/js/plan/provinces/<%=provinces%>/planList.js" type="text/javascript"></script><!--planList.js 列表数据的视图-->
    <script src="<%=contextPath%>/assets/js/plan/provinces/<%=provinces%>/planDetail.js" type="text/javascript"></script><!--planDetail.js 弹窗数据的视图-->

</head>
<body>
	<%
	    String planUrl="provinces/"+provinces+"/planmanage_extends.jsp";
	%>
	<%@ include file="../../jsp/common/header.jsp" %>
	<jsp:include page="<%=planUrl%>"></jsp:include>
</html>