<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${path}/static/switch/css/switch.css"/>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/common/tooltip.css"/>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/user/management/user.css"/>
<script type="text/javascript">
	$(document).ready(function(){
		searchUser(1);
		$("#addUserButton").hover(function(){
			$(this).addClass("am-btn-secondary");
		}, function(){
			$(this).removeClass("am-btn-secondary");
		});
		
		$("#group.select2").select2({
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
		
		$("#userGroup.select2").select2({
			width: "100%" ,
			placeholder: "None Group" ,
			maximumInputLength: 20 ,
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
	
	function showAddUserModal(){
		$("#addUserButton").button("loading");
		$("#addUsername").val("");
		$("#addNickname").val("");
		$("#addPassword").val("");
		$("#addPasswordConfirm").val("");
		$("#addEmail").val("");
		$("#invite").prop("checked", false);
		$("#addUserModal").modal({
			"onConfirm": function(){
				saveUser();
			}, 
			"closeOnConfirm": false
		});
		$("#addUserButton").button("reset");
	}
	
	function saveUser(){
		modalButtonLoading("saveUserButton", "cancelUserButton");
		var result = checkParamBeforeSubmit();
		if(result===false){
			modalButtonReset("saveUserButton", "cancelUserButton");
		}else{
			$.post("${path}/user/management/addUser", result, function(data){
				if(data.result=="success"){
					//need to go user profile page
					modalButtonReset("saveUserButton", "cancelUserButton");
					$("#addUserModal").modal("close");
				}else{
					var messageCode = data.data;
					if(messageCode=="uae"){
						var topOffset = $("#vldTooltip").parent().offset().top;
						var leftOffset = $("#vldTooltip").parent().offset().left;
						showTooltip("vldTooltip", "addUsername", "用户名已经存在", 30 - topOffset, -leftOffset);
						$("#addUsername").focus();
						modalButtonReset("saveUserButton", "cancelUserButton");
					}
				}
			});
		}
	}
	
	function modalButtonLoading(sureButtonId, cancelButtonId){
		$("#"+cancelButtonId).hide().removeClass("am-modal-btn");
		$("#"+sureButtonId).button("loading");
	}
	
	function modalButtonReset(sureButtonId, cancelButtonId){
		$("#"+sureButtonId).button("reset");
		$("#"+cancelButtonId).addClass("am-modal-btn").show();
	}
	
	function checkParamBeforeSubmit(){
		var username = $.trim($("#addUsername").val());
		var nickname = $("#addNickname").val();
		var password = $("#addPassword").val();
		var passwordConfirm = $("#addPasswordConfirm").val();
		var email = $.trim($("#addEmail").val());
		var topOffset = $("#vldTooltip").parent().offset().top;
		var leftOffset = $("#vldTooltip").parent().offset().left;
		if($.trim(username)==""){
			showTooltip("vldTooltip", "addUsername", "用户名不能为空", 30 - topOffset, -leftOffset);
			$("#addUsername").focus();
			return false;
		}
		if($.trim(password)==""){
			showTooltip("vldTooltip", "addPassword", "密文不能为空", 30 - topOffset, -leftOffset);
			$("#addPassword").focus();
			return false;
		}
		if(passwordConfirm!=password){
			showTooltip("vldTooltip", "addPasswordConfirm", "密文不一致", 30 - topOffset, -leftOffset);
			$("#addPasswordConfirm").focus();
			return false;
		}
		if($.trim(email)==""){
			showTooltip("vldTooltip", "addEmail", "邮箱不能为空", 30 - topOffset, -leftOffset);
			$("#addEmail").focus();
			return false;
		}
		var invite = $("#invite").prop("checked");
		return {"username":username, "nickname":nickname, "password":password, "email":email, "invite":invite};
	}
	
	function showUserGroupModal(userId, obj){
		$(obj).button("loading");
		$("#userGroupUserId").val(userId);
		$("#userGroupUsername").val($("#usernameA"+userId).html());
		$("#userGroupNickname").val($("#nicknameTd"+userId).html());
		var originalGroup = [];
		$("input[name=userGroupIdInput"+userId+"]").each(function(){
			originalGroup.push($(this).val());
		});
		$("#userGroup").val(originalGroup).change();
		$("#userGroupModal").modal({
			"onConfirm": function(){
				updateUserGroup();
			}, 
			"closeOnConfirm": false
		});
		$(obj).button("reset");
	}
	
	function updateUserGroup(){
		modalButtonLoading("saveUserButton", "cancelUserButton");
		var userId = $("#userGroupUserId").val();
		var groupIdStringList = "";
		$("#userGroup option:selected").each(function(){
			groupIdStringList += $(this).val() + ",";
		});
		$.post("${path}/user/management/updateUserGroup", {"id":userId, "groupIdStringList":groupIdStringList}, function(data){
			if(data.result=="success"){
				searchUser($("#currentPage").val());
				$("#userGroupModal").modal("close");
			}
			modalButtonReset("saveUserButton", "cancelUserButton");
		});
	}
	
	function showDeleteUserModal(userId, obj){
		$(obj).button("loading");
		var username = $("#usernameA"+userId).html();
		var nickname = $("#nicknameTd"+userId).html();
		$("#deleteNickname").html(nickname);
		$("#deleteUsername").html(username);
		$("#deleteUserId").val(userId);
		$("#deleteUserModal").modal({
			"onConfirm": function(){
				deleteUser();
			}, 
			"closeOnConfirm": false
		});
		$(obj).button("reset");
	}
	
	function deleteUser(){
		modalButtonLoading("deleteUserButton", "cancelDeleteButton");
		var userId = $("#deleteUserId").val();
		$.post("${path}/user/management/deleteUser", {"userId":userId}, function(data){
			if(data.result=="success"){
				$("#deleteUserModal").modal("close");
				$("#username").val($("#deleteUsername").html());
				$("#nickname").val($("#deleteNickname").html());
				searchUser(1);
			}else{
				alert("删除出错!");
			}
			modalButtonReset("deleteUserButton", "cancelDeleteButton");
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
				<button id="addUserButton" type="button" class="am-btn am-btn-default am-round" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showAddUserModal();">
					<i class="am-icon-user-plus"></i>
					Add User
				</button>
			</div>
		</div>
		<div id="userTable" class="am-g"></div>
	</div>
</div>

<div id="addUserModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			添加用户
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以创建用户, 请在创建成功后授予相应的权限. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				<form class="am-form am-form-horizontal form-radius">
					<div class="am-form-group">
						<label for="addUsername" class="am-u-sm-4 am-form-label">用户 / Username</label>
						<div class="am-u-sm-8 am-u-end">
							<input id="addUsername" type="text" placeholder="Username" maxlength="20"/>
						</div>
					</div>
					<div class="am-form-group">
						<label for="addNickname" class="am-u-sm-4 am-form-label">昵称 / Nickname</label>
						<div class="am-u-sm-8 am-u-end">
							<input id="addNickname" type="text" placeholder="Nickname" maxlength="40" />
						</div>
					</div>
					<div class="am-form-group">
						<label for="addPassword" class="am-u-sm-4 am-form-label">密码 / Password</label>
						<div class="am-u-sm-8 am-u-end">
							<input id="addPassword" type="password" placeholder="Password" maxlength="16"/>
						</div>
					</div>
					<div class="am-form-group">
						<label for="addPasswordConfirm" class="am-u-sm-4 am-form-label">确认 / Confirm</label>
						<div class="am-u-sm-8 am-u-end">
							<input id="addPasswordConfirm" type="password" placeholder="Confirm Password" maxlength="16"/>
						</div>
					</div>
					<div class="am-form-group">
						<label for="addEmail" class="am-u-sm-4 am-form-label">邮箱 / Email</label>
						<div class="am-u-sm-8 am-u-end">
							<input id="addEmail" type="text" placeholder="Email@xiaotuitui.com" maxlength="50"/>
						</div>
            		</div>
            		<div class="am-form-group">
						<label for="invite" class="am-u-sm-4 am-form-label">邀请 / Invite</label>
						<div class="am-u-sm-8 am-u-end">
							<div class="model-13">
								<div class="checkbox">
									<input id="invite" type="checkbox" value="y"/><label></label>
								</div>
							</div>
						</div>
            		</div>
				</form>
			</div>
		</div>
		<hr class="footer-hr"/>
		<div class="am-modal-footer">
			<span id="cancelUserButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="saveUserButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
		<div id="vldTooltip" class="vld-tooltip"></div>
	</div>
</div>

<div id="userGroupModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			用户与用户组
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以编辑用户与用户组的关系. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				<form class="am-form am-form-horizontal form-radius">
					<div class="am-form-group">
						<label class="am-u-sm-4 am-form-label">用户 / Username</label>
						<div class="am-u-sm-8 am-u-end">
							<input id="userGroupUserId" type="hidden"/>
							<input id="userGroupUsername" type="text" readonly="readonly" />
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-4 am-form-label">昵称 / Nickname</label>
						<div class="am-u-sm-8 am-u-end">
							<input id="userGroupNickname" type="text" readonly="readonly" />
						</div>
					</div>
					<div class="am-form-group">
						<label for="userGroup" class="am-u-sm-4 am-form-label">用户组 / Group</label>
						<div class="am-u-sm-8 am-u-end">
							<select id="userGroup" class="am-radius select2" multiple="multiple">
								<c:forEach var="group" items="${groupList}">
									<option value="${group.id}">${group.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</form>
			</div>
		</div>
		<hr class="footer-hr"/>
		<div class="am-modal-footer">
			<span id="cancelUserGroupButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="saveUserGroupButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
	</div>
</div>

<div id="deleteUserModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			删除用户
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以删除用户, 删除成功后自动移除所有相关依赖. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				确定要删除该用户吗 ?
				<br/>
				用户 : <span id="deleteNickname"></span> - (<span id="deleteUsername"></span>)
				<input id="deleteUserId" type="hidden" />
			</div>
		</div>
		<hr class="footer-hr"/>
		<div class="am-modal-footer">
			<span id="cancelDeleteButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="deleteUserButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/pages/common/footer.jsp"%>