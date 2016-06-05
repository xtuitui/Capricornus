<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/user/management/user.css"/>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/user/management/group.css"/>
<script type="text/javascript">
	$(document).ready(function(){
		searchGroup(1);
		$("#addGroupButton").hover(function(){
			$(this).addClass("am-btn-secondary");
		}, function(){
			$(this).removeClass("am-btn-secondary");
		});
	});
	
	function searchGroup(currentPage){
		$("#search").button("loading");
		$("#loading").fadeIn(500);
		var groupName = $("#groupName").val();
		var param  = {"groupName":groupName, "currentPage":currentPage};
		$.post("${path}/user/management/queryGroupByPage", param, function(data){
			$("#groupTable").html(data);
			$("#search").button("reset");
			$("#loading").fadeOut(500);
		});
	}
	
	function paginate(currentPage){
		searchGroup(currentPage);
	}
</script>
<div class="admin-content">
	<div class="admin-content-body">
		<div class="am-cf am-padding am-padding-bottom-0">
			<div class="am-fl am-cf">
				<strong class="am-text-primary am-text-lg">Group Management</strong>
			</div>
		</div>
		<br/>
		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-4">
				<div class="am-form-group">
					<label for="groupName" class="am-form-label">Group Name</label>
					<input id="groupName" type="text" class="am-form-field am-radius"/>
				</div>
			</div>
			<div class="am-u-sm-12 am-u-md-1">
				<button id="search" type="button" class="am-btn am-btn-success am-round" data-am-loading="{spinner:'spinner', loadingText:'Searching...'}" onclick="searchGroup(1);">
					<i class="am-icon-search"></i>
					Search
				</button>
			</div>
			<div class="am-u-sm-12 am-u-md-1 am-u-end">
				<button id="addGroupButton" type="button" class="am-btn am-btn-default am-round" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showAddGroupModal();">
					<i class="am-icon-group"></i>
					Add Group
				</button>
			</div>
		</div>
		<div id="groupTable" class="am-g"></div>
	</div>
</div>