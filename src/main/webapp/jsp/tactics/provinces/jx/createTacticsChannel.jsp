<%@ page contentType="text/html; charset=utf-8" %>
<!-- 中 -->
		<div id="channelDiv" class="put-center w-818 fleft" style="display:none">
			<!--step3***************************st-->
			<div class="step-title color-333 ft18">选渠道</div>
			<div class="bg-fff">
				<!--渠道列表-->
				<ul id="channelList" class="content-type-outer-box clearfix">
				</ul>
				<!--tab2-->
				<div class="trench-header bg-ebf0f3">
					<ul class="nav nav-tabs nav-tabs-public ft14 inline-block" role="tablist">
					   	<li role="presentation" class="active">
					   		<a href="#trench-11" aria-controls="trench-11" role="tab" data-toggle="tab">短信</a>
					  	</li>
					   	<li role="presentation">
					   		<a href="#trench-21" aria-controls="trench-21" role="tab" data-toggle="tab">营业厅</a>
					   	</li>
					   	<li role="presentation">
					   		<a href="#trench-31" aria-controls="trench-31" role="tab" data-toggle="tab">短信夹带</a>
					   	</li>
					</ul>
				</div>
				<!--tab2 tab-content -->
				<div class="tab-content trench-tab-content padding-15">
					<!-- 短信 -->
					<div role="tabpanel" id="trench-11"  class="tab-pane active ">
						<div class="left-right-box">
							<span class="color-333 left ft14">短信推荐用语：</span>
							<div class="right text-area-box">
								<textarea></textarea>
								<p class="ft12 color-999 text-mes">您还可以输入<span>240</span>个字</p>
							</div>
						</div>
						<div class="left-right-box">
							<span class="color-333 left">营销用语替换变量：</span>
							<div class="right ft12">
								<span class="border-item">提醒目标用户号码</span>
								<span class="border-item">对端号码长号</span>
								<span class="border-item">对端号码短号</span>
								<span class="border-item active">提醒目标用户号码</span>
								<span class="see-icon">查看替换效果</span>
							</div>
						</div>
						<div class="left-right-box">
							<span class="color-333 left">发送周期：</span>
							<div class="btn-group btn-group-ft12">
							  <button type="button" class="btn  active">一次性</button>
							 	<button type="button" class="btn">周期性</button>
							</div>
						</div>
						<div class="btns-wrp over-hidden">
							<p class="fright">
								<a href="javascript:;" class="mright_10 btn60-26 btn-a btn-a-blue">确定</a>
								<a href="javascript:;" class="btn60-26 btn-a btn-a-white">预览</a>
							</p>
							
						</div>
					</div>
					<!-- 营业厅 -->
					<div role="tabpanel" id="trench-21" class="tab-pane">
						营业厅营业厅营业厅营业厅
					</div>
					<!-- 短信夹带 -->
					<div role="tabpanel" id="trench-31" class="tab-pane">
						短信夹带短信夹带短信夹带
					</div>
				</div>
			</div>
			<!--step2***************************end-->
		</div>