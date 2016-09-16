function initTable() {
	initTabInfo();
	initTabInfo();
}
// 查询政策列表
function queryPlan(pageNum) {
	var keyword=$("#inputKeywordPlan").val();
	var planTypeId = $("#divDimPlanTypes span.active").attr("planTypeId");
	var planSrvType = $("#divDimPlanSrvType span.active").attr("planSrvType")
	var channelId = $("#divDimChannels span.active").attr("channelId");
	
	var ejsUrlPlans=contextPath + '/assets/js/tactics/provinces/' + provinces + '/tablePlans.ejs';
	var ejsUrlPlansPage=contextPath + '/assets/js/tactics/provinces/' + provinces + '/tablePlansPage.ejs';
	
	var page=pageNum;
	if (pageNum==null || pageNum=="") {
		var turnPageVal = $("#turnPageId").val();
		if(turnPageVal) {
			page=turnPageVal;
		}
	}
	jQuery.ajax({
		url:contextPath+"/tactics/tacticsManage/queryPlansByCondition",
		data:{
			"keyWords":keyword,
			"planTypeId":planTypeId,
			"planSrvType":planSrvType,
			"channelId":channelId,
			"pageSize":10,
			"pageNum":page
		},
		type:"POST",
		success:function(data, textStatus) {
			if (data) {
				// 渲染表格部分
				var _HtmlPlans = new EJS({
					url : ejsUrlPlans
				}).render({data:data.result});
				$("#tbodyPlansList").html(_HtmlPlans);
				// 渲染分页部分
				var _HtmlPlansPage = new EJS({
					url : ejsUrlPlansPage
				}).render({data:data});
				$("#divPlansPage").html(_HtmlPlansPage);
			}
		}
	
	});
}
// 查询并初始化tab页筛选条件列表
function initTabInfo() {
	
	var ejsUrlPlanTypes=contextPath + '/assets/js/tactics/provinces/' + provinces + '/dimPlanTypes.ejs';
	var ejsUrlChannels=contextPath + '/assets/js/tactics/provinces/' + provinces + '/dimChannels.ejs';
	
	$.ajax({
		url:contextPath+"/tactics/tacticsManage/queryPlanTypes",
		data:{},
		success:function(data, textStatus) {
			if (data) {
				var _HtmlPlanTypes = new EJS({
					url : ejsUrlPlanTypes
				}).render({data:data.planTypes});
				$("#divDimPlanTypes").html(_HtmlPlanTypes);
				var _HtmlChannels = new EJS({
					url : ejsUrlChannels
				}).render({data:data.channels});
				$("#divDimChannels").html(_HtmlChannels);
				
				// 初始化产品类别
				$("#divDimPlanTypes > span").on('click', function(){
					var $target = $(this);
					$target.addClass("active").siblings().removeClass("active");
					queryPlan(1);
				});
				// 初始化渠道类型
				$("#divDimChannels > span").on('click', function(){
					var $target = $(this);
					$target.addClass("active").siblings().removeClass("active");
					queryPlan(1);
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
// 点选一个策略的复选框
function choosePlan(planId, planName) {
	if ($("#divChoosedPlan span[planId=" + planId +"]").length > 0) {
		// 已选中 取消选择
		$("#divChoosedPlan [planId=" + planId +"]").remove();
		$("#ulWorkspacePlanList [planId=" + planId +"]").remove();
		$(".batch_chk_box[planId=" + planId +"]").removeProp("checked");
	} else {
		$(".batch_chk_box").removeProp("checked");
		$(".batch_chk_box[planId=" + planId +"]").prop("checked", true);
		// 未选中，选中该策略
		var span="<span class=\"policy\" planId=" + planId + "><i class=\"close\" onclick=\"unchoosePlan('" + planId + "')\"> &times;</i><em>" + planName + "</em></span>";
		$("#divChoosedPlan").html(span);
		var li="<li planId=" + planId + "><span>.</span>" + planName + "</li>";
		$("#ulPlanList").html(li);
		// 查询该策略下对应哪些渠道
		$.ajax({
			url:contextPath+"/tactics/tacticsManage/selectPlanBackChannels",
			data:{"planId":planId},
			success:function(data, textStatus) {
				if(data) {
					// TODO 根据查到的策略下渠道类型列表，调整渠道子页面的显示内容
				}
			}
		});
	}
}
// 已选政策列表中取消一个策略的选中状态
function unchoosePlan(planId) {
	// 查询结果列表复选框取消选中状态
	$(".batch_chk_box[planId=" + planId +"]").removeProp("checked");
	// 删除已选政策列表中的展示内容
	$("#divChoosedPlan [planId=" + planId +"]").remove();
	// 删除右侧工作站的已选政策
	$("#ulWorkspacePlanList [planId=" + planId +"]").remove();
}

/**
 * 左侧数字导航事件
 */
function addNavigationClickListener(){
	$("#stepOl li").click(function(event){
		var selectedIndex=parseInt($(event.target).html(),10);
		if(isNaN(selectedIndex)){
			return ;
		}
		//动态切换数据的样式
		$("#stepOl li").removeClass("active");
		$(event.target).parent().addClass("active");
		//动态切换div
		var divIds=["divFramePlan","divFrameCustGroup","divFrameChannel"];
		for(var i=0;i<divIds.length;i++){
			var divObj=$("#"+divIds[i]);
			if(i==(selectedIndex-1)){
				divObj.show();
			}else{
				divObj.hide();
			}
		}
	});
	
	$(".btn-nextstep").click(function(event){
		//找到当前的位置  如果是3则切换到1 
		var selectedIndex=parseInt($("#stepOl .active").find("i").html(),10);
		var nextIndex=selectedIndex+1;
		if(nextIndex>3){
			nextIndex=1;
		}
		//找到相应的li并触发点击事件 要保证事件的入口唯一
		$("#stepOl li :contains('"+nextIndex+"')").trigger("click");
	});
}

