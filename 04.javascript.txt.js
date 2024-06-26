자바스크립트 
HTML - 뼈대 
CSS - 스타일 
JS - 기능 


크롬개발도구 콘솔 사용
	crtl+R
	shift+ enter 여러줄입력
	위 방향키 : 이전 기존코드 가져오기
	그냥엔터 : 실행
	기본수준... (메세지 수준 선택 옵션)


1. 실습방법 안내 
	<style>
	  ....
	</style>

- 브라우저 - 콘솔
	- 렌더링 엔진 + 자바스크립트 해석 엔진(V8)  (크롬기준)

		:  렌더링엔진 : Webkit/bink : 스타일해석
		:  자바스크립트 엔진 :V8

- SHIFT + 엔터 -> 줄 개행

자바스크립트 기초 문법
1. 자바스크립트 사용하는 방법
	1) script 태그 안에 정의하는 방식

	2) HTML 태그의 이벤트 처리기 속성 	
		on동작
	예) onclick="클릭시 동작하는 스크립트 코드"

	3) 외부 스크립트 파일로 사용하는 방법 
		<script src="스크립트파일경로"></script>

2. 자바스크립트 주석 처리
	- 자바스크립트 엔진이 해석 X
	1) 설명 
		// 한줄 설명..
		/*
		  여러줄설명 ...
		*/
		 /**
		  * 설명용 주석
		  *
		 */
	2) 실행 배제 
		 - 해석하지 않으므로 
3. 변수
	1) 정의
		공간의 이름
		변하는 수 

	2) 변수선언
		var 변수명;

		변수명 = 값; (저장)  / 최초로 값을 저장 -> 초기화


		var 변수명 = 값;  // 선언과 동시에 초기화

		참고)
			; -> 문장의 끝

			console.log(값); -> 값의 출력 기능

		CTRL + R / F5 : 새로고침
		
	3) 선언자 종류
		var, 
		let, const 
			참고) 2015년 ECMA Script6 유럽표준단체...기점으로 javascript 많이 바뀜, 추가 많이 됨 
				var 안쓰고 let, const 나옴
				현재버전 : ECMAScript6+ =  ES6+ = ESNext 
	  
	4) 선언 방법	
		변수가 여러개 있는 경우 
		변수 선언자 변수명, 변수명, 변수명;
		

	5) 변수 선언 생략
	6) 변수 끌어올림과 변수 중복 선언
		- 변수 중복 선언시 기존 값이 변경 

	7) 변수의 명명규칙
		1) 알파벳(대소문자), 숫자, 특수문자(_, $)
			 - 한글도 사용 가능 - 권장 X 
		2) 숫자는 변수명 앞에 올수 없다.
		3) 예약어는 쓸 수 없다.
			(return, if, for .... throw ...)

		4) 변수명은 의미있는 단어 작성

			var a = 10; X
			var noOfStudent;    


			(1) - var num1
			(2) - var 1st
			(3) - var _abc
			(4) - var $dollar;

		변수명을 짖는 관례 (함수명 적용)
			var numberOfOrder : 카멜 표기법
			- number Of Order 
			- 단어의 첫문자는 대문자, 다만 시작 단어는 소문자

		참고) 상수 : 변경이 불가한 수 표기 관례 : NO_OF_STUDENT
   

8) 변수에 저장할 수 있는 자료형
	자료형 : 값의 형태, 형식 

	참고) typeof : 자료형을 체크해 보는 연산자

	- 문자형(string) : "또는 '로 값을 감싸면 문자형
		var str = 'ABC'; 
		var str = "ABC";
		
	- 숫자형(number) 
		- 정수(소수점이 없는 수  - 양수(1,10), 음수(-1, -10), 0)
		- 실수(소수점이 있는 수 - 3.123, 0.0)
		
	- 논리형(boolean) 
		- 참, 거짓 
		- true (참)
		- false (거짓)
		
		- 제어문, 반복문에서 자주 활용
		
	- null : 값이 없는 상태(값)

	- undefined : 값이 정해지지 않은 상태 - 변수를 선언만 한 경우

4. 연산자	
	- 항 : 연산에 사용되는 값
			연산에 사용되는 값의 갯수 
			1 : 단항 연산
			2 : 이항 연산 
			3 : 삼항 연산
			
	- 연산자 : 연산에 사용되는 기호
	
	
	1) 산술 연산자 
		- 더하기(+), 빼기(-), 곱하기(*), 나누기( / ), 나머지(%)
		
		- 나머지(%) : 균등 배분(나머지가 나누는 수보다 작은 수가 반복해서 나온다)
		
		- 문자형 + 연산자 : 수치 연산 X,  문자를 결합
			var str1 = "abc";
			var str2 = str1 + "def";   
		- 문자 + 숫자 -> 문자 
			var num1 = '100';
			var num2 = num1 + 100; // 100100   <- 연산자 오버로드
		- 문자 + 불린 -> 문자  // 100 + true => 100true 
				
		
		- *, /, %의 연산의 우선 순위가 +, -보다 크다  
		- (....) : 연산자의 우선순위 강제 적용 
        
		[] : 대괄호
		{} : 중괄호
		() : 소괄호  : 연산시 적용 우선순위 가장 높다

	2) 대입연산자 
		- =
		- 오른쪽에 있는 값을 왼쪽 변수에 저장
		- var num = 10;
		- 연산의 우선순위가 가장 낮은 연산자
	
	3) 증가감소 연산자 
		- 단항 연산 
		- 1씩 증가, 1씩 감소
		
		var num = 10;
		num = num + 1; -> num++;   // ++num;
		
		- ++
		- --
		
		num++;   // num = num + 1;   num2 = num++; (대입부터 하고 ++ 나중에)
		++num;   // num = num + 1;   num2 = ++num; (++ 먼저하고 나중에 대입)
		
		num--; // num = num - 1;
		--num; // num = num - 1;
	
	4) 복합 대입 연산자 
		산술 연산 + 대입 
		(+, -, *, /, %) + =
		
		num = num + 2; // num += 2;
		num = num * 2; // num *= 2;
		
	5) 부호 연산자 
		(+, -)
		- : 부호 반전 (음수 -> 양수, 양수 -> 음수)
			(* - 1)
		
	6) 비교 연산자 = 논리연산자
		크다(>), 작다(<), 크거나 같다(>=), 작거나 같다(<=)
		같다(==)
		같지않다(!=, !==)
		
		- 연산의 결과가 논리값(boolean : 참(true), 거짓(false)) -> 판별식에서 주로 사용(조건식, 반복문)
		
		참고) 
			동일성 비교 : 동일한 주소(===)
			동등성 비교 : 동등한 가치 (==)  => type이 다른건 체크하지 않음
			ex) num1 = 10; num2 = '10'; (num1 == num2) => true / (num1 === num2) => fase 
			
		- ! : 부정 -> 부정연산(NOT) : 참 -> 거짓, 거짓 -> 참
			
		
			
	7) 논리 연산자
		AND 연산 : 교집합과 비슷 
			- &&
			
			true && true -> true
			
		OR 연산 : 합집합과 비슷 
			- || 
			true || true -> true 
			true || false -> true 
			false || true -> true
			
		NOT 연산(부정 연산) 
			- ! : 참 -> 거짓, 거짓 -> 참 
		
		
		연산자의 우선순위 
		비교 연산 > 논리 연산 
		
			비교연산이 논리연산보다 연산 순서 빠르다
			비교연산부터 한 후 논리연산 한다 
			num1 > 10 && num2 <= 100 ->  && 양쪽의 비교부터 한 논리값으로 논리연산 하는 순서.

			
		
		false로 인식하는 값
			- 0, '', undefined, null, NaN
			
		true로 인식하는 값 
			- false 인식하는 값 이외의 값
		
		
		논리 연산의 값 : 최종 연산의 값
		
		
		|| : 기본값
		&& : 간단한 조건식을 대체 

			(단락회로평가)
			var num = 10;
			num++ > 10 && (num = num + 20) > 15; ==> false, num=11  
			
			var num = 10;
			++num > 10 && (num = num + 20) > 15; ==> true, num = 32
			
			 : && 연산일때 왼쪽 false면 오른쪽을 계산도 안하고, 왼쪽이 true면 오른쪽도 계산
			 : || 연산일때 왼쪽 true면 오른쪽을 계산도 안하고, 왼쪽이 false면 오른쪽도 계산
			 
			(단락회로평가 활용 : 변수 초기값에 활용됨 -> 값이 없으면 이걸로 초기화...)
			var num1;
			var num2 = 10;
			var num3 = num1 || num2;
			=> num1 에 값이 없으므로 num3는 num2로 초기화 => num3 = 10;

			(단락회로평가 활용 : if문 대체)
			var str;
			str && alert(str); 
		
	8) 삼항 조건 연산자 
	
	조건식 ? 참일때 : 거짓일때 
	(1항)      (2항)       (3항) 
	
		var num = 10;
		var result = num == 10 ? '같다':'다르다' ;
		==> result 는 "같다"
	
	참고) 
		조건식 : 참 거짓을 판별하는 판별 식 (주로 비교, 논리 연산...)
	
	
	연산자의 우선 순위 
	
	= (대입 연산자) < 논리 < 비교 < (...)
	
	비교연산이 논리연산보다 연산 순서 빠르다
	비교연산부터 한 후 논리연산 한다 
	num1 > 10 && num2 <= 100 ->  && 양쪽의 비교부터 한 논리값으로 논리연산 하는 순서.
	대입연산자 마지막 순서
	

	
