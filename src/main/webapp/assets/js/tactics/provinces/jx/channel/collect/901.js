function collectData(event,data){
	var channelContentInfo = new Object();
	channelContentInfo.channelId = data.channelId;
	channelContentInfo.channelName = data.channelName;
	channelContentInfo.channelContentWords = $("channelId_"+data.channelId+"_contentWords").attr("value");
	channelContentInfo.isLoopSend = $("channelId_"+data.channelId+"_sendOnce").hasClass("active")? 1:0;
	//channelContentInfo.isAssignSendDate = "";
	//channelContentInfo.sendCycle = $("channelId_"+data.channelId+"_sendOnce").hasClass("active")? -1:0;
	
	return channelContentInfo;
}