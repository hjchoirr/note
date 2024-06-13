웹서버 개발 6/10 오후 (소스 D:\hjchoi\9.웹서버프로그램구현)

개발환경 구축

	인텔리제이 기준
		- plugin : Smart tomcat
		- Tomcat 10 버전 다운로드 -> zip 파일
			WAS(Web Application Server)
			
		(참고) tomcat10 부터 패키지 이름 바뀜 - 자카르타
			tomcat9 -> javaee 8, 패키지명 javax
			tomcat10 -> jakarta ee 10, 패키지명 jakarta

		inteliJ - files -> settings 
		
			-> marketplace -> tomcat 검색 -> Smart Tomcat install
			-> build tool -> gradle -> inteliJ 
			-> encoding -> UTF-8
			
			
		jakarta ee 10 doc api 검색
		
		jakarta.servlet-api
		jakarta.servlet.jsp-api
		
		=> 개발시에만 필요, 실제 구현체는 각 웹서버가 가지고 있으므로 배포시에는 배제됨
		
		의존성 추가 - mvn repository : 
		Jakarta Servlet » 6.0.0
			compileOnly 'jakarta.servlet:jakarta.servlet-api:6.0.0'

		Jakarta Server Pages API » 3.1.1
			compileOnly 'jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.1.1'


	src/main/webapp
			- HTML, JS, CSS, JSP 
			
	src/main/webapp/WEB-INF/  폴더 만들기
		- web.xml : 애플케이션 배치 설명자 파일 만들기 첫줄: <?xml version="1.0" encoding="UTF-8" ?> 다음줄 <web-app>
		- 서블릿, 필더 등등 배치에 대한 설정
		- lib : 자바 라이브러리 jar
		- classes : 컴파일된 class

		web.xml 파일 작성 ( 참고 - D:\hjchoi\apache-tomcat-10.1.24\webapps\examples\WEB-INF : 톰캣 다운 받은거의 examples에 있음 )
		web-app xmlns (네임스페이스)  붙여넣고 마지막부분 지우기 
				--->  metadata-complete="true" 이것만 지우기(이거 있으면 특정 어노테이션 동작 안함)
		
		run : current file -> 드롭다운 클릭 -> 왼쪽 상단 + 버튼 -> Smart Tomcat -> 
			Name : Tomcat 10
			config  -> Tomcat Server : 톰캣 다운받은 폴더
			
		main/webapp/index.jsp 파일 추가( Hello!)
		첫줄 한글깨짐 방지 <%@ page contentType="text/html; charset=UTF-8" %>
웹 기초
	1. 요청과 응답 이해하기
		요청(request)
		응답(response) 

		요청 전문 
			헤더(header)
			  - 요청에 대한 정보 
			  - 요청 주소 (URL, URI)
			  	URL : Uniform Resource Locator
			  	URI : Uniform Resource Identifier
			  	
			  - 요청방식 (method : POST, GET)
			  	GET : 검색등 가져오기 위한 요청
					예) 쿼리스트링 https://search.naver.com/search.naver
						?
						where=nexearch
						&
						sm=top_hty
						&
						fbm=0
						&
						ie=utf8
						&
						query=날씨
						
			  	POST : 서버에 변경을 가하는 요청 
				


			  	
			  - 브라우저의 언어설정(Accept-Language) 등.. 
			  - 브라우저 종류(User-Agent)
			  - body 데이터의 형식(content-type : (예) application/x-www-form-urlencoded; or application/json; ) 
			  - 쿠키 : 브라우저에 저장되는 개인 서비스 데이터
			  
			  헤더확인방법 : 크롬 www.naver.com 우클릭 -> 검사 -> 네트워크 -> 맨위로 스크롤 ->  www.naver.com 선택 -> 헤더 -> 요청헤더
			
			바디(body)
			
				- 요청쪽에서 서버로 전송하는 데이터(POST 메서드)
				
				요청헤더의 Content-Type:application/x-www-form-urlencoded 부분에서 데이터의 형태 알려줌
				
				subject=제목!&content=내용!!  -> URL 인코딩 -> 서버전송 -> 서버수신 -> URL 디코딩 -> 원래 형태
				subject=%EC%A0%9C%EB%AA%A9%21&content=%EB%82%B4%EC%9A%A9%21%21%21
				한글은 전송 안되므로 ㅂ6진수로 변환, 16진수 사이사이 % 
				
				(참고) 
				Content-Type:application/json;	<-- 요청헤더 중
				{"키":"값", "키":"값"}			<-- 요청데이터 body의 내용	 
			
				Content-Type : 요청해더에 정의됨(바디의 컨텐트타입)
				
				- get의 쿼리스트링은 헤더에 포함되는 정보
				- post의 요청정보는 body에 포함되는 정보

		응답 전문
			헤더(header) 
			  - 응답 데이터에 대한 정보
			  - body 데이터의 형식(content-type)
				예) content-type: text/html -> 브라우저는 응답한 분자열 데이터 -> HTML 형식으로 인식 -> Document객체 변환
			  - 응답 상태코드 
			  - 응답 서버에 대한 정보
			  - 서버쪽에서 브라우저 행위 통제
			    : Location: 주소 -> 브라우저의 주소를 변경페이지로 이동
				: Refresh: 초 -> 초 주기로 새로고침
				: Cache-Control: no-cache : 캐시 통제
				: Set-Cookie: 키=값;
				
			바디(body)
				: 서버가 응답한 데이터
	2. HTTP ( HyperText Transfer Protocol) : HTML 형식의 문서전송 프로토콜, 기본포트 80
	   HTTPS ( S : Secured ) : 기본포트 443
	   (참고) FTP, SFTP, WS ( Web Socket )
	   
	3. 헤더(header)
	
	4. HTTP 상태 코드
		2xx : 200 OK 정상응답(요청이 서버에 정상도달, 정상 응답) / 201 작성됨(CREATED) / 204 내용없음(NO CONTENT)
		3xx : 301 영구이동 / 302 임시이동(location으로 이동시) / 304 캐시됨
		4xx : 클라이언트 오류(사용자쪽) 400 잘못된요청(BAD REQUEST) / 서버쪽에서 지정한 형식과 맞지 않는 경우 / 401 권한없음(Unauthorized) 권한없는 페이지 접근 / 404 페이지없음(Page NOT FOUND) / 405 (METHOD NOT ALLOWED)허용되지 않은 요청 매서드 /
		5xx : 내부 서버 오류(INTERNAL SERVER ERROR) 서버의 물리적 오류, 코드상의 오류 / 502(BAD GATEWAY) / 503 서비스이용불가(Service Unavailable) 
	
	5. HTTP 요청 메서드
	
		기타 요청 방식
		  (POST 계열)
		   - PUT 데이터 치환
		   - PATCH 데이터 부분치환
		  (GET 계열)
		   - DELETE
		  OPTIONS : 제공되는 메소드들을 확인 

