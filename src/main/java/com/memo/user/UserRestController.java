package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.model.User;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	/**
	 * 회원가입 - id중복확인
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is_duplicated_id")
	public Map<String, Object> isDuplicationId( // 결과를 {"result": success"}나올 예정이므로 Map이다.
			@RequestParam("loginId") String loginId) {
		
		Map<String, Object> result = new HashMap<>();
		boolean existLoginId = userBO.existLoginId(loginId);
		 result.put("result", existLoginId); // id가 이미 존재하면 true
		 
		return result;
	}
	/**
	 * 회원가입 - ajax호출 기능
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	// ajax로 submit 한다.
	@PostMapping("/sign_up")
	public Map<String, Object> signUpForSubmit(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// 비밀번호 암호화
		String encryptPassword = EncryptUtils.md5(password); // 브레이크 포인트
		
		// DB insert
		int row = userBO.addUser(loginId, encryptPassword, name, email);
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");  // 디버깅 포인트
		
		if (row < 1) {
			result.put("result", "error");
		}
		return result; 
	}
	
	@PostMapping("/sign_in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		
		// 비밀번호 암호화
		String encryptPassword = EncryptUtils.md5(password);
		
		// loginId, 암호화 비번으로 DB에서 셀렉트
		User user = userBO.getUserByLoginIdPassword(loginId, encryptPassword); // 여기서는 password를 encryptPasssord로 넣어줘야한다.!!
		
		// 결과 json 리턴
		Map<String, Object> result =new HashMap<>();
		result.put("result", "success");
		
		// 로그인이 성공하면 세션에 User 정보를 담는다.
		if (user != null) {
			// 로그인 - 세션에 저장(로그인 상태를 유지한다)
			HttpSession session = request.getSession();
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userid", user.getId());
			session.setAttribute("userName", user.getName());
			// 여기는 간편하게만 저장해야한다. session은 어디에서든 사용할수 있다. db처럼 사용하지 말것!
			// session은 로그인 판별 용도이다!!
		} else {
			result.put("result", "error");
			result.put("errorMessage", "존재하지 않는 사용자입니다.");
		}
		return result;
		
	}
}
