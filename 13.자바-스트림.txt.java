

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
		
	- 스트림은 원본 데이터 소스를 변경하지 않는다.
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
		
		package hakwon03;
		import java.util.*;
		import java.util.stream.IntStream;
		import java.util.stream.Stream;

		public class Ex01 {
			public static void main(String[] args) {
				int[] arr = { 1,2,3,4,5,6};
				IntStream iStm =  Arrays.stream(arr); // 배열 -> 스트림 기본자료 스트림
				iStm.forEach(System.out::println);

				String[] sArr = {"apple", "orange", "mango", "melon"};
				Stream<String> sStm = Arrays.stream(sArr);  // 배열 -> 스트림 기본자료 스트림
				sStm.forEach(System.out::println);

				List<String> strList = Arrays.asList("apple", "orange", "mango", "melon");
				Stream<String> stm = strList.stream();  // 컬렉션 -> 스트림
				stm.forEach(System.out::println);

				HashSet<String> strSet = new HashSet<>();
				strSet.add("이름1");
				strSet.add("이름2");
				strSet.add("이름2");
				strSet.add("이름3");
				strSet.add("이름4");

				Stream<String> sStm2 = strSet.stream(); // 컬렉션 -> 스트림
				sStm2.forEach(System.out::println);

				Stream<String> sStm3 = Stream.of("apple", "orange", "mango", "melon"); //Stream.of() 정적메서드로 스트림생성
				sStm3.forEach(System.out::println);
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
			
			Arrays.stream(arr)   => 기본자료형 스트림 생성 가능
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
			
	

3. 스트림의 연산
	1) 중간연산
		- 스트림 중간 부분에 정의된 메서드
		- 반환값이 Stream인 형태이면 중간연산  *** 중요 ***  메서드 체인 사용 가능 
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

		package exam01;
		import java.util.Arrays;

		public class Ex01 {
			public static void main(String[] args) {
				String[] fruits = { "apple", "Orange", "Banana", "apple", "Mango"};
				/**
				 * 1. 중복 제거
				 * 2. 문자열 길이 변경
				 * 3. int[]배열로
				 */
				int[] nums = Arrays.stream(fruits).distinct().mapToInt(String::length).toArray();
				System.out.println(Arrays.toString(nums));
			}
		}
		

4. 기본자료형을 다루는 스트림

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

						System.out.printf("평균 : %f, 총 갯수 : %d, 최대: %d, 최소: %d, 합계 : %d%n", 
						avg, count, max, min, sum );
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
								 
		-------------------------------------------------------------
		package exam02;
		import java.util.stream.IntStream;

		public class Ex13 {
			public static void main(String[] args) {
				IntStream.range(1,10).forEach(i -> System.out.println("반복" + i));
				
				int tot = IntStream.rangeClosed(1,10).sum();
								
			}
		}
		-------------------------------------------------------------
		package exam01;
		import java.util.stream.IntStream;

		public class Ex02 {
			public static void main(String[] args) {
				int tot = IntStream.rangeClosed(1,100).filter(x -> x % 2 == 0).sum();
				System.out.println(tot);
			}
		}
	
	
	
2) 임의의 수
	java.util.Random
		무한 스트림 - 갯수 제한이 필요 **** 중요 ****
		IntStream ints();  : 정수범위 난수 
		LongStream longs() : 
		DoubleStream doubles() : 실수 범위 난수
		
		package exam01;
		import java.util.Random;
		import java.util.stream.DoubleStream;

		public class Ex03 {
			public static void main(String[] args) {
				Random rand = new Random();
				rand.ints().limit(6).forEach(System.out::println); //6개만 난수 만들기
				rand.ints(6).forEach(System.out::println); // 위와 동일 6개만 난수 만들기
			}
		}
		
		
3) 람다식 - iterate(), generate()
	- 무한스트림
	
		package exam01;
		import java.util.Arrays;
		import java.util.stream.IntStream;

		public class Ex04 {
			public static void main(String[] args) {
				int[] nums = IntStream.iterate(2, x -> x + 2).limit(10).toArray();
				System.out.println(Arrays.toString(nums));
			}
		}	
		package exam01;
		import java.util.Arrays;
		import java.util.stream.IntStream;

		public class Ex05 {
			public static void main(String[] args) {
				int[] nums = IntStream.generate(() -> 1).limit(10).toArray();
				System.out.println(Arrays.toString(nums));
			}
		}
			
