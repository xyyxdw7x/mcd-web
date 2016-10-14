<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<span class="username">${sessionScope.USER.name},您好</span>
	</div>
	<div class="subnav" >
		<div class="width950">
			<ul id="subMenuUl">
			    <c:forEach items="${menus}" var="subMenu">
    				<c:if test="${subMenu.pid!='91'}">
    				    <li data-id="${subMenu.id}" data-pid="${subMenu.pid}" data-url="${subMenu.url}" style="display:none" >${subMenu.name}</li>
    				</c:if>  
				</c:forEach> 
			</ul>
		</div>
	</div>
</div>