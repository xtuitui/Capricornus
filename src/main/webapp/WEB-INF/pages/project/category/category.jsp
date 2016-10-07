<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/common/tooltip.css"/>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/common/modal.css"/>
<link type="text/css" rel="stylesheet" href="${path}/static/capricornus/css/project/category/category.css"/>
<script type="text/javascript">

	$(document).ready(function(){
		$("#addCategoryButton").hover(function(){
			$(this).addClass("am-btn-secondary");
		}, function(){
			$(this).removeClass("am-btn-secondary");
		});
		
		$("#addCategoryModal").on("closed.modal.amui", function(){
			var isReload = $("#reloadCategory").val();
			if(isReload == "true"){
				showDynamicContent("${path}/project/category/toCategory");
			}
		});
	});
	
	function showAddCategoryModal(){
		$("#addCategoryButton").button("loading");
		$("#addCategoryName").val("");
		$("#addDescription").val("");
		$("#addCategoryModal").modal({
			"onConfirm": function(){
				saveCategory();
			}, 
			"closeOnConfirm": false
		});
		$("#addCategoryButton").button("reset");
	}
	
	function saveCategory(){
		modalButtonLoading("saveCategoryButton", "cancelCategoryButton");
		var result = checkParamBeforeSave();
		if(result===false){
			modalButtonReset("saveCategoryButton", "cancelCategoryButton");
		}else{
			$.post("${path}/project/category/addCategory", result, function(data){
				if(data.result=="success"){
					$("#reloadCategory").val("true");
					$("#addCategoryModal").modal("close");
				}else{
					var messageCode = data.data;
					if(messageCode=="cae"){
						var topOffset = $("#vldTooltip").parent().offset().top;
						var leftOffset = $("#vldTooltip").parent().offset().left;
						showTooltip("vldTooltip", "addCategoryName", "分类已经存在", 30 - topOffset, -leftOffset);
						$("#addCategoryName").focus();
					}else{
						alert("添加出错!");
					}
				}
				modalButtonReset("saveCategoryButton", "cancelCategoryButton");
			});
		}
	}
	
	function checkParamBeforeSave(){
		var categoryName = $.trim($("#addCategoryName").val());
		var description = $("#addDescription").val();
		var topOffset = $("#vldTooltip").parent().offset().top;
		var leftOffset = $("#vldTooltip").parent().offset().left;
		if($.trim(categoryName)==""){
			showTooltip("vldTooltip", "addCategoryName", "分类不能为空", 30 - topOffset, -leftOffset);
			$("#addCategoryName").focus();
			return false;
		}
		return {"name":categoryName, "description":description};
	}
	
	function showDeleteCategoryModal(categoryId, obj){
		$(obj).button("loading");
		$("#deleteCategoryId").val(categoryId);
		$("#deleteCategoryName").html($("#categoryNameSpan"+categoryId).html());
		$("#deleteCategoryModal").modal({
			"onConfirm": function(){
				checkDependencyAndDelete();
			}, 
			"closeOnConfirm": false
		});
		$(obj).button("reset");
	}
	
	function checkDependencyAndDelete(){
		modalButtonLoading("deleteCategoryButton", "cancelDeleteButton");
		var categoryId = $("#deleteCategoryId").val();
		$.post("${path}/project/category/checkProjectCategoryDependency", {"id":categoryId}, function(data){
			if(data.result=="success"){
				var projectList = data.data;
				if(projectList.length>0){
					$("#deleteCategoryModal").modal("close");
					showUpdateProjectCategoryModal(categoryId, projectList);
				}else{
					deleteCategory();
				}
			}else{
				alert("拉取Project数据出错");
			}
			modalButtonReset("deleteCategoryButton", "cancelDeleteButton");
		});
	}
	
	function showUpdateProjectCategoryModal(categoryId, projectList){
		var categoryName = $("#categoryNameSpan"+categoryId).html();
		$("#pcCategoryName").html(categoryName);
		var htmlStr = "";
		for(var i=0;i<projectList.length;i++){
			var projectName = projectList[i].name;
			var projectId = projectList[i].id;
			htmlStr += createProjectCategoryDiv(projectId, projectName);
		}
		$("#projectCategoryForm").html(htmlStr);

		$("#projectCategoryForm .select2").select2({
			width: "100%" ,
			maximumInputLength: 20 ,
		});
		
		$("#projectCategoryModal").modal({
			"width": "800",
			"onConfirm": function(){
				updateProjectCategory();
			}, 
			"closeOnConfirm": false
		});
	}
	
	function updateProjectCategory(){
		$.ajax({
			url:"${path}/project/category/updateProjectCategory",
			type:"post",
			async:true,
			data:$("#projectCategoryForm").serialize(),
			cache:false,
			dataType:"json",
			success:function(data){
				if(data.result=="success"){
					deleteCategory();
					$("#projectCategoryModal").modal("close");
				}else{
					alert("更新项目分类出错");
				}
			},
			error:function(){
				alert("更新项目分类出错");
			}
		});
	}
	
	function createProjectCategoryDiv(projectId, projectName){
		var htmlStr = "<div class='am-form-group'><div class='am-u-sm-4'>";
		htmlStr += "<input type='text' readonly='readonly' value='"+projectName+"'/>";
		htmlStr += "<input type='hidden' name='projectIds' value='"+projectId+"'/></div>";
		htmlStr += "<div class='am-u-sm-4 project-category-line'>----------------------------></div>";
		htmlStr += "<div class='am-u-sm-4 am-u-end'>";
		htmlStr += createCategorySelect();
		htmlStr += "</div></div>";
		return htmlStr;
	}
	
	function createCategorySelect(){
		var htmlStr = "<select name='categoryIds' class='am-radius select2'>";
		htmlStr += createCategorySelectOption();
		htmlStr += "</select>";
		return htmlStr;
	}
	
	function createCategorySelectOption(){
		var deleteCategoryId = $("#deleteCategoryId").val();
		var optionHtml = "<option value='' selected='selected'>None Category</option>";
		$("span.category-name").each(function(){
			var categoryId = $(this).attr("id").substr("categoryNameSpan".length);
			if(categoryId == deleteCategoryId){
				return true;
			}
			var categoryName = $(this).html();
			optionHtml += "<option value='"+categoryId+"'>"+categoryName+"</option>";
		});
		return optionHtml;
	}
	
	function deleteCategory(){
		var categoryId = $("#deleteCategoryId").val();
		modalButtonLoading("deleteCategoryButton", "cancelDeleteButton");
		$.post("${path}/project/category/deleteCategory", {"categoryId":categoryId}, function(data){
			if(data.result=="success"){
				$("#deleteCategoryModal").modal("close");
				$("#categoryNameSpan"+categoryId).closest("tr").remove();
			}else{
				alert("删除出错!");
			}
			modalButtonReset("deleteCategoryButton", "cancelDeleteButton");
		});
	}
	
	function showEditCategoryModal(categoryId, obj){
		$(obj).button("loading");
		$("#editCategoryId").val(categoryId);
		$("#editCategoryName").val($("#categoryNameSpan"+categoryId).html());
		$("#editDescription").val($("#categoryDescriptionSpan"+categoryId).html());
		$("#editCategoryModal").modal({
			"onConfirm": function(){
				updateCategory();
			}, 
			"closeOnConfirm": false
		});
		$(obj).button("reset");
	}
	
	function updateCategory(){
		modalButtonLoading("editCategoryButton", "cancelEditButton");
		var result = checkParamBeforeUpdate();
		if(result==false){
			modalButtonReset("editCategoryButton", "cancelEditButton");
		}else{
			$.post("${path}/project/category/updateCategory", result, function(data){
				if(data.result=="success"){
					$("#categoryNameSpan"+result.id).html(result.name);
					$("#categoryDescriptionSpan"+result.id).html(result.description);
					$("#editCategoryModal").modal("close");
				}else{
					var messageCode = data.data;
					if(messageCode=="cae"){
						var topOffset = $("#editVldTooltip").parent().offset().top;
						var leftOffset = $("#editVldTooltip").parent().offset().left;
						showTooltip("editVldTooltip", "editCategoryName", "分类已经存在", 30 - topOffset, -leftOffset);
						$("#editCategoryName").focus();
					}else{
						alert("编辑出错!");
					}
				}
				modalButtonReset("editCategoryButton", "cancelEditButton");
			});
		}
	}
	
	function checkParamBeforeUpdate(){
		var categoryId = $("#editCategoryId").val();
		var categoryName = $.trim($("#editCategoryName").val());
		var categoryDescription = $("#editDescription").val();
		var topOffset = $("#editVldTooltip").parent().offset().top;
		var leftOffset = $("#editVldTooltip").parent().offset().left;
		if($.trim(categoryName)==""){
			showTooltip("editVldTooltip", "editCategoryName", "分类不能为空", 30 - topOffset, -leftOffset);
			$("#editCategoryName").focus();
			return false;
		}
		return {"id":categoryId, "name":categoryName, "description":categoryDescription};
	}
	
