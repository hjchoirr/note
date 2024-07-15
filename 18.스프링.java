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
		4) 자동 스캔 적용 (@CompoetScan) 시
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
		@Component - 작은 기능들 .. validator, utils
		@Service - 큰 기능
		
		- 툭정 기능과 관련된 에노테이션
		
		@Configuration
		@Controller
		@RestController
		@ControllerAdvice
		@RestControllerAdvice
		@Aspect
		@Repository : DAO에 주로 정의 
		

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
			
			
		2) 수동 등록한 빈과 충돌
		
		3) excludeFilters

			@Configuration
			@ComponentScan("exam01.member")
			public class AppCtx3 {
			
				@Bean
				public MemberDao memberDao() {   // 수동등록 Bean 우선 됨
					return new MemberDao();
				}
			}
			
			@Component("memberDao2")  
				public class MemberDao {
			}

		3) excludeFilters

 
				 스캔 배제 Filter

				@Configuration
				@ComponentScan(
				basePackages="member",
				excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = ManualBean.class))  // ManualBean 에노테이션 붙어있는거 배제
				public class AppCtx {
				} 

				package global.annotations;
				import java.lang.annotation.ElementType;
				import java.lang.annotation.Retention;
				import java.lang.annotation.RetentionPolicy;
				import java.lang.annotation.Target;

				@Target(ElementType.TYPE)
				@Retention(RetentionPolicy.RUNTIME)
				public @interface ManualBean {

				}

				
				@Repository
				@ManualBean
				public class MemberDao { // memberDao - 빈 이름

					private static Map<String, Member> members = new HashMap<>();

					public void register(Member member) {
						members.put(member.getEmail(), member);
					}

					public Member get(String email) {
						return members.get(email);
					}

					public List<Member> getList() {
						List<Member> data = new ArrayList<>(members.values());

						return data;
					}
				}
				@ComponentScan(
				basePackages="member",
				excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {MemberDao.class, JoinService.class})) // 클래스명 명시하여 배제
				public class AppCtx {

				}

				Ant pattern Filter - 제일 간단함
				
				  mvn reposi : aspectjweaver 검색
				  의존성 : runtimeOnly 'org.aspectj:aspectjweaver:1.9.22.1'
				          -> 
						  implementation 'org.aspectj:aspectjweaver:1.9.22.1'

					@ComponentScan(
					basePackages="member",
					excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern="member.*Dao"))
					public class AppCtx {


					}




스프링 컨테이너( AnnotationConfigApplicationContext ) 가 하는 일

 - 스프링 설정 클래스를 가지고 
	- @Configuration 에노테이션이 붙어있는 클래스 를 가지고 
	- @ComponentScan("스캔범위")
	
 - 객체생성
 
	- 기본 스캔 대상에 해당하는 클래스이면 객체 생성
	  (@Component, @Service...)
	
 - 의존성주입
 
	- @Autowired 사용하는 방법
	
		1) 멤버변수 위에
		2) setter 메서드위에 
		3) Optional 형태로 감싼 구조의 멤버변수, setter메서드 위에
		
	- @Autowired 사용하지 않는 방법
	
		4) 생성자매개변수에 의존성을 주입하는 방법 (+ 기본생성자 없어야함)
		   : 객체 생성 시 의존성 강제 주입 
		
			- lombok @RequiredArgsConstructor 와 함께 많이 쓴다
			  : 초기화가 반드시 필요한 멤버변수를 생성자매개변수에
			  final 의 경우는 반드시 초기화값 필요 : 생성자 매개변수에 초기화 강제  
			  -> final은 상수이므로 값을 못바꾸므로 -> 그래서 @NonNull을 맴버버변수위에 넣으면 final로 안해도 됨


					@Service
					@RequiredArgsConstructor
					public class JoinService {

						private final JoinValidator validator;  //이렇게해도 강제주입 됨 @RequiredArgsConstructor +  final

						@NonNull
						private MemberDao memberDao;   //이렇게해도 강제주입 됨 @RequiredArgsConstructor + @NonNull

						public void process(RequestJoin form) {
							...
						}
					}


7/8 11시, 12시 부터

