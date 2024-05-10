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
		4) INTERSECT	( 교집합 )  : MySQL 에는 없음
		   

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
		SELECT sal, lpad(sal, 6, 0), ename, rpad(SUBSTR(ename,1,3), LENGTH(ename) , '*') FROM EMP ;
		SELECT LPAD('100', 10, 0) FROM dual;
		
	7. 두 문자열 데이터를 합치는 CONCAT 함수
		SELECT empno, ename, job, concat(ename, job) AS con FROM emp;
		SELECT empno, ename, job, ename || job AS con FROM emp;
		
	8. 문자열 데이터를 연결하는 || 연산자
	9. 특정 문자를 지우는 TRIM, LTRIM, RTRIM 함수
	
		SELECT '11' || trim( LEADING FROM '   abc   ') || '11' FROM dual;
		SELECT '11' || trim( TRAILING FROM '   abc   ') || '11' FROM dual;

		SELECT '11' || trim( LEADING '_' FROM '___abc___') || '11' FROM dual;
		SELECT '11' || trim( TRAILING '_' FROM '___abc___') || '11' FROM dual;

		SELECT '11' || ltrim('**abc**', '*') || '11' FROM dual;
		
		
숫자 데이터를 연산하고 수치를 조정하는 숫자 함수
	1. 특정 위치에서 반올림하는 ROUND
		SELECT round(123.4567, 1) FROM dual;
		
	2. 특정 위치에서 버리는 TRUNC 함수 - 절사
	3. 지정한 숫자와 가까운 정수를 찾는 CEIL, FLOOR 함수 - 올림, 버림
	4. 숫자를 나눈 나머지 값을 구하는 MOD 함수


날짜 데이터를 다루는 날짜 함수
	1. DATE형 데이터는 다음과 같이 간단한 연산이 가능합니다.
		1) 날짜 데이터 + 숫자
		2) 날짜 데이터 - 숫자
		3) 날짜 데이터 - 날짜 데이터
		4) 날짜 데이터 + 날짜 데이터 -- 안됨

	2. SYSDATE 함수를 사용하여 날짜 출력하기
	
		SELECT HIREDATE, 
				HIREDATE + 100, 
				HIREDATE - 100, 
				SYSDATE  - HIREDATE   -- 날짜끼리 빼는 건 가능 : 경과일수, 더하는건 안됨
		FROM emp;

	3. 몇 개월 이후 날짜를 구하는 ADD_MONTHS 함수
		- 월단위로 가감
		SELECT ADD_MONTHS(SYSDATE, 5) "5개월 후", 
		   ADD_MONTHS(SYSDATE, -5) "5개월전"  
		FROM DUAL;
		
	4. 두 날짜 간의 개월 수 차이를 구하는 MONTHS_BETWEEN 함수
		
		SELECT HIREDATE, CEIL(MONTHS_BETWEEN(SYSDATE, HIREDATE)) FROM EMP; 
		
	5. 돌아오는 요일, 달의 마지막 날짜를 구하는 NEXT_DAY, LAST_DAY 함수
	
		SELECT NEXT_DAY(SYSDATE, '목요일') FROM dual;
		SELECT NEXT_DAY(SYSDATE, '목') FROM dual;
		
		SELECT LAST_DAY(SYSDATE) FROM DUAL;  -- 이번달 마지막날짜

