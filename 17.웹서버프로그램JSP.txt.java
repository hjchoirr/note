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
			
			api : implementation 'jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.0'  
			구현체 : implementation 'org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1'  
		
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
			- <jsp:include page="이동할 페이지 - JSP, html, text, servlet 경로" />
			
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
		
		(참고)자바의 RequestDispatcher() 메서드의 동작과 동일 
			- forward(..)
			- include(..)
			
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
6/14 9.웹서버프로그램구현\day05 계속


	5. 자바빈즈 액션 태그의 기능과 사용법
		1) 자바빈즈 :  데이터 표현을 목적으로 하는 자바 클래스
			Bean : 자바 객체를 의미함
			<jsp:useBean id="자바빈즈식별이름" class="패키지명 포함한 클래스명" scope="범위">
			
			scope : 속성 조회가능 범위
			  - page(기본값) / PageContext 범위에서 조회 가능
			  - request / HttpServletRequest
			  - session / HttpSession
			  - application / ServletContext
				--------------------------------------------------------
				<%@ page contentType="text/html;charset=UTF-8" %>
				<%@ page import="java.util.*" %>
				<jsp:useBean id="items" class="java.util.ArrayList" />
				${items}
				<%
					// scope='page' 일때 조회
					ArrayList data = (ArrayList)pageContext.getAttribute("items");
					data.add("A");
					data.add("B");
					System.out.println(data); // [A, B]
					
					// scope : request 이면 못가져옴 -> null 왜냐하면 scope기 default pageContext 이므로
					ArrayList data = (ArrayList)request.getAttribute("items");
					System.out.println(data); // null
					
				%>		  
				>> 
				[]
				[A, B]
				null
				--------------------------------------------------------
				<jsp:useBean id="items" class="java.util.ArrayList" scope="request" /> 
				이렇게  scope="request" 넣으면 결과 [] 로 잘 나옴
				
			
		2) 자바빈즈 작성 규칙
			- 기본 생성자가 반드시 정의되어 있어야 한다
			- 데이터를 담고, 조회하는 데이터클래스 위주로 정의(getter, setter)
			- 직렬화(serialization) 필수 (예전)
				-----------------------------------------------------
				<%@ page contentType="text/html;charset=UTF-8" %>
				<%@ page import="java.time.*" %>
				<jsp:useBean id="now" class="java.time.LocalDateTime" />
				${now}
				==> 에러
				-----------------------------------------------------
				<%@ page contentType="text/html;charset=UTF-8" %>
				<%@ page import="board.entities.BoardData" %>
				<jsp:useBean id="item" class="board.entities.BoardData" />
				${item}

				(참고) 빌더패턴 : 직접 객체 생성 안할 목적 (new ..) 이라서 기본생성자가 private 이다
				편법으로 기본생성자 정의하려면 
				  @NoArgsConstructor
				  @AllArgsConstructor
				  이렇게 쓴다
				  
		3) useBean 액션 태그로 자바빈즈 사용하기
		
		4) setProperty 액션 태그로 프로퍼티 값 저장하기
			// setter 메서드 호출과 동일
		5) getProperty 액션 태그로 프로퍼티의 값 가져오기
			// getter 메서드 호출과 동일
			
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ page import="board.entities.BoardData" %>
			<jsp:useBean id="item" class="board.entities.BoardData" />
			${item}<br>

			<jsp:setProperty name="item" property="subject" value="제목" />
			<jsp:setProperty name="item" property="content" value="내용" />
			<jsp:setProperty name="item" property="poster" value="작성자" />

			제목: <jsp:getProperty name="item" property="subject" /><br>
			내용: <jsp:getProperty name="item" property="content" /><br>
			작성자: <jsp:getProperty name="item" property="poster" /><br>
			<br>
			제목: ${item.getSubject()}<br>
			내용: ${item.getContent()}<br>
			작성자: ${item.getPoster()}<br>
			<br>
			제목: ${item.subject}<br>
			내용: ${item.content}<br>
			작성자: ${item.poster}<br>			
			

			========================================================
			<%@ page contentType="text/html;charset=UTF-8" %>
			<form method="post" action="ex04_ps.jsp">
				작성자: <input type='text' name="writer"><br>
				제목: <input type='text' name="subject"><br>
				내용: <textarea name="content"></textarea><br>
				<button type="submit">작성하기</button>
			</form>
			----------------------------------------------------------
			ex04_ps.jsp

			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ page import="board.entities.BoardData" %>
			<jsp:useBean id="item" class="board.entities.BoardData" scope="request"/>
			${item}<br>

			<jsp:setProperty name="item" property="subject" />   //input태그 name 과 property 일치시키기 
			<jsp:setProperty name="item" property="content" />
			<jsp:setProperty name="item" property="poster" param="writer"/>  //input태그 name 과 property 다를때

			제목: ${item.subject}<br>
			내용: ${item.content}<br>
			작성자: ${item.poster}<br>
			----------------------------------------------------------
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ page import="board.entities.BoardData" %>
			<jsp:useBean id="item" class="board.entities.BoardData" scope="request"/>

			<jsp:setProperty name="item" property="*" />   // 이렇게 하면 더 간단히 사용가능함
			
			제목: ${item.subject}<br>
			내용: ${item.content}<br>
			작성자: ${item.poster}<br>
			----------------------------------------------------------



