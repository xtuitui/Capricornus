<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${path}/static/sidemenu/css/sidemenu.css"/>
<div class="page">
	<span class="menu_toggle">
		<i class="menu_open am-icon-bars am-icon-sm"></i>
		<i class="menu_close am-icon-close am-icon-sm"></i>
	</span>
	<ul class="menu_items">
		<li><a href="javascript:showDynamicContent('${path}/system/permission/toSystemPermission');"><i class="icon am-icon-lock am-icon-md"></i> System Permission</a></li>
	</ul>
	<div class="content">
		<div id="dynamicContent" class="content_inner"></div>
	</div>
</div>
<script type="text/javascript">
	//showDynamicContent("${path}/user/management/toUser");
	$(window).resize(function(){
		resetMenuToggleHeight();
	});
	var $page = $('.page');
	$('.menu_toggle').on('click', function () {
	    $page.toggleClass('shazam');
	});
	$('.content').on('click', function () {
	    $page.removeClass('shazam');
	});
	resetMenuToggleHeight();
</script>