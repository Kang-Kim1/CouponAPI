package com.restapi.couponapi;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.restapi.couponapi.dao.CouponDAO;
import com.restapi.couponapi.dto.CouponDTO;

@SpringBootTest
class CouponApiApplicationTests {
	
	final String testCode ="TESTCODE";

	@Autowired
	private MockMvc mockMvc;
	
	@Test 
	void testURL() {
		mockMvc.perform(MockMvcRequestBuilders.get("/coupon/get")).andDo(print());
	}
	
	@Autowired
	CouponDAO couponDAO;
	
	// Test 1 : 쿠폰 정상 입력 테스트 
	@Test
	void addCouponTest() {
		couponDAO.addCoupon(testCode);
		
		final int expected = 1;
		final int actual = couponDAO.countCoupon(testCode); 
		
		couponDAO.deleteCoupon(testCode);
		assertTrue(expected == actual);
		
	}
	
	// Test 2 : 중복 쿠폰 입력 불가 테스트
	@Test
	void duplicateCouponTest() {
		boolean expected = true;
		boolean actual = false;
		
		couponDAO.addCoupon(testCode);		
		
		try {
			couponDAO.addCoupon(testCode);
		} catch(DuplicateKeyException exp) {
			actual = true;
		}

		couponDAO.deleteCoupon(testCode);
		assertTrue(expected == actual);
	}
	
	// Test 3 : 쿠폰 지급 테스트
	@Test
	void assignCouponTest() {
		couponDAO.addCoupon(testCode);
		couponDAO.updateAssignedCoupon(testCode);
		
		CouponDTO couponDTO = couponDAO.getCoupon(testCode);
		boolean isAssigned = couponDTO.getC_assignedTo() != null;
		
		couponDAO.deleteCoupon(testCode);
		
		assertTrue(isAssigned);
	}
	
	// Test 4 : 쿠폰 사용 테스트 
	@Test
	void useCouponTest() {
		couponDAO.addCoupon(testCode);
		couponDAO.updateAssignedCoupon(testCode);
		couponDAO.useCoupon(testCode);
		
		CouponDTO couponDTO = couponDAO.getCoupon(testCode);
		
		boolean isUsed = couponDTO.isC_isUsed();
		
		couponDAO.deleteCoupon(testCode);
		
		assertTrue(isUsed);
	}
	
	// Test 5 : 쿠폰 취소 테스트 
	@Test
	void cancelCouponTest() {
		couponDAO.addCoupon(testCode);
		couponDAO.updateAssignedCoupon(testCode);
		couponDAO.useCoupon(testCode);
		couponDAO.cancelCoupon(testCode);
		
		CouponDTO couponDTO = couponDAO.getCoupon(testCode);
		
		boolean isCanceled = !couponDTO.isC_isUsed();
		
		couponDAO.deleteCoupon(testCode);
		
		assertTrue(isCanceled);
	}
	
	// Test 6 : 쿠폰 만료일 테스트 
	@Test
	void expDateTest() {
		couponDAO.addCoupon(testCode);
		couponDAO.updateAssignedCoupon(testCode);
		
		CouponDTO couponDTO = couponDAO.getCoupon(testCode);
		
		Date expDate = couponDTO.getC_expDate();
		Date today = new Date(); // your date

		Calendar expCal = Calendar.getInstance();
		expCal.setTime(expDate);
		
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(today);
		
		String expected = todayCal.get(Calendar.MONTH) + "/" + (todayCal.get(Calendar.DATE)+8);
		String actual = expCal.get(Calendar.MONTH) + "/" + expCal.get(Calendar.DATE);
		
		couponDAO.deleteCoupon(testCode);
		
		assertTrue(expected.equals(actual));
	}
	
	// Test 7 : 쿠폰코드 중 
	
}
