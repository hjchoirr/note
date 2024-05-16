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


	제약 조건 비활성화, 활성화	