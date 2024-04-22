상속

1. 상속이란?
2. 클래스의 상속
3. 클래스 상속 문법
	class 자식클래스 extends 부모클래스
	
5. super
	자식클래스 생성자 함수에는 super() 자동 추가됨
	
	지역변수 
		상위 클래스객체의 주소값
		(참고) this : 현재 클래스객체의 주소값
		
	함수(메서드)
		super(..) : 현재 클래스의 생성자 함수를 클래스 내부에서 호출할때 사용
		(참고) this(..) : 현재 클래스의 생성자 함수를 클래스 내부에서 호출할때 사용 
	
	
	하위클래스 객체를 상위클래스의 참조변수에 대입하면 
	하위클래스의 객체 -> 상위클래스의 자료형으로 자동 형변환가능 : 다형성
					-> but 상위클래스의 자원만 사용가능


		C c = new C();
        A a = c;
        B b = c;
        
        A a = new C();	// 위의 두줄과 동일
		
	instanceof 연산자
		System.out.println(a instanceof A); // a는 A클래스의 객체인가? 뜻 ( js 는 다름 )
	
	instance: 실체 - 실제 존재
	클래스는 객체의 정의일 뿐 존재하진 않음 -> new -> 힙 공간에 instance 생성됨
	
		package Exam02;
		public class A {
			int numA = 10;
			public A() {
				System.out.println("A 생성자");
			}
		}

		package Exam02;
		public class B extends A{
			int numB = 20;
			public B() {
				System.out.println("B 생성자");
			}
		}
		package Exam02;
		public class C extends B {
			int numC = 30;
			public C() {
				super();  // B 클래스에 정의된 생성자함수
				System.out.println("C 생성자");
			}
		}       
		package Exam02;
		public class Ex03 {
			public static void main(String[] args) {
				C c = new C();
				System.out.println(c instanceof C);
				System.out.println(c instanceof B);
				System.out.println(c instanceof A);
			}
		}		
		package Exam02;
		public class D extends A{
			int numD = 40;
		}
		-----------------------------------
		package Exam02;

		public class Ex04 {
			public static void main(String[] args) {
				A ad = new D();
				A ac = new C();       // 상위클래스 자원 범위로 좁힐때 

				//C c = (C) ad;  // 에러

				if(ad instanceof C) {
					System.out.println( "ad 는 C의 인스턴스 ");
					C c = (C) ad;     // ad 는 C의 인스턴스가 아니므로 실행 안됨
				}
				if(ac instanceof C) {
					System.out.println("ac는 C의 인스턴스");
					C cc = (C) ac;    // 하위클래스 자원범위로 넓힐때 캐스팅하여 더 많은 정보 사용
				}
			}
		}

	상위클래스 : 일반적인 개념 정의  - 자원(속성)의 양이 더 적음
	하위클래스 : 구체적인 개념 정의  - 자원(속성)의 양이 더 많음
	
	Animal 클래스 (일반적 개념)
	
	Human 클래스 (구체적 개념)  
	Bird  클래스 (구체적 개념)
	Tiger 클래스 (구체적 개념)
	
	package exam03;
	public class Animal {

		public void move() {
			System.out.println("움직인다");  // <- 실제로는 쓰이지 않고 틀만 제공함
		}
	}


	package exam03;

	public class Ex01 {
		public static void main(String[] args) {

			Human human = new Human();
			//human.move();

			Tiger tiger = new Tiger();
			//tiger.move();

			Bird bird = new Bird();
			//bird.move();

			Animal[] animals = new Animal[3];

			animals[0] = human;
			animals[1] = tiger;
			animals[2] = bird;


			/*for(int i = 0; i < animals.length; i++) {
				animals[i].move();
			}*/
			for(Animal animal : animals) {
				animal.move();
			}
		}
	}
	package exam03;

	import java.util.Arrays;

	public class Ex01 {
		public static void main(String[] args) {

			Human human = new Human();
			//human.move();

			Tiger tiger = new Tiger();
			//tiger.move();

			Bird bird = new Bird();
			//bird.move();

			Animal[] animals = new Animal[3];

			animals[0] = human;
			animals[1] = tiger;
			animals[2] = bird;


			/*for(int i = 0; i < animals.length; i++) {
				animals[i].move();
			}*/
			for(Animal animal : animals) {
				animal.move();
			}
		}
	}
	package exam03;

	public class Ex02 {
		public static void main(String[] args) {
			Animal[] animals = { new Human(), new Bird(), new Tiger() };
			// 위 한줄과 동일
			//Animal[] animals = new Animal[3];
			//animals[0] = human;
			//animals[1] = tiger;
			//animals[2] = bird;

			for(Animal animal: animals) {
				animal.move();   // 각각 하위 클래스의 오버라이드된 메서드 move() 실행됨
			}
		}
	}
	>>>
	두발로 직립보행
	두 날개로 날아간다.
	네발로 걸어다닌다


