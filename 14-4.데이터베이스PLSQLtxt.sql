PL/SQL 기초
	- Oracle Procedual Language extension to SQL

	PL/SQL 구조
		1. 블록이란?

		데이터베이스 관련 특정 작업을 수행하는 명령어와 실행에 필요한 여러 요소를 정의하는 명령어 등으로 구성되며, 이러한 명령어를 모아 둔 PL/SQL 프로그램의 기본 단위

	2. 기본 형식

		DECLARE
			[실행에 필요한 여러 요소 선언];  -- 변수, 상수, 커서
		BEGIN
			[작업을 위해 실제 실행하는 명령어];
		EXCEPTION
			[PL/SQL 수행 도중 발생하는 오류 처리];
		END; 

	3. Hello, PL/SQL 출력하기
	
		DECLARE 
			GREETING VARCHAR2(30);
		BEGIN
			GREETING := 'Hello, PL/SQL!'; 	
			DBMS_OUTPUT.PUT_LINE(GREETING);
		END;	
		
		
	4. PL/SQL 주석
		--
		/* */

	변수와 상수
	1. 변수 선언과 사용
	1) 변수이름 자료형 := 값 또는 값이 도출되는 표현식 
	2) 변수 선언 및 변수 값 출력하기

	2. 상수 정의하기
	1) 변수이름 CONSTANT 자료형 := 값 또는 값이 도출되는 표현식 
	2) 상수에 값을 대입한 후 출력하기

	3. 변수의 기본값 지정하기 
	1) 변수이름 자료형 DEFAULT := 값 또는 값이 도출되는 표현식
	2) 변수에 기본값을 설정한 후 출력하기


	4. 변수에 NULL 값 저장 막기
	1) 변수이름 자료형 NOT NULL := 값 또는 값이 도출되는 표현식
	2) 변수에 NOT NULL을 설정하고 값을 대입한 수 출력하기

	5. 변수 이름 정하기

	변수의 자료형
	
	1. 스칼라 : 단일값 
		1) 숫자 NUMBER 
		2) 문자열 CHAAR, VARCHAR2 
		3) 날짜 DATE 
		4) 논리 데이터 BOOLEAN : true, false, NULL
			-----------------------------------------------------
			DECLARE
				EMPNO NUMBER(4) NOT NULL := 1;
				-- EMPNO NUMBER(4) NOT NULL DEFAULT 1;  -- 윗줄과 같음
				ENAME VARCHAR2(10);
			BEGIN
				EMPNO := 1000;
				ENAME := '이이름';
				DBMS_OUTPUT.PUT_LINE(EMPNO || ' : ' || ENAME);
			END;
			-----------------------------------------------------

	2. 참조형
		1) %TYPE : 열을 참조 ( 특정 테이블의 컬럼(1개)의 자료형을 참조 )
		2) %ROWTYPE : 행을 참조 ( 특정 테이블의 보든 컬럼들을 참조 )
		3) 변수 이름 테이블이름.열이름%TYPE
		4) 참조형(열)의 변수에 값을 대입한 후 출력하기

		5) 특정 테이블에서 하나의 열이 아닌 행 구조 전체를 참조할 때 %ROWTYPE을 사용
		6) 변수이름 테이블이름%ROWTYPE
		7) 참조형(행)의 변수에 값을 대입한 후 출력하기
			-----------------------------------------------------
			DECLARE
				EMPNO EMP.EMPNO%TYPE;
				ENAME EMP.ENAME%TYPE;
			BEGIN
				EMPNO := 1000;
				ENAME := '이이름';
				DBMS_OUTPUT.PUT_LINE(EMPNO || ' : ' || ENAME);
			END;

			-----------------------------------------------------
			DECLARE
				EMP_ROW EMP%ROWTYPE;
			BEGIN
				EMP_ROW.EMPNO := 1000;
				EMP_ROW.ENAME := '이이읆';
				EMP_ROW.JOB := 'CLERK';
				EMP_ROW.DEPTNO := 40;

				DBMS_OUTPUT.PUT_LINE('EMPNO : ' || EMP_ROW.EMPNO);
				DBMS_OUTPUT.PUT_LINE('ENAME : ' || EMP_ROW.ENAME);
				DBMS_OUTPUT.PUT_LINE('JOB : ' || EMP_ROW.JOB);
				DBMS_OUTPUT.PUT_LINE('DEPTNO : ' || EMP_ROW.DEPTNO);
			END;
			-----------------------------------------------------
			DECLARE
				EMP_ROW EMP%ROWTYPE;
			BEGIN
				SELECT * INTO EMP_ROW
				FROM EMP WHERE ENAME = 'SCOTT';
				
				DBMS_OUTPUT.PUT_LINE('EMPNO : ' || EMP_ROW.EMPNO);
				DBMS_OUTPUT.PUT_LINE('ENAME : ' || EMP_ROW.ENAME);
				DBMS_OUTPUT.PUT_LINE('JOB : ' || EMP_ROW.JOB);
				DBMS_OUTPUT.PUT_LINE('DEPTNO : ' || EMP_ROW.DEPTNO);
			END;
			>>
			EMPNO : 7788
			ENAME : SCOTT
			JOB : ANALYST
			DEPTNO : 20			
			------------------------------------------------------
			DECLARE
				V_NUM CONSTANT NUMBER := 1000;
			BEGIN
				--V_NUM := 2000; -- 에러: 상수인데 변경 못함
				DBMS_OUTPUT.PUT_LINE(V_NUM);
			END;
			------------------------------------------------------
			
	3. 복합형, LOB형
		1) 컬렉션, TABLE 자료형 : 한 가지 자료형의 데이터를 여러 개 저장(테이블의 열과 유사) : 키-값 
		2) 레코드, RECORD 자료형 : 여러 종류 자료형의 데이터를 저장(테이블의 행과 유사) 
		3) BLOB, CLOB : Large OBJECT

			DECLARE
			TYPE REC_DEPT IS RECORD (
					DEPTNO DEPT.DEPTNO%TYPE,
					DNAME DEPT.DNAME%TYPE,
					LOC DEPT.LOC%TYPE
				);

				DEPT_DATA REC_DEPT;
			BEGIN
				DEPT_DATA.DEPTNO := 99;
				DEPT_DATA.DNAME := 'DATABASE';
				DEPT_DATA.LOC := 'SEOUL';

				DBMS_OUTPUT.PUT_LINE('DEPTNO : ' || DEPT_DATA.DEPTNO);
				DBMS_OUTPUT.PUT_LINE('DNAME : ' || DEPT_DATA.DNAME);
				DBMS_OUTPUT.PUT_LINE('LOC : ' || DEPT_DATA.LOC);
			END;


	조건 제어문
	1. 조건문
		1) IF-THEN
		- 변수에 입력한 값이 홀수인지 알아보기(입력 값이 홀수일 때)

		2) IF-THEN-ELSE
		- 변수에 입력된 값이 홀수인지 짝수인지 알아보기(입력 값이 짝수일 때)

			DECLARE
				V_NUMBER NUMBER := 21;
			BEGIN
				IF MOD(V_NUMBER, 2) = 1 THEN
					DBMS_OUTPUT.PUT_LINE('홀수입니다.');
				ELSE
					DBMS_OUTPUT.PUT_LINE('짝수입니다.');
				END IF;
			END;		
		

		3) IF-THEN-ELSIF
		- 입력한 점수가 어느 학점인지 출력하기
		
			DECLARE
				V_SCORE NUMBER := 83;
			BEGIN
				IF V_SCORE >= 90 THEN
					DBMS_OUTPUT.PUT_LINE('A학점');
				ELSIF V_SCORE >= 80 THEN
					DBMS_OUTPUT.PUT_LINE('B학점');
				ELSIF V_SCORE >= 70 THEN
					DBMS_OUTPUT.PUT_LINE('C학점');
				ELSIF V_SCORE >= 60 THEN
					DBMS_OUTPUT.PUT_LINE('D학점');
				ELSE
					DBMS_OUTPUT.PUT_LINE('F학점');
				END IF;
			END;

	2. CASE 조건문
		1) 단순 CASE
		- 입력 점수에 따른 학점 출력하기(단순 CASE 사용)

			DECLARE
				V_RANK NUMBER := 3;
			BEGIN
				CASE V_RANK
					WHEN 1 THEN DBMS_OUTPUT.PUT_LINE('금매달');
					WHEN 2 THEN DBMS_OUTPUT.PUT_LINE('은매달');
					WHEN 3 THEN DBMS_OUTPUT.PUT_LINE('동매달');
					ELSE DBMS_OUTPUT.PUT_LINE('노메달');
				END CASE;
			END;

		2) 검색 CASE
		- 입력 점수에 따른 학점 출력하기(검색 CASE 사용)
		
			DECLARE
				V_SCORE NUMBER := 75;
			BEGIN
				CASE 
					WHEN V_SCORE >= 90 THEN DBMS_OUTPUT.PUT_LINE('A학점');
					WHEN V_SCORE >= 80 THEN DBMS_OUTPUT.PUT_LINE('B학점');
					WHEN V_SCORE >= 70 THEN DBMS_OUTPUT.PUT_LINE('C학점');
					WHEN V_SCORE >= 60 THEN DBMS_OUTPUT.PUT_LINE('D학점');
					ELSE DBMS_OUTPUT.PUT_LINE('F학점');
				END CASE;
			END;
			
	반복 제어문

	1. 반복문 종류 
		1) 기본 LOOP

			---------------------------------
			DECLARE
				V_NUM NUMBER := 1;
			BEGIN
				LOOP
					DBMS_OUTPUT.PUT_LINE('V_NUM : ' || V_NUM );
					V_NUM := V_NUM + 1;
				
					IF V_NUM > 4 THEN EXIT;
					END IF;
				END LOOP;
			END;
			---------------------------------
			DECLARE
				V_NUM NUMBER := 1;
			BEGIN
				LOOP
					DBMS_OUTPUT.PUT_LINE('V_NUM : ' || V_NUM );
					V_NUM := V_NUM + 1;

					EXIT WHEN V_NUM > 4;
				END LOOP;
			END;
			---------------------------------
			
		2) WHILE LOOP
			---------------------------------
			DECLARE
				V_NUM NUMBER := 1;
			BEGIN
				WHILE V_NUM <= 4 LOOP
					DBMS_OUTPUT.PUT_LINE('V_NUM : ' || V_NUM);
					V_NUM := V_NUM + 1;
				END LOOP;
			END;
			---------------------------------
		3) FOR LOOP
			---------------------------------
			BEGIN
				FOR i IN 1..4 LOOP
					DBMS_OUTPUT.PUT_LINE('i : ' || i);
				END LOOP;
			END;

			BEGIN
				FOR I IN REVERSE 1..4 LOOP
					DBMS_OUTPUT.PUT_LINE('i : ' || i);
				END LOOP;
				
			END;
			---------------------------------
			DECLARE
				V_TOTAL NUMBER := 0;
			BEGIN
				FOR i IN 1..100 LOOP
					CONTINUE WHEN MOD(i, 2) = 0;
					V_TOTAL := V_TOTAL + i;
				END LOOP;
				DBMS_OUTPUT.PUT_LINE('합계 : ' || V_TOTAL);
			END;			
			---------------------------------

		4) Cursor For LOOP

	2. 반복 중단 명령어 종류
		1) EXIT
		2) EXIT-WHEN
		3) CONTINUE
		4) CONTINUE-WHEN

	3. 기본 LOOP
	4. WHILE LOOP
	5. FOR LOOP
	6. CONTINUE문, CONTINUE-WHEN문		
	
	