JSP 내장객체

	1. request
	2. response
	3. out
	4. session
	5. application
	6. pageContext
	7. page
	8. config
	9. exception

	속성 처리 객체와 메서드의 종류
		pageContext, request, session, application

		1. setAttribute(String name, Object value)
		2. getAttrubute(String name)
		3. removeAttribute(String name
		4. getAttributeNames()

		-------------------------------------------------------------------
		request.getContextPath()  예제
		<%@ page contentType="text/html;charset=UTF-8" %>
		<%
		String url = request.getContextPath() + "/exam03/ex04.jsp";
		%>
		<a href="<%=url%>">이동하기</a>

		request.getQueryString()
		response.sendRedirect(String url)
		-------------------------------------------------------------------



익스프레션 언어(EL 표현식)
	연산식 
	${식} / 연산, 속성(변수)
		1. 애트리뷰트 형태로 전달되는 데이터
		- setAttribute, getAttribute, removeAttribute

		2. 애트리뷰트 값을 출력하는 EL식
			setAttribute("이름", "값");
			
			${이름}
			

	익스프레션 언어의 기초문법
		1. EL식의 문법
			${...식 ...}
			
		2. 데이터 이름 하나로만 구성된 EL 식

		3. JSP/서블릿 기술에서 사용되는 네 종류의 애트리뷰트
			1) page
			2) request
			3) session
			4) application
			5) EL 식 안에 있는 데이터 이름이 해석되는 순서

			--------------------------------------------------
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%
				pageContext.setAttribute("num", 300);
				request.setAttribute("num", 200);
				application.setAttribute("num", 100);
			%>
			${num} <!--범위가 작은 게 우선 순위 높다-->
			
			>> 300
			--------------------------------------------------



	익스프레션 언어의 내장 객체

		1. 익스프레션 언어의 내장 객체
			속성값을 범위에 따라 조회할 수 있는 객체(map)
				마침표(.), 대괄호(['속성명'])
			
			1) pageScope
			2) requestScope
			3) sessionScope
			4) applicationScope
			
				------------------------------------------------------------
				day05/exam04/ex02.jsp
				<%@ page contentType="text/html;charset=UTF-8" %>
				<%
					pageContext.setAttribute("num", 300);
					request.setAttribute("num", 200);
					application.setAttribute("num", 100);
					session.setAttribute("num", 400);
					
					pageContext.setAttribute("max-num", 1000);

				%>
				pageContext.num : ${pageScope.num}<br>
				request.num : ${requestScope.num}<br>
				application.num : ${applicationScope.num}<br>
				session.num : ${sessionScope.num}<br>
				
				max-num : ${pageScope["max-num"]}<br> <!-- max-num :변수명 규칙과 맞지 않을때 -->
				------------------------------------------------------------

			5) param
			
				- 요청데이터 
					값: url 인코딩된 값
				 Get방식 : ?이름=값&이름=값
				 POST방식 : 바디 / application/x-www-form-urlencoded
				            이름=값&이름=값 

				- 자바서블릿은 모두 String 타입, EL식은 데이터 타입 자동

				- 마침표, 대괄호
				 (참고) HttpServletRequest 의 String getParamter(String name) 동일 
				 
				 
				 
				------------------------------------------------------------- 
				<%@ page contentType="text/html;charset=UTF-8" %>
				${param.num1} + ${param.num2} = ${param.num1 + param.num2}

				http://localhost:3000/day05/exam04/ex03.jsp?num1=100&num2=200
				------------------------------------------------------------- 


				
			6) paramValues

				HttpServletRequest - String[] getParameterValues(String name) 동일
				-----------------------------------------------------------
				<%@ page contentType="text/html;charset=UTF-8" %>
				<form method="POST" action="ex04_ps.jsp">
					이메일 : <input type="text" name="email"><br>
					비밀번호 : <input type="password" name="password"><br>
					선택 : <input type="checkbox" name="chk" value="1">1
					<input type="checkbox" name="chk" value="2">2
					<input type="checkbox" name="chk" value="3">3<br>
					<button type="submit">로그인</button>
				</form>
				-----------------------------------------------------------
				<%@ page contentType="text/html;charset=UTF-8" %>
				이메일 : ${param.email}<br>
				비밀번호 : ${param.password}<br>
				선택 : ${paramValues.chk[0]} | ${paramValues.chk[1]} | ${paramValues.chk[2]}<br>
				-----------------------------------------------------------


			7) header
			8) headerValues
			9) cookie	
			10) initParam

				<context-param>
					<param-name></param-name>
					<param-value></param-value>
				</context-param>
			
			11) pageContext
				- JSP 페이지의 주변 환경에 대한 정보를 제공하는 객체

				-------------------------------------------------------------------------------------
				User-Agent: ${header["User-Agent"]}<br>
				JSESSIONID : ${cookie.JSESSIONID.getValue()}<br>
				JSESSIONID : ${cookie.JSESSIONID.value}<br>
				JSESSIONID : ${header["Cookie"]}<br>			
				key1 : ${initParam.key1}<br>
				URI : ${pageContext.getRequest().getRequestURI()}<br>
				URI : ${pageContext.request.requestURI}<br>   <!-- 윗줄과 동일 -->
				>>>
				User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36
				JSESSIONID : 0331517251F37DC5E111AA7569558144
				JSESSIONID : 0331517251F37DC5E111AA7569558144
				JSESSIONID : JSESSIONID=0331517251F37DC5E111AA7569558144
				key1 : value1
				URI : /day05/exam04/ex05.jsp
				URI : /day05/exam04/ex05.jsp
				-------------------------------------------------------------------------------------
				web.xml
				<?xml version="1.0" encoding="UTF-8"?>
				<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
						 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						 xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
									  https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
						 version="6.0">

					<context-param>
						<param-name>key1</param-name>
						<param-value>value1</param-value>
					</context-param>

				</web-app>				
				-------------------------------------------------------------------------------------
				
			11) pageContext
				- JSP 페이지의 주변 환경에 대한 정보를 제공하는 객체
			

	익스프레션 언어의 연산자
	
	1. 익스프레션 언어의 연산자
		lt - lesser than : <
		gt - greater than : >
		le  - lesser than and equal : <=
		ge - greater than and equal : >= 
		eq - equal : ==
		ne - not equal != 

		논리 연산자 
			&&  / AND
			||  / OR
			!  / NOT

	2. 엠프티 연산자
	
		${empty str}
		null 체크, 빈문자열 ''
		
		${empty param.str ? '없음' : param.str }
		
		
	3. 대괄호 연산자와 마침표 연산자
		EL식 속성 : 객체 -> 객체의 각 속성명을 접근(getter  호출)
		예) ${book.title} -> book.getTitle()
			 ${book['title']}
			 
			 마침표로 사용하는 경우는 변수명 규칙과 동일하게 적용
				- 앞에는 숫자 X  예) nums['0']
				- 특수문자 $, _  예) num-1 (X) -> 대괄호 연산자 ['num-1']

