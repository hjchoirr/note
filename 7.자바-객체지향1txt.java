객체지향 프로그래밍1

4/16

1. 객체 지향 프로그래밍과 클래스
	객체(Object)?
		- 변수와 함수를 가짐 (속성과 기능을 가짐) 

	속성  : 정의, 상태  -> 변수
	행위 :  -> 함수 -> 메서드


	1) 객체와 객체 지향 프로그래밍
	2) 생활 속에서 객체 찾아보기

	2. 클래스 살펴보기
	클래스란 객체를 만들기 위한 설계 명세서 

	=> 클래스=객체가 아니라 객체를 만들기 위한 설계도 => 클래스가 객체아니라 인스턴스가 객체네...

	1) 클래스를 정의하는 문법

	class 클래스명 {
		변수 정의 
		함수 정의 
	}

	2) 클래스 이름을 짓는 규칙
		OrderInfo -> Order + Info

	3) 클래스의 속성을 구현하는 멤버변수
	- 클래스에 변수 정의 -> 멤버 변수(정의)
	- 클래스에 정의된 함수 -> 메서드

	변수.속성명
	변수.함수명(....);

3. 클래스와 인스턴스

	인스턴스(instance) - 실체 : 실제로 존재한다.
	- 생성된 객체 
	- 클래스의 변수 정의로는 실제 존재 아닌 상태이고 클래스(설계서)로 생성된 인스턴스가 객체 ***중요***

	(정의에 불과한 클래스 명세 -> 메모리에 생성된 객체(실체))


메서드
1. 함수란?
	- 기능
	
2. 함수의 입력과 반환
3. 함수 정의하기
	
	반환값의자료형 함수명 (매개변수 ....  ) {
		// 실행될 코드 정의 
		
		return 반환값;
	}

	1) 함수이름
	2) 매개변수
	3) return 예약어와 반환형
	
4. 함수 호출하고 값 반환하기
	호출 -> 함수명(값, ...); 
	
5. 매개변수 살펴보기
6. 함수 호출과 스택 메모리
	스택 메모리 : 임시메모리 
		-> 함수가 연산을 수행할때만 공간을 할당, 작업 완료(return) -> 제거
	
	참고)
		스택(stack) 구조 
			- 가장 마지막에 투입된 자료 : 가장 먼저 나오고
			- 가장 처음에 투입된 자료 : 가장 나중에 나온다.
			
		큐(queue) 구조
	
	
	함수에 정의된 변수 -> 스택에서 활성화, 스택에서 제거 
	함수 지역 -> 지역 변수 
	
7. 함수의 장점 : 재활용성

8. 클래스 기능을 구현하는 메서드

	힙 영역 메모리 : 객체 전용 메모리

	참조 자료형 
		클래스 형태의 자료형 -> 객체가 되어야 사용 가능 
		
	참조 변수 : 생성된 객체의 주소값을 가지고 있는 변수 (지역변수)
	```java
	package exam01;
	public class Student {

			int id;
			String name;
			String subject;

			void study() {
					System.out.printf("%s가 %s를 공부한다.%n", name, subject);
			}
	}
	
	package exam01;
	public class Ex01 {
		public static void main(String[] args) {
			Student s1 = new Student();
			s1.id = 1000;
			s1.name = "이이름";
			s1.subject = "자바";
			System.out.println(s1.id);
			s1.study();                  // 인스턴스 메서드 이이름가 자바를 공부한다.

			Student s2 = new Student();
			s2.id = 1001;
			s2.name = "김이름";
			s2.subject = "JS";
			s2.study();                 // 김이름가 JS를 공부한다.

			System.out.println(s1 == s2);    // false

			Student s3 = s2;
			s3.study();
			s3.name = "(수정)김이름";
			s3.study();                   // (수정)김이름가 JS를 공부한다.
			s2.study();                   // (수정)김이름가 JS를 공부한다.
			System.out.println(s2 == s3);    // true
		
			s1 = null;    // s1 은 객체와 연결(참조) 끊겨 더이상 못쓴다 (GC : GarbageCollector 가 힙메모리에서 제거)
			s1.study();   // 오류발생
		}
	}
	```
	
