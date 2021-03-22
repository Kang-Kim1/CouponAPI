package com.restapi.couponapi.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class APIUtility {
	// 랜덤한 8자리 쿠폰코드를 생성하기 위한 Utility Method
	public static String generateCode(int couponSize) {
		final String alphaNumericSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; 
		final int len = alphaNumericSet.length();
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < couponSize; i++) {
			sb.append(alphaNumericSet.charAt((int)(Math.random() * len)));
		}
		return sb.toString();
	}
	// yyyy년 MM월 dd일로 Date format을 변경하기 위한 Utility Method
	public static String changeDateFormat(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일"); 
		String updatedDate = simpleDateFormat.format(date);

		return updatedDate;
	}

}
