function collectData912(event,data){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = data.channelId;
	channelContentInfo.channelName = data.channelName;
	channelContentInfo.execContent = $("#channelId_"+data.channelId+"_contentWords").val();
	
	channelContentInfo.keys[0] = "channelId";
	channelContentInfo.keys[1] = "channelName";
	channelContentInfo.keys[2] = "execContent";
	channelContentInfo.values[0] = data.channelId;
	channelContentInfo.values[1] = data.channelName;
	channelContentInfo.values[2] = $("#channelId_"+data.channelId+"_contentWords").val();
	
	return channelContentInfo;
}

/**
 * 加载界面上渠道下的基础数据
 * @param data
 */
function loadChannelBaseData912(data){
	
}

/**
 * 界面上各个元素的事件处理
 * @param data
 */
function clickChannelContentEventHandler912(data){
	
	//确定按钮
	clickCommitButtonEventHandler912(data);
	
	//预览按钮
//	clickPreviewButtonEventHandler912(data);
}

/**
 * 在选择渠道录入渠道信息后，点击确定按钮操作的处理。</br>
 * 2. 将要加入购物车的渠道id计入本地变量中（将推入购物车的渠道记录下来，渠道被取消时要据此判断是否通知购物车移除渠道）</br>
 * 3.触发changeChannel事件，将表单数据推给购物车。
 */
function clickCommitButtonEventHandler912(data){
	$("#commitButton_channelId_"+data.channelId).click(function(){
		var newdata = collectData912(this, data);
		channelsInShoppingCar.push(data.channelId);
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}

/**
 * 点击预览按钮事件处理
 * @param data
 */
//function clickPreviewButtonEventHandler912(data){
//	$("#previewButton_channelId_"+data.channelId).click(function(){
//		alert("暂未实现");
//	});
//}