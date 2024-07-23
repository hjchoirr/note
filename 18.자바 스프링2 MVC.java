Mybatis 활용하기
7/10 day04  


스프링 MVC 시작하기

	의존성
	- servlet-api
	- servlet.jsp-api
	- jstl-impl
	- jstl 구현체
	
	-> spring webmvc : 위의 4가지 모두 포함됨
	 implementation 'org.springframework:spring-webmvc:6.1.10'
		-> 변경 implementation "org.springframework:spring-webmvc:$springVersion"
		   context... 지우기 ( 포함됨)

	jakarta servlet api compileOnly 'jakarta.servlet:jakarta.servlet-api:6.0.0'

	Jakarta Server Pages API  compileOnly 'jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.1.1'
	Jakarta Standard Tag Library API  implementation 'jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.0'
	Jakarta Standard Tag Library Implementation implementation 'org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1'


		dependencies {
			implementation "org.springframework:spring-webmvc:$springVersion"

			implementation "org.springframework:spring-jdbc:$springVersion"
			implementation 'org.springframework.data:spring-data-jdbc:3.3.1'
			implementation 'org.apache.tomcat:tomcat-jdbc:10.1.25'
			runtimeOnly 'com.oracle.database.jdbc:ojdbc11:23.4.0.24.05'
			implementation 'org.mybatis:mybatis:3.5.16'
			implementation 'org.mybatis:mybatis-spring:3.0.3'
			implementation 'org.mindrot:jbcrypt:0.4'
			compileOnly 'jakarta.servlet:jakarta.servlet-api:6.0.0'
			compileOnly 'jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.1.1'
			implementation 'jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.0'
			implementation 'org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1'

			compileOnly 'org.projectlombok:lombok:1.18.34'
			annotationProcessor 'org.projectlombok:lombok:1.18.34'
			implementation 'org.slf4j:slf4j-api:2.0.13'
			implementation 'ch.qos.logback:logback-classic:1.5.6'

			testImplementation "org.springframework:spring-test:$springVersion"
			testImplementation platform('org.junit:junit-bom:5.10.0')
			testImplementation 'org.junit.jupiter:junit-jupiter'

		}

		main/webapp/WEB-INF/ 만들면 톰캣 서버 설정 가능해짐
		
		web.xml -> 아파치에서 namespace 가져오기
			
			<?xml version="1.0" encoding="UTF-8" ?>
			<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
					 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
					 xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
								  https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
					 version="6.0">
				<servlet>
					<servlet-name>dispatcher</servlet-name>
					<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
					<init-param>
						<param-name>contextClass</param-name>
						<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
					</init-param>
					<init-param>
						<param-name>contextConfigLocation</param-name>
						<param-value>
							config.MvcConfig
							config.ControllerConfig
						</param-value>
					</init-param>
				</servlet>
				<servlet-mapping>
					<servlet-name>dispatcher</servlet-name>
					<url-pattern>/</url-pattern>
				</servlet-mapping>
				<filter>
					<filter-name>encodingFilter</filter-name>  //<!-- spring 4까지는 인코딩 필수 -->
					<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
					<init-param>
						<param-name>encoding</param-name>
						<param-value>UTF-8</param-value>
					</init-param>
				</filter>
				<filter-mapping>
					<filter-name>encodingFilter</filter-name>
					<url-pattern>/*</url-pattern>  ...*/
				</filter-mapping>
			</web-app>
			
			package configs;
			@Configuration
			@EnableWebMvc
			@ComponentScan("member")
			@Import(DBConfig.class)
			public class MvcConfig implements WebMvcConfigurer {
				@Override
				public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
					configurer.enable();  // 디폴트경로 "/" 에 들어왔을때 
				}

				@Override
				public void configureViewResolvers(ViewResolverRegistry registry) {
					registry.jsp("/WEB-INF/templates/", ".jsp");
				}
			}

			package configs;
			import org.apache.ibatis.session.SqlSessionFactory;
			import org.apache.tomcat.jdbc.pool.DataSource;
			import org.mybatis.spring.SqlSessionFactoryBean;
			import org.mybatis.spring.annotation.MapperScan;
			import org.springframework.context.annotation.Bean;
			import org.springframework.context.annotation.Configuration;
			import org.springframework.jdbc.core.JdbcTemplate;
			import org.springframework.jdbc.datasource.DataSourceTransactionManager;
			import org.springframework.transaction.PlatformTransactionManager;
			import org.springframework.transaction.annotation.EnableTransactionManagement;

			@Configuration
			@MapperScan("mappers")
			@EnableTransactionManagement
			public class DBConfig {

				@Bean(destroyMethod = "close")
				public DataSource dataSource() {
					DataSource ds = new DataSource();
					ds.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
					ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
					ds.setUsername("SPRING");
					ds.setPassword("oracle");

					ds.setTestWhileIdle(true); //유휴 객체 유효성 체크
					ds.setInitialSize(2);
					ds.setMaxActive(10);
					ds.setTimeBetweenEvictionRunsMillis(10 * 1000); //10초마다 연결 상태 체크
					ds.setMinEvictableIdleTimeMillis(60 * 1000); //유휴객체 생존시간

					return ds;
				}

				@Bean
				public JdbcTemplate jdbcTemplate() {
					return new JdbcTemplate(dataSource());
				}

				@Bean
				public PlatformTransactionManager transactionManager() {
					DataSourceTransactionManager tm = new DataSourceTransactionManager();
					tm.setDataSource(dataSource());
					return tm;
				}
				@Bean
				public SqlSessionFactory sqlSessionFactory() throws Exception {
					SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
					sqlSessionFactoryBean.setDataSource(dataSource());

					SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
					return sqlSessionFactory;
				}

			}		
			
			@Controller
			public class MemberController {

				@Autowired
				private HttpServletRequest request;

				@Autowired
				private HttpServletResponse response;

				@Autowired
				private HttpSession session;

				@GetMapping("/member/join")
				public String join(@RequestParam(name="name", required=false, defaultValue = "기본값") String name) {
					System.out.println("name:" + name);
					return "member/join";
				}

				@PostMapping("/member/join")
				public String joinPs(RequestJoin form, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, Errors errors) {

					System.out.println("form:" + form);
					return "member/join";
				}
			}

	1. 스프링 MVC를 위한 설정
		1) 컨트롤러 구현
		2) JSP 구현

	2. 스프링 MVC 프레임워크 동작 방식
		요청(/hello) -> DispatcherServlet -> HandlerMapping -> 컨트롤러 빈(스프링 컨테이너) -> HandlerAdapter -> 컨트롤러 빈 -> 실행 -> ModelAndView
		HandlerAdapter : 컨트롤러 빈의 종류가 다양하기 때문에 맞춰서 실행하기 위한 목적 
							   @Controller, Controller 인터페이스의 구현체, HttpRequestHandler 인터페이스 구현체


	ModelAndView :
		1) Model : 데이터 (EL 속성으로 추가된 데이터)
		2) View : 출력 템플릿 경로 정보 

	3. WebMvcConfigurer 인터페이스와 설정

	4. 정리
		1) DispatcherServlet 	
			: 요청과 응답의 창구 역할을 하는 서블릿 클래스 
			- 스프링 컨테이너 생성 
			
		2) HandlerMapping	
			: 요청 방식 + 주소 -> 스프링 컨테이너에 있는 컨트롤러 빈을 검색
			
		3) HandlerAdapter 			
			: 형태가 다양한 컨트롤러 빈(@Controller, Controller 인터페이스, HttpRequestHandler 인터페이스) -> 실행 -> ModelAndView로 반환 
			
			참고) ModelAndView 
						- addObject(String name, String value) : EL 속성으로 추가되는 속성 
- setViewName(...) : 뷰 경로 
			
			요청메서드의 반환값이 String 이미지만 -> HandlerAdpter에서 실행시 ModelAndView 객체로 변환
			
		4) ViewResolver
			: ModelAndView 정보 -> 출력을 위한 View 객체 검색 
			
			
	5. 요청 처리에 대한 편의 기능 제공 
		1) 요청 데이터의 이름과 동일한 매개변수를 요청 메서드에 정의하면 자동으로 주입 
		2) 정의한 변수의 자료형으로 자동 형변환 
		3) 요청 데이터의 이름과 요청 메서드에 정의한 이름이 다른 경우
			@RequestParam("요청 데이터의 이름")
				- required : true(기본값) : 요청 파라미터의 필수
				
				
		요청 데이터 
			GET : ?이름=값&이름=값
			POST : 요청 바디 이름=값&이름=값 
			
			HttpServletRequest 
				String getParameter(String name)
				String[] getParameterValues(String name);

			커맨드 객체


