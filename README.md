# Spring + JPA를 이용하여 간단한 게시판 만들기
spring과 jpa를 이용하여 간단한 게시판 프로젝트를 진행하였습니다.

게시글의 CRUD와 로그인 까지 구현을 하였습니다.

## 1. 제작기간 & 프로젝트 규모
- 2021/09/22 ~ 2021/09/29
- 소규모 개인 프로젝트

## 2. 사용 기술
- `Backend`
  - JPA
  - Spring Boot
  - Spring web
  - Lombok
- `Frontend`
  -  Thymeleaf
- `DB`
  - h2
  - mysql
  
  
## 3. 실행 화면
#### 1. 로그인 화면
![image](https://user-images.githubusercontent.com/23234577/135236608-02d8e41b-ef0a-4d23-9ca5-897737ffe309.png)

#### 2. 회원가입 화면
![image](https://user-images.githubusercontent.com/23234577/135236813-05321da3-5023-45e0-971a-8793ba9e92a4.png)

#### 3. 게시글 목록 화면
![image](https://user-images.githubusercontent.com/23234577/135237073-40f7138b-1495-4168-844a-49da90cdbb8a.png)

#### 4. 게시글 자세히 보기 화면
![image](https://user-images.githubusercontent.com/23234577/135237145-c565bd8e-d5f5-4af8-a8ff-e6c1fc782418.png)

#### 5. 게시글 수정 화면
![image](https://user-images.githubusercontent.com/23234577/135237212-1426f98d-201d-43ce-b773-7d0346e5d16b.png)

#### 6. 게시글 등록 화면
![image](https://user-images.githubusercontent.com/23234577/135237264-10b2a4e6-ac69-4541-9182-ee2be9da5f74.png)

## 4. 핵심 기술
- 로그인, 회원가입
  - HttpSession을 이용하여 로그인과 회원가입을 구현하였습니다.
  - session을 통해 브라우저를 닫고 재접속을 하여도 로그인 상태가 유지 됩니다.
- 간단한 게시글 CRUD
  - 게시글 추가가 가능하며, 이 때 작성이 이름은 로그인한 이메일 값으로 고정됩니다.
  - 게시글 삭제가 가능합니다.
  - 게시글 수정이 가능합니다.
  - 게시글 목록보기가 가능합니다.
  - 게시글 1개 자세히 보기가 가능합니다.

## 5. 해결한 문제 정리해보기
- `frontend`
  - 게시글 등록 시 post api를 등록해야하는 문제
    - 템플릿 내 jQuery를 통하여 ajax를 통해 해결
  - 한 개 선택시 querystring을 통하여 id와 함꼐 get 통신을 해야하는 문제
    - 타임리프의 문법을 통해 간단히 id값을 queryString으로 등록하도록 해결
  - css 문제
    - 부트스트랩이 제공하는 표준 css 적용
- `backend`
  - frontend 에서 요청한 데이터를 response하거나 html을 렌더링 해야하는 문제
    - @controller와 함께 spring mvc 기법을 활용하고 타임리프를 통해서 해결
  - 로그인 시 email을 통한 중복 확인
    - jpa의 "findByEmail" 함수를 재정의하여 해결
  - 게시글 목록의 등록 시간의 내림차순으로 정렬해야하는 문제
    - jpa findAll 함수를 ".findAll(Sort.by(Sort.Direction.DESC, "createdAt"))" 를 통해서 해결
  - 게시글 1개 보기와 게시글 목록 보기에 나타나는 데이터를 다르게 나타내야하는 문제
    - DTO를 이용하여 게시글 1개 보기와 게시글 목록 보기의 DTO를 달리하여 해결, 목록 보기는 추가로 List를 활용하여 해결
  

