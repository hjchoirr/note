컬렉션 프레임워크(Collections Framework)

	컬렉션(Collections) - 데이터 군 
	프레임워크(Framework) : 표준화된 설계 (틀)

	데이터 군을 다루는데 필요한 클래스를 표준화서 설계 

	(참고) 프레임워크 vs 라이브러리
		프레임워크 : 개발방식의 틀을 정해놓은 것

	컬렉션의 주요 작업

		C(Create) : 추가
		R(Read) : 조회
		U(Update) : 변경
		D(Delete) : 삭베
	
	
1. 컬렉션 프레임워크의 핵심 인터페이스 
	(참고) E : element 의미
	Collection Framework : 데이터 군을 다룰때 편하게 사용할 수 있도록 틀을 정해놓은 것
	Collection 인터페이스 - List, Set

	1) List 인터페이스 : 순차 자료 구조에 대한 설계
		- 순서가 있는 자료
		- 대표적인 예 : 배열
		- 특정 순서에 추가, 제거, 변경 등 매개변수가 정의된 메서드가 많다
		  ex) add(int index, E element) 
		  
		  추가
		  - bollean add(E e)
		  - bollean add(int index, E e)
		  - bollean addAll(Collection<? extends E> ..)
		  - bollean addAll(inr index, Collection<? extends E> ..)

		  조회
		  - E get(int index)
		  - int indexOf(Obejct o)  : 특정요소의 위치번호 반환( 왼쪽 -> 오른쪽, 없을때는 -1 )
		  - int lastIndexOd(Object e) : 특정 요소의 위치번호 ( 오른쪽 -> 왼족, 없을때는 -1 )
		  - boolean contains(Object e)
		  - boolean containsAll(Collection<?>..)

		  수정
		  - E set(int index, E e) : 특정 위치에 있는 요소 변경

		  삭제
		  - E remove(int index) : 특성순서 위치에 있는 요소 제거(꺼냄)
		  - boolean remove(Object e)
		  - boolean removeAll(Collecttion<?> c)

		 기타
		  - int size() :요소의 개수
		  - clear() : 요소 비우기
		  - retainAll(Collection..) : 매개변수에 있는 값만 제외하고 모두 제거
		 
		 구현된 클래스
		 
		  - ArrayList
			 : 배열을 구현한 클래스
			 : 스택 구현 시 활용 가능, 삭제는 역방향으로...
			 : 쓰레드 안정성 확보 안됨
			 : 기본 생성 배열 칸수 10, 공간의 갯수 부족하면 2배 크기로 새로 생성
			 
			 : 물리적으로 붙어있는 나열 구조이므로 순차조회는 매우 빠르다
			 : 순서위치가 바뀌는 수정, 삭제시 새로운 배욜이 매번 생성 -> 성능 저하
			 : List 구현 객체 중 가장 많이 사용함
			 
		  - LinkedList
		  
			 : 다음 요소, 이전 요소 ..주소를 가지고 서로 연결 관계(물리적 순서 아니고 논리적 순서로 되어 있음)
			 : 삭제, 수정 등 순서 위치가 자주 바뀌는 자료에서 유리
				- 다음 요소 위치 주소만 변경하면 되므로, 성능저하 없다
			 : 논리적 순서이므로 위치 계산하는 일을 더 하므로 조회에서는 ArrayList보다 불리
			 : 자바는 DoublyLinkedList 형태로 구현되어 있음( 다음요소, 이전요소 주소를 모두 가지고 있음)
			 : Queue 인터페이스의 구현체
			 
			 List<String> names = new LinkedList<>();  //다형성
			 List<String> names = new ArrayList<>();   //다형성
			 
		  - Stack 인터페이스
			 : E push(E e) : 끝에 추가
		     : E pop() : 끝 요소 꺼내기
		  - Queue 인터페이스
			 : offer(E e) : 끝에 추가
			 : poll() : 맨앞 요소 꺼내기
		  - Dqueue 인터페이스
			 : Stack + Dqueue
			 : boolean offerFirst(E e) :요소 앞에 추가
			 : boolean offerLast(E e) :요소 끝에 추가
			 : E pollFirst :가장 앞요소 꺼내기
			 : E pollLast :가장 끝요소 꺼내기
			 : peek() : 제거하지 않고 조회만
			  
		  - Vector 
		     : ArrayList 동일 / 배열을 구현한 클래스, 쓰레드 안정성 확보
			 : 과거 기능의 호환성 유지를 위해 남겨둠 

			---(ArrayList 예제)------------
			package exam04;
			import java.util.ArrayList;

			public class Ex01 {
				public static void main(String[] args) {

					ArrayList<String> names = new ArrayList<>();
					names.add("이름1");
					names.add("이름2");
					names.add("이름3");
					names.add("이믈4");
					names.add("이름5");

					/* // 앞에서부터 지우면 지울때마다 새로 배열 생성되고 다 지워지지 않는다
					for(int i = 0; i < names.size(); i++) {
						names.remove(i);
					}
					*/
					// 거꾸로 뒤에서부터 제거하면 새로 배열을 만들지 않으므로 다 제거됨 
					for(int i = names.size() - 1; i >= 0 ; i--) {
						names.remove(i);
					}
					System.out.println(names); //names.toString() -> 담긴 값을 확인할 수있도록 재정의

					for(int i = 0; i < names.size(); i++) {
						String name = names.get(i);
						System.out.printf("%d) %s%n", i, name);
					}
				}
			}
			>> 스택 구현시 활용 가능 : LIFO 
			----(Stack 예제)--------------
			package exam04;
			import java.util.Stack;

			public class Ex02 {
				public static void main(String[] args) {
					Stack<String> names = new Stack<>();
					names.push("이름1");
					names.push("이름2");
					names.push("이름3");

					System.out.println(names.pop());
					System.out.println(names.pop());
					System.out.println(names.pop());
				}
			}
			>>
			이름3
			이름2
			이름1
			----(Vector 예제 => 배열은 자리 모자라면 두배로 다시 만든다)--------------
			package exam04;
			import java.util.Vector;

			public class Ex03 {
				public static void main(String[] args) {
					Vector<String> names = new Vector<>(3);
					System.out.println(names.capacity());
					names.add("이름1");
					names.add("이름2");
					names.add("이름3");
					System.out.println(names.capacity());
					names.add("이름4");
					System.out.println(names.capacity());
				}
			}
			----Queue 예제 -------------------------
			package exam04;

			import java.util.LinkedList;
			import java.util.Queue;

			public class Ex04 {
				public static void main(String[] args) {
					Queue<Integer> items = new LinkedList<>();
					items.offer(1);
					items.offer(2);
					items.offer(3);
					System.out.println(items.poll());
					System.out.println(items.poll());
					System.out.println(items.poll());
				}
			}
			------LinkedList 예제-------------------------------------------
			package exam04;
			import java.util.ArrayList;
			import java.util.LinkedList;
			import java.util.List;

			public class Ex01 {
				public static void main(String[] args) {

					List<String> names = new LinkedList<>(); //다형성
					names.add("이름1");
					names.add("이름2");
					names.add("이름3");
					names.add("이믈4");
					names.add("이름5");

					/*//
					for(int i = 0; i < names.size(); i++) {
						names.remove(i);
					}
					*/
					// 
					for(int i = names.size() - 1; i >= 0 ; i--) {
						names.remove(i);
					}


					System.out.println(names); 

					for(int i = 0; i < names.size(); i++) {
						String name = names.get(i);
						System.out.printf("%d) %s%n", i, name);
					}
				}
			}
			
		Iterator, ListIterator, (Enumeration : Iterator보다 구식)

		 - 반복자 패턴 인터페이스
		    Collection의 Iterator : 커서 이동 방식 hasNext(), next()
			 
			 Iterator<E> iterator() 
			 
			package exam04;
			import java.util.ArrayList;
			import java.util.Iterator;
			import java.util.List;

			public class Ex05 {
				public static void main(String[] args) {
					List<Book> books = new ArrayList<>();
					books.add(new Book(1000, "책1", "저자1"));
					books.add(new Book(1001, "책2", "저자2"));
					books.add(new Book(1002, "책3", "저자3"));
					books.add(new Book(1003, "책4", "저자4"));
					books.add(new Book(1004, "책5", "저자5"));

					Iterator<Book> iter = books.iterator();
					while(iter.hasNext()) {
						Book book = iter.next();
						System.out.println(book);
					}
					System.out.println();
					iter = books.iterator(); // 다시 이터레이터 돌리려면 iterator 생성 또 해야함
					while(iter.hasNext()) {
						Book book = iter.next();
						System.out.println(book);
					}
				}
			}
		
		ListIterator - List에 특화된 Iterator / List 인터페이스에 정의
			순서에 대한 메서드 정의
			순방향 조회 : hasNext(), next(), nextIndex()
			역방향 조회 : hasPrevious(), previous(), previousIndex()
				package exam01;

				import java.util.ArrayList;
				import java.util.ListIterator;

				public class Ex02 {
					public static void main(String[] args) {
						ArrayList<Book> books = new ArrayList<>();
						books.add(new Book(1000, "책1","저자1"));
						books.add(new Book(1001, "책2","저자2"));
						books.add(new Book(1002, "책3","저자3"));
						books.add(new Book(1003, "책4","저자4"));
						books.add(new Book(1004, "책5","저자5"));
						books.add(new Book(1005, "책6","저자6"));

						ListIterator<Book> iter = books.listIterator();
						System.out.println("---순방향----");
						while(iter.hasNext()) {
							Book book = iter.next();
							int index = iter.nextIndex();
							System.out.println(book);
							System.out.println("index: " + index);
						}
						System.out.println("----역방향----");
						while(iter.hasPrevious()) {
							Book book = iter.previous();
							int index = iter.previousIndex();
							System.out.println(book);
							System.out.println("index: " + index);
						}
					}
				}
			
			
		-> 향상된 for문, forEach 더 많이 씀
		
			package exam01;
			import java.util.ArrayList;

			public class Ex03 {
				public static void main(String[] args) {
					ArrayList<Book> books = new ArrayList<>();
					books.add(new Book(1000, "책1","저자1"));
					books.add(new Book(1001, "책2","저자2"));
					books.add(new Book(1002, "책3","저자3"));
					books.add(new Book(1003, "책4","저자4"));
					books.add(new Book(1004, "책5","저자5"));
					books.add(new Book(1005, "책6","저자6"));

					for(Book book : books) {   //향상된 for문
						System.out.println(book);
					}
					books.forEach(System.out::println); // forEach 문
				}
			}		
		
	2)  Set 인터페이스 : 집합 자료 구조에 대한 설계
		- 중복 없음
			: 중목제거 기준 : 동등성 비교 - equals() & hashCode()   ****중요****
			
		- 순서 불필요
		
		추가
		  - boolean add(E e)
		  - boolean addAll(Collection..)
		
		조회
		수정
		  - index가 없으니 조회, 수정 할 수 없음
		
		삭제
		  - boolean remove(Object e)
		  - boolean removeAll(Collecttion<?> c)
		
		기타
		  - int size() : 요소의 갯수
		  - boolean contains(Object e)
		  - boolean containsAll(Collection<?>..)
		  - clear() : 요소 비우기
		  - retainAll(Collection..) : a매개변수에 있는 값만 제외하고 모두 제거

		구현된 클래스
		  - HashSet
		  - TreeSet : HashSet + 정렬
		  
		  - Comparator와 Comparable : 기본정렬 기준 인터페이스   ****중요****
		  
		     : java.lang.Comparable 인터페이스 타고 
			 : 기본정렬기준 : Natural Ordering -> 이건 원하는 정렬이 아님
			 : int compareTo(T o) 재정의 하기
				반환값이 양수 : 현재 객체 뒤로 배치, T o을 앞에 배치
				반환값이 0 : 배치 안함
				반환값이 음수 : 현재 객체 앞으로 배치, T o는 뒤에 배치
				
				현재 객체의 정수 - 비교 객체의 정수 : 오름차순
				비교 객체의 정수 - 현재 객체의 정수 : 내림차순
		  
		  - Comparator : 대안적인 정렬기준 추가 ( 예 : String은 오름차순밖에 안됨 )
				java.util.Comparator
		  
				
			package exam01;
			import java.util.HashSet;

			public class Ex04 {
				public static void main(String[] args) {
					HashSet<String> names = new HashSet<>();
					names.add("이름1");
					names.add("이름2");
					names.add("이름2");
					names.add("이름3");
					names.add("이름4");
					names.add("이름5");
					System.out.println(names);
				}
			}
			-----------------------------------------------
			package exam01;

			import java.util.Objects;

			public class Book {
				private int isbn;
				private String title;
				private String author;

				public Book(int isbn, String title, String author) {
					this.isbn = isbn;
					this.title = title;
					this.author = author;
				}

				@Override
				public boolean equals(Object obj) {  // 중복 기준 1
					if(obj instanceof  Book) {
						Book book = (Book) obj;
						if( isbn == book.isbn && title == book.title && author == book.author) {
							return true;
						}
					}
					return false;
				}
				@Override
				public int hashCode() {  // 중복 기준 2
					return Objects.hash(isbn, title, author);
				}
				@Override
				public String toString() {
					return "Book{" +
							"isbn=" + isbn +
							", title='" + title + '\'' +
							", author='" + author + '\'' +
							'}';
				}
			}
			package exam01;

			import java.util.ArrayList;
			import java.util.HashSet;

			public class Ex05 {

			    public static void main(String[] args) {
					HashSet<Book> books = new HashSet<>();

					books.add(new Book(1000,"책1","저자1"));
					books.add(new Book(1001,"책2","저자2"));
					books.add(new Book(1001,"책3","저자3"));
					books.add(new Book(1002,"책4","저자4"));
					books.add(new Book(1003,"책5","저자5"));
					books.add(new Book(1004,"책6","저자6"));
				}
			}
			---------------------------------------------
			package exam01;

			import java.util.Set;
			import java.util.TreeSet;

			public class Ex04 {
				public static void main(String[] args) {
					Set<String> names = new TreeSet<>();  // 다형성
					names.add("이름1");
					names.add("이름2");
					names.add("이름2");
					names.add("이름3");
					names.add("이름4");
					names.add("이름5");
					System.out.println(names);
				}
			}
			>> [이름1, 이름2, 이름3, 이름4, 이름5]  //정렬됨
			
			---------------------------------------------
			package exam01;

			import java.util.Set;
			import java.util.TreeSet;

			public class Ex05 {
				public static void main(String[] args) {
					Set<Book> books = new TreeSet<>();

					books.add(new Book(1000,"책1","저자1"));
					books.add(new Book(1001,"책2","저자2"));
					books.add(new Book(1001,"책3","저자3"));
					books.add(new Book(1002,"책4","저자4"));
					books.add(new Book(1003,"책5","저자5"));
					books.add(new Book(1004,"책6","저자6"));

					books.forEach(System.out::println);
				}
			}

			이게 정렬이 안되어서..
			Book class에 정렬 기준 추가 , Comparable 인터페이스 상속, compareTo() 메서드 재정의

			public class Book implements Comparable<Book>{
				...
				
			    @Override
				public int compareTo(Book o) {
					//return isbn - o.isbn; //오름차순
					return o.isbn - isbn;   //내림차순
				}
			---------------------------------------------
			대안적 정렬 기준 정의 방법 
			package exam01;
			import java.util.Comparator;
			import java.util.Set;
			import java.util.TreeSet;

			public class Ex04 {
				public static void main(String[] args) {

					Comparator<String> comp = new Comparator<String>() {
						@Override
						public int compare(String o1, String o2) {
							//return o1.compareTo(o2); //오름차순: 기존 String 정렬기준과 동일
							return o2.compareTo(o1); // 내림차순
							//return o1.compareTo(o2) * -1; // 내림차순
						}
					};
					Set<String> names = new TreeSet<>(comp);  // 정렬기준 제공
					names.add("이름4");
					names.add("이름3");
					names.add("이름1");
					names.add("이름2");
					names.add("이름2");
					names.add("이름5");
					System.out.println(names);
				}
			}

			-----------------------------------------------
			Set<String> names = new TreeSet<>(comp); //이거 대신
			Set<String> names = new TreeSet<>(Comparator.reverseOrder());  // 이렇게 쓰는게 쉽다
			
			-----------------------------------------------
			//Book Class에서 정렬은 오름차순으로 해 놓고 
			
			public int compareTo(Book o) {
				return isbn - o.isbn; //오름차순 이게 기본 : 주로이렇게 고정
			}

			/// 내림차순은 이렇게 쓴다 
			import java.util.Comparator;
			import java.util.Set;
			import java.util.TreeSet;

			public class Ex05 {
				public static void main(String[] args) {
					//Set<Book> books = new TreeSet<>();
					Set<Book> books = new TreeSet<>(Comparator.reverseOrder());// 내림차순은 주로이렇게

					books.add(new Book(1000,"책1","저자1"));
					books.add(new Book(1001,"책2","저자2"));
					books.add(new Book(1001,"책3","저자3"));
					books.add(new Book(1002,"책4","저자4"));
					books.add(new Book(1003,"책5","저자5"));
					books.add(new Book(1004,"책6","저자6"));

					books.forEach(System.out::println);
				}
			}


		naturalOrder() : 기본정렬 기준 사용한 정렬 (java.lang.Comparable() 인터페이스 + compareTo()
		Comparator.reversedOrder() : 기본정렬기준의 반대 ( 꼭 내림차순이라 볼 수 없음 )
	
	List, Set : 컬렉션 프레임워크 내
	Map : 컬렉션 프레임워크 외

Map : 사전 자료 구조에 대한 설계
	- key와 value의 쌍
	- key는 중복없고, value는 중복허용
	- key : 값을 찾기위한 값

	추가
		V put(K key, V value) 	: key 가 없을땐 추가, 있을땐 value 값 수정
		void putAll(Map<? extends K,? extends V> m)  	: Map 객체로 전체 추가
		V putIfAbsent(K key, V value)  	: key 없을때만 추가
		
	조회
		V get(Object key)  : 키를 가지고 값 조회, 없을땐 null
		V getOrDefault(Object key, V defaultValue) : key 가지고 조회, 없을땐 defaultValue로 대체
		
		Set<Map.Entry<K,V> entrySet() : 전체키, 값의 쌍(Map.Entry) 조회
	수정
		V put(K key, V value) 
		V replace(K key, V value)
		boolean replace(K key, V oldValue, V newValue)  : 기존값이 oldValue 인 것 newValue로 교체 안전하게 교체 
	삭제
		V remove(Object key) : 키값 가지고 삭제
		boolean remove(Object key, Object value) : key, value 모두 일치하는 요소만 삭제
	기타
		int size() : 요소의 갯수
		Set<K> keySet() : Map에 포함되어 있는 키값만 추출
		Collection<V> values() : Map에 포함되어 있는 value값만 추출
		boolean containsKey(Object key) : Map key 가 포함되어 있는지 여부
		boolean containsValue(Object value) : Map에 value가 포함되어 있는지 여부
		
	구현된 클래스
		1. HashMap
		2. TreeMap
			-> 키값의 정렬
				-> 기본 정렬기준 : java.lang.Comparable / int compareTo()
				-> 대안 정렬
		
		
			package exam02;
			import java.util.HashMap;

			public class Ex01 {
				public static void main(String[] args) {
					HashMap<String, String> members = new HashMap<>();
					members.put("user01", "사용자1");
					members.put("user02", "사용자2");
					members.put("user03", "사용자3");
					members.putIfAbsent("user02", "(수정)사용자2");

					HashMap<String, String> members2 = new HashMap<>();
					members2.put("user06", "사용자6");
					members2.put("user07", "사용자7");

					members.putAll(members2);

					String userNm = members.get("user03");
					System.out.println(userNm);

					String userNm2 = members.getOrDefault("user08", "없음");
					System.out.println(userNm2);

					//System.out.println(members); // == members.toString()
				}
			}
			----------------------------------------------------------
			package exam02;

			import java.util.Collection;
			import java.util.HashMap;
			import java.util.Set;

			public class Ex02 {
				public static void main(String[] args) {
					HashMap<String, String> members = new HashMap<>();
					members.put("user01", "사용자01");
					members.put("user02", "사용자02");
					members.put("user03", "사용자03");

					Set<String> keys = members.keySet();
					Collection<String> values = members.values();
					System.out.println("keys : " + keys);
					System.out.println("values : " + values);
				}
			}

			package exam02;
			import java.util.HashMap;
			import java.util.Map;
			import java.util.Set;

			public class Ex03 {
				public static void main(String[] args) {
					HashMap<String, String> members = new HashMap<>();
					members.put("user01", "사용자01");
					members.put("user02", "사용자02");
					members.put("user03", "사용자03");

					//Set<Map.Entry<String, String>> entries = members.entrySet(); //entrySet() 많이 씀
					//for(Map.Entry<String, String> entry : entries) {
					// 위의 두줄 줄려서 아래 한줄로 합침
					for(Map.Entry<String, String> entry : members.entrySet()) {
						String key = entry.getKey();
						String value = entry.getValue();
						System.out.printf("아이디 : %s, 회원명: %s%n", key, value);
					}
				}
			}
			--------------------------------------------------
			//위 예제를 TreeMap으로 바꾸면 정령되어 나옴
			Map<String, String> members = new TreeMap<>(); 
			
			//생성자에  Comparator.reverseOrder() 넣으면 거꾸로 sort됨
			Map<String, String> members = new TreeMap<>(Comparator.reverseOrder());






2. Stack과 Queue

3. Iterator, ListIterator, Enumeration

4. Comparator와 Comparable

5. Arrays

6. Collections