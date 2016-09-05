<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=utf-8"%>
<html>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<head>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
%>
<script type="text/javascript"
	src="../mcd/assets/scripts/jquery/jquery-3.1.0.min.js"></script>
<script type="text/javascript">
	var _ctx = "<%=request.getContextPath()%>";

	$(document).ready(function() {
		$("#btnLogin").click(function() {
			login();
		});
        
	});
	$(document).ajaxError(function(event, jqxhr, settings, thrownError) {
		alert("thrownError=" + thrownError);
	});
	
	$(document).keyup(function(event){
		  if(event.keyCode ==13){
		    $("#btnLogin").trigger("click");
		  }
		});
	
	function login() {
		var userId = $("#userName").val();
		var pwd = $("#userPwd").val();
		var url = _ctx + "/privilege/login/login";
		$.ajax({
			type : 'POST',
			url : url,
			dataType : "json",
			data : {
				userId : userId,
				userPwd : pwd
			},
			success : function(result) {
				if (result == true) {
					window.location = _ctx + "/mcd/pages/index/index.jsp"
				} else {
					alert("用户名或密码错误！");
				}
			},
			error : function(err) {
				alert(err + "ERROR");
			}
		});

	}
</script>
</head>
<body>
	<div class="container" align="center" style="margin-top: 200px;">
		<ul style="list-style-type: none;">
			<li>
			<label>用户名：</label>
			<input type="text"
				style="width: 320px; height: 35px; color: #b6b6b6; margin-top: 12px; margin-bottom: 15px;  font-size: 14px; padding: 0 0 0 10px;"
				id="userName">
			</li>
			<li>
			<label>密&nbsp;码：</label>
			<input type="password"
				style="width: 320px; height: 35px; color: #b6b6b6; font-size: 14px; padding: 0 0 0 10px; margin-bottom: 15px;"
				id="userPwd">
			</li>
			<li><input id="btnLogin" type="submit" value="立即登录"
				style="background-color: #ff961e; color: white; width: 300px; height: 35px; margin-top: 12px; font-size: 16px; border: none; text-align: center;"></li>
		</ul>
	</div>
</body>
</html>