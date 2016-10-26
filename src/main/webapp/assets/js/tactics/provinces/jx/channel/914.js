/*
 * 渠道选择封装类
 * baseInfo渠道基本信息，包含渠道ID 渠道名称
 */
var channelInfo914={baseInfo:null};

/**
 * 渠道初始化函数  在主体架构中会动态调用该函数
 */
function initView914(data){
	channelInfo914.baseInfo=data;
	channelInfo914.initView();
}
/**
 * 界面上初始化、事件监听
 */
channelInfo914.initView=function(){
	//加载一些界面数据
	channelInfo914.loadSomeBaseData914();
	
	//初始化值(编辑时)
	channelInfo914.initValue914();
	
	//添加事件监听
	channelInfo914.clickChannelContentEventHandler914();
}

/**
 * 加载界面上渠道下的基础数据
 */
channelInfo914.loadSomeBaseData914=function(){
	
}

/**
 * 初始化值(编辑时)
 */
channelInfo914.initValue914 = function(){
	//如果有默认的推荐语则
	if(tacticsInfo.plan.planComment!=null&&tacticsInfo.plan.planComment!=undefined){
		$("#channelId_"+channelInfo914.baseInfo.channelId+"_contentWords").val(tacticsInfo.plan.planComment);
	}
	
	var data = channelInfo.mcdPlanChannelList;
	if(data != null && data != undefined && data.length>0){
		for(var i=0;i<data.length;i++){
			if(data[i].channelId == channelInfo914.baseInfo.channelId) {
				if(data[i].smsContent!=null&&data[i].smsContent!=undefined&&data[i].smsContent!=""){
					$("#channelId_"+channelInfo914.baseInfo.channelId+"_SMS_CONTENT").val(data[i].smsContent);
				}
				if(data[i].oneWordsContent!=null&&data[i].oneWordsContent!=undefined){
					$("#channelId_"+channelInfo914.baseInfo.channelId+"_ONE_WORDS_CONTENT").val(data[i].oneWordsContent);
				}
				break;
			}
		}
	}
	
	if(tacticsInfo.plan.planComment!=null&&tacticsInfo.plan.planComment!=undefined){
		$("#channelId_"+channelInfo914.baseInfo.channelId+"_contentWords").val(tacticsInfo.plan.planComment);
	}
	
	//如果营销用语内容存在则需要更新营销用语、营销用语的可输入长度
	if(channelInfo914.baseInfo.hasOwnProperty("execContent")){
		//更新营销用语
		$("#channelId_"+channelInfo914.baseInfo.channelId+"_contentWords").val(channelInfo914.baseInfo.execContent);

	}
	
	//如果营销短信语存在则需要更新营销短信语、营销短信语的可输入长度
	if(channelInfo914.baseInfo.hasOwnProperty("sendSms")){
		//更新营销短信语
		$("#channelId_"+channelInfo914.baseInfo.channelId+"_SMS_CONTENT").val(channelInfo914.baseInfo.execContent);
	}
	
	//如果一句话营销存在则需要更新一句话营销、一句话营销的可输入长度
	if(channelInfo914.baseInfo.hasOwnProperty("oneWordsContent")){
		//更新一句话营销
		$("#channelId_"+channelInfo914.baseInfo.channelId+"_ONE_WORDS_CONTENT").val(channelInfo914.baseInfo.execContent);
	}
	
	if(channelInfo914.baseInfo.hasOwnProperty("execContent")||channelInfo914.baseInfo.hasOwnProperty("sendSms")||channelInfo914.baseInfo.hasOwnProperty("oneWordsContent")){
		//输入字数时对字数限制
		channelInfo914.textAreaInputNumTip914();
		//触发事件，将编辑回显得数据放入购物车
		var newdata = channelInfo914.collectData914();
		$("#channelDiv").trigger("changeChannel", newdata);
	}
}

/**
 * 界面上各个元素的事件处理
 */
channelInfo914.clickChannelContentEventHandler914=function(){
	//确定按钮
	channelInfo914.clickCommitButtonEventHandler914();
	
	//输入字数时对字数限制
	channelInfo914.textAreaInputNumTip914();
	
}

/**
 * 收集此渠道的渠道表单内容
 * @returns 渠道表单内容
 */