JSTL - Jsp Standard Tag Library

	의존성 
    api : implementation 'jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.0'  
    구현체 : implementation 'org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1'   


	1. 설치하기
	
		core 라이브러리 : 제어구문, 반복문 등
		fmt 라이브러리 : 형식화, 숫자 형식, 날짜 형식, 시간대, 다국어
		functions 라이브러리 : 문자열 가공 등
		sql 라이브러리 - 안씀 : jsp에서 DB 쿼리 하는것 안좋음

		https://jakarta.ee/specifications/tags/3.0/tagdocs/


		2. 코어(core) 라이브러리
			uri="jakarta.tags.core"
			 참고)
				javaee 9 ->  JSTL 1.2 
				uri="http://java.sun.com/jsp/jstl/core"
				
				<%@ taglib prefix="c" uri="jakart.tags.core" %>
				
					
			1) <c:set>	 : 속성 설정
			   <c:set var="속성명" value="값" />
			   ${속성명}
			   scope="page|request|session|application"
			   기본값 : page

				-------------------------------------------------
				<%@ page contentType="text/html;charset=UTF-8" %>
				<%@ taglib uri="jakarta.tags.core" prefix="c" %>
				<c:set var="num1" value="100" />
				<c:set var="num2" value="200" />
				${num1} + ${num2} = ${num1 + num2}<br>


			2) <c:remove>	
				- 속성값 제거
				<c:remove var="속성명" />
				-scope 값이 없으면 모든 범위의 속성값 제거
				
				-------------------------------------------------
				<%@ page contentType="text/html;charset=UTF-8" %>
				<%@ taglib uri="jakarta.tags.core" prefix="c" %>
				<c:set var="num1" value="100" />
				<c:set var="num1" value="200" scope="request" />
				<c:set var="num1" value="300" scope="application" />
				pageScope.num1 : ${pageScope.num1} <br>
				requestScope.num1 : ${requestScope.num1} <br>
				applicationScope.num1 : ${applicationScope.num1} <br>

				<c:remove var="num1" />

				pageScope.num1 : ${pageScope.num1} <br>
				requestScope.num1 : ${requestScope.num1} <br>
				applicationScope.num1 : ${applicationScope.num1} <br>
				
				>>>
				pageScope.num1 : 100
				requestScope.num1 : 200
				applicationScope.num1 : 300
				pageScope.num1 :
				requestScope.num1 :
				applicationScope.num1 :
				-------------------------------------------------
			3) <c:if>
				- 조건식
				<c:if test="${조건식}">
				</c:if>
				
					-------------------------------------------------				
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib prefix="c" uri="jakarta.tags.core" %>
					<c:set var="num" value="10" />
					<c:if test="${num % 2 == 1}">
					홀수입니다
					</c:if>
					<c:if test="${!(num % 2 == 1)}">
					짝수입니다
					</c:if>

					${num % 2 == 1 ? "홀수입니다" : "짝수입니다"}
					-------------------------------------------------				

			4) <c:choose>
				<c:choose>
					<c:when test="${조건식1}">
					</c:when>
					<c:when test="${조건식2}">
					</c:when>
					<c:when test="${조건식3}">
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				
					-------------------------------------------------				
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib uri="jakarta.tags.core" prefix="c" %>
					<c:choose>
						<c:when test="${param.age < 8}">
							유치원생
						</c:when>
						<c:when test="${param.age < 14}">
							초등학생
						</c:when>
						<c:when test="${param.age < 17}">
							청소년
						</c:when>
						<c:otherwise>
							성인
						</c:otherwise>
					</c:choose>			
					
					http://localhost:3000/day05/exam05/ex04.jsp?age=15
					>>청소년
					-------------------------------------------------				

			5) <c:forEach>
			
				- 반복문 
				- 횟수 
					<c:forEach begin=시작숫자, end=종료숫자, step=증감단위/기본값1, var="카운트변수" >
					</c:forEach>
						-----------------------------------------------------
						<%@ page contentType="text/html;charset=UTF-8" %>
						<%@ taglib uri="jakarta.tags.core" prefix="c" %>
						<c:forEach begin="1" end="10" var="cnt" step="2">
							<div>반복 ${cnt}</div>
						</c:forEach>					
						-----------------------------------------------------
				- 배열, 컬랙션(Collection - List, Set), Iterator, Enumeration (items="${배열컬렉션 속성}" var="요소1개")
					향상된 for문
					varStatus="status"
						${status.index} :반복순서번호(0부터 시작)
						${status.count} :반복순서번호(1부터 시작)
						${status.first} :반복순서처음인지
						${status.last} :반복순서마지막인지
				
				- 콤마(,) 문자열 
				- <c:forEach>액션의 items 애트리뷰트를 이용해서 처리할 수 있는 데이터
					
					-------------------------------------------------------------
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib prefix="c" uri="jakarta.tags.core" %>
					<h1>게시글 목록</h1>
					<ul>
					<c:forEach  var="item" items="${items}" >
						<li>${item.seq} | ${item.subject} | ${item.poster} | ${item.content} | ${item.regDt}
					</c:forEach>
					</ul>
					-------------------------------------------------------------
					==============================================================
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib prefix="c" uri="jakarta.tags.core" %>
					<h1>게시글 목록</h1>
					<ul>
					<c:forEach  var="item" items="${items}" varStatus="status">
						<li>${item.seq} | ${item.subject} | ${item.poster} | ${item.content} | ${item.regDt}
							<div>
								index: ${status.index} / count: ${status.count}
								first: ${status.first} / last: ${status.last}<br>
								current: ${status.current}
							</div>
						</li>
					</c:forEach>
					</ul>
					>>
					게시글 목록
					1 | 제목1 | 작성자1 | 내용1 | 2024-06-14T16:53:05.419775900
					index: 0 / count: 1 first: true / last: false
					current: BoardData(seq=1, subject=제목1, content=내용1, poster=작성자1, regDt=2024-06-14T16:53:05.419775900)
					2 | 제목2 | 작성자2 | 내용2 | 2024-06-14T16:53:05.419775900
					index: 1 / count: 2 first: false / last: false
					current: BoardData(seq=2, subject=제목2, content=내용2, poster=작성자2, regDt=2024-06-14T16:53:05.419775900)
					3 | 제목3 | 작성자3 | 내용3 | 2024-06-14T16:53:05.419775900
					index: 2 / count: 3 first: false / last: false
					current: BoardData(seq=3, subject=제목3, content=내용3, poster=작성자3, regDt=2024-06-14T16:53:05.419775900)
					4 | 제목4 | 작성자4 | 내용4 | 2024-06-14T16:53:05.419775900


					-------------------------------------------------------------------
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib prefix="c" uri="jakarta.tags.core" %>

					<c:forEach var="item" items="Apple,Orange,Melon">
						<div>${item}</div>
					</c:forEach>
					>>
					Apple
					Orange
					Melon
					-------------------------------------------------------------------

			6) <c:forTokens>
				- java.util.StringTokenizer 
				
				var="문자" items="문자열.." delim="구분패턴문자"
					-------------------------------------------------------------------
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib prefix="c" uri="jakarta.tags.core" %>

					<c:forTokens var="item" items="Apple#Orange#Melon" delims="#+">
					<div>${item}</div>
					</c:forTokens>
					>>
					Apple
					Orange
					Melon
					-------------------------------------------------------------------
