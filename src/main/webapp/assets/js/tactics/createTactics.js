
/**
 * 测试页面框架js
 */
$(document).ready(function(){
	//绑定事件
	addEventListenter();
    initView();
});
/**
 * 全局变量信息  plan产品信息 custGroup客户群信息 channels渠道信息 为数组
 */
var tacticsInfo={plan:null,custGroup:null,channels:null};


/**
 * 页面元素进行统一的绑定事件入口
 */
function addEventListenter(){
	addStepNumEventListenter();
	addNextBtnEventListenter();
	addChangePlanEvent();
	addChangeCustomerGroupEvent();
	addChangeChannelEvent();
}
/**
 * 初始化各个子页面
 */
function initView(){
	// 初始化筛选条件
	planInfo.initPlan();
	//调用子页面的函数
	initCustomerGroup();
	shopCarInfo.initShopCar();
	//初始化渠道
	initChannel();
}
/**
 * 左侧数字导航事件
 */
function addStepNumEventListenter(){
	$("#stepOl li").click(function(event){
		var selectedIndex=parseInt($(event.target).html(),10);
		if(isNaN(selectedIndex)){
			return ;
		}
		if(selectedIndex==2&&tacticsInfo.plan==null){
			alert("请选择政策");
			return ;
		}
		if(selectedIndex==3&&tacticsInfo.custGroup==null){
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
	});
}
/**
 * 下一步按钮数字导航
 */
function addNextBtnEventListenter(){
	$("#nextBtn").click(function(event){
		//找到当前的位置  如果是3则切换到1 
		var selectedIndex=parseInt($("#stepOl .active").find("i").html(),10);
		var nextIndex=selectedIndex+1;
		if(nextIndex>3){
			nextIndex=1;
		}
		//找到相应的li并触发点击事件 要保证事件的入口唯一
		$("#stepOl li :contains('"+nextIndex+"')").trigger("click");
	});
}

/**
 * 注册产品发生变化事件
 */
function addChangePlanEvent(){
	$("#planDiv").bind("changePlan",changePlanEvent);
}


/**
 * 产品发生变化事件
 * @param event
 * @param data
 */
function changePlanEvent(event,data){
	tacticsInfo.plan=data;
	//data为产品的所有信息
	//派发事件
	$("#shopCar").trigger("shopCarChangePlan",data);
	$("#channelDiv").trigger("getPlanChange",data);
}
/**
 *  注册客户群发生变化事件
 */
function addChangeCustomerGroupEvent(){
	$("#cgDiv").bind("changeCustomerGroup",changeCustomerGroupEvent);
}
/**
 * 客户群发生变化事件
 * @param event
 * @param data
 */
function changeCustomerGroupEvent(event,data){
	//data为客户群的所有信息
	//派发事件
	$("#shopCar").trigger("shopCarChangeCustomerGroup",data);
	tacticsInfo.custGroup=data;
}
/**
 * 渠道发生变化
 * @param event
 * @param data
 */
function addChangeChannelEvent(){
	//派发事件
	$("#channelDiv").bind("changeChannel",changeChannelEvent);
}

function changeChannelEvent(event,data){
	//派发客户群变化事件，短信渠道需要相应变化变量
	$("#shopCar").trigger("shopCarChangeChannel",data);
	//channels为数组
	if(tacticsInfo.channels==null){
		var channelsArr = new Array(data); 
		tacticsInfo.channels=channelsArr;
	}else{
		//取消渠道选择
		if(data.hasOwnProperty('isCancell')&&data["isCancell"]=="1"){
			for(var i=0;i<tacticsInfo.channels.length;i++){
				var channelItem=tacticsInfo.channels[i];
				if(channelItem.channelId==data.channelId){
					tacticsInfo.channels.remove(channelItem);
				}
			}
		}else{
			tacticsInfo.channels.push(data);
		}
	}
}




