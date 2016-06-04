<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Login</title>
		<%@include file="/WEB-INF/pages/common/header.jsp"%>
		<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/index/login.css"/>
		<script type="text/javascript">
			function login(){
				var username = $("#username").val();
				var password = $("#password").val();
				var rememberMe = $("#rememberMe").prop("checked");
				$("#loginButton").button("loading");
				$.post("${path}/index/login",{"username":username, "password":password, "rememberMe":rememberMe},function(data){
					if(data.result=="success"){
						window.location.href = "${path}/index/toWelcome";
					}else{
						$("#loginMessage").modal();
						$("#password").val("");
					}
					$("#loginButton").button("reset");
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
		<%@include file="/WEB-INF/pages/common/loader1.jsp"%>
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
			      <div class="am-form-icon">
			      	  <i class="am-icon-user"></i>
				      <input id="username" type="text" class="am-form-field" onkeypress="callLogin(event);" placeholder="Username" maxlength="20"/>
			      </div>
			      <br/>
			      <label for="password">密码:</label>
			      <div class="am-form-icon">
			      	  <i class="am-icon-key"></i>
				      <input id="password" type="password" class="am-form-field" onkeypress="callLogin(event);" placeholder="Password" maxlength="16"/>
			      </div>
			      <label for="rememberMe" class="am-checkbox am-secondary">
				      <input id="rememberMe" type="checkbox" data-am-ucheck/>
						记住密码
			      </label>
			      <br/><br/>
			      <div class="am-cf">
			        <button id="loginButton" type="button" class="am-btn am-btn-primary am-round" data-am-loading="{spinner:'spinner', loadingText:'登录中...'}" onclick="login();">
				        <i class="am-icon-home">&nbsp;</i>
			        	登 录
			        </button>
			        <input type="button" value="忘记密码 ^_^?" class="am-btn am-btn-default am-btn-sm am-fr am-round"/>
			      </div>
			    </form>
		    	<hr>
		  	</div>
			<div class="login-footer">
		    	<%@include file="/WEB-INF/pages/common/footer.jsp"%>
			</div>
		</div>
		<div id="loginMessage" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
		  <div class="am-modal-dialog">
		    <div class="am-modal-hd">
		    	登录信息
		      <a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		    </div>
		    <div class="am-modal-bd">
		      	用户名或者密码错误, 请重新填写...
		    </div>
		  </div>
		</div>
	</body>
</html>