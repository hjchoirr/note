람다식(Lamba expression)

	-----js 참고------------------
	//---매개변수가 함수
	function outer(callback) {
		callback();
	}
	function inner() {
		console.log("inner호출");
	}
	outer(inner);
	>> inner호출

	outer(function() {
		console.log("inner호출");
	});
	>> inner호출

	outer(console.log("inner호출"));
	>> inner호출

	outer(() => console.log("inner호출"));
	>> inner호출
	//--------------------------
	const nums = [1,2,3,4,5,6];
	const nums2 = nums.map(function(num) {
		 return num * num;   
	});

	nums2;
	(6) [1, 4, 9, 16, 25, 36]
	//--------------------------

	const nums = [1,2,3,4,5,6];
	const nums2 = nums.map(x => x * x);
	nums2
	(6) [1, 4, 9, 16, 25, 36]
	const nums = [1,2,3,4,5,6];
	const nums2 = nums.map(function(x) {return x * x});
	nums2
	(6) [1, 4, 9, 16, 25, 36]


	// 반환값이 함수인 경우 -------
	function sum(num1) {
		return function(num2) {
			return num1 + num2;
		};
	}
	const num3 = sum(10); // sum : 팩토리함수 -> 클로저 : 자유변수 10이 속박되어 고정되는 것을 말함
	>>
	ƒ (num2) {
			return num1 + num2;
		}
		
	num3(20);
	>>30

	sum(10)(20);
	>>30

	sum(30)(40)
	>>70

	//------------------------

1. 람다식이란?
	- 메서드(함수)를 하나의 식으로 표현
	
	- 함수형 프로그래밍
	
		- 함수란?
	
			: 하나의 기능(단일기능)
			 
		- 함수는 값으로 사용 ( 일등함수 )
		
			- 매개변수 : 사용자 정의 가능
			- 반환값 : 참고) js - 클로저, 새로운 함수를 만드는 함수인 팩토리함수
			
	자바는 함수는 값으로 사용 불가
	
		- 인터페이스의 객체가 되는 조건을 이용 - 
		- 형식을 단순화 
		
		
			--------------------------------------------------------------------------
			Consumer 인터페이스 이용하여 함수형 프로그램밍 가능 ~~~> 화살표함수로 대체 가능
			--------------------------------------------------------------------------
			package exam02;
			import java.util.Arrays;
			import java.util.List;
			import java.util.function.Consumer;

			public class Ex01 {
				public static void main(String[] args) {
					
					List<String> names = Arrays.asList("이름3","이름1","이름2","이름4", "이름5");
					
					// names.forEach(s -> System.out.println(s)); 와 같음
					names.forEach(new Consumer<String>() {
						@Override
						public void accept(String s) {
							System.out.println(s);
						}
					});
					// 동일함
					names.forEach(s -> System.out.println(s));
				}
			}
		
			--------------------------------------------------------------------------
			package exam02;
			public interface Calculator {
				int add(int num1, int num2);
			}

			package exam02;
			public class Ex02 {
				public static void main(String[] args) {
					
					//-----------------------------------
					Calculator cal = new Calculator() {
						@Override
						public int add(int num1, int num2) {
							return num1 + num2;
						}
					};
					//----------------------------------- 아래 한줄로 대체 가능 
					Calculator cal2 = (a, b) -> a + b;        // 람다식
					//-----------------------------------
				}
			}
			--------------------------------------------------------------------------
			
			        int c = 10;
					Calculator cal2 = (a, b) -> {
						c = 20; // 에러 : c 는 상수화되었으므로
						return a + b + c;
					};


2. 람다식 문법 살펴보기
	메서드를 하나의 식으로 짧게 표현하는 문법 / 용도가 제한적이므로 굳이 길게 작성 안함
	-> 최대한 짧게 쓰는 게 좋은 방식

	1) 접근 제어자, 반환값 타입, 함수명을 생략 
	2) 매개변수 정의 부분과 함수 구현 부분({ }) 사이에 -> 추가 
	3) 매개변수의 자료형 생략 가능 
	4) 구현코드가 한줄일때는 { ... } 생략 가능, return 예약어도 생략 해야 된다.
	5) 최대한 짧게 쓰는 방향(변수명도 한 글자로 하는 것이 권장)
	
3. 람다식 사용하기
4. 함수형 인터페이스(Functional Interface)
	- 메서드가 1개만 정의해야 함 : 단일 기능, 단일 역할, 형식상 제한 조건
	- 명확한 형식 제어를 위한 에노테이션 
	   @FunctionalInterface   -> 이 에노테이션 있으면 "람다식 쓰기" 
	  

5. 함수형 인터페이스 타입의 매개변수와 반환 타입

	javadoc에서 Consumer 찾으면 
	------------------------------
	@FunctionalInterface			// 람다식으로 쓰기 위함 Consumer 인터페이스는 함수형인터페이스이므로 
	public interface Consumer<T>	// 메서드 1개만 있다 -> accept
	------------------------------


6. java.util.function패키지

1) 매개변수가 X, 반환값 1개     - 제공자
	Supplier<T>
			: T get()

2) 매개변수가 1개, 반환값 0개    - 소비자
	Consumer<T>
			: void accept(T t)
			
3) 매개변수가 1개 반환값도 1개    - 함수 
	Function<T,R>
			: R apply(T t)

4) 매개변수가 1개, 반환값은 boolean(논리값)  - 판별
	Predicate<T>                        
			: boolean test(T t)

매개변수 2개

1) 매개변수가 2개, 반환값 X
	BiConsumer<T, U>
		: void accept(T t, U u)
		
2) 매개변수가 2개,  반환값 1개 
	BiFunction<T,U,R>
		: R apply(T t, U u)

