7/5  소스 11.스프링 프레임워크 day01
 
스프링 프레임워크

	1. 스프링 프레임워크란?
		(Spring Framework)

		1) 의존 주입(Dependency Inject : DI) 지원
		2) AOP(Aspect-Oriented Programming) 지원
			- 관점지향 프로그래밈 / 관점 -개발자의 공통적인 처리 부분
			- 프록시(proxy) : 대신하다, 대리하다.
			
		3) MVC 웹 프레임워크 제공
			- spring-webmvc 
			
		4) JDBC, JPA 연동, 선언적 트랜잭션 처리 등 DB 연동 지원	
			JPA(Java Persistence API - ORM 표준 설계)

		5) 스프링 데이터,  스프링 시큐리티, 스프링 배치
			- 스프링 시큐리티 : 인증(로그인), 인가(통제)

	2. 스프링 프로젝트 생성하기
	
		spring-context 의존성 ( 가장중요한 의존성 )
		spring6
		  - JDK 컴파일버전 17 이상(반드시)
		
		
		spring context mvn repository 검색
		Spring Context » 6.1.10
		implementation 'org.springframework:spring-context:6.1.10'

		설정클래스 @Configuration

		
		
	3. 스프링은 객체 컨테이너
	
		IoC - Inversion Of Control : 제어의 역전 
			- 개발자가 해야되는 객체의 관리 -> 스프링 컨테이너가 대신 수행
		
		- 다양한 방식으로 객체 관리 
		  1) 싱글톤 방식으로 객체를 관리
		  
					package exam01;
					import exam01.config.AppCtx;
					import org.junit.jupiter.api.Test;
					import org.springframework.context.annotation.AnnotationConfigApplicationContext;

					public class Ex01 {
						@Test
						void test1() {
							AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class); //스프링 컨테이너 객체

							// Greeter.class에서 "greeter" 메서드 꺼내오기
							Greeter g1 = ctx.getBean("greeter", Greeter.class);
							g1.hello("이이름");

							Greeter g2 = ctx.getBean("greeter", Greeter.class);
							g2.hello("김이름");

							System.out.println(g1 == g2); // true : 싱글톤

							ctx.close();
						}
						@Test
						void test2() { 
							AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);

							//위와 동일 기능, 싱글톤, 객체한개만 있는 경우가 많다, => 메서드이름 없이 Class 클래스만 있어도 
							//찾을 수 있다 = ctx.getBean("greeter", Greeter.class);
							Greeter g1 = ctx.getBean(Greeter.class); 
							g1.hello("이이름");
							
							ctx.close();
						}						
					}
			설정방식 종류
			- AnnotationConfigApplicationContext  :  Annotation
			- GenericXmlApplicationContext  : XML
			- GenericGroovyApplicationContext  : Groovy 
			
		스프링문서 검색 - spring core api doc https://docs.spring.io/spring-framework/docs/current/javadoc-api/
		
		
	4. 스프링 DI(Dependency Injection - 의존주입)

		1) 의존(Dependency)
			의존성 : 필요로 하는 객체 
			
			
			public class JoinService {
				private JoinValidator validator;
				
				//의존관계 ( 없으면 객체는 생성 안됨)
				public JoinService(JoinValidator validator) {
					this.validator = validator;
				}

				//참고 : 연관관계 - 의존관계 아님 ( 없어도 객체는 생성되므로) 
				private void setValidator(JoinValidator validator) {
					this.validator = validator;
				}
				//-> 지워야함
				
				public void process(RequestJoin form) {
					validator.check(form);  //JoinService는 validator객체와 form 객체에 의존함
					
					//데이터 영구저장 - DAO Data Access Object : 매퍼
					Member member = Member.builder()
						.email(form.getEmail())
						.password(form.getPassword())
						.userName(form.getUserName())
						.regDt(form.getRegDt())
						.build();
					
					memberDao.register(member);
				}					
			}			
			
			//객체 조립기			
			package exam01.member.services;

			import exam01.member.dao.MemberDao;
			import exam01.member.validators.JoinValidator;

			public class Assembler {
				public MemberDao memberDao() {
					return new MemberDao();
				}
				public JoinValidator joinValidator() {
					return new JoinValidator();
				}
				public JoinService joinService() {
					return new JoinService(joinValidator(), memberDao());
				}
			}

			public class Ex02 {
				@Test
				void test1() {
					Assembler assembler = new Assembler(); //조립기 객체
					JoinService joinService = assembler.joinService();
					MemberDao memberDao = assembler.memberDao();

					RequestJoin form = RequestJoin.builder()
						.email("user01.test.com")
						.userName("사용자01")
						.password("12345678")
						.confirmPassword("12345678")
						.build();
					joinService.process(form);

					List<Member> members = memberDao.getList();
					members.forEach(System.out::println);

				}
			}

			참고) lombok 의존성
				compileOnly 'org.projectlombok:lombok:1.18.34'
				annotationProcessor 'org.projectlombok:lombok:1.18.34'
				testCompileOnly 'org.projectlombok:lombok:1.18.34'
				testAnnotationProcessor 'org.projectlombok:lombok:1.18.34'
			
		2) DI를 통한 의존 처리
		3) DI와 의존 객체 변경의 유연함

					
	5.  객체 조립기
	
		JoinValidator에 아래처럼 추가해야 한다면 .. Assembler에만 추가하면 됨
						
				public class JoinValidator implements Validator<RequestJoin> {

					private MemberDao memberDao;

					public void setMemberDao(MemberDao memberDao) {
						this.memberDao = memberDao;
					}

					@Override
					public void check(RequestJoin form) {

					}
				}		


				public class Assembler {
					public MemberDao memberDao() {
						return new MemberDao();
					}
					public JoinValidator joinValidator() {
						JoinValidator validator = new JoinValidator();
						validator.setMemberDao(memberDao());
						return validator;
					}
					public JoinService joinService() {
						return new JoinService(joinValidator(), memberDao());
					}
				}

			==> 이것도 안해도 되고, 어노테이션 @Configuration 으로 객체조립기 만들기
			
				@Configuration
				public class AppCtx2 {

					@Bean
					public MemberDao memberDao() {
						return new MemberDao();
					}

					@Bean
					public JoinValidator joinValidator() {
						JoinValidator joinValidator =  new JoinValidator();
						joinValidator.setMemberDao(memberDao());
						return joinValidator;
					}
					@Bean
					public JoinService joinService() {
						return new JoinService(joinValidator(),memberDao());
					}

					@Bean
					public InfoService infoService() {
						InfoService infoService = new InfoService();
						infoService.setMemberDao(memberDao());
						return infoService;
					}
				}
				public class InfoService {
					private MemberDao memberDao;

					public void setMemberDao(MemberDao memberDao) {
						this.memberDao = memberDao;
					}
					public void printList() {
						List<Member> members = memberDao.getList();
						members.forEach(System.out::println);
					}
				}
				public class Ex03 {
					@Test
					void test1() {
						AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx2.class);

						JoinService joinService = ctx.getBean(JoinService.class);
						InfoService infoService = ctx.getBean(InfoService.class);

						RequestJoin form = RequestJoin.builder()
							.email("user01@test.com")
							.password("12345678")
							.confirmPassword("12345678")
							.userName("사용자01")
							.build();

						joinService.process(form);
						infoService.printList(); //DAO를 직접쓰지말고 이렇게..
						ctx.close();
					}
				}


				