빈 라이프 사이클과 범위
	1. 컨테이너 초기화 : 빈 객체의 생성, 의존 주입, 초기화
	
	2. 컨테이너 종료 : 빈 객체의 소멸
	

	3. 빈 객체의 라이프 사이클
		1) 객체 생성 -> 의존 설정 -> 초기화 -> 소멸
			초기화 : 객체가 완전히 생성되고 조립된 다음에 처리할 작업을 정의하면 실행되는 단계
			소멸 : ctx.close() - 객체 소멸, 소멸전에 처리할 작업을 정의하면 실행하는 단계
			
			스프링컨테이너 생성시 진행되는 부분 : (객체 생성 -> 의존 설정 -> 초기화)
			
		2) InitializingBean
			 - afterPropertiesSet 메서드 : 초기화 단계시 실행됨 ( 객체가 완전히 도립되고 생성된 후에 처리할 작업을 정의)
			 
		3) DisposableBean
			- destroy 메서드 
			   : 컨테이너에 있는 객체가 소멸되기 전에 실행
			     (주로 객체 소멸전에 할 작업 예) - 자원 해제 등...)
				 

			@Service
			public class BoardService implements InitializingBean, DisposableBean {
				public void write() {
					System.out.println("글쓰기!");
				}

				@Override
				public void afterPropertiesSet() throws Exception {
					System.out.println("afterPropertiesSet()!");
				}

				@Override
				public void destroy() throws Exception {  // ctx.close() 호출시만 동작함
					System.out.println("destroy()!");
				}
			}
			
			

	4.  빈 객체의 초기화와 소멸 : 커스텀 메서드
		- 외부 라이브러리, 자바JDK 기본 클래스, 스프링 프레임워크 기본 클래스 등등..
		
		1) initMethod 
		2) destroyMethod 
		
		@Bean
		initMethod
		 - InitializingBean : afterPropertiesSet 와 동일시점 실행
		
		destroyMethod
		 - DisposableBean

		@Configuration
		@ComponentScan(basePackages={"member","board"})
		public class AppCtx {

			@Bean(initMethod = "init", destroyMethod = "destroy")
			public BoardService2 boardService2() {
				return new BoardService2();
			}
		}


	5. 빈 객체의 생성과 관리 범위
	@Scope 
		- @Scope("singleton") : 정의하지 않아도 기본은 싱글턴 패턴으로 관리
		- @Scope("prototype") : 매번 조회시 마다 새로운 객체를 샐성
			- 스프링 객체관리는 기본이 싱글턴이지만 "prototype" 은 일부기능에 제한적으로 사용가능

			@Configuration
			@ComponentScan(basePackages={"member","board"})
			public class AppCtx {

				@Scope("prototype")
				@Bean(initMethod = "init", destroyMethod = "destroy")
				public BoardService2 boardService2() {
					return new BoardService2();
				}
			}

