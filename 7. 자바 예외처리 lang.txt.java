4/23

예외처리

1. 오류와 예외
	오류(Error)	: 시스템의 오류, JVM 오류 ... : 통제 불가 오류
	
	예외(Exception) : 코드 상의 오류 : 통제 가능한 오류 
					       -  버그
	
2. 예외 클래스의 종류
	
	      Throwable 
		  
	Error           Exception
						
	(javadoc Throwable 찾아보면 직접 하위 클래스 : Error, Exception)



Exception  ( RuntimeException 상속여부 중요 )

			
	- RuntimeException을 중간에 상속 받은 예외 클래스
	
		- Runtime : 실행 
		예) java.lang.ArithmethicException  :  0으로 나눌때 발생 
		
		- 예외가 발생하더라도 컴파일 O, class 파일 생성 
		- 예외의 체크는 실행 중 체크, 실행이 되려면? class 파일 필요(컴파일은 된다...)
		- 유연한 예외, 형식은 X
		
	- Exception을 바로 상속 받은 예외 클래스
	
		예) java.io.IOException  / 파일을 읽을때, 쓸때 (FileInputStream, FileOutputStream)
			
			 java.io.FileNotFoundException
			 
			- 예외있든 없든 처리가 안되어 있으면 컴파일 X   
			- 예외의 체크는 컴파일시 체크, 예외가 있으면 컴파일 X ( <- RuntimeException을 상속 없으므로 )
			- 예외가 발생하든 안하든 반드시 적절한 예외 처리가 필요한 예외 
			- 엄격한 예외,  형식을 매우 중시 
	
	예외가 발생하면 프로그램 중단! -> 프로그램 중단을 막기 위한 조치 
		- 예외처리의 목적 : 예외가 발생시 적절한 조치 -> 서비스 중단을 막는 것 
		
참고)
	java.exe : 클래스파일 실행 
	javac.exe : java -> class 컴파일 

예외 처리하기
1. try ~ catch문

 try {
	// 예외가 발생할 가능성이 있는 코드 
	
 } catch (예외 객체 ....) {
	// 예외 발생시 처리할 코드 
 }

참고)
	예외 발생 
		throw 예외객체;

	예외, 오류 -> 원인을 확인을 하는것이 중요
	
	
	예외 클래스 주요 메서드 : 정보확인
			java.lang.Throwable 
										- String getMessage() - 오류 메세지 확인
										- void printStackTrace() : 

		package exam02;
		import java.io.FileInputStream;
		import java.io.FileNotFoundException;

		public class Ex02 {
			public static void main(String[] args) {

				try {
					
					FileInputStream fis = new FileInputStream("b.txt");
					System.out.println("파일처리..."); // 예외 발생시 실행 안됨

				} catch(FileNotFoundException e) {

					System.out.println("예외 발생!");
					String message = e.getMessage();
					e.printStackTrace();
					System.out.println(message);
				}
				System.out.println("매우 중요한 실행 코드..");
			}
		}
		
		package exam02;
		public class Ex01 {
			public static void main(String[] args) {
				try {
					int num1 = 10;
					int num2 = 0;

					int result = num1 / num2;
					System.out.println(result);

				} catch (ArithmeticException e) {
					String message = e.getMessage();
					System.out.println(message);
					e.printStackTrace();
				}
				System.out.println("매우 중요한 코드..");
			}
		}		

		package exam02;
		public class Ex03 {
			public static void main(String[] args) {
				try {
					int num1 = 10;
					int num2 = 2;

					String str = null;
					System.out.println(str.toUpperCase()); // NullPointer Exception

				} catch (ArithmeticException | NullPointerException e) {
					e.printStackTrace();
				}
				System.out.println("매우 중요한 코드..");
			}
		}
		package exam02;
		public class Ex03 {
			public static void main(String[] args) {
				try {
					int num1 = 10;
					int num2 = 2;

					String str = null;
					System.out.println(str.toUpperCase()); // NullPointer Exception

				} catch (Exception e) { //요거는 가장 하단 : ArithmeticException, NullPointerException 의 상위클래스, 다형성
					System.out.println("유입!");
					e.printStackTrace();
				}
				System.out.println("매우 중요한 코드..");
			}
		}	
		

		
2. try-catch-finally문
	- 자원을 소비하는 객체 - FileInputStream, FileOutputStream, Connection, PrepareStatement ... 
	- 자원 해제 -> 애플리케이션 종료시에 해제 
	- 서버? 종료 X -> 자원해제를 하지 않으면 메모리 부족 현상 발생 
	- 자원해제를 적절하게 해야 된다.(close()...)
	
	try {
	
	} catch (...) {
		...
	} finally {
		// 예외가 발생하든 안하든 항상 실행되는 코드 
		// return 하더라도 코드가 실행 
	}
		package exam02;
		import java.io.File;
		import java.io.FileInputStream;
		import java.io.IOException;

		public class Ex04 {
			public static void main(String[] args) {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream("a.txt");
					System.out.println("파일작업...");

				} catch (IOException e) {
					e.printStackTrace();

				} finally {     // 자원해제 또는 로그 finally 에 주로 정의
					if(fis != null) {
						try {
							fis.close();    //IOException
						} catch ( IOException e) {

						}
					}

				}
			}
		}

	
