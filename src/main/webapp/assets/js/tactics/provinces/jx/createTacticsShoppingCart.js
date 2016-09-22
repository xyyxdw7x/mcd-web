
/**
 * 暂存架页面初始化
 */
function initShopCar(){
	addShopCarChangePlanEvent();
	addShopCarChangeCustomerGroupEvent();
	saveTactics();
	addShopCarChangeChannelEvent();
}

/**
 * 保存策略
 */
function saveTactics(){
	$("#saveTacticsId").click(function() {
		$(".save-dialog-box").show();
  	    $(".ui-widget-overlay").show();
		initDialog();
		showDatepicker();
	});
}

function initDialog(){
	$(".save-dialog").dialog({
		width:600,
		height:245,
		resizable: false,
    modal: true,
    title:"保存",
    buttons: [{
        "class":"dialog-btn dialog-btn-blue",
        "text":"保存",
        "click": function() {
        	var _campName=$("#tacticsName").val();
			var _putDateStart=$("#startDate").val();
			var _putDateEnd=$("#endDate").val();
        	var _planid = tacticsInfo.plan.PLAN_ID;
        	var _planname = tacticsInfo.plan.PLAN_Name;
        	var customGroupId = tacticsInfo.custGroup.customGroupId;
        	if(_putDateStart>_putDateEnd){
				ts.attr("ifclose","false");
				alert("开始日期不能大于结束日期");
				return;
			}
        	
        	var _ajaxData="{";
        	//遍历规则1 规则2...提取所有属性
        	_ajaxData+='"rule0": {';
        	_ajaxData+='"channelIds": "901",';
        	_ajaxData+='"execContent":[';
        	_ajaxData+='{"channelId":"901","planName":"'+_planname+'","adivId":"1"'+',"execContent":"短信内容",'+'"isHasVar":"true"}';
        	_ajaxData+='],';
        	_ajaxData+='"isFilterDisturb": 1';
        	_ajaxData+='},';
        	_ajaxData+='"commonAttr": {';
        	_ajaxData+='"planId": "'+_planid+'",';
        	_ajaxData+='"campName": "'+_campName+'",';
        	_ajaxData+='"startDate": "'+_putDateStart+'",';
        	_ajaxData+= '"endDate": "'+_putDateEnd+'",';
        	_ajaxData+='"isApprove": "false",';
        	_ajaxData+='"isFilterDisturb": "0",';
        	_ajaxData+='"custgroupId": "'+customGroupId+'"';
        	_ajaxData+='}';
        	_ajaxData+='}';
        	
        	$.ajax({
        		url:contextPath+"/tactics/tacticsManage/saveOrUpdate.do",
        		data:{"ruleList":_ajaxData},
        		success:function(result) {
        			if(result=="0"){
        				alert("保存成功");
        				$(".save-dialog").dialog( "close" );
        			}
        		},
        		error:function (XMLHttpRequest, textStatus, errorThrown) {
        			alert("保存失败");
        		}
        	});
            $(".ui-widget-overlay").hide();
        	}
    	},{
        "class":"dialog-btn dialog-btn-orange",
        "text":"提交审批",
        "click": function() {
            	$( this ).dialog("close" );
                $(".ui-widget-overlay").hide();
        	}
    	}]
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



/**
 *  注册产品变化事件
 */
function addShopCarChangePlanEvent(){
	$("#shopCar").bind("shopCarChangePlan",shopCarChangePlanEvent);
}

/**
 * 产品变化事件
 * @param event
 * @param data
 */
function shopCarChangePlanEvent(event,data){
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
}

/**
 *  注册客户群变化事件
 */
function addShopCarChangeCustomerGroupEvent(){
	$("#shopCar").bind("shopCarChangeCustomerGroup",shopCarChangeCustomerGroupEvent);
}

/**
 * 客户群变化事件
 * @param event
 * @param data
 */
function shopCarChangeCustomerGroupEvent(event,data){
	if(data==null){
		$("#selectedCg").html("");
		$("#selectedCg").data("data",null);
	}
	//设置客户群名称
	$("#selectedCg").html(data.customGroupName);
	//将数据绑定到dom元素上
	$("#selectedCg").data("data",data);
}


/**
 *  注册渠道变化事件
 */
function addShopCarChangeChannelEvent(){
	$("#shopCar").bind("shopCarChangeChannel",shopCarChangeChannelEvent);
}

/**
 * 渠道变化事件
 * @param event
 * @param data
 */
function shopCarChangeChannelEvent(event,data){
	$("#selectedChannels").html("");
	if(data==null||data== undefined){
		return ;
	}
	//将数据绑定到dom元素上
	$("#selectedChannels").data("data",data);
}