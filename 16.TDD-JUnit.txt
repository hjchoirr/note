
	테스트 : 단위테스트(기능단위별:JUnit), 통합테스트

JUnit 5 기초1

1. 의존성 

	1) maven 기준 
		junit-jupiter - dependency에 추가
		maven-surefire-plugin - plugin (백그라운드 프로그램 추가)

	2) gradle 기준

		dependencies {
			testImplementation 'org.junit.jupiter:junit-jupiter-api:버전'
		}

		test {
			useJUnitPlatform()
		}


		maven repository에서 junit jupiter 검색 : 
		JUnit Jupiter (Aggregator) » 5.10.2
		
		    testImplementation platform('org.junit:junit-bom:5.10.2')  // 5.10.0 -> 5.10.2

		seeings - UTF-8 
		
		Project Lombok » 1.18.32
		compileOnly 'org.projectlombok:lombok:1.18.32'

			compileOnly 'org.projectlombok:lombok:1.18.32'
			annotationProcessor 'org.projectlombok:lombok:1.18.32'

		sync 버튼
		

2. @Test 애노테이션
	1) 테스트로 사용할 클래스를 만들고 @Test 애노테이션을 메서드를 붙이면 테스트 가능 
	2) @Test 애노테이션을 붙인 메서드는 private이면 안 된다. (default 이상)

	참고) 테스트용 클래스 -> 명칭+Test 관례
	
	
3. 테스트 메서드 : 주요 단언 메서드

	1) assertEquals(expected, actual) : 예상값(expected)이 실제값(actual) 과 일치하는지 
		: 동등성비교 ( equals, hashcode)
	
	2) assertNotEquals(unexpected, actual) : 일치하지 않으면 통과
	
	3) assertSame (Object expected, Object actual) 
		: 예상했던 객체와 실제값이 일치하는지 체크 (==) 
		: 동일성비교 - 주소비교
		
	4) assertNotSame(Object unexpected, Object actual)
		
	5) assertTrue(boolean condition)
		: true 나오면 통과
		
	6) assertFalse(boolean condition)
		: false 나오면 통과
		
	7) assertNull(Object actual) : null이 나오면 통과
	8) assertNotNull(Object actual) 
	
	9) fail() : 특정 상황에서 테스트 실패를 유도하는 경우
	
	10) assertThrows(Class<T> expectedType, Executable executable)
		: 예외발생여부 - 예상했던 예외가 나오는지
		: expectedType - 발생예상되는 예외 클래스 클래스
		: executable - 실행코드
		
	11) assertDoesNotThrow(Executable executable)
		: executable - 실행코드 
		: 실행코드 실행시 예외가 발생하지 않으면 통과
		
	12) assertAll(Executable... executable)
		: 가변적인 갯수의 실행코드..
		
	
		
		import static org.junit.jupiter.api.Assertions.*;  => 이렇게 임포트

		--------------------------------------------------
		package exam01;
		import org.junit.jupiter.api.Test;
		import java.time.LocalDate;
		import static org.junit.jupiter.api.Assertions.*;

		@DisplayName("테스트들...")
		public class Ex01Test {
			@Test
			void test1() {
				Calculator cal = new Calculator();
				int num1 = 10, num2 = 20;
				int result = cal.add(num1, num2);

				assertEquals(30, result);
			}
			@Test
			void test2() {
				LocalDate expected = LocalDate.now();
				LocalDate date = LocalDate.of(2024, 6, 5);
				assertEquals(expected, date); //동등성비교  - pass
				assertSame(expected, date); // 동일성비교 - Test failed 
			}
			
			@Test
			void test3() {
				fail();   // Test failed 
			}
			
			@DisplayName("테스트 4")
			@Test
			void test4() {

			}
			
			@DisplayName("테스트--5")
			@Test
			@Disabled
			void test5() {

			}			
		}
		--------------------------------------------------


	(참고)
	TDD - Test Driven Development ( 테스트주도개발 )
	테스트를 통한 설계방식
	1) 테스트하기위한 시나리오 
		-> 필요한 기능 개발 -> 테스트실행 -> 통과하지 않으면 -> 기능보완(리팩토링) -> 통과하면 -> 기능완성
		
		간단한 시나리오부터 복잡한 시나리오로
		지속적인 리팩토링
		

			-----------------------------------
			package tests;
			import member.services.JoinService;
			import org.junit.jupiter.api.DisplayName;
			import org.junit.jupiter.api.Test;
			import static org.junit.jupiter.api.Assertions.*;

			@DisplayName("회원가입기능테스트")
			public class JoinServiceTest {
				 @Test
				 @DisplayName("회원가입성공시 예외가 발생하지 않음")
				 void successTest() {
					  assertDoesNotThrow(()-> {
						   JoinService service = new JoinService();
						   service.process();
					  });
				 }
			}
			=====================================
			=====================================
			package member.services;

			import global.exceptions.ValidationException;
			import member.controllers.RequestJoin;

			public class JoinService {
				public void process(RequestJoin form) {
					String email = form.getEmail();
					if(email == null || email.isBlank()) {
						throw new ValidationException("이메일을 입력하세요");
					}

					String password = form.getPassword();
					if(password == null || password.isBlank()) {
						throw new ValidationException("암호를 입력하세요");

					}
				}
			}
			-----------------------------------
			package member.controllers;
			import lombok.Builder;
			import lombok.Data;

			@Data   // getter, setter, toString equals 자동추가
			@Builder
			public class RequestJoin {
				private String email;
				private String password;
				private String confirmPassword;
				private String userName;
				private boolean termsAgree; //약관동의여부
			}
			-----------------------------------
			package global.exceptions;

			public class ValidationException extends RuntimeException{
				public ValidationException(String message) {
					super(message);
				}
			}
			-----------------------------------
			package tests;
			import global.exceptions.ValidationException;
			import member.controllers.RequestJoin;
			import member.services.JoinService;
			import org.junit.jupiter.api.DisplayName;
			import org.junit.jupiter.api.Test;
			import static org.junit.jupiter.api.Assertions.*;

			@DisplayName("회원가입기능테스트")
			public class JoinServiceTest {
				 @Test
				 @DisplayName("회원가입성공시 예외가 발생하지 않음")
				 void successTest() {
					  assertDoesNotThrow(()-> {
						   JoinService service = new JoinService();
						   RequestJoin form = RequestJoin.builder().build();
						   service.process(form);
					  });
				 }
				 @Test
				 @DisplayName("필수항목체크(이메일, 비번, 비번확인, 회원명)-검증 실패시 ValidationException 발생")
				 void requiredFieldTest() {
					  // 이메일 필수 체크
					  assertThrows(ValidationException.class, () -> {
						   RequestJoin form = RequestJoin.builder().build();
						   JoinService service = new JoinService();
						   service.process(form);
					  });
				 }
			}
			-------------------------------------

	JoinService에 검증루틴 너무 반복되므로 리팩토링 필요함 - > 지네릭
	6/5 11시 동영상( 소스: 9. 웹 서버 프로그램 구현/day01 ) - 리팩토링 과정 
	(11시 https://drive.google.com/drive/folders/1KMDxdZMVbPpXH-McC0PuiwyC3rJzK6uN )
	(12시 https://drive.google.com/drive/folders/1KMDxdZMVbPpXH-McC0PuiwyC3rJzK6uN )

			package global.validators;

			public interface Validator<T> {
				void check(T form);
			}


4. 테스트 라이프사이클

	1) @BeforeEach 애노테이션 : 각각의 단위테스트 메서드 실행전에 항상 실행
	2) @AfterEach 애노테이션 : 각각의 단위테스트 메서드 실행후에 항상 실행
	3) @BeforeAll 애노테이션 : 모든 단위테스트 메서드 실행 전에 한번 실행 : 정적 매서드로 정의
	4) @AfterAll 애노테이션 :  모든 단위테스트 메서드 실행 후에 한번 실행: 정적 매서드로 정의
		------------------------------
		package exam01;
		import org.junit.jupiter.api.AfterEach;
		import org.junit.jupiter.api.BeforeEach;
		import org.junit.jupiter.api.Test;

		public class Ex02Test {
			@BeforeEach
			void beforeEach() {
				System.out.println("BEFORE EACH--!");
			}
			@AfterEach
			void afterEach() {
				System.out.println("AFTER EACH--!");
			}
			@Test
			void test1() {
				System.out.println("테스트1번 진행");
			}

			@Test
			void test2() {
				System.out.println("테스트2번 진행");

			}
		}
		------------------------------

