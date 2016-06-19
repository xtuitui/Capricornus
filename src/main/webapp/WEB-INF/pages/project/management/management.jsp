<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${path}/static/sidemenu/css/sidemenu.css"/>
<div class="page">
	<span class="menu_toggle">
		<i class="menu_open am-icon-bars am-icon-sm"></i>
		<i class="menu_close am-icon-close am-icon-sm"></i>
	</span>
	<ul class="menu_items">
		<li><a href="javascript:showDynamicContent('${path}/project/project/toProject');"><i class="icon am-icon-database am-icon-md"></i> Project Management</a></li>
		<li><a href="javascript:showDynamicContent('${path}/project/category/toCategory');"><i class="icon am-icon-binoculars am-icon-md"></i> Category Management</a></li>
	</ul>
	<div class="content">
		<div id="dynamicContent" class="content_inner"></div>
	</div>
</div>
<script type="text/javascript">
	showDynamicContent("${path}/project/project/toProject");
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