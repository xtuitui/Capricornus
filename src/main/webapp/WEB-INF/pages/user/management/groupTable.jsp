<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<div class="am-u-sm-12">
	<table
		class="am-table am-table-bordered am-table-radius am-table-striped am-table-hover am-table-compact custom-table-class">
		<thead>
			<tr>
				<th>ID</th>
				<th>Group Name</th>
				<th>Operation</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="group" items="${groupList}">
				<tr>
					<td>${group.id}</td>
					<td><span class="group-name">${group.name}</span><br/><span class="group-description">${group.description}</span></td>
					<td class="am-text-middle">
						<div class="am-btn-toolbar">
							<div class="am-btn-group am-btn-group-xs">
								<button class="am-btn am-btn-default am-btn-xs am-text-secondary">
									<span class="am-icon-pencil-square-o"></span> Edit
								</button>
								<button class="am-btn am-btn-default am-btn-xs am-text-success">
									<span class="am-icon-group"></span> User
								</button>
								<button class="am-btn am-btn-default am-btn-xs am-text-danger">
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