메서드 재정의
1. 메소드 재정의 : 상속시에만 가능, 인스턴스메서드에서만 가능	
	하위클래스에 상위클래스와 동일한 메서드가 있으면 하위클래스의 메서드가 우선
	-> 메서드 오버라이딩
	
	상속은 객체간에만 가능
	
	인스턴스 생성될때 가상 메서드 테이블에 공간 
	
	
2. 묵시적 형변환과 메서드 재정의
	
	애노테이션(Annotation) : 주석, 주해
	형식: @이름  -> 정보전달
	
	@Override : 재정의된 메서드임을 컴파일러에게 알려주는 정보
	
	package exam03;
	public class Human extends Animal {
		@Override 
		public void move() {
			System.out.println("두발로 직립보행");
		}
	}	
	
	
	재정의시 접근제어자 변경 범위
	좁은 범위 에서 넓은 범위로 변경가능 : private은 접근 자체가 안되므로 불가
	
	
3. 가상메서드


다형성  
1. 다운 캐스팅과 instanceof		

	package exam03;

	public class Ex02 {
		public static void main(String[] args) {
			Animal[] animals = { new Human(), new Bird(), new Tiger() };
			// 위 한줄과 동일
			//Animal[] animals = new Animal[3];
			//animals[0] = human;
			//animals[1] = tiger;
			//animals[2] = bird;

			for(Animal animal: animals) {
				animal.move();

				if(animal instanceof Human) {
					Human human = (Human) animal;
					human.reading();
				} else if(animal instanceof Tiger) {
					Tiger tiger = (Tiger)  animal;
					tiger.hunting();
				}
			}
		}
	}

	------------------------------------------------------
	package exam03;
	public class Student {
		protected int id;
		protected String name;
		
		public Student(int id, String name) {
			this.id = id;
			this.name = name;
		}
	}
	package exam03;
	public class HighSchoolStudent extends Student {

		public HighSchoolStudent() {
			super(1000, "hjchoi");
		}
	}


추상 클래스
	추상적: 어떤일을 해야할지 결정되니 않은, 정해지지 않은, 공통적인
	구체적: 어떤일을 할지 명확하게 정의

1. 추상 클래스란?
	구성요소중에서 정해지지 않은 클래스
	- 함수가 수행할 내용이 결정되지 않은 클래스
	- 함수의 정의만 있는 클래스(함수 수행내용이 없음)
	- 하위 클래스가 꼭 따라야 할 것만 확실히 정해 놓은 불완전한 클래스
	
2. 추상클래스 문법

	public abstract class 클래스명 {
		public abstract add(int num1, int num2);
	}
	
	package exam05;
	public abstract class Calculate {

		public abstract int add(int num1, int num2) ;

	}
	--------------------------------------------
	package exam05;
	public abstract class Calculate {                  //추상클래스 정의
		int num = 10;

		public abstract int add(int num1, int num2) ;  //추상클래스의 멤버함수 미정의
	}

	package exam05;
	public class SimpleCalculate extends Calculate{   // 추상클래스를 상속받으면서 상위클래스의 멤버함수 정의

		@Override
		public int add(int num1, int num2) {
			return num1 + num2;
		}
	}

	package exam05;
	public class Ex01 {
		public static void main(String[] args) {
			SimpleCalculate cal = new SimpleCalculate();   // 추상클래스를 상속받은 하위 클래스로 인스턴스 만들고
			int result= cal.add(10,20);                    //  사용
			System.out.println(result);
			System.out.println(cal.num);
		}
	}
	--------------------------------------------
	