AOP 프로그래밍(Aspect Oriented Programming : 관점지향 프로그래밍)

 - 관점 : 개발자의 공통적인 관심사
 - 공통기능, 핵심기능 분리 기술

	공통기능 : 스프링이 대신수행
	핵심기능 : 개발자 정의
	
	 - 데코레이터 패턴과 유사하고,  대신 해준다는 의미로  AOP를 프록시 라고 함.
		(버퍼드스트림)
		
		
		=> 공통기능과 핵심기능 분리하기 ( 데코레이터 패턴 )

			public interface Calculator {
				long factorial(long num);
			}		

			public class ImplCalculator implements Calculator{

				@Override
				public long factorial(long num) {
					long result = 1L;

					for(long i = 1L; i <= num; i++) {
						result *= i;
					}
					return result;
				}
			}

			public class RecCalculator implements Calculator{

				@Override
				public long factorial(long num) {
					if(num < 1L) {
						return 1L;
					}
					return num * factorial(num - 1L);
				}
			}

			// ImplCalculator, RecCalculator 둘다 대신 수행 해 줄 수 있는 프록시 클래스 (데코레이터 패턴)
			public class ProxyCalculator implements Calculator{

				private Calculator calculator;

				public ProxyCalculator(Calculator calculator) {
					this.calculator = calculator;
				}

				@Override
				public long factorial(long num) {
					long stime = System.nanoTime();

					try {
						long result = calculator.factorial(num); //다른 계산기의 factorial 연산을 대신 수행  ==> Proxy
						return result;
					} finally {
						long etime = System.nanoTime();
						System.out.println("걸린시간 : " + (etime - stime));
					}
				}
			}

			@Test 
			void test1() { // 프록시 사용 안한 경우
				long stime = System.nanoTime();   //공통기능
				RecCalculator cal2 = new RecCalculator();
				long result2 = cal2.factorial(10L);  //핵심기능
				long etime = System.nanoTime();
				System.out.println("cal2=" + result2 + " 걸린시간:" + (etime - stime));

				stime = System.nanoTime(); //공통기능
				ImplCalculator cal1 = new ImplCalculator();
				long result1 = cal1.factorial(10L);  //핵심기능
				etime = System.nanoTime();
				System.out.println("cal1=" + result1 + " 걸린시간:" + (etime - stime));

			}
			
			=> 공통기능과 핵심기능 분리하기 ( 데코레이터 패턴 )
			
			@Test // 프록시 사용
			void test2() {
				ProxyCalculator cal1 = new ProxyCalculator(new ImplCalculator());
				long result1 = cal1.factorial(10L);
				System.out.println("cal1:" + result1);


				ProxyCalculator cal2 = new ProxyCalculator(new RecCalculator());
				long result2 = cal2.factorial(10L);
				System.out.println("cal2:" + result2);
			}
			
			=> 이렇게 거 많이 씀 : 알게 모르게 
			
			@Test
			void test2() {
				Calculator cal1 = new ProxyCalculator(new ImplCalculator());
				long result1 = cal1.factorial(10L);
				System.out.println("cal1:" + result1);


				Calculator cal2 = new ProxyCalculator(new RecCalculator());
				long result2 = cal2.factorial(10L);
				System.out.println("cal2:" + result2);
			}			
			
			이런 프록시 너무 많이 만들어야 하므로
			
			=> 동적 프록시 => AOP
			
			동적프록시 
			java.lang.reflect.Proxy
			
				InvocationHandler
				: 인터페이스를 통한 프록시이므로 인터페이스 정의가 필수 
				
			동적프록시 예제
				package exam02;
				import java.lang.reflect.InvocationHandler;
				import java.lang.reflect.Method;

				public class CalculatorHandler implements InvocationHandler {

					private Object obj;
					public CalculatorHandler(Object obj) {
						this.obj = obj;
					}
					/**
					 * @param proxy the proxy instance that the method was invoked on
					 * @param method : 호출한 메서드의 정보
					 *               : 동적 메서드 호출 method.invoke
					 * @param args : 메서드 호출시 넘겨준 값(인수)
					 * @return
					 * @throws Throwable
					 */
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println(proxy.getClass());

						long stime = System.nanoTime(); //공통기능

						try {

							Object result = method.invoke(obj, args); //핵심기능
							return result;
						} finally {
							long etime = System.nanoTime();
							System.out.println("걸린시간: " + (etime - stime));
						}
					}
				}

				public class Ex02 {
					@Test
					void test1() {
						Object obj = Proxy.newProxyInstance(
								Calculator.class.getClassLoader(),
								new Class[] {Calculator.class},
								new CalculatorHandler(new ImplCalculator()));

						Calculator cal = (Calculator) obj;
						long result = cal.factorial(10L);
						System.out.println(result);
					}
				}
			
AOP 의존성
spring-aop API  : spring-context 만 추가하면 AOP 따라온다
aspectjweaver : 구현체

스프링에 제공하는 모든 관리기능의 전제조건?
	- 스프링이 관리하는 객체만 관리기능을 제공한다
	- 스프링컨테이너 안에 있는 객체만 적용 가능




스프링 의존성 추가
implementation 'org.springframework:spring-context:6.1.10'

aspectjweaver 검색 - 구현체

	runtimeOnly 'org.aspectj:aspectjweaver:1.9.22.1'
	->변경
	implementation 'org.aspectj:aspectjweaver:1.9.22.1'

 
