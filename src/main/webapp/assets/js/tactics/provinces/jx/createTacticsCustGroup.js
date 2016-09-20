/**
 * 客户群选择页面初始化
 */
function initCustomerGroup(){
	queryCustomerGroupList(1);
	addCloseCustGroupEvent();
	addCustomerGroupSearchEvent();
}
/**
 *  删除已选客户群
 */
function addCloseCustGroupEvent(){
	$("#closeGroup").click(function(event){
		$("#cgList li").removeClass("active");
		$("#selCgDiv").hide();
		$("#selcgListName em").html("");
		//派发事件
		$("#cgDiv").trigger("changeCustomerGroup",null);
	});
}
/**
 * 客户群搜索事件
 */
function addCustomerGroupSearchEvent(){
	$("#cgSearchBtn").click(function(event){
		queryCustomerGroupList(1);
	});
}
/**
 *  查询客户群列表
 */
function queryCustomerGroupList(pageNum){
	//去掉空格
	var keyWords=$("#cgSearchInput").val().replace(/(^\s*)|(\s*$)/g,"");
	var url=contextPath+"/tactics/tacticsManage/getMoreMyCustom.do";
	var data={pageNum:pageNum,keyWords:keyWords};
	$.post(url,data,queryCustomerGroupListSuc);
}
/**
 * 查询客户群列表成功
 */
function queryCustomerGroupListSuc(obj){
	var cgItemEjsUrl=contextPath+"/assets/js/tactics/provinces/"+provinces+"/createTacticsCustGroup.ejs";
	var selectCg = $("#selectedCg").data("data");
	var cgListHtml ;
	if(selectCg != null){
		cgListHtml = new EJS({url:cgItemEjsUrl}).render({data:obj.result,customGroupId:selectCg.customGroupId});
	}else{
		cgListHtml = new EJS({url:cgItemEjsUrl}).render({data:obj.result,customGroupId:null});
	}
	$("#cgList").html(cgListHtml);
	addCustomerGroupEvent(obj.result);
	addCustomerGroupDetailsEvent(obj.result);
	renderCustGroupPageView(obj);
}
/**
 * 测试用的 因此总页数添加了120
 * @param data
 */
function renderCustGroupPageView(data){
	$("#custGroupPage").pagination({
        items: data.totalSize,
        itemsOnPage: data.pageSize,
        currentPage:data.pageNum,
        prevText:'上一页',
        nextText:'下一页',
        cssStyle: 'light-theme',
        onPageClick:function(pageNumber,event){
        	queryCustomerGroupList(pageNumber);
        }
    });
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
		$(event.currentTarget).addClass("active");
		//派发事件
		$("#cgDiv").trigger("changeCustomerGroup",item);
		$("#selCgDiv").show();
		$("#selcgListName em").html(item.customGroupName);
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
