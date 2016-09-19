/**
 * 客户群选择页面初始化
 */
function initCustomerGroup(){
	queryCustomerGroupList();
	$("#selCgDiv").hide();
	clickCloseGroup();
	selCustGroup();
}
/** 
 * 查询按钮绑定查询事件 
 */ 
function selCustGroup(){ 
	$("#searchBtn").click(function(event){ 
		queryCustomerGroupList() 
	}); 
} 
/**
 *  查询客户群列表
 */
function queryCustomerGroupList(){
	//去掉空格
	var keyWords=$("#cgSearchInput").val().replace(/(^\s*)|(\s*$)/g,"");
	var url=contextPath+"/tactics/tacticsManage/getMoreMyCustom.do";
	var data={pageNum:"1",keyWords:keyWords};
	$.post(url,data,queryCustomerGroupListSuc);
}
/**
 *  删除已选客户群
 */
function clickCloseGroup(){
	$("#closeGroup").click(function(event){
		$("#cgList li").removeClass("active");
		$("#selCgDiv").hide();
		$("#selcgListName em").html("");

		//派发事件
		$("#cgDiv").trigger("changeCustomerGroup",null);
	});
}
/**
 * 查询客户群列表成功
 */
function queryCustomerGroupListSuc(obj){
	var cgItemEjsUrl=contextPath+"/jsp/test/provinces/"+provinces+"/test_extends_customer_group_item.ejs";
	var cgListHtml = new EJS({url:cgItemEjsUrl}).render({data:obj.result});
	$("#cgList").html(cgListHtml);
	addCustomerGroupEvent(obj.result);
	addCustomerGroupDetailsEvent(obj.result);
}
/**
 * 注册客户群选择事件
 * @param obj
 */
function addCustomerGroupEvent(obj){
	$("#cgList li").click(function(event){
		var index = $("#cgList li").index(this);
		var item=obj[index];
		$("#cgList li").removeClass("active");
		$("#selCgDiv").show();
		$("#selcgListName em").html(item.customGroupName);
		$(event.currentTarget).addClass("active");
		//派发事件
		$("#cgDiv").trigger("changeCustomerGroup",item);
	});
}
/**
 * 注册客户群详情事件
 * @param obj
 */
function addCustomerGroupDetailsEvent(obj){
	$("#cgList li .group-box-detail > a").click(function(event){
		var event =event || window.event || arguments.callee.caller.arguments[0];
	    event.stopPropagation(); 
	    event.cancelBubble = true;
		 
	});
}
