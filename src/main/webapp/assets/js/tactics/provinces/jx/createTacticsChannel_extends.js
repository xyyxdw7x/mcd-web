/**
 * 初始化渠道
 */
function initChannel(){
	queryChannelList();
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
		debugger;
		$("#channelList li").removeClass("active");
		$(event.currentTarget).addClass("active");
		//派发事件
		$("#channelList").trigger("changeChannelEvent",item);
	});
}