package com.memo.post.bo; // bo할때 쿼리끼리 모아두면 보기 편하다!

import java.io.IOException;
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
		// 1. 여기서 파일을 이미지 패스를 만든다.
		// db에서는 파일을 저장을 못하고 url을 저장할수 잇다.
		
		String imagePath = null;
		// file 기능을 다른곳에서 사용할수 있도록 만들어주는것이 더 좋다!!
		// file이 없을때도 있다.
		if (file != null) {
			//TODO: 파일 매니저 서비스 	input:MultipartFile 	output:이미지의 주소
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		
		// 2. insert DAO
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
				try {
					fileManager.deleteFile(post.getImagePath());
				} catch (IOException e) {
					logger.error("[update post] 이미지 삭제 실패 {}, {}",  post.getId(), post.getImagePath());
				} 
			}
		}
		
		// 3. db update
		return postDAO.updatePostByUserIdPostId(userId, postId, subject, content, imagePath);
		
	}
	
	// 삭제
	public int deletePostByPostIdAndUserId(int postId, int userId) {
		// 요청시 로그인이 풀릴경우를 대비해 userId를 가져온다.
		
		// 1. 삭제전 게시글을 먼저 가져와야한다(imagePath가 있기 때문이다.)
		Post post = getPostById(postId);
		if (post == null) {
			logger.warn("[delete post] 삭제할 메모가 존재하지 않습니다.");
			return 0;
		} 
		// 2. imagePath가 있는 경우 파일 삭제
		if (post.getImagePath() != null) {
			// 기존 이미지 삭제
			try {
				fileManager.deleteFile(post.getImagePath());
			} catch (IOException e) {
				logger.error("[delet post] 이미지 삭제 실패 {}, {}",  post.getId(), post.getImagePath());
			} 
		}
		// 3. db에서 post 삭제
		
		return postDAO.deletePostByUserIdPostId(userId, postId) ;
		
	}
	// 글만지우는것인지 다른것도 다 지워야 하는지 생각해 봐야한다.
	
}