서블릿(Servlet)

	1. 서블릿(Servlet)의 개요
		: 자바의 웹을 구현하는 기술 
		
	2. 서블릿(Servlet) 클래스 작성하기
		: HttpServlet 추상클래스 상속 -> 서블릿클래스
		
	3. web.xml 파일에 서블릿(Servlet) 등록하기
		<servlet>
			<servlet-name>서블릿구분 이름(중복안됨)</servlet-name>
			<servlet-class>서블릿클래스 전체 이름</servlet-class>
		</servlet>
		<servlet-mapping>
			<servlet-name>서블릿구분 이름</servlet-name>
			<url-pattern>주소패턴</url-pattern>
		</servlet-mapping>
		
			-------------------------------------
			<?xml version="1.0" encoding="UTF-8" ?>
			<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
					 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
					 xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
								  https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
					 version="6.0">
				
				<servlet>
					<servlet-name>hello-servlet</servlet-name>
					<servlet-class>servlets.helloServlet</servlet-class>
				</servlet>
				<servlet-mapping>
					<servlet-name>hello-servlet</servlet-name>
					<url-pattern>/hello</url-pattern>
				</servlet-mapping>
			</web-app>
			-------------------------------------
			
	
	4. XML 문법의 기초
	
	5. 웹 브라우저로부터 데이터 입력 받기
		ServletRequest
			String getPrameter(String name) : name으로 전송된 값
			Map<String, String[]> getParameterMap() :전체 요청 데이터를 Map형태로 반환
			Enumeration<String> getParameterNames() :전송된 모든 name을 반환
			String[] getParameterValues(String name): name으로 전송된 값이 여러개인 경우
			
	
	6. init메서드와  destroy 메서드
	
		init() : 서블릿객체 생성이후 최초 한번 실행
		do요청방식: 요청시마다 매번 실행
		destroy() : 서블릿 객체가 소멸전 한번 실행
		
		ServletConfig
		
			String	getInitParameter​(String name) -> 설정값 조회
			1) 서블릿 설정 init-param : 특정 서블릿내에서만 유효한 초기설정 값
			
				<servlet>
					<init-param>
						<param-name>이름</param-name>
						<param-value>값</param-value>
					</init-param>
				</ervlet>
		
			2) context-param : 모든 서블릿에서 유효한 설정값
		
		
		

		(참고)
		TOMCAT -> 동기식, 접속자 추가 -> 쓰레드 생성
		 -> 서블릿쪽에서 인스턴스 변수 정의 지양
		 
		Jetty : 비동기서버
	
	
			----------------------------------------------------------
			<?xml version="1.0" encoding="UTF-8" ?>
			<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
					 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
					 xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
								  https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
					 version="6.0">

				<context-param>
					<param-name>common1</param-name>
					<param-value>commonValue1</param-value>
				</context-param>
				<context-param>
					<param-name>common2</param-name>
					<param-value>commonValue2</param-value>
				</context-param>

				<servlet>
					<servlet-name>hello-servlet</servlet-name>
					<servlet-class>servlets.HelloServlet</servlet-class>
					<init-param>
						<param-name>key1</param-name>
						<param-value>value1</param-value>
					</init-param>
					<init-param>
						<param-name>key2</param-name>
						<param-value>value2</param-value>
					</init-param>
				</servlet>
				<servlet-mapping>
					<servlet-name>hello-servlet</servlet-name>
					<url-pattern>/hello</url-pattern>
				</servlet-mapping>
			</web-app>
			----------------------------------------------------------
			package servlets;

			import jakarta.servlet.ServletConfig;
			import jakarta.servlet.ServletException;
			import jakarta.servlet.http.HttpServlet;
			import jakarta.servlet.http.HttpServletRequest;
			import jakarta.servlet.http.HttpServletResponse;
			import java.io.IOException;

			public class HelloServlet extends HttpServlet {
				@Override
				public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					System.out.println("doGet()!!");

					// 모든 servlet이 조회 가능
					String common1 = req.getServletContext().getInitParameter("common1");
					String common2 = req.getServletContext().getInitParameter("common2");
					System.out.printf("common1=%s, common2=%s%n", common1, common2);

				}

				@Override
				public void destroy() {
					System.out.println("destroy()");
				}

				@Override
				public void init(ServletConfig config) throws ServletException {
					//HelloServlet 만 조회가능
					String key1 = config.getInitParameter("key1");
					String key2 = config.getInitParameter("key2");
					System.out.printf("key1=%s, key2=%s%n", key1, key2);


				}
			----------------------------------------------------------
			import jakarta.servlet.http.HttpServletRequest;
			import jakarta.servlet.http.HttpServletResponse;

			import java.io.IOException;
			import java.io.PrintWriter;

			public class JoinServlet extends HttpServlet {
				@Override
				public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					String email = req.getParameter("email");
				}

				@Override
				public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					resp.setContentType("text/html");
					resp.setCharacterEncoding("UTF-8");

					PrintWriter out = resp.getWriter();
					out.println("<h1>회원가입</h>");
				}
			}
			----------------------------------------------------------
			package servlets;
			import jakarta.servlet.ServletException;
			import jakarta.servlet.http.HttpServlet;
			import jakarta.servlet.http.HttpServletRequest;
			import jakarta.servlet.http.HttpServletResponse;
			import java.io.IOException;
			import java.io.PrintWriter;
			import java.util.Arrays;

			public class JoinServlet extends HttpServlet {
				@Override
				public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

					//System.out.println(req.getCharacterEncoding());
					req.setCharacterEncoding("UTF-8"); // ???

					PrintWriter out = resp.getWriter();

					String email = req.getParameter("email");
					String password = req.getParameter("password");
					String confirmPassword = req.getParameter("confirmPassword");
					String userName = req.getParameter("userName");
					String[] hobbies = req.getParameterValues("hobby");

					System.out.printf("email=%s password=%s confirmPassword=%s userName=%s%n", email, password, confirmPassword, userName);
					Arrays.stream(hobbies).forEach(s-> System.out.println("hobby:" + s ));
				}

				@Override
				public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					resp.setContentType("text/html;charset=UTF-8");
					//resp.setContentType("text/html");
					//resp.setCharacterEncoding("UTF-8");

					PrintWriter out = resp.getWriter();
					out.println("<h1>회원가입</h1>");
					out.println("<form method='POST' action='join'> ");
					out.println("이메일:<input type='text' name='email'><br> ");
					out.println("비밀번호:<input type='password' name='password'><br> ");
					out.println("비밀번호확인:<input type='password' name='confirmPassword'><br> ");
					out.println("회원명:<input type='text' name='userName'><br> ");

					out.println("취미:<input type='checkbox' name='hobby' value='취미1'>취미1 ");
					out.println("<input type='checkbox' name='hobby' value='취미2'>취미2 ");
					out.println("<input type='checkbox' name='hobby' value='취미3'>취미3 ");
					out.println("<input type='checkbox' name='hobby' value='취미4'>취미4 ");
					out.println("<input type='checkbox' name='hobby' value='취미5'>취미5 <br>");

					out.println("<button type='submit'>가입하기</button><br> ");
					out.println("</form>");

				}
			}

			----- servlet - mapping 설정 묶어서..-------------------------------------------------
			
				<servlet>
					<servlet-name>member-servlet</servlet-name>
					<servlet-class>servlets.member.MemberServlet</servlet-class>
				</servlet>
				<servlet-mapping>
					<servlet-name>member-servlet</servlet-name>
					<url-pattern>/member/* </url-pattern>
				</servlet-mapping>
					*/
			------------------------------------------------------
			package servlets.member;
			import jakarta.servlet.ServletException;
			import jakarta.servlet.http.HttpServlet;
			import jakarta.servlet.http.HttpServletRequest;
			import jakarta.servlet.http.HttpServletResponse;

			import java.io.IOException;

			public class MemberServlet extends HttpServlet {
				@Override
				public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					System.out.println("MemberServlet-doGet()");
					System.out.println(req.getRequestURL());
				}

				@Override
				public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					System.out.println("MemberServlet-doPost()");

				}
			}
		------------------------------------------------------
		
		
