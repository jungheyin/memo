<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<div> 
	<!-- form태그는 form-name-submit 3종이 세트이다! -->
	<form id="loginForm" action="/user/sign_in" method="post" >
		<input type="text" name="loginId" id="loginId"class="form-control mb-2"
			 placeholder="ID">
		<input type="password" name="password" id="password" class="form-control mb-3"
			placeholder="PASSWORD">
			
		<input type="submit" class="btn btn-block btn-warning mb-2" value="로그인">
		
		<a href="/user/sign_up_view" class="btn btn-blok btn-secondary w-100">회원가입</a>
	</form>
	
<script>
$(document).ready(function() {
	$('#loginForm').on('submit', function(e) {
		e.preventDefault(); // submit 기능 중단
		
		// validation
		let loginId = $('#loginId').val().trim();
		if (loginId.length < 1) {
			alert("아이디를 입력해주세요.");
			return false; // submit 사용시 return false라고 써야 된다.
		}
		
		let password = $('#password').val().trim();
		if (password.length < 1) {
			alert("비밀번호를 입력해주세요");
			return false;
		}
		// ajax 호출 
		// post 아작스로 form의 action을 읽는 방법
		let url = $(this).attr('action'); // form 태그에 있는 action 주소를 가져옴 $(this)는 $('#loginFrom')이다.
		// 데이터 만들기
		let params = $(this).serialize(); // form 태그에 있는 name 값들을 쿼리스트링으로 구성
		
		$.post(url,params) // 포스트 방식으로 날아간다.
		.done(function(data) { 
			// 응답값 
			if (data.result == 'success') {
				// 성공일 경우
				location.href = "post/post_list_view"; 
			} else {
				alert(data.errorMessage); // 실패하면 머무르도록 얼럿만 띄어준다.
			}
		});
	});
	
});

// form을 쓰지만 바로 아작스를 호출 하는법도 있다. -선생님은 이것을 더 선호한다.

</script>
</div>
