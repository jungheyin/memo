package com.memo.interceptor;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PermissionInterceptor implements HandlerInterceptor{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException  {
		// 로그인 된 상태에서 로그인, 회원가입에 온 경우 타임라인으로 리다이렉트
		// 로그아웃 path는 인터셉터 설정에서 제외한다.( 안하면 무한르투가 생기므로 무조건 빠져나갈 구멍이 있어야한다.)
		
		// 세션이 있는지 확인한다. => 있으면 로그인 된 상태
		HttpSession session = request.getSession();
		String userLoginId = (String) session.getAttribute("userLoginId");
		
		// url확인 (url path를 가져온다.)
		String uri = request.getRequestURI();
		
		if (userLoginId == null && uri.startsWith("/post")) {
			// 비로그인 && 접급을 시도한 uri path가 /post이면 로그인 페이지로 리다이렉트
			response.sendRedirect("/user/sign_in_view");
			return false;
		} else if (userLoginId != null && uri.startsWith("/user")) {
			// 로그인 && 접근을 시도한 uri path가 /user 이면 게시판 페이지로 리다이렉트
			response.sendRedirect("/post/post_list_view");
			return false;
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			ModelAndView modelAndView) {
		
		// URI 확인 (url path를 가져온다)
		String uri = request.getRequestURI();
		logger.info("###### postHandler" + uri);
	}
	
	@Override
	public void afterCompletion(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception ex) {
		
		// uri 확인 (uri path를 가져온다.)
		String uri = request.getRequestURI();
		logger.info("###### afterCompletion:" + uri);
		
	}
}
