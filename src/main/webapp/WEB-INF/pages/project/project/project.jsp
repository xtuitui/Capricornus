<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/common/tooltip.css"/>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/common/modal.css"/>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/project/project/project.css"/>
<script type="text/javascript">

	$(document).ready(function(){
		$("#addProjectButton").hover(function(){
			$(this).addClass("am-btn-secondary");
		}, function(){
			$(this).removeClass("am-btn-secondary");
		});
		
		$(".select2").select2({
			width: "100%" ,
			maximumInputLength: 20 ,
		});
		
		$("#addProjectModal").on("closed.modal.amui", function(){
			var isReload = $("#reloadProject").val();
			if(isReload == "true"){
				showDynamicContent("${path}/project/project/toProject");
			}
		});
		
		$("#deleteProjectModal").on("closed.modal.amui", function(){
			var isReload = $("#reloadProjectAfterDelete").val();
			if(isReload == "true"){
				showDynamicContent("${path}/project/project/toProject");
			}
		});
		
		$("#editProjectModal").on("closed.modal.amui", function(){
			var isReload = $("#reloadProjectAfterUpdate").val();
			if(isReload == "true"){
				showDynamicContent("${path}/project/project/toProject");
			}
		});
	});
	
	function showAddProjectModal(){
		$("#addProjectButton").button("loading");
		$("#addProjectName").val("");
		$("#addDescription").val("");
		$("#addProjectModal").modal({
			"onConfirm": function(){
				saveProject();
			}, 
			"closeOnConfirm": false
		});
		$("#addProjectButton").button("reset");
	}
	
	function saveProject(){
		modalButtonLoading("saveProjectButton", "cancelProjectButton");
		var result = checkParamBeforeSave();
		if(result===false){
			modalButtonReset("saveProjectButton", "cancelProjectButton");
		}else{
			$.post("${path}/project/project/addProject", result, function(data){
				if(data.result=="success"){
					$("#addProjectModal").modal("close");
					$("#reloadProject").val("true");
				}else{
					var topOffset = $("#vldTooltip").parent().offset().top;
					var leftOffset = $("#vldTooltip").parent().offset().left;
					var messageCode = data.data;
					if(messageCode=="pae"){
						showTooltip("vldTooltip", "addProjectName", "项目已经存在", 30 - topOffset, -leftOffset);
						$("#addProjectName").focus();
					}else if(messageCode=="pkae"){
						showTooltip("vldTooltip", "addProjectKey", "键值已经存在", 30 - topOffset, -leftOffset);
						$("#addProjectKey").focus();
					}else{
						alert("添加出错!");
					}
				}
				modalButtonReset("saveProjectButton", "cancelProjectButton");
			});
		}
	}
	
	function checkParamBeforeSave(){
		var projectName = $.trim($("#addProjectName").val());
		var projectKey = $.trim($("#addProjectKey").val());
		var categoryId = $("#addProjectCategory").val();
		var description = $("#addDescription").val();
		
		var topOffset = $("#vldTooltip").parent().offset().top;
		var leftOffset = $("#vldTooltip").parent().offset().left;
		if($.trim(projectName)==""){
			showTooltip("vldTooltip", "addProjectName", "名称不能为空", 30 - topOffset, -leftOffset);
			$("#addProjectName").focus();
			return false;
		}
		if($.trim(projectKey)==""){
			showTooltip("vldTooltip", "addProjectKey", "键值不能为空", 30 - topOffset, -leftOffset);
			$("#addProjectKey").focus();
			return false;
		}
		return {"name":projectName, "projectKey":projectKey, "category.id":categoryId, "description":description};
	}
	
	function showDeleteProjectModal(projectId, obj){
		$(obj).button("loading");
		$("#deleteProjectId").val(projectId);
		$("#deleteProjectName").html($("#projectNameSpan"+projectId).html());
		$("#deleteProjectModal").modal({
			"onConfirm": function(){
				deleteProject();
			}, 
			"closeOnConfirm": false
		});
		$(obj).button("reset");
	}
	
	function deleteProject(){
		var projectId = $("#deleteProjectId").val();
		modalButtonLoading("deleteProjectButton", "cancelDeleteButton");
		$.post("${path}/project/project/deleteProject", {"projectId":projectId}, function(data){
			if(data.result=="success"){
				$("#deleteProjectModal").modal("close");
				$("#reloadProjectAfterDelete").val("true");
			}else{
				alert("删除出错!");
			}
			modalButtonReset("deleteProjectButton", "cancelDeleteButton");
		});
	}
	
	function showEditProjectModal(projectId, obj){
		$(obj).button("loading");
		$("#editProjectId").val(projectId);
		$("#editProjectName").val($("#projectNameSpan"+projectId).html());
		$("#editProjectKey").val($("#projectKeySpan"+projectId).html());
		$("#editProjectCategory").val($("#projectCategoryHidden"+projectId).val()).change();
		$("#editDescription").val($("#projectDescriptionSpan"+projectId).html());
		$("#editProjectModal").modal({
			"onConfirm": function(){
				updateProject();
			}, 
			"closeOnConfirm": false
		});
		$(obj).button("reset");
	}
	
	function updateProject(){
		modalButtonLoading("editProjectButton", "cancelEditButton");
		var result = checkParamBeforeUpdate();
		if(result==false){
			modalButtonReset("editProjectButton", "cancelEditButton");
		}else{
			$.post("${path}/project/project/updateProject", result, function(data){
				if(data.result=="success"){
					$("#editProjectModal").modal("close");
					$("#reloadProjectAfterUpdate").val("true");
				}else{
					var topOffset = $("#editVldTooltip").parent().offset().top;
					var leftOffset = $("#editVldTooltip").parent().offset().left;
					var messageCode = data.data;
					if(messageCode=="pae"){
						showTooltip("editVldTooltip", "editProjectName", "项目已经存在", 30 - topOffset, -leftOffset);
						$("#editProjectName").focus();
					}else if(messageCode=="pkae"){
						showTooltip("editVldTooltip", "editProjectKey", "键值已经存在", 30 - topOffset, -leftOffset);
						$("#editProjectKey").focus();
					}else{
						alert("编辑出错!");
					}
				}
				modalButtonReset("editProjectButton", "cancelEditButton");
			});
		}
	}
	
	function checkParamBeforeUpdate(){
		var projectId = $("#editProjectId").val();
		var projectName = $.trim($("#editProjectName").val());
		var projectKey = $.trim($("#editProjectKey").val());
		var categoryId = $("#editProjectCategory").val();
		var description = $("#editDescription").val();
		
		var topOffset = $("#editVldTooltip").parent().offset().top;
		var leftOffset = $("#editVldTooltip").parent().offset().left;
		if($.trim(projectName)==""){
			showTooltip("editVldTooltip", "editProjectName", "名称不能为空", 30 - topOffset, -leftOffset);
			$("#editProjectName").focus();
			return false;
		}
		if($.trim(projectKey)==""){
			showTooltip("editVldTooltip", "editProjectKey", "键值不能为空", 30 - topOffset, -leftOffset);
			$("#editProjectKey").focus();
			return false;
		}
		return {"id":projectId, "name":projectName, "projectKey":projectKey, "category.id":categoryId, "description":description};
	}

