package com.memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;

@RestController
public class PostRestController {
	
	@Autowired
	public PostBO postBO;

	@RequestMapping("/posts")
	public List<Post> posts() { // 디버깅 걸어서 볼수있는게 따로 담아서 보는것이 좋다.
		List<Post> postList = postBO.getPostList();
		return postList;
	}
	
	
}