레코드와 컬렉션

	자료형이 다른 여러 데이터를 저장하는 레코드

	1. 레코드란?
		- 각기 다른 데이터를 하나의 변수에 저장하는 데 사용
		- 레코드에 포함된 변수는 레코드 이름과 마침표(.)로 사용할 수 있습니다.

	2. 형식
		TYPE 레코드 이름 IS RECORD(
			변수 이름 자료형 NOT NULL := (또는 DEFAULT) 값 또는 값이 도출되는 여러 표현식
		);



	3. 레코드 정의해서 사용하기

		DECLARE
			TYPE REC_DEPT IS RECORD (
				DEPTNO DEPT.DEPTNO%TYPE,
				DNAME DEPT.DNAME%TYPE,
				LOC DEPT.LOC%TYPE
			);

			DEPT_DATA REC_DEPT;
		BEGIN
			DEPT_DATA.DEPTNO := 99;
			DEPT_DATA.DNAME := 'DATABASE';
			DEPT_DATA.LOC := 'SEOUL';

			DBMS_OUTPUT.PUT_LINE('DEPTNO : ' || DEPT_DATA.DEPTNO);
			DBMS_OUTPUT.PUT_LINE('DNAME : ' || DEPT_DATA.DNAME);
			DBMS_OUTPUT.PUT_LINE('LOC : ' || DEPT_DATA.LOC);
		END;

		------------------------------------------------------
		DECLARE 
			-- RECORD 자료형 선언
			TYPE REC_DEPT IS RECORD(
				DEPTNO DEPT.DEPTNO%TYPE,
				DNAME DEPT.DNAME%TYPE,
				LOC DEPT.LOC%TYPE
			);

			TYPE REC_EMP IS RECORD (
				EMPNO EMP.EMPNO%TYPE,
				ENAME EMP.ENAME%TYPE,
				DINFO REC_DEPT
			);
			
			EMP_DATA REC_EMP;

		BEGIN
			SELECT E.EMPNO, E.ENAME, D.DEPTNO, D.DNAME, D.LOC
				INTO EMP_DATA.EMPNO, 
					EMP_DATA.ENAME, 
					EMP_DATA.DINFO.DEPTNO, 
					EMP_DATA.DINFO.DNAME, 
					EMP_DATA.DINFO.LOC
			FROM EMP E, DEPT D 
			WHERE E.DEPTNO = D.DEPTNO AND E.ENAME = 'SCOTT';

			dbms_output.put_line('EMPNO : ' || EMP_DATA.EMPNO);
			dbms_output.put_line('ENAME : ' || EMP_DATA.ENAME);
			dbms_output.put_line('DEPTNO : ' || EMP_DATA.DINFO.DEPTNO);
			dbms_output.put_line('DNAME : ' || EMP_DATA.DINFO.DNAME);
			dbms_output.put_line('LOC : ' || EMP_DATA.DINFO.LOC);
		END;
		----------------------------------------------------

	4. 레코드를 사용한 INSERT
		insert into dept_record values dept_data;
		----------------------------------------------------
		DECLARE 
			-- RECORD 자료형 선언
			TYPE REC_DEPT IS RECORD(
				DEPTNO DEPT.DEPTNO%TYPE,
				DNAME DEPT.DNAME%TYPE,
				LOC DEPT.LOC%TYPE
			);
			-- DEPT_DATA 변수선언
			DEPT_DATA REC_DEPT;
		BEGIN
			DEPT_DATA.DEPTNO := 99;
			DEPT_DATA.DNAME := 'DATABASE';
			DEPT_DATA.LOC := 'SEOUL';

			INSERT INTO DEPT_RECORD VALUES DEPT_DATA;
		END;
		---------------------------------------------------------
		DECLARE 
			-- RECORD 자료형 선언
			TYPE REC_DEPT IS RECORD(
				DEPTNO DEPT.DEPTNO%TYPE,
				DNAME DEPT.DNAME%TYPE,
				LOC DEPT.LOC%TYPE
			);
			-- DEPT_DATA 변수선언
			DEPT_DATA REC_DEPT;
		BEGIN
			DEPT_DATA.DEPTNO := 99;
			DEPT_DATA.DNAME := 'DB';
			DEPT_DATA.LOC := 'INCHEON';

			UPDATE dept_record SET ROW = DEPT_DATA WHERE deptno = 99;  --- SET ROW 주의
		END;

		SELECT * FROM dept_record;
		---------------------------------------------------------


	1) DEPT_RECORD 테이블 생성하기
	2) 레코드를 사용하여 INSERT 하기

	5. 레코드를 포함하는 레코드
	1) 레코드에 포함된 변수의 자료형을 지정할 때 다른 레코드를 지정할 수도 있습니다.
	2) 레코드에 다른 레코드 포함하기


	자료형이 같은 여러 데이터를 저장하는 컬렉션
	- 컬렉션은 특정 자료형의 데이터를 여러 개 저장하는 복합 자료형
	

	1. 연관배열
	1) 인덱스라고 불리는 키(key), 값(value)으로 구성되는 컬렉션
	2) 중복되지 않은 유일한 키를 통해 값을 저장하고 불러오는 방식을 사용
	3) 형식
		TYPE 연관 배열 이름 IS TABLE OF 자료형 [NOT NULL]
		INDEX BY 인덱스형;
		INDEX BY 인덱스형;

		---------------------------------------------------------
		DECLARE 
			TYPE type1 IS TABLE OF varchar2(200)
			INDEX BY PLS_INTEGER;

			text_arr type1;
		BEGIN
			text_arr(1) := '첫번쩨';
			text_arr(2) := '두번째';
			text_arr(3) := '세번째';
			text_arr(4) := '네번째';

			dbms_output.put_line('text_arr(1)' || TEXT_ARR(1));
			
			FOR i IN 1..4 LOOP
				dbms_output.put_line('text_arr(' || i || ') : ' || text_arr(i) );
			END LOOP;
			
		END;
		---------------------------------------------------------
		DECLARE 
			TYPE type2 IS TABLE OF varchar2(20)
			INDEX BY varchar2(20);
			TEXT_ARR TYPE2;

		BEGIN
			TEXT_ARR('KEY1') := 'VALUE1';
			DBMS_OUTPUT.PUT_LINE(TEXT_ARR('KEY1'));
		END;
		---------------------------------------------------------



	4) 연관 배열 사용하기
	5) 연관 배열 자료형에 레코드 사용하기

	2. 컬렉션 메서드	

