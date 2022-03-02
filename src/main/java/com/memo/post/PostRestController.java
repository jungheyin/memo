package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	@Autowired
	public PostBO postBO;
	

	/**
	 * 글쓰기 API
	 * @param subject
	 * @param content
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam(value="content", required=false) String content,
			@RequestParam(value="file", required=false) MultipartFile file,
			HttpServletRequest request) {
		
		// 잘 넘어왔는지 확인
		Map<String, Object> result = new HashMap<>(); // break point
		result.put("result", "success");

		// 글쓴이 정보를 가져온다.:세션에서 (가졍: 로그인 상태라는 가정)
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId"); // null일수 있으므로 Integer
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// 권한검사를 나중에 할것이므로 if를 해주는것!
		if(userId == null) {
			result.put("result", "error");
			result.put("errorMessage", "로그인 후 사용 가능합니다.");
			return result;
		}
		
		// userId, userLoginId, subject, content, file => BO insert 요청
		postBO.addPost(userId, userLoginId, subject, content, file);
		
		return result;
	}
	
	@PutMapping("/update") // ajax의 type과 일치해야만 들어온다.
	public Map<String, Object> update(
			@RequestParam("postId") int postId,
			@RequestParam("subject") String subject,
			@RequestParam(value="content", required=false)String content,
			@RequestParam(value="file", required=false) MultipartFile file,
			HttpServletRequest request) {
		// userId도 같이 넘겨준다. 
		HttpSession session = request.getSession();
		String userLoginId = (String)session.getAttribute("userLoginId");
		int userId = (int)session.getAttribute("userId");
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		
		// db update
		int row = postBO.updataPost(postId, userLoginId, userId, subject, content, file);
		if (row < 1) {
			result.put("result", "error");
			result.put("errorMessage", "메모 수정에 실패했습니다.");
		}
		return result;
	}
}