6/12 day04 (소스 D:\hjchoi\9.웹서버프로그램구현)

	1) file-settings
		-editor-file encoding
		-builder,-gradle-inteliJ
	
	2) 의존성 추가 : jakarta servlet api,  jakarta servlet jsp, lombok project
	
	compileOnly 'jakarta.servlet:jakarta.servlet-api:6.0.0'
	compileOnly 'jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.1.1'
    compileOnly 'org.projectlombok:lombok:1.18.32'
    annotationProcessor 'org.projectlombok:lombok:1.18.32'
 
	3) 웹서비스용 경로 만들기 src/mian 안에 webapp/WEB-INF/web.xml
	
		(샘플) D:\hjchoi\apache-tomcat-10.1.24\webapps\examples\WEB-INF
		
		<?xml version="1.0" encoding="UTF-8" ?>
		<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
				 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
				 xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
							  https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
				 version="6.0">
		</web-app>
		
	4) 서버설정 (Tomcat 10)
	
	5) src/mian 안에 controllers 패키지
	
	XML 말고 서블릿 매핑 방법 : 어노테이션 @WebServlet 사용하기
	@WebServlet("/member/*") 
	
		--------------------------------------------		
		<?xml version="1.0" encoding="UTF-8" ?>
		<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
				 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
				 xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
							  https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
				 version="6.0">
		</web-app>	
		--------------------------------------------		
		package controllers;
		import jakarta.servlet.ServletException;
		import jakarta.servlet.annotation.WebServlet;
		import jakarta.servlet.http.HttpServlet;
		import jakarta.servlet.http.HttpServletRequest;
		import jakarta.servlet.http.HttpServletResponse;

		import java.io.IOException;
		import java.io.PrintWriter;
		import java.util.Arrays;
		@WebServlet("/member/*")
		public class MemberController extends HttpServlet {
			@Override
			public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				System.out.println("URI: " + req.getRequestURI());
				System.out.println("URL: " + req.getRequestURL().toString());
				System.out.println("getQueryString: " + req.getQueryString());
				System.out.println("getMethod: " + req.getMethod());

				String mode = getMode(req);
				if(mode.equals("join")) {
					joinForm(req, resp);
				}else if(mode.equals("login")) {
					loginForm(req, resp);
				}
			}

			@Override
			public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				String mode = getMode(req);
				if(mode.equals("join")) {
					joinProcess(req, resp);
				}else if(mode.equals("login")) {
					loginProcess(req, resp);
				}
			}

			private String getMode(HttpServletRequest req) {
				String url = req.getRequestURI();
				String[] urls = url.split("/");

				return urls.length > 0 ? urls[urls.length - 1] : "";  // URL 비어있을까봐
			}

			private void joinForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				resp.setContentType("text/html;charset=UTF-8");
				PrintWriter out = resp.getWriter();
				out.println("<h1>회원가입</h1>");
			}
			private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
				resp.setContentType("text/html;charset=UTF-8");
				PrintWriter out = resp.getWriter();
				out.println("<h1>로그인</h1>");

			}
			private void joinProcess(HttpServletRequest req, HttpServletResponse resp) {

			}
			private void loginProcess(HttpServletRequest req, HttpServletResponse resp) {

			}

		}
		--------------------------------------------		


