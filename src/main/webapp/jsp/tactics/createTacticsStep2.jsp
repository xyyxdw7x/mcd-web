<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>选政策</title>
<link rel="stylesheet" type="text/css" href="${ctx}/assets/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/css/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/css/provinces/jx/main.css" />
<!--江西 公共导航头部 -->
<link rel="stylesheet" type="text/css" href="${ctx}/assets/css/provinces/jx/public-header.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/css/provinces/jx/put/newCreateTactics.css" />
<script type="text/javascript" src="${ctx}/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/bootstrap/bootstrap.min.js"></script>

<script type="text/javascript">
	$(function() {
		$('.public-table tbody tr:nth-child(2n+1)').addClass('odd');//奇数行添加背景色
		$('.public-table tbody tr:nth-child(2n)').addClass('even');//偶数行添加背景色
	})
	var _ctx='${ctx}';
</script>
</head>
<body>
	<!-- 头部导航 用原来的 st -->
	<jsp:include page="/mcd/pages/header/header.jsp"></jsp:include>
		
	<!-- 头部导航 用原来的 end -->

	<!-- 新建策略 st -->
	<div class="put-container w-1200">
		<!-- 左 -->
		<div class="put-left fleft ft14 mtop_47">
			<ol>
				<li class="active">
					<span onclick="alert('TODO! should go back to step1')">选政策</span><i>1</i>
				</li>
				<li class="line"></li>
				<li>
					<span onclick="alert('TODO! should go back to step2')">选客户</span><i>2</i>
				</li>
				<li class="line"></li>
				<li>
					<span onclick="alert('TODO! should go back to step3')">选渠道</span><i>3</i>
				</li>
			</ol>
		</div>
		<!-- 中 -->
		<div class="put-center w-818 fleft">
			<!--step1***************************st-->
			<div class="step-title color-333 ft18">选政策</div>
			<div class="policy-selected-box bg-ebf0f3 over-hidden">
				<span class="fleft row-name">已选政策：</span>
				<div class="fleft ft12 color-666 policy-selected-right" id="divChoosedPlan">
				</div>
			</div>
			<div class="bg-fff">
				<!--输入框-->
				<div class="search-box-wrp">
					<div class="search-box">
						<p class="fleft">
							<input id="keyword" name="keyword" placeholder="请输入关键字" type="text">
						</p>
						<i class="searchBtn fright" onclick="queryPolicy(1)"></i>
					</div>
				</div>
				<!--选择-->
				<div class="select-item-wrp ft12">
					<div class="item-box">
						<span class="fleft select-left">产品类别：</span>
						<div id="divDimPlanTypes" class="fleft color-666 select-right">
							<span class="active">不限</span>
						</div>
					</div>
					<div class="item-box">
						<span class="fleft select-left">产品类型：</span>
						<div id="divDimPlanSrvType" class="fleft color-666 select-right">
							<span planSrvType="" class="active">不限</span>
							<span planSrvType="1">单产品 </span>
							<span planSrvType="2">政策</span>
						</div>
					</div>
					<div class="item-box">
						<span class="fleft select-left">渠道类型：</span>
						<div id="divDimChannels" class="fleft color-666 select-right">
							<span class="active">不限</span>
						</div>
					</div>
				</div>
				<!--搜索结果表格-->
				<div class="search-tab-wrp">
					<table class="table ft12 public-table">
						<thead>
							<tr>
								<th class="text-center" width="8%"><input type="checkbox" /></th>
								<th class="text-center" width="10%">序号</th>
								<th class="text-left" width="40%">政策编号及名称</th>                                                                  
								<th class="text-center" width="12%">政策粒度</th>
								<th class="text-center" width="15%">政策归属</th>
								<th class="text-center" width="15%">是否已匹配</th>
							</tr>
						<thead>
						<tbody class="color-666" id="tbodyPlansList">
							
						</tbody>
					</table>
					<!--分页-->
					<div class="content-table-page">
						<div class="fright clearfix centent-page-box" id="divPlansPage">
							
						</div>
					</div>
				</div>
				<!--按钮区-->
				<div class="btn-wrp text-center bg-ebf0f3">
					<a href="javascript:;" class="btn-a btn-a-blue btn100-30">下一步</a>
				</div>
			</div>
			<!--step1***************************end-->
		</div>
		<!-- 右 -->
		<div class="put-right fright bg-fff">
			<div class="top-hd">工作站 </div>
			<div class="info-box">
				<h5>营销政策</h5>
				<ul id="ulWorkspacePlanList">

				</ul>
			</div>
			<div class="info-box">
				<h5>适配客户群</h5>
				<div class="custom" id="divWorkspaceCustGroupList">
					<span class="color-333 mright_10">北京测试客户群3</span>
				</div>
			</div>
			<div class="info-box">
				<h5>适配渠道</h5>
				<div class="custom" id="divWorkspaceChannelList">
					<p class="ft14">短信</p>
					<div>
						<p><span class="color-666">短信触发时机：</span><em class="color-333">***********</em> </p>
						<p><span class="color-666">营销用语替换变量：</span><em class="color-333">*********** *********** *********** *********** </em> </p>
    					<p><span class="color-666">发送周期：</span><em class="color-333">周期性</em> </p>
    					<p><span class="color-666">派发时间：</span><em class="color-333">从2016-08开始，每个月1号 </em> </p>
    					<p><span class="color-666">频次控制：</span><em class="color-333">*********** </em> </p>
    					<p><span class="color-666">免打扰用户：</span><em class="color-333">主动要求屏蔽客户；敏感投诉客户；黑名单******</em> </p>
    					<p><span class="color-666 block">群发用语：</span><em class="color-333">***************************************************************</em> </p>
					</div>
					<hr />
					<p class="ft14">BOSS运营位</p>
					<p><span class="color-333 mright_10">***********</span><span class="color-333 mright_10">***********</span><span class="color-333 mright_10">***********</span></p>
				</div>
			</div>
			<!--保存按钮-->
			<div class="btn-wrp  btn-wrp10 text-center bg-ebf0f3">
				<a href="javascript:;" class="btn140-40">保存</a>
			</div>
		</div>
	</div>
	<!-- 新建策略 end -->
</body>
<script type="text/javascript" src="${ctx}/assets/js/tactics/createTacticsStep1.js" ></script>
<script type="text/javascript" src="${ctx}/mcd/assets/scripts/EJS/ejs_production.js" ></script>
</html>