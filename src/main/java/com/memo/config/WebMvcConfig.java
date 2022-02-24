package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration	
public class WebMvcConfig implements WebMvcConfigurer {
	
	/*
	 * 웹의 이미지 주소와 실제 파일 경로를 매핑해주는 설정
	 * 
	 * */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**") // 웹의 이미지 주소 http://loclahost/images/hi1856_21098475885/sun.png
		.addResourceLocations("file:///D:\\정혜인\\6_spring_project\\memo\\workspace\\images/"); // 실제 파일 경로를 매핑
					
	}
}
