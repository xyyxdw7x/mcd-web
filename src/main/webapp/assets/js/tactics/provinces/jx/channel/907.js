/*
 * 渠道选择封装类
 * baseInfo渠道基本信息，包含渠道ID 渠道名称
 */
var channelInfo907={baseInfo:null};

/**
 * 渠道初始化函数  在主体架构中会动态调用该函数
 */
function initView907(data){
	channelInfo907.baseInfo=data;
	channelInfo907.initView();
}
/**
 * 界面上初始化、事件监听
 */
channelInfo907.initView=function(){
	//加载一些界面数据
	channelInfo907.loadSomeBaseData907();
	
	//初始化值(编辑时)
	channelInfo907.initValue907();	
	
	//添加事件监听
	channelInfo907.clickChannelContentEventHandler907();
}

/**
 * 加载界面上渠道下的基础数据
 */
channelInfo907.loadSomeBaseData907=function(){
	
}

/**
 * 初始化值(编辑时)
 */
channelInfo906.initValue907 = function(){
	//如果营销用语内容存在则需要更新营销用语、营销用语的可输入长度
	if(channelInfo907.baseInfo.hasOwnProperty("execContent")){
		//更新营销用语
		$("#channelId_"+channelInfo907.baseInfo.channelId+"_contentWords").val(channelInfo907.baseInfo.execContent);
		//营销用语的可输入长度
		var $textArea=$("#channelId_"+channelInfo907.baseInfo.channelId+"_contentWords");
		var $maxNum=$("#channelId_"+channelInfo907.baseInfo.channelId+"_wordSize");
		textAreaInputNumTip($textArea,$maxNum);
		var wordLen = channelInfo907.baseInfo.execContent.length;
		$maxNum.text($maxNum.text()-wordLen);
		
		
		//触发事件，将编辑回显得数据放入购物车
		var newdata = channelInfo907.collectData907();
		$("#channelDiv").trigger("changeChannel", newdata);
	}
}

/**
 * 界面上各个元素的事件处理
 */
channelInfo907.clickChannelContentEventHandler907=function(){
	//确定按钮
	channelInfo907.clickCommitButtonEventHandler907();
	
	//输入字数时对字数限制
	channelInfo907.textAreaInputNumTip907();
	
}

/**
 * 收集此渠道的渠道表单内容
 * @returns 渠道表单内容
 */
channelInfo907.collectData907=function(){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = channelInfo907.baseInfo.channelId;
	channelContentInfo.channelName = channelInfo907.baseInfo.channelName;
	channelContentInfo.execContent = $("#channelId_"+channelInfo907.baseInfo.channelId+"_contentWords").val();
	
	channelContentInfo.keys[0] = "渠道名称";
	channelContentInfo.keys[1] = "推荐营销用语";
	channelContentInfo.values[0] = channelInfo907.baseInfo.channelName;
	channelContentInfo.values[1] = channelContentInfo.execContent;
	
	return channelContentInfo;
}

/**
 * 录入渠道的相关信息后，点击确定按钮操作的处理。</br>
 */
channelInfo907.clickCommitButtonEventHandler907=function(){
	$("#commitButton_channelId_"+channelInfo907.baseInfo.channelId).click(function(){
		//输入项校验
		var checkResult = channelInfo907.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		
		var newdata = channelInfo907.collectData907();
		
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}

/**
 * 营销用语输入字数限制
 */
channelInfo907.textAreaInputNumTip907=function(){
	//输入字数时对字数限制
	var $textArea=$("#channelId_"+channelInfo907.baseInfo.channelId+"_contentWords");
	var $maxNum=$("#channelId_"+channelInfo907.baseInfo.channelId+"_wordSize");
	textAreaInputNumTip($textArea,$maxNum);
}

/**
 * 确定、预览前的合法性检查
 */
channelInfo907.checkValidation=function(){
	var result = new Array();
	result[0]=true;
	
	var contentWords = $("#channelId_"+channelInfo907.baseInfo.channelId+"_contentWords").val();
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