6/17 day05 계속


			7) <c:catch>
				- 자바 코드 없이 태그 방식으로 예외 처리 
				- var : 예외가 발생하면 생성될 예외객체 변수명
						- 예외가 없으면 null
					----------------------------------------------------
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib uri="jakarta.tags.core" prefix="c" %>


					<c:catch var="e">
					<%
						String str = null;
						str.toUpperCase();
					%>
					</c:catch>
					<c:if test="${e != null}">
						<%--에러메시지 : ${e.getMessage()} 아래줄과 동일-->
						에러메시지 : ${e.message}
					</c:if>
					----------------------------------------------------
						
			8) <c:redirect>
				- 주소변경 <c:redirect url="ex03.jsp" />
				- HttpServletRequest 의 sendRedirect(String location) 과 동일
				- <c:param name="이름" value="값" /> : 쿼리스트링 형태로 URL에 파라미터 추가
				
					----------------------------------------------------
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib prefix="c" uri="jakarta.tags.core" %>

					<h1>ex02.jsp</h1>

					<c:redirect url="ex03.jsp" />				
					----------------------------------------------------
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib prefix="c" uri="jakarta.tags.core" %>

					<h1>ex02.jsp</h1>

					<c:redirect url="ex03.jsp">
						<c:param name="key1" value="value1" />
						<c:param name="key2" value="value2" />
					</c:redirect>					
					----------------------------------------------------
				
			9) <c:import>
				 : 서버쪽 자원 외에도 외부 자원도 버퍼에 추가 가능
				 - url : 버퍼에 추가할 자원 주소
				 - 어떤 값을 출력하는 태그의 경우 값을 EL식 속성으로 추가할 수  있는 속성 var
				   var="변수" : 변수에 출력결과물 담을 수도 있다
				   - <c:param name="이름" value="값" /> : 요청데이터에 추가도 할 수 있다
				   
				   ---------------------------------------------------
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib prefix="c" uri="jakarta.tags.core" %>
					<h1>ex04.jsp - 상단</h1>
					<c:import var="html" url="inc.jsp" />
					${html}
					${html}
					<h1>ex04.jsp - 하단</h1>				   
					================================================
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib prefix="c" uri="jakarta.tags.core" %>
					<h1>ex04.jsp - 상단</h1>

					<c:import url="inc.jsp" >
						<c:param name="num1" value="20" />
						<c:param name="num2" value="30" />
					</c:import>
					<h1>ex04.jsp - 하단</h1>
					------------------------------------------------------
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib prefix="c" uri="jakarta.tags.core" %>
					<h1>inc.jsp</h1>

					num1 : ${param.num1}<br>
					num2 : ${param.num2}<br>
					합계 : ${param.num1 + param.num2}
					------------------------------------------------------
				   
				참고) 
					<jsp:include ... /> 
					  : 버퍼에 추가, 서버쪽 자원만 버퍼에 추가 가능
					  : <jsp:param name="이름" value="값" /> : 요청데이터에 추가
					------------------------------------------------------
					
			10) <c:url> 
					- contextPath를 자동으로 붙여주는 태그 ==> 꼭 이렇게 url 시용할것 ****중요
					- <c:param name="이름" value="값" /> : 요청데이터에 추가도 할 수 있다
					
					
				참고) HttpServletRequest 의 	String getContextPath()
				
					---------------------------------------------------
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib prefix="c" uri="jakarta.tags.core" %>
					<a href="<c:url value='/member/login' />">로그인</a>			
					----------------------------------------------------
					<%@ page contentType="text/html;charset=UTF-8" %>
					<%@ taglib prefix="c" uri="jakarta.tags.core" %>
					<c:url var="loginUrl" value='/member/login' />


					<a href="${loginUrl}">로그인</a>
					<a href="<c:url value='/member/join' />">회원가입</a>

					<form method="POST" action="${loginUrl}" />
						이메일 : <input type="text" name="email"><br>
						비번 : <input type="password" name="password"><br>
						<button type="submit">로그인</button>
					</form>						
					----------------------------------------------------
			11) <c:out> 	
			- Escape된 문자열로 출력
			- Escape된 상태 : HTML 태그를 문자열로 변경하여 출력
				<h1>제목</h1> - > lt;h1gt;제목lt;/h1gt;
			- default :기본값
			- escapeXml : true - 기본값(HTML -> 문자)
						: false -> HTML로 인식
				---------------------------------------------------------
				<%@ page contentType="text/html;charset=UTF-8" %>
				<%@ taglib prefix="c" uri="jakarta.tags.core" %>
				<c:set var="html" value="<script>alert('알림');</script>" />
				<%--
				${html}
				--%> 
				<c:out value="${html}" />  <%-- 보안적인 측면에서 사용: 자바스크립트를 실행시키지 않기 위해 --%>
				<br>
				<c:out value="${str}" default="값없음" />
				<c:out value="<h1>제목</h1>" />
				<c:out value="<h1>제목제목</h1>" escapeXml="false"/>
				---------------------------------------------------------
						
			
			
			
