<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/user/management/user.css"/>
<script type="text/javascript">
	$(document).ready(function(){
		searchUser(1);
		$("#addUserButton").hover(function(){
			$(this).addClass("am-btn-secondary");
		}, function(){
			$(this).removeClass("am-btn-secondary");
		});
		
		$(".select2").select2({
			width: "120%" ,
			placeholder: "Default Option" ,
			maximumInputLength: 20 ,
			//minimumResultsForSearch: Infinity
			//maximumSelectionLength: 2
			//selectOnClose: false
			
			//event
			//change
			//select2:close
			//select2:closing
			//select2:open
			//select2:opening
			//select2:select
			//select2:selecting
			//select2:unselect
			//select2:unselecting
			
			//api
			//select2("open")
			//select2("close")
			//select2()
			//select2("destroy")
			//val(null).trigger("change");
		});
	});
	
	function searchUser(currentPage){
		$("#search").button("loading");
		$("#loading").fadeIn(500);
		var username = $("#username").val();
		var nickname = $("#nickname").val();
		var email = $("#email").val();
		var groupId = $("#group").val();
		var param  = {"username":username, "nickname":nickname, "email":email, "groupId":groupId, "currentPage":currentPage};
		$.post("${path}/user/management/queryUserByPage", param, function(data){
			$("#userTable").html(data);
			$("#search").button("reset");
			$("#loading").fadeOut(500);
		});
	}
	
	function paginate(currentPage){
		searchUser(currentPage);
	}
</script>
<div class="admin-content">
	<div class="admin-content-body">
		<div class="am-cf am-padding am-padding-bottom-0">
			<div class="am-fl am-cf">
				<strong class="am-text-primary am-text-lg">User Management</strong>
			</div>
		</div>
		<br/>
		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-2">
				<div class="am-form-group">
					<label for="username" class="am-form-label">Username</label>
					<input id="username" type="text" class="am-form-field am-radius"/>
				</div>
			</div>
			<div class="am-u-sm-12 am-u-md-2">
				<div class="am-form-group">
					<label for="nickname" class="am-form-label">Nickname</label>
					<input id="nickname" type="text" class="am-form-field am-radius"/>
				</div>
			</div>
			<div class="am-u-sm-12 am-u-md-2">
				<div class="am-form-group">
					<label for="email" class="am-form-label">Email</label>
					<input id="email" type="text" class="am-form-field am-radius"/>
				</div>
			</div>
			<div class="am-u-sm-12 am-u-md-2">
				<div class="am-form-group">
					<label for="group" class="am-form-label">Group</label>
					<select id="group" class="am-radius select2">
						<option value="">All</option>
						<c:forEach var="group" items="${groupList}">
							<option value="${group.id}">${group.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="am-u-sm-12 am-u-md-1">
				<button id="search" type="button" class="am-btn am-btn-success am-round" data-am-loading="{spinner:'spinner', loadingText:'Searching...'}" onclick="searchUser(1);">
					<i class="am-icon-search"></i>
					Search
				</button>
			</div>
			<div class="am-u-sm-12 am-u-md-1 am-u-end">
				<button id="addUserButton" type="button" class="am-btn am-btn-default am-round" data-am-loading="{spinner:'spinner', loadingText:'Adding...'}">
					<i class="am-icon-user-plus"></i>
					Add User
				</button>
			</div>
		</div>

		<div id="userTable" class="am-g"></div>
	</div>
</div>
<%@include file="/WEB-INF/pages/common/footer.jsp"%>