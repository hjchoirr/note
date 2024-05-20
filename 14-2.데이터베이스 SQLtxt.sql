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
		WHERE e.SAL  BETWEEN s.LOSAL AND s.HISAL ;	--  e.SAL >= s.LOSA AND e.SAL<= s.HISAL
		
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
			공통이름의 컬럼이 1개 이상일때 
			SELECT * FROM EMP JOIN DEPT USING(DEPTNO); -- JOIN 앞에 INNER 생략되어 있음
		
		3. JOIN ~ ON
			공통컬럼의 이름이 동일하지 않은 경우
			SELECT * FROM emp e JOIN dept d ON e.DEPTNO = d.DEPTNO ; 
		
		4. OUTER JOIN
		
			왼쪽 외부조인 Left Outer Join
			오른쪽 외부조인 Right Outer Join
			
				SELECT * FROM emp_join e LEFT OUTER JOIN dept_join d ON e.deptno = d.deptno;
				SELECT * FROM emp_join e RIGHT OUTER JOIN dept_join d ON e.deptno = d.deptno;
			
			FULL OUTER JOIN
			
				SELECT * FROM EMP_JOIN E FULL JOIN dept_join d ON e.deptno = d.deptno;
			
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



SQL문 속 또 다른 SQL문, 서브 쿼리


서브 쿼리
	SELECT (1) FROM (2) WHERE (3);
	
	(1) 스칼라 서브쿼리 (스칼라: 단일값)
	(2) 인라인뷰 : 가상의 테이블
	(3) 상관부속 질의

	1. 특징 
		1) 서브 쿼리는 연산자와 같은 비교 또는 조회 대상의 오른쪽에 놓이며 괄호 ( )로 묶어서 사용합니다.
		2) 특수한 몇몇 경우를 제외한 대부분의 서브쿼리에서는 ORDER BY 절을 사용할 수 없습니다.
		3) 서브쿼리의 SELECT절에 명시한 열은 메인쿼리의 비교 대상과 같은 자료형과 같은 개수로 지정해야 합니다. 즉 메인쿼리의 비교 대상 데이터가 하나라면 서브쿼리의 SELECT절 역시 같은 자료형인 열을 하나 지정해야 합니다.
		4) 서브쿼리에 있는 SELECT문의 결과 행 수는 함께 사용하는 메인쿼리의 연산자 종류와 호환 가능해야 합니다. 예를 들어 메인쿼리에 사용한 연산자가 단 하나의 데이터로만 연산이 가능한 연산자라면 서브쿼리의 결과 행 수는 반드시 하나여야 합니다.


	실행 결과가 하나인 단일행 서브 쿼리

		SELECT * FROM EMP 
		WHERE SAL < (SELECT MIN(SAL) FROM EMP WHERE DEPTNO = 30);  --단일행 서브쿼리 = 스칼라 서브쿼리

		1. 단일행 서브 쿼리(single-row subquery)는 실행 결과가 단 하나의 행으로 나오는 서브쿼리를 뜻합니다.
		2. 서브쿼리에서 출력되는 결과가 하나이므로 메인쿼리와 서브쿼리 결과는 다음과 같이 단일행 연산자를 사용하여 비교
		3. 단일행 서브쿼리와 날짜형 데이터
		4. 단일행 서브쿼리와 함수


	실행 결과가 여러 개인 다중행 서브 쿼리
	
		SELECT * FROM EMP WHERE SAL IN ( SELECT MAX(SAL) FROM EMP GROUP BY DEPTNO);
		
		1. 다중행 서브쿼리(multiple-row subquery)는 실행 결과 행이 여러 개로 나오는 서브쿼리를 가리킵니다.
		2. 단일행 서브쿼리와 달리 서브쿼리 결과가 여러 개이므로 단일행 연산자는 사용할 수 없고 다중행 연산자를 사용해야 메인쿼리와 비교할 수 있습니다.
		3. IN 연산자
		4. ANY, SOME 연산자
		
			: 쿼리 결과값이 하나라도 참이면 참
			: ANY, SOME 동일
								
				SELECT * FROM EMP WHERE SAL < ( SELECT MAX(SAL) FROM EMP WHERE DEPTNO = 30 );
				SELECT * FROM EMP WHERE SAL < ANY (SELECT SAL FROM EMP WHERE DEPTNO = 30 ); -- 위와 동일

				SELECT * FROM EMP WHERE SAL > ( SELECT MIN(SAL) FROM EMP WHERE DEPTNO = 30);
				SELECT * FROM EMP WHERE SAL > ANY (SELECT SAL FROM EMP WHERE DEPTNO = 30); -- 위와 동일
				
		5. ALL 연산자
		
			- 쿼리 결과값이 모두 참이어야 참
			
				SELECT * FROM EMP WHERE SAL > ( SELECT MAX(SAL) FROM EMP WHERE DEPTNO = 30); 
				SELECT * FROM EMP WHERE SAL > ALL (SELECT SAL FROM EMP WHERE DEPTNO = 30); --위와 동일
			
		6. EXISTS 연산자
		
			- 서브쿼리의 레코드가 있으면 참
				SELECT * FROM EMP WHERE EXISTS ( SELECT * FROM DEPT WHERE DEPTNO = 30 );
				SELECT * FROM EMP WHERE NOT EXISTS ( SELECT * FROM DEPT WHERE DEPTNO = 50 );
				
	비교할 열이 여러 개인 다중열 서브쿼리
		1. 서브쿼리의 SELECT절에 비교할 데이터를 여러 개 지정하는 방식
		2. 다중열 서브쿼리 사용하기

	FROM절에 사용하는 서브쿼리와 WITH절
	
		1. 인라인 뷰(inline view)
			SELECT * FROM (SELECT * FROM EMP WHERE DEPTNO IN (10,20));
			
		2. WITH절 사용하기 : only Oracle
			
			WITH
			E AS (SELECT EMPNO, ENAME, JOB, SAL, DEPTNO 
					FROM EMP WHERE DEPTNO IN (10, 20)),
			D AS (SELECT * FROM DEPT)
			SELECT E.EMPNO, E.ENAME, D.DNAME
			FROM E,D
			WHERE E.DEPTNO = D.DEPTNO AND E.SAL >= 2000; 
			
			-- 동일함
			SELECT E.EMPNO, E.ENAME, D.DNAME
			FROM
			(SELECT EMPNO, ENAME, JOB, SAL, DEPTNO 
					FROM EMP WHERE DEPTNO IN (10, 20)) E,
			(SELECT * FROM DEPT) D
			WHERE E.DEPTNO = D.DEPTNO AND E.SAL >= 2000;
		
		3. 상호 연관 서브쿼리
		
			SELECT * FROM EMP e WHERE SAL > ( SELECT MIN(SAL) FROM EMP WHERE DEPTNO = e.DEPTNO);

	SELECT 절에 사용하는 서브쿼리
		1. 스칼라 서브쿼리(scalar subquery)
			스칼라 : 단일값 - 단일행 서브쿼리
			
			SELECT EMPNO, ENAME, ( SELECT DNAME FROM DEPT WHERE DEPTNO = e.DEPTNO ) 
			FROM EMP e ;
			
		2. SELECT절에 서브쿼리 사용하기
		3. SELECT 절에 명시하는 서브쿼리는 반드시 하나의 결과만 반환하도록 작성
		

