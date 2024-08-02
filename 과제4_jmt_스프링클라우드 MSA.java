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




JWT(Json Web Token)

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
	
	
	
	
	dsccccdscdscdssssssssssssbbbbbbbbbbbbbbbb3333333333333
	base64 encode
	ZHNjY2NjZHNjZHNjZHNzc3Nzc3Nzc3Nzc2JiYmJiYmJiYmJiYmJiYmIzMzMzMzMzMzMzMzMz
	
	
	configServerUrl=http://localhost:3100;db.host=localhost;db.password=oracle;db.port=1521;db.username=BOARD_PROJECT;eurekaHost=http://localhost:3101;jwtSecret=ZHNjY2NjZHNjZHNjZHNzc3Nzc3Nzc3Nzc2JiYmJiYmJiYmJiYmJiYmIzMzMzMzMzMzMzMzMz;jwtValidSeconds=3600