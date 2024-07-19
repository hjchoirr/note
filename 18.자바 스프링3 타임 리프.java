7/18 PM 3:40


타임리프(Thymeleaf)

1. 설정

	thymeleaf-spring6
	thymeleaf - java8time // JDK8 Date & TIME API  -> #temporals : 형식화 
	thymeleaf layout :레이아웃 기능 

	implementation 'org.thymeleaf:thymeleaf-spring6:3.1.2.RELEASE'
	implementation 'org.thymeleaf.extras:thymeleaf-extras-java8time:3.0.4.RELEASE'
	implementation 'nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.3.0'


2. 특징
	- Natural Template 
	- 원래 HTML과 서버사이드 렌더링 결과 거의 동일하게 보이는 효과 
	- 번역 기술 
	- 캐시 기능 제공
	

		@Configuration
		@RequiredArgsConstructor
		public class ThymLeafConfig implements WebMvcConfigurer {
			private final WebApplicationContext applicationContext;

			@Bean
			public SpringResourceTemplateResolver templateResolver() {
				SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
				templateResolver.setApplicationContext(applicationContext);
				templateResolver.setPrefix("/WEB-INF/templates2/");
				templateResolver.setSuffix(".html"); // natural templates 지향
				templateResolver.setCacheable(false);  //개발할때만 false로 
				return templateResolver;
			}

			@Bean
			public SpringTemplateEngine templateEngine() {
				SpringTemplateEngine templateEngine = new SpringTemplateEngine();
				templateEngine.setTemplateResolver(templateResolver());
				templateEngine.setEnableSpringELCompiler(true);  // EL식 지원
				templateEngine.addDialect(new Java8TimeDialect()); // 확장기능 : time
				templateEngine.addDialect(new LayoutDialect()); // 확장기능 : layout
				return templateEngine;
			}

			@Bean
			public ThymeleafViewResolver thymeleafViewResolver() {
				ThymeleafViewResolver resolver = new ThymeleafViewResolver();
				resolver.setContentType("text/html");
				resolver.setCharacterEncoding("utf-8");
				resolver.setTemplateEngine(templateEngine());
				return resolver;
			}

			@Override
			public void configureViewResolvers(ViewResolverRegistry registry) {
				registry.viewResolver(thymeleafViewResolver());
			}
		}
	
	
