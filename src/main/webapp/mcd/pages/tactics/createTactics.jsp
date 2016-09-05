<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html class="box-html">
<head>
	<title>新建策略</title>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/mcd/assets/images/logos/favicon.ico" />
	<link rel="stylesheet/less" type="text/css" href="../../assets/styles/common.css" />
	<link href="${ctx}/mcd/assets/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/mcd/assets/styles/CEP/cep.main.bootstrap.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet/less" type="text/css" href="../../assets/styles/jqueryUI/jquery-ui-1.11.0.min.css" />
	<link href="../../assets/scripts/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
	<link href="../../assets/styles/style.css" rel="stylesheet"/>
	<link href="../../assets/styles/tactics/createTactics.css" rel="stylesheet" type="text/css">
	<link href="../../assets/styles//resetDialog-jqueryUI.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
</head>
<script type="text/javascript">
	_ctx = "<%=request.getContextPath()%>";
</script>
<jsp:include page="../header/header.jsp"></jsp:include>
<div class="container min-height">
    <!-- 最左边的滑动效果  beggin -->
	<div class="left-nav">
		<div class="left-nav-box left-nav-active">
			<span>选政策</span>
			<i class="fright left-nav-bg" data-num="1">1</i>
		</div>
		<div class="left-nav-line"></div>
		<div class="left-nav-box">
			<span>选客户</span>
			<i class="fright left-nav-bg" data-num="2">2</i>
		</div>
		<div class="left-nav-line"></div>
		<div class="left-nav-box">
			<span>选渠道</span>
			<i class="fright left-nav-bg" data-num="3">3</i>
		</div>
		<input id="automaticClick" value="自动填充" style="background-color:#f00; cursor:pointer; margin-top:300px; text-align:center; color:#fff; font-size:16px; padding:0; text-indent:0; width:100px; display:none;" />
	</div>
      <!-- 最左边的滑动效果  end -->
      
     <!-- 最右边的暂存架 beggin -->
	<div class="shop-cart J_shopCartContent">
		<div class="tempshelf"><span class="car"></span>暂存架</div>
		<div class="addStrategy">
			<div class="scrollToleft"><div></div></div>
			<div class="boxlist">
				<div class="strategy J_strategy" data-num="1">场景</div>
			</div>
			<span class="icon_add J_addRule" style="display:none;"></span>
			<div class="scrollToright"><div></div></div>
		</div>
		<div class="J_rule J_flag" data-num="1">
			<div class="box ">
				<h5>营销政策</h5>
				<div class="J_Policy_Cart"></div>
			</div>
			<div class="box">
				<h5>适配客户群</h5>
				<div id="J_cartGroup" class="J_cartGroup">
				</div>
			</div>
			<div class="box">
				<h5>适配渠道</h5>
				<div class="J_addChannelToCart">
				</div>
			</div>
		</div>
		<div class="totalNum" style="display: none;">已配置<i id="channelNumBox">1</i>个规则</div>
		<div class="saveBtnCT"><input type="button" id="saveCreateTactics" class="btn_save" value="保存" wait-click-times="0" wait-click-times-bc="0" wait-click-times-sp="0" /></div>
	</div><!--shop-cart end -->
	 <!-- 最右边的暂存架  end -->
	 
	 <!-- 中间部分：政策-客户-渠道  beggin -->
	<div class="box-container">
		<div class="content box-content width-849">
			<div class="content-title J_poilyTitleBox width-849 clearfix" id="poilyTitle">
				选政策
				<div class="ui-tactics-radio">
					<ul class="clearfix">
						<li>
							<label><input type="radio" checked="checked" name = "J_redio_tactics" class="J_redio_tactics" value="0"/>单选</label>
						</li>
						<li>
							<label><input type="radio" name = "J_redio_tactics" class="J_redio_tactics" value="1"/>多选</label>
						</li>
					</ul>
				</div>
			</div>
			<div class="content-main">
				<ul class="content-type-outer-box">
					<li class="content-type-search">
						<div class="content-type-search-box fleft">
							<p class="fleft">
								<input type="text" name="search" id ="sreachPlain" placeholder="请输入关键字"/>
							</p>
							<i class="searchBtn fright J_sreachPlain"></i>
						</div>
					</li>
					<li class="clearfix content-type-item">
						<div class="fleft content-type-tite">政策类别：</div>
						<div class="fleft content-type-list">
							<div class=" content-type-item-inner">
								<div id="dimPlanType" class="content-type-inner">
								</div>
							</div>
						</div>
					</li>
					<li class="clearfix content-type-item">
						<div class="fleft content-type-tite">政策粒度：</div>
						<div class="fleft content-type-list">
							<div class=" content-type-item-inner">
								<div class="content-type-inner" id="initGrade">
								</div>
							</div>
						</div>
					</li>
					<li class="clearfix content-type-item" style="height:56px">
						<div class="fleft content-type-tite">适用渠道：</div>
						<div class="fleft content-type-list" style="height:56px">
							<div class=" content-type-item-inner" style="height:56px">
								<div class="content-type-inner" style="height:56px" id="channelType" >
								</div>
							</div>
						</div>
					</li>
				</ul>
				<div class="content-table">
					<div class="content-sub-title">营销政策列表</div>
					<div class="content-table-box" id="createDimPlanList">
					</div>
					<div class="choosedDimPlan">
						<font>已选政策:</font>
						<span id="J_addPolicy">--</span>
					</div>
				</div>
			</div>
		</div>
		<div class="content box-content width-849">
			<div class="content-title " id="customerTitle">
				选客户
			</div>
			<div class="content-main">
				<div class="group">
					<div class="group-info">
						<div class="group-info-search">
							<div class="group-info-search-box fleft">
								<p class="fleft">
									<input type="text" name="search" id="sreachGroup" placeholder="客户群编码/客户群名称">
								</p>
								<i class="searchBtn fright J_sreachPlain" id="searchGroupIcon"></i>
							</div>
							<div class="group-info-newgroup">
								<a style="text-decoration: none;" target="_blank" href="<%=request.getContextPath() %>/mcd/pages/custom/addCustom.jsp?navId=7143&subNavId=714312"><span>+</span>
								<span>新建客户群</span></a>
							</div>
							<div class="group-page rFloat">
								<span class="group-page-span prev">&lt;</span>
								<span class="group-page-span next " style="border-left: none;">&gt;</span>
							</div>
						</div>
						<ul class="group-info-ul" id="groupInfo"></ul>
					</div>

					<div class="group-select">
						<div class="group-select-title">已选客户群：</div>
						<div class="group-select-info">
							<ul class="group-select-info-ul select clearfix" id="selectedConditiom" >
							</ul>
						</div>
						<ul class="group-select-value">
							<li class="J_firstGroupValue"><span>目标客户数量：</span><span class="num cus-num">--</span></li>
						</ul>
						<div class="group-select-submit" id="addGroupToCart" style="cursor:default;">
							<button type="button" class="calculate-customer-submit J_addGroup">确定</button>
						</div>
					</div>

				</div>


			</div>
		</div>
		<div class="content box-content width-849">
			<div class="content-title" id="channelTitle">
				选渠道
			</div>
			<div class="content-main" id="channelContentMain">
				<ul id="selectedChannel" class="content-type-outer-box selectChannel clearfix J_selectedChannel">
				</ul>
			</div>
		</div>
	</div>
	 <!-- 中间部分：政策-客户-渠道  end -->
