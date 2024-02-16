# 항해 플러스 3기 개인 프로젝트
항해 플러스 3기를 진행하면서 실습한 내용들을 정리한 프로젝트입니다.

### 기술 스택
- Java 17
- SpringBoot 3.2.0
- Gradle
- MySQL, Redis, JPA

## Chapter 1. CI/CD 배포 파이프라인 구축
### 환경 분리
- local
- prod

## Chapter 2. TDD 서버 구축
### Layered Architecture
```
com.hanghae.hanghaeplus3
├── HanghaeEcommerceApplication.java
├── commons
│   ├── advice
│   │   ├── ConsumerMapper.java
│   │   ├── KafkaProducer.java
│   │   ├── KafkaTopics.java
│   │   ├── LockHandler.java
│   │   ├── RedisKeyFactory.java
│   │   └── TransactionHandler.java
│   └── config
│       ├── CacheConfig.java
│       ├── JpaConfig.java
│       ├── ProducerConfiguration.java
│       ├── RedissonConfig.java
│       └── RunnableConfig.java
├── controller
│   ├── ApiControllerAdvice.java
│   ├── ApiResponse.java
│   ├── order
│   │   ├── OrderController.java
│   │   ├── OrderProductRequest.java
│   │   ├── OrderProductResponce.java
│   │   ├── OrderRequest.java
│   │   └── OrderResponse.java
│   ├── payment
│   │   └── PaymentController.java
│   ├── product
│   │   ├── ProductController.java
│   │   └── ProductResponse.java
│   └── user
│       ├── BalanceController.java
│       ├── BalanceResponse.java
│       └── ChargeBalanceRequest.java
├── domain
│   ├── order
│   │   ├── Order.java
│   │   ├── OrderManager.java
│   │   ├── OrderProduct.java
│   │   ├── OrderService.java
│   │   └── event
│   │       ├── OrderCancelEvent.java
│   │       ├── OrderCreatedEvent.java
│   │       └── OrderEventListener.java
│   ├── payment
│   │   ├── Payment.java
│   │   ├── PaymentManager.java
│   │   ├── PaymentService.java
│   │   └── event
│   │       ├── ComplatePaymentEvent.java
│   │       └── PaymentEventListener.java
│   ├── product
│   │   ├── PopularProductsManager.java
│   │   ├── Product.java
│   │   ├── ProductManager.java
│   │   └── ProductService.java
│   └── user
│       ├── Balance.java
│       ├── BalanceService.java
│       └── UserBalanceManager.java
└── entity
    ├── BaseEntity.java
    ├── order
    │   ├── OrderEntity.java
    │   ├── OrderProductEntity.java
    │   ├── OrderState.java
    │   ├── PopularProduct.java
    │   └── repository
    │       ├── OrderEntityRepository.java
    │       ├── OrderProductEntityRepository.java
    │       ├── OrderProductRepository.java
    │       ├── OrderProductRepositoryImpl.java
    │       ├── OrderRepository.java
    │       └── OrderRepositoryImpl.java
    ├── payment
    │   ├── PaymentEntity.java
    │   └── repository
    │       ├── PaymentEntityRepository.java
    │       ├── PaymentRepository.java
    │       └── PaymentRepositoryImpl.java
    ├── product
    │   ├── ProductEntity.java
    │   └── repository
    │       ├── ProductEntityRepository.java
    │       ├── ProductRepository.java
    │       └── ProductRepositoryImpl.java
    └── user
        ├── BalanceEntity.java
        ├── UserEntity.java
        ├── UserRepository.java
        └── repository
            ├── BalanceEntityRepository.java
            ├── BalanceRepositoryImpl.java
            └── BalanceRepository.java
```
### TDD
- 구현 기능
  - 이커머스(e-commerce) 상품 구매 서비스
  - 주요 API
    - 계좌 잔액 조회
    - 계좌 잔액 충전
    - 상품 조회
    - 상품 구매
    - 인기 상품 조회
- 주요 포인트
  - 동시성 테스트
    - 잔액 충전 더블 클릭 방지
      - Redis Lock 사용 (분산 환경 및 DB Connection 보호)
    - 동시 재고 차감 방지
      - Redis Lock 사용 (상품 ID로 sort하여 데드락 방지)
  - 캐시
    - 인기 상품 조회 ->  ```@Cacheable```을 사용하여 캐싱하고 매일 한번 스케줄링으로 캐시를 삭제

## Chapter 3. 통합 모니터링 시스템 구축
- logback console, file 로그 남기기
- CloudWatch 연동

## Chapter 4. 부하테스트 진행
- nGrinder 적용
- 인기상품 조회, 주문 관련 API 테스트 시나리오 작성