스프링 DI 설정 및 사용
	1. 스프링을 이용한 객체 조립과 사용
	2. DI 방식1 : 생성자 방식
	3. DI 방식2 : 세터 메서드 방식
	4. @Configuration
	5. @Bean 	
	6. 두 개 이상의 설정 파일 사용하기
		1) 생성자 매개변수
		2) @Import : 설정클래스에서 다른 설정클래스 포함

				//설정 클래스 AppCtx.class, AppCtx2.class 2개 한번에 , 또는 패키지 통째로 ..
				public class Ex03 {
					@Test
					void test2() {
						AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class, AppCtx2.class);
						
						//패키지 통째로 ..
						//AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("exam01.config");


						//AppCtx2.class
						JoinService joinService = ctx.getBean(JoinService.class);
						InfoService infoService = ctx.getBean(InfoService.class);

						RequestJoin form = RequestJoin.builder()
								.email("user01@test.com")
								.password("12345678")
								.confirmPassword("12345678")
								.userName("사용자01")
								.build();

						joinService.process(form);
						infoService.printList(); //DAO를 직접쓰지말고 이렇게..

						//AppCtx.class
						Greeter g1 = ctx.getBean(Greeter.class);
						g1.hello("이이름");

						ctx.close();
					}
				}
				
				또는
				
				//설정 클래스 - 스프링 컨테이너가 관리할 객체를 정의, 설정
				//@Import(value = {AppCtx2.class})  다음줄과 동일
				@Import({AppCtx2.class})
				@Configuration
				public class AppCtx {

					@Bean
					public Greeter greeter() {
						return new Greeter();
					}
				}				

				@Import(value = {AppCtx2.class})
				@Import({AppCtx2.class})
				@Import(AppCtx2.class)

				@Import({AppCtx2.class, 000.class, 000.class})

