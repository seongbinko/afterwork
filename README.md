[![Build Status](https://travis-ci.com/seongbinko/afterwork.svg?branch=master)](https://travis-ci.com/seongbinko/afterwork)

# AFTER WORK - BACKEND SERVER REPOSITORY

## [AFTER WORK](https://afterwork.co.kr/) 서비스 소개

- 넘쳐나는 취미 플랫폼 클래스들을 한 번에 모아볼 수 있는 사이트

- 유명 플랫폼 7개사 클래스 인기, 가격 한 번에 비교
- 구글/네이버/카톡 아이디로 간편하게 소셜 로그인
- 사용자별 관심 지역, 분야 설정을 통한 맞춤 추천

## 🎯 Target

- 퇴근 후 뭐하지? 고민하는 직장인
- 직장인 58% 재택근무 경험…90% “증가된 여가시간에 새 취미생활 하고파” <br/> ('20.03 직장인 소셜미디어 블라인드 설문조사 결과)
- 넘쳐나는 취미 플랫폼과 클래스 정보들로 혼란스러운 이들

### AFTER WORK - FRONTEND REPOSITORY

- https://github.com/miniPinetree/_AfterWork

### AFTER WORK - BACKEND SCHEDULER REPOSITORY

- https://github.com/seongbinko/afterwork-scheduler

## 개요

- 명칭 : AFTER WORK - SERVER
- 개발 인원 : 프론트(React) 3명, 백엔드(Spring) 3명, 디자이너 1명
- 개발 기간 : 2021.04.25 ~ 2021.05.12
- 운영 기간 : 2021.05.13 ~ 진행중 (의견을 바탕으로 서비스 개선)
- 담당 업무 :
  - 고성빈
    - 인프라 구축
    - 테이블 설계
    - 소셜 로그인(Google, Kakao, Naver), 회원정보 조회 api 구현
    - Http -> Https 전환
    - 빌드 자동화 및 서비스 무중단 배포 구현
  - 김남석
    - 클래스톡, 하비인더박스, 마이비스킷, 클래스101 웹사이트 자동 크롤링 구현
    - 카테고리 별 리스트, 검색, 카테고리별 추천 리스트, 온라인 추천 리스트, 오프라인 추천 리스트 api 구현
    - 카테고리 조회 api 구현
    - 회원정보 수정 / 탈퇴 api 구현    
  - 최재성
    - 하비풀, 모카클래스, 탈잉 웹사이트 자동 크롤링 구현
    - 찜 항목 저장, 개별 및 전체 삭제, 조회 api 구현
    - 지역별 추천 상품 api 구현
- 개발 환경 : Springboot 2.4.5, Jdk 1.8, Spring Security, Spring JPA, Junit5
- 배포 환경 : Gradle, Travis CI, AWS S3, AWS CodeDeploy, AWS EC2
- 웹 서버 : Nginx, Tomcat 9.0
- 데이터베이스 : MariaDB 10.4 (AWS RDS)
- 협업툴: Git, Notion, Slack
- 간단 소개 : 퇴근하고 뭐할지 고민하는 사람들을 위한, 모든 플랫폼들의 취미들을 한 곳에서 비교가능한 서비스 <br/> 사용자는 관심 지역, 관심 카테고리에 따라 클래스를 추천받을 수 있다
- 주요 기능 : 소셜 로그인(Google, Kakao, Naver), 검색기능, 찜 기능, 사용자 관심지역, 카테고리별 추천 기능
- 특징 : 서비스의 데이터를 정기적으로 최신화 ([Afterwork-Scheduler-Repository](https://github.com/seongbinko/afterwork-scheduler)) <br/>
  빌드 자동화를 통한 서비스 무중단 배포 구현

## 테이블 설계

![스크린샷 2021-05-17 오후 2 06 13](https://user-images.githubusercontent.com/60464424/118435052-163b9100-b719-11eb-9214-977d95a696b0.png)

## API 설계

- [Detail api document](https://documenter.getpostman.com/view/11580833/TzRVdRFD)

|기능|Method|URL| Request Params / Body|
|:---|:---:|:---:|:---:|
|카테고리 목록|GET|/api/categorys||
|카테고리 별 리스트|GET|/api/categorys/{categoryId}|page, size, sort, direction, filter, sitename|
|찜 목록 불러오기|GET|/api/collects||
|찜 등록|POST|/api/collects| productId |
|찜 목록 전체 삭제|DELETE|/api/collects||
|찜 목록 개별 삭제|DELETE|/api/collects/{collectId}||
|위치별 추천 리스트|GET|/api/recommend||
|관심카테고리 별 추천 리스트|GET|/api/recommend/categorys||
|온라인 추천 리스트|GET|/api/recommend/online||
|오프라인 추천 리스트|GET|/api/recommend/offline||
|검색|GET|/api/search|page, size, sort, direction,filter|
|회원정보 수정|POST|/api/user| offtime, locations, categorys |
|회원정보 탈퇴|DELETE|/api/user||
|로그인 회원정보 조회|GET|/api/user/me| |

## 타임라인

| 일자       | 진행 목록                                                    |
| ---------- | ------------------------------------------------------------ |
| 2021.04.25 | 프로젝트 메인 및 테스트 디렉토리 생성, Gradle Dependencies 설정 - [고성빈] |
| 2021.04.26 | Entity 디렉토리 생성, 와이어 프레임 대로 Category/Collect/FeedBack/Interest/Product/User 및 상속 받을 BaseEntity 구현 - [고성빈] <br/> Spring security, JsonWebToken 추가, 시큐리 configuration 구현, 실행앱 yaml 설정 - [고성빈] <br/> Social login 기본 구조 구현 (Auth, OAuth2), UserPrinciple 구현 - [고성빈]|
| 2021.04.27 | Product price 데이터 타입 변경, Price info, Site name 변수명 변경, Site URL 컬럼 추가, Popularity 컬럼 수정 - [고성빈] <br/> Gradle 설정 수정, Profile Controller api 구현 - [고성빈] <br/> h2-console 및 test 확인, 상황별 데이터 베이스를 사용하기 간편하게 각종 Yaml 객체 구현 - [고성빈] |
| 2021.04.28 | Category repository, CategoryResponseDto, Product controller, Category controller 및 api 구현 - [김남석] <br/> Security 허용 범위 설정 - [김남석] <br/> Category 별 리스트 api 구현, 페이지 요청 구현 (Pageable) - [김남석] |
| 2021.04.29 | Category 별 리스트를 페이지 요청시 오름차순, 내림차순으로 정렬하여 반환될 수 있게끔 구현, 대문자 소문자 구분 조건 추가 - [김남석] <br/> User 정보 조회, 수정, 삭제 controller 및 api 구현 - [김남석] <br/> FeedBack controller api, FeedbackRequestDto, FeedbackRepository 구현 - [김남석] <br/> Search 기능 api 구현 - [김남석]  <br/> 상품에 대한 찜 등록, 전체 삭제, 개별 삭제 controller 및 api, Service, Repository 구현 - [최재성] |
| 2021.04.30 | BaseEntity를 상속받은 Entity 클래스 모두 BaseTimeEntity 상속으로 변경 - [고성빈] <br/> Collect 항목 전체 조회 api 구현 - [최재성] <br/> |
| 2021.05.01 | Collect 항목 전체 조회시 반환 될 값이 Collect 가 아닌 Product 항목으로 fix - [최재성] |
| 2021.05.03 | Feedback controller 를 위한 valid 검사 추가 - [고성빈] <br/> Product repository 에 query 어노테이션 추가 (중복 검색을 위한 방지) - [김남석] <br/> Recommend 리스트 controller 및 api 구현 - [최재성] |
| 2021.05.04 | LocationResponseDto 클래스 어노테이션 추가, User controller 에 Authorize 어노테이션 추가 - [고성빈] |
| 2021.05.05 | Product repository 에 검색 조건 추가 (중복 검색 제거), User 삭제시 연결된 Location, Interest, Collect 가 삭제되어야 가능하도록 fix - [김남석] <br/> Collect 상품에 대한 중복 fix - [최재성] |
| 2021.05.06 | User 관련된 테스트 코드 작성 및 fix - [고성빈] <br/> Category 관련된 테스트 코드 작성 - [김남석] |
| 2021.05.07 | Category api 에 filter 기능 추가 - [김남석] <br/> Recommend 상품에 대한 랜덤 12개를 status = 'Y' 만 반환 되게끔 수정 - [최재성] |
| 2021.05.08 | 로그인 오류 메세지 수정, 크롤링 기능 및 업데이트 클래스 전부 삭제 -> 새로운 Afterwork-Scheduler 에 옮김 - [고성빈] |
| 2021.05.10 | Category 별 추천 기능, Authorize 어노테이션 추가 - [김남석] <br/> Product Repository 에 Native query 를 JPQL 로 변경, group by 추가 - [김남석] <br/> Search 관련된 테스트 코드 작성 - [김남석] |
| 2021.05.11 | Recommend Category 리스트 관련된 테스트 코드 작성 - [김남석] <br/> Recommend 상품, Collect 관련된 테스트코드 작성 - [최재성] |
| 2021.05.12 | Controller 에 구현한 api 를 service 로 refactoring - [김남석] [고성빈] <br/> Collect, Recommend api 에 예외 처리 추가 - [최재성] <br/> 프로젝트 성능 개선을 위해 불필요한 import , 주석, 출력 모두 제거 - [고성빈] <br/> License 구현 - [고성빈] |
| 2021.05.13 | SecurityConfig /api/** permit 에서 사용하는 api만 permit 으로 fit - [김남석] <br/> 검색 필터 오류(online,offline 둘다 되는 경우 total일 경우에만 나타나는 현상) fit - [김남석] <br/> start.sh log파일 생성하도록 변경 - [고성빈]|
| 2021.05.15 | Recommend service, Recommend category 중복 값 제거 메소드 추가 - [김남석] [최재성] |
| 2021.05.17 | 오늘의 오프라인 클래스, 온라인 클래스 추천을 위한 컬럼 추가 (is_recommend_offline, is_recommend_online) - [고성빈] <br/> 정확한 로그 확인을 위한 application.yml 정보 수정 - [고성빈] <br/> 온라인/오프라인 랜덤추천 기능 추가 - [김남석]|
| 2021.05.18 | 온라인/오프라인 랜덤추천 기능 테스트코드 추가 - [김남석]|
| 2021.05.19 | 사이트이름 별 필터 기능, 테스트코드 추가 - [김남석] <br/> 정렬 오류 수정 (sort에 title도 추가) - [김남석] <br/> 토큰 만료시간 30분으로 변경 - [고성빈] |