package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
public class PostBO {
	
	@Autowired 
	public PostDAO postDAO;
	public List<Post> getPostList() {
		return postDAO.selectPostList();
	}
}
