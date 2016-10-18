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
	//如果有默认的推荐语则
	if(tacticsInfo.plan.planComment!=null&&tacticsInfo.plan.planComment!=undefined){
		$("#channelId_"+channelInfo906.baseInfo.channelId+"_contentWords").val(tacticsInfo.plan.planComment);
	}
	
	//如果营销用语内容存在则需要更新营销用语、营销用语的可输入长度
	if(channelInfo906.baseInfo.hasOwnProperty("execContent")){
		//更新营销用语
		$("#channelId_"+channelInfo906.baseInfo.channelId+"_contentWords").val(channelInfo906.baseInfo.execContent);
		//输入字数时对字数限制
		//channelInfo906.textAreaInputNumTip906();
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
	
	//预览按钮
	channelInfo906.clickPreviewButtonEventHandler906();
	
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
		if($("#commitButton_channelId_"+channelInfo906.baseInfo.channelId).hasClass("disable-href")){
			return ;
		}
		
		var newdata = channelInfo906.collectData906();
		
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}

/**
 * 点击预览按钮事件处理
 */
channelInfo906.clickPreviewButtonEventHandler906=function(){
	$("#previewButton_channelId_"+channelInfo906.baseInfo.channelId).click(function(){
		//输入项校验
		var checkResult = channelInfo906.checkValidation();
		if(!checkResult[0]){
			alert(checkResult[1]);
			return ;
		}
		
		//预览数据填充
		var plans = "";
		$("#selectedPlan li span").each(function(){
			plans= plans + $(this).html();
		});
		$("#ChannelId_"+channelInfo906.baseInfo.channelId+"_PreviewPlanName1").html(plans);
		$("#ChannelId_"+channelInfo906.baseInfo.channelId+"_PreviewPlanName2").html(plans);
		var words = $("#channelId_"+channelInfo906.baseInfo.channelId+"_contentWords").val();
		$("#ChannelId_"+channelInfo906.baseInfo.channelId+"_Previe_contentWords1").html(words);
		$("#ChannelId_"+channelInfo906.baseInfo.channelId+"_Previe_contentWords2").html(words);
		var custgroupId = $("#selectedCg").data("data").customGroupId;
		//通过客户群id获取客户群对应的客户画像
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
								$("#ChannelId_"+channelInfo906.baseInfo.channelId+"_hxdiv1").append(hxspan);
								$("#ChannelId_"+channelInfo906.baseInfo.channelId+"_hxdiv2").append(hxspan);
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
		$.post(contextPath+"/action/tactics/createTactics/getAboutPlanRewardInfo.do",
				{planId: planIds, channelId: channelInfo906.baseInfo.channelId},
				function(retData, textStatus, jqXHR){
					if(retData.flag){
						if(retData.data != null){
							var reward = retData.data.planReward;
							$("#ChannelId_"+channelInfo906.baseInfo.channelId+"_Previe_reward1").append(reward);
							$("#ChannelId_"+channelInfo906.baseInfo.channelId+"_Previe_reward2").append(reward);
						}
					} else{alert(retData.msg);}
					//数据加载完后做其他处理
				},"json");
		
		//展示预览窗口
		$("#channelId_"+channelInfo906.baseInfo.channelId+"_PreviewDiv").show();
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

/**
 * 营销用语输入字数限制
 */
channelInfo906.textAreaInputNumTip906=function(){
	//输入字数时对字数限制
	var $textArea=$("#channelId_"+channelInfo906.baseInfo.channelId+"_contentWords");
	var $maxNum=$("#channelId_"+channelInfo906.baseInfo.channelId+"_wordSize");
	var $commitButton=$("#commitButton_channelId_"+channelInfo906.baseInfo.channelId);
	textAreaInputNumTip($textArea,$maxNum,$commitButton);
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
