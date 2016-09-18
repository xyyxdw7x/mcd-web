var shoppingCart={
		changePlan:function(event,data) {
			// 目前不支持多选
			$("#ulShoppingCartPlanList li").remove();
			var li=$("<li planId=" + data.PLAN_ID + "><span>.</span>" + data.PLAN_NAME + "</li>");
			$("#ulShoppingCartPlanList").append(li);
		},
		cancelPlan:function(event,data) {
			$("#ulShoppingCartPlanList li[planId=" + data.PLAN_ID + "]").remove();
		},
		changeCustGroup:function(event, data) {
			// 目前不支持多选
			$("#divShoppingCartCustGroupList span").remove();
			var span=$("<span class=\"color-333 mright_10\" custGroupId=" + data.customGroupId + ">" + data.customGroupName + "</span>");
			$("#divShoppingCartCustGroupList").append(span);
		},
		cancelCustGroup:function(event, data) {
			$("#divShoppingCartCustGroupList span[custGroupId=" + data.customGroupId + "]").remove();
		},
		changeChannel:function(event, data) {
			// TODO 更新购物车的已选渠道列表
		}
}

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