day03 7/9 

	1. 프록시(proxy)
	
		BufferedInputStream 도 대표적인 데코레이터 패턴
		
		Class BufferedInputStream extends InputStream {
			private InputStream in;
			
			public BufferedInputStream(InputStream in) {
				this.in = in;
			}
			
			public int read() {
			
				//버퍼추가 기능 - 공통기능
				
				int byte = in.read(); //핵심기능을 inputStream 대신 수행 
				
				//버퍼추가 기능 - 공통기능
				return byte;
			}
		}
		

	2. AOP (Aspect Oriented Programming)
		- 관점 : 공통기능이 공통 관심사.. 
	
		1) @Aspect 
		
			- 공통기능이 정의된 클래스위에
			
		2) @Pointcut 
			- execution 명시자 - Ant패턴
			
			- 공통기능이 적용될 패키지 범위, 메서드 패턴
			
		3) 공통기능을 수행할 메서드 위에 
		
		  @Before : 메서드가 호출되기 전 공통기능
		  @After : 메서드가 호출된 후 공통기능 
		  @AfterReturning : 반환값을 내보낸 후 공통기능
		  @AfterThrowing : 예외가 발생한 후 공통기능
		  @Around : 메서드 호출 전, 후 공통기능

			- ProceedingJoinPoint
				Object proceed() 핵심기능을 대신 수행해줌
				Signature getSignature() : 호출한 메서드 정보
				 - getTarget() : 실제 메서드를 호출한 객체(RecCalculator..)
				 - Object[] getArgs() : 인수목록
				 
			- Signature
				String getName()
				String toLongString()
				String toShortString()
				
		Ant 패턴
		 - exam01.* -> exam01 패키지의 클래스 
		 - exam01..* -> exam01 패키지 포함 하위 패키지 모든 클래스
		 
		 
		 execution(* aopex.*.*()) : 모든 반환값의 aopex 패키지의 모든 하위 패키지의 매개변수없는 모든 메서드
		 execution(* aopex..*.*(..)) : 모든 반환값의  aopex 패키지 포함 모든 하위 패키지의 모든 매개변수의 모든 메서드
		  = execution(* aopex..*(..))

		 execution(* get*(*)) -> get으로 시작하는 매개변수 1개짜리 모든 메서드 
		 execution(* get*(*,*)) -> get으로 시작하는 매개변수 2개짜리 모든 메서드 
		 
		 execution(* read*(Integer, ..)) -> 매서드명이 read로 시작하고 첫번째 매개변수는 Integer 이고 나머지 매개변수는 0개 이상 아무거나 


			@Configuration
			@EnableAspectJAutoProxy // AOP 자동설정 에너테이션 : 동적 프록시
			public class AppCtx {

				@Bean
				public ProxyCalculator2 proxyCalculator() {
					return new ProxyCalculator2();
				}

				@Bean
				public Calculator calculator() {
					return new RecCalculator();
				}
			}

			@Aspect
			public class ProxyCalculator {

				@Pointcut("execution(* exam01..*(..))")  //공통기능이 적용될 범위 : exam01 패키지의 모든 메서드->
				public void publicTarget() {
				}

				@Before("publicTarget()")
				public void before(JoinPoint joinPoint) {
					System.out.println("Before..");
				}

				@After("publicTarget()")
				public void after(JoinPoint joinPoint) {
					System.out.println("After..");
				}
				@AfterReturning(value= "publicTarget()", returning = "returnValue")
				public void afterReturning(JoinPoint joinPoint, Object returnValue) throws Throwable{
					System.out.println("AfterReturning.." + returnValue);
				}


				@AfterThrowing(value="publicTarget()", throwing = "e")
				public void afterThrowing(JoinPoint joinPoint, Throwable e) {
					System.out.println("AfterThrowing..");
					e.printStackTrace();
				}
				@Around("publicTarget()") //publicTarget() 메서드에 정의한 Pointcut에 공통 기능을 적용한다는 것을 의미
				public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
					
					Signature sig = joinPoint.getSignature();
					System.out.println("joinPoint.getSignature() :" + sig.toLongString());

					Object[] args = joinPoint.getArgs();
					System.out.println("joinPoint.getArgs() :" + Arrays.toString(args));

					Object obj = joinPoint.getTarget();
					System.out.println("joinPoint.getTarget() : " + obj.getClass());
					
					long stime = System.nanoTime();    //공통기능
					try {
						Object result = joinPoint.proceed(); // 핵심기능을 대신 수행해줌
						return result;
					}finally {
						long etime = System.nanoTime();  //공통기능
						System.out.println("걸린시간:" + (etime - stime));
					}
				}
			}


			public class Ex02 {
				@Test
				void test1() {
					AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);

					Calculator cal = ctx.getBean(Calculator.class);
					long result = cal.factorial(10L);
					System.out.println(result);

					ctx.close();
				}
			}

			>>>
			joinPoint.getSignature() :public abstract long exam01.Calculator.factorial(long)
			joinPoint.getArgs() :[10]
			joinPoint.getTarget() : class exam01.RecCalculator
			Before..
			AfterReturning..3628800
			After..
			걸린시간:203000
			3628800

	** 서브클래스 기반의 프록시 **
	
			@Configuration
			@EnableAspectJAutoProxy(proxyTargetClass = true) // 서브클래스 기반의 프록시
			public class AppCtx {

				@Bean
				public ProxyCalculator2 proxyCalculator() {
					return new ProxyCalculator2();
				}

				@Bean
				public Calculator calculator() {
					return new RecCalculator();
				}
			}

			@Aspect
			public class ProxyCalculator2 {

				@Pointcut("execution(* exam01..*(..))")
				public void publicTarget() {

				}

				@Around("publicTarget()")
				public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
					long stime = System.nanoTime();

					try {
						Object result = joinPoint.proceed();
						return result;
					}finally {
						long etime = System.nanoTime();
						System.out.println("걸린시간 : " + (etime - stime));
					}
				}
			}

			@Test
			void test2() {
				AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);

				RecCalculator cal = ctx.getBean(RecCalculator.class);
				long result = cal.factorial(10L);
				System.out.println(result);

				ctx.close();
			}

	3. @Order

		ProxyCache -> ProxyCalculator
		
		-> 프록시의 적용 순서, 숫자가 작은 순서 부터 적용

			@Configuration
			@EnableAspectJAutoProxy // AOP 자동설정 에너테이션 : 동적 프록시
			public class AppCtx {

				@Bean
				public  ProxyCache proxyCache() {
					return new ProxyCache();
				}

				@Bean
				public ProxyCalculator2 proxyCalculator() {
					return new ProxyCalculator2();
				}

				@Bean
				public Calculator calculator() {
					return new RecCalculator();
				}
			}
					
			@Aspect
			@Order(1)
			public class ProxyCache {
				private Map<Long, Object> data = new HashMap<>();

				@Pointcut("execution(* exam01..*(..))")
				public void publicTarget() {

				}
				@Around("publicTarget()")
				public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
					Object[] args = joinPoint.getArgs();
					Long num = (Long)args[0];

					if(data.containsKey(num)) {
						System.out.println("Cache 사용..");
						Object result = data.get(num);
						return result;
					}

					Object result = joinPoint.proceed();
					data.put(num, result);
					System.out.println("Cache 저장..");
					return result;
				}
			}
			@Aspect
			@Order(2)
			public class ProxyCalculator2 {

				@Pointcut("execution(* exam01..*(..))")
				public void publicTarget() {

				}

				@Around("publicTarget()")
				public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
					long stime = System.nanoTime();

					try {
						Object result = joinPoint.proceed();
						return result;
					}finally {
						long etime = System.nanoTime();
						System.out.println("걸린시간 : " + (etime - stime));
					}
				}
			}

			@Test
			void test1() {
				AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);

				Calculator cal = ctx.getBean(Calculator.class);
				long result = cal.factorial(10L);
				System.out.println(result);

				result = cal.factorial(10L);
				System.out.println(result);

				result = cal.factorial(10L);
				System.out.println(result);

				result = cal.factorial(10L);
				System.out.println(result);

				ctx.close();
			}
		
		
	4. 프록시 생성방식
	5. @Around의 Pointcut 설정과 @Pointcut 재사용
	
		@Around("execution(* exam01..*(..))") 
		
		또는
		
		@Aspect
		public class CommonPointcut {
			@Pointcut("execution(* exam01..*(..))")
			public void pointcut() {}
		}
		
		+ 
		
		@Around("config.CommonPointcut.publicTarget()")   // 같은 패키지명이라면 패키지명 생략도 가능함
		
		


