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

컴파일러가 자동 추가해 주는 내용 *******

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

		동일성 비교 (주소비교) => Object
		동등성 비교 (내용비교) => String 
		
		동등성 비교하려면 : equals(), hashcode() 재정의 ****
		
		
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
		   b1.equals(b2) : false    => 객체에서는 주소비교 
		   
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
		  str1.equals(str2) : true  => equals : String 클래스에서는 equals 재정의되어있어서 동등성 비교하도록 함
		 
		 참고) String 문자열 비교시 == 쓰면 안됨, equals 사용해야함  (js : == 동등성비교 === 동일성비교)
		 
		-----Book 클래스에서 equals를 이렇게 재정의한다면 ----------------------------
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Book) {
				Book book = (Book)obj;
				if(isbn == book.isbn && title.equals(book.title) && author.equals(book.author)) {
					return true;
				}
			}
			return false;
		}		
		----------------------------------------------------------------------------
		참고) 중복제거 : 동등성 비교로 중복제거됨
		
		package exam01;
		import java.util.HashSet;

		public class Ex05 {
			public static void main(String[] args) {
				HashSet<String> data = new HashSet<>();
				data.add("AAA");
				data.add("BBB");
				data.add("AAA");
				data.add("CCC");
				System.out.println(data);
			}
		}

	3. hashCode() 메서드
		- 기본구현 : 객체의 주소값
		- 동등성 비교하려면 equals와 hashcode 둘다 재정의해야함
		
		
			package exam01;
			import java.util.HashSet;

			public class Ex06 {
				public static void main(String[] args) {
					HashSet<Book> books = new HashSet<>();
					books.add(new Book(1000, "책1", "저자1"));
					books.add(new Book(1000, "책1", "저자1"));
					books.add(new Book(2000, "책2", "저자2"));
					books.add(new Book(3000, "책3", "저자3"));
					books.add(new Book(4000, "책4", "저자4"));
					books.add(new Book(5000, "책5", "저자5"));

					System.out.println(books);

					for(Book book: books) {
						System.out.println(book);
					}
				}
			}		
			>> 중복제거 안됨
			---- Book Class 에 추가 하면 ----------
			@Override
			public int hashCode() {
				return Objects.hash(isbn, title, author);  // Objects => util 패키지의 Object 편의기능 ***
			}
			>> 중복제거 성공
		
		java.util.Objects  util에 s 붙은 클래스 ->  편의기능
		
		참고) 가변적변수 예제
			package exam01;
			import java.util.Arrays;

			public class Ex08 {
				public static void main(String[] args) {
					int result1 = sum(10, 20,30,40);
					int result2 = sum(10,20);
					System.out.println(result1);
					System.out.println(result2);
				}
				public static int sum(int... nums) {

					//System.out.println(Arrays.toString(nums));
					int total= 0;
					for(int num: nums) {
						total += num;
					}
					return total;
				}
			}		
		

String 클래스

	1. String을 선언하는 두 가지 방법
	
		String str = "문자열";
		String str = new String("문자열");
		
		
	2. String 클래스의 final char[] 변수  //jdk11까지는 이게 맞았는데 
	
		최근: final byte[]
		- 문자열은 불변하는 특징
		- 문자열의 불변성 -> 문자열 변화하면 주소 빠뀜 -> 문자열 변화 많으면 성눙저하

		package exam02;
		public class Ex03 {
			public static void main(String[] args) {
				
				String str = "ABC";
				System.out.printf("str주소 : %d%n", System.identityHashCode(str));
				
				str += "DEF";   // 주소바뀜, 문자열의 불변성 -> 문자열 변화 많으면 성눙저하
				System.out.printf("str주소 : %d%n", System.identityHashCode(str));
			}
		}
		
		>>
		str주소 : 1324119927
		str주소 : 1922154895
		
	3. StringBuffer와 StringBuilder 클래스 활용하기
	
		버퍼 : 임시 메모리
		- 문자열 가감이 많은 경우 사용, 
		- StringBuffer : 쓰레드 안정성(동시성 작업시 안전)
		- StringBuilder : 쓰레드 안정성X(동시성 작업시 불안전)
		
		반환값이 this : 동일한 객체를 반환 -> 메서드 체이닝 기법 의도
		
			package exam02;
			public class Ex05 {
				public static void main(String[] args) {
					StringBuffer sb = new StringBuffer(100);
					/*
					StringBuffer sb2 = sb.append("ABC");
					StringBuffer sb3 = sb2.append("EDF");
					System.out.println(sb == sb2);
					*/

					// 메서드 체이닝
					String str = sb.append("ABC").append("DEF").append("GHI").toString();
					System.out.println(str);
				}
			}
		

