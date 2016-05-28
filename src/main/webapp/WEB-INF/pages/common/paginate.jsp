<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/common/common.jsp"%>
<div class="am-cf">
	共 ${pageObject.totalRecord} 条记录, 共 ${pageObject.totalPage} 页
	<div class="dataTables_paginate">
		<c:choose>
			<c:when test="${pageObject.totalPage != 0 and pageObject.currentPage != 1}">
				<a class="paginate_button" onclick="paginate(1);">First</a>
				<a class="paginate_button" onclick="paginate(${pageObject.currentPage - 1});">Previous</a>
			</c:when>
			<c:otherwise>
				<a class="paginate_button">First</a>
				<a class="paginate_button">Previous</a>
			</c:otherwise>
		</c:choose>
		<span>
			<c:forEach var="currentIndex" begin="1" end="${pageObject.totalPage}" step="1">
				<c:choose>
					<c:when test="${currentIndex != pageObject.currentPage}">
						<a class='paginate_button' onclick="paginate(${currentIndex});">${currentIndex}</a>
					</c:when>
					<c:otherwise>
						<a class='paginate_button current'>${currentIndex}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</span>
		<c:choose>
			<c:when test="${pageObject.totalPage != 0 and pageObject.currentPage != pageObject.totalPage}">
				<a class="paginate_button" onclick="paginate(${pageObject.currentPage + 1});">Next</a>
				<a class="paginate_button" onclick="paginate(${pageObject.totalPage});">Last</a>
			</c:when>
			<c:otherwise>
				<a class="paginate_button">Next</a>
				<a class="paginate_button">Last</a>
			</c:otherwise>
		</c:choose>
	</div>
</div>