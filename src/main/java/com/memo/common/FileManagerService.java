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
	public final static String FILE_UPLOAD_PATH = "D:\\정혜인\\6_spring_project\\memo\\workspace\\images/";
	
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
}
