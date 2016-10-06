<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:set var="salary" scope="session" value="${2000*2}"/>
<c:out value="${salary}"/>
<c:out value="${salary}"/>
<div class="header" id="headerDiv" >
	<div class="inner">
		<div class="logo">
			<img src="${pageContext.request.getContextPath()}/assets/images/logos/logo.png" />
		</div>
		<ul id="menuUl" class="nav">
		<c:forEach items="${menus}" var="menu">  
    		<c:if test="${menu.pid=='91'}">
    		    <li data-id="${menu.id}" data-url="${menu.url}" >${menu.name}</li>
    		</c:if>  
		</c:forEach> 
		</ul>
	</div>
	<div class="subnav" >
		<div class="width950">
			<ul id="subMenuUl">
			    <c:forEach items="${menus}" var="menu2">
    				<c:if test="${menu2.pid!='91'}">
    				    <li data-id="${menu2.id}" data-pid="${menu2.pid}" data-url="${menu2.url}" >${menu2.name}</li>
    				</c:if>  
				</c:forEach> 
			</ul>
		</div>
	</div>
</div>
</body>
</html>