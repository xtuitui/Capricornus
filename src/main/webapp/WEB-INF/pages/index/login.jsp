<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Login</title>
		<%@include file="/WEB-INF/pages/common/header.jsp"%>
		<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/index/login.css"/>
		<script type="text/javascript" lang="javascript">
			function login(){
				var username = $("#username").val();
				var password = $("#password").val();
				var rememberMe = $("#rememberMe").prop("checked");
				$.post("${path}/index/login",{"username":username, "password":password, "rememberMe":rememberMe},function(data){
					if(data.result=="success"){
						window.location.href = "${path}/index/toWelcome";
					}else{
						alert("用户名或者密码错误!");
						$("#password").val("");
						$("#username").focus();
					}
				});
			}
			
			function callLogin(event){
				event = window.event||event;
				if(event.keyCode==13){
					login();
				}
			}
		</script>
	</head>
	<body>
		<div class="header">
			<div class="am-g">
		    	<h1>Capricornus</h1>
		    	<p>项目与事务跟踪工具<br/>应用于缺陷跟踪、客户服务、需求收集、流程审批、任务跟踪、项目跟踪和敏捷管理等工作领域</p>
		  	</div>
		  	<hr />
		</div>
		<div class="am-g">
			<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
		    	<h3>登录</h3>
			    <form method="post" class="am-form">
			      <label for="username">用户名 / 邮箱:</label>
			      <input id="username" type="text" onkeypress="callLogin(event);"/>
			      <br/>
			      <label for="password">密码:</label>
			      <input id="password" type="password" onkeypress="callLogin(event);"/>
			      <label for="rememberMe" class="am-checkbox am-secondary">
				      <input id="rememberMe" type="checkbox" data-am-ucheck/>
						记住密码
			      </label>
			      <br/><br/>
			      <div class="am-cf">
			        <input id="loginButton" type="button" value="登 录" class="am-btn am-btn-primary am-btn-sm am-fl" onclick="login();"/>
			        <input type="button" value="忘记密码 ^_^?" class="am-btn am-btn-default am-btn-sm am-fr"/>
			      </div>
			    </form>
		    	<hr>
		    	<p>Copyright &copy; 2016 XiaoTuiTui, Inc.</p>
		  	</div>
		</div>
	</body>
</html>