생성자

	- 클래스명과 동일한 명칭의 함수 
	- 객체를 생성해 주는 역할 

	데이터 영역 메모리(코드 & 상수 영역 메모리)

	클래스 로더 -> 클래스 파일(코드) -> 데이터 영역에 로드 

	1. 디폴트 생성자(기본 생성자)
		- 클래스에 생성자 메서드가 정의된 것이 없으면(**중요) -> 컴파일러가 자동 추가
			public 클래스명() {}
			
	2. 생성자 만들기 
		생성자 함수 - 객체를 만드는 함수
		           (함수 내용이 비어있어도 이 기능을 한다, 반환값 없어도 내부적으로 있다. 통제 못하게..)
		생성자 함수 내부 내용 - 객체가 생성된 이후 실행코드 정의
		```java
			package exam01;
			public class Student {
				int id;
				String name;
				String subject;

				void study() {
					System.out.printf("%s가 %s를 공부한다.%n", name, subject);
				}
				/*   default 생성자: 이거 정의하지 않으면 컴파일러가 자동으로 이렇게 만듬
				public Student() {
					// 객체가 생성된 이후 실행코드 정의
					// 객체가 생성된 이후 실행코드 정의, id, name, subject 에 공간 할당 받은 상태
					// 인스턴스 변수 초기화 여기서 함	
				}
				*/
			}	
		```
	3. 생성자 오버로드
		
		(함수의 오버로드)
		함수의 이름 - 함수의 시그니처
		
		패키지명 + 클래스명 + 반환값타입 + 함수명 + 매개변수 타입 + 예외전가
		함수의 시그니처가 동일 -> 함수의 중복정의 -> 오류
		함수의 시그니처가 동일하지 않으면 -> 함수명이 같아도 다른 함수를 정의한것
		```java
		package exam01;
		public class Ex04 {  //class name : exam01.Ex04
			public static void main(String[] args) {

			}
			static int add(int num1, int num2) {
				return num1 + num2;
			}
			static int add(int num1, int num2, int num3) {
				return num1 + num2 + num3;
			}
			static double add(double num1, double num2) {
				return num1 + num2;
			}
		}
		```
	javadoc 보기
	
	Field Summary : 변수정의
	Nested Classes : 내부 클래스, 인터페이스
	final : 상수
	Type 자료형
	Field : 매개변수
	Constructor : 생성자
	
		class 정보가 담겨 있는 객체 class
		```java
		package exam01;

		import java.lang.reflect.Method;
		public class Ex05 {
			public static void main(String[] args) {
				Class cls = Student.class;

				Method[] methods = cls.getMethods();
				for(Method method : methods) {
					System.out.println(method);
				}
			}
		}
		```
	
	
정보 은닉
	1. 접근제어자 정리
	
		public : 다른 패키지에서도 접근이 가능  
				 다른 패미지의 클래스 쓸때 import문 사용
				 
				 import 패키지.클래스;
				 import 패키지.*;
				 
				 (자동임포트: 
				 inteliJ -> setting -> editor -> General -> auto import -> java -> Add unambiguous.. , Optimize imports on the fly 2개 체크)
		
		protected : 동일 패키지에서 접근 가능(default) 
		            외부 패키지에 있는 클래스에서 상속을 받으면 클래스 내부에서 접근 가능(private)
		
		default : 접근 제어자를 명시하지 않은 경우 : 동일 패키지에서 접근 가능
		
		private : 클래스 내부에서만 접근 가능 (클래스 내부 메서드에서만 가능)

		범위크기 : public > protected > default > private

		*** 패키지의 하위 패키지(하위폴더) 는 다른 패키지임
		
	- 멤버 변수에 직접 값을 대입하는 것 -> 통제 불가 : 사용을 지양(멤버 변수의 접근 제어자(private))
	- 멤버 변수의 값을 확인할 수 있는 접근 가능한 메서드 추가

	
	정보은닉
