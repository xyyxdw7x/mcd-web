//当前购物车中存放的渠道
var channelsInShoppingCar = new Array();

/**
 * 初始化渠道
 */
function initChannel(){
	queryChannelList();
	addPlanEevent();
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
 *  查询渠道列表
 */
function queryChannelList(){
	var url=contextPath+"/action/tactics/createTactics/getChannels.do";
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
		var hasActive = $(this).hasClass("active");//原来是否已处于active状态
		if(!hasActive){
			//激活active
			$(this).addClass("active");
			$(this).children(".my-selected-icon").show();
		}
		
		/**
		 * 触发点击渠道事件:控制页签的增加，原来不是active状态则增加，否则无变化
		 */
		var addChannelTab = !hasActive;
		$("#channelDiv").trigger("clickChannelEvent", [item, addChannelTab]);
	});
}

/**
 * 注册渠道被点击事件
 * @param event 
 * @param data
 */
function bindClickChannelEvent(){
	$("#channelDiv").bind("clickChannelEvent", clickChannelEventHandler);
}


/**
 * 绑定选择产品事件
 */
function addPlanEevent(){
	$("#channelDiv").bind("getPlanChange",selectChannelEvent);
}

/**
 * 根据产品id获取渠道列表
 * @param event
 * @param data
 */
function selectChannelEvent(event,data){
	var url=contextPath+"/action/tactics/createTactics/selectPlanBackChannels.do";
	$.post(url,{planId:data.planId},selectChannelByPlan);
}

/**
 * 点击渠道事件处理器
 * @param event
 * @param data
 * @param addChannelTab 要增加渠道页签 true:是|false:否 
 */
function clickChannelEventHandler(event, data, addChannelTab){
	if(!isSelectPlan()){
		alert("请选择产品!");
		return;
	}
	if(!isSelectCustomGroup()){
		alert("请选择客户群!");
		return;
	}
	if(addChannelTab) {
		//展示渠道页签
		$("#selectedChannelsDisplayDiv").show();
		//添加渠道
		addNewChannelToTab(data);
		
		//最新加入的channel是active
		latestAddeddChannelActive();
		
		//根据客户群id获取客户群变量
		selectSmsAttribute(tacticsInfo.custGroup.customGroupId);
		
		//提示文本域中还可以输入字体个数
		hintInputWordCount(data);
	}
	
	//渠道关闭事件
	clickCloseChannel();

}

function hintInputWordCount(data){
	var maxWordSize = $("#channelId_"+data.channelId+"_wordSize").text();
	$("#channelId_"+data.channelId+"_contentWords").keyup(function () {
		var value = $(this).val();
		var count = $("#channelId_"+data.channelId+"_wordSize");
		debugger;
		if (value.length > maxWordSize) {
			$(this).val(value.substring(0, maxWordSize));
			count.html(0);
		} else {
			count.html(maxWordSize - value.length);
		}
	});
}


/**
 * 将选中的渠道加入渠道页签列表，同时加载渠道信息
 * @param data
 */
function addNewChannelToTab(data){
	//将渠道名称加入渠道页签页签
	var ejsLiTabsUrl=contextPath + '/assets/js/tactics/provinces/'+provinces+'/channel/liTabsChannelId.ejs';
	var li_tabs_html = new EJS({url:ejsLiTabsUrl}).render({data:data});
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
	$.getScript(channelProcessJSUrl, function(){
		//加载渠道内容展示基础数据
		var fx = window["loadChannelBaseData"+data.channelId];
		if (typeof fx == "function") { 
			fx.apply(window, [data]);
		}
		
		//增加渠道内容点击事件绑定
		var fn = window["clickChannelContentEventHandler"+data.channelId];
		if (typeof fn == "function") { 
			fn.apply(window, [data]);
		}
	});
}

/**
 * 是否选择客户群 tacticsInfo为全局变量
 * @returns {Boolean}
 */
function isSelectCustomGroup(){
	if(tacticsInfo.custGroup){
		if(!tacticsInfo.custGroup.customGroupId){
			return false;
		}
	}else{
		return false;
	}
	return true;
	
}

/**
 * 是否选择产品 tacticsInfo为全局变量
 * @returns {Boolean}
 */
function isSelectPlan(){
	if(tacticsInfo.plan){
		if(!tacticsInfo.plan.planId){
			return false;
		}
	}else{
		return false;
	}
	return true;
}

/**
 * 如果这个渠道已放入购物车，则通知购物车移除此渠道
 * @param channelId
 */
function callBacllChannel(channelId){
	if(channelsInShoppingCar.length>0){
		for(var i = 0; i < channelsInShoppingCar.length; i++){
			if(channelsInShoppingCar[i] == channelId){
				channelsInShoppingCar = channelsInShoppingCar.splice(i,1);//移除已经存放的渠道id
				
				//通知购物车移除渠道
				var channelContentInfo = new Object();
				channelContentInfo.channelId = channelId;
				channelContentInfo.isCancell = "1";
				$("#channelDiv").trigger("changeChannel", channelContentInfo);
				break;
			}
		}
	}
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

/**
 * 根据客户群id获取附件属性列表
 * @param event
 * @param data
 */
function selectSmsAttribute(custgroupId){
	$.ajax({
		url:contextPath+"/action/tactics/createTactics/getCustGroupVars.do",
		data:{"custGroupId":custgroupId},
		success:function(result, textStatus) {
			if (result) {
				var temp ="";
				//客户群的所有变量列表
				for(var x in result){
					if($("#_smsVar").find('span[attrCol="'+result[x].attrCol+'"]').length==0){
						var _li='<span class="border-item" attrCol="'+result[x].attrCol+'">'+result[x].attrColName+'</span>';
						temp += _li;
					}
				}
				 $("#_smsVar").html(temp);
				//选择变量时，写入文本域
				var _textarea = $('#channelId_901_contentWords');
					$('span.border-item').on("click",function(){
					_textarea.insertContent(("$"+$(this).attr('attrCol')+"$"));
					$(this).addClass("active");
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
