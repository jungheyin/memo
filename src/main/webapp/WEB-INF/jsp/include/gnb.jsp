<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h3>MEMO</h3>
<!-- 세션이 있을때만(로그인 되었을 때만) 출력 -->
<c:if test="${not empty userName}" >
<div>
	<span>${userName}님 안녕하세요.</span>
	<a herf="/usr/sign_out" >로그아웃</a>
	<!-- 리다이렉트로 한다. -->
</div>
</c:if>