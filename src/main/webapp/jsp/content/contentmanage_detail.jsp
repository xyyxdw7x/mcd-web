<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>内容详细内容</title>
    <%@ include file="../../jsp/common/head.jsp" %>
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"> 
    <!--css-->
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/provinces/<%=provinces%>/content/content_detail.css"/><!--通用样式-->
    <!--js-->
    <script type="text/javascript" src="<%=contextPath%>/assets/js/content/provinces/<%=provinces%>/contentmanage_extends_detail.js"></script>

    <% String contentId=request.getParameter("contentId"); %>
    <script type="text/javascript" >
        var content_id="<%=contentId%>";
    </script>
</head>
<body>
	<% String contentDetailUrl="provinces/"+provinces+"/contentmanage_extends_detail.jsp"; %>
	<jsp:include page="<%=contentDetailUrl%>"></jsp:include>
</body>
</html>