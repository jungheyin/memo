<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	 <div> <!-- submit일경우 action="/user/sign_up_for_submit" -->
	 		<!-- ajax일 경우  action="/user/sign_up"-->
	 	<form id="signUpForm" method="post" action="/user/sign_up">
	 	<!-- 화면이 넘어가는 것이므로 form으로 넘겨도 된다. -->
	 	
	 	</form>
		<div id="idCheckLength" class="small text-danger d-none"></div>
		<div id="idCheckDuplicated" class="small text-danger d-none"></div>
		<div id="idCheckOk" class="small text-danger d-none">사용 가능한 ID입니다.</div>
		
		<button type="submit" id=""class="btn btn-primary" >회원가입</button>
	</div>

<script> // form 태그로 이용해서 id값으로 가져온다.
 $(document).ready(function(){
	// 아이디 중복확인
	$('#loginIdCheckBtn'). on('click', function() {
		alert("중복확인 버튼 클릭");
		let loginId = $('#loginId').val().trim();
		
		// 상황 문구 안보이게 모두 초기화
		$('#idCheckLength').addClass('d-none');
		$('#idCheckDuplicated').addClass('d-none');
		$('#idCheckOk').addClass('d-none');
		
		if (loginId.length < 4) { // ajax를 안하고 끝낸다.
		 	// id가 4자 미만일 때 경고 문구 노출하고 끝낸다.
		 	$('#idCheckLength').removeClass('d-none');
			return;
		}
		
		// AJAX - 중복확인  DB에서 INSERT가 되어있는지 확인해야 한다.
			// loginId를 idx로 걸어주는것이 좋다.
			// 유니크키 인덱스는 조심해야한다 회사에서는!!- 정말 겹치지 않을때만 해주는 것이 좋다!!
		$.ajax({
			// type은 생략이 가능하다!
			url: "/user/is_duplicated_id"
			,data: {"loginId":loginId}
			,success: function(data) { // String으로 내려가는데 json도 String이다.
				if (data.result == true) // 기본값이 true이므로 == true 생략 가능
					// 중복인 경우 => 이미 사용중인 아이디
					$('#idCheckDuplicated').removeClass('d-none');
				} else if (data.result == false) {
					// 중복이 아닌 경우
					$('#idCheckOk').removeClass('d-none');
				} else {
					// 에러
				}
			}	
			, error: function() {
				alert("아이디 중복확인에 실패했습니다. 관리자에게 문의해주세요.")
			}	
		});
		
	});
	
	// 회원가입
	$('#signUpFrom').on('submit', function(e) { 
		// return false는 submit 법칙이다!!
		// validation 체크시 return false로 인해 다른 페이지로 안넘어가야한다.(넘어가면 안됨!)
		e.preventDAfault(); //서브밋 기증 중단 - 자동으로 서브밋 해주는 것이다.
		
		// validation
		let loginId = $('#loginid').val().trim();
		if (loginId == '') {
			alert("아이디를 입력해주세요.");
			return false;
		
		let password = $('#password').val();
		let confirmPassword = $('#confirmPassword').val();
		if (password == ''|| confirmPassword == '') {
			alert("비밀번호를 입력해주세요");
			return false;
		}
		
		if (password != confirmPassword) {
			alert("비밀번호가 일치하지 않습니다.");
			// 비밀번호가 일치하지 않으면 텍스트의 값을 초기화 한다.
			$('#password').val(''); 
			$('#confirmPassword').val(''); 
			return false;
		}
				// 비밀번호에 대해 정규식도 넣어주면 좋다!!
		
		let name = $('#name').val().trim();
		if (name == '') {
			alert("이름을 입력해주세요.");
			return false;
		}
		
		let email =$('#eamil').val().trim();
		if (email == '') {
			alert("이메일을 입력해주세요.");
			return false;
		}
		
		// 사용가능한 아이디입니다. 문구가 노출할 경우 회원가입이 가능하게 한다.
		// 아이디 중복확인이 되었는지 확인
		// idCheckOk <div>에 클래스 중 d-none이 있는 경우 => 성공이 아님 => return시킴 (회원가입 X)
		if ($('#idCheckOk').hasClass('d-none')) { // == true 일 경우 성공이 아니다.
			alert ("아이디 중복확인을 다시 해주세요.");
			return;
		}
		
		// alert("성공");
		
		// submit
		// 1. form 서브밋 => 응답이 화면이 됨
		// 2. ajax 서브밋 => 응답은 데이터가 됨
		
		// 1. form 서브밋
		// $('#signUpFrom')[0].submit(); //[0]을 꼭 써줘야 된다. 이것을 하는것은 수동으로 submit 해주는것이다.
		
		// 2. ajax 서브밋
		let url= $('#signUpFrom').attr('action'); // form 태그에 있는 action 주소를 가져오는법

		// alert(url);
		let params = $(this).serialize(); // form태그로 가져오므로 name속성이 꼭 있어야 한다. (한방에 가져오는 법)
										// form태그에 들어있는 값을 한번에 보낼 수 있게 구성(name 속성)
		// console.log(data);  // 직렬화(serialize)를 통해 제이슨으로 안보내도 통체로 보낼수 있다.
		
		// 새로운 Ajax통신 사용하는 방법
		// $.post(주소, 파라미터)
		// .done(성공시 인명함수)
		$.post(url, params) // url은 응답값이 내려오는 주소! (get함수도 있음!!)
		.done(function(data) { // 응답값을 보고 만들기
			if (data.result == 'success') {
				alet("회원 가입을 환영합니다. 로그인을 해주세요.");
				location.href = "/user/sign_in_view"; 
			} else {
				alert("회원 가입에 실패했습니다. 다시 시도해주세요.");
			}
		});  
	});
 });

</script>
</body>
</html>