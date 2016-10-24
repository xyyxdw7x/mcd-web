 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--THE SITE IS DESIGNED BY john0723@outlook.com, Inc 17/10/2016-->
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>内容详细内容</title>
     <%@ include file="../../../common/head.jsp" %>
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"> 
    <!--css-->
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/content/common.css"/><!--通用样式-->
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/content/content_detail.css"/><!--通用样式-->
    <!--js-->
    <script type="text/javascript" src="<%=contextPath%>/assets/js/content/provinces/<%=provinces%>/contentmanage_extends_detail.js"></script>

    <%
    String contentId=request.getParameter("contentId");
    %>
    <script type="text/javascript" >
        var content_id="<%=contentId%>";
        //var planId = '10003';
    </script>
</head>
<body>
<!-- 弹出窗的背景 -->
<div class="popwin"  style="display: ">
     <div class="head">
         <div class="pop-page-header">
            <div class="pop-page-header-content">
                <div class="header-content-left">
                    <span id="header-content-detail">详情</span>
                    <%-- <span><img src="<%=contextPath%>/assets/images/bianji.png" id="detail_img" title="编辑"></span> --%>
                   <!--  <span id="header-content-back"> < 返回 </span> -->
                </div>
                <div class="header-content-right">
                   <!--  <span id="close" title="关闭"> X </span> -->
                </div>
            </div><!--pop-page-header-content end-->
        </div><!--pop-page-header end-->
     </div>
     <div class="main">
       <div class="poppage " id="poppage" style="display: ">
        <div class="pop-page-content">
            <div class="pop-page-content-top">
                <div class="pop-page-content-top-div">
                    <div class="input-div">
                        <label>内容编码：</label>
                        <input type="text" class="min-input min-input-min-width " id="content_id" disabled>
                    </div>

                    <div class="input-div">
                        <label>类型：</label>
                        <input type="text" class="min-input min-input-min-width" id="content_class" disabled>
                    </div>

                    <div class="input-div">
                        <label>内容三级分类：</label>
                        <input type="text" class="min-input min-input-min-width" id="content_class3" disabled>
                    </div>
                    
                    <div class="input-div">
                        <label>发布时间：</label>
                        <input type="text" class="min-input min-input-min-width" id="pub_time" disabled>
                    </div>
                    
                    <div class="input-div">
                        <label>URL长链接：</label>
                        <input type="text" class="min-input min-input-min-width" id="content_url" disabled>
                    </div>
                    
                    <div class="input-div">
                        <label>适用地市：</label>
                        <input type="text" class="min-input min-input-min-width" id="city" disabled>
                    </div>
                </div><!--1 end-->
                <div class="pop-page-content-top-div">
                    <div class="input-div">
                        <label>内容名称：</label>
                        <input type="text" class="min-input min-input-min-width" id="content_name" disabled>
                    </div>
                    <div class="input-div">
                        <label>内容一级分类：</label>
                        <input type="text" class="min-input min-input-min-width" id="content_class1" disabled>
                    </div>
                    <div class="input-div">
                        <label>内容四级分类：</label>
                         <input type="text" class="min-input min-input-min-width" id="content_class4" disabled>
                    </div>
                    <div class="input-div">
                        <label>失效时间：</label>
                        <input type="text" class="min-input min-input-min-width" id="invalid_time" disabled>
                    </div>
                    <div class="input-div">
                        <label>来源：</label>
                        <input type="text" class="min-input min-input-min-width" id="content_source" disabled>
                    </div>
                    <div class="input-div">
                        <label>状态：</label>
                        <select name="" id="online_status" class="min-input " >

                        </select>
                    </div>
                </div><!--2 end-->
                <div class="pop-page-content-top-div " id="last">
                    <div class="input-div">
                        <label>类别：</label>
                        <input type="text" id="type_name" class="min-input min-input-min-width" disabled>
                    </div>
                    <div class="input-div">
                        <label>内容二级分类：</label>
                        <input type="text" class="min-input min-input-min-width" id="content_class2" disabled>
                    </div>
                    <div class="input-div">
                        <label>单价(元/次)：</label>
                        <input type="text" id="unit_price" class="min-input min-input-min-width" disabled>
                    </div>
                    <div class="input-div">
                        <label>酬金(元)：</label>
                        <input type="text" id="award_mount" class="min-input min-input-min-width" disabled>
                    </div>
                    <div class="input-div">
                        <label>产品经理：</label>
                        <input type="text" id="manager" class="min-input min-input-min-width" disabled>
                    </div>
                    <div class="input-div">
                        <label></label>
                    </div>
                </div><!--3 end-->
            </div><!--pop-page-content-top end-->

            <div class="pop-page-content-center">
                <div class="pop-page-content-center-top">
                    <div class="pop-page-content-center-top-div">
                        <div class="input-div">
                            <label for="">摘要：</label>
                            <input type="text" class="min-input min-input-lg-width"  id="content_remark"  disabled>
                        </div>
                    </div>
                </div><!--pop-page-content-center-top  End-->
                <div class="pop-page-content-center-foot">
                    <div class="container-fluid">
                        <div class="pop-page-content-center-foot-btn">
                            <div class="btn-div">
                                <%--<button type="button" class="btn-blu" id="save-btn" disabled>保存</button>--%>
                            </div>
                        </div>
                    </div>
                </div><!--pop-page-content-center-foot  End-->
            </div><!--pop-page-content-center-->

            <div class="container-fluid pop-page-content-foot ">
                <div class=" pop-page-content-foot-content">
                    <div class="pop-page-content-foot-btn-div btn-pref"> <span id="btn-pref"> < </span></div>
                    <div class="pop-page-content-foot-content-div">
                        <div class="pop-page-content-foot-content-div-list">
                           <div class="pop-page-content-foot-content-div-list-div" id="campsegContent">

                           </div>
                        </div>
                    </div>
                    <div class="pop-page-content-foot-btn-div btn-next"> <span id="btn-next"> > </span></div>
                </div><!--pop-page-content-foot-content  End-->
            </div><!--pop-page-content-foot-->
        </div>
    </div><!-- poppage end -->
     
     </div>
    
</div><!--popwin end-->
</body>
</html>