4/22

		예를 들어 추상클래스  InputStream javadoc에서 보면
		public abstract class InputStream <- 추상메서드

		abstract int read()    <- 추상 메서드 

		Direct Known Subclasses: <- InputStream을 상속받은 클래스들
		AudioInputStream, ByteArrayInputStream, FileInputStream, FilterInputStream, ObjectInputStream, PipedInputStream, SequenceInputStream, StringBufferInputStream
		
		FileInputStream 눌러보면 read 메서드


	3, 추상메서드의 원칙 : 하위 클래스가 따라야할 설계원칙
	   하위 클래스가 공유해야할 공통적인 자원을 정의 
	
		package exam01;
		public abstract class Calculator {
			int num = 10;

			public Calculator() {
				System.out.println("Calculator 생성자 num: " + num);
			}
			public abstract int add(int num1, int num2);
			public void commonMethod() {
			}
		}

		package exam01;
		public class SimpleCalculator extends Calculator {
			public int add(int num1, int num2) {
				return num1 + num2;
			}
		}


		package exam01;
		public class ComplexCalculator extends Calculator {
			public int add(int num1, int num2) {
				return num1 + num2;
			}
		}	

		package exam01;
		public class Ex01 {
			public static void main(String[] args) {
				SimpleCalculator cal = new SimpleCalculator() ;
				int ret = cal.add(20, 30);
				System.out.println("cal.add : " + ret);
				System.out.println(cal.num);
			}
		}
	
	

final 예약어
   - 마지막, 최종적
   - 변경 불가 뜻
   
	1. 변수 : 상수
	2. 메서드 : 재정의 불가
		- 템플릿 메서드 패턴
		- 절차에 대한 메서드
	3. 클래스 : 상속 불가
	
			package exam02;
			public abstract class Car {
				public abstract void start();
				public abstract void press();
				public abstract void turnoff();

				public void run() {   // 절차 정의하는 메서드 -> 템플릿 메서드 패턴
					start();
					press();
					turnoff();
				}
			}
			
			package exam02;
			public class SportsCar extends Car {
				public void start() {
					System.out.println("시동 켜기");
				}
				public void press() {
					System.out.println("엑셀밟기");
				}
				public void turnoff() {
					System.out.println("시동끄기");
				}
			}

			package exam02;
			public class Ex01 {
				public static void main(String[] args) {
					SportsCar sCar = new SportsCar();
					sCar.run();
				}
			}
		
인터페이스

1. 인터페이스란?
	Interface : 설계 목적의 클래스
		- 추상 메서드만 정의하기 위한 메서드
	
	참고) API: Application Programming Interface - 개발시 필요한 설계 가이드 라인
		 JDBC API ( Java DataBase Connectivity Applcation Programming Interface )
	
2. 인터페이스 문법 
	interface 인터페이스명 {
		추상메서드 정의..
	}

	package exam03;
	public interface Calculator {
		int add(int num1, int num2); // 인터페이스 일때는 public abstract 자동 추가됨
	}
	package exam03;
	public class SimpleCalculator implements Calculator {
		public int add(int num1, int num2) { // public 꼭 붙여라***
			return num1 + num2;
		}
	}	
	
3. 클래스에서 인터페이스 구현하기  ( absstract 는 extends 로 구현)
	implements : 구현하다     
	class 클래스면 implements 인터페이스1, 인터페이스2,...{
	}
	
	주문: 판매자, 구매자 