타임리프 기본문법 

	1. 타임리프의 주요 식(expression)
	
		1) 변수 식 : ${식...}
		
			${변수, 연산, 함수식}
			
		2) 메세지 식 : #{메세지 코드}

			참고) jsp 경우
				fmt:setBundle
					<fmt:message key="메세지 코드">
					
					
		3) 링크 식 : @{링크}
			<a th:href="@{/member/login}">로그인</a>
			<a th:href="@{/member/login(p1=*{email}, p2=*{userName})}">로그인</a>
			<a th:href="@{/member/info/{p1}/{p2}(mail=*{email},mail2=*{userName})}">로그인</a><br>

			- 컨텍스트 경로 추가 
			-  URL 변수 식, 요청 파라미터 쉽게 추가 
				
			
			참고) jsp 경우
				<c:url value="...." />

		4) 선택 변수식 

			th:object="${객체}" 
			
				*{속성명}

			th:block 태그 -> 번역되면 삭제 
					-> 기능만 필요한 경우 

				<div th:object="${member}"><div>
				<th:block th:object="${member}"></th:block>
				
			------------
			변수식예제
			------------
				<!DOCTYPE html>
				<html xmlns="http://www.thymeleaf.org">
					<head>
						<meta charset="UTF-8">
					</head>
					<body>
						<h1>회원정보</h1>
						<dl>
							<dt>이메일</dt>
							<dd th:text="${member.email}"></dd>
						</dl>
						<dl>
							<dt>회원명</dt>
							<dd th:text="${member.userName}"></dd>
						</dl>
						<dl>
							<dt>가입일시</dt>
							<dd th:text="${member.regDt}"></dd>
						</dl>
					</body>
				</html>

			=> 선택 변수식 적용 간결하게

				<!DOCTYPE html>
				<html xmlns="http://www.thymeleaf.org">
					<head>
						<meta charset="UTF-8">
					</head>
					<body>
						<h1>회원정보</h1>
						<div th:object="${member}">
							<dl>
								<dt>이메일</dt>
								<dd th:text="*{email}"></dd>
							</dl>
							<dl>
								<dt>회원명</dt>
								<dd th:text="*{userName}"></dd>
							</dl>
							<dl>
								<dt>가입일시</dt>
								<dd th:text="*{regDt}"></dd>
							</dl>
						</div>
					</body>
				</html>

			메시지식
				<!DOCTYPE html>
				<html xmlns="http://www.thymeleaf.org">
					<head>
						<meta charset="UTF-8">
					</head>
					<body>
						<h1>회원정보</h1>
						<div th:object="${member}">
						<dl>
							<dt th:text="#{이메일}">이메일</dt>
							<dd th:text="*{email}"></dd>
						</dl>
						<dl>
							<dt th:text="#{회원명}">회원명</dt>
							<dd th:text="*{userName}"></dd>
						</dl>
						<dl>
							<dt th:text="#{가입일시}">가입일시</dt>
							<dd th:text="*{regDt}"></dd>
						</dl>
						</div>
					</body>
				</html>

			th:block 태그 -> 위의 div 태그 대신

				//<!--<div th:object="${member}">-->
				<th:block th:object="${member}">
					<dl>
						<dt th:text="#{이메일}">이메일</dt>
						<dd th:text="*{email}"></dd>
					</dl>
					<dl>
						<dt th:text="#{회원명}">회원명</dt>
						<dd th:utext="*{userName}"></dd>  // utext 는 html도 가능함
					</dl>
					<dl>
						<dt th:text="#{가입일시}">가입일시</dt>
						<dd th:text="*{regDt}"></dd>
					</dl>
					
					<a href="#" th:href="@{/member/login}"></a>로그인<br>
					<a th:href="@{/member/login(p1=*{email}, p2=*{userName})}">로그인</a><br>
					<a th:href="@{/member/info/{p1}/{p2}(mail=*{email},mail2=*{userName})}">로그인</a><br>
				</th:block>

	2. 타임리프 식 객체
	
		1) #strings
		2) #numbers
		3) #dates, #calendars, #temporals : (java 8, java.time 패키지)
		4) #lists, #sets, #maps
			-> 내장 식객체에 없는 기능? 스프링 빈으로 생성 

			${@빈이름.메서드명(...)}

	3. th:text	
	
		- 문자열 출력 (문자열만!! - HTML 태그는 해석 X)
		- th:utext - HTML 태그도 해석 O ( js 코드 )
		
			<dt th:text="#{회원명}">회원명</dt>
			<dt th:utext="#{회원명}">회원명</dt> 
		
		- 기본 : 속성을 통해서 번역 
		- [[${.. }]] : 태그 안쪽에서도 출력 / 문자열만 인식, HTML 태그는 해석 X
		
	4. th:each 
	
		- 반복문

		- status
			- index : 0부터 시작하는 순서 번호 
			- count : 1부터 시작하는 순서 번호
			- first : 첫번째 행 여부 
			- last : 마지막 행 여부 
			- even  : 짝수 행 여부 
			- odd : 홀수 행 여부 
			
				<tbody>
					<tr th:each="item : ${items}">
						<td th:text="${item.email}"></td>
						<td th:text="${item.userName}"></td>
						<td th:text="${item.regDt}"></td>
					</tr>
				</tbody>
				
				<tbody>
					<tr th:each="item : ${items}" th:object="${item}">
						<td th:text="*{email}"></td>
						<td th:text="*{userName}"></td>
						<td th:text="*{regDt}"></td>
					</tr>
				</tbody>			
				
                <tr th:each="item, status : ${items}" th:object="${item}">
                    <td th:text="${status.count}"></td>
                    <td th:text="*{email}"></td>
                    <td th:text="*{userName}"></td>
                    <td th:text="*{regDt}"></td>
                </tr>				
				
                <tr th:each="item, status : ${items}" th:object="${item}">
                    <td th:text="${status.count}"></td>
                    <td>
                        순번:[[${status.count}]]
                        / 첫번째 : [[${status.first}]]
                        / 마지막 : [[${status.last}]]
                        / 짝수 : [[${status.even}]]
                        / 홀수 : [[${status.odd}]]
                    </td>
                    <td th:text="*{email}"></td>
                    <td th:text="*{userName}"></td>
                    <td th:text="*{regDt}"></td>
                </tr>
			
	5. th:if, th:unless 
		th:if : 조건식 
		th:if="${....}"
		
		th:unless="${...}" : 조건식이 false -> 노출, true -> 노출 X
		
			<tr th:each="item, status : ${items}" th:object="${item}">
				<td th:text="${status.count}"></td>
				<td>
					순번:[[${status.count}]]
					/ 첫번째 : [[${status.first}]]
					/ 마지막 : [[${status.last}]] /
					<th:block th:if="${status.even}" th:text="짝수"></th:block>
					<th:block th:if="${status.odd}">홀수</th:block>
					<th:block th:unless="${status.even}"> / 홀수</th:block>
				</td>
				<td th:text="*{email}"></td>
				<td th:text="*{userName}"></td>
				<td th:text="*{regDt}"></td>
			</tr>
				
		true, false -> 상수로 바로 인식
		
			<h1 th:if="true">항상노출</h1>
			<h1 th:if="false">항상 미노출</h1>		

	7. th:switch, th:case 
	
		<tr th:each="item, status : ${items}" th:object="${item}">
			<td th:text="${status.count}"></td>
			<td>
				순번:[[${status.count}]]
				/ 첫번째 : [[${status.first}]]
				/ 마지막 : [[${status.last}]] /
				<th:block th:switch="${status.even}">
					<span th:case="true">짝수</span>
					<span th:case="false">홀수</span>
				</th:block>
				/
				<th:block th:if="${status.even}" th:text="짝수"></th:block>
				<th:block th:if="${status.odd}">홀수</th:block>
				<th:block th:unless="${status.even}"> / 홀수</th:block>
			</td>
			<td th:text="*{email}"></td>
			<td th:text="*{userName}"></td>
			<td th:text="*{regDt}"></td>
		</tr>
		
	8. th:href
		th:src 
		th:action 
		
	9. th:object 
	
	10. th:classappend
	
		- 클래스를 조건에 따라 추가 제거하는 문법
		th:classappend="${조건식 ? '참일때 추가될 클래스명' : '거짓일때 추가될 클래스명'}"
		
		<tr th:each="item, status : ${items}" th:object="${item}" th:classappend="${status.even} ? 'on':''">

		

