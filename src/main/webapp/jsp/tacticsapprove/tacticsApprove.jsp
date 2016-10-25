<%@ page contentType="text/html; charset=utf-8" %> 
<!DOCTYPE html>
<html>
<head>
<title>策划审批</title>
<%@ include file="../../jsp/common/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/put/resetDialog-jqueryUI.css">
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/put/approve.css">
</head>
<script type="text/javascript">
	_ctx = "<%=request.getContextPath()%>";
</script>
<body >
<jsp:include page="../../jsp/common/header.jsp"></jsp:include>
<div class="container">
	<div id="tacticsApproveTabCT" class="content-main content-main-approve">
		<div class="common-table-box">
			<form id="tacticsApproveUserForm" action="" class="form-main">
				<table class="common-table">
					<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="65%">策略名称</th>
						<th width="10%">策划人</th>
						<th width="10%">
							<div class="sel-box">
								<div class="sel-txt">提交时间</div>
							</div>
						</th>
						<th width="10%" >审批</th>
					</tr>
					<thead>
					<tbody id="tacticsApproveTable">
					</tbody>
				</table>
				<div class="content-table-page">
					<div class="fright clearfix centent-page-box" id="centent-page-box-div">
				
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!--container end -->

</div>
<script type="text/javascript">
	seajs.use("<%=contextPath%>/assets/js/tacticsapprove/tacticsApprove",function(main){
		main.init();
	});
</script>
</body>
</html>