</div>

<Br><br>

<!-- 保存策略的弹出层 beggin -->
<div id="saveCreateTacticsDialog">
	<dl class="savePreview">
		<dt>策略配置保存</dt>
		<dd id="shopcarSaveTable"></dd>
	</dl>

	<div class="cleanInfoCT">
		<div class="box">
			<h4>清洗详情</h4>
			<table>
				<tr>
					<th>清洗规则</th>
					<th>被清洗数量</th>
					<th>操作</th>
				</tr>
				<tr>
					<td>频次</td>
					<td>1,000</td>
					<td>预览</td>
				</tr>
				<tr>
					<td>黑名单</td>
					<td>1,500</td>
					<td>预览</td>
				</tr>
			</table>
		</div>
	</div>

	<table class="clchoose">
		<tr>
			<th><font class="red">*</font>策略名称:</th>
			<td><input id="typePolicyName" type="text"></td>
			<th><font class="red">*</font>执行周期:</th>
			<td>
				<div style="width:250px;">
					<input id="putDateStart" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})">
					-
					<input id="putDateEnd" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'putDateStart\',{d:1})}'})">
				</div>
			</td>
		</tr>
		<tr>
			<th id="saveCampsegTypeTh">营销类型:</th>
			<td><select id="saveCampsegType"></select></td>
			<th class='saveBlackList' style='display:none'>黑名单过滤:</th>
			<td class='saveBlackList' style='display:none'>
				<select id="saveBlackList">
					<option value="1" checked>是</option>
                    <option value="0">否</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="4" style="width:100%;font-size:12px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：1、短信群发端口号：提醒类、服务类、公益类策略为10086，营销类策略为：10658258<br>
    					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、黑名单过滤说明：短信渠道营销类策略，固定过滤黑名单，其他渠道可选
			</td>
		</tr>
	</table>

