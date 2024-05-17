규칙에 따라 순번을 생성하는 시퀀스
	1. 시퀀스란?
		1) 오라클 데이터베이스에서 특정 규칙에 맞는 연속 숫자를 생성하는 객체

	2. 시퀀스 생성
		시퀀스 생성 권한 필요함
			system 계정에서
			SQL> grant create sequence to scott;
			
		1) 사용법 
			CREATE SEQUENCE 시퀀스이름
				INCREMENT BY 숫자 -- 숫자씩 증가
				START WITH 숫자 -- 숫자에서부터 증가 시작
				MAXVALUE 숫자 -- 최대 숫자만큼 증가
				MINVALUE 숫자 -- 최소 숫자 무터 시작
				CYCLE -- 최대숫자 도달시  MINVALUE 부터 다시 시작 | NOCYCLE ( default 최대숫자 도달시 에러)
				CACHE 숫자 -- default (CACHE 20 -> 20개의 숫자를 미리 생성해 놓는다) | NOCACHE
				
			ALTER SEQUENCE
			DROP SEQUENCE

		
		2) DEPT 테이블을 사용하여 DEPT_SEQUENCE 테이블 생성하기
			CREATE TABLE dept_sequence AS SELECT * FROM dept WHERE 1 <> 1;
		
		3) DEPT_SEQUENCE 테이블에 DEPTNO가 10씩 증가할 수 있는 시퀀스 생성

			CREATE SEQUENCE SEQ_DEPT_SEQUENCE 
				INCREMENT BY 10 START WITH 10 
				MAXVALUE 90 MINVALUE 0;

		4) 시퀀스 생성을 확인
			SELECT * FROM USER_SEQUENCES;
			( SELECT * FROM DICT; )

	3. 시퀀스 사용
	
		1) [시퀀스 이름.CURRVAL]과 [시퀀스 이름.NEXTVAL]을 사용
		2) CURRVAL은 시퀀스에서 마지막으로 생성한 번호를 반환
		3) NEXTVAL는 다음 번호를 생성

		INSERT INTO DEPT_SEQUENCE VALUES (SEQ_DEPT_SEQUENCE.NEXTVAL, 'DATABASE', 'SEOUL');
		
		SELECT SEQ_DEPT_SEQUENCE.CURRVAL FROM dual;
		
	4. 시퀀스 수정
	
		ALTER SEQUENCE seq_dept_sequence CYCLE  nocache;
		
	5. 시퀀스 삭제
		DROP SEQUENCE seq_dept_sequence;


공식 별칭을 지정하는 동의어

	1. 동의어란?
		1) 테이블-뷰-시퀀스 등 객체 이름 대신 사용할 수 있는 다른 이름을 부여하는 객체
		2) 테이블 이름이 너무 길어 사용이 불편할 때 좀 더 간단하고 짧은 이름을 하나 더 만들어 주기 위해 사용합

	2. 사용법
		CREATE SYNONYM  -- 현재 사용자만 사용하는 동의어 생성 
		CREATE PUBLIC SYNONYM --모든 사용자에게 동의어 공유

		CREATE PUBLIC SYNONYM 동의어명칭 FOR 대상테이블또는뷰 
		
	3. 권한 부여하기(SQL*PLUS)
		GRANT CREATE PUBLIC SYNONYM, CREATE SYNONYM TO 사용자
		
	4. 동의어 생성
		
		SQL> conn system/oracle
		Connected.
		SQL> grant create synonym, create public synonym to scott
		Grant succeeded.		

		CREATE synonym E FOR EMP;
		SELECT * FROM e;	
		
	5. 동의어 삭제
		DROP SYNONYM E;


제약 조건

