$(function(){
	initTable();
});

function initTable() {
	initTabInfo();
}
// 查询政策列表
function queryPolicy() {
	var keyword=$("#keyword").val();
	var typeId = $("#dimPlanType .J_type.active").attr("typeId");
	var planTypeId = $("#initGrade .J_type.active").attr("typeId")
	var channelTypeId = $("#channelType .J_type.active").attr("typeId");
	
	jQuery.ajax({
		url:_ctx+"/tacticsManage/queryPlansByCondition",
		data:{},
		success:function(data, textStatus) {
			
		}
	
	});
}
// 查询并初始化tab页筛选条件列表
function initTabInfo() {
	
	var ejsUrlPlanTypes=_ctx + '/mcd/pages/EJS/tacticsCreate/dimPlanTypes.ejs';
	
	jQuery.ajax({
		url:_ctx+"/tactics/tacticsManage/queryPlanTypes",
		data:{},
		success:function(data, textStatus) {
			if (data && data.code == "200") {
				var resultDara = data.data;
				var _Html = new EJS({
					url : ejsUrlDimPlanList
				}).render(resultDara);
				$("").html(_Html);
			} else {
				// 查询失败
			}
		}
	
	});
}