</div>
<!-- 保存策略的弹出层 end-->

<div id="indexBigAD" class="addQueryTactics">
	<div class="searchCT">
		<div class="content-type-search-box fright">
			<p class="fleft">
				<input type="text" name="search" id="productSelect" placeholder="请输入关键字"/>
			</p>
			<i class="searchBtn fright"></i>
		</div>
		<div class="indexBigADH4">请选择素材</div>
	</div>

	<div class="content-table">
		<div class="content-table-box">
			<table class="content-table">
				<tr>
					<th class="content-table-th" width="50">序号</th>
					<th class="content-table-th" width="90">名称</th>
					<th class="content-table-th" width="50">更新日期</th>
					<th class="content-table-th" width="">业务口径</th>
					<th class="content-table-th" width="120">用户数</th>
					<th class="content-table-th" width="120">数据日期</th>
					<th class="content-table-th" width="120">操作</th>
				</tr>
				<tr class="even content-table-tr">
					<td class="content-table-td">1</td>
					<td class="content-table-td">虚拟潜在用户（月）</td>
					<td class="content-table-td">月</td>
					<td class="content-table-td">通过分析挖掘方法，以交往圈中虚拟用户个数....</td>
					<td class="content-table-td">82,345</td>
					<td class="content-table-td">2015-06-30</td>
					<td class="content-table-td">
						<button type="button" class="table-button ">添加</button>
					</td>
				</tr>
				<tr class="odd content-table-tr">
					<td class="content-table-td">1</td>
					<td class="content-table-td">虚拟潜在用户（月）</td>
					<td class="content-table-td">月</td>
					<td class="content-table-td">通过分析挖掘方法，以交往圈中虚拟用户个数....</td>
					<td class="content-table-td">82,345</td>
					<td class="content-table-td">2015-06-30</td>
					<td class="content-table-td">
						<button type="button" class="table-button ">添加</button>
					</td>
				</tr>
				<tr class="even content-table-tr">
					<td class="content-table-td">1</td>
					<td class="content-table-td">虚拟潜在用户（月）</td>
					<td class="content-table-td">月</td>
					<td class="content-table-td">通过分析挖掘方法，以交往圈中虚拟用户个数....</td>
					<td class="content-table-td">82,345</td>
					<td class="content-table-td">2015-06-30</td>
					<td class="content-table-td">
						<button type="button" class="table-button ">添加</button>
					</td>
				</tr>
				<tr class="odd content-table-tr">
					<td class="content-table-td">1</td>
					<td class="content-table-td">虚拟潜在用户（月）</td>
					<td class="content-table-td">月</td>
					<td class="content-table-td">通过分析挖掘方法，以交往圈中虚拟用户个数....</td>
					<td class="content-table-td">82,345</td>
					<td class="content-table-td">2015-06-30</td>
					<td class="content-table-td">
						<button type="button" class="table-button ">添加</button>
					</td>
				</tr>
			</table>
			<div class="content-table-page">
				<div class="fright clearfix centent-page-box">
					<a href="javascript:;" class="page-button fleft">上一页</a>
					<a href="javascript:;" class="page-num fleft">1</a>
					<a href="javascript:;" class="page-num fleft">2</a>
					<a href="javascript:;" class="page-num fleft">3</a>
					<a href="javascript:;" class="page-num fleft">···</a>
					<a href="javascript:;" class="page-num fleft">99</a>
					<a href="javascript:;" class="page-num fleft">100</a>
					<a href="javascript:;" class="page-button fleft">下一页</a>
					
				</div>
			</div>
		</div>
	</div>
