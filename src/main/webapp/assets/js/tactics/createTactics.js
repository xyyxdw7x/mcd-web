
/**
 * 测试页面框架js
 */
$(document).ready(function(){
	//绑定事件
	addEventListenter();
    initView();
    // 初始化筛选条件
	TacticsPlan.initTabInfo();
	// 初始化查询结果
	TacticsPlan.queryPolicy(1);
	//tacticsCustGroup.queryCustGroup();
});


/**
 * 页面元素进行统一的绑定事件入口
 */
function addEventListenter(){
	addStepNumEventListenter();
	addNextBtnEventListenter();
	addChangePlanEvent();
	addChangeCustomerGroupEvent();
}
/**
 * 初始化各个子页面
 */
function initView(){
	$('.public-table tbody tr:nth-child(2n+1)').addClass('odd');
	$('.public-table tbody tr:nth-child(2n)').addClass('even');
	//调用子页面的函数
	initCustomerGroup();
	initShopCar();
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
	//data为产品的所有信息
	//派发事件
	$("#shopCar").trigger("shopCarChangePlan",data);
}
/**
 *  注册客户群发生变化事件
 */
function addChangeCustomerGroupEvent(){
	$("#cgDiv").bind("changeCustomerGroup",changeCustomerGroupEvent);
}
/**
 * 渠道发生变化事件
 * @param event
 * @param data
 */
function changeCustomerGroupEvent(event,data){
	//data为客户群的所有信息
	//派发事件
	$("#shopCar").trigger("shopCarChangeCustomerGroup",data);
	//
	$("#shopCar").trigger("channelSMSChangeCustomerGroup",data);
}


function initPage() {
	// 初始化筛选条件
	TacticsPlan.initTabInfo();
	// 初始化查询结果
	TacticsPlan.queryPolicy(1);
	tacticsCustGroup.queryCustGroup();
}

/**
 * 监听各个子页面冒泡上来的跨页面事件
 */
function addEventListenter2(){
	addNavigationClickListener();
	addPlanChangeListener();
	addCustGroupChangeListener();
	addChannelChangeListener();
	
	// 初始化产品类型点击事件
	$("#divDimPlanSrvType > span").on("click", function(){
		var $target = $(this);
		$target.addClass("active").siblings().removeClass("active");
		queryPlan(1);
	});
	$("#btnSearchCustGroup").on("click", tacticsCustGroup.queryCustGroup());
}

/**
 * 监听政策变化事件
 */
function addPlanChangeListener() {
	$("#divFramePlan").bind("planChange",function(event,data) {
		// 当政策变化时，更新购物车的政策信息
		shoppingCart.changePlan(event,data);
		
		
	});
	
	$("#divFramePlan").bind("planCancel",function(event,data) {
		// 当政策变化时，更新购物车的政策信息
		shoppingCart.cancelPlan(event,data);
	});
}

/**
 * 监听客户群变化事件
 */
function addCustGroupChangeListener() {
	$("#divFrameCustGroup").bind("custGroupChange",function(event,data){
		// 当客户群变化时，更新购物车的客户群信息
		shoppingCart.changeCustGroup(event,data);
	});
	
	$("#divFrameCustGroup").bind("custGroupCancel",function(event,data){
		// 当客户群变化时，更新购物车的客户群信息
		shoppingCart.cancelCustGroup(event,data);
	});
}

/**
 * 监听渠道变化事件
 */
function addChannelChangeListener() {
	$("#divFrameChannel").bind("channelChange",function(event,data){
		// 当渠道变化时，更新购物车的渠道信息
		shoppingCart.changeChannel(event,data);
	});
}

/**
 * 左侧数字导航事件
 */
function addNavigationClickListener(){
	$("#stepOl li").click(function(event){
		var selectedIndex=parseInt($(event.target).html(),10);
		if(isNaN(selectedIndex)){
			return ;
		}
		//动态切换数据的样式
		$("#stepOl li").removeClass("active");
		$(event.target).parent().addClass("active");
		//动态切换div
		var divIds=["divFramePlan","divFrameCustGroup","divFrameChannel"];
		for(var i=0;i<divIds.length;i++){
			var divObj=$("#"+divIds[i]);
			if(i==(selectedIndex-1)){
				divObj.show();
			}else{
				divObj.hide();
			}
		}
	});
	
	$(".btn-nextstep").click(function(event){
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

