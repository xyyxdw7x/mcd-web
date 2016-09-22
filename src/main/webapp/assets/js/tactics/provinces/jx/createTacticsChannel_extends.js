//当前购物车中存放的渠道
//var channelsInShoppingCar = new Array();

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
	});
	
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
		//clickChannelEventHandler(event,item, addChannelTab);
		$("#channelList").trigger("clickChannelEvent", [item, addChannelTab]);
	});
}

/**
 * 注册渠道被点击事件
 * @param event 
 * @param data
 */
function bindClickChannelEvent(){
	$("#channelList").bind("clickChannelEvent", clickChannelEventHandler);
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
	var url=contextPath+"/tactics/tacticsManage/selectPlanBackChannels.do";
	$.post(url,{planId:data.PLAN_ID},selectChannelByPlan);
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
		//添加渠道
		
		addNewChannelToTab(data);
		
		//确定按钮、预览按钮事件处理器添加
		clickCommitButtonEventHandler(data);//确定
		//$("#previewButton_channelId_"+data.channelId).on("click", clickPreviewButtonEventHandler(data));//预览
		
		//最新加入的channel是active
		latestAddeddChannelActive();
	} else {
		//移除页签
		$("#li_tabs_channelId_"+data.channelId).remove();
		
		//移除渠道的营销内容
		$("#href-channelId_"+data.channelId).remove();
		
		//渠道页签中的第一个渠道展示
		firstChannelActive();
		
//		//通知购物车移除此渠道
//		callBacllChannel(channelId);
	}
	
	//根据客户群id获取短信变量
	selectSmsAttribute(tacticsInfo.custGroup.customGroupId);
	//渠道关闭事件
	clickCloseChannel();

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
}

/**
 * 在选择渠道录入渠道信息后，点击确定按钮操作的处理。</br>
 * 当前使用<bold>动态加载javascript脚本</bold>的方式来处理各个渠道的表单内容，可能后边需要再修改。</br>
 * 步骤：1. 调用动态加载的js中的collectData方法收集表单数据；</br>
 * 2. 将要加入购物车的渠道id计入本地变量中（将推入购物车的渠道记录下来，渠道被取消时要据此判断是否通知购物车移除渠道）</br>
 * 3.触发changeChannel事件，将表单数据推给购物车。
 */
function clickCommitButtonEventHandler(data){
	$("#conmmitButton_channelId_"+data.channelId).click(function(){
		var newdata = null;
		var channelContentcollectJsUrl = contextPath + '/assets/js/tactics/provinces/'+provinces+'/channel/collect/'+data.channelId+'.js'
		$.getScript(channelContentcollectJsUrl, function(){
			newdata = collectData(this, data);
//			channelsInShoppingCar.push(data.channelId);
			$("#channelDiv").trigger("changeChannel", newdata);
		});
		
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
		if(!tacticsInfo.plan.PLAN_ID){
			return false;
		}
	}else{
		return false;
	}
	return true;
}

///**
// * 如果这个渠道已放入购物车，则通知购物车移除此渠道
// * @param channelId
// */
//function callBacllChannel(channelId){
//	if(channelsInShoppingCar.length>0){
//		var existsflag = false;
//		for(var i = 0; i < channelsInShoppingCar.length; i++){
//			if(channelsInShoppingCar[i] == channelId){
//				existsflag = true;
//				break;
//			}
//		}
//	}
//	channelsInShoppingCar = channelsInShoppingCar.splice();
//	
//	
//	
//	
//}

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
		url:contextPath+"/tactics/tacticsManage/getCustGroupVars.do",
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

						//edit页面 回显
						var _textareaValue=_textarea.val();
						var temp$ary=_textareaValue.match(/\$.+?\$/g);
						if(temp$ary && temp$ary.length>0){
							for(var xi=0;xi<temp$ary.length;xi++){
								var attrcol=temp$ary[xi].replace(/\$/g,"");
								var tarli=appendParent.find('li[attrcol="'+attrcol+'"]');
								if(tarli.length>0){
									tarli.addClass("active");
								}
							}
						}
						var tempary=_textareaValue.match(/\#.+?\#/g);
						if(tempary && tempary.length>0){
							for(var xi=0;xi<tempary.length;xi++){
								var attrcol=tempary[xi].replace(/\#/g,"");
								var tarli=appendParent.find('span[attrcol="'+attrcol+'"]');
								if(tarli.length>0){
									tarli.addClass("active");
								}
							}
						}
					
			} else {
				// 查询失败
			}
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			// error happening, do nothing
		}
	});
}

/**
 * 点击保存按钮事件处理
 * @param data
 */
function clickPreviewButtonEventHandler(data){
	//alert("暂未实现");
}