5. 추가 애노테이션
	1) @DisplayName - 보여주는 테스트명
	2) @Disabled - 테스트배제
	
	lombok 의존성 test 가능하도록 추가 -> build.gradle
	
    testCompileOnly 'org.projectlombok:lombok:1.18.32'
    testAnnotationProcessor 'org.projectlombok:lombok:1.18.32'
	


		package tests;

		import global.exceptions.ValidationException;
		import member.controllers.RequestJoin;
		import member.services.JoinService;
		import member.validators.JoinValidator;
		import org.junit.jupiter.api.DisplayName;
		import org.junit.jupiter.api.Test;
		import static org.junit.jupiter.api.Assertions.*;

		@DisplayName("회원가입기능테스트")
		public class JoinServiceTest {
			 
			 @Test
			 @DisplayName("회원가입성공시 예외가 발생하지 않음")
			 void successTest() {
				  assertDoesNotThrow(()-> {
					   JoinService service = new JoinService(new JoinValidator());
					   RequestJoin form = RequestJoin.builder().build();
					   service.process(form);
				  });
			 }
			 @Test
			 @DisplayName("필수항목체크(이메일, 비번, 비번확인, 회원명)-검증 실패시 ValidationException 발생")
			 void requiredFieldTest() {
				  // 이메일 필수 체크
				  assertThrows(ValidationException.class, () -> {
					   RequestJoin form = RequestJoin.builder().build();
					   JoinService service = new JoinService(new JoinValidator());
					   service.process(form);
				  });
			 }
		}

		=================
		package tests;

		import global.exceptions.ValidationException;
		import member.controllers.RequestJoin;
		import member.services.JoinService;
		import member.validators.JoinValidator;
		import org.junit.jupiter.api.BeforeEach;
		import org.junit.jupiter.api.DisplayName;
		import org.junit.jupiter.api.Test;
		import static org.junit.jupiter.api.Assertions.*;

		@DisplayName("회원가입기능테스트")
		public class JoinServiceTest {
			 private JoinService joinService;


			 @BeforeEach
			 void init() {
				  joinService = new JoinService(new JoinValidator());
			 }
			 RequestJoin getData() {
				  return RequestJoin.builder()
						  .email("test" + System.currentTimeMillis() + "@test.org")
						  .password("12345678")
						  .confirmPassword("12345678")
						  .userName("사용자")
						  .termsAgree(true)
						  .build();
			 }

			 @Test
			 @DisplayName("회원가입성공시 예외가 발생하지 않음")
			 void successTest() {
				  assertDoesNotThrow(()-> {
					   joinService.process(getData());
				  });
			 }
			 @Test
			 @DisplayName("필수항목체크(이메일, 비번, 비번확인, 회원명)-검증 실패시 ValidationException 발생")
			 void requiredFieldTest() {
				  // 이메일 필수 체크
				  assertThrows(ValidationException.class, () -> {
					  RequestJoin form = getData();
					  //null체크
					  form.setEmail(null);
					  joinService.process(form);

					   //공백체크
					   form.setEmail("  ");
					   joinService.process(form);
				  });
				  assertThrows(ValidationException.class, () -> {
					   RequestJoin form = getData();
					   form.setUserName(null);

					   form.setPassword(null);
					   joinService.process(form);

					   form.setPassword("  ");
					   joinService.process(form);
				  });
			 }
		}

		package tests;

		import global.exceptions.ValidationException;
		import member.controllers.RequestJoin;
		import member.services.JoinService;
		import member.validators.JoinValidator;
		import org.junit.jupiter.api.BeforeEach;
		import org.junit.jupiter.api.DisplayName;
		import org.junit.jupiter.api.Test;
		import static org.junit.jupiter.api.Assertions.*;

		@DisplayName("회원가입기능테스트")
		public class JoinServiceTest {
			 private JoinService joinService;


			 @BeforeEach
			 void init() {
				  joinService = new JoinService(new JoinValidator());
			 }
			 RequestJoin getData() {
				  return RequestJoin.builder()
						  .email("test" + System.currentTimeMillis() + "@test.org")
						  .password("12345678")
						  .confirmPassword("12345678")
						  .userName("사용자")
						  .termsAgree(true)
						  .build();
			 }

			 @Test
			 @DisplayName("회원가입성공시 예외가 발생하지 않음")
			 void successTest() {
				  assertDoesNotThrow(()-> {
					   joinService.process(getData());
				  });
			 }
			 @Test
			 @DisplayName("필수항목체크(이메일, 비번, 비번확인, 회원명)-검증 실패시 ValidationException 발생")
			 void requiredFieldTest() {
				  // 이메일 필수 체크
				  ValidationException thrown = assertThrows(ValidationException.class, () -> {
					  RequestJoin form = getData();

					  //null체크
					  form.setEmail(null);
					  joinService.process(form);

					   //공백체크
					   form.setEmail("  ");
					   joinService.process(form);
				  });

				  String message = thrown.getMessage();
				  assertTrue(message.contains("이메일"));


				  thrown = assertThrows(ValidationException.class, () -> {
					   RequestJoin form = getData();
					   
					   form.setUserName(null);    // =======> test fail
					   //form.setPassword(null);
					   joinService.process(form);

					   form.setPassword("  ");
					   joinService.process(form);
				  });
				  message = thrown.getMessage();
				  assertTrue(message.contains("암호"));
			 }
		}
		=======리팩토링==============================================
		package tests;

		import global.exceptions.ValidationException;
		import member.controllers.RequestJoin;
		import member.services.JoinService;
		import member.validators.JoinValidator;
		import org.junit.jupiter.api.BeforeEach;
		import org.junit.jupiter.api.DisplayName;
		import org.junit.jupiter.api.Test;
		import static org.junit.jupiter.api.Assertions.*;

		@DisplayName("회원가입기능테스트")
		public class JoinServiceTest {
			 private JoinService joinService;


			 @BeforeEach
			 void init() {
				  joinService = new JoinService(new JoinValidator());
			 }
			 RequestJoin getData() {
				  return RequestJoin.builder()
						  .email("test" + System.currentTimeMillis() + "@test.org")
						  .password("12345678")
						  .confirmPassword("12345678")
						  .userName("사용자")
						  .termsAgree(true)
						  .build();
			 }

			 @Test
			 @DisplayName("회원가입성공시 예외가 발생하지 않음")
			 void successTest() {
				  assertDoesNotThrow(()-> {
					   joinService.process(getData());
				  });
			 }
			 @Test
			 @DisplayName("필수항목체크(이메일, 비번, 비번확인, 회원명)-검증 실패시 ValidationException 발생")
			 void requiredFieldTest() {
				  // 이메일 필수 체크
				  ValidationException thrown = assertThrows(ValidationException.class, () -> {
					  RequestJoin form = getData();

					  //null체크
					  form.setEmail(null);
					  joinService.process(form);

					   //공백체크
					   form.setEmail("  ");
					   joinService.process(form);
				  });

				  String message = thrown.getMessage();
				  assertTrue(message.contains("이메일"));


				  thrown = assertThrows(ValidationException.class, () -> {
					   RequestJoin form = getData();

					   form.setPassword(null);
					   joinService.process(form);

					   form.setPassword("  ");
					   joinService.process(form);
				  });
				  message = thrown.getMessage();
				  assertTrue(message.contains("암호"));
			 }

			 void requiredFieldEachTest(RequestJoin form, String keyword){
				 ValidationException thrown = assertThrows(ValidationException.class, () -> {
					   joinService.process(form);
				  });
				 String message = thrown.getMessage();
				 assertTrue(message.contains(keyword));
			 }
		}
		======리팩토링===============================
		package tests;

		import global.exceptions.ValidationException;
		import member.controllers.RequestJoin;
		import member.services.JoinService;
		import member.validators.JoinValidator;
		import org.junit.jupiter.api.BeforeEach;
		import org.junit.jupiter.api.DisplayName;
		import org.junit.jupiter.api.Test;
		import static org.junit.jupiter.api.Assertions.*;

		@DisplayName("회원가입기능테스트")
		public class JoinServiceTest {
			 private JoinService joinService;


			 @BeforeEach
			 void init() {
				  joinService = new JoinService(new JoinValidator());
			 }
			 RequestJoin getData() {
				  return RequestJoin.builder()
						  .email("test" + System.currentTimeMillis() + "@test.org")
						  .password("12345678")
						  .confirmPassword("12345678")
						  .userName("사용자")
						  .termsAgree(true)
						  .build();
			 }

			 @Test
			 @DisplayName("회원가입성공시 예외가 발생하지 않음")
			 void successTest() {
				  assertDoesNotThrow(()-> {
					   joinService.process(getData());
				  });
			 }
			 @Test
			 @DisplayName("필수항목체크(이메일, 비번, 비번확인, 회원명)-검증 실패시 ValidationException 발생")
			 void requiredFieldTest() {
				  // 이메일 필수 체크
				  RequestJoin form = getData();
				  form.setEmail(null);
				  requiredFieldEachTest(form, "이메일");

				  form.setEmail("  ");
				  requiredFieldEachTest(form, "이메일");

				  form = getData();
				  form.setPassword(null);
				  requiredFieldEachTest(form, "암호");

				  form.setPassword(" ");
				  requiredFieldEachTest(form, "암호");

			 }

			 void requiredFieldEachTest(RequestJoin form, String keyword){
				 ValidationException thrown = assertThrows(ValidationException.class, () -> {
					   joinService.process(form);
				  });
				 String message = thrown.getMessage();
				 assertTrue(message.contains(keyword));
			 }
		}
		==================================================
		