데이터를 추가, 수정, 삭제하는 데이터 조작어 (DML - Data Manipulation Language )

	테이블에 데이터 추가하기
	
		1. 실습 - 테이블 생성하기
		
			CREATE TABLE DEPT_TEMP AS SELECT * FROM DEPT
			
		2. INSERT문 실습 전 유의점
		3. 문법 
		4. 실습
			1) DEPT_TEMP 테이블에 데이터 추가하기
			2) INSERT문으로 데이터 입력하기(열 지정을 생략할 때)

		5.  테이블에 NULL 테이터 입력하기
			1) NULL을 지정하여 입력하기
			2) 빈 공백 문자열로 NULL을 입력하기
			3) 열 데이터를 넣지 않는 방식으로 NULL 데이터 입력하기

		6. 테이블에 날짜 데이터 입력하기
			1) INSERT문으로 날짜 데이터 입력하기(날짜 사이에 / 입력)
			2) INSERT문으로 날짜 데이터 입력하기(날짜 사이에 - 입력)
			
			3) TO_DATE 함수를 사용하여 날짜 데이터 입력하기
				
				TO_DATE('2024//05//13', 'YYYY//MM//DD')
				
				(참고)
				TO_NUMBER(..)
				TO_CHAR(..)
			4) SYSDATE를 사용하여 날짜 데이터 입력하기

		7. 서브 쿼리를 사용하여 한 번에 여러 데이터 추가하기
			1) 서브 쿼리로 여러 데이터 추가하기 
			2) INSERT문에서 서브쿼리를 사용할 때 유의할 점
				- VALUES 절은 사용하지 않는다.
				- 데이터가 추가되는 테이블의 열 개수와 서브쿼리의 열 개수가 일치해야 한다.
				- 데이터가 추가되는 테이블의 자료형과 서브쿼리의 자료형이 일치해야 한다.
		
	테이블에 있는 데이터 수정하기
		- UPDATE문을 사용

		1. UPDATE 문의 기본 사용법
			UPDATE 테이블명
			SET
			  컬럼명 = 값,
			  컬럼명 = 값,
			  컬럼명 = 값
			WHERE 조건식;
			  
		2. 데이터 전체 수정하기
			참고) ROLLBACK
		3. 데이터 일부분 수정하기


	서브쿼리를 사용하여 데이터 수정하기
		1. 서브쿼리로 데이터 일부분 수정하기
			UPDATE DEPT_TEMP3 
				SET (dname, loc) = ( SELECT DNAME, LOC FROM DEPT WHERE DEPTNO = 40)
			WHERE DEPTNO = 40;
		
		2. 열 하나하나를 수정하는 경우
		3. UPDATE문의 WHERE절에 서브쿼리 사용하기


	테이블에 있는 데이터 삭제하기
		1. DELETE문의 기본 형식
		2. WHERE절을 사용하여 데이터 일부분만 삭제하기
		3. 테이블에 있는 전체 데이터 삭제하기		
		