</script>
<div class="admin-content">
	<div class="admin-content-body">
		<div class="am-cf am-padding am-padding-bottom-0">
			<div class="am-fl am-cf">
				<strong class="am-text-primary am-text-lg">Category Management</strong>
			</div>
		</div>
		<br/>
		<div class="am-g">
			<div class="am-u-sm-12 category-title">
				您现在可以管理项目的分类, 请定义一系列的分类类型, 每个项目只可以指定一种分类, 合理管理分类可以更好地管理项目.<br/>
				请尽量给项目指定一个明确的分类.
			</div>
		</div>
		<hr data-am-widget="divider" class="am-divider am-divider-dotted" />
		<div class="am-g category-margin">
			<table class="am-table">
			    <thead>
			        <tr>
			            <th>
			            	类别 - (Category)&nbsp;&nbsp;&nbsp;&nbsp;
			            	<button id="addCategoryButton" type="button" class="am-btn am-btn-default am-round" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showAddCategoryModal(this);">
								<i class="am-icon-binoculars"></i>
								Add Category
							</button>
						</th>
			            <th>操作 - (Operation)</th>
			        </tr>
			    </thead>
			    <tbody>
			    	<c:forEach var="category" items="${categoryList}">
						<tr>
			        		<td><span id="categoryNameSpan${category.id}" class="category-name">${category.name}</span><br/><span id="categoryDescriptionSpan${category.id}" class="category-description">${category.description}</span></td>
			        		<td class="am-text-middle">
			        			<div class="am-btn-toolbar">
			        				<div class="am-btn-group am-btn-group-xs">
			        					<button class="am-btn am-btn-default am-btn-xs am-text-secondary" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showEditCategoryModal(${category.id}, this);">
											<span class="am-icon-pencil-square-o"></span> Edit
										</button>
										<button class="am-btn am-btn-default am-btn-xs am-text-danger" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showDeleteCategoryModal(${category.id}, this);">
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