DECLARE
/*
	TYPE rec_dept IS RECORD (
		deptno dept.deptno%TYPE,
		dname dept.dname%TYPE
	);
	TYPE dept_arr IS TABLE OF rec_dept
	*/
	TYPE TYPE_DEPT IS TABLE OF DEPT%ROWTYPE
	INDEX BY PLS_INTEGER;

	DEPT_ARR TYPE_DEPT;
	IDX PLS_INTEGER := 1;

BEGIN
	FOR d IN (SELECT deptno, dname FROM dept) LOOP
		DEPT_ARR(IDX).DEPTNO := d.DEPTNO;
		DEPT_ARR(IDX).DNAME := d.DNAME;
		DBMS_OUTPUT.PUT_LINE('DEPTNO : ' || DEPT_ARR(IDX).DEPTNO || 
			'DNAME : ' || DEPT_ARR(IDX).DNAME  );
		IDX := IDX + 1;
	END LOOP;	
END;

	
커서와 예외 처리

	특정 열을 선택하여 처리하는 커서
	1. 커서란?
		1) SELECT문 또는 데이터 조작어 같은 SQL문을 실행했을 때 해당 SQL문을 처리하는 정보를 저장한 메모리 공간
		2) 커서는 이 메모리의 포인터
		3) 커서는 사용 방법에 따라 명시적(explicit) 커서와 묵시적(implicit) 커서로 나뉩니다.

	2. SELECT INTO 방식
		1) SELECT INTO를 사용한 단일행 데이터 저장하기

	3. 명시적 커서
		1) 단계
		- 커서 선언(Declaration)
		- 커서 열기(open)
		- 커서에서 읽어온 데이터 사용(Fetch)
		- 커서 닫기(Close)

		2) 작성법
		DECLARE 
			CURSOR 커서이름 IS SQL문;  -- 커서 선언(Declaration)
		BEGIN
			OPEN 커서 이름;   -  커서 열기(open)
			FETCH 커서이름 INTO 변수  - 커서로부터 읽어온 데이터 사용(Fetch)
			CLOSE 커서이름;  -- 커서 닫기(Close)
		END;

		3) 하나의 행만 조회되는 경우
		4) 여러 행이 조회되는 경우 사용하는 LOOP문
		5) 여러 개의 행이 조회되는 경우(FOR LOOP문)
		6) 커서에 파라미터 사용하기

			------------------------------------------------
			DECLARE
				V_DEPT DEPT%ROWTYPE;
				
				CURSOR C1 IS
					SELECT * FROM DEPT WHERE DEPTNO = 40;
			BEGIN
				OPEN C1;

				FETCH C1 INTO V_DEPT;	
				DBMS_OUTPUT.PUT_LINE('DEPTNO : ' || V_DEPT.DEPTNO);
				DBMS_OUTPUT.PUT_LINE('DNAME : ' || V_DEPT.DNAME);
				DBMS_OUTPUT.PUT_LINE('LOC : ' || V_DEPT.LOC);

				CLOSE C1;
			END;
			------------------------------------------------
			DECLARE
				V_DEPT DEPT%ROWTYPE;
				
				CURSOR C1 IS
					SELECT * FROM DEPT ;
			BEGIN
				OPEN C1;
				LOOP
					FETCH C1 INTO V_DEPT;	
					EXIT WHEN C1%NOTFOUND;
				
					DBMS_OUTPUT.PUT_LINE('DEPTNO : ' || V_DEPT.DEPTNO);
					DBMS_OUTPUT.PUT_LINE('DNAME : ' || V_DEPT.DNAME);
					DBMS_OUTPUT.PUT_LINE('LOC : ' || V_DEPT.LOC);
					DBMS_OUTPUT.PUT_LINE('------------------');
				END LOOP;
				CLOSE C1;
			END;
			------------------------------------------------
			DECLARE 
				CURSOR C1 IS SELECT * FROM DEPT;
			BEGIN
				FOR D IN C1 LOOP
					DBMS_OUTPUT.PUT_LINE('DEPTNO : ' || D.DEPTNO);
					DBMS_OUTPUT.PUT_LINE('DNAME : ' || D.DNAME);
					DBMS_OUTPUT.PUT_LINE('LOC : ' || D.LOC);
				END LOOP;
				
			END;
			------------------------------------------------
			DECLARE
				V_DEPT DEPT%ROWTYPE;
				CURSOR C1(P_DEPTNO DEPT.DEPTNO%TYPE) IS
					SELECT * FROM DEPT WHERE DEPTNO = P_DEPTNO;
					BEGIN
				OPEN C1(10);
				LOOP
					FETCH C1 INTO V_DEPT;
					EXIT WHEN C1%NOTFOUND;
					DBMS_OUTPUT.PUT_LINE('DEPTNO : ' || V_DEPT.DEPTNO);
					DBMS_OUTPUT.PUT_LINE('DNAME : ' || V_DEPT.DNAME);
					DBMS_OUTPUT.PUT_LINE('LOC : ' || V_DEPT.LOC);
					
				END LOOP;
				
				CLOSE C1;
			END;




	4. 묵시적 커서
		1) 다른 선언 없이 SQL문을 사용했을 때 오라클에서 자동으로 선언되는 커서
		2) 사용자가 OPEN, FETCH, CLOSE를 지정하지 않습니다. PL/SQL문 내부에서 DML 명령어나 SELECT INTO문 등이 실행될 때 자동으로 생성 및 처리

		3) 묵시적 커서의 속성 사용하기


	오류가 발생해도 프로그램이 비정상 종료되지 않도록 하는 예외 처리

		1. 예외가 발생하는 PL/SQL
		2. 예외를 처리하는 PL/SQL(예외 처리 추가)
		3. 예외 발생 후의 코드 실행 여부 확인하기
		4. 예외 종류
		5. 예외 처리부 작성

			EXCEPTION
				WHEN 예외 이름1 [OR 예외 이름2 - ] THEN
					예외 처리에 사용할 명령어;
				WHEN 예외 이름3 [OR 예외 이름4 - ] THEN 
					예외 처리에 사용할 명령어;
				...
				WHEN OTHERS THEN
					예외 처리에 사용할 명령어;
				
		6. 사전 정의된 예외 사용
		7. 오류 코드와 오류 메시지 사용
			SQLCODE
			SQLERRM
		
		
			-----------------------------------------------------------
			DECLARE
				V_DNAME NUMBER;
			BEGIN
				SELECT DNAME INTO V_DNAME FROM DEPT WHERE DEPTNO = 10;
			EXCEPTION
				WHEN VALUE_ERROR THEN 
					DBMS_OUTPUT.PUT_LINE('수치 또는 값 오류');
			END;
			-----------------------------------------------------------
			DECLARE
				V_DNAME VARCHAR2(30);
			BEGIN
				SELECT DNAME INTO V_DNAME FROM DEPT ;
			EXCEPTION
				WHEN VALUE_ERROR THEN 
					DBMS_OUTPUT.PUT_LINE('수치 또는 값 오류');
				WHEN TOO_MANY_ROWS THEN
					DBMS_OUTPUT.PUT_LINE('여러행 조회 오류');
			END;


			-----------------------------------------------------------
			DECLARE
				V_DNAME VARCHAR2(20);
				V_NUM NUMBER := 10;
			BEGIN
				SELECT DNAME INTO V_DNAME FROM DEPT WHERE DEPTNO = 10 ;
				DBMS_OUTPUT.PUT_LINE(V_NUM / 0);
			EXCEPTION
				WHEN VALUE_ERROR THEN 
					DBMS_OUTPUT.PUT_LINE('수치 또는 값 오류');
				WHEN TOO_MANY_ROWS THEN
					DBMS_OUTPUT.PUT_LINE('여러행 조회 오류');
				WHEN OTHERS THEN
					DBMS_OUTPUT.PUT_LINE('기타오류');
					DBMS_OUTPUT.PUT_LINE('SQLCODE : ' || TO_CHAR(SQLCODE));
					DBMS_OUTPUT.PUT_LINE('SQLERRM : ' || SQLERRM);
			END;



