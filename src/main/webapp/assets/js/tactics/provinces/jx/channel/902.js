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
	//如果有默认的推荐语则
	if(tacticsInfo.plan.planComment!=null&&tacticsInfo.plan.planComment!=undefined){
		$("#channelId_"+channelInfo902.baseInfo.channelId+"_contentWords").val(tacticsInfo.plan.planComment);
	}
	
	//如果营销用语内容存在则需要更新营销用语、营销用语的可输入长度
	if(channelInfo902.baseInfo.hasOwnProperty("execContent")){
		//更新营销用语
		$("#channelId_"+channelInfo902.baseInfo.channelId+"_contentWords").val(channelInfo902.baseInfo.execContent);
		//输入字数时对字数限制
		//channelInfo902.textAreaInputNumTip902();
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
	
	//预览按钮
	channelInfo902.clickPreviewButtonEventHandler902();
	
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
		if($("#commitButton_channelId_"+channelInfo902.baseInfo.channelId).hasClass("disable-href")){
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
	var $commitButton=$("#commitButton_channelId_"+channelInfo902.baseInfo.channelId);
	textAreaInputNumTip($textArea,$maxNum,$commitButton);
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

/**
 * 点击预览按钮事件处理
 */
channelInfo902.clickPreviewButtonEventHandler902=function(){
	$("#previewButton_channelId_"+channelInfo902.baseInfo.channelId).click(function(){
		//输入项校验
		var checkResult = channelInfo902.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		
		//预览数据填充
		var plans = "";
		$("#selectedPlan li span").each(function(){
			plans= plans + $(this).html();
		});
		$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_PreviewPlanName1").html(plans);
		$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_PreviewPlanName2").html(plans);
		var words = $("#channelId_"+channelInfo902.baseInfo.channelId+"_contentWords").val();
		$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_Previe_contentWords1").html(words);
		$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_Previe_contentWords2").html(words);
		var custgroupId = $("#selectedCg").data("data").customGroupId;
		if(custgroupId){
			$.post(
				contextPath+"/action/custgroup/custGroupManager/getCustGroupPortrait.do",
				{custgroupId: custgroupId},
				function(retData, textStatus, jqXHR){
					if(retData.sucFlag){
						if(retData.data != null){
							for(var i in retData.data){
								var hx = retData.data[i];
								var hxspan = '<span class="color-blue-dialog">'+hx+'</span>';
								$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_hxdiv1").append(hxspan);
								$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_hxdiv2").append(hxspan);
							}
						} else {/*客户群画像为空*/}
					} else{alert("获取客户画像失败！");}
					//数据加载完后做其他处理
				},"json");
		}
		
		//通过产品id和渠道id获取积分和酬金
		var planIds = "";
		var planlis = $("#selectedPlan li");
		planIds = $(planlis[0]).data().data.planId;
		$.post(contextPath+"/action/custgroup/custGroupManager/getPlanRewardAndScoreInfo.do",
				{planId: planIds, channelId: channelInfo902.baseInfo.channelId},
				function(retData, textStatus, jqXHR){
					if(retData.flag){
						if(retData.reward != null){
							$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_Previe_reward1").append(retData.reward.score);
							$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_Previe_reward2").append(retData.reward.award);
						}
						if(retData.orderMount != null){							
							if(retData.orderMount.sucRate<0.01){								
								$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_Previe_sucRate1").append("低");
								$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_Previe_sucRate2").append("低");
							} else if(retData.orderMount.sucRate>=0.01 && retData.orderMount.sucRate<=0.05) {
								
								$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_Previe_sucRate1").append("中");
								$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_Previe_sucRate2").append("中");
							} else {
								$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_Previe_sucRate1").append("高");
								$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_Previe_sucRate2").append("高");
								
							}
							
							$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_Previe_orderMount1").append(retData.orderMount.orderMount);
							$("#ChannelId_"+channelInfo902.baseInfo.channelId+"_Previe_orderMount2").append(retData.orderMount.orderMount);
						}
					} else{alert(retData.msg);}
					//数据加载完后做其他处理
				},"json");
		
		//展示预览窗口
		$("#channelId_"+channelInfo902.baseInfo.channelId+"_PreviewDiv").show();
		$('.perCommend-bg').removeClass('none');//显示背景图片
		$(".perCommend-dilog").dialog({
			width:900,
			height:560,
			resizable: false,
			modal: true,
			title:"个性化推荐功能",
		});
		//点击关闭预览按钮
		$('.ui-dialog-titlebar-close').off('click').on('click',function(){
			$(".perCommend-dilog").dialog('close');
			$('.perCommend-bg').addClass('none');//背景图片不需要时
		});
		//增加类名控制公共样式
		$(".gradient-dialog").dialog('widget').addClass('gradient-dialog');
		//用户属性展开与收藏
		$('.userPrio-toggle span').click(function(){
			if($('.userPrio-show').is(':hidden')){
				$('.userPrio-show').stop().slideDown();
				$('.userPrio-toggle span').find('i').removeClass('tog-hide').addClass('tog-show');
			}else{
				$('.userPrio-show').stop().slideUp();
				$('.userPrio-toggle span').find('i').removeClass('tog-show').addClass('tog-hide');
			}
		});
	});
}