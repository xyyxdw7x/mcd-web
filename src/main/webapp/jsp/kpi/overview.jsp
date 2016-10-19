<%@ page contentType="text/html; charset=utf-8" %> 
<!DOCTYPE html>
<html>
<head>
<title>总览</title>
<%@ include file="../../jsp/common/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

<link  type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/put/newCreateTactics.css" />
<link  type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/kpi/effectAppraisal.css" />
<script type="text/javascript" src="<%=contextPath%>/assets/js/tactics/tacticsManage.js" ></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/kpi/LoadOverview.js" ></script>
<script type="text/javascript" src="<%=contextPath%>/assets/js/kpi/effectMain.js" ></script>
</head>
<script type="text/javascript">
	_ctx = "<%=request.getContextPath()%>";
</script>
<body>
<jsp:include page="../../jsp/common/header.jsp"></jsp:include>
<div class="container effectContainer">
	<div class="content">
    	<dl class="overviewDl">
        	<dt><span id="dayDate1" class="dateChoose"><input type="text" id="effectOverviewDate_day" class="Wdate" style="border:1px solid #efefef;"></span>日使用效果</dt>
            <dd>
            	<div class="chartsInfo" id="echarts_1">日营销用户数<span class="num"></span>人，营销成功用户数<span class="num red"></span>人，营销成功率<span class="num"></span></div>
                <div class="echartsCT">
                	<div class="msmechart" style="width:100%;height:400px;" id="index_1"  
                	data-individuation="parseIndex1" data-params="paramsIndex1"></div>
                </div><!-- ehartsCT end -->
            </dd>
        </dl><!-- overviewDl end -->
        
        <dl class="overviewDl">
        	<dt><span id="dayDate2" class="dateChoose"><input type="text" id="effectOverviewDate_month" class="chooseMonth"></span>月使用效果</dt>
            <dd>
					<div class="chartsInfo">
						<%--全省(市)目标用户数<span id="tarNum" class="num"></span>人，--%>营销用户数<span
							id="campNum" class="num"></span>人，营销成功用户数<span id="sucNum"
							class="num red"></span>人，营销成功率<span id="sucRate" class="num"></span>
					</div>
					<div class="content-table">
						<div id="effectOverviewTable" class="content-table-box"></div>
					</div>
					<div class="leftright">
                	<table width="100%" height="100%">
                    	<tr>
                        	<td width="49.9%">
                            	<div class="echartsCT">
                            		<div class="msmechart" style="width:100%;height:400px;" id="index_2" 
                           			 data-individuation="parseIndex2" data-params="paramsIndex2">
                           			</div>	
								</div>
                            </td>
                            <td class="sprtYlineTD"><div class="sprtYline">&nbsp;</div></td>
                            <td width="49.9%">
                            	<div class="echartsCT">
                            		<div class="msmechart" style="width:100%;height:400px;" id="index_3" 
                           			 data-individuation="parseIndex3" data-params="paramsIndex3">
                           			</div>	
                            	</div>
                            </td>
                        </tr>
                    </table>
                </div><!-- leftright end -->
            </dd>
        </dl><!-- overviewDl end -->
        
        <dl class="overviewDl">
        	<dt>
                <span id="dayDate3" class="dateChoose">
                	<input type="text" id="effectOverviewDate_channelsMonth" class="chooseMonth">
                    <input type="text" id="effectOverviewDate_channelsList" class="chooseMonth">
                </span>
                渠道执行情况
            </dt>
            <dd>
                <div class="leftright">
                	<table width="100%" height="100%">
                    	<tr>
                        	<td width="49.9%">
                            	<div class="echartsCT">
                            		<div class="msmechart" style="width:100%;height:400px;" id="index_4" 
                           			 data-individuation="parseIndex4" data-params="paramsIndex4">
                           			</div>
                            	</div>
                            </td>
                            <td class="sprtYlineTD"><div class="sprtYline">&nbsp;</div></td>
                            <td width="49.9%">
                            	<div class="echartsCT">
                            		<div class="msmechart" style="width:100%;height:200px;" id="index_5" 
                           			 data-individuation="parseIndex5" data-params="paramsIndex5">
                           			</div>	
                            	</div>
                            	<div class="echartsCT">
                            		<div class="msmechart" style="width:100%;height:200px;" id="index_6" 
                           			 data-individuation="parseIndex6" data-params="paramsIndex6">
                           			</div>	
                            	</div>
                            </td>
                        </tr>
                    </table>
                </div><!-- leftright end -->
            </dd>
        </dl><!-- overviewDl end -->
        
    </div><!-- content end -->
</div><!-- effectContainer end -->

</body>
</html>

<script type="text/javascript">
seajs.use("<%=contextPath%>/assets/js/kpi/effectOverview",function(main){
	
		$(function(){

		});
		
		main.init();
		
	});
	
</script>