4) 두 스트림의 연결 - concat()
		package exam01;
		import java.util.Arrays;
		import java.util.stream.IntStream;

		public class Ex06 {
			public static void main(String[] args) {
				IntStream stm1 = IntStream.rangeClosed(1, 50);
				IntStream stm2 = IntStream.rangeClosed(51, 100);
				IntStream stm = IntStream.concat(stm1, stm2);

				int[] nums = stm.toArray();
				System.out.println(Arrays.toString(nums));
			}
		}
	

2. 스트림의 중간 연산
1)  skip(), limit() 

	limit() : 갯수 제한 
	skip() : 건너 뛰기

		package exam01;
		import java.util.Arrays;
		import java.util.List;

		public class Ex07 {
			public static void main(String[] args) {
				List<String> fruits = Arrays.asList("사과","배","오렌지","망고","멜론");
				
				String[] selected = fruits.stream().skip(2).limit(2).toArray(i->new String[i]);
				String[] selected = fruits.stream().skip(2).limit(2).toArray(String[]::new); //윗줄 동일
				
				System.out.println(Arrays.toString(selected));
			}
		}

2) filter(), distinct()
 filter(Predicate<T> ...)  : 스트림을 걸러주는 기능 
 
 distinct() :  중복 제거
	- 중복 제거 기준 : equals() and hashCode()
 
 
3) sorted()
	- 정렬 : 기본 정렬 기준 java.lang.Comparable  int compareTo(...)  / Natural Order
	- sorted(Comparator ....) 
		- 대안적인 기준 : java.util.Comparator :: int compare(....)
	
4) map()

	map(Function<T,R> ...)  : 변환 메서드 

	참고) 기본자료형 스트림 변환 메서드
		IntStream mapToInt(ToIntFunction<T>..)
		LongStream mapToLong(ToLongFunction<T> ..)
		DoubleStream mapToDouble(ToDoubleFunction<T> ..)
		
	
5) peek()  (훔쳐보다)
	- forEach와 매개변수가 동일 
	- Stream peek(Consumer<T> ... ) : 중간 연산 : 중간에 값을 확인할 경우 많이 사용 
	- void forEach(Consumer<T> ...) : 최종 연산 : 최종적으로 출력할때 사용
	
		package exam01;
		import java.util.Arrays;
		import java.util.List;

		public class Ex07 {
			public static void main(String[] args) {
				List<String> fruits = Arrays.asList("사과","배","오렌지","망고","멜론");
				String[] selected = fruits.stream()
						.peek(System.out::println)  // 확인용
						.skip(2)
						.limit(2)
						.toArray(String[]::new);
				System.out.println(Arrays.toString(selected));
			}
		}
		>>
		사과
		배
		오렌지
		망고
		[오렌지, 망고]	

6) mapToInt(), mapToLong(), mapToDouble()


Optional 클래스 
- JDK8 
- null에 대한 다양한 처리 방법을 제공하는 클래스 
- Wrapper 클래스 

Optional 클래스의 목적
	: NullPointerException 문제 방지 ( 자바는 NPE 에 취약한 언어 )
	: null에 대한 다양한 처리방식을 제공하는 클래스

Optional 클래스의 특징
	: 값이 null인지 아닌지 체크해 보려면 값을 가지고 있어야 함
	: Wrapper 클래스
	

class Optional<T> {
	...
	private final T value;
	...
}


1. Optional 객체 생성하기
	static Optional<T> of(T t) : t가 null이면 오류 발생 
	static Optional<T> ofNullable(T t) : t가 null이어도 오류  발생 안함 
	
