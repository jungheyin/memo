package com.memo.common;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // spring bean 으로 만들어주는 조상어노테이션
public class FileMengerService {
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
		
		
	}
}
