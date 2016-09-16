/**
 * 测试页面框架js
 */
$(document).ready(function(){
	//绑定事件
	addEventListenter();
    initView();	
});

/**
 * 页面元素进行统一的绑定事件入口
 */
function addEventListenter(){
	addStepNumEventListenter();
	addNextBtnEventListenter();
}
/**
 * 初始化各个子页面
 */
function initView(){
	//调用子页面的函数
	initCustomerGroup();
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
