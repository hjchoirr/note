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
	
			: springboot dev tools - 서버 자동 재시작, 라이브 리로드(템플릿 변경사항 -> 브라우저 새로고침)
	
	inteliJ세팅 	file-> settings 
	- Build, Execution, Deployment -> compiler -> build project automatically 체크
	- advanced settings -> compiler -> allow auto make to start  ... 체크 
	 
	application.properties
	 -> spring.devtools.livereload.enabled=true  ## : 개발시에만 사용하기 
	 
	 chrome 확장기능 - liveReload 설치
	 
	
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
		  ddlAuto: create   # 실행시 테이블 drop 하고 create  자동으로 ...> 꼭 테스트환경에서만 사용하기

	logging:
	  level:
		org.hibernate.type: trace

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
	// 스프링과 조합 가능 : Redis or Mongodb or spring Data JDBC or JPA 

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
				
				@SpringBootTest
				@Transactional
				@TestPropertySource(properties = "spring.profiles.active=test")
				public class Ex02 {
				
					@PersistenceContext // 이걸로 @Autowired 역할까지 
					private EntityManager em;
					
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
				실제 테이블명 CH_MEMBER인데 클래스는 MEMBER 이면  
				
				@Table(name="CH_MEMBER")

				@Table(indexes =  @Index(name="idx_created_at_desc", columnList = "createdAt DESC"))

				@Table(indexes = {
						@Index(name="idx_created_at_desc", columnList = "createdAt DESC"),
						@Index(name="uq_email_password", columnList = "email, password", unique=true)
					})				
					
			- @Temporal
				- Date, Calendar 클래스 관련 : 날짜, 시간, 날짜 + 시간 
				
				- java.time API  사용시 필요 X
					LocalDate, LocalTime, LocalDateTime 
					
			- @CreatedDate, @LastModifiedDate 
			
				: 엔티티 변화 감지를 통해서 변경(DB와 상관 X)
				: 변화 감지를 위한 이벤트 리스너
				
		2) 공통 속성화
			@MappedSuperclass : 공통 속성화를 위한 상위 클래스 

			-----------------------------------------------------
			@Getter @Setter
			@MappedSuperclass
			public abstract class BaseEntity {

				@CreatedDate //스프링 표준 에노테이션 
				private LocalDateTime createdAt;

				@LastModifiedDate //스프링 표준 에노테이션 
				private LocalDateTime modifiedAt;
			}			
			-----------------------------------------------------


			@CreatedDate, @LastModifiedDate -> 엔티티의 상태변화에 따라서 값이 업데이트
			
			Auditing을 이용한 엔티티 공통 속성화

			1. @MappedSuperclass
			2. AuditorAware 인터페이스 
			3. @EntityListeners
			4. @EnableJpaAuditing

			@Getter @Setter
			@MappedSuperclass
			@EntityListeners(AuditingEntityListener.class)
			public abstract class BaseEntity {

				@CreatedDate //스프링 표준 에노테이션
				private LocalDateTime createdAt;

				@LastModifiedDate //스프링 표준 에노테이션
				private LocalDateTime modifiedAt;
			}

			@EnableJpaAuditing
			@Configuration
			public class MvcConfig implements WebMvcConfigurer {
			}


		
			
		3) @IdClass
			: 기본키를 여러 컬럼을 조합해서 생성 
			
			: 게시글 조회수 - UV
				브라우저 정보(User-Agent) + IP + 회원번호(0)  : UID  + 게시글 번호(ID)

		  복합키 만들기 - 복합키 컬럼으로 클레스를 만들어서 Entitiy에서 사용
		
			방법1)
	
			@EqualsAndHashCode  // 동등성, 동일성 비교??
			@AllArgsConstructor
			@NoArgsConstructor
			public class BoardViewId {
				private long seq;
				private int uid;
			}
					
			@Data
			@Entity
			@IdClass(BoardViewId.class)
			public class BoardView {
				@Id
				private long seq;

				@Id
				@Column(name="_uid")   // uid : 오라클 예약어라서 컬럼명으로 못씀
				private int uid;
			}

			방법2)
			
			@EqualsAndHashCode
			@AllArgsConstructor
			@NoArgsConstructor
			@Embeddable
			public class BoardViewId {
				private long seq;
				@Column(name="_uid")
				private int uid;
			}
			
			@Data
			@Entity
			public class BoardView {
				@EmbeddedId
				private BoardViewId id;
			}


		4) @GeneratedValue : 자동증감 번호 컬럼
			
			public class Member {
				@Id @GeneratedValue(strategy = GenerationType.AUTO)
				private long seq;
				...
			}
			
			@GeneratedValue(strategy = GenerationType.AUTO)   
			@GeneratedValue(strategy = GenerationType.IDENTITY) // 예 : mysql
			@GeneratedValue(strategy = GenerationType.SEQUENCE)  // 예:오라클 
			
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
				
				@CreationTimestamp   // 비표준 : hibernate 꺼
				private LocalDateTime createdAt;
				
				@UpdateTimestamp     // 비표준 : hibernate 꺼
				private LocalDateTime modifiedAt;
			}

		-------------------------------------------------------------------------
		@Enumerated(EnumType.STRING)   // 중요  EnumType.STRING : 디폴트값 쓰면 안됨(디폴트 숫자 매우 위험)
		private Authority authority;
		-------------------------------------------------------------------------
		@Transient  // DB 테이블에는 없는 필드 (가공해서 사용할 임시 필드..)
		private String introduction;
		-------------------------------------------------------------------------
		@Temporal(TemporalType.DATE) //날짜만
		private Date date;		     // 엣날자바에서 사용하는 자료형
		

		@Column 
		
			-------------------------------------------------------------------------
			@Column(name="name")
			private String userName;		
			-------------------------------------------------------------------------
			
			@Column(unique = true)
			private String email;
			-------------------------------------------------------------------------

			public class Member extends BaseEntity {
				@Id @GeneratedValue(strategy = GenerationType.AUTO)
				private long seq;

				@Column(length = 60, nullable = false, unique = true)
				private String email;

				@Column(length = 65, nullable = false)
				private String password;

				@Column(length = 40, nullable = false)
				private String userName;
				//@Lob
				@Transient
				private String introduction;
				
				@Column(length = 10)
				@Enumerated(EnumType.STRING)
				private Authority authority;

			}
			
		@columnDefinition 쓰지 않는게 좋다 .. DB마다 달라서..

	JPQL  -> Java Persistence Query Language
		  -> 모든 DB 플랫폼에 호환
		  -> 조회 결과가 영속 상태(변화 감지)
		  
		: 엔티티 기준의.. 

	Repository 설계하기

		- DAO 클래스 대체 

		1. JpaRepository 인터페이스  : 상속 
		
			(CrudRepository의 하위 인터페이스)
			
			public interface MemberRepository extends JpaRepository<Member, Long> {
			}
			

		2. JpaRepository에서 지원하는 메서드 
		
			- S save(S entity) : persist(...) : 영속성 상태로 추가 
			
			- S saveAndFlush(S entity) : persist() + flush()
			
			- List<S> saveAll(Collection<S> ...) 
			
			- List<S> saveAllAndFlush(....)

			- void delete(T entity) : remove(...)
			
			- count()
			
			- Iterable findAll() 
			
			- S findOne(..)
			  S findById(기본키)
			  
			  Optional<S> findById(기본키)  -> 낱개로 조회할때 널처리 위한 optional ****
			  
			  ... get... 
			  
			  참고) find로 시작하는 메서드 : 영속 상태 
					get로 시작하는 메서드 : 비영속 상태 - 상태변화 감지 X -> deprecated -> 대신 detach 하면됨
					  
			- flush() : 상태 변화 감지 -> DB 에 반영
			
			
			- JpaRepository 쓸때 test에서 @Transactional 안써도 됨


				@SpringBootTest
				@TestPropertySource(properties = "spring.profiles.active=test")
				public class Ex05 {

					@Autowired
					private MemberRepository memberRepository;

					@BeforeEach
					void init() {

						List<Member> members = IntStream.rangeClosed(1, 10)
								.mapToObj(i -> Member.builder()
								.email("user" + i + "@test.com")
								.password("123456")
								.userName("user" + i )
								.authority(Authority.USER)
								.build()).toList();
						memberRepository.saveAllAndFlush(members);
						/*
						for (int i = 1; i <= 10; i++) {
							Member member = Member.builder()
									.email("user" + i + "@test.org")
									.password("12345678")
									.userName("사용자" + i)
									.authority(Authority.USER)
									.build();

							memberRepository.save(member);
						}

						memberRepository.flush();
						 */
						//member.setUserName("(수정)사용자01");

						//memberRepository.save(member);
						//memberRepository.flush();

					}

					@Test
					void test1() {
						Member member = memberRepository.findById(1L).orElse(null);
						System.out.println(member);

						member.setUserName("(수정)사용자01");

						memberRepository.save(member);

						Member member2 = memberRepository.findById(1L).orElse(null);
						System.out.println(member2);

						Member member3 = memberRepository.findById(1L).orElse(null);
						System.out.println(member3);

						if(member3 != null ) {
							memberRepository.delete(member3);
							memberRepository.flush();
						}
						List<Member> members = memberRepository.findAll();
						members.forEach(System.out::println);

					}

					@Test
					void test2() {
						List<Member> members = memberRepository.findAll();
						members.forEach(System.out::println);
					}
				}

				--------------------------------------------------------------------------------------
				public interface MemberRepository extends JpaRepository<Member, Long> {
					Member findByEmail(String email);

					Page<Member> findByEmailContaining(String keyword, Pageable pageable);
					List<Member> findByEmailContainingAndUserNameContainingOrderByCreatedAtDesc(String key1, String key2);
				}



				@SpringBootTest
				@TestPropertySource(properties = "spring.profiles.active=test")
				public class Ex06 {

					@Autowired
					private MemberRepository memberRepository;

					@BeforeEach
					void init() {

						List<Member> members = IntStream.rangeClosed(1, 10)
								.mapToObj(i -> Member.builder()
										.email("user" + i + "@test.com")
										.password("123456")
										.userName("user" + i)
										.authority(Authority.USER)
										.build()).toList();

						memberRepository.saveAllAndFlush(members);
					}

					@Test
					void test1() {
						Member member = memberRepository.findByEmail("user2@test.com");
						System.out.println(member);

					}

					@Test
					void test2() {
						List<Member> members = memberRepository.findByEmailContainingAndUserNameContainingOrderByCreatedAtDesc("ser", "용");
						members.forEach(System.out::println);
					}
				}
	쿼리 메서드
	
		Pageable - 페이징 기초정보
		
		slice - 한 페이지의 데이터

			------------------------------------------------------
			public interface MemberRepository extends JpaRepository<Member, Long> {
				Page<Member> findByEmailContaining(String keyword, Pageable pageable);
			}

			@SpringBootTest
			@TestPropertySource(properties = "spring.profiles.active=test")
			public class Ex06 {

				@Autowired
				private MemberRepository memberRepository;

				@BeforeEach
				void init() {

					List<Member> members = IntStream.rangeClosed(1, 10)
							.mapToObj(i -> Member.builder()
									.email("user" + i + "@test.com")
									.password("123456")
									.userName("user" + i)
									.authority(Authority.USER)
									.build()).toList();

					memberRepository.saveAllAndFlush(members);
				}

				@Test
				void test3() {
					// 2페이지 가져오려고 세팅 : 페이지당 데이터 3개 
					Pageable pageable = PageRequest.of(1, 3); //  page 번호 0 부터, 페이지 당 데이터 갯수:3

					Page<Member> data = memberRepository.findByEmailContaining("ser", pageable);
					long total = data.getTotalElements();
					System.out.println("data.getTotalElements() : " + total);

					List<Member> items = data.getContent(); // *** 현재 페이지의 데이터 
					items.forEach(System.out::println);
				}
			}
			
			------------------------------------------------------
			
		@Query 애노테이션

		JPQL(Java Persistence Query Language)
		- 엔티티 기준의  SQL, 조회 결과 
		상태

		Querydsl : 비표준
		
			http://querydsl.com/static/querydsl/5.0.0/reference/html_single/		

			------------------------------------------------------
			public interface MemberRepository extends JpaRepository<Member, Long> {

				@Query("select m from Member m where m.email like :k1 and m.userName like :k2 order by m.createdAt desc") //entity의 이름 기준으로  Member, userName...
				List<Member> getMembers(@Param("k1") String key1, @Param("k2")String key2);
			}

			@Test
			void test4() {
				List<Member> members = memberRepository.getMembers("%ser%", "%용자%");
				members.forEach(System.out::println);
			}
			------------------------------------------------------


		의존성 추가 querydsl 검색 - Querydsl JPA Support, Querydsl APT Support 
		
			implementation 'com.querydsl:querydsl-jpa:5.1.0'
			implementation 'com.querydsl:querydsl-apt:5.1.0' 
			   -> annotationProcessor 'com.querydsl:querydsl-apt:5.1.0'
			   
			=>
			
			implementation 'com.querydsl:querydsl-jpa:5.1.0:jakarta'
			annotationProcessor 'com.querydsl:querydsl-apt:5.1.0:jakarta'

			-- build.gradle 에 추가 -----
			
			def querydslDir = layout.buildDirectory.dir("generated/querydsl").get().asFile

			sourceSets {
				main.java.srcDirs += [querydslDir]
			}

			tasks.withType(JavaCompile) {
				options.getGeneratedSourceOutputDirectory().set(file(querydslDir))
			}

			clean.doLast {
				file(querydslDir).deleteDir()
			}
			---------------------------------------------------

		리포지토리 정의
		
		QueryDslPredicateExecutor 를 함께 상속 -> 


			public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {

			}

		main/generated/.../entities/QMember 생김
		
		Q클래스  
			eq : =
			lt : <
			loe : <=
			gt : >
			goe : >=
		
		BooleanBuilder : 여러 검색 조건을 만드는 경우
		
		  - and(Predicate...)
		  - or(Predicate..)
		  - not(Predicate..)
		  

			@SpringBootTest
			@TestPropertySource(properties = "spring.profiles.active=test")
			public class Ex07 {
				@Autowired
				private MemberRepository memberRepository;

				@BeforeEach
				void init() {

					List<Member> members = IntStream.rangeClosed(1, 10)
						.mapToObj(i -> Member.builder()
						.email("user" + i + "@test.com")
						.password("123456")
						.userName("사용자" + i)
						.authority(i % 2 == 0 ? Authority.USER : Authority.ADMIN)
						.build()).toList();

					memberRepository.saveAllAndFlush(members);
				}

				@Test
				void test1() {
					QMember member = QMember.member;
					BooleanExpression c1 = member.userName.contains("용");
					List<Member> members = (List<Member>)memberRepository.findAll(c1);
					members.forEach(System.out::println);
				}
				@Test
				void test2() {
					String key = "용";
					QMember member = QMember.member;

					BooleanBuilder andBuilder = new BooleanBuilder();
					BooleanBuilder orBuilder = new BooleanBuilder();

					orBuilder.or(member.email.contains(key))
							.or(member.userName.contains(key));

					andBuilder.and(orBuilder);
					List<Member> members = (List<Member>)memberRepository.findAll(orBuilder);
					members.forEach(System.out::println);

				}

				@Test
				void test3() {
					QMember member = QMember.member;
					BooleanExpression c1 = member.email.concat(member.userName).contains("용");
					System.out.println("c1 : " + c1);
					List<Member> members = (List<Member>)memberRepository.findAll(c1);
					members.forEach(System.out::println);
				}
			}
		
	연관 관계 매핑 - jakarta.persistence
	
		1. 일대일(1:1) : @OneToOne
		
		2. 일대다(1:N) : @OneToMany
		
			Member - BoardData
			- 연관관계의 주인 설정 - 관계의 주인의 외래키쪽
			- mappedBy 
			
			- lombok 의 toString() 과 함께 사용되면 순환참조 발생 가능성
			  :  toString() 할때 getter 매서드를 사용하기 때문에
			  BoardData -> toString() -> getMember() -> toString() ->List<BoardData> -> toString()
		
			=> 해결방법 ?
			
				: toString을 멤버 변수를 직접 출력하는 것으로 직접 정의
				
				: @ToString.Exclude -> ToString에 배제
				: @ToString.Include -> ToString에 포함

		BOARD_DATA : MEMBER 
		 - 연관관계의 주인은 BOARD_DATA table
		 
		@JoinColumn : 조인되는 컬럼의 이름을 변경할 때


		3. 다대일(N:1) : @ManyToOne
		
		
		4. 다대다(N:M) : @ManyToMany
		  - 중간 테이블 하나 생성
		  - BoardData - HashTag
		  
			게시글1 태그1 태그4
			게시글2 태그1 태그2
			게시글3 태그3 태그4

			태그1 - 게시글1, 게시글2 
		  
		
			@Data
			@Builder
			@Entity
			@NoArgsConstructor @AllArgsConstructor
			public class BoardData extends BaseEntity {
				@Id @GeneratedValue
				private Long seq;

				@ManyToOne // -> member_seq : 엔티티명_기본키 (memberSeq)
				@JoinColumn(name="mSeq") // m_seq 로 이름 바꾸기
				private Member member;

				@Column(nullable = false)
				private String subject;

				@Lob
				private String content;

			}

			@Builder
			@Entity
			@Data
			@NoArgsConstructor
			@AllArgsConstructor
			public class Member extends BaseEntity {
				@Id @GeneratedValue(strategy = GenerationType.AUTO)
				private long seq;

				@Column(length = 60, nullable = false, unique = true)
				private String email;

				@Column(length = 65, nullable = false)
				private String password;

				@Column(length = 40, nullable = false)
				private String userName;
				//@Lob
				@Transient
				private String introduction;

				@Column(length = 10)
				@Enumerated(EnumType.STRING)
				private Authority authority;


				@ToString.Exclude // toString 추가를 제외 시킴
				@OneToMany(mappedBy = "member") // BoardData 엔티티의 @ManyToOne 속성 지정해야함
				private List<BoardData> items;

				@OneToOne
				@JoinColumn(name="profileSeq")
				private MemberProfile profile;

			}

			@Data
			@Builder
			@Entity
			@NoArgsConstructor @AllArgsConstructor
			public class MemberProfile {
				@Id @GeneratedValue
				private long seq;

				private String profileImage;
				private String status;

				@OneToOne(mappedBy = "profile")
				@ToString.Exclude
				private Member member;

			}		


			@SpringBootTest
			@ActiveProfiles("test")
			@Transactional //관계매핑 등 연관테이터 가져올때는 원 엔티티가 영속성 상태를 유지할 수 있도록 Transactional
			public class Ex09 {
				@Autowired
				private MemberRepository memberRepository;
				@Autowired
				private BoardDataRepository boardDataRepository;

				@PersistenceContext  // @Autowired 포함된 기능
				private EntityManager em;

				@BeforeEach
				void init() {
					Member member = Member.builder()
						.email("user01@test.com")
						.password("12345")
						.userName("사용자01")
						.authority(Authority.USER)
						.build();
					memberRepository.saveAndFlush(member);

					List<BoardData> items = IntStream.rangeClosed(1, 10)
						.mapToObj(i-> BoardData.builder()
							.subject("제목" + i)
							.content("내용" + i)
							.member(member)
							.build()).toList();

					boardDataRepository.saveAllAndFlush(items);
					em.clear() ; // 1차 캐시 방지 하기 : 테스트 확실히 하기 위해
				}
				@Test
				void test1() {
					BoardData boardData = boardDataRepository.findById(1L).orElse(null);

					Member member = boardData.getMember();
					System.out.println(member);
				}

				@Test
				void test2() {
					Member member = memberRepository.findById(1L).orElse(null);
					List<BoardData> items = member.getItems();
					items.forEach(System.out::println);
				}

			}

			@SpringBootTest
			@ActiveProfiles("test")
			@Transactional
			public class Ex10 {
				@Autowired
				private MemberRepository memberRepository;
				@Autowired
				private MemberProfileRepository profileRepository;

				@PersistenceContext
				private EntityManager em;

				@BeforeEach
				void init() {
					MemberProfile profile = MemberProfile.builder()
							.profileImage("이미지")
							.status("상태")
							.build();
					profileRepository.saveAndFlush(profile);

					Member member = Member.builder()
							.email("user01@test.com")
							.password("12345")
							.userName("사용자01")
							.authority(Authority.USER)
							.profile(profile)
							.build();
					memberRepository.saveAndFlush(member);

					em.clear();

				}

				@Test
				void test1() {
					Member member = memberRepository.findById(1L).orElse(null);
					MemberProfile profile = member.getProfile();
					System.out.println(profile);
				}
				@Test
				void test2() {
					MemberProfile profile = profileRepository.findById(1L).orElse(null);
					Member member = profile.getMember();

					System.out.println(member);
				}
			}

			-------@ManyToMany----------------------------------------
			@Data
			@Builder
			@Entity
			@NoArgsConstructor @AllArgsConstructor
			public class BoardData extends BaseEntity {
				@Id @GeneratedValue
				private Long seq;

				@ManyToOne // -> member_seq : 엔티티명_기본키 (memberSeq)
				@JoinColumn(name="mSeq") // m_seq 로 이름 바꾸기
				private Member member;

				@Column(nullable = false)
				private String subject;

				@Lob
				private String content;

				@ManyToMany
				private List<HashTag> tags;
			}

			@Data
			@Entity
			@Builder
			@NoArgsConstructor
			@AllArgsConstructor
			public class HashTag {
				@Id
				private String tag;

				@ManyToMany(mappedBy = "tags")
				@ToString.Exclude
				private List<BoardData> items;
			}

			@SpringBootTest
			@ActiveProfiles("test")
			@Transactional
			public class Ex11 {

				@Autowired
				private BoardDataRepository boardDataRepository;

				@Autowired
				private HashTagRepository hashTagRepository;

				@PersistenceContext
				private EntityManager em;

				@BeforeEach
				void init() {
					List<HashTag> tags = IntStream.rangeClosed(1, 5)
							.mapToObj(i -> HashTag.builder()
							.tag("태그" + i)
							.build()).toList();

					hashTagRepository.saveAllAndFlush(tags);

					List<BoardData> items = IntStream.rangeClosed(1, 5)
						.mapToObj(i -> BoardData.builder()
							.subject("제목" + i)
							.content("내용" + i)
							.tags(tags)
							.build()).toList();
					boardDataRepository.saveAllAndFlush(items);

					em.clear();
				}

				@Test
				void test1() {
					BoardData item = boardDataRepository.findById(1L).orElse(null);

					List<HashTag> tags = item.getTags();
					tags.forEach(System.out::println);
				}

				@Test
				void test2() {

					HashTag tag = hashTagRepository.findById("태그2").orElse(null);
					List<BoardData> items = tag.getItems();
					items.forEach(System.out::println);

				}
			}
			-------@ManyToMany----------------------------------------
			
			-> ManyToMany 는 중간 테이블 만들어짐 ( 로그 보면 ..)
				Hibernate: 
					create table hash_tag (
						tag varchar(255) not null,
						primary key (tag)
					)			
			----------------------------------------------------------


	지연로딩 
	
		1. FetchType.EAGER : 즉시 로딩 - 처음부터 join
		2. FetchType.LAZY : 지연로딩, 처음에는 현재 엔티니만 조회, 다른 매핑된 엔티티는 사용할때만 2차 실행

		- 글로벌 전략으로 지연로딩, 필요시메만 즉시 로딩 전략 사용해라
		
		- ManyToOne : 기본값 즉시로딩 FetchType.EAGER : 처음부터 조인함
		- OneToMany : 기본값 지연로딩 FetchType.LAZY : 필요할때만 조인함
		
	    - 지연로딩때는 @Transactional 과 함께 많이 사용함
		
		Fetch 조인 -> 필요한 엔티티만 즉시 로딩 전략을 이용
		
		1) JPQL 직접 정의 : @Query 에노테이션
		2) @EntityGraph :  쿼리매서드에만 사용가능함
		
			 @EntityGraph(attributePaths = "member")
			 
			 
			 
				 public interface BoardDataRepository extends JpaRepository<BoardData, Long>, QuerydslPredicateExecutor<BoardData> {

					@Query("select b from BoardData b left join fetch b.member")
					List<BoardData> getAllList();

					@EntityGraph(attributePaths = "member")
					List<BoardData> findBySubjectContaining(String key);

				}

				@Builder
				@Entity
				@Data
				@NoArgsConstructor
				@AllArgsConstructor
				public class Member extends BaseEntity {
					@Id @GeneratedValue(strategy = GenerationType.AUTO)
					private long seq;

					@Column(length = 60, nullable = false, unique = true)
					private String email;

					@Column(length = 65, nullable = false)
					private String password;

					@Column(length = 40, nullable = false)
					private String userName;
					//@Lob
					@Transient
					private String introduction;

					@Column(length = 10)
					@Enumerated(EnumType.STRING)
					private Authority authority;

					@ToString.Exclude // toString 추가를 제외 시킴
					@OneToMany(mappedBy = "member") // BoardData 엔티티의 @ManyToOne 속성 지정해야함
					private List<BoardData> items;

				}
				@Data
				@Builder
				@Entity
				@NoArgsConstructor @AllArgsConstructor
				public class BoardData extends BaseEntity {
					@Id @GeneratedValue
					private Long seq;

					@ManyToOne(fetch=FetchType.LAZY) // -> member_seq : 엔티티명_기본키 (memberSeq) 지연로딩
					@JoinColumn(name="mSeq") // m_seq 로 이름 바꾸기
					private Member member;

					@Column(nullable = false)
					private String subject;

					@Lob
					private String content;

					@ManyToMany
					private List<HashTag> tags;
				}


				@SpringBootTest
				@ActiveProfiles("test")
				@Transactional
				public class Ex12 {

					@Autowired
					private MemberRepository memberRepository;
					@Autowired
					private BoardDataRepository boardDataRepository;

					@Autowired
					private JPAQueryFactory queryFactory;

					@PersistenceContext  // @Autowired 포함된 기능
					private EntityManager em;


					@BeforeEach
					void init() {
						Member member = Member.builder()
								.email("user01@test.com")
								.password("12345")
								.userName("사용자01")
								.authority(Authority.USER)
								.build();
						memberRepository.saveAndFlush(member);

						List<BoardData> items = IntStream.rangeClosed(1, 10)
								.mapToObj(i -> BoardData.builder()
										.subject("제목" + i)
										.content("내용" + i)
										.member(member)
										.build()).toList();

						boardDataRepository.saveAllAndFlush(items);
						em.clear();

					}


					@Test
					void test2() {
						List<BoardData> items = boardDataRepository.getAllList();
					}

					@Test
					void test3() {
						List<BoardData> items = boardDataRepository.findBySubjectContaining("제목");
					}
				}
			 
		3) QueryDsl의 fetchJoin() 메서드 사용
		
			JPAQueryFactory - 생성자 매개변수: EntityManager
			JPAQuery
			
				@Configuration
				public class DBConfig {
					@PersistenceContext
					private EntityManager em;

					@Lazy
					@Bean
					public JPAQueryFactory jpaQueryFactory() {
						return new JPAQueryFactory(em);
					}
				}		



				@Test
				void test5() {
					QBoardData boardData = QBoardData.boardData;  // Tuple : querydsl 
					JPAQuery<Tuple> query = queryFactory.select(boardData.subject, boardData.content).from(boardData);
						// select : 영속 상태 아님 selectFrom : 영속상태 
						
					List<Tuple> items = query.fetch();
					for (Tuple item : items) {
						String subject = item.get(boardData.subject);
						String content = item.get(boardData.content);
						System.out.println("subject: " + subject + ", content: " + content);
					}
				}
				

				@Test
				void test6() {
					QBoardData boardData = QBoardData.boardData;
					JPAQuery<Long> query = queryFactory.select(boardData.seq.sum()).from(boardData);
					long sum = query.fetchOne(); // 결과 단 하나 row만 있을때
					System.out.println("sum: " + sum);
				}

				@Test
				void test7_() {
					QBoardData boardData = QBoardData.boardData;
					PathBuilder<BoardData> pathBuilder = new PathBuilder<>(BoardData.class, "boardData");

					JPAQuery<BoardData> query = queryFactory.selectFrom(boardData) // 변화감지
						.offset(3) // 조회 시작 레코드 위치 : 3번 행부터 조회시작 --> 페이징 대신..
						.limit(3)  //3개 레코드로 한정
						.orderBy(
							new OrderSpecifier(Order.DESC, pathBuilder.get("createdAt")),
							new OrderSpecifier(Order.ASC, pathBuilder.get("subject"))
						);
					List<BoardData> items = query.fetch();

					//items.forEach(System.out::println);
				}


				(참고) 			
				@BatchSize(size=10)

					적용 전

					SELECT .. from BoardData
					SELECT .. from BoardData where seq = 1L;
					SELECT .. from BoardData where seq = 2L;
					SELECT .. from BoardData where seq = 3L;
					...

					적용 후
					SELECT .. from BoardData
					SELECT .. from BoardData where seq in (1L, 2L, 3L);

					spring.jpa.properties.hibernate.default_batch_fetch_size: 100


				(참고)  스프링 객체들 싱글톤 :  프로그램 시작시 너무 느려질 수 있으므로 
				 @Lazy -> 객체 사용 시점에 객체 생성하기


