<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="loading">
	<div id="loading-center">
		<div id="loading-center-absolute">
			<div class="loader" id="loader_4"></div>
			<div class="loader" id="loader_3"></div>
			<div class="loader" id="loader_2"></div>
			<div class="loader" id="loader_1"></div>
		</div>
	</div>
</div>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/common/loader1.css"/>
<script type="text/javascript">
	$(document).ready(function(){
		$("#loading").fadeOut(500);
	});
</script>