Wrapper 클래스
- 기본 자료형을 위한 클래스

	기본자료형 

	정수 - byte, short, int, long
	실수 - float, double
	논리 - boolean
	문자 - char

1. Wrapper 클래스의 종류
	기본자료형은 기능이 없으므로 기본자료형에 기능을 부여하기 위해 
	기본자료형의 값을 처리하는 편의 기능 클래스
	
	byte -> Byte 클래스
	short -> Short 클래스
	int -> Int 클래스
	long -> Long 클래스
	
	float -> Float 클래스
	double -> Double 클래스
	boolean -> Boolean 클래스
	char -> Character 클래스
	
	class Integer {
		...
		private final int value;
		..
	}

	static 메서드 들 중에
	- OOOOOO.parseOOO(...) OOO으로 변환
	

2. Integer 클래스 사용하기
	static int parseInt(String s) : 문자열 숫자 -> integer로 변환 
	static Integer valueOf(int i) : 
	
			int num3 = Integer.parseInt(str);
	


1) Integer 클래스의 메서드
		package exam03;
		public class Ex02 {
			public static void main(String[] args) {
				Integer num1 = new Integer(10);
				Integer num2 = new Integer(10);
				Integer num3 = Integer.valueOf(10);  // 자원낭비 방지위해 ( 단 작은 숫자에 한함 )
				Integer num4 = Integer.valueOf(10);

				Integer num5 = 10;
				Integer num6 = 10;

				System.out.printf("num1 주소 : %d%n", System.identityHashCode(num1));
				System.out.printf("num2 주소 : %d%n", System.identityHashCode(num2));

				System.out.printf("num3 주소 : %d%n", System.identityHashCode(num3));
				System.out.printf("num4 주소 : %d%n", System.identityHashCode(num4));

				System.out.printf("num5 주소 : %d%n", System.identityHashCode(num5));
				System.out.printf("num6 주소 : %d%n", System.identityHashCode(num6));
			}
		}
	

3. 오토박싱과 언박싱
	연산 (+, -, * , / ...) 
	
	기본자료형만 연산 가능 , 같은 형끼리만 가능, intValue() 자동추가됨 => 언박싱
	

		package exam03;
		public class Ex05 {
			public static void main(String[] args) {
				int num1 = 100;
				Integer num2 = Integer.valueOf(200);
				
				int result = num1 + num2;  //num2.intValue(); 자동추가됨, 원래는 숫자와 객체는 연산이 안됨
				System.out.println(result);

				Integer num3 = Integer.valueOf(100);
				Integer num4 = Integer.valueOf(200);
				
				int result2 = num3 + num4; // num3.intValue() + num4.initValue() : 언박싱
				System.out.println(result2);			
				
				Integer num1 = 100;  // Integer.valueOf(100); 자동추가됨 :  오토박싱
				Integer num2 = 200;

			}
		}
	
		package exam03;
		public class Ex06 {
			public static void main(String[] args) {
				Integer num1 = 100;  // Integer.valueOf(100); 자동추가 오토박싱
				Integer num2 = 200;
				
				Integer num3 = num1 + num2; //내부적으로 Integer.valueOf(num1.intValue()) + Integer.valueOf(num2.intValue());
				double num5 = num1.doubleValue(); 
			}
		}

	java.lang.Number

		package exam03;
		public class Ex07 {
			public static void main(String[] args) {
				double result = add(10.0, 10L);
				System.out.println(result);
			}

			public static double add(Number num1, Number num2) {
				return num1.doubleValue() + num2.doubleValue();
			}
		}
Class 클래스
	- 클래스의 정보가 담겨있는 객체가 자동생성 - Class 클래스 객체
	