7/11 어제꺼 복습

	스프링mvc 의존성
	
		spring webmvc  
		servlet-api			jakarta servlet api 6.0
		servlet.jsp-api		Jakarta Servlet JSP
		
		jstl-api 			Jakarta Standard Tag Library API
		jstl-impl			glassfish jstl implement
		
		tomcat-jdbc
		spring-jdbc
		spring-data jdbc ( 연습위함 -> 나중엔 JPA 사용 예정)
		ojdbc11
		
		mybatis
		mybatis-spring
		
		lombok
		
		slf4j-api
		logback classic
		
		spring-test
		
		
	main/java/org/choongang 
	main/resource/org/choongang 
	main/webapp/WEB-INF/templates/
	main/webapp/WEB-INF/web.xml
	
	
	CrudRepository 인터페이스
	
	repositories  : 스프링에서 DAO(매퍼) 대신		
		
		
		
	Spring 쿼리메서드 
	  :  @Query 에노테이션
	
	페이징 Pageable  인터페이스
	PageRequest 구현클래스  PageRequest.of(페이지번호, 페이지사이즈) : 페이지번호 0페이지 부터
	
		Page 자료형

			@Configuration
			@EnableTransactionManagement
			@MapperScan("org.choongang")
			@EnableJdbcRepositories("org.choongang")
			public class DBConfig extends AbstractJdbcConfiguration {
				@Bean(destroyMethod = "close")
				public DataSource dataSource()  {
					DataSource ds = new DataSource();
					ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
					ds.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
					ds.setUsername("SPRING");
					ds.setPassword("oracle");
					ds.setInitialSize(2);
					ds.setMaxActive(10);
					ds.setMaxIdle(10);
					ds.setTestWhileIdle(true);
					ds.setMinEvictableIdleTimeMillis(60 * 1000); // default 60 sec
					ds.setTimeBetweenEvictionRunsMillis(5 * 1000); // default 5 sec
					return ds;
				}

				@Bean
				public JdbcTemplate jdbcTemplate()  {   // 마이바티스만 쓸땐 이거 필요 없음
					return new JdbcTemplate(dataSource());
				}
				@Bean
				public PlatformTransactionManager transactionManager()  {
					return new DataSourceTransactionManager(dataSource());
				}

				@Bean
				public SqlSessionFactory sqlSessionFactory() throws Exception {  // 마이바티스 쓸때 필요함
					SqlSessionFactoryBean sqlsessionFactoryBean = new SqlSessionFactoryBean();
					sqlsessionFactoryBean.setDataSource(dataSource());
					return sqlsessionFactoryBean.getObject();
				}

				@Bean
				public NamedParameterJdbcOperations namedParameterJdbcTemplate(DataSource dataSource)  {
					return new NamedParameterJdbcTemplate(dataSource);
				}
			}	
					
			@Configuration
			@EnableWebMvc
			@ComponentScan("org.choongang")
			@Import(DBConfig.class)
			public class MvcConfig implements WebMvcConfigurer {
				@Override
				public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
					configurer.enable();
				}

				@Override
				public void configureViewResolvers(ViewResolverRegistry registry) {
					registry.jsp("/WEB-INF/templates/", ".jsp");
				}
			}

			@Data
			@Builder
			@NoArgsConstructor 
			@AllArgsConstructor
			public class Member {
				@Id
				private Long seq;
				private String email;
				private String password;
				private String userName;
				private LocalDateTime regDt;
			}
					
			public interface MemberRepository extends CrudRepository<Member, Long> {
				Member findByEmail(String email);
				//List<Member> findByUserNameContaining(String keyword);
				Page<Member> findByUserNameContaining(String keyword, Pageable pageable);
				List<Member> findByUserNameAndEmailContainingOrderByRegDt(String key1, String key2);

				@Query("select * from member where user_name like :param1 and email like :param2 order by reg_dt desc")
				List<Member> getMembers(@Param("param1") String key1, @Param("param2") String key2);

			}

			@SpringJUnitWebConfig
			@ContextConfiguration(classes = MvcConfig.class)
			public class MemberRepositoryTest {

				@Autowired
				private MemberRepository repository;

				@Test
				void test1() {
					List<Member> members = (List<Member>) repository.findAll();
					members.forEach(System.out::println);
				}

				@Test
				void test2() {
					Member member = Member.builder()
						.seq(23L)
						.email("user06@test.com")
						.password("12345")
						.userName("사용자06")
						.build();
					repository.save(member);
				}

				@Test
				void test3() {
					Member member = repository.findById(1L).orElse(null);
					System.out.println(member);
				}

				@Test
				void test4() {
					Member member = repository.findByEmail("user06@test.com");
					System.out.println(member);
				}

				@Test
				void test5() {
					Pageable pageable = PageRequest.of(0,3);
					Page<Member> members = repository.findByUserNameContaining("사용자", pageable);
					members.forEach(System.out::println);
				}
				@Test
				void test6() {
					List<Member> members = repository.findByUserNameAndEmailContainingOrderByRegDt("사용자", "user");
					members.forEach(System.out::println);

				}
				@Test
				void test7() {
					List<Member> members = repository.getMembers("%용자%", "%user%");
					members.forEach(System.out::println);
				}
				@Test
				void test8() {
					//Pageable pageable = PageRequest.of(1,3);
					//Pageable pageable = PageRequest.of(0,3, Sort.by(Sort.Order.desc("regDt")));
					Pageable pageable = PageRequest.of(0,3, Sort.by(desc("regDt"), asc("email")));
					Page<Member> data = repository.findByUserNameContaining("사용", pageable);

					List<Member> members = data.getContent();

					long total = data.getTotalElements();
					int pages = data.getTotalPages();

					members.forEach(System.out::println);
					System.out.printf("total=%d pages=%d\n", total, pages);

				}
			}


			=====================================================
			@Data
			@Builder
			@NoArgsConstructor   // repository 사용시 필요함
			@AllArgsConstructor  // repository 사용시 필요함
			@Table("CH_MEMBER")   // 테이블명이 클래스명과 다를 때
			public class Member {
				@Id
				@Column("ID")   // 컬럼명이 다를 때
				private Long seq;
				private String email;
				private String password;
				private String userName;
				private LocalDateTime regDt;
			}
			=====================================================



DispatcherServlet   스프링 컨테이너 

	-> HandlerMapping : 요청 url에 맞는 컨트롤러 찾기
	-> HandlerAdapter : 컨트롤러 실행결과를 ModelAndView로 변환해서 리턴
	-> ViewResolver : 컨트롤러의 실행결과를 가지고 view 검색
	
	
	
				
Model - request 범위


	addAttribute(키, 값)
	addAllAttribute(Map ...)
	
	Attribute : 속성
	
	ModelAndView 에서 Model 은 View에서 보여줄 데이터
	
		@Controller
		public class MemberController {
			
			@GetMapping("/member/join")
			public String join(Model model){
				model.addAttribute("message", "안녕하세요?");
				return "member/join";
			}
		}

		//위 아래 동일함

		@Controller
		public class MemberController {
		
			@GetMapping("member/join")
			public ModelAndView join() {
				ModelAndView mv = new ModelAndView();
				mv.addObject("message", "안녕하세요");
				mv.setViewName("member/join");
				return mv;
			}
		}	
	
	
	정적 경로 (Ant 패턴)
	
		@Configuration
		@EnableWebMvc
		@ComponentScan("org.choongang")
		@Import(DBConfig.class)
		public class MvcConfig implements WebMvcConfigurer {
			@Override
			public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
				configurer.enable();
			}

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");  // 정적 경로 설정 
			}

			@Override
			public void configureViewResolvers(ViewResolverRegistry registry) {
				registry.jsp("/WEB-INF/templates/", ".jsp");
			}
		}	
	
	**  예) /board/** -> /board 경로의 모든 파일과 하위 경로 포함 모든 파일 */
			/board/*  -> /board 경로의 모든 파일 ( 하위 경로 포함하지 않음 ) */
			
	?  : 문자 1개  /m?01   
	
7/12 day05 계속	

스프링 MVC : 요청 매핑, 커맨드 객체, 리다이렉트, 폼 태그, 모델

