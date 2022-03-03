package com.memo.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.memo.post.model.Post;

@Repository
public interface PostDAO {
	
	public List<Post> selectPostListByUserId(int userId);
	
	public Post selectPostById(int id);
	
	public void insertPost(
			@Param("userId") int userId, 
			@Param("subject") String subject, 
			@Param("content") String content,
			@Param("imagePath") String imagePath);
	
	public int updatePostByUserIdPostId(
			@Param("userId") int userId,
			@Param("postId") int postId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath);
	
	public int deletePostByUserIdPostId(
			@Param("userId") int userId, 
			@Param("postId") int postId);
}
