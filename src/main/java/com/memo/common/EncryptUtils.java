package com.memo.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
	// static은 일단 메모리에 올려두는 형식이다.
	
	public static String md5(String message) { // message에 패스워드 들어있음
		String encData = ""; // 암호화가 된 패스워드가 들어있다.
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
		
			byte[] bytes = message.getBytes();
	        md.update(bytes);
	        byte[] digest = md.digest();
	        
	        for(int i = 0; i < digest.length; i++ ) {
	            encData += Integer.toHexString(digest[i]&0xff); // 16진수로 변환하는 과정
	        }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encData;
	}
	
	// 
}
