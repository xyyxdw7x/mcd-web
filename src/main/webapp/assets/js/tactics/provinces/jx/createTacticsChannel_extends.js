/**
 * 初始化渠道
 */
function initChannel(){
	queryChannelList();
	addPlanEevent();
}

/**
 *  查询渠道列表
 */
function queryChannelList(){
	var url=contextPath+"/tactics/tacticsManage/getChannels.do";
	$.post(url,null,queryChannelListSuc);
}

/**
 * 查询渠道列表成功
 */
function queryChannelListSuc(obj){
	var ejsUrlChannels=contextPath + '/assets/js/tactics/provinces/' + provinces + '/channelList.ejs';
	var channelListHtml = new EJS({url:ejsUrlChannels}).render({data:obj});
	$("#channelList").html(channelListHtml);
	addChannelEvent(obj);
}

/**
 * 注册渠道选择事件
 * @param obj
 */
function addChannelEvent(obj){
	$("#channelList li").click(function(event){
		var target=$(this);
		var item=obj[target];
		var channelId=target.attr("channelId");
		if(target.hasClass("active")){
			target.removeClass("active");
			target.children(".selected-img").hide();
		}else{
			target.addClass("active");
			target.children(".selected-img").show();
		}
		
		//派发事件
		$("#channelList").trigger("channelChange",item);
	});
}

/**
 * 绑定选择产品事件
 */
function addPlanEevent(){
	$("#channelList").bind("getPlanChange",selectChannelEvent);
}

/**
 * 根据产品id获取渠道列表
 * @param event
 * @param data
 */
function selectChannelEvent(event,data){
	var url=contextPath+"/tactics/tacticsManage/selectPlanBackChannels";
	$.post(url,{planId:data.PLAN_ID},selectChannelByPlan);
}

/**
 * 根据产品id获取渠道列表成功
 * @param data
 */
function selectChannelByPlan(data){
	var channels = data.channels;
	$("#channelList li").each(function(){
		var currentChannelId = $(this).attr("channelId");
		if(channels.indexOf(currentChannelId)<0){
			$(this).hide();
		}
	});
}