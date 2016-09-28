/**
 * <bold>模板ID分隔符：</bold><br>
 * 从前端往服务端传的模板id实际上是多个模板id拼接的字符串，模板id拼接时需要使用分隔符。
 */
var TEMPLATEID_SEPARATOR = ",";
/**
 * 营销短信导语
 */
var SM_QUOTE = "【推广】";

/**
 * 收集此渠道的渠道表单内容
 * @param event 事件
 * @param data 渠道信息
 * @returns 渠道表单内容
 */
function collectData910(event,data){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = data.channelId;
	channelContentInfo.channelName = data.channelName;
	channelContentInfo.execContent = $("#channelId_"+data.channelId+"_contentWords").val();
	
	var templates = getSelectedSMtemplates(data);
	channelContentInfo.bossTemplateId=templates[0];
	
	channelContentInfo.keys[0] = "渠道名称";
	channelContentInfo.keys[1] = "短信推荐用语";
	channelContentInfo.keys[2] = "短信模板";
	channelContentInfo.values[0] = data.channelName;
	channelContentInfo.values[1] = channelContentInfo.execContent;
	channelContentInfo.values[2] = templates[1];

	
	return channelContentInfo;
}

/**
 * 加载界面上渠道下的基础数据
 * @param data 渠道信息
 */
function loadChannelBaseData910(data){
	//要拼接的用于显示短信模板的html片段
	var typeDiv = 
		'<div class="left-mes" id="{typeDiv}">\n'+
		'	<span class="color-333  ft12 ftB" id="{spanTpyeTextId}">{spanTpyeText}</span>\n'+
		'	<ul class="public-border-ul mesOld-ul" id="{typeUlId}">\n'+
		'	</ul>\n'+
		'</div>\n';
	var templateLi = 
		'<li  data-toggle="tooltip" data-placement="bottom" title="{Tooltip}" smtemp="{smtemp}" id="{liId}">\n'+
			'<span class="type-title" id="{templateTextId}">{smsTemplateText}</span>\n'+
			'<img class="selected-img" src="../../assets/images/channelSelected-2.png">\n'+
		'</li>\n';
	
	//ajax请求后台获取短信模板、注册界面元素事件
	var jqxhr =$.post(
		contextPath+"/action/tactics/createTactics/getBossSmsTemplate.do",
		null,
		function(retData, textStatus, jqXHR){
			if (retData) {
				//如果短信模板存在，则遍历模板数据，拼接显示短信模板的html
				for(var index in retData){
					var templates = retData[index].templates;
					
					var divhtml = typeDiv.replace("{typeDiv}", "channel"+data.channelId+"SMTypeDiv_"+retData[index].typeId);
					divhtml = divhtml.replace("{spanTpyeTextId}", "channel"+data.channelId+"SMTypeTextId_"+retData[index].typeId);
					divhtml = divhtml.replace("{spanTpyeText}", retData[index].typeName);
					divhtml = divhtml.replace("{typeUlId}", "channel"+data.channelId+"SMTypeUl_"+retData[index].typeId);
					$("#channelId_"+data.channelId+"_SMtemplateDiv").append(divhtml);
					
					for(var index2 in templates){
						var templateHtml = templateLi.replace("{Tooltip}", templates[index2].templateContent);
						templateHtml = templateHtml.replace("{smtemp}", templates[index2].templateId);
						templateHtml = templateHtml.replace("{liId}", "channel"+data.channelId+"SMUl"+retData[index].typeId+"li"+templates[index2].templateId);
						templateHtml = templateHtml.replace("{templateTextId}", "channel"+data.channelId+"SMUl"+retData[index].typeId+"liTextId"+templates[index2].templateId);
						templateHtml = templateHtml.replace("{smsTemplateText}", templates[index2].templateName);
						$("#channel"+data.channelId+"SMTypeUl_"+retData[index].typeId).append(templateHtml);
					}
				}
			} else {
				// 查询失败
			}
			
			//在此做界面元素事件注册
			clickChannelContentEventHandler910(data);
		},"json");
}

/**
 * 界面上各个元素的事件处理
 * @param data 渠道信息
 */
function clickChannelContentEventHandler910(data){
	//点击短信模板事件
	clickSMtemplateEventHandler910(data);
	
	//确定按钮
	clickCommitButtonEventHandler910(data);
	
	//预览按钮
	clickPreviewButtonEventHandler910(data);
}

/**
 * 获得已选择的短信模板
 * data 渠道信息
 */
function getSelectedSMtemplates(data){
	var templates = new Array();
	templates[0] = "";//拼接的短信模板id
	templates[1] = "";//拼接的短信模板内容
	//支持多模板选择，模板id间用分隔符分隔
	
	var firstFlag = true;
	$("#channelId_"+data.channelId+"_SMtemplateDiv ul li.active").each(function(){
		if(firstFlag){
			templates[0] = $(this).attr("smtemp");
			firstFlag = false;
		} else {
			templates[0] = templates[0] + TEMPLATEID_SEPARATOR + $(this).attr("smtemp");
		}
		
		var typeName = $(this).parent().prevAll("span").html();
		templates[1] = templates[1] + typeName+$(this).attr("title");
	});
	return templates;
}

/**
 * 点击短信模板事件
 * @param data 渠道信息
 */
function clickSMtemplateEventHandler910(data){
	$("#channelId_"+data.channelId+"_SMtemplateDiv div ul li").click(function(){
		var hasActive = $(this).hasClass("active");
		if(!hasActive){
			//激活active
			$(this).addClass("active");
		} else {
			//去除active
			$(this).removeClass("active");
		}
	});
}

/**
 * 在选择渠道录入渠道信息后，点击确定按钮操作的处理。</br>
 * 1. 将要加入购物车的渠道id计入本地变量中（将推入购物车的渠道记录下来，渠道被取消时要据此判断是否通知购物车移除渠道）</br>
 * 2. 触发changeChannel事件，将表单数据推给购物车。
 * data 渠道信息
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
 * @param data 渠道信息
 */
function clickPreviewButtonEventHandler910(data){
	$("#previewButton_channelId_"+data.channelId).click(function(){
		checkValidation(data);
		
		//收集并展示要预览的内容：短信模板+营销短信导语+推荐营销用语
		var templates = getSelectedSMtemplates(data);
		var channelContentWords = $("#channelId_"+data.channelId+"_contentWords").val();
		var privewContent = templates[1]+SM_QUOTE+channelContentWords;
		$("#SMPreviewText_channelId_"+data.channelId).html(privewContent);
		
		//将预览展示作为dialog弹出
		$("#SMPreviewDiv_channelId_"+data.channelId).dialog({
			width:260,
			height:630,
			resizable: false,
			modal: true,
			title: data.channelName+"夹带短信预览",
			buttons: [{
				"class":"dialog-btn dialog-btn-blue",
				"text":"关闭",
				"click": function() {
					$( this ).dialog( "close" );
				}
			}]
		});
	});
}

/**
 * 确定、预览前的合法性检查
 */
function checkValidation(data){
	var result = new Array();
	if($("#channelId_"+data.channelId+"_SMtemplateDiv div ul li.active").length < 1){
		result[0]=false;
		result[1]="必须选择短信模板!";
	}
	
	var test="ssssss";
}
