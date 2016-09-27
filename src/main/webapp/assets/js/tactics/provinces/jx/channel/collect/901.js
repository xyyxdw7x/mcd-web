function collectData901(event,data){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = data.channelId;
	channelContentInfo.channelName = data.channelName;
	channelContentInfo.execContent = $("#channelId_"+data.channelId+"_contentWords").val();
	channelContentInfo.isLoopSend = $("#channelId_"+data.channelId+"_loopSend").hasClass("active")? 1:0;
	channelContentInfo.isHasVar = true;
	
	channelContentInfo.keys[0] = "channelId";
	channelContentInfo.keys[1] = "channelName";
	channelContentInfo.keys[2] = "execContent";
	channelContentInfo.keys[3] = "isLoopSend";
	channelContentInfo.keys[4] = "isHasVar";
	channelContentInfo.values[0] = data.channelId;
	channelContentInfo.values[1] = data.channelName;
	channelContentInfo.values[2] = $("#channelId_"+data.channelId+"_contentWords").val();
	channelContentInfo.values[3] = $("#channelId_"+data.channelId+"_loopSend").hasClass("active")? 1:0;
	channelContentInfo.values[4] = true;
	
	return channelContentInfo;
}

/**
 * 加载界面上渠道下的基础数据
 * @param data
 */
function loadChannelBaseData901(data){

}

/**
 * 界面上各个元素的事件处理
 * @param data
 */
function clickChannelContentEventHandler901(data){
	
	//发送周期[一次性|周期性]按钮
	selectSendCycleButtonEventHandler901(data);
	
	//确定按钮
	clickCommitButtonEventHandler901(data);
}

/**
 * 发送周期[一次性|周期性]按钮被点击时，需要对点击事件做处理
 * @param data
 */
function selectSendCycleButtonEventHandler901(data){
	$("#channelId_"+data.channelId+"_sendOnce").click(function(){
		var hasActive =  $(this).hasClass("active");
		if(!hasActive){
			$(this).addClass("active");
			$("#channelId_"+data.channelId+"_loopSend").removeClass("active");
		}
	});

	$("#channelId_"+data.channelId+"_loopSend").click(function(){
		var hasActive =  $(this).hasClass("active");
		if(!hasActive){
			$(this).addClass("active");
			$("#channelId_"+data.channelId+"_sendOnce").removeClass("active");
		}
	});

}

/**
 * 在选择渠道录入渠道信息后，点击确定按钮操作的处理。</br>
 * 2. 将要加入购物车的渠道id计入本地变量中（将推入购物车的渠道记录下来，渠道被取消时要据此判断是否通知购物车移除渠道）</br>
 * 3.触发changeChannel事件，将表单数据推给购物车。
 */
function clickCommitButtonEventHandler901(data){
	$("#commitButton_channelId_"+data.channelId).click(function(){
		var newdata = collectData901(this, data);
		channelsInShoppingCar.push(data.channelId);
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}