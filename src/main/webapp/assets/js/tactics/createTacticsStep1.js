$(function() {
	// 页面初始化
	// 初始化tab页
	initTable();
	// 初始化查询结果
	queryPolicy(1);
	// 控件初始化
	// TODO
	
	// 初始化产品类型
	$("#divDimPlanSrvType > span").on('click', function(){
		var $target = $(this);
		$target.addClass("active").siblings().removeClass("active");
		queryPolicy(1);
	});
	
});

function initTable() {
	initTabInfo();
}
// 查询政策列表
function queryPolicy(pageNum) {
	var keyword=$("#keyword").val();
	var planTypeId = $("#divDimPlanTypes span.active").attr("planTypeId");
	var planSrvType = $("#divDimPlanSrvType span.active").attr("planSrvType")
	var channelId = $("#divDimChannels span.active").attr("channelId");
	
	var ejsUrlPlans=_ctx + '/assets/js/tactics/tablePlans.ejs';
	var ejsUrlPlansPage=_ctx + '/assets/js/tactics/tablePlansPage.ejs';
	
	var page=pageNum;
	if (pageNum==null || pageNum=="") {
		var turnPageVal = $("#turnPageId").val();
		if(turnPageVal) {
			page=turnPageVal;
		}
	}
	jQuery.ajax({
		url:_ctx+"/tactics/tacticsManage/queryPlansByCondition",
		data:{
			"keyWords":keyword,
			"planTypeId":planTypeId,
			"planSrvType":planSrvType,
			"channelId":channelId,
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
	
	var ejsUrlPlanTypes=_ctx + '/assets/js/tactics/dimPlanTypes.ejs';
	var ejsUrlChannels=_ctx + '/assets/js/tactics/dimChannels.ejs';
	
	$.ajax({
		url:_ctx+"/tactics/tacticsManage/queryPlanTypes",
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
					queryPolicy(1);
				});
				// 初始化渠道类型
				$("#divDimChannels > span").on('click', function(){
					var $target = $(this);
					$target.addClass("active").siblings().removeClass("active");
					queryPolicy(1);
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
		// 未选中，选中该策略
		var span="<span class=\"policy\" planId=" + planId + "><i class=\"close\" onclick=\"unchoosePlan('" + planId + "')\"> &times;</i><em>" + planName + "</em></span>";
		$("#divChoosedPlan").html(span);
		var li="<li planId=" + planId + "><span>.</span>" + planName + "</li>";
		$("#ulPlanList").html(li);
		// 查询该策略下对应哪些渠道
		$.ajax({
			url:_ctx+"/tactics/tacticsManage/selectPlanBackChannels",
			data:{"planId":planId},
			success:function(data, textStatus) {
				if(data) {
					
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