</script>
<div class="admin-content">
	<div class="admin-content-body">
		<div class="am-cf am-padding am-padding-bottom-0">
			<div class="am-fl am-cf">
				<strong class="am-text-primary am-text-lg">Project Management</strong>
			</div>
		</div>
		<br/>
		<div class="am-g">
			<div class="am-u-sm-12 project-title">
				您现在可以管理项目.
			</div>
		</div>
    	<button id="addProjectButton" type="button" class="am-btn am-btn-default am-round" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showAddProjectModal(this);">
			<i class="am-icon-plus"></i>
			Add Project
		</button>
		<hr data-am-widget="divider" class="am-divider am-divider-dotted" />
		<div class="am-g project-margin">
			<table class="am-table">
				 <thead>
			        <tr>
						<th>名称 - (Name)</th>
						<th>描述 - (Description)</th>
			            <th>操作 - (Operation)</th>
			        </tr>
			    </thead>
				<tbody>
				
					<!-- Category Project -->
					<c:forEach var="category" items="${categoryList}">
						<c:if test="${!empty category.projects}">
							<tr class="category-name">
								<td colspan="3">
									<div class="am-cf am-padding am-padding-bottom-0">
										<div class="am-fl am-cf">
											<strong class="am-text-warning am-text-lg">[Category] - ${category.name}</strong>
										</div>
									</div>
								</td>
							</tr>
							<c:forEach var="project" items="${category.projects}">
								<tr>
									<td><a href="#"><span id="projectNameSpan${project.id}" class="project-name">${project.name}</span></a><br/><span id="projectKeySpan${project.id}" class="project-key">${project.projectKey}</span><input id="projectCategoryHidden${project.id}" type="hidden" value="${category.id}"/></td>
									<td><span id="projectDescriptionSpan${project.id}" class="project-description">${project.description}</span></td>
									<td class="am-text-middle">
					        			<div class="am-btn-toolbar">
					        				<div class="am-btn-group am-btn-group-xs">
					        					<button class="am-btn am-btn-default am-btn-xs am-text-secondary" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showEditProjectModal(${project.id}, this);">
													<span class="am-icon-pencil-square-o"></span> Edit
												</button>
												<button class="am-btn am-btn-default am-btn-xs am-text-danger" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showDeleteProjectModal(${project.id}, this);">
													<span class="am-icon-trash-o"></span> Delete
												</button>
					        				</div>
					        			</div>
					        		</td>
								</tr>
							</c:forEach>
						</c:if>
					</c:forEach>
					
					<!-- None Category -->
					<tr class="category-name">
						<td colspan="3">
							<div class="am-cf am-padding am-padding-bottom-0">
								<div class="am-fl am-cf">
									<strong class="am-text-warning am-text-lg">[Category] - None Category</strong>
								</div>
							</div>
						</td>
					</tr>
					<c:forEach var="project" items="${noneCategoryProjectList}">
						<tr>
							<td><a href="#"><span id="projectNameSpan${project.id}" class="project-name">${project.name}</span></a><br/><span id="projectKeySpan${project.id}" class="project-key">${project.projectKey}</span><input id="projectCategoryHidden${project.id}" type="hidden"/></td>
							<td><span id="projectDescriptionSpan${project.id}" class="project-description">${project.description}</span></td>
							<td class="am-text-middle">
			        			<div class="am-btn-toolbar">
			        				<div class="am-btn-group am-btn-group-xs">
			        					<button class="am-btn am-btn-default am-btn-xs am-text-secondary" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showEditProjectModal(${project.id}, this);">
											<span class="am-icon-pencil-square-o"></span> Edit
										</button>
										<button class="am-btn am-btn-default am-btn-xs am-text-danger" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showDeleteProjectModal(${project.id}, this);">
											<span class="am-icon-trash-o"></span> Delete
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

