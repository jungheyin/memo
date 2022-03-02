<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 서브밋 말고 버튼으로 쓰기!! 아작쓰일 경우 써브밋 안됨!! -->
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
	 <c:if test="${not empty post.imagePath}">
	 <div class="image-area mb-3">
	 	<img src="${post.imagePath}" alt="이미지 업로드" width="300">
	 </div>
	 </c:if>
	 
	 <div class="d-flex justify-content-between">
		 <button type="button" id="postDeleteBtm" class="btn btn-dark mt-2">삭제</button>
		 
		 
		 <div class="mt-2">
		 	<button type="button" id="postListBtn" class="btn btn-secondary">목록</button>
		 	<!-- 목록 부분을 앵커태그로 해도 된다. -->
		 	<button type="button" id="saveBtn" class="btn btn-warning ml-2" data-post-id="${post.id}">수정</button>
		 </div>
	 </div>
</div>
</div>

<script>
$(document).ready(function() {
	
	// 삭제 버튼
	
	// 목록 버튼 (script 사용시)
	$('#postListBtn').on('click', function() {
		location.href="/post/post_list_view";
	});
	// 수정 버튼
	$('#saveBtn').on('click', function(){
		
		// validation check 
		//제목은 필수값이다!!
		
		let subject = $('#subject').val().trim();
		if (subject == '') {
			alert("제목을 입력해주세요.");
			return;
		}
		
		let content = $('#content').val();
		
		// 파일 부분
		// 파일이 업로드 된 경우 확장자 체크
		let file = $('#file').val(); // 파일의 경로만 가져온다.
		console.log(file);
		if (file != "") {
			let ext = file.split('.').pop().toLowerCase(); 
			// 파일 경로를 .으로 나누고 확장자가 있는 마지막 문자열을 가져온 후 모두 소문자로 변경
			if($.inArray(ext, ['gif', 'png', 'jpg', 'jpeg']) == -1) {
					// -1 (= false)
				alert("gif, png, jpg, jpeg파일만 업로드 할 수 있습니다.");
					// 파일을 비운다.
				$('#file').val('');
				return;
			}
		}
		
		// 폼 태그 객체를 자바스크립트에서 만든다.
		let formData = new FormData();
		// 수정시 글 번호도 같이 넘어가므로 postId가 꼭 있어야한다.
		// 태그쪽에서 글이 몇번인지 심어놓고 가져오는것이 좋다!
		// data-post-id="${post.id}"
		let postId = $(this).data('post-id'); // 먼저 검증해주는것이 좋다.
		console.log(postId);
		formData.append("postId", postId);
		formData.append("subject", subject);
		formData.append("content", content);
		formData.append("file", $('#file')[0].files[0]);
		
		
		// AJAX
		$.ajax({
			type: "PUT" // update시 type은 PUT
			, url: "/post/update"
			, data: formData
			, enctype: "multipart/form-data" // 파일 업로드를 위한 필수 설정
			, processData: false // 파일 업로드를 위한 필수 설정
			, contentType: false // 파일 업로드를 위한 필수 설정
			, success: function(data) {
				if (data.result == "success") {
					// 성공 -  저장되고 새로고침화면
					alert("메모가 수정되었습니다.");
					location.reload();
				} else {
					alert(data.errorMessage);
				}
			}
			, error: function(e) {
				alert("메모 저장에 실패했습니다. 관리자에게 문의해주세요");
			}
		});
	});
	
});

</script>