--------------------------
추상클래스 vs 인터페이스
--------------------------
추상클래스
	추상메서드(설계틀 목적) abstract 붙임
	공통메서드(인스턴스 메서드) : 하위 클래스가 공유할 자원
	-> 메서드 2가지 

인터페이스의 목적 - 설계
	추상메서드 만 : 설계상의 규제, 틀 , abstract 안붙임,  public abstract 자동 추가됨
--------------------------

4. 인터페이스 구현과 형변환(다형성)
5. 인터페이스의 요소 살펴보기

	1) 인터페이스 상수 

		package exam03;
		public interface Calculator {
			int num = 10; // 인터페이스는 변수에 public static final 자동추가  -> NUM 으로 쓰기
			int add(int num1, int num2); // 인터페이스는 메서드에 public abstract 자동 추가됨
		}

		컴파일러가 자동으로 추가해 주는 것들
			1. 디폴트생성자 //생성자를 정의하지 않은 경우 
			2. super() 모든 생성자 메서드의 첫줄 super() // super()를 정의하지 않은 경우
			3. 참조변수.toString()
	
	2) 디폴트 메서드
		- 자바7부터 추가
		- 완전 구현된 인스턴스 메서드 추가 가능
	
	3) 정적 메서드 (static)
		- 원래 가능
		- 객체 생성과 관련 없이 독자적으로 사용 가능
		- 
		
	4) private 메서드 
		package exam04;
		public interface Buyer {
			void buy();

			default void order() {
				System.out.println("바이어에서 주문");
				privateMethod();
			}
			private void privateMethod() {
			}
		}	
	
	
인터페이스 활용하기

1. 한 클래스가 여러 인터페이스를 구현하는 경우
	참고) 클래스에서 상속은 단일 상속만 가능
	
	interface A 
		- void method()
	interface B 
		- void method()
	
	class C implements A, B
		- public void method() {..} 

	package exam05;
	public class Ex01 {
		public static void main(String[] args) {
			C c = new C();
			c.method(); // C에서 구현한 method() , 헷갈리지 않는다
		}
	}		
	인터페이스는 추사메서드 이기때문에 호출 주체는 명확( 구현한 클래스의 메서드) - 다중 메서드 구현 가능
	클래스에서는 다중 상속시 인스트턴스 메서드가 동일하면 어느것의 구현인지 알수없으므로 다중 상속 불가능
	
2. 두 인터페이스의 디폴트 메서드가 중복되는 경우
	package exam04;
	public interface Seller {
		void sell();

		default void order() {
			System.out.println("Seller에서 주문");
		}

		static void staticMethod() {

		}
	}
	package exam04;
	public interface Buyer {
		void buy();

		default void order() {
			System.out.println("바이어에서 주문");
			privateMethod();
		}
		private void privateMethod() {
		}
	}

	package exam04;
	public class Order implements Seller, Buyer {
		@Override
		public void buy() {
			System.out.println("구매");
		}
		@Override
		public void sell() {
			System.out.println("판매");
		}
		public void order() {
			Buyer.super.order();   // Buyer, Seller 중 어떤 order인지 명확히 하기
		}
	}
	package exam04;
	public class Ex02 {
		public static void main(String[] args) {
			Order order = new Order();
			order.order(); // Buyer의 order 메서드
		}
	}

3. 인터페이스 상속하기(extends)

	- 개념간 체계 나눌때
	- 다중 상속 가능(추상 메서드)

		
		package exam09;
		public interface X {
			void method1();
			void method();
		}
		package exam09;
		public interface Y {
			void method2();
			void method();
		}
		package exam09;
		public interface Z extends X, Y{
			void method3();
		}
		package exam09;
		public class Ex01 implements Z{
			@Override
			public void method3() {

			}

			@Override
			public void method1() {

			}

			@Override
			public void method2() {

			}

			@Override
			public void method() {
				System.out.println("Ex01 에서 호출한 메서드");

			}
		}
		

	(참고) 컬렉션 프레임워크
		- Collection : 데이터 군집 : 자료
		- Framework : 표준적인 설계틀
		
		자료구조
		collection 인터페이스 
		  List 인터페이스 : 순차 자료구조 설계
		  Set 인터페이스 : 집합 자료구조 설계
		
		Map 인터페이스 : 사전 자료 구조
		
		
		
		