데이터 정의어

	객체를 생성, 변경, 삭제하는 데이터 정의어
		1. 데이터 정의어(DDL - Data Definition Language)
		2.  데이터베이스 데이터를 보관하고 관리하기 위해 제공되는 여러 객체(object)의 생성, 변경, 삭제 관련 기능을 수행
		3. 데이터 정의어를 사용할 때 유의점
			1) 데이터 정의어를 실행하면 자동으로 COMMIT되기 때문에 이전에 사용한 데이터 조작어는 영구히 데이터베이스에 반영
			2) ROLLBACK을 통한 실행 취소가 불가

	테이블을 생성하는 CREATE
		
		1. 오라클 데이터베이스 테이블 객체를 생성하는 데 사용하는 명령어
		2. 작성방법
		
			CREATE TABLE 테이블명 (
				컬럼명 자료형 [제약조건],
				컬럼명 자료형 [제약조건],
				컬럼명 자료형 [제약조건]
			);
			
		3. 테이블 이릅 생성 규칙
			- 알파벳, 숫자, 특수문자($,_,#)
			- 숫자는 앞에 안됨
			- 예약어 사용 안됨
			
		4. 열 이름 생성 규칙
		
			- 테이블과 열이름 대소문자 구분 없음, 단어와 단어사이 구분 _ 사용
		
		5. 실습1 
		
		
		6. 실습2 - 기본 테이블 열 구조와 데이터를 복사하여 새 테이블 생성하기
			1) 다른 테이블을 복사하여 테이블 생성하기
			2) 다른 테이블의 일부를 복사하여 테이블 생성하기
			3) 다른 테이블의 구조만 복사하여 테이블 생성하기


	테이블을 변경하는 ALTER
		1. 테이블에 열 추가하는 ADD
			- ALTER 명령어로 HP 열 추가하기
			  ALTER TABLE 테이블명 ADD 컬럼명 자료형 [제약조건]

				ALTER TABLE EMP_DDL 
				  ADD (HP VARCHAR2(20), MP VARCHAR2(20));

			  
		2. 열 이름을 변경하는 RENAME COLUMN 
				
			ALTER TABLE EMP_DDL RENAME COLUMN MP TO CP;			  
				
		3. 열의 자료형을 변경하는 MODIFY
		
			ALTER TABLE EMP_DDL MODIFY EMPNO NUMBER(5) ;
		
		4. 특정 열을 삭제할 때 사용하는 DROP
		
			ALTER TABLE EMP_DDL DROP COLUMN HP;
			
	테이블 이름을 변경하는 RENAME
		RENAME EMP_DDL TO EMP_DDL2;
		
	테이블의 데이터를 삭제하는 TRUNCATE
	
		truncate table emp_ddl2;
		
	테이블을 삭제하는 DROP

자료형		
	문자
		CHAR(길이) : 고정 길이 문자
		VARCHAR2(길이) : 최대 길이 만큼 가변적인 문자형 / 한줄 텍스트
		NVARCHAR2(길이)
		
		CLOB - Character Large Object : 최대 4GB 여러줄 텍스트
		
	숫자
		NUMBER(전체자리수, 소수점 자리수)
		
	날짜
		DATE
		
	기타
	  BLOB - 최대 4GB 
	  BFILE


