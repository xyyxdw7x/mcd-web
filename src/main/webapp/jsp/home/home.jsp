<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <%@ include file="../../jsp/common/head.jsp" %>
    <link  type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/home/home.css" />
    <link  type="text/css" rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/put/resetDialog-jqueryUI.css" />
</head>
<script type="text/javascript">
    _ctx = "<%=request.getContextPath()%>";
</script>
<body style="overflow-y: scroll;">
<%@ include file="../../jsp/common/header.jsp" %>
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
                    <a href="javascript:;" data-href="<%=request.getContextPath() %>/jsp/tactics/createTactics.jsp" data-subNavId="M001005004001002001" target="_blank">
                        <img class="icon" src="../../assets/images/home/index-url-icon-1.png" ></img>
                        <p class="text">新建营销策略</p>
                    </a>
                </li>
                <li class="jump-li borderRight">
                    <a href="javascript:;" data-href="<%=request.getContextPath() %>/jsp/tactics/tacticsManage.jsp" data-subNavId="M001005004001002002" target="_blank">
                        <img class="icon" src="../../assets/images/home/index-url-icon-2.png" ></img>
                        <p class="text">管理已有策略</p>
                    </a>
                </li>
                <li class="jump-li borderRight">
                    <a href="javascript:;" data-href="<%=request.getContextPath() %>/jsp/custom/addCustom.jsp?navId=M001005004001001&subNavId=M001005004001001002" data-subNavId="M001005004001001002" target="_blank">
                        <img class="icon" src="../../assets/images/home/index-url-icon-3.png" ></img>
                        <p class="text">新建客户群</p>
                    </a>
                </li>
                <li class="jump-li">
                    <a href="javascript:;" data-href="<%=request.getContextPath() %>/jsp/priority-order/priorityOrder.jsp" data-subNavId="M001005004001002003" target="_blank">
                        <img class="icon" src="../../assets/images/home/index-url-icon-4.png" ></img>
                        <p class="text">优先级管理</p>
                    </a>
                </li>
            </ul>
        </div>

        <div class="myTable">
            <div>
                <span class="mysale">我的营销策略</span>
                <span class="more-mysale"><a href="<%=request.getContextPath() %>/jsp/tactics/tacticsManage.jsp">更多></a></span>
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
						<!-- <span class="priority-item-operation fleft" >操作</span> -->
                    </li>
                </ul>
                <ul class="priority-list-content" id="mySales">
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
                </ul>

            </div>
        </div>
        
        <!-- 为簇群预留的标签 -->
        <div class="cluster-group">
        </div>

    </div>

</body>
</html>
<script type="text/javascript">
    seajs.use("home/home.js",function(home){
    	home.init();
    });
</script>