저장 서브프로그램
	1. 저장 서브 프로그램이란?
		1) PL/SQL로 만든 프로그램을 주기적으로 또는 필요할 때마다 여러 번 사용할 목적으로 이름을 지정하여 오라클에 저장해 두는 PL/SQL 프로그램을 저장 서브프로그램(stored subprogram)이라고 합니다.
		2) 익명 블록과 달리 저장 서브프로그램은 오라클에 저장하여 공유할 수 있으므로 메모리,성능,재사용성 등 여러 면에서 장점

		3) 종류
		- 저장 프로시저(stored procedure) : 일반적으로 특정 처리 작업 수행을 위한 서브프로그램으로 SQL문에서는 사용할 수 없습니다.
		- 저장 함수(stored function) : 일반적으로 특정 연산을 거친 결과 값을 반환하는 서브프로그램으로 SQL문에서 사용할 수 있습니다.
		- 패키지(package) : 저장 서브프로그램을 그룹화하는 데 사용합니다.
		- 트리거(trigger) : 특정 상황(이벤트)이 발생할 때 자동으로 연달아 수행할 기능을 구현하는 데 사용합니다.



프로시저
	1. 파라미터를 사용하지 않는 프로시저
		1) 문법
		CREATE [OR REPLACE(1)] PROCEDURE 프로시저 이름
		IS | AS 
			선언부 - 변수 정의, 커서 정의
		BEGIN 
			실행부
		EXCEPTION - 
			예외 처리부
		END [프로시저 이름];

		2) 프로시저 생성하기

		3) SQL*PLUS로 프로시저 실행하기
		
			CALL 프로시저명(값1,..)
			
			CALL PRO_NOPARAM();
			
		4) PL/SQL 블록에서 프로시저 실행하기
		
			BEGIN
				PRO_NOPARAM(); -- 또는 PRO_NOPARAM;
			END;		
		
		5) 프로시저 내용 확인하기
		
			- USER_SOURCE 
			
		6) 프로시저 삭제하기
			DROP PROCEDURE PRO_NOPARAM;

	2. 파라미터를 사용하는 프로시저
		1) 문법
		CREATE [OR REPLACE(1)] PROCEDURE 프로시저 이름
		[(파라미터 이름1 [modes] 자료형 [ := | DEFAULT 기본값],
		  파라미터 이름2 [modes] 자료형 [ := | DEFAULT 기본값],
		  ...
		  파라미터 이름N [modes] 자료형 [ := | DEFAULT 기본값]
		)]
		IS | AS
			선언부
		BEGIN
			실행부
		EXCEPTION
			예외 처리부
		END [프로시저 이름];

		2) 파라미터 모드
		- IN : 지정하지 않으면 기본값으로 프로시저를 호출할 때 값을 입력받습니다
		- OUT : 호출할 때 값을 반환합니다.
		- IN OUT : 호출할 때 값을 입력받은 후 실행 결과 값을 반환합니다.

		3) IN 모드 파라미터
		- 필요한 값을 직접 입력받는 형식의 파라미터를 지정할 때
		- 기본값, 생략 가능 

		4) OUT 모드 파라미터
		- 프로시서 실행 후 호출한 프로그램으로 값을 반환합니다. 

		5) IN OUT 모드 파라미터
		- IN, OUT으로 선언한 파라미터 기능을 동시에 수행합니다.
		- 값을 입력받을 때와 프로시저 수행 후 결과 값을 반환할 때 사용합니다.


	3. 프로시저 오류 정보 확인하기
		1) SHOW ERRORS
			sqlplus 에서
		2) USER_ERRORS  
		
			------------------------------------------------------------
			SELECT * FROM user_errors;

			CREATE OR REPLACE PROCEDURE PRO_NOPARAM  -- 파라메터 없으므로 () 필요없음
			IS
				V_DEPTNO DEPT.DEPTNO%TYPE;
				V_DNAME DEPT.DNAME%TYPE;
			BEGIN
				SELECT DEPTNO, DNAME INTO V_DEPTNO, V_DNAME FROM DEPT WHERE DEPTNO = 20;
				DBMS_OUTPUT.PUT_LINE('DEPTNO : ' || V_DEPTNO );
				DBMS_OUTPUT.PUT_LINE('DNAME : ' || V_DNAME );
			END PRO_NOPARAM;
			-----------매개변수 있을때---------------------
			CREATE OR REPLACE PROCEDURE PRO_PARAM_IN(
				P_DEPTNO IN NUMBER,  -- IN은 안써도 됨
				P_JOB VARCHAR2
			)
			IS 	
				CURSOR C1 IS
					SELECT * FROM EMP 
						WHERE DEPTNO = P_DEPTNO AND JOB = P_JOB;
			BEGIN 
				FOR d IN C1 LOOP
					DBMS_OUTPUT.PUT_LINE('EMPNO : ' || d.EMPNO);
					DBMS_OUTPUT.PUT_LINE('ENAME : ' || d.ENAME);
					DBMS_OUTPUT.PUT_LINE('JOB : ' || d.JOB);
					DBMS_OUTPUT.PUT_LINE('--------------------------');
				END LOOP;
			END PRO_PARAM_IN; 
			-----PRO_PARAM_IN 프로시저 사용
			CALL PRO_PARAM_IN(30,'MANAGER');
			----------------------------------------------
			
			CREATE OR REPLACE PROCEDURE PRO_PARAM_OUT (
				P_DEPTNO EMP.DEPTNO%TYPE,   -- 입력받을 변수
				O_SUM OUT NUMBER,  -- 처리해서 내보낼 변수 선언
				O_AVG OUT NUMBER
			)
			IS 
			BEGIN
				SELECT SUM(SAL), ROUND(AVG(SAL), 2) INTO O_SUM, O_AVG
				FROM EMP WHERE DEPTNO = P_DEPTNO;
			END PRO_PARAM_OUT;
			
			
			------ PRO_PARAM_OUT 프로시저 사용하기
				DECLARE
					v_SUM NUMBER; -- 처리결과 받아올 변수 선언  
					V_AVG NUMBER; -- 처리결과 받아올 변수 선언  
				BEGIN
					PRO_PARAM_OUT(20,v_SUM, V_AVG);
					DBMS_OUTPUT.PUT_LINE('SUM : ' || V_SUM);
					DBMS_OUTPUT.PUT_LINE('AVG : ' || V_AVG);
				END;
			----------------------------------
			CREATE OR REPLACE PROCEDURE PRO_PARAM_INOUT(
				P_NUM IN OUT NUMBER
			)
			IS

			BEGIN
				P_NUM := P_NUM * P_NUM;	
			END PRO_PARAM_INOUT;

			--- PRO_PARAM_INOUT 사용 
				DECLARE
					V_NUM NUMBER := 10;
				BEGIN
					PRO_PARAM_INOUT(V_NUM);
					PRO_PARAM_INOUT(V_NUM);
					DBMS_OUTPUT.PUT_LINE('V_NUM : ' || V_NUM);
				END;			
			--------------------------------------------------------------

