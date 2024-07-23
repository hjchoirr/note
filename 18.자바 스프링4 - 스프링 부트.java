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
	 컴파일시)
	 D:\hjchoi\11.SPRING_BOOT\day01> gradle jar 
	 실행시)
	 D:\hjchoi\11.SPRING_BOOT\day01\build\libs>java -jar day01-0.0.1-SNAPSHOT.jar
	 D:\hjchoi\11.SPRING_BOOT\day01\build\libs>java -jar -Dspring.profiles.active=prod day01-0.0.1-SNAPSHOT.jar  
	 D:\hjchoi\11.SPRING_BOOT\day01\build\libs>java -jar day01-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
	 -----------------------------------------------------------------------------
	
	YAML : Yet Another Markup Language
	       YAML is Arn't Markup Language
	  
	  확장자
	  yaml
	  yml
	
	  - 중괄호 없는 JSON 과 비슷.. 들여쓰기 주의 
	  
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
	 
	https://start.spring.io/
	#!type=gradle-project&language=java
	&platformVersion=3.3.2
	&packaging=jar
	&jvmVersion=17
	&groupId=org.choongang
	&artifactId=day01
	&name=day01
	&description=Demo%20project%20for%20Spring%20Boot
	&packageName=org.choongang
	&dependencies=lombok,devtools,web,thymeleaf,validation
	 

	의존성 
	 - lombok
	 - devtools ( fast application restarts, LiveReload,..)
	 - spring web (apache Tomcat embedded)
	 - Validation ( Validation with Hibernate validator )
	 - JPA
	 - oracle
	 - H2 Database ( a fast in-memory database)
	 

		수정 runtimeOnly 'com.h2database:h2' -> testRuntimeOnly 'com.h2database:h2' 
		추가 annotationProcessor 'jakarta.annotation:jakarta.annotation-api' //JPA 관련
		추가 annotationProcessor 'jakarta.persistence:jakarta.persistence-api' //JPA 관련



	gradlew : 그래들 버전 문제 발생 방지 : 그래들 버전을 당시 버전으로 유지해줌
	HELP.md start 형태의 의존성 추가시마다 업데이트됨

	resource/static 이미 만들어져 있음

	스프링부트에는 이미 톰캣서버 내장되어 있음
	WEBMVC - 그런 설정 이미 다 되어 있음
	기본 패키지가 기본 스캔 범위임(Code Coverage)



Spring Data JPA


	C:\>docker exec -it oracle-xe /bin/bash
	SQL> create user JPA_STUDY identified by oracle quota unlimited on users;
	SQL> grant connect, resource to JPA_STUDY;


	------------------------------
	resource/application.yml
	------------------------------
	server:
	  port: 3000

	spring:
	  datasource:
		driverClassName: oracle.jdbc.driver.OracleDriver
		url: jdbc:oracle:thin:@localhost:1521:XE
		username: ${db.username}
		password: ${db.password}

	  jpa:
		properties:
		  show_sql: true # 실행 sql 보여주기
		  format_sql: true # sql 들여쓰기, 줄개행
		  use_sql_comments: true
		  #dialect: org.hibernate.dialect.OracleDialect # 예전 버전은 꼭 명시 해야..
		hibernate:
		  ddlAuto: create

	logging:
	  level:
		org:
		  hibernate:
			type: trace

	------------------------------
	resource/application-test.yml
	------------------------------
	spring:
	  datasource:
		driverClassName: org.h2.Driver
		url: jdbc:h2:mem:test
		username: sa
		password:

	  jpa:
		properties:
		  show_sql: true # 실행 sql 보여주기
		  format_sql: true # sql 들여쓰기, 줄개행
		  use_sql_comments: true
		  #dialect: org.hibernate.dialect.OracleDialect # 예전 버전은 꼭 명시 해야..
		hibernate:
		  ddlAuto: create

	logging:
	  level:
		org:
		  hibernate:
			type: trace