테스트 실패하면 그 다음은 실행되지 않음... => assertAll(Executable... executable)


		 void requiredFieldTest() {
			  assertAll(
					  () -> {
						   RequestJoin form = getData();
						   form.setEmail(null);
						   requiredFieldEachTest(form, "이메일");
						   form.setEmail("   ");
						   requiredFieldEachTest(form, "이메일");
					  },
					  () -> {
						   RequestJoin form = getData();
						   form.setPassword(null);
						   requiredFieldEachTest(form, "암호");
						   form.setPassword("  ");
						   requiredFieldEachTest(form, "암호");
					  },
					  () -> {
						  RequestJoin form = getData();
						  form.setConfirmPassword(null);
						  requiredFieldEachTest(form, "암호확인");
						  form.setConfirmPassword("  ");
						  requiredFieldEachTest(form, "암호확인");
					  },
					  () -> {
						   RequestJoin form = getData();
						   form.setUserName(null);
						   requiredFieldEachTest(form, "이름");
						   form.setUserName("  ");
						   requiredFieldEachTest(form, "이름");
					  }
			  );
		 }


		==================================
		package global.validators;

		public interface RequiredValidator {
			default void checkRequired(String field, RuntimeException e){
				if(field == null || field.isBlank()) {
					throw e;
				}
			}
		}


		package member.validators;
		import global.exceptions.ValidationException;
		import global.validators.RequiredValidator;
		import global.validators.Validator;
		import member.controllers.RequestJoin;

		public class JoinValidator implements Validator<RequestJoin>, RequiredValidator {

			@Override
			public void check(RequestJoin form) {
				String email = form.getEmail();
				String password = form.getPassword();
				String confirmPassword = form.getConfirmPassword();
				String userName = form.getUserName();
				boolean termsAgree = form.isTermsAgree();

				checkRequired(email, new ValidationException("이메일을 입력하세요."));
				checkRequired(password, new ValidationException("암호를 입력하세요."));
				checkRequired(confirmPassword, new ValidationException("암호확인을 입력하세요."));
				checkRequired(userName, new ValidationException("이름을 입력하세요."));

				if(!termsAgree) {
					throw new ValidationException("약관에 동의하세요");
				}
			}
		}

		package tests;

		import global.exceptions.ValidationException;
		import member.controllers.RequestJoin;
		import member.services.JoinService;
		import member.validators.JoinValidator;
		import org.junit.jupiter.api.BeforeEach;
		import org.junit.jupiter.api.DisplayName;
		import org.junit.jupiter.api.Test;
		import static org.junit.jupiter.api.Assertions.*;

		@DisplayName("회원가입기능테스트")
		public class JoinServiceTest {
			 private JoinService joinService;


			 @BeforeEach
			 void init() {
				  joinService = new JoinService(new JoinValidator());
			 }
			 RequestJoin getData() {
				  return RequestJoin.builder()
						  .email("test" + System.currentTimeMillis() + "@test.org")
						  .password("12345678")
						  .confirmPassword("12345678")
						  .userName("사용자")
						  .termsAgree(true)
						  .build();
			 }

			 @Test
			 @DisplayName("회원가입성공시 예외가 발생하지 않음")
			 void successTest() {
				  assertDoesNotThrow(()-> {
					   joinService.process(getData());
				  });
			 }
			 @Test
			 @DisplayName("필수항목체크(이메일, 비번, 비번확인, 회원명)-검증 실패시 ValidationException 발생")
			 void requiredFieldTest() {
				  assertAll(
						  () -> {
							   RequestJoin form = getData();
							   form.setEmail(null);
							   requiredFieldEachTest(form, "이메일");
							   form.setEmail("   ");
							   requiredFieldEachTest(form, "이메일");
						  },
						  () -> {
							   RequestJoin form = getData();
							   form.setPassword(null);
							   //form.setUserName(null);
							   requiredFieldEachTest(form, "암호");
							   form.setPassword("  ");
							   requiredFieldEachTest(form, "암호");
						  },
						  () -> {
							  RequestJoin form = getData();
							  form.setConfirmPassword(null);
							  requiredFieldEachTest(form, "암호확인");
							  form.setConfirmPassword("  ");
							  requiredFieldEachTest(form, "암호확인");
						  },
						  () -> {
							   RequestJoin form = getData();
							   form.setUserName(null);
							   requiredFieldEachTest(form, "이름");
							   form.setUserName("  ");
							   requiredFieldEachTest(form, "이름");
						  },
						  //약관동의 검증
						  () -> {
							   RequestJoin form = getData();
							   form.setTermsAgree(false);
							   requiredFieldEachTest(form, "약관에 동의");
						  }
				  );
			 }

			 void requiredFieldEachTest(RequestJoin form, String keyword){
				 ValidationException thrown = assertThrows(ValidationException.class, () -> {
					   joinService.process(form);
				  });
				 String message = thrown.getMessage();
				 assertTrue(message.contains(keyword));
			 }
		}


		==================================
		----------------------------------------------
		package global.validators;

		public interface RequiredValidator {
			default void checkRequired(String field, RuntimeException e){
				if(field == null || field.isBlank()) {
					throw e;
				}
			}
			default void checkTrue(boolean result, RuntimeException e) {
				if(!result) {
					throw e;
				}
			}
		}
		package member.services;
		import global.validators.Validator;
		import member.controllers.RequestJoin;
		import member.validators.JoinValidator;

		public class JoinService  {
			// 구성방식으로 .. 조립기..
			private Validator<RequestJoin> validator = new JoinValidator();

			// 생성자매개변수로 받아서 의존성 주입
			public JoinService(Validator<RequestJoin> validator) {
				this.validator = validator;
			}
			public void process(RequestJoin form) {
				validator.check(form);
			}
		}

		package member.validators;
		import global.exceptions.ValidationException;
		import global.validators.RequiredValidator;
		import global.validators.Validator;
		import member.controllers.RequestJoin;

		public class JoinValidator implements Validator<RequestJoin>, RequiredValidator {

			@Override
			public void check(RequestJoin form) {
				String email = form.getEmail();
				String password = form.getPassword();
				String confirmPassword = form.getConfirmPassword();
				String userName = form.getUserName();
				boolean termsAgree = form.isTermsAgree();

				checkRequired(email, new ValidationException("이메일을 입력하세요."));
				checkRequired(password, new ValidationException("암호를 입력하세요."));
				checkRequired(confirmPassword, new ValidationException("암호확인을 입력하세요."));
				checkRequired(userName, new ValidationException("이름을 입력하세요."));
				checkTrue(termsAgree, new ValidationException("약관에 동의하세요"));

			}
		}

		이런거 더 추가 하여 ... 
		@Test
		@DisplayName("비밀번호와 비밀번호 확인 일치테스트, 검증실패시 ValidationException 발생")
		void passwordMatchTest() {

		}
		@Test
		@DisplayName("이메일 중복여부 테스트, 검증실패시 DuplicatedMemberException 발생")
		void duplicateEmailTest() {

		}

		@Test
		@DisplayName("회원가입 완료 후 실제 데이터베이스에서 조회되는 지 검증")
		void memberExistsTest() {
		  //가입처리
		  
		  //getData()로 생성한 테이터의 email항목으로 DB조회
		  //조회한 회원데이터 이메일과 getData()로 생성된 이메일이 일치하는지 체크
		  //assertEquals(...)
		  
		  
		}
		==================================