1. 요청 매핑 애노테이션을 이용한 경로 매핑
	- GET, DELETE
	- POST, PATCH, PUT

	@RequestMapping : 모든 요청 매서드에 매핑, method 설정에 GET POST DELETE 등 설정 가능
	
	(spring 4.3)
	@GetMapping
	@PostMapping
	@PatchMapping
	@PutMapping
	@DeleteMapping


		@Controller
		@RequestMapping
		public class MemberController {

			@RequestMapping(path="/member/join", method = {RequestMethod.GET, RequestMethod.POST})
			public String join(Model model, HttpServletRequest request){
				model.addAttribute("message", "안녕하세요?");

				System.out.println("method : " + request.getMethod());
				return "member/join";
			}
		}



		@Controller
		@RequestMapping("/member")
		public class MemberController {

			@GetMapping("/join")
			public String join(Model model, HttpServletRequest request){
				return "member/join";
			}
			
			@PostMapping("/join")
			public String joinPs(RequestJoin form) {
				
				return "redirect:/member/login";
			}
		}
		
		
		
	2. 요청 파라미터 접근
		params : 오청 파리메터를 한정하여 매핑
		headers : 요청헤더 데이터 체크
		consumes : 요청쪽 content-Type 체크
		produces : 응답 헤더쪽 content-Type 설정
	
		@Controller
		@RequestMapping("/member")
		public class MemberController {

			@GetMapping(path="/join", params={"mode=join"})  // request parameter mode = join  있어야먼 유입됨
			public String join(Model model, HttpServletRequest request){

				System.out.println("mode=join");

				return "member/join";
			}
		}
	
		@Controller
		@RequestMapping("/member")
		public class MemberController {

			@GetMapping("/join")  // 파라미터 없을때는 여기로 유입됨
			public String joinNoParam(Model model, HttpServletRequest request){

				System.out.println("mode없음");

				return "member/join";
			}

			@GetMapping(path="/join", params={"mode=join"}) // mode=join 있으면 여기로 매핑됨
			public String join(Model model, HttpServletRequest request){

				System.out.println("mode=join");

				return "member/join";
			}
		}
		
		
		(참고) 로거 사용
		
		private final Logger log = LoggerFactory.getLogger(MemberController.class);  //slf4 import
		
		또는  @Slf4j(롬복) 사용
		
			log.fatal(..)
			log.error(..)
			log.warn(..)
			log.info(..)
			log.debug(..)
			log.trace(..)
		
			lombok 활용시 다음 에너테이션 사용 하면 로거 연동 코드 자동 생성 변수명은 log
			@slf4j 
			@log4j 
			@log4j2  
	
		통합테스트
		MockMvc - 컨트롤러 테스트
		
			@Slf4j
			@Controller
			@RequestMapping("/member")
			public class MemberController {
			 
				//@PostMapping(path="/join", headers="appKey=1234", consumes = "application/json") //요청헤더 인증키, content-Type:json 인 것만 응답함
				@PostMapping(path="/join", headers="appKey=1234", consumes = MediaType.APPLICATION_JSON_VALUE) //요청헤더 인증키, content-Type:json 인 것만 응답함
				public String joinPs(RequestJoin form) {
					log.info("joinPs 실행..");
					return "redirect:/member/login";
				}
			}		

			@SpringJUnitWebConfig
			@ContextConfiguration(classes = MvcConfig.class)
			public class MemberControllerTest {

				@Autowired
				private WebApplicationContext ctx;
				private MockMvc mockMvc;


				@BeforeEach
				void init() {
					mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
				}

				@Test
				void test1() throws Exception{
					mockMvc.perform(
						post("/member/join")
							.header("appKey", "1234") //요청헤더
							//.contentType("application/json")
							.contentType(MediaType.APPLICATION_JSON_VALUE)
						).andDo(print());
				}

			}
			
		================================================================
		@PostMapping("/join") 
		public String joinPs(RequestJoin form){
			// redirect: 스프링 문법(앞에 contextPath 붙여서..) 응답헤더의 Location 에.. 페이지 이동
			return "redirect:/member/login";     setting하여 처리됨 
			
		}
	
	반환값 
	  : redirect : 주소 - 헤더 location에 ..
	  : forward  : 주소 - 주소의 출력 데이터로 버퍼를 치환
	
			
		1) @RequestParam 
		2) 커맨드 객체를 이용해서 요청 파라미터 사용하기
			커맨드 객체 클래스명 -> EL 속성으로 자동 추가
			예) RequestJoin -> requestJoin
			
				@Slf4j
				@Controller
				@RequestMapping("/member")
				public class MemberController {

					@GetMapping("/join")
					public String join(Model model) { //

						RequestJoin form = new RequestJoin();
						model.addAttribute("requestJoin", form); //join.jsp 에 form에서 커맨드 객체 requestJoin  사용하므로 빈거라도 있어야
						return "member/join";
					}
					@PostMapping("/join")
					public String joinPs(RequestJoin form){

						log.info(form.toString());
						return "member/join";
					}
				}
				<%@ page contentType="text/html; charset=UTF-8" %>
				<%@ taglib prefix="c" uri="jakarta.tags.core"%>
				<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
				<c:url var="actionUrl" value="/member/join" />
				<h1>회원가입</h1>
				${requestJoin}
				<form:form method="post" action="${actionUrl}" autocomplete="off" modelAttribute="requestJoin">
					<dl>
						<dt>이메일</dt>
						<dd><form:input path="email" /></dd>
					</dl>
					<dl>
						<dt>약관동의</dt>
						<dd>
							<form:checkbox path="agree" value="true" label="회원가입 약관에 동의합니다"/>
						</dd>
					</dl>
					<button type="submit">가입 하기</button>
				</form:form>

	3. 뷰 JSP 코드에서 커맨드 객체 사용하기

	4. @ModelAttribute 애노테이션으로 커맨드 객체 속성 이름 변경
	
		1) 커맨드객체가 없으면 빈 커맨드 객체 생성
		2) 커맨드객체 속성이름 변경
		
		
		    @GetMapping("/join")
			public String join(@ModelAttribute RequestJoin form) {

				return "member/join";
			}
			
		3) 공통데이터(속성) 으로 사용할 데이터 지정
			Controller, RestController
			ControllerAdvice, RestControllerAdvice - 지정된 패키지
			
			@Slf4j
			@Controller
			@RequestMapping("/member")
			public class MemberController {			
	
				@ModelAttribute("commonValue")    // ****
				public String commonValue() {
					return "공통 속성값..";
				}
				@GetMapping("/join")
				public String join(@ModelAttribute RequestJoin form) {

					return "member/join";
				}

				@PostMapping("/join")
				public String joinPs(RequestJoin form){

					log.info(form.toString());
					return "member/join";
				}
			}

	5. 커맨드 객체와 스프링 폼 연동

		1) <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
		2) <form:form>
		3) <form:input>
		4) <form:password> 


	6. 컨트롤러 구현 없는 경로 매핑
		- 컨트롤러 구성을 할 필요 없는 간단한 페이지 구성 시
		WebMvcConfigurer
		- addViewControllers 설정
			@Configuration
			@EnableWebMvc
			@ComponentScan("org.choongang")
			@Import(DBConfig.class)
			public class MvcConfig implements WebMvcConfigurer {

				@Override
				public void addViewControllers(ViewControllerRegistry registry) {
					registry.addViewController("/").setViewName("main/index"); 
					registry.addViewController("/mypage/**").setViewName("mypage/index");  */
				}
			}		
			
		- Spring6(jdk17 이상) 
		- 커맨드 객체에서 사용가능한 거 또 Record(VO) 클래스  
		
			public record RequestLogin2(
				String email,
				String password
			) {}		

	7. 커맨드 객체 : 중첩 · 콜렉션 프로퍼티

	4. 체크박스 관련 커스텀 태그 : <form:checkboxes>, <form:checkbox>
	
			@Data
			public class RequestJoin {
				private String email;
				private String password;
				private String confirmPassword;
				private String userName;
				//private String[] hobby;
				//private Set<String> hobby;
				private List<String> hobby;
				//private String hobby;
				private boolean agree;
			}

			@ModelAttribute("hobbies")
			public List<String> hobbies() {
				return List.of("취미1", "취미2", "취미3", "취미4");
			}
			
			<dl>
				<dt>취미</dt>
				<dd><form:checkboxes path="hobby" items="${hobbies}"/></dd>
			</dl>			
		
		중첩 : 커맨드객체 안의 커맨드 객체
		
			@Data
			public class Address {
				private String zipCode;
				private String address;
				private String addressSub;

			}

			@Data
			public class RequestJoin {
				private String email;
				private String password;
				private String confirmPassword;
				private String userName;
				private List<String> hobby;
				private boolean agree;

				private Address addr;
			}
			<dl>
				<dt>주소</dt>
				<dd>
					<form:input path="addr.zipCode" placeholder="우편번호"/>
					<form:input path="addr.address" placeholder="주소"/>
					<form:input path="addr.addressSub" placeholder="나머지 주소"/>
				</dd>
			</dl>


	8. Model을 통해 컨트롤러에서 뷰에 데이터 전달하기

	9. ModelAndView를 통한 뷰 선택과 모델 전달

	10. GET 방식과 POST 방식에 동일 이름 커맨드 객체 사용하기


주요 폼 태그 설명

	1. <form> 태그를 위한 커스텀 태그: <form:form>
	
	2. <input> 관련 커스텀 태그 : <form:input>, <form:password>, <form:hidden>
	
	3. <select> 관련 커스텀 태그 : <form:select>, <form:options>, <form:option>
			<dl>
				<dt>취미</dt>
				<dd><form:select path="hobby" items="${hobbies}" /></dd>

			</dl>	
			<dl>
				<dt>취미</dt>
				<dd>
					<form:select path="hobby">
						<option value="">선택하세요</option>
						<form:options items="${hobbies}"/>
					</form:select>
				</dd>			
			</dl>	
	
	
	    @ModelAttribute("hobbies2")
		public List<CodeValue> hobbies2() {
			return List.of(
					new CodeValue("취미1", "hobby1"),
					new CodeValue("취미2", "hobby2"),
					new CodeValue("취미3", "hobby3"),
					new CodeValue("취미4", "hobby4")
			);
		}

        <dt>취미</dt>
        <dd>
            <form:radiobuttons path="hobby" items="${hobbies}"/>
        </dd>

        <dd>
            <form:select path="hobby">
                <option value="">선택하세요</option>
                <form:options items="${hobbies2}" itemLabel="code" itemValue="value"/>
            </form:select>
        </dd>
        <dd>
            <form:select path="hobby">
                <option value="">선택하세요</option>
                <form:options items="${hobbies}"/>
            </form:select>
        </dd>
            <dd><form:select path="hobby" items="${hobbies}" /></dd>
            <dd><form:checkboxes path="hobby" items="${hobbies}"/></dd>

	5. 라디오버튼 관련 커스텀 태그: <form:radiobuttons>, <form:radiobutton>
	
	6. <textarea〉 태그를 위한 커스텀 태그 : <form:textarea>