제약 조건의 종류
	1. 제약 조건이란?
		1) 제약 조건은 테이블의 특정 열에 지정
		2) 제약 조건을 지정한 열에 제약 조건에 부합하지 않는 데이터를 저장할 수 없습니다. 
		3) 제약 조건 지정 방식에 따라 기존 데이터의 수정이나 삭제 가능 여부도 영향을 받습니다.

	2. 종류
		1) NOT NULL 
		2) UNIQUE
		3) PRIMARY KEY
		4) FOREIGN KEY 
			: 다른 테이블의 기본키를 참조하는 키, 참조 무결성 제약조건
			: 부모 레코드에 자식 레코드가 있는 경우 부모레코드 삭제 불가			
		5) CHECK

	3. 데이터 무결성이란?
		1) 데이터베이스에 저장되는 데이터의 정확성과 일관성을 보장한다는 의미
		2) 종류
			- 도메인 무결성 제약조건 : 자료형 관련
			- 개체 무결성 제약조건 : 기본키와 관련 - 중복X, NULL 허용X
			- 참조 무결성 제약조건 : 외래키와 관련

	빈값을 허락하지 않는 NOT NULL
		1. 테이블을 생성하며 제약 조건 지정
		2. 제약 조건 확인
			1) USER_CONSTRAINTS 데이터 사전을 활용
			
			SELECT * FROM USER_CONSTRAINTS; 
			
			CONSTRAINT_TYPE 
			
			: C - 일반 제약조건, NOT NULL, CHECK
			: P - Primary key
			: R - Foreign KEY
			: U - UNIQUE

		3. 제약 조건 이름 직접 지정 
			컬럼명 자료형 CONSTRAINT 제약조건명 제약조건

			CREATE TABLE MEMBER (
				user_id varchar2(30) CONSTRAINT MEM_USER_ID_NN NOT NULL,
				user_pw varchar2(65) CONSTRAINT MEM_USER_PW_NN NOT NULL,
				user_nm varchar2(30) CONSTRAINT MEM_USER_NM_NN NOT NULL,
				mobile varchar2(5)
			);
			
			
		4. 이미 생성한 테이블에 제약 조건 지정
			1) NOT NULL 제약 조건의 추가는 ALTER 명령어와 MODIFY 키워드를 사용
				ALTER TABLE 테이블명 MODIFY 
				
			2) TEL 열에 NOT NULL 제약 조건 추가하기
				ALTER TABLE MEMBER MODIFY MOBILE  CONSTRAINT MEM_MOBILE_NM NOT NULL;
				ALTER TABLE MEMBER DROP CONSTRAINT MEM_MOBILE_NM;
			3) TEL 열 데이터 수정하기
			4) NOT NULL 제약조건 추가하기

		5. 제약 조건 삭제
			- ALTER 명령어에 DROP CONSTRAINT 키워드를 사용하면 지정한 제약 조건을 삭제할 수 있습니다.


	중복되지 않는 값 UNIQUE
		1. 요약
		1) 열에 저장할 데이터의 중복을 허용하지 않고자 할 때 사용
		2) NULL은 값이 존재하지 않음을 의미하기 때문에 중복 대상에서는 제외됩니다. 
		3) UNIQUE 지정 방법은 NOT NULL 제약 조건과 동일합니다.

		2. 테이블을 생성하여 제약 조건 지정
		3. 제약 조건 확인
		4. 중복을 허락하지 않는 UNIQUE
		1) TABLE_UNIQUE 테이블에 데이터 중복하여 입력하기

		5. UNIQUE 제약 조건과 NULL 값
		6. 테이블 생성하며 제약 조건 이름 직접 지정
		7. 이미 생성한 테이블에 제약 조건 지정
		8. 제약 조건 삭제

	유일하게 하나만 있는 값 PRIMARY KEY
		1. 요약 
		1) UNIQUE와 NOT NULL 제약 조건의 특성을 모두 가지는 제약 조건(데이터 중복을 허용하지 않고 NULL도 허용하지 않습니다.)
		2) NULL이 아닌 유일한 값을 가지므로 테이블의 각 행을 식별하는 데 활용
		3) PRIMARY KEY 제약 조건은 테이블에 하나밖에 지정할 수 없습니다.
		4) PRIMARY KEY로 지정하면 해당 열에는 자동으로 인덱스가 만들어집니다.

		2. 테이블을 생성하며 제약 조건 지정하기
		1) 테이블을 생성할 때 특정 열에 PRIMARY KEY 설정하기
		2) 생성한 PRIMARY KEY 확인하기
		3) 생성한 PRIMARY KEY를 통해 자동 생성된 INDEX 확인하기

		3. 테이블을 생성하며 제약 조건 이름 직접 지정하기
		4. PRIMARY KEY 제약 조건을 지정한 열 확인(중복 값을 입력했을 때)
		5. PRIMARY KEY 제약 조건을 지정한 열 확인(NULL 값을 입력했을 때) 
		6. CREATE문에서 제약 조건을 지정하는 다른 방식

	

	다른 테이블과 관계를 맺는 FOREIGN KEY
		1. 요약 
			1) 서로 다른 테이블 간 관계를 정의하는 데 사용하는 제약 조건
			2) 특정 테이블에서 PRIMARY KEY 제약 조건을 지정한 열을 다른 테이블의 특정 열에서 참조하겠다는 의미로 지정

		2. EMP 테이블과 DEPT 테이블의 제약 조건 살펴보기

		3. FOREIGN KEY 지정하기

			CREATE TABLE 테이블명(
				...
				컬럼명 자료형,
				CONSTRAINT 제약조건 이름 FOREIGN key(현재 테이블의 컬럼명) REFERENCES 참조 테이블(참조할 컬럼)
			);

			CREATE TABLE EMP_FK (
				EMPNO NUMBER(4) CONSTRAINT EMP_FK_PK PRIMARY KEY,
				ENAME VARCHAR2(10),
				JOB VARCHAR2(9),
				MGR NUMBER(4),
				HIREDATE DATE,
				SAL NUMBER(7,2),
				COMM NUMBER(7,2),
				-- DEPTNO NUMBER(2) CONSTRAINT EMP_FK_FK REFERENCES DEPT_FK(DEPTNO)
				DEPTNO NUMBER(2),
				CONSTRAINT EMP_FK_FK 
					FOREIGN KEY(DEPTNO) REFERENCES DEPT_FK(DEPTNO)
			); 
			
			ALTER TABLE 테이블 ADD CONSTRAINT 제약조건이름 FOREIGN KEY(컬럼명,..);
			ALTER TABLE 테이블명 DROP CONSTRAINT 제약조건명;
		
		4. FOREIGN KEY로 참조 행 데이터 삭제하기
			ON DELETE
				- NO ACTION : 기본값 - 부모에 자식레코드가 있으면 삭제 불가
				- CASCASE : 부모를 삭제하면 자식도 함께 삭제
				- SET NULL : 부모가 삭제되면 자식레코드의 해당항목을 NULL로 교체
				

	데이터 형태와 범위를 정하는 CHECK
	- 열에 저장할 수 있는 값의 범위 또는 패턴을 정의할 때 사용합니다.

		1. 테이블을 생성할 때 CHECK 제약 조건을 설정하기
		2. CHECK 제약 조건 확인하기

	기본값을 정하는 DEFAULT
		1. 테이블을 생성할 때 DEFAULT 제약 조건 설정하기
		2. DEFAULT로 지정한 기본 값이 입력되는 INSERT문 확인하기

		(참고) DEFAULT 
	제약 조건 비활성화, 활성화	
		
		CREATE TABLE MEMBER (
			USER_NO NUMBER(10) PRIMARY KEY,
			USER_ID VARCHAR2(30) UNIQUE NOT NULL,
			USER_PW VARCHAR2(65) NOT NULL,
			USER_NM VARCHAR2(40) NOT NULL,
			AGE NUMBER(3) CONSTRAINT MEM_AGE_CK CHECK(AGE >= 14),
			REG_DT DATE DEFAULT SYSDATE
		);

		ALTER TABLE MEMBER DISABLE CONSTRAINT MEM_AGE_CK;
		ALTER TABLE MEMBER ENABLE CONSTRAINT MEM_AGE_CK;

		CREATE SEQUENCE SEQ_MEMBER;

		INSERT INTO MEMBER (USER_NO, USER_ID, USER_PW, USER_NM, AGE)
			VALUES (SEQ_MEMBER.NEXTVAL, 'USER03', '1234', '사용자03', 13);

		UPDATE MEMBER SET AGE = 15 WHERE USER_ID = 'USER02';	


