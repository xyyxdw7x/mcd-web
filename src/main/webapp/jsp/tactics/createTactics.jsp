<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>新建策略</title>
<%@ include file="../../jsp/common/head.jsp" %>
<link  type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/put/newCreateTactics.css" />
<link  rel="stylesheet" type="text/css" href="<%=contextPath%>/assets/css/lib/tooltip.css" />

<script type="text/javascript" src="<%=contextPath%>/assets/js/tactics/provinces/<%=provinces%>/createTacticsPlan.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/tactics/provinces/<%=provinces%>/createTacticsCustGroup.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/tactics/provinces/<%=provinces%>/createTacticsChannel.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/tactics/provinces/<%=provinces%>/createTacticsShoppingCart.js"></script>
</head>
<body class="bg-fa">
	<%
	    String planUrl="provinces/"+provinces+"/createTacticsPlan.jsp";
		String customerGroupUrl="provinces/"+provinces+"/createTacticsCustGroup.jsp";
		String channelUrl="provinces/"+provinces+"/createTacticsChannel.jsp";
		String shopCarUrl="provinces/"+provinces+"/createTacticsShoppingCart.jsp";
	%>
	<%@ include file="../../jsp/common/header.jsp" %>
	
	<div  class="perCommend-bg none"></div><!-- 个性化功能推荐弹窗背景图 -->
	<div class="put-container w-1200">
		<!-- 左侧数字导航区 -->
		<div class="put-left fleft ft14 mtop_47">
			<ol id="stepOl">
				<li class="active"><span>选产品</span><i>1</i></li>
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
	<script type="text/javascript">
	    seajs.use("tactics/createTactics.js",function(createTactics){
	    	createTactics.init();
	    });
	</script>
	<script type="text/javascript" src="<%=contextPath%>/assets/js/tactics/provinces/<%=provinces%>/insertContent.js"></script>
</body>
</html>