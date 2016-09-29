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
	$("#channelList li").click(function(event){
		var index = $("#channelList li").index(this);
		var item=obj[index];
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
		//添加tab页签
		channelInfo.addChannelTab(item);
	});
}
/**
 * 添加渠道到TAB标签中
 */
channelInfo.addChannelTab=function(data){
	//展示渠道页签
	$("#selectedChannelsDisplayDiv").show();
	//添加渠道
	//将渠道名称加入渠道页签页签
	var ejsLiTabsUrl=contextPath + '/assets/js/tactics/provinces/'+provinces+'/channel/liTabsChannelId.ejs';
	//var li_tabs_html = new EJS({url:ejsLiTabsUrl}).render({data:data});
	var li_tabs_html = new EJS({element:"channelTabTemp"}).render({data:data});
	$("#selectedChannelsDisplayUl").prepend($(li_tabs_html));
	
	//展示此渠道的营销内容
	var ejsDivTabpanelUrl = contextPath + '/assets/js/tactics/provinces/'+provinces+'/channel/divTabpanelChannelId.ejs';
	var div_tabpanel_html = new EJS({url:ejsDivTabpanelUrl}).render({data:data});
	$("#selectedChannelsContentDisplayDiv").prepend($(div_tabpanel_html));//展示渠道内容的div
	
	var ejsChannelContentUrl = contextPath + '/assets/js/tactics/provinces/'+provinces+'/channel/'+data.channelId+'.ejs';
	var channelContentHtml = new EJS({url:ejsChannelContentUrl}).render({'data':{'channelId':''+data.channelId+'','channelName':''+data.channelName+'','wordSize':"240"}});
	$("#href-channelId_"+data.channelId).html(channelContentHtml);//渠道内容
	
	//加载各个渠道操作对应的js
	var channelProcessJSUrl = contextPath + '/assets/js/tactics/provinces/'+provinces+'/channel/collect/'+data.channelId+'.js'
	var channelJs=document.createElement("script");
	channelJs.type="text/javascript";
    // IE
    if (channelJs.readyState){
    	channelJs.onreadystatechange = function () {
            if (channelJs.readyState == "loaded" || channelJs.readyState == "complete") {
            	channelJs.onreadystatechange = null;
                channelInfo.loadChannelJsComplete(data);
            }
        };
    } else { // others
    	channelJs.onload = function () {
        	channelInfo.loadChannelJsComplete(data);
        };
    }
	channelJs.src=channelProcessJSUrl;
	document.body.appendChild(channelJs);
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
	//最新加入的channel是active
	latestAddeddChannelActive();
}


/**
 * 关闭渠道页签
 */
function clickCloseChannel(){
	//tab切换删除按钮
	$('.trench-header li i').click(function() {
		var channelId = $(this).parent('li').attr("tabChannelId");
		$('#channelList li[channelid='+channelId+']').removeClass("active");
		
		//移除页签
		$("#li_tabs_channelId_"+channelId).remove();
		
		//移除渠道的营销内容
		$("#href-channelId_"+channelId).remove();

		//渠道页签中的第一个渠道展示
		firstChannelActive();
		//通知购物车移除此渠道
		callBacllChannel(channelId);
		
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
 * 如果这个渠道已放入购物车，则通知购物车移除此渠道
 * @param channelId
 */
function callBacllChannel(channelId){
	//通知购物车移除渠道
	var channelContentInfo = new Object();
	channelContentInfo.channelId = channelId;
	channelContentInfo.isCancell = "1";
	$("#channelDiv").trigger("changeChannel", channelContentInfo);
}

/**
 * 将排在页签里第一个渠道激活展示
 * @param data
 */
function firstChannelActive(){
	$("#selectedChannelsDisplayUl li").each(function(){
		if($(this).index() ==0) {
			$(this).addClass("active");
		} else {
			$(this).removeClass("active");
		}
	});
	
	$("#selectedChannelsContentDisplayDiv div").each(function(){
		if($(this).index() ==0) {
			$(this).addClass("active");
		} else {
			$(this).removeClass("active");
		}
	});
}

/**
 * 之前添加的渠道页签、渠道内容的class属性的active移除，最新加入的channel是active
 * @param data
 */
function latestAddeddChannelActive(){
	$("#selectedChannelsDisplayUl li").each(function(){
		if($(this).index() !=0) {
			$(this).removeClass("active");
		}
	});
	
	$("#selectedChannelsContentDisplayDiv div").each(function(){
		if($(this).index() !=0) {
			$(this).removeClass("active");
		}
	});
}



