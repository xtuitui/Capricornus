<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Welcome</title>
		<%@include file="/WEB-INF/pages/common/header.jsp"%>
		<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/index/welcome.css"/>
		<script type="text/javascript">
		$(document).ready(function(){
			$('#admin-fullscreen').on('click', function() {
				$.AMUI.fullscreen.toggle();
		    });
		});
		</script>
	</head>
	<body>
		<%@include file="/WEB-INF/pages/common/loader1.jsp"%>
		<header id="header-toolbar" class="am-topbar am-topbar-inverse admin-header">
			<div id="phoneBar" class="am-show-sm-only">
				<a href="${path}/index/toWelcome" class="am-icon-home am-icon-sm"></a>
				<span id="titleSpan"><b>Capricornus</b></span>
				<button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success" data-am-collapse="{target:'#topbar-collapse'}"><span class="am-sr-only">导航切换</span><span class="am-icon-bars"></span></button>
			</div>
			<div id="topbar-collapse" class="am-collapse am-topbar-collapse">
				<ul class="am-nav am-nav-pills am-topbar-nav am-topbar-left admin-header-list">
		      		<li><a href="${path}/index/toWelcome" class="am-icon-home am-icon-sm am-hide-sm-only"></a></li>
		      		<li class="am-dropdown" data-am-dropdown>
		        		<a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
		          			<span class="am-icon-dashboard"></span> DashBoard <span class="am-icon-caret-down"></span>
	        			</a>
		        		<ul class="am-dropdown-content">
							<li><a href="javascript:;"><span class="am-icon-desktop"></span> DashBoard-1</a></li>
							<li><a href="javascript:;"><span class="am-icon-desktop"></span> DashBoard-2</a></li>
							<li><a href="javascript:;"><span class="am-icon-desktop"></span> DashBoard-3</a></li>
							<li class="am-divider"></li>
							<li><a href="javascript:;"><span class="am-icon-cog"></span> Configuration</a></li>
		        		</ul>
		      		</li>
		      		<li class="am-dropdown" data-am-dropdown>
		        		<a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
		          			<span class="am-icon-folder-open"></span> Project <span class="am-icon-caret-down"></span>
	        			</a>
		        		<ul class="am-dropdown-content">
							<li><a href="javascript:;"><img alt="short-cut" src="${path}/static/capricornus/image/background.jpg" class="am-circle projectImg" />&nbsp;&nbsp;&nbsp;Project A</a></li>
							<li><a href="javascript:;"><img alt="short-cut" src="${path}/static/capricornus/image/background.jpg" class="am-circle projectImg" />&nbsp;&nbsp;&nbsp;Project B</a></li>
							<li><a href="javascript:;"><img alt="short-cut" src="${path}/static/capricornus/image/background.jpg" class="am-circle projectImg" />&nbsp;&nbsp;&nbsp;Project C</a></li>
							<li class="am-divider"></li>
							<li><a href="javascript:;"><span class="am-icon-cubes"></span> View All Project</a></li>
		        		</ul>
		      		</li>
		      		<li class="am-dropdown" data-am-dropdown>
		        		<a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
		          			<span class="am-icon-info-circle"></span> Issue <span class="am-icon-caret-down"></span>
	        			</a>
		        		<ul class="am-dropdown-content">
							<li><a href="javascript:;"><span class="am-icon-search"></span> Search For Issue</a></li>
							<li class="am-divider"></li>
							<li><a href="javascript:;"><span class="am-icon-info"></span> Issue-1</a></li>
							<li><a href="javascript:;"><span class="am-icon-info"></span> Issue-2</a></li>
							<li><a href="javascript:;"><span class="am-icon-info"></span> Issue-3</a></li>
							<li class="am-divider"></li>
							<li><a href="javascript:;"><span class="am-icon-filter"></span> Filter-1</a></li>
							<li><a href="javascript:;"><span class="am-icon-filter"></span> Filter-2</a></li>
							<li><a href="javascript:;"><span class="am-icon-filter"></span> Filter-3</a></li>
							<li class="am-divider"></li>
							<li><a href="javascript:;"><span class="am-icon-upload"></span> Import Issue</a></li>
		        		</ul>
		      		</li>
		      		<li class="am-dropdown" data-am-dropdown>
		        		<a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
		          			<span class="am-icon-rocket"></span> Agile <span class="am-icon-caret-down"></span>
	        			</a>
		        		<ul class="am-dropdown-content">
							<li><a href="javascript:;"><span class="am-icon-building"></span> Ban-1</a></li>
							<li><a href="javascript:;"><span class="am-icon-building-o"></span> Ban-2</a></li>
							<li><a href="javascript:;"><span class="am-icon-building"></span> Ban-3</a></li>
							<li class="am-divider"></li>
							<li><a href="javascript:;"><span class="am-icon-hand-o-right"></span> Getting Started</a></li>
		        		</ul>
		      		</li>
		      		<li>
		      			<a href="javascript:;" class="am-btn am-btn-secondary am-round"><i class="am-icon-plus"></i><b> Create </b></a>
		      		</li>
		    	</ul>
		    	<ul id="subToolbar-right" class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
				    <li><a href="javascript:;"><span class="am-icon-question-circle"></span> Help </a></li>
		      		<li class="am-dropdown" data-am-dropdown>
		        		<a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
		          			<span class="am-icon-cogs"></span> Setting <span class="am-icon-caret-down"></span>
		        		</a>
		        		<ul class="am-dropdown-content">
							<li><a href="#"><span class="am-icon-database"></span> Project Management</a></li>
							<li><a href="#"><span class="am-icon-users"></span> User Management</a></li>
							<li><a href="#"><span class="am-icon-gavel"></span> Project Configuration</a></li>
							<li><a href="#"><span class="am-icon-cog"></span> System Configuration</a></li>
		        		</ul>
		      		</li>
		      		
		      		<li class="am-dropdown" data-am-dropdown>
		        		<a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
		          			<img alt="short-cut" src="${path}/static/capricornus/image/background.jpg" class="am-circle userImg" /> Username <span class="am-icon-caret-down"></span>
		        		</a>
		        		<ul class="am-dropdown-content">
							<li><a href="#"><span class="am-icon-user"></span> Profile</a></li>
							<li class="am-divider"></li>
							<li><a href="#"><span class="am-icon-sign-out"></span> Sign Out</a></li>
		        		</ul>
		      		</li>
		      		<li class="am-hide-sm-only"><a id="admin-fullscreen" href="javascript:;"><span class="am-icon-arrows-alt"></span></a></li>
	    		</ul>
		  	</div>
		</header>
		<div>
			<iframe src="" width="100%" height="100%"></iframe>
		</div>
	</body>
</html>