내부 클래스
 - 클래스 안에 클래스가 정의된 형식
	
1. 인스턴스 내부 클래스 
	- 맴버 변수, 맴버 메서드와 비슷한 성격
	- 외부 클래스의 객체 생성 이후 접근 가능한 클래스
		package exam10;
		public class Outer {

			int num1 = 10;
			class Inner {
				int num1 = 15;
				int num2 = 20;   // 인스턴스 내부 클래스
				public void method() {

					System.out.println("인스턴스 내부 클래스에서 호출");
					System.out.printf("num1 + num2 = %d%n", num1 + num2);
					System.out.printf("num1 + num2 = %d%n", Outer.this.num1 + num2);  // Outer.this.num1 != num1
				}
			}
		}
		package exam10;
		public class Ex01 {
			public static void main(String[] args) {
				Outer outer = new Outer();
				Outer.Inner inner = outer.new Inner();  // 내부 클래스 인스턴스 생성
				inner.method();
			}
		}

2. 정적 내부 클래스 
	- 정적 변수, 정적 메서드와 비슷한 성격
	- 객체 생성과 상관없이 접근 가능(외부 클래스명으로 접근 가능)
	- static이므로 Outer 클래스의 자원에 접근하지 못함
	
		package exam11;
		public class Outer {
			static class Inner {
				public void method() {
					System.out.println("정적 내부 클래스에서 메서드 호출");
				}
			}
		}

		package exam11;
		public class Ex01 {
			public static void main(String[] args) {
				Outer.Inner inner = new Outer.Inner(); // stattic 내부 클래스 이므로 외부 클래스 객체 생성 없이도 사용 가능함
				inner.method();
			}
		}	
		------------------------------------------------------------------
		package exam11;
		public class Outer {
			int num1 = 10;
			static int num2 = 20;

			static class Inner {
				public void method() {
					System.out.println("정적 내부 클래스에서 메서드 호출");
				}
			}
		}

		package exam11;
		public class Ex01 {
			public static void main(String[] args) {
				Outer.Inner inner = new Outer.Inner();
				inner.method();
				//System.out.println(num1);  // static 메서드 이므로 외부 클래스의 자원에 접근하지 못함: 에러
				System.out.println(Outer.num2); // static 변수는 사용 가능함
			}
		}

