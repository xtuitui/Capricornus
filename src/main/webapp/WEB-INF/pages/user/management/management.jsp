<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${path}/static/sidemenu/css/sidemenu.css"/>
<script type="text/javascript">
	function showDynamicContent(url){
		$('.page').removeClass('shazam');
		$("#loading").fadeIn(500);
		$.get(url, {}, function(data){
			$("#dynamicContent").html(data);
			$("#loading").fadeOut(500);
		});
	}
	
	function resetMenuToggleHeight(){
		var headerHeight;
		if($("#phoneBar").is(":hidden")){
			headerHeight = $("#header-toolbar").css("height");
		}else{
			headerHeight = $("#phoneBar").css("height");
		}
		var menuHeight = $(".menu_toggle").css("top");
		if(menuHeight!=headerHeight){
			$(".menu_toggle").css("top", headerHeight);
		}
	}
</script>
<div class="page">
	  <span class="menu_toggle">
	    <i class="menu_open am-icon-bars am-icon-sm"></i>
	    <i class="menu_close am-icon-close am-icon-sm"></i>
	  </span>
	  <ul class="menu_items">
	    <li><a href="javascript:showDynamicContent('${path}/user/management/toUser');"><i class="icon am-icon-user am-icon-md"></i> User Management</a></li>
	    <li><a href="#"><i class="icon am-icon-group am-icon-md"></i> Group Management</a></li>
	  </ul>
	  <div class="content">
	    <div id="dynamicContent" class="content_inner"></div>
	  </div>
</div>
<script type="text/javascript">
	showDynamicContent("${path}/user/management/toUser");
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