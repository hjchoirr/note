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
	
2) 메세지 식 : #{메세지 코드}

	참고)
		fmt:setBundle
			<fmt:message key="메세지 코드">
			
			
3) 링크 식 : @{링크}
	- 컨텍스트 경로 추가 
	-  URL 변수 식, 요청 파라미터 쉽게 추가 
	
	참고)
		<c:url value="...." />

4) 선택 변수식 
	th:object="${객체}"
		*{속성명}


th:block 태그 -> 번역되면 삭제 
				  -> 기능만 필요한 경우 

2. 타임리프 식 객체
1) #strings
2) #numbers
3) #dates, #calendars, #temporals : (java 8, java.time 패키지)
4) #lists, #sets, #maps
-> 내장 식객체에 없는 기능? 스프링 빈으로 생성 

${@빈이름.메서드명(...)}


3. th:text	
	- 문자열 출력 (문자열만!! - HTML 태그는 해석 X)
	- th:utext - HTML 태그도 해석 O
	
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
		
5. th:if, th:unless 
	th:if : 조건식 
	th:if="${....}"
	
	th:unless="${...}" : 조건식이 false -> 노출, true -> 노출 X
	
	
	true, false -> 상수로 바로 인식

7. th:switch, th:case 
	
7. th:href
	th:src 
	th:action 
	
8. th:object 

스프링 MVC 폼과 에러 메시지 연동
1. #fields.errors(..)
2. #fields.globalErrors(..)


타임리프 페이지 레이아웃

th:replace : 템플릿 파일 치환 
th:fragment 
