<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="w-75">
	<h1>글 목록</h1>
	
	<table class="table table-hover">
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
	
	<div class="float-right">
		<!-- float를 사용해서 버튼을 원하는 위치에 놓을수 있지만 다음에 클리어를 해줘야한다는 단점. -->
		<!-- 화면 이동시 앵커 태그로 보내는것이 편하다. -->
		<a herf="/post/post_create_view" class="btn btn-warning">글쓰기</a> 
	</div>
</div>