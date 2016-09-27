/**
 * 客户群选择页面初始化
 */
function initCustomerGroup(){
	queryCustomerGroupList(1);
	addCloseCustGroupEvent();
	addCustomerGroupSearchEvent();
	addCustomerGroupAddEvent();
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
function addCustomerGroupAddEvent(){
	$("#cgDiv").bind("addCustomerGroup",function(event,data){
		$($("#cgList li")[0]).trigger("click", data);
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
	var data={pageNum:pageNum,pageSize:12,keyWords:keyWords};
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
		var detailButton = $(this);
		var event =event || window.event || arguments.callee.caller.arguments[0];
	    event.stopPropagation(); 
	    event.cancelBubble = true;
	    
	    var customGroupId = detailButton.parent().parent().parent().parent().attr('customGroupId');
	    
	    queryCustomerGroupDetails(customGroupId);
	});
}
/**
 * 查询客户群详情
 * @param customGroupId
 */
function queryCustomerGroupDetails(customGroupId){
	var url=contextPath+"/tactics/tacticsManage/viewCustGroupDetail.do";
	var data={custGroupId:customGroupId};
	$.post(url,data,queryCustomerGroupDetailsSuc);
}
/**
 * 查询客户群详情成功
 * @param GrpDetail
 */
function queryCustomerGroupDetailsSuc(GrpDetail){
	$('.showGroupTypeDialog-id').html(GrpDetail.CUSTOM_GROUP_ID);
	$('.showGroupTypeDialog-name').html(GrpDetail.CUSTOM_GROUP_NAME);
	$('.showGroupTypeDialog-desc').html(GrpDetail.CUSTOM_GROUP_DESC);
	$('.showGroupTypeDialog-filter').html(GrpDetail.RULE_DESC);
	$('.showGroupTypeDialog-creater').html(GrpDetail.CREATE_USER_NAME);
	$('.showGroupTypeDialog-createtime').html(GrpDetail.CREATE_TIME_STR);
	$('.showGroupTypeDialog-updatecycle').html(GrpDetail.UPDATE_CYCLE_NAME);
	$('.showGroupTypeDialog-datatime').html(GrpDetail.DATA_TIME_STR);
	$('.showGroupTypeDialog-effectivetime').html(GrpDetail.CUSTOM_STATUS);
	$('.showGroupTypeDialog-failtime').html(GrpDetail.FAIL_TIME_STR);
	
	openDialogForPreview('showGroupTypeDialog','客户群详情',null,680,390);
}
/**
 * 打开客户群详情dialog
 * @param className
 * @param titleTxt
 * @param buttons
 * @param _w
 * @param _h
 * @param _open
 * @param _close
 */
function openDialogForPreview(className,titleTxt,buttons,_w,_h,_open,_close){
	$('body').css('overflow', 'hidden');
	var ctObj=$("."+className);
	if(ctObj.length>0){
		ctObj=$("."+className);
	}else{
		ctObj=$("<div class='"+className+"'></div>");
		$("body").append(ctObj);
	}

	ctObj.dialog({
		title:titleTxt,
		modal:true,
		width:_w||1100,
		height:_h||610,
		position: { my: "center", at: "center", of: window },
		buttons: buttons,
		open:_open||function(){},
		close:function(event, ui ){
			$(".exclamation:visible").hide();
			$('body').css('overflow', 'auto');
			_close?_close():"";
		}
	});

}