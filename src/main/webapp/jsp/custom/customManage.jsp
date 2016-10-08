<%@ page contentType="text/html; charset=utf-8" %> 
<!DOCTYPE html>
<html>
<head>
<title>客户群管理</title>
<%@ include file="../../jsp/common/head.jsp" %>
<%@ include file="../../jsp/common/header.jsp" %>
<link  type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/put/customManage.css">
<link  type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/put/resetDialog-jqueryUI.css" />
</head>
<script type="text/javascript">
    _ctx = "<%=request.getContextPath()%>";
</script>
<body>
<div class="myCustomQuery">
    <div class="search-box-container">
	    <div id="search_all" class="content-type-search-box fright hide">
	        <p class="fleft">
	            <input type="text" name="search" placeholder="客戶群编码/客户群名称"/>
	        <p>
	        <i id="searchButton_all" class="searchBtn fright"></i>
	    </div>
	    <div id="search_mine" class="content-type-search-box fright hide">
	        <p class="fleft">
	            <input type="text" name="search" placeholder="客戶群编码/客户群名称"/>
	        <p>
	        <i id="searchButton_mine" class="searchBtn fright"></i>
	    </div>
	    <div id="search_abnormal" class="content-type-search-box fright hide">
	        <p class="fleft">
	            <input type="text" name="search" placeholder="客戶群编码/客户群名称"/>
	        <p>
	        <i id="searchButton_abnormal" class="searchBtn fright"></i>
	    </div>
    </div>
    <ul id="customManageQueryTab" class="customManageQueryTab" dataCT="customManageTabCT" >
        <li data-tab="ALL-CUSTOM" class="active"><div class="tab-select"></div>所有客户群</li>
        <li data-tab="MY-CUSTOM"><div class="tab-select"></div>我的客户群</li>
        <li data-tab="ABNORMAL-CUSTOM"><div class="tab-select"></div>异常客户群</li>
    </ul>
</div>

<div class="container customManageContainer">
    <div class="content">
        <div id="customManageTabCT" class="content-main">
            <div class="box active">
                <div class="content-table">
                    <div id="customTable_all" class="content-table-box customManageTable"></div>
                </div><!-- content-table end -->
            </div><!--box end -->
            
            <div class="box">
                <div class="content-table">
                    <div id="customTable_mine" class="content-table-box customManageTable"></div>
                </div><!-- content-table end -->
            </div><!--box end -->
            
            <div class="box">
                <div class="content-table">
                    <div id="customTable_abnormal" class="content-table-box customManageTable"></div>
                </div><!-- content-table end -->
            </div><!--box end -->
        </div>
    </div>
</div><!--container end -->

<!-- 弹出窗的背景 -->
<div class="pop-win-background"></div>
<!-- 查看客户群 -->
<div class="pop-win look-up-custom-pop">
    <div class="look-up-custom-title">
        <div class="look-up-custom-title-title">客户群详情</div>
        <div class="look-up-custom-title-close">x</div>
    </div>
    <div class="look-up-custom-content">
	    <table class="look-up-custom-table">
	        <tbody>
	            <tr>
	                <td class="title">客户群编码：</td>
	                <td id="detail-custom-group-id" class="content-column1"></td>
	                <td class="title">客户群名称：</td>
	                <td id="detail-custom-name" class="content-column1"></td>
	            </tr>
	            <tr>
	                <td class="title">客户群描述：</td>
	                <td id="detail-custom-desc" colspan="3" class="content-column3"></td>
	            </tr>
	            <tr>
	                <td class="title">客户群筛选条件：</td>
	                <td id="detail-custom-filter" colspan="3" class="content-column3"></td>
	            </tr>
	            <tr>
	                <td class="title">客户群创建人：</td>
	                <td id="detail-custom-creater" class="content-column1"></td>
	                <td class="title">客户群创建时间：</td>
	                <td id="detail-custom-create-time" class="content-column1"></td>
	            </tr>
	            <tr>
	                <td class="title">客户群周期：</td>
	                <td id="detail-custom-update-cycle" class="content-column1"></td>
	                <td class="title">客户群数据日期：</td>
	                <td id="detail-custom-data-time" class="content-column1"></td>
	            </tr>
	            <tr>
	                <td class="title">客户群状态：</td>
	                <td id="custom-effective-time" class="content-column1"></td>
	                <td class="title">客户群失效日期：</td>
	                <td id="custom-fail-time" class="content-column1"></td>
	            </tr>
	        </tbody>
	    </table>
	    <div class="look-up-custom-pop-confirm">确定</div>
    </div>
</div>
<!-- 删除客户群提示框 -->
<div class="pop-win delete-custom-pop">
    <div class="delete-custom-title">
        <div class="delete-custom-title-title">提示</div>
        <div class="delete-custom-title-close">x</div>
    </div>
    <div class="delete-custom-content">
        <div class="delete-custom-content-tip">
            <div class="delete-custom-content-icon"></div>
                                    确定删除客户群？
        </div>
        <div class="delete-custom-btn-container">
            <div class="delete-custom-btn-confirm">确定</div>
            <div class="delete-custom-btn-cancel">取消</div>
        </div>
    </div>
</div>

<style>
	.handOutList{
		margin: 20px 100px;
	}
	.handOutList .item{
		margin-top: 10px;
	}
	.handOutList input{
		margin-right: 20px;
	}
	.ui-dialog .ui-widget-content .ui-state-default.ok-button{
		width: 100px;
	}
	.ui-dialog .ui-dialog-buttonpane .bigButton{
		width: 100px;
	}
</style>
<div id="handOutDialog" class="handOutDialog" style="display:none;">
	<ul class="handOutList">
	</ul>
</div>

</body>
</html>
<script type="text/javascript">
    seajs.use("custom/customManage",function(main){
        main.init();
    });
</script>