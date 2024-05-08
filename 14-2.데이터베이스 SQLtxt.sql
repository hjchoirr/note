04 SELECT 문의 기본 형식 

실습용 테이블 살펴보기
	1. EMP 테이블 : 직원정보
	2. DEPT 테이블 : 부서정보
	3. SALGRADE 테이블 : 급여 등급 정보

데이터를 조회하는 3가지 방법
	1. 셀렉션(행) : 행 단위로 원하는 데이터를 조회하는 방식 
	2. 프로젝션(열) : 열 단위로 데이터를 조회하는 방식 
	3. 행과 열을 모두 선별할 경우 셀렉션과 프로젝션을 함께 사용할 수 있음
	4. 조인 : 다른 테이블에 데이터를 공통적인 컬럼을 통해 연결하여 조회하는 방식

SQL의 기본 뼈대, SELECT절과 FROM 절
	1. 형식 SELECT [ALL|DISTINCT] 조회할 컬럼 FROM 테이블명;  ( 모든컬럼: * )
	2. SELECT
	3. FROM 
	
	DML(Data Manipulation Language : 데이터 조작어) - INSERT UPDATE, DELETE, SELECT(DQL: 데이터질의어,쿼리) 
	

주석 --

대소문자 구분 

	: 오라클은 대문자 사용 권장 ( 소문자도 다 대문자로 인식함 )
	
	: MySQL 
		- 테이블, 컬럼명 : 대소문자 구분함
		- 테이블명 : 윈도우OS 대소문자 구분 없고, 리눅스와 맥은 대소문자 구분 함
	

중복 데이터를 삭제하는 DISTINCT
	1. ALL - 생략
	2. DISTINCT 


한눈에 보기 좋게 별칭 설정하기
	1. 실습1
	
	- 열에 연산식을 사용하여 출력하기
	- 컬럼명 별칭을 지정하는 방식 
		AS "별 칭"  (띄어쓰기 있으면 반드시 큰따옴표 사용)
	
	2. 실습2 

원하는 순서로 출력 데이터를 정렬하는 ORDER BY
	: 오름차순 ORDER BY 컬럼명 ASC
	: 내림차순 ORDER BY 컬럼명 DESC
	
	ORDER BY 컬럼1 DESC, 컬럼2 ASC, ...
	
	1. 실습
	2. 주의 사항 : - Full Scan 하면 정렬시 성능저하 문제, 인덱스 통해 해소 가능 


더 정확하고 다양하게 결과를 출력하는 WHERE절과 연산자

	필요한 데이터만 쏙 출력하는 WHERE절 : 조건절

	1. 문법 
	2. 실습 
		
	여러 개 조건식을 사용하는 AND, OR 연산자
	1. AND 연산자
	2. OR 연산자 

	

	연산자 종류와 활용 방법 알아보기
	1. 산술 연산자 + - * / 
	
		(참고) nvl, nvl2
			null은 연산이 안됨
			SELECT EMPNO, ENAME, SAL, COMM, SAL * 12  + nvl(COMM, 0) 연봉 FROM EMP  ;
		
		(참고) DUAL 테이블 : 가상의 테이블
			SELECT 100 * 3 FROM DUAL;
		
	2. 비교 연산자 >, <, >=, <=
	3. 등가 비교 연산자 
	
		= : 같다
		<> : 같지않다 ( != , ^= 이것도 됨)
	
	4. 논리 부정 연산자 
		NOT 
		SELECT * FROM EMP WHERE  NOT SAL >= 2000;
	5. IN 연산자 
		- DEPTNO IN (10, 20, 30)
		- DEPTNO NOT IN (10,20)
		
	6. BETWEEN A AND B 연산자
		SELECT * FROM EMP WHERE HIREDATE >= '1981-01-01' AND HIREDATE <= '1981-12-31';
		SELECT * FROM EMP WHERE HIREDATE BETWEEN '1981-01-01' AND '1981-12-31';
	
	7. LIKE 연산자와 와일드 카드
		1) 문법
			컬럼명 LIKE '키워드' 
			컬럼명 LIKE '키워드%' 
			컬럼명 LIKE '%키워드%' 
			
		2) _, %  : _ 한글자
		
		3) 와일드 카드 문자가 데이터의 일부일 경우 - ESCAPE 절 사용 
		
		CREATE TABLE EMP_LIKE AS SELECT * FROM EMP;	

		INSERT INTO EMP_LIKE (EMPNO, ENAME, JOB) 
			VALUES (9998, 'N%MAE', 'NONE');

		SELECT * FROM EMP_LIKE WHERE ENAME LIKE '_\%%' ESCAPE '\' ;    -- '\\''

	8. IS NULL 연산자
	
		컬럼명 IS NULL 

		오라클 : '' , NULL 동일 
		
			INSERT INTO EMP_LIKE (EMPNO, ENAME, JOB) VALUES (9997, 'NMAE', '');
			SELECT * FROM EMP_LIKE WHERE  job IS NULL ;
			
		MySQL : '' - 빈문자열, NULL - 값없음
		
	
	9. 집합 연산자
		1) UNION        ( 합집합 )
			- 중복 X
		2) UNION ALL
		3) MINUS        ( 차집합 )  : MySQL 에는 없음
		4) INSERSECT	( 교집합 )  : MySQL 에는 없음


