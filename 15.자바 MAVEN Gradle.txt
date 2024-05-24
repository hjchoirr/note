maven
- 빌드, 의존성(필요한 라이브러리 jar(Java Archive))관리, 배포, 테스트 툴 

1. 설치
 - apache maven 검색
	Binary zip archive 
	apache-maven-3.9.6-bin.zip 다운로드, 압축해제, 위치이동
	환경변수 - 시스템변수 path에 추가 : D:\hjchoi\apache-maven-3.9.6\bin
	확인 - 
		C:\Users\admin>mvn -v
		Apache Maven 3.9.6 (bc0240f3c744dd6b6ec2920b3cd08dcc295161ae)
		Maven home: D:\hjchoi\apache-maven-3.9.6
		Java version: 17.0.10, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-17
		Default locale: ko_KR, platform encoding: MS949
		OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"	
	D:\
	cd \hjchoi\7.SQL응용
	D:\hjchoi\7.SQL응용>mvn archetype:generate           <--- 프로젝트 생성
	
	Define value for property 'groupId': org.choongang
	Define value for property 'artifactId': exam01
	Define value for property 'version' 1.0-SNAPSHOT: : 그냥앤터
	Define value for property 'package' org.choongang: : 그냥앤터
	Confirm properties configuration: ...... Y :  Y
	
2. 사용 
1) maven 프로젝트 생성 
mvn archetype:generate
		groupId : 소속된 그룹(도메인 방식)
						예) org.project
		artifactId : 프로젝트 구분 명칭 

		프로젝트 디렉토리 구조 
			클래스패스 : 클래스 파일을 인식할 수 있는 경로 
			 
			src/main/java : 작성하는 자바코드(.java)
			src/main/resources : 정적 자원 
											- 설정 파일(xml, properties)
											- css, js, 이미지
											
			src/test/java : 테스트 자바 코드(.java)
			src/test/resources : 테스트시 필요 정적 자원
			
		pom.xml : maven 설정 파일  ( pom : Project Object Manager ) (참고) node : package.json 처럼
			<properties>
				<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding> : 소스 인코딩 ***
				<maven.compiler.source>17</maven.compiler.source> : 소스 컴파일 자바 버전 ***
				<maven.compiler.target>17</maven.compiler.target> : 배포 파일(jar) 생성시 자바 버전 ***
			  </properties>
			  
			   - 의존성 설정
			  <dependencies>
			  
			  </dependencies>	

					  <dependencies>
						  <dependency>
						  <groupId>junit</groupId>
						  <artifactId>junit</artifactId>
						  <version>4.11</version>
						  <scope>test</scope>  <!--  -->
						</dependency>
					  </dependencies>

				 사용자명/.m2  -> 메이븐 레포지토리 (공유)
				 
				 scope
					- compile (기본값) : 빌드시 포함, 배포시 포함 
					- runtime : 빌드시 포함 X, 실행할때는 필요한 라이브러리 ( 동적로딩 -> Class.forName(..) )
					- provided : 개발시에만 필요, 빌드 및 배포시에는 미포함 -> 플랫폼 내에서 제공되는 라이브러리
								예) servlet-api, servlet.jsp-api 
					- test  : 테스트 시에만 필요한 라이브러리
			
		mvn compile  : java 파일 -> class 컴파일 (target 폴더)
		
		mvn clean : 컴파일 소스 전체 지우기(target 폴더 삭제)
		
			예) 기존 컴파일 소스 삭제 후 다시 컴파일 
				mvn clean compile
				
		mvn test : 테스트 케이스를 실행(전체)
		
		mvn package : 배포 파일 생성(jar)
				compile -> test -> package(jar)
					-> 테스트 미통과시 배포 X
				
				mvn) 기존 컴파일 소스 삭제 후 배포 실행 
					mvn clean package
					
				(참고)	
					jar 파일 실행
						- java -jar 파일명.jar
					
		CTRL + SHIFT + B
		
버전 표기법 
	1       .18.       30
	(Major)  (Minor)   (Patch)

	Major - 기존 버전과 호환되지 않는 버전
	Minor - 호환성에는 문제가 없는 기능추가, 수정
	Patch - 오류, 버그, 수정 있는 경우


lombok.jar 설치방법

	maven central : 의존성 저장소에서 검색  또는 

	mvnrepository : maven central 보다 더 많이 참고하게 됨
		lombok 검색 
		Lombok 1.18.32 찾아서 maven dependencies 부분 카피 해서 
		내 pom.xml의 
		<dependencies>
		</dependencies>
		사이에 붙이기
		
	또는 

	인텔리J에서 파일 -> Settings -> plugins 에서 lombok install

JUnit jupiter 설치
	
	mvnrepository : Junit jupiter 검색

		JUnit Jupiter (Aggregator)  5.10.2 선택 (5.11.0-M2, M1 등 비안정 버전)
		 - dependency 카피해서 dependency 로 추가
		 
		Maven Surefire Plugin  검색
		 - dependency 카피해서 plugin 으로 추가
		 
			-------------------------
			<dependencies>
				<dependency>
					<groupId>org.junit.jupiter</groupId>
					<artifactId>junit-jupiter</artifactId>
					<version>5.10.2</version>
					<scope>test</scope>
				</dependency>
			</dependencies>
			<build>
				<plugins>
					<plugin>
						<groupId>org.apache.maven.plugins</groupId>
						<artifactId>maven-surefire-plugin</artifactId>
						<version>3.2.5</version>
					</plugin>

				</plugins>
			</build>
			-------------------------

		인텔리J에서 sync 버튼 누르기