5. 제어문

1) 조건문
	if (조건식) { // 논리, 비교 연산자가 주로..
		// 조건식이 참일때 실행되는 코드 
	}

	if (조건식) { 
		// 조건식이 참일때 실행되는 코드 
	} else {
		// 조건식이 거짓일때 실행되는 코드 
	}

	if (조건식1) {
		// 조건식1이 참일때 실행되는 코드
		
	} else if (조건식2) {
		// 조건식1이 거짓이고 조건식2가 참일때 실행되는 코드 
		
	} else if (조건식3) {
		// 조건식1, 조건식2 -> 거짓, 조건식3이 참일때 실행되는 코드 
		
	}
	...

	} else {
		// 모든 조건이 거짓일때 실행되는 코드
	}

6. 선택문
	- 값의 일치 여부 체크, 선택 
	- 키워드 값과 일치하는 시점 -> 실행 시점, break를 만날때까지 실행 
	
	switch(키워드) {
		case 값1 : 
			// 키워드가 값1과 일치하는 경우 실행되는 코드
		case 값2 : 
			// 키워드가 값2와 일치하는 경우 실행되는 코드
		case 값3 : 
			// 키워드가 값3과 일치하는 경우 실행되는 코드..
			...
		default :
			// 모든 키워드가 매칭이 안되면 실행되는 코드 
	}
	
	switch, case, break, default
	
7. 반복문

	1) while 

	while(조건식) {
		// 조건식이 참일때 반복되는 코드 
	}


	2) do~while

	do {
		// 조건식이 참일때 반복되는 코드  
		// 조건이 거짓 이더라도 한번은 실행 된다.
		
	} while(조건식);

	조건식 : 반복을 유지하는 조건 / 반복을 중단 할 수 있는 조건..
	
		
	횟수가 정해진 반복문의 필수 요소
		초기값, 증감식, 조건식 
		
	3) for 
		- 횟수가 정해진 반복문 
		
		for(초기화식; 조건식; 증감식) {
			// 반복 실행되는 코드 
		}
		
		
		관례 
			
			횟수, 순서
			index : 0부터 시작하는 순서 번호
				i \
				
				
				변수명 i, j, k, l, m ... 
			
	break : 반복 중단
	continue : 반복 건너뛰기
	
	
8. ECMAScript 6 부터 추가된 데이터 타입
2015(ES6+, ES6Next) 모던 자바스트립트
1) 통일성 : 표준안 
2) 신기술 : 매년 스펙 추가 

1. 심벌(Symbol)	
1) 심벌의 생성	
2) 심벌과 문자열 연결하기
	
2. 템플릿 리터럴	
	참고) 메타 문자 
		\n : 줄 개행    =>   역따옴표 안에 줄개행 포함시킴
		\t : 탭키 1번 
		\b : 백스페이스 키 1번
		
3. 보간 표현식
	(placeholder)
	
	${변수}
	${연산식}
	${함수호출}
	
	==> console.log( `${i} X  ${j} = ${i*j}`);

객체 

(object) - 사물, 대상

1. 객체 리터럴
- 객체는 이름과 값을 한쌍을 묶은 데이터를 여러개 모은 것
- 객체는 데이터 여러개를 하나로 모은 복합 데이터로 연관배열 또는 사전(Diction) 이라고 부릅니다.
	{
		속성명: 값,
		속성명: 값,
		속성명: 값,
		...
	}
	var person = { name : '이아름', age : 20	}
	
	접근방법) 
	변수명.속성명   => 속성명을 변수로 사용할때는 못쓰는 한계
	변수명['속성명']
	
	var person = { name : '이아름', age : 20	}
	
	peron.name, peron.age, person['name'], peron['age']
	var key = 'name';   
	peron[key]    => 속성명 name을 key라는 변수에 넣어서 사용할 수 있다

2. 객체 리터럴로 객체 생성하기
	데이터
	CRUD ( create, read, update, delete )
	-> 이미 있는 객체에 속성명 대입하면 업데이트됨
	-> 추가 : 없는 속성명에 값을 대입하면 추가됨
	
	person.name = '이수정';  -> { name : '이수정', age : 20	}  
	person.address = '주소';  -> {name: '이수정', age: 20, address: '주소'}
	delete person.address; ->  -> { name : '이수정', age : 20	} 
	값 : 숫자, 문자, 논리값, 객체, null, undefined -> 전부 값으로 사용 가능 
	
3. 프로퍼티 추가와 삭제
4. in 연산자로 프로퍼티가 있는지 확인하기	
	'age' in person;  => true
	
	var person = {
		name : '최혜진',
		age : 20
	}
	for(var key in person) {
		console.log(key, person[key]);
	}
	==> name 최혜진
		age 20
   
   
   
5. 메서드	
   - 객체안에 정의된 함수
		var person = {
			name : '이아름',
			age : 20,
			showInfo : function() {
				console.log('메서드');
			}
		};
	
6. 객체는 참조 타입
	메모리
		데이터영역
		스택영역 - 함수 전용 메모리
		힙영역 - 객체 전용 메모리
		
(3/23)	

함수
- 일련의 처리를 하나로 모아 언제든 호출할 수 있도록 만들어 놓은것
- 기능 
- 함수 = 매서드

1. 함수 선언문으로 함수 정의하기

	function 함수명(매개변수,...) {
		// 실행코드 정의..
		return 반환값;
	}
	
	
	function abc() {
	};
	console.dir(abc);


	function abc(x) {
		y = 2x + y;
		return y;
	}

	x: 매개변수

	function add(num1, num2) {
		var result = num1 + num2;
		return result;
	}
	
	=>
	
	add : 변수 - 함수 객체의 주소값 
	함수도 변수이다. 함수명 add는 함수객체의 주소값
	
	add2 = add;
	add2(20,30); -> 50
	
	console.dir(add);
	
	참고) console.dir(..) -> 객체를 속성과 값형태로 출력
	
	객체간의 상속 ( 이미 만들어진 객체끼리 상속관계로 연결시킴 )
	상속 : 
	  [[Prototype]] :프로토타입 체인 - 상속 관계 링크
					: 모든 객체가 가짐
	                : __proto__ 속성을 통해서 접근
					

	function add(num1, num2) {
		var result = num1 + num2;
		return result;
	}

	==>

	var add = function (num1, num2) {
		var result = num1 + num2;
		return result;
	}
	

2. 함수 호출			
3. 함수의 실행흐름
4. 함수 선언문의 끌어올림

   함수객체(실행X) -> 번역(평가) -> 실행가능객체(EC - Execution Context) -> 실행

	var num0 = 5;   //전역변수

	function outer() {
		var num1 = 10; //지역변수

		function inner() {
			var num2 = 20;
			var result = num0 + num1 + num2;
			return result;
		}
		var resullt = inner();
		console.log(result);
	}
	
	=>
	
	global EC {
		변수레코드 - window 의 하위 속성으로 변수가 정의
		window.num0 = 5;
		외부변수 레코드 참조 : global EC 레코드 주소 : window
		this바인딩 : window객체의 주소값
	}
	outer EC {
		변수 레코드 객체: {
			num1: 10
		}
		외부변수 레코드 참조 : num0 = 5
		
	}
	inner EC {
		변수 레코드 객체 {
			num2: 20,
			result: 35
		}
		외부 변수 레코드 참조:
	}
	외부 변수 레코드 참조: outer EC 변수 레코드 주소
	
	유효범위 체인(Scope)
	
	------------------------------------------------
	const person = {
		name: '최혜진',
		age: 40,
		showInfo: function() {
			console.log(this);
		}
	}

	person.showInfo();
	{name: '최혜진', age: 40, showInfo: ƒ}

	const showInfo = person.showInfo; /***************************/
	showInfo();	
	--------------------------------------------------
5. 값으로서의 함수
	함수(X), 함수객체(O) - 값이 있음, 변수에 대입 가능
	일등 함수 : 변수와 함수를 동등하게 취급한다, 함수 == 값
	
	function outer() {
		console.log('outer 호출');
		inner();
		
		function inner() {
			console.log('inner 호출');
		}
	}
	outer();
	
	
	function outer() {
		function inner() { //지역변수 inner 에 함수객체의 주소 대입
		}
	}
	
	
	함수명는 변수, 함수는 객체. 	
	
	일등 함수 : 변수와 함수를 동등하게 취급한다, 함수 == 값	
	 1) 매개변수로 함수객체를 사용
	 2) 반환값으로 함수객체를 사용(클로저..)
	 -> 함수형 프로그래밍이 가능
	 
	ex) 
	1)
	----------------------------
	function outer(callback) {
		var num = 3;
		callback(num);
	}
	function inner(n) {
		console.log(`숫자 : ${n}`);
	}
	outer(inner);
	----------------------------

	-> 숫자 : 3	
	
	----------------------------
	function outer(callback) {   
		var num = 3;
		callback(num);
	}
	outer (function (n) {
		console.log('숫자 :' , `${n}`);
	});
	
	var fruits = ["apple", "Orange", "Mango"];
	
	fruits.forEach(function(fruit) {
		console.log(fruit);
	});
	
	apple
	Orange
	Mango

	2)
	--------------------------------
	function add(num1) {
		return function (num2) {
			return num1 + num2;
		};
	}

	add(10);
	ƒ (num2) {
			return num1 + num2;
		}
	let add2 = add(10);

	add2
	ƒ (num2) {
			return num1 + num2;
		}
	add2(20);
	30
	--------------------------------
	