2. Optional 객체의 값 가져오기
	
	T get() : null 이면 오류 발생 (NoSuchElementException 발생)
	
	T orElse(T other) : null이 아니면 값 반환, null이면 other 값 반환 
	
	T orElseGet(Supplier<T ... >  ) : 값을 조금 더 가공, 처리가 필요할땐? 함수형 인터페이스
		- null이아니면 값반환, null이면 Supplier::get() 함수에서 생성한 값을 반환
		- 값에 대한 변경, 코드 추가가 필요한 경우에 사용
		
	T orElseThrow() : null이면 예외 발생 (NoSuchElementException 발생)
	
	T orElseThrow(Supplier<T ... > ) : null이면 Supplier의 get()에 정의한 예외 발생
	
	(참고) 반환값이 Optional 인 메서드들은 반환값이 null일 가능성이 있는 메서드.. 그걸 처리하려고

		package exam01;
		import java.util.Optional;

		public class Ex08 {
			public static void main(String[] args) {
				String str = "ABC";
				Optional<String> opt = Optional.of(str);  // str이 null 아닐때 문제 없음, str을 Optional Wrapper 클래스로 감싸기
				System.out.println(opt.get());            // str이 null 아닐때 문제 없음
				
				String str1 = null;
				//Optional<String> opt1 = Optional.of(str); //NPE 발생 하므로 아랫줄로
				Optional<String> opt1 = Optional.ofNullable(str1);  //NPE 발생안함 null 허용

				//System.out.println(opt.get()); //NPE 발생
				String value = opt1.orElse("기본값"); //null 허용된 opt => 꺼낼때 null 인지 체크하기 
				System.out.println(value);
			}
		}

		package exam01;
		import java.util.Optional;

		public class Ex09 {
			public static void main(String[] args) {
				Book book = null;

				//System.out.println(book.toString());  //NPE 발생
				Optional<Book> opt = Optional.ofNullable(book);

				Book book2 = opt.orElseGet(() -> new Book());
				Book book3 = opt.orElseGet(Book::new); //윗줄과 동일

				System.out.println(book2.toString());

			}
		}
	
	
3. OptionalInt, OptionalLong, OptionalDouble
- 기본형을 처리하는 Optional 클래스 
- 오토박싱, 언박싱이 발생 X -> 성능상의 이점 

(참고)
	중간연산 flatMap : 중첩된 스트림을 일차원적스트림으로 변환


