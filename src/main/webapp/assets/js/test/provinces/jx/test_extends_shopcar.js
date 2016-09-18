/**
 * 暂存架页面初始化
 */
function initShopCar(){
	addShopCarChangeCustomerGroupEvent();
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
	if(data == null){
		//设置客户群名称
		$("#selectedCg").html("");
		//将数据绑定到dom元素上
		$("#selectedCg").data("data","");
	}else{
		//设置客户群名称
		$("#selectedCg").html(data.customGroupName);
		//将数据绑定到dom元素上
		$("#selectedCg").data("data",data);
	}

}

