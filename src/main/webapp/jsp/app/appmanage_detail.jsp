<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>应用详细内容</title>
    <%@ include file="../../jsp/common/head.jsp" %>
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"> 
    <!--css-->
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/app/app_detail.css"/><!--通用样式-->
    <!--js-->
    <script type="text/javascript" src="<%=contextPath%>/assets/js/app/provinces/<%=provinces%>/appmanage_extends_detail.js"></script>

    <% String appId=request.getParameter("appId"); %>
    <script type="text/javascript" >
        var app_id="<%=appId%>";
    </script>
</head>
<body>
	<% String appDetailUrl="provinces/"+provinces+"/appmanage_extends_detail.jsp"; %>
	<jsp:include page="<%=appDetailUrl%>"></jsp:include>
</body>
</html>