lombok - 개발 편의도구

 - @Getter
 - @Setter
 - @ToString
 - @EqualsAndHashCode
 
 - @Data : 위의 4가지 모두 포함된 에너테이션
 
 - @NoArgsConstructor : 기본생성자
 - @AllArgsConstructor : 모든 멤버 변수로 초기화 생성자
 - @RequiredArgsConstructor : 필수 멤버 변수 생성 초기화 생성자 
	- 필수 멤버변수 : @NonNull 표기된 멤버변수
	- 멤버변수를 final(상수)로 정의한 경우, 값을 대입하지 않은 경우
	

	@NoArgsConstructor(access = AccessLevel.PUBLIC) //기본생성자
	@AllArgsConstructor
	
		package member;
		import lombok.EqualsAndHashCode;
		import lombok.Getter;
		import lombok.Setter;
		import lombok.ToString;

		import java.time.LocalDateTime;

		//@EqualsAndHashCode
		//@Getter @Setter @ToString
		@Data
		@NoArgsConstructor(access = AccessLevel.PUBLIC) //기본생성자, 접근자 
		@AllArgsConstructor
		public class Member {
			private String userId;
			private String userNm;
			private String email;
			private LocalDateTime regDt;
		}
		package exam01;
		import member.Member;
		import org.junit.jupiter.api.Test;
		import java.time.LocalDateTime;

		public class Ex01 {

			@Test
			void test1() {
				Member member = new Member();
				member.setUserId("user01");
				member.setUserNm("사용자01");
				member.setEmail("user01@test.org");
				member.setRegDt(LocalDateTime.now());

				System.out.println(member);
			}

			@Test
			void test2() {
				Member member1 = new Member();
				member1.setUserId("user01");
				member1.setUserNm("사용자01");
				member1.setEmail("user01@test.org");
				member1.setRegDt(LocalDateTime.now());

				Member member2 = new Member();
				member2.setUserId("user01");
				member2.setUserNm("사용자01");
				member2.setEmail("user01@test.org");
				member2.setRegDt(LocalDateTime.now());

				System.out.println(member1.equals(member2));
				System.out.println(member1.hashCode());
				System.out.println(member2.hashCode());
			}

		}


		-------------------------
		package member;
		import lombok.*;

		import java.time.LocalDateTime;
		@Data
		@RequiredArgsConstructor
		public class Member {
			@NonNull //필수 초기화 변수 지정하기
			private String userId;
			
			@NonNull  // 필수 초기화 변수 지정하기
			private String userNm;
			private String email;
			
			@ToString.Exclude   // ToString에 나오는거 배제하기
			private LocalDateTime regDt;
		}

		또는
		package member;
		import lombok.Data;
		import lombok.RequiredArgsConstructor;

		@Data
		@RequiredArgsConstructor
		public class Board {
			private int seq;
			private final String subject;   // @NonNull 또는 final 로 필수 초기화 변수 지정하기 @RequiredArgsConstructor
			private final String content;   // 필수 초기화 변수 지정하기
			private  String poster;
		}
		-------------------------------------
		package exam01;

		import member.Board;
		import member.Member;
		import org.junit.jupiter.api.Test;

		public class Ex02 {
			@Test
			void test1() {
				Member member = new Member("USER02", "사용자02");
				System.out.println(member);
			}
			@Test
			void test2() {
				Board board = new Board("제목", "내용");
				System.out.println(board);

			}
		}

@Builder 
	- 빌더패턴 
		: 객체를 내부에서 생성하는 패턴
		: 생성자 접근 제어자 private
	
		//----의사코드  User 클래스----
		package member;
		import java.time.LocalDateTime;

		public class User {
			private String userId;
			private String userNm;
			private String email;
			private LocalDateTime regDt;

			private User() {}

			public static Builder builder() {
				return new Builder();
			}

			@Override
			public String toString() {
				return "User{" +
						"userId='" + userId + '\'' +
						", userNm='" + userNm + '\'' +
						", email='" + email + '\'' +
						", regDt=" + regDt +
						'}';
			}

			public static class Builder {
				private User user = new User();
				public Builder userId(String userId) {
					user.userId = userId;
					return this;
				}
				public Builder userNm(String userNm) {
					user.userNm = userNm;
					return this;
				}
				public Builder userEmail(String email) {
					user.email = email;
					return this;
				}
				public Builder regDt(LocalDateTime regDt) {
					user.regDt = regDt;
					return this;
				}

				public User build() {
					return user;
				}
			}
		}
		//------@Builder 사용 User2 클래스------------------
		package member;
		import lombok.Builder;
		import lombok.ToString;
		import java.time.LocalDateTime;

		@Builder @ToString
		public class User2 {
			private String userId;
			private String userNm;
			private String email;
			
			@ToString.Exclude  // ToString 에서 배제될 항목 위에 선언
			private LocalDateTime regDt;
		}
		//-------User, User2 사용 테스트---------
		package exam01;
		import member.Board;
		import member.Member;
		import member.User;
		import member.User2;
		import org.junit.jupiter.api.Test;
		import java.time.LocalDateTime;

		public class Ex02 {
			@Test
			void test3() {
				User user = User.builder()
							.userId("USER01")
							.userNm("사용자01")
							.userEmail("aaa@aaa.com")
							.regDt(LocalDateTime.now())
							.build();
				System.out.println(user);

			}
			@Test
			void test4() {
				User2 user = User2.builder()
							.userId("USER01")
							.userNm("사용자01")
							.email("aaa@aaa.com")
							.regDt(LocalDateTime.now())
							.build();
				System.out.println(user);
			}
		}





