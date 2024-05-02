

java.util.Arrays : 배열의 편의 기능
java.util.Collections : 컬렉션의 편의 기능

배열과 컬렉션 동일한 방법으로 처리하기 위함 - 스트림

스트림(Stream)
1. 스트림이란?
	java.util.stream
	
	- 데이터 소스가 무엇이든 간에 같은 방식으로 다룰 수 있게 데이터를 추상화 하고 데이터를 다루는데 자주 사용되는 메서드들을 정의해 놓음
		- 배열이든, 컬렉션이든
		- Stream 객체로 변환하면 동일한 방식으로 처리 가능
		- + 편의 기능 
		
	- 스트림은 데이터 소스를 변경하지 않는다.
	- 스트림은 일회용이다.
	- 스트림은 작업을 내부 반복으로 처리한다.
	
2. 스트림만들기
	1) 컬렉션
		Collection
				Stream stream()
				
	2) 배열 
		Arrays
			static stream(....)


	package exam02;
	import java.util.Arrays;

	public class Ex01 {
		public static void main(String[] args) {
			String[] names = {"이름1","이름3","이름2"};  //배열일때

			Arrays.sort(names);
			System.out.println(Arrays.toString(names));
		}
	}
	>> [이름1, 이름2, 이름3]

	package exam02;
	import java.util.Arrays;
	import java.util.Collections;
	import java.util.List;

	public class Ex02 {
		public static void main(String[] args) {
			List<String> names = Arrays.asList("이름1","이름3","이름2"); //컬렉션일때
			
			Collections.sort(names);
			System.out.println(names);
		}
	}
	>> [이름1, 이름2, 이름3]

	package exam02;
	import java.util.Arrays;

	public class Ex03 {
		public static void main(String[] args) {
			/**
			 * 문자열 앞뒤에 *
			 * 대문자로 변환
			 * 출력
			 */
			String[] fruits = { "apple", "orange", "mango", "melon"};
			Arrays.stream(fruits).map(s-> "*" + s + "*")
					.map(String::toUpperCase)
					.forEach(System.out::println);
		}
	}

	package exam02;
	import java.util.Arrays;
	import java.util.List;

	public class Ex04 {
		public static void main(String[] args) {
			List<String> fruits = Arrays.asList("apple", "orange", "mango", "melon");

			fruits.stream().map(s -> "*" + s + "*")
					.map(String::toUpperCase)
					.forEach(System.out::println);
		}
	}


스트림(Stream)
1. 스트림이란?
	java.util.stream
	
	- 데이터 소스가 무엇이든 간에 같은 방식으로 다룰 수 있게 데이터를 추상화 하고 데이터를 다루는데 자주 사용되는 메서드들을 정의해 놓음
		- 배열이든, 컬렉션이든
		- Stream 객체로 변환하면 동일한 방식으로 처리 가능
		- + 편의 기능 
		
	- 스트림은 데이터 소스를 변경하지 않는다.
	- 스트림은 일회용이다.
	- 스트림은 작업을 내부 반복으로 처리한다.
	
		package exam02;
		import java.util.Arrays;
		import java.util.stream.IntStream;

		public class Ex05 {
			public static void main(String[] args) {
				int[] nums = {22, 11, 15, 35, 40, 77};

				IntStream stm = Arrays.stream(nums);
				int total = stm.map(x -> x * x).sum();
				System.out.println(total);

				stm = Arrays.stream(nums);  // 다시 만들어야 함, stream은 일회용 객체, 재사용 못함
				int total2 = stm.map(x -> x * x * x).sum();

			}
		}
		
		// 의사코드
		package exam02;
		import java.util.Arrays;
		import java.util.function.IntUnaryOperator;

		public class Ex06 {
			public static void main(String[] args) {
				int[] nums = { 10,20,30,40,50};
				
				//-----
				int[] nums2 = map(nums, new IntUnaryOperator() {
					@Override
					public int applyAsInt(int num) {
						return num * num;
					}
				});
				// stream 이용해 줄이면
				int[] nums2 = Arrays.stream(nums).map(num -> num * num).toArray();

				System.out.println(Arrays.toString(nums2));
			}

			public static int[] map(int[] nums, IntUnaryOperator oper) {

				int[] newNums = new int[nums.length];
				for(int i = 0 ; i < nums.length; i++) {
					newNums[i] = oper.applyAsInt(nums[i] );
				}
				return newNums;
			}
		}
		
	
	