4. 스트림의 최종 연산
- 최종 연산이 호출되어야 중간 연산도 수행, 스트림을 소비 

	1) forEach(consumer<T>..)
		- 반복 작업 수행

	2) allMatch(), anyMatch(), noneMatch(), findFirst(), findAny()

		- boolean allMatch(Predicate ... ) : 전부 참인 경우 참 
			(참고) js 배열객체 every와 비슷

		- boolean anyMatch(Predicate ...) : 어떤 것이든 하나라도 참이면 참 
			(참고) js 배열객체 some과 비슷

		- boolean noneMatch(Predicate ...) : 전부 거짓일때 참 
		
		- T findFirst() : 가장 첫번째 스트림의 요소를 반환 
			(참고) findAny() : 


			package exam01;
			import java.lang.reflect.Array;
			import java.util.Arrays;

			public class Ex01 {
				public static void main(String[] args) {
					int nums[] = {1, 3, 5, 7, 9, 11, 13, 14};
					/*
					boolean result = true;
					for(int num : nums) {
						if (num % 2 == 0) {
							result = false;
							break;
						}
					}
					*/
					boolean isOdd = Arrays.stream(nums).allMatch(x -> x % 2 == 1);
					System.out.println(isOdd);

					boolean isEven = Arrays.stream(nums).anyMatch(x -> x % 2 == 0);
					System.out.println(isEven);

					boolean notIncludedEvent = Arrays.stream(nums).noneMatch(x -> x % 2 == 0);
					System.out.println(notIncludedEvent);
				}
			}

			package exam01;
			import java.util.Random;

			public class Ex02 {
				public static void main(String[] args) {
					Random rand = new Random();
					int firstOdd = rand.ints(100)
									.filter(x -> x % 2 == 1 )
									.findFirst()
									.orElse(-1);
					System.out.println(firstOdd);
				}
			}

	3) count(), sum(), average(), max(), min()
	
		long count() : 요소의 갯수 - 일반 스트림(Stream<T>), 기본자료형 스트림(IntStream, LongStream, DoubleStream)
		
		기본자료형 스트림(IntStream, LongStream, DoubleStream))
		
			long sum() : 합계
			OptionalDouble average() : 평균
			
	4) reduce()
		-------------------------------------------------------
		package exam01;
		import java.util.Arrays;

		public class Ex03 {
			public static void main(String[] args) {
				int[] scores = { 67, 80, 98, 76, 56 };

				System.out.println(Arrays.stream(scores).sum());

				//int tot = Arrays.stream(scores).reduce(0, (n1, n2) -> n1 + n2);
				int tot = Arrays.stream(scores).reduce(0, (n1, n2) -> {
					System.out.printf("n1=%d n2=%d%n", n1, n2 );
					n1 += n2;
					return n1;
				});
				System.out.println(tot);

			}
		}
		>>
		377
		n1=0 n2=67
		n1=67 n2=80
		n1=147 n2=98
		n1=245 n2=76
		n1=321 n2=56
		377
		-------------------------------------------------------------
		package exam01;
		import java.util.Arrays;

		public class Ex04 {
			public static void main(String[] args) {
				int[] scores = { 67, 80, 100, 98, 76, 56 };

				//int max = Arrays.stream(scores).reduce((a, b) -> a > b ? a : b).getAsInt();
				int max = Arrays.stream(scores).reduce((a, b) -> {
					System.out.printf("a=%d b=%d %n", a, b);
					int m = a > b ? a : b;
					return m;
				}).getAsInt();

				System.out.println(max);
			}
		}
		>>
		a=67 b=80 
		a=80 b=100 
		a=100 b=98 
		a=100 b=76 
		a=100 b=56 
		100
		-------------------------------------------------------------
		
	5) collect()
		Collector
		
		java.util.stream.Collectors 
			6) toList(), toSet(), toMap(), toCollection(), toArray()
				- toMap() : 
				- toCollection() : List, Set의 하위 클래스 객체 
					만약 ArrayList 변환?, HashSet, TreeSet 변환 .. toCollectors(..)
				
			7)  joining()

			package exam01;
			import java.util.Arrays;
			import java.util.List;
			import java.util.Set;
			import java.util.stream.Collectors;

			public class Ex05 {
				public static void main(String[] args) {
					String[] names = {"이름1", "이름2","이름3","이름4","이름5","이름3"};

					List<String> namesList = Arrays.stream(names).collect(Collectors.toList());
					System.out.println(namesList);
					
					List<String> namesList2 = Arrays.stream(names).toList();
					System.out.println(namesList2);

					Set<String> namesSet = Arrays.stream(names).collect(Collectors.toSet());
					System.out.println(namesSet);
				}
			}
			
			>>
			[이름1, 이름2, 이름3, 이름4, 이름5, 이름3]
			[이름1, 이름2, 이름3, 이름4, 이름5, 이름3]
			[이름3, 이름2, 이름1, 이름5, 이름4]
			
			------------------------------------------------------------------------
			package exam01;
			import java.util.Arrays;
			import java.util.List;
			import java.util.stream.Collectors;

			public class Ex06 {
				public static void main(String[] args) {
					List<String> fruits = Arrays.asList("Apple", "Orange","Mango","Grape");

					String str = fruits.stream().collect(Collectors.joining(","));
					System.out.println(str);

					String str2 = fruits.stream().collect(Collectors.joining("#", "**", "^^"));
					System.out.println(str2);
				}
			}
			>>
			Apple,Orange,Mango,Grape
			**Apple#Orange#Mango#Grape^^
			--------------------------------------------------------------------------
			package exam01;
			import java.util.ArrayList;
			import java.util.Arrays;
			import java.util.stream.Collectors;

			public class Ex07 {
				public static void main(String[] args) {
					String[] names = {"이름1", "이름2","이름3","이름4","이름5","이름3"};

					ArrayList<String> items = Arrays.stream(names).collect(Collectors.toCollection(() -> new ArrayList<>()));
					ArrayList<String> items2 = Arrays.stream(names).collect(Collectors.toCollection(ArrayList::new));

					System.out.println(items);
					System.out.println(items2);
				}
			}


	8) groupingBy(), partitioningBy()
	
		groupingBy()  : 특정값을 가지고 그룹핑
		
		partitioningBy() : 양분(참, 거짓)
		
			Map<Boolean, List<..>>
			
			--------------------------------------------------------
			---	groupingBy 		
			--------------------------------------------------------
						
			package exam01;
			public class Student {
				private int ban;
				private String name;

				public Student(int ban, String name) {
					this.ban = ban;
					this.name = name;
				}

				@Override
				public String toString() {
					return "Student{" +
							"ban=" + ban +
							", name='" + name + '\'' +
							'}';
				}

				public int getBan() {
					return ban;
				}
				public String getName() {
					return name;
				}
			}
						
						
			package exam01;
			import javax.management.remote.rmi.RMIServerImpl_Stub;
			import java.sql.SQLOutput;
			import java.util.Arrays;
			import java.util.List;
			import java.util.Map;
			import java.util.stream.Collectors;

			import static java.util.stream.Collectors.toMap;

			public class Ex08 {
				public static void main(String[] args) {
					Student[] students = {
							new Student(1, "이이름"),
							new Student(1, "김이름"),
							new Student(1, "박이름"),

							new Student(2, "이이름"),
							new Student(2, "김이름"),
							new Student(2, "박이름"),

							new Student(3, "이이름"),
							new Student(3, "김이름"),
							new Student(3, "박이름")
					};

					//Map<Integer, String> data = Arrays.stream(students).collect(toMap(Student::getBan, Student::getName));
					//System.out.println(data);

					Map<Integer, List<Student>> data = Arrays.stream(students).collect(Collectors.groupingBy(s -> s.getBan()));
					List<Student> students2 = data.get(2);
					students2.forEach(System.out::println);
				}
			}
			>>
			Student{ban=2, name='이이름'}
			Student{ban=2, name='김이름'}
			Student{ban=2, name='박이름'}
			--------------------------------------------------------
			---	partitioningBy 		
			--------------------------------------------------------	
			package exam01;
			public class Student {
				private int ban;
				private String name;
				private int score;

				public Student(int ban, String name, int score) {
					this.ban = ban;
					this.name = name;
					this.score = score;
				}

				@Override
				public String toString() {
					return "Student{" +
							"ban=" + ban +
							", name='" + name + '\'' +
							'}';
				}

				public int getBan() {
					return ban;
				}
				public String getName() {
					return name;
				}
				public int getScore() {
					return score;
				}

			}

			package exam01;
			import javax.management.remote.rmi.RMIServerImpl_Stub;
			import java.sql.SQLOutput;
			import java.util.Arrays;
			import java.util.List;
			import java.util.Map;
			import java.util.stream.Collectors;

			import static java.util.stream.Collectors.toMap;

			public class Ex08 {
				public static void main(String[] args) {
					Student[] students = {
							new Student(1, "이이름", 80),
							new Student(1, "김이름", 100),
							new Student(1, "박이름", 40),

							new Student(2, "이이름", 80),
							new Student(2, "김이름", 50),
							new Student(2, "박이름", 70),

							new Student(3, "이이름", 80),
							new Student(3, "김이름", 100),
							new Student(3, "박이름", 90)
					};

					Map<Boolean, List<Student>> data = Arrays.stream(students)
							.collect(Collectors.partitioningBy(s -> s.getScore() >= 80));
							
					List<Student> students1 = data.get(true);
					students1.forEach(System.out::println);
				}
			}
			>>
			Student{ban=1, name='이이름'}
			Student{ban=1, name='김이름'}
			Student{ban=2, name='이이름'}
			Student{ban=3, name='이이름'}
			Student{ban=3, name='김이름'}
			Student{ban=3, name='박이름'}			
	flatMap : 중간연산
	
			public class Ex10 {
				public static void main(String[] args) {
					List<String> names = Arrays.asList("이름1", "이름2","이름3");
					List<String> fruits = Arrays.asList("Apple", "Mango","Melon");

					Stream<Stream<String>> stm = Stream.of(names.stream(), fruits.stream());
					//stm.forEach(System.out::println);
					/*
					stm.forEach(st -> {
					   st.forEach(System.out::println);
					});
					*/
					//stm.flatMap(s -> s).forEach(System.out::println);
					String[] strs = stm.flatMap(s -> s).toArray(String[]::new);
					System.out.println(Arrays.toString(strs));
				}
			}
			>>
			[이름1, 이름2, 이름3, Apple, Mango, Melon]
			
	
	참고 JS) 
		const nums = [[1,2,3], [4,5,6], [7,8,9]];
		const nums2 = nums.flatMap(n => n);
		nums2;
		(9) [1, 2, 3, 4, 5, 6, 7, 8, 9]
	
	
	