그레이들(Gradle) 사용법

	설정파일 build.gradle -> 그루비, 코틀린 : DSL 특화언어
	DSL ( Domain Specific Language )
		- Domain : 전문가영역, 영역별로 설정하는 방식
		- 설정영역
			ext {
				// 람다식
			}

	1. 설치 
		gradle 사이트 -> releases -> 8.7 Download: binary-only  
		환경변수 path 추가 D:\hjchoi\gradle-8.7\bin
		
	2. 그레이들 명령어
		1) 버전 확인 
			> gradle --version
			
			> gradle tasks : 명령어 확인하기

		2) 프로젝트 생성
			- gradle init [--type 타입명]  ( 메이븐은 mvn archetype:generate 메이븐 프로젝트 생성)
			- build.gradle : 프로젝트에 필요한 의존성과 빌드처리 내용을 작성하는 파일
			- settings.gradle : 프로젝트에 대한 설정정보를 작성하는 파일
			
			exam03 폴더에 생성
			cd exam03 
			D:\hjchoi\7.SQL응용\exam03> gradle init
			type of build : Application
			language : java
			target java verion : 17
			project name : exam03
			application structure: Single Application
			build script DSL: Groovy
			test framework : JUnit Jupiter
			Generate build using new APIs and behavior : no
			
			
			
			

		3) java-application 타입으로 생성 시 프로젝트 구조

		4) 프로젝트 빌드
			- gradle build  ( 메이븐의 경우 mvn package : compile test 배포 )
			- 프로젝트를 컴파일(빌드)한다.
			- build.gradle에 apply plugin: 'java'가 추가된 경우 .jar파일로 패키징까지 된다.
			- 컴파일된 파일들은 'app > build' 폴더 안에 생성되며, .jar파일은 'build > libs'에 패키징된다.
			- 주로 이렇게 : gradle clean build
			- 테스트 실패하면 배포 안됨

		5) 프로젝트 실행
			- gradle run 
			- 컴파일 후 메인클래스를 실행한다.
			- 스프링부트의 경우 gradle bootRun을 통해 앱을 구동할 수 있다.

		6) 프로젝트 패키징
			- gradle jar   : 테스트가 실패해도 패키징 진행됨 ( 참고: gradle bootjar 스프링부트에서 )
			- 프로그램을 .jar로 패키징
			- 'build > libs'에 생성된다.
			- apply plugin: 'java'가 추가된 경우 build명령으로 해결가능

		7) 프로젝트 클린
			- gradle clean
			- build 디렉토리 삭제
			

		8) gradle-wrapper
			- gradle' 명령어로 프로젝트를 빌드할 수 있지만, gradle-wrapper의 실행명령으로도 task를 실행할 수 있다.
			- 새로운 환경에서 gradle을 설치하지 않고도 빌드가 가능
				- gradle 명령어의 경우 기본적으로 gradle이 로컬에 설치가 되어있어야 한다.)
				- 또한 gradle 명령어로 빌드를 할 경우 로컬에 설치된 gradle 버젼으로 빌드되기 때문에, 개발 당시 버젼과 다를경우 문제를 일으킬 수도 있다.
			- gradlew build를 사용하면 사용자가 프로젝트를 만든 사람과 동일한 버전으로 빌드를 할 수 있으며, 심지어 gradle이 설치되지 않아도 빌드가 가능하다.


	3. build.gradle
	
		compile,api : 의존성 상위까지 모두 빌드함 -> 빌드 느림
		implementation : 직접 의존하는 모듈만 빌드 
		testImplementation : 테스트에 사용하는  라이브러리 추가
		annotationProcessor : 
		compileOnly
		runtimeOnly : compile 시에는 필요하지 않지만 실행시 필요
		developmentOnly : 개발시에만 필요, compile시에 제거
		
		
		참고) 메이븐 scope 
			- compile : 컴파일 시에 포함, 배포시 포함
			- runtime : 컴파일시에 포함 안되고 실행중에 포함
			- provided : 개발시에만 필요, 배포시엔 배제
			- test : 테스트시에 필요
			<dependency>
				<scope></scope>
			<dependency>
			
	

인텔리j에서 제공하는 gradle  사용 설정
settings -> Build,Exc.. -> Build tool -> Gradle -> Build run using  2가지 -> inteliJ IDE 로 변경


