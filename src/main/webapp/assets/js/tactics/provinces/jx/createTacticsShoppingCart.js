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
			return ;
		}
		var planId=data.PLAN_ID;
		//设置产品名称
		var liStr="<li id='selectedPlan_"+planId+"' shopCarPlanId='"+planId+"'><span>"+data.PLAN_NAME+"</span></li>";
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
		alert(JSON.stringify(data));
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
		var channelHtmlStr="<li id='selectedChannel_"+channelId+"'><p class='ft14'>"+channelName+"</p><div>"+channelColumns+"</div><hr/></li>";
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
		showDatepicker();
		$("#saveDialog").dialog({
			width:600,
			height:300,
			resizable: false,
			modal: true,
			title:"保存",
			buttons: [saveBtnInfo,commitBtnInfo]
		});
	});
}
/**
 * 保存或提交审批
 * @param dialog
 * @param isCommit 是否提交审批
 */
shopCarInfo.saveOrCommitTactics=function(dialog,isCommit){
	var campName=$("#tacticsName").val();
	var putDateStart=$("#startDate").val();
	var putDateEnd=$("#endDate").val();
	var planId = tacticsInfo.plan["PLAN_ID"];
	var customGroupId = tacticsInfo.custGroup["customGroupId"];
	if(putDateStart>putDateEnd){
		alert("开始日期不能大于结束日期");
		return;
	}
	var campInfo=new Object();
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
		url:contextPath+"/tactics/tacticsManage/saveOrUpdate.do",
		type:"POST",
		data:{"data":dataStr},
		success:function(result) {
			if(result=="0"){
				alert("保存策略成功");
				$(dialog).dialog("close");
			}else{
				alert("保存策略失败");
			}
		},
		error:function (event) {
			alert("保存策略失败");
		}
	});
}


function showDatepicker(){
	 var  from = $( "#startDate" ).datepicker({
         changeMonth: true,
         numberOfMonths: 2,
         dateFormat:"yyyy-mm-dd"
       }).on( "change", function() {
         to.datepicker( "option", "minDate", getDate( this ) );
       }),
     to = $( "#endDate" ).datepicker({
       changeMonth: true,
       numberOfMonths: 2,
     dateFormat:"yy-mm-dd"
     }).on( "change", function() {
       from.datepicker( "option", "maxDate", getDate( this ) );
     });
	 $.datepicker.dpDiv.addClass("ui-datepicker-box");
}

function getDate( element ) {
    var date;
    try {
      date = $.datepicker.parseDate( "yy-mm-dd", element.value );
    } catch( error ) {
      date = null;
    }
    return date;
  }