자료형을 변환하는 형 변환 함수
	1. 자동 형 변환, 암시적 형변환
		'2024-05-09' : 문자 -> DATE 형 변환
		'1000' : 문자 -> 연산시 -> NUMBER 형으로 자동 변환
		
		SELECT '1000' + 200 FROM dual;   
		--SELECT '1,000' + 200 FROM dual;   -- 이건 에러
		-> 형식을 오라클이 충분히 파악 가능한 경우
		
		TO_CHAR(..)  : 숫자, 날짜 -> 형식화된 문자열
		TO_NUMBER(..) : 문자로 되어 있는 형식화된 숫자
		TO_DATE(..) : 문자로 형식화된 날짜 -> 날짜
		
	2. 형변환 함수 - TO_CHAR, TO_NUMBER, TO_DATE 
	
		SELECT TO_NUMBER('1,000', '999,999') + 200 FROM dual; -- 1200
		SELECT empno, ename, SAL, TO_CHAR(SAL, '$999,999'), emp.* FROM emp;
		
		SELECT * FROM EMP WHERE HIREDATE BETWEEN '1981-01-01' AND '1981-12-31';
		
	

	3. 날짜, 숫자 데이터를 문자 데이터로 변환하는 TO_CHAR 함수
	4. 문자 데이터를 숫자 데이터로 변환하는 TO_NUMBER 함수
	5. 문자 데이터를 날짜 데이터로 변환하는 TO_DATE 함수
	
		형식
		
		YYYY  
		YY  : 90-05-09 => 2090-05-09  : 현재의 연도 앞자리 
		
		RRRR -> 4자리 연도
		RR -> 2자리 연도 : 90-05-09 => 1990-05-09  : 가장 가까운 앞자리 연도
		
		MM : 월
		DD : 일
		
		HH24 : 24시간 표기 시간
		HH12 / HH : 12시간 표기 시간
		
		MI : 분
		SS : 초
		
		SELECT * FROM emp 
		WHERE HIREDATE BETWEEN TO_DATE('1981-01-01', 'YYYY-MM-DD') AND TO_DATE('1981-12-31', 'YYYY-MM-DD');

		SELECT HIREDATE, TO_CHAR(HIREDATE, 'YYYY.MM.DD') FROM emp;
		SELECT HIREDATE, TO_CHAR(SYSDATE, 'YYYY.MM.DD HH24:MI:SS') FROM emp;
		
		SELECT TO_DATE('90-05-08', 'RR-MM-DD') FROM DUAL; -- 1990-05-08
		SELECT TO_DATE('90-05-08', 'YY-MM-DD') FROM DUAL; -- 2090-05-08

NULL 처리 함수
	1. NVL 함수
		NVL(컬럼, 기본값)
		
	2. NVL2 함수
		NVL2(컬럼, 널이아닐때, 널일때)
		SELECT EMPNO, COMM, NVL2(COMM, 'O', 'X' ) FROM EMP;

상황에 따라 다른 데이터를 반환하는 DECODE 함수와 CASE문
	1. DECODE 함수
	2. CASE 문
	
		SELECT EMPNO, ENAME, JOB, SAL, 
		DECODE(JOB, 
			'MANAGER', SAL * 1.1, 
			'SALESMAN', SAL * 1.05, 
			SAL * 1.03) "내년급여" 
		FROM EMP;

		SELECT EMPNO, ENAME, JOB, SAL, 
			CASE JOB WHEN 'MANAGER' THEN SAL * 1.1
					WHEN 'SALESMAN' THEN SAL * 1.05
					ELSE SAL * 1.03
			END AS "내년급여"
			FROM EMP;
			
		SELECT EMPNO, ENAME, JOB, SAL,
			CASE WHEN SAL >= 3000 THEN 'HIGH'
				WHEN SAL >= 2000 THEN 'MID'
				ELSE 'LOW'
			END AS "급여등급"
		FROM EMP;


다중행 함수와 데이터 그룹화

	하나의 열에 출력 결과를 담는 다중행 함수
	-  여러 행을 바탕으로 하나의 결과 값을 도출해 내기 위해 사용하는 함수입니다.
	- SUM 함수를 사용하여 급여 합계 출력하기
	- SUM 함수를 사용하여 사원 이름과 급여 합계 출력하기(오류 발생)
	- 자주 사용하는 다중행 함수

	1. 합계를 구하는 SUM 함수
	1) 데이터의 합을 구하는 함수
	2) 추가 수당 합계 구하기

	3) SUM 함수와 DISTINCT, ALL 함께 사용하기
		SELECT sum(ALL sal), sum(DISTINCT sal) FROM emp;

2. 데이터 개수를 구해 주는 COUNT 함수
	SELECT count(*) FROM emp;
	SELECT count(job) FROM emp;
	SELECT count(DISTINCT job) FROM emp;
	
	SELECT count(COMM) FROM emp;  -- 집계함수에서 null은 제외되고 집계됨


	1) 데이터 개수를 출력하는 데 사용
	2)  NULL이 데이터로 포함되어 있을 경우, NULL 데이터는 반환 갯수에서 제외

