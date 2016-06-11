<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
		$("input[type='checkbox']").uCheck();
		//Reset The TH Font
		$("#all").parent().css({"padding-top":"0px", "font-weight":"bold", "font-size":"1.6rem"});
		//Reset The TH BackgroundColor
		$("label[for='all'] .am-icon-checked, label[for='all'] .am-icon-unchecked").css({"background-color":"white"});
	});
	
	function selectAll(){
		var allCheckBox = $("#searchUserModalTable input[type='checkbox'][data-am-ucheck]");
		if($("#all").prop("checked")){
			allCheckBox.uCheck("check");
		}else{
			allCheckBox.uCheck("uncheck");
		}
	}
</script>
<table id="searchUserModalTable" class="am-table am-table-bordered am-table-radius am-table-striped am-table-hover am-table-compact custom-table-class">
	<thead>
		<tr>
			<th>
				<label for="all" class="am-checkbox am-warning">
					All
					<input id="all" type="checkbox" data-am-ucheck onclick="selectAll();" />
				</label>
			</th>
			<th>ID</th>
			<th>Username</th>
			<th>Nickname</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="user" items="${userList}">
			<tr>
				<td>
					<label class="am-checkbox am-warning">
						<input type="checkbox" name="userCheckBox" data-am-ucheck />
					</label>
				</td>
				<td>${user.id}<input type="hidden" name="id" value="${user.id}"/></td>
				<td>${user.username}<input type="hidden" name="username" value="${user.username}"/></td>
				<td>${user.nickname}<input type="hidden" name="nickname" value="${user.nickname}"/></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div style="text-align: left;">
	<%@include file="/WEB-INF/pages/common/paginate.jsp"%>
</div>