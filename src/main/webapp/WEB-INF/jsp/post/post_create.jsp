<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="w-75 my-2">
	<h1>글쓰기</h1>
	
	<!-- form 태그를 해서 ajax를 사용할지
	 	form태그만 사용할지 ajax만 사용할지는 선택사항이다 
	 	이번엔 ajax로 보내는것!-->
	 	
	<input type="text" id="subject" class="form-control" placeholder="제목을 입력해 주세요.">	
	<textarea class="form-control" id="content" 
	 	rows="15" placeholder="내용을 입력해 주세요."></textarea>
	 	
	 <div class="d-flex justify-content-end my-3">
	 	<input id="file" type="file" accept=".jpg, .png, .gif, .jpeg">
	 	<!-- appept로 허용되는 파일만 보이게 된다. 
	 		막아주는것이 아니라 편의성을 위해 그림만 보여지게 해주는것이다.-->
	 </div>
	 
	 <div class="d-flex justify-content-between">
		 <button type="button" id="postListBtn" class="btn btn-dark">목록</button>
		 
		 
		 <div>
		 	<button type="button" id="clearBtn" class="btn btn-secondary">모두지우기</button>
		 	<button type="button" id="saveBtn" class="btn btn-warning">저장</button>
		 </div>
	 </div>
</div>

<script>
$(document).ready(function() {
	
	// 1. 목록 버튼 클릭 => 글 목록으로 이동
	$('#postListBtn').on('click', function() {
		// alert("확인");
		
		// 이동
		location.href="/post/post_list_view";
	});
	
	// 2.모두지우기 
	$('#clearBtn').on('click', function() {
		// 공백으로 세팅- 제목과 내용 부분을 빈칸으로 만든다.
		$('#subject').val('');
		$('#content').val('');
	});
	
	// 3.글 내용 저장
	$('#saveBtn').on('click', function(e) {  // e를 통해 e.preventDefault를 사용할수있음
		
		//validation (DB의 스키마 참고 - null여부)
		let subject = $('#subject').val().trim(); // 변수
		if (subject.length < 1) {
			alert("제목을 입력해주세요.");
			return;
		}
		
		// content는 null허용이지만, 데이터를 가져와야 한다.
		let content = $('#content').val();
		// console로 통해 content내용이 잘 넘어오는지 확인하기!
		console.log(content);
		
		// 파일이 업로드 된 경우 확장자 체크
		let file = $('#file').val(); // 파일의 경로만 가져온다.
		console.log(file);
		if (file != '') {
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
		
		// 파일이 들어가서 무조건 form으로 보내야한다.
		// 폼태그를 자바스크립트에서 만든다.
		let formData = new FormData(); 		// 객체이므로 new가 있다!!
		formData.append("subject", subject); //키와 벨유로 보낸다.
		formData.append("content", content); 
		formData.append("file", $('#file')[0].files[0]);  
		// $('#file')[0] 첫번째 input 파일 태그를 의미
		// .files[0]는 업로드된 첫번째 파일을 의미
		
		// 파일이 안들어온 경우
		
		
		// AJAX form 데이터 전송
		$.ajax({
			type: "POST" //post로 보내는이유는 : 큰 데이터를 보내므로
			, url: "/post/create" // 화면이 아닌 API
			, data: formData // query는 json으로 보낼수 없어 객체를 보낸다.
			, enctype: "multipart/form-data" // 파일 업로드를 위한 필수 설정
						// form태그 사용시 form에서 작성해준다.
			, processData: false   // 파일 업로드를 위한 필수 설정
			, contentType: false   // 파일 업로드를 위한 필수 설정
			// 쿼리로 보내주려는 설정을 파일 업로드할땐 꺼주는것이다.
			, success: function(data) {
				if (data.result == "success") {
					alert("메모가 저장되었습니다.");
					location.href = "/post/post_list_view"
				}
			}
			, error: function(e) {
				alert("메모 저장에 실패했습니다. 관리자에게 문의해주세요");
				// 여기서 뿌려도 되고 서버쪽에서 뿌려줘도 된다.
				
			}
		});		// alert로 줄마다 되는지 안되는지 검증해 본다.
	});
	
	
	
});
</script>