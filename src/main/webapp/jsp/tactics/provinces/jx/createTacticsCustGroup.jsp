<%@ page language="java" pageEncoding="UTF-8"%>
<div id="cgDiv" class="put-center w-818 fleft"  style="display: none">
	<!--step2***************************st-->
	<div class="step-title color-333 ft18">选客户</div>
	<div class="policy-selected-box over-hidden">
		<span class="fleft row-name">已选客户群：</span>
		<div id="selCgDiv" class="fleft ft12 color-666 policy-selected-right" style="display:none">
			<span class="policy" id = "selcgListName"> <i class="close" id="closeGroup"> &times;</i> <em>
			</em>
			</span>
		</div>
	</div>
	<div class="bg-fff">
		<!--输入框-->
		<div class="search-box-wrp">
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
</div>
