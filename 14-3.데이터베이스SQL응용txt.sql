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
			CREATE OR REPLACE PROCEDURE PRO_NOPARAM
			IS
				V_DEPTNO DEPT.DEPTNO%TYPE;
				V_DNAME DEPT.DNAME%TYPE;
			BEGIN
				SELECT DEPTNO, DNAME INTO V_DEPTNO, V_DNAME FROM DEPT WHERE DEPTNO = 20;
				DBMS_OUTPUT.PUT_LINE('DEPTNO : ' || V_DEPTNO );
				DBMS_OUTPUT.PUT_LINE('DNAME : ' || V_DNAME );
			END PRO_NOPARAM;
		

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
		2) USER_ERRORS 


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

	4. DML 트리거의 제작 및 사용(AFTER)
		1) EMP_TRG_LOG 테이블 생성하기
		2) EMP_TRG_LOG 테이블에 EMP_TRG 테이블 데이터 변경 사항을 기록하는 트리거를 생성

	5. 트리거 정보 조회
		- USER_TRIGGERS

	6. 트리거 변경

	7. 트리거 삭제