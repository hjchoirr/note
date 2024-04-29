컬렉션 프레임워크(Collections Framework)

	컬렉션(Collections) - 데이터 군 
	프레임워크(Framework) : 표준화된 설계 (틀)

	데이터 군을 다루는데 필요한 클래스를 표준화서 설계 

	(참고) 프레임워크 vs 라이브러리
		프레임워크 : 개발방식의 틀을 정해놓은 것
	
	
1. 컬렉션 프레임워크의 핵심 인터페이스 
	(참고) E : element 의미

	1) List : 순차 자료 구조에 대한 설계
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
			 : 스택 구현 시 활용 가능
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
			 
			 List<String> names = new LinkedList<>();  //다형성
			 List<String> names = new ArrayList<>();   //다형성
			 
		  - Stack 
			 : E push(E e) : 끝에 추가
		     : E pop() : 끝 요소 꺼내기
		  - Queue
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
					iter = books.iterator(); // 다시 이터레이터 돌리려면 이렇게 해야함
					while(iter.hasNext()) {
						Book book = iter.next();
						System.out.println(book);
					}
				}
			}
			
		
	2)  Set : 집합 자료 구조에 대한 설계
		- 중복 없음
			: 중목제거 기준 : 동등성 비교 - equals() & hashCode()
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
		  - TreeSet

	List, Set : 컬렉션 프레임워크 내
	Map : 컬렉션 프레임워크 외

Map : 사전 자료 구조에 대한 설계
	- key와 value의 쌍
	- key는 중복없고, value는 중복허용
	- key : 값을 찾기위한 값
	

	컬렉션의 주요 작업

		C(Create) : 추가
		R(Read) : 조회
		U(Update) : 변경
		D(Delete) : 삭베




2. Stack과 Queue

3. Iterator, ListIterator, Enumeration

4. Comparator와 Comparable

5. Arrays

6. Collections