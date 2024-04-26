4/12

실습환경 구축하기
1. 자바 설치하기
	CRTL + SHIFT + B : 즐겨찾기 탭 

	버전 :JDK22 /21 /17

	jdk 다운로드
	https://www.oracle.com/kr/java/technologies/downloads/

	jdk 21 / 17 : LTS버전 

	jdk17 Windows용	x64 MSI Installer 다운
	Documentation Download jdk-17.0.10_doc-all.zip -> doc폴더 옮기기
	
	
2. 환경변수 설정하기
	JAVA_HOME : 다른 SW가 사용하기 때문에 설정함.
		Win버튼 -> 찾기 -> 환경변수 -> 시스템변수
	JAVA_HOME : C:\Program Files\Java\jdk-17
	
	C:\Program Files\Java\jdk-17
	C:\Program Files\Java\jdk-17\bin 
		- 실행 파일이 있는 폴더 
		
		java.exe  : class 파일 실행 
		javac.exe : java -> class 컴파일 
		javadoc.exe : java api 문서를 생성

	- 환경 변수 등록 목적 : 어떤 경로라도 항상 접근 가능 설정 
	> java --version

	jdk-17\bin
	jdk-17\lib
	jdk-17\lib\src.zip
	
	
3. 인텔리제이  (커뮤니티 버전) 체코업체, 코틀린 개발한 업체
	다운, 설치
	https://www.jetbrains.com/ko-kr/idea/
	IntelliJ IDEA Community Edition (ideaIC-2024.1.exe) : 확장자에 연결 안함
	
	new project > day01 > location: D:\hjchoi\3.응용SW기초기술활용\source
	Add sample code : 체크안함
	
	
	확장자 java -> 컴파일(javac 파일명.java) -> 확장자 class -> java 클래스 파일

	Write Once, Run EveryWhere
		
	.java -> .class (JVM 인식하는 중간언어) -> JVM(Java Virtual Machin - 자바 가상머신) - 플랫폼에 맞는 기계어로 컴파일 


	패키지 : 폴더
		패키지 설정되어 있지 않으면 src 폴더 => default 패키지
		exam01 패키지 만들고 안에 클래스 추가하면 -> 
			--------------------------
			package exam01;
				public class Hello {
			}
			--------------------------
			
		하위패키지(하위폴더) 는 . 으로 표시

			--------------------------
			package exam01.sub;
				public class Hello {
			}
			--------------------------
		day01/out 폴더 : 인텔리제이가 컴파일하면 클래스 파일 생성해서 넣는 폴더 : 이클립스는 다름
		

변수와 자료형
1. 컴퓨터는 데이터를 어떻게 표현할까?
	- 반도체 
	전기 신호가 있으면 1
	전기 신호가 없으면 0
	-> 2진수
	
2. 10진수와 2진수
	1100100 - 2진수 
	10진수 : 100

	bit : 1,0으로 표현할 수 있는 최소 단위
	8bit -> 1byte

3. 부호있는 수를 표현하는 방법
	1bit : 부호 비트 (0 - 양수, 1 - 음수)
	1byte = -2^7 ~ 2^7 - 1 (-128 ~ 127)

변수란 무엇일까?
1. 변수란? 
	- 변하는 수
	- 공간의 이름 
	- 공간 : 메모리 
	- 자료형 : 메모리 크기 
	
	메모리, 이름, 자료형
	자료형 : 공간의 크기 
	
2. 변수 선언하고 값 대입하기
	자료형 변수명;
	int 변수; (4바이트)
	
	자료형 변수명 = 값;
	
3. 변수 초기화하기
	변수에 최초로 값을 대입(저장) 하는 것 
	자료형 변수명 = 값;  - 선언과 동시에 초기화
	
4. 변수 이름 정하기
	1) 알파벳, 숫자, 특수문자($, _)
	2) 숫자는 변수명 앞에 사용 불가 
	3) 예약어는 사용 불가(return, throw, if ...)
	4) 유의미한 단어로 변수명 구성 
	
		int 1st; - X
		int _$dollor - O
		int noOfStudent - O
		int $a  - O
		int throw - X
		
		noOfStudent     : 카멜 표기법 - 변수
		class OrderInfo : 클래스명은 첫글자 대문자로 -> 파스칼표기법
		
참고)

	주석 - 설명, 컴파일러가 해석 X, 제거
		// - 한줄 주석 
		/*
			여러줄 주석 
			....
		*/
		/**  설명 주석 
		*
		*
		*/
	2) 프로그램 실행 배제 - 해석 X -> 실행 X
	
