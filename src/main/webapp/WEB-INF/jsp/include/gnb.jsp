<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	<div class="ml-5">

	</div>
	
	<h3 class="text-warning">MEMO</h3>
		
	<div>
	<!-- 세션이 있을때만(로그인 되었을 때만) 출력 -->
	<c:if test="${not empty userName}" >
		<span class="small text-right ml-2 font-weight-bold">${userName}님</span>
		<br>
		<a href="/user/sign_out" class="small text-secondary font-weight-bold ml-2">로그아웃</a>
		<!-- 리다이렉트로 한다. -->
	</c:if>
	</div>
