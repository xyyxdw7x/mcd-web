<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>测试页面</title>
<%@ include file="../../jsp/common/head.jsp" %>
<link  type="text/css" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/put/newCreateTactics.css" rel="stylesheet"  />
<script type="text/javascript" src="<%=contextPath%>/assets/js/test/test.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/test/provinces/<%=provinces%>/test_extends_customer_group.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/test/provinces/<%=provinces%>/test_extends_shopcar.js"></script>
</head>
<body class="bg-fa">
	<%
	    String planUrl="provinces/"+provinces+"/test_extends_plan.jsp";
		String customerGroupUrl="provinces/"+provinces+"/test_extends_customer_group.jsp";
		String channelUrl="provinces/"+provinces+"/test_extends_channel.jsp";
		String shopCarUrl="provinces/"+provinces+"/test_extends_shopcar.jsp";
	%>
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
			<div class="btn-wrp text-center bg-ebf0f3 clear-both" >
				<a id="nextBtn" href="javascript:;" class="btn-a btn-a-blue btn100-30">下一步</a>
			</div>
		</div>
		<!-- 右侧购物车 -->
		<jsp:include page="<%=shopCarUrl%>"></jsp:include>
	</div>
</div>
</body>
</html>