3. 포매팅(fmt) 라이브러리
	uri="jakarta.tags.fmt" // JSTL 3.0
	prefix="fmt"
	
	
	JSTL 1.2
	uri="http://java.sun.com/jsp/jstl/fmt"
	
	1) fmt:formatDate : 날짜 형식화 
	  - Date 객체 기준
	  - java.text.SimpleDateFormat
	  - type :date(기본값) - 날짜만 표기
			 time - 시간만 표기
			 both - 날짜와 시간을 함께 표기
	  - dateStyle : full, long, medium, short
	  - timeStyle : full, long, mediaum, short
	  - pattern : SimpleDateFormat 에 정의된 날짜, 시간 형식화 패턴
			------------------------------------------------------------------------------------------------
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ page import="java.util.*" %>
			<%@ taglib prefix="c" uri="jakarta.tags.core" %>
			<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
			<c:set var="date" value="<%=new Date()%>" />
			${date}<br>
			<fmt:formatDate value="${date}" /><br>
			type: date - <fmt:formatDate value="${date}" type="date" /><br>
			type : time - <fmt:formatDate value="${date}" type="time" /><br>
			type : both - <fmt:formatDate value="${date}" type="both" /><br>

			style : full - <fmt:formatDate type="both" value="${date}" dateStyle="full" timeStyle="full" /><br>
			style: short - <fmt:formatDate type="both" value="${date}" dateStyle="short" timeStyle="short" /><br>

			pattern : <fmt:formatDate type="both" value="${date}" pattern="yyyyMMdd HH:mm:ss" /><br>  
			------------------------------------------------------------------------------------------------
	  
	2) fmt:formatNumber : 숫자 형식화 
	  - java.text.DecimalFormat
	  - groupingUsed : true(기본값) false( , 숫자 3자리마다 나누지 않기)
	  - type : percent( 소수점표기 -> 퍼센트표기) | currency(지역에 맞는 총화 표기법)
	  - currencySymbol="기호"
	  - pattern 
	  
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ taglib prefix="c" uri="jakarta.tags.core" %>
			<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

			<c:set var="num1" value="100000000" />
			<fmt:formatNumber value="${num1}" /><br>
			<fmt:formatNumber value="${num1}" groupingUsed="false" /><br>
			type - percent : <fmt:formatNumber value="0.25" type="percent" /><br>
			type - currency : <fmt:formatNumber value="10000" type="currency" /><br>	
			  
			<c:set var="num" value="123000000.12" /><br>
			#,###.##### : <fmt:formatNumber value="${num}" pattern="#,###.#####" /><br>
			0,000.00000 : <fmt:formatNumber value="${num}" pattern="0,000.00000" /><br>

	  
	3) fmt:setLocale
		:지역화 설정
		- value : 언어코드(en, ko, ja, zn) 또는 언어코드_국가코드 (en_us, ko_kr, ja_jp)
		
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ page import="java.util.*" %>
			<%@ taglib prefix="c" uri="jakarta.tags.core" %>
			<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

			<c:set var="date" value="<%=new Date()%>" />
			<h1>대한민국</h1>
			<fmt:setLocale value="ko_kr" />
			일시:<fmt:formatDate value="${date}" type="both" dateStyle="full" timeStyle="full" /><br>
			금액:<fmt:formatNumber value="100000000" type="currency" /><br>

			<h1>미국</h1>
			<fmt:setLocale value="en_us" />
			일시:<fmt:formatDate value="${date}" type="both" dateStyle="full" timeStyle="full" /><br>
			금액:<fmt:formatNumber value="100000000" type="currency" /><br>

			<h1>일본</h1>
			<fmt:setLocale value="ja_jp" />
			일시:<fmt:formatDate value="${date}" type="both" dateStyle="full" timeStyle="full" /><br>
			금액:<fmt:formatNumber value="100000000" type="currency" /><br>


	4) fmt:timeZone과 fmt:setTimeZone  -> 엣날 자바 라이브러리 사용하므로 잘 안씀 -> 커스텀 만들어 쓰기
		- 시간대 
		- value: Asia/Seoul
		
		- fmt:setTimeZone : 태그 적용후 하위 태그에 반영
		- fmt:timeZone : 여는 태그, 덛는 태그, 태그 안쪽에 내용이 반영

			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ page import="java.util.*" %>
			<%@ taglib prefix="c" uri="jakarta.tags.core" %>
			<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
			<c:set var="date" value="<%=new Date()%>" />
			서울 : <fmt:formatDate value="${date}" type="both" /><br>
			<fmt:timeZone value="Europe/London">
				런던 : <fmt:formatDate value="${date}" type="both" /><br>
			</fmt:timeZone>

			<fmt:timeZone value="America/New_York">
				뉴욕 : <fmt:formatDate value="${date}" type="both" /><br>
			</fmt:timeZone>

			<fmt:setTimeZone value="Europe/London" />
				런던 : <fmt:formatDate value="${date}" type="both" /><br>

			<fmt:setTimeZone value="America/New_York" />
				뉴욕 : <fmt:formatDate value="${date}" type="both" /><br>


	
	5) fmt:setBundle과 fmt:bundle
		<fmt:message key="메시지키"/> 와 함께 사용
	
		- 메시지 관리, 문구관리
		
		- properties 파일형태로 관리 / 클래스 패스에 위치
		   키=값
		   키=값
		
		- properties 파일의 주석 : #
		
		- 클래스패스 : 클래스파일을 인식할 수 있는 경로
			src/main/java
			src/main/resource : 메시지파일(properties 파일)
			webapp/WEB-INF/lib, webapp/WEB-INF/classes..
			
		<fmt:bundle basename="messages.commons">     ( messages.commons : properties 파일)
			...
		</fmt:bundle>
		위 두줄 또는 아래 한줄
		<fmt:setBundle basename="messages.commons" /> 
		
		
		- 메시지 치환기능
		- 메시지 작성시 {0} {1} {2} 

		- 다국어설정 ( main/resources/messages/common_en.properties ) : 파일이름만으로 가능함
			1. 요청 헤더 Accept-Language 헤더정보 : Locale객체
			2. <fmt:setLocale>
		
			--------------------------------------------------------
			---main/resources/messages/common.properties 파일 ------
			# 로그인
			EMAIL=이메일
			PASSWORD=비밀번호
			LOGIN=로그인
			LOGIN_MSG={0}({1}) 님 로그인
			--------------------------------------------------------
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ taglib prefix="c" uri="jakarta.tags.core" %>
			<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
			<fmt:bundle basename="messages.common">
			<form>
				<fmt:message key="EMAIL" />
				<input type="text" name="email"><br>

				<frm:message key="PASSWORD" />
				<input type="text" name="password"><br>

				<button type="submit"><fmt:message key="LOGIN" /><button>
			</form>
			</fmt:bundle>
			--------------------------------------------------------

			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ taglib prefix="c" uri="jakarta.tags.core" %>
			<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
			<fmt:setBundle basename="messages.commons" />
			<form>
				<fmt:message key="EMAIL" />
				<input type="text" name="email"><br>

				<frm:message key="PASSWORD" />
				<input type="text" name="password"><br>

				<button type="submit"><fmt:message key="LOGIN" /><button>
			</form>

				<fmt:message key="LOGIN_MSG">
					<fmt:param>값1</fmt:param>
					<fmt:param>값2</fmt:param>
				</fmt:message>
			--------------------------------------------------------
			이메일=이메일
			비밀번호=비밀번호
			로그인=로그인
			로그인_메시지={0}({1}) 님 로그인
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ taglib prefix="c" uri="jakarta.tags.core" %>
			<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
			-------------------------------------------------
			<c:if test="${!empty param.lang}">
				<fmt:setLocale value="${param.lang}" />
			</c:if>

			<fmt:setBundle basename="messages.common" />

			<a href="?lang=ko">한국어</a>
			<a href="?lang=en">English</a>

			<form>
				<fmt:message key="이메일" />
				<input type="text" name="email"><br>

				<frm:message key="비밀번호" />
				<input type="text" name="password"><br>

				<button type="submit"><fmt:message key="로그인" />
				</button>
			</form>
			<hr>
			<h1>
				<fmt:message key="로그인_메시지">
					<fmt:param>이이름</fmt:param>
					<fmt:param>aaa@bbb.com</fmt:param>
				</fmt:message>
			</h1>
			
			-------------------------------------------------
			
	CTRL + SHIFT + N : 크롬 시크릿 모드
	
		6) fmt:requestEncoding
			<fmt:requestEncoding value="UTF-8" />
		
			참고)서블릿 4버전까지 적용 필요
			request.setCharacterEncoding("...");
			

	4. 함수(functions) 라이브러리
		uri="jakarta.tags.functions"
		prefix="fn"
		
		 - 문자열 가공 편의 함수
		 - EL식 내에서 적용 가능 ${fn:concat(...)}
		 
			-----------------------------------------------------------
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ taglib prefix="c" uri="jakarta.tags.core" %>
			<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
			<c:set var="str" value="Apple,Melon,Orange,Grape" />

			<c:set var="fruits" value="${fn:split(str,',')}" />
			str : ${str} <br>
			fruits : ${fruits} <br>

			<c:forEach var="fruit" items="${fruits}">
			${fruit}<br>
			</c:forEach>

			str2 : ${fn:join(fruits, '#')}<br>
			-----------------------------------------------------------

