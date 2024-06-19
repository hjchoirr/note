JSP 전체 리뷰

6/18 PM 4 
 - 소스 9.웹서버프로그램구현/day07/

	web.xml
	mybatis-config.xml 
	
	

	

의존성 build.gradle	
	
	servlet-api  (jakarta servlet api) Jakarta Servlet » 6.0.0 
	servlet.jsp-api (Jakarta Server Pages API » 3.1.1)

	- JSTL
	jstl-api (Jakarta Standard Tag Library API » 3.0.0)
	jstl-impl
	lombok
	
	- 디비
	ojdbc11
	mybatis
	
	- 로그
	slf4j-api
	logback-classic
	
	mockito core
	mockito jupiter
	javafaker
	
	
dependencies {
    compileOnly 'jakarta.servlet:jakarta.servlet-api:6.0.0'
    testCompileOnly 'jakarta.servlet:jakarta.servlet-api:6.0.0'
    compileOnly 'jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.1.1'
    implementation 'jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.0'
    implementation 'org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1'
    compileOnly 'org.projectlombok:lombok:1.18.32'
    testCompileOnly 'org.projectlombok:lombok:1.18.32'
    annotationProcessor'org.projectlombok:lombok:1.18.32'
    testAnnotationProcessor 'org.projectlombok:lombok:1.18.32'

    implementation 'com.oracle.database.jdbc:ojdbc11:23.4.0.24.05'
    implementation 'org.mybatis:mybatis:3.5.16'
    implementation 'org.slf4j:slf4j-api:2.0.13'
    implementation 'ch.qos.logback:logback-classic:1.5.6'

    testImplementation platform('org.junit:junit-bom:5.10.0')
    testImplementation 'org.junit.jupiter:junit-jupiter'

    testImplementation 'org.mockito:mockito-core:5.12.0'
    testImplementation 'org.mockito:mockito-junit-jupiter:5.12.0'
    testImplementation 'com.github.javafaker:javafaker:1.0.2'
}

Docker - 

c:> docker exec -it oracle-xe /bin/bash

bash-4.4$ sqlplus system/oracle
SQL> create user PROJECT3 identified by oracle quota unlimited on users;
SQL> grant connect, resource to project3;

dbeaver

CREATE TABLE MEMBER (
	user_no number(10) PRIMARY KEY,
	email varchar2(60) NOT NULL UNIQUE,
	password varchar2(65) NOT NULL,
	user_name varchar2(30) NOT NULL,
	user_type varchar2(10) DEFAULT 'USER' check(user_type IN ('USER', 'ADMIN')),
	reg_dt DATE DEFAULT sysdate,
	mod_dt date
);

CREATE SEQUENCE seq_member;


mybatis-config.xml 

사이트의 매핑된 SQL 구문 살펴보기

org\choongang\member\mapper\MemberMapper
org.choongang.member.mapper.MemberMapper


D:\hjchoi\9.웹서버프로그램구현\day07\src\main\java\
org\choongang\member\mapper\MemberMapper

org\choongang\global\member\mapper
org/choongang/global/configs/mybatis-config.xml


org/choongang/member/mapper/MemberMapper.xml


org/choongang/global/configs/mybatis-config.xml
org/choongang/global/configs/mybatis-config.xml


org.choongang.member.mapper.MemberMapper
org.choongang.member.mapper.MemberMapper

org\choongang\global\configs
org\choongang\global\member\mapper

org\choongang\global\member\mapper
org\choongang\global\configs

mybatis-config.xml");
mybatis-config.xml



컨트롤러 - 뷰

회원가입

로그인

모델 - 서비스

회원가입기능 - JoinService
			- RequestJoin : DTO
			- JoinValidator
			- MemberMapper : DAO
			

			
			