CSS 및 HTML 태그와 관련된 공통 속성

	1. cssClass: HTML의 class 속성값
	2. cssErrorClass : 폼 검증 에러가 발생했을 때 사용할 HTML의 class 속성값
	3. cssStyle: HTML의 style 속성값

HTML 태그가 사용하는 다음 속성도 사용 가능하다.

	1. id, title, dir
	2. disabled, tabindex
	3. onfocus, onblur, onchange onclick, ondblclick
	4. onkeydown, onkeypress, onkeyup
	5. onmousedown, onmousemove, onmouseup
	6. onmouseout, onmouseover


각 커스텀 태그는 htmlEscape 속성을 사용해서 커맨드 객체의 값에 포함된 HTML 특수 문자를 엔티티 레퍼런스로 변환할지를 결정할 수 있다.


스프링 MVC 

	1. 메시지
	
		1) MessageSource
			- Bean으로 등록
			- Bean 이름 반드시 MessageSource
			
		2) ResourceBundleMessageSource
		3) 다국어 지원 위한 메시지 파일

			@Configuration
			public class MessageConfig {
				@Bean
				public MessageSource messageSource() {
					ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
					ms.setBasenames("messages.commons");
					ms.setDefaultEncoding("UTF-8");
					ms.setUseCodeAsDefaultMessage(true); //메세지 코드가 없는 경우 코드로 메세지 대체
					return ms;
				}
			}
			
			<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

			<spring:message code="LOGIN_MSG" arguments="사용자01,USER01"/>
			<spring:message code="LOGIN_MSG">
				<spring:argument value="사용자01" />
				<spring:argument value="User01" />
			</spring:message>
			<h1><spring:message code="회원가입"/></h1>

			<form:form method="post" action="${actionUrl}" autocomplete="off" modelAttribute="requestJoin" >
				<dl>
					<dt><spring:message code="이메일"/></dt>
					<dd><form:input path="email" cssClass="input-txt" /></dd>
				</dl>
				<dl>
					<dt><spring:message code="비밀번호"/></dt>
					<dd><form:password path="password" /></dd>
				</dl>
			</form:form>

			@GetMapping("/join")
			public String join(@ModelAttribute RequestJoin form) {
				Locale locale = request.getLocale(); //브라우저에 설정된 언어 : 요청헤더 Accept-Language
				String message = messageSource.getMessage("EMAIL", null, locale);

				log.info(message);
				return "member/join";
			}

			@SpringJUnitWebConfig
			@ContextConfiguration(classes = MvcConfig.class)
			public class MessageSourceTest {

				@Autowired
				private MessageSource messageSource;

				@Test
				void test1() {
					//String message = messageSource.getMessage("LOGIN_MSG", new Object[] {"사용자01", "usEr01"}, Locale.KOREAN);
					String message = messageSource.getMessage("LOGIN_MSG", new Object[] {"사용자01", "usEr01"}, Locale.ENGLISH);
					System.out.println(message);
				}

				@Test
				void test2() {
					String message = messageSource.getMessage("EMAIL", null, Locale.KOREAN);
					System.out.println(message);
				}
			}


	2. 커맨드 객체 검증
	
		1) Validator 인터페이스 
		
			- supports(..) : 검증하는 커맨드 객체 한정 설정
			
			- validate(Object target, Errors errors) : 커맨드 객체 검증..
			  Object target : 커맨드 객체 -> 형변환
			  Errors errors : 검증 실패시 전달한 메세지 등록
			
		2) Errors
		
			- 커맨드 객체 자체 오류에 대한 처리 --
			
			  reject("에러코드");
			  reject("에러코드", "기본 메세지")
			
			- 커맨드 객체의 특정 필드 오류에 대한 처리 --
			
			  rejectValue("필드명", "에러코드");
			  rejectValue("필드명", "에러코드", "기본메세지");
			
			
			-
			  hasErrors() : 한개라도 reject 또는 rejectValue가 호출되면 true

			(참고) spring utils
			  - StringUtils.hasText(...)
			
			타임리프
			 #fields.errors("필드명") : -> errors 객체 담긴 메세지를 필드명으로 조회 -> 배열 
			 
			 <form:errors path="필드명" />
			  - 기본 에러출력 태그 span, 여러 에러메시지가 있는 경우 <br> 구부(delimiter)
			  - element="태그명"
			  - delimiter="구분자명"
			  
				<dd>
					<form:input path="email" />
					<form:errors path="email" element="div" delimiter=""/>
				</dd>			  
			 
		3) ValidationUtils
		
			- 필수 항목 검증에 편의 메서드
			- rejectIfEmpty(...)  : null 또는 ''
			- rejectIfEmptyOrWhitespace(...) : 공백 포함 체크
			


	3. 에러 코드에 해당하는 메시지 코드를 찾는 규칙
	
		- 에러코드 + "." + 커맨드객체이름 + "." + 필드명
		- 에러코드 + "." + 필드명
		- 에러코드 + "." + 필드타입
		- 에러코드

			--------------------------------------
			messages.validations.peoperties 파일
			--------------------------------------
			Required=필수 입력 항목 입니다.

			Required.mail=이메일을 입력하세요.
			Required.password=비밃번호를 입력하세요.
			Required.confirmPassword=비밀번호 확인을 입력하세요
			Required.userName=사용자명을 입력하세요

			Required.requestJoin.agree=회원가입 약관에 동의하세요 //	requestJoin 커맨드 객체로 범위를 한정함
			--------------------------------------

	4. 프로퍼티 타입이 List나 목록인 경우 다음 순서를 사용해서 메시지 코드를 생성한다.

		에러코드 + "." + 커맨드객체이름 + "." + 필드명[인덱스].중첩필드명
		에러코드 + "." + 커맨드객체이름 + "." + 필드명.중첩필드명
		에러코드 + "." + 필드명[인덱스].중첩필드명
		에러코드 + "." + 필드명.중첩필드명
		에러코드 + "." + 중첩필드명
		에러코드 + "." + 필드타입
		에러코드

	
			@Data
			public class RequestJoin {
				@NotBlank(message = "이메일을 입력하세요")
				@Email(message = "이메일 형식이 아닙니다")
				private String email;

				@NotBlank
				@Size(min=8)
				private String password;

				@NotBlank
				private String confirmPassword;

				@NotBlank
				private String userName;

				@AssertTrue
				private boolean agree;

			}

			@PostMapping("join")
			public String joinPs(@Valid RequestJoin form, Errors errors) { // Valid 커맨드객체 바로 뒤에 에러객체 : 중요

				joinValidator.validate(form, errors);
			}

			-----------------------------------
			validations.properties
			-----------------------------------
			Email=이메일 형식이 아닙니다
			Mismatch.confirmPassword=비밀번호가 일치하지 않습니다.
			Duplicated.requestJoin.email=이미 가입된 회원입니다
			NotBlank=필수 입력 항목 입니다
			NotBlank.email=이메일을 입력하세요
			NotBlank.password=비밀번호를 입력하세요
			NotBlank.confirmPassword=비밀번호를 확인하세요
			NotBlank.userName=회원명을 입력하세요
			AssertTrue.requestJoin.agree=회원가입 약관에 동의합니다
			Size.requestJoin.password=비밀번호는 8자리 이상 입력하세요
			-----------------------------------


	5. 글로벌 범위 Validator와 컨트롤러 범위 Validator

		1) 글로벌 범위 Validator 설정과 @Valid 애노테이션
		
			-  WebMvcConfigurer의 getValidalor() 
			  : 모든 컨트롤러의 공통적인 검증이 필요한 경우 - MvcConfig 에 추가
			  
			  
				@Configuration
				@EnableWebMvc
				@ComponentScan("org.choongang")
				@Import({DBConfig.class, MessageConfig.class})
				@RequiredArgsConstructor
				public class MvcConfig implements WebMvcConfigurer {
					
					private final JoinValidator joinValidator;

					@Override
					public Validator getValidator() {
						return joinValidator;  //모든 컨트롤러에 적용될 수 있는 전역 validator 
					}
				}



			
		2) @InitBinder 애노테이션을 이용한 컨트롤러 범위 Validator
		
			@InitBinder
			protected void InitBinder(WebDataBinder binder) {
				binder.setValidator(new RegisterRequestValidator());
			}
			
			- 특정 컨트롤러에서 사용할 공통적인 Validator
			
				@Controller
				@RequestMapping("/member")
				@RequiredArgsConstructor
				public class MemberController {
					private final JoinValidator joinValidator;

					@GetMapping("join")
					public String join(@ModelAttribute RequestJoin form) {
						return "member/join";
					}

					@PostMapping("join")
					public String joinPs(@Valid RequestJoin form, Errors errors) { // Valid 커맨드객체 바로 뒤에 에러객체 : 중요

						//joinValidator.validate(form, errors); //이거 없이... 가능함

						if(errors.hasErrors()) { //reject, rejectValue 가 한번이라도 호출되면 true
							return "member/join";
						}
						return "redirect:/member/login";
					}

					@InitBinder   // 특정 컨트롤러에서 사용할 공통적인 Validator 
					public void initBinder(WebDataBinder binder) {
						binder.setValidator(joinValidator);
					}
				}

			
		3) 컨트롤러 범위 Validator  > 글로벌 범위 Validator

	6. Bean Validation
		Bean Validation API 
		  - Jakarta Validation API 의존성 추가
		  implementation 'jakarta.validation:jakarta.validation-api:3.1.0'  -> 3.0.2 로 바꾸기 : 아래거 의존성의 읭존성 관련..

		hibernate Validator  
		  - implementation 'org.hibernate.validator:hibernate-validator:8.0.1.Final'

		커맨드 객체 검증
		1) Bean Validation API - 에너테익션으로 기본 검증 처리

		1) 설정
		2) Bean Validation의 주요 애노테이션 
			@AssertTrue
			@AssertFalse
			@DecimalMax
			@DecimalMin
			@Max
			@Min
			@Digits
			@Size
			@Null
			@NonNull
			@Pattern

			@NotEmpty
			@NotBlank
			@Positive
			@PositiveOrZero
			@Email
			@Future
			@FutureOrPresent
			@Past
			@PastOrPresent


	1. 컨트롤러 - 
	2. 커맨드 객체 검증
	 1) Bean Validator
	 2)
	3. 


