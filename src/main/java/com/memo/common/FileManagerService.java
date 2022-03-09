package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // spring bean 으로 만들어주는 조상어노테이션
public class FileManagerService {
	// @Autowired 사용시도 사용가능하다.
	
	// 서버에 이미지를 저장할때 따로 분리해서 사용한다.
	// CDN 서버 (이미지, cssm js 저장할때 사용한다.)
	// 상수 사용시 이름은 대문자로 써줘야한다. (마지막 부분에 꼭 슬래시(/)를 넣어줘야한다.
//	public final static String FILE_UPLOAD_PATH = "D:\\정혜인\\6_spring_project\\memo\\workspace\\images/";
	public final static String FILE_UPLOAD_PATH = "C:\\Users\\user\\Desktop\\portfolio\\memo\\workspace\\images/";
	
	public String saveFile(String userLoginId, MultipartFile file) {
		// 파일 디렉토리 경로 예: hi1856_21098475885/sun.png(아이디_올린시간/파일이름.파일명)
		// 파일명이 겹치지 않게 하기 위해 현재시간을 경로에 붙여준다.
		String directoryName = userLoginId + "_" + System.currentTimeMillis() + "/";
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		// 디렉토리 만들기 (폴더 만들기)
		File directory = new File(filePath); // 자바에서 제공해주는 File클레스
		if (directory.mkdir() == false) { //여기서 만들어준다.
			return null; //디렉토리 생성 시 샐패하면 null을 리턴
		}
		
		// 파일 업로드: byte 단위로 업로드 한다.
		try {
			byte[] bytes = file.getBytes();	
			Path path = Paths.get(filePath + file.getOriginalFilename()); // file을 우리가 올린 파일이름! - 파일이름은 영어로된 이름으로 만들수 잇게 만들어기!(sns에서)
												//getOriginalFilename()는 input에서 올린 파일명이다.(한글 안됨)
			Files.write(path, bytes);
			
			//이미지 url을 리턴한다. (WebMucConfig에서 매핑한 이미지 path)
			// 예) http://loclahost/images/hi1856_21098475885/sun.png
			return "/images/" + directoryName + file.getOriginalFilename();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// 삭제
	// input: 이미지 패스 ,output:
	public void deleteFile(String imagePath) throws IOException { // 위로 던졌으므로 부른 BO에서 책임져야 한다.
		// 1. image path의 /images/hi1856_21098475885/sun.png에서
			// images/를 제거한 path를 실제 저장경로를 뒤에 붙인다. 
			//	FILE_UPLOAD_PATH + image 를 하면 images가 겹치므로 /images/ 를 제거해준다.
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		if (Files.exists(path)) { // file이 있으면 삭제
				Files.delete(path);
		} // 여기까지 하면 사진만 제거 되고 폴더는 그대로 남아있다.!!
		
		// 2. 디렉토리(폴더) 삭제
		path = path.getParent(); // path의 부모
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
// 삭제시 부른 BO에서 처리하는 이유는 logger를 이용해서 에러기록을 남길수있기때문이다.