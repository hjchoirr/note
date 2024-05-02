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
		--------타입 매개변수 T 사용 --------------------------------
		package exam05;
		public class Box<T> { //Box<T> : 지네릭클래스 T:타입매개변수 Box: 원시타입
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
		
2. 지네릭 클래스의 선언

3. 지네릭스의 용어
1) 지네릭 클래스
	class Box<T> { .... }
2) 타입변수 T  : 참조자료형과 배열만 가능, 기본자료형은 불가능함
3) 원시타입 Box 

타입 매개변수는 컴파일시 제거 -> Object -> 객체가 생성될때 타입 매개변수의 자료형으로 형변환 
타입은 객체 생성시(실행시) 결정

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

		컴파일러는 T 를 Object 로 바꿔줌 (모든 클래스는 Object의 하위 클래스이므로)
		
		-> 객체 생성 시 타입힌트를 통해 자료형(Apple)으로 Object를 형변환 (Object -> Apple)
		    Box<Apple> appleBox = new Box<Apple>()
		
		-> 지네릭 타입의 자료형은 객체 생성 시점에만 결정되므로 처음부터 자료형이 결정되어야 하는 변수에는 사용하지 못함
			1) 정적변수에는 사용 불가 private staic T item : 이건 안됨
			2) 배열 사용 불가 : 메모리에 공간 생성 역할 
			   private T[] nums = new T[3]; // 안됨
			
		-> 이렇게 사용하는게 더 일반적임
		   Box<Apple> appleBox = new Box<>(); //아래줄과 동일함
		   Box<Apple> appleBox = new Box<Apple>();  
		   
		-> 메서드 내부에서 접근 가능한 지네릭 타입 인스턴스 자원은 모두 Object 형
			형변환된 객체가 정의된 인스턴스 자원 접근 안됨.
			<T extends Fruit> 으로 해결
			
			공통된 틀 Fruit 을 정해서 타입 매개변수의 하위 클래스임을 명시하면 

			package exam01;
			public abstract class Fruit {      // 껍데기 추상클래스 Fruit만들고 
				public abstract String get();
			}
			
			package exam01;  
			public class Apple extends Fruit {    //Fruit를 상속받아 정의
				public String get() {
					return "사과";
				}
			}
			package exam01;
			public class Grape extends Fruit{    //Fruit를 상속받아 정의
				public String get() {
					return "포도";
				}
			}

			package exam01;
			public class Box<T extends Fruit> { // T 는 Fruit의 하위클래스로 한정하면 Fruit의 get 사용가능
				private T item;

				public void setItem(T item) {
					this.item = item;
				}
				public T getItem() {
					return item;
				}
				public String toString() {  
					return item.get();    // Fruit을 상속받은 클래스로 T를 한정했으므로 가능함
				}
			}		   


6. 제한된 지네릭 클래스
	<T extends 타입> -> T 를 "타입"의 하위 클래스로 제한한다.. 
	<T extends 타입1 & 타입2> -> T를 "타입1" 의 하위 클래스 이고 "타입2" 인터페이스의 구현 클래스 
	
	package exam01;
	public class Box<T extends Fruit & Eatable> { //Fruit는 추상클래스, Eatable은 인터페이스까지 들어가면
		private T item;
	
		public void setItem(T item) {
			this.item = item;
		}
		public T getItem() {
			return item;
		}
		public String toString() {
			return item.get();
		}
	}
	
	package exam01;
	public interface Eatable {
	}	
	
7. 와일드 카드
	<?> : <? extends Object>
	<? extends 타입> -> T는 "타입"의 하위 클래스 : 타입으로 상한 제한 
		<? extends 클래스형 & 인터페이스형>  : 사용불가함
		
	<? super 타입> -> T는 "타입"의 상위 클래스 : 타입으로 하한 제한
	
	package exam02;
	public class Apple {
		public String toString() {
			return "사과";
		}
	}	
		
	package exam02;
	public class Grape {
		public String toString() {
			return "포도";
		}
	}

	package exam02;
	import java.util.ArrayList;

	public class Juicer {
		public static void make(FruitBox<?> box) {
			ArrayList<?> fruits = box.getItems();
			System.out.println(fruits);
		}
	}
	package exam02;
	import exam01.Apple;
	import exam01.Fruit;

	package exam02;

	public class Ex02 {
		public static void main(String[] args) {
			FruitBox<Grape> grapeBox = new FruitBox<>();
			grapeBox.add(new Grape());
			grapeBox.add(new Grape());
			Juicer.make(grapeBox);
		}
	}

	--------------------------------------------------
	package exam02;
	public class Ex03 {
		public static void main(String[] args) {
			FruitBox<Toy> toyBox = new FruitBox<>();
			toyBox.add(new Toy()); 
			Juicer.make(toyBox);    // 이게 되면 안되므로 아래와 같이 수정
		}
	}
	
	package exam02;
	public abstract class Fruit {

	}
	package exam02;
	public class Apple extends Fruit{
		public String toString() {
			return "사과";
		}
	}
	package exam02;

	public class Grape extends Fruit{
		public String toString() {
			return "포도";
		}
	}

	package exam02;
	import java.util.ArrayList;

	public class Juicer {
		public static void make(FruitBox<? extends Fruit> box) {
			ArrayList<?> fruits = box.getItems();
			System.out.println(fruits);
		}
	}
	--------------------------------------------------
	package exam02;
	import java.util.ArrayList;

	public class Juicer {
		public static void make(FruitBox<? extends Fruit> box) {
			ArrayList<?> fruits = box.getItems();
			System.out.println(fruits);
		}
	}
	
	
