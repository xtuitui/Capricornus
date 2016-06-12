<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<style type="text/css">
	.permission-description{
		border: .2rem solid green;
		border-radius: 10px;
		box-shadow: 1px 1px 5px 1px green;
		background-color: rgba(224, 242, 177, 0.89);
		margin-bottom: 20px;
	}
	
	.permission-margin{
		margin-top: 20px;
	}
	
	.am-table tbody tr:hover{
		background-color: #FFD;
	}
</style>
<div class="admin-content">
	<div class="admin-content-body">
		<div class="am-cf am-padding am-padding-bottom-0">
			<div class="am-fl am-cf">
				<strong class="am-text-primary am-text-lg">System Permission</strong>
			</div>
		</div>
		<br/>
		<div class="am-g">
			<div class="am-u-sm-12 permission-description">
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
			        <tr>
			            <td>Capricornus - System Administrator</td>
			            <td>http://amazeui.org</td>
						<td class="am-text-middle">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<button class="am-btn am-btn-default am-btn-xs am-text-success" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showUserGroupModal(${user.id}, this);">
										<span class="am-icon-group"></span> Group
									</button>
								</div>
							</div>
						</td>
			        </tr>
			        <tr>
			            <td>Capricornus - Administrator</td>
			            <td>http://amazeui.org</td>
			            <td class="am-text-middle">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<button class="am-btn am-btn-default am-btn-xs am-text-success" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showUserGroupModal(${user.id}, this);">
										<span class="am-icon-group"></span> Group
									</button>
								</div>
							</div>
						</td>
			        </tr>
			        <tr>
			            <td>Capricornus - User</td>
			            <td>http://amazeui.org</td>
			            <td class="am-text-middle">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<button class="am-btn am-btn-default am-btn-xs am-text-success" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showUserGroupModal(${user.id}, this);">
										<span class="am-icon-group"></span> Group
									</button>
								</div>
							</div>
						</td>
			        </tr>
			        <tr>
			            <td>Amaze UI</td>
			            <td>http://amazeui.org</td>
			            <td class="am-text-middle">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<button class="am-btn am-btn-default am-btn-xs am-text-success" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showUserGroupModal(${user.id}, this);">
										<span class="am-icon-group"></span> Group
									</button>
								</div>
							</div>
						</td>
			        </tr>
			        <tr>
			            <td>Amaze UI</td>
			            <td>http://amazeui.org</td>
			            <td class="am-text-middle">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<button class="am-btn am-btn-default am-btn-xs am-text-success" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showUserGroupModal(${user.id}, this);">
										<span class="am-icon-group"></span> Group
									</button>
								</div>
							</div>
						</td>
			        </tr>
			        <tr>
			            <td>Amaze UI</td>
			            <td>http://amazeui.org</td>
			            <td class="am-text-middle">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<button class="am-btn am-btn-default am-btn-xs am-text-success" data-am-loading="{spinner:'spinner', loadingText:'Showing...'}" onclick="showUserGroupModal(${user.id}, this);">
										<span class="am-icon-group"></span> Group
									</button>
								</div>
							</div>
						</td>
			        </tr>
			    </tbody>
			</table>
		</div>
	</div>
</div>
<%@include file="/WEB-INF/pages/common/footer.jsp"%>