```java
		package exam02;

		public class Schedule {
			private int year;
			private int month;
			private int day;

			public void setYear(int _year) {
				this.year = _year;
			}
			public void setMonth(int _month) {
				this.month = _month;
			}
			public void setDay(int _day) {
				if(this.month == 2 && _day > 28) {
					this.day = 28;
				}
			}
			public int getYear() {
				return this.year;
			}
			public int getMonth() {
				return this.month;
			}
			public int getDay() {
				return this.day;
			}
			void showDate() {
				System.out.printf("year=%d, month=%d, day=%d%n", year, month, day);
			}
			
			public Schedule returnThis() {
				return(this);
			}
			
		}

		package exam02;

		public class Ex01 {
			public static void main(String[] args) {
				Schedule s1 = new Schedule();
				s1.setYear(2024);
				s1.setMonth(2);
				s1.setDay(31);

				s1.showDate();
				int month = s1.getMonth();
				System.out.println(month);				
			}
		}

```
	2. get(), set() 메서드 - 통제 가능하게 만들기 위해 getter setter 메서드 사용
	
		get멤버변수명();    -> getter 함수
		set멤버변수명();    -> setter 함수
		
		- 데이터 클래스 : 데이터를 담기 위한 클래스 - getter, setter 정의하는 클래스들
		
		package exam01;

		public class Ex03 {
			public static void main(String[] args) {
				Schedule s1 = new Schedule(2024,3,20);
				System.out.println(s1);  //s1.toString() -> 멤버변수 값을 확인하는 용도로 재정의
				System.out.println(s1.toString());  //윗줄과 동일함
			}
		}

		그런데 Schedule 클래스에 아래 추가하면
		@Override
		public String toString() {
			return "Schedule{" +
					"year=" + year +
					", month=" + month +
					", day=" + day +
					'}';
		}
		
		Ex03.java 다른 결과 => Schedule{year=2024, month=3, day=20}
		
		참고) Record 클래스
		
		package exam01;
		public record Schedule2(int year, int month, int day) {
		}

		package exam01;
		public class Ex04 {
			public static void main(String[] args) {
				Schedule2 s1 = new Schedule2(2024,4,17);
				int year = s1.year();
				int month = s1.month();
				int day = s1.day();

				System.out.println(s1);    
			}
		}

		>> Schedule2[year=2024, month=4, day=17]
		
	this 예약어	
		
		(변수)
		- 인스턴스 메서드안에서 사용하는 지역변수 
		- 메서드 안에서 자신 객체의 자원에 접근하기 위한 주소값 
		(함수)
		- 클래스 내부에서 정의된 생성자를 호출
		- 생성자 함수(메서드) 내부에서 주로 사용
		- 생성자 메서드의 첫줄에만 사용 가능
		
		package exam01;

		public class Schedule {
			private int year;
			private int month;
			private int day;

			public Schedule(){
				this(2024, 4, 17);  // this - 생성자 메서드안에서 다른 생성자 메서드 호출, 첫줄이어야함
			}
			public Schedule(int year, int month, int day) {
				this.year = year;
				this.month = month;
				this.day = day;
			}
		}
		
		
	
	static 변수
		static : 정적인, 고정된 
		
		정적메모리 - 데이터영역 메모리, 변하지 않는 데이터
		            프로그램 코드 & 상수 영역 : 애플리케이션 시작시 생성, 종료시 제거
		동적메모리 - 스택영역메모리, 힙영역 메모리
		
		정적변수 static 
		 - 객체가 생성되는 것과 상관 없이  프로그램 처음 로드될때 부터 공간 할당
		 - 클래스명으로 직접 접근 가능
		 - 그래서 "클래스 변수" 라고 함
		 
		package exam02;
		public class Student {
			public static int id;
			private String name;
			private String subject;

			public static void staticMethod() {
				System.out.println("정적메서드");  // 여기선 this 사용 못함 : 객체와 상관 없으므로 객체의 자원 사용 못함
			}
		}

		package exam02;
		public class Ex02 {
			public static void main(String[] args) {
				Student.id = 1000;     // 객체 생성 전부터 사용가능, 인스턴스 메서드에서 사용할때도 Student.id이렇게 사용하기
				Student.staticMethod();   // 객체 생성 전부터 사용가능
			}
		}

	static 메서드
		- 객체생성과 상관없이 사용 가능
		- 클래스명으로 직접 호출 가능
		- 그래서 "클래스 메서드" 라고 함
		- 그래서 static 메서드 내에서 this 못씀, 객체의 자원을 사용할 수 없음
		
		
		package exam02;

		public class Ex03 {
			public static void main(String[] args) {
				int result = add(10, 20);
				System.out.println(result);
			}
			public static int add(int num1, int num2) {   // ****왜 static 썻는지 중요****
				return num1 + num2;
			}
		}
		

