var tacticsCustGroup = {
	queryCustGroup : function() {
		var ejsUrlPlanTypes=contextPath + '/assets/js/tactics/provinces/' + provinces + '/liCustGroup.ejs';
		
		var keyWords=$("#inputKeywordCustGroup").val();
		$.ajax({
			url:contextPath+"/tactics/tacticsManage/getMoreMyCustom",
			data:{"keyWords":keyWords},
			method:"POST",
			success:function(data, textStatus) {
				if (data) {
					var _HtmlCustGroup = new EJS({url:ejsUrlPlanTypes}).render({data:data});
					$("#ulCustGroupList").html(_HtmlCustGroup);
					// TODO 因为客户群查询接口暂时还不支持分页，所以分页展现功能先不做
					
					// 初始化客户群列表
					$("#ulCustGroupList li").click(function(event){
						var index = $("#ulCustGroupList li").index(this);
						var item=data[index];
						$("#ulCustGroupList li").removeClass("active");
						$(event.currentTarget).addClass("active");
						
						
						//派发事件
						$("#divFrameCustGroup").trigger("seleCustomerGroup",item);
					});
				} else {
					// 查询失败
				}
			},
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				// error happening, do nothing
			}
		});
	}
}