6. 모든 테스트 실행하기
	1) mvn test
		mvn package : 컴파일 -> 테스트 -> 배포(jar)
		
	2) gradle test
		gradle build : 컴파일 -> 테스트 -> 배포(jar)
		gradle test
		
		

JUnit 5 기초2

1. 조건에 따른 테스트

	1) @EnabledOnOs, @DisabledOnOs
	2) @EnabledOnJre, @DisabledOnJre
	3) @EnabledIfSystemProperty, @DisabledIfSystemProperty
	4) @EnabledIfEnvironmentVariable, @DisabledIfEnvironmentVariable
	
	public class OsTmpPathTest {
		@Test
		@EnabledOnOs(OS.WINDOWS)
		void windowTmpPath() {
			Path tmpPath = Paths.get("C:\\Temp");
			assertTrue(Files.isDirectory(tmpPath));
		}
		
		@Test
		@EnabledOnOs(OS.LINUX)
		void linuxTmpPath() {
			Path tmpPath = Paths.get("/tmp");
			assertTrue(Files.isDirectory(tmpPath));
		}
	}	
	
	@Test
	@EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11})
	void testOnJre() {
		assertEquals(LocalDate.of(1919,3,1), LocalDate.of(2019, 3, 1).minusYears(100));
	}

2. 태깅과 필터링
	1) @Tag 애노테이션은 태스크에 태그를 달 때 사용한다. @Tag 애노테이션은 클래스와 테스트 메서드에 적용할 수 있다.
	2) @Tag 애노테이션을 이용하면 메이븐이나 그래들에서 실행할 테스트를 선택할 수 있다.
	3) 적용 예
	
	@Tag("integration")
	public class TagTest {
		
		@Tag("very-slow")
		@Test
		void verySlow() {
			int result = someVerySlowOp();
			assertEquals(result, 0);
		}
	}

	그래들 설정 예)
	build.gradle 파일에 포함할 테스트와 배제할 테스트 정의하고  테스트 메서드에 @Tag("integration") 붙여줌
	test {
		useJUnitPlatform {
			includeTags 'integration'
			excludeTags 'slow | very-slow'
		}
	}

	test {
		useJUnitPlatform {
			includeTags 'exercise'
		}
	}
	test {
		useJUnitPlatform {
			includeTags '!exercise'
		}
	}
	4) 테스트 포함 대상이나 제외 대상을 지정할 때 태그 식을 사용하는데 태그 식에서는 다음 연산을 이용해서 식을 조합
	!, & |

