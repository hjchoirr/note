JSP + TDD 객체조립기 등 전체 리뷰 코딩

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
			
        <mapper resource="org/choongang/member/mapper/MemberMapper.xml" />
		
  * mapper를 다시 생성하지 않으면 mybatis 가 이전 데이터를 캐싱하고 있다

MemberServiceProvider 객체조립기 만들기 12시40분 경

RequiredValidator 
	- 필수항목체크 인터페이스 : default 메서드 :  default checkRequired 
	
	
비밀번호 암호화 처리
	- 암호화 
	   : 양방향 암호화 : 암호화 - 복호화 AES256, ARIA ..
	   : 일방향 암호화 : 해시(Hash) - 복호화 불가
	     -  고정해시 - 같은 값에 대해서 같은 해시값 - (md5, sha1,) sha256, sha512
		 -  유동해시 - 같은 값에 대해서도 만들때 마다 변경되는 해시 / 예측 불가능성
			BCrypt 
	
	jbcrypt
		: hashpw(..)
		: checkpw(..)
	- 의존성추가 (jbcrypt 검색)
		implementation 'org.mindrot:jbcrypt:0.4'
		
	
1. 필수항목 체트
2. 이메일로 회원이 등록되어 있는 지 체크
3. 비밀번호 검증
4. 세션 회원 정보 유지



TRUNCATE TABLE MEMBER;



        setParam("email", "***" + form.getEmail());
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            loginService.process(request);
        });
        
        String message = thrown.getMessage();
        assertTrue(message.contains("이메일 또는 비밀번호"));
    }
	
	
given(request.getSession()).willReturn(session);


기본자료혈 래퍼클래스
	Integer
		parseInteger(문자열)
	Long
		pardeLong(문자열..)
	Boolean
		paeseBoolean(문자열)
		
		parse자료형(..) : 문자열 -> 기본자료형
		
	ValueOf(문자열, 기본자료형의 값) -> 래퍼클래스 객체로 변환
	
	Integer.parseInt -> int형으로 변환
	Integer.ValueOf(..) -> Int 클래스형으로 변환


체크박스 처리 방법
	//Objects.requiredNoneNullElse(객체, null일때 기본값);
	String _termsAgree = Objects.requireNonNullElse(request.getParameter("termsAgree"), "false");
	boolean termsAgree = Boolean.parseBoolean(_termsAgree);
	
JoinController

public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            JoinService service = MemberServiceProvider.getInstance().joinService();
            service.process(req);
        } catch(CommonException e){
            resp.setContentType("text/html; charset=UTF-8");
            resp.setStatus(e.getStatus());
            PrintWriter out = resp.getWriter();
            out.printf("<script>alert('%s');</script>", e.getMessage()); //응답코드 설정하기
        }
    }
}

응답코드 : 서버 처리상태 코드
	2xx
	 - 200 :OK
	 - 201 :Created (작성됨)
	 - 204 :NO CONTENT (내용없음)
	3xx
	 - 301,302 :이동
	 - 304 : 캐시됨
	4xx
	 - 400 :잘못된요청
	 - 401 :권한없음
	 - 404 :페이지없음
	 - 405 :허용된 메서드가 아님
	5xx ---인터널 서버 에러
	 - 500 
	 - 503
	 
	
common.tag 에 히든 iframe 추가  - 에러 얼러트 페이지 정신없는거 방지 
	<iframe name="ifrmProcess" class="dn"></iframe>

join.jsp form태그에 target="ifrmProcess" 추가

    <form method="post" action="${actionUrl}" autocomplete="off" target="ifrmProcess">
	

public class MessageUtil {
    public static void alertError(Exception e, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html; charset=UTF-8");
        if(e instanceof CommonException commonException) {
            //CommonException commonException = (CommonException) e; //이거보다 윗줄로.. 
            resp.setStatus(commonException.getStatus());
        }
        PrintWriter out = resp.getWriter();
        out.printf("<script>alert('%s');</script>", e.getMessage()); //응답코드 설정하기
    }
}	


parent.location.href="주소"
parent.location.replace("주소")



public static void go(String url, String target, HttpServletResponse resp) throws IOException {
        target = target == null || target.isBlank() ? "self" : target ;

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        //location.href : 이동기록 남아서 뒤로 가기 하면 이전 POST 페이지로 갈 수 있어서 .. replace(url)로 해야함
        //out.printf("<script>%s.location.href='%s';</script>", target, url);
        out.printf("<script>%s.location.replace('%s');</script>", target, url);
        
    }
}	
	
public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            JoinService service = MemberServiceProvider.getInstance().joinService();
            service.process(req);
            go(req.getContextPath() + "/member/login", "parent", resp );
            //resp.sendRedirect(req.getContextPath() + "/member/login");
        } catch(CommonException e){
            alertError(e, resp);
        }
    }
}
	
	
	
	
	
	

			