channelInfo914.collectData914=function(){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = channelInfo914.baseInfo.channelId;
	channelContentInfo.channelName = channelInfo914.baseInfo.channelName;
	channelContentInfo.execContent = $("#channelId_"+channelInfo914.baseInfo.channelId+"_contentWords").val();
	channelContentInfo.sendSms = $("#channelId_"+channelInfo914.baseInfo.channelId+"_SMS_CONTENT").val();
	channelContentInfo.oneWordsContent = $("#channelId_"+channelInfo914.baseInfo.channelId+"_ONE_WORDS_CONTENT").val();
	
	channelContentInfo.keys[0] = "渠道名称";
	channelContentInfo.keys[1] = "推荐营销用语";
	channelContentInfo.keys[2] = "营销短信语";
	channelContentInfo.keys[3] = "一句话营销";
	channelContentInfo.values[0] = channelInfo914.baseInfo.channelName;
	channelContentInfo.values[1] = channelContentInfo.execContent;
	channelContentInfo.values[2] = channelContentInfo.sendSms;
	channelContentInfo.values[3] = channelContentInfo.oneWordsContent;
	
	return channelContentInfo;
}

/**
 * 录入渠道的相关信息后，点击确定按钮操作的处理。</br>
 */
channelInfo914.clickCommitButtonEventHandler914=function(){
	$("#commitButton_channelId_"+channelInfo914.baseInfo.channelId).click(function(){
		//输入项校验(北京移动914渠道不对数据做非空校验)
		var checkResult = channelInfo914.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		if($("#commitButton_channelId_"+channelInfo914.baseInfo.channelId).hasClass("disable-href")){
			return ;
		}
		
		var newdata = channelInfo914.collectData914();
		
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}

/**
 * 营销用语输入字数限制
 */
channelInfo914.textAreaInputNumTip914=function(){
	//输入字数时对字数限制
	var $textArea=$("#channelId_"+channelInfo914.baseInfo.channelId+"_contentWords");
	var $maxNum=$("#channelId_"+channelInfo914.baseInfo.channelId+"_contentWords_wordSize");
	var $commitButton=$("#commitButton_channelId_"+channelInfo914.baseInfo.channelId);
	textAreaInputNumTip($textArea,$maxNum,$commitButton);
	
	//输入字数时对字数限制
	var $textArea2=$("#channelId_"+channelInfo914.baseInfo.channelId+"_SMS_CONTENT");
	var $maxNum2=$("#channelId_"+channelInfo914.baseInfo.channelId+"_SMS_CONTENT_wordSize");
	var $commitButton2=$("#commitButton_channelId_"+channelInfo914.baseInfo.channelId);
	textAreaInputNumTip($textArea2,$maxNum2,$commitButton2);
	
	//输入字数时对字数限制
	var $textArea3=$("#channelId_"+channelInfo914.baseInfo.channelId+"_ONE_WORDS_CONTENT");
	var $maxNum3=$("#channelId_"+channelInfo914.baseInfo.channelId+"_ONE_WORDS_CONTENT_wordSize");
	var $commitButton3=$("#commitButton_channelId_"+channelInfo914.baseInfo.channelId);
	textAreaInputNumTip($textArea3,$maxNum3,$commitButton3);
}

/**
 * 确定、预览前的合法性检查
 */
channelInfo914.checkValidation=function(){
	var result = new Array();
	result[0]=true;
	
	var contentWords = $("#channelId_"+channelInfo914.baseInfo.channelId+"_contentWords").val();
	if(contentWords.length<1){
		result[0]=false;
		result[1]="必须录入推荐用语!";
		return result;
	} else if(contentWords.trim().length<1) {
		result[0]=false;
		result[1]="推荐用语不能为空字符!";
		return result;
	}else if($("#commitButton_channelId_"+channelInfo914.baseInfo.channelId).hasClass("disable-href")){
		result[0]=false;
		result[1]="";
		return result;
	}
	var SMS_content = $("#channelId_"+channelInfo914.baseInfo.channelId+"_SMS_CONTENT").val();
	if(SMS_content.length<1){
		result[0]=false;
		result[1]="必须录入营销短信语!";
		return result;
	} else if(SMS_content.trim().length<1) {
		result[0]=false;
		result[1]="营销短信语不能为空字符!";
		return result;
	}else if($("#commitButton_channelId_"+channelInfo914.baseInfo.channelId).hasClass("disable-href")){
		result[0]=false;
		result[1]="";
		return result;
	}
	var oneWordsContent = $("#channelId_"+channelInfo914.baseInfo.channelId+"_ONE_WORDS_CONTENT").val();
	if(oneWordsContent.length<1){
		result[0]=false;
		result[1]="必须录入一句话营销!";
		return result;
	} else if(oneWordsContent.trim().length<1) {
		result[0]=false;
		result[1]="一句话营销不能为空字符!";
		return result;
	}else if($("#commitButton_channelId_"+channelInfo914.baseInfo.channelId).hasClass("disable-href")){
		result[0]=false;
		result[1]="";
		return result;
	}
	
	return result;
}