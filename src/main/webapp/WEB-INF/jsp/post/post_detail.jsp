<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="w-75 my-2">
	<h1>글 상세/수정</h1>

	<input type="text" id="subject" class="form-control" placeholder="제목을 입력해 주세요." value="${post.subject}">	
	<textarea class="form-control" id="content" 
	 	rows="15" placeholder="내용을 입력해 주세요.">${post.content}</textarea>
	 	
	 <div class="d-flex justify-content-end my-3">
	 	<input id="file" type="file" accept=".jpg, .png, .gif, .jpeg">
	 	<!-- appept로 허용되는 파일만 보이게 된다. 
	 		막아주는것이 아니라 편의성을 위해 그림만 보여지게 해주는것이다.-->
	 </div>
	 
	 <!-- 이미지가 있을 때만 이미지 영역 추가 -->
	 <c:if test="not empty post.imagePath">
	 	<div class="image-area ㅡㅠ-3">
	 		<img src="${post.imagePath}" alt="이미지 업로em" width="300">
	 	</div>
	 </c:if>
	 
	 
	 <div class="d-flex justify-content-between">
		 <button type="button" id="postDeleteBtm" class="btn btn-secondary">삭제</button>
		 
		 
		 <div>
		 	<button type="button" id="postListBtn" class="btn btn-dark">목록</button>
		 	<button type="button" id="saveBtn" class="btn btn-warning">저장</button>
		 </div>
	 </div>
</div>
    