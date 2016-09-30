/*
 * 渠道选择封装类
 * baseInfo渠道基本信息，包含渠道ID 渠道名称
 */
var channelInfo909={baseInfo:null};

/**
 * 渠道初始化函数  在主体架构中会动态调用该函数
 */
function initView909(data){
	channelInfo909.baseInfo=data;
	channelInfo909.initView();
}
/**
 * 界面上初始化、事件监听
 */
channelInfo909.initView=function(){
	//加载一些界面数据
	channelInfo909.loadSomeBaseData909();
	
	//初始化值(编辑时)
	channelInfo909.initValue909();	
	
	//添加事件监听
	channelInfo909.clickChannelContentEventHandler909();
}

/**
 * 加载界面上渠道下的基础数据
 */
channelInfo909.loadSomeBaseData909=function(){
	
}

/**
 * 初始化值(编辑时)
 */
channelInfo909.initValue909 = function(){
	if(tacticsInfo.camp!=null){
		$("#channelId_"+channelInfo909.baseInfo.channelId+"_contentWords").val(channelInfo909.baseInfo.execContent);
	}
}

/**
 * 界面上各个元素的事件处理
 */
channelInfo909.clickChannelContentEventHandler909=function(){
	//确定按钮
	channelInfo909.clickCommitButtonEventHandler909();
	
	//输入字数时对字数限制
	channelInfo909.textAreaInputNumTip909();
	
}

/**
 * 收集此渠道的渠道表单内容
 * @returns 渠道表单内容
 */
channelInfo909.collectData909=function(){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = channelInfo909.baseInfo.channelId;
	channelContentInfo.channelName = channelInfo909.baseInfo.channelName;
	channelContentInfo.execContent = $("#channelId_"+channelInfo909.baseInfo.channelId+"_contentWords").val();
	
	channelContentInfo.keys[0] = "渠道名称";
	channelContentInfo.keys[1] = "推荐营销用语";
	channelContentInfo.values[0] = channelInfo909.baseInfo.channelName;
	channelContentInfo.values[1] = channelContentInfo.execContent;
	
	return channelContentInfo;
}

/**
 * 录入渠道的相关信息后，点击确定按钮操作的处理。</br>
 */
channelInfo909.clickCommitButtonEventHandler909=function(){
	$("#commitButton_channelId_"+channelInfo909.baseInfo.channelId).click(function(){
		//输入项校验
		var checkResult = channelInfo909.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		
		var newdata = channelInfo909.collectData909();
		
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}

/**
 * 营销用语输入字数限制
 */
channelInfo909.textAreaInputNumTip909=function(){
	//输入字数时对字数限制
	var $textArea=$("#channelId_"+channelInfo909.baseInfo.channelId+"_contentWords");
	var $maxNum=$("#channelId_"+channelInfo909.baseInfo.channelId+"_wordSize");
	textAreaInputNumTip($textArea,$maxNum);
}

/**
 * 确定、预览前的合法性检查
 */
channelInfo909.checkValidation=function(){
	var result = new Array();
	result[0]=true;
	
	var contentWords = $("#channelId_"+channelInfo909.baseInfo.channelId+"_contentWords").val();
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