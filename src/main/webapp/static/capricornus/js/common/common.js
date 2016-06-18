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