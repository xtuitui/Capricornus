<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/common/tooltip.css"/>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/common/modal.css"/>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/common/custom-table.css"/>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/user/management/group.css"/>
<script type="text/javascript">
	$(document).ready(function(){
		searchGroup(1);
		$("#addGroupButton").hover(function(){
			$(this).addClass("am-btn-secondary");
		}, function(){
			$(this).removeClass("am-btn-secondary");
		});
		
		$("#groupUserModal").on("closed.modal.amui", function(){
			pageButton = "group";
			$("#searchUserModal").html("");
		});
		
		$("#groupUserModal").on("opened.modal.amui", function(){
			pageButton = "userModal";
		});
	});
	
	function showAddGroupModal(){
		$("#addGroupButton").button("loading");
		$("#addGroupName").val("");
		$("#addDescription").val("");
		$("#addGroupModal").modal({
			"onConfirm": function(){
				saveGroup();
			}, 
			"closeOnConfirm": false
		});
		$("#addGroupButton").button("reset");
	}
	
	function saveGroup(){
		modalButtonLoading("saveGroupButton", "cancelGroupButton");
		var result = checkParamBeforeSave();
		if(result===false){
			modalButtonReset("saveGroupButton", "cancelGroupButton");
		}else{
			$.post("${path}/user/management/addGroup", result, function(data){
				if(data.result=="success"){
					$("#groupName").val(result.name);
					searchGroup(1);
					$("#addGroupModal").modal("close");
				}else{
					var messageCode = data.data;
					if(messageCode=="gae"){
						var topOffset = $("#vldTooltip").parent().offset().top;
						var leftOffset = $("#vldTooltip").parent().offset().left;
						showTooltip("vldTooltip", "addGroupName", "用户组已经存在", 30 - topOffset, -leftOffset);
						$("#addGroupName").focus();
					}else{
						alert("添加出错!");
					}
				}
				modalButtonReset("saveGroupButton", "cancelGroupButton");
			});
		}
	}
	
	function checkParamBeforeSave(){
		var groupName = $.trim($("#addGroupName").val());
		var description = $("#addDescription").val();
		var topOffset = $("#vldTooltip").parent().offset().top;
		var leftOffset = $("#vldTooltip").parent().offset().left;
		if($.trim(groupName)==""){
			showTooltip("vldTooltip", "addGroupName", "用户组不能为空", 30 - topOffset, -leftOffset);
			$("#addGroupName").focus();
			return false;
		}
		return {"name":groupName, "description":description};
	}
	
	function showDeleteGroupModal(groupId, obj){
		$(obj).button("loading");
		$("#deleteGroupId").val(groupId);
		$("#deleteGroupName").html($("#groupNameSpan"+groupId).html());
		$("#deleteGroupModal").modal({
			"onConfirm": function(){
				deleteGroup();
			}, 
			"closeOnConfirm": false
		});
		$(obj).button("reset");
	}
	
	function deleteGroup(){
		var groupId = $("#deleteGroupId").val();
		modalButtonLoading("deleteGroupButton", "cancelDeleteButton");
		$.post("${path}/user/management/deleteGroup", {"groupId":groupId}, function(data){
			if(data.result=="success"){
				$("#deleteGroupModal").modal("close");
				$("#groupName").val($("#deleteGroupName").html());
				searchGroup(1);
			}else{
				alert("删除出错!");
			}
			modalButtonReset("deleteGroupButton", "cancelDeleteButton");
		});
	}
	
	function showEditGroupModal(groupId, obj){
		$(obj).button("loading");
		$("#editGroupId").val(groupId);
		$("#editGroupName").val($("#groupNameSpan"+groupId).html());
		$("#editGroupDescription").val($("#groupDescriptionSpan"+groupId).html());
		$("#editGroupModal").modal({
			"onConfirm": function(){
				updateGroup();
			}, 
			"closeOnConfirm": false
		});
		$(obj).button("reset");
	}
	
	function updateGroup(){
		modalButtonLoading("editGroupButton", "cancelEditButton");
		var result = checkParamBeforeUpdate();
		if(result==false){
			modalButtonReset("editGroupButton", "cancelEditButton");
		}else{
			$.post("${path}/user/management/updateGroup", result, function(data){
				if(data.result=="success"){
					$("#groupNameSpan"+result.id).html(result.name);
					$("#groupDescriptionSpan"+result.id).html(result.description);
					$("#editGroupModal").modal("close");
				}else{
					var messageCode = data.data;
					if(messageCode=="gae"){
						var topOffset = $("#editVldTooltip").parent().offset().top;
						var leftOffset = $("#editVldTooltip").parent().offset().left;
						showTooltip("editVldTooltip", "editGroupName", "用户组已经存在", 30 - topOffset, -leftOffset);
						$("#editGroupName").focus();
					}else{
						alert("编辑出错!");
					}
				}
				modalButtonReset("editGroupButton", "cancelEditButton");
			});
		}
	}
	
	function checkParamBeforeUpdate(){
		var groupId = $("#editGroupId").val();
		var groupName = $.trim($("#editGroupName").val());
		var groupDescription = $("#editGroupDescription").val();
		var topOffset = $("#editVldTooltip").parent().offset().top;
		var leftOffset = $("#editVldTooltip").parent().offset().left;
		if($.trim(groupName)==""){
			showTooltip("editVldTooltip", "editGroupName", "用户组不能为空", 30 - topOffset, -leftOffset);
			$("#editGroupName").focus();
			return false;
		}
		return {"id":groupId, "name":groupName, "description":groupDescription};
	}
	
	function showGroupUserModal(groupId, obj){
		searchUserModal(1);
		searchUserByGroup(groupId);
		$(obj).button("loading");
		$("#groupUserGroupId").val(groupId);
		$("#groupUserGroupName").html($("#groupNameSpan"+groupId).html());
		$("#groupUserModal").modal({
			"width": "1300",
			"onConfirm": function(){
				updateGroupUser();
			}, 
			"closeOnConfirm": false
		});
		$(obj).button("reset");
	}
	
	function updateGroupUser(){
		modalButtonLoading("groupUserButton", "cancelGroupUserButton");
		var groupId = $("#groupUserGroupId").val();
		var userIdStringList = "";
		$("#groupUserSelect option").each(function(){
			userIdStringList += $(this).val() + ",";
		});
		$.post("${path}/user/management/updateGroupUser", {"id":groupId, "userIdStringList":userIdStringList}, function(data){
			if(data.result=="success"){
				$("#groupUserModal").modal("close");
			}else{
				alert("出错");
			}
			modalButtonReset("groupUserButton", "cancelGroupUserButton");
		});
	}
	
	function searchUserByGroup(groupId){
		$.post("${path}/user/management/queryUserByGroup", {"id":groupId}, function(data){
			var optionHtml = "";
			var userList = data.data;
			for(var i=0;i<userList.length;i++){
				var user = userList[i];
				optionHtml += "<option value='"+user.id+"' ondblclick='$(this).remove();'>"+user.nickname+" - ("+user.username+")</option>";
			}
			$("#groupUserSelect").html(optionHtml);
		});
	}
	
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
	
	function searchUserModal(currentPage){
		$("#searchUser").button("loading");
		var username = $("#groupUserUsername").val();
		var nickname = $("#groupUserNickname").val();
		var param  = {"username":username, "nickname":nickname, "currentPage":currentPage};
		$.post("${path}/user/management/searchUserModal", param, function(data){
			$("#searchUserModal").html(data);
			$("#searchUser").button("reset");
		});
	}
	
	function addToUserList(obj){
		$(obj).button("loading");
		var select = $("#groupUserSelect");
		var originalOption = [];
		select.children("option").each(function(){
			originalOption.push($(this).val());
		});
		var optionHtml = "";
		$("input[type='checkbox'][name='userCheckBox']:checked").each(function(){
			var tr = $(this).parent().parent().parent();
			var id = tr.find("input[type='hidden'][name='id']").val();
			if($.inArray(id, originalOption)!=-1){
				return true;
			}
			var username = tr.find("input[type='hidden'][name='username']").val();
			var nickname = tr.find("input[type='hidden'][name='nickname']").val();
			optionHtml += "<option value='"+id+"' ondblclick='$(this).remove();'>"+nickname+" - ("+username+")</option>";
		});
		select.append(optionHtml);
		$(obj).button("reset");
	}
	
	function addAllToUserList(obj){
		$("#all").uCheck("check");
		selectAll();
		addToUserList(obj);
	}
	
	function removeFromUserList(obj){
		$(obj).button("loading");
		$("#groupUserSelect option:selected").each(function(){
			$(this).remove();
		});
		$(obj).button("reset");
	}
	
	function removeAllFromUserList(obj){
		$(obj).button("loading");
		$("#groupUserSelect").html("");
		$(obj).button("reset");
	}
	
	var pageButton = "group";
	function paginate(currentPage){
		if(pageButton=="group"){
			searchGroup(currentPage);
		}else if(pageButton=="userModal"){
			searchUserModal(currentPage);
		}
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

<div id="addGroupModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			添加用户组
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以创建用户组, 请在创建成功后添加相应的用户. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				<form class="am-form am-form-horizontal form-radius">
					<div class="am-form-group">
						<label for="addGroupName" class="am-u-sm-5 am-form-label">用户组 / Group Name</label>
						<div class="am-u-sm-7 am-u-end">
							<input id="addGroupName" type="text" placeholder="Group Name" maxlength="50"/>
						</div>
					</div>
					<div class="am-form-group">
						<label for="addDescription" class="am-u-sm-5 am-form-label">描述 / Description</label>
						<div class="am-u-sm-7 am-u-end">
							<textarea id="addDescription" placeholder="Description" maxlength="1000"></textarea>
						</div>
            		</div>
				</form>
			</div>
		</div>
		<hr class="footer-hr"/>
		<div class="am-modal-footer">
			<span id="cancelGroupButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="saveGroupButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
		<div id="vldTooltip" class="vld-tooltip"></div>
	</div>
</div>

<div id="editGroupModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			编辑用户组
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以编辑用户组. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				<form class="am-form am-form-horizontal form-radius">
					<div class="am-form-group">
						<label for="editGroupName" class="am-u-sm-5 am-form-label">用户组 / Group Name</label>
						<div class="am-u-sm-7 am-u-end">
							<input id="editGroupId" type="hidden" />
							<input id="editGroupName" type="text" placeholder="Group Name" maxlength="50"/>
						</div>
					</div>
					<div class="am-form-group">
						<label for="editGroupDescription" class="am-u-sm-5 am-form-label">描述 / Description</label>
						<div class="am-u-sm-7 am-u-end">
							<textarea id="editGroupDescription" placeholder="Description" maxlength="1000"></textarea>
						</div>
            		</div>
				</form>
			</div>
		</div>
		<hr class="footer-hr"/>
		<div class="am-modal-footer">
			<span id="cancelEditButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="editGroupButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
		<div id="editVldTooltip" class="vld-tooltip"></div>
	</div>
</div>

<div id="deleteGroupModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			删除用户组
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以删除用户组, 删除成功后自动移除所有相关依赖. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				确定要删除该用户组吗 ?
				<br/>
				用户组 : <span id="deleteGroupName"></span>
				<input id="deleteGroupId" type="hidden" />
			</div>
		</div>
		<hr class="footer-hr"/>
		<div class="am-modal-footer">
			<span id="cancelDeleteButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="deleteGroupButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
	</div>
</div>

<div id="groupUserModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			用户组成员
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以添加用户组成员. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				<form class="am-form am-form-horizontal form-radius">
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-end">
							<label for="groupUserUsername" class="am-u-sm-4 search-user-label">用户名 / Username</label>
							<label for="groupUserNickname" class="am-u-sm-4 search-user-label">昵称 / Nickname</label>
							<button id="searchUser" type="button" class="am-u-sm-2 am-u-end am-btn am-btn-success am-round" data-am-loading="{spinner:'spinner', loadingText:'Searching...'}" onclick="searchUserModal(1);">
								<i class="am-icon-search"></i>
								Search
							</button>
						</div>
						<div class="am-u-sm-3 am-u-end">
							<label id="groupUserGroupName" class="am-u-sm-12 search-user-label"></label>
							<input id="groupUserGroupId" type="hidden"/>
						</div>
						<div class="am-u-sm-9 am-u-end">
							<div class="am-u-sm-4">
								<input id="groupUserUsername" type="text" placeholder="Username" class="am-u-sm-4"/>
							</div>
							<div class="am-u-sm-4 am-u-end">
								<input id="groupUserNickname" type="text" placeholder="Nickname" class="am-u-sm-4"/>
							</div>
						</div>
						<div class="am-u-sm-3 am-u-end">
							<label class="am-u-sm-12 search-user-label">组内成员</label>
						</div>
					</div>
					<div class="am-form-group">
						<div id="searchUserModal" class="am-u-sm-8"></div>
						<div id="operationArea" class="am-u-sm-1">
							<button type="button" class="am-u-sm-1 am-btn-lg am-btn am-btn-danger am-round" data-am-loading="{spinner:'spinner'}" onclick="addAllToUserList(this);">
								<i class="am-icon-angle-double-right"></i>
							</button>
							<br/><br/><br/>
							<button type="button" class="am-u-sm-1 am-btn-lg am-btn am-btn-danger am-round" data-am-loading="{spinner:'spinner'}" onclick="addToUserList(this);">
								<i class="am-icon-angle-right"></i>
							</button>
							<br/><br/><br/>
							<button type="button" class="am-u-sm-1 am-btn-lg am-btn am-btn-danger am-round" data-am-loading="{spinner:'spinner'}" onclick="removeFromUserList(this);">
								<i class="am-icon-angle-left"></i>
							</button>
							<br/><br/><br/>
							<button type="button" class="am-u-sm-1 am-u-end am-btn-lg am-btn am-btn-danger am-round" data-am-loading="{spinner:'spinner'}" onclick="removeAllFromUserList(this);">
								<i class="am-icon-angle-double-left"></i>
							</button>
						</div>
						<div class="am-u-sm-3 am-u-end">
							<select id="groupUserSelect" multiple="multiple" size="10"></select>
						</div>
            		</div>
				</form>
			</div>
		</div>
		<hr class="footer-hr"/>
		<div class="am-modal-footer">
			<span id="cancelGroupUserButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="groupUserButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/pages/common/footer.jsp"%>