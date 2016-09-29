/*
 * 渠道选择封装类
 * baseInfo渠道基本信息，包含渠道ID 渠道名称
 */
var channelInfo904={baseInfo:null};

/**
 * 渠道初始化函数  在主体架构中会动态调用该函数
 */
function initView904(data){
	channelInfo904.baseInfo=data;
	channelInfo904.initView();
}
/**
 * 界面上初始化、事件监听
 */
channelInfo904.initView=function(){
	//加载一些界面数据
	channelInfo904.loadSomeBaseData904();
	//添加事件监听
	channelInfo904.clickChannelContentEventHandler904();
}

/**
 * 加载界面上渠道下的基础数据
 */
channelInfo904.loadSomeBaseData904=function(){
	
}

/**
 * 界面上各个元素的事件处理
 */
channelInfo904.clickChannelContentEventHandler904=function(){
	//确定按钮
	channelInfo904.clickCommitButtonEventHandler904();
	
	//输入字数时对字数限制
	channelInfo904.textAreaInputNumTip904();
	
}

/**
 * 收集此渠道的渠道表单内容
 * @returns 渠道表单内容
 */
channelInfo904.collectData904=function(){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = channelInfo904.baseInfo.channelId;
	channelContentInfo.channelName = channelInfo904.baseInfo.channelName;
	channelContentInfo.execContent = $("#channelId_"+channelInfo904.baseInfo.channelId+"_contentWords").val();
	
	channelContentInfo.keys[0] = "渠道名称";
	channelContentInfo.keys[1] = "推荐营销用语";
	channelContentInfo.values[0] = channelInfo904.baseInfo.channelName;
	channelContentInfo.values[1] = channelContentInfo.execContent;
	
	return channelContentInfo;
}

/**
 * 录入渠道的相关信息后，点击确定按钮操作的处理。</br>
 */
channelInfo904.clickCommitButtonEventHandler904=function(){
	$("#commitButton_channelId_"+channelInfo904.baseInfo.channelId).click(function(){
		//输入项校验
		var checkResult = channelInfo904.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		
		var newdata = channelInfo904.collectData904();
		
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}

/**
 * 营销用语输入字数限制
 */
channelInfo904.textAreaInputNumTip904=function(){
	//输入字数时对字数限制
	var $textArea=$("#channelId_"+channelInfo904.baseInfo.channelId+"_contentWords");
	var $maxNum=$("#channelId_"+channelInfo904.baseInfo.channelId+"_wordSize");
	textAreaInputNumTip($textArea,$maxNum);
}

/**
 * 确定、预览前的合法性检查
 */
channelInfo904.checkValidation=function(){
	var result = new Array();
	result[0]=true;
	
	var contentWords = $("#channelId_"+channelInfo904.baseInfo.channelId+"_contentWords").val();
	if(contentWords.length<1){
		result[0]=false;
		result[1]="必须录入推荐营销用语!";
		return result;
	} else if(contentWords.trim().length<1) {
		result[0]=false;
		result[1]="推荐营销用语不能为空字符!";
		return result;
	}
	return result;
}