2. 스트림만들기

	 ( 기본자료형 스트림 	: java.util.stream.IntStream,... )
	 ( 일반 스트림 		: java.util.stream.Stream )

1) 컬렉션 에서 생성하는 방법 
	Collection클래스의 매서드
		Stream<E> stream()    => 일반 스트림만 생성
			
			
2) 배열 에서 생성하는 방법 
	Arrays
		static stream(....)
		
		Arrays.stream(..   => 기본자료형 스트림 생성 가능
			static IntStream Arrays.stream(int[] array)
			static LongStream Arrays.stream(int[] array)
			static Stream<T> Arrays.stream(T[] array)   => 일반 스트림도 생성 가능
			...
			
3)
	Stream
			.of(T... )
	참고)
		JDK8 부터 
			of(...) : 객체 생성 메서드
			
		package exam02;
		import java.util.stream.Stream;

		public class Ex09 {
			public static void main(String[] args) {
				long cnt = Stream.of("이름1","이름2","이름3").count();
				System.out.println(cnt);
			}
		}
			
	

2. 스트림의 연산
	1) 중간연산
		- 스트림 중간 부분에 정의된 메서드
		- 반환값이 Stream인 형태이면   *** 중요 ***  메서드 체인 사용 가능 
		- 최종연산이 호출될때까지는 연산 안함. -> 지연된 연산

			package exam02;
			import java.util.Arrays;

			public class Ex07 {
				public static void main(String[] args) {
					int[] nums = { 22,11,53, 4,6, 43 };
					
					Arrays.stream(nums).map(x -> x * x).filter( x -> x % 2 == 0).forEach(System.out::println);
					// -> 메서드 체인 : 반환값의 자료형이 모두 stream 인 메서드들을 연결하여 사용
				}
			}

	2) 최종연산
		- 가장 끝에 추가된 메서드
		- 반환값이 Stream이 아닌 형태   *****

	3) 지연된연산
		중간 연산은 최종 연산이 호출되어야 스트림을 소비하면서 연산이 진행 된다.

3. 기본자료형을 다루는 스트림

	기본자료형 스트림
	IntStream 
	LongStream 
	DoubleStream 
		-> 오토박싱, 언박싱이 발생 X -> 성능상 이점
		-> 숫자 관련 편의 기능 추가(예 - 통계 관련 기능)
	
	
	## 변환 : 일반stream <-> 기본자료형stream 
	
	 : 변환이유 : IntStream에 있는 sorted()는 기본 정렬만 됨.
	   거꾸로 정렬하려면 일반 스트림에는 
	   Stream<T> sorted() 기능과  
	   Stream<T> sorted(Comparator<? super T> comparator)  기능도 있다
	
	1) 일반 스트림 -> 기본 자료형 스트림으로 변환 메서드 
	  
		mapToInt   : IntStream 
		mapToLong : LongStream
		mapToDouble : DoubleStream
		
		
	2) 기본 자료형 스트림 -> 일반 스트림 변환 메서드  
		
		 ( 기본자료형 스트림 	: java.util.stream.IntStream )
		 ( 일반 스트림 		: java.util.stream.Stream)
		 
		   
		boxed() 
			예) IntStream -> Stream<Integer>
				LongStream -> Stream<Long>
				
			IntSummaryStatistics summaryStatistics()
			
				package exam02;
				import java.util.Arrays;
				import java.util.IntSummaryStatistics;

				public class Ex10 {
					public static void main(String[] args) {
						int[] scores = { 60,78,90,87, 100 };

						IntSummaryStatistics stat = Arrays.stream(scores).summaryStatistics();

						double avg = stat.getAverage();
						long count = stat.getCount();
						int max = stat.getMax();
						int min = stat.getMin();
						long sum = stat.getSum();

						System.out.printf("평균 : %f, 총 갯수 : %d, 최대: %d, 최소: %d, 합계 : %d%n", avg, count, max, min, sum );
					}
				}
			
				package exam02;
				import java.util.Arrays;
				import java.util.List;

				public class Ex11 {
					public static void main(String[] args) {
						List<Integer> scores = Arrays.asList( 60,78,90,87, 100);

						long sum = scores.stream().mapToInt(x -> x).sum();
						System.out.println(sum);
					}
				}				
				--------------------------------------------------------
				package exam02;
				import java.util.Arrays;
				import java.util.Comparator;

				public class Ex12 {
					public static void main(String[] args) {
						int[] nums = {33,10, 5, 1, 3, 88, 100, 10};
						int[] nums2 = Arrays.stream(nums)
									.sorted()       // IntStream 이라서 기본 sort밖에 안됨
									.toArray(); 
						System.out.println(Arrays.toString(nums2));

						int[] nums3 = Arrays.stream(nums)
								.boxed()                      // 일반 Stream으로 변환
								.sorted(Comparator.reverseOrder())  // 일반 Stream이라서 Comparator 사용가능
								.mapToInt(x -> x).toArray();

						System.out.println(Arrays.toString(nums3));
					}
				}
		