7/16 day05 project 계속

	 - 인터셉터는 주소 기준 통제
	 - 컨트롤러 어드바이스는 컨트롤러 기준의 통제
	 - 세션어트리뷰트(-> 모델어트리뷰트) 는 결국 세션을 이용하므로 범위를 벋어날때 해제해야함


스프링 MVC 
	1. 세션
		
		 - @SessionAttribute("이름")
		   : 세션값 조회 및 주입
		   
		 - @SessionAttributes("이름") 
		   : Model로 해당이름으로 속성 추가하면 세션에도 동일한 이름으로 추가
		   : 세션에 해당이름의 값이 있으면 Model에 자동 추가
		   : Magic Form - 여러 페이지의 양식
		
		   - SessionStatus : 완료상태 플래그
			    void setComplete() 
			    boolean isComplete()
				
				HttpSession
				   session.removeAttribute("이름")
				   
				   : @SessionAttributes에 지정된 이름의 세션값을 비울때
			  
			  

			@GetMapping("/login")
			public String login(@ModelAttribute RequestLogin form, @SessionAttribute(name="member", required=false) Member member) {

				if(member != null) {
					log.info(member.toString());
				}

				return "member/login";
			}
			@RequestMapping("logiout")
			public  String logout(HttpSession session) {
				session.invalidate();
				return "redirect:/member/login";
			}
			
			
			
			@Service
			@RequiredArgsConstructor
			public class LoginService {
				private final MemberMapper mapper;
				private final HttpSession session;

				public void process(String email) {
					Member member = mapper.get(email);

					if(email == null) {
						return;
					}
					session.setAttribute("member", member);
				}
			}
			@RequestMapping("logout")
			public  String logout(HttpSession session) {
				session.invalidate();
				return "redirect:/member/login";
			}

		======================================================
					
			package org.choongang.survey.controllers;

			import org.springframework.stereotype.Controller;
			import org.springframework.web.bind.annotation.*;

			@Controller
			@RequestMapping("/survey")
			@SessionAttributes("requestSurvey") // session 에 유지하기 ***
			public class SurveyController {

				@ModelAttribute // 공통 커맨드 객체  ***
				public RequestSurvey requestSurvey() {
					return new RequestSurvey();
				}

				@GetMapping("/step1")
				public String step1() { // get일때는 @ModelAttribute 명시해야함 => 공통 커맨드 객체 있으므로 빼기
					return "survey/step1";
				}

				@PostMapping("/step2")
				public String step2(RequestSurvey form) {
					return "survey/step2";
				}

				@PostMapping("/step3")
				public String step3(RequestSurvey form) {
					return "survey/step3";
				}
			}

			@Data
			public class RequestSurvey {
				private String q1;
				private String q2;
				private String q3;
				private String q4;

			}
			---step1.jsp-------------------------------------------------------
			<%@ page contentType="text/html; charset=UTF-8" %>
			<%@ taglib prefix="c" uri="jakarta.tags.core" %>
			<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
			<c:url var="actionUrl" value="/survey/step2" />
			<h1>step1.jsp</h1>
			<form:form method="post" autocomplete="off" action="${actionUrl}" modelAttribute="requestSurvey">
				질문1: <form:input path="q1" /><br>
				질문2: <form:input path="q2" /><br>
				<button type="submit">다음 설문</button>
			</form:form>
			
			---step2.jsp-------------------------------------------------------
			<%@ page contentType="text/html; charset=UTF-8" %>
			<%@ taglib prefix="c" uri="jakarta.tags.core" %>
			<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
			<c:url var="actionUrl" value="/survey/step3" />
			<h1>step2.jsp</h1>
			<form:form method="post" autocomplete="off" action="${actionUrl}" modelAttribute="requestSurvey">

				질문3: <form:input path="q3" /><br>
				질문4: <form:input path="q4" /><br>
				<button type="submit">제출 하기</button>
			</form:form>
			
			
			----step3.jsp------------------------------------------------------
			<%@ page contentType="text/html; charset=UTF-8" %>
			<%@ taglib prefix="c" uri="jakarta.tags.core" %>
			<h1>step3</h1>

			질문1 : ${requestSurvey.q1}<br>
			질문2 : ${requestSurvey.q2}<br>
			질문3 : ${requestSurvey.q3}<br>
			질문4 : ${requestSurvey.q4}<br>

		=> sessionAttribute 삭제
		
			@PostMapping("/step3")
			public String step3(RequestSurvey form, SessionStatus status, HttpSession session) {
				log.info("step3");
				log.info("form 1 : " + form.toString());
				form = (RequestSurvey) session.getAttribute("requestSurvey");
				log.info("form 2 : " + form.toString());

				session.removeAttribute("requestSurvey"); // 세션 비우기 - request

				form = (RequestSurvey) session.getAttribute("requestSurvey");
				if (form == null) {
					log.info("form is null");
				} else {
					log.info("form : " + form.toString());
				}

				return "survey/step3";
			}		
				
	


	2.인터셉터 
	
		1) HandlerInterceptor 인터페이스 구현
		
		  - boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
		    : 컨트롤러(핸들러) 객체를 실행하기 전에
			: 반환값 true, false 에 따라 컨트롤러 빈의 메서드의 실행여부 통제
		  
		  - void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception;
		    : 컨트롤러(핸들러)가 정상적으로 실행된 이후에 추가 기능을 구현할 때 
		  - void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception;
		    : 뷰가 클라이언트에 응답을 전송한 뒤에 실행 
		  
		2) WebMvcConfigurer 인터페이스의  : addInterceptors(InterceptorRegistry registry) 메서드 오버라이딩
		
		3) Ant 경로 패턴으로 인터셉터 범위를 지정
		
		  - * : 0개 또는 그 이상의 글자
		  - ** 0개 또는 그 이상의 폴더 경로
		  - ? : 1개 글자



				
			@Slf4j
			@Component
			public class MemberOnlyInterceptor implements HandlerInterceptor {
				@Override
				public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

					log.info("preHandle()");
					HttpSession session = request.getSession();

					if(session.getAttribute("member") != null) {
						return true;  //로그인 상태
					}
					//미로그인 상태
					response.sendRedirect(request.getContextPath() + "/member/login");
					return false;
				}

				@Override
				public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
					modelAndView.addObject("message", "postHandle");
					log.info("postHandle()");
				}

				@Override
				public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
					log.info("afterCompletion()");
				}
			}
			
			@Configuration
			@RequiredArgsConstructor
			public class InterceptorConfig implements WebMvcConfigurer {

				private final MemberOnlyInterceptor memberOnlyInterceptor;

				@Override
				public void addInterceptors(InterceptorRegistry registry) {
					registry.addInterceptor(memberOnlyInterceptor)
							.addPathPatterns("/mypage/**"); */
				}
			}

			@Configuration
			@EnableWebMvc
			@ComponentScan("org.choongang")
			@Import({DBConfig.class, MessageConfig.class, InterceptorConfig.class}) 
			public class MvcConfig implements WebMvcConfigurer {
				...
			}
			
			mypage/index.jsp
			
			<%@ page contentType="text/html; charset=UTF-8" %>
			<h1>마이페이지</h1>
			<h2>${message}</h2>

	3.쿠키
	
	  @CookieValue : 개별 쿠키값 조회 ( required = false 중요 ***)
	  
		@GetMapping("/login")
		public String login(@ModelAttribute RequestLogin form,
							@CookieValue(name="savedEmail", required = false) String savedEmail
							/*@SessionAttribute(name="member", required = false) Member member*/) {

			if(savedEmail != null) {
				form.setSaveEmail(true);
				form.setEmail(savedEmail);
			}

			return "member/login";
		}