<div id="addCategoryModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			添加项目分类
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以创建项目的分类. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				<form class="am-form am-form-horizontal form-radius">
					<div class="am-form-group">
						<label for="addCategoryName" class="am-u-sm-5 am-form-label">分类 / Category Name</label>
						<div class="am-u-sm-7 am-u-end">
							<input id="reloadCategory" type="hidden" value="false"/>
							<input id="addCategoryName" type="text" placeholder="Category Name" maxlength="50"/>
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
			<span id="cancelCategoryButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="saveCategoryButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
		<div id="vldTooltip" class="vld-tooltip"></div>
	</div>
</div>

<div id="deleteCategoryModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			删除分类
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以删除分类, 删除成功后项目会自动分配无分类. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				确定要删除该分类吗 ?
				<br/>
				分类 : <span id="deleteCategoryName"></span>
				<input id="deleteCategoryId" type="hidden" />
			</div>
		</div>
		<hr class="footer-hr"/>
		<div class="am-modal-footer">
			<span id="cancelDeleteButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="deleteCategoryButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
	</div>
</div>

<div id="editCategoryModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			编辑分类
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;您现在可以编辑分类. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				<form class="am-form am-form-horizontal form-radius">
					<div class="am-form-group">
						<label for="editCategoryName" class="am-u-sm-5 am-form-label">分类 / Category Name</label>
						<div class="am-u-sm-7 am-u-end">
							<input id="editCategoryId" type="hidden" />
							<input id="editCategoryName" type="text" placeholder="Category Name" maxlength="50"/>
						</div>
					</div>
					<div class="am-form-group">
						<label for="editDescription" class="am-u-sm-5 am-form-label">描述 / Description</label>
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
			<span id="editCategoryButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
		<div id="editVldTooltip" class="vld-tooltip"></div>
	</div>
</div>

<div id="projectCategoryModal" class="am-modal am-modal-no-btn" tabindex="-1" onclick="closeModal(this, event);">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			批量更新项目分类
			<a href="javascript:;" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<hr/>
		<div class="modal-description">
			<div>&nbsp;&nbsp;欢迎使用Capricornus!<br/>&nbsp;&nbsp;该分类 - [<span id="pcCategoryName"></span>]即将删除, 请重新分配以下项目的分类. <br/>&nbsp;&nbsp;谢谢支持...</div>
		</div>
		<hr/>
		<div class="am-modal-bd">
			<div class="am-u-sm-12 am-u-end update-project-category-title-div">
				<div class="am-form-group">
					<div class="am-u-sm-4 update-project-category-title">Project</div>
					<div class="am-u-sm-4">&nbsp;</div>
					<div class="am-u-sm-4 am-u-end update-project-category-title">Category</div>
				</div>
			</div>
			<div class="am-u-sm-12 am-u-end" style="position: relative;left: 0px;">
				<form id="projectCategoryForm" class="am-form am-form-horizontal form-radius"></form>
			</div>
		</div>
		<hr class="footer-hr"/>
		<div class="am-modal-footer">
			<span id="cancelUpdatePCButton" class="am-modal-btn" data-am-modal-cancel>取消</span>
			<span id="updatePCButton" class="am-modal-btn" data-am-modal-confirm data-am-loading="{spinner:'spinner', loadingText:'Please Waiting...'}">确定</span>
		</div>
		<div id="updatePCVldTooltip" class="vld-tooltip"></div>
	</div>
</div>

<%@include file="/WEB-INF/pages/common/footer.jsp"%>