필터와 래퍼

	- 요청과 응답 사이에서 걸러주는 기능 ( 응답 전후 )

			----------------------------------------------------
			<?xml version="1.0" encoding="UTF-8" ?>
			<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
					 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
					 xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
								  https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
					 version="6.0">

				<filter>
					<filter-name>board-filter</filter-name>
					<filter-class>filters.BoardFilter</filter-class>
				</filter>
				<filter-mapping>
					<filter-name>board-filter</filter-name>
					<url-pattern>/board</url-pattern>
				</filter-mapping>

				<filter>
					<filter-name>board2-filter</filter-name>
					<filter-class>filters.Board2Filter</filter-class>
				</filter>
				<filter-mapping>
					<filter-name>board2-filter</filter-name>
					<url-pattern>/board</url-pattern>
				</filter-mapping>

				<filter>
					<filter-name>common-filter</filter-name>
					<filter-class>filters.CommonFilter</filter-class>
				</filter>
				<filter-mapping>
					<filter-name>common-filter</filter-name>
					<url-pattern>/*</url-pattern>
					*/
				</filter-mapping>
			</web-app>
			----------------------------------------------------

			package controllers;

			import jakarta.servlet.ServletException;
			import jakarta.servlet.annotation.WebServlet;
			import jakarta.servlet.http.HttpServlet;
			import jakarta.servlet.http.HttpServletRequest;
			import jakarta.servlet.http.HttpServletResponse;

			import java.io.IOException;
			import java.io.PrintWriter;

			@WebServlet("/board")
			public class BoardController extends HttpServlet {
				@Override
				public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					resp.setContentType("text/html;charset=UTF-8");
					PrintWriter out = resp.getWriter();
					out.println("<h1>게시판</h>");

				}
			}
			----------------------------------------------------
			package filters;
			import jakarta.servlet.*;
			import java.io.IOException;

			public class BoardFilter implements Filter {

				@Override
				public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
					System.out.println("BoardFilter - 응답 전");
					chain.doFilter(req, resp); // 다음 필터 또는 서블릿의 처리 메서드 실행
					System.out.println("BoardFilter - 응답 전");
				}
			}
			----------------------------------------------------
			public class Board2Filter implements Filter {
				@Override
				public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
					System.out.println("Board2Filter - 응답 전");
					chain.doFilter(request, response);
					System.out.println("Board2Filter - 응답 후");
				}
			}
			----------------------------------------------------
			>>
			BoardFilter 응답 전 필터
			Board2Filter - 응답 전
			CommonFilter 요청전
			doGet()!
			CommonFilter 응답후
			Board2Filter - 응답 후
			BoardFilter 응답 후 필터
	


	1. 필터 클래스, 필터 객체, 필터
		Filter 인터페이스를 구현 -> 필터 클래스 

	2. 필터가 위치하는 곳

	3. 필터 클래스의 작성, 설치, 등록
		web.xml 
			<filter>
				<filter-name>..</filter-name>
				<filter-class>...</filter-class>
			</filter>
			<filter-mapping>
				<filter-name>...</filter-name>
				<url-pattern>...</url-pattern>
			</filter-mapping>

		- 필터 체인(filter chain)
		
	4. 필터 클래스의 init 메서드와 destroy 메서드
		web.xml 
			<filter>
				<filter-name>..</filter-name>
				<filter-class>...</filter-class>
				<init-param>
					<param-name>이름</param-name>
					<param-value>값</param-value>
				<init-param>
			</filter>
			
			<filter-mapping>
				<filter-name>...</filter-name>
				<url-pattern>...</url-pattern>
			</filter-mapping>
	

			-----------------------------
			<?xml version="1.0" encoding="UTF-8" ?>
			<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
					 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
					 xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
								  https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
					 version="6.0">

				<filter>
					<filter-name>common-filter</filter-name>
					<filter-class>filters.CommonFilter</filter-class>
					<init-param>
						<param-name>key1</param-name>
						<param-value>value1</param-value>
					</init-param>
					<init-param>
						<param-name>key2</param-name>
						<param-value>value2</param-value>
					</init-param>
				</filter>
				<filter-mapping>
					<filter-name>common-filter</filter-name>
					<url-pattern>/*</url-pattern>*/
				</filter-mapping>

				<filter>
					<filter-name>board-filter</filter-name>
					<filter-class>filters.BoardFilter</filter-class>
				</filter>
				<filter-mapping>
					<filter-name>board-filter</filter-name>
					<url-pattern>/board</url-pattern>
				</filter-mapping>

				<filter>
					<filter-name>board2-filter</filter-name>
					<filter-class>filters.Board2Filter</filter-class>
				</filter>
				<filter-mapping>
					<filter-name>board2-filter</filter-name>
					<url-pattern>/board</url-pattern>
				</filter-mapping>

			</web-app>
			-----------------------------------------
			package filters;
			import jakarta.servlet.*;
			import java.io.IOException;

			public class CommonFilter implements Filter {
				@Override
				public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

					System.out.println("CommonFilter 요청전");  // 여기 코드 삽입 지양 -> Wrapper에다
					chain.doFilter(request, response);
					System.out.println("CommonFilter 응답후"); // 여기 코드 삽입 지양-> Wrapper에다
				}

				@Override
				public void init(FilterConfig filterConfig) throws ServletException {
					System.out.println("CommonFilter - init()");
					System.out.println("key1 : " + filterConfig.getInitParameter("key1"));
					System.out.println("key2 : " + filterConfig.getInitParameter("key2"));
				}

				@Override
				public void destroy() {
					System.out.println("CommonFilter - destroy()");

				}
			}
			----------------------------------------------------------------------
			--------web.xml 필터 설정 빼고 어노테이션 @WebFilter 사용하기------------
			----------------------------------------------------------------------
			package filters;
			import jakarta.servlet.*;
			import jakarta.servlet.annotation.WebFilter;
			import jakarta.servlet.annotation.WebInitParam;

			import java.io.IOException;

			@WebFilter(value="/board",  
				initParams = {
					@WebInitParam(name="k1", value="value1"),
					@WebInitParam(name="k2", value="value2")
			})
			public class BoardFilter implements Filter {
				@Override
				public void init(FilterConfig filterConfig) throws ServletException {
					String k1 = filterConfig.getInitParameter("k1");
					String k2 = filterConfig.getInitParameter("k2");
					System.out.printf("k1=%s k2=%s%n", k1, k2);
				}

				@Override
				public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
					System.out.println("BoardFilter 응답 전 필터");
					chain.doFilter(req, resp); // 다음 필터 또는 서블릿
					System.out.println("BoardFilter 응답 후 필터");
				}
			}
			-------------------------------------------------------------------------

	
	5. 래퍼 클래스 작성 및 적용하기

		필터 클래스 FilterChain::doFilter() 메서드 전, 후 -> 여기에 코드 작성 지양
		- 래퍼는 필터에만 사용 가능함 - 필터에 플러스 알파 기능 추가
		- 필터는 래퍼 없이 사용가능함
		
		기본구현 클래스 - Adapter 클래스
		ServletRequest 인터페이스 -> servletRequestWrapper
		ServletResponse 인터페이스 -> servletResponseWrapper

		HttpServletRequest 인터페이스 -> HttpservletRequestWrapper
		HttpServletRequest 인터페이스 -> HttpservletRequestWrapper
		
		참고) 클래스명에 Wrapper 있다고 해서 감싸는 구조는 아님! (중요)
		
		자카르타 DOC 문서 - 
		
		jakarta.servlet
			- ServletRequestWrapper - ServletRequestWrapper
		jakarta.servlet.http 
			- HttpServletRequest - HttpServletRequestWrapper	

			---------------------------------------------------------
			package filters;
			import jakarta.servlet.*;
			import java.io.IOException;

			public class CommonFilter implements Filter {
				@Override
				public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

					System.out.println("CommonFilter 요청전");  // 여기 코드 삽입 지양 -> 대신에 Wrapper 사용
					chain.doFilter(new CommonRequestWrapper(request), new CommonResponseWrapper(response));
					System.out.println("CommonFilter 응답후"); // 여기 코드 삽입 지양 -> 대신에 Wrapper 사용

				}
			}
			---------------------------------------------------------

			package filters;
			import jakarta.servlet.ServletRequest;
			import jakarta.servlet.http.HttpServletRequest;
			import jakarta.servlet.http.HttpServletRequestWrapper;

			public class CommonRequestWrapper extends HttpServletRequestWrapper {

				public CommonRequestWrapper(ServletRequest request) {
					super((HttpServletRequest) request);

					//요청전 공통처리부분
					System.out.println("요청전 공통처리 코드...CommonRequestWrapper 생성자");
				}

				@Override
				public String getParameter(String name) {
					String value = super.getParameter(name);
					value = value == null ? value : "★" + value + "♥";
					return value;
				}

			}

			---------------------------------------------------------

			package filters;
			import jakarta.servlet.ServletResponse;
			import jakarta.servlet.http.HttpServletResponse;
			import jakarta.servlet.http.HttpServletResponseWrapper;

			public class CommonResponseWrapper extends HttpServletResponseWrapper {
				public CommonResponseWrapper(ServletResponse response) {
					super((HttpServletResponse) response);

					System.out.println("응답후 공통부분 - CommonResponseWrapper 생성자");

				}
			}
			---------------------------------------------------------
			>>>
			CommonFilter 요청전
			요청전 공통처리 코드...CommonRequestWrapper 생성자
			응답후 공통부분 - CommonResponseWrapper 생성자
			BoardFilter 응답 전 필터
			Board2Filter - 응답 전
			doGet()!
			Board2Filter - 응답 후
			BoardFilter 응답 후 필터
			CommonFilter 응답후
			---------------------------------------------------------

		(필터의 용도)
		Examples that have been identified for this design are:

			Authentication Filters
			Logging and Auditing Filters
			Image conversion Filters
			Data compression Filters
			Encryption Filters
			Tokenizing Filters
			Filters that trigger resource access events
			XSL/T filters
			Mime-type chain Filter
			
			인코딩,디코딩 필터 
			쿠키 암호화, 복호화

	필터 예제 
	https://github.com/yonggyo1125/board_jsp-11-/blob/master/main/java/commons/CommonRequestWrapper.java
	
	6/12 15시 동영상 - 테스트 래퍼, 필터 코드 막기
	

