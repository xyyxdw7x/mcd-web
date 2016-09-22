function collectData(event,data){
	var channelContentInfo = new Object();
	channelContentInfo.channelId = data.channelId;
	channelContentInfo.channelName = data.channelName;
	channelContentInfo.channelContentWords = $("#channelId_"+data.channelId+"_contentWords").attr("value");
	
	return channelContentInfo;
}