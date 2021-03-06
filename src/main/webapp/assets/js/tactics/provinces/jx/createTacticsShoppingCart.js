var shopCarInfo={};

/**
 * 暂存架页面初始化
 */
shopCarInfo.initShopCar=function(){
	shopCarInfo.addChangePlanEvent();
	shopCarInfo.addChangeCustomerGroupEvent();
	shopCarInfo.addChangeChannelEvent();
	shopCarInfo.addSaveDialogEvent();
};
/**
 * 产品发生变化事件
 */
shopCarInfo.addChangePlanEvent=function(){
	$("#shopCar").bind("shopCarChangePlan",function(event,data){
		$("#selectedPlan").html("");
		if(data==null){
			$("#selectedPlan").html("");
			// 产品取消
			return ;
		}
		var planId=data.planId;
		//设置产品名称
		var liStr="<li id='selectedPlan_"+planId+"' shopCarPlanId='"+planId+"'><span>"+data.planName+"</span></li>";
		$("#selectedPlan").append(liStr);
		//将数据绑定到dom元素上
		$("#selectedPlan_"+planId).data("data",data);
	});
};
/**
 * 客户群发生变化事件
 */
shopCarInfo.addChangeCustomerGroupEvent=function(){
	$("#shopCar").bind("shopCarChangeCustomerGroup",function(event,data){
		if(data==null){
			$("#selectedCg").html("");
			$("#selectedCg").data("data",null);
			return ;
		}
		//设置客户群名称
		$("#selectedCg").html(data.customGroupName);
		//将数据绑定到dom元素上
		$("#selectedCg").data("data",data);
	});
};
/**
 * 渠道发生变化
 */
shopCarInfo.addChangeChannelEvent=function(){
	$("#shopCar").bind("shopCarChangeChannel",function(event,data){
		if(data==null||data== undefined){
			return ;
		}
		var channelId=data.channelId;
		//先判断是取消还是增加
		if(data.hasOwnProperty('isCancell')&&data["isCancell"]=="1"){
			$("#selectedChannel_"+channelId).remove();
			return ;
		}
		var channelName=data.channelName;
		var channelColumns="";
		var channelInfoStr="<div>";
		if(data.keys!=null&&data.keys!=undefined){
			for(var i=0;i<data.keys["length"];i++){
				var key=data.keys[i];
				var value=data.values[i];
				var itemStr="<p class='right-p'><span class='color-666'>"+key+"：</span><em class='color-333'>"+value+"</em></p>";
				channelInfoStr=channelInfoStr+itemStr;
			}
			delete data.keys;
			delete data.values;
		}
		channelInfoStr=channelInfoStr+"</div>";
		var channelHtmlStr="<li id='selectedChannel_"+channelId+"'><p class='ft14'>"+channelName+"</p>"+channelInfoStr+"<div>"+channelColumns+"</div><hr/></li>";
		//<p><span class="color-666">短信触发时机：</span><em class="color-333">***********</em></p>
		//如果渠道已经存在就更新
		if($("#selectedChannel_"+channelId).length>0){
			$("#selectedChannel_"+channelId).html(channelHtmlStr);
		}else{
			$("#selectedChannels").append(channelHtmlStr);
		}
		//将数据绑定到dom元素上
		//$("#selectedChannels").data("data",data);
	});
};
/**
 * 点击保存弹出对话框事件
 */
shopCarInfo.addSaveDialogEvent=function(){
	var saveBtnInfo={"class":"dialog-btn dialog-btn-blue","text":"保存","click":function(){
		shopCarInfo.saveOrCommitTactics(this,false);
	}};
	var commitBtnInfo={"class":"dialog-btn dialog-btn-orange","text":"提交审批","click":function(){
		shopCarInfo.saveOrCommitTactics(this,true);
	}};
	$("#saveDialogBtn").click(function(){
		//判断产品 客户群 渠道是否已经选择 
		if(tacticsInfo.plan==null){
			alert("请选择产品");
			return ;
		}
		if(tacticsInfo.custGroup==null){
			alert("请选择客户群");
			return ;
		}
		if(tacticsInfo.channels==null||tacticsInfo.channels.length==0){
			alert("请选择渠道");
			return ;
		}
		shopCarInfo.intiCampInfo();
		shopCarInfo.showDatePicker();
		$("#saveDialog").dialog({
			width:600,
			height:250,
			resizable: false,
			modal: true,
			title:"保存",
			buttons: [saveBtnInfo,commitBtnInfo]
		});
	});
}
/**
 * 策略保存页面默认数据
 */