</div><!--indexBigAD end -->


<div id="createTacticsDialogs">
	<ul id="tactics-state-tab" class="tactics-state-tab" >
		<li class="active">我的客户群</li>
		<li>业务类标签</li>
		<li>产品关系选择</li>
		<li>基础属性</li>
	</ul>
	<div class="box active">
		<div id="addQueryTacticsDialog" class="addQueryTactics">
			<div class="searchCT">
				<div class="content-type-search-box">
					<p class="fleft"><input type="text" name="search" placeholder="请输入关键字" disabled="disabled" id="mycustomerSearch"/>
					</p>
					<i id="mycustomerTableSearch" class="searchBtn fright"></i>
				</div>
				<ul id="mycustomerQueryList" class="listInArow"></ul>
			</div>

			<div class="content-table">
				<div id="mycustomerTable" class="content-table-box"></div>
			</div>


		</div><!--addQueryTacticsDialog end -->
	</div><!--box end -->

	<div class="box">
		<div id="chooseLabelAndGroup" class="addQueryTactics">
			<div class="searchCT">
				<div class="content-type-search-box fright">
					<p class="fleft"><input type="text" name="search" id="lableSelect" placeholder="请输入关键字"/></p>
					<i id="busnessLabelSearch" class="searchBtn fright"></i>
				</div>
				<ul id="busLabelQueryList" class="listInArow"></ul>
			</div>

			<div class="content-table">
				<div id="bussinessLableTable" class="content-table-box"></div>
			</div>

		</div><!--chooseLabelAndGroup end -->
	</div><!--box end -->

	<div class="box">
		<div id="productRelationChoose" class="addQueryTactics">
			<div class="searchCT">
				<div class="content-type-search-box fright">
					<p class="fleft">
						<input type="text" name="search" placeholder="请输入关键字"/>
					</p>
					<i id="productRelationSearch" class="searchBtn fright"></i>
				</div>
				<ul id="productRelationQueryList" class="listInArow"></ul>
			</div>

			<div class="content-table">
				<div id="productRelationTable" class="content-table-box"></div>
			</div>

		</div><!--productRelationChoose end -->
	</div><!--box end -->

	<div class="box">
		<div id="moreQueryConditionDialog" class="moreQueryCondition">
			<div id="baseAttributesList">
				<dl class="conditionChoose">
					<dt>常用属性</dt>
					<dd id="commonAttrList">
						<ul>
							<li>品牌</li>
							<li class="active">ARPU</li>
							<li>性别</li>
							<li>年龄</li>
							<li>职业</li>
							<li>品牌</li>
							<li>ARPU</li>
							<li>性别</li>
							<li>年龄</li>
							<li>职业</li>
							<li>品牌</li>
							<li>ARPU</li>
							<li>性别</li>
							<li>年龄</li>
							<li>职业</li>
						</ul>
					</dd>
				</dl>
				<dl class="conditionChoose">
					<dt>流量类</dt>
					<dd id="flowClassList">
						<ul>
							<li>品牌</li>
							<li class="active">ARPU</li>
							<li>性别</li>
						</ul>
					</dd>
				</dl>
				<dl class="conditionChoose">
					<dt>时长类</dt>
					<dd timingClassList>
						<ul><li>品牌</li></ul>
					</dd>
				</dl>
				<dl class="conditionChoose">
					<dt>流量类</dt>
					<dd>
						<ul>
							<li class="active">入网时长</li>
							<li>通话时长</li>
						</ul>
					</dd>
				</dl>
			</div>
		</div><!--moreQueryConditionDialog end -->
	</div><!--box end -->
	<div class="choosed">
		<div class="choosedTitle">已选条件及规模</div>
		<span>选中客户群：</span>
		<ul id="selectedCustomers"></ul>
		<span>已选业务标签：</span>
		<ul id="selectedBusLabel"></ul>
		<span>选中产品：</span>
		<ul id="selectedProduct"></ul>
		<span>剔除产品：</span>
		<ul id="selectedExcept"></ul>
		<span>已选属性：</span>
		<ul id="selectedAttr"></ul>
	</div>