커스텀 액션

	1. 커스텀 액션을 만드는 방법
		1) 태그 파일을 작성해서 만드는 방법
			확장자 .tag 
			<%@ taglib prefix=".." tagdir="경로" %>
			
				---태그정의 : main/webapp/WEB-INF/tags/utils/line.tag 파일-----------
				<%@ tag body-content="empty" %>
				.....................................................

				---만든태그 사용-----------------------------------------------------
				<%@ page contentType="text/html;charset=UTF-8" %>
				<%@ taglib prefix="util" tagdir="/WEB-INF/tags/utils" %>
				<util:line />


		2) 태그 클래스를 작성해서 만드는 방법 (많이 안씀)
			SimpleTag 인터페이스
			SimpleTagSupport 클래스

	2. 태그파일을 이용해서 커스텀 액션 만들기
		1) tag 지시자는 태그 파일에만 사용할 수 있는 지시자인데, 웹 컨테이너가 태그 파일을 처리할 때 필요한 여러가지 정보를 기술하는 역할을 합니다.
		2) tag 지시자는 page 지시자와 마찬가지로 <%@으로 시작해서 %>로 끝나야 합니다. 그리고 <%@ 바로 다음에는 지시자의 종류를 표시하는 tag라는 이름이 와야 합니다.
		3) 여러가지 정보를 이름="값" 또는 이름='값' 형태로 기술할 수 있습니다. 즉, 애트리뷰트 형태로 기술할 수 있습니다.

	3. 태그 파일에서 사용할 수 있는 지시어
		1) tag 지시자
			- page 지시자와 유사, 태그의 정보 정의
			- body-content 
			
				: empty - 단일 테그 형태로 사용하는 경우, 예) <util:line />
				: scriptless - 여는 태그, 닫는 태그, 태그 안쪽 내용물에 자바코드 사용불가, EL식과 가른 태그 사용 가능
				: tagdependent - 여는 태그, 닫는 태그, 태그 안쪽 내용물은 모두 문자로 인식(자바코드, EL식, 모든태그 사용불가)
				
			- pageEncoding : 태그 인코딩 설정
			- import:자바패키지 추가
			- trimDirectiveWhitespaces : true - 태그 앞뒤 공백을 제거하고 출력
			
		2) include 지시자
			- <%@ include file="파일경로" %>
			
		3) taglib 지시자
			- 태그 파일 내에서도 다른 태그 라이브러리 기능 사용가능함
			
		4) attribute 지시자
			<%@ attribute name="속성명" %>
			- 속성명으로 지역변수 생성
			- 속성명으로 EL식 속성 추가됨
			
			- type : 자료형(기본값-java.lang.String)
			       : 기본자료형은 사용 X,  래퍼클래스 형태로만 설정
				   (int -> Integer)
			- required : false(기본값) - 없어도 될때
			             true - 필수(설정하지 않으면 경고표시)
						 
				--------------------------------------------------------   
				<%@ page contentType="text/html;charset=UTF-8" %>
				<%@ taglib prefix="util" tagdir="/WEB-INF/tags/utils" %>
				<util:line color="blue" size="30"/>
				<util:line color="orange" size="100" />

				<%@ tag body-content="empty" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
				<%@ attribute name="color" %>
				<%@ attribute name="size" type="java.lang.Integer" required="true" %>
				<%--
				<%=color%>
				${color}
				--%>
				<div style="color: ${color};">
				<%
					for(int i = 0; i < size; i++) {
						out.write("-");
					}
				%>
				</div>
			
				
				

		5) variable 지시자
		 
	4. 한글을 포함하는 태그 파일
	5. 애트리뷰트(속성)를 지원하는 태그 파일
	6. 태그 파일의 내장 변수
	7. 동적 애트리뷰트를 지원하는 태그 파일
	- dynamic-attributes
			=======================================================
			다이나믹 속성 예제
			<%@ page contentType="text/html; charset=UTF-8" %>
			<%@ taglib prefix="util" tagdir="/WEB-INF/tags/utils" %>
			<util:starline  color="blue" size="30" />				
			--------------------------------------------------------   
			<%@ tag body-content="empty" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
			<%@ tag import="java.util.*" %>
			<%@ tag dynamic-attributes="attrs" %>
			<%--
			${attrs.size}
			${attrs.color}
			--%>
			<%
				Map<String, String> attrs = (Map<String, String>) jspContext.getAttribute("attrs");
				String _size = attrs.getOrDefault("size", "0");
				int size = Integer.parseInt(_size);
			%>
			<div style="color: ${attrs.color};">
			<%
				for(int i = 0; i < size; i++) {
					out.write("*");
				}
			%>
			</div>
			--------------------------------------------------------   
	
	8. 커스텀 액션의 본체를 처리하는 태그 파일
		tag body-content="scriptless"
		<jsp:doBody />
	
			---------------------------------------------------------	
			<%@ page contentType="text/html;charset=UTF-8" %>
			<%@ taglib prefix="util" tagdir="/WEB-INF/tags/utils" %>
			<util:linebox color="blue">
				<h1>안녕하세요</h1>
			</util:linebox>	
			---------------------------------------------------------	
			<%@ tag body-content="scriptless" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
			<%@ attribute name="color" %>
			<div style="border: 1px double ${color}; padding:30px;">
				<jsp:doBody />
			</div>
			---------------------------------------------------------	
	
	
	9. 변수를 지원하는 커스텀 액션
		@variable : 변수
		
		예) <c:set var="변수명" value="..." />
	
		1) name-given : 속성명 - EL식 변수
		2) variable-class : EL식 변수의 자료형, 문자(java.lang.String - 기본값)
		3) scope 
			- NESTED :여는 태그, 닫는 태그 형식, 태그 내부에서 EL식 변수를 접근
			- AT_BEGIN : 여는 태그 바로 아래쪽에서 접근
			- AT_END : 주로 단일 태그에서 많이 사용하고, 닫는 태그 아래쪽에 접근 가능

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/utils" %>
<util:max num1="100" num2="200" />
${maximum}