영속성 전이 - @OneToMany 에 부여

  - 부모 엔티티의 영속성 변화 상태를 자식 엔티티에 전달

	1. CASCADE 종류	
		type
		1) PERSIST
		2) MERGE  : 부모 엔티티가 영속화 -> 자식 엔티티도 ..
		3) REMOVE : 부모 엔티티가 삭제 되기 전 자식 엔티티들 부터 삭제 
		4) REFRESH
		5) DETACH
		6) ALL


			@Builder
			@Entity
			@Data
			@NoArgsConstructor
			@AllArgsConstructor
			public class Member extends BaseEntity {
				@Id @GeneratedValue(strategy = GenerationType.AUTO)
				private long seq;

				@ToString.Exclude 
				@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE) 
				private List<BoardData> items;
			}

			@Test
			void test1() {
				Member member = memberRepository.findById(1L).orElse(null);

				memberRepository.delete(member);
				memberRepository.flush();
			}
			----------------------------------------------
			public class Member extends BaseEntity {
				@Id @GeneratedValue(strategy = GenerationType.AUTO)
				private long seq;

				@ToString.Exclude 
			@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, CascadeType.PERSIST},orphanRemoval = true)) 
				private List<BoardData> items;
			}
			
			@Test
			void test2() {
				Member member = memberRepository.findById(1L).orElse(null);
				List<BoardData> items = member.getItems();

				items.remove(0);
				items.remove(1);

				memberRepository.flush();

			}			

	2. 고아 객체 제거하기
	
	- @OneToMany 애노테이션에 CascadeType.PERSIST, orphanRemoval=true 옵션을 추가

			public class Member extends BaseEntity {
				@Id @GeneratedValue(strategy = GenerationType.AUTO)
				private long seq;

				@ToString.Exclude 
			@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)) 
				private List<BoardData> items;
			}
			
			@Test
			void test2() {
				Member member = memberRepository.findById(1L).orElse(null);
				List<BoardData> items = member.getItems();

				items.remove(0);
				items.remove(1);

				memberRepository.flush();

			}			