객체 종류

데이터베이스를 위한 데이터를 저장한 데이터 사전
	1. 데이터 사전이란?
	
		DICTIONARY 테이블
		동의어 DICT
		
		SELECT * FROM DICTIONARY WHERE TABLE_NAME LIKE '%TABLES';
		SELECT * FROM DICT;
		SELECT * FROM USER_TABLES;
		
		1) 데이터베이스를 구상하고 운영하는 데 필요한 모든 정보를 저장하는 특수한 테이블
		2) 데이터베이스가 생성되는 시점에 자동으로 만들어집니다.

	2. 접두어
		1) USER_XXXX - 현재 데이터베이스에 접속한 사용자가 소유한 객체 정보
		2) ALL_XXXX -  사용 가능한 모든 객체 정보, 현재 사용자가 접근가능한 모든 정보
		3) DBA_XXXX - 데이터베이스 관리를 위한 정보(데이터베이스 관리 권한을 가진 SYSTEM, SYS 사용자만 열람 가능)
		4) V$_XXXX - 데이터베이스 성능 관련 정보(X$_XXXX 테이블의 뷰)

	3. 요약
		1) 데이터 사전은 오라클 데이터베이스를 구성하고 운영하는 데이터를 저장하는 특수한 테이블로서 오라클 사용자가 직접 접근할 수 없습니다.
		2) SELECT문으로 데이터를 명령할 수 있도록 데이터 사전 뷰를 제공
		3) 대표적인 데이터 사전 뷰 중 현재 접속한 사용자가 소유하는 테이블 목록을 보기 위해서는 USER_TABLES를 사용
		4) 사용자가 소유하는 테이블을 포함해 다른 사용자가 소유한 테이블 중 현재 사용자에게 사용 허가가 되어 있는 테이블을 보기 위해서는 ALL_TABLES를 사용


더 빠른 검색을 위한 인덱스
	1. 인덱스란?
		1) 색인이라는 뜻의 인덱스(index)
			- 정렬이 필요한 항목
		
		2) 책 내용을 찾는 것과 마찬가지로 오라클 데이터베이스에서 데이터 검색 성능 향상을 위해 테이블 열에 사용하는 객체를 뜻
		3) 인덱스 사용 여부에 따라 데이터 검색 방식을 Table Full Scan, Index Scan으로 구분합니다.
			- Table Full Scan : 처음부터 끝까지 검색하여 원하는 데이터를 찾는 방식
			- Index Scan : 인덱스를 통해 데이터를 찾는 방식
			
		4) SCOTT 계정이 소유한 인덱스 정보 알아보기(SCOTT 계정일 때)
		5) SCOTT 계정이 소유한 인덱스 컬럼 정보 알아보기
		6) 인덱스는 사용자가 직접 특정 테이블의 열에 지정할 수도 있지만 열이 기본키(primary key) 또는 고유키(unique key)일 경우에 자동으로 생성합니다.


인덱스 생성
	1. 사용법 
	
		CREATE INDEX 인덱스명 ON 테이블명(컬럼명 [ASC|DESC], 컬럼명..);
		
	2. EMP 테이블의 SAL열에 인덱스를 생성하기
		CREATE INDEX IDX_EMP_SAL ON EMP (SAL);

	3. 생성된 인덱스 살펴보기(USER_INDEXES, USER_IND_COLUMNS 사용)
	
		SELECT * FROM USER_INDEXES;
		SELECT * FROM USER_IND_COLUMNS;

인덱스 삭제
	1. 사용법 
		
		DROP INDEX IDX_EMP_SAL;

	참고)
		1) 인덱스 생성이 항상 좋은 결과로 이어지지는 않습니다. 정확한 데이터 분석에 기반을 두지 않는 인덱스의 무분별한 생성은 오히려 성능을 떨어트리는 원인이 되기도 합니다. 
		2) 인덱스는 데이터 종류, 분포도, 조회하는 SQL의 구성, 데이터 조작 관련 SQL문의 작업 빈도, 검색 결과가 전체 데이터에서 차지하는 비중 등 많은 요소를 고려하여 생성합니다.
		3) 선택도가 낮을 수록 인덱스 적용이 좋고 선택도가 높을 수록 인덱스는 불리하다


