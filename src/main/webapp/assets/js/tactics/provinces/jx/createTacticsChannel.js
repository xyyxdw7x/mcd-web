var channelFrame={
		queryChannel:function(data) {
			// TODO 根据策略查询渠道列表，并拼装ejs模板展示成多个tab
			// 查询该策略下对应哪些渠道
			$.ajax({
				url:contextPath+"/tactics/tacticsManage/selectPlanBackChannels",
				data:{"planId":data.PLAN_ID},
				success:function(data, textStatus) {
					if(data) {
						// TODO 根据查到的策略下渠道类型列表，调整渠道子页面的显示内容，尚未完成
						this.showTabs(data);
					}
				}
			});
		},
		saveChannel:function(channelId) {
			// TODO 更新channel信息
		},
		showTabs:function(channels) {
			
		}
}