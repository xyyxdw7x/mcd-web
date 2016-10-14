/*
 * VOP渠道选择封装类
 * baseInfo渠道基本信息，包含渠道ID 渠道名称
 */
var channelInfo913={baseInfo:null};


/**
 * VOP渠道初始化函数  在主体架构中会动态调用该函数
 */
function initView913(data){
	channelInfo913.baseInfo=data;
	channelInfo913.initView(data);
}

/**
 * VOP渠道初始化
 */
channelInfo913.initView=function(data){
	channelInfo913.queryAdivInfo();
	channelInfo913.addSaveBtnClickEvent();
	channelInfo913.clickPreview();//预览
	channelInfo913.clickChannelVopEventHandler();
	channelInfo913.clickClosePreviewDialogHandler();
	var $textArea=$("#content913");
	var $maxNum=$("#wordSize913");
	textAreaInputNumTip($textArea,$maxNum);
	//编辑情况下有策略ID
	if(data.campId==null||data.campId==undefined){
		return ;
	}
	//回显
	if(data.hasOwnProperty("execContent")){
		$("#content913").val(data.execContent);
		var wordLen = data.execContent.length;
		$maxNum.text($maxNum.text()-wordLen);
	}
	if(data.hasOwnProperty("execContent")){
		$("#channelSaveBtn913").trigger("click");
	}
}

/**
 * 获取运营位信息
 */
channelInfo913.queryAdivInfo = function(){
	var channelId = channelInfo913.baseInfo.channelId;
	var planId = tacticsInfo.plan.planId;
	var url=contextPath+"/action/tactics/createTactics/getAdivInfo.do";
	$.ajax({
		url:url,
		data:{"channelId":channelId,"planId":planId},
		success:channelInfo913.queryAdivInfoSuc,
		error:function (e){
			alert("获取运营位信息失败");
		}
	});
}

/**
 * 获取运营位信息成功
 */
channelInfo913.queryAdivInfoSuc = function(result){
	var adivIds="";
	if(result.data){
	for(var i=0;i<result.data.length;i++){
		var item=result.data[i];
		if(i==(result.data.length-1)){
			adivIds=item.adivId+adivIds;
		}else{
			adivIds=item.adivId+adivIds+",";
		}
	}
	channelInfo913.baseInfo.adivIds=adivIds;
	}
}


/**
 * 
 * 点击保存事件
 * 派发渠道保存事件
 */
channelInfo913.addSaveBtnClickEvent=function(){
	$("#channelSaveBtn913").click(function(event){
		//输入项校验
		var checkResult = channelInfo913.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		
		var newdata=channelInfo913.getChannelInfoData();
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}


/**
 * 获取渠道输入信息
 * 每次点击应该传入新的channelInfo对象
 */
channelInfo913.getChannelInfoData=function(){
	var channelInfo = new Object();
	channelInfo.channelId=channelInfo913.baseInfo.channelId;
	channelInfo.channelName=channelInfo913.baseInfo.channelName;
	channelInfo.execContent=$("#content913").val();
	var keys=["渠道名称","推荐用语","运营位ID"];
	var content=channelInfo.execContent;
	var values=[channelInfo913.baseInfo.channelName,content,channelInfo913.baseInfo.adivIds];
	channelInfo.keys=keys;
	channelInfo.values=values;
	channelInfo.adivIds=channelInfo913.baseInfo.adivIds;
	return channelInfo;
}

channelInfo913.clickChannelVopEventHandler = function(){
	$("#channelVopId .opera-top").click(function(){
		
		var hasClass = $("#channelVopId").hasClass("active");
		if(!hasClass){
			$("#channelVopId").addClass("active");
		} else {
			$("#channelVopId").removeClass("active");
		}
	});
}

/**
 * 点击预览按钮事件处理
 * @param data
 */
channelInfo913.clickPreview = function(){
	$("#channelVopPrevId").click(function(){
		//输入项校验
		var checkResult = channelInfo913.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		$("#channelVopId").addClass("active");
		$("#channelVopPrevDialog").show();
		$(".ui-widget-overlay").show();
		$(".ui-widget-overlay").css("opacity",".9");
		$("#leftPlanName913").html(tacticsInfo.plan.planName);
		$("#rightPlanName913").html(tacticsInfo.plan.planName);
		$("#topPlanName913").html(tacticsInfo.plan.planName);
		var content1=$("#content913").val();
		$("#previewContent913").text(content1);
	});
	$("#previewBtn913").click(function(){
		//输入项校验
		var checkResult = channelInfo913.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		
		$("#channelVopPrevDialog").show();
		$(".ui-widget-overlay").show();
		$(".ui-widget-overlay").css("opacity",".9");
		$("#leftPlanName913").html(tacticsInfo.plan.planName);
		$("#rightPlanName913").html(tacticsInfo.plan.planName);
		$("#topPlanName913").html(tacticsInfo.plan.planName);
		var content=$("#content913").val();
		$("#previewContent913").text(content);
	});
}

/**
 * 确定、预览前的合法性检查
 */
channelInfo913.checkValidation=function(){
	var result = new Array();
	result[0]=true;
	
	var contentWords = $("#content913").val();
	if(contentWords.length<1){
		result[0]=false;
		result[1]="必须录入推荐用语!";
		return result;
	} else if(contentWords.trim().length<1) {
		result[0]=false;
		result[1]="推荐用语不能为空字符!";
		return result;
	}
	return result;
}

/**
 * 点击关闭预览窗口事件处理
 */
channelInfo913.clickClosePreviewDialogHandler=function(){
	$("#closePreview_channelId_"+channelInfo913.baseInfo.channelId).click(function(){
		$(".ui-widget-overlay").hide();
		$("#channelVopPrevDialog").hide();
	});
}