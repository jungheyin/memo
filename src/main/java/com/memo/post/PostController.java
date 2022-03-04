package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostBO postBO;
	
	/**
	 * 글 목록 화면
	 * @param model
	 * @return
	 */
	@RequestMapping("/post_list_view")
	public String postListView(
			@RequestParam(value="prevId", required=false) Integer prevIdParam,
			@RequestParam(value="nextId", required=false) Integer nextIdParam,
			Model model, 
			HttpServletRequest request) {
		// 글쓴이 정보를 가져오기 위해 세션에서 userId를 꺼낸다.
		HttpSession session = request.getSession();
		int userId = (int)session.getAttribute("userId"); //
		
		// postList는 가져와서 보여줄것이므로 글목록을 가져오기만 한다.
		// 글 목록 DB에서 가져오기
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
		// postList가 없는 경우 에러 방지
		int prevId = 0;
		int nextId = 0;
		if (CollectionUtils.isEmpty(postList) == false) { 
			// post.isEmpty()을 사용시 위험한 코드 이므로 CollectionUtils를 사용
			prevId = postList.get(0).getId(); // 리스트 중 가장 앞쪽(제일 큰값) id
			nextId = postList.get(postList.size() - 1).getId(); //리스트 중 가장 뒷쪽 (제일 작은 값) id
			
			// 이전이나 다음이 없는 경우 nextI, prevId를 0으로 세팅한다.
			
			// 마지막 페이지(다음 방향 끝) => nextId를 0으로  
			if (postBO.isLastPage(userId, nextId)) {
				nextId = 0;
			}
			
			// 첫 페이지 (이전 방향의 끝) => prevId를 0으로
			if (postBO.isFirstPage(userId, prevId)) {
				prevId = 0;
			}
		}
		
		
		model.addAttribute("postList", postList); // postList 가져온다.
		model.addAttribute("prevId", prevId);
		model.addAttribute("nextId", nextId);
		model.addAttribute("viewName", "post/post_list");
		return "template/layout";
	}
	
	/**
	 * 글 쓰기 화면
	 * @param model
	 * @return
	 */
	@RequestMapping("/post_create_view")
	public String postCreateView(Model model) {
		model.addAttribute("viewName", "post/post_create");
		
		return "template/layout";
	}
	
	@RequestMapping("/post_detail_view")
	public String postDetailView(
			@RequestParam("postId") int postId, Model model) {
		
		// postId로 해당하는 글을 가져옴 -postId와 userId도 가져와도 된다.
		Post post = postBO.getPostById(postId);
		
		model.addAttribute("post", post);
		model.addAttribute("viewName", "post/post_detail");
		
		return "template/layout";
	}
}
