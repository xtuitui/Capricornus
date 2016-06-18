<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/system/permission/permission.css"/>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/common/modal.css"/>
<script type="text/javascript">

	$(document).ready(function(){
		$("#permissionGroup.select2").select2({
			width: "100%" ,
			placeholder: "None Group" ,
			maximumInputLength: 20 ,
		});
		
		$("#systemPermissionGroupModal").on("closed.modal.amui", function(){
			var isReload = $("#reloadSystemPermission").val();
			if(isReload == "true"){
				showDynamicContent("${path}/system/permission/toSystemPermission");
			}
		});
	});

	function showSystemPermissionGroupModal(systemPermissionId, obj){
		$(obj).button("loading");
		$("#systemPermissionId").val(systemPermissionId);
		$("#permissionName").val($("#systemPermissionNameSpan"+systemPermissionId).html());
		var originalGroup = [];
		$("input[name=permissionGroupIdInput"+systemPermissionId+"]").each(function(){
			originalGroup.push($(this).val());
		});
		$("#permissionGroup").val(originalGroup).change();
		$("#systemPermissionGroupModal").modal({
			"onConfirm": function(){
				updateSystemPermissionGroup();
			}, 
			"closeOnConfirm": false
		});
		$(obj).button("reset");
	}
	
	function updateSystemPermissionGroup(){
		modalButtonLoading("saveSystemPermissionGroupButton", "cancelSystemPermissionGroupButton");
		var systemPermissionId = $("#systemPermissionId").val();
		var groupIdStringList = "";
		$("#permissionGroup option:selected").each(function(){
			groupIdStringList += $(this).val() + ",";
		});
		$.post("${path}/system/permission/updateSystemPermissionGroup", {"id":systemPermissionId, "groupIdStringList":groupIdStringList}, function(data){
			if(data.result=="success"){
				$("#reloadSystemPermission").val("true");
				$("#systemPermissionGroupModal").modal("close");
			}
			modalButtonReset("saveSystemPermissionGroupButton", "cancelSystemPermissionGroupButton");
		});
	}
</script>
<div class="admin-content">
	<div class="admin-content-body">
		<div class="am-cf am-padding am-padding-bottom-0">
			<div class="am-fl am-cf">
				<strong class="am-text-primary am-text-lg">System Permission</strong>
			</div>
		</div>
		<br/>
		<div class="am-g">
			<div class="am-u-sm-12 permission-title">
				请把权限赋予给指定的用户组, 该用户组内的成员即可获得该权限.<br/>
				权限之间没有叠加概念, 目前权限只可以赋予用户组, 一个用户组可以分配多个权限, 同理, 一个权限可以赋予多个用户组.
			</div>
		</div>
		<hr data-am-widget="divider" class="am-divider am-divider-dotted" />
		<div class="am-g permission-margin">
			<table class="am-table">
			    <thead>
			        <tr>
			            <th>权限 - (Permission)</th>
			            <th>用户组 - (Group)</th>
			            <th>操作 - (Operation)</th>
			        </tr>
			    </thead>
			    <tbody>
			        <c:forEach var="systemPermission" items="${systemPermissionList}">
						<tr>
			        		<td><span id="systemPermissionNameSpan${systemPermission.id}" class="permission-name">${systemPermission.name}</span><br/><span class="permission-description">${systemPermission.description}</span></td>
			        		<td>
			        			<c:forEach var="group" items="${systemPermission.groups}">
			        				<a href="#">${group.name}</a><input type="hidden" name="permissionGroupIdInput${systemPermission.id}" value="${group.id}" /><br/>
			        			</c:forEach>
			        		</td>
			        		<td class="am-text-middle">
			        			<div class="am-btn-toolbar">
			        				<div class="am-btn-group am-btn-group-xs">
			        					<button class="am-btn am-btn-default am-btn-xs am-text-success" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showSystemPermissionGroupModal(${systemPermission.id}, this);">
			        						<span class="am-icon-group"></span> Group
			        					</button>
			        				</div>
			        			</div>
			        		</td>
			        	</tr>
			        </c:forEach>
			    </tbody>
			</table>
		</div>
	</div>
</div>

<div id="systemPermissionGroupModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			系统权限与用户组
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以编辑系统权限与用户组的关系. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				<form class="am-form am-form-horizontal form-radius">
					<div class="am-form-group">
						<label class="am-u-sm-4 am-form-label">权限 / Permission</label>
						<div class="am-u-sm-8 am-u-end">
							<input id="systemPermissionId" type="hidden"/>
							<input id="reloadSystemPermission" type="hidden" value="false"/>
							<input id="permissionName" type="text" readonly="readonly" />
						</div>
					</div>
					<div class="am-form-group">
						<label for="permissionGroup" class="am-u-sm-4 am-form-label">用户组 / Group</label>
						<div class="am-u-sm-8 am-u-end">
							<select id="permissionGroup" class="am-radius select2" multiple="multiple">
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
			<span id="cancelSystemPermissionGroupButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="saveSystemPermissionGroupButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/pages/common/footer.jsp"%>