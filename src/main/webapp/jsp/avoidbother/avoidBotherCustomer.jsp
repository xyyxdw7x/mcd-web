<%@ page contentType="text/html; charset=utf-8" %> 
<!DOCTYPE html>
<html>
<head>
<title>消息免打扰</title>
<%@ include file="../../jsp/common/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/put/resetDialog-jqueryUI.css">
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/put/disturb.css">
</head>
<script type="text/javascript">
	_ctx = "<%=request.getContextPath()%>";
</script>
<body >
<jsp:include page="../../jsp/common/header.jsp"></jsp:include>
<div class="container">
		<div id="tacticsManageTabCT" class="content-main content-main-avoid">
			<div class="box active">
				<ul class="content-type-outer-box">
					<li class="content-type-search">
						<div class="content-type-search-box fleft">
							<p class="fleft">
								<input type="text" name="search" placeholder="请输入手机号"/>
							<p>
							<i id="searchButton_mine" class="searchBtn fright"></i>
						</div>
					</li>
					<li class="avoidBotherUserType clearfix content-type-item ">
					</li>
					<li class="clearfix content-type-item ">
						<div class="fleft content-type-tite">通用渠道：</div>
						<div class="fleft content-type-list">
							<div class=" content-type-item-inner">
								<div id="channelIdDiv" class="content-type-inner">
									<span onclick="channelChange(this)" class="fleft content-type-box" channelId="">全部</span>
								</div>
							</div>
						</div>
					</li>
					<!-- 2016/10/11时点，不需要营销类型。但以后可能需要所以暂时先注释掉。
					<li class="avoidBotherCampsegType clearfix content-type-item"></li>
					 -->
				</ul>
			</div><!--box end -->
	
		<div class="tools">
			<ol class="fright">
				<li>
					<button type="button" class="button button-info" id="showAddBtn">新增</button>
				</li>
				<li>
					<button type="button" class="button button-default" id="batchRmBtn">批量删除</button>
					<input type="hidden" name="sortColumn" id="sortColumn" value="ENTER_TIME">
					<input type="hidden" name="sortOrderBy" id="sortOrderBy" value="desc">
				</li>
			</ol>
		</div>
		<div class="common-table-box">
			<form id="batchRmBotherAvoidUserForm" action="">
				<table class="common-table">
					<thead>
					<tr>
						<th width="60">
							<input type="checkbox" id="selectAll" name="" value="" class="checkbox_all"/>
						</th>
						<th>手机号码</th>
						<th>用户类型</th>
						<th>通用渠道</th>
						<!--  <th>营销类型</th> -->
						<th>创建人</th>
						<th width="160">
							<div class="sel-box">
								<div class="sel-txt">创建时间</div>
							</div>
						</th>
						<th width="160" >操作</th>
					</tr>
					<thead>
					<tbody id="avoidBotherCustomerTable">
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

<!-- 弹出框-->
<!-- 新增加弹出框 -->
<div class="modal fade" id="addDialog" tabindex="-1" role="dialog" aria-labelledby="openAddOpportunityLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content width420">
            <div class="modal-header">
                <button type="button" class="close close-add-btn" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="openAddOpportunityLabel">新增</h4>
            </div>
            <div class="modal-body">
            	<form id="newBotherAvoidUserForm" method="post" action="" enctype="multipart/form-data">
				<div class="add-form-box">
               	  <ol>
               	  	<li>
               	  		<label class="fleft labFmt80">用户类型：</label>
               	  		<select id="userTypeId" name="userTypeId" class="fleft sel115 avoidBotherUserTypeSelect">
               	  		</select>
               	  	</li>	
               	  	<li>
               	  		<label class="fleft labFmt80">通用渠道：</label>
               	  		<select id="avoidBotherType" name="avoidBotherType" class="fleft sel115 avoidBotherChannelTypeSelect">
               	  		</select>
               	  	</li>	
               	  	<!-- 2016/10/11时点，不需要营销类型。但以后可能需要所以暂时先注释掉。
               	  	<li>
               	  		<label class="fleft labFmt80">营销类型：</label>
               	  		<select id="avoidCustType" name="avoidCustType" class="fleft sel115 avoidBotherCampsegTypeSelect">
               	  		</select>
               	  	</li>
               	  	 -->
               	  	<li class="way">
               	  		<a href="javascript:void(0)" id="handBtn">批量导入</a>
               	  		<a href="javascript:void(0)" class="none" id="importBtn">手动输入</a>
               	  		<a href="uploadmodel.csv" target="_blank" class="none" id="downloadModelBtn">下载模板</a>
               	  	</li>
               	  	<li>
               	  		<label class="fleft labFmt80" >导入号码：</label>
               	  		<textarea name="productNo" class="fleft textarea280_110"  id="handInput">请输入手机号码，多个号码用逗号分开。</textarea>
               	  		<div class="fleft file-box none" id="importInput">
               	  			<input type="text" value="" id="show_upload_file" name="show_upload_file" class="fleft fileText" />
               	  			<input type="file" id="filterFile" name="filterFile" class="fright file"/>
               	  			<a href="javascript:void(0);" class="fright fileBtn"></a>
               	  		</div>	
               	  	</li>
               	  </ol>
               </div>
               <!-- 2016/10/11时点，不需要营销类型。
               		页面上的选择框注释掉了。但后台逻辑还在，为了今后恢复方便，加了此hidden项目。
               		今后营销类型恢复的时候，要删掉。-->
               <input type="hidden" id="avoidCustTypeForAdd" name="avoidCustType" value="1"/>
               
               </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="button button-default close-add-btn" data-dismiss="modal">取消</button>
                <button type="button" class="button button-info" id="addBtn">确认</button>
            </div>
        </div>
    </div>