</div><!--addQueryTacticsDialog end -->

<br><br><br>

<div id="chooseNameDialog" class="chooseNetFlowDialog chooseNameDialog">
	<h4><span class="close">×</span>姓名-条件选择</h4>
	<div class="ct">
		<div class="rows">
			<table class="fwtable">
				<tr>
					<td><input type="checkbox" class="nameCheck">模糊值</td>
					<td><input type="text" class="typeName" style="width:150px;"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td class="grayText">输入您想查询的内容</td>
				</tr>
			</table>
		</div>
		<div class="btnpanel">
			<button id="chooseName-ok" class="table-button" type="button">确定</button>
			<button class="table-button gray-button" type="button">取消</button>
		</div>
	</div>
</div><!-- chooseNameDialog end-->

<div id="previewAppDialog">
</div>

<div class="chooseNetFlowDialog previewExecontentDialog_902" style="width: 1120px;height: 720px;">

	<div  style="width: 100%;height: 100%;background:url(../../assets/images/yulan-bg.jpg);position: absolute;" >
		<div  style="background-color: white;margin-top: 244px;margin-left: 255px;width: 790px;height: 280px;border: none;" >

			<div class="content" width="760px" height="400px">
				<div class="marketing-recommended-title">
					<div class="title-icon"> </div>
					<span> 个性化推荐</span>
				</div>
				<div class="banner">
					<div class="marketing-prev-btn J_silder" id="prev_slider"></div>
					<div class="marketing-next-btn J_silder" id="next_slider"></div>
				</div>
				<div class="marketing-recommended-box">
					<div id="silder" class="banner">
						<ul>
							<li>
								<div class="marketing-main">
									<div class="marketing-item">
										<div class="marketing-logo">
											<img src="../../assets/images/marketing-icon-left.png" class="marketing-icon-left">
										</div>
										<div class="marketing-content">
											<div class="marketing-btn">
												<p class="marketing-btn-title"></p>
												<p class="marketing-btn-text"></p>
											</div>
											<div class="marketing-text-box">
												<p class="marketing-text"></p>
												<button type="button">立即办理</button>
											</div>
										</div>
									</div>
									<div class="marketing-item marketing-float-right">
										<div class="marketing-logo">
											<img src="../../assets/images/marketing-icon-right.png" class="marketing-icon-right">
										</div>
										<div class="marketing-content">
											<div class="marketing-btn">
												<p class="marketing-btn-title">短信包</p>
												<p class="marketing-btn-text">档次：星级客户省钱1+1新入网赠送180元话费</p>
											</div>
											<div class="marketing-text-box">
												<p class="marketing-text">推荐语：588元包全国主叫4000分钟，国内免费接听，超出计费0.2元。</p>
												<button type="button">立即办理</button>
											</div>
										</div>
									</div>
								</div>
							</li>
						</ul>

					</div>
				</div>
			</div>

		</div>
	</div>
