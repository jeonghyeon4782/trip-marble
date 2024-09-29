# 두정마블

### 프로젝트 일정
2024.04.26 ~ 2024.05.23 (4주)


## ✨ 기획 배경

### 개요

여행지 선택을 돕기 위해 재미있는 콘텐츠를 제공하며, 방문한 장소에 대해서만 리뷰를 남길 수 있는 정보 공유 서비스

### 목적

-   여행지를 선택하는 어려움을 즐거움으로 변화시키기
-   여행에 관심 있는 사람들끼리 정보 공유 및 추천

### 현 상황

-   복잡한 사용자 인터페이스로 인한 피로도
-   여행지 리뷰의 신뢰성 문제

### 기대효과

-   복잡하지 않은 UI로 피로감 감소
-   유저들의 선호도와 트랜드 분석
-   편리한 여행지 선택 및 공유

## ✨ 기능 소개

### 로그인

-    카카오 소셜로그인으로 로그인
-    인증된 이메일로 아이디 및 비밀번호 찾기

|<div width="100%"><img src="https://github.com/user-attachments/assets/b893ec06-f660-4433-9c65-c6c217ce138b"></div>|
|:------------------------------------------------------:|

### 회원가입

-    아이디 및 닉네임 중복 검사
-    smtp를 이용한 이메일 인증
-    입력값 유효성 검사

|<div><img src="https://github.com/user-attachments/assets/801daf70-ebe8-4ba8-9ab2-3d06f7e7a692"></div>|
|:------------------------------------------------------:|

### 메인페이지

-    로그인 상태에 따른 네비게이션 바 변화

|<div><img src="https://github.com/user-attachments/assets/fda61ab1-9fac-44f0-8043-11bf792b2a86"></div>|
|:------------------------------------------------------:|


### 리뷰

-    리뷰 검색 기능
-    지역 필터링 기능으로 원하는 지역 리뷰 조회
-    최신순이나 좋아요 순으로 정렬 기능
-    페이징 기능

|<div><img src="https://github.com/user-attachments/assets/e7a106b8-e903-41ea-8969-378f0acf2364"></div>|
|:------------------------------------------------------:|


### 리뷰 상세보기

-    작성한 리뷰 수정 및 삭제
-    좋아요 기능

|<div><img src="https://github.com/user-attachments/assets/96547808-c7ab-428f-8ceb-4fd1c4d33507"></div>|
|:------------------------------------------------------:|


### 게시글 등록 및 수정

-    리뷰뷰 등록 및 수정
-    입력값 유효성 검증

|<div><img src="https://github.com/user-attachments/assets/792da328-7104-4ebf-be98-3b50b57e6f7c"></div>|
|:------------------------------------------------------:|


### 댓글

-    댓글 추가
-    작성한 댓글 수정, 삭제

|<div><img src="https://github.com/user-attachments/assets/652d7abd-04e9-4a89-871c-a63f2fbf10e6" ></div>|
|:------------------------------------------------------:|


### 마이페이지

-    유저 정보 조회
-    비밀번호 인증 후 유저 정보 수정 및 삭제
-    보드게임에서 도착한 관광지의 로그 조회

|<div><img src="https://github.com/user-attachments/assets/1b1281ef-e4d3-4ebb-b14b-36cebad19e28" ></div>|
|:------------------------------------------------------:|


### 회원탈퇴 및 수정

-    유저 프로필 변경

|<div><img src="https://github.com/user-attachments/assets/c2b3bcf2-491d-4f04-81cf-f501eecd879a" ></div>|
|:------------------------------------------------------:|


### 보드게임

-    보드게임으로 지역별 관광지 랜덤으로 추천

|<div><img src="https://github.com/user-attachments/assets/5df2c9a1-565c-4bf4-a143-6d8bc04e635e" ></div>|
|:------------------------------------------------------:|


## ✨ 기술 스택

-   **프론트엔드** : HTML, JavaScript, Vue.js
-   **백엔드** : Java17, Spring Boot, Spring Security, JWT, MySQL
-   **데이터베이스** : MySQL, Amazon S3
-   **이슈 관리** : Mattermost, Notion

## ✨ 기타 산출물

### 기능명세서

[기능 명세서](https://successful-mountain-ee3.notion.site/454d757178ac42b6b2767509eca3c132?v=13062621ba9e4ac0a0abf327d7c6757c&pvs=4)
![기능명세서](https://github.com/user-attachments/assets/d60a452a-014d-43f7-b896-0e9a12273fe8)

### ERD

![erd](https://github.com/user-attachments/assets/d5a0be54-86a1-420c-b18b-91a23d9c3eed)


### API 명세서

[API 명세서](https://successful-mountain-ee3.notion.site/83d63cf81da04958936713a9460d3501?v=ba1a1ef9872a4edbb94c8e6709ba22c9&pvs=74)
![api 명세서](https://github.com/user-attachments/assets/2970d7a3-c687-4222-9f20-d81d91c449c4)