Auditing을 이용한 엔티티 공통 속성화

	1. @MappedSuperclass
	2. AuditorAware 인터페이스 
	3. @EntityListeners
	4. @EnableJpaAuditing

JPQL

	- 설정된 주기별로 실행될 함수를 설정
	
	@Scheduled
	
	1) fixedDelay : 작업 완료 후 고정시간 지연 
	2) fixedRate : 고정시간 주기로 실행
	3) initialDelay : 작업 시작 전 간격 지연
	4) cron : 상세한 실행주기 
	5) @EnableScheduling : 스케줄링 설정 활성화
	
		@EnableJpaAuditing
		@EnableScheduling    // ******
		@Configuration
		public class MvcConfig implements WebMvcConfigurer {
		}
		
		@Slf4j
		@Service
		public class MemberStatisticService {

			@Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
			public void makeData() {
				log.info("실행 ---!!!");
			}
		}
	
SQL> create user BOARD_PROJECT identified by oracle quota unlimited on users;
SQL> grant connect, resource to board_project;





			https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.3.2&packaging=jar&jvmVersion=17&groupId=org.choongang&artifactId=project&name=project&description=Spring%20Board%20Project&packageName=org.choongang&dependencies=devtools,lombok,web,thymeleaf,security,data-jpa,h2,oracle,validation

			압축파일 해제 -> project

			D:\P-4\project>git init
			Initialized empty Git repository in D:/P-4/project/.git/

			D:\P-4\project>git remote add origin https://github.com/hjchoirr/SPRING_BOARD.git

			D:\P-4\project>

			D:\P-4\project>git add .

			D:\P-4\project>git commit -m "초기"

			D:\P-4\project>git push ...


				
			inteliJ settings -> editor -> general -> auto import -> optimize import on fly 체크
			빌드 -> compiler -> Build project automatically

			gitignore  -> application-dev.*

			...properties -> ...yml

				testRuntimeOnly 'com.h2database:h2' // runtimeOnly -> testRuntimeOnly


			query-dsl 추가

			 - https://mvnrepository.com/ -> querydsl-> 
			 
				Querydsl JPA Support » 5.1.0 
				Querydsl APT Support » 5.1.0
			 

				implementation 'com.querydsl:querydsl-jpa:5.1.0'
				implementation 'com.querydsl:querydsl-apt:5.1.0'
				
				->
				
				implementation 'com.querydsl:querydsl-jpa:5.1.0:jakarta'  //  :jakarta
				annotationProcessor 'com.querydsl:querydsl-apt:5.1.0:jakarta' // implementation 을 변경, :jakarta 

			그냥 에디팅 추가 
			annotationProcessor 'jakarta.annotation:jakarta.annotation-api'
			annotationProcessor 'jakarta.persistence:jakarta.persistence-api'
			
			
			타임리프 레이아웃 추가
			implementation 'nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.3.0'

			모델 매퍼 modelMapper 추가
			implementation 'org.modelmapper:modelmapper:3.2.1'


	스프링 시큐리티 

	1. 의존성 설치
		스프링 부트 initializr 에서 시큐리티 선택하면 이렇게 추가됨
		
		implementation 'org.springframework.boot:spring-boot-starter-security'
		implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'
		testImplementation 'org.springframework.security:spring-security-test'

