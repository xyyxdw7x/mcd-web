function collectData(event,data){
	var channelContentInfo = new Object();
	channelContentInfo.channelId = data.channelId;
	channelContentInfo.channelName = data.channelName;
	channelContentInfo.execContent = $("#channelId_"+data.channelId+"_contentWords").val();
	channelContentInfo.adivId="1335491647281";
	
	return channelContentInfo;
}