5. 테스트 메시지
	1) assertEquals() 메서드의 세 번째 인자는 테스트에 실패할 때 표시할 메시지로 사용된다. 
	2) 테스트 코드가 실패하면 다음과 같이 메세지로 전달한 문자열이 실패 안내 문구에 함께 표시된다.
	예) 
	assertEquals(expected.get(i), ret.get(i), "ret[" + i + "]");

	--- 출력 ---
	org.opentest4j.AssertionFailedError: ret[1] ==> 
	Expected: 2
	Actual: 6


6. @TempDir 애노테이션을 이용한 임시 폴더 생성

		package exam01;
		import org.junit.jupiter.api.Test;
		import org.junit.jupiter.api.io.TempDir;
		import java.io.File;

		public class Ex03Test {
			@TempDir
			private File tempDir;

			@Test
			void test1() {
				String path = tempDir.getAbsolutePath();
				System.out.println(path);
			}
		}
		
		
7. @Timeout 애노테이션을 이용한 테스트 실행 시간 검증


		@Test
		@Timeout(3) //3초내에 수행이 되면 테스트 통과
		void test2() throws Exception {
			Thread.sleep(5000);

		}
		
		@Test
		//@Timeout(3) //3초내에 수행이 되면 테스트 통과
		@Timeout(value=2500, unit= TimeUnit.MILLISECONDS) //3초내에 수행이 되면 테스트 통과
		void test2() throws Exception {
			Thread.sleep(5000);

		}