스프링 MVC 폼과 에러 메시지 연동

	1. #fields.errors(..)
	
		- 특정 필드에 한정한 오류 출력(email, password..)
		
			- 커맨드 객체에 있는 에노테이션 검증(@NotBank..)
			- Errors::rejectValue("필드명", "에러코드")
			
		- global 필드이면 Global Error 출력
		
	2. #fields.globalErrors(..) : Global Error 출력 용도
	 - Errors::reject(에러코드)
	
	

	참고)
		#이 붙어있는 경우 -> 식객체(내장객체)
		<form:errors path=".." />

		타임리프 페이지 레이아웃

		th:replace : 템플릿 파일 치환 
		th:fragment 


th:field="*{속성명}"

	<form method="post" autocomplete="off" th:object="${requestJoin}">
		<dl>
			<dt th:text="#{이메일2}">이메일</dt>
			<dd>
				<input type="text" name="email" th:field="*{email}">
			</dd>
		</dl>
		<dl>
			<dt>비밀번호</dt>
			<dd><input type="password" name="password" th:field="*{password}"> </dd>
		</dl>
		<dl>
			<dt>비밀번호확인</dt>
			<dd>
				<input type="password" name="confirmPassword" th:field="*{confirmPassword}">
			</dd>
		</dl>
		<dl>
			<dt>회원명</dt>
			<dd><input type="text" name="userNamer" th:field="*{userName}"></dd>
		</dl>
		<div>
			<input type="checkbox" name="agree" id="agree" value="true" th:field="*{agree}">
			<label for="agree">회원가입 약관에 동의합니다.</label>
		</div>
		<button type="submit">가입하기</button>
	</form>