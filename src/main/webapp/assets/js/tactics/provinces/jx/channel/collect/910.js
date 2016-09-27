function collectData910(event,data){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = data.channelId;
	channelContentInfo.channelName = data.channelName;
	channelContentInfo.execContent = $("#channelId_"+data.channelId+"_contentWords").val();
	channelContentInfo.bossTemplateId="1335491647281";
	
	return channelContentInfo;
}

function clickChannelContentEventHandler910(data){
	
	//确定按钮
	clickCommitButtonEventHandler910(data);
	
	//预览按钮
//	clickPreviewButtonEventHandler910(data);
}

/**
 * 在选择渠道录入渠道信息后，点击确定按钮操作的处理。</br>
 * 2. 将要加入购物车的渠道id计入本地变量中（将推入购物车的渠道记录下来，渠道被取消时要据此判断是否通知购物车移除渠道）</br>
 * 3.触发changeChannel事件，将表单数据推给购物车。
 */
function clickCommitButtonEventHandler910(data){
	$("#commitButton_channelId_"+data.channelId).click(function(){
		var newdata = collectData910(this, data);
		channelsInShoppingCar.push(data.channelId);
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}

/**
 * 点击预览按钮事件处理
 * @param data
 */
//function clickPreviewButtonEventHandler910(data){
//	$("#previewButton_channelId_"+data.channelId).click(function(){
//		alert("暂未实现");
//	});
//}