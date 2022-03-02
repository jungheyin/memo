package com.memo.post.bo; // bo할때 쿼리끼리 모아두면 보기 편하다!

import java.util.List;

import org.slf4j.Logger; // logger는 import가 중요!!! 
import org.slf4j.LoggerFactory; // 잘보기!! 중요
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
// 스프링 빈은 Auto와이어로 불러준다.
public class PostBO {
	
	private Logger logger = LoggerFactory.getLogger(PostBO.class);
	// private Logger logger = LoggerFactory.getLogger(this.getClass()); // 둘중 아무거나 써도 된다. 
	
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
	
	// 수정
	// void로 받거나 int로 받거나
	public int updataPost(int postId, String userLoginId, int userId, String subject,
			String content, MultipartFile file) {

		// 1. postId에 해당하는 게시글이 있는지 DB에서 가져와 본다.
			// BO의 기본이 되는 메소드를 가져오거나(더 좋음), dao에서 가져오거나
			// BO에서도 높낮이가 있다. 나중에 변경이 될수도 있으니깐,, BO에서 가져오는게 좋음!
		Post post = getPostById(postId);
		if (post == null) { // 글이 없다. - 이런 상황이 없어야 한다! 이런건 기록해야한다.(log를 가지고)
			logger.error("[update post] 수정할 메모가 존재하지 않습니다." + postId);
			return 0;
		}
		// 2. 파일이 있는지 본 후 있으면 파일을 업로드 후 image path 받아온다. (파일 매니저를 통해 사용하면 된다)
			// 파일이 만약 없으면 수정하지 않는다.
		String imagePath = null;
		if (file != null) {
			imagePath = fileManager.saveFile(userLoginId, file); // 컴퓨터에 파일 업로드 후 URL Path를 얻어야 한다.
			
			// 새로 업로드 된 이미지가 성공하면 기존 이미지는 삭제 ( 기존 이미지가 있을 때에만)
			if (post.getImagePath() != null && imagePath != null) {
				// 기존 이미지 삭제 (post의 있던 옛것)
				fileManager.deleteFile(post.getImagePath()); 
			}
		}
		
		// 3. db update
		return postDAO.updatePostByUserIdPostId(userId, postId, subject, content, imagePath);
		
	}
	
}