의존 자동 주입
	1. @Autowired
		- 의존성을 주입해야되는 객체임을 알려주는 어노테이션
		
			public class JoinService {
				@Autowired
				private JoinValidator validator;

				@Autowired
				private MemberDao memberDao;		
					
				...
			}
			


			public class InfoService {
				private MemberDao memberDao;

				@Autowired
				public void setMemberDao(Optional<MemberDao> opt) {
					this.memberDao = opt.get();
				}
				public void printList() {
					List<Member> members = memberDao.getList();
					members.forEach(System.out::println);
				}
			}

			public class JoinValidator implements Validator<RequestJoin> {

			private MemberDao memberDao;

				@Autowired
				public void setMemberDao(MemberDao memberDao) {
					this.memberDao = memberDao;
				}

				@Override
				public void check(RequestJoin form) {

				}
			}

			@Configuration
			public class AppCtx2 {
				@Bean
				public MemberDao memberDao() {
					return new MemberDao();
				}

				@Bean
				public JoinValidator joinValidator() {
					return new JoinValidator();
				}
				@Bean
				public JoinService joinService() {
					return new JoinService();
				}

				@Bean
				public InfoService infoService() {
					return new InfoService();
				}
			}




		1) 멤버변수 위에 정의
		2) setter 메사드 위에 정의
		3) Optional 정의된 멤버변수, 메서드의 매개변수에도 있어도 주입
		4) 자동 스캔 적용 (@CompoentScan) 시
			- @Autowired 미적용방식
			- 생성자 매개변수로 정의 / 기본생성자가 정의 되지 않아야 한다
			=> 또는 lombok  RequiredArgsConstructor 쓰고 멤버변수에 final이나 notNull 로...
			
		참고) @Resource @Inject
		
		
	2. 일치하는 빈이 없는 경우
	3. @Qualifier
		:빈의 이름을 직접 지정


			@Configuration
			public class AppCtx2 {
				@Bean
				@Qualifier("mDao")
				public MemberDao memberDao() {
					return new MemberDao();
				}

				@Bean
				public MemberDao memberDao2() {
					return new MemberDao();
				}

				@Bean
				public JoinValidator joinValidator() {
					return new JoinValidator();
				}
				@Bean
				public JoinService joinService() {
					return new JoinService();
				}

				@Bean
				public InfoService infoService() {
					return new InfoService();
				}
			}


			public class InfoService {
				private MemberDao memberDao;

				@Autowired
				@Qualifier("memberDao2")
				public void setMemberDao(Optional<MemberDao> opt) {
					this.memberDao = opt.get();
				}
				public void printList() {
					List<Member> members = memberDao.getList();
					members.forEach(System.out::println);
				}
			}


			public class JoinValidator implements Validator<RequestJoin> {

				private MemberDao memberDao;

				@Autowired
				@Qualifier("memberDao2")
				public void setMemberDao(MemberDao memberDao) {
					this.memberDao = memberDao;
				}

				@Override
				public void check(RequestJoin form) {

				}
			}

			public class JoinService {
				@Autowired
				private JoinValidator validator;

				@Autowired
				@Qualifier("memberDao2")
				private MemberDao memberDao;



				public void process(RequestJoin form) {
					validator.check(form);  //JoinService는 validator객체와 form 객체에 의존함

					//데이터 영구저장 - DAO Data Access Object : 매퍼
					Member member = Member.builder()
						.email(form.getEmail())
						.password(form.getPassword())
						.userName(form.getUserName())
						.regDt(form.getRegDt())
						.build();

					memberDao.register(member);
				}
			}

		
	4. Bean이름과 기본 한정자
		@Bean :메서드명
		@Qualifier :변경한이름
		클래스명(자동 스캔의 경우) -> 앞첫글자는 소문자
		예) Class Joinservce -> joinService()
	
	5. @Autowired 애노테이션의 필수 여부
		- Required 
		: true - 기본값, 주입받는 객체가 반드시 컨테이너 안에 생성되어 있어야 한다..
					: 없으면 예외발생
		: false - 주입받는 객체가 없으면 setter 메서드 호출 안하고 의존성 주입 아예 못한다.
		
		- Required true  - 없을 수도 있는 주입받는 객체 @Nullable 에노테이션 적용해도 필수 여부는 해제 되지만
						- 메서드는 호출되지만, 없는 의존성에 null을 대입한다
		
			public class InfoService {
				private MemberDao memberDao;
				private DateTimeFormatter formatter;

				@Autowired
				@Qualifier("memberDao")
				public void setMemberDao(Optional<MemberDao> opt) {
					this.memberDao = opt.get();
				}

				/*
				//@Autowired(required = false) //Bean이 업으면 호출 안함
				@Autowired //Bean이 업으면 호출 안함
				public void setFormatter(DateTimeFormatter formatter) {
					this.formatter = formatter;
				}
				*/
				@Autowired //@Nullable 쓰면  호출되지만 null로 대체됨
				public void setFormatter(@Nullable DateTimeFormatter formatter) {
					this.formatter = formatter;
				}

				public void printList() {
					List<Member> members = memberDao.getList();
					members.forEach(m-> {
						System.out.println(m);
						LocalDateTime regDt = m.getRegDt();
						if(formatter != null) {
							System.out.println(formatter.format(regDt));
						}
					});
				}
			}

			@Configuration
			public class AppCtx2 {
			
				//@Bean  -> *** 이렇게하면
				public DateTimeFormatter dateFormatter() {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					return formatter;
				}
			}
		
		
	-------------------------------------------------------------------------------
	@Bean //자바 JDK, 외부 라이브러리는 수동등록이 필수 
    public DateTimeFormatter dateFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return formatter;
    }
	-------------------------------------------------------------------------------
		