스프링 MVC : 날짜 값 변환, @PathVariable, 익셉션 처리
	
	1. 날짜 값 변환
		@DateTimeFormat 
		- LocalDate, LocalTime, LocalDateTime ..
		- 형식이 일치 하지 않으면 예외 발생 
			- 메세지 코드 typeMismatch
			
		@Data
		public class MemberSearch {
			@DateTimeFormat(pattern = "yyyyMMdd")
			private LocalDate sDate;  //검색 시작일

			@DateTimeFormat(pattern = "yyyyMMdd")
			private LocalDate eDate;  //검색 종료일
		}	
			
		@GetMapping("/list")
		public String list(@ModelAttribute MemberSearch memberSearch) {
			log.info(memberSearch.toString());
			return "member/list";
		}



		<%@ page contentType="text/html; charset=UTF-8" %>
		<%@ taglib prefix="c" uri="jakarta.tags.core" %>
		<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
		<h1>회원목록</h1>
		<form:form method="get" autocomplete="off" modelAttribute="memberSearch">
			가입일
			<form:input path="sDate" /> ~
			<forn:input path="eDate" />
			<button type="submit">검색하기</button>
		</form:form>


		-----------------------------------------
		validations.properties 파일에 추가부분 : 
		-----------------------------------------
		typeMismatch.java.time.LocalDate=날짜 형식이 아닙니다 (예 - 20240716)
		-----------------------------------------

		@GetMapping("/list")
		public String list(@Valid @ModelAttribute MemberSearch memberSearch, Errors errors) {

			log.info(memberSearch.toString());
			return "member/list";
		}

	2. @PathVariable : 경로 변수 
	
		@ResponseBody
		@GetMapping({"/info/{id}/{id2}", "/info/{id}"})
		public void info(@PathVariable("id") String email, @PathVariable(name="id2", required = false) String email2) {
			log.info("email : {} email2 : {} ", email, email2);
		}

		@ResponseBody
		@GetMapping({"/info/{id}/{id2}", "/info/{id}", "/info/", "/info//", "/info///"})
		public void info(@PathVariable(name="id", required = false) String email, @PathVariable(name="id2", required = false) String email2) {
			log.info("email : {} email2 : {} ", email, email2);
		}

	3. 컨트롤러 익셉션 처리하기
	
		1) @ExceptionHandler
		
			- 발생 예외를 정의 
			- 예외발생시 특정 페이지를 노출 
			- 메서드에 자동 주입 
			
				- 발생한 예외 객체
				- Model 
				- HttpServletRequest
				- HttpServletResponse 
				- HttpSession 
				
				- 스프링 부트에서 추가된 EL식 속성
				  status : HTTP 상태코드
				  error : 에러코드
				  path : 예외가 발생한 URI
				  exception
				  message
				  timestamp
				
				
				
				@GetMapping("/list")
				public String list(@Valid @ModelAttribute MemberSearch memberSearch, Errors errors) {

					log.info(memberSearch.toString());

					boolean result = false;
					if(!result) {
						throw new BadRequestException("예외발생!!");
					}
					return "member/list";
				}

				@ExceptionHandler({BadRequestException.class})
				public String errorHandler() {
					return "error/common";
				}				

		2) @ControllerAdvice
		
			: 컨트롤러의 공통적인 처리 
			: 공통값 유지 - @ModelAttribute
			: 공통 에러 페이지 처리 - @ExceptionHandler
			
			// MemberConmtroller.java
			@Slf4j
			@Controller
			@RequestMapping("/member")
			@RequiredArgsConstructor
			public class MemberController {
		
				@ExceptionHandler(Exception.class)    // 우선순위 높다
				public String errorHandler(Exception e, Model model, HttpServletRequest request, HttpServletResponse response) {

					e.printStackTrace();
					log.info("MemberController에서 유입");

					return "error/common";
				}
			}			
			// - global/advice/CommonControllerAdvice.java
			@Slf4j
			@ControllerAdvice("org.choongang")    // 우선순위 낮다
			public class CommonControllerAdvice {   

				@ExceptionHandler(Exception.class)
				public String exception(Exception e, HttpServletRequest request) {
					log.info("Advice 에서 유입");
					return "error/common";
				}
			}	

			==> status 코드 세팅
		
			@Slf4j
			@ControllerAdvice("org.choongang")
			public class CommonControllerAdvice {

				@ExceptionHandler(Exception.class)
				public ModelAndView exception(Exception e, HttpServletRequest request) {
					log.info("Advice 에서 유입");

					HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; //500

					if(e instanceof CommonException commonException) {
						status = commonException.getStatus();
					}
					ModelAndView mv = new ModelAndView();
					mv.setStatus(status);
					mv.setViewName("error/common");

					return mv;
				}
			}

			public class CommonException extends RuntimeException{

				private HttpStatus status;

				public CommonException(String message) {
					this(message, HttpStatus.INTERNAL_SERVER_ERROR); // 500
				}

				public CommonException(String message, HttpStatus status) {
					super(message);
					this.status = status;
				}

				public HttpStatus getStatus() {
					return status;
				}
			}

7/17 day05 project 계속

스프링 파일 업로드(MultipartFile)

	1. multipart란?
		<form> 속성 : enctype="multipart/form-data"
		
		- multipart 
			- 일반 양식 데이터의 파트 
			- 파일 데이터(바이너리 데이터) 파드 

		기본 양식 content-type 
		
			application/x-www-form-urlencoded
			
	2. web.xml 설정 
		<multipart-config>
			<max-file-size>20971520</max-file-size> <!--  1MB * 20 -->
			<max-request-size>41943040</max-request-size> <!-- 40MB -->
		</multipart-config>
		
		<form:form method="post" autocomplete="off" encType="multipart/form-data">

	3. MultipartFile 인터페이스
	
		@RequestPart
		- 동일 이름의 여러 파일을 전송하는 경우? MultipartFile[] 와 같은 배열로 주입

	4. addResourceHandlers 설정
		- 파일 업로드 경로 -> 서버 접근 URL로 연결 

		@Slf4j
		@Controller
		@RequestMapping("/file")
		public class FileController {

			@GetMapping("/upload")
			public String upload() {
				return "file/upload";
			}

			@ResponseBody
			@PostMapping("/upload")
			public void uploadPs(@RequestPart("file") MultipartFile file) {
				String name = file.getOriginalFilename();
				log.info("파일명 : {}" , name);

				File uploadPath = new File("D:/uploads/" + name);
				try {
					file.transferTo(uploadPath);
				}catch (IOException e) {}
			}
		}

		<%@ page contentType="text/html; charset=UTF-8" %>
		<%@ taglib prefix="c" uri="jakarta.tags.core" %>
		<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
		<form:form method="post" autocomplete="off" encType="multipart/form-data">
			제목 : <input type="text" name="subject" ><br>
			파일 : <input type="file" name="file"><br>
			<button type="submit">전송하기</button>
		</form:form>


		웹을 통해 d:\uploads 폴더가 접근 가능하도록 설정

		@Configuration
		public class FileConfig implements WebMvcConfigurer {
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/uploads/**")
						.addResourceLocations("file:///D:/uploads/");
			}
		}

		----------------------------------------------------------------------
		예제
		----------------------------------------------------------------------
		1) resource/application.properties 파일
		
			file.upload.path=D:/uploads/ 


		2) MvcConfig.java 에 추가
		
			@Bean
			public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
				PropertySourcesPlaceholderConfigurer conf = new PropertySourcesPlaceholderConfigurer();
				conf.setLocations(new ClassPathResource("application.properties"));
				return conf;
			}		  

		3) @Value("${프로퍼티 값"}) - 주입되는 값은 전부 문자열
			
			@Configuration
			public class FileConfig implements WebMvcConfigurer {

				@Value("${file.upload.path}")  // application.properties 내용 : file.upload.path=D:/uploads/ 
				private String uploadPath;

				@Override
				public void addResourceHandlers(ResourceHandlerRegistry registry) {
					registry.addResourceHandler("/uploads/**")
							.addResourceLocations("file:///" + uploadPath);
				}
			}	

			@Slf4j
			@Controller
			@RequestMapping("/file")
			public class FileController {
				
				@Value("${file.upload.path}") /***/
				private String uploadDir;
				
				@GetMapping("/upload")
				public String upload() {
					return "file/upload";
				}

				@ResponseBody
				@PostMapping("/upload")
				public void uploadPs(@RequestPart("file") MultipartFile file) {
					
					String name = file.getOriginalFilename();
					log.info("파일명 : {}" , name);

					File uploadPath = new File(uploadDir + name);
					try {
						file.transferTo(uploadPath);
					}catch (IOException e) {}
				}
			}
			
			


프로필
	1. @Profile

	2. spring.profiles.active
	
	  - 지정된 환경변수 값 -> @Profile에 설정시 @Bean을 프로필에 따라서 달리 생성하는 기술
	  
		1) web.xml 
		
		   <init-param>
		     <param-name>spring.profiles.active</param-name>
			 <param-value>dev</param-value>
		   </init-param>
		   
		2) 톰캣 환경변수 
		
		3) 배포 파일 실행시 
			java -jar -Dspring.profiles.active=프로필이름 
			
		4) 환경변수를 조회하는 방법
		    System.getEnv("환경변수명")

	프로퍼티 파일을 이용한 프로퍼티 설정 y
	 - resource/application.properties 파일

	1. @Configuration
		public static PropertySourcesPlaceholderConfigurer properties() {
			PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
			configurer.setLocations(
					new ClassPathResource("db.properties"),
					new ClassPathResource("info.properties"));
			return configurer;
		}

	2. @Value("${프로퍼티 키값}")

			=> MvcConfig.java properties 파일 분리하기
			
			@Bean
			public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {

				String fileName = "application";
				String profile = System.getenv("spring.profiles.active");

				fileName += StringUtils.hasText(profile) ? "-" + profile : "";
				System.out.println("fileName = " + fileName);
				/*
				 * spring.profiles.active = dev -> application-dev.properties
				 * spring.profiles.active = prod -> application-prod.properties
				 */
				PropertySourcesPlaceholderConfigurer conf = new PropertySourcesPlaceholderConfigurer();
				conf.setLocations(new ClassPathResource(fileName + ".properties"));
				return conf;
			}

