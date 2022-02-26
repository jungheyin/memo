<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="d-flex justify-content-center">
<div class="w-75">
	<h4 class="ml-3 my-3 font-weight-bold">글쓰기</h4>
	
	<input type="text" id="subject" class="form-control mt-2 mb-3" placeholder="제목을 입력해 주세요."  value="${post.subject}">
		
	<textarea class="form-control" id="content" 
	 	rows="15" placeholder="내용을 입력해 주세요.">${post.content}</textarea>
	 
	 <div class="d-flex justify-content-end my-3">
	 	<input id="file" type="file" accept=".jpg, .png, .gif, .jpeg">
	 </div>
	 	<!-- 이미지가 있을 때만 이미지 영역 추가 -->
	 <c:if test="not empty post.imagePath">
	 <div class="image-area mb-3">
	 	<img src="${post.imagePath}" alt="이미지 업로드" width="300">
	 </div>
	 </c:if>
	 
	 <div class="d-flex justify-content-between">
		 <button type="button" id="postDeleteBtm" class="btn btn-dark mt-2">삭제</button>
		 
		 
		 <div class="mt-2">
		 	<button type="button" id="postListBtn" class="btn btn-secondary">목록</button>
		 	<button type="button" id="saveBtn" class="btn btn-warning ml-2">수정</button>
		 </div>
	 </div>
</div>
</div>

<script>
$(document).ready(function() {
	
	// 삭제 버튼
	
	// 목록 버튼
	
	// 
});

</script>