</div>	

   <!-- 修改提示 -->
<div class="modal fade" id="editDialog" tabindex="-1" role="dialog" aria-labelledby="openAddOpportunityLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content width420">
            <div class="modal-header">
                <button type="button" class="close close_edit_btn" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="openAddOpportunityLabel">修改</h4>
            </div>
            <div class="modal-body">
				<form id="mdfBotherAvoidUserForm" action="">
				<div class="add-form-box">
					<ol>
	               	  	<li>
	               	  		<label class="fleft labFmt80">手机号码：</label>
	               	  		<label class="fleft labFmt80  labFmt80-big avoidBotherproductNo"></label>
	               	  		
	               	  	</li>	
	               	  	<li>
	               	  		<label class="fleft labFmt80">用户类型：</label>
	               	  		<select id="edit-avoid-bother-customer-select-user-type" name="userTypeId" class="fleft sel115 avoidBotherUserTypeSelect">
	               	  			<option value="">访问客户</option>
	               	  		</select>
	               	  	</li>	
	               	  	<li>
	               	  		<label class="fleft labFmt80">通用渠道：</label>
	               	  		<select id="edit-avoid-bother-customer-select-channel-type" name="avoidBotherType" class="fleft sel115 avoidBotherChannelTypeSelect">
	               	  		</select>
	               	  	</li>
	               	  	<!-- 2016/10/11时点，不需要营销类型。但以后可能需要所以暂时先注释掉。	
	               	  	<li>
	               	  		<label class="fleft labFmt80">营销类型：</label>
	               	  		<select id="edit-avoid-bother-customer-select-campseg-type" name="avoidCustType" class="fleft sel115 avoidBotherCampsegTypeSelect">
	               	  		</select>
	               	  	</li>
	               	  	-->
					</ol>
				</div>
				<input type="hidden" id="productNoBef" name="productNoBef" value=""/>
			    <input type="hidden" id="avoidCustTypeBef" name="avoidCustTypeBef" value=""/>
			    <input type="hidden" id="avoidBotherTypeBef" name="avoidBotherTypeBef" value=""/>
			    <input type="hidden" id="updateConfirmFlg" name="updateConfirmFlg" value=""/>
                <!-- 2016/10/11时点，不需要营销类型。
                		页面上的选择框注释掉了。但后台逻辑还在，为了今后恢复方便，加了此hidden项目。
               	 		今后营销类型恢复的时候，要删掉。-->
			    <input type="hidden" id="avoidCustTypeForEdit" name="avoidCustType" value="1"/>
		    	</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="button button-default close-add-btn" data-dismiss="modal">取消</button>
                <button type="button" class="button button-info" id="edit_btn">确认</button>
            </div>
        </div>
    </div>
</div>    
<!-- 删除提示 -->
<div class="modal fade" id="delDialog" tabindex="-1" role="dialog" aria-labelledby="openAddOpportunityLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content width420">
            <div class="modal-header">
                <button type="button" class="close close_rm_btn" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="openAddOpportunityLabel">提示</h4>
            </div>
            <div class="modal-body">
            	<form id="rmBotherAvoidUserForm" action="">
                   <div class="add-form-box">
					<ol>
                   	  	<li>
                    	  	<p class="delTip">
								<i class="fleft icon_mark"></i>
								<span class="fleft">您确定要删除所选信息吗？</span>
								<input type="hidden" id="rmProductNo" name="productNo" value=""/>
					            <input type="hidden" id="rmAvoidBotherType" name="avoidBotherType" value=""/>
					            <input type="hidden" id="rmAvoidCustType" name="avoidCustType" value=""/>
							</p>
						</li>	
					</ol>
                   </div>
				</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="button button-default close-add-btn" data-dismiss="modal">取消</button>
                <button type="button" class="button button-info" id="remove_btn">确认</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
	seajs.use("<%=contextPath%>/assets/js/avoidbother/avoidBotherCustomer",function(main){
		main.init();
	});
</script>
</body>
</html>