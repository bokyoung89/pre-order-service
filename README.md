# pre-order-service

- 소셜 미디어 기반의 예약 구매 서비스로, 예약 구매 시 발생할 수 있는 대규모 트래픽을 처리한다.

## 프로젝트 목표

- 기본적인 유저 관리 기능을 포함한 소셜 미디어 서비스를 개발한다.
    - 사용자는 회원가입, 로그인, 로그아웃 등의 기본적인 유저 관리 기능을 편리하게 이용할 수 있어야 한다.
    - 소셜 미디어의 핵심 요소인 친구 관계, 포스트 작성, 댓글 등을 구현하여 사용자 간 상호 작용을 촉진한다.
- 대규모 트래픽 처리 환경을 구축하여 성능최적화를 진행한다.
- 개발된 서비스는 확장 가능하고 효율적으로 동작해야 한다.
- 모든 기능은 **4주** 안에 완성하는 것을 목표로 한다.

## 프로젝트 시작하기
* docker-compose 명령어
```shell
git clone https://github.com/bokyoung89/pre-order-service.git
docker-compose -f docker-compose.yml up
```

# 개발 기간

- 총 4주(2024.01.24.~2024.02.22.)

## 사용 기술

- Docker / Docker Compose
- Spring Boot
- JDK 11
- Gradle
- JWT
- HTTP Request / Response
- Kafka
- Hibernate
- DB 미정
- JUnit
- Github

## 주요 기능

**[소셜 미디어]**

- 유저 관리
- 팔로우 관계
- 포스트 및 뉴스피드
- 댓글 및 상호 작용

**[예약 구매]**

- Kafka 구축
- 쇼핑몰 목업 구축
- 상품 목록
- 상품 상세
- 주문 현황
- 어드민(재고관리)
- 결제 시뮬레이션
- 주문 큐
- 주문 처리
- 배치 작업