7/26 2시부터 BOARD_SPRING 설정 부터 강의 시작

	properties 중 일부의 설정을 자바 클래스로 관리할 수 있다

	방법1)
	
		@Configuration
		public class FileConfig implements WebMvcConfigurer {
			
			@Value("${file.upload.path}")
			private String path;

			@Value("${file.upload.url}")
			private String url;

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {

			}
		}	

		(프로퍼티파일 application.yml 중)
		file:
		  upload:
			path: D:/uploads
			url: /upload/

	방법2)

		@Data
		@ConfigurationProperties(prefix="file.upload")
		public class FileProperties {
			private String path;
			private String url;
		}

		@Configuration
		@RequiredArgsConstructor
		@EnableConfigurationProperties(FileProperties.class)
		public class FileConfig implements WebMvcConfigurer {

			private final FileProperties fileProperties;

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler(fileProperties.getUrl() + "**")
					.addResourceLocations("file:" + fileProperties.getPath());
			}
		}

7/29 AM9  타임리프 레이아웃, ,,

	templates
	
	- admin
	- front - PC view 
	- mobile view
	
	css/js
	 - 모든 뷰(관리자,PC,mobile)에 공통 
	 - 각 뷰마다의 공통
	 
	 
	Pattern.compile(..)
	Matcher
	find() -> 포함여부
	
	간단한 정규표현식 체크, String 클래스 -> matrches(..)
	 String.class 의 boolean matches(String regex)
	 
	 matches : 처음 위치부터 일치여부 체크 
	 
	 "[0-9]+"
	 \d+ 
	
	.*[0-9].* 
	
	replaceAll(String regex, String replacement)
	

	2. 스프링 시큐티리 설정 
	3. 회원가입 구현 
		1) UserDetails 인터페이스 : DTO
			
		2) UserDetailsService 인터페이스 : Service
		
	4. 시큐리티를 이용한 회원 인증(로그인) 구현 
	5. 로그인 정보 가져오기
	1) Principal 요청메서드에 주입  : getName() : 아이디  : 요청 메서드의 주입
	2) SecurityContextHolder를 통해서 가져오기
	3) @AuthenticationPrincipal  : UserDetails 구현 객체 주입, 요청 메서드의 주입시 밖에 사용 가능 

	4) Authentication
		Object getPrincipal(...) : UserDetails의 구현 객체 
		boolean isAuthenticated() : 인증 여부
		
	/error 템플릿 경로 : 응답 코드.html

	6. thymeleaf-extras-springsecurity6
		1) xmlns:sec="http://www.thymeleaf.org/extras/spring-security"
			
		2) sec:authorize="hasAnyAuthority(...)", sec:authorize="hasAuthority(...)"
		3) sec:authorize="isAuthenticated()" : 로그인 상태 
		4) sec:authorize="isAnonymous()" : 미로그인 상태 
		
		5) csrf 토큰 설정하기 
			- ${_csrf.token}
			- ${_csrf.headerName}
		
	7. 페이지 권한 설정하기 
		- AuthenticationEntryPoint 
		
	8.  Spring Data JPA Auditing + Spring Security
	- 로그인 사용자가 자동 DB 추가 
	1) AuditorAware 인터페이스




