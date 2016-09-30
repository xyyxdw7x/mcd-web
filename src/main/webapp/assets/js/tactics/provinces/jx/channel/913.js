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
	$("#channelVopId").click(function(){
		$(this).addClass("active");
	});
	channelInfo913.queryAdivInfo();
	channelInfo913.addSaveBtnClickEvent();
	channelInfo913.clickPreview();//预览
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


/**
 * 
 * 点击保存事件
 * 派发渠道保存事件
 */
channelInfo913.addSaveBtnClickEvent=function(){
	$("#channelSaveBtn913").click(function(event){
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
	var keys=["推荐语","运营位ID"];
	var content=channelInfo.execContent;
	var values=[content,channelInfo913.baseInfo.adivIds];
	channelInfo.keys=keys;
	channelInfo.values=values;
	channelInfo.adivIds=channelInfo913.baseInfo.adivIds;
	return channelInfo;
}


/**
 * 点击预览按钮事件处理
 * @param data
 */
channelInfo913.clickPreview = function(){
	$("#channelVopPrevId").click(function(){
		$("#channelVopPrevDialog").show();
		$(".ui-widget-overlay").show();
		$(".ui-widget-overlay").css("opacity",".9");
	});
	$("#previewBtn913").click(function(){
		$("#channelVopPrevDialog").show();
		$(".ui-widget-overlay").show();
		$(".ui-widget-overlay").css("opacity",".9");
	});
	$(".ui-widget-overlay").click(function(){
		$(this).hide();
		$("#channelVopPrevDialog").hide();
	});
	$("#leftPlanName913").html(tacticsInfo.plan.planName);
	$("#rightPlanName913").html(tacticsInfo.plan.planName);
	$("#topPlanName913").html(tacticsInfo.plan.planName);
	var content=$("#content913").val();
	$("#previewContent901").html(content);
}