변수의 유효범위
	1. 지역변수 
		- 함수가 호출되고 실행될때만 스택에 공간 할당
		- 함수 연산 종료되면(return) 스택에서 제거
		- 함수 지역 안에서만 유효함
		
	2. 멤버변수(인스턴스 변수)
		- 객체가 생성되면 힙메모리에 공간 할당
		- 객체의 참조가 끊어지면(더이상 사용하는 참조변수가 없을때 가비지 콜렉터가 제거
		
	3. static 변수 
		- 애플리케이션 로딩 시 처음부터 데이터 영역에 할당되는 변수
		- 애플리케이션 종료시 제거되는 변수

	static 응용 - 싱글톤 패턴	
		- 메모리를 절약하는 방법 패턴
	
	javadoc
	
	java.lang.Math : 수학관련 편의 기능 모음 
	
		Field : static final double PI  => 스태틱 상수 PI => Math.PI
		Method : static int max(int a, int b)	

	java.util.Arrays
		method 에 다 static 붙어있음 -> 클래스명으로 접근하는 메서드
		
		=> Arrays.toString(arr) : Array클래스의 클래스 메서드를 사용하는 방법
		
	편의기능 모음 클래스는 객체 생성할 필요없이 static으로 정의하는 경우 많다.
	문제점: 사용하지 않아도 메모리를 차지하는 문제 -> 싱글톤으로 해결
	
	
	객체를 매번 생성하는 경우 :  데이터 클래스
	
	
	참고) 디자인 패턴
		싱글톤패턴 (메모리 절약 위해)
		
		1) 기능을 담당하는 객체는 여러개 만들 필요 없다
		2) 이 기능이 항상 필요한건 아니므로 필요할때만 객체 생성
		3) 객체는 필요할때 하나만 생성하여 공유한다
		
		만드는 방식
		1) 생성자를 외부에서 직접 호출하지 못하게 막기, private 생성자 메서드
		2) 클래스 내부에서 정적(static)변수로 선언만 했다가
		3) 클래스 내부에 생성된 객체를 반환하는 정적(static)함수
		 - 최조 호출할때 객체 생성함
		4) 동시성 작업시에는 동기화처리 해야함(lock걸고..-> 나중에 쓰레드에서)
		 
			package exam01;
			public class Board {
				//private static Board instance = new Board();  //클래스 로드 시점 부터 객체 생성, 메모리 차지하므로 아랫줄처럼 선언만
				private static Board instance;

				private Board() { }

				public static Board getInstance() {
					if(instance == null) {
						instance = new Board();  // 필요한 시점에 1번만 객체 생성
					}
					return instance;
				}
			}
					 
			package exam01;
			public class Ex01 {
				public static void main(String[] args) {
					/* b1, b2 계속 생성
					Board b1 = new Board();
					Board b2 = new Board();

					System.out.println(b1 == b2);
					System.out.println("b1주소: " + System.identityHashCode(b1));
					System.out.println("b2주소: " + System.identityHashCode(b2));
					*/

					Board b1 = Board.getInstance();
					Board b2 = Board.getInstance();

					System.out.println(b1 == b2);
					System.out.println("b1주소: " + System.identityHashCode(b1));
					System.out.println("b2주소: " + System.identityHashCode(b2));

				}
			}
		
	(참고) static block
	static {
		//클래스 로드되는 순간 실행되는 부분 정의
	}