Mockito 기초 사용법
6/10  (소스 :9.웹서버프로그램구현\day02)

	사전 작업
	dependencies {
		testImplementation platform('org.junit:junit-bom:5.10.2')    // 5.10.0 에서 수정
		testImplementation 'org.junit.jupiter:junit-jupiter'
	}

	- Mockito는 모의 객체 생성, 검증, 스텁을 지원하는 프레임워크이다.
	
	

1. 의존성 

	1) mockito-core
	2) mockito-junit-jupiter

	mvn repository : mockito core 검색 Mockito Core » 5.12.0
	Mockito JUnit Jupiter » 5.12.0
	
	Mockito Core » 5.12.0
	Mockito mock objects library core API and implementation  // API와 구현체 함께 있다고 나옴 implementation

	testImplementation 'org.mockito:mockito-core:5.12.0'
	testImplementation 'org.mockito:mockito-junit-jupiter:5.12.0'

	(참고) servlet.api
	  - javax.servlet-api     
	  - jakarta.servlet-api / jakarta ee9~10
	  
		HttpServletRequest 인터페이스 : 사용자 요청 데이터 조회, 처리 - String getParameter(String name);
	  
	jakarta servlet api -> Jakarta Servlet » 6.0.0 -> 배포시에는 배제됨 : 웹서버에  이미 있으니까 -- 테스트 환경의 스텁 제공함?
    compileOnly 'jakarta.servlet:jakarta.servlet-api:6.0.0'
    testCompileOnly 'jakarta.servlet:jakarta.servlet-api:6.0.0'  // compileOnly 로 되어있으면 src/java/main에서만 인식되므로 src/test 에도 사용하기 위함

		과정 : 6/10 동영상 9시 중간부터 
		-------------------------------------------
		package member.services;
		import jakarta.servlet.http.HttpServletRequest;

		public class LoginService {
			public void process(HttpServletRequest request) {
				//아이디 : email, 비밀번로 password

				String email = request.getParameter("email");
				String password = request.getParameter("password");
				System.out.printf("email=%s, password=%s%n", email, password);
				
				String test = request.getParameter("123");  //참고1

			}
		}
		-------------------------------------------
		package tests;
		import jakarta.servlet.http.HttpServletRequest;
		import member.services.LoginService;
		import org.junit.jupiter.api.BeforeEach;
		import org.junit.jupiter.api.DisplayName;
		import org.junit.jupiter.api.Test;
		import static org.junit.jupiter.api.Assertions.*;
		import static org.mockito.BDDMockito.given;
		import static org.mockito.BDDMockito.willReturn;
		import static org.mockito.Mockito.mock;

		@DisplayName("로그인 서비스 기능 테스트")
		public class LoginServiceTest {
			private LoginService loginService;
			private HttpServletRequest request;

			@BeforeEach
			void init() {
				loginService = new LoginService();

				// mock HttpServletRequest 모의객체 생성
				request = mock(HttpServletRequest.class);
				given(request.getParameter("email")).willReturn("test01@test.org");
				given(request.getParameter("password")).willReturn("12345678");

				given(request.getParameter("123")).willThrow(IllegalAccessException.class); //참고1

			}

			@Test
			@DisplayName("로그인 성공시 예외발생 없음")
			void successTest() {
				assertDoesNotThrow( ()-> {
				   loginService.process(request);
				});
			}

			@Test
			@DisplayName("필수항목(아이디, 비밀번호) 검증, 검증실패시 ValidationException 발생")
			void requiredFieldTest() {

			}
		}
		-------------------------------------------
		좀 정리하면
		-------------------------------------------
		package tests;
		import jakarta.servlet.http.HttpServletRequest;
		import member.services.LoginService;
		import org.junit.jupiter.api.BeforeEach;
		import org.junit.jupiter.api.DisplayName;
		import org.junit.jupiter.api.Test;
		import static org.junit.jupiter.api.Assertions.*;
		import static org.mockito.BDDMockito.given;
		import static org.mockito.BDDMockito.willReturn;
		import static org.mockito.Mockito.mock;

		@DisplayName("로그인 서비스 기능 테스트")
		public class LoginServiceTest {
			private LoginService loginService;
			private HttpServletRequest request;

			@BeforeEach
			void init() {
				loginService = new LoginService();

				// mock HttpServletRequest 모의객체 생성
				request = mock(HttpServletRequest.class);
			}
			
			void setParamData(String name, String value)    {
				given(request.getParameter(name)).willReturn(value);
			}
			void setSuccessData() {
				setParamData("email", "test01@test.org");
				setParamData("password", "12345678");
			}
			
			@Test
			@DisplayName("로그인 성공시 예외발생 없음")
			void successTest() {
				assertDoesNotThrow( ()-> {
					setSuccessData();
					loginService.process(request);
				});
			}

		}

	



