<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/common/tooltip.css"/>
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
	
	function modalButtonLoading(sureButtonId, cancelButtonId){
		$("#"+cancelButtonId).hide().removeClass("am-modal-btn");
		$("#"+sureButtonId).button("loading");
	}
	
	function modalButtonReset(sureButtonId, cancelButtonId){
		$("#"+sureButtonId).button("reset");
		$("#"+cancelButtonId).addClass("am-modal-btn").show();
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
						<label for="editDescription" class="am-u-sm-5 am-form-label">描述 / Description</label>
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
<%@include file="/WEB-INF/pages/common/footer.jsp"%>