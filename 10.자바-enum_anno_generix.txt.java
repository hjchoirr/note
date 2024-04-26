4/26

열거형(enums)

	1. 열거형이란?
		상수만을 위한 클래스의 일종
		
		java.util.Calendar : 정적 상수 무지 많은데 .. (java8 이전)
		java.time.temporal.ChronoField   : 이제 enum 클래스 생겨서 별도의 enum class에 상수를 담음
		java.time.temporal.ChronoUnit
		java.time.LocalDate
		
	2. 열거형 정의와 사용
		enum 클래스명 {
			상수,상수,상수..
		}
		
		상수간의 비교 == 이렇게
		
		enum 상수는 자바 클래스

			package exam01;
			public enum Transportation {
				BUS,
				SUBWAY,
				TAXI
			}

			package exam01;
			public class Ex01 {
				public static void main(String[] args) {
					Transportation trans1 = Transportation.BUS;
					Transportation trans2 = Transportation.BUS;
					System.out.println(trans1 == trans2);
				}
			}

			package exam01;
			public class Ex02 {
				public static void main(String[] args) {
					
					Transportation trans = Transportation.BUS;
					switch(trans) {
						case BUS: // Transportation.BUS 라고 쓰지 않는다
							System.out.println("버스");
							break;
						case SUBWAY:
							System.out.println("지하철");
							break;
						case TAXI:
							System.out.println("택시");
							break;
					}
				}
			}



	3. 모든 열거형의 상위 클래스 - java.lang.Enum
		enum Transportation {
			BUS,
			SUBWAY,
			TAXI
		}
		
		이렇게 만들면 컴파일러가 자동으로 아래처럼 바꾼다
		
		abstract class Transportation extends java.lang.Enum {
			public static final BUS = new Transportation();
			public static final SUBWAY = new Transportation();
			public static final TAX = new Transportation();
			
			private Transportation() {} 
		}

		//위 예제에서..
		Transportation trans = Transportation.BUS;
		Enum<Transportation> trans2 = trans;  // 다형성 사용 가능
		
		BUS : 상수형 객체
		
		package exam01;
		public class Ex03 {
			public static void main(String[] args) {
				Transportation BUS = Transportation.BUS;
				System.out.println(BUS instanceof Transportation);
				
				Enum trans = BUS; // 이것도 가능
			}
		}
		

	1) Enum 클래스에 정의된 메서드

		String name() : Enum 상수이름을 문자열로 반환 
			=> String toString() 동일
			
		int ordinal() : 상수의 위치번호
		String toString() : 
		
		static valueOf(..) : 변환 메서드
		
			--------------------------------------------------
			package exam01;
			public class Ex04 {
				public static void main(String[] args) {
					Transportation trans = Transportation.TAXI;
					String str = trans.name();  // => trans.toString();
					System.out.println(str);

					int ordinal = trans.ordinal(); // 상수의 위치번호 
					System.out.println(ordinal);  
				}
			}
			>>
			TAXI
			2  // 상수의 위치번호 
			
			package exam01;
			public class Ex05 {
				public static void main(String[] args) {
					
					String tran1 = "BUS";

					//주어진 스트링은 Transportation 클래스의 값이다. -> 스트링을 클래스로 변환 
					Transportation BUS = Transportation.valueOf(tran1); 
					
					//윗줄과 동일, 윗줄이 더 간단함
					//Transportation BUS = Enum.valueOf(Transportation.class, tran1); 

					System.out.println(BUS.name());
				}
			}
			>>
			BUS
			--------------------------------------------------
			
		

	2) 컴파일러가 자동으로 추가해주는 메서드

		valueOf(String str) : 호출 객체가 Enum 상수이므로 Class 객체는 안씀
		values() : 정의된 상수 목록을 배열로 반환
			-------------------------------------------------
			package exam01;
			import java.util.Arrays;

			public class Ex06 {
				public static void main(String[] args) {
					Transportation[] trans = Transportation.values(); // enum의 values() 메서드
					System.out.println(Arrays.toString(trans));
				}
			}
			>> [BUS, SUBWAY, TAXI]
			-------------------------------------------------
			package exam01;
			public enum Transportation {
				BUS(1400),
				SUBWAY(1250),
				TAXI(5000);

				private final int fare;
				
				Transportation(int fare) {   // 생성자 재정의 -> 자동으로 private 됨
					this.fare = fare;
				}
				
				public int getFare() {
					return fare;
				}
				
				public abstract int getTotal(int person); // 추상메서드
			}

			package exam01;
			public class Ex08 {
				public static void main(String[] args) {
					Transportation BUS = Transportation.BUS;
					int fare = BUS.getFare();
					System.out.println(fare);
				}
			}
			>>
			1400
			-------------------------------------------------


	4. 열거형에 멤버 추가하기
	5. 열거형에 추상메서드 추가하기

		package exam01;
		public enum Transportation {
			BUS(1350) {
				public int getTotal(int person) {
					return getFare() * person;
				}
			},
			SUBWAY(1450) {
				public int getTotal(int person) {
					return getFare() * person;
				}
			},
			TAXI(4800) {
				public  int getTotal(int person) {
					return getFare() * person;
				}
			};
			private final int fare;

			Transportation(int fare) {
				this.fare = fare;
			}

			public int getFare() {
				return fare;
			}

			public abstract int getTotal(int person);
		}

		package exam01;
		public class Ex08 {
			public static void main(String[] args) {
				Transportation BUS = Transportation.BUS;
				int fare = BUS.getFare();
				System.out.println(fare);
				int tot = BUS.getTotal(20);
				System.out.println(tot);
			}
		}


