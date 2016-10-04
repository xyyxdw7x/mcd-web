<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/mcd/assets/images/logos/favicon.ico" />

    <link rel="stylesheet" type="text/css" href="../../assets/styles/common.css" />
    <link rel="stylesheet" type="text/css" href="../../assets/styles/jqueryUI/jquery-ui-1.11.0.min.css" />
    <link rel="stylesheet" type="text/css" href="../../assets/styles/index.css" />
    <link rel="stylesheet" type="text/css" href="../../../wro/resetDialog-jqueryUI.css" >
   

</head>
<script type="text/javascript">
    _ctx = "<%=request.getContextPath()%>";
</script>
<body style="overflow-y: scroll;">
<jsp:include page="../../../action/privilege/login/getUserMenuAll.do"></jsp:include>
    <div class="content">
        <div class="sale-situation">
            <ul class="sale-situation-ul">
                <li class="sale-situation-li-up" data-range="A" data-tab="t_cam">
                    <div class="sale-situation-li-up-name">
                        总营销数（人）
                    </div>
                    <div class="sale-situation-li-up-value totalNum">
                    </div>
                </li>
                <li class="sale-situation-li-up" data-range="A" data-tab="t_suc">
                    <div class="sale-situation-li-up-name">
                        总成功数（人）
                    </div>
                    <div class="sale-situation-li-up-value totalSuccessNum">
                    </div>
                </li>
            </ul>
            <ul class="sale-situation-ul flexDisplay">
                <li class="sale-situation-li-down" data-range="M" data-tab="t_cam">
                    <div class="sale-situation-li-down-name">本月营销数（人）</div>
                    <div class="sale-situation-li-down-value saleNumMon"></div>
                </li>
                <li class="sale-situation-li-down" data-range="M" data-tab="t_suc">
                    <div class="sale-situation-li-down-name">本月成功数（人）</div>
                    <div class="sale-situation-li-down-value saleSuccessNumMon"></div>
                </li>
                <li class="sale-situation-li-down" data-range="M" data-tab="t_cam">
                    <div class="sale-situation-li-down-name">昨日营销数（人）</div>
                    <div class="sale-situation-li-down-value saleNumDay"></div>
                </li>
                <li class="sale-situation-li-down" data-range="M" data-tab="t_suc">
                    <div class="sale-situation-li-down-name">昨日成功数（人）</div>
                    <div class="sale-situation-li-down-value saleSuccessNumDay"></div>
                </li>
            </ul>
        </div>

        <div class="jump">
            <ul class="jump-ul">
                <li class="jump-li borderRight">
                    <a href="javascript:;" data-href="<%=request.getContextPath() %>/mcd/pages/tactics/createTactics.jsp?navId=M001005004001002&subNavId=M001005004001002001" data-subNavId="M001005004001002001" target="_blank">
                        <img class="icon" src="../../assets/images/index/url-icon/index-url-icon-1.png" ></img>
                        <p class="text">新建营销策略</p>
                    </a>
                </li>
                <li class="jump-li borderRight">
                    <a href="javascript:;" data-href="<%=request.getContextPath() %>/mcd/pages/tactics/tacticsManage.jsp?navId=M001005004001002&subNavId=M001005004001002002" data-subNavId="M001005004001002002" target="_blank">
                        <img class="icon" src="../../assets/images/index/url-icon/index-url-icon-2.png" ></img>
                        <p class="text">管理已有策略</p>
                    </a>
                </li>
                <li class="jump-li borderRight">
                    <a href="javascript:;" data-href="<%=request.getContextPath() %>/mcd/pages/custom/addCustom.jsp?navId=M001005004001001&subNavId=M001005004001001002" data-subNavId="M001005004001001002" target="_blank">
                        <img class="icon" src="../../assets/images/index/url-icon/index-url-icon-3.png" ></img>
                        <p class="text">新建客户群</p>
                    </a>
                </li>
                <li class="jump-li">
                    <a href="javascript:;" data-href="<%=request.getContextPath() %>/mcd/pages/effectAppraisal/saleOrderPage.jsp?navId=M001005004001002&subNavId=M001005004001002003" data-subNavId="M001005004001002003" target="_blank">
                        <img class="icon" src="../../assets/images/index/url-icon/index-url-icon-4.png" ></img>
                        <p class="text">优先级管理</p>
                    </a>
                </li>
            </ul>
        </div>

        <div class="myTable">
            <div>
                <span class="mysale">我的营销策略</span>
                <span class="more-mysale"><a href="<%=request.getContextPath() %>/mcd/pages/tactics/tacticsManage.jsp?navId=M001005004001002&subNavId=M001005004001002002">更多></a></span>
            </div>
            <div class="priority-list-content">
                <ul class="priority-list-content">
                    <li class="priority-list-thead"  style="width: 1202px;">
                        <span class="priority-item-num fleft">序号</span>
                        <span class="priority-item-id fleft" >策略编号</span>
                        <span class="priority-item-name fleft" >策略名称</span>
                        <span class="priority-item-status fleft" >状态</span>
                        <span class="priority-item-daysucc fleft" style="color:#333">昨日成功数</span>
                        <span class="priority-item-monsucc fleft" style="color:#333">本月成功数</span>
                        <span class="priority-item-info fleft" >详情</span>
						<span class="priority-item-operation fleft" >操作</span>
                    </li>
                </ul>
                <ul class="priority-list-content" id="mySales">
                    <!--<li class="priority-list-tbody"  style="width: 1202px;">
                        <span class="priority-item-num fleft">1</span>
                        <span class="priority-item-name fleft" style="width:190px;">123456</span>
                        <span class="priority-item-desc fleft" style="width: 200px;">测试1</span>
                        <span class="priority-item-date fleft" >草稿</span>
                        <span class="priority-item-prior fleft" >5</span>
                        <span class="priority-item-status fleft" >1111</span>
                        <span class="priority-item-status fleft" style="width: 100px;">查看</span>
						<span class="priority-item-operation fleft" style = "width:130px;">
							<button type="button" class="bulk-quota-btn fleft list-btn "  >
                                <span class=" J_bulkStop" >我要催单</span>
                            </button>
						</span>					</li>
                    <li class="priority-list-tbody"  style="width: 1202px;">
                        <span class="priority-item-num fleft">2</span>
                        <span class="priority-item-name fleft" style="width:190px;">123456</span>
                        <span class="priority-item-desc fleft" style="width: 200px;">测试2</span>
                        <span class="priority-item-date fleft" >草稿</span>
                        <span class="priority-item-prior fleft" >5</span>
                        <span class="priority-item-status fleft" >1111</span>
                        <span class="priority-item-status fleft" style="width: 100px;">查看</span>
						<span class="priority-item-operation fleft" style = "width:130px;">
							<button type="button" class="bulk-quota-btn fleft list-btn "  >
                                <span class=" J_bulkStop" >我要催单</span>
                            </button>
						</span>					</li>
                    <li class="priority-list-tbody"  style="width: 1202px;">
                        <span class="priority-item-num fleft">3</span>
                        <span class="priority-item-name fleft" style="width:190px;">123456</span>
                        <span class="priority-item-desc fleft" style="width: 200px;">测试3</span>
                        <span class="priority-item-date fleft" >草稿</span>
                        <span class="priority-item-prior fleft" >5</span>
                        <span class="priority-item-status fleft" >1111</span>
                        <span class="priority-item-status fleft" style="width: 100px;">查看</span>
						<span class="priority-item-operation fleft" style = "width:130px;">
							<button type="button" class="bulk-quota-btn fleft list-btn "  >
                                <span class=" J_bulkStop" >我要催单</span>
                            </button>
						</span>
                    </li>
                    <li class="priority-list-tbody"  style="width: 1202px;">
                        <span class="priority-item-num fleft">4</span>
                        <span class="priority-item-name fleft" style="width:190px;">123456</span>
                        <span class="priority-item-desc fleft" style="width: 200px;">测试4</span>
                        <span class="priority-item-date fleft" >草稿</span>
                        <span class="priority-item-prior fleft" >5</span>
                        <span class="priority-item-status fleft" >1111</span>
                        <span class="priority-item-status fleft" style="width: 100px;">查看</span>
						<span class="priority-item-operation fleft" style = "width:130px;">
							<button type="button" class="bulk-quota-btn fleft list-btn "  >
                                <span class=" J_bulkStop" >我要催单</span>
                            </button>
						</span>
                    </li>
                    <li class="priority-list-tbody"  style="width: 1202px;">
                        <span class="priority-item-num fleft">5</span>
                        <span class="priority-item-name fleft" style="width:190px;">123456</span>
                        <span class="priority-item-desc fleft" style="width: 200px;">测试5</span>
                        <span class="priority-item-date fleft" >草稿</span>
                        <span class="priority-item-prior fleft" >5</span>
                        <span class="priority-item-status fleft" >1111</span>
                        <span class="priority-item-status fleft" style="width: 100px;">查看</span>
						<span class="priority-item-operation fleft" style = "width:130px;">
							<button type="button" class="bulk-quota-btn fleft list-btn "  >
                                <span class=" J_bulkStop" >我要催单</span>
                            </button>
						</span>
                    </li>
                    <li class="priority-list-tbody"  style="width: 1202px;">
                        <span class="priority-item-num fleft">6</span>
                        <span class="priority-item-name fleft" style="width:190px;">123456</span>
                        <span class="priority-item-desc fleft" style="width: 200px;">测试6</span>
                        <span class="priority-item-date fleft" >草稿</span>
                        <span class="priority-item-prior fleft" >5</span>
                        <span class="priority-item-status fleft" >1111</span>
                        <span class="priority-item-status fleft" style="width: 100px;">查看</span>
						<span class="priority-item-operation fleft" style = "width:130px;">
							<button type="button" class="bulk-quota-btn fleft list-btn "  >
                                <span class=" J_bulkStop" >我要催单</span>
                            </button>
						</span>
                    </li>
                    <li class="priority-list-tbody"  style="width: 1202px;">
                        <span class="priority-item-num fleft">7</span>
                        <span class="priority-item-name fleft" style="width:190px;">123456</span>
                        <span class="priority-item-desc fleft" style="width: 200px;">测试7</span>
                        <span class="priority-item-date fleft" >草稿</span>
                        <span class="priority-item-prior fleft" >5</span>
                        <span class="priority-item-status fleft" >1111</span>
                        <span class="priority-item-status fleft" style="width: 100px;">查看</span>
						<span class="priority-item-operation fleft" style = "width:130px;">
							<button type="button" class="bulk-quota-btn fleft list-btn "  >
                                <span class=" J_bulkStop" >我要催单</span>
                            </button>
						</span>
                    </li>
                    <li class="priority-list-tbody"  style="width: 1202px;">
                        <span class="priority-item-num fleft">8</span>
                        <span class="priority-item-name fleft" style="width:190px;">123456</span>
                        <span class="priority-item-desc fleft" style="width: 200px;">测试8</span>
                        <span class="priority-item-date fleft" >草稿</span>
                        <span class="priority-item-prior fleft" >5</span>
                        <span class="priority-item-status fleft" >1111</span>
                        <span class="priority-item-status fleft" style="width: 100px;">查看</span>
						<span class="priority-item-operation fleft" style = "width:130px;">
							<button type="button" class="bulk-quota-btn fleft list-btn "  >
                                <span class=" J_bulkStop"  >我要催单</span>
                            </button>
						</span>
                    </li>
                    <li class="priority-list-tbody"  style="width: 1202px;">
                        <span class="priority-item-num fleft">9</span>
                        <span class="priority-item-name fleft" style="width:190px;">123456</span>
                        <span class="priority-item-desc fleft" style="width: 200px;">测试9</span>
                        <span class="priority-item-date fleft" >草稿</span>
                        <span class="priority-item-prior fleft" >5</span>
                        <span class="priority-item-status fleft" >1111</span>
                        <span class="priority-item-status fleft" style="width: 100px;">查看</span>
						<span class="priority-item-operation fleft" style = "width:130px;">
							<button type="button" class="bulk-quota-btn fleft list-btn "  >
                                <span class=" J_bulkStop" >我要催单</span>
                            </button>
						</span>
                    </li>
                    <li class="priority-list-tbody"  style="width: 1202px;">
                        <span class="priority-item-num fleft">10</span>
                        <span class="priority-item-name fleft" style="width:190px;">123456</span>
                        <span class="priority-item-desc fleft" style="width: 200px;">测试10</span>
                        <span class="priority-item-date fleft" >草稿</span>
                        <span class="priority-item-prior fleft" >5</span>
                        <span class="priority-item-status fleft" >1111</span>
                        <span class="priority-item-status fleft" style="width: 100px;">查看</span>
						<span class="priority-item-operation fleft" style = "width:130px;">
							<button type="button" class="bulk-quota-btn fleft list-btn "  >
                                <span class=" J_bulkStop"  >我要催单</span>
                            </button>
						</span>
                    </li>-->
                </ul>
            </div>
        </div>

        <div class="recommend">
            <div class="recommend-title lFloat">
                <span class="recommend-title-text">优秀营销策略推荐</span>
            </div>
            <div class="recommend-page rFloat">
                <span class="recommend-page-span prev disable-recommend-page"><</span>
                <span class="recommend-page-span next " style="border-left: none;">></span>
            </div>
            <div class="recommend-detail lFloat" id="recommend-detail">
                <ul class="recommend-detail-ul" id="recommendCamps">
                    <!--<li class="recommend-detail-li">
                        <div class="recommend-detail-li-div recommend-detail-color-1">
                            <div class="title">
                                <p title="">4G飞享套餐</p>
                            </div>
                            <div class="info info-1">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="info info-2">
                                <span class="info-name">成功率</span>
                                <span class="info-value">98%</span>
                            </div>
                            <div class="info info-3">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="icon-list">
                                <ul class="icon-list-ul">
                                    <li class="icon-list-li"></li>
                                </ul>
                            </div>
                        </div>
                        <div class="recommend-detail-li-div recommend-detail-color-2">
                            <div class="title">
                                <p title="">4G飞享套餐</p>
                            </div>
                            <div class="info info-1">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="info info-2">
                                <span class="info-name">成功率</span>
                                <span class="info-value">98%</span>
                            </div>
                            <div class="info info-3">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="icon-list">
                                <ul class="icon-list-ul">
                                    <li class="icon-list-li"></li>
                                </ul>
                            </div>
                        </div>
                        <div class="recommend-detail-li-div recommend-detail-color-3">
                            <div class="title">
                                <p title="">4G飞享套餐</p>
                            </div>
                            <div class="info info-1">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="info info-2">
                                <span class="info-name">成功率</span>
                                <span class="info-value">98%</span>
                            </div>
                            <div class="info info-3">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="icon-list">
                                <ul class="icon-list-ul">
                                    <li class="icon-list-li"></li>
                                </ul>
                            </div>
                        </div>
                        <div class="recommend-detail-li-div recommend-detail-color-4">
                            <div class="title">
                                <p title="">4G飞享套餐</p>
                            </div>
                            <div class="info info-1">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="info info-2">
                                <span class="info-name">成功率</span>
                                <span class="info-value">98%</span>
                            </div>
                            <div class="info info-3">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="icon-list">
                                <ul class="icon-list-ul">
                                    <li class="icon-list-li"></li>
                                </ul>
                            </div>
                        </div>
                        <div class="recommend-detail-li-div recommend-detail-color-5">
                            <div class="title">
                                <p title="">4G飞享套餐</p>
                            </div>
                            <div class="info info-1">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="info info-2">
                                <span class="info-name">成功率</span>
                                <span class="info-value">98%</span>
                            </div>
                            <div class="info info-3">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="icon-list">
                                <ul class="icon-list-ul">
                                    <li class="icon-list-li"><img src="<%=request.getContextPath()%>/mcd/assets/images/index-icon/channel_901.png" /></li>
                                    <li class="icon-list-li"><img src="<%=request.getContextPath()%>/mcd/assets/images/index-icon/channel_902.png" /></li>
                                    <li class="icon-list-li"><img src="<%=request.getContextPath()%>/mcd/assets/images/index-icon/channel_903.png" /></li>
                                    <li class="icon-list-li"><img src="<%=request.getContextPath()%>/mcd/assets/images/index-icon/channel_904.png" /></li>
                                    <li class="icon-list-li moreChannels">更多&nbsp;></li>

                                </ul>
                            </div>
                        </div>
                    </li>
                    <li class="recommend-detail-li">
                        <div class="recommend-detail-li-div recommend-detail-color-1">
                            <div class="title">
                                <p title="">4G飞享套餐</p>
                            </div>
                            <div class="info info-1">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="info info-2">
                                <span class="info-name">成功率</span>
                                <span class="info-value">98%</span>
                            </div>
                            <div class="info info-3">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="icon-list">
                                <ul class="icon-list-ul">
                                    <li class="icon-list-li"></li>
                                </ul>
                            </div>
                        </div>
                        <div class="recommend-detail-li-div recommend-detail-color-2">
                            <div class="title">
                                <p title="">4G飞享套餐</p>
                            </div>
                            <div class="info info-1">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="info info-2">
                                <span class="info-name">成功率</span>
                                <span class="info-value">98%</span>
                            </div>
                            <div class="info info-3">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="icon-list">
                                <ul class="icon-list-ul">
                                    <li class="icon-list-li"></li>
                                </ul>
                            </div>
                        </div>
                        <div class="recommend-detail-li-div recommend-detail-color-3">
                            <div class="title">
                                <p title="">4G飞享套餐2</p>
                            </div>
                            <div class="info info-1">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="info info-2">
                                <span class="info-name">成功率</span>
                                <span class="info-value">98%</span>
                            </div>
                            <div class="info info-3">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="icon-list">
                                <ul class="icon-list-ul">
                                    <li class="icon-list-li"></li>
                                </ul>
                            </div>
                        </div>
                        <div class="recommend-detail-li-div recommend-detail-color-4">
                            <div class="title">
                                <p title="">4G飞享套餐</p>
                            </div>
                            <div class="info info-1">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="info info-2">
                                <span class="info-name">成功率</span>
                                <span class="info-value">98%</span>
                            </div>
                            <div class="info info-3">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="icon-list">
                                <ul class="icon-list-ul">
                                    <li class="icon-list-li"></li>
                                </ul>
                            </div>
                        </div>
                        <div class="recommend-detail-li-div recommend-detail-color-5">
                            <div class="title">
                                <p title="">4G飞享套餐</p>
                            </div>
                            <div class="info info-1">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="info info-2">
                                <span class="info-name">成功率</span>
                                <span class="info-value">98%</span>
                            </div>
                            <div class="info info-3">
                                <span class="info-name">营销</span>
                                <span class="info-value">36000人</span>
                            </div>
                            <div class="icon-list">
                                <div class="channel-page-span prev">
                                    <
                                </div>
                                <div class="icon-list-div">
                                    <ul class="icon-list-ul">
                                        <li class="icon-list-li">
                                            <img src="<%=request.getContextPath()%>/mcd/assets/images/index-icon/channel_901.png" />
                                            <img src="<%=request.getContextPath()%>/mcd/assets/images/index-icon/channel_902.png" />
                                            <img src="<%=request.getContextPath()%>/mcd/assets/images/index-icon/channel_903.png" />
                                            <img src="<%=request.getContextPath()%>/mcd/assets/images/index-icon/channel_904.png" />
                                        </li>
                                        <li class="icon-list-li">
                                            <img src="<%=request.getContextPath()%>/mcd/assets/images/index-icon/channel_901.png" />
                                            <img src="<%=request.getContextPath()%>/mcd/assets/images/index-icon/channel_902.png" />
                                            <img src="<%=request.getContextPath()%>/mcd/assets/images/index-icon/channel_903.png" />
                                            <img src="<%=request.getContextPath()%>/mcd/assets/images/index-icon/channel_904.png" />
                                        </li>
                                    </ul>
                                </div>
                                <div class="channel-page-span next">
                                    >
                                </div>
                            </div>
                        </div>
                    </li>-->
                </ul>

            </div>
        </div>

    </div>

</body>
</html>
<script type="text/javascript" src="../../assets/scripts/seajs/sea.js"></script>
<script type="text/javascript" src="../../assets/scripts/seajs/seajs-preload.js"></script>
<script type="text/javascript" src="../../assets/scripts/seajs/sea-config.js"></script>
<script type="text/javascript">

    seajs.use("../../assets/scripts/index/index",function(main){
        main.init();
    });
</script>
<script type="text/javascript" src="../../../assets/js/core.js"></script>