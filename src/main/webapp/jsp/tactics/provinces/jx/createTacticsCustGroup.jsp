<%@ page language="java" pageEncoding="UTF-8"%>
<div id="cgDiv" class="put-center w-818 fleft"  style="display: none">
	<!--step2***************************st-->
	<div class="step-title color-333 ft18">选客户</div>
	<div class="policy-selected-box bg-ebf0f3 over-hidden">
		<span class="fleft row-name row-name-custom">已选客户群：</span>
		<div id="selCgDiv" class="fleft ft12 color-666 policy-selected-right" style="display:none">
			<span class="policy" id = "selcgListName"> <i class="close" id="closeGroup"> &times;</i> <em>
			</em>
			</span>
		</div>
	</div>
	<div class="bg-fff">
		<!--输入框-->
		<div class="search-box-wrp search-box-wrp-custom">
			<div class="search-box">
				<p class="fleft">
					<input id="cgSearchInput" placeholder="请输入关键字" type="text" >
				</p>
				<i id="cgSearchBtn" class="searchBtn fright"  id="searchBtn"></i>
			</div>
		</div>
		<!--搜索结果表格-->
		<div class="search-tab-wrp search-list-wrp">
			<ul id="cgList" class="group-info-ul public-border-ul">
			</ul>
			<!--分页-->
			<div class="content-table-page">
				<div id="custGroupPage" class="fright clearfix centent-page-box">
					
				</div>
			</div>
		</div>
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
					<td class="title title-date">客户群接收数据日期：</td>
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
</div>