애너테이션(annotation)
	정보를 전달하기 위한 목적이 정해진 클래스의 일종

1. 에너테이션이란?
	주석, 주해..
	정보전달
	
	@interface 애노테이션명 {
	}
	
	컴파일러가 자동변환
	interface 애노테이션명 extends java.lang.annotation.Annotation {
	}
	
2. 표준 애너테이션 

	-JDK에 이미 정의된 애노테이션
	@Override : 재정의된 메서드임을 알려주는 목적
	@Deprecated : 향후 제거될 가능성이 있는 메서드임을 알려주는 목적
	@SuppressWarnings
	@SafeVarargs  : 가변 타입의 매개변수
	@FunctionalInterface : 함수형 인터페이스
	@Native : 시스템 OS 의 메서드
	

3. 메타 애너테이션 

	- 애노테이션을 정의할때 필요한 애노테이션
	
	- @Target : 애노테이션 적용범위 설정
	
		ElememtType : 적용범위 enum 상수
			METHOD : 메서드 위에 적용
			FIELD : 멤버변수
			CONSTRUCTOR : 생성자
			PARAMETER : 매개변수
			..
	- @Retention : 정보전달 시점
		RetentionPolicy
			SOURCE
				: 애노테이션이 java 파일 상에 유지, 컴파일 완료 후(class파일)에는 제거 
				   - 컴파일러가 필요한 정보
			CLASS
				:  기본값
				   - 애노테이션이 java 파일 상에 유지, 컴파일 완료 후에도 유지
				   - 정보 전달 안하고, 기본값임에도 사용하지 않는다.
				   
			RUNTIME
				: 애노테이션이 java파일 상에 유지, 컴파일 완료 후(class 파일) 유지
					- 정보전달이 실행중에 제공
					
	단순 정보전달 목록 (설정X) : 마커 애노테이션 - @Override
	

	애노테이션에서 추가정보전달 
		- 추상메서드 정의
	
	

		package exam03;
		import static java.lang.annotation.ElementType.*;
		//import java.lang.annotation.ElementType; 
		import java.lang.annotation.Retention;
		import java.lang.annotation.RetentionPolicy;
		import java.lang.annotation.Target;

		@Target({TYPE, METHOD, FIELD})  //배열형식으로 여러가지 범위로 지정가능
		
		//위 import 문 막은거랑 한쌍임
		//@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD}) 
		@Retention(RetentionPolicy.RUNTIME)
		public @interface MyAnno {

		}


		package exam03;
		@MyAnno
		public class Ex01 {

			@MyAnno
			private String str;

			@MyAnno
			public static void main(String[] args) {

			}
		}
		package exam03;
		@MyAnno(min=10, max=100)
		public class Ex02 {
		}

		package exam03;
		import java.lang.annotation.Annotation;

		public class Ex03 {
			public static void main(String[] args) {

				Class cls = Ex02.class;

				MyAnno anno = (MyAnno)cls.getAnnotation(MyAnno.class);
				int min = anno.min();
				int max = anno.max();

				System.out.printf("min=%d, max=%d%n", min, max);

			}
		}
		-----------------------------------------
		package exam03;
		import static java.lang.annotation.ElementType.*;
		import java.lang.annotation.Retention;
		import java.lang.annotation.RetentionPolicy;
		import java.lang.annotation.Target;

		@Target({TYPE, METHOD, FIELD})  //배열형식으로 여러가지 범위로 지정가능
		@Retention(RetentionPolicy.RUNTIME)
		public @interface MyAnno {
			int min() default 10; // nin 설정항목, 기본값 10
			int max() default 100; // max 설정항목, 기본값 100
		}


		package exam03;
		public class Ex02 {

		}
		-----------------------------
		package exam03;

		import static java.lang.annotation.ElementType.*;
		import java.lang.annotation.Retention;
		import java.lang.annotation.RetentionPolicy;
		import java.lang.annotation.Target;

		@Target({TYPE, METHOD, FIELD})  //배열형식으로 여러가지 범위로 지정가능
		@Retention(RetentionPolicy.RUNTIME)
		public @interface MyAnno {
			int min() default 10; // nin 설정항목, 기본값 10
			int max() default 100; // max 설정항목, 기본값 100
			String[] names() default {"이름1"};
		}


		package exam03;
		@MyAnno(names={"이름1", "이름2"})
		public class Ex02 {

		}

		package exam03;
		import java.lang.annotation.Annotation;
		import java.util.Arrays;

		public class Ex03 {
			public static void main(String[] args) {

				Class cls = Ex02.class;

				MyAnno anno = (MyAnno)cls.getAnnotation(MyAnno.class);
				int min = anno.min();
				int max = anno.max();

				System.out.printf("min=%d, max=%d%n", min, max);
				String[] names = anno.names();
				System.out.println(Arrays.toString(names));
			}
		}
		>>
		min=10, max=100
		[이름1, 이름2]
		-----------------------------

		package exam03;
		import java.lang.annotation.ElementType;
		import java.lang.annotation.Retention;
		import java.lang.annotation.RetentionPolicy;
		import java.lang.annotation.Target;

		@Target(ElementType.TYPE)
		@Retention(RetentionPolicy.RUNTIME)
		public @interface Todos {
			Todo[] value();
		}

		package exam03;
		import java.lang.annotation.*;

		@Target(ElementType.TYPE)
		@Retention(RetentionPolicy.RUNTIME)
		@Repeatable(Todos.class)
		public @interface Todo {
			String value();
		}
		
		package exam03;
		
		@Todo("할일1")
		@Todo("할일2")
		@Todo("할일3")
		public class Ex04 {
			public static void main(String[] args) {
				Class cls = Ex04.class;
				Todos todos = (Todos)cls.getAnnotation(Todos.class);
				Todo[] todo = todos.value();

				for(Todo t : todo) {
					System.out.println(t.value());
				}
			}
		}
		>>
		할일1
		할일2
		할일3		
		
