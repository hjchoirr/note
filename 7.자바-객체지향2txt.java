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
1. 메소드 재정의 : 상속시에만 가능, 인스턴스매서드에서만 가능	
	하위클래스에 상위클래스와 동일한 매서드가 있으면 하위클래스의 매서드가 우선
	-> 매서드 오버라이딩
	
	상속은 객체간에만 가능
	
	인스턴스 생성될때 가상 매서드 테이블에 공간 
	
	
2. 묵시적 형변환과 메서드 재정의
	
	애노테이션(Annotation) : 주석, 주해
	형식: @이름  -> 정보전달
	
	@Override : 재정의된 매서드임을 컴파일러에게 알려주는 정보
	
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

final 예약어
1. 변수 
2. 메서드
3. 클래스 

인터페이스
1. 인터페이스란?
2. 인터페이스  문법 
3. 클래스에서 인터페이스 구현하기
4. 인터페이스 구현과 형변환(다형성)
5. 인터페이스의 요소 살펴보기
	1) 인터페이스 상수 
	2) 디폴트 메서드
	3)  정적 메서드
	4)  private 메서드 
	
인터페이스 활용하기
1. 한 클래스가 여러 인터페이스를 구현하는 경우
2. 두 인터페이스의 디폴트 메서드가 중복되는 경우
3. 인터페이스 상속하기