shopCarInfo.intiCampInfo=function(){
	if(tacticsInfo.camp!=null){
		var camp=tacticsInfo.camp;
		if(camp.campName!=undefined){
			$("#tacticsName").val(camp.campName);
		}
		if(camp.startDate!=undefined){
			$("#startDate").val(camp.startDate);
		}
		if(camp.endDate!=undefined){
			$("#endDate").val(camp.endDate);
		}
		return ;
	}
	//策略名称自动生成 产品名称+2016092911011111
	var campNewName=tacticsInfo.plan.planName+"-"+(new XDate().toString("yyyyMMddHHmmss"));
	$("#tacticsName").val(campNewName);
}

/**
 * 保存或提交审批
 * @param dialog
 * @param isCommit 是否提交审批
 */
shopCarInfo.saveOrCommitTactics=function(dialog,isCommit){
	var campName=$("#tacticsName").val();
	campName=campName.replace(/\n/gi,"");
	if(campName==""){
		alert("策略名称不能为空");
		return ;
	}
	var putDateStart=$("#startDate").val();
	var putDateEnd=$("#endDate").val();
	var planId = tacticsInfo.plan["planId"];
	var customGroupId = tacticsInfo.custGroup["customGroupId"];
	if(putDateStart==""){
		alert("开始日期不能为空");
		return ;
	}
	if(putDateEnd==""){
		alert("结束日期不能为空");
		return ;
	}
	if(putDateStart>putDateEnd){
		alert("开始日期不能大于结束日期");
		return;
	}
	var campInfo=new Object();
	if(tacticsInfo.camp!=null){
		//编辑需要传入策略ID
		if(tacticsInfo.camp.hasOwnProperty("campId")){
			campInfo.campId=tacticsInfo.camp.campId;
		}
	}
	campInfo.planId=planId;
	campInfo.campName=campName;
	campInfo.startDate=putDateStart;
	campInfo.endDate=putDateEnd;
	campInfo.custgroupId=customGroupId;
	campInfo.isApprove=isCommit;
	campInfo.planId=planId;
	campInfo.isFilterDisturb="0";//是否免打扰
	var dataObj=new Object();
	dataObj.channelsInfo=tacticsInfo.channels;
	dataObj.campInfo=campInfo;
	var dataStr=JSON.stringify(dataObj);
	$.ajax({
		url:contextPath+"/action/tactics/createTactics/saveOrUpdate.do",
		type:"POST",
		data:{"data":dataStr},
		success:function(result) {
			if(result.flag=="1"){
				$(dialog).dialog("close");
				var tacticsManagerUrl=contextPath+"/jsp/tactics/tacticsManage.jsp";
				window.location.href=tacticsManagerUrl;
			}else if(result.flag=="2"){
				alert("保存策略保存成功，但是提交审批失败");
			}
		},
		error:function (event) {
			alert("保存策略失败");
		}
	});
}
/**
 * 显示日期组件
 */
shopCarInfo.showDatePicker=function(){
	 var fromDate = $("#startDate" ).datepicker({changeMonth: true,numberOfMonths:1,dateFormat:"yy-mm-dd"}).on( "change", function(){
		 toDate.datepicker("option", "minDate", $.datepicker.parseDate("yy-mm-dd", this.value));
     });
	 fromDate.datepicker("option", "minDate", new Date());
     var toDate = $( "#endDate" ).datepicker({changeMonth: true,numberOfMonths:1,dateFormat:"yy-mm-dd"}).on( "change", function() {
    	 fromDate.datepicker( "option", "maxDate", $.datepicker.parseDate("yy-mm-dd", this.value));
     });
	 $.datepicker.dpDiv.addClass("ui-datepicker-box");
}


