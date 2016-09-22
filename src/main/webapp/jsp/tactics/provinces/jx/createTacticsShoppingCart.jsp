<%@ page language="java" pageEncoding="UTF-8"%>
<div id="shopCar" class="put-right fright bg-fff">
	<div class="top-hd">工作站</div>
	<div class="btn-box bg-ebf0f3" style="display:none">
		<div class="dropdown caret-drop">
			<button class="caret-btn dropdown-toggle" type="button"
				id="dropdownMenu1" data-toggle="dropdown">
				<span class="caret"></span>
			</button>
			<ul class="caret-box dropdown-menu" style="display: block;"
				role="menu" aria-labelledby="dropdownMenu1">
				<li role="presentation" class="caret-edit"><a role="menuitem"
					tabindex="-1" class="active" href="javascript:;"></a></li>
				<li role="presentation" class="caret-save"><a role="menuitem"
					tabindex="-1" href="javascript:;"></a></li>
			</ul>
		</div>
		<span class="del-icon delete"></span>
	</div>
	<div class="info-box">
		<h5>营销政策</h5>
		<ul id="selectedPlan">
		</ul>
	</div>
	<div class="info-box">
		<h5>适配客户群</h5>
		<div class="custom">
			<span id="selectedCg" class="color-333 mright_10"></span>
		</div>
	</div>
	<div class="info-box">
		<h5>适配渠道</h5>
		<div class="custom">
		<ul id="selectedChangeChannelUl">
			<li>
			<p class="ft14">短信</p>
				<div>
					<p>
						<span class="color-666">短信触发时机：</span><em class="color-333">***********</em>
					</p>
					<p>
						<span class="color-666">营销用语替换变量：</span><em class="color-333">***********
							*********** *********** *********** </em>
					</p>
					<p>
						<span class="color-666">发送周期：</span><em class="color-333">周期性</em>
					</p>
					<p>
						<span class="color-666">派发时间：</span><em class="color-333">从2016-08开始，每个月1号
						</em>
					</p>
					<p>
						<span class="color-666">频次控制：</span><em class="color-333">***********
						</em>
					</p>
					<p>
						<span class="color-666">免打扰用户：</span><em class="color-333">主动要求屏蔽客户；敏感投诉客户；黑名单******</em>
					</p>
					<p>
						<span class="color-666 block">群发用语：</span><em class="color-333">***************************************************************</em>
					</p>
				</div>
				<hr />
			</li>
			<li>
				<p class="ft14">BOSS运营位</p>
				<p>
					<span class="color-333 mright_10">***********</span><span
						class="color-333 mright_10">***********</span><span
						class="color-333 mright_10">***********</span>
				</p>
				<hr />
			</li>
		</ul>

		</div>
	</div>
	<!--保存按钮-->
	<div class="btn-wrp  btn-wrp10 text-center bg-ebf0f3">
		<a href="javascript:;" class="btn140-40" id="saveTacticsId">保存</a>
	</div>
	
<!-- 新建策略-保存弹窗 -->
<div class="save-dialog-box container"  >
	<div class="own-dialog save-dialog">
		<!-- 内容区 -->
		<form class="form-horizontal">
			<div class="form-group">
			    <label class="col-sm-4 control-label"><i class="color-red">*</i><span>策略名称:</span></label>
			    <div class="col-sm-6">
			      	<input type="text" class="form-control" id="tacticsName">
			    </div>
		    </div> 
			<div class="form-group form-group-new">
			    <label class="col-sm-4 control-label"><i class="color-red">*</i><span>投放周期：</span></label>
			    <div class="col-sm-3">
			      	<input type="text" id="startDate" class="form-control">
			    </div>
			    <div class="col-sm-3">
			    	<span class="color-999 fleft">-</span>
			      	<input type="text" id="endDate" class="form-control">
			    </div>
		    </div> 
		    <div class="form-group">
			    <label class="col-sm-4 control-label">营销类型：</label>
			    <div class="col-sm-6">
				    <div class="input-group">
				      <input type="text" class="form-control">
				      <div class="input-group-btn">
				        <button type="button" class="btn btn-default btn-default-new dropdown-toggle" data-toggle="dropdown">
				        	<span class="caret"></span>
				        </button>
				      </div>
				    </div>
			    </div>
		    </div> 
		</form>
	</div><!--dialog end-->
	<!-- <div class="ui-widget-overlay ui-front" style="z-index: 100;"></div> --><!--半透明背景-->
</div>