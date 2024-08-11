서버들

1. config server - 3100  name: config-server
2. eureka server - 3101 name: eureka-server
3. gateway server - 3000 name: gateway-server
4-1. api-server -  3001  name: api-service
4-2. admin-server - 3002 name: admin-service


==============
bootstrap.yml
==============

	1. config server 

	env:
	secretKey=cdscd\;k\;jcsdjcdcsdlkj;eurekaHost=localhost
	-------------------------------------
	server:
	  port: 3100

	spring:
	  application:
		name: config-server

	  profiles:
		active: native

	  cloud:
		config:
		  server:
			native:
			  search-locations: classpath:/configs

	encrypt:
	  key: ${secretKey}
	-------------------------------------

	2. eureka server

	env:
	configServerUrl=http://localhost:3100
	-------------------------------------
	spring:
	  application:
		name: eureka-server
	  cloud:
		config:
		  uri: ${configServerUrl}
	  loadbalancer:
		ribbon:
		  enabled: false
	-------------------------------------

	3. gateway server


	env:
	configServerUrl=http://localhost:3100;eurekaHost=http://localhost:3101

	-------------------------------------
	spring:
	  application:
		name: gateway-server
	  cloud:
		config:
		  uri: ${configServerUrl}
	-------------------------------------

	4-1. api-server 

	env:
	configServerUrl=http://localhost:3100;db.host=localhost;db.password=oracle;db.port=1521;db.username=BOARD_PROJECT;eurekaHost=http://localhost:3101
	-------------------------------------
	spring:
	  application:
		name: api-service  # 중요

	  cloud:
		config:
		  uri: ${configServerUrl}
	-------------------------------------

	4-2. admin-server
	env:
	configServerUrl=http://localhost:3100;db.host=localhost;db.password=oracle;db.port=1521;db.username=BOARD_PROJECT;eurekaHost=http://localhost:3101;file.path=D:/uploads;spring.profiles.active=dev

	-------------------------------------
	spring:
	  application:
		name: admin-service

	  cloud:
		config:
		  uri: ${configServerUrl}
	-------------------------------------


