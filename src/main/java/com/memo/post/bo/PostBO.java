package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
public class PostBO {
	
	@Autowired 
	public PostDAO postDAO;
	
	public List<Post> getPostList() {
		return postDAO.selectPostList();
	}
	// userLoginId는 파일 업로드를 하기 위한 파라미터이다
	public void addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) { // userId는 null이면 안되므로 int이다!
		// db에서는 파일을 저장을 못하고 url을 저장할수 잇다.
		// 여기서 파일을 이미지 패스를 만든다.
		
		String imagePath = null;
		// file 기능을 다른곳에서 사용할수 있도록 만들어주는것이 더 좋다!!
		// file이 없을때도 있다.
		if (file != null) {
			
		}
		
		// insert DAO
	}
}
