var tacticsInfo={};
/**
 * 初始化产品页面
 */
tacticsInfo.initPlan=function(){
	tacticsInfo.initPlanView();
	tacticsInfo.addEventListenter();
};
/**
 * 初始化页面元素
 */
tacticsInfo.initPlanView=function(){
	tacticsInfo.loadDimInfo();
	tacticsInfo.queryPlan();
	$('.public-table tbody tr:nth-child(2n+1)').addClass('odd');
	$('.public-table tbody tr:nth-child(2n)').addClass('even');
}
/*
 * 绑定事件
 */
tacticsInfo.addEventListenter=function(){
	tacticsInfo.addPlanSearchEvent();
};
/**
 * 加载产品类别和渠道类型数据
 */
tacticsInfo.loadDimInfo=function(){
	var ejsUrlPlanTypes=contextPath + '/assets/js/tactics/provinces/' + provinces + '/dimPlanTypes.ejs';
	var ejsUrlChannels=contextPath + '/assets/js/tactics/provinces/' + provinces + '/dimChannels.ejs';
	$.ajax({
		url:contextPath+"/tactics/tacticsManage/queryPlanTypes.do",
		data:{},
		success:function(data) {
			if(!data){
				return ;
			}
			//产品类型
			var planTypesHtml = new EJS({url: ejsUrlPlanTypes}).render({data:data.planTypes});
			$("#divDimPlanTypes").html(planTypesHtml);
			//渠道类型
			var channelTypesHtml = new EJS({url:ejsUrlChannels}).render({data:data.channels});
			$("#divDimChannels").html(channelTypesHtml);
			tacticsInfo.addDimChangeEvent();
		}
	});
};
/**
 * 产品类型  产品类别  渠道类型 切换过滤产品事件
 */
tacticsInfo.addDimChangeEvent=function(){
	// 初始化产品类别
	$("#divDimPlanTypes > span").on('click', function(){
		var $target = $(this);
		$target.addClass("active").siblings().removeClass("active");
		tacticsInfo.queryPlan(1);
	});
	// 初始化产品类型
	$("#divDimPlanSrvType > span").on('click', function(){
		var $target = $(this);
		$target.addClass("active").siblings().removeClass("active");
		tacticsInfo.queryPlan(1);
	});
	// 初始化渠道类型
	$("#divDimChannels > span").on('click', function(){
		var $target = $(this);
		$target.addClass("active").siblings().removeClass("active");
		tacticsInfo.queryPlan(1);
	});
};
/**
 * 查询产品列表
 */
tacticsInfo.queryPlan=function(pageNum){
	var keyword=$("#inputKeywordPlan").val().replace(/(^\s*)|(\s*$)/g,"");;
	var planTypeId = $("#divDimPlanTypes span.active").attr("planTypeId");
	var planSrvType = $("#divDimPlanSrvType span.active").attr("planSrvType")
	var channelId = $("#divDimChannels span.active").attr("channelId");
	var ejsUrlPlans=contextPath + '/assets/js/tactics/provinces/' + provinces + '/tablePlans.ejs';
	$.ajax({
		url:contextPath+"/tactics/tacticsManage/queryPlansByCondition.do",
		data:{
			"keyWords":keyword,
			"planTypeId":planTypeId,
			"planSrvType":planSrvType,
			"channelId":channelId,
			"pageSize":5,
			"pageNum":pageNum
		},
		type:"POST",
		success:function(data) {
			if(!data){
				return ;
			}
			// 渲染表格部分
			var planHtml = new EJS({url:ejsUrlPlans}).render({data:data.result});
			$("#tbodyPlansList").html(planHtml);
			// 分页渲染
			tacticsInfo.renderPageView(data);
			tacticsInfo.addPlanClickEvent(data);
			tacticsInfo.addPlanNameClickEvent(data);
		}
	
	});
}
/**
 * 注册产品点击事件
 */
tacticsInfo.addPlanClickEvent=function(data){
	$("#tbodyPlansList .btn-a-blue").on("click", function(event){
		var index = $("#tbodyPlansList .btn-a-blue").index(this);
		var item=data.result[index];
		//已存在的不需要再处理
		if ($("#divChoosedPlan span[planId=" + item.PLAN_ID +"]").length > 0) {
			return ;
		}
		$("#divChoosedPlan span").remove();
		var li=$("<i class=\"close\"\"> &times;</i>");
		li.on("click", function(){
			// 删除已选政策列表中的展示内容
			$("#divChoosedPlan [planId=" + item.PLAN_ID +"]").remove();
			// 发布策略取消事件
			$("#planDiv").trigger("changePlan",null);
		})
		var span=$("<span class=\"policy\" planId=" + item.PLAN_ID + "><em>" + item.PLAN_NAME + "</em></span>");
		span.append(li);
		$("#divChoosedPlan").append(span);
		// 发布策略变更事件
		$("#planDiv").trigger("changePlan",item);
	});
}
/**
 * 点击查询按钮事件
 */
tacticsInfo.addPlanSearchEvent=function(){
	$("#planSearchBtn").click(function(event){
		tacticsInfo.queryPolicy(1);
	});
}
/**
 * 分页显示组件
 */
tacticsInfo.renderPageView=function(data){
	$("#divPlansPage").pagination({
        items: data.totalSize,
        itemsOnPage: data.pageSize,
        currentPage:data.pageNum,
        prevText:'上一页',
        nextText:'下一页',
        cssStyle: 'light-theme',
        onPageClick:function(pageNumber,event){
        	tacticsInfo.queryPolicy(pageNumber);
        }
    });
}
tacticsInfo.formatDate=function(now) {
	var year=now.getUTCFullYear();
	var month=now.getMonth()+1;
	var date=now.getDate();
	var hour=now.getHours();
	var minute=now.getMinutes();
	var second=now.getSeconds();
	if(hour == 0){
		return year+"-"+month+"-"+date;
	}
	return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
};