========================================
config-server 의 yml 들
========================================

	-------------------------------------
	eureka-server.yml
	-------------------------------------
	server:
	  port: 3101

	eureka:
	  instance:
		hostname: localhost
	  client:
		registerWithEureka: false
		fetchRegistry: false
		serviceUrl:
		  defaultZone:
			- http://${eureka.instance.hostname}:${server.port}/eureka/

	  server:
		waitTimeInMsWhenSyncEmpty: 30000 # 30초에 한번씩 인스턴스 검색


	-------------------------------------
	gateway-server.yml
	-------------------------------------
	server:
	  port: 3000

	spring:
	  cloud:
		gateway:
		  discovery.locator:
			enabled: true
			lowerCaseServiceId: true

		  routes:
			- id: api-service
			  uri: lb://api-service
			  predicates:
				- Path=/api/v1/**                                   #*/
			  filters:
				- RewritePath=/api/v1/?(?<path>.*),/$\{path}

			- id: admin-service
			  uri: lb://admin-service
			  predicates:
				- Path=/admin/**
			  filters:
				- RewritePath=/admin/?(?<path>.*),/$\{path}

	# 유레카 설정
	eureka:
	  instance:
		preferIpAddress: true
	  client:
		registerWithEureka: true
		fetchRegistry: true
		serviceUrl:
		  defaultZone: ${eurekaHost}/eureka/

	#액추에이터 설정
	management:
	  endpoint:
		gateway:
		  enabled: true

	  endpoints:
		web:
		  exposure:
			include: gateway

	-------------------------------------
	admin-service.yml
	-------------------------------------
	server:
	  port: 3002

	spring:
	  datasource:
		driverClassName: '{cipher}2e598bc25dbeddf43975c8667e8c193b4bac039ff059e9050563d17461e675bb494b709d16cef48cf8027d9a4b43e46e'
		url: '{cipher}a55d8978e320fbfbf30fd4342cff57f8ddbadfcd2bbbc44aff570b6dcbc4732e43b17c6477b5725959d20a16a05b9e63a2fe2b1f55760d5ab6cf5f57b6faf05e'
		username: ${db.username}
		password: ${db.password}

	  # JPA 설정
	  jpa:
		properties:
		  hibernate:
			show_sql: true
			format_sql: true
			use_sql_comments: true
		hibernate:
		  ddlAuto: create

	  # 파일 업로드 용량 설정
	  servlet:
		multipart:
		  maxFileSize: 20MB
		  maxRequestSize: 60MB
		  fileSizeThreshold: 30MB

	  # 정적 자원 설정(CSS, JS, 이미지)
	  web:
		resources:
		  staticLocations: classpath:/static/

	  # 타임리프 설정
	  thymeleaf:
		cache: true
		prefix: classpath:/templates/

	# 로거 설정
	logging:
	  level:
		org.hibernate.type: trace
		org.hibernate.orm.jdbc.bind: trace

	# 파일 업로드 경로 설정
	file:
	  upload:
		path: ${file.path}
		url: /upload/

	# 유레카 설정
	eureka:
	  instance:
		preferIpAddress: true
	  client:
		registerWithEureka: true
		fetchRegistry: true
		serviceUrl:
		  defaultZone: ${eurekaHost}/eureka/

	# 액추에이터 설정
	management:
	  endpoint:
		health:
		  showDetails: always
		refresh:
		  enabled: true
	  endpoints:
		web:
		  exposure:
			include: health, refresh
	-------------------------------------
	api-service.yml
	-------------------------------------
	server:
	  port: 3001

	spring:
	  datasource:
		driverClassName: '{cipher}2e598bc25dbeddf43975c8667e8c193b4bac039ff059e9050563d17461e675bb494b709d16cef48cf8027d9a4b43e46e'
		url: '{cipher}a55d8978e320fbfbf30fd4342cff57f8ddbadfcd2bbbc44aff570b6dcbc4732e43b17c6477b5725959d20a16a05b9e63a2fe2b1f55760d5ab6cf5f57b6faf05e'
		username: ${db.username}
		password: ${db.password}

	  jpa:
		properties:
		  hibernate:
			show_sql: true
			format_sql: true
			use_sql_comments: true
		hibernate:
		  ddlAuto: update

	  servlet:
		multipart:
		  maxFileSize: 20MB
		  maxRequestSize: 60MB
		  fileSizeThreshold: 30MB

	# 로거 설정
	logging:
	  level:
		org.hibernate.type: trace
		org.hibernate.orm.jdbc.bind: trace

	# 유레카 설정
	eureka:
	  instance:
		preferIpAddress: true
	  client:
		registerWithEureka: true
		fetchRegistry: true
		serviceUrl:
		  defaultZone: ${eurekaHost}/eureka/

	# 액추에이터 설정
	management:
	  endpoint:
		health:
		  showDetails: always
		refresh:
		  enabled: true
	  endpoints:
		web:
		  exposure:
			include: health, refresh
	# JWT
	jwt:
	  secret: ${jwtSecret}
	  validSeconds: ${jwtValidSeconds}			
	-------------------------------------
	api-service-test.yml
	-------------------------------------
	spring:
	  # 데이터베이스 설정
	  datasource:
		driverClassName: org.h2.Driver
		url: jdbc:h2:mem:test
		username: sa
		password:
	-------------------------------------
	api-service-dev.yml
	-------------------------------------
	file:
	  upload:
		path: ${fileUpload.path}
		url: /upload/


	  devtools:  # 타입리프쓸때
		livereload:
		  enabled: true

	  web:   # 타입리프쓸때
		resources:
		  staticLocations: file:src/main/resources/static/
	-------------------------------------
	admin-service-dev.yml
	-------------------------------------
	spring:

	  # 라이브 리로드 설정
	  devtools:
		livereload:
		  enabled: true

	  # 정적 자원 설정(CSS, JS, 이미지)
	  web:
		resources:
		  staticLocations: file:src/main/resources/static/

	  # 타임리프 설정
	  thymeleaf:
		cache: false
		prefix: file:src/main/resources/templates/
	-------------------------------------
	admin-service-test.yml
	-------------------------------------
	spring:
	  datasource:
		driverClassName: org.h2.Driver
		url: jdbc:h2:mem:test
		username: sa
		password:
	-------------------------------------
	
(참고) DOS 명령 curl


dos 명령 : curl -X POST http://localhost:3100/encrypt -d "암호화할 데이터"


C:\Users\admin>curl -X POST http://localhost:3100/encrypt -d "oracle.jdbc.driver.OracleDriver"
2e598bc25dbeddf43975c8667e8c193b4bac039ff059e9050563d17461e675bb494b709d16cef48cf8027d9a4b43e46e
C:\Users\admin>


C:\Users\admin>curl -X POST http://localhost:3100/encrypt -d "jdbc:oracle:thin:@${db.host}:${db.port}:XE"
a55d8978e320fbfbf30fd4342cff57f8ddbadfcd2bbbc44aff570b6dcbc4732e43b17c6477b5725959d20a16a05b9e63a2fe2b1f55760d5ab6cf5f57b6faf05e
C:\Users\admin>




=====================================
의존성 - build.gradle
=====================================
		-------------------------------------
		컨피그서버
		-------------------------------------

		plugins {
			id 'java'
			id 'org.springframework.boot' version '3.3.2'
			id 'io.spring.dependency-management' version '1.1.6'
		}

		group = 'com.jmt502'
		version = '0.0.1-SNAPSHOT'

		java {
			toolchain {
				languageVersion = JavaLanguageVersion.of(17)
			}
		}

		repositories {
			mavenCentral()
		}

		ext {
			set('springCloudVersion', "2023.0.3")
		}

		dependencies {
			implementation 'org.springframework.boot:spring-boot-starter-actuator'
			implementation 'org.springframework.cloud:spring-cloud-config-server'
			implementation 'org.springframework.cloud:spring-cloud-starter-bootstrap'

			testImplementation 'org.springframework.boot:spring-boot-starter-test'
			testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
		}

		dependencyManagement {
			imports {
				mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
			}
		}

		tasks.named('test') {
			useJUnitPlatform()
		}



		-------------------------------------
		유레카서버
		-------------------------------------
		plugins {
			id 'java'
			id 'org.springframework.boot' version '3.3.2'
			id 'io.spring.dependency-management' version '1.1.6'
		}

		group = 'com.jmt502'
		version = '0.0.1-SNAPSHOT'

		java {
			toolchain {
				languageVersion = JavaLanguageVersion.of(17)
			}
		}

		repositories {
			mavenCentral()
		}

		ext {
			set('springCloudVersion', "2023.0.3")
		}

		dependencies {
			implementation 'org.springframework.boot:spring-boot-starter-actuator'
			implementation 'org.springframework.cloud:spring-cloud-starter-config'
			implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'
			implementation 'org.springframework.cloud:spring-cloud-starter-bootstrap'

			testImplementation 'org.springframework.boot:spring-boot-starter-test'
			testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
		}

		dependencyManagement {
			imports {
				mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
			}
		}

		tasks.named('test') {
			useJUnitPlatform()
		}

		-------------------------------------
		게이트웨이서버
		-------------------------------------
		plugins {
			id 'java'
			id 'org.springframework.boot' version '3.3.2'
			id 'io.spring.dependency-management' version '1.1.6'
		}

		group = 'com.jmt502'
		version = '0.0.1-SNAPSHOT'

		java {
			toolchain {
				languageVersion = JavaLanguageVersion.of(17)
			}
		}

		repositories {
			mavenCentral()
		}

		ext {
			set('springCloudVersion', "2023.0.3")
		}

		dependencies {
			implementation 'org.springframework.cloud:spring-cloud-starter-bootstrap'

			implementation 'org.springframework.boot:spring-boot-starter-actuator'
			implementation 'org.springframework.cloud:spring-cloud-starter-config'
			implementation 'org.springframework.cloud:spring-cloud-starter-gateway'
			implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
			testImplementation 'org.springframework.boot:spring-boot-starter-test'
			testImplementation 'io.projectreactor:reactor-test'
			testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
		}

		dependencyManagement {
			imports {
				mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
			}
		}

		tasks.named('test') {
			useJUnitPlatform()
		}

		-------------------------------------
		admin-service
		-------------------------------------
		plugins {
			id 'java'
			id 'org.springframework.boot' version '3.3.2'
			id 'io.spring.dependency-management' version '1.1.6'
		}

		group = 'com.jmt502'
		version = '0.0.1-SNAPSHOT'

		java {
			toolchain {
				languageVersion = JavaLanguageVersion.of(17)
			}
		}

		configurations {
			compileOnly {
				extendsFrom annotationProcessor
			}
		}

		repositories {
			mavenCentral()
		}

		ext {
			set('springCloudVersion', "2023.0.3")
		}

		dependencies {
			implementation 'org.springframework.cloud:spring-cloud-starter-bootstrap'

			implementation 'org.springframework.boot:spring-boot-starter-actuator'
			implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
			implementation 'org.springframework.boot:spring-boot-starter-security'
			implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
			implementation 'org.springframework.boot:spring-boot-starter-validation'
			implementation 'org.springframework.boot:spring-boot-starter-web'
			implementation 'org.springframework.cloud:spring-cloud-starter-config'
			implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
			implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'
			compileOnly 'org.projectlombok:lombok'
			developmentOnly 'org.springframework.boot:spring-boot-devtools'
			runtimeOnly 'com.h2database:h2'
			runtimeOnly 'com.oracle.database.jdbc:ojdbc11'
			annotationProcessor 'org.projectlombok:lombok'
			testImplementation 'org.springframework.boot:spring-boot-starter-test'
			testImplementation 'org.springframework.security:spring-security-test'
			testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
		}

		dependencyManagement {
			imports {
				mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
			}
		}

		tasks.named('test') {
			useJUnitPlatform()
		}

-------------------------------------
-------------------------------------
-------------------------------------
-------------------------------------

확인


	http://localhost:3100/api-service/default
	http://localhost:3100/api-service/dev
	http://localhost:3100/api-service/test


	http://localhost:3101/
	http://localhost:3101/eureka/apps/api-service
	http://localhost:3101/eureka/apps/admin-service


	http://localhost:3001/actuator/
	http://localhost:3002/actuator/
	http://localhost:3001/actuator/health
	http://localhost:3002/actuator/health


	http://localhost:3000/actuator/gateway/routes

	http://localhost:3000/api-service/account/token
	http://localhost:3000/api/v1/account/token


	http://localhost:3002/main
	http://localhost:3101/eureka/apps/admin-service
	http://localhost:3000/admin-service/main




JWT(Json Web Token) 8/3 토요일 강의

	- OAuth 인증과 달리 토큰에 기본 인증 정보가 담겨있고, 길이가 더 길다. 그래서 DB에 저장할 필요 없음
	 
	  (참고)
	 - 인증 받으면 토큰 발급
	 - OAuth2 인증 - Open Auth
	 
	 - 유효시간 
	 - 리프레쉬 토큰 
	 
	header : signature를 해싱하기 위한 알고리즘 정보
	payload : 실제로 사용될 데이터
	signature : 토큰의 유효성 검증을 위한 문자열로 이 문자열을 통해 이 토큰이 유효한 토큰인지 검증
	aaaaaa(header).bbbbbb(payload).cccccc(signature)
	
	
	dependency {
	  ...
	  
	  implementation 'io.jsonwebtoken:jjwt-api:0.12.3'
	  implementation 'io.jsonwebtoken:jjwt-impl:0.12.3'
	  implementation 'io.jsonwebtoken:jjwt-jackson:0.12.3'
	  
	  ...
	}	
	
	
	
	jwtSecret 환경변수 
	
	conig 서버의 api-server.yml에 JWT 설정 추가하고 
	
	# JWT
	jwt:
	  secret: ${jwtSecret}
	  validSeconds: ${jwtValidSeconds}	

	api-server의 환경변수 추가
	
		configServerUrl=http://localhost:3100;db.host=localhost;db.password=oracle;db.port=1521;db.username=BOARD_PROJECT;eurekaHost=http://localhost:3101;jwtSecret=YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFiYmJiYmJiYmJiYmJiYmJiYmJiYmJiYmJiYmJiYmJiYmJiYmJiYmJjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2MxMTExMTExMTExMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMTQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NA==;jwtValidSeconds=3600


		configServerUrl=http://localhost:3100;db.host=localhost;db.password=oracle;db.port=1521;db.username=BOARD_PROJECT;eurekaHost=http://localhost:3101;jwtSecret=YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFiYmJiYmJiYmJiYmJiYmJiYmJiYmJiYmJiYmJiYmJiYmJiYmJiYmJjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2MxMTExMTExMTExMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMTQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NA==;jwtValidSeconds=3600;spring.profiles.active=dev


	스프링은 로그인 유지를 필터에서 한다
	
	- UsernamePasswordAuthenticationFilter
	
	
	
	CORS(Cross Origin Resource Sharing) : 교차 출처 자원 공유 정책
	
	 - 기본정책은 Same Origin : 같은 출처(서버)에서만 자원 공유
	 
	응답헤더 Acess-Control-Allow-Origin 설정해야 
	

2조 서버 세팅

	접속 도메인 
	jeommechu.xyz


	ADMIN 서버 
	13.125.102.94:3002


	ORACLE 서버
	13.125.102.94:1521


	API 서버
	43.202.80.182:3001


	젠킨스 서버
	54.180.14.104:8080

	

	- 컨피그 서버(3100), 유레카 서버(3101), 게이트웨이(3000)
	- 엔진엑스

	13.124.204.160






	기본 설정 구현
	회원 관리 구현
	리뷰 관리 구현
	AI기반 메뉴 추천 기능 구현


	http://jeommechu.xyz:3101/
	http://jeommechu.xyz/api/v1/account ??


	http://jeommechu.xyz:3101/


	http://jeommechu.xyz:3101/eureka/apps/api-service
	
	http://13.124.204.160:3100/admin-service/default



	점메추 서버의 
	유레카 설정 바꾸기 - config-server 의 api-service.yml , admin-servoce.yml, gateway-server.yml

	# 유레카 설정
	eureka:
	  instance:
		hostname: ${hostname}
		#preferIpAddress: true
		
		

	아마존 서버 세팅 강의 8/5 10시부터


	aws
	EC2 - Elastic Cloud Computing

	13.124.204.160 

	http://13.124.204.160/



	sudo -s -> 

	exit


	apt / apt-get
	 - install 설치
	 - update 업데이트


	자바 설치 apt-get openjdk-17...

	아파치 - 동기식
	nginx - 비동기식

	reverse proxy 설정 -> 80, 443 -> 3000

	systemctl start|stop|reload|restart|status



	http://54.180.14.104:8080/github-webhook/   ***



	http://jenkins.jeommechu.xyz:8080/

	- 아이디 : admin
	- 비밀번호 : _aA123456


	http://jenkins.jeommechu.xyz:8080/github-webhook/


	http://jeommechu.xyz/actuator/gateway/routes

	http://jeommechu.xyz:3101/

	http://jeommechu.xyz/admin-service/

	http://jeommechu.xyz/admin/


	gateway서버
	api 서버
	관리자서버
	 환경변수 hostname=localhost
	 
	 
	 
======================================================================================================= 
 
리액트


		cd d:\react\react_project
		npm i ( == yarn install )




		리액트의 기본포트 3000 -> 

		package.json -> 

		scripts": {
			"start": "set PORT=4000 &&& react-scripts start",
		   

		yarn start


		page
		 
		리액트 환경변수 
		.env.development.local

			REACT_APP_API_URL=http://localhost:3000/api/v1  : api 서버 URL + contextPath(?)
		 
		fetch-API 안되고 axios 사용
		D:\react\react_project>yarn add axios
 
 



http://jeommechu.xyz:3101/eureka/apps/admin-service
http://jeommechu.xyz:3101/eureka/apps/api-service



D:\react\react_project>yarn add react-cookies



https://seoul.openapi.redtable.global/


서울관광재단 API 토큰 : 개인
8Mu7gNxO98975QV25VMKBnsDC82WaomG1raYEiOXoi3kOTGsi89KCUJBxZI0HNz6

1	메뉴정보 한국어	/api/menu/korean	메뉴정보 한국어 조회
2	메뉴정보 영어	/api/menu/eng	메뉴정보 영어 조회
3	메뉴정보 일본어	/api/menu/jpnse	메뉴정보 일본어 조회
4	메뉴정보 중국어	/api/menu/chchr	메뉴정보 중국어 조회
5	음식이미지정보	/api/food/img	음식이미지정보 조회
6	메뉴설명정보 한국어	/api/menu-dscrn/korean	메뉴설명정보 한국어 조회
7	메뉴설명정보 중국어	/api/menu-dscrn/chchr	메뉴설명정보 중국어 조회
8	메뉴설명정보 영어	/api/menu-dscrn/eng	메뉴설명정보 영어 조회
9	메뉴설명정보 일본어	/api/menu-dscrn/jpnse	메뉴설명정보 일본어 조회
10	식당이미지정보	/api/rstr/img	식당이미지정보 조회
11	식당기본정보	/api/rstr	식당기본정보 조회
12	식당운영정보	/api/rstr/oprt	식당운영정보 조회
13	식당품질정보	/api/rstr/qlt	식당품질정보 조회


식당기본정보 조회
Call Back URL https://seoul.openapi.redtable.global/api/rstr
              https://seoul.openapi.redtable.global/api/rstr?serviceKey=인증키(URL Encode)
              https://seoul.openapi.redtable.global/api/rstr?serviceKey=8Mu7gNxO98975QV25VMKBnsDC82WaomG1raYEiOXoi3kOTGsi89KCUJBxZI0HNz6
serviceKey 인증키(URL Encode) 발급받은 인증키
pageNo 페이지 번호

RSTR_ID 		식당ID 
RSTR_NM 		식당명 
RSTR_RDNMADR 	도로명주소
RSTR_LNNO_ADR 	지번주소
RSTR_LA 		식당위도 18 0 37.572857
RSTR_LO 		식당경도 18 0 126.98557
RSTR_TELNO 		식당대표전화번호 30 0 02-725-00
BSNS_STATM_BZCND_NM 영업신고증업태명
BSNS_LCNC_NM 	영업인허가명 50 0 일반음식점 영업인허가명
RSTR_INTRCN_CONT 	음식점소개내용 8000
pageNo 			페이지번호 4 1 1 페이지 번호
numOfRows 		표시건수 4 1 10 한 페이지 결과수
totalCount 		전체건수 4 1 100 전체 데이터 건수
resultCode 		결과코드 2 1 00 결과코드
resultMsg 		결과메세지 50 1 NORMAL




https://seoul.openapi.redtable.global/api/rstr?serviceKey=인증키(URL Encode)


서버간 로그인 유지

	Spring session
	Spring data redis

	직렬화 

	redis - 윈도우에 설치못함 -> 도커로 ..-> 오라클 서버에 도커 있으니까.. 거기.. default port : 6379 
	
		이건 PC에 테스트로 설치해봄
		D:\>docker pull redis	
		D:\>docker images
		D:\>docker run -d -p 6379:6379 --name redis redis
		D:\>docker exec -it redis /bin/bash

		root@2f224eb1a67e:/data# redis-cli
		127.0.0.1:6379> set key1 value1
		OK
		127.0.0.1:6379> keys *
		1) "key1"
		127.0.0.1:6379> get key1
		"value1"
		127.0.0.1:6379>		
		
		----------------------------------------------
		D:\>docker pull redis
		Using default tag: latest
		latest: Pulling from library/redis
		efc2b5ad9eec: Pull complete
		82797145fff6: Pull complete
		405e1ffae71e: Pull complete
		0beb16fe974a: Pull complete
		73eb92ddeed3: Pull complete
		87e613039f4a: Pull complete
		4f4fb700ef54: Pull complete
		9579b898bbe4: Pull complete
		Digest: sha256:79676a8f74e4aed85b6d6a2f4e4e3e55d8a229baa7168362e592bbfdc67b0c9b
		Status: Downloaded newer image for redis:latest
		docker.io/library/redis:latest

		What's Next?
		  1. Sign in to your Docker account → docker login
		  2. View a summary of image vulnerabilities and recommendations → docker scout quickview redis

		D:\>docker images
		REPOSITORY                  TAG       IMAGE ID       CREATED        SIZE
		redis                       latest    509b2fc82da6   8 days ago     117MB
		gvenzl/oracle-xe            18        df6621d4edd4   2 months ago   2.61GB
		loliconneko/oracle-ee-11g   latest    b1ed15c38b8c   5 years ago    5GB

		D:\>docker run -d -p 6379:6379 --name redis redis
		2f224eb1a67eecffffb6f9e3a59e14f3773b9e0fd5f0a293efc44189725f7d59

		D:\>docker exec -it redis /bin/bash
		root@2f224eb1a67e:/data# redis-cli
		127.0.0.1:6379> set key1 value1
		OK
		127.0.0.1:6379> keys
		(error) ERR wrong number of arguments for 'keys' command
		127.0.0.1:6379> keys *
		1) "key1"
		127.0.0.1:6379> get key1
		"value1"
		127.0.0.1:6379>	
		----------------------------------------------


	의존성 api, admin
	Spring Boot Starter Data Redis
	
	Spring Boot Starter Data Redis » 3.3.2
	implementation 'org.springframework.boot:spring-boot-starter-data-redis:3.3.2'
	Spring Session Data Redis
	implementation 'org.springframework.session:spring-session-data-redis:3.3.1'
	
	=>
	
	implementation 'org.springframework.boot:spring-boot-starter-data-redis'
	implementation 'org.springframework.session:spring-session-data-redis'



지도 

		카카오지도 javascript 앱키 : 

		앱이름 : hj***

		<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=발급받은 APP KEY를 사용하세요"></script>
		<script>



	<AI기반 메뉴 추천 식당 예약 서비스 - 점메추>

	1. 회원 - 회원가입(관심지역 설정), 로그인, 이메일 전송(회원가입 인증, 비밀번호 초기화)
	   마이페이지 - 회원정보 수정, 찜한 내역, 리뷰 작성
	   (수민)

	2. 메인 페이지 - 상단 메뉴, 관심지역, 검색, 배너, 음식 카테고리
	   매장 목록 페이지
	   (재은)

	3. 매장 정보 - 지도 API, 메뉴, 리뷰 조회(사진 업로드), 찜하기
		(민혁)
		

	4. 예약, 결제 페이지 - 예약내역
		(정인)

	5-1. 메뉴 추천(AI) 페이지
	5-2. 관리자 - 회원 관리, 리뷰 관리
		(회원)



mobile page 만들기 

    <meta name="viewport" content="width=device-width,user-scalable=yes,initial-scale=1.0,minimum-scale=0.5,maximum-scale=2.0">

	크롬 F12 -> 네트워크 -> 요청헤더 -> User-Agent 
	  : Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Mobile Safari/537.36
		
	  : Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1

	".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

	?device=PC PC뷰로 수동이동
	?device=MOBILE 모바일뷰로 수동이동
	=> interceptor : 컨트롤러 처리 전에 동작하기 위해 preHandle 
	
	

		@Component
		public class CommonInterceptor implements HandlerInterceptor {
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
				//모든 컨트롤러 전에 ..
				checkDevice(request);

				return HandlerInterceptor.super.preHandle(request, response, handler);
			}

			private void checkDevice(HttpServletRequest request) {
				String device = request.getParameter("device");
				if(StringUtils.hasText(device)) {
					return;
				}

				device = device.toUpperCase().equals("MOBILE") ? "MOBILE" : "PC";
				HttpSession session = request.getSession();
				session.setAttribute("device", device);
			}
		}


		@Configuration
		@RequiredArgsConstructor
		public class InterceptorConfig implements WebMvcConfigurer {

			//사이트 공통 인터셉터
			private final CommonInterceptor commonInterceptor;

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				//공통 적용 - 모든 페이지에 적용할때 ** 설정은 생략 가능함
				registry.addInterceptor(commonInterceptor);
			}
		}



react kakao map

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=발급받은 APP KEY를 사용하세요&libraries=services"></script>
<script>






AI Server

https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.3.2&packaging=jar&jvmVersion=17&groupId=com.jmt&artifactId=ai&name=ai&description=AI%20PROJECT&packageName=com.jmt&dependencies=devtools,lombok,web,cloud-config-client,cloud-eureka,spring-ai-openai,spring-ai-vertexai-gemini

build.gradle에 추가
	implementation 'org.springframework.cloud:spring-cloud-starter-bootstrap'
	implementation 'org.springframework.boot:spring-boot-starter-actuator'



config-server 의 ai-service.yml 파일 추가

	server:
	  port: 3003






https://platform.openai.com/api-keys

API keys :


gemini project-id : jmt502