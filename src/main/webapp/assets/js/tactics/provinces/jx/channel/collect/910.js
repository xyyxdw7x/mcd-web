function collectData910(event,data){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = data.channelId;
	channelContentInfo.channelName = data.channelName;
	channelContentInfo.execContent = $("#channelId_"+data.channelId+"_contentWords").val();
	channelContentInfo.bossTemplateId="1335491647281";
	
	channelContentInfo.keys[0] = "channelId";
	channelContentInfo.keys[1] = "channelName";
	channelContentInfo.keys[2] = "execContent";
	channelContentInfo.keys[3] = "bossTemplateId";
	channelContentInfo.values[0] = data.channelId;
	channelContentInfo.values[1] = data.channelName;
	channelContentInfo.values[2] = $("#channelId_"+data.channelId+"_contentWords").val();
	channelContentInfo.values[2] = "1335491647281";
	
	return channelContentInfo;
}

/**
 * 加载界面上渠道下的基础数据
 * @param data
 */
function loadChannelBaseData910(data){
	//要拼接的用于显示短信模板的html片段
	var typeDiv = 
		'<div class="left-mes" id="{typeDiv}">\n'+
		'	<span class="color-333  ft12 ftB" id="{spanTextId}">{spanText}</span>\n'+
		'	<ul class="public-border-ul mesOld-ul" id="{typeUlId}">\n'+
		'	</ul>\n'+
		'</div>\n';
	var templateLi = 
		'<li  data-toggle="tooltip" data-placement="bottom" title="{Tooltip}" id="{liId}">\n'+
			'<span class="type-title" id="{templateTextId}">{smsTemplateText}</span>\n'+
			'<img class="selected-img" src="../../assets/images/channelSelected-2.png">\n'+
		'</li>\n';
	
	//ajax请求后台获取短信模板
	$.post(
		contextPath+"/tactics/tacticsManage/getBossSmsTemplate.do",
		null,
		function(retData, textStatus, jqXHR){
			if (retData) {
				//如果短信模板存在，则遍历模板数据，拼接显示短信模板的html
				for(var index in retData){
					var templates = retData[index].templates;
					
					var divhtml = typeDiv.replace("{typeDiv}", "channel"+data.channelId+"SMTypeDiv_"+retData[index].typeId);
					divhtml = divhtml.replace("{spanTextId}", "channel"+data.channelId+"SMTypeTextId_"+retData[index].typeId);
					divhtml = divhtml.replace("{spanText}", retData[index].typeName);
					divhtml = divhtml.replace("{typeUlId}", "channel"+data.channelId+"SMTypeUl_"+retData[index].typeId);
					$("#channelId_"+data.channelId+"_SMtemplateDiv").append(divhtml);
					
					for(var index2 in templates){
						var templateHtml = templateLi.replace("{Tooltip}", templates[index2].templateContent);
						templateHtml = templateHtml.replace("{liId}", "channel"+data.channelId+"SMUl"+retData[index].typeId+"li"+templates[index2].templateId);
						templateHtml = templateHtml.replace("{templateTextId}", "channel"+data.channelId+"SMUl"+retData[index].typeId+"liTextId"+templates[index2].templateId);
						templateHtml = templateHtml.replace("{smsTemplateText}", templates[index2].templateName);
						$("#channel"+data.channelId+"SMTypeUl_"+retData[index].typeId).append(templateHtml);
						templateHtml = "";
					}
				}
			} else {
				// 查询失败
			}
		},"json");
}

/**
 * 界面上各个元素的事件处理
 * @param data
 */
function clickChannelContentEventHandler910(data){
	//点击短信模板事件
	clickSMtemplateEventHandler910(data);
	
	//确定按钮
	clickCommitButtonEventHandler910(data);
	
	
	//预览按钮
//	clickPreviewButtonEventHandler910(data);
}

/**
 * 点击短信模板事件
 * @param data
 */
function clickSMtemplateEventHandler910(data){
	$("#channelId_"+data.channelId+"_SMtemplateDiv div ul li").click(function(){
		alert();
	});
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