변수가 저장되는 공간의 특성, 자료형
1. 변수와 메모리

	참고)
	자료형 (공간의 크기)
	- 기본 자료형
		- 숫자를 담는 자료형
		- 변수만 선언해도 기본값 들어감
		  정수 기본값 : 0
		  실수 기본값 : 0.0
		  boolean 기본값 : false		

		정수형
		   : byte  - 1byte 
		   : short - 2byte
		   : int   - 4byte
		   : long  - 8byte / l(L) 
		실수형
		   : float -  4byte / f(F)
		   : double - 8byte
		논리형
		   : boolean - 1byte (true, false)
		   : 판별식, 반복문에서 주로 사용
		문자형
		   : char - 2byte ~ 3byte 문자 char ch = 'A';
		     
			아스키코드(1byte) : 문자표
			유니코드(2 ~ 3byte) : 문자표
				---------------------------
				char ch2 = '가';
				System.out.println(ch2);
				System.out.println(ch2 + 1);			
				----------------------------
				( * char는 양의 정수만 저장 가능함  char ch3 = -65;  <- 안됨 )  
			
1) 정수 자료형
	byte : 1바이트(8비트) -2^7 ~ 2^7 -1 (-126~125)
	short : 2바이트(16비트)  -2^15~2^15 - 1 
	int    : 4바이트(32비트) -2^31~2^31 - 1
	long  : 8바이트(64비트) -2^63~2^63 - 1
	
2) 문자 자료형
	char : 2바이트 
	
		참고) 숫자 변수에 대입 숫자 그대로 대입 
				문자를 변수에 대입할 경우 '문자'
				
				숫자 - 문자 : 아스키코드(1byte)
									/ 양의 정수 
									unsigned : 양의 정수 
									0~255
									
									- 영문, 특수문자 
				숫자 - 한글 :  유니코드(2byte, 3byte)
				
3) 실수 자료형
	- 소수점을 가지고 있는 숫자 
	float : 4byte
	  - 숫자 끝에 F(f)를 추가 
	double : 8byte
	
4) 논리자료형
	- 참, 거짓 (true, false)
	boolean

	- 참조 자료형
		- 다른 데이터의 주소를 참조 하는 자료형 
		- 자원의 주소만 가지고 있는 변수 사용시 
		- 기본값 : null (변수만 선언해도 기본값 들어감)


상수와 리터럴
1. 상수 선언하기
	- 상수 : 변하지 않는 수 (constant)
	- final 예약어 변수명 앞에 추가 
	  (final 이름 명명 관례)
		: 대문자로 작성 
		: 단어와 단어 사이 _로 구분  
			예) final int MAX_NUMBER = 10;
		
참고) final : 변경할 수 없는 
		
2. 리터럴(literal)
	
	- 리터럴 상수 
	- 재료가 되는 수 (문자, 숫자, 논리값)
	- 같은 재료 -> 하나만 생성(상수)		
	
	
	- 모든 정수를 처음에는 int 인식 
	long num = 10000000000
	
		:  처음부터 long으로 할당하려면 숫자 끝에 l, L을 붙여주면 된다.
		long num1 = 10000000000L;    // long type은 이렇게 써야..
		long num1 = 10_000_000_000L;  // 너무 기니까 이렇게 써도 됨
			
	1) int 10000000000 -> 오류 
		
		
	
	- 모든 실수를 처음에는 double 인식 
			
	
형변환
- 자료형간 변환 

1. 묵시적 형변환
	- 자동 형변환 
	- 작은 자료형 -> 큰 자료형 
	- 정수(덜 정밀한 숫자) -> 실수(더 정밀한 숫자)
	- 연산 중 자동 형변환  : 연산은 같은 자료형만 가능  -> 연산을 위해서 자동 형변환 
	
        byte num1 = 100; 
        int num2 = num1;  // 자동 형변환 = 묵시적 형변환
        long num3 = num2; // 자동 형변환 = 묵시적 형변환
	
	byte -> short -> int -> long -> float -> double  // 자동 형변환
			char -> int
			
	연산중에도 자동형변환 : 작은자료형 -> 큰자료형 변경
	정수 -> 실수
	
	    int num1 = 10;
        double num2 = 2.5;
		System.out.println(num1 * num2);
		>> 25.0 
	
		
2. 명시적 형변환
	- 데이터의 유실이 발생할 가능성이 있는 경우 - 자동 형변환 X
	- 명시적으로 형변환 의사 표현 
        
		int num1 = 100;
        byte num2 = (byte)num1;	
		
        double num1 = 10.123;
        long num2 = (long)num1;
        System.out.println(num2);  // -> 10 데이터 유실
		
		
