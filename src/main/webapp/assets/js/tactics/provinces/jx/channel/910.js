/**
 * <bold>模板ID分隔符：</bold><br>
 * 从前端往服务端传的模板id实际上是多个模板id拼接的字符串，模板id拼接时需要使用分隔符。
 */
var TEMPLATEID_SEPARATOR = ",";
/**
 * 营销短信导语
 */
var SM_QUOTE = "【推广】";

/*
 * 短信渠道选择封装类
 * baseInfo渠道基本信息，包含渠道ID 渠道名称
 */
var channelInfo910={baseInfo:null};

/**
 * 渠道初始化函数  在主体架构中会动态调用该函数
 */
function initView910(data){
	channelInfo910.baseInfo=data;
	channelInfo910.initView();
}

/**
 * 收集此渠道的渠道表单内容
 * @returns 渠道表单内容
 */
channelInfo910.collectData910=function(){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = channelInfo910.baseInfo.channelId;
	channelContentInfo.channelName = channelInfo910.baseInfo.channelName;
	channelContentInfo.execContent = $("#channelId_"+channelInfo910.baseInfo.channelId+"_contentWords").val();
	
	var templates = channelInfo910.getSelectedSMtemplates();
	channelContentInfo.bossTemplateId=templates[0];
	
	channelContentInfo.keys[0] = "渠道名称";
	channelContentInfo.keys[1] = "短信推荐用语";
	channelContentInfo.keys[2] = "短信模板";
	channelContentInfo.values[0] = channelInfo910.baseInfo.channelName;
	channelContentInfo.values[1] = channelContentInfo.execContent;
	channelContentInfo.values[2] = templates[1];

	
	return channelContentInfo;
}

/**
 * 界面上初始化、事件监听
 */
channelInfo910.initView=function(){
	//加载短信模板数据
	channelInfo910.loadSMtempaltes910();
	
	//添加事件监听
	channelInfo910.clickChannelContentEventHandler910();
}

/**
 * 初始化值(编辑时)
 */
channelInfo910.initValue910 = function(){
	//初始化值（编辑时）
	if(tacticsInfo.camp!=null){
		if(channelInfo910.baseInfo.bossTemplateId!=null){
			var templates = channelInfo910.baseInfo.bossTemplateId.split(",");
			$("#channelId_"+channelInfo910.baseInfo.channelId+"_SMtemplateDiv li").each(function(){
				for(var i in templates){
					if($(this).attr("smtemp")==templates[i]){
						$(this).addClass("active");
						break;
					}
				}
			});
		}
		
		//如果营销用语内容存在则需要更新营销用语、营销用语的可输入长度
		if(channelInfo910.baseInfo.hasOwnProperty("execContent")){
			//更新营销用语
			$("#channelId_"+channelInfo910.baseInfo.channelId+"_contentWords").val(channelInfo910.baseInfo.execContent);
			//营销用语的可输入长度
			var $textArea=$("#channelId_"+channelInfo910.baseInfo.channelId+"_contentWords");
			var $maxNum=$("#channelId_"+channelInfo910.baseInfo.channelId+"_wordSize");
			textAreaInputNumTip($textArea,$maxNum);
			var wordLen = channelInfo910.baseInfo.execContent.length;
			$maxNum.text($maxNum.text()-wordLen);
			
			
			//触发事件，将编辑回显得数据放入购物车
			var newdata = channelInfo910.collectData910();
			$("#channelDiv").trigger("changeChannel", newdata);
		}
	}
}

/**
 * 加载短信模板
 */