3. 최댓값과 최솟값을 구하는 MAX, MIN 함수
	SELECT min(sal), max(sal), avg(sal), max(HIREDATE), min(HIREDATE) FROM EMP;
	1) 부서 번호가 10번인 사원들의 최대 급여 출력하기
	2) 부서 번호가 10번인 사원들의 최소 급여 출력하기
	3) 부서 번호가 20인 사원의 입사일 중 제일 최근 입사일 출력하기
	4) 부서 번호가 20인 사원의 일사일 중 제일 오래된 입사일 출력하기

4. 평균 값을 구하는 AVG 함수
	1) 부서 번호가 30인 사원들의 평균 급여 출력하기
	2) DISTINCT로 중복을 제거한 급여 열의 평균 급여 구하기


결과 값을 원하는 열로 묶어 출력하는 GROUP BY절
	- 집합 연산자를 사용하여 각 부서별 평균 급여 출력하기

1. GROUP BY 절의 기본 사용법
	1) 여러 데이터에서 의미 있는 하나의 결과를 특정 열 값으로 묶어서 출력할 때 데이터를 '그룹화' 한다고 표현
	2) 문법 
	3) GROUP BY절에 명시하는 열은 여러 개 지정할 수 있습니다. 
	4) GROUP BY를 사용하여 부서별 평균 급여 출력하기 
	5) 부서 번호 및 직책별 평균 급여로 정렬하기

2. GROUP BY절을 사용할 떄 유의점
	1) 다중행 함수를 사용하지 않은 일반 열은 GROUP BY절에 명시하지 않으면 SELECT 절에서 사용할 수 없다는 것 
	2) GROUP BY절에 없는 열을 SELECT절에 포함했을 경우


	GROUP BY절에 조건을 줄 때 사용하는 HAVING절
	1) HAVING 절은 SELECT문에 GROUP BY절이 존재할 때만 사용할 수 있습니다. 
	2) GROUP BY절을 통해 그룹화된 결과 값의 범위를 제한하는 데 사용
	3) GROUP BY 절과 HAVING절을 사용하여 출력하기

1. HAVING절의 기본 사용법
	
	SELECT deptno, job, ROUND(avg(sal)) 
	FROM emp 
	GROUP BY deptno, job
	HAVING avg(sal) > 900 ;

2. HAVING절을 사용할 때 유의점
	1)  WHERE절은 출력 대상 행을 제한하고, HAVING절은 그룹화된 대상을 출력에서 제한 
		- HAVING 절은 집계 함수만 사용 가능 
	2) HAVING절 대신 WHERE절을 잘못 사용했을 경우


그룹화와 관련된 여러 함수
1. ROLLUP 함수를 적용한 그룹화

	SELECT deptno, job, ROUND(avg(sal)), MAX(SAL), MIN(SAL), COUNT(*) 
	FROM emp 
	GROUP BY ROLLUP (deptno, job)  -- 그룹 계
	ORDER BY DEPTNO  ;

2. CUBE 함수를 적용한 그룹화			

	SELECT deptno, job, ROUND(avg(sal)), MAX(SAL), MIN(SAL), COUNT(*) 
	FROM emp 
	GROUP BY CUBE (deptno, job)
	ORDER BY DEPTNO  ;
	

여러 테이블을 하나의 테이블처럼 사용하는 조인

	 - 카티전 프로덕트, 데카르트 곱 : 무작위 순서쌍
		SELECT * FROM EMP, DEPT;


조인
	1. 집합 연산자와 조인의 차이점
	2. 여러 테이블을 사용할 때의 FROM절

	3. 테이블의 별칭 설정

		From 테이블명 "별칭"  -- 별칭에 띄어쓰기 있으면 따옴표 사용 꼭
		From 테이블명 별칭

		 SELECT e.EMPNO , e.ENAME , e.JOB , d.DEPTNO , d.DNAME , d.LOC  
		FROM emp e, dept d
		WHERE e.deptno = d.deptno ;