컴포넌트 스캔

	1. @Component
	2. @ComponentScan   
		- 스프링 컨테이너가 자동으로 스캔할 범위 지정
		
	3. 기본 스캔 대상
		- 
		@Component - 작은 기능들 .. validator, utils (Service의 구성요소..)
		@Service - 큰 기능
		
		- 툭정 기능과 관련된 에노테이션
		
		@Configuration
		@Controller
		@RestController
		@ControllerAdvice
		@RestControllerAdvice
		@Aspect
		

			@Configuration
			//@ComponentScan(basePackages = "exam01.member") 아랫줄과 동일
			@ComponentScan("exam01.member")
			public class AppCtx3 {
				/// 비어있어도 됨 @Bean ... 나열하지 않아도 @ComponentScan("exam01.member") 로 
				//자동 스캔하여 Bean컨테이너가 객체관리함
			}		
	
	4. 컴포넌트 스캔에 따른 충돌 처리
		1) Bean 이름 충돌
			- 클래스명만 Bean의 이름으로 고려
			- 다른 패키지에 있는 동일한 이름의 클래스가 있으면 충돌 발생
			
			exam01.member.dao.MemberDao - Bean이름 : memberDao
			exam01.member.dao.sub.MemberDao - Bean이름 : memberDao
			
			@Component("memberDao2")   <-- 이름 지정
			
			
		2)  수동 등록한 빈과 충돌
		3) excludeFilters


			@Configuration
			@ComponentScan("exam01.member")
			public class AppCtx3 {
			
				@Bean
				public MemberDao memberDao() {   // 수동등록 Bean 우선 됨
					return new MemberDao();
				}
			}
			
			@Component("memberDao2")  //이름 바꾸는 방법
				public class MemberDao {
			}


빈 라이프 사이클과 범위
	1. 컨테이너 초기화 : 빈 객체의 생성, 의존 주입, 초기화

	2. 컨테이너 종료 : 빈 객체의 소멸

	3. 빈 객체의 라이프 사이클
		1) 객체 생성 -> 의존 설정 -> 초기화 -> 소멸
		2) InitializingBean
		3) DisposableBean

	4.  빈 객체의 초기화와 소멸 : 커스텀 메서드
		1) initMethod 
		2) destroyMethod 

	5. 빈 객체의 생성과 관리 범위
	@Scope 

AOP 프로그래밍
(Aspect Oriented Programming)
spring-aop API 
aspectjweaver

	1. 프록시(proxy)

	2. AOP
		1) @Aspect 
		2) @Pointcut 
			- execution 명시자
			
		3) @Around

	3. @Order

		4. 프록시 생성방식
		5. @Around의 Pointcut 설정과 @Pointcut 재사용