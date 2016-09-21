<%@ page contentType="text/html; charset=utf-8" %>
<div id="planDiv" class="put-center w-818 fleft">
	<div class="step-title color-333 ft18">选产品</div>
	<div class="policy-selected-box bg-ebf0f3 over-hidden">
		<span class="fleft row-name">已选产品：</span>
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
						<th class="text-center" width="6%">序号</th>
						<th class="text-left" width="30%">产品编号及名称</th>                                                                  
						<th class="text-center" width="10%">产品类别</th>
						<th class="text-center" width="10%">产品类型</th>
						<th class="text-center" width="12%">生效时间</th>
						<th class="text-center" width="12%">失效时间</th>
						<th class="text-center" width="10%">是否匹配</th>
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
<!-- 产品详情保存弹窗 -->
<div class="productDetail-dialog-box container">
	<div class="own-dialog productDetail-dialog">
		11111
	</div><!--dialog end-->
<!-- 	<div class="ui-widget-overlay ui-front" style="z-index: 100;"></div>半透明背景 -->
</div>

<!--客户群详情div -->
<div class="showGroupTypeDialog">
	<div class="showGroupTypeDialog-content">
		<table class="showGroupTypeDialog-detail">
			<tbody>
			<tr>
				<td class="title">客户群编码：</td>
				<td class="showGroupTypeDialog-id"></td>
				<td class="title">客户群名称：</td>
				<td class="showGroupTypeDialog-name"></td>
			</tr>
			<tr>
				<td class="title">客户群描述：</td>
				<td class="showGroupTypeDialog-desc" colspan="3"></td>
			</tr>
			<tr>
				<td class="title">客户群筛选条件：</td>
				<td class="showGroupTypeDialog-filter" colspan="3"></td>
			</tr>
			<tr>
				<td class="title">客户群创建人：</td>
				<td class="showGroupTypeDialog-creater"></td>
				<td class="title">客户群创建时间：</td>
				<td class="showGroupTypeDialog-createtime"></td>
			</tr>
			<tr>
				<td class="title">客户群周期：</td>
				<td class="showGroupTypeDialog-updatecycle"></td>
				<td class="title">客户群接收数据日期：</td>
				<td class="showGroupTypeDialog-datatime"></td>
			</tr>
			<tr>
				<td class="title">客户群状态：</td>
				<td class="showGroupTypeDialog-effectivetime"></td>
				<td class="title">客户群失效日期：</td>
				<td class="showGroupTypeDialog-failtime"></td>
			</tr>
			</tbody>
		</table>
	</div>
</div>