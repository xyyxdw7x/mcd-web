var tacticsCustGroup = {
	queryCustGroup : function() {
		var ejsUrlPlanTypes=contextPath + '/assets/js/tactics/provinces/' + provinces + '/liCustGroup.ejs';
		
		var keyWords=$("#inputKeywordCustGroup").val();
		$.ajax({
			url:contextPath+"/tactics/tacticsManage/getMoreMyCustom",
			data:{"keyWords":keyWords},
			method:"POST",
			success:function(data, textStatus) {
				if (data) {
					var _HtmlCustGroup = new EJS({url:ejsUrlPlanTypes}).render({data:data});
					$("#ulCustGroupList").html(_HtmlCustGroup);
					// TODO 因为客户群查询接口暂时还不支持分页，所以分页展现功能先不做
					
					// 初始化客户群列表
					$("#ulCustGroupList li").click(function(event){
						var index = $("#ulCustGroupList li").index(this);
						var item=data[index];
						
						if ($("#divCustGroupList span[customGroupId=" + item.customGroupId + "]").length > 0) {
							$("#divCustGroupList span[customGroupId=" + item.customGroupId + "]").remove();
							$("#ulCustGroupList li[customGroupId=" + item.customGroupId + "]").removeClass("active");
							// 发布客户群取消选择事件
							$("#divFrameCustGroup").trigger("custGroupCancel",item);
						} else {
							$("#ulCustGroupList li").removeClass("active");
							$(event.currentTarget).addClass("active");
							// 如果将来支持多客户群可以不预先删除全部客户群
							$("#divCustGroupList span").remove();
							
							var li = $("<i class=\"close\"> &times;</i>");
							li.on("click", function(){
								$("#ulCustGroupList li[customGroupId=" + item.customGroupId + "]").removeClass("active");
								$("#divCustGroupList span[customGroupId=" + item.customGroupId + "]").remove();
								// 发布客户群取消选择事件
								$("#divFrameCustGroup").trigger("custGroupCancel",item);
							});
							var span=$("<span class=\"policy\" customGroupId=" + item.customGroupId + "><em>" + item.customGroupName + "</em></span>")
							span.append(li);
							$("#divCustGroupList").append(span);
							
							//派发事件——客户群发生变化
							$("#divFrameCustGroup").trigger("custGroupChange",item);
						}
						
					});
				} else {
					// 查询失败
				}
			},
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				// error happening, do nothing
			}
		});
	}
}


/**
 * 客户群选择页面初始化
 */
function initCustomerGroup(){
	queryCustomerGroupList();
	addCloseCustGroupEvent();
	addCustomerGroupSearchEvent();
	$("#custGroupPage").pagination({
        items: 154,
        itemsOnPage: 5,
        prevText:'上一页',
        nextText:'下一页',
        cssStyle: 'light-theme'
    });
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

function addCustomerGroupSearchEvent(){
	$("#cgSearchBtn").click(function(event){
		queryCustomerGroupList();
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