1. Class 클래스를 선언하고 클래스 정보를 가져오는 방법
	1) 모든 클래스의 정적변수 class 
	
		Class cls1 = Person.class;  // 인스턴스 객체 생성 안해도 사용가능
	
	2) Object 클래스의 정의된 getClass()
		
		Person person = new Person();
		Class cls2 = person.getClass(); 
	

2. Class 클래스를 활용해 클래스 정보 알아보기

	package exam03;
	public class Person {
		public int age;
		public String name;

		public Person() {}
		public Person(int age, String name) {
			this.age = age;
			this.name = name;
		}
		public int getAge() {
			return age;
		}

		public String getName() {
			return name;
		}
	}

	package exam03;
	import java.lang.reflect.Constructor;
	import java.lang.reflect.Field;
	import java.lang.reflect.Method;

	public class Ex08 {
		public static void main(String[] args) {

			Class cls1 = Person.class;  // 인스턴스 객체 생성 안해도 사용가능
			Field[] fields = cls1.getFields();
			Method[] methods = cls1.getMethods();
			Constructor[] constructors = cls1.getConstructors();

			System.out.println("----- Fields -----");
			for(Field field : fields) {
				System.out.println(field);
			}
			System.out.println("----- Constructors -----");
			for(Constructor constructor : constructors) {
				System.out.println(constructor);
			}
			System.out.println("------ Methods -------");
			for(Method method : methods) {
				System.out.println(method);
			}

			Person person = new Person();
			Class cls2 = person.getClass();   //인스턴스객체가 생성되어야 쓸수 있다. 클래스 내부에서 사용할 목적
		}
	}

3. Class.forName()을 사용해 동적 로딩 하기




유용한 클래스
1. java.lang.Math 클래스

	- 수학관련 편의기능 클래스
	- 모두 정적 메서드
	- abs(..) : 절대값
	- ceil(..) : 올림
	- floor(..) : 버림
	- round(..) : 반올림
	- max(..) : 둘중 큰거
	- min(..) : 둘중 작은거
	- pow(..) : pow(2,3) : 2의 3제곱
	- random() : 0 ~ 1 사이의 무작위 난수
	- sqrt(..) : 루트값 : 제곱근 
	
	Math.random() * n; // 정수부분이 언제나 n보다 작은 숫자 무작위로 나옴
	
			
		package exam04;
		import java.util.Arrays;

		public class Ex02 {
			public static void main(String[] args) {

				// 1 ~ 43, 6개의 숫자 - 중복 X
				int cnt = 0;
				int[] lotto = new int[6];

				while(cnt < 6) {
					int num = getNumber();
					if(chkDuplicated(lotto, num)) {
						continue;
					}
					lotto[cnt] = num;
					cnt++;
				}
				System.out.println(Arrays.toString(lotto));
			}
			public static int getNumber() {
				return (int)(Math.random() * 43) + 1;
			}

			public static boolean chkDuplicated(int[] lotto, int num) {
				for(int n : lotto) {
					if( n == num) return true;
				}
				return false;
			}
		}

		package exam04;
		import java.util.HashSet;

		public class Ex03 {
			public static void main(String[] args) {
				//set : 중복 X
				HashSet<Integer> lotto = new HashSet<>();
				while(lotto.size() < 6) {
					lotto.add(getNumber());
				}
				System.out.println(lotto);
			}
			public static int getNumber() {
				return (int)(Math.random() * 43) + 1;
			}
		}

	
2. java.util.Objects 클래스
	- 객체를 다룰때 사용하는 편의기능
	- 모든 메서드가 정적 (static)
	- equals : 두객체간의 주소비교
	- deepEquals : 중첩된 객체를 재귀적으로 주소 비교
		참고) Arrays.equals(..), Arrays.deelpEquals(..)
		
	- int hash(Object.. values) :
	- int hashCode(Object o) 
	- boolean isNull(..)  : 참조변수가 널인지 체크
	- boolean nonNull(..) : 참조변수가 널이 아닌지 체크
	- requiredNonNullElse(..) :
	
	
3. java.util.Random 클래스
4. java.util.Scanner 클래스
5. java.util.StringTokenizer 클래스