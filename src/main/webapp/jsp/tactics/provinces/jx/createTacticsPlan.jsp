<%@ page contentType="text/html; charset=utf-8" %>
<div id="planDiv" class="put-center w-818 fleft">
	<div class="step-title color-333 ft18">选政策</div>
	<div class="policy-selected-box bg-ebf0f3 over-hidden">
		<span class="fleft row-name">已选政策：</span>
		<div id="divChoosedPlan" class="fleft ft12 color-666 policy-selected-right" ></div>
	</div>
	<div class="bg-fff">
		<!--输入框-->
		<div class="search-box-wrp">
			<div class="search-box">
				<p class="fleft">
					<input id="inputKeywordPlan" name="inputKeywordPlan" placeholder="请输入关键字" type="text">
				</p>
				<i id="planSearchBtn" class="searchBtn fright"></i>
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
					<span planSrvType="" class="active">不限</span> <span planSrvType="1">单产品
					</span> <span planSrvType="2">政策</span>
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
						<th class="text-center" width="10%">序号</th>
						<th class="text-left" width="40%">政策编号及名称</th>
						<th class="text-center" width="10%">政策粒度</th>
						<th class="text-center" width="15%">政策归属</th>
						<th class="text-center" width="15%">是否已匹配</th>
						<th class="text-center" width="10%">操作</th>
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
	</div>
</div>