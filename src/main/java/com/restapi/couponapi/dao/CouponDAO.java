package com.restapi.couponapi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.restapi.couponapi.dto.CouponDTO;

@Mapper
public interface CouponDAO {
	// Q1
	public int countCoupon(String code);
	public void addCoupon(String code);
	
	// Q2
	public String getUnassignedCouponCode();
	public CouponDTO getCoupon(String code);
	public void updateAssignedCoupon(String code);
	
	// Q3. 사용자에게 지급된 쿠폰을 조회하는 API를 구현하세요.
	public List<CouponDTO> getAssignedCoupons();
	
	// Q4
	public int useCoupon(String code);
	
	// Q5
	public int cancelCoupon(String code);
	
	// Q6
	public List<CouponDTO> getExpiredCoupons();
	
	// Q7
	public List<CouponDTO> getCouponsToBeExp();
	
	
	// Test
	public void deleteCoupon(String code);
}




// 6. 발급된 쿠폰중 당일 만료된 전체 쿠폰 목록을 조회하는 API를 구현하세요.
// 7. 발급된 쿠폰중 만료 3일전 사용자에게 메세지(“쿠폰이 3일 후 만료됩니다.”)를 발송하는 기능을 구현하세요. (실제 메세지를발송하는것이 아닌 stdout 등으로 출력하시면 됩니다.)