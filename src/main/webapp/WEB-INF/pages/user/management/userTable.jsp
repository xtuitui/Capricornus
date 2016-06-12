<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<div class="am-u-sm-12">
	<!-- table:am-text-nowrap   am-scrollable-horizontal am-table-centered td:am-text-middle -->
	<table
		class="am-table am-table-bordered am-table-radius am-table-striped am-table-hover am-table-compact custom-table-class">
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
			<c:forEach var="user" items="${userList}">
				<tr>
					<td>${user.id}</td>
					<td><a href="#" id="usernameA${user.id}">${user.username}</a></td>
					<td id="nicknameTd${user.id}">${user.nickname}</td>
					<td>${user.email}</td>
					<td>
						<c:forEach var="group" items="${user.groups}">
							<a href="#">${group.name}</a><input type="hidden" name="userGroupIdInput${user.id}" value="${group.id}" /><br/>
						</c:forEach>
					</td>
					<td class="am-text-middle">
						<div class="am-btn-toolbar">
							<div class="am-btn-group am-btn-group-xs">
								<button class="am-btn am-btn-default am-btn-xs am-text-secondary">
									<span class="am-icon-pencil-square-o"></span> Edit
								</button>
								<button class="am-btn am-btn-default am-btn-xs am-text-success" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showUserGroupModal(${user.id}, this);">
									<span class="am-icon-group"></span> Group
								</button>
								<button class="am-btn am-btn-default am-btn-xs am-text-warning">
									<span class="am-icon-eye"></span> Role
								</button>
								<button class="am-btn am-btn-default am-btn-xs">
									<span class="am-icon-ban"></span> Block
								</button>
								<button class="am-btn am-btn-default am-btn-xs am-text-danger" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showDeleteUserModal(${user.id}, this);">
									<span class="am-icon-trash-o"></span> Delete
								</button>
							</div>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@include file="/WEB-INF/pages/common/paginate.jsp"%>
</div>