JdbcTemplate
	DataAccessException 

	1. 설치 및 설정 (의존성)
		1) spring-jdbc  ( spring과 동일버전 )
			implementation 'org.springframework:spring-jdbc:6.1.10'

		2) tomcat-jdbc
			implementation 'org.apache.tomcat:tomcat-jdbc:10.1.25'

			- 커넥션 풀 
				- 미리 연결 객체를 여러개 생성해서 필요할때마다 빌려주고, 회수하는 방식 
				- 반응성, 성능 향상의 효과 
				
				- DataSource interface 사용 (javax.sql.DataSource)
				
				- HikariCp 

		
		+ spring-context
		+ lombok
		+ spring-test
		  testImplementation 'org.springframework:spring-test:6.1.10'

		+ ojdbc11
		  implementation 'com.oracle.database.jdbc:ojdbc11:23.4.0.24.05'
		  -> 
				runtimeOnly 'com.oracle.database.jdbc:ojdbc11:23.4.0.24.05'

			--------------------------------------------------------------------------
			build.gradle
			--------------------------------------------------------------------------
			ext {
			   springVersion = '6.1.10'
			}
			dependencies {
				implementation "org.springframework:spring-context:$springVersion"
				implementation "org.springframework:spring-jdbc:$springVersion"
				implementation 'org.apache.tomcat:tomcat-jdbc:10.1.25'
				runtimeOnly 'com.oracle.database.jdbc:ojdbc11:23.4.0.24.05'

				compileOnly 'org.projectlombok:lombok:1.18.34'
				annotationProcessor 'org.projectlombok:lombok:1.18.34'

				testImplementation "org.springframework:spring-test:$springVersion"
				testImplementation platform('org.junit:junit-bom:5.10.0')
				testImplementation 'org.junit.jupiter:junit-jupiter'
			}
			--------------------------------------------------------------------------


	2. DataSource 설정 
		- javax.sql.DataSource
		- 연결 유효성 체크 관련 메서드 - TomcatJDBC
			SQL> create user SPRING identified by oracle quota unlimited on users;
			SQL> grant connect, resource to SPRING;
			
			CREATE TABLE MEMBER (
				SEQ number(11) PRIMARY KEY,
				EMAIL varchar2(60) NOT NULL UNIQUE,
				PASSWORD VARCHAR2(65) NOT NULL,
				USER_NAME VARCHAR2(40) NOT NULL,
				REG_DT DATE DEFAULT SYSDATE
			);
			
			CREATE SEQUENCE seq_member;
			
			

	3. JdbcTemplate을 이용한 쿼리실행 
	
		1) query() : select문
		
		- List query(String sql, RowMapper rowMapper)
		- List query(String sql, Object[] args, RowMapper rowMapper)
		- List query(String sql, RowMapper rowMapper, Object... args)

		2) queryForObject() : 단일 데이터 조회시
			- 조회 결과가 반드시 한개 레코드 이어야 함, 0이거나 복수면 에러 -> try.. catch 쓰기
			

		3) update() : insert, update, delete : 반환값 : 반영된 레코드 갯수
		
			- int update(String sql)
			- int update(String sql, Object... args)  
				: PreparedStatement 방식으로 쿼리 작성
				: 값 바인딩 ?, ?, ?

		로거
		
		slf4j-api/logback classic 의존성
		
		implementation 'org.slf4j:slf4j-api:2.0.13'
		testImplementation 'ch.qos.logback:logback-classic:1.5.6' 
		-> implementation 'ch.qos.logback:logback-classic:1.5.6'

		src/main/resources/logback.xml

			<?xml version="1.0" encoding="UTF-8" ?>
			<configuration>
				<appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
					<encoder>
						<pattern>%d %5p %c{2} - %m%n</pattern> // 5p : 로그레벨, m: 로그메세지 
					</encoder>
				</appender>
				<root level="INFO">
					<appender-ref ref="stdout" />
				</root>
				
				<logger name="org.springframework.jdbc" level="TRACE" />  // 로그레벨 자세한 TRACE(DEBUG) 레벨로
			</configuration>
				
			

			@Data
			@Builder
			public class Member {
				private long seq;
				private String email;
				private String password;
				private String userName;
				private LocalDateTime regDt;
			}
			package config;

			import org.apache.tomcat.jdbc.pool.DataSource;
			import org.springframework.context.annotation.Bean;
			import org.springframework.context.annotation.Configuration;
			import org.springframework.jdbc.core.JdbcTemplate;

			@Configuration
			public class AppCtx {

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
			}

			@ExtendWith(SpringExtension.class)
			@ContextConfiguration(classes = AppCtx.class)
			public class DBConnectionTest {
				@Autowired
				private DataSource dataSource;

				@Test
				void test1() throws SQLException {
					Connection conn = dataSource.getConnection();
					System.out.println(conn);
				}
			}

			@ExtendWith(SpringExtension.class)
			@ContextConfiguration(classes = AppCtx.class)
			public class Ex01 {
				@Autowired
				private JdbcTemplate jdbcTemplate;

				@Test
				void test1() {
					String sql = "insert into member (seq, email, password, user_name) " +
							" values (SEQ_MEMBER.NEXTVAL, ?, ?, ?)";

					int result = jdbcTemplate.update(sql, "user03@test.com", "123456", "사용자03");
					System.out.println(result);
				}

				@Test
				void test2() {
					List<Member> members = jdbcTemplate.query("select * from member",
						this::mapper);
					System.out.println(members);
				}

				@Test
				void test3() {
					String email = "user05@test.com";
					try {
						Member member = jdbcTemplate.queryForObject("select * from member where email = ?",
								(rs, num) -> mapper(rs, num), email);
						System.out.println(member);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				private Member mapper(ResultSet rs, int num) throws SQLException {
					return  Member.builder().seq(rs.getLong("seq"))
							.email(rs.getString("email"))
							.password(rs.getString("password"))
							.userName(rs.getString("user_name"))
							.regDt(rs.getTimestamp("reg_dt").toLocalDateTime())
							.build();
				}

				@Test
				void test4() {
					int total = jdbcTemplate.queryForObject("select count(*) from member", int.class);
					System.out.println(total);
				}
			}


day04 7/10




	4. PreparedStatementCreator를 이용한 쿼리 실행
		-> connection 객체을 매개변수로 .. 정의된 메서드를 통해 사용가능함
		-> 증감번호 등 자동생성되는 숫자타입의 PK 가져올 때 사용
		
	5. INSERT 쿼리 실행 시 KeyHolder를 이용해서 자동 생성 키값 구하기
	
	6. 스프링의 익셉션 변환 처리
		- 각 연동 기술에 따라 발생하는 익셉션을 스프링이 제공하는 익셉션으로 변환함으로써 다음과 같이 구현 기술에 상관없이 동일한 코드로 익셉션을 처리할 수 있게 된다.
		SQLExcpetion, HibernateException, PersistenceException ->  DataAccessException(RuntimeException)
		
	7. 트랜잭션 처리 - 수동관리
	
		- @Transactional
		  : 클래스명 위에, 메서드명 위에
		  : 테스트에 쓰면 자동 롤백됨
		
			Connection - setAutoCommit(false);  // 공통기능
			SQL1.. //핵심기능
			SQL2..
			SQL2..
			Connection - commit();
	
		  : 필요한 설정 
			@EnableTransactionManagement : AppCtx 클래스에 추가
			
			@Bean
			public PlatformTransactionManager transactionManager() {
				DataSourceTransactionManager tm = new DataSourceTransactionManager();
				tm.setDataSource(dataSource());
				return tm;
			}
			
스프링 데이터 프레임워크
	의존성 spring data JDBC 검색 -> Spring Data JDBC » 3.3.1
	implementation 'org.springframework.data:spring-data-jdbc:3.3.1'

	문서 spring data api docs 구글링 
	
	@EnableJdbcRepositories 에너테이션 추가 - AppCtx.java
	
	Member 클래스의 seq위에 @Id 붙이기
	
	
	Spring Data JDBC
	 - 쿼리 메서드
	 -CrudRepository 
	

JDBC와 커넥션 풀 설정
	1. JDBC 연결

	2. 커넥션 풀 설정
	1) Tomcat JDBC를 사용한 설정
	2) HikariCP를 사용한 설정