(소스 D:\hjchoi\9.웹서버프로그램구현\day04 참고)

JSP의 특징
(Java Server Page) - 서블릿 코드 번역기술
	1. JSP는 서블릿 기술의 확장
	2. JSP는 유지 관리가 용이
	3. JSP는 빠른 개발이 가능
	4. JSP로 개발하면 코드 길이를 줄일 수 있다.

JSP의 페이지 처리과정
	
	post.jsp -> post_jsp.java -> 컴파일 -> post_jsp.class -> 실행 -> _jspInit() - 한번 -> _jspService() - 매 요청시 -> _jspDestroy();
	
	Catalina base : C:\Users\admin\.SmartTomcat\day04\day04.main 내의 work 폴더
	
	
	C:\Users\admin\.SmartTomcat\day04\day04.main\work\Catalina\localhost\day04\org\apache\jsp\ 이곳에
		-> post_jsp.class, post_jsp.java 파일 자동 생성됨 ( 이클립스는 경로 다름)
	
	
JSP 생명 주기
	_jspInit() : 초기화시(처음만 호출)
	_jspService(....) : 매 요청시 
	_jspDestroy() : 소멸시
	
 
	참고)
		_jspService 메서드 지역 내부에 정의된 객체 -> 내장 객체 
		-> jsp 페이지에서 바로 접근이 가능 
		
			- 외우기
			1)HttpServletRequest request : 요청 관련 정보, 기능 
			2)HttpServletResponse response : 응답 관련 정보, 기능
		
			3)PageContext pageContext : JSP로 번역된 서블릿 클래스의 환경 정보, 기능
			4)ServletContext application : 서블릿(애플리케이션) 환경 정보, 기능
			5)ServletConfig config : 서블릿 설정 
			6)HttpSession session : 세션 
			7)JspWriter out : JSP 페이지에 출력
			8)Object page = this // 생성된 서블릿 객체를 참조 
			
			9)Throwable exception   // 에러 페이지에서
			
			----------------------------------------------------------------------
			try {
				response.setContentType("text/html; charset=UTF-8");
				pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
				application = pageContext.getServletContext();
				config = pageContext.getServletConfig();
				session = pageContext.getSession();
				out = pageContext.getOut();
			----------------------------------------------------------------------

		jsp 페이지에서 자바 코드 : 번역시 자바 코드 그자체로 추가 
		<%
			자바 코드 
		%>
		
스크립트 태그
	- 자바 코드를 입력할 수 있는 태그 
	
	1. 스크립트 태그의 종류
		1) 선언문 
			- 번역 위치가 클래스명 바로 아래쪽 추가 (멤버 변수, 메서드)
			<%!
				자바코드 ...
			%>
		2) 스크립틀릿
			_jspService 메서드의 지역 내에 코드 추가 (메서드 정의 X, 변수 -> 지역변수)
			<%
				자바코드 ...   
			%>
		3) 표현문(expression)
			_jspService 메서드의 지역 내에 번역
			
			<%=변수%> = out.print(변수)
		
			-------------------------------------------------
			post.jsp
			-------------------------------------------------
			<%@ page contentType="text/html;charset=UTF-8" %>
			<h1>게시글 작성</h1>

			<form method="post" action="post_ps.jsp">
				제목 : <input type="text" name='subject'><br>
				내용 : <textarea name="content"></textarea><br>
				<button type="submit">작성하기</button>
			</form>
			
			<%
				// 스크립틀릿 : _jspService() 메서드 지역 내부  
				
				int num1 = 100;
				int num2 = 200;
				
				//int result = num1 + num2;
				
				int result = add(num1, num2);
				System.out.println(result);
				
			%>
			<%=num1%>+<%=num2%>=<%=result%>
			<%! 
				// 선언문 : 인스턴스변수
				
				int num1 = 10;
				int num2 = 20;
				
				int add(int num1, int num2) {
					return num1 + num2;
				}
				
			%>
			---------------------------------------------------
			post_jsp.jsp
			-------------------------------------------------
			public final class post_jsp extends org.apache.jasper.runtime.HttpJspBase
				implements org.apache.jasper.runtime.JspSourceDependent,
							 org.apache.jasper.runtime.JspSourceImports,
							 org.apache.jasper.runtime.JspSourceDirectives {

				// 선언문 : 인스턴스변수
				int num1 = 10;
				int num2 = 20;
				
				int add(int num1, int num2) {
					return num1 + num2;
				}
				...

				public void _jspService(final jakarta.servlet.http.HttpServletRequest request, final jakarta.servlet.http.HttpServletResponse response)
				throws java.io.IOException, jakarta.servlet.ServletException {
						// ...
						out.write("\r\n");
						out.write("<h1>게시글 작성</h1>\r\n");
						out.write("\r\n");
						out.write("<form method=\"post\" action=\"post_ps.jsp\">\r\n");
						out.write("    제목 : <input type=\"text\" name='subject'><br>\r\n");
						out.write("    내용 : <textarea name=\"content\"></textarea><br>\r\n");
						out.write("    <button type=\"submit\">작성하기</button>\r\n");
						out.write("</form>\r\n");

						// 스크립틀릿 : 메서드 지역 내부 _jspService() 
						int num1 = 100;
						int num2 = 200;

						//int result = num1 + num2;

						int result = add(num1, num2);
						System.out.println(result);
						
						out.write("<br>\r\n");
						out.print(num1);
						out.write('+');
						out.print(num2);
						out.write('=');
						out.print(result);

					} catch (java.lang.Throwable t) {
							...
						}
					} 
				}	
			-------------------------------------------------	