데이터 처리와 가공을 위한 오라클 함수

오라클 함수의 종류

	1. 내장 함수 
		1) 단일행 함수(single-row function) : 데이터가 한 행씩 입력되고 입력된 한 행당 결과가 하나씩 나오는 함수
			- REPLACE 같은거
			
		2) 다중행 함수(multiple-row function): 여러 행이 입력되어 하나의 행의 결과로 반환되는 함수
			- SUM 같은거

	2. 사용자 정의 함수 



문자 데이터를 가공하는 문자함수

	1. 대,소문자를 바꿔 주는 UPPER, LOWER, INITCAP 함수
		UPPER() : 소문자 -> 대문자
		LOWER() : 대문자 -> 소문자
		INITCAP() : 첫글자만 대문자로
		
		SELECT * from EMP where UPPER(ENAME) like UPPER('scott');

	2. 문자열 길이를 구하는 LENGTH 함수
		참고) DUAL 테이블 
		LENGTH : 문자열의 글자수
		LENGTHB : 문자열의 바이트수
		
		SELECT LENGTH('가나다라'), LENGTH('abcd') FROM DUAL;   --> 4, 4
		SELECT LENGTHB('가나다라'), LENGTHB('abcd') FROM DUAL; --> 12, 4
		
		참고) MySQL : LENGTH - 문자열의 바이트수
					  CHAR_LENGTH - 문자열의 글자수
		

	3. 문자열 일부를 추출하는 SUBSTR 함수
	
		SUBSTR(컬럼, 시작위치, 추출길이)
		SUBSTR(컬럼, 시작위치) - 시작위치 부터 끝까지
			
		- 시작위치 : 1부터
		- 시작위치 양수 : 처음 부터
		- 시작위치 음수 : 마지막 부터

	4. 문자열 데이터 안에서 특정 문자 위치를 찾는 INSTR 함수
	
		- LIKE 검색 대신 사용
		
		INSTR(컬럼, '찾는키워드') : 위치 1 부터 찾기, 못찾으면 0
		INSTR(컬럼, '찾는키워드', 찾기 시작 위치) 
		
	5. 특정 문자를 다른 문자로 바꾸는 REPLACE 함수
		REPLACE(컬럼, '찾는문자열', '대체할 문자열')
		
	6. 데이터의 빈 공간을 특정 문자로 채우는 LPAD, RPAD 함수
		1) LPAD : 왼쪽 패딩
		2) RPAD : 오른쪽 패딩
		
		LPAD(컬럼, 자리수, '채울문자')
		RPAD(컬럼, 자리수, '채울문자')
		SELECT  ENAME, LPAD( ENAME, 10, 'AA') AS aa  FROM EMP ;
		
	7. 두 문자열 데이터를 합치는 CONCAT 함수
	8. 문자열 데이터를 연결하는 || 연산자
	9. 특정 문자를 지우는 TRIM, LTRIM, RTRIM 함수


숫자 데이터를 연산하고 수치를 조정하는 숫자 함수
	1. 특정 위치에서 반올림하는 ROUND
	2. 특정 위치에서 버리는 TRUNC 함수
	3. 지정한 숫자와 가까운 정수를 찾는 CEIL, FLOOR 함수
	4. 숫자를 나눈 나머지 값을 구하는 MOD 함수


날짜 데이터를 다루는 날짜 함수
	1. DATE형 데이터는 다음과 같이 간단한 연산이 가능합니다.
		1) 날짜 데이터 + 숫자
		2) 날짜 데이터 - 숫자
		3) 날짜 데이터 - 날짜 데이터
		4) 날짜 데이터 + 날짜 데이터

	2. SYSDATE 함수를 사용하여 날짜 출력하기
	3. 몇 개월 이후 날짜를 구하는 ADD_MONTHS 함수
	4. 두 날짜 간의 개월 수 차이를 구하는 MONTHS_BETWEEN 함수
	5. 돌아오는 요일, 달의 마지막 날짜를 구하는 NEXT_DAY, LAST_DAY 함수



자료형을 변환하는 형 변환 함수
	1. 자동 형 변환, 암시적 형변ㅅ환
	2. 형변환 함수 - TO_CHAR, TO_NUMBER, TO_DATE 
	3. 날짜, 숫자 데이터를 문자 데이터로 변환하는 TO_CHAR 함수
	4. 문자 데이터를 숫자 데이터로 변환하는 TO_NUMBER 함수
	5. 문자 데이터를 날짜 데이터로 변환하는 TO_DATE 함수

NULL 처리 함수
	1. NVL 함수
	2. NVL2 함수

상황에 따라 다른 데이터를 반환하는 DECODE 함수와 CASE문
	1. DECODE 함수
	2. CASE 문