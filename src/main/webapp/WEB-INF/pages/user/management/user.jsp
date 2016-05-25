<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
		$("#addUserButton").hover(function(){
			$(this).addClass("am-btn-secondary");
		}, function(){
			$(this).removeClass("am-btn-secondary");
		});
		
		$(".select2").select2({
			width: "150%" ,
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
			<div class="am-u-sm-12 am-u-md-1 am-u-md-offset-2">
				<button id="search" type="button" class="am-btn am-btn-success am-round" data-am-loading="{spinner:'spinner', loadingText:'Searching...'}">
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

		<div class="am-g">
			<div class="am-u-sm-12">
				<!-- table:am-text-nowrap   am-scrollable-horizontal am-table-centered td:am-text-middle -->
				<table class="am-table am-table-bordered am-table-radius am-table-striped am-table-hover am-table-compact" style="border-radius: 15px; box-shadow: 1px 1px 1px #888888;">
					<thead>
						<tr>
							<th>ID</th>
							<th>Username</th>
							<th>Nickname</th>
							<th>Email</th>
							<th>Group</th>
							<th>Operation</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td><a href="#">ASNPH35</a></td>
							<td>Liang, Jackie-D</td>
							<td>Jackie-D.Liang@xtt.com</td>
							<td>
								<a href="#">capricornus-administrator</a><br/>
								<a href="#">capricornus-user</a>
							</td>
							<td class="am-text-middle">
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button class="am-btn am-btn-default am-btn-xs am-text-secondary">
											<span class="am-icon-pencil-square-o"></span> Edit
										</button>
										<button class="am-btn am-btn-default am-btn-xs">
											<span class="am-icon-ban"></span> Block
										</button>
										<button class="am-btn am-btn-default am-btn-xs am-text-danger">
											<span class="am-icon-trash-o"></span> Delete
										</button>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="am-cf">
					共 15 条记录
					<div class="dataTables_paginate">
						<a class="paginate_button disabled">First</a>
						<a class="paginate_button disabled">Previous</a>
						<span>
							<a class='paginate_button current'>1</a>
							<a class='paginate_button'>2</a>
						</span>
						<a class="paginate_button">Next</a>
						<a class="paginate_button">Last</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="/WEB-INF/pages/common/footer.jsp"%>