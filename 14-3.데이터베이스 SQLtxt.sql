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
	

중복 데이터를 삭제하는 DISTINCT
	1. ALL
	2. DISTINCT 


한눈에 보기 좋게 별칭 설정하기
	1. 실습1
	- 열에 연산식을 사용하여 출력하기
	- 별칭을 지정하는 방식 
	2. 실습2 

원하는 순서로 출력 데이터를 정렬하는 ORDER BY
	1. 실습
	2. 주의 사항