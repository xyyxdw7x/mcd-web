/*
 * VOP渠道选择封装类
 * baseInfo渠道基本信息，包含渠道ID 渠道名称
 */
var channelInfo913={baseInfo:null};
var channel913AdivNames = null;

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
	
	//如果有默认的推荐语则
	if(tacticsInfo.plan.planComment!=null&&tacticsInfo.plan.planComment!=undefined){
		$("#channelId_"+channelInfo913.baseInfo.channelId+"_contentWords").val(tacticsInfo.plan.planComment);
	}
	
	//回显
	if(data.hasOwnProperty("execContent")){
		$("#content913").val(data.execContent);
	}
	if(data.hasOwnProperty("execContent")){
		$("#channelSaveBtn913").trigger("click");
	}
	//输入字数时对字数限制
	channelInfo913.textAreaInputNumTip913();
	//编辑情况下没有策略ID
	if(data.campId==null||data.campId==undefined){
		return ;
	}
}

/**
 * 营销用语输入字数限制
 */
channelInfo913.textAreaInputNumTip913=function(){
	var $textArea=$("#content913");
	var $maxNum=$("#wordSize913");
	var $commitButton=$("#channelSaveBtn913");
	channelInfo913.textAreaInputNumTip($textArea,$maxNum,$commitButton);
}

/**
 * 输入框字数变化提示;
 * 推荐用语输入50个字为最佳效果，超过50个字还可以输入，最多可以输入140个字，
 * 超出140个字推荐用语边框变红，“确定”按钮文字变为“字数超出请修改”并不能点击
 * @param textArea输入框
 * @param numItem 最大字数
 */
channelInfo913.textAreaInputNumTip = function(textArea,numItem,commitButton) {
    var max = numItem.text();
    var curLength;
    textArea[0].setAttribute("maxlength", 140);
    curLength = textArea.val().length;
    numItem.text(max - curLength);
    textArea.on('input propertychange', function () {
        var _value = $(this).val().replace(/\n/gi,"").replace(/\$[\s\S]*?\$/gi,"");
        numItem.text(max - _value.length);
        if(_value.length>=50){
        	numItem.text(0);
        }
        
        if(numItem.text()==0&&_value.length>=140){
        	textArea.addClass("red-border");
        	commitButton.addClass("disable-href");
        	commitButton.text("字数超出请修改");
        }else{
        	textArea.removeClass("red-border");
        	commitButton.removeClass("disable-href");
        	commitButton.text("确定");
        }
    });
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
	var adivNames = "";
	if(result.data){
	for(var i=0;i<result.data.length;i++){
		var item=result.data[i];
		if(i==(result.data.length-1)){
			adivIds=item.adivId+adivIds;
			adivNames=item.adivName+adivNames;
		}else{
			adivIds=item.adivId+adivIds+",";
			adivNames=item.adivName+adivNames+",";
		}
	}
	channelInfo913.baseInfo.adivIds=adivIds;
	channel913AdivNames = adivNames;
	}
}


/**
 * 
 * 点击保存事件
 * 派发渠道保存事件
 */
channelInfo913.addSaveBtnClickEvent=function(){
	$("#channelSaveBtn913").click(function(event){
		
		if($("#channelSaveBtn901").hasClass("disable-href")){
			return ;
		}
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
	var keys=["渠道名称","推荐用语","运营位ID","运营位名称"];
	var content=channelInfo.execContent;
	var values=[channelInfo913.baseInfo.channelName,content,channelInfo913.baseInfo.adivIds,channel913AdivNames];
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