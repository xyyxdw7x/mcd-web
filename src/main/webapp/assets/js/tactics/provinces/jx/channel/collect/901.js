function collectData(event,data){
	var channelContentInfo = new Object();
	channelContentInfo.channelId = data.channelId;
	channelContentInfo.channelName = data.channelName;
	channelContentInfo.execContent = $("#channelId_"+data.channelId+"_contentWords").val();
	channelContentInfo.isLoopSend = $("#channelId_"+data.channelId+"_sendOnce").hasClass("active")? 1:0;
	channelContentInfo.isHasVar = true;
	
	
	return channelContentInfo;
}