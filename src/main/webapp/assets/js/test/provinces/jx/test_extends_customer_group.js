/**
 * 客户群选择页面初始化
 */
function initCustomerGroup(){
	queryCustomerGroupList();
	$("#selCgDiv").hide();
	clickCloseGroup();
}
/**
 *  查询客户群列表
 */
function queryCustomerGroupList(){
	var url=contextPath+"/tactics/tacticsManage/getMoreMyCustom.do";
	var data={pageNum:"1",keyWords:""};
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
		$("#cgDiv").trigger("changeCustomerGroup",item);
	});
}
/**
 * 查询客户群列表成功
 */
function queryCustomerGroupListSuc(obj){
	var cgItemEjsUrl=contextPath+"/jsp/test/provinces/"+provinces+"/test_extends_customer_group_item.ejs";
	var cgListHtml = new EJS({url:cgItemEjsUrl}).render({data:obj});
	$("#cgList").html(cgListHtml);
	addCustomerGroupEvent(obj);
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
		$(event.currentTarget).addClass("active");debugger
		//派发事件
		$("#cgDiv").trigger("changeCustomerGroup",item);
	});
}