JSON 응답과 요청 처리

  - REST : Representational State Transfer : 표현적 상태 전이

	1. JSON 개요 (JavaScript Object Notation)
	
	2. Jackson 의존 설정
	
		- jackson-databind 
		- jackson-datatype-jsr310  ( jsr : java standard recommand,  310 : Date & Time API : java.time 패키지  )

		 implementation 'com.fasterxml.jackson.core:jackson-databind:2.17.2'
		 implementation 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2'
		 
		- ObjectMapper : 내부적으로 동작함 (@RestController에서)
		
		  자바객체 -> JSON 문자열 - writeObjectAsString (자바객체)
		  JSON 문자열 -> 자바객체 - readValue(..)
		  
	3. @RestController로 JSON 형식 응답
	
		반환값
		  - 자바객체(getter 있는) : JSON 문자열로 자바객체 변환 후 출력
		  
		     응답헤더 :Content-Type = application/json
			 
		  - 반환값이 없는 경우 : 응답 body가 비어 있음
		  
		  - 문자열 : 문자열 그대로 출력이 된다.
		  
		     응답헤더 :Content-Type = text/plain
		  
	
	4. @ResponseBody 애노테이션 
	
	    : @Controller 로 설정된 일반 컨트롤러 메서드를 Rest로 응답하게 만들어주는 에노테이션
		: 자바객체, 문자열, 반환값 없음
	
	5. @Jsonlgnore를 이용한 제외 처리
	
		@Data
		@Builder
		public class Member {
			private Long seq;
			private String email;
			@JsonIgnore   // password는 json으로 안보여줌
			private String password;
			private String userName;
			@JsonFormat(pattern = "yyyyMMdd HH:mm:ss")
			private LocalDateTime regDt;
		}		
	
	6. 날짜 형식 변환 처리: @JsonFormat 사용
	
	   : 출력 날짜, 입력날짜 형식을 지정
	   
			1) 개별 entities 필드에 추가하는 방법 - 우선순위 높다
			@Data
			@Builder
			public class Member {
				@Id
				private Long seq;
				private String email;
				@JsonIgnore
				private String password;
				private String userName;
				@JsonFormat(pattern = "yyyyMMdd HH:mm:ss")
				private LocalDateTime regDt;
			}

			2) MvcConfig.java에 추가하는 방법 - 전체적 적용 - 우선순위 낮다
			@Override
			public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
					.json()
					.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter))
					.build();

				converters.add(0, new MappingJackson2HttpMessageConverter(objectMapper)); // 0, 1, 2 추가 가능함
			}

	json대신 XML로 응답하기 
	
	 의존성 추가
	 - implementation 'com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.17.2'
		

		@Slf4j
		@RestController
		@RequestMapping(path="/api/member", produces = MediaType.APPLICATION_JSON_VALUE)  // 소속된거 모두 기본적으로 JSON 받기
		@RequiredArgsConstructor
		public class ApiMemberController {

			private final MemberMapper mapper;
			private final JoinService joinService;
			private final Utils utils;

			@PostMapping
			public ResponseEntity join(@RequestBody @Valid RequestJoin form, Errors errors) {
				if (errors.hasErrors()) {
					throw new BadRequestException(utils.getErrorMessages(errors));
				}

				joinService.process(form);
				return ResponseEntity.status(HttpStatus.CREATED).build();
				//return ResponseEntity.ok().build();
			}

			@GetMapping("/info/{email}")
			public JSONData info(@PathVariable("email") String email) {
				Member member = mapper.get(email);

				return new JSONData(member);
			}
			@GetMapping(path = "list", produces = MediaType.APPLICATION_XML_VALUE)   //  api/member/list 이것만 XML 제이터 받기
			public ResponseEntity<JSONData> list() {
				List<Member> members = IntStream.range(1, 10)
						.mapToObj(i -> Member.builder()
								.email("user" + i + "@test.com")
								.password("12345")
								.userName("사용자" + i)
								.regDt(LocalDateTime.now()).build()).toList();

				HttpHeaders headers = new HttpHeaders();
				headers.add("t1", "v1");
				headers.add("t2", "v2");

				return new ResponseEntity<>(new JSONData(members), headers, HttpStatus.OK);
				//return ResponseEntity.status(HttpStatus.OK).header("t1", "v1").body(members);
			}

		}

	
	7. @RequestBody JSON 요청 처리
	
		커맨드 객체 변환 기준 : Content-Type: application/x-www.form-urlencoded
		커맨드 객체 앞에 @RequestBody 추가하면 Content-Type: application/json
		
		(JSON Client 테스트 툴)
		 - POSTMAN
		 - ARC (Advanced Rest Client) : Rest Test
		 - MockMvc
	
	8. ResponseEntity 로 객체 리턴하고 응답 코드 지정하기
	
	    - 응답 헤더, 바디쪽을 상세하게 설정하는 경우
	
		1) ResponseEntity를 이용한 응답 데이터 처리
		
		2) ResponseEntity.status(상태코드).body(객체) : 응답 상태코드 + 출력 데이터
		
		3) ResponseEntity.status(상태코드).build(); 응답 상태코드 / 출력 데이터 없음

		4) ReponseEntity.ok(member)
		5) noContent() : 204
		6) badRequest() : 400
		7) notFound() : 404

			@Slf4j
			@RestController
			@RequestMapping("/api/member")
			@RequiredArgsConstructor
			public class ApiMemberController {

				private final MemberMapper mapper;
				private final JoinService joinService;

				@PostMapping
				public ResponseEntity join(@RequestBody RequestJoin form) {
					log.info(form.toString());

					joinService.process(form);
					return ResponseEntity.status(HttpStatus.CREATED).build();
				}

				@GetMapping("/info/{email}")
				public Member info(@PathVariable("email") String email) {
					Member member = mapper.get(email);

					return member;
				}
				@GetMapping("list")
				public ResponseEntity<List<Member>> list() {
					List<Member> members = IntStream.range(1, 10)
						.mapToObj(i -> Member.builder()
						.email("user" + i + "@test.com")
						.password("12345")
						.userName("사용자" + i)
						.regDt(LocalDateTime.now()).build()).toList();

					HttpHeaders headers = new HttpHeaders();
					headers.add("t1", "v1");
					headers.add("t2", "v2");

					return new ResponseEntity<>(members, headers, HttpStatus.OK);

					//return ResponseEntity.status(HttpStatus.OK).body(members);
				}

				@GetMapping(path="/test", produces = "text/html;charset=UTF-8") // produces: response Header 의 contentType 값 넣기
				public String test() {
					//HttpHeaders headers = new HttpHeaders();
					//headers.add("Content-Type", "text/html;charset=UTF-8");

					//return new ResponseEntity<>("안녕하세요.", headers, HttpStatus.OK);
					return "안녕하세요";
				}
				@GetMapping("/test2")
				public void test2() {
					log.info("test2...");
				}
			}


			@SpringJUnitWebConfig
			@ContextConfiguration(classes = MvcConfig.class)
			public class ApiMemberControllerTest {

				private MockMvc mockMvc;
				@Autowired
				private ApiMemberController controller;

				@BeforeEach
				void init() {
					mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
				}

				@Test
				void test1() throws Exception {

					/* 일반 post form request
					// Content-Type: application/x-www.form-urlencoded
					// 이름=값&이름=값..
					mockMvc.perform(post("/api/member")
							.param("email", "user99@test.com")
							.param("password", "12345678")
							.param("confirmPassword", "12345678")
							.param("userName", "사용자99"))
							.andDo(print());
					 */

					// JSON post 요청 => requestContent-Type: application/json
					
					ObjectMapper om = new ObjectMapper(); // JSON string <-> Object 변환
					om.registerModule(new JavaTimeModule());

					RequestJoin form = new RequestJoin();
					form.setEmail("user102@test.com");
					form.setPassword("12345678");
					form.setConfirmPassword("12345678");
					form.setUserName("사용자102");
					form.setAgree(true);

					String json = om.writeValueAsString(form);  // RequestJoin form Object -> String json 으로 쓰기
					System.out.println(json);
					
					//RequestJoin form = om.readValue(json, RequestJoin.class);   // String json -> RequestJoin form 으로 읽어라
					mockMvc.perform(post("/api/member")
							.contentType(MediaType.APPLICATION_JSON)
							.content(json)  //요청바디
						).andDo(print())
						.andExpect(status().isCreated());

				}
				@Test
				void test2() throws Exception {
					mockMvc.perform(get("/api/member/list"))
							.andDo(print());
				}
			}
			-------------------------------------------------------------------
			@Slf4j
			@RestController
			@RequestMapping(path="/api/member", produces = MediaType.APPLICATION_JSON_VALUE)
			@RequiredArgsConstructor
			public class ApiMemberController {

				private final MemberMapper mapper;
				private final JoinService joinService;
				private final Utils utils;

				@PostMapping
				public ResponseEntity join(@RequestBody @Valid RequestJoin form, Errors errors) {
					if (errors.hasErrors()) {
						throw new BadRequestException(utils.getErrorMessages(errors));
					}

					joinService.process(form);
					return ResponseEntity.status(HttpStatus.CREATED).build();
					//return ResponseEntity.ok().build();
				}
			
				@GetMapping("/info/{email}")
				public Member info(@PathVariable("email") String email) {
					Member member = mapper.get(email);

					return member;
				}
			 
				@GetMapping("/info/{email}")
				public JSONData info(@PathVariable("email") String email) {
					Member member = mapper.get(email);

					return new JSONData(member);
				}
			
				@GetMapping(path = "list", produces = MediaType.APPLICATION_XML_VALUE)
				public ResponseEntity<List<Member>> list() {
					List<Member> members = IntStream.range(1, 10)
						.mapToObj(i -> Member.builder()
						.email("user" + i + "@test.com")
						.password("12345")
						.userName("사용자" + i)
						.regDt(LocalDateTime.now()).build()).toList();

					HttpHeaders headers = new HttpHeaders();
					headers.add("t1", "v1");
					headers.add("t2", "v2");

					return new ResponseEntity<>(members, headers, HttpStatus.OK);
					//return ResponseEntity.status(HttpStatus.OK).header("t1", "v1").body(members);
				}
			
				@GetMapping(path = "list", produces = MediaType.APPLICATION_XML_VALUE)
				public ResponseEntity<JSONData> list() {
					List<Member> members = IntStream.range(1, 10)
							.mapToObj(i -> Member.builder()
									.email("user" + i + "@test.com")
									.password("12345")
									.userName("사용자" + i)
									.regDt(LocalDateTime.now()).build()).toList();

					HttpHeaders headers = new HttpHeaders();
					headers.add("t1", "v1");
					headers.add("t2", "v2");

					return new ResponseEntity<>(new JSONData(members), headers, HttpStatus.OK);
					//return ResponseEntity.status(HttpStatus.OK).header("t1", "v1").body(members);
				}

				@GetMapping(path="/test", produces = "text/html;charset=UTF-8") // produces: response Header 의 contentType 값 넣기
				public String test() {
					//HttpHeaders headers = new HttpHeaders();
					//headers.add("Content-Type", "text/html;charset=UTF-8");

					//return new ResponseEntity<>("안녕하세요.", headers, HttpStatus.OK);
					return "안녕하세요";
				}
				@GetMapping("/test2")
				public void test2() {
					log.info("test2...");
				}
			}



	9. @ExceptionHandler 적용 메서드에서 ResponseEntity로 응답하기
	
		@RestControllerAdvice("org.choongang")
		public class RestCommonControllerAdvice {
			@ExceptionHandler(Exception.class)
			public ResponseEntity<JSONData> errorHandler(Exception e) {

				Object message = e.getMessage();

				HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
				if (e instanceof CommonException commonException) {
					status = commonException.getStatus();

					Map<String, List<String>> errorMessages = commonException.getErrorMessages();
					if (errorMessages != null) message = errorMessages;
				}

				JSONData data = new JSONData();
				data.setSuccess(false);
				data.setMessage(message);
				data.setStatus(status);

				e.printStackTrace();

				return ResponseEntity.status(status).body(data);
			}
		}
	
		==> CommonException 수정

		@Getter @Setter
		public class CommonException extends RuntimeException{

			private HttpStatus status;
			private Map<String, List<String>> errorMessages;

			public CommonException(String message) {
				this(message, HttpStatus.INTERNAL_SERVER_ERROR); // 500
			}

			public CommonException(String message, HttpStatus status) {
				super(message);
				this.status = status;
			}

		}
	

		@Component
		@RequiredArgsConstructor
		public class Utils {

			private final MessageSource messageSource;
			private final HttpServletRequest request;

			public Map<String, List<String>> getErrorMessages(Errors errors) {
				// FieldErrors
				Map<String, List<String>> messages = errors.getFieldErrors()
						.stream()
						.collect(Collectors.toMap(FieldError::getField, e -> getCodeMessages(e.getCodes())));

				// GlobalErrors
				List<String> gMessages = errors.getGlobalErrors()
						.stream()
						.flatMap(e -> getCodeMessages(e.getCodes()).stream()).toList();

				if (!gMessages.isEmpty()) {
					messages.put("global", gMessages);
				}
				return messages;
			}


			public List<String> getCodeMessages(String[] codes) {
				ResourceBundleMessageSource ms = (ResourceBundleMessageSource) messageSource;
				ms.setUseCodeAsDefaultMessage(false);

				List<String> messages = Arrays.stream(codes)
						.map(c -> {
							try {
								return ms.getMessage(c, null, request.getLocale());
							} catch (Exception e) {
								return "";
							}
						})
						.filter(s -> !s.isBlank())
						.toList();

				ms.setUseCodeAsDefaultMessage(true);
				return messages;
			}
		}

	10. @Valid 에러 결과를 JSON으로 응답하기
	
		Errors
			getFieldErrors() : 필드별 전체 에러 정보
			getGlobalErrors() : 커맨드 객체 자체 에러정보
			getAllErrors() : 전체 에러 정보

		Swagger API : 스프링 문서 자동 생성해주는 API

