/*
 * 渠道选择封装类
 * baseInfo渠道基本信息，包含渠道ID 渠道名称
 */
var channelInfo905={baseInfo:null};

/**
 * 渠道初始化函数  在主体架构中会动态调用该函数
 */
function initView905(data){
	channelInfo905.baseInfo=data;
	channelInfo905.initView();
}
/**
 * 界面上初始化、事件监听
 */
channelInfo905.initView=function(){
	//加载一些界面数据
	channelInfo905.loadSomeBaseData905();
	
	//初始化值(编辑时)
	channelInfo905.initValue905();
	
	//添加事件监听
	channelInfo905.clickChannelContentEventHandler905();
}

/**
 * 加载界面上渠道下的基础数据
 */
channelInfo905.loadSomeBaseData905=function(){
	
}

/**
 * 初始化值(编辑时)
 */
channelInfo905.initValue905 = function(){
	//如果营销用语内容存在则需要更新营销用语、营销用语的可输入长度
	if(channelInfo905.baseInfo.hasOwnProperty("execContent")){
		//更新营销用语
		$("#channelId_"+channelInfo905.baseInfo.channelId+"_contentWords").val(channelInfo905.baseInfo.execContent);
		//输入字数时对字数限制
		//channelInfo905.textAreaInputNumTip905();
		//触发事件，将编辑回显得数据放入购物车
		var newdata = channelInfo905.collectData905();
		$("#channelDiv").trigger("changeChannel", newdata);
	}
}

/**
 * 界面上各个元素的事件处理
 */
channelInfo905.clickChannelContentEventHandler905=function(){
	//确定按钮
	channelInfo905.clickCommitButtonEventHandler905();
	
	//输入字数时对字数限制
	channelInfo905.textAreaInputNumTip905();
	
}

/**
 * 收集此渠道的渠道表单内容
 * @returns 渠道表单内容
 */
channelInfo905.collectData905=function(){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = channelInfo905.baseInfo.channelId;
	channelContentInfo.channelName = channelInfo905.baseInfo.channelName;
	channelContentInfo.execContent = $("#channelId_"+channelInfo905.baseInfo.channelId+"_contentWords").val();
	
	channelContentInfo.keys[0] = "渠道名称";
	channelContentInfo.keys[1] = "推荐营销用语";
	channelContentInfo.values[0] = channelInfo905.baseInfo.channelName;
	channelContentInfo.values[1] = channelContentInfo.execContent;
	
	return channelContentInfo;
}

/**
 * 录入渠道的相关信息后，点击确定按钮操作的处理。</br>
 */
channelInfo905.clickCommitButtonEventHandler905=function(){
	$("#commitButton_channelId_"+channelInfo905.baseInfo.channelId).click(function(){
		//输入项校验
		var checkResult = channelInfo905.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		if($("#commitButton_channelId_"+channelInfo905.baseInfo.channelId).hasClass("disable-href")){
			return ;
		}
		
		var newdata = channelInfo905.collectData905();
		
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}

/**
 * 营销用语输入字数限制
 */
channelInfo905.textAreaInputNumTip905=function(){
	//输入字数时对字数限制
	var $textArea=$("#channelId_"+channelInfo905.baseInfo.channelId+"_contentWords");
	var $maxNum=$("#channelId_"+channelInfo905.baseInfo.channelId+"_wordSize");
	var $commitButton=$("#commitButton_channelId_"+channelInfo905.baseInfo.channelId);
	textAreaInputNumTip($textArea,$maxNum,$commitButton);
}

/**
 * 确定、预览前的合法性检查
 */
channelInfo905.checkValidation=function(){
	var result = new Array();
	result[0]=true;
	
	var contentWords = $("#channelId_"+channelInfo905.baseInfo.channelId+"_contentWords").val();
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