6. 참조에 의한 호출과 값에 의한 호출

변수의 유효범위
1. 전역 유효 범위와 지역 유효범위
    -> 함수지역
	-> 유효범위 체인(Scope)
	
2. 변수의 충돌
	같은 이름이면 지역변수 우선함
	
3. 함수 안에서 변수 선언과 변수 끌어올림( hoisting 호이스팅)

4. 함수 안에서 변수 선언 생략
5. 블록 유효 범위 : let과 const

	var: 함수 지역이 유효범위
	let, const -> { ... } 중괄호 안에서만 유효함
	let : 변수 - 값 변경이 가능
	const: 상수 - 값변경 불가능
	
	-> 변수는 기본적으로 const로 정의, 변경이 필요한 경우만 let으로 사용
	
	
6. 함수 리터럴로 함수 정의하기
	const 변수명 = 함수객체;
	
	console.log(add);
	//add(100, 200);     // 여기선 에러남.. 
	
	var add = function(num1, num2) {
		var result = num1 + num2;
		return result;
	};
	add(100, 200);  // 요렇게 써야
	
	this 바인딍 : 호출한 객체의 주소값
	∴ this : 호출할때 결정됨 
	
	
7. 객체의 메서드
8. 즉시 실행 함수
	- 함수 정의와 동시에 호출
	
	(function(매개변수정의, ...) {
	})(인자...);
	
	let result = (function(num1,num2) {
		const ret = num1 + num2;
		return ret;
	})(10,20);
	

9. 가변길이 인수 목록(Arguments 객체)
	arguments - 인자: 매개변수로 사용된 값
	parameter - 인수
	
	전개 연산자, 나머지 연산자: ...
	
	function sum(...params) {
		console.log(params);
	}

	function func1(a,b) {
		b = b || 10;     //  <- 기본값 세팅 방법으로 사용:과거방식
		console.log('a', a, 'b', b);
	}
	=>
	function add(a,b = 10) {  // 기본값 세팅 방법2 : 오즘방식
    console.log('a', a, 'b', b);
}

생성자	

함수객체 -> 다른 객체 생산

new 연산자: 메모리에 공간을 생성하는 연산자

생성자 함수명
  첫 시작 문자 대문자

객체가 생성되는 과정
1. 함수가 객체에 정의된 prototype 객체의 상속
2. this값을 상속받은 객체로 변경함으로써 초기화


function Person () {
    this.name = '최혜진';
    this.age = 40;
}
const p1 = new Person();

참고) 객체간의 상속
	프로토타입 체인 연결
	[[Prototype]]: 프로토타입 체인

	__proto__ 

	const objA = { a: 10, b: 20};
	const objB = { c: 30, d: 40};
	objA.__proto__ = objB; //  <-  


	---------------------------
	function Person () {
		this.name = '최혜진';
		this.age = 40;
	}
	---------------------------
	const p1 = new Person(); 

	<-  상속하면서 생성하는 방법

	const p1 = {};
	p1.__proto__ = Person.prototype;
	Person.call(p1);  <- 초기화
	
	<-   생성하여 상속연결하고 call로 this의 값을 바꿔줌
	
	다시
	-------------------------------------
	function Person () {
		this.name = '최혜진';
		this.age = 40;
	}

	const p1 = {};
	p1.__proto__ = Person.prototype;
	p1;
	 => Person {}
	Person.prototype.constructor.call(p1);  // ∵ Person.prototype.constructor === Person 초기화
	p1;
	 => Person {name: '최혜진', age: 40}

	-------------------------------------
	function Person(name, age) {
	this.name = name;
	this.age = age;
	}
	const p1 = new Person("최혜진", 20);
	const p2 = new Person("최혜진2", 40);
	p1;
	=>Person {name: '최혜진', age: 20}
	p2;
	=>Person {name: '최혜진2', age: 40}
	p1.showInfo();
	=> 최혜진 20
	p2.showInfo();
	=> 최혜진2 40



모든함수
	function Function() {
	}
	
	모든함수는 apply, call
	
var num1 = 10;

function outer() {
    
    var num2 = 20;
    
    function inner() {
        
        var num3 = 30;
        return num1 + num2 + num3;
    };
    var result = inner();
    console.log(result);
};

	
	this 바인딩 - this 지역변수 값 결정
	
5. 값으로서의 함수
	함수(X), 함수 객체(O) - 값이 있음, 변수에 대입 가능
	
	일등 함수 : 변수와 함수를 동등하게 취급, 함수 == 값
		1) 매개변수로 함수 객체를 사용 
		2) 반환값으로 함수 객체를 사용(클로저...)
		
		-> 함수형 프로그래밍이 가능

6. 참조에 의한 호출과 값에 의한 호출

변수의 유효범위
1. 전역 유효 범위와 지역 유효범위
	-> 함수 지역 
	-> 유효범위 체인(Scope)

2. 변수의 충돌
3. 함수 안에서 변수 선언과 변수 끌어올림
4. 함수 안에서 변수 선언 생략
5. 블록 유효 범위 : let과 const
	
	var : 함수 지역이 유효범위 
	
	let, const -> { .... }
	
	let : 변수 - 값 변경이 가능 
	const: 상수 - 값 변경이 불가 
	
	
	-> 변수는 기본적으로 const로 정의, 변경이 필요한 경우만 let으로 사용

6. 함수 리터럴로 함수 정의하기
	const 변수명 = 함수 객체;

7. 객체의 메서드
8. 즉시 실행 함수
9. 가변길이 인수 목록(Arguments 객체)


parameter - 인자(매개변수)
argument - 인수(매개변수에 대입된 값

생성자

객체=변수+함수

prototype 

new 연산자로 객체 만드는 것은 다음 과정과 동일하다

1) 함수 객체의 prototype 객체의 상속 (상속은 자원을 공유하려는 목적)
2) this가 prototype객체를 상속받은 객체로 주소변경 -> 초기화

function Person() {
    this.name = "최지예";
    this.age = 25;
};
console.dir(Person);
const p1 = {};
p1.__proto__ = Person.prototype;
Person.apply(p1);  == Person.prototype.constructor.apply(p1);   -> 초기화
p1;
Person {name: '최지예', age: 25}


함수내의 매서드 정의는 prototype에 정의하는게 상속 시 같은 기능을 중복되게 자원관리하지 않도록할 수 있다.
상속을 총해 공유하게 하기 위함.

예1) 바람직 하지 않은 함수내의 매서드 정의
function Person(name, age) {
    this.name = name;
    this.age = age;
	this.showInfo = function () {
		console.log(this.name, this.age);
	}
};

const p1 = new Person('김이름', 30);
const p2 = new Person('이이름', 50);
p1.showInfo === p2.showInfo;
  => false

예2) 상속을 위한 올바른 메서드 정의
function Person(name, age) {
    this.name = name;
    this.age = age;
};
Person.prototype.showInfo = function() {
    console.log(this.name, this.age);
};

const p1 = new Person('김이름', 30);
const p2 = new Person('이이름', 50);
p1.showInfo === p2.showInfo;
  => true

===== 새로운 구문 ========================
class Person {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }
    showInfo(){
        console.log(this.name, this.age);
    }
}

class Person {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }
    static count  = 10;
    static staticMethod() {
        console.log('정적 메소드..');
    }
    showInfo(){
        console.log(this.name, this.age);
    }
}
const p1 = new Person('이이름', 40);
console.dir(p1.showInfo);  ==> prototype 이 없으므로 ..생성자로 사용은 안된다(상속은 안된다)


===================================
function Elipse(a, b) {
    this.a = a;
    this.b = b;
}
Elipse.prototype.area = function() {
    console.log('면적: ' + this.a * this.b * Math.PI);
}
function Circle(r) {
    this.a = r;
    this.b = r;
}
Circle.prototype.__proto__ = Elipse.prototype;
Circle.prototype.constructor = Circle;

const c1 = new Circle(10);
c1.area();


===================================
class Person {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }
    showInfo(){
        console.log(this.name, this.age);
    }
}

======>
요렇게 쓰자 간단히 

class Elipse {
    constructor(a, b) {
        this.a = a;
        this.b = b;
    }
    area() {
        console.log('면적 :' ,  this.a * this.b * Math.PI);
    }
}
class Circle extends Elipse {
    constructor(r) {
        super(r, r);
    }
}

const c1 = new Circle(10);
c1.area();

---------------------------
이렇게 함수를 정의하기도 한다 

const add = (a,b) => a + b;
add instanceof Function;
-> true
console.log(dir(add));
typeof add;
-> 'function'
console.dir(add);
---------------------------


생성자 함수 객체의 상속


