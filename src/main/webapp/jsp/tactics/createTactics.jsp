<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>新建策略</title>
<%@ include file="../../jsp/common/head.jsp" %>
<link type="text/css" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/put/newCreateTactics.css" rel="stylesheet"  />
</head>
<body class="bg-fa">
<%
    String planUrl="provinces/"+provinces+"/createTacticsPlan.jsp";
	String customerGroupUrl="provinces/"+provinces+"/createTacticsCustGroup.jsp";
	String channelUrl="provinces/"+provinces+"/createTacticsChannel.jsp";
	String shoppintCartUrl="provinces/"+provinces+"/createTacticsShoppingCart.jsp";
	String headerUrl="";
%>	
	<jsp:include page="../../mcd/pages/header/header.jsp"></jsp:include>
	<div class="put-container w-1200">
		<!-- 左侧数字导航区 -->
		<div class="put-left fleft ft14 mtop_47">
			<ol id="stepOl">
				<li class="active"><span>选政策</span><i>1</i></li>
				<li class="line"></li>
				<li><span>选客户</span><i>2</i></li>
				<li class="line"></li>
				<li><span>选渠道</span><i>3</i></li>
			</ol>
		</div>
		<!--  中间操作区 -->
		<div class="put-center w-818 fleft">
			<jsp:include page="<%=planUrl%>"></jsp:include>
			<jsp:include page="<%=customerGroupUrl%>"></jsp:include>
			<jsp:include page="<%=channelUrl%>"></jsp:include>
		</div>
		<!-- 右侧购物车 -->
		<jsp:include page="<%=shoppintCartUrl%>"></jsp:include>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/assets/js/tactics/provinces/<%=provinces%>/createTactics.js" ></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/tactics/provinces/<%=provinces%>/createTacticsPlan.js" ></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/tactics/provinces/<%=provinces%>/CreateTacticsCustGroup.js" ></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/tactics/provinces/<%=provinces%>/createTacticsChannel.js" ></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/tactics/provinces/<%=provinces%>/createTacticsShoppingCart.js" ></script>

<script type="text/javascript">
$(function() {
	$('.public-table tbody tr:nth-child(2n+1)').addClass('odd');//奇数行添加背景色
	$('.public-table tbody tr:nth-child(2n)').addClass('even');//偶数行添加背景色
	// 页面初始化
	initPage();

	
	// 初始化全局事件监听
	addEventListenter();
	
})
</script>
</html>