함수

	1. 문법 
		CREATE [OR REPLACE] FUNCTION 함수 이름
		[(
		   파라미터 이름1 [IN] 자료형1, - (1) 
		   파라미터 이름2 [IN] 자료형2,
		   ...
		   파라미터 이름N [IN] 자료형N  
		)]
		RETURN 자료형 - (2)
		IS | AS 
			선언부 
		BEGIN 
			실행부
			RETURN (반환 값); - (3) 
		EXCEPTION
			예외 처리부 
		END [함수 이름];

	2. 함수 생성하기
	3. 함수 실행하기
		1) PL/SQL로 함수 실행하기
		2) SQL문에서 함수 실행하기

	4. 함수 삭제하기
		SELECT * FROM user_source WHERE TYPE='FUNCTION';
	
		-----------------------------------------------
		CREATE OR REPLACE FUNCTION FUNC_AFTERTAX(
			P_SAL NUMBER 
		) RETURN NUMBER 
		IS 
			TAX NUMBER := 0.05;
			SAL NUMBER ;
		BEGIN
			SAL := ROUND(P_SAL - P_SAL * TAX);
			RETURN SAL;
		END FUNC_AFTERTAX;	
		
		-- 함수사용
		DECLARE 
			AFTERTAX NUMBER;
		BEGIN
			AFTERTAX := FUNC_AFTERTAX(2500);
			DBMS_OUTPUT.PUT_LINE('세후급여:' || AFTERTAX);
		END;
		-- 또는
		SELECT ename, sal, func_aftertax(sal) FROM emp;
		-----------------------------------------------
		DROP FUNCTION FUNC_AFTERTAX;
		