new 생성자 함수(); 
	1) prototype 객체의 상속 ( prototype에 정의된 자원 공유 )
	2) this값 -> 현재 생성될 객체의 주소 -> 생성자함수 호출 -> 초기화

 연산자
	instanceof : 객체의 출처를 체크(프로토타입 체인)
		function Person() {
		this.name = '최지수';
		this.age = 40;
	}
	const p1 = new Person();
	p1 instanceof Person;  <- p1은 Person에서 생성된 객체인지 확인
	-> true
	Person instanceof Function ;
	-> true
	p1 instanceof Object;
	-> true
	p1 instanceof Array;
	-> false	
	

자바스크립트객체

코어객체
	- 내장 생성자 함수 객체
	
	- 내장객체
		Math - 수학관련 편의 메서드가 있는 객체
		JSON 
	
호스트객체 : 실행환경에 따른 객체
	- 웹브라우저 객체 - 웹브라우저의 기능과 관련된 객제
	   window
	     location 객체 - 브라우저의 주소와 관연된 기능, 정보
		 history 객체 - 브라우저의 방문기록과 기능
		 screen 객체
		 navigator 객체
		 
		 document 객체 - 웹문서를 다루는 객체
		 

function Person (){
    this.name = '이이름';
    this.age = 20;
}
Person.prototype.showInfo = function () {
    console.log("이름" , this.name, "나이", this.age);
}
person.method = function () {
    console.log("기능!");
}
const p1 = new Person();
p1.showInfo();
p.method();
p1;
Person;
console.dir(Function);
console.dir(Person);
Person.method;
console.dir(Person.method);
VM1045:1 undefined
undefined
typeof Person;
'function'
Person instanceof Function;
true
---------------------------
class Person {
    constructor (name, age) {
        this.name = '이이름';
        this.age = 40;
    }
    showInfo() {
        console.log("이름", this.name);
    }
    static method () {
        console.log("기능!");
    }
};
console.dir(Person);
undefined
const p1 = new Person();
p1;
p1.showInfo();
p1.method();
---------------------------

class Elipse {
    constructor(a,b) {
        this.a = a;
        this.b = b;
    }
    area() {
        console.log("면적:", this.a * this.b * Mat.PI);
    }
}
class Circle extends Elipse {
    constructor(r) {
        super(r,r);
    }
}

undefined
const c1 = new Circle();
------------------------------
function Elipse(a, b) {
    this.a = a;
    this.b = b;
}
Elipse.prototype.area = function() {
    console.log("면적", this.a * this.b * Math.PI);
}
    

ƒ () {
    console.log("면적", this.a * this.b * Math.PI);
}
function Circle(r) {
    Elipse.call(this, r, r);
}
Circle.prototype.__proto__ = Elipse.prototype;
Circle.prototype.constructor = Circle;
------------------------------


코어객체
	내장 생성자객체
	
	내장객체

호스트객체
	웹브라우저객체
	window
		location
		history
		screen
		navigator
		document
		...
		

내장 생성자
- 자바스크립트에 처음부터 포함된 내장 생성자
1. Object
	Object.prototype
	const 변수명 = new Object();
	=>
	{..};  // 짧게 사용가능
	
	prototype	
		.toLocaleString() 
		
			Locale : 지역화
			- 지역화에 맞게 변경 -> 출력
			
		Object.getOwnPropertyDescriptor()
		Object.getOwnPropertyDescriptors()
	
		데이터 프로퍼티
			value - 값
			writable : false면 값변경 불가
			enumerable : false면 열거 불가
			configurable : false면 설정변경 불가, 삭제 불가
						 false 라도 예외..
						 writable true-> 단 한번만 fasle로 변경 가능
						 
			-> 변경
			Object.defineProperty
			Object.defineProperties
			
			Object.preventExtensions()

			const person = {
				name: "이믈",
				age : 40
			};
			for(const key in person) {
				console.log(key);
			}
			Object.preventExtensions(person);
			person.address = '주소';
			person;
			
			
			Object.seal() : 객체를 밀봉 (삭제불가, 속성추가 불가, 값변경 가능)
			Object.freeze() : 객체를 동결 (삭제불가, 속성추가 불가, 값변경 불가)
			
			참고) 속성: 통제 기능 포함
		
		접근자 프로퍼티
			- 함수형태: 단축 표현법
			- set 함수명 - 값을 변경하는 접근자 속성
			- get 함수명 - 값을 조회하는 접근자 속성
			- enumerable
			- configurable


			const schedule = {
				_year : 2024,
				_month : 3,
				_day : 28,

				set year(year) {
					this._year = year;
				},
				get year() {
					return this._year;
				},
				set month(month) {
					this._month = month;
				},
				get month() {
					return this._month;
				},
				set day(day) {
					if(this._month == 2 && day > 28) {
						day = 28;
					}
					this._day = day;
				},
				get day() {
					return this._day;
				}
			}
			
			schedule.year;
			2024
			schedule.year = 2023;
			2023
			schedule.year;
			2023
			schedule.month = 2;
			2
			schedule.day = 31;
			31
			schedule;
			{_year: 2024, _month: 2, _day: 28, …}	


			const schedule = ( function(){
				let _year = 2024;
				let _month = 3;
				let _day = 28;

				return {
					set year(year) {
						_year = year;
					},
					get year() {
						return _year;
					},
					set month(month) {
						_month = month;
					},
					get month() {
						return _month;
					},
					set day(day) {
						_day = day;
					},
					get day() {
						return _day;
					},
				}
			})();
			----------------------------------------
			schedule;
			{}
			schedule.year;
			2024
			schedule.year=2023;
			2023
			schedule.year;
			2023			
			
			const person = {
				name:'이이름',
				age: 40
			}
			const person2 = person; //얕은 복사, 주소복사
			person === person2;
			true
			const person3 = Object.assign({}, person); //깊은복사
			person === person3;
			false	

			----------------------------------------
			전개연산자... : 깊은복사 기능
			const person = {
				name:'이이름',
				age: 40
			}
			const person2 = { ...person };
			person === person2;
			false			
			----------------------------------------
			const person = {
				name:'이이름',
				age: 40
			}
			const person2 = { ...person };
			person === person2;
			false
			const person2 = { ...person, age: 30 };
			person2;
			{name: '이이름', age: 30}

		(참고)
			entries : 이름-값 쌍 조회
			Map.Entry : 이름-값의 쌍
			
			const person = {
				name:'이름',
				age: 40
			}
			for(const entry of Object.entries(person)) {
				console.log(entry);
			}
		
			Object.keys(person);
			Object.values(person);
			
			
			
원시타입, 원시값 - 재료가 되는 값

숫자
문자
논리값 
undefined
null
...
		
2. String 
	wrapper 생성자 객체
		const str = 'abc';   <= const str = new Srting('abc');
		str.toUpperCase();   <= String 생성해서 바꾸고 다시 원시 변환함  
	
	indexOf(..)  : 문자열의 ..가 있는 첫번째 위치번호  a.indexOf('little') 
	lastIndexOf(..) : 문자열의 ..가 있는 첫번째 위치번호(우->좌 방향으로 찾기)
	-> 문자열 찾지 못하면 -1 반환
	
	replace : 
		const c = 'abcAbc345';
		const d = c.replace(/b/g, 'BB');
		-> 'aBBcABBc345'
	concat
	trim
	
3. Number 
	wrapper 생성자 객체
	NaN : Not a Number - 숫자가 아니다
		isNaN(...): 숫자형식이 아니면 true (데이터타입의 문제 아님) 
	isNaN(123);
	false  -> 숫자다
	isNaN('123');  
	false  -> 숫자다  
	isNaN('abcd');
	true  -> 숫자아니다
	Number("cdscds")
	NaN	  -> 숫자아니다
	
	const num1 = '123';
	const num2 = Number(num1); 문자열을 숫자로 변환 하는 과정에 isNaN 사용
	
	typeof num1 === 'number' : 숫자 판별 가능
	
	const num1 = '123';
	typeof num1;
	'number'
	
	parseInt(문자열|실수) => 정수로변환
	parseFloat(문자열) => 실수로 변환
	int : 정수 
	float : 실수
	
	const num1 = '123';
	const num2 = parseInt(num1);   //parseInt는 window 전역내장함수
	num2;
	123
	
	parseInt(num1) === Number.parseInt(num1);
	
	parse -> 형변환(변경)
	
	const num1 = 10.12345;
	const num2 = num1.toFixed(2);
	num2;
	'10.12'	
	
	
4. Boolean 
	0, -0, null, false, NaN, undefined, 빈 문자열 ("")이라면 객체의 초기값은 false

	const num1 = 0;
	typeof num1;
	'number'
	const bool1 = Boolean(num1);
	bool1;
	false
	Boolean(1);
	true	

5. Array
	const 변수명 = new Array(...) ;
	=>
	[...];  // 짧게 사용가능
	=>
	Array.prototype
	
	- 배열X, 배열객체O
	  (참고)배열이란 같은 자료형이 연속해서 나열된 형태
	