지네릭스

1. 지네릭스란?

참고)

- 다양한 자료형을 수용 -> Object 클래스 사용 
	단점
	1) 타입 안정성 X 
	2) 형변환의 번거로움
	
		package exam04;
		public class Box {
			private Object item;
			
			public Object getItem() {
				return item;
			}
			public void setItem(Object item) {
				this.item = item;
			}
		}

		package exam04;
		public class Apple {
			public String get() {
				return "사과";
			}
		}
		package exam04;
		public class Grape {
			public String get(){
				return "포도";
			}
		}

		package exam04;
		public class Ex01 {
			public static void main(String[] args) {
				Box appleBox = new Box();
				appleBox.setItem(new Apple());

				Apple apple = (Apple) appleBox.getItem(); //형변환의 번거로움 
				System.out.println(apple.get());

				Box grapeBox = new Box();
				grapeBox.setItem(new Grape());

				if(grapeBox.getItem() instanceof Apple) { //타입 안정성이 떨어지는 문제 발생
					Apple grape = (Apple) grapeBox.getItem();
					System.out.println(grape.get());
				}
			}
		}
		-------------T 사용 --------------------------------
		package exam05;
		public class Box<T> { // T : Type,자료형
			private T item;

			public T getItem() {
				return item;
			}
			public void setItem(T item) {
				this.item = item;
			}
		}

		package exam05;
		public class Ex01 {
			public static void main(String[] args) {
				
				Box<Apple> appleBox = new Box<Apple>();  // Apple type으로 한정함
				appleBox.setItem(new Apple());
				
				//appleBox.setItem(new Grape());  // 이거 안됨 => Object 형을 사용하지 않고 T에 Apple 명시했으므로

				Apple apple = appleBox.getItem();
				System.out.println(apple.get());
			}
		}

		컴파일러는 T 를 Object 로 바꿔줌 -> 객체 생성 시 투입된 자료형(Apple)으로 Object를 형변환
		

2. 지네릭 클래스의 선언

3. 지네릭스의 용어
1) 지네릭 클래스
	class Box<T> { .... }
2) 타입변수 T
3) 원시타입 Box 

타입 매개변수는 컴파일시 제거 -> Object -> 객체가 생성될때 타입 매개변수의 자료형으로 형변환 
(타입은 객체 생성시 결정)

사용의 제한점)
	static 멤버 변수에는 타입 매개변수 사용 불가 
		- 처음부터 자료형이 명시 되어야 공간을 할당 
	
	배열 생성 불가 
		- new 연산자 : 배열 공간 생성 : 명확한 자료형을 알아야 공간을 계산 ..

4. 지네릭스의 제한
1) 타입 매개변수와 동일한 자료형
2) static멤버에 타입 변수 T를 사용할 수 없다.
3) 지네릭 타입의 배열을 생성하는 것도 허용하지 않는다.

5. 지네릭 클래스의 객체 생성과 사용
6. 제한된 지네릭 클래스
	<T extends 타입> -> T 는 타입의 하위 클래스 
	<T extends 타입1 & 타입2> -> T는 타입1의 하위 클래스 이고 타입2 인터페이스의 구현 클래스 
	
7. 와일드 카드
	<?> : <? extends Object>
	<? extends 타입> -> T는 타입의 하위 클래스 : 타입으로 상한 제한 
	<? super 타입> -> T는 타입의 상위 클래스 : 타입으로 하한 제한
8. 지네릭 메서드
9. 지네릭 타입의 제거		