<div id="addProjectModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			添加项目
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以创建项目. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				<form class="am-form am-form-horizontal form-radius">
					<div class="am-form-group">
						<label for="addProjectName" class="am-u-sm-5 am-form-label">项目名称 / Name</label>
						<div class="am-u-sm-7 am-u-end">
							<input id="reloadProject" type="hidden" value="false"/>
							<input id="addProjectName" type="text" placeholder="Project Name" maxlength="50"/>
						</div>
					</div>
					<div class="am-form-group">
						<label for="addProjectKey" class="am-u-sm-5 am-form-label">项目键值 / Key</label>
						<div class="am-u-sm-7 am-u-end">
							<input id="addProjectKey" type="text" placeholder="Project Key" maxlength="50"/>
						</div>
					</div>
					<div class="am-form-group">
						<label for="addProjectCategory" class="am-u-sm-5 am-form-label">项目分类 / Category</label>
						<div class="am-u-sm-7 am-u-end">
							<select id="addProjectCategory" class="am-radius select2">
								<option value="" selected='selected'>None Category</option>
								<c:forEach var="category" items="${categoryList}">
									<option value="${category.id}">${category.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="am-form-group">
						<label for="addDescription" class="am-u-sm-5 am-form-label">项目描述 / Description</label>
						<div class="am-u-sm-7 am-u-end">
							<textarea id="addDescription" placeholder="Description" maxlength="1000"></textarea>
						</div>
            		</div>
				</form>
			</div>
		</div>
		<hr class="footer-hr"/>
		<div class="am-modal-footer">
			<span id="cancelProjectButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="saveProjectButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
		<div id="vldTooltip" class="vld-tooltip"></div>
	</div>
</div>

<div id="deleteProjectModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			删除项目
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以删除项目, 会自动删除项目所有关联. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				确定要删除该项目吗 ?
				<br/>
				项目 : <span id="deleteProjectName"></span>
				<input id="reloadProjectAfterDelete" type="hidden" value="false"/>
				<input id="deleteProjectId" type="hidden" />
			</div>
		</div>
		<hr class="footer-hr"/>
		<div class="am-modal-footer">
			<span id="cancelDeleteButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="deleteProjectButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
	</div>
</div>

<div id="editProjectModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			编辑项目
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以编辑项目. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				<form class="am-form am-form-horizontal form-radius">
					<div class="am-form-group">
						<label for="editProjectName" class="am-u-sm-5 am-form-label">项目名称 / Name</label>
						<div class="am-u-sm-7 am-u-end">
							<input id="editProjectId" type="hidden" />
							<input id="reloadProjectAfterUpdate" type="hidden" value="false"/>
							<input id="editProjectName" type="text" placeholder="Project Name" maxlength="50"/>
						</div>
					</div>
					<div class="am-form-group">
						<label for="editProjectKey" class="am-u-sm-5 am-form-label">项目键值 / Key</label>
						<div class="am-u-sm-7 am-u-end">
							<input id="editProjectKey" type="text" placeholder="Project Key" maxlength="50"/>
						</div>
					</div>
					<div class="am-form-group">
						<label for="editProjectCategory" class="am-u-sm-5 am-form-label">项目分类 / Category</label>
						<div class="am-u-sm-7 am-u-end">
							<select id="editProjectCategory" class="am-radius select2">
								<option value="">None Category</option>
								<c:forEach var="category" items="${categoryList}">
									<option value="${category.id}">${category.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="am-form-group">
						<label for="editDescription" class="am-u-sm-5 am-form-label">项目描述 / Description</label>
						<div class="am-u-sm-7 am-u-end">
							<textarea id="editDescription" placeholder="Description" maxlength="1000"></textarea>
						</div>
            		</div>
				</form>
			</div>
		</div>
		<hr class="footer-hr"/>
		<div class="am-modal-footer">
			<span id="cancelEditButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="editProjectButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
		<div id="editVldTooltip" class="vld-tooltip"></div>
	</div>
</div>

<%@include file="/WEB-INF/pages/common/footer.jsp"%>