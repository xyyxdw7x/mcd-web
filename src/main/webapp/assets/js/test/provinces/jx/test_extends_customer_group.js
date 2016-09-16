/**
 * 客户群选择页面初始化
 */
function initCustomerGroup(){
	queryCustomerGroupList();
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
 * 查询客户群列表成功
 */
function queryCustomerGroupListSuc(obj){
	var cgItemEjsUrl=contextPath+"/jsp/test/provinces/"+provinces+"/test_extends_customer_group_item.ejs";
	var cgListHtml = new EJS({url:cgItemEjsUrl}).render({data:obj});
	$("#cgList").html(cgListHtml);
	addCustomerGroupEvent();
}

function addCustomerGroupEvent(){
	$("#cgList li").click(function(event){
		$("#cgList li").removeClass("active");
		$(event.currentTarget).addClass("active");
	});
}
