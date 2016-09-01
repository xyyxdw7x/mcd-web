<%@ page contentType="text/html; charset=utf-8" %> 
<!DOCTYPE html>
<html>
<head>
<title>策略管理</title>

<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link rel="shortcut icon" href="<%=request.getContextPath()%>/mcd/assets/images/logos/favicon.ico" />

<link rel="stylesheet/less" type="text/css" href="../../assets/styles/common.css" />
<link rel="stylesheet/less" type="text/css" href="../../assets/styles/jqueryUI/jquery-ui-1.11.0.min.css" />
<!--<link rel="stylesheet/less" type="text/x-less" href="../../assets/styles/tacticsManage.less" />-->
<!--<link rel="stylesheet/less" type="text/x-less" href="../../assets/styles/common/resetDialog-jqueryUI.less" />-->
<link href="../../../wro/resetDialog-jqueryUI.css" rel="stylesheet" type="text/css">
<link href="../../../wro/tacticsManage.css" rel="stylesheet" type="text/css">

</head>
<script type="text/javascript">
	_ctx = "<%=request.getContextPath()%>";
</script>
<body>
<jsp:include page="../header/header.jsp"></jsp:include>
<div class="myTacticsQuery">
	<ul id="tacticsManageQueryTab" class="tacticsManageQueryTab" dataCT="tacticsManageTabCT" >
		<li class="active">我的策略<span class="icon_arrUp"></li>
		<li data-tab="ALL">全部策略<span class="icon_arrUp"></li>
	</ul>
</div>

<div class="container tacticsManageContainer">
	<div class="content">
		<div id="tacticsManageTabCT" class="content-main">
			<div class="box active">
				<ul class="content-type-outer-box">
					<li class="content-type-search">
						<div class="content-type-search-box fleft">
							<p class="fleft">
								<input type="text" name="search" placeholder="请输入关键字"/>
							<p>
							<i id="searchButton_mine" class="searchBtn fright"></i>
						</div>
					</li>
					<li class="tacticsManageSearchDimCampDrvType clearfix content-type-item">
					</li>
					<li class="tacticsManageSearchCampsegStat clearfix content-type-item ">
					</li>
					<li class="clearfix content-type-item ">
						<div class="fleft content-type-tite">渠道：</div>
						<div class="fleft content-type-list">
							<div class=" content-type-item-inner">
								<div id="channelIdDiv" class="content-type-inner">
									<span onclick="channelChange(this)" class="fleft content-type-box" channelId="">全部</span>
								</div>
							</div>
						</div>
					</li>
				</ul>
				
				<div class="content-table">
					<div id="tacticsTable" class="content-table-box tacticsManageTable">
					</div>
				</div><!-- content-table end -->
			</div><!--box end -->
			
			<div class="box">
				<ul class="content-type-outer-box">
					<li class="content-type-search">
						<div class="content-type-search-box fleft">
							<p class="fleft">
								<input type="text" name="search" placeholder="请输入关键字"/>
							<p>
							<i id="searchButton_all" class="searchBtn fright"></i>
						</div>
					</li>
					<li class="tacticsManageSearchDimCampDrvType clearfix content-type-item">
					</li>
					<li class="J_tacticsAll clearfix content-type-item"></li>
					<li class="clearfix content-type-item ">
						<div class="fleft content-type-tite">渠道：</div>
						<div class="fleft content-type-list">
							<div class=" content-type-item-inner">
								<div id="channelIdDivAll" class="content-type-inner">
									<span onclick="channelChangeAll(this)" class="fleft content-type-box" channelId="">全部</span>
								</div>
							</div>
						</div>
					</li>
					
				</ul>
				<div class="content-table">
					<div id="tacticsTable_all" class="content-table-box tacticsManageTable">
						
					</div>
				</div><!-- content-table end -->
			</div><!--box end -->
		</div>
	</div>
</div><!--container end -->

</body>
</html>
<script type="text/javascript" src="../../assets/scripts/seajs/sea.js"></script>
<script type="text/javascript" src="../../assets/scripts/seajs/seajs-preload.js"></script>
<script type="text/javascript" src="../../assets/scripts/seajs/sea-config.js"></script>
<script type="text/javascript">
	seajs.use("../../assets/scripts/tactics/tacticsManage",function(main){
		main.init();
		
		/*
		各种下拉control列表未完成。。
		var str_manageList=""
		var manageList=$(str_manageList);
		main.tacticsManageControls(".controls.manage",manageList);
		*/
	});
</script>