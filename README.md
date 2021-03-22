# CouponAPI

### 1. 개발 프레임워크
   - Framework : Spring Boot(v2.5), MyBatis, JUnit
   - DB : MySQL
### 2. 문제해결 전략
   - Architecture : DTO&DAO를 활용한 RESTful API
   - 개발 순서
      1. 각 문제의 입력 및 출력값 확인
        * 전체적인 Application 디자인을 위해 정확히 문제 파악
  2. Schema 설계
    > 모든 기능을 수행할 수 있도록 아래와 같이 테이블 구현
  3. Project Package 설계
    > 아래와 같이 Package 설계
  4. Mapper(Query) 작성
    > 입, 출력값 및 제약을 고려한 Query문 작성
  3. HTTP Request Controller 구현
    > 각 문항에 대한 URI Handler 구현
  4. Unit Test 진행
    > 입, 출력 테스트 및 각 기능에 대한 Unit Test 진행(JUnit)
   
### 3. 실행방법
  1. Project root에 위치한 coupons.sql 실행하여 DB&Table 생성
  2. src>main>resources>application.properties 파일에서 uername, password 변경
  3. CouponAPI 어플리케이션 실행
  4. 아래 URI를 통해 각 문제에 대한 결과 확인
    1. 랜덤 코드 N개 생성 (input : int N)
      > http://localhost:8080/coupon/new?N={N}
    2. 생성된 쿠폰 지급 (output : String couponCode)
      > http://localhost:8080/coupon/regi
    3. 사용자에게 지급된 쿠폰 조회
      > http://localhost:8080/coupon/assigned
    4. 지급된 쿠폰 하나 사용 (input : String couponCode)
      > http://localhost:8080/coupon/use?code={couponCode}
    5. 지급 및 사용된 쿠폰 사용 취소 (input : String couponCode)
      > http://localhost:8080/coupon/cancel?code={couponCode}
    6. 지급된 쿠폰 중, 당일 만료된 쿠폰 조회
      > http://localhost:8080/coupon/expired
    7. 쿠폰 만료 3일 전 안내 메세지 발송 (매일 00시에 자동 발송)
      > http://localhost:8080/coupon/noti