기타 스프링 편의기능

	UriComponentsBuilder : 

		@SpringJUnitWebConfig
		@ContextConfiguration(classes = MvcConfig.class)
		public class Ex01 {
			@Test
			void test1() {
				URI uri = UriComponentsBuilder.fromUriString("https://www.naver.com")
					.path("/news/{0}")
					.queryParam("t1", "v1")
					.queryParam("t2", "v2")
					.queryParam("t3", "한글")
					.queryParam("t4", "aa{1}")
					.fragment("hash")
					//.encode()     // encoding 된 문자열이 존재함을 알려주기
					.build("AAA", "BBB");
				System.out.println(uri);
			}
		}	


	RestTemplate ( JSON Rest Client 입장)
	- 클라이언트 사이드에서 동기적인 HTTP 요청을 하는 클래스
		
		1. <T> ResponseEntity<T> getForEntity(...) - getForObject 보다 상세한 결과 받음 
		2. <T> T getForObject
		
		3. <T> ResponseEntity<T> postForEntity  - postForObject 보다 상세한 결과 받음
		4. <T> T postForObject
		
		5. <T> ResponseEntity<T> exchange(...) => PUT, DELETE, PATCH 등 method

		TypeReference -> 데이터 여러개 
		HttpEntity -> 헤더, 바디 등 함께 전송 시 필요함
		
			@SpringJUnitWebConfig
			@ContextConfiguration(classes = MvcConfig.class)
			public class Ex01 {

				@Autowired
				private ObjectMapper om;

				@Test
				void test2() {  // 데이터 1개만 있을때 

					RestTemplate restTemplate = new RestTemplate();
					PostData data = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/1", PostData.class);

					System.out.println(data);
				}

				@Test
				void test3() throws Exception{
					RestTemplate restTemplate = new RestTemplate();
					String body = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/1", String.class);

					// JSON Data 1개
					PostData data = om.readValue(body, PostData.class); 
					System.out.println(data);

					// JSON Data -> 리스트
					String itemsBody = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", String.class);

					List<PostData> items = om.readValue(itemsBody, new TypeReference<>(){});
					//List<PostData> items = om.readValue(itemsBody, new TypeReference<List<PostData>>(){});
					items.forEach(System.out::println);
				}

				@Test
				void test4() throws JsonProcessingException {
				
					RestTemplate restTemplate = new RestTemplate();
					RequestJoin form = new RequestJoin();
					form.setEmail("user02@test.com");
					form.setPassword("user02@test.com");
					form.setConfirmPassword(form.getPassword());
					form.setUserName("사용자02");
					form.setRegDt(LocalDateTime.now());
					form.setAgree(true);

					String params = om.writeValueAsString(form);
					System.out.println(params);

					HttpHeaders headers = new HttpHeaders();
					//headers.add("Content-Type", "application/json"); //아래 줄과 동일
					headers.setContentType(MediaType.APPLICATION_JSON);

					HttpEntity<String> request = new HttpEntity<>(params, headers);
					ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:3000/day05/api/member", request, String.class);

					System.out.println(response);
				}
				@Test
				@DisplayName("일반 양식 POST : Content-Type: application/x-www-form-urlencoded")
				void test5() {
					RestTemplate restTemplate = new RestTemplate();
					MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
					params.add("email", "user03@test.com");
					params.add("password", "user03@test.com");
					params.add("confirmPassword", "user03@test.com");
					params.add("userName", "user03");
					params.add("regDt", LocalDateTime.now().toString());
					params.add("agree", "true");
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
					HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

					String url = "http://localhost:3000/day05/member/join";
					ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

					System.out.println(response);
				}
				
			}

			@Configuration
			public class BeanConfig {
				@Bean
				public ObjectMapper objectMapper() {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.registerModule(new JavaTimeModule());
					return objectMapper;
				}
			}

			@Data
			public class PostData {
				private String userId;
				private long id;
				private String title;
				private String body;
			}

RestCommonException, 커멘드객체 검증 등 다시 설명 7/19 9시