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
		modalButtonLoading();
		var result = checkParamBeforeSubmit();
		if(result===false){
			modalButtonReset();
		}else{
			$.post("${path}/user/management/addGroup", result, function(data){
				if(data.result=="success"){
					$("#groupName").val(result.name);
					searchGroup(1);
					modalButtonReset();
					$("#addGroupModal").modal("close");
				}else{
					var messageCode = data.data;
					if(messageCode=="gae"){
						var topOffset = $("#vldTooltip").parent().offset().top;
						var leftOffset = $("#vldTooltip").parent().offset().left;
						showTooltip("vldTooltip", "addGroupName", "用户组已经存在", 30 - topOffset, -leftOffset);
						$("#addGroupName").focus();
						modalButtonReset();
					}
				}
			});
		}
	}
	
	function modalButtonLoading(){
		$("#cancelGroupButton").hide().removeClass("am-modal-btn");
		$("#saveGroupButton").button("loading");
	}
	
	function modalButtonReset(){
		$("#saveGroupButton").button("reset");
		$("#cancelGroupButton").addClass("am-modal-btn").show();
	}
	
	function checkParamBeforeSubmit(){
		var groupName = $.trim($("#addGroupName").val());
		var description = $.trim($("#addDescription").val());
		var topOffset = $("#vldTooltip").parent().offset().top;
		var leftOffset = $("#vldTooltip").parent().offset().left;
		if($.trim(groupName)==""){
			showTooltip("vldTooltip", "addGroupName", "用户组不能为空", 30 - topOffset, -leftOffset);
			$("#addGroupName").focus();
			return false;
		}
		return {"name":groupName, "description":description};
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
<%@include file="/WEB-INF/pages/common/footer.jsp"%>