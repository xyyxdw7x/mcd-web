define(function(require, exports, module){
	require("xdate");
	require("purl");
	require("page");
	require("toast");
	require("modal");
	
	exports.init=function(){
		$(document).ready(function(){
			//绑定事件
			tacticsInfo.addEventListenter();
			tacticsInfo.initView();
		});
	}
});
/**
 * 全局变量信息  camp策略信息  plan产品信息 custGroup客户群信息 channels渠道信息 为数组
 */
var tacticsInfo={camp:null,plan:null,custGroup:null,channels:null,isEdit:false};

/**
 * 页面元素进行统一的绑定事件入口
 */
tacticsInfo.addEventListenter=function(){
	tacticsInfo.addStepNumEventListenter();
	tacticsInfo.addNextBtnEventListenter();
	tacticsInfo.addChangePlanEvent();
	tacticsInfo.addChangeCustomerGroupEvent();
	tacticsInfo.addChangeChannelEvent();
}
/**
 * 初始化各个子页面
 */
tacticsInfo.initView=function(){
	//初始化筛选条件
	planInfo.initPlan();
	//初始化客户群信息
	customerGroupInfo.initCustomerGroup();
	//初始化渠道
	channelInfo.initChannel();
	//初始化购物车
	shopCarInfo.initShopCar();
	//判断是否是编辑页面
	var isEdit=$.url().param("isEdit");
	if(isEdit=="1"){
		var campId=$.url().param("campId");;
		tacticsInfo.isEdit=true;
		tacticsInfo.queryCampInfoById(campId);
	}
}
/**
 * 左侧数字导航事件
 */
tacticsInfo.addStepNumEventListenter=function(){
	$("#stepOl li").click(function(event){
		var selectedIndex=parseInt($(event.target).html(),10);
		if(isNaN(selectedIndex)){
			return ;
		}
		if(selectedIndex==2&&tacticsInfo.plan==null){
			alert("请选择产品");
			return ;
		}
		if(selectedIndex==3&&(tacticsInfo.plan==null)){
			alert("请选择产品");
			return ;
		}
		if(selectedIndex==3&&(tacticsInfo.custGroup==null)){
			alert("请选择客户群");
			return ;
		}
		//动态切换数据的样式
		$("#stepOl li").removeClass("active");
		$(event.target).parent().addClass("active");
		//动态切换div
		var divIds=["planDiv","cgDiv","channelDiv"];
		for(var i=0;i<divIds.length;i++){
			var divObj=$("#"+divIds[i]);
			if(i==(selectedIndex-1)){
				divObj.show();
			}else{
				divObj.hide();
			}
		}
		if(selectedIndex==1||selectedIndex==2){
			$("#nextBtn").show();
		}else{
			$("#nextBtn").hide();
		}
	});
}
/**
 * 下一步按钮数字导航
 */
tacticsInfo.addNextBtnEventListenter=function(){
	$("#nextBtn").click(function(event){
		//找到当前的位置  如果是3则切换到1 
		var selectedIndex=parseInt($("#stepOl .active").find("i").html(),10);
		var nextIndex=selectedIndex+1;
		if(nextIndex>3){
			$("#nextBtn").hide();
		}
		//找到相应的li并触发点击事件 要保证事件的入口唯一
		$("#stepOl li :contains('"+nextIndex+"')").trigger("click");
	});
}

/**
 * 注册产品发生变化事件
 */
tacticsInfo.addChangePlanEvent=function(){
	$("#planDiv").bind("changePlan",function(event,data){
		tacticsInfo.plan=data;
		//data为产品的所有信息
		//派发事件
		$("#shopCar").trigger("shopCarChangePlan",data);
		$("#channelDiv").trigger("addPlanChange",data);
	});
}

/**
 *  注册客户群发生变化事件
 */
tacticsInfo.addChangeCustomerGroupEvent=function(){
	$("#cgDiv").bind("changeCustomerGroup",function(event,data){
		//data为客户群的所有信息
		//派发事件
		$("#shopCar").trigger("shopCarChangeCustomerGroup",data);
		$("#channelDiv").trigger("changeCustomerGroup",data);
		tacticsInfo.custGroup=data;
	});
}
/**
 * 渠道发生变化
 * @param event
 * @param data
 */
tacticsInfo.addChangeChannelEvent=function(){
	//派发事件
	$("#channelDiv").bind("changeChannel",function(event,data){
		//派发客户群变化事件，短信渠道需要相应变化变量
		$("#shopCar").trigger("shopCarChangeChannel",data);
		//channels为数组
		if(tacticsInfo.channels==null){
			var channelsArr = new Array(data);
			tacticsInfo.channels=channelsArr;
		}else{
			//取消渠道选择
			for(var i=0;i<tacticsInfo.channels.length;i++){
				var channelItem=tacticsInfo.channels[i];
				if(channelItem.channelId==data.channelId){
					tacticsInfo.channels.splice(i,1);
				}
			}
			if(!(data.hasOwnProperty('isCancell')&&data["isCancell"]=="1")){
				tacticsInfo.channels.push(data);
			}
		}
	});
}

/**
 * 根据策略ID查询策略详情
 * @param campId
 */
tacticsInfo.queryCampInfoById=function(campId){
	if(campId==null||campId==undefined){
		alert("URL参数错误");
		return ;
	}
	var url=contextPath+"/action/tactics/createTactics/getCampInfo.do";
	var data={pid:campId};
	$.post(url,data,tacticsInfo.queryCampInfoByIdSuc);
}
/**
 * 根据策略ID查询策略详情成功
 * @param result
 */
tacticsInfo.queryCampInfoByIdSuc=function(result){
	if(result==null){
		alert("查询策略信息失败");
		return ;
	}
	tacticsInfo.camp=result.campInfo;
	tacticsInfo.plan=result.planInfo;
	tacticsInfo.custGroup=result.custGroupInfo;
	tacticsInfo.channels=result.channelsInfo;
	//派发产品事件
	if(tacticsInfo.plan!=undefined){
		$("#planDiv").trigger("addPlan",tacticsInfo.plan);
	}
	//派发客户群事件
	if(tacticsInfo.custGroup!=undefined){
		$("#cgDiv").trigger("addCustomerGroup",tacticsInfo.custGroup);
	}
	//派发渠道事件
	if(tacticsInfo.channels!=undefined){
		if(tacticsInfo.channels.length>0){
			$("#channelDiv").trigger("addChannles",[tacticsInfo.channels]);
		}
	}
}
