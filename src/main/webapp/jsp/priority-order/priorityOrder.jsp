<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>优先级管理</title>
<%@ include file="../../jsp/common/head.jsp" %>

<link href="<%=request.getContextPath()%>/assets/css/provinces/jx/bull/bulkManage.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/assets/css/tacticsorder/createSaleOrder.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	_ctx = "<%=request.getContextPath()%>";
</script>
</head>
<body>
	<%@ include file="../../jsp/common/header.jsp" %>
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
<script type="text/javascript">
	seajs.use("priority-order/priorityOrder.js", function(priorityOrder) {
		priorityOrder.init();
	});
</script>
</html>