2. 모의 객체 생성
	1) Mockito.mock() 메서드를 이용하면 특정 타입의 모의 객체를 생성할 수 있다.

3. 스텁 설정
	1) 모의 객체를 생성한 뒤에는 BDDMockito 클래스를 이용해서 모의 객체에 스텀을 구성할 수 있다.
	2) BDDMockito.given() 메서드를 이용하면 모의 객체의 메서드가 특정 값을 리턴하도록 설정할 수 있다.
	3) given() 메서드에 이어 willReturn() 메서드는 스텁을 정의한 메서드가 리턴할 값을 지정한다.

	- 모의 객체 생성
	GameNumGen genMock = mock(GameNumGen.class);

	- 스텁 설정
	given(genMock.generate(GameLevel.EASY)).willReturn("123");

	- 스텁 설정에 매칭되는 메서드 실행
	String num = genMock.generate(GameLevel.EASY);

	4) 지정한 값을 리턴하는 대신에 익셉션을 발생하게 설정할 수 도 있다. 이때는 willReturn() 대신 willThrow() 메서드를 사용하면 된다.

	5) BDDMockito.willThrow() 메서드는 발생할 익셉션 타입이나 익셉션 객체를 인자로 받는다. 이어서 given() 메서드는 모의 객체를 전달받는다. 이때는 모의 객체 메서드의 실행이 아닌 모의 객체를 만들어 주는 것  이때 given() 메서드는 인자로 전달받은 모의 객체 자신을 리턴하는데 이때 익셉션을 발생할 메서드를 호출한다.

	(참고)
	가짜 데이터 생성 라이브러리
	java faker
	
	java faker 검색(mvn repository)
	Java Faker » 1.0.2
	testImplementation 'com.github.javafaker:javafaker:1.0.2'
	
			----------------------------------------	
			package global.validators;
			public interface Validator<T>{
				void check(T form);
			}
				
			----------------------------------------	
			package global;

			public class ValidationException extends RuntimeException{
				public ValidationException(String message) {
					super(message);
				}
			}

			----------------------------------------	
			package member.Validators;
			import global.validators.Validator;
			import jakarta.servlet.http.HttpServletRequest;

			public class LoginValidator implements Validator<HttpServletRequest> {
				@Override
				public void check(HttpServletRequest form) {

				}
			}

			----------------------------------------	
			package member.services;
			import global.ValidationException;
			import global.validators.Validator;
			import jakarta.servlet.http.HttpServletRequest;
			import member.Validators.LoginValidator;

			public class LoginService {

				private Validator<HttpServletRequest> validator;

				public LoginService(Validator<HttpServletRequest> validator) {
					this.validator = validator;
				}

				public void process(HttpServletRequest request) {
					//아이디 : email, 비밀번로 password

					String email = request.getParameter("email");
					String password = request.getParameter("password");
					System.out.printf("email=%s, password=%s%n", email, password);

					if(email == null || email.isBlank())    {
						throw new ValidationException("이메일을 입력하세요");
					}

				}
			}

			----------------------------------------	
			package tests;
			import com.github.javafaker.Faker;
			import global.ValidationException;
			import jakarta.servlet.http.HttpServletRequest;
			import member.Validators.LoginValidator;
			import member.services.LoginService;
			import org.junit.jupiter.api.BeforeEach;
			import org.junit.jupiter.api.DisplayName;
			import org.junit.jupiter.api.Test;
			import static org.junit.jupiter.api.Assertions.*;
			import static org.mockito.BDDMockito.given;
			import static org.mockito.Mockito.mock;

			@DisplayName("로그인 서비스 기능 테스트")
			public class LoginServiceTest {
				private LoginService loginService;
				private HttpServletRequest request;
				private Faker faker;

				@BeforeEach
				void init() {
					loginService = new LoginService(new LoginValidator());

					// mock HttpServletRequest 모의객체 생성
					request = mock(HttpServletRequest.class);
					faker = new Faker();
				}

				void setParamData(String name, String value)    {
					given(request.getParameter(name)).willReturn(value);
				}
				void setSuccessData() {

					String password = faker.regexify("\\w{8}").toLowerCase();
					setParamData("email", faker.internet().emailAddress());
					setParamData("password", password);
				}

				@Test
				@DisplayName("로그인 성공시 예외발생 없음")
				void successTest() {
					assertDoesNotThrow( ()-> {
						setSuccessData();
						loginService.process(request);
					});
				}

				@Test
				@DisplayName("필수항목(아이디, 비밀번호) 검증, 검증실패시 ValidationException 발생")
				void requiredFieldTest() {

					//아이디 필수항목 검증
					assertThrows(ValidationException.class, () -> {
					   loginService.process(request);
					});
				}
			}


4. 인자 매칭 처리
	1) org.mockito.ArgumentMatchers 클래스를 사용하면 정확하게 일치하는 값 대신 임의의 값에 일치하도록 설정할 수 있다.
	2) ArgumentMatchers 클래스는 다음의 메서드를 제공
	- anyInt(), anyShort(), anyLong(), anyByte(), anyChar(), anyDouble(), anyFloat(), anyBoolean() : 기본 데이터 타입에 대한 임의 값 일치
	- anyString() : 문자열에 대한 임의 값 일치
	- any() : 임의 타입에 대한 일치
	- anyList(), anySet(), anyMap(), anyCollection() : 임의 콜렉션에 대한 일치
	- matches(String), matches(Pattern): 정규표현식을 이용한 String 값 일치 여부
	- eq(값) : 특정 값과 일치 여부


