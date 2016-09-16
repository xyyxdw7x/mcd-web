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
				}
			}
		
		});
	}
}