패키지
	-  기능 면에서 연관성이 높은 프로시저, 함수 등 여러 개의 PL/SQL 서브프로그램을 하나의 논리 그룹으로 묶어 통합,관리하는 데 사용하는 객체

	1. 패키지 구조와 생성
		1) 패키지 명세
		- 패키지 명세는 패키지에 포함할 변수, 상수, 예외, 커서 그리고 PL/SQL 서브프로그램을 선언하는 용도로 작성합니다.
		- 패키지 명세에 선언한 여러 객체는 패키지 내부뿐만 아니라 외부에서도 참조할 수 있습니다.
		- 문법
		CREATE [OR REPLACE] PACKAGE 패키지 이름
		IS | AS 
			서브프로그램을 포함한 다양한 객체 선언
		END [패키지 이름];

		- 패키지 생성하기


		2) 패키지 본문
		- 패키지 명세에서 선언한 서브프로그램 코드를 작성합니다. 
		- 문법
		CREATE [OR REPLACE] PACKAGE BODY 패키지 이름
		IS | AS 
			패키지 명세에서 선언한 서브프로그램을 포함한 여러 객체를 정의
			경에우 따라 패키지 명세에 존재하지 않는 객체 및 서브프로그램도 정의 가능
		END [패키지 이름];

		- 패키지 본문 생성하기

	2. 패키지 사용하기
	3. 패키지 삭제하기

		CREATE OR REPLACE PACKAGE BODY PKG_EMP
		IS 
			FUNCTION FUNC_AFTERTAX(P_SAL NUMBER) RETURN NUMBER
				IS 
					TAX NUMBER := 0.05;
					SAL NUMBER;
				BEGIN 
					SAL := ROUND(P_SAL - P_SAL * TAX);
					
					RETURN SAL;
				
				END FUNC_AFTERTAX;
			
			PROCEDURE PRO_SEARCH_EMP(
				P_DEPTNO EMP.DEPTNO%TYPE, 
				P_JOB EMP.JOB%TYPE
			)
			IS 
				
			BEGIN 
				FOR d IN (SELECT * FROM EMP WHERE DEPTNO = P_DEPTNO AND JOB = P_JOB) LOOP 
					DBMS_OUTPUT.PUT_LINE('EMPNO : ' || d.EMPNO);
					DBMS_OUTPUT.PUT_LINE('ENAME : ' || d.ENAME);
					DBMS_OUTPUT.PUT_LINE('JOB : ' || d.JOB);
					DBMS_OUTPUT.PUT_LINE('--------------------------');
				END LOOP;
				
			END PRO_SEARCH_EMP;
			
			
		END PKG_EMP; 

		---- 패키지 사용
		BEGIN
			DBMS_OUTPUT.PUT_LINE('세후급여 : ' || PKG_EMP.FUNC_AFTERTAX(2500));
			PKG_EMP.PRO_SEARCH_EMP(20, 'MANAGER');
		END;

		SELECT * FROM user_source WHERE TYPE = 'PACKAGE';


