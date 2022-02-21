package com.memo.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@RequestMapping("/is_duplication_id")
	public Map<String, Object> isDuplicationId( // 결과를 {"result": success"}나올 예정이므로 Map이다.
			@RequestParam("loginId") String loginId) {
		
		Map<String, Object> result = new HashMap<>();
		boolean existLoginId = userBO.existLoginId(loginId);
		 result.put("result", existLoginId); // id가 이미 존재하면 true
		 
		return result;
	}
	
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
		userBO.addUser(loginId, encryptPassword, name, email);
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");  // 디버깅 포인트
		
		return result; 
	}
}