조인 종류

	1. 등가 조인
		- 공통적인 값의 일치 조건을 가지고 테이블을 결합하는 방식
		- 동등조인, 내부(inner)조인

			SELECT * FROM emp_join e, DEPT_JOIN d
			WHERE e.DEPTNO = d.DEPTNO(+) ;	
		
	2. 비등가 조인
		- 등가조인이 아닌 조인
		- 암묵적 조인
		- 범위에 대한 조인
		
		SELECT e.empno, e.ENAME, e.SAL,  s.GRADE , s.LOSAL, s.HISAL  
		FROM emp e, SALGRADE s 
		WHERE e.SAL  BETWEEN s.LOSAL AND s.HISAL ;		
		
	3. 자체 조인
		SELECT e.empno, e.ename, e.job, e.mgr, m.ename, m.job  
		FROM emp e, emp m
		WHERE e.mgr = m.empno;
	
	4. 외부 조인
		--왼쪽 외부 조인 : 왼쪽 테이블은 오른쪽이 있던 없던 전체 다 나옴
		SELECT * FROM emp_join e, DEPT_JOIN d
		WHERE e.DEPTNO = d.DEPTNO(+) ;	  -- left outer join : left Main정보

		--오른쪽 외부 조인 : 오른쪽 테이블은 왼쪽이 있던 없던 전체 다 나옴
		SELECT * FROM emp_join e, DEPT_JOIN d
		WHERE e.DEPTNO(+) = d.DEPTNO ;	  -- right outer join : right Main정보

	5. Full OUTER JOIN
	
		SELECT * FROM EMP_JOIN e, DEPT_JOIN D
		WHERE e.deptno(+) = D.deptno
		union
		SELECT * FROM EMP_JOIN e, DEPT_JOIN D
		WHERE e.deptno = D.deptno(+);


	SQL-99 표준 문법으로 배우는 조인
	
	  - 등가조인(INNER JOIN)	
		
		1. NATUAL JOIN
			-- DEPTNO 동일컬럼
			SELECT e.EMPNO , e.ENAME , e.JOB, DEPTNO, d.DNAME , d.LOC 
				FROM EMP e NATURAL JOIN DEPT d ; -- DEPTNO 동일컬럼 FROM 절에 사용		
		
		2. JOIN ~ USING
			공통이름의 컬럼이 2개 이상일때 
			SELECT * FROM EMP JOIN DEPT USING(DEPTNO); -- JOIN 앞에 INNER 생략되어 있음
		
		3. JOIN ~ ON
		
		4. OUTER JOIN
		
		5. 세 개 이상의 테이블을 조인할 때	


트랜잭션 제어와 세션

하나의 단위로 데이터를 처리하는 트랜잭션
1. 트랜잭션이란?
	1) 관계형 데이터베이스에서 하나의 작업 또는 밀접하게 연관되어 있는 작업 수행을 위해 나눌 수 없는 최소 수행 단위
	2) SQL 문법 중 이러한 트랜잭션을 제어하는 데 사용하는 명령어를 TCL 이라고 합니다.
	
	

트랜잭션을 제어하는 명령어

	1. COMMIT : DB에 영구반영

	2. ROLLBACK : Commit 전에 실행하면 복구됨



세션과 읽기 일관성의 의미
	1. 세션이란?
	- 데이터베이스 접속을 시작으로 여러 데이터베이스에서 관련 작업을 수행한 후 접속을 종료하기까지 전체 기간

	2. 읽기 일관성의 중요성

수정 중인 데이터 접근을 막는 LOCK
	1. LOCK 이란?
	- 특정 세션에서 조작중인 데이터는 트랜잭션이 완료(COMMIT, ROLLBACK)되기 전까지 다른 세션에서 조작할 수 없는 상태

	2. LOCK 개념 살펴보기


SQL
	- DDL : Data Definition Language
		데이터의 구조 정의하는 언어
		실행하자마자 COMMIT 바로 실행 -> 바로 영구 반영
		TRUNCATE 도 DDL
		
	- DML : Data Manipulation Language
		INSERT, UPDATE, DELETE, SELECT - DQL
		COMMIT ROLLBACK 이 적용될 수 있음
		
	- DCL : Data Control Language
		GRANT, REVOKE
		COMMIT, ROLLBACK - TCL
		
세션 : 연결 시작, 종료 