3. 지역 내부 클래스 
	 - 함수 안에 정의된 클래스
	 
		package exam12;
		public class Outer {
			public void method() {

				class Inner {
					public void method() {
						System.out.println("지역 내부 클래스에서 호출!");
					}
				}

				Inner inner = new Inner();
				inner.method();
			}
		}
		package exam12;
		public class Ex01 {
			public static void main(String[] args) {
				Outer outer = new Outer();
				outer.method();
			}
		}
		-> 이런 형태는 거의 안쓰고 아래는 무지 씀
	 
	 - 인터페이스, 추상클래스가 객체가되는 조건  *****중요*****
	   1) 지역 내부, 클래스의 멤버변수에서 객체 생성
	   2) 미구현된 메서드의 재정의를 통해 생성 
	   
		package exam13;
		public interface Calculator {
			int add(int num1, int num2);
		}

		package exam13;
		public class Outer {
			public void method() {
				Calculator cal = new Calculator() {
					@Override
					public int add(int num1, int num2) {
						return num1 + num2;
					}
				};
				int result = cal.add(10,20);
				System.out.println(result);
			}
		}

		package exam13;
		public class Ex01 {
			public static void main(String[] args) {
				Outer out = new Outer();
				out.method();
			}
		}
		--------------------------------------
		package exam13;
		public interface Calculator {
			int add(int num1, int num2);
		}
		
		package exam13;
		public class Outer {
			public Calculator method() {
				Calculator cal = new Calculator() {
					@Override
					public int add(int num1, int num2) {
						return num1 + num2;
					}
				};
				return cal;  // 인스턴스 변수를 반환 해서 외부에서도 사용하도록 함
			}
		}

		package exam13;
		public class Ex01 {
			public static void main(String[] args) {
				Outer out = new Outer();
				Calculator cal = out.method();
				int result = cal.add(10,20);
				System.out.println(result);
			}
		}
		-------------------------------------------------------
		package exam13;
		public interface Calculator {
			int add(int num1, int num2);
		}
		
		package exam13;
		public class Outer {
			public Calculator method(int num3) {   // 지역변수 추가하면 지역변수는 함수가 return하면 지역변수 스택제거되지만
				Calculator cal = new Calculator() {
					@Override
					public int add(int num1, int num2) {
						//num3 = 100; // final int num3 - 지역변수의 상수화: 지역변수가 제거되지 않도록
						return num1 + num2 + num3;
					}
				};
				return cal;
			}
		}
		package exam13;
		public class Ex01 {
			public static void main(String[] args) {
				Outer out = new Outer();
				Calculator cal = out.method(30);
				int result = cal.add(10,20);
				System.out.println(result);

				int result2 = cal.add(20,30);
				System.out.println(result2);
			}
		}

		package exam13;
		public class Ex02 {
			public static void main(String[] args) {    // 이렇게 많이 씀 ( 사용자 정의 함수 )
				Calculator cal = new Calculator() {
					@Override
					public int add(int num1, int num2) {
						return num1 + num2;
					}
				};
				int result = cal.add(10, 20);
				System.out.println(result);
			}
		}

예외처리
1. 오류와 예외
	오류(Error)	: 시스템의 오류, JVM 오류 ... : 통제 불가 오류
	
	예외(Exception) : 코드 상의 오류 : 통제 가능한 오류 
					       -  버그
	
2. 예외 클래스의 종류
	
	      Throwable 
		  
	Error           Exception
						

Exception 
	- Exception을 바로 상속 받은 예외 클래스
		예) java.io.IOException  / 파일을 읽을때, 쓸때 (FileInputStream, FileOutputStream)
			
			 java.io.FileNotFoundException
			 
			- 예외있든 없든 처리가 안되어 있으면 컴파일 X
			- 예외의 체크는 컴파일시 체크, 예외가 있으면 컴파일 X
			- 예외가 발생하든 안하든 반드시 적절한 예외 처리가 필요한 예외 
			- 엄격한 예외,  형식을 매우 중시 
			
	- RuntimeException을 중간에 상속 받은 예외 클래스
		- Runtime : 실행 
		예) java.lang.ArithmethicException  :  0으로 나눌때 발생 
		
		- 예외가 발생하더라도 컴파일 O, class 파일 생성 
		- 예외의 체크는 실행 중 체크, 실행이 되려면? class 파일 필요(컴파일은 된다...)
		- 유연한 예외, 형식은 X
		
	
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
	
3. try-with-resources문
	- JDK7에서 추가
	- 자원 해제를 자동 

	try ( 해제할 자원 객체;
			해제할 자원 객체 ...) {
		// 예외가 발생할 가능성이 있는 코드 
		
	} catch(예외 객체 ...) {
	
	}
	
	
	자원 자동해제의 기준
		AutoCloseable 인터페이스의 구현 클래스 
			- close() 메서드를 자동 호출 
			
	참고) 
		instanceof 
	

예외 처리 미루기
1. 예외 처리를 미루는 throws 사용하기
	- 메서드를 호출 하는쪽에서 예외 처리 전가 
	- 전가시키는 예외에 대해서 명시(throws)
		메서드 매개변수 뒤쪽에 throws 전가할 예외 작성
	- Exception을 상속 받은 경우(RuntimeException이 없는 경우)
	
2. 다중 예외 처리

	
3. 사용자 정의 예외
	- JDK 기본 정의 예외 외에 따로 작성하는 예외 