POST 요청시 CSRF 토큰 검증 : 검증 실패시 403
- 자바 스크립트 ajax 형태로 POST 데이터를 전송할시 CSRF 토큰 검증 


9. @EnableMethodSecurity

1) @PreAuthorize: 메서드가 실행되기 전에 인증을 거친다.
2) @PostAuthorize: 메서드가 실행되고 나서 응답을 보내기 전에 인증을 거친다.

3) 사용할수 있는 표현식 
- hasRole([role]) : 현재 사용자의 권한이 파라미터의 권한과 동일한 경우 true
- hasAnyRole([role1,role2]) : 현재 사용자의 권한디 파라미터의 권한 중 일치하는 것이 있는 경우 true
- principal : 사용자를 증명하는 주요객체(User)를 직접 접근할 수 있다.
- authentication : SecurityContext에 있는 authentication 객체에 접근 할 수 있다.
- permitAll : 모든 접근 허용
- denyAll : 모든 접근 비허용
- isAnonymous() : 현재 사용자가 익명(비로그인)인 상태인 경우 true
- isRememberMe() : 현재 사용자가 RememberMe 사용자라면 true
- isAuthenticated() : 현재 사용자가 익명이 아니라면 (로그인 상태라면) true
- isFullyAuthenticated() : 현재 사용자가 익명이거나 RememberMe 사용자가 아니라면 true