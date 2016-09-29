/**
 * 渠道信息
 */
var channelInfo={};
/**
 * 初始化渠道
 */
channelInfo.initChannel=function(){
	channelInfo.queryAllChannelList();
	channelInfo.addPlanChangeEvent();
	channelInfo.addAddChannelsEvent();
}
/**
 *  查询所有渠道列表
 */
channelInfo.queryAllChannelList=function(){
	var url=contextPath+"/action/tactics/createTactics/getChannels.do";
	$.post(url,null,function(result){
		var ejsUrlChannels=contextPath+"/assets/js/tactics/provinces/"+provinces+"/channelList.ejs";
		var channelListHtml = new EJS({url:ejsUrlChannels}).render({data:result});
		$("#channelList").html(channelListHtml);
		//绑定点击渠道事件
		channelInfo.addChannelClickEvent(result);
	});
}
/**
 * 渠道点击添加事件
 */
channelInfo.addChannelClickEvent=function(obj){
	$("#channelList li").click(function(event,data){
		var item=data;
		if(item==null){
			var index = $("#channelList li").index(this);
			item=obj[index];
		}
		var hasActive = $(this).hasClass("active");//原来是否已处于active状态
		//已经选中直接返回
		if(hasActive){
			
			return ;
		}
		if(!hasActive){
			//激活active
			$(this).addClass("active");
			$(this).children(".my-selected-icon").show();
		}
		//TODO 应该加入loading  数据有时没加载进来
		//添加tab页签
		channelInfo.addChannelTab(item);
	});
}
/**
 * 添加渠道到TAB标签中
 */
channelInfo.addChannelTab=function(data){
	var channelId=data.channelId;
	if($("#channelContentDiv_"+channelId).length>0){
		return ;
	}
	if($("#channelContentDiv_"+channelId).length>0){
		return ;
	}
	//展示渠道页签
	$("#selectedChannelsDisplayDiv").show();
	$("#selectedChannelsDisplayUl li").filter(".active").removeClass("active");
	$("#selectedChannelsContentDisplayDiv div").filter(".active").removeClass("active");
	
	//添加渠道标签
	var li_tabs_html = new EJS({element:"channelTabTemp"}).render({data:data});
	$("#selectedChannelsDisplayUl").append($(li_tabs_html));
	var channelContentDiv="<div role='tabpanel' id='channelContentDiv_"+channelId+"' class='tab-pane active '></div>";
	$("#selectedChannelsContentDisplayDiv").append(channelContentDiv);
	
	//页签点击事件和关闭事件
	channelInfo.addTabClickEvent(data);
	channelInfo.addCloseTabeClickEvent(data);
	
	//TODO word size可以从后端配置而获得
	var ejsChannelContentUrl = contextPath+"/assets/js/tactics/provinces/"+provinces+"/channel/"+channelId+".ejs";
	var channelContentHtml = new EJS({url:ejsChannelContentUrl}).render({'data':{'channelId':''+data.channelId+'','channelName':''+data.channelName+'','wordSize':"240"}});
	$("#channelContentDiv_"+channelId).html(channelContentHtml);//渠道内容
	
	//加载各个渠道操作对应的js
	var channelProcessJSUrl = contextPath + '/assets/js/tactics/provinces/'+provinces+'/channel/'+data.channelId+'.js'
	loadJsFile(channelProcessJSUrl,channelInfo.loadChannelJsComplete,data)
}
/**
 * 加载渠道js信息成功
 */
channelInfo.loadChannelJsComplete=function(data){
	var initViewFun=window["initView"+data.channelId];
	if(typeof initViewFun == "function"){
		initViewFun.apply(window,[data]);
	}
	//$.getScript异步加载 但是并不需要异步 而且在浏览器中不能直接调试
	//$.getScript(channelProcessJSUrl, function(){});
}
/**
 * 渠道TAB页签点击事件
 */
channelInfo.addTabClickEvent=function(data){
	var channelId=data.channelId;
	$("#channelTab_"+channelId).bind("click",function(event){
		$("#selectedChannelsDisplayUl li").filter(".active").removeClass("active");
		$(event.target).parent().addClass("active");
		//$("#901SendCycle button").not(".active").addClass("active");
		$("#selectedChannelsContentDisplayDiv > div").hide();
		$("#channelContentDiv_"+channelId).show();
	});
}
/**
 * 渠道页签关闭事件
 */
channelInfo.addCloseTabeClickEvent=function(data){
	var channelId=data.channelId;
	$("#channelClose_"+data.channelId).bind("click",function(event){
		$("#li_channelId_"+channelId).removeClass("active");
		$("#channelTabDiv_"+channelId).remove();
		$("#channelContentDiv_"+channelId).remove();
		$("#selectedChannelsDisplayUl li").last().addClass("active");
		$("#selectedChannelsContentDisplayDiv > div").last().show();
		//通知购物车移除渠道
		var channelInfo = new Object();
		channelInfo.channelId = channelId;
		channelInfo.isCancell = "1";
		$("#channelDiv").trigger("changeChannel", channelInfo);
	});
}

/**
 * 绑定选择产品事件
 */
channelInfo.addPlanChangeEvent=function(){
	$("#channelDiv").bind("getPlanChange",function(event,data){
		var url=contextPath+"/action/tactics/createTactics/selectPlanBackChannels.do";
		$.post(url,{planId:data.planId},channelInfo.getPlanChannelsSuc);
	});
}
/**
 * 根据产品查询渠道信息成功
 */
channelInfo.getPlanChannelsSuc=function(data){
	var channels = data.channels;
	$("#channelList li").each(function(){
		var currentChannelId = $(this).attr("channelId");
		if(channels.indexOf(currentChannelId)<0){
			$(this).hide();
		}
	});
}
/**
 * 渠道编辑传入渠道信息事件
 */
channelInfo.addAddChannelsEvent=function(){
	$("#channelDiv").bind("addChannles",function(event,data){
		for(var i=0;i<data.length;i++){
			var item=data[i];
			var channelId=item.channelId;
			$("#li_channelId_"+channelId).trigger("click",item);
		}
	});
}




