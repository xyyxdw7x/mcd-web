<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="box-html">
<head>
<title>数据详情</title>
<%@ include file="../../jsp/common/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

<link  type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/home/dataDrilling.css" />
<script type="text/javascript" src="<%=contextPath%>/assets/js/home/main.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/home/home_chart.js"></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/home/T_ELEPHANT.js"></script>
<style>
	.clearBoth{
		clear:both;
	}
</style>
</head>
<body>
	<div id="page-container">
		<div class="navigate">
			<div class="logAndInfo">
				<div class="floatLeft">
					<img class="log1Imag"
						src="${ctx }/assets/images/logos/logo.png" alt="log">
				</div>
			</div>
			<div id="dataInfo">数据详情</div>
		</div>

		<div class="content">

			<div class="trendYears">
				<span class="recentYear active" id="allYears" mac="A">所有</span>
				<!-- 			<span class="mysale">趋势变化情况</span> -->
				<span class="recentYear" id="recentTwoYears" mac="M">本月</span>
<!-- 				 <span class="recentYear" id="recentOneYear" mac="D">今日</span> -->
			</div>
			<p class="mysale">趋势变化情况</p>
			<div class="firstChartDiv TEND_CHART"
				style="background-color: white;">
				<span class="tab-item active" mat="t_cam" verti="tend">总营销人数</span>
				<span class="tab-item" mat="t_suc" verti="tend">总营销成功数</span> <span
					class="tab-item " mat="cam_cvt" verti="tend">营销转化率</span>
				<div id="echart1" class="e_chart clearBoth"></div>
			</div>
			<p class="mysale" style="margin-bottom: -19px;">营销投放去向</p>
			<div class="middleChartDiv PUT_CHART"
				style="background-color: white;">
				<span class="tab-item active" mat="t_cam" verti="put">总营销人数</span> <span
					class="tab-item" mat="t_suc" verti="put">总营销成功数</span> <span
					class="tab-item " mat="cam_cvt" verti="put">营销转化率</span>
				<div id="echart2" class="e_chart clearBoth"></div>

			</div>
			<p class="mysale" style="">地区分布情况</p>
			<div class="endChartDiv AREA_CHART" style="background-color: white;">
				<span class="tab-item active" mat="t_cam" verti="area">总营销人数</span>
				<span class="tab-item" mat="t_suc" verti="area">总营销成功数</span> <span
					class="tab-item " mat="cam_cvt" verti="area">营销转化率</span>
				<div id="echart3" class="e_chart clearBoth"></div>
			</div>
		</div>
	</div>
</body>
</html>