gradle 프로젝트에 lombok 설정하기

		https://mvnrepository.com/
		lombok 검색 Project Lombok » 1.18.32 
		Gradle(Short)  용 카피하여  build.gradle 파일의 dependency 에 추가

		compileOnly 'org.projectlombok:lombok:1.18.32' 
		->  annotationProcessor 'org.projectlombok:lombok:1.18.32' 로 고쳐서 추가하기

			compileOnly "org.projectlombok:lombok:$lbversion"
			annotationProcessor "org.projectlombok:lombok:$lbversion"
			testcompileOnly "org.projectlombok:lombok:$lbversion"
			testannotationProcessor "org.projectlombok:lombok:$lbversion"



		sourceCompatibility = 17

		ext {  // 각 브레이스 안에서 모두 사용 가능한 전역변수 선언
			lbversion = '1.18.32'
		}


		settings -> Build, Exc.. -> compiler -> Annotation Processor

		gradle sync 버튼 누르기


		sourceCompatibility : 자바버전
		ext {
			//전역변수
		}

		def // 지역변수


docker oracle-xe 설치

C:\Users\admin>docker search oracle-xe

NAME                              DESCRIPTION                                      STARS     OFFICIAL
gvenzl/oracle-xe                  Oracle Database XE (21c, 18c, 11g) for every…   304
oracleinanutshell/oracle-xe-11g                                                    288
wnameless/oracle-xe-11g-r2        Oracle Express Edition 11g Release 2 on Ubun…   105
..

C:\Users\admin>docker pull gvenzl/oracle-xe:18

C:\Users\admin>docker images
C:\Users\admin>docker run --name oracle-xe -d -p 1521:1521 -e ORACLE_PASSWORD=oracle gvenzl/oracle-xe:18


create user study iden... oracle

grant connect, resource to study

alter user study quota unlimited on users;



ojdbc11 mvn repository 검색

	Ojdbc11 » 23.4.0.24.05 
	Orai18n » 23.4.0.24.05   

gradle short 의존성 추가 build.gradle에 

implementation 'com.oracle.database.jdbc:ojdbc11:23.4.0.24.05'
-> 변경
runtimeOnly 'com.oracle.database.jdbc:ojdbc11:23.4.0.24.05'


Orai18n » 23.4.0.24.05    mvn repository 검색
implementation 'com.oracle.database.nls:orai18n:23.4.0.24.05'



Project Lombok » 1.18.32

compileOnly 'org.projectlombok:lombok:1.18.32'
annotationProcessor 'org.projectlombok:lombok:1.18.32'



JDBC(Java DataBase Connectivity) API

	API(Application Programming Interface)
	
		- 자바 데이터베이스 연결 기술 명세서 - 인터페이스로 구성
		- 구현체는 각 DB업체가 구성(데이터베이스 드라이버)
		- java.sql 패키지 :JDBC API
			javadoc - java.sql package
		
		
		
	1. Oracle JDBC 드라이버 설치
		DriverManager 클래스 -> Connection 
		DataSource 인터페이스 -> Connection : 커넥션풀

	2. 연동 과정
	1) java.sql.* 패키지 임포트
	2) JDBC 드라이버 로딩
		- Class.forName(..)  -> odjdbc11.jar
			oralce.jdbc.driver.OracleDriver
			
		
	3) 데이터베이스 접속을 위한 Connection객체 생성
		DriverManager
			.getConnection(..)
			
		DataSource 인터페이스 : 커넥션풀을 제공하는 라이브러리 구현체가 있다
			Tomcat JDBC, HikariCP
			Connection getConnection
			Connection conn = DriverManager.getConnection(url, user, password);
		
	4) 쿼리문을 실행하기 위한 Statement/PreparedStatement/CallableStatement 객체 생성
	5) 쿼리 실행
		Statement
		
	6) 쿼리 실행 결과 값(int, ResultSet) 사용
	7) 사용된 객체(ResultSet, Statement/PreparedStatement/CallableStatement, Connection) 종료

	3. JDBC 드라이버 로딩 및 DBMS 접속
	1) JDBC 드라이버 로딩하기
	2) Connection 객체 생성하기
	3) 데이터베이스 연결 닫기


