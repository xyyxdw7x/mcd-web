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
	
	//绑定点击渠道事件
	bindClickChannelEvent();
	
	addChannelEvent(obj);
}

/**
 * 注册渠道选择事件
 * @param obj
 */
function addChannelEvent(obj){
	$("#channelList li").click(function(event){
		var index = $("#channelList li").index(this);
		var item=obj[index];
		
		/**
		 * 控制渠道列表的渠道被点击的样式变化
		 */ 
		var taget = $(this);
		var activedFlag = taget.hasClass("active");//原来是否已处于active状态
		if(activedFlag){
			//使active失效
			taget.removeClass("active");
			taget.children(".my-selected-icon").hide();
		} else {
			//激活active
			taget.addClass("active");
			taget.children(".my-selected-icon").show();
		}
		
		/**
		 * 触发点击渠道事件:控制页签的增加或移除，原来已经active则移除；原来未active则增加
		 */
		var addChannelTab = !activedFlag;
		$("#channelList li").trigger("clickChannelEvent", [item, addChannelTab]);
	});
}

/**
 * 注册渠道被点击事件
 * @param event 
 * @param data
 */
function bindClickChannelEvent(){
	$("#channelList li").bind("clickChannelEvent", clickChannelEventHandler);
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
 * 点击渠道事件处理器
 * @param event
 * @param data
 * @param addChannelTab 要增加渠道页签?true:是|false:否 
 */
function clickChannelEventHandler(event, data, addChannelTab){
	var channelId = event.currentTarget.attributes.channelId.value;
	if(addChannelTab) {
		//渠道被选中，增加显示渠道的页签
		if(channelId == data.channelId){
			debugger
			//渠道页签
			var ejsLiTabsUrl=contextPath + '/assets/js/tactics/provinces/'+provinces+'/channel/liTabsChannelId.ejs';
			var li_tabs_html = new EJS({url:ejsLiTabsUrl}).render({data:data});
			$("#selectedChannelsDisplayUl").prepend($(li_tabs_html));
			
			//展示此渠道的营销内容
			var ejsDivTabpanelUrl = contextPath + '/assets/js/tactics/provinces/'+provinces+'/channel/divTabpanelChannelId.ejs';
			var div_tabpanel_html = new EJS({url:ejsDivTabpanelUrl}).render({data:data});
			$("#selectedChannelsContentDisplayDiv").prepend($(div_tabpanel_html));//展示渠道内容的div
			
			var ejsChannelContentUrl = contextPath + '/assets/js/tactics/provinces/'+provinces+'/channel/'+data.channelId+'.ejs';
			var channelContentHtml = new EJS({url:ejsChannelContentUrl}).render({data:data});;//渠道内容
			$("#href-channelId_"+data.channelId).html(channelContentHtml);
			
			//移除之前添加的渠道页签、对应的内容active
			$("#selectedChannelsDisplayUl").children("li").first().next().removeClass("active");
			$("#selectedChannelsContentDisplayDiv").children("div").first().next().removeClass("active");
		}
	} else {
		//渠道的选中被取消，移除显示渠道的页签
		if(channelId == data.channelId){
			//移除页签
			$("#li_tabs_channelId_"+data.channelId).remove();
			
			//移除渠道的营销内容
			$("#href-channelId_"+data.channelId).remove();
		}
	}
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