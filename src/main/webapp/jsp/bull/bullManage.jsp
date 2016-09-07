<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>群发管理</title>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/mcd/assets/images/logos/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

<link rel="stylesheet" type="text/css" href="${ctx}/mcd/assets/styles/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/mcd/assets/styles/jqueryUI/jquery-ui-1.11.0.min.css" />
<link href="${ctx}/mcd/assets/styles/bulkManage.css" rel="stylesheet" type="text/css" />
<%-- <link rel="stylesheet/less" type="text/x-less" href="${ctx}/mcd/assets/styles/createTactics.less" /> --%>
<link href="<%=request.getContextPath()%>/wro/resetDialog-jqueryUI.css" rel="stylesheet" type="text/css"> 
<link href="<%=request.getContextPath()%>/wro/createTactics.css" rel="stylesheet" type="text/css">

<%-- <link rel="stylesheet" type="text/css" href="${ctx}/mcd/assets/styles/common.css" /> --%>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/mcd/assets/styles/jqueryUI/jquery-ui-1.11.0.min.css" /> --%>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/mcd/assets/styles/quotaManage.css" /> --%>
<%-- <link href="<%=request.getContextPath()%>/wro/resetDialog-jqueryUI.css" rel="stylesheet" type="text/css">  --%>
<%-- <link href="<%=request.getContextPath()%>/wro/createTactics.css" rel="stylesheet" type="text/css"> --%>

<script type="text/javascript" src="${ctx}/mcd/assets/scripts/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${ctx}/mcd/assets/scripts/bulk/percent.js"></script>
<style type="text/css">

.bulk-city-day-month-quota {
    width: 520px;
    height: 36px;
    background: #eadeac;
}
</style>

</head>
<jsp:include page="/mcd/pages/header/header.jsp"></jsp:include>
<body class="bulk-body">
	<div class="bulk-container"  <c:if test="${cityId == '999'}">style="display:none"</c:if>>
	 		
		<div class="bulk-total-quota" >
			<div class="bulk-quota-title fleft">
				<i class="fleft bulk-quota-icon"></i>
				<span>当月配额</span>
			</div>
			<div class="bulk-total-quota-column fleft">
				<div class="bulk-total-quota-column-inner percent-inner-column">${cityStatis.cityUsedPercent}</div>
			</div>
		</div>
		<div class="bulk-total-quota">
			<div class="bulk-quota-title fleft">
				<i class="fleft bulk-quota-icon"></i>
				<span>今日配额</span>
			</div>
			<div class="bulk-total-quota-column fleft">
				<div class="bulk-total-quota-column-inner percent-inner-column">${cityQuotaStatisDay.cityUsedPercentDay}</div>
			</div>
		</div>
		<div class="bulk-legend-box clearfix">
			<p class="fleft bulk-legend-item">
				<i class="fleft bulk-legend-icon"></i>
				<em class="fleft">总配额</em>
			</p>
			<p class="fleft bulk-legend-item">
				<em class="fright">已用配额</em>
				<i class="fright bulk-legend-right-icon"></i> 
			</p>
		</div>
		
		
		<div class="bulk-quota-column">
			<c:forEach items="${currentStatis}" var="cs" varStatus="status">
				<div class="bulk-quota-column-item fleft">
					<div class="bulk-quota-item-title">${cs.deptName}</div>
					<div class="clearfix">
						<div class="bulk-quota-column-box fleft">
							<div class="bulk-quota-column-box-inner percent-inner-column">${cs.monthUsedPercent}</div>
						</div>
						<div class="fleft">月配额</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<div class="bulk-table-content">
		<div class="bulk-container">
			<div class="bulk-table-top clearfix">
				<div class="fleft bulk-table-tab">
					<p class="fleft bulk-table-tab-tem J_sendType <c:if test='${currentSendType == "1"}'>active</c:if> " sendType="1">顺序发送</p>
					<p class="fleft bulk-table-tab-tem J_sendType <c:if test='${currentSendType == "3"}'>active</c:if>" sendType="3">等量轮询</p>
				</div>
				<div class="fright bulk-table-btn-box J_taskOP">
					<button type="button" class="bulk-quota-btn fright J_taskStopAll" style="margin-left: 10px;">
						<span class="J_taskStopAll" style="padding-right:8px;">全部暂停</span> <span class="bulk-stop-icon"></span>
					</button>
					<button type="button" class="bulk-quota-btn fright last-child J_taskStartAll">
						<span class="J_taskStartAll"  style="padding-right:8px;">全部开始</span> <span class="bulk-start-icon"></span>
					</button>
					<div class="bulk-select-box fright">
						<span class="fleft">科室筛选:</span> 
						<span class="fleft bulk-select-item J_deptList"> 
							<em class="fleft bulk-select-text">科室选择</em>
							<em class="fright bulk-select-more"></em> 
							<div class="bulk-select-blank"></div>
						</span>
					</div>
					<ul class="bulk-dept-list hidden" id="deptBox">
					</ul>
				</div>
			</div>
		 	<div class="bulk-list-content">
				<ul class="bulk-list-content">
					<li class="bulk-list-thead">
						<span class="bulk-item-num fleft">序号</span>
						<span class="bulk-item-name fleft">策略名称</span>
						<span class="bulk-item-department fleft">科室</span>
						<span class="bulk-item-user fleft">策划人</span>
						<span class="bulk-item-date fleft">提交时间</span>
						<span class="bulk-item-quantity fleft">客户群数量</span>
						<span class="bulk-item-quantity fleft">发送总量</span>
						<span class="bulk-item-quantity fleft">过滤数量</span>
						<span class="bulk-item-status fleft">发送状态</span> 
						<span class="bulk-item-operation fleft">操作</span>
					</li>
				</ul>
				<ul class="bulk-list-content" id="sortListItem">
				</ul>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		_ctx = "${ctx}";
	</script>
	<script type="text/javascript" src="${ctx}/mcd/assets/scripts/seajs/sea.js"></script>
	<script type="text/javascript" src="${ctx}/mcd/assets/scripts/seajs/seajs-preload.js"></script>
	<script type="text/javascript" src="${ctx}/mcd/assets/scripts/seajs/sea-config.js"></script>
	<script type="text/javascript">
		seajs.use("${ctx}/mcd/assets/scripts/bulk/bulkManage",function(main){
			main.init();
		});
	</script>
</body>
</html>


