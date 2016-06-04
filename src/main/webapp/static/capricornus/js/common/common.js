function closeModal(obj, event) {
	event = window.event || event;
	if ($(event.target).attr("id") == $(obj).attr("id")) {
		$(obj).modal("close");
	}
}

function showTooltip(tooltipId, referenceTargetId, message, topOffset, leftOffset) {
	$("#"+tooltipId).html(message).show().css({
		left : $("#"+referenceTargetId).offset().left + topOffset,
		top : $("#"+referenceTargetId).offset().top + leftOffset
	});
	setTimeout(function() {$("#"+tooltipId).fadeOut(1000);}, 1000);
}