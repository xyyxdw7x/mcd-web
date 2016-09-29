<%@ page contentType="text/html; charset=utf-8" %>
<div id="channelDiv" class="put-center w-818 fleft" style="display:none">
	<div class="step-title color-333 ft18">选渠道</div>
	<div class="bg-fff">
		<!--渠道列表-->
		<ul id="channelList" class="content-type-outer-box public-border-ul clearfix">
		</ul>
		<!-- 展示已选渠道的div -->
		<div class="trench-header bg-ebf0f3" id="selectedChannelsDisplayDiv" style="display:none">
			<ul id="selectedChannelsDisplayUl" class="nav nav-tabs nav-tabs-public ft14 inline-block"  role="tablist">
				<script id="channelTabTemp" type="text/ejs">
					<li id="channelTabDiv_[%=data.channelId%]" role="presentation" class="active">
						<a id="channelTab_[%=data.channelId%]" href="#channelContentDiv_[%=data.channelId%]" role="tab" data-toggle="tab">[%=data.channelName%]</a>
						<i id="channelClose_[%=data.channelId%]" class="close">&times;</i>
					</li>
				</script>
			</ul>
		</div>
		<!-- 展示已选的渠道内容 -->
		<div id="selectedChannelsContentDisplayDiv" class="tab-content trench-tab-content padding-15" >
		</div>
	</div>
</div>