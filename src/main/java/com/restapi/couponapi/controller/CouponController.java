package com.restapi.couponapi.controller;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.couponapi.dao.CouponDAO;
import com.restapi.couponapi.dto.CouponDTO;
import com.restapi.couponapi.util.APIUtility;

@RestController
@MapperScan(basePackages = "com.restapi.couponapi.dao")
@RequestMapping("/coupon")
public class CouponController {
	@Autowired
	private CouponDAO couponDAO;

	/*1. 랜덤한 코드의 쿠폰을 N개 생성하여 데이터베이스에 보관하는 API를 구현하세요.
	 *  > 쿠폰 코드  형식 : 8자리 String이며 대문자 알파뱃과 숫자로 구성 
	 *  > 8자리 쿠폰 코드 생성 후, 중복 확인을 거친 뒤에 등록
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String createCoupons(@RequestParam("N") int N) {
		final int codeLength = 8;

		boolean hasDup = true;
		String code = "";
		for (int i = 0; i < N; i++) {
			while (hasDup) {
				code = APIUtility.generateCode(codeLength);
				hasDup = couponDAO.countCoupon(code) > 0;
			}
			couponDAO.addCoupon(code);
			hasDup = true;
		}

		return "<html><body><b>" + N + "개 쿠폰이 정상적으로 등록되었습니다.</b></body> </html>";
	}

	/* 2. 생성된 쿠폰중 하나를 사용자에게 지급하는 API를 구현하세요.
	 *   > 생성된 쿠폰 중, 지급되지 않은 가장 오래된(생성일 기준) 쿠폰  지급
	 *   > 쿠폰은 지급된 날 기준 8일 뒤 자정에 만료(ex. 2021-03-21 15:00 지급 => 2021-03-29 00:00에 만료)
	*/
	@RequestMapping(value = "/regi", method = RequestMethod.PATCH)
	public String getCoupon() {
		final String codeToBeAssigned = couponDAO.getUnassignedCouponCode();

		// 지급되지 않은 쿠폰 중 생성된지 가장 오래된 쿠폰 선택
		couponDAO.updateAssignedCoupon(codeToBeAssigned);
		CouponDTO assignedCoupon = couponDAO.getCoupon(codeToBeAssigned);

		return "<html>" + "<header><title>쿠폰 지급</title></header>" + "<body>" + "<b>아래와 같이 쿠폰이 지급되었습니다.</b><br> "
				+ "- 쿠폰 번호 : " + assignedCoupon.getC_couponCode() + "<br>" + "- 쿠폰 만료일 :"
				+ APIUtility.changeDateFormat(assignedCoupon.getC_expDate()) + " 00:00</body></html>";

	}

	/* 3. 사용자에게 지급된 쿠폰을 조회하는 API를 구현하세요.
	 *   > 지급 완료된 모든 쿠폰의 코드와 만료일 조회
	 */
	@RequestMapping(value = "/assigned", method = RequestMethod.GET)
	public String getAllAssignedCoupon() {
		List<CouponDTO> usedCoupons = couponDAO.getAssignedCoupons();
		String output = "<html><body>" + "<b>사용자들에게 지급된 쿠폰은 아래와 같습니다.</b><br>";
		for (CouponDTO coupon : usedCoupons) {
			output += "쿠폰코드 : " + coupon.getC_couponCode() + " / 만료일 : "
					+ APIUtility.changeDateFormat(coupon.getC_expDate()) + "<br>";
		}
		return output += "</body></html>";
	}

	 /*  4. 지급된 쿠폰중 하나를 사용하는 API를 구현하세요. (쿠폰 재사용은 불가)
	  *    > 지급된 쿠폰 중, 입력된 쿠폰 코드가 존재할 경우 사용 사용 처리, code가 존재하지 않거나 이미 사용 중일 경우 사용 불가 안내 
	  */
	@RequestMapping(value = "/use", method = RequestMethod.PATCH)
	public String useCoupon(@RequestParam("code") String code) {
		int result = couponDAO.useCoupon(code);

		if (result == 0) {
			return "<html><body><b>사용할 수 없는 쿠폰번호입니다.</b></body></html>";
		}

		return "<html><body><b>["+ code +"] 쿠폰을 사용하였습니다.</b></body></html>";
	}

	/* 5. 지급된 쿠폰중 하나를 사용 취소하는 API를 구현하세요. (취소된 쿠폰 재사용 가능)
	 *   > 지급된 쿠폰 중, 입력된 쿠폰 코드가 존재할 경우 사용 취소, code가 존재하지 않거나 아직 사용되지 않은 경우 취소 불가 안내 
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.PUT)
	public String cancelCoupon(@RequestParam("code") String code) {
		int result = couponDAO.cancelCoupon(code);

		if (result == 0) {
			return "<html><body><b>취소할 수 없는 쿠폰번호입니다.</b></body></html>";
		}
				
		return "<html><body><b>["+ code +"] 쿠폰 사용을 취소하였습니다.</b></body></html>";
	}

	/* 6. 발급된 쿠폰중 당일 만료된 전체 쿠폰 목록을 조회하는 API를 구현하세요.
	 *   > 만료일이 당일과 일치하는 모든 쿠폰 조회
	 */
	@RequestMapping(value = "/expired", method = RequestMethod.GET)
	public String getExpiredCoupon() {
		String output = "<html><body>" + "<b>오늘 만료된 쿠폰은 아래와 같습니다.</b><br>";
		List<CouponDTO> expiredCoupons = couponDAO.getExpiredCoupons();

		for (CouponDTO coupon : expiredCoupons) {
			output += "쿠폰코드 : " + coupon.getC_couponCode() + "<br>";
		}
		return output += "</body></html>";
	}

	 
	/* 7. 발급된 쿠폰중 만료 3일전 사용자에게 메세지(“쿠폰이 3일 후 만료됩니다.”)를 발송하는 기능을 구현하세요. 
	 *	 > 매일 00:00에 3일 뒤 만료되는 쿠폰을 대상으로 쿠폰이 등록된 사용자에게 만료 안내
	 */ 
	@RequestMapping(value = "/noti", method = RequestMethod.GET)
	@Scheduled(cron = "0 0 0 * * *")
	public void sendExpNoti() {
		List<CouponDTO> expCoupons = couponDAO.getExpiredCoupons();
		for(CouponDTO coupon : expCoupons) {
			System.out.println(coupon.getC_UID() +"님의 쿠폰 ["+ coupon.getC_couponCode() +"]이 3일 뒤에 만료됩니다.");
		}
	}

}