6/13 동영상, day04 소스 exam02 or exam03 부터 ??
디렉티브 태그
	<%@ ..... %>
	- page 
		
		errorPage -> 에러 출력 페이지 설정
		isErrorPage="true" : 번역될때 exception 내장 객체 생성 
		isELIgnored="true" : EL 식 사용 불가 X
								(Expression Language)
								${식}
		
	- include
	- taglib 

	1. page 디렉티브 태그의 기능과 사용법
		language, contentType, pageEncoding, 
		
		<%@ page language="java" %>
		<%@ page contentType="text/html" %>
		<%@ page pageEncoding="UTF-8" %>


		<%@ page contentType="text/html" pageEncoding="UTF-8" %>
		또는
		<%@ page contentType="text/html; charset=UTF-8" %>
		
		<%@ page import="java.util.*" %>
		<%@ page import="java.time.LocalDateTime" %>
		또는
		<%@ page import="java.util.*,java.time.LocalDateTime" %>
		
		<%@ page session="true" %>

		<%@ page buffer="32kb" %>: 기본값 8KB
		
		<%@ page autoFlush="true" %>  기본값 true : flush는 비운다 = 출력한다
		
		<%@ page info="Home Page JSP" %> 
		
		<%@ page errorPage="error.jsp" %> 현재 페이지에서 에러 나면 error.jsp 페이지로 가라
		<%@ page isErrorPage="true" %>  현재 페이지가 오류페이지인지
			---------------------------------------------------------
			9.웹서버프로그램구현/day04/exam01/ex03.jsp
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ page errorPage="error.jsp" %>
			<%
				int num1 = 0;
				int num2 = 20;
				
				int result = num2 / num1;
			%>

			---------------------------------------------------------
			error.jsp
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ page isErrorPage="true" %>
			<h1>에러 발생</h1>
			<%=exception.getMessage()%>
			<%
				exception.printStackTrace(new java.io.PrintWriter(out));
			%>
			---------------------------------------------------------
			==>	error_jsp.java
			java.lang.Throwable exception = org.apache.jasper.runtime.JspRuntimeLibrary.getThrowable(request);
			if (exception != null) {
			  response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			...
			out.print(exception.getMessage());
			exception.printStackTrace(new java.io.PrintWriter(out));
			...
					
			---------------------------------------------------------
			<%@ page contentType="text/html;charset=UTF-8" %>
			${10 + 20}    // <-- EL식

			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ page isELIgnored="true" %>
			${10 + 20}
			---------------------------------------------------------

		
		
	2. include 디렉티브 태그의 기능과 사용법
		-> JSP, 서블릿, 텍스트파일 출력 결과물을 물리적으로 선언
		
		file="jsp|HTML 경로"
		<%@ include file="..." %>
		
			- ex01.jsp : 각각 출력결과 끼워짐 ( 코드가 아니라 )
			---------------------------------------------------
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ include file="header.jsp" %>
			<main>
				<h1>내용영역</h1>
			</main>
			<%@ include file="footer.jsp" %>
			---------------------------------------------------
			- ex01_jsp.java 
			out.write("<!DOCTYPE html>\r\n");
			out.write("<html>\r\n");
			out.write("	<head>\r\n");
			out.write("		<title>\r\n");
			out.write("		사이트!\r\n");
			out.write("		</title>\r\n");
			out.write("	</head>\r\n");
			out.write("	<body>\r\n");
			out.write("		<header>\r\n");
			out.write("			상단영역\r\n");
			out.write("		</header>\r\n");
			out.write("		\r\n");
			out.write("\r\n");
			out.write("<main>\r\n");
			out.write("	<h1>내용영역</h1>\r\n");
			out.write("</main>\r\n");
			out.write("\r\n");
			out.write("		<footer>\r\n");
			out.write("			<h1>하단영역</h1>\r\n");
			out.write("		</footer>\r\n");
			out.write("	</body>\r\n");
			out.write("</html>");
		
	3. taglib 디렉티브 태그의 기능과 사용법
		- 태그 라이브러리 
		
		JSTL (JSP Standard Tag Libaray) - 3.0
		uri="jakarta.tags.core"
		
		(참고) 예전 JSTL(1.2 ~)
			uri = "http://java.sun.com/jsp/jstl/core|fmt|functions"
		
		- JSTL 3.0
		
			- jstl-api 
			- jstl 구현체 
			
			
			의존성 mvn repository -> 선택 주의 
			Jakarta Standard Tag Library Implementation » 3.0.1
			- api와 구현체 모두 포함되어 있음
			
			implementation 'org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1'

			
		
	JSP의 주석 처리
		<%-- 주석 --%> : 번역 X

		/*  */ : 자바 코드의 주석으로 번역


	https://jakarta.ee/specifications/tags/3.0/tagdocs/

액션태그
	- 액션 태그는 서버나 클라이언트에게 어떤 행동을 하도록 명령하는 태그
	- 스크립트 태그, 주석, 디렉티브 태그와 함게 JSP 페이지를 구성하는 태그
	- 커스텀 태그의 일종


	액션태그의 종류
		1. forward 액션 태그의 기능과 사용법
			<jsp:forward page="이동할 페이지 - JSP, html, text, servlet 경로"  />
			 - 페이지 이동 / 버퍼의 통제 
			 - 기존 출력 버퍼를 취소, page에 정의된 경로의 출력 결과물을 버퍼에 출력하고 비운다.
			 - location 아님 (중요), 버퍼를 이용한 방식 
			 - 출력 결과물을 담아 교체함, 페이지는 이동하지 않고 출력 버퍼내용만 치환 
			 - 아래 ex01.jsp 과 ex03.jsp 의 차이
			 - ex01.jsp 가 8kb 이상이면 그 다음에 치환됨 -- /exam03/ex01.jsp 
				-------------------------------------------------			 
				ex01.jsp
				<%@ page contentType="text/html;charset=UTF-8" %>
				<h1>ex01.jsp</h1>
				<jsp:forward page="ex02.jsp" />
				-------------------------------------------------			 
				ex02.jsp
				<%@ page contentType="text/html;charset=UTF-8" %>
				<h1>ex02.jsp</h1>
				-------------------------------------------------	
				<%@ page contentType="text/html;charset=UTF-8" %>
				<h1>ex03.jsp</h1>
				<%
					response.sendRedirect("ex02.jsp");
				%>
				-------------------------------------------------	

		2. include 액션 태그의 기능과 사용법
		
			- 페이지 추가 / 버퍼의 통제 - 버퍼 내용 추가 방식
			
			-------------------------------------------------	
			9.웹서버프로그램구현\day04\src\main\webapp\exam04\ex01.jsp
			<%@ page contentType="text/html;charset=UTF-8" %>
			<jsp:include page="header.jsp" />
			<main>
				<h1>내용영역...</h1>
			</main>
			<jsp:include page="footer.jsp" />
			-------------------------------------------------	
			ex01_jsp.java
			out.write('\r');
			out.write('\n');
			org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "header.jsp", out, false);
			out.write("\r\n");
			out.write("<main>\r\n");
			out.write("	<h1>내용영역...</h1>\r\n");
			out.write("</main>\r\n");
			org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "footer.jsp", out, false);
			out.write('\r');
			out.write('\n');
			-------------------------------------------------	
			

		3. include 액션 태그와 include 디렉티브 태그의 차이
			- include 디렉티브 : 물리적인 결과물이 추가된채로 하나의 java, class 파일 만들어짐
			- include 액션 태그 : 각각의 jsp에 대한 각각의 java 파일 만들어짐, 출력버퍼 추가 방식
			
		*** 액션태그 forward, include => 출력 버퍼치환, 버퍼추가 
		
		(참고)RequestDispatcher() 메서드의 동작과 동일 - forward(..), include(..)
			
			- ex01.jsp
			----------------------------------------------------
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ page import="jakarta.servlet.*" %>
			<h1>ex01.jsp - 상단</h1>
			<%
				RequestDispatcher rd = request.getRequestDispatcher("ex02.jsp");
				//rd.forward(request, response);
				rd.include(request, response);
			%>
			<h1>ex01.jsp - 하단</h1>
			
			----------------------------------------------------
			- ex02.jsp
			<%@ page contentType="text/html;charset=UTF-8" %>
			<h1>ex02.jsp</h1>
			
			----------------------------------------------------
			
			WEB-INF폴더 : 서블릿에서만 접근 가능하므로 뷰 템블릿jsp들을 여기에 넣어야 함. 
			
				------------------------------------------------------------------------
				package order.controllers;
				import jakarta.servlet.RequestDispatcher;
				import jakarta.servlet.ServletException;
				import jakarta.servlet.annotation.WebServlet;
				import jakarta.servlet.http.HttpServlet;
				import jakarta.servlet.http.HttpServletRequest;
				import jakarta.servlet.http.HttpServletResponse;
				import java.io.IOException;

				@WebServlet("/order")
				public class OrderController extends HttpServlet {
					@Override
					public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

						//서비스 처리...
						req.setAttribute("message", "처리완료");  // 뷰에 전달해줄 값

						//출력처리 (View : order.jsp")
						RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/templates/order/order.jsp");
						rd.forward(req, resp);
					}
				}
				------------------------------------------------------------------------
				- order.jsp
				<%@ page contentType="text/html;charset=UTF-8" %>
				<h1>주문서 확인</h1>
				<h2>${message}</h2>
				------------------------------------------------------------------------



			
		4. param 액션 태그의 기능과 사용법
			- param 액션 태그는 현재 JSP 페이지에서 다른 페이지에 정보를 전달하는 태그
			- 요청 데이터를 전달
			- <jsp:forward /> <jsp:include />
			   : 요청 처리 중에 버퍼추가 / 요청 처리 중 추가 정보를 전달
			   : request.getParameter()
			

				-----------------------------------------------------
				ex03.jsp(exam05)
				<%@ page contentType="text/html;charset=UTF-8" %>
				<h1>ex03.jsp</h1>
				<jsp:forward page="ex04.jsp">
					<jsp:param name="key1" value="value1" />
					<jsp:param name="key2" value="value2" />
				 </jsp:forward>
				 
				-----------------------------------------------------
				ex04.jsp
				<%@ page contentType="text/html;charset=UTF-8" %>
				<h1>ex04.jsp</h>
				<%
				String key1 = request.getParameter("key1");
				String key2 = request.getParameter("key2");
				%>
				<br>
				key1 : <%=key1%>
				<br>
				key2 : <%=key2%>
				-----------------------------------------------------


		한글데이터 post submit 하고 크롬에서 헤더 보면
		contentType : application/x-www-form-urlencoded

			--------------------------------------------------------- 
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ page import="java.net.*" %>
			<h1>ex03.jsp</h1>
			<%
			String key1 = URLEncoder.encode("이름1", "UTF-8");
			%>
			<jsp:forward page="ex04.jsp">
				<jsp:param name="key1" value="<%=key1%>" />
				<jsp:param name="key2" value="value2" />
			 </jsp:forward>
			--------------------------------------------------------- 
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ page import="java.net.*" %>
			<h1>ex04.jsp</h>
			<%
			String key1 = URLDecoder.decode(request.getParameter("key1"),"UTF-8");
			String key2 = request.getParameter("key2");
			%>
			<br>
			key1 : <%=key1%>
			<br>
			key2 : <%=key2%>
			--------------------------------------------------------- 


	(참고)
	 - 속성을 관리하는 객체 4가지
	 - 속성 : 뷰에서 사용할 수 있는 데이터
	
		PageContext pageContext : 하나의 jsp 페이지 내에서만 조회
		HttpServletRequest request : 하나의 요청이 처리되는 범위에서 조회 가능
		HttpSession session : 세션이 유지되는 범위에서 조회가능
		ServletContext application : 매플리케이션이 종료되기 전까지 조회 가능

		void setAttribute(String name, Object value) : 속성지정 / 없을땐 추가, 있을땐 추가
		Object getAttribute(String name) : 속성조회
		void removeAttribute(String name) : 속성제거
			
		데이터가 유지되는 범위
			pageContext < request < session < application

		---------------------------------------------------------
		ex01.jsp (exam06)
		<%@ page contentType="text/html;charset=UTF-8" %>
		<%
			//pageContext.setAttribute("key1", "value1");
			request.setAttribute("key1", "value1");
		%>
		<jsp:include page="ex02.jsp" />

		<%
			//String key1 = (String) pageContext.getAttribute("key1");
			String key1 = (String) request.getAttribute("key1");
		%>
		<div>
			ex01-key1: <%=key1%>
		</div>

		---------------------------------------------------------
		ex02.jsp
		<%@ page contentType="text/html;charset=UTF-8" %>
		<%
			//String key1 = (String) pageContext.getAttribute("key1");
			String key1 = (String) request.getAttribute("key1");
		%>

		<div>
			ex02-key1: <%=key1%>
		</div>
		---------------------------------------------------------

		게시물 목록 예제

		----------------------------------------------------------
		/WEB-INF/templates/board/list.jsp
		<%@ page contentType="text/html;charset=UTF-8" %>
		<%@ taglib prefix="c" uri="jakarta.tags.core" %>
		<h1>게시글 목록</h1>
		<c:forEach var="item" items="${items}" >
			<li>
				${item.subject} / ${item.poster} / ${item.content} / ${item.regDt} /
			</li>
		</c:forEach>
		----------------------------------------------------------
		package board.controllers;
		import board.entities.BoardData;
		import board.services.BoardInfoService;
		import jakarta.servlet.RequestDispatcher;
		import jakarta.servlet.ServletException;
		import jakarta.servlet.annotation.WebServlet;
		import jakarta.servlet.http.HttpServlet;
		import jakarta.servlet.http.HttpServletRequest;
		import jakarta.servlet.http.HttpServletResponse;
		import java.io.IOException;
		import java.util.List;

		@WebServlet("/board/list/*")
		public class BoardListController extends HttpServlet {
			@Override
			public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

				BoardInfoService service = new BoardInfoService();
				List<BoardData> items = service.getList();

				req.setAttribute("items", items);

				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/templates/board/list.jsp");
				rd.forward(req, resp);
			}
		}
		----------------------------------------------------------

		package board.entities;
		import lombok.Builder;
		import lombok.Data;
		import java.time.LocalDateTime;

		@Data
		@Builder
		public class BoardData {
			private long seq;
			private String subject;
			private String content;
			private String poster;
			private LocalDateTime regDt;
		}
		----------------------------------------------------------

		package board.services;
		import board.entities.BoardData;
		import java.time.LocalDateTime;
		import java.util.List;
		import java.util.stream.IntStream;

		public class BoardInfoService {
			public List<BoardData> getList() {
				//기시글 목록 조회

				List<BoardData> items = IntStream.rangeClosed(1, 10).mapToObj(i-> BoardData.builder()
				.seq(i).subject("제목" + i)
						.content("내용" + i)
						.poster("작성자" + i)
						.regDt(LocalDateTime.now())
						.build()).toList();
				return items;
			}
		}
		----------------------------------------------------------



		5. 자바빈즈 액션 태그의 기능과 사용법
			1) 자바빈즈 :  데이터 표현을 목적으로 하는 자바 클래스
			2) 자바빈즈 작성 규칙
			3) useBean 액션 태그로 자바빈즈 사용하기
			4) setProperty 액션 태그로 프로퍼티 값 저장하기
			5) getProperty 액션 태그로 프로퍼티의 값 가져오기