5. 행위 검증
	- 실제로 모의 객체가 불렸는지 검증하는 것
	- 예시
	GameNumGen genMock = mock(GameNumGen.class);
	Game game = new Game(genMock);
	game.init(GameLevel.EASY);

	then(genMock).should().generate(GameLevel.EASY);

	1) BDDMockito.then()은 메서드 호출 여부를 검증할 모의 객체를 전달받는다.
	2) should() 메서드는 모의 객체의 메서드가 불려야 한다는 것을 설정하고 should() 메서드 다음에 실제로 불려야 할 메서드를 지정한다.
	3) 이 코드에서 genMock 모의 객체의 generate() 메서드가 GameLevel.EASY 인자를 사용해서 호출되었는지 검증한다.
	4) 정확한 값이 아니라 메서드가 불렸는지 여부가 중요하다면 any(), anyInt() 등을 사용해서 인자를 지정하면 된다.
		then(genMock).should().generate(any());

	5) 정확하게 한 번만 호출된 것을 검증하고 싶다면 should() 메서드에 Mockito.only()를 인자로 전달한다
		then(genMock).should(only()).generate(any());
	6) 메서드 호출 횟수를 검증하기 위해 Mockito 클래스가 제공하는 메서드는 다음과 같다.
	- only() : 한 번만 호출
	- times(int) : 지정한 횟수만큼 호출
	- never() : 호출하지 않음
	- atLeast(int) : 적어도 지정한 횟수만큼 호출
	- atLeastOnce() : atLeast(1)과 동일
	- atMost(int) : 최대 지정한 횟수만큼 호출

		--------------------------------------------------------------

		package global;
		public class Mailer {
			public void send(String email) {
				System.out.println("메일전송!!");
			}
		}

		--------------------------------------------------------------

		package member.services;
		import global.Mailer;
		import global.validators.Validator;
		import jakarta.servlet.http.HttpServletRequest;

		public class LoginService {

			private Validator<HttpServletRequest> validator;
			private Mailer mailer;

			public LoginService(Validator<HttpServletRequest> validator) {
				this.validator = validator;
			}
			public void setMailer(Mailer mailer) {
				this.mailer = mailer;
			}

			public void process(HttpServletRequest request) {
				//아이디 : email, 비밀번로 password
				System.out.println(request.getParameter("email"));
				System.out.println(request.getParameter("password"));
				validator.check(request);

				//로그인 성공 가정
				//성공시 메일 전송

				if(mailer != null) {
					String email = request.getParameter("email");

					mailer.send(email);
					mailer.send(email);
				}
			}
		}

		--------------------------------------------------------------
		package exam01;
		import com.github.javafaker.Faker;
		import global.Mailer;
		import jakarta.servlet.http.HttpServletRequest;
		import member.Validators.LoginValidator;
		import member.services.LoginService;
		import org.junit.jupiter.api.BeforeEach;
		import org.junit.jupiter.api.Test;
		import java.util.Locale;
		import static org.mockito.BDDMockito.given;
		import static org.mockito.BDDMockito.then;
		import static org.mockito.Mockito.mock;

		public class Ex02Test {
			private LoginService loginservice ;
			private  Mailer mailer;
			private Faker faker;
			private HttpServletRequest request;

			@BeforeEach
			void init() {
				loginservice = new LoginService(new LoginValidator());
				mailer = mock(Mailer.class);   // Juniper 이용하는 test를 위해서는 꼭 이렇게 .. new 로 생성하면 안됨
				faker = new Faker(Locale.ENGLISH);
				request = mock(HttpServletRequest.class);

				//스텁추가
				given(request.getParameter("email")).willReturn(faker.internet().emailAddress());
				given(request.getParameter("password")).willReturn(faker.regexify("\\w{8}").toLowerCase());
			}

			@Test
			void test1() {
				loginservice.setMailer(mailer);
				loginservice.process(request);
				String email = request.getParameter("email");
				then(mailer).should().send(email); // send 함수가 loginService.process 메서드에서 한번이상 호출되는지 체크
				//then(mailer).should(only()).send(email); // send 함수가 loginService.process 메서드에서 한번만 호출되는지 체크
			}

			@Test
			void test2() {
				loginservice.setMailer(mailer);
				loginservice.process(request);

				String email = request.getParameter("email");
				System.out.println(email);

				ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

				then(mailer).should().send(captor.capture()); //인자 캡쳐
				String usedEmail = captor.getValue();   //send() 메서드에서 매개변수에 사용한 값 확인 위해
				System.out.println(usedEmail);

				assertTrue(email.contains(usedEmail)); 
			}
		}
		===================================
		@Mock
		private  Mailer mailer;

		@Mock
		private HttpServletRequest request;

		@BeforeEach
		void init() {
			//mailer = mock(Mailer.class);   // 에너테이션 @Mock 쓰면 이거 필요없음
			//request = mock(HttpServletRequest.class);
			...
		}
		===================================


6. 인자 캡쳐
	1) 단위 테스트를 실행하다보면 모의 객체를 호출할 때 사용한 인자를 검증해야 할 때가 있다. String이나 int와 같은 타입은 쉽게 검증할 수 있지만 많은 속성을 가진 객체는 쉽게 검증하기 어렵다. 이럴 때 사용할 수 있는 것이 인자 캡처이다.

	2) Mockito의 ArgumentCaptor를 사용하면 메서드 호출 여부를 검증하는 과정에서 실제 호출할 때 전달한 인자를 보관할 수 있다.

7. JUnit 5 확장 설정