</div>
<div class="chooseNetFlowDialog previewExecontentDialog_906" style="width: 1120px;height: 720px;">

	<div  style="width: 100%;height: 100%;background:url(../../assets/images/channel-yyt-yl.png) no-repeat 0px -48px;position: absolute;" >
		<div  style="background-color: white;margin-top: 244px;margin-left: 275px;width: 790px;height: 280px;border: none;" >

			<div class="content" width="760px" height="400px">
				<div class="banner">
					<div class="marketing-prev-btn J_silder" id="prev_slider"></div>
					<div class="marketing-next-btn J_silder" id="next_slider"></div>
				</div>
				<div class="marketing-recommended-box">
					<div id="silder" class="banner">
						<ul>
							<li>
								<div class="marketing-main">
									<div class="marketing-item">
										<div class="marketing-logo">
											<img src="../../assets/images/channel-yyt-icon-right.png" class="marketing-icon-left">
										</div>
										<div class="marketing-content">
											<div class="marketing-btn">
												<p class="marketing-btn-title"></p>
												<p class="marketing-btn-text"></p>
											</div>
											<div class="marketing-text-box">
												<p class="marketing-text"></p>
												<button type="button">立即办理</button>
											</div>
										</div>
									</div>
									<div class="marketing-item marketing-float-right">
										<div class="marketing-logo">
											<img src="../../assets/images/channel-yyt-icon-left.png" class="marketing-icon-right">
										</div>
										<div class="marketing-content">
											<div class="marketing-btn">
												<p class="marketing-btn-title">4G终端特惠购</p>
												<p class="marketing-btn-text">档次：星级客户省钱1+1新入网赠送180元话费</p>
											</div>
											<div class="marketing-text-box">
												<p class="marketing-text">推荐语：588元包全国主叫4000分钟，国内免费接听，超出计费0.2元。</p>
												<button type="button">立即办理</button>
											</div>
										</div>
									</div>
								</div>
							</li>
						</ul>

					</div>
				</div>
			</div>

		</div>
	</div>
</div>
<div class="chooseNetFlowDialog showPlanInfoDialog" id="showPlanInfoDialog" >
	<div class="more-info-page ui-dlg-padding">
		<div >
			<span>政策编号:</span><span class="showPlanInfoDialog-id"></span>
			<span>政策名称:</span><div title='' style="white-space: nowrap; text-overflow: ellipsis;width:250px;height:70px;" class ="showPlanInfoDialog-name"></div>
			<!-- <span class ="showPlanInfoDialog-name"></span> -->
		</div>
		<div>
			<span>政策描述:</span><span class ="showPlanInfoDialog-detail"></span>
		</div>
		<div>
			<span>政策类型:</span><span class="showPlanInfoDialog-stateType"></span>
			<span>政策归属:</span><span class ="showPlanInfoDialog-cityName"></span>
		</div>
		<div>
			<span>生效时间:</span><span class="showPlanInfoDialog-beginTime"></span>
			<span>失效时间:</span><span class ="showPlanInfoDialog-endTime"></span>
		</div>
	</div>
</div>