데이터베이스 쿼리 실행

	1. Statement 객체로 데이터 접근하기
		1) ResultSet executeQuery(String sql)
			- select 
			- 조회
		2) int executeUpdate(String sql)  
		
			- insert, update, delete
				반환값 : 반영된 레코드 갯수
				
		3) close()

	3. 쿼리문 실행 결과 값 가져오기
	
		1) ResultSet 객체의 메서드	
		boolean next()
		
		자료형 get자료형(int 컬럼순서번호 );
		자료형 get자료형(int 컬럼명 );
		
		String getString(..) 문자형 데이터(CHAR, VARCHAR2, CLOB)
		int getInt(...), long getLong(..) :정수형 데이터
		float getFloat(..) double getDouble(..) 실수형 데이터
		
		java.sql.Time
		java.sql.Date
		java.sql.TimeStamp : 날짜 + 시간 = getTimestamp(..)
		
			------------------------------------------------------------------------
			package exam01;
			import org.junit.jupiter.api.Test;
			import java.sql.*;

			public class Ex01 {
				@Test
				void test1() {
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					String url = "jdbc:oracle:thin:@localhost:1521:XE";
					String user = "STUDY";
					String password = "oracle";

					try (Connection conn = DriverManager.getConnection(url, user, password);
						 Statement stmt = conn.createStatement()) {

						String sql = "Insert into member (user_no, user_id, user_pw, user_nm, mobile) values (seq_member.NEXTVAL, 'USER02', '123456', '사용자02','01055554444')";

						int cnt = stmt.executeUpdate(sql);
						System.out.println(cnt);
					}
					catch (SQLException e) {
							e.printStackTrace();
					}
				}
				@Test
				void test2() {
					//오라클 드라이버 동적 로딩
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
					} catch(ClassNotFoundException e) {
						e.printStackTrace();
					}

					String url = "jdbc:oracle:thin:@localhost:1521:XE";
					String user = "STUDY";
					String password = "oracle";

					try (Connection conn = DriverManager.getConnection(url, user, password);
						Statement stmt = conn.createStatement()) {
						//String sql = "select * from member";
			            String keyword = "사용자";
						String sql = "select * from member where user_nm like '%" + keyword + "%'";

						//String sql = "select user_no, user_id from member";
						ResultSet rs = stmt.executeQuery(sql);
						while(rs.next()) {
							//long userNo = rs.getLong(1);   // column index 
							//String userId = rs.getString(2);
							long userNo = rs.getLong("user_no");
							String userId = rs.getString("user_id");
							String userPw = rs.getString("user_pw");
							String userNm = rs.getString("user_nm");
							String mobile = rs.getString("mobile");
							//Timestamp ts =  rs.getTimestamp("reg_dt");
							LocalDateTime regDt =  rs.getTimestamp("reg_dt").toLocalDateTime();

							System.out.printf("user_no : %d, user_id : %s, user_pw : %s, mobile : %s, regDt : %s%n", userNo, userId, userPw, mobile, regDt);
						}
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			------------------------------------------------------------------------------------

	2. PreparedStatement 객체로 데이터 접근하기
	
		1) 동적인 쿼리에 사용
		2) 하나의 객체로 여러 번의 쿼리를 실행할 수 있으며, 동일한 쿼리문을 특정 값만 바꾸어서 여러 번 실행해야 할 때, 매개변수가 많아서 쿼리문을 정리해야 할 때 유용
			- SQL injection 방지 
			
			Connection	
				PreparedStatement prepareStatement(Strinf sql);
			sql에서 값이 할당될 부분에 ? 로 기입
			
			set자료형(int ?의 위치번호, 값)
			
			ResultSet executeQuery();  : select
			int executeUpdate(); : insert, update, delete
			
			---------------------------------------------------------
			public class Ex02 {
				private String url = "jdbc:oracle:thin:@localhost:1521:XE";
				private String user = "STUDY";
				private String password = "oracle";

				@BeforeAll
				static void init() {
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
					}catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			....
			@Test
			void test2() {
				String userId = "USER04";
				String userNm = "사용자04";
				String userPw = "123456";
				String mobile = "0101112222";

				String sql = "insert into member(user_no, user_id, user_nm, user_pw, mobile) values (seq_member.nextval, ?, ?, ?, ?)";

				try(Connection conn = DriverManager.getConnection(url, user, password);
					PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"user_no"})) {
					pstmt.setString(1, userId);
					pstmt.setString(2, userNm);
					pstmt.setString(3, userPw);
					pstmt.setString(4, mobile);

					int cnt = pstmt.executeUpdate();
					System.out.println(cnt);

					// sequence next val 로 만들어진 member 테이블의 기본키의 새로 생성된 데이터 받아오기
					ResultSet rs = pstmt.getGeneratedKeys();
					if(rs.next()) {
						long userNo = rs.getLong(1);
						System.out.printf("user_no : %d%n", userNo);
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}

			}
			---------------------------------------------------------


	4. CallableStatement 객체로 데이터 접근하기
	
		1) 프로시저 실행시 사용 

			-------------------------------------------------
			package exam01;
			import org.junit.jupiter.api.BeforeAll;
			import org.junit.jupiter.api.Test;

			import java.sql.CallableStatement;
			import java.sql.Connection;
			import java.sql.DriverManager;
			import java.sql.SQLException;

			public class Ex03 {
				private String url = "jdbc:oracle:thin:@localhost:1521:XE";
				private String user = "STUDY";
				private String password = "oracle";

				@BeforeAll
				static void init() {
					try {
						//오라클 드라이버 동적 로딩
						Class.forName("oracle.jdbc.driver.OracleDriver");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}

				@Test
				void test1() {
					String sql = "CALL Register_member(?, ?, ?, ?)";
					try(Connection conn = DriverManager.getConnection(url, user, password);
						CallableStatement cstmt = conn.prepareCall(sql)) {
						cstmt.setString(1, "USER06");
						cstmt.setString(2,"12234");
						cstmt.setString(3, "사용자06");
						cstmt.setString(4, "01088899999");
						int cnt = cstmt.executeUpdate();
						System.out.println(cnt);

					}catch(SQLException e) {
						e.printStackTrace();
					}

				}
			}
			----------------------------------------------------
(정리)		
오라클 JDBC Driver ojdbc11.jar

	1. 드라이버 동적 로딩
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
	2. Connection 객체 생성
		1) DriverManager
			static Connection getConnection(url, user.password);
			url = "jdbc:oracle:thin:@localhost:1521:XE"
		2) DataSource 인터페이스 : 커넥션 풀 사용
			Tomcat JDBC
				의존성 : tomcat-jdbc
			HikariCP
		
	3. Statement
		1) 정적 SQL 실행 (보안등 문제)
		2) Connection
			Statement createStatement()
			 - ResultSet executeAQuery(String sql) - Select
				ResultSet : 조회결과 레코드 주소 : 커서 이동 다음해 ㅇ조회
					boolean next() 다음행 있으면 true 없으면 false
					자료형 get자료형(int 컬럼순서번호);
					자료형 get자료형(String 컬럼명);
			int executeUpdate(String sql) ; insert, delete, update
				- 반환값 : 반영된 레코드 갯수
				
	4. PreparedStatement
		1) 동적 SQL
		2) 값을 ? 파라메터로 미리 정의, 값을 set자료형 메서드로  준비
		3) Connection
			PreparedStatement prepareStatement(String sql);
			PreparedStatement prepareStatement(String sql, String[] columnNames);
			 -> ResultSet getGeneratedKeys()를 통해 조회
		4) 실행
			ResultSet executeQuery();
			int executeUpdate();
			
	5. CallableStatement
		- 프로시저 호출용
	

