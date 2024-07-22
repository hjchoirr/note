7/22 12시 

스프링 부트 
1. 소개 
2. 설정 
	context-path = '/'
	resource/application.properties
	
	 -> server.port=3000
	 
		server.port=3000
		spring.application.name=day01
		spring.devtools.livereload.enabled=true
	
	
	springboot dev tools - 서버 자동 재시작, 라이브 리로드(템플릿 변경사항 -> 브라우저 새로고침)
	
	file-> settings -> compiler -> build project automatically 체크
	 -> advanced settings -> compiler -> allow auto make to start  ... 체크 
	 
	application.properties
	 -> spring.devtools.livereload.enabled=true
	 
	 chrome 확장기능 - liveReload 설치
	 
	 : 개발시에만 사용하기 
	
	
	프로필 (중요)
	
	 spring.profiles.active  ( ****** )
	  - 기본값 default
	  
	 설정 값에 따라서 application-환경변수명.properties로 동작
	
	실행시 환경변수 설정 방법
	-D 환경변수명=값
	--환경변수명=값
	
	환경변수 (environment variable) spring.profiles.active=prod
	
	  =>  resource/application-prod.properties 사용하기
	

	jar
	 gradle build / mvn package
	 : 컴파일 -> 테스트 -> jar
	 
	 gradle jar 
	 : 컴파일 -> jar
	 
	 gradle bootJar
	 : 컴파일 -> jar
	 
	 gradle bootRun : 스프링 부트 시작 
	 
	 -----------------------------------------------------------------------------
	 D:\hjchoi\11.SPRING_BOOT\day01> gradle jar 
	 D:\hjchoi\11.SPRING_BOOT\day01\build\libs>java -jar day01-0.0.1-SNAPSHOT.jar
	 D:\hjchoi\11.SPRING_BOOT\day01\build\libs>java -jar -Dspring.profiles.active=prod day01-0.0.1-SNAPSHOT.jar  
	 D:\hjchoi\11.SPRING_BOOT\day01\build\libs>java -jar day01-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
	 -----------------------------------------------------------------------------
	
	YAML : Yet Another Markup Language
	       YAML is Arn't Markup Language
	  
	  확장자
	  yaml
	  yml
	
	  - 중괄호 없는 JSON
	  
	 설정파일(yml, properties)에 환경변수로 치환되는 부분을 직접 설정
	 ${환경 변수 명}
	  - 민감정보는 환경변수로 실행중에 설정
	  
	  quartz 의존성
	  
	  

3. @Scheduled
1) fixedDelay
2) fixedRate
3) initialDelay
4) cron
5) @EnableScheduling 

spring initializr 검색

 - https://start.spring.io/

gradlew : 그래들 버전 문제 발생 방지 : 그래들 버전을 당시 버전으로 유지해줌
HELP.md start 형태의 의존성 추가시마다 업데이트됨

resource/static 이미 만들어져 있음

스프링부트에는 이미 톰캣서버 내장되어 있음
WEBMVC - 그런 설정 이미 다 되어 있음
기본 패키지가 기본 스캔 범위임(Code Coverage)



Spring Data JPA

1. JPA 동작방식
 0) 
