<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Login</title>
		<%@include file="/WEB-INF/pages/common/header.jsp"%>
		<style>
			.header {
			  text-align: center;
			}
			.header h1 {
			  font-size: 200%;
			  color: #333;
			  margin-top: 30px;
			}
			.header p {
			  font-size: 14px;
			}
		</style>
		<script type="text/javascript" lang="javascript">
			function login(obj){
				var username = $("#username").val();
				var password = $("#password").val();
				$.post("${path}/index/login",{"username":username, "password":password},function(data){
					alert(JSON.stringify(data));
				});
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
			      <input id="username" type="text" />
			      <br/>
			      <label for="password">密码:</label>
			      <input id="password" type="password" />
			      <br/>
			      <label for="remember-me">
			      <input id="remember-me" type="checkbox"/>
					记住密码
			      </label>
			      <br/>
			      <div class="am-cf">
			        <input type="button" value="登 录" class="am-btn am-btn-primary am-btn-sm am-fl" onclick="login(this);"/>
			        <input type="button" value="忘记密码 ^_^?" class="am-btn am-btn-default am-btn-sm am-fr"/>
			      </div>
			    </form>
		    	<hr>
		    	<p>Copyright &copy; 2016 XiaoTuiTui, Inc.</p>
		  	</div>
		</div>
	</body>
</html>