<!--客户群详情div -->
<div class="showGroupTypeDialog">
	<div class="showGroupTypeDialog-content">
		<table class="showGroupTypeDialog-detail">
			<tbody>
			<tr>
				<td class="title">客户群编码：</td>
				<td class="showGroupTypeDialog-id"></td>
				<td class="title">客户群名称：</td>
				<td class="showGroupTypeDialog-name"></td>
			</tr>
			<tr>
				<td class="title">客户群描述：</td>
				<td class="showGroupTypeDialog-desc" colspan="3"></td>
			</tr>
			<tr>
				<td class="title">客户群筛选条件：</td>
				<td class="showGroupTypeDialog-filter" colspan="3"></td>
			</tr>
			<tr>
				<td class="title">客户群创建人：</td>
				<td class="showGroupTypeDialog-creater"></td>
				<td class="title">客户群创建时间：</td>
				<td class="showGroupTypeDialog-createtime"></td>
			</tr>
			<tr>
				<td class="title">客户群周期：</td>
				<td class="showGroupTypeDialog-updatecycle"></td>
				<td class="title">客户群接收数据日期：</td>
				<td class="showGroupTypeDialog-datatime"></td>
			</tr>
			<tr>
				<td class="title">客户群状态：</td>
				<td class="showGroupTypeDialog-effectivetime"></td>
				<td class="title">客户群失效日期：</td>
				<td class="showGroupTypeDialog-failtime"></td>
			</tr>
			</tbody>
		</table>
	</div>
</div>

<br><br>

<div id="selectMonthPLanDialog">
	<div class="searchCT">
		<div class="content-type-search-box">
			<p class="fleft">
				<input type="text" name="search" placeholder="请输入任务编号或者名称查找" id="monthPlanSearchInput">
			</p>
			<i id="monthPLanSearchI" class="searchBtn fright"></i>
		</div>
	</div>

	<div class="content-table">
		<div class="content-table-box">
			<div id="monthPlanTable">

			</div>
		</div>
	</div>
</div>
<div id="smallDialogOverlay" class="smallDialogOverlay"></div>
<div id="cep-div" class="cep-div" style="display: none;width:880px;">
<%-- 	<iframe class="cep-iframe" src="http://localhost:8077/cep/streamsScene/addInit?sysId=CEP&backUrl=http://localhost:8076<%=request.getContextPath()%>/mpm/cepEventRuleCallback_zj.jsp"></iframe> --%>
</div>
<input type="hidden" id="cepCreateCode_url" >
<input type="hidden" id="cepCreateCodeCallBack_url" >
<input type="hidden" id="customPropertiesNum" value="2">
<!-- 实时事件开始   add by lixq10  begin -->
	<!-- Modal -->
<div class="modal fade bs-example-modal-lg" id="openAddOpportunity" tabindex="-1" role="dialog" aria-labelledby="openAddOpportunityLabel" data-backdrop="static">
	  <div class="modal-dialog" role="document">
		<div class="modal-content">
		  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<h4 class="modal-title" id="openAddOpportunityLabel">营销触发时机配置</h4>
		  </div>
		  <div class="modal-body">
			 <div class="row container-fluid" id="virtualNetScene">
			 </div>
			 <div class="container-fluid tag-select" id="modalGrounp"></div>
		  </div>
		</div>
	  </div>
</div>
<!-- 实时事件结束 -->
	<div class="modal fade bs-example-modal-sm" id="tipDialog" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" data-backdrop="static">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
		    <div class="modal-header">
				<button type="button" class="close" onclick="$('#tipDialog').modal('hide');"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="mySmallModalLabel">提示框</h4>
			 </div>
		    <div class="modal-body" id="CEPtipContent">
		    </div>
		    <div class="modal-footer">
				<button type="button" class="btn btn-default" onclick="$('#tipDialog').modal('hide');">确定</button>
			</div>
	    </div>
	  </div>
	</div>
</body>
</html>
<script type="text/javascript" src="../../assets/scripts/seajs/sea.js"></script>
<script type="text/javascript" src="../../assets/scripts/seajs/seajs-preload.js"></script>
<script type="text/javascript" src="../../assets/scripts/seajs/sea-config.js"></script>

<script type="text/javascript">

	seajs.use(["../../assets/scripts/nav/navManage","../../assets/scripts/tactics/createTactics"],function(nav,ct){
		 nav.init();
 		ct.ifEditFun(); 
		ct.init();
	});
	seajs.use("../../assets/scripts/tactics/main",function(main){
		main.init();
	});

</script>
<script type="text/javascript" src="../../assets/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=pOvQ10UjwGlvdThzxSYnjGEo"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>