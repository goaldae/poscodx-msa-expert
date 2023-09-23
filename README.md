# poscodx-msa-expert
POSCODX에서 수행하는 ’23년 핵심기술전문가 수행 과제

---
## 아키텍쳐에 집중해 복잡하지 않은 E-commerce 애플리케이션을 개발함

### 적용 기술
1. Eureka service discovery
2. API gateway service


### Services  
1. catalog-service
2. user-service
3. order-service

### 주요 기능(API)
1. 상품 조회
2. 사용자 조회
3. 상품 주문
4. 상품 수량 업데이트
5. 주문 확인
6. 주문 조회

### 전체 애플리케이션 구성요소
1. Git Repository: 마이크로서비스 소스 관리 및 프로파일 관리
2. Config Server: Git 저장소에 등록된 프로파일 정보 및 설정 정보
3. Eureka Server: 마이크로서비스 등록 및 검색
4. API Gateway Server: 마이크로서비스 부하 분산 및 서비스 라우팅
5. Microservices: 회원MS, 주문MS, 상품(카테고리) MS
6. Queuing System: 마이크로서비스 간 메시지 발행 및 구독
   
| 마이크로서비스         | RESTful API                                                                                                              | HTTP Method          |
|-----------------|--------------------------------------------------------------------------------------------------------------------------|----------------------|
| Catalog Service | /catalog-service/catalogs: 상품 목록 제공                                                                                      | GET                  |
| User Service    | /user-service/users: 사용자 정보 등록 <br/> /user-service/users: 전체 사용자 조회 <br/> /user-service/users{user_id}: 사용자 정보, 주문 내역 조회 | POST<br/>GET<br/>GET |
| Order Service   | /order-service/users/{user_id}/orders: 주문 등록 <br/> /order-service/users/{user_id}/orders: 주문 확인                          | POST<br/>GET         |