<%@ tag body-content="empty" %>
<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="num1" type="java.lang.Integer" required="true" %>
<%@ attribute name="num2" type="java.lang.Integer" required="true" %>
<%@variable name-given="maximum" variable-class="java.lang.Integer" scope="AT_END" %>
<%
    int max = num1 > num2 ? num1 : num2;
%>
<c:set var="maximum" value="<%=max%>" />


	10. 커스텀 액션의 본체 안에서 변수를 사용하는 예
		1)  name-from-attribute
		2)  alias

- 변수명 속성의 필수조건
	- name 속성이 [var] 이며 그 값은 name-from-attribute의 값인) java.lang.String 타입이어야 하고
	  required 여야 하며, "rtexprvalue"가 되어서는 안됩니다.

		----------------------------------------------------------
		<%@ page contentType="text/html;charset=UTF-8" %>
		<%@ taglib prefix="c" uri="jakarta.tags.core" %>
		<%@ taglib prefix="util" tagdir="/WEB-INF/tags/utils" %>
		<util:min var="min" num1="100" num2="200" />
		${min}
		----------------------------------------------------------
		min.tag
		<%@ tag body-content="empty" %>
		<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
		<%@ taglib prefix="c" uri="jakarta.tags.core" %>
		<%@ attribute name="num1" type="java.lang.Integer" required="true" %>
		<%@ attribute name="num2" type="java.lang.Integer" required="true" %>
		<%@ attribute name="var" required="true" rtexprvalue="false"%>
		<%@ variable name-from-attribute="var" alias="minimum" variable-class="java.lang.Integer" scope="AT_END" %>

		<%
			int min = num1 < num2 ? num1 : num2;
		%>
		<c:set var="minimum" value="<%=min%>" />
		----------------------------------------------------------

	11. 커스텀 액션 태그를 이용하여 레이아웃 구성하기

		---------------------------------------------------------------
		<%@ page contentType="text/html;charset=UTF-8" %>
		<%@ taglib prefix="c" uri="jakarta.tags.core" %>
		<%@ taglib prefix="util" tagdir="/WEB-INF/tags/utils" %>
		<util:min var="min" num1="100" num2="200" />
		${min}
		<br>
		<util:forEach var="a" begin="10" end="20">
			${a} 번째<br>
		</util:forEach>

		-------forEach.tag---------------------------------------------
		<%@ tag body-content="scriptless" %>
		<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
		<%@ taglib prefix="c" uri="jakarta.tags.core" %>
		<%@ attribute name="var" required="true" rtexprvalue="false" %>
		<%@ attribute name="begin" type="java.lang.Integer" required="true" %>
		<%@ attribute name="end" type="java.lang.Integer" required="true" %>
		<%@ variable name-from-attribute="var" alias="cnt" variable-class="java.lang.Integer" scope="NESTED" %>

		<% for(int i = begin; i <= end; i++ ) { %>
			<c:set var="cnt" value="<%=i%>" />
			<jsp:doBody />
		<% } %>

		---------------------------------------------------------------
