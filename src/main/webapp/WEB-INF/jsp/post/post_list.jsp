<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="w-100 ">
	<div class="d-flex justify-content-between">
		<h5 class="ml-3 mt-2 font-weight-bold">글 목록</h5>
		<a href="/post/post_create_view" 
			class="btn btn-warning mb-3 font-weight-bold text-white">글쓰기</a> 
	</div>
	<div class="">
	<table class="table table-hover text-center">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성날짜</th>
				<th>수정날짜</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${postList}" var="post">
			<tr>
				<td>${post.id}</td>
				<td><a href="/post/post_detail_view?postId=${post.id}">${post.subject}</a></td>
				<td> <!-- Date객체 -> String -->
					<fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td>
					<fmt:formatDate value="${post.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<!-- 페이징 -->
	<div class="d-flex justify-content-center">
		<c:if test="${prevId ne 0}">
			<a href="/post/post_list_view?prevId=${prevId}" class="mr-2">&lt;&lt;</a>
		</c:if>
		<c:if test="${nextId ne 0}">
			<a href="/post/post_list_view?nextId=${nextId}" class="mr-2">&gt;&gt;</a>
		</c:if>
	</div>
	<!-- <div class="float-right">
		float를 사용해서 버튼을 원하는 위치에 놓을수 있지만 다음에 클리어를 해줘야한다는 단점.
		화면 이동시 앵커 태그로 보내는것이 편하다.
		
	</div> -->
</div>