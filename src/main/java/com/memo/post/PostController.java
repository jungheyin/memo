package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String postListView(Model model, HttpServletRequest request) {
		// 글쓴이 정보를 가져오기 위해 세션에서 userId를 꺼낸다.
		HttpSession session = request.getSession();
		int userId = (int)session.getAttribute("userid"); //
		
		// postList는 가져와서 보여줄것이므로 글목록을 가져오기만 한다.
		// TODO: 글 목록 DB에서 가져오기
		List<Post> postList = postBO.getPostListByUserId(userId);
		
		model.addAttribute("postList", postList); // postList 가져온다.
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
