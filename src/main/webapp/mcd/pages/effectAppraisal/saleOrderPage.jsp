<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>优先级管理</title>

<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link rel="shortcut icon" href="<%=request.getContextPath()%>/mcd/assets/images/logos/favicon.ico" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mcd/assets/styles/common.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mcd/assets/styles/jqueryUI/jquery-ui-1.11.0.min.css" /> 
<link href="<%=request.getContextPath()%>/mcd/assets/styles/bulkManage.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/assets/css/tacticsorder/createSaleOrder.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	_ctx = "<%=request.getContextPath()%>";
</script>
</head>
<body>
	<jsp:include page="../header/header.jsp"></jsp:include>
	<div class="container effectContainer">
		<div class="content">
			<div id="" class="content-main">
				<ul class="content-type-outer-box">

					<li id="effectOverviewCity" class="clearfix content-type-item">
						<div class="fleft content-type-tite">线上渠道优先级管理：</div>
						<div class="fleft content-type-list">
							<div class="content-type-item-inner">
								<div class="content-type-inner">
								<table  id="content_offline" border=0></table>
									<!-- 									<span class="fleft content-type-box J_campType">手机营业厅(6)</span> -->
									<!-- 									<span class="fleft content-type-box J_campType">门户网站(4)</span> -->
									<!-- 									<span class="fleft content-type-box J_campType">BOSS运营位(6)</span> -->
									<!-- 									<span class="fleft content-type-box J_campType">微信(4)</span> -->
								</div>
							</div>
						</div>
						<div id="content-type-right-online" class="fleft content-type-right" onclick="clickOpen(this)"></div>
					</li>
					<hr style="border: 1px dashed #e3e3e3;">
					<li id="effectOverviewChannels" class="clearfix content-type-item">
						<div class="fleft content-type-tite">线下渠道优先级管理：</div>
						<div class="fleft content-type-list">
							<div class=" content-type-item-inner">
								<div class="content-type-inner" >
								<table  id="content_online" border=0></table>
									<!-- 									<span class="fleft content-type-box J_campType">手机营业厅(6)</span> -->
									<!-- 									<span class="fleft content-type-box J_campType">门户网站(4)</span> -->
									<!-- 									<span class="fleft content-type-box J_campType">微信(4)</span> -->
								</div>
							</div>
						</div>
							<div id="content-type-right-offline" class="fleft content-type-right" onclick="clickOpen(this)"></div>
					</li>

				</ul>
				<div id="manualList"></div>
				<div id="sysAutoList"></div>
			</div>

		</div>
	</div>
	<!--container end -->

</body>
<script type="text/javascript" src="${ctx}/mcd/assets/scripts/seajs/sea.js"></script>
<script type="text/javascript" src="${ctx}/mcd/assets/scripts/seajs/seajs-preload.js"></script>
<script type="text/javascript" src="${ctx}/mcd/assets/scripts/seajs/sea-config.js"></script> 
<script type="text/javascript">
	seajs.use("${ctx}/mcd/assets/scripts/home/here_it_is_4_order_sale", function(main) {
		main.init();
	});
	seajs.use("${ctx}/mcd/assets/scripts/nav/navManage",function(main) {
		main.init();
	});

</script>
</html>