MyBatis와 스프링 연동

	1. MyBatis 관련 라이브러리 추가
	
		1) spring-jdbc/spring-tx 
		
		2) mybatis/mybatis-spring
		
			의존성 추가 
			implementation 'org.mybatis:mybatis:3.5.16'
			implementation 'org.mybatis:mybatis-spring:3.0.3'



	2. SQLSessionFactory
		mybatis-spring
		  SqlSessionFactory -> setDataSource(..)
		  
		  @Bean

		@Bean
		public SqlSessionFactory sqlSessionFactory() throws Exception {
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
			sqlSessionFactoryBean.setDataSource(dataSource());

			SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
			return sqlSessionFactory;
		}
		
		public interface MemberMapper {
			
			@Select("select count(*) from member")   //간단한 쿼리는 mapper.xml 안쓰고 이렇게 가능함
			long getTotal();
		}

		@ExtendWith(SpringExtension.class)
		@ContextConfiguration(classes = AppCtx.class)
		public class Ex03 {
			@Autowired
			private MemberMapper memberMapper;

			@Test
			void test1() {
				long total = memberMapper.getTotal();
				System.out.println(total);
			}
		}

	3. 스프링과의 연동 처리
		1) Mapper 인터페이스
		2) XML 매퍼와 함께 사용
		
		
		jbcrypt 의존성 implementation 'org.mindrot:jbcrypt:0.4'

			@Configuration
			@EnableTransactionManagement
			@MapperScan("mappers")
			@ComponentScan("member")
			public class AppCtx {

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

			public interface MemberMapper {

				@Select("select count(*) from member")
				long getTotal();

				int register(Member member);
				Member get(String email);
				int exists(String email);
			}

			MemberMapper.xml

			<?xml version="1.0" encoding="UTF-8" ?>
			<!DOCTYPE mapper
					PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
					"https://mybatis.org/dtd/mybatis-3-mapper.dtd">
			<mapper namespace="mappers.member.MemberMapper">
				<resultMap id="memberMap" type="member.entities.Member">
					<result column="seq" property="seq" />
					<result column="email" property="email"/>
					<result column="password" property="password"/>
					<result column="user_name" property="userName"/>
					<result column="reg_dt" property="regDt"/>
				</resultMap>

				<insert id="register">
					insert into member (seq, email, password, user_name)
					values (seq_member.nextval, #{email}, #{password}, #{userName})
				</insert>

				<select id="get" resultMap="memberMap">
					select seq, email, password, user_name, reg_dt from member where email = #{email}
				</select>

				<select id="exists" resultType="int">
					select count(*) from member where email = #{email}
				</select>
			</mapper>

			@Transactional
			@ExtendWith(SpringExtension.class)
			@ContextConfiguration(classes = AppCtx.class)
			public class Ex04 {

				@Autowired
				private JoinService joinService;

				@Test
				void test1() {
					RequestJoin form = new RequestJoin();
					form.setEmail("user99@test.com");
					form.setPassword("12345678");
					form.setConfirmPassword("12345678");
					form.setUserName("사용자99");
					form.setAgree(true);
					joinService.process(form);
				}
			}		

	4. slf4j 설정
			<?xml version="1.0" encoding="UTF-8" ?>
			<configuration>
				<appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
					<encoder>
						<pattern>%d %5p %c{2} - %m%n</pattern>
					</encoder>
				</appender>
				<root level="INFO">
					<appender-ref ref="stdout" />
				</root>

				<logger name="org.springframework.jdbc" level="TRACE" />
				<logger name="mappers" level="DEBUG" />
			</configuration>

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
					registry.addViewController("/mypage/**").setViewName("mypage/index");
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


스프링 MVC 
	1. 세션
		
		 - @SessionAttribute("이름")
		   : 세션값 조회 및 주입
		   
		 - @SessionAttributes("이름") 
		   : Model로 해당이름으로 속성 추가하면 세션에도 동일한 이름으로 추가
		   : 세션에 해당이름의 값이 있으면 Model에 자동 추가
		   : Magic Form - 여러 페이지의 양식
		
		   - SessionStatus
			  : setComplete() : @SessionAttributes에 지정된 이름의 세션값을 비울때
			  
			  

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
		@RequestMapping("logiout")
		public  String logout(HttpSession session) {
			session.invalidate();
			return "redirect:/member/login";
		}


	2.인터셉터
		1) HandlerInterceptor 인터페이스 
		- boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
		- void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception;
		- void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception;
		2) WebMvcConfigurer 인터페이스 : addInterceptors(InterceptorRegistry registry)
		3) Ant 경로 패턴
		- * : 0개 또는 그 이상의 글자
		- ** 0개 또는 그 이상의 폴더 경로
		- ? : 1개 글자

	3.쿠키
	
	  @CookieValue
	
	
	
