var TacticsPlan = {
	selectedPlan:{
		planId:"",
		planName:""
	},
	initTabInfo:function() {
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
						TacticsPlan.queryPolicy(1);
					});
					// 初始化渠道类型
					$("#divDimChannels > span").on('click', function(){
						var $target = $(this);
						$target.addClass("active").siblings().removeClass("active");
						TacticsPlan.queryPolicy(1);
					});
				} else {
					// 查询失败
				}
			},
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				// error happening, do nothing
			}
		});
	},
	queryPolicy:function (pageNum) {
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
					
					$(".batch_chk_box").on("click", function(event){
						var index = $("#tbodyPlansList .batch_chk_box").index(this);
						var item=data.result[index];
						
						if ($("#divChoosedPlan span[planId=" + item.PLAN_ID +"]").length > 0) {
							// 已选中 取消选择
							$("#divChoosedPlan [planId=" + item.PLAN_ID +"]").remove();
							$(".batch_chk_box[planId=" + item.PLAN_ID +"]").removeProp("checked");
							// 发布策略取消事件
							$("#divFramePlan").trigger("planCancel",item);
						} else {
							$(".batch_chk_box").removeProp("checked");
							$(".batch_chk_box[planId=" + item.PLAN_ID +"]").prop("checked", true);
							// 未选中，选中该策略
							// 如果将来支持多选，可以不预先清空已选中列表
							$("#divChoosedPlan span").remove();
							var li=$("<i class=\"close\"\"> &times;</i>");
							li.on("click", function(){
								// 查询结果列表复选框取消选中状态
								$(".batch_chk_box[planId=" + item.PLAN_ID +"]").removeProp("checked");
								// 删除已选政策列表中的展示内容
								$("#divChoosedPlan [planId=" + item.PLAN_ID +"]").remove();
								// 发布策略取消事件
								$("#divFramePlan").trigger("planCancel",item);
							})
							var span=$("<span class=\"policy\" planId=" + item.PLAN_ID + "><em>" + item.PLAN_NAME + "</em></span>");
							span.data=item;
							span.append(li);
							//var span="<span class=\"policy\" planId=" + item.PLAN_ID + "><i class=\"close\" onclick=\"unchoosePlan('" + item.PLAN_ID + "')\"> &times;</i><em>" + item.PLAN_NAME + "</em></span>";
							$("#divChoosedPlan").append(span);
							// 发布策略变更事件
							$("#divFramePlan").trigger("planChange",item);
						}
					});
				}
			}
		
		});
	}
}