스트림 활용 
1. 생성하기
	Collection::stream() : 일반 스트림
	Arrays.stream(...) : 일반스트림 + 기본 자료형 스트림 
	
	Stream.of(T.... ) : 일반스트림, 기본 자료형 스트림
	
1) 특정 범위의 정수
	기본 자료형 스트림 (IntStream, LongStream..)
	- 횟수가 정해진 반복을 할때
		range(int s, int r)
		rangeClosed(...)  

		static IntStream IntStream.range(시작번호, 종료 번호(미만))
		static IntStream IntStream.rangeClosed(시작번호, 종료 번호(이하))
								 

		package exam02;
		import java.util.stream.IntStream;

		public class Ex13 {
			public static void main(String[] args) {
				IntStream.range(1,10).forEach(i -> System.out.println("반복" + i));
				
				int tot = IntStream.rangeClosed(1,10).sum();
								
			}
		}
	
	
	
2) 임의의 수
	java.util.Random
		무한 스트림 - 갯수 제한이 필요
		IntStream ints();  : 정수범위 난수 
		LongStream longs() : 
		DoubleStream doubles() : 실수 범위 난수
		
3) 람다식 - iterate(), generate()
	- 무한스트림
	
	
4) 두 스트림의 연결 - concat()
	

2. 스트림의 중간 연산
1)  skip(), limit()

limit() : 갯수 제한 
skip() : 건너 뛰기

2) filter(), distinct()
 filter(Predicate<T> ...)  : 스트림을 걸러주는 기능 
 
 distinct() :  중복 제거
	- 중복 제거 기준 : equals() and hashCode()
 
 
3) sorted()
	- 정렬 : 기본 정렬 기준 java.lang.Comparable  int compareTo(...)
	- sorted(Comparator ....) 
		- 대안적인 기준 : java.util.Comparator :: int compare(....)
	
4) map()
	map(Function<T,R> ...)  : 변환 메서드 
	
5) peek()
	- forEach와 매개변수가 동일 
	- Stream peek(Consumer<T> ... ) : 중간 연산 : 중간에 값을 확인할 경우 많이 사용 
	- void forEach(Consumer<T> ...) : 최종 연산 : 최종적으로 출력할때 사용

6) mapToInt(), mapToLong(), mapToDouble()


Optional 클래스 
- JDK8 
- null에 대한 다양한 처리 방법을 제공하는 클래스 
- Wrapper 클래스 

class Optional<T> {
	...
	private final T value;
	...
}


1. Optional 객체 생성하기
	static Optional<T> of(T t) : t가 null이면 오류 발생 
	static Optional<T> ofNullable(T t) : t가 null이어도 오류  발생 X 
	
2. Optional 객체의 값 가져오기
	
	T get() : null 이면 오류 발생 
	T orElse(T other) : null이 아니면 값 반환, null이면 other 반환 
	T orElseGet(Supplier<T ... >  )
	T orElseThrow() : null이면 예외 발생 
	T orElseThrow(Supplier<T ... > ) 
	
3. OptionalInt, OptionalLong, OptionalDouble
- 기본형을 처리하는 Optional 클래스 
- 오토박싱, 언박싱이 발생 X -> 성능상의 이점 


3. 스트림의 최종 연산
- 최종 연산이 호출되어야 중간 연산도 수행, 스트림을 소비 

1) forEach()

2) allMatch(), anyMatch(), noneMatch(), findFirst(), findAny()

boolean allMatch(Predicate ... ) : 전부 참인 경우 참 
boolean anyMatch(Predicate ...) : 어떤 것이든 하나라도 참이면 참 
boolean noneMatch(Predicate ...) : 전부 거짓일때 참 
T findFirst() : 가장 첫번째 스트림의 요소를 반환 


3) count(), sum(), average(), max(), min()

4) reduce()

5) collect()
	Collector
	
	java.util.stream.Collectors 
		6) toList(), toSet(), toMap(), toCollection(), toArray()
			- toMap() : 
			- toCollection() : List, Set의 하위 클래스 객체 
			
		7)  joining()

8) groupingBy(), partitioningBy()