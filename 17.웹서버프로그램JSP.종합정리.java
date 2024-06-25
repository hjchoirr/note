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
	
자바에서 response.setStatus(Exception.getStatus) 



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


parent.location.href="주소"   => history 남아 뒤로가기 가능함
parent.location.replace("주소")  => history 남지 않아 뒤로가기 불가능함



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
			
6/21 

	로그인 구현 
	- 로그인 상태 EL식 속성, 회원정보 속성 - 필터를 이용하여 모든 요청에 공유가능하게 
	
		tag/util/GuestOnly..
		
		CommonFilter : Filter를 구현
			import jakarta.servlet.Filter; 꼭 이거 임포트..
			
			doFilter() 구현
			@WebFilter("/*") : 모든걸 필터링한다
			
			requestWrapper ..
			
			
	이메일 기억하기
		login.jsp
		
		<input type="text" name="email" value="${cookie.saveEmail.value}">

		loginController.java
		
		//이메일 기억하기 처리
		String email = req.getParameter("email");
		Cookie cookie = new Cookie("saveEmail", email);

		if(req.getParameter("saveEmail") != null) {
			// 7일간 기억하기
			cookie.setMaxAge(60 * 60 * 24 * 7);
		} else { // 체크 해제 : 만료날짜를 과거로
			cookie.setMaxAge(0);
		}

		resp.addCookie(cookie);

	
	파일 업로드
	
		1. multipart
		
			일반적인 양식 전송 형태 : contentType: application/x-www-form-urlencoded (텍스트양식 데이터 종류)
			파일데이터는 바이트 데이터 형식 : 
			
			파트를 나눠서 일부파트는 양식데이터, 일부파트는 파일데이터 -> 멀티파트
			멀티파트 형태로 전송하려면 form태그의 속성 중 enctype="multipart/form-data"
			
				<form method="post" action="${actionUrl}" enctype="multipart/form-data">
	
		2. 의존성 추가
				commons-fileupload2-jakarta (스프링에는 이미 추가되어 있음)
				
				Apache Commons FileUpload Jakarta Servlet 6 » 2.0.0-M2
				
				implementation 'org.apache.commons:commons-fileupload2-jakarta-servlet6:2.0.0-M2'

		3. 파일업로드
			FileUploadController.java
			
			public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

				JakartaServletDiskFileUpload upload = new JakartaServletDiskFileUpload();

				//multipart 형식 body 데이터를 일반 양식과 파일 데이터를 분리하여 조회 가능한 List 형태로 변환
				List<DiskFileItem> items = upload.parseRequest(req);
				for (DiskFileItem item : items) {
					if (item.isFormField()) { //일반 텍스트 형태의 양식데이터
						String name = item.getFieldName();
						String value = item.getString(Charset.forName("UTF-8"));
						System.out.printf("name=%s, value=%s\n", name, value);
					} else { //파일 데이터
						String filename = item.getName();
						String contentType = item.getContentType();
						long size = item.getSize(); //파일크기 byte

						System.out.println("filename=" + filename + ", contentType=" + contentType + ", size=" + size);
						File file = new File("D:/uploads/" + filename);
						item.write(file.toPath());
					}
				}
			}
			
			
		multiple : 파일 여러개 가능하게 
		jsp 파일 : <input type="file" name="file" multiple>
		
		
		5. 파일서치
			
			D:/uploads/ 브라우저 접근 불가 -> 서블릿이 파일 읽어와서 body에 출력 / 형식 정확히 알려줘야 함 contentType
			
			웹경로 /upload/1.png   -> D:/uploads/1.png
			

			
			file 서치 - 자바 
			
				@WebServlet("/upload/*")
				public class FileSearchController extends HttpServlet {
					@Override
					public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
						String uri = req.getRequestURI().replace(req.getContextPath(), "");
						Pattern pattern = Pattern.compile("^/upload/(.+)");
						Matcher matcher = pattern.matcher(uri);
						if(matcher.find()) {
							String fileName = matcher.group(1); // .+ 부분에 해당되는 거 : group(1)
							File file = new File("D:/uploads/" + fileName);
							if(file.exists()) {
								Path source = file.toPath();
								String contentType = Files.probeContentType(source);
								resp.setContentType(contentType);
								
								try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))){
									OutputStream out = resp.getOutputStream();
									out.write(bis.readAllBytes());
								}
								return;
							}
						}
						resp.sendError(HttpServletResponse.SC_BAD_REQUEST);

					}
				}		
			
		
		5. 파일다운로드
		
			- 출력방향을 브라우저화면 아닌 지정된 파일로 지정
			- 응답헤더 : Content-Disposition; attatchment;filename=파일명

			- 캐싱?
			- 캐싱이 되면 기존 파일 내용이 변경 안됨 
			
			-> 캐싱 사용하지 않도록 하려면
				Cache-Controll: must-revalidate - 캐시 갱신 (new)
				Pragma : public - 캐시 갱신 (old)
				
			오랜시간 다운로드하면 - 브라우저가 연결 종료 (time out)
			-> 정상적인 다운로드 불가
			
			Expires : 0 -> 만료시간 없음
			
			파일전체용량 :Content-length
파일형식: Content-Type
			
			