(그러나 문제가 있다)

DriverManager - 접속할때마다 Connection 객체를 생성 -> 효율성 떨어짐, 성능도 안좋음




커넥션 풀
Tomcat JDBC

		2) DataSource 인터페이스 : 커넥션 풀 사용
			Tomcat JDBC
				의존성 : tomcat-jdbc
			HikariCP


	maven repository에서 Tomcat jdbc 검색
		Tomcat JDBC » 10.1.24 
		build.gradle 파일에 의존성 카피 
			implementation 'org.apache.tomcat:tomcat-jdbc:10.1.24' : 
		
		dependencies {
			implementation 'org.apache.tomcat:tomcat-jdbc:10.1.24'
		}

			-----tomcat.jdbc 예제----------------------------------------------------------
			package exam01;
			import org.apache.tomcat.jdbc.pool.DataSource;
			import org.junit.jupiter.api.BeforeAll;
			import org.junit.jupiter.api.Test;

			import java.sql.CallableStatement;
			import java.sql.Connection;
			import java.sql.DriverManager;
			import java.sql.SQLException;

			public class Ex03 {
				private String url = "jdbc:oracle:thin:@localhost:1521:XE";
				private String user = "STUDY";
				private String password = "oracle";

				@BeforeAll
				static void init() {
					try {
						//오라클 드라이버 동적 로딩
						Class.forName("oracle.jdbc.driver.OracleDriver");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}

				@Test
				void test1() {
					String sql = "CALL Register_member(?, ?, ?, ?)";
					try(Connection conn = DriverManager.getConnection(url, user, password);
						CallableStatement cstmt = conn.prepareCall(sql)) {
						cstmt.setString(1, "USER06");
						cstmt.setString(2,"12234");
						cstmt.setString(3, "사용자06");
						cstmt.setString(4, "01088899999");
						int cnt = cstmt.executeUpdate();
						System.out.println(cnt);

					}catch(SQLException e) {
						e.printStackTrace();
					}

				}
				@Test
				void test2() {
					DataSource ds = new DataSource();
					ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
					ds.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
					ds.setUsername("STUDY");
					ds.setPassword("oracle");

					ds.setInitialSize(2);  //초기 로드 생성 연결 객체 수 - 기본값 10
					ds.setMaxActive(10); // 최대 생성할 연결객체 수 - 기본값 100
					ds.setTestWhileIdle(true); // 연결객체가 유휴 상태일때 연결상태 체크
					ds.setTimeBetweenEvictionRunsMillis(5 * 1000); //5초에 한번씩 연결상태 체크
					ds.setMinEvictableIdleTimeMillis(30 * 1000); //유휴상태 객체를 30초후 소멸 후 새로 생성

					try(Connection conn = ds.getConnection()) {
						System.out.println(conn);
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}

			}
			-------------------------------------------------------

	HikariCP » 5.1.0
	implementation 'com.zaxxer:HikariCP:5.1.0'

			-------Hikari 예제---------------------------------------
			@Test
			void test3() {
				HikariConfig config = new HikariConfig();
				config.setDriverClassName("oracle.jdbc.driver.OracleDriver");
				config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:XE");
				config.setUsername("STUDY");
				config.setPassword("oracle");

				config.setMinimumIdle(2);
				config.setMaximumPoolSize(10);

				HikariDataSource ds = new HikariDataSource(config);
				try(Connection cnn = ds.getConnection()) {
					System.out.println(cnn);
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}




마이바티스(mybatis) 프레임워크 설치 및 적용 - 마이바티스 공식사이트 참조
	1. 설치 
		INTELIj 에 gradle 프로젝트 만들면 build.gradle 파일 자동 생성됨 - 의존성 설정파일
		
		mvn repository검색 -= 의존성 추가
		
		ojdbc11 - Oracle JDBC Driver compatible with JDK11, JDK17, JDK19, and JDK21
		Ojdbc11 » 23.4.0.24.05
		Gradle(short)
		implementation 'com.oracle.database.jdbc:ojdbc11:23.4.0.24.05'
			: implementation -> runtimeOnly 로 바꾸기 : 실행중에만..
		
		orai18n - 한글
		Orai18n » 23.4.0.24.05
		implementation 'com.oracle.database.nls:orai18n:23.4.0.24.05'

		MyBatis » 3.5.16
		implementation 'org.mybatis:mybatis:3.5.16'
		
		lombok 
		Project Lombok » 1.18.32
		compileOnly 'org.projectlombok:lombok:1.18.32'
		annotationProcessor 'org.projectlombok:lombok:1.18.32'
    
		의존성 추가 후 sync 버튼
		
		
		https://mybatis.org/ 마이바티스 공식사이트 참조
		
		시작하기 페이지 - XML 설정 복사 (XML설정파일에서 지정하는 마이바티스의 핵심이 되는 설정)
			: resources 안에 configs 디렉토리 만들고 xml 파일 추가(DB연결 설정)
			
		-> src/main/resources/configs/mybatis-config.xml 파일 만들어 붙여넣기
		
			<?xml version="1.0" encoding="UTF-8" ?>
			<!DOCTYPE configuration
					PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
					"https://mybatis.org/dtd/mybatis-3-config.dtd">
			<configuration>
				<environments default="development">
					<environment id="development">
						<transactionManager type="JDBC"/>
						<dataSource type="POOLED">
							<property name="driver" value="${driver}"/>
							<property name="url" value="${url}"/>
							<property name="username" value="${username}"/>
							<property name="password" value="${password}"/>
						</dataSource>
					</environment>
				</environments>
				<mappers>
					<mapper resource="org/mybatis/example/BlogMapper.xml"/>
				</mappers>
			</configuration>		
			
			---------------------------------------------------------------
		-> <properties></properties> 만들고 변수 값 채우기
		
			driver -> oracle.jdbc.driver.OracleDriver
			url -> jdbc:oracle:thin:@localhost:1521:XE
			username -> STUDY
			password -> oracle
			
			
			----이렇게 추가, 수정--------------------------------------------
			<?xml version="1.0" encoding="UTF-8" ?>
			<!DOCTYPE configuration
					PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
					"https://mybatis.org/dtd/mybatis-3-config.dtd">
			<configuration>
				<properties>
					<property name="driver" value="oracle.jdbc.driver.OracleDriver"/>
					<property name="url" value="jdbc:oracle:thin:@localhost:1521:XE" />
					<property name="username" value="STUDY" />
					<property name="password" value="oracle"/>
				</properties>
				<environments default="dev"> 
					<environment id="dev">   <!--개발환경과 운영환경 다르면 추가할 수 있다 -->
						<transactionManager type="JDBC"/>
						<dataSource type="POOLED">
							<property name="driver" value="${driver}"/>
							<property name="url" value="${url}"/>
							<property name="username" value="${username}"/>
							<property name="password" value="${password}"/>
						</dataSource>
					</environment>
				</environments>
				<mappers>
					<mapper resource="mappers/MemberMapper.xml" />
				</mappers>
			</configuration>
			------------------------------------------------
			
	src/main/resources/mappers/MemberMapper.xml 만들기
	-> 마이바티스 공식 사이트 시작하기 아래쪽에 매핑 xml 복사
	  : 맵퍼의 namespace 중요 - 바꾸기 
			
		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE mapper
				PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
				"https://mybatis.org/dtd/mybatis-3-mapper.dtd">
		<mapper namespace="mappers.MemberMapper">
		
			<!-- 
			/*DB 필드명과 클래스 멤버변수와 다를때는 이렇게 매핑 
			<resultMap id="memberMap" type="exam01.Member">
				<result column="user_no" property="userNo" />
				<result column="user_id" property="userId" />
				<result column="user_pw" property="userPw" />
				<result column="user_nm" property="userNm" />
				<result column="mobile" property="mobile" />
				<result column="reg_dt" property="regDt" />
			</resultMap>
			*/
			-->
			
			<select id="getList" resultMap="memberMap">
				SELECT * FROM MEMBER
			</select>
			<!--
			<insert></insert>
			<update></update>
			<delete></delete>
			-->			

		</mapper>
		---------------------------------------------------------------------	
		package configs;
		import org.apache.ibatis.io.Resources;
		import org.apache.ibatis.session.SqlSession;
		import org.apache.ibatis.session.SqlSessionFactory;
		import org.apache.ibatis.session.SqlSessionFactoryBuilder;

		import java.io.IOException;
		import java.io.Reader;

		public class DBConn {
			private static SqlSessionFactory factory;

			static {
				try {
					// 설정파일(mybatis-config.xml) -> Reader 객체로 변환 (resource 폴더 기준)
					Reader reader = Resources.getResourceAsReader("configs/mybatis-config.xml");

					factory = new SqlSessionFactoryBuilder().build(reader); // default environment
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			public static SqlSession getSession(boolean autoCommit) { // autoCommit 옵션 받기
				SqlSession session = factory.openSession(autoCommit);
				return session;
			}

			public static SqlSession getSession() { // autoCommit 설정 없으면 기본으로 autoCommit
				return getSession(true);
			}
		}		
		--------------------------------------------------------------------
		package exam01;
		import lombok.Data;
		import java.time.LocalDateTime;

		@Data
		public class Member {
			private long user_no;
			private String user_id;
			private String user_pw;
			private String user_nm;
			private String mobile;
			private LocalDateTime red_dt;
			private LocalDateTime mod_dt;
		}
		--------------------------------------------------------------------
		package exam02;

		import configs.DBConn;
		import exam01.Member;
		import org.apache.ibatis.session.SqlSession;
		import org.junit.jupiter.api.Test;

		import java.util.List;

		public class Ex01 {

			@Test
			void test1() {
				SqlSession session = DBConn.getSession();
				List<Member> members = session.selectList("mappers.MemberMapper.getList");
				members.forEach(System.out::println);
			}
		}
		-------------------------------------------------------------------

	2. 설정
		SqlSessionFactory
		
			-> SqlSession 객체 생성
			
			
				-> insert(..)
				-> delete(..)
				-> update(..)
				-> List<T> selectList(..)
				-> T selectOne(..)
		
			SqlSessionFactoryBuilder 로 
			
			
			getMapper(Class class)
			
			
			
	3. 적용



5. 로그
	mybatis 사이트 - 매퍼설정 logImpl

		<settings>
			<setting name="logImpl" value="SLF4J"/>
		</settings>
	
	slf4j-api 가이드라인(인터페이스 위주) 만 제공하므로 구현체 필요함
	logback-class 대표적 구현체
	
	의존성 찾기 slf4j api 
	
	SLF4J API Module » 2.0.13
	implementation 'org.slf4j:slf4j-api:2.0.13'


	logback 검색
	Logback Classic Module » 1.5.6
	testImplementation 'ch.qos.logback:logback-classic:1.5.6'
	-> test 떼고 implementation 'ch.qos.logback:logback-classic:1.5.6'

	sync 버튼

	source/main/resources/
	logback.xml  : mybatis 공식사이트 로깅 부분 카피
	    
		<pattern>%5level [%thread] - %msg%n</pattern>
		
		<pattern>%d %5p %c{2} - %m%n</pattern>
		// 5글자 내에서 로그레벨 : 5p  // %m : 로그메시지 , %d :로그시점 기록, %c : 클래스명
		// %c{2} 패키지명은 한자로 축약, 클래스명은 전체이름 표시

		<root level="error">
	
			로그 레벨
			
				FATAL
				ERROR
				WARN
				INFO - 일반정보
				DEBUG - 자세한 정보
				TRACE - 더 자세한 정보
				---------------------------------------------------------------------
				<?xml version="1.0" encoding="UTF-8"?>
				<!DOCTYPE configuration>
				<configuration>

					<appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
						<encoder>
							<pattern>%d %5p %c{2} - %m%n</pattern>
						</encoder>
					</appender>

					<logger name="mappers">
						<level value="DEBUG"/>
					</logger>

					<root level="INFO">
						<appender-ref ref="stdout"/>
					</root>

				</configuration>
				---------------------------------------------------------------------

4. 동적 SQL
	mybatis 사이트의 동적 SQL 참조 
	
	1) if : 
	2) choose, when, otherwise
	3) trim, where, set
	
		<trim prefix="WHERE" prefixOverrides="AND |OR ">  //WHERE절 가장 앞쪽 AND, OR 제거와 WHERE 빠져야 할 경우 필ㅇㅛ
		  ...
		</trim>
		위의 trim 대신 where 절 감싸기
		<WHERE>  //가장 앞쪽 AND, OR 제거
		  ...
		</WHERE>
		
		
		<trim prefix="SET" suffixOverrides=",">   // 마지막 , 는 제거 
		  ...
		</trim>
		위의 trim 대신 set절 감싸기
		<set>
		..
		</set>
		

    <select id="getList3" resultMap="memberMap">
        SELECT * FROM MEMBER
        <where>
            <if test="userId != null">
                user_id = #{userId}
            </if>
            <if test="userNm != null">
                and user_nm like #{userNm}
            </if>
            <if test="mobile != null">
                and mobile like #{mobile}
            </if>
        </where>
    </select>


	<update id="modify">
        update member
            <set>
                <if test="userPw != null">
                    user_pw = #{userPw},
                </if>
                <if test="userNm != null">
                    user_nm = #{userNm},
                </if>
                <if test="mobile != null">
                    mobile = #{mobile},
                </if>
            </set>
        where user_id = #{userId}
    </update>

    <select id="getList3" resultMap="memberMap">
        <bind name="P_userNm" value="'%' + _parameter.getUserNm() + '%'"/>
        <bind name="P_mobile" value="'%' + _parameter.getMobile() + '%'"/>
        SELECT * FROM MEMBER
        <where>
            <if test="userId != null">
                user_id = #{userId}
            </if>
            <if test="userNm != null">
                and user_nm like #{P_userNm}
            </if>
            <if test="mobile != null">
                and mobile like #{P_mobile}
            </if>
        </where>
    </select>		

    <select id="getList4" resultMap="memberMap">
        select * from member
        <where>
            <foreach item="item" index="index" collection="list"
                open="USER_ID IN (" close=")" separator=",">
                #{item}

            </foreach>
        </where>
    </select>
    <select id="getList5">
        select * from member where
        <![CDATA[
            user_no > #{userNo}
        ]]>
    </select>

	4) foreach
	5) bind