6. Date
	-날짜와 시간
	const date = new Date(); //객체 생성시점의 날짜와 시간
	undefined
	date;
	Fri Mar 29 2024 14:36:08 GMT+0900 (한국 표준시)	
	
	date.getUTCDate();
	29
	date.getFullYear()
	2024
	date.getMonth()
	2
	date.getDate()
	29
	date.getDay()
	5
	date.getHours()
	14
	date.getMinutes()
	36
	date.getSeconds()
	8	
	
	const strDate = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
	strDate
	'2024-3-29'
	
	const strTime = `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
	strTime;
	'14:36:8'	
	
		- getFullYear() / setFullYear()
		- getMonth() // 0~11월  /setMonth()
	
	date.setFullYear(2023);
	1680068168711
	date;
	Wed Mar 29 2023 14:36:08 GMT+0900 (한국 표준시)	
	
	date.setDate(date.getDate() - 7); //7일전
	1679463368711
	date;
	Wed Mar 22 2023 14:36:08 GMT+0900 (한국 표준시)	
	
	const date = new Date();
	date.getTime(); // 1970.1.1 자정부터 1000분의 1초 단위로 카운트한 숫자 -> Epoch Time -> 초단위 -> Timestamp
	                -> 사용예) 유일한 아이디 만들때 UID 등으로
	const date = new Date();
	date.getTime();  //현재시간의 epoch time
	1711691687687

	Date.now();
	1711691905071
	
	- Date.parse(문자열 날짜) : 문자열 날짜 -> Date 객체로 변환
	
	const d3 = Date("3 29 2024");
	d3;
	'Fri Mar 29 2024 15:21:06 GMT+0900 (한국 표준시)'

	const strDate = '2021-11-01T11:59:30';
	const d1 = Date.parse(strDate);
	d1;
	1635735570000
	const d2 = new Date(d1);
	d2;
	Mon Nov 01 2021 11:59:30 GMT+0900 (한국 표준시)	  

	생성자 Date
	
	const d1 = new Date(2021, 2, 29);
	d1;
	Mon Mar 29 2021 00:00:00 GMT+0900 (한국 표준시)
	
	
	- new Date(year, monthIndex, day, hours, minutes, seconds, milliseconds);
	
	달력
	1) 이번달이 몇일로 끝나는지
	2) 이번달 1일 무슨 요일인지

		-> 다음달 1일에서 - 1일
		-> getDay()
	
7. Function

	apply
	call
	bind

	function add(num1, num2) {
		return num1 + num2;
	}
	const add10 = add.bind(this, 10);
	add10(20);
	30
	
	const person = {
		name : 'hjchoi',
		age: 40
	}
	function showInfo() {
		console.log(this.name, this.age);
	}
	showInfo = showInfo.bind(person);

	showInfo();
	
	hjchoi 40	

8. RegExp - 나중에
9. Error 
	try {
	
	} catch(e) {
	
	}


ES6부터 추가된 주요 내장 생성자   ****
1. Symbol
2. Promise  : 리액트할때

콜렉션 프레임워크 - 자바할때
3. Map
4. Set

기타 내장 객체
JSON (Javascript Object Notation) - 자바스크립트 객체 표기법
 {
	"이름": "값",
	"이름": "값",
	...
 }
 
 .parse(...) 
   - JSON 문자열 -> 자바스크립트 객체로 변환
 .stringify(...) : 자바스크립트 객체 -> JSON 문자열로 변환
 
	const person = {
		name : 'hjchoi',
		age: 40
	}
	JSON.stringify(person);
	'{"name":"hjchoi","age":40}'
	const str = JSON.stringify(person);
	str;
	'{"name":"hjchoi","age":40}'
	JSON.parse(str);
	{name: 'hjchoi', age: 40} 
   
Math 
	- 수학관련 편의함수, 상수 정의되어 있음 
	- (PI 원주율) 
	- round(..)   Math.round(10.5)
	- floor(..)버림
	- ceil(..) 올림
	- abs(..) 절대값
	- sqrt(..)

전역 객체
1. 전역 프로퍼티
	- undefined, NaN, Infinity

2. 생성자 
	Object(), String(), Number() 등

3. 전역 함수
	parseInt(), parseFloat(), isNaN() 등 

4. 내장 객체
	Math, JSON, Reflect


자바 스크립트 객체의 분류


배열
1. 배열의 기초
- Array 생성자로 만들어진 객체 
- 데이터군
- 여러데이터
참고) 배열 아니고 배열처럼 생긴 Array객체 
	<- 같은 자료형의 순차적 나열구조 가 아니므로
	
		const fruits = new Array("Apple", "Orange", "Melon");
		fruits;

1) 배열 리터럴로 생성하기
const 배열명 = [....]; // new Array(...)

		const fruits2 = ["Apple", "Orange", "Melon"];
		fruits2;


2) length 프로퍼티
	- 배열공간의 갯수

		const fruits2 = ["Apple", "Orange", "Melon"];
		fruits2.length;
		3	
	참고) 객체의 속성명이 변수명 규칙과 어긋나면 [ ]이용하여 순서로 지정 
		fruits2[0], fruits2[1], fruits2[3]  
	
	fruits2[3] = 'manggo';
	delete fruits2[0];
	true
	fruits2
	(4) [비어 있음, 'Orange', 'Melon', 'Manggo']	
	

3) Array 생성자로 생성하기
const 배열명 = new Array(3); 
- 매개변수가 1개일때는 배열 공간의 갯수
- 양의 정수 숫자 가능 
- 매개변수가 여러개 일때는 갯수만큼 공간이 생성, 값이 순서대로 추가 

4) 배열 요소의 참조
5) 배열은 객체
6) 배열 요소의 추가와 삭제
	- 추가 
		push(...items) : 끝에 추가 
		unshift(...items) : 앞에 추가 
		
	- 삭제
		pop() : 가장 끝에 요소 꺼내기 
		shift() : 가장 앞에 요소를 꺼내기
		
	- 중간 추가, 삭제, 변경
		splice(start, deleteCount, ...items)  
			=> start 에서부터 deleteCount만큼 제거하고 items 들을 그자리에 추가


	const fruits2 = ["Apple", "Orange", "Melon"];
	fruits2
	(3) ['Apple', 'Orange', 'Melon']
	ftruits2.push("Mango", "Aaa");
	fruits2.push("Mango", "Aaa");
	5
	const removed = fruits2.pop(); //꺼내옴
	removed;
	'Aaa'	

(4/1)
	
7) 배열 요소가 있는지 확인하기

	in
	Array.prototype.find : 첫번째 발견요소
	Array.prototype.findIndex : 첫번째 발견요소의 인덱스
	Array.prototype.findIndex : 끝에서부터 찾아서 첫번째 발견요소의 인덱스
	Array.prototype.includes(...) :요소가 있는지 
	Array.prototype.indexOf(...) :요소의 위치, 못찾으면 -1
	Array.prototype.lastIndexOf(...) :끝에서 부터 찾는 요소의 위치, 못찾으면 -1
	
	화살표함수
		1. function 키워드 생략 가능
		2. 매개변수와 구현체 사이에 =>
		3. 구련내용이 한줄이면 {} 생략가능. return 생략가능
		4. 매개변수가 1개면 (..) 생략 가능, 단 매개변수 없으면 생략 불가
		
		- 생성자 함수로 역할은 못함
		- arguments지역변수 없다
		- this 범위가 함수를 정의할때 이미 정의된 this가 this가 된다.
		
		---------------------------------------------------
		const fruits = ["Apple", "Orange", "Mango", "Melon"];
		fruits.find( function(fruit) {
			fruit === "Melon";
		});
		fruits.find( function(fruit) {
			return fruit === "Melon";
		});
		'Melon'
		fruits.find((x) => x === "Melon");
		'Melon'		
		---------------------------------------------------

		const person = {
			name: 'hjchoi',
			age : 40,
			showInfo: () => {
				console.log(this); // window
			}
		};

		const person = {
			name: 'hjchoi',
			age : 40,
			showInfo: function() {
				const printInfo = () => console.log(this); //person
			}
		};



2. 배열의 메서드
	concat : 병합, 새로운 Array 반환
	forEach
	
	const fruits = ["Apple", "Orange", "Mango", "Melon"];
	fruits.find( function(fruit) {
		fruit === "Melon";
	});
	fruits.find( function(fruit) {
		return fruit === "Melon";
	});
	'Melon'
	fruits.find( (x) => x === "Melon");
	'Melon'
	fruits;
	(4) ['Apple', 'Orange', 'Mango', 'Melon']
	const f2 = fruits.concat(["Banana", "Pear"]);
	f2;
	(6) ['Apple', 'Orange', 'Mango', 'Melon', 'Banana', 'Pear']	

	판별
	filter(callbackFn) : callbackFn 이 참으로 반환되는 요소만
	every(callbackFn) : 모든요소가 참일때만 참
	some(callbackFn) : 어떤요소든 참일때는 참
	
	반복: 단순반복
	forEach(callbackFn)
	
	변경	
	map(callbackFn)
	flatMap(callbackFn) :중첩된 배열 객체 -> 1차원적인 배열 객체 반환
	
	배열 -> 문자열
	join(
	참고) String.prototype.split('구분문자열') -> 문자열 쪼개서 Array로 변환
	
	
	const nums = [1,2,3,4,5,6,7,8,9,10];
	//반복, 횟수 - 요소의 갯수, nums.length 만큼 - for
	nums.filter(x => x % 2 == 0);
	
	const nums = [1,3,5,7,9];
	const result = nums.every(x => x % 2 == 1);
	result;
	true
	const nums2 = [1,3,5,7,9, 10];
	const result = nums2.every(x => x % 2 == 1);
	result;
	false	

	----------------------------------------------------
	const fruits = ["Apple", "Orange", "Mango", "Melon"];
	fruits.forEach((el, i) => {
		console.log("el: ", el, "index:", i);
	});
	el:  Apple index: 0
	el:  Orange index: 1
	el:  Mango index: 2
	el:  Melon index: 3

	fruits.forEach( (el, i, arr) => { // 세번째 매개변수 arr은 주로 원본데이터 객체의 주소 -> 원본 변경하기위해
		console.log("el: ", el, "index:", i);
		arr[i] = arr[i].toUpperCase();
	});
	el:  Apple index: 0
	el:  Orange index: 1
	el:  Mango index: 2
	el:  Melon index: 3
	
	fruits
	['APPLE', 'ORANGE', 'MANGO', 'MELON']
	
	const nums = [1,2,3,4,5,6,7,8,9,10];
	const nums2 = nums.map(x => x * x);
	nums2;
	 [1, 4, 9, 16, 25, 36, 49, 64, 81, 100]	

3. 다차원 배열
	[...]: 1차원배열
	[[...],[...],[...]] 2차원배열
	
	const nums = [[1,2,3], [4,5,6], [7,8,9]];
	const nums2 = nums.flatMap(x => x); 
	nums2;
	[1, 2, 3, 4, 5, 6, 7, 8, 9]
	const nums3 = nums.flatMap(x => x).map(x => x * x);
	nums3;
	[1, 4, 9, 16, 25, 36, 49, 64, 81]
	
	const fruits = ["Apple", "Orange", "Mango", "Melon"];
	const str = fruits.join();
	str;
	'Apple,Orange,Mango,Melon'
	const str2 = fruits.join("#");
	str2;
	'Apple#Orange#Mango#Melon'
	const f3 = str2.split('#');
	f3;
	(4) ['Apple', 'Orange', 'Mango', 'Melon']	
	
	const nums = [...(new Array(101)).keys()];
	nums;
	
	const nums = [1,2,3,4,5,6,7,8,9,10];
	nums.reverse();
	const nums2 = nums.slice(0);  //깊은복사와 같이 작동함
	const nums3 = nums.slice(1,3);
	
	const nums = [1,2,3,40,5,60,7,8,9,10];
	nums.sort((a,b) => a - b);  //원본 데이터 바꿈()
	nums.sort((a,b) => b - a)()
	
	
	reduce() 

	누산기 (acc)
	현재 값 (cur)
	현재 인덱스 (idx)
	원본 배열 (src)
	----------------------------------------------
	const nums = [1,2,3,4,5];
	const tot = nums.reduce((acc, cur) => {
		console.log("acc:",  acc, "cur:", cur);
		acc += cur;
		return acc;
	});
	acc: 1 cur: 2
	acc: 3 cur: 3
	acc: 6 cur: 4
	acc: 10 cur: 5

	reduce()로 최대값구하기
	----------------------------------------------
	const num2 = [99, 100, 3, 5, 21];
	const max = num2.reduce((a, b) => a > b ? a : b);
	max -> 100	
	
	Array.isArray(nums);
	true
	typeof nums;
	'object'
	nums instanceof Array;
	true	
	
	Symbol.iterator 가 정의된 경우 ( iterator:반복자 )
		반복자패턴 구현 -> 디자인패턴
		커서 : 이동위치
		-> 커서 이동하면서 다음 요소 접근
		next() : 커서이동 다음 요소
		for .. of 구문  <- Symbol.iterator가 구현된 객체면 사용가능

		const fruits = ["Apple", "Orange", "Mango", "Melon"];
		const iter = fruits[Symbol.iterator]();
		iter.next() ...
		iter.next()
		iter.next()
		...  => done: false, done: true 
		
		const fruits = ["Apple", "Orange", "Mango", "Melon"];
		for(const fruit of fruits) {
			console.log(fruit);
		}
		
		Apple
		Orange
		Mango
		Melon		
		
		const str = '1234567'; // String도 @@
		for(const num of str) {
			console.log(num);
		}



4. 유사배열
	 상속관계가(프로토타입 체인)이 Array.prototype이 아닌 형태 
	 for .. of 구문 사용가능
	 
5. Array.prototype의 메서드를 유사 배열 객체에서 사용하기
	 
	 
ECMAScript6+에 추가된 기능
1. 비구조화 할당

	const person = {
		name : 'hjchoi',
		age : 40
	};
	const { name: name, age: age } = person;
	name
	'hjchoi'
	age
	40
	const {name, age} = person;
	name
	'hjchoi'
	age
	40
	
	const person = {
		name : 'hjchoi',
		age : 40,
		addr : { zip: '123', addr1: '주소1', addr2: '주소2'}
	};
	const { name, age, addr : {zip, addr1, addr2}} = person;
	name
	'hjchoi'
	zip
	'123'
	addr2
	'주소2'	
	
	

1) 배열의 비구조화 할당
- 기본적인 사용법
const|let [a, b] = [1, 2];
	a = 1
	b = 2
- 이미 선언된 변수를 비구조화 할당하는 예

	let a = 10, b = 20;
	[b, a] = [a,b];
	[10, 20]
	a
	b
	

- 나머지 요소
	... 

	const nums = [1,2,3,4,5,6,7,8,9,10];
	const [c, d, ...rest] = nums;
	rest
	[3, 4, 5, 6, 7, 8, 9, 10]	
	
	참고)
		이터레이터, 제너레이터 -> 값을 전개
		
- 요소의 기본값
2) 객체의 비구조화 할당
- 기본적인 사용법
- 프로터피의 기본값
- 프로퍼티 이름 생략하기
3) 반복 가능한 객체의 비구조화 할당
- 이터레이터, 제너레이터 
4) 중첩된 자료 구조의 비구조화 할당


2. 전개 연산자

	const nums = [10, 20];
	function add(num1, num2) {
		return num1 + num2;
	}
	add(...nums);
	30
	----------------------	
	const [a,b, c = 30] = [10, 20];
	a
	10
	b
	20
	c
	30	
	----------------------	
	const person = {
		name : 'hjchoi',
		age : 40
	};
	for(data of Object.entries(person)) {
		console.log(data);
	}
	['name', 'hjchoi']
	['age', 40]
	
	for([key, value] of Object.entries(person)) {
		console.log(key,value);
	}
	name hjchoi
	age 40
	
	
코어객체
	- 내장생성자 객체
		Array
		String..
		Object
		
	- 내장객체
		Math,
		JSON

호스트객체 - 웹브라우저 객체
window
	location: URL과 관련된 객체
	history: 방문기록과 관련된 객체
	screen: 화면 정보, 
	navigator:브라우저 환경정보
	
	Document 
	
	REPL(명령행): Read Evaluate Print Loop
	
웹브라우저객체

1. 클라이언트측 자바스크립트

	웹브라우저에서 자바스크립트가 하는 일
	웹브라우저에서 자바스크립트 실행 순서

		서버가 html 응답데이터(문자열)  -> 브라우저 
		-> document 객체로 변환 -> DOMContentLoaded 이벤트 발생 -> document 객체 변환작업 완료
		-> 트리구조 재구성(이진트리 - 검색에 최적화하려고)
		-> DOMTree

	async와 defer 속성
		defer : 외부링크 연결하는 방식 <script defer src=".."></script> 에서
		- src 내의 실행코드는 DomcontentLoaded 이후에 실행된다

		<script defer src="scripts.js"></script>
		
		 scripts.js 에 
			window.addEventListener("DOMContentLoaded", function() {
				.....
			});
			이걸로 감쌀 필요없다.	

2. CSS와 렌더링
3. 웹 브라우저 렌더링 엔진과 자바스크립트 엔진


Window 객체
1. Window 객체의 주요 프로퍼티
	pageXOffset :가로방향 스크롤정도 
	pageYOffset :세로방향 스크롤정도
	
	innerWidth : 스크롤바를 제외한 현재 창의 보이는 영역
	innerHeight
	
	outerWidth: : 스크롤바 등 모두를 포함한 현재 창의 보이는 영역
	outerHeight:
	
	console.log("값")
		.dir(..) 객체를 이름과 값형태로 보여줌
		.error("값")
		conole.trace()에 도달할때까지 위치를 쌓아가듯이 (stack)보여주는 기능
		
		open(주소, 창이름,옵션) -> 팝업참
		: opner - open을 호출해서 열어준 창
		: 옵션 - width, height, scrolling, location
			
2. Window 객체의 주요 메서드
	alert("메세지") - 알림메세지
	const str = prompt("메세지") - 입력칸..
	confirm("메세지") - 확인(true), 취소(false)

	print()

Location 객체
	- 주소에 
1. Location 객체의 프로퍼티
2. Location 객체의 메서드
	- 주소에 대한 통제 기능
	  assign(주소): 주소이동 - 방문기록 남는 주소이동
	   = location.href="주소"
	  
	  replace(주소): 주소이동 - 방문기록 남지 않음 ( history 안쌓이므로 뒤로가기 관련 이용가치있음)  /*******4/1 3:35**/
	  reload() :새로고침
	  시크릿모드(ctrl+shift+n)  
	  
	  
History 객체
1. History 객체의 프로퍼티
	length : 방문기록 개수
	scrollRestoration : auto - 페이지스크롤 위치 복구, manual - 페이지스크로 위치 복구X, 문서 상단 위치
	
2. History 객체의 메서드
	back() :뒤로 한단계 이동
	forward() :앞으로 한단계 이동
	go(-2): 뒤로 2단계 이동

Naviator 객체
1. Navigator 객체의 주요 프로퍼티
	userAgent
2. Navigator 객체의 메서드

Screen 객체
1. Screen 객체의 주요 프로퍼티

창 제어하기
1. 창 여닫기
open

2. open 메서드로 설정할 수 있는 옵션의 이름
width, height, location, scrollbars, toolbar, status, menubar

3. 창 제어하기
1) moveBy(..), moveTo(...)
2) resizeBy(..), resizeTo(...)
3) scrollBy(..), scrollTo(...)

4. 다른 창 참조하기
	- 창의 이름을 가지고 참조 
	
Document 객체
- Document 객체는 창에 표시되고 있는 웹 페이지를 관리합니다.
- 이 객체로 웹 페이지의 내용물인 DOM 트리를 읽거나 쓸 수 있습니다.
- Document 객체는 클라이언트 측 자바스크립트에서 가장 중요한 객체입니다.

1. Document 객체의 주요 프로퍼티
2. Document 객체 주요 메서드
	선택을 위한 메서드
	1)아이디로 선택: document.getElementById("아이디") - 단수개 선택
	2)클래스명으로 선택: document.getElementsByClassName("클래스명") - 복수개 선택
	3)태그명으로 선택: document.getElementsByTagName("태그명") - 복수개 선택
	4)n4ame속성으로 선택: document.getElementsByName(name속성) - 복수개 선택
	
	5)CSS 선택자 형식으로 선택
	: document.querySelector("CSS 선택자 형식"); - 단수개 선택 첫번째 것만 
	: document.querySelectorAll("CSS 선택자 형식"); - 복수개 선택 
	
	6)children: 자식요소들
	7)parentElement : 부모요소
	8)firstElementChild : 첫번째 자식요소
	9)lastElementChild : 마지막 자식요소

	참고) jQuery : 요즘 잘 안씀
	
	10)previousElementSibling  앞쪽 형제요소
	11)nextElementSibling  : 뒤쪽 형제 요소
	
	12)html = document
	23document.head
	14)document.body
	
	15)양식의 이름값
	form의 name 속성
	
	naver PC 페이지에서 ----------------------------------------------
	const wrap = document.getElementById("wrap");
	const children = wrap.children;
	children;
	HTMLCollection(3) [div#header, div#container, div#footer, header: div#header, container: div#container, footer: div#footer]0: div#header1: div#container2: div#footercontainer: div#containerfooter: div#footerheader: div#headerlength: 3[[Prototype]]: HTMLCollection
	const footer = children[2];
	footer;
	<div id=​"footer" role=​"contentinfo">​…​</div>​
	const parent = footer.parentElement;
	parent;
	<div id=​"wrap">​…​</div>​

	const header = wrap.firstElementChild;
	header;
	<div id=​"header" role=​"banner">​…​</div>​
	const container = header.nextElementSibling;
	container;
	<div id=​"container" role=​"main">​…​</div>​	
	
	document.head;
	<head>​…​</head>​
	document.body;
	<body>​…​</body>​
	
	naver 로그인  페이지 에서 ----------------------------------------------
	양식이름.입력항목이름
	frmNIDLogin.id;
	<input type=​"text" id=​"id" name=​"id" placeholder=​"아이디" title=​"아이디" class=​"input_text" maxlength=​"41" value>​
	frmNIDLogin.id.value="hjchoir"; 
	
	속성을 관리하는 메서드
	- 추가
	   setAttribute("이름", "값");
	- 조회
	   getAttribute("이름");
	- 삭제
	   removeAttribute("이름");
	
	사용빈도가 높은 기능과 관련된 속성응 document 객체에서 하위속성으로 접근가능
	id, type, name, value, target, action, href, src....
	
	className : class 속성
	   
	const textE1 = document.getElementById("text");
	textE1.setAttribute("price", 10000);
	const price = textE1.getAttribute("price");
	price;
	'10000'
	textE1.removeAttribute("price");
	textE1;
	<input type=​"text" id=​"text">​	   
	textE1.type;
	textE1.type = "button";
	
	정보성 속성은 속성명 앞에 data- 추가한다 - 권장사항
	<input type="text" id="text" data-price-type="order">
	data-속성명: document 객체에 하위 속성 dataset 객체를 통해 접근 가능, 속성이름 data- 빠지고 카멜표기로 바뀜
	
	const textE1 = document.getElementById("text")
	const dataset = textE1.dataset;
	dataset.priceType;
	'order'
	
	class를 추가, 제거하는 방식으로 동작 제어
	
	클래스만을 위한 속성 - classList 객체
	  classList
		add() : 클래스 추가
		remove() : 클래스 제거
		replace() : 클래스 변경
		toggle() :클래스가 있으면 제거, 없으면 추가
		contains() 클래스의 존재 유무

		<input type="text" id="text" class="cls1 cls2 cls3">
		
		const inputEl = document.getElementById("text");
		const classList = inputEl.classList;
		classList;
		classList.add("cls4");
		classList.remove("cls2");
		classList.toggle("cls2");
		true
		classList.toggle("cls2");
		false	   

	동적 요소 생성, 변경
	document.createElement("태그명")
	document.createtextNote("메뉴3")
	부모요소.appendChild("자식요소")
	부모요소.removeChild("자식요소")
	부모요소.replaceChild("기존요소", "새로운요소")
	부모요소.insertBefore("기준요소", "추가요소")
	
	const LiE1 = document.createElement("li");
	LiE1;
	<li>​</li>​
	const textE1 = document.createTextNode("메뉴3");
	LiE1.appendChild(textE1);
	"메뉴3"
	LiE1;
	<li>​메뉴3​</li>​
	const menusE1 = document.querySelector(".menus");
	menusE1.appendChild(LiE1);
	<li>메뉴3</li>​	

이벤트 처리
1. 이벤트 처리기를 등록하는 방법
	- document 객체에 "on이벤트명" 속성에 이벤트 핸들러 함수를 직접 대입 
		<script>
            window.addEventListener("DOMContentLoaded", function() {
                const buttonE1 = document.getElementById("button");
                buttonE1.onclick = function() {
                    alert("클릭!");
                }
            });
        </script>
	
	- 이벤트 처리기의 문제점 : 여러개 정의하면 마지막것만 남고 오버라이트됨(buttonE1.onclick의 주소에 계속) 넣으므로 전역 환경에서 쓰는것은 위험) => 버튼에도 addEventListener 사용하여 동작 정의함으로 해결
		<script>
		window.addEventListener("DOMContentLoaded", function() {
			const buttonE1 = document.getElementById("button");
			buttonE1.onclick = function() {
				alert("클릭!");
			};
			buttonE1.onclick = function() {
				alert("클릭2!");
			}
		});
		</script>
		<script>
            window.onload = function () {
                const buttonE1 = document.getElementById("button");
                buttonE1.onclick = function() {
                    alert("클릭!");
                };
            }		
		</script>


	
2. 이벤트 리스너를 등록하고 삭제하는 방법
- addEventListener를 사용해서 얻을 수 있는 장점
	<script>
		window.addEventListener("DOMContentLoaded", function() {
			const buttonE1 = document.getElementById("button");
			buttonE1.addEventListener("click", function() {
			  alert("클릭1");
			});
			buttonE1.addEventListener("click", function() {
			  alert("클릭2");
			});
		});
		
	</script>

	참고) 
	
	이벤트 -> 비동기 방식(동기 -> 순서대로 실행)
		setTimeout(function() {
	}, 지연시간(1/1000초))
	
	5000 -> 5초지연
	-> 반환값 -> 이벤트핸들러 등록ID
	clearTimeout(이벤트핸들러 등록ID) : 이벤트 취소시
	
	지연반복 실행코드
	setInterval(function() {
	}, 지연시간(1/1000초));
	clearInterval(지연반복실행 등록ID) :지연반복취소시
	
	const intervalId = setInterval(function() {
		console.log("3초마다 실행");
	}, 3000);
	3초마다 실행
	clearInterval(intervalId );

메모리
 - 데이터 영역(정적메모리): 한번 설정되면 변경되지 않는 부분 (작성한 코드, 상수, 메서드)
 - 스택영역(동적영역): 
 - 힙영역(동적영역):
	
	

- addEventListener("이벤트 명", 이벤트 핸들러 함수, 캡쳐링 여부 - false (기본값))
	캡쳐링 여부 - false (기본값) : 버블링 단계에서 이벤트 전파 
				    - true : 캡쳐링 단계에서 이벤트 전파
					
- removeEventListener 메서드로 이벤트 리스너 삭제하기

  <script>
	window.addEventListener("DOMContentLoaded", function() {
	  const buttonEl = document.getElementById("button");
	  
	  buttonEl.addEventListener("click", handleClick);
	  buttonEl.removeEventListener("click", handleClick);

	  function handleClick() {
		alert("클릭됨!");
	  }
	});
  </script>


이벤트 객체
1. 이벤트 객체의 공통 프로퍼티
2. 마우스 이벤트 객체
  - 마우스 이벤트 객체에서 좌표를 담당하는 프로퍼티
  - mounseenter, mouseleave
  - mouseover, mouseout
     <style>
        .box {
          width: 150px;
          height: 150px;
          background: orange;
        }
        .box.on { /* 붙여 쓰면 .. | 띄여 쓰면 상위, 하위 */
          width: 300px;
          height: 300px;
        }

	</style>
	<script>
		window.addEventListener("DOMContentLoaded", function() {
			const boxEl = document.querySelector(".box");
			boxEl.addEventListener("mouseenter", function() {
				this.classList.add("on");
			});
			boxEl.addEventListener("mouseleave", function() {
				this.classList.remove("on");
			});
		});
	</script>

3. 키보드 이벤트 객체
  keyup : 키 눌렀다 뗄때 발생 (이거 많이 씀)
  keypress : 누르면 계속 발생
  keydown : 누르면 한번만 이벤트 발생

  change: 키를 조작할때
    : select, input[type='file'] - 파일을 선택, input[type='number|range']

      <script>
        window.addEventListener("DOMContentLoaded", function() {
          const textEl = document.getElementById("text");
          textEl.addEventListener("keyup", function(e) {
            console.log(e);
            console.log("keyup", this.value);
          });

        });

      </script>
      <input type="text" id="text" />
  

이벤트 전파

      <script>
        window.addEventListener("DOMContentLoaded", function() {
          const box1 = document.querySelector(".box1");
          const box2 = document.querySelector(".box2");
          const box3 = document.querySelector(".box3");

          box1.addEventListener("click", function () {
            console.log("BOX1");
          });
          box2.addEventListener("click", function() {
            console.log("BOX2");
          });
          box3.addEventListener("click", function() {
            console.log("BOX3");
          });
        });
      </script>
      <div class="box box1">
        BOX1
        <div class="box box2">
          BOX2
          <div class="box box3">
            BOX3
          </div>
        </div>
      </div>

1. 이벤트의 전파 단계
1) 캡처링 단계
2) 타깃 단계 
3) 버블링 단계
	addEventListener( 이벤트타입, 이벤트핸들러, 캡처링여부)
		캡처링여부 : 기본값 false (이벤트전파가 버블링단계에서 발생)
					(true 면 이벤트전파가 캡처링단계에서 발생)

      <script>
        window.addEventListener("DOMContentLoaded", function() {
          const box1 = document.querySelector(".box1");
          const box2 = document.querySelector(".box2");
          const box3 = document.querySelector(".box3");

          box1.addEventListener("click", function () {
            console.log("BOX1");
          });
          box2.addEventListener("click", function() {
            console.log("BOX2");
          }, true);
          box3.addEventListener("click", function() {
            console.log("BOX3");
          });
        });
      </script>
      <div class="box box1">
        BOX1
        <div class="box box2">
          BOX2
          <div class="box box3">
            BOX3
          </div>
        </div>
      </div>
		
	
2. 이벤트 전파
1) 이벤트 전파 취소하기
	stopPropagation() : 이벤트 전파 취소
	
	box3.addEventListener("click", function() {
	console.log("BOX3");

2) 이벤트 전파의 일시 정지
	stopImmediatePropagation()

3) 기본 동작 취소하기
	preventDefault()


3. 이벤트 리스너 안의 this
- event 
	.target   //실제 클릭한 요소
	.currentTarget //이벤트가 바인딩된 요소 === this, but 화살표함수로 바꾸면 this가 window가 된다.
	
	
1) 이벤트 리스너 안의 this는 이벤트가 발생한 요소 객체

2) this가 원하는 객체를 가리키도록 설정하는 방법
- bind 메서드를 사용하는 방법

      <script>
        const person = {
          name: "hjchoi",
          age: 40
        };
        window.addEventListener("DOMContentLoaded", function() {

          function handleClick() {
            console.log(this);
          }
          handleClick = handleClick.bind(person);
          const buttonE1 = document.getElementById("button");
          buttonE1.addEventListener("click", handleClick) ;
        });
      </script>

      <button type="button" id="button">클릭!</button>

- 익명 함수 안에서 실행하는 방법
- 화살표 함수를 사용하는 방법
- addEventListener의 두 번째 인수로 객체를 넘기는 방법
	- handleEvent 
		
	<!DOCTYPE html>
	<html>
		<head>
			<meta charset="UTF-8">
		</head>

		<body>
		  <script>
			const person = {
			  name: "hjchoi",
			  age: 40,
			  showInfo() {
				console.log(this);
			  },
			  handleEvent() {
				console.log("handleEvent", this);
			  }
			};
			window.addEventListener("DOMContentLoaded", function() {

			  function handleClick() {
				console.log(this);
			  }
			  handleClick = handleClick.bind(person);
			  const buttonE1 = document.getElementById("button");
			  //buttonE1.addEventListener("click", handleClick) ;

			  //다음 두개 같은 this
			  //buttonE1.addEventListener("click", person.showInfo);
			  /*
			  buttonE1.addEventListener("click", function() {
				person.showInfo();
			  });
			  */
			  buttonE1.addEventListener("click", person); //person.handleEvent
			});
		  </script>

		  <button type="button" id="button">클릭!</button>
		</body>
	</html>		
	

	--------------------------------------------
	const key = "name";
	const person = {
		[key] : 'hjchoi',
		age: 40
	};

	person;
	{name: 'hjchoi', age: 40}

	const person2 = {...person, name: 'hjchoir'};
	person2
	{name: 'hjchoir', age: 40}
	--------------------------------------------


Document 객체 	
	동적 요소 생성, 변경 
	document.createElement("태그명") 
	document.createTextNode("텍스트명");
	
	부모요소.appendChild("자식요소")  -> 가장 마지막 자식 요소로 추가
	부모요소.removeChild("자식요소") -> 자식요소 제거 
	부모요소.replaceChild("기존 요소", "새로운 요소");
	부모요소.insertBefore("기준 요소", "추가 요소"); // 기준 요소 앞에 추가 요소가 추가 
	
	const liEl = document.createElement("li");
	liEl;
	<li>​</li>​
	const textEl = document.createTextNode("메뉴3");
	textEl;
	"메뉴3"
	liEl.appendChild(textEl);
	"메뉴3"
	liEl;
	<li>​메뉴3​</li>​
	const menusEl = document.querySelector(".menus");
	menusEl.appendChild(liEl);

	------------------------------------------------------
	const liEl = document.createElement("li");
	const textEl = document.createTextNode("메뉴3");
	liEl.appendChild(textEl);
	"메뉴3"
	const menu2El = document.querySelector(".menus li:last-of-type");
	menu2El;
	<li>​…​</li>​
	const menusEl = document.querySelector("menus");
	menusEl;
	null
	const menusEl = document.querySelector(".menus");
	menusEl.insertBefore(liEl, menu2El);
	------------------------------------------------------

코어객체
	내장생성자 객체
		Object
		String
		Array
		Number
		Boolean
		...
	
	내장객체
		JSON
		Math
	
호스트객체 - 실행환경에 따른 객체

웹브라우저객체
	window
		location
		history : 방문기록 정보, 
		screen, navigator..
		
		document객체 : 웹문서와 관련죈 객체


4/8
	
localStorage 객체
	
검사 -> 애플리케이션 - 저장용량 ->

브라우저 저장소 ( 키 -값 문자열쌍 )
	로컬스토리지: 브라우저 종료해도 데이터 유지됨
	세션스토리지: 브라우저 종료하면 데이터 삭제됨
	
	추가, 수정
	setItem("키", "값");
	
	조회
	getItem("키");
	
	삭제
	removeItem("키");
	
	전체삭제
	clear()
	
	
	localStorage.setItem('key1', 'value1');
	localStorage.getItem('key1');
	
	객체 -> JSON 문자열 : JSON.stringify(객체);
	
	JSON 문자열 -> 객체 : JSON.parse(JSON 문자열);
	
	const person = {
		name: 'hjchoi',
		age: 40
	};
	localStorage.setItem("person", JSON.stringify(person));
	const person2 = JSON.parse(localStorage.getItem("person"));
	person2;
	
	localStorage.removeItem('key1');
	localStorage.clear();
	
	
	
4/11
웹브라우저 객체
todo3.js

        <script type="text/html" id="tpl">
            <li data-id="#id">#subject
                <button type="button">삭제</button>
            </li>
        </script>
		치환코드 만들어서 ...

    const tpEl = document.getElementById("tpl");
    this.tpl = tpEl.innerHTML;
    this.parser = new DOMParser();
	
	
	let html = this.tpl;
    html = html.replace(/#subject/g, subject).replace(/#id/g, ++this.id);
    const dom = this.parser.parseFromString(html, "text/html");