사용자, 권한, 롤 관리

사용자 관리
	1. 사용자란?
		- 데이터베이스에 접속하여 데이터를 관리하는 계정

	2. 데이터베이스 스키마란?
		1) 데이터베이스에서 데이터 간 관계, 데이터 구조, 제약 조건 등 데이터를 저장 및 관리하기 위해 정의한 데이터베이스 구조의 범위
		2) 오라클 데이터베이스에서는 스키마와 사용자를 구분하지 않고 사용하기도 합니다.
		3) 사용자 : 데이터를 사용 및 관리하기 위해 오라클 데이터베이스에 접속하는 개체를 뜻
		4) 스키마 : 오라클 데이터베이스에 접속한 사용자와 연결된 객체


	3. 사용자 생성
		 - 새로 생성한 사용자에 접속권한 부여해야 함 - GRANT / REVOKE
			GRANT 권한 TO 사용자;
			
				SYSTEM 계정에서
				SQL> create user DBSTUDY identified by oracle ;  -- 오라클 DBSTUDY 계정 추가
				
				SQL> conn DBSTUDY/oracle
				ERROR:
				ORA-01045: user DBSTUDY lacks CREATE SESSION privilege; logon denied
				
				SQL> grant create session to DBSTUDY; -- DBSTUDY 사용자에 세션 생성 권한 부여
				Grant succeeded.

				SQL> conn DBSTUDY/oracle
				Connected.
				
				SQL> alter user DBSTUDY identified by oracle123; -- DBSTUDY 사용자 암호 변경
				User altered.
				
				SQL> alter user DBSTUDY password expire;  -- DBSTUDY 사용자 암호 변경 강제화
				User altered	
				SQL> conn DBSTUDY/oracle123
				ERROR:
				ORA-28001: the password has expired
				Changing password for DBSTUDY
				New password:
				Retype new password:
				Password changed
				Connected.
				SQL>
				
				SQL> alter user DBSTUDY account lock;   -- DBSTUDY 사용자 잠그기
				User altered. 
				SQL> conn DBSTUDY/oracle
				ERROR:
				ORA-28000: the account is locked
				Warning: You are no longer connected to ORACLE.
				SQL> conn SYSTEM/oracle
				Connected.
				SQL> alter user DBSTUDY account unlock;   -- DBSTUDY 사용자 잠금 풀기

				User altered.			


	4. 사용자 정보 조회
	5. 오라클 사용자의 변경과 삭제

		SYSTEM 계정에서 drop user DBSTUDY CASCADE;   -- DBSTUDY 사용자와 사용자의 스키마 삭제

