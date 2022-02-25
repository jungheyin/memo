package com.memo.post.bo; // bo할때 쿼리끼리 모아두면 보기 편하다!

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
// 스프링 빈은 Auto와이어로 불러준다.
public class PostBO {
	
	@Autowired 
	private PostDAO postDAO;
	
	@Autowired 
	private FileManagerService fileManager; 
	
	public List<Post> getPostListByUserId(int userId) {
		return postDAO.selectPostListByUserId(userId);
	}
	
	public Post getPostById(int id) {
		return postDAO.selectPostById(id);
	}
	// userLoginId는 파일 업로드를 하기 위한 파라미터이다
	public void addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) { // userId는 null이면 안되므로 int이다!
		// db에서는 파일을 저장을 못하고 url을 저장할수 잇다.
		// 여기서 파일을 이미지 패스를 만든다.
		
		String imagePath = null;
		// file 기능을 다른곳에서 사용할수 있도록 만들어주는것이 더 좋다!!
		// file이 없을때도 있다.
		if (file != null) {
			//TODO: 파일 매니저 서비스 	input:MultipartFile 	output:이미지의 주소
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		
		// insert DAO
		// int userId, String subject, string content,String imagePath
		postDAO.insertPost(userId, subject, null, imagePath);
	}
	
	
}