Spring Data JPA 

	1. JPA 동작 방식
		0) ORM : 객체 <- 번역(ORM) -> DB

		1) JPA란?
		JPA (Java Persistence API) : ORM 표준 (Object Relational Mapping)
		Hibernate Entity Manager


		- 자바 영속성 API 
		
		- 영속성 : 상태 변화 감지 메모리 
		
			: 데이터 값이 변경 : UPDATE 쿼리 
			: 없는 데이터를 추가한 엔티티 : INSERT 쿼리 
			: 데이터 제거 : DELETE 쿼리 

		동일한 코드 -> 드라이버 변경 -> 플랫폼에 맞는 쿼리 실행 


		EntityManagerFactory 
			-> EntityManager : 엔티티 영속성 관리 


		2) JPA 동작 방식
			- 엔티티 : 엔티티 클래스의 정의 : 테이블의 정의 
						: 각각의 엔티티는 데이터 하나 하나 
			
			- 엔티티 매니저 팩토리
			- 엔티티 매니저
				1) find() : 조회, 기본키로 조회, 이미 영속성에 엔티티가 있으면 DB에서 조회 X - 1차 캐시, 성능상 이점 
				2) persist() : 영속성 컨텍스트에 엔티티를 영속  : 상태 감지 시작
				3) remove() : 영속성 상태 -> 제거 상태 : DELETE
				4) flush() : DB 반영 
						참고)
							find(..) 조회 메서드 호출시 flush()가 먼저 진행 되고 -> 조회 
				5) detach() : 영속성 분리 : 상태감지 X
				6) clear() : 영속성 전체 제거
				6) merge() : 분리된 영속성 -> 영속 상태  : 상태 감지 O
				
			- EntityManagerFactory 
			- EntityManager : 엔티티의 영속성 관리 

				----------------------------------------------------
				application-test.yml
				----------------------------------------------------
				spring:
				  datasource:
					driverClassName: org.h2.Driver
					url: jdbc:h2:mem:test
					username: sa
					password:

				  jpa:
					properties:
					  hibernate:
						show_sql: true # 실행 sql 보여주기
						format_sql: true # sql 들여쓰기, 줄개행
						use_sql_comments: true
						#dialect: org.hibernate.dialect.OracleDialect # 예전 버전은 꼭 명시 해야..
						ddlAuto: create

				logging:
				  level:
					org:
					  hibernate:
						type: info
				----------------------------------------------------
			
				@SpringBootTest
				@Transactional
				@TestPropertySource(properties = "spring.profiles.active=test")

				@Entity
				@Data
				public class Member {
					@Id
					private long seq;
					private String email;
					private String password;
					private String userName;
					private LocalDateTime createAt;
					private LocalDateTime modifiedAt;
				}
				public class Ex02 {
					@PersistenceContext
					private EntityManager em;
					@Autowired
					private SpringDataWebAutoConfiguration springDataWebAutoConfiguration;

					@BeforeEach
					void init() {
						for(long i = 1L; i <= 10L; i++) {

							Member member = new Member();
							member.setSeq(i);
							member.setEmail("user" + i + "@test.com");
							member.setPassword("12345678");
							member.setUserName("사용자" + i);
							member.setCreateAt(LocalDateTime.now());
							em.persist(member);

						}
						em.flush(); // DB insert
						em.clear(); // PersistenceContext 비우기
					}


					@Test
					void test1() {

						Member member = em.find(Member.class, 1L);  // 기본키로 조회
						System.out.println(member);

						Member member2 = em.find(Member.class, 1L);
						System.out.println(member2);

						System.out.println(member == member2);

						member.setUserName("(수정)사용자1");
						//em.flush(); // UPDATE 쿼리수행
						// 값 변경 후, 삭제 상태 변경 후 해당 데이터 조회하면 -> 암묵적으로 flush()
						System.out.println("find 전--");
						Member member3 = em.find(Member.class, 1L);
						System.out.println(member3);

					}
				}
			
		3) 영속성 컨텍스트 사용 시 이점
		4) 영속성 컨텍스트 
		5) 엔티티의 생명주기

			- 1차 캐시
			- 동일성 보장
			- 영속성 컨텍스트 쓰기 지연 SQL 저장소
			- 트랜잭션을 지원하는 쓰기 지연

		6) 설정하기
		
			- DDL_AUTO
			
				none : 아무런 변경 X
				create : 애플리케이션 시작시에 기존 테이블 DROP, 새로 생성
				create-drop : 애플리케이션 시작시에 기존 테이블 DROP, 새로 생성, 종료시에도 DROP
				update : 기존 테이블 DROP X, 변경 사항만 반영(제거  X)
				validate : 기존 테이블 DROP X, 변경 사항 체크(변경 사항이 있으면 예외 발생)
				
				개발시 : create, update 
				배포 서버 : none, validate ( **** )
			
	2. Entity 설계하기

		1) 엔티티 매핑 관련 애노테이션

			- @Entity : 
				엔티티명 : 기본값 : 클래스명
				참고) JPQL(Java Persistence Query Language)
				- name, value  - 엔티티 명을 직접 설정 
				
			- @Table 
			  - name : 테이블 명 
					 : 엔티티 명 == 테이블명 
				실제 테이블명 CH_MEMBER인데 클래스는 MEMBER 이면  @Table(name="CH_MEMBER")
				
					
			- @Temporal
				- Date, Calendar 클래스 관련 : 날짜, 시간, 날짜 + 시간 
				
				- java.time API  사용시 필요 X
					LocalDate, LocalTime, LocalDateTime 
			- @CreatedDate, @LastModifiedDate 
				: 엔티티 변화 감지를 통해서 변경(DB와 상관 X)
				: 변화 감지를 위한 이벤트 리스너
				
		2) 공통 속성화
			@MappedSuperclass : 공통 속성화를 위한 상위 클래스 
			
			
		3) @IdClass
			: 기본키를 여러 컬럼을 조합해서 생성 
			
			: 게시글 조회수 - UV
				브라우저 정보(User-Agent) + IP + 회원번호(0)  : UID  + 게시글 번호(ID)
		
		4) @GeneratedValue

			-----------------------------
			@Table 예제
			-----------------------------
			@Entity
			@Data
			//@Table(name="CH_MEMBER")
			@Table(indexes =  @Index(name="idx_created_at_desc", columnList = "createdAt DESC"))
			public class Member {
				@Id
				private long seq;
				private String email;
				private String password;
				private String userName;
				private LocalDateTime createAt;
				private LocalDateTime modifiedAt;
			}

			-----------------------------
			@Table 예제
			-----------------------------
			package org.choongang.member.entities;
			import jakarta.persistence.*;
			import lombok.Data;
			import java.time.LocalDateTime;

			@Entity
			@Data
			@Table(indexes = {
					@Index(name="idx_created_at_desc", columnList = "createdAt DESC"),
					@Index(name="uq_email_password", columnList = "email, password", unique=true)
				})
			public class Member {
				@Id
				private long seq;
				private String email;
				private String password;
				private String userName;
				private LocalDateTime createdAt;
				private LocalDateTime modifiedAt;
			}

			---------------------------
			@GeneratedValue, @Lob
			---------------------------
			@Entity
			@Data
			public class Member {
				@Id @GeneratedValue
				private long seq;
				private String email;
				private String password;
				private String userName;
				@Lob
				private String introduction;				
				
				@Enumerated(EnumType.STRING)   // 중요  EnumType.STRING : 디폴트값 쓰면 안됨
				private Authority authority;
				
				@CreationTimestamp   // 비표준 : hibernate 
				private LocalDateTime createdAt;
				@UpdateTimestamp     // 비표준 : hibernate 
				private LocalDateTime modifiedAt;
			}
