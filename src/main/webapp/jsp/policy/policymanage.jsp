<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>政策库</title>
    <%@ include file="../../jsp/common/head.jsp" %>
    <link rel="shortcut icon" href="<%=contextPath%>/assets/images/logos/favicon.ico" />
    <!-- css内容 -->
    <link href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/policy/policy.css" rel="stylesheet" type="text/css"><!-- 政策样式 -->
    <link href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/policy/policy_table.css" rel="stylesheet" type="text/css"><!-- 政策table样式 -->
    <link href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/policy/policy_dialog.css" rel="stylesheet" type="text/css"><!-- 政策弹窗样式 -->

    <!--js内容-->
    <script src="<%=contextPath%>/assets/js/policy/provinces/<%=provinces%>/policyChoose.js" type="text/javascript"></script><!--policyChoose.js 选择菜单的视图-->
    <script src="<%=contextPath%>/assets/js/policy/provinces/<%=provinces%>/policyList.js" type="text/javascript"></script><!--policyList.js 列表数据的视图-->
    <script src="<%=contextPath%>/assets/js/policy/provinces/<%=provinces%>/policyDetail.js" type="text/javascript"></script><!--policyDetail.js 弹窗数据的视图-->

</head>
<body>
	<% String policyUrl="provinces/"+provinces+"/policymanage_extends.jsp"; %>
	<%@ include file="../../jsp/common/header.jsp" %>
	<jsp:include page="<%=policyUrl%>"></jsp:include>
</html>
