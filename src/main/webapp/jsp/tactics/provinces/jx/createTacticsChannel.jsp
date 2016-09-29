<%@ page contentType="text/html; charset=utf-8" %>
<div id="channelDiv" class="put-center w-818 fleft" style="display:none">
	<div class="step-title color-333 ft18">选渠道</div>
	<div class="bg-fff">
		<!--渠道列表-->
		<ul id="channelList" class="content-type-outer-box public-border-ul clearfix">
		</ul>
		
		<!-- 展示已选渠道的div -->
		<div class="trench-header bg-ebf0f3" id="selectedChannelsDisplayDiv" style="display:none">
			<ul class="nav nav-tabs nav-tabs-public ft14 inline-block" id="selectedChannelsDisplayUl" role="tablist">
				<script id="channelTabTemp" type="text/ejs">
					<li role="presentation" class="active">
						<a href="#href-channelId_[%=data.channelId%]" role="tab" data-toggle="tab">[%=data.channelName%]</a>
						<i class="close">&times;</i>
						</li>
				</script>
			</ul>
		</div>
		
		<!-- 展示已选的渠道内容 -->
		<div class="tab-content trench-tab-content padding-15" id="selectedChannelsContentDisplayDiv">
		</div>
	</div>
</div>