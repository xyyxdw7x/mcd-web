/*
 * 渠道选择封装类
 * baseInfo渠道基本信息，包含渠道ID 渠道名称
 */
var channelInfo915={baseInfo:null};

/**
 * 渠道初始化函数  在主体架构中会动态调用该函数
 */
function initView915(data){
	channelInfo915.baseInfo=data;
	channelInfo915.initView();
}
/**
 * 界面上初始化、事件监听
 */
channelInfo915.initView=function(){
	//加载一些界面数据
	channelInfo915.loadSomeBaseData915();
	
	//初始化值(编辑时)
	channelInfo915.initValue915();
	
	//添加事件监听
	channelInfo915.clickChannelContentEventHandler915();
}

/**
 * 加载界面上渠道下的基础数据
 */
channelInfo915.loadSomeBaseData915=function(){
	
}

/**
 * 初始化值(编辑时)
 */
channelInfo915.initValue915 = function(){
	//如果有默认的推荐语则
	if(tacticsInfo.plan.planComment!=null&&tacticsInfo.plan.planComment!=undefined){
		$("#channelId_"+channelInfo915.baseInfo.channelId+"_contentWords").val(tacticsInfo.plan.planComment);
	}
	
	//如果营销用语内容存在则需要更新营销用语、营销用语的可输入长度
	if(channelInfo915.baseInfo.hasOwnProperty("execContent")){
		//更新营销用语
		$("#channelId_"+channelInfo915.baseInfo.channelId+"_contentWords").val(channelInfo915.baseInfo.execContent);
		//输入字数时对字数限制
		//channelInfo915.textAreaInputNumTip915();
		//触发事件，将编辑回显得数据放入购物车
		var newdata = channelInfo915.collectData915();
		$("#channelDiv").trigger("changeChannel", newdata);
	}
}

/**
 * 界面上各个元素的事件处理
 */
channelInfo915.clickChannelContentEventHandler915=function(){
	//确定按钮
	channelInfo915.clickCommitButtonEventHandler915();
	
	//输入字数时对字数限制
	channelInfo915.textAreaInputNumTip915();
	
}

/**
 * 收集此渠道的渠道表单内容
 * @returns 渠道表单内容
 */
channelInfo915.collectData915=function(){
	var channelContentInfo = new Object();
	channelContentInfo.keys = new Array();
	channelContentInfo.values = new Array();
	channelContentInfo.channelId = channelInfo915.baseInfo.channelId;
	channelContentInfo.channelName = channelInfo915.baseInfo.channelName;
	channelContentInfo.execContent = $("#channelId_"+channelInfo915.baseInfo.channelId+"_contentWords").val();
	
	channelContentInfo.keys[0] = "渠道名称";
	channelContentInfo.keys[1] = "推荐营销用语";
	channelContentInfo.values[0] = channelInfo915.baseInfo.channelName;
	channelContentInfo.values[1] = channelContentInfo.execContent;
	
	return channelContentInfo;
}

/**
 * 录入渠道的相关信息后，点击确定按钮操作的处理。</br>
 */
channelInfo915.clickCommitButtonEventHandler915=function(){
	$("#commitButton_channelId_"+channelInfo915.baseInfo.channelId).click(function(){
		//输入项校验
		var checkResult = channelInfo915.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		
		if($("#commitButton_channelId_"+channelInfo915.baseInfo.channelId).hasClass("disable-href")){
			return ;
		}
		
		var newdata = channelInfo915.collectData915();
		
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}

/**
 * 营销用语输入字数限制
 */
channelInfo915.textAreaInputNumTip915=function(){
	//输入字数时对字数限制
	var $textArea=$("#channelId_"+channelInfo915.baseInfo.channelId+"_contentWords");
	var $maxNum=$("#channelId_"+channelInfo915.baseInfo.channelId+"_wordSize");
	textAreaInputNumTip($textArea,$maxNum);
}

/**
 * 确定、预览前的合法性检查
 */
channelInfo915.checkValidation=function(){
	var result = new Array();
	result[0]=true;
	
	var contentWords = $("#channelId_"+channelInfo915.baseInfo.channelId+"_contentWords").val();
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