3) 매개변수가 2개, 반환값 boolean
	BiPredicate<T, U>
		: boolean test(T t, U u)
		
		
		
		package exam01;
		import java.util.function.BiFunction;

		public class Ex02 {
			public static void main(String[] args) {
				BiFunction<Integer, Integer, Integer> calc = (a, b) -> a+b;
				int result = calc.apply(10,20);
				System.out.println(result);
				/**
				 * (참고) 지네릭 타입변수 T  : 참조자료형과 배열만 가능, 기본자료형은 불가능함
				 * (문제점)
				 * 1. 언박싱,오토박싱,언박싱 수행됨 : 성능저하, 연산은 기본형이 좋다
				 * 2. 자료형 반복 정의
				 */

				BinaryOperator<Integer> calc2 = (a, b) -> a + b;
				int result2 = calc2.apply(10,20);
				System.out.println(result2);
			}
		}
				
		package exam01;
		import java.util.function.IntBinaryOperator;

		public class Ex04 {
			public static void main(String[] args) {
				IntBinaryOperator calc = (a, b) -> a + b;   // 기본자료형 함수형인터페이스 처리 하기
				int result = calc.applyAsInt(10, 20);
				System.out.println(result);
			}
		}


매개변수와 반환값이 동일한 경우

	매개변수1개, 반환값1개 -> 자료형이 모두 동일 
	UnaryOperator<T>   ---> 상위 인터페이스 : Function<T, T>
		T apply(T t)


	매개변수가 2개, 반환값1 -> 자료형이 모두 동일 
	BinaryOperator<T>  ---> 상위 인터페이스 : BiFunction<T, T, T>
		T apply(T t1, T t2)
	
	
	javadoc에
	Interface UnaryOperator<T>
	Superinterfaces: Function<T,T>
	
기본형 타입의 함수형 인터페이스 
	- Package java.util.function 참고하기

	예)	IntConsumer  : 매개변수 int
		IntToDoubleFunction : 매개변수 int, 반환값 double
		ToIntFunction<T> : 매개변수 T, 반환값 int
		IntFunction<R> : 매개변수 int, 반환값 R  
		IntBinaryOperator : 매개변수 2개 int, 반환값 int
		
		


7. Function의 합성과 Predicate의 결합

	함수 결합 	
	java.util.function 참고
	
	Function
		andThen(Function ... )
		f.andThen(g) -> f -> g 
		
		compose(..)
		g.compose(f) -> f -> g

	Predicate : 논리연산
		and
		or 
		negate(무효화하다, 부정하다)

			package exam01;
			import java.util.function.Function;

			public class Ex05 {
				public static void main(String[] args) {
					Function<String, Integer> func1 = s -> s.length();
					//int len = func1.apply("가나다");
					//System.out.println(len);

					Function<Integer,Integer> func2 = x -> x * x;
					Function<String, Integer> func3 = func1.andThen(func2);

					int num = func3.apply("가나다");
					System.out.println(num);
					

					Function<String, Integer> func4 = func2.compose(func1); //andThen과 반대순서
					num = func4.apply("가나다");
					System.out.println(num);
					
				}
			}
			>> 9
		
	Predicate : 논리연산
		and
		or 
		negate(무효화하다, 부정하다)
		
		package exam01;

		import java.util.function.IntPredicate;

		public class Ex06 {
			public static void main(String[] args) {
				IntPredicate cond1 = x -> x >= 10;
				IntPredicate cond2 = x -> x <= 100;
				IntPredicate cond3 = cond1.and(cond2); // x >= 10 && x <= 100

				System.out.println(cond3.test(20));
				System.out.println(cond3.test(120));
				
				IntPredicate cond4 = cond2.negate(); // x > 100
				System.out.println(cond4.test(150));
				
			}
		}
		>>
		true
		false
		true
		

8. 메서드 참조
	- 람다식을 더욱 간결하게 표현
	
	- 클래스명::메서드명
	- 참조변수::메서드명
	
		package exam01;

		import java.util.function.BiPredicate;
		import java.util.function.IntFunction;
		import java.util.function.ToIntFunction;
		import java.util.function.Supplier;

		public class Ex07 {
			public static void main(String[] args) {
				ToIntFunction<String> func1 = s -> s.length();
				ToIntFunction<String> func2 = String::length;

				BiPredicate<String, String> cond1 = (s1, s2) -> s1.equals(s2);
				BiPredicate<String, String> cond2 = String::equals; // s1, s2 순서 동일할때만

				Supplier<Book> s1 = () -> new Book();
				Supplier<Book> s2 = Book::new;

				Book b1 =  s2.get();
				System.out.println(b1);				
			}
		}

		package exam01;
		import java.util.Arrays;
		import java.util.List;

		public class Ex09 {
			public static void main(String[] args) {
				List<String> alpha = Arrays.asList("abc","def","ghi");

				String[] upperAlpha = alpha.stream().map(s -> toUpperCase()).toArray(i -> new String[i]);
				String[] upperAlpha = alpha.stream().map(String::toUpperCase).toArray(i -> new String[i]);
				String[] upperAlpha2 = alpha.stream().map(String::toUpperCase).toArray(String[]::new); //위와 동일

				System.out.println(Arrays.toString(upperAlpha));
				System.out.println(Arrays.toString(upperAlpha2));
			}
		}



		package exam01;
		import java.util.function.Supplier;

		public class Book {

			String title;
			
			public String getTitle() {
				return title;
			}
			public  void printTitle() {
				Supplier<String> t1 = () -> getTitle();  
				Supplier<String> t2 = this::getTitle;  //위외 동일
			}
		}
