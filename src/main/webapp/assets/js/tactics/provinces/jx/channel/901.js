/*
 * 短信渠道选择封装类
 * baseInfo渠道基本信息，包含渠道ID 渠道名称
 */
var channelInfo901={baseInfo:null};

/**
 * 短信渠道初始化函数  在主体架构中会动态调用该函数
 */
function initView901(data){
	channelInfo901.baseInfo=data;
	channelInfo901.initView(data);
}

/**
 * 短信渠道初始化
 */
channelInfo901.initView=function(data){
	//一次性客户群只能选择一次性
	if(tacticsInfo.custGroup.updateCycle==1){
		$("#901SendCycle [data-data='0']").attr("disabled",'true');
		$("#901SendCycle [data-data='1']").removeAttr("disabled",'true');
	}else{
		$("#901SendCycle [data-data='1']").attr("disabled",'true');
		$("#901SendCycle [data-data='0']").removeAttr("disabled",'true');
	}
	//发送周期切换
	channelInfo901.addSendCycleChangeEvent();
	channelInfo901.addSaveBtnClickEvent();
	//changeCustomerGroup
	channelInfo901.querySmsVarList();
	var $textArea=$("#content901");
	var $maxNum=$("#wordSize901");
	textAreaInputNumTip($textArea,$maxNum);
	//如果有默认的推荐语则
	if(tacticsInfo.plan.planComment!=null){
		$("#content901").val(tacticsInfo.plan.planComment);
		var wordLen = data.execContent.length;
		$maxNum.text($maxNum.text()-wordLen);
	}
	//编辑情况下有策略ID
	if(data.campId==null||data.campId==undefined){
		return ;
	}
	//回显
	if(data.hasOwnProperty("execContent")){
		$("#content901").val(data.execContent);
		var wordLen = data.execContent.length;
		$maxNum.text($maxNum.text()-wordLen);
	}
	if(data.hasOwnProperty("contactType")){
		var contactType=data.contactType;
		$("#901SendCycle button").removeClass("active");
		$("#901SendCycle [data-data='"+contactType+"']").addClass("active");
	}
	if(data.hasOwnProperty("execContent")){
		$("#channelSaveBtn901").trigger("click");
	}
}
/**
 * 获取渠道输入信息
 * 每次点击应该传入新的channelInfo对象
 */
channelInfo901.getChannelInfoData=function(){
	var channelInfo = new Object();
	channelInfo.channelId=channelInfo901.baseInfo.channelId;
	channelInfo.channelName=channelInfo901.baseInfo.channelName;
	channelInfo.execContent=$("#content901").val();
	var isLoopSend=$("#901SendCycle button").filter(".active").data("data");
	channelInfo.contactType =isLoopSend;
	channelInfo.isHasVar=channelInfo901.hasSmsVar(channelInfo.execContent);
	
	var keys=["推荐语","触发时机","执行周期","派发时间"];
	var content=channelInfo.execContent;
	var cepName="";
	var cycleName=$("#901SendCycle button").filter(".active").html();
	var sendTime="";
	var values=[content,cepName,cycleName,sendTime];
	channelInfo.keys=keys;
	channelInfo.values=values;
	
	return channelInfo;
}

/**
 * 发送周期切换事件
 *
 */
channelInfo901.addSendCycleChangeEvent=function(){
	 $("#901SendCycle button").bind("click",function(event){
		 var hasActive=$(event.currentTarget).hasClass("active");
		 if(hasActive){
			 $("#901SendCycle button").not(".active").addClass("active");
			 $(event.currentTarget).removeClass("active");
		 }else{
			 $("#901SendCycle button").filter(".active").removeClass("active");
			 $(event.currentTarget).addClass("active");
		 }
	 });
}

/**
 * 
 * 点击保存事件
 * 派发渠道保存事件
 */
channelInfo901.addSaveBtnClickEvent=function(){
	$("#channelSaveBtn901").click(function(event){
		var content=$("#content901").val().replace(/\n/gi,"");
		if(content==""){
			alert("短信推荐用语不能为空");
			return ;
		}
		var newdata=channelInfo901.getChannelInfoData();
		$("#channelDiv").trigger("changeChannel", newdata);
	});
}
/**
 * 判断短信内容中是否含有短信变量
 */
channelInfo901.hasSmsVar=function(contentStr){
	var suc=false;
	var varList=$("#smsVar901 span");
	if(varList.length==0){
		return suc;
	}
	varList.each(function(index,obj){
		var varValue=$(obj).attr("attrcol");
		if(contentStr.indexOf(varValue)>=0){
			suc=true;
			//return false跳出each循环
			return false;
		}
	});
	return suc;
}
/**
 * 查询客户群变量信息
 */
channelInfo901.querySmsVarList=function(){
	var custgroupId=tacticsInfo.custGroup.customGroupId;
	var url=contextPath+"/action/tactics/createTactics/getCustGroupVars.do";
	$.ajax({
		url:url,
		data:{"custGroupId":custgroupId},
		success:channelInfo901.querySmsVarListSuc,
		error:function (e){
			alert("查询客户群变量信息失败");
		}
	});
}
/**
 * 查询客户群变量信息成功
 */
channelInfo901.querySmsVarListSuc=function(result){
	if(!result){
		return ;
	}
	var temp ="";
	//客户群的所有变量列表
	for(var x in result){
		if($("#smsVar901").find('span[attrCol="'+result[x].attrCol+'"]').length==0){
			var liStr='<span class="border-item" attrCol="'+result[x].attrCol+'">'+result[x].attrColName+'</span>';
			temp+=liStr;
		}
	}
	$("#smsVar901").html(temp);
	//选择变量时，写入文本域
	var textarea = $('#content901');
	$("#smsVar901 span").on("click",function(){
		textarea.insertContent(("$"+$(this).attr('attrCol')+"$"));
		$(this).addClass("active");
	});
}