8. 지네릭 메서드
	타입을 클래스에 정의하면 : 지네릭 클래스
	예) clas Box<T> : 
	
	타입을 메서드 반환값 앞에 정의하면 : 지네릭 메서드
	public <T, U>String method(T, U)의 자료형은 함수가 함수가 호출될때 결정
	
		package exam03;
		public class Box<T> {
			private T item;

			// Box가 객체가 될때 T 결정됨 - 지네릭 클래스
			public void method1(T str1, T str2) {
				
			}

			//지네릭 메서드 - 호출시에 T의 자료형이 결정된다
			public <T> void method2(T str1, T str2) {
			}
		}	

9. 지네릭 타입의 제거		


지네릭스
1. 지네릭스란?

참고)
- 다양한 자료형을 수용 -> Object 클래스 사용 
	단점
	1) 타입 안정성 X  // instanceof 연산자 
	2) 형변환의 번거로움

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




package exam01;

public class Box {
    private Object item;

    public void setItem(Object item) {
        this.item = item;
    }

    public Object getItem() {
        return item;
    }
}

컴파일 시점 ? 
	자료형이 명확하게 결정되어 있어야 컴파일 가능
		-> 컴파일러가 알수 있는 형태로 자료형을 결정 
		    (모든 클래스는 Object의 하위 클래스임을 알고 있다 -> 자료형을 Object로 결정)
		
	<T> : 형식상 오류
	-> 컴파일 시점에 형식상의 오류를 해결하기 위해서 지네릭 타입은 모두 제거
	
	
자료형 결정 시점
객체를 생성하는 시점에 타입 힌트를 통해서 형변환이 발생(Object -> Apple)
예) Box<Apple> appleBox = new Box<Apple>();

-> 지네릭 타입의 자료형은 객체 생성 시점에만 결정되므로 처음부터 자료형이 결정되어야 하는 변수에는 사용 불가
	1) 정적 변수는 사용 불가
		private static T item (X)
		
	2) 배열 사용 불가 
		new 연산자 때문 : 메모리에 공간 생성 역할
		private static T[] nums; (X)
	

<T extends Fruit>
: T는 Fruit의 하위 클래스, 충분한 정보가 있으므로 Object가 아니라 T를 Fruit로 변경
public class Box {
    private Fruit item;

    public void setItem(Fruit item) {
        this.item = item;
    }

    public Fruit getItem() {
        return item;
    }
}


지네릭 클래스 T 
	-> 컴파일시에 형식 오류로 제거 
	-> 타입 매개변수 T는 우선 Object 변경 
	-> 객체를 만드는 시점에 있는 타입 힌트를 통해서 형변환이 발생
		Box<Apple> appleBox = new Box<>()
			-> Object -> (Apple)
		-> 제한 조건이 발생 
			: 처음부터 공간이 필요한 정적(static) 변수는 사용 불가 
			: 배열도 new 연산자를 통한 생성을 하려면 명확한 자료형이 정의 -> 사용 불가 
			
			: 메서드 내부에서 접근 가능한 지네릭 타입 인스턴스 자원은 모두 Object 형
				-> 형변환된 객체의 정의된 인스턴스 자원 접근 X
				-> 공통된 틀을 정해서 타입 매개변수의 하위 클래스임을 정의 
					<T extends Fruit> -> T는 Fruit의 하위 클래스임을 알수 있음 -> Object 보다는 Fruit로 변환
					


지네릭스

지네릭 클래스 - 타입결정 시점 : 객체생성시

지네릭 메서드 - 타입결정시점 : 매서드가 호출될때

  
  class Box<T> {
	  
	  private T item; // T: 지네릭클래스의 T
	  
	  public <T> void print(T item) {  // T : 지네릭 메서드의 T
		  
	  }
  }
  
  
  Box box = new Box();
  box.<Apple> print(apple); //타입결정시점 : 매서드가 호출될때
  box.print(apple)
  

지네릭 메서드 
  - 와일드 카드 <?>
  
  public void print(FruitBox<?> box) {
	  
  }
  
  
javadoc 에서

Interface List<E>  : E - 지네릭클래스

메서드 앞에 static <E> : 지네릭메서드 