연산자
	- 항과 연산자
		항: 연산에 사용되는 값
		연산자: 연산에 사용되는 기호
		단항연산, 이항연산, 삼항연산 
		
	1. 대입 연산자 
		int num1 = 10 + 20 * 3; 
		연산의 우선순위 가장 낮다.

	2. 부호 연산자			
		+
		- : 부호반전 ( 음수 -> 양수, 양수 -> 음수 )
		
	3. 산술 연산자   
		+ - * / %
		연산자 우선순위 *, /, % 가 +, - 보다 높다
		=>  (..) 우선순위 강제 적용
		
	4. 증가감소 연산자	
		++ -- : 단항연산자
		
		int num2 = num++  : 대입먼저, 증가나중
		int num2 = ++num  : 증가먼저, 대입나중 
		
	5. 복합대입 연산자 : 대입연산자 + 다른 연산자(주로 산술연산, +,-,*,/,% ...)
		num += 2;
		num *= 2;
	
	
4/15


	6. 비교연산자(관계연산자)
		> <=, >=, <
		==
		!=
		: 연산 결과는 논리값(true, false) / 판별식, 조건식, 반복문
		
		== : 동일성 비교 (주소비교) (참고) js의 === 해당
		
	7. 논리연산
		AND연산  &&
		OR연산   ||   
		NOT연산  !
		
		boolean r1 = num >= 10;
		boolean r2 = num < 100;
		boolean r = r1 && r2;
		boolean r = num >= 10 && num < 100;
		
		* 논리연산 보다 비교연산 먼저 수행 ( 우선순위: 비교연산 > 논리연산 ) 
		* 단락회로평가
		
			package exam01;

			public class Ex02 {
				public static void main(String[] args) {
					int num = 10;
					boolean r = num++ > 10 && (num = num + 10) > 15; //뒷쪽연산은 아예 수행 안함
					System.out.println(r);
					System.out.println(num);

					int num1 = 10;
					boolean r1 = ++num1 > 10 && (num1 = num1 + 10) > 15; // 뒷쪽연산도 수행함
					System.out.println(r1);
					System.out.println(num1);
				}
			}

			>>
			false
			11   
			true
			21
			
	8. 삼항조건 연산자
		조건식 ? 참일때 : 거짓일때
		1항      2항      3항
		
		(참고) String 문자열
			   String str = "가나다라";
			   + 연산자 : 문자열 결합, 문자+숫자, 문자+문자, 문자+논리값
			   char ch = '가';  //문자1개
			   
	* 연산자의 우선순위
		- 가장 낮은거 : 대입
		- 가장 높은거 : ()
		- 비교 먼저, 논리 나중 
	
		
조건문
	if (조건식) {
		// 조건식이 참일때 실행되는 코드
	}

	if (조건식) {
		// 조건식이 참일때 실행되는 코드
	} else {
		// 조건식이 거짓일때 
	}

	if (조건식1) {
		// 조건식1이 참일때 실행되는 코드 
	} else if (조건식2) {
		// 조건식1 거짓 + 조건식2 참
		
	} else if (조건식3) {
		// 조건식1 거짓 + 조건식2 거짓 + 조건식3 참
	} else {
		// 모든 조건이 거짓일때
	}

	선택문 
	switch(키워드) {
		case 값1: 
			//키워드 == 값1 -> 실행되는 코드 
			
		case 값2: 
			//키워드 == 값2 -> 실행되는 코드 
	}

	키워드 -> 정수(int, char), ENUM상수, 문자열 상수) 


반복문 

	1. while 

	while(조건식) {
		// 조건식이 참일때 반복 수행되는 코드 
	}

	2. do ~ while 

	do {
		// 조건식이 참일때 반복 수행되는 코드 
		// 조건식이 거짓 이라고 하더라도 한번은 실행
	} while(조건식);

	3. for 

	for (초기화식; 조건식; 증감식) {
		// 조건식이 참일때 반복 수행되는 코드
	}

	반복 중단 : break
	반복 건너뛰기 : continue - 현재 반복을 중단, 새로 시작 

	1,3,5,7,9,11,13 ...  // 2n + 1 -> 2로 나누었을때 나머지가 1인 경우 -> 홀수 

	- 횟수, 순서(index)
	- 관례적으로 초기화식 변수명 i 부터 시작하고 다음 알파벳 부터 순서대로 사용 
	i, j, k, l ....

		package exam01;
		public class Ex06 {
			public static void main(String[] args) {
				int tot = 0, tot2 = 0;
				for(int i = 0, j = 0; i <= 100; i++, j += 2) {
					tot += i;
					tot2 += j;
				}
				System.out.println("합계 tot: " + tot);
				System.out.println("합계 tot2:" + tot2);
			}
		}

		----------------------------------------------------------
		package exam01;
		public class Ex06 {
			public static void main(String[] args) {
				int tot = 0, tot2 = 0;
				int i = 0, j = 0;
				for(; ; i++, j += 2) {
					if(i > 100) {
						break;
					}
					tot += i;
					tot2 += j;
				}
				System.out.println("합계 tot :" + tot);
				System.out.println("합계 tot2:" + tot2);
			}
		}
		----------------------------------------------------------
		for(;;) , while(true) 동일
		break(반복중단), continue(반복건너뛰기) 

	중첩 반복문
		- 반복문 안에 반복문 


		System.out.println(값);  : 출력 + 줄개행(\n)
		           print(값) : 출력 (줄개행 X)
		           println() : 줄개행(\n)
		           printf(형식화)

		           %s -> 문자열 대체 
		           %d -> 정수 대체 
		           %f -> 실수 대체 
		           %n -> 줄개행

		package exam01;
		public class Ex07 {
			public static void main(String[] args) {
				for(int i = 2 ;i <= 9; i++) {
					System.out.println(i + "단-------");
					for(int j = 2; j <= 9; j++) {
						System.out.printf("%d X %d = %d\n", i, j, i * j);  // %n = \n
					}
				}
			}
		}


