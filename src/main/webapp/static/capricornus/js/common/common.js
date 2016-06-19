function modalButtonLoading(sureButtonId, cancelButtonId){
	$("#"+cancelButtonId).hide().removeClass("am-modal-btn");
	$("#"+sureButtonId).button("loading");
}

function modalButtonReset(sureButtonId, cancelButtonId){
	$("#"+sureButtonId).button("reset");
	$("#"+cancelButtonId).addClass("am-modal-btn").show();
}

function closeModal(obj, event) {
	event = window.event || event;
	if ($(event.target).attr("id") == $(obj).attr("id")) {
		$(obj).modal("close");
	}
}

function showTooltip(tooltipId, referenceTargetId, message, topOffset, leftOffset) {
	$("#"+tooltipId).html(message).show().css({
		top : $("#"+referenceTargetId).offset().top + topOffset,
		left : $("#"+referenceTargetId).offset().left + leftOffset
	});
	setTimeout(function() {$("#"+tooltipId).fadeOut(1000);}, 1000);
}

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
		$("#dynamicContent").css("margin-top", headerHeight);
	}
}