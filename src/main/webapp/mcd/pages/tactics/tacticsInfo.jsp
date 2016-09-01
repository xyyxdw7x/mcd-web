﻿<%@ page contentType="text/html; charset=utf-8" %> 
<!DOCTYPE html>
<html>
<head>
<title>策略详情-查看</title>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/mcd/assets/images/logos/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<%-- <link href="<%=request.getContextPath()%>/mcd/assets/styles/dataDrilling.css" rel="stylesheet" type="text/css"> --%>
<link rel="stylesheet/less" type="text/css" href="../../assets/styles/common.css" />
<link rel="stylesheet/less" type="text/css" href="../../assets/styles/jqueryUI/jquery-ui-1.11.0.min.css" />
<link href="../../../wro/createTactics.css" rel="stylesheet" type="text/css">
<link href="../../../wro/tacticsInfo.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
	_ctx = "<%=request.getContextPath()%>";
</script>
<body class="bg-white">
<div class="navigate navHeader">
	<div class="logAndInfo">
		<div class="floatLeft">
			<img class="log1Imag" height="42" style="margin:4px 0 0 15px;" src="<%=request.getContextPath()%>/mcd/assets/images/logos/logo.png" alt="log">
		</div>
	</div>
	<div id="dataInfo">策略详情查看</div>
</div>
<!-- <div class="header tacticsInfo-header"> -->
<!-- 	<h1>策略详情查看</h1> -->
<!-- 	<!--  -->
<!-- 	<div class="logo"></div> -->
	
<!-- </div>header end -->
<div class="tactics-state">
	<div id="tacticsDetail">
	</div>
	<ul id="tactics-state-tab" class="tactics-state-tab">
		<li class="active" data-type="tacticsInfo">基础信息</li>
		<li data-type="tacticsRunInfo">执行信息</li>
		<li data-type="tacticsLog">审批状态</li>
	</ul>
</div>

<div class="container tabbox active">
	<div id="subCamsegTab" class="left-nav">
	</div> 
	<div id="subCamsegBoxesCT">
	</div><!--subCamsegBoxesCT end -->
</div><!--container end -->


<div class="container tabbox">
	<div class="left-nav" id="channelExeStateList">
<!-- 		<div class="left-nav-box left-nav-active"> -->
<!-- 			<span>规则1</span> -->
<!-- 			<i class="fright left-nav-bg" data-num="1"></i> -->
<!-- 		</div> -->
<!-- 		<div class="left-nav-line"></div> -->
<!-- 		<div class="left-nav-box"> -->
<!-- 			<span>规则2</span> -->
<!-- 			<i class="fright left-nav-bg" data-num="2"></i> -->
<!-- 		</div> -->
<!-- 		<div class="left-nav-line"></div> -->
<!-- 		<div class="left-nav-box"> -->
<!-- 			<span>规则3</span> -->
<!-- 			<i class="fright left-nav-bg" data-num="3"></i> -->
<!-- 		</div> -->
	</div> 
	<div id="channelExeStateDiv" class="content">
	</div>
</div><!--container2 end -->

<div class="container tabbox">
	<div id="logRecordCT" class="logRecordCT">
	</div>
</div><!--container3 end -->
</body>
</html>

<script type="text/javascript" src="../../assets/scripts/seajs/sea.js"></script>
<script type="text/javascript" src="../../assets/scripts/seajs/seajs-preload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/mcd/assets/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../assets/scripts/seajs/sea-config.js"></script>
<script type="text/javascript">
	seajs.use("../../assets/scripts/tactics/tacticsInfo",function(main){
		main.init();
	});
</script>