channelInfo910.loadSMtempaltes910=function(){
	//要拼接的用于显示短信模板的html片段
	var typeDiv = 
		'<div class="left-mes" id="{typeDiv}">\n'+
		'	<span class="color-333  ft12 ftB" id="{spanTpyeTextId}">{spanTpyeText}</span>\n'+
		'	<ul class="public-border-ul mesOld-ul" id="{typeUlId}">\n'+
		'	</ul>\n'+
		'</div>\n';
	var url = contextPath;
	var templateLi = 
		'<li  data-toggle="tooltip" data-placement="bottom" title="{Tooltip}" smtemp="{smtemp}" id="{liId}">\n'+
			'<span class="type-title" id="{templateTextId}">{smsTemplateText}</span>\n'+
			'<img class="selected-img" src="'+contextPath+'/assets/images/channelSelected.png">\n'+
		'</li>\n';
	
	//ajax请求后台获取短信模板、注册界面元素事件
	$.post(
		contextPath+"/action/tactics/createTactics/getBossSmsTemplate.do",
		null,
		function(retData, textStatus, jqXHR){
			if (retData) {
				//如果短信模板存在，则遍历模板数据，拼接显示短信模板的html
				for(var index in retData){
					var templates = retData[index].templates;
					
					var divhtml = typeDiv.replace("{typeDiv}", "channel"+channelInfo910.baseInfo.channelId+"SMTypeDiv_"+retData[index].typeId);
					divhtml = divhtml.replace("{spanTpyeTextId}", "channel"+channelInfo910.baseInfo.channelId+"SMTypeTextId_"+retData[index].typeId);
					divhtml = divhtml.replace("{spanTpyeText}", retData[index].typeName);
					divhtml = divhtml.replace("{typeUlId}", "channel"+channelInfo910.baseInfo.channelId+"SMTypeUl_"+retData[index].typeId);
					$("#channelId_"+channelInfo910.baseInfo.channelId+"_SMtemplateDiv").append(divhtml);
					
					for(var index2 in templates){
						var templateHtml = templateLi.replace("{Tooltip}", templates[index2].templateContent);
						templateHtml = templateHtml.replace("{smtemp}", templates[index2].templateId);
						templateHtml = templateHtml.replace("{liId}", "channel"+channelInfo910.baseInfo.channelId+"SMUl"+retData[index].typeId+"li"+templates[index2].templateId);
						templateHtml = templateHtml.replace("{templateTextId}", "channel"+channelInfo910.baseInfo.channelId+"SMUl"+retData[index].typeId+"liTextId"+templates[index2].templateId);
						templateHtml = templateHtml.replace("{smsTemplateText}", templates[index2].templateName);
						$("#channel"+channelInfo910.baseInfo.channelId+"SMTypeUl_"+retData[index].typeId).append(templateHtml);
					}
				}
				
				//往下弹出tooltip的样式设定
				$("[data-toggle='tooltip']").tooltip({});
			} else {
				// 查询失败
			}
			
			channelInfo910.initValue910();
			
			//点击短信模板事件
			channelInfo910.clickSMtemplateEventHandler910();
		},"json");
}

/**
 * 界面上各个元素的事件处理
 */
channelInfo910.clickChannelContentEventHandler910=function(){
	
	//确定按钮
	channelInfo910.clickCommitButtonEventHandler910();
	
	//预览按钮
	channelInfo910.clickPreviewButtonEventHandler910();
	
	//输入字数时对字数限制
	channelInfo910.textAreaInputNumTip910();
}

/**
 * 获得已选择的短信模板
 */
channelInfo910.getSelectedSMtemplates=function (){
	var templates = new Array();
	templates[0] = "";//拼接的短信模板id
	templates[1] = "";//拼接的短信模板内容
	//支持多模板选择，模板id间用分隔符分隔
	
	var firstFlag = true;
	$("#channelId_"+channelInfo910.baseInfo.channelId+"_SMtemplateDiv ul li.active").each(function(){
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
 * 营销用语输入字数限制
 */
channelInfo910.textAreaInputNumTip910=function(){
	//输入字数时对字数限制
	var $textArea=$("#channelId_"+channelInfo910.baseInfo.channelId+"_contentWords");
	var $maxNum=$("#channelId_"+channelInfo910.baseInfo.channelId+"_wordSize");
	textAreaInputNumTip($textArea,$maxNum);
}

/**
 * 点击短信模板事件
 */
channelInfo910.clickSMtemplateEventHandler910=function(){
	$("#channelId_"+channelInfo910.baseInfo.channelId+"_SMtemplateDiv div ul li").click(function(){
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
 */
channelInfo910.clickCommitButtonEventHandler910=function(){
	$("#commitButton_channelId_"+channelInfo910.baseInfo.channelId).click(function(){
		//输入项校验
		var checkResult = channelInfo910.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		
		var newdata = channelInfo910.collectData910();
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}

/**
 * 点击预览按钮事件处理
 */
channelInfo910.clickPreviewButtonEventHandler910=function(){
	$("#previewButton_channelId_"+channelInfo910.baseInfo.channelId).click(function(){
		//输入项校验
		var checkResult = channelInfo910.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		
		//收集并展示要预览的内容：短信模板+营销短信导语+推荐营销用语
		var templates = channelInfo910.getSelectedSMtemplates();
		var channelContentWords = $("#channelId_"+channelInfo910.baseInfo.channelId+"_contentWords").val();
		var privewContent = templates[1]+SM_QUOTE+channelContentWords;
		$("#SMPreviewText_channelId_"+channelInfo910.baseInfo.channelId).html(privewContent);
		
		//将预览展示作为dialog弹出
		$("#SMPreviewDiv_channelId_"+channelInfo910.baseInfo.channelId).dialog({
			width:260,
			height:630,
			resizable: false,
			modal: true,
			title: channelInfo910.baseInfo.channelName+"夹带短信预览",
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
channelInfo910.checkValidation=function(){
	var result = new Array();
	result[0]=true;
	
	if($("#channelId_"+channelInfo910.baseInfo.channelId+"_SMtemplateDiv ul li.active").length < 1){
		result[0]=false;
		result[1]="必须选择短信模板!";
		return result;
	}
	var contentWords = $("#channelId_"+channelInfo910.baseInfo.channelId+"_contentWords").val();
	if(contentWords.length<1){
		result[0]=false;
		result[1]="必须录入短信推荐用语!";
		return result;
	} else if(contentWords.trim().length<1) {
		result[0]=false;
		result[1]="短信推荐用语不能为空字符!";
		return result;
	}
	return result;
}