테이블처럼 사용하는 뷰
	1. 뷰란?
	- 가상테이블(virtual table)로 부르는 뷰(view)는 하나 이상의 테이블을 조회하는 SELECT 문을 저장한 객체를 뜻
	2. 뷰의 사용 목적(편리성)
		1) 편리성 : SELECT문의 복잡도를 완화하기 위해
		2) 보안성 : 테이블의 특정 열을 노출하고 싶지 않을 경우

	3. 뷰 생성
		
CREATE VIEW 뷰이름 AS SELECT 문;
		
		1) 권한부여
			CREATE VIEW
			SYSTEM 계정
			GRANT CREATE VIEW TO SCOTT;
			
		2) 뷰 생성하기
			CREATE VIEW vw_emp
			AS ( SELECT e.empno, e.ename, e.job, e.sal, d.*, g.*
					FROM emp e LEFT OUTER JOIN DEPT d ON e.deptno = d.deptno
								LEFT OUTER JOIN SALGRADE g ON e.sal BETWEEN g.losal AND g.hisal
				)		
		3) 생성한 뷰 확인하기
		
			SELECT * FROM USER_VIEWS ;
			
			뷰 변경하기 
			CREATE OR REPLACE VIEW vw_emp AS (
				SELECT e.empno, e.ename, e.job, e.sal, d.*, g.*
					FROM emp e LEFT OUTER JOIN DEPT d ON e.deptno = d.deptno
								LEFT OUTER JOIN SALGRADE g ON e.sal BETWEEN g.losal AND g.hisal
			);
			
		4) 생성한 뷰 조회하기
		5) 뷰 삭제
			DROP VIEW 뷰이름;
		
	- 뷰는 실제 데이터가 아닌 데이터가 아닌 SELECT문만 저장하므로 뷰를 삭제해도 테이블이나 데이터가 삭제되는 것은 아닙니다.


인라인 뷰를 사용한 TOP-N SQL문
	1. 인라인 뷰(서브쿼리 사용)
	2. 인라인 뷰(WITH절 사용

	ROWNUM : 레코드 순번 ( 오라클 only ) 
	
	SELECT ROWNUM, e.* FROM EMP e;
	SELECT ROWNUM, e.* FROM EMP e ORDER BY sal DESC; -- ROWNUM 뒤죽박죽
	
	SELECT ROWNUM, e.* FROM ( SELECT * FROM emp ORDER BY sal DESC) e; -- ROWNUM 정돈됨
	
	--ROWNUM 이용하여 3개 레코드만 가져오기 
	SELECT ROWNUM, e.* FROM ( SELECT * FROM emp ORDER BY sal DESC) e WHERE ROWNUM <= 3;

	SELECT ROWNUM, e.* 
		FROM ( SELECT * FROM emp ORDER BY sal DESC) e 
	 WHERE ROWNUM >= 3 AND ROWNUM < 10;   --  보다 작은 조건 가능하나 보다 큰 조건은 불가능
	 
	-- 범위 검색 가능하려면 이렇게 하기 
	SELECT * FROM 
		(SELECT ROWNUM num, e1.* 
	FROM ( SELECT * FROM emp ORDER BY sal DESC) e1) e 
	WHERE E.num >= 3 AND e.num < 10;
	
	


규칙에 따라 순번을 생성하는 시퀀스
	1. 시퀀스란?
		1) 오라클 데이터베이스에서 특정 규칙에 맞는 연속 숫자를 생성하는 객체

	2. 시퀀스 생성
		1) 사용법 
		2) DEPT 테이블을 사용하여 DEPT_SEQUENCE 테이블 생성하기
		3) DEPT_SEQUENCE 테이블에 DEPTNO가 10씩 증가할 수 있는 시퀀스 생성
		4) 시퀀스 생성을 확인

	3. 시퀀스 사용
		1) [시퀀스 이름.CURRVAL]과 [시퀀스 이름.NEXTVAL]을 사용
		2) CURRVAL은 시퀀스에서 마지막으로 생성한 번호를 반환
		3) NEXTVAL는 다음 번호를 생성


	4. 시퀀스 수정
	5. 시퀀스 삭제


공식 별칭을 지정하는 동의어
	1. 동의어란?
		1) 테이블-뷰-시퀀스 등 객체 이름 대신 사용할 수 있는 다른 이름을 부여하는 객체
		2) 테이블 이름이 너무 길어 사용이 불편할 때 좀 더 간단하고 짧은 이름을 하나 더 만들어 주기 위해 사용합

	2. 사용법
	3. 권한 부여하기(SQL*PLUS)
	4. 동의어 생성
	5. 동의어 삭제
	
	
	