/*
 * 渠道选择封装类
 * baseInfo渠道基本信息，包含渠道ID 渠道名称
 */
var channelInfo902={baseInfo:null};

/**
 * 渠道初始化函数  在主体架构中会动态调用该函数
 */
function initView902(data){
	channelInfo902.baseInfo=data;
	channelInfo902.initView();
}
/**
 * 界面上初始化、事件监听
 */
channelInfo902.initView=function(){
	//加载一些界面数据
	channelInfo902.loadSomeBaseData902();
	
	//初始化值(编辑时)
	channelInfo902.initValue902();
	
	//添加事件监听
	channelInfo902.clickChannelContentEventHandler902();
}

/**
 * 加载界面上渠道下的基础数据
 */
channelInfo902.loadSomeBaseData902=function(){
	
}

/**
 * 初始化值(编辑时)
 */
channelInfo902.initValue902 = function(){
	//如果营销用语内容存在则需要更新营销用语、营销用语的可输入长度
	if(channelInfo902.baseInfo.hasOwnProperty("execContent")){
		//更新营销用语
		$("#channelId_"+channelInfo902.baseInfo.channelId+"_contentWords").val(channelInfo902.baseInfo.execContent);
		//营销用语的可输入长度
		var $textArea=$("#channelId_"+channelInfo902.baseInfo.channelId+"_contentWords");
		var $maxNum=$("#channelId_"+channelInfo902.baseInfo.channelId+"_wordSize");
		textAreaInputNumTip($textArea,$maxNum);
		var wordLen = channelInfo902.baseInfo.execContent.length;
		$maxNum.text($maxNum.text()-wordLen);
		
		
		//触发事件，将编辑回显得数据放入购物车
		var newdata = channelInfo902.collectData902();
		$("#channelDiv").trigger("changeChannel", newdata);
	}
}

/**
 * 界面上各个元素的事件处理
 */
channelInfo902.clickChannelContentEventHandler902=function(){
	//确定按钮
	channelInfo902.clickCommitButtonEventHandler902();
	
	//输入字数时对字数限制
	channelInfo902.textAreaInputNumTip902();
	
}

/**
 * 收集此渠道的渠道表单内容
 * @returns 渠道表单内容
 */
channelInfo902.collectData902=function(){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = channelInfo902.baseInfo.channelId;
	channelContentInfo.channelName = channelInfo902.baseInfo.channelName;
	channelContentInfo.execContent = $("#channelId_"+channelInfo902.baseInfo.channelId+"_contentWords").val();
	
	channelContentInfo.keys[0] = "渠道名称";
	channelContentInfo.keys[1] = "推荐营销用语";
	channelContentInfo.values[0] = channelInfo902.baseInfo.channelName;
	channelContentInfo.values[1] = channelContentInfo.execContent;
	
	return channelContentInfo;
}

/**
 * 录入渠道的相关信息后，点击确定按钮操作的处理。</br>
 */
channelInfo902.clickCommitButtonEventHandler902=function(){
	$("#commitButton_channelId_"+channelInfo902.baseInfo.channelId).click(function(){
		//输入项校验
		var checkResult = channelInfo902.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		
		var newdata = channelInfo902.collectData902();
		
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}

/**
 * 营销用语输入字数限制
 */
channelInfo902.textAreaInputNumTip902=function(){
	//输入字数时对字数限制
	var $textArea=$("#channelId_"+channelInfo902.baseInfo.channelId+"_contentWords");
	var $maxNum=$("#channelId_"+channelInfo902.baseInfo.channelId+"_wordSize");
	textAreaInputNumTip($textArea,$maxNum);
}

/**
 * 确定、预览前的合法性检查
 */
channelInfo902.checkValidation=function(){
	var result = new Array();
	result[0]=true;
	
	var contentWords = $("#channelId_"+channelInfo902.baseInfo.channelId+"_contentWords").val();
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