권한 관리하기

	1. 시스템 권한 
		1) 사용자 생성과 정보 수정 및 삭제, 데이터베이스의 접근, 오라클 데이터베이스의 여러 자원과 객체 생성 및 관리 등의 권한을 포함
		2) 데이터베이스의 관리 권한이 있는 사용자가 부여할 수 있는 권한
		3) 소유자 ANY 키워드가 들어 있는 권한은 소유자에 상관없이 사용 가능한 권한

	2. USER(사용자)
		1) CREATE USER
		2) ALTER USER 
		3) DROP USER 

	3. SESSION(사용자)
		1) CREATE SESSION
		2) ALTER SESSION

	4. TABLE(테이블)
		1) CREATE TABLE

	5. INDEX(인덱스)
	6. VIEW(뷰)
	7. SEQUENCE(시퀀스)
	8. SYNONYM(동의어)
	9. PROFILE(프로파일)
	10. ROLE(롤)

	6. 시스텀 권한 부여
	
		GRANT 시스템권한(시스템롤) .. TO 사용자;
		GRANT 시스템권한(시스템롤) .. TO 사용자 WITH ADMIN OPTION;
		
		: WITH ADMIN OPTION -> 사용자가 가지고 있는 권한을 다른 사용자에게 부여할 수 있도록
		
			SQL> conn SYSTEM/oracle
			Connected.
			SQL> grant create table to DBSTUDY ;
			Grant succeeded.
			
			-- DBSTUDY에게 권한 부여하고, 권한 부여 권한도 함께 부여함
			SQL> grant create session, create table, create user, alter user, drop user to DBSTUDY WITH ADMIN OPTION; 
			Grant succeeded.
		
	7. 시스템 권한 취소
		SQL> revoke create session, create table from DBSTUDY2;
		
		

	객체 권한

	1) 특정 사용자가 생성한 테이블,인덱스,뷰,시퀀스 등과 관련된 권한

	1. 객체 권한 분류
		1) TABLE(테이블)
			ALTER, DELETE, INDEX, INSERT, REFERENCES, SELECT, UPDATE
			
		2) VIEW(뷰)
		3) SEQUENCE(시퀀스)
		4) PROCEDURE(프로시저)
		5) FUNCTION(함수)
		6) PACKAGE(패키지)

	2. 객체 권한 부여
	
		- DML 관련 권한 : insert, update, delete, select 권한
		grant [객체권한|ALL PRIVILEGES] on 스키마.객체이름 to [사용자|role|public] - 
		grant [객체권한|ALL PRIVILEGES] on 스키마.객체이름 to [사용자|role|public] - with grant option 
		
		ALL PRIVILEGES : 모든 권한
		
		-- SYSTEM계정에서 :  DBSTUDY2 에게 SCOTT.EMP 테이블에 select 권한 부여, 권한 부여권한도 부여
		SQL> grant select on SCOTT.EMP to DBSTUDY2 with grant option; 

		-- DBSTUDY2 에서 DBSTUDY3에게 다시 권한 부여 가능
		SQL> show user
		USER is "DBSTUDY2"
		SQL> select * from SCOTT.EMP;
		SQL> grant select on SCOTT.EMP to DBSTUDY3;
		Grant succeeded.
		SQL> grant update on SCOTT.EMP to DBSTUDY3;
		grant update on SCOTT.EMP to DBSTUDY3
		ERROR at line 1:
		ORA-01031: insufficient privileges		
		
		
	3. 객체 권한 취소
		SQL> revoke select on  SCOTT.EMP from DBSTUDY3;


	롤 관리
	1. 롤이란?
		1) 여러 종류의 권한을 묶어 놓은 그룹
		2) 오라클 데이터베이스를 설치할 때 기본으로 제공되는 사전 정의된 롤(predefined roles)과 사용자 정의 롤(user roles)로 나뉩니다.

		CREATE ROLE 롤이름;
		GRANT 시스템권한,.. TO 롤;
		
		SQL> show user
		USER is "SYSTEM"
		SQL> create role ROLE1;  -- ROLE1 이란 롤 만들기
		Role created.
		SQL> grant create session, create table, create sequence, create view to ROLE1; --ROLE1 에 권한들 추가
		Grant succeeded.		
		SQL> create user DBSTUDY3 identified by oracle;  -- DBSTUDY3 유저생성
		User created.
		SQL> grant ROLE1 to DBSTUDY3;  -- DBSTUDY3 에게 ROLE1 역할 부여
		Grant succeeded.		
		
	2. 사전 정의된 롤
		1) CONNECT롤 
		2) RESOURCE 롤
		3) 보통 새로운 사용자를 생성하면 CONNECT롤과 RESOURCE롤을 부여하는 경우가 많습니다
		
		SQL> create user DBSTUDY4 identified by oracle;
		User created.
		SQL> grant connect, resource to DBSTUDY4; -- 새로 만든 사용자 DBSTUDY4 에게 connect와 resource 역할 부여
		Grant succeeded.		
		

	3. 사용자 정의 롤
		1) 롤 생성
		2) 부여된 롤과 권환 확인 
		- USER_SYS_PRIVS;
		- USER_ROLE_PRIVS;

		3) 롤 취소
			REVOKE ROL1 from DBSTUDY3;
		3) 롤 삭제	
			DROP role ROLE1;


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

	조건 제어문
	1. 조건문
	1) IF-THEN
	- 변수에 입력한 값이 홀수인지 알아보기(입력 값이 홀수일 때)

	2) IF-THEN-ELSE
	- 변수에 입력된 값이 홀수인지 짝수인지 알아보기(입력 값이 짝수일 때)

	3) IF-THEN-ELSIF
	- 입력한 점수가 어느 학점인지 출력하기



	2. CASE 조건문
	1) 단순 CASE
	- 입력 점수에 따른 학점 출력하기(단순 CASE 사용)

	2) 검색 CASE
	- 입력 점수에 따른 학점 출력하기(검색 CASE 사용)

	반복 제어문

	1. 반복문 종류 
	1) 기본 LOOP
	2) WHILE LOOP
	3) FOR LOOP
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