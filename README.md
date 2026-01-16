## 1. 프로젝트 소개

본 프로젝트는 Spring Boot 기반의 사용자 및 기록 관리 웹 애플리케이션입니다.  
사용자 인증 흐름과 게시글 CRUD, 이미지 업로드 기능을 중심으로  
백엔드 기본 구조와 계층 분리를 학습하고 구현하는 것을 목표로 진행했습니다.

Controller – Service – Repository – Entity 구조를 명확히 분리하여  
유지보수성과 확장성을 고려한 설계를 적용했습니다.

---

## 2. 기술 스택

| 구분 | 기술 |
|----|----|
| Language | Java 21 |
| Framework | Spring Boot |
| ORM | Spring Data JPA |
| Database | H2 |
| Build Tool | Gradle |
| File Upload | MultipartFile + 로컬 파일 시스템 |

---

## 3. 주요 기능

### 3.1 사용자 기능

- 로그인 페이지 제공  
- 사용자 정보 기반 인증 흐름 구성  
- Spring Security 설정을 통한 요청 필터링 구조 이해  

---

### 3.2 게시글 기능

- 게시글 생성 / 조회 / 수정  
- 게시글 내용 변경을 위한 도메인 메서드 분리  
- 게시글과 이미지 파일 연계 처리  

---

### 3.3 이미지 업로드

- MultipartFile을 이용한 이미지 업로드  
- UUID 기반 파일명 생성으로 중복 방지  
- 서버 로컬 디렉토리에 파일 저장  
- WebConfig를 통한 /images/ URL 매핑 처리  

---

## 4. 계층 분리 (Layered Architecture)

### Controller
- HTTP 요청/응답 처리  
- 비즈니스 로직은 Service 계층에 위임  

### Service
- 핵심 비즈니스 로직 담당  
- 트랜잭션 단위 처리  

### Repository
- JPA 기반 데이터 접근 계층  
- Spring Data JPA 기본 메서드 활용  

### Entity
- 데이터와 변경 메서드를 함께 관리  
- 단순 DTO가 아닌 도메인 객체로 설계 

