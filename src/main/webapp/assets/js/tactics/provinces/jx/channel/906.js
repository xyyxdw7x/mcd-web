/*
 * 渠道选择封装类
 * baseInfo渠道基本信息，包含渠道ID 渠道名称
 */
var channelInfo906={baseInfo:null};

/**
 * 渠道初始化函数  在主体架构中会动态调用该函数
 */
function initView906(data){
	channelInfo906.baseInfo=data;
	channelInfo906.initView();
}
/**
 * 界面上初始化、事件监听
 */
channelInfo906.initView=function(){
	//加载一些界面数据
	channelInfo906.loadSomeBaseData906();
	
	//初始化值(编辑时)
	channelInfo906.initValue906();
	
	//添加事件监听
	channelInfo906.clickChannelContentEventHandler906();
}

/**
 * 加载界面上渠道下的基础数据
 */
channelInfo906.loadSomeBaseData906=function(){
	
}

/**
 * 初始化值(编辑时)
 */
channelInfo906.initValue906 = function(){
	//如果营销用语内容存在则需要更新营销用语、营销用语的可输入长度
	if(channelInfo906.baseInfo.hasOwnProperty("execContent")){
		//更新营销用语
		$("#channelId_"+channelInfo906.baseInfo.channelId+"_contentWords").val(channelInfo906.baseInfo.execContent);
		//营销用语的可输入长度
		var $textArea=$("#channelId_"+channelInfo906.baseInfo.channelId+"_contentWords");
		var $maxNum=$("#channelId_"+channelInfo906.baseInfo.channelId+"_wordSize");
		textAreaInputNumTip($textArea,$maxNum);
		var wordLen = channelInfo906.baseInfo.execContent.length;
		$maxNum.text($maxNum.text()-wordLen);
		
		
		//触发事件，将编辑回显得数据放入购物车
		var newdata = channelInfo906.collectData906();
		$("#channelDiv").trigger("changeChannel", newdata);
	}
}

/**
 * 界面上各个元素的事件处理
 */
channelInfo906.clickChannelContentEventHandler906=function(){
	//确定按钮
	channelInfo906.clickCommitButtonEventHandler906();
	
	//输入字数时对字数限制
	channelInfo906.textAreaInputNumTip906();
	
}

/**
 * 收集此渠道的渠道表单内容
 * @returns 渠道表单内容
 */
channelInfo906.collectData906=function(){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = channelInfo906.baseInfo.channelId;
	channelContentInfo.channelName = channelInfo906.baseInfo.channelName;
	channelContentInfo.execContent = $("#channelId_"+channelInfo906.baseInfo.channelId+"_contentWords").val();
	
	channelContentInfo.keys[0] = "渠道名称";
	channelContentInfo.keys[1] = "推荐营销用语";
	channelContentInfo.values[0] = channelInfo906.baseInfo.channelName;
	channelContentInfo.values[1] = channelContentInfo.execContent;
	
	return channelContentInfo;
}

/**
 * 录入渠道的相关信息后，点击确定按钮操作的处理。</br>
 */
channelInfo906.clickCommitButtonEventHandler906=function(){
	$("#commitButton_channelId_"+channelInfo906.baseInfo.channelId).click(function(){
		//输入项校验
		var checkResult = channelInfo906.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		
		var newdata = channelInfo906.collectData906();
		
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}

/**
 * 营销用语输入字数限制
 */
channelInfo906.textAreaInputNumTip906=function(){
	//输入字数时对字数限制
	var $textArea=$("#channelId_"+channelInfo906.baseInfo.channelId+"_contentWords");
	var $maxNum=$("#channelId_"+channelInfo906.baseInfo.channelId+"_wordSize");
	textAreaInputNumTip($textArea,$maxNum);
}

/**
 * 确定、预览前的合法性检查
 */
channelInfo906.checkValidation=function(){
	var result = new Array();
	result[0]=true;
	
	var contentWords = $("#channelId_"+channelInfo906.baseInfo.channelId+"_contentWords").val();
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