트리거
	1. 트리거란?
		1) 데이터베이스 안의 특정 상황이나 동작, 즉 이벤트가 발생할 경우에 자동으로 실행되는 기능을 정의하는 PL/SQL 서브프로그램
		2) 트리거는 특정 작업 또는 이벤트 발생으로 다른 데이터 작업을 추가로 실행하기 때문에 무분별하게 사용하면 데이터베이스 성능을 떨어뜨리는 원인이 되므로 주의

	2. DML 트리거
		1) DML 트리거 형식
		CREATE [OR REPLACE] TRIGGER 트리거 이름 - (1)
		BEFORE | AFTER - (2) 
		INSERT | UPDATE | DELETE ON 테이블 이름 - (3)
		REFERENCING OLD as old | NOW as new - (4)
		FOR EACH ROW WHEN 조건식 - (5) 
		FOLLOWS 트리거 이름2, 트리거 이름3 ...  - (6)
		ENABLE | DISABLE  - (7)

		DECLARE
		  선언부 
		BEGIN 
		  실행부 
		EXCEPTION
		  예외 처리부
		END;


	3. DML 트리거의 제작 및 사용(BEFORE)
		1) EMP_TRG 테이블 생성하기
		2) DML 실행 전에 수행할 트리거 생성하기
		
			INSERTING
			UPDATING
			DELETING
			
			raise_application_error(에러코드, 에러메세지);

				CREATE TABLE EMP_TRG AS SELECT * FROM EMP;

				CREATE OR REPLACE TRIGGER TRG_EMP_NODML
				BEFORE 
				INSERT OR UPDATE OR DELETE ON EMP_TRG
				BEGIN
					IF TO_CHAR(SYSDATE, 'DY') IN ('화', '수') THEN
						IF INSERTING THEN
							raise_application_error(-20000, '추가 불가');
						ELSIF UPDATING THEN
							raise_application_error(-2000, '수정 불가');
						ELSIF DELETING THEN
							raise_application_error(-2000, '삭제불가');
						ELSE
							raise_application_error(-2000, '작업불가');
						END IF;
							
					END IF;
				END;

				SELECT * FROM user_errors;
				SELECT * FROM user_triggers;

				INSERT INTO EMP_TRG (empno, ename, job) VALUES (9999, 'hjchoi','CLERK'); --에러남

				SELECT * FROM emp_trg WHERE ename = 'hjchoi';

				ALTER TRIGGER TRG_EMP_NODML disable;
				
				DROP trigger TRG_EMP_NODML ;


	4. DML 트리거의 제작 및 사용(AFTER)
		1) EMP_TRG_LOG 테이블 생성하기
		2) EMP_TRG_LOG 테이블에 EMP_TRG 테이블 데이터 변경 사항을 기록하는 트리거를 생성
			: NEW - INSERT 시 추가된 레코드 참조
			: OLD - UPDATE, DELETE 시 수정 전 레코드 참조

	5. 트리거 정보 조회
		- USER_TRIGGERS

	6. 트리거 변경

	7. 트리거 삭제
	
		CREATE OR REPLACE TRIGGER TRG_EMP_LOG
		AFTER 
		INSERT OR UPDATE OR DELETE ON EMP_TRG
		FOR EACH ROW 

		BEGIN 
			IF INSERTING THEN 
				INSERT INTO EMP_TRG_LOG (DML_TYPE, EMPNO, ENAME)
					VALUES ('INSERT', :NEW.EMPNO, :NEW.ENAME);
			ELSIF UPDATING THEN 
				INSERT INTO EMP_TRG_LOG (DML_TYPE, EMPNO, ENAME)
					VALUES ('UPDATE', OLD.EMPNO, OLD.ENAME);
			ELSIF DELETING THEN 
				INSERT INTO EMP_TRG_LOG (DML_TYPE, EMPNO, ENAME)
					VALUES ('DELETING', OLD.EMPNO, OLD.ENAME);
			END IF;
		END; 
	