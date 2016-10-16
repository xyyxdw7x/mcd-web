<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=utf-8"%>
<html>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<head>
<link rel="stylesheet" type="text/css" href="../assets/css/common.css" />
<script type="text/javascript" src="../assets/js/lib/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="../assets/js/lib/jquery.md5.min.js"></script>
<script type="text/javascript" src="../assets/js/lib/jquery.cookie.min.js"></script>
<script type="text/javascript">
	var contextPath = "<%=request.getContextPath()%>";
	$(document).ready(function() {
		$("#btnLogin").click(function() {
			login();
		});
		if($.cookie("IMCD_USER")!=undefined){
			$("#userName").val($.cookie("IMCD_USER"));
		}
	});
	//点击回车进行登录
	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			$("#btnLogin").trigger("click");
		}
	});
	function login() {
		var userId = $("#userName").val().replace(/\n/gi,"").replace(/\$[\s\S]*?\$/gi,"");
		if(userId==""){
			alert("用户名不能为空");
			return ;
		}
		var pwd=$("#userPwd").val().replace(/\n/gi,"").replace(/\$[\s\S]*?\$/gi,"");
		var userPwd = $.md5(pwd);
		var url = contextPath + "/action/privilege/login/login.do";
		var data={userId:userId,userPwd:userPwd};
		$.ajax({
			type:"POST",
			url:url,
			dataType:"json",
			data:data,
			success:function(result) {
				if (result == true) {
					//保存或取消用户名
					if($("#remUserName").is(':checked')){
						$.cookie("IMCD_USER",userId,{expires:10});
					}else{
						$.removeCookie("IMCD_USER");
					}
					window.location = contextPath + "/jsp/home/home.jsp";
				} else {
					alert("用户名或密码错误！");
				}
			},
			error : function(err) {
				alert(err + "登录失败，请重试。");
			}
		});
	}
</script>
</head>
<body>
	<div class="userLogin-box" align="center">
		<div class="fleft loginLeft-box"></div>
		<div class="fright loginRight-box">
				<ul>
					<li class="text-center login-logo"><img src="../assets/images/login/login-logo.png" />
					<li>
					<li><input id="userName" class="login-input" placeholder="用户名" type="text"></li>
					<li>
					<li><input id="userPwd" class="login-input" placeholder="密码" type="password">
					<li>
					<li class="psd-li">
							<label class="fleft">
							<input id="remUserName" type="checkbox">记住用户名</label> <a href="#" class="fright" style="display:none">忘记密码？</a>
					</li>
					<li><input id="btnLogin" class="login-input btn-loginSubmit" value="登录" type="submit"></li>
				</ul>
		</div>
	</div>
</body>
</html>