3. try-with-resources문
	- JDK7에서 추가
	- 자원 해제를 자동 
	- AutoCloseable 인터페이스 구현체일 경우만 가능함

	try ( 해제할 자원 객체;
			해제할 자원 객체 ...) {
		// 예외가 발생할 가능성이 있는 코드 
		
	} catch(예외 객체 ...) {
	
	}
	
		package exam02;
		import java.io.FileInputStream;
		import java.io.IOException;

		public class Ex05 {
			public static void main(String[] args) {

				try(FileInputStream fis = new FileInputStream("a.txt")) {
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		------------------------------------------------
		package exam03;
		public class Ex01 {
			public static void main(String[] args) {
				try(Resources res = new Resources();    // 다수 자원 해제 시
					Resources2 res2 = new Resources2()) {
					// res가 AutoClosable 인터페이스 구현 객체인지 확인 후 close()
		 
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		-----------------------------------------------------------
		package exam03;

		public class Ex01 {
			public static void main(String[] args) throws Exception {
				Resources res = new Resources();
				Resources2 res2 = new Resources2();
				try (res; res2){
					// res가 AutoClosable 인터페이스 구현 객체인지 확인 후 close()

				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	
	자원 자동해제의 조건
		AutoCloseable 인터페이스의 구현 클래스 
			- FileInputStream 는 AutoCloseable 인터페이스의 구현 클래스이므로 try-with-resource 문으로 자동 해제 사용 가능
		- close() 메서드를 자동 호출 
			
	참고) 
		instanceof 
	



예외 처리 미루기
1. 예외 처리를 미루는 throws 사용하기
	- 메서드를 호출 하는쪽에서 예외 처리 전가 
	- 전가시키는 예외에 대해서 명시(throws)
		메서드 매개변수 뒤쪽에 throws 전가할 예외 작성
	- Exception을 상속 받은 경우(RuntimeException이 없는 경우)

		package exam04;
		public class UserIdException extends Exception {

			public UserIdException(String message) {
				super(message);
			}
		}
		package exam04;
		public class UserPwException extends Exception {
			public UserPwException(String message) {
				super(message);
			}
		}

		package exam04;
		public class LoginService {
			public void login(String userId, String userPw)  {
				// userId : user01, userPw : 123456
				try {   // UserPwException 은 Exception 의 하위 클래스이므로 RuntimeException 이 아니라서 try catch 필요함
					if (!userId.equals("user01")) {
						throw new UserIdException("아이디가 일치하지 않습니다.");
					}
					if (!userPw.equals("123456")) {
						throw new UserPwException("패스워드가 일치하지 않습니다");
					}
				} catch (UserIdException | UserPwException e) {
					System.out.println(e.getMessage());
				}
			}
		}

		package exam04;
		public class Ex01 {
			public static void main(String[] args) {
				LoginService service = new LoginService();
				service.login("user02", "123456");
			}
		}
		-------------------------------예외처리 전가 하면-------------
		package exam04;
		public class LoginService {
			public void login(String userId, String userPw) throws UserIdException, UserPwException {
				if (!userId.equals("user01")) {
					throw new UserIdException("아이디가 일치하지 않습니다.");
				}
				if (!userPw.equals("123456")) {
					throw new UserPwException("패스워드가 일치하지 않습니다");
				}
			}
		}

		package exam04;
		public class Ex01 {
			public static void main(String[] args) {
				LoginService service = new LoginService();
				try {
					service.login("user02", "123456");
				}  catch (UserIdException | UserPwException e) {
					System.out.println(e.getMessage());
				}
			}
		}

		----------------------------유연한 RuntimeException으로 바꾸면---------
		package exam04;
		public class UserIdException extends RuntimeException {
			public UserIdException(String message) {
				super(message);
			}
		}
		package exam04;
		public class UserPwException extends RuntimeException {
			public UserPwException(String message) {
				super(message);
			}
		}
		package exam04;
		public class LoginService {
			public void login(String userId, String userPw)  {
				if (!userId.equals("user01")) {
					throw new UserIdException("아이디가 일치하지 않습니다.");
				}
				if (!userPw.equals("123456")) {
					throw new UserPwException("패스워드가 일치하지 않습니다");
				}
			}
		}

		package exam04;
		public class Ex01 {
			public static void main(String[] args) {
				LoginService service = new LoginService();
				try {
					service.login("user02", "123456");
				}  catch (UserIdException | UserPwException e) {
					System.out.println(e.getMessage());
				}
			}
		}

	
2. 다중 예외 처리
	
3. 사용자 정의 예외
	- JDK 기본 정의 예외 외에 따로 작성하는 예외 



java.lang 패키지
- 기본적으로 많이 사용하는 클래스들이 포함

예) String 
	java.lang.String 

컴파일러가 패키지명 바로 아래쪽 import java.lang.*;가 추가 
- lang 패키지의 모든 클래스는 그냥 사용 가능 

Object 클래스
	- 모든 클래스의 상위 클래스 
	- 상속이 명시 X -> extends java.lang.Object 가 추가(컴파일러)
	
	
	package exam05;
	import java.lang.*;

	public class Ex01 extends java.lang.Object {
		public static void main(String[] args) {
			java.lang.String str = "ABC";
		}
	}

컴파일러가 자동 추가해 주는 내용

	1. 기본생성자
	2. 생성자에서 super()를 첫줄에 추가
	3. 참조변수를 출력 -> 참조변수.toString()
	4. 지역변수의 상수화 final 
	5. 인터페이스의 추상메서드 - public abstract
	6. 인터페이스의 변수 정의 (정적변수) : public static final
	7. import java.lang.*
	8. extends java.lang.Object 
	


- Object 클래스에 정의된 메서드
	1. toString() 메서드
	
		Book book = new Book();
		System.out.println(book); // = System.out.println(book.toString());
		Class cls = Book.class;
        Class cls2 = book.getClass();

        System.out.println(book.getClass().getName());
        System.out.println(book.getClass());

		//java.lang.Object 에서
		public String toString() {
			return getClass().getName() + "@" + Integer.toHexString(hashCode());
			
		}
		 - getClass().getName() : 클래스명( 패키지명을 포함한 전체 클래스명) - exam03.Book
		 - Integer.toHexString(hashCode()) : 객체의 주소값을 16진수로 변환한 문자열
		 
		 
		package exam06;
		public class Book extends java.lang.Object{
			private int isbn;
			private String title;
			private String author;

			public Book(int isbn, String title, String author) {
				this.isbn = isbn;
				this.title = title;
				this.author = author;
			}

			@Override
			public String toString() {
				return String.format("isbn=%d, tite=%s, author=%s%n", this.isbn, this.title, this.author);
			}
		}

		package exam06;
		public class Ex01 {
			public static void main(String[] args) {
				Book book = new Book(1000, "제목1","저자1");
				System.out.println(book); // = System.out.println(book.toString());
			}
		}

		package exam06;
		import java.time.LocalDate;

		public class Ex02 {
			public static void main(String[] args) {
				LocalDate today = LocalDate.now();
				System.out.println(today);
			}
		}		 
		
	2. equals() 메서드
	
		//java.lang.Object 에서
		public boolean equals(Object obj) {
			return (this == obj);
		}
		동일성 비교 (주소비교)
		
		package exam06;
		public class Ex03 {
			public static void main(String[] args) {
				Book b1 = new Book(1000, "책1", "저자1");
				Book b2 = new Book(1000, "책1", "저자1");

				System.out.printf("b1 == b2 : %s%n", b1 == b2);
				System.out.printf("b1.equals(b2) : %s%n", b1.equals(b2));
			}
		}
		>> b1 == b2 : false
		   b1.equals(b2) : false
		   
		package exam06;
		public class Ex04 {
			public static void main(String[] args) {
				String str1 = new String("abc");
				String str2 = new String("abc");

				System.out.printf("str1 == str2 : %s%n", str1 == str2);
				System.out.printf("str1.equals(str2) : %s%n", str1.equals(str2));
			}
		}
		>>
		  str1 == str2 : false
		  str1.equals(str2) : true  => equals : String 클래스에서는 재정의 하여 동등성 비교하도록 함
		 
		 참고) String 문자열 비교시 == 쓰면 안됨, equals 사용해야함  (js : == 동등성비교 === 동일성비교)

	3. hashCode() 메서드


String 클래스

1. String을 선언하는 두 가지 방법
2. String 클래스의 final char[] 변수
3. StringBuffer와 StringBuilder 클래스 활용하기


Wrapper 클래스
- 기본 자료형을 위한 클래스

1. Wrapper 클래스의 종류

2. Integer 클래스 사용하기
1) Integer 클래스의 메서드

3. 오토박싱과 언박싱


Class 클래스

1. Class 클래스를 선언하고 클래스 정보를 가져오는 방법
2. Class 클래스를 활용해 클래스 정보 알아보기
3. Class.forName()을 사용해 동적 로딩 하기