배열
1. 배열이란? 
- 같은 자료형(공간의 크기가 동일)       ****중요
- 물리적인 나열 구조(순차 자료 구조)    ****중요

2.  배열 선언과 초기화
	자료형[] 배열명 = new 자료형[공간의 갯수];
	자료형 배열명[] = new 자료형[공간의 갯수];
	
    int[] studentIds = new int[99]; // int 형 변수 99개 - 동일자료형
    int studentIds[] = new int[99]; // 위와 동일 , 잘 안씀
	
	
3.  배열 사용하기
	- 인덱스 연산자
			[] : 배열 공간의 위치를 계산해 주는 연산자 
					
			참고)
				인덱스 : 0부터 시작하는 순서 
	
	- 선언과 동시에 초기화 
		자료형[] 배열명 = new 자료형[] { 값1, 값2, 값3 ....};
		자료형[] 배열명 = { 값1, 값2, 값3 .... };

		int[] nums = new int[] {10,20,30,40};
		int[] nums = {10,20,30,40};
		
		가능
		int[] nums;
		nums = new int[] {10,20,30,40};  
		
		불가능
		int[] nums;
		nums = {10,20,30,40};   // 오류
	
참고)
	기본 자료형 : 초기값 
		정수형 자료형(byte, short, int, long) -> 0
		실수형 자료형(float, double) -> 0.0
		논리형 자료형(boolean) -> false
	
	참조 자료형 
		예) String - 초기값 null 

	
4. 전체 배열 길이와 유효한 값
	모든 배열 
		length : 공간의 갯수 
		
5. 향상된 for문과 배열
	for(요소 : 배열)

	package exam02;
	public class Ex04 {
		public static void main(String[] args) {
			String[] str = {"가나", "다라", "마바"};
			
			for(int i = 0; i < str.length; i++) { // 일반 for문
				System.out.println(str[i]);
			}
			for(String s: str) {         // 향상된 for문
				System.out.println(s); 
			}
		}
	}
	
	6. 다차원배열				
		1차원배열 - 직선구조
		
		2차원배열 - 면
		자료형 [][] 배열명 = new 자료형[행][열];
		
		int[][] nums = new int[] { {10, 20,30, 40}, {10, 20, 30, 40} };
		
		3차원배열 - 입체
		자료형 [][][] 배열명 = new 자료형[높이][가로][세로];


		package exam02;

		public class Ex07 {
			public static void main(String[] args) {
				int[][]nums = { {10,20,30,40}, {50,60,70,80}};

				for(int i = 0; i < nums.length; i ++) {   // nums.length: 행의갯수
					for(int j = 0; j < nums[i].length; j++) {
						System.out.println(nums[i][j]);     // i행j열
					}
				}
			}
		}
	
	java.lang : 언어 핵심 클래스가 있는 패키지
	java.util : 편의기능 모음

		- Arrays :배열의 편의기능 모음
		참고)java.util.Obects : 객체의 편의기능 모음
			 java.util.Collections : 컬렉션의 편의기능 모음
			 java.stream.Collectors : 스트림의 데이터 수집 기능 모음
			 
	

		package exam02;
		import java.util.Arrays;

		public class Ex07 {
			public static void main(String[] args) {
				int[][]nums = { {10,20,30,40}, {50,60,70,80}};
				//System.out.println(Arrays.toString(nums));
				System.out.println(Arrays.deepToString(nums));
			}
		}
		>>
		[[10, 20, 30, 40], [50, 60, 70, 80]]

