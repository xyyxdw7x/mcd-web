<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
	_ctx = "<%=request.getContextPath()%>";
</script>

<div class="header" id="headerDiv" >
	<div class="inner">
		<div class="logo">
			<img src="<%=request.getContextPath()%>/mcd/assets/images/logos/logo.png" />
		</div>
		<ul class="loginfo">
			<li>
				<a href="javascript(void:0)" >导航地图<span class="icon_arrDown"></span></a>
			</li>
			<li>
				<a href=""><span class="J_userInfo"></span><span class="icon_arrDown"></span></a>
				<div class="slidedownCT">
					<div class="userImg"><img src="<%=request.getContextPath()%>/mcd/assets/images/temp/administrator.png"></div>
					<dl class="userinfo">
						<dt class="J_userInfo"></dt>
						<dd class="J_userDept"></dd>
						<input type="hidden" class="J_userId"/>
						<input type="hidden" class="J_userCityId"/>
						<input type="hidden" class="J_userCityName"/>
						<input type="hidden" class="J_userSex"/>
						<input type="hidden" class="J_userType"/>
						<dd>
							<span class="icon_activeuser"></span>
							<div class="activeuser">活跃用户</div>
						</dd>
					</dl>
					<ul class="logout">
						<li><a href="#">修改密码</a></li>
						<li><a href="#">个人中心</a></li>
						<li ><a href="<%=request.getContextPath()%>/mpm/imcdUser.aido?cmd=userLoginOut" onclick="if(!confirm('是否要退出？')) return false;">退出</a></li>
					</ul>
				</div>
			</li>
			<li>
				<a href="javascript(void:0)">我的收藏<span class="icon_arrDown"></span></a>
				<div class="slidedownCT">
					<dl class="newfavList">
						<dt>最新加入收藏的</dt>
						<dd><a href="javascript:void(0)">取消</a>数据流量272元VIP客户升档营销</dd>
						<dd><a href="javascript:void(0)">取消</a>数据流量272元VIP客户升档营销</dd>
						<dd><a href="javascript:void(0)">取消</a>数据流量280元VIP客户升档营销</dd>
						<dd><a href="javascript:void(0)">取消</a>数据流量280元VIP客户升档营销</dd>
						<dd><a href="javascript:void(0)">取消</a>数据流量280元VIP客户升档营销</dd>
						<dd><a href="javascript:void(0)">取消</a>数据流量280元VIP客户升档营销</dd>
					</dl>
					<div class="gotoFav"><input type="button" class="btn_blue" value="去收藏夹"></div>
				</div>
			</li>
			<li>
				<a href="javascript(void:0)">消息<span class="icon_arrDown"></span></a>
				<div class="slidedownCT">
					<dl class="newfavList">
						<dt>未读新消息</dt>
						<dd><a href="javascript:void(0)">取消</a>消息名称消息名称</dd>
						<dd><a href="javascript:void(0)">取消</a>消息名称消息名称</dd>
						<dd><a href="javascript:void(0)">取消</a>消息名称消息名称</dd>
					</dl>
					<div class="gotoFav">
						<a href="javascript:void(0)" class="set">设置</a>
						<input type="button" class="btn_blue" value="查看历史消息">
					</div>
				</div>
			</li>
		</ul>
		<ul class="nav">
		</ul>
	</div>
	<div class="subnav" style="display: none;">
		<!-- 950px -->
		<div class="width950">
			<ul class="ul01"></ul>
			<ul class="ul02">
			</ul>
			<ul class="ul03"></ul>
			<ul class="ul04"></ul>
			<ul class="ul05"></ul>
		</div>
	</div>
</div><!--header end -->
