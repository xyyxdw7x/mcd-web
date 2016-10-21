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
	channelInfo913.clickClosePreviewDialogHandler();
	
	//如果有默认的推荐语则
	if(tacticsInfo.plan.planComment!=null&&tacticsInfo.plan.planComment!=undefined){
		$("#content913").val(tacticsInfo.plan.planComment);
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
	if(result.data){
		//运营位li元素字符串
		var liStr=
		'<li id="{channelVopId}" title="{title}" adivname="{adivname}" adivid="{adivid}" >\n'+
		'	<div class="opera-top">\n'+
		'	<img src="{contextPath}/assets/images/put/opera-1.png"/>\n'+
		'		<div class="op-bg"></div>\n'+
		'	<div class="op-add"></div>\n'+
		'	</div>\n'+
		'	<p class="opera-bot text-center ft-14" id="{channelVopPrevId}">\n'+
		'	底部运营位<a href="javascript:;" class="opera-btn fright"></a>\n'+
		'	</p>\n'+
		'</li>\n';
		//根据运营位的个数动态加载界面展示运营位
		for(var i=0;i<result.data.length;i++){
			var item=result.data[i];
			var lihtml= liStr.replace("{channelVopId}", "channel"+channelInfo913.baseInfo.channelId+"adiv"+item.adivId);
			lihtml = lihtml.replace("{channelVopPrevId}", "channel"+channelInfo913.baseInfo.channelId+"VopPrev"+item.adivId);
			lihtml = lihtml.replace("{title}", "运营位名称："+item.adivName);
			lihtml = lihtml.replace("{adivname}", item.adivName);
			lihtml = lihtml.replace("{adivid}", item.adivId);
			lihtml = lihtml.replace("{contextPath}", contextPath);
			$("#channelId"+channelInfo913.baseInfo.channelId+"_vopAdivInfoId").append(lihtml);

			//运营位标题预览被点击事件处理
			$("#channel"+channelInfo913.baseInfo.channelId+"VopPrev"+item.adivId).click(function(){
				//输入项校验
				var checkResult = channelInfo913.checkValidation();
				if(!checkResult[0]){
					alert(checkResult[1]);
					return ;
				}
				$("#channel"+channelInfo913.baseInfo.channelId+"adiv"+item.adivId).addClass("active");
				$("#channelVopPrevDialog").show();
				$(".ui-widget-overlay").show();
				$(".ui-widget-overlay").css("opacity",".9");
				$("#leftPlanName913").html(tacticsInfo.plan.planName);
				$("#rightPlanName913").html(tacticsInfo.plan.planName);
				$("#topPlanName913").html(tacticsInfo.plan.planName);
				var content1=$("#content913").val();
				$("#previewContent913").text(content1);
			});
			
			//运营位背景图片被点击事件处理
			$("#channelId913_vopAdivInfoId li .opera-top").click(function(event){
				var ce = $(event.currentTarget);
				var parent = $(ce).parent();
				var hasClass = $(parent).hasClass("active");
				if(!hasClass){
					$("#channel"+channelInfo913.baseInfo.channelId+"adiv"+item.adivId).addClass("active");
				} else {
					$("#channel"+channelInfo913.baseInfo.channelId+"adiv"+item.adivId).removeClass("active");
				}
			});
		}
	} else{
		alert("此产品在此渠道无运营位。");
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
		
		//加入购物车
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
	
	var yunyingwei = $("#channelId913_vopAdivInfoId li.active");
	var adivIds="";
	var adivNames = "";
	for(var i=0;i<yunyingwei.length;i++){
		var item=yunyingwei[i];
		if(i==0){
			adivIds=$(item).attr("adivid");
			adivNames=$(item).attr("adivname");
		}else{
			adivIds=adivIds+","+$(item).attr("adivid");
			adivNames=adivNames+","+$(item).attr("adivname");
		}
	}
	channelInfo913.baseInfo.adivId=adivIds;
	channel913AdivNames = adivNames;
	
	var keys=["渠道名称","推荐用语","运营位ID","运营位名称"];
	var content=channelInfo.execContent;
	var values=[channelInfo913.baseInfo.channelName,content,channelInfo913.baseInfo.adivId,channel913AdivNames];
	channelInfo.keys=keys;
	channelInfo.values=values;
	channelInfo.adivId=channelInfo913.baseInfo.adivId;
	return channelInfo;
}

/**
 * 点击预览按钮事件处理
 * @param data
 */
channelInfo913.clickPreview = function(){
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
	
	//是否已经加载了运营位
	var yunyingwei = $("#channelId913_vopAdivInfoId li");
	if(yunyingwei.length == 0){
		result[0]=false;
		result[1]="界面未加载运营位，渠道信息不完整!";
		return result;
	}
	
	//为选择运营位
	yunyingwei = $("#channelId913_vopAdivInfoId li.active");
	if(yunyingwei.length == 0){
		result[0]=false;
		result[1]="至少要选择一个运营位!";
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