<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<title>科室月配额列表</title>	
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<%@ include file="../../jsp/common/head.jsp" %>
    	<link href="${ctx}/assets/css/provinces/<%=provinces%>/quota/quota.css" rel="stylesheet" type="text/css" />
	</head>
	<script type="text/javascript">
			   var _ctx = "${ctx}";
	</script>
   <jsp:include page="/jsp/common/header.jsp"></jsp:include>
<body>
	<div class="container">
	 <div class="fleft detail-content-box">
	 	<div class="detail-title">
	 		<dl class="fleft">
	 			<dt>
	 				<i></i>
	 				<span>月配额</span>
	 			</dt>
	 			<dd>
	 				<i></i>
	 				<!--<input type="text" id="month"  value=""  readonly="readonly" onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy年MM月',onpicked:changeMonthDate})" />  -->
	 				<input type="text" id="month"  value=""  readonly="readonly" />
	 			</dd>
	 		</dl>
	 		<ol class="fright">
	 			<li>
	 				<a href="javascript:void(0);" class="icon_edit" id="monthEdit"></a>
	 			</li>
	 			<li>
	 				<button href="javascript:void(0);" class="icon_save" disabled="disabled" id="monthSave"></button>
	 			</li>
	 		</ol>
	 	</div>
	 	<div class="detail-content">
	 		 <div class="common-table-box">
	 		 	  <table class="common-table" >                                                    
	 		 	  	<caption class="red" >
	 		 	  		      总限额<span  id="totalMoney" ></span>条
	 		 	  	</caption>
	 		 	  	<thead>
	 		 	  		<tr>
	 		 	  			<th width="100">科室   </th>
	 		 	  			<th width="165">月配额 </th>
	 		 	  			<th width="160">已使用 </th>
	 		 	  			<th>剩余   </th>
	 		 	  		</tr>
	 		 	  	</thead>
	 		 	  </table>
	 		 	   <div class="month-table-box	">
		 		 	    <table class="common-table" id="monthTable" >
		 		 	         <tbody  id="monthTbody" >
		 		 	  	    </tbody>
		 		 	    </table>
	 		 	    </div>
	 		 </div>
	 		 <p class="descTip">
	 		   注：日配额及时生效，月配额第二天生效
	 		 </p>
	 	</div>
        <!-- 确认提示层 -->
        <div class="shade" id="monthShade"></div>
        <div class="promptLayer" id="monthPromptLayer">
        	<div class="header">确认保存</div>
            <div class="body">
            	<ol>
            		<li>
            			<i class="mark_red fleft rMargin5"></i>
            			<span class="fleft">您确定要保存，当前操作吗？立即生效</span>
            		</li>
            		<li>
            			<p class="saveCbx"><input type="checkbox" class="fleft rMargin5"  id="resetBtn" /> <span class="fleft">保存为默认配置</span></p>
            		</li>
            	</ol>
            </div>
            <div class="footer">
            	 <ol>
            	 	<li>
            	 		<button type="button" class="cancelBtn" >取消</button>
            	 	</li>
            	 	<li>
            	 		<button type="button" class="sureBtn" >确定</button>
            	 	</li>
            	 </ol>
            </div>
        </div>
	 </div> 
	 <div class="fright detail-content-box">
	 	<div class="detail-title">
	 		<dl class="fleft">
	 			<dt>
	 				<i></i>
	 				<span>当月日配额</span>
	 			</dt>
	 		</dl>
	 		<ol class="fright">
	 			<li>
	 				<a href="javascript:void(0);" class="icon_search" id="limitEdit"></a>
	 			</li>
	 			<li>
	 				<a href="javascript:void(0);" class="icon_edit" id="dateEdit"></a>
	 			</li>
	 			<li>
	 				<button href="javascript:void(0);" class="icon_save" disabled="disabled" id="dateSave"></button>
	 			</li>
	 		</ol>
	 	</div>
	 	<div class="detail-content">
	 	     <div class="calendar-table-box">
	 		 	  <table class="calendar-table" id="dateTable" >
	 		 	       <thead>
		 		 	       	<tr>
		 		 	       		<th class="red" >周日</th>
		 		 	       		<th>周一</th>
		 		 	       		<th>周二</th>
		 		 	       		<th>周三</th>
		 		 	       		<th>周四</th>
		 		 	       		<th>周五</th>
		 		 	       		<th class="red" >周六</th>                                                  
		 		 	       	</tr>
	 		 	       </thead>
	 		 	       <tbody  id="dateTbody" >
	 		 	       </tbody>
	 		 	  </table>
	 	     </div>
	 	</div>

	 	 <!-- 确认提示层 -->
        <div class="shade" id="dateShade"></div>
        <div class="promptLayer" id="datePromptLayer" >
        	<div class="header">确认保存</div>
            <div class="body">
            	<ol>
            		<li>
            			<i class="mark_red fleft rMargin5"></i>
            			<span class="fleft">您确定要保存，当前操作吗？立即生效</span>
            		</li>
            	</ol>
            </div>
            <div class="footer">
            	 <ol>
            	 	<li>
            	 		<button type="button" class="cancelBtn"   >取消</button>
            	 	</li>
            	 	<li>
            	 		<button type="button" class="sureBtn">确定</button>
            	 	</li>
            	 </ol>
            </div>
        </div>
	 </div>
</div>
	<script type="text/javascript" src="${ctx}/assets/js/lib-ext/my97/WdatePicker.js"></script> 
<%--     <script type="text/javascript" src="${ctx}/mcd/assets/scripts/scrollbar/jquery.mousewheel.min.js"> </script> --%>
    <script type="text/javascript" src="${ctx}/assets/js/quota/quota.js"></script>
</body>
</html>