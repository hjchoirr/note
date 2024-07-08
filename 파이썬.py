파이썬

7/5 

실습환경 구축 
https://www.python.org/downloads/
    설치옵션
    - Add python.exe to path 선택
    
    C:\Users\admin> python --version
    Python 3.12.4
    
>>> import os
>>> os.system('cls')
>>> message
'안녕하세요'
>>>
>>> message="안녕하셍"
>>> message            
'안녕하셍'
>>>
    
리스트는 수정가능
튜플은 수정불가능 자료형


자료형

1. 숫자형 
    1) 정수형 
    2) 실수형 
    3) 8진수와 16진수 


2. 숫자형 연산자 

    1) 사칙연산
    
    2) ** 연산자 : 제곱 
    
        a ** b -> a의 b제곱
        
    3) % 연산자
    
        나머지
        
    4) // 연산자
    
        나머지 필없이 몫만 구할때

            import os
                    
            num = 100
            print(type(num))  # <class 'int'>
            num = 100.123
            print(type(num)) # <class 'float'>

            # os.system('cls')

            a = 2
            b = 4
            print(a ** 4) # 16

            a=81
            print(a % 2) # 1

            print(a // 2) # 40

            a = 24E10
            print(a)  # 240000000000.0

            a = 0o10
            print(a) # 8

            a = 0xFF
            print(a) # 255

3. 문자열 자료형
    1) 문자열을 만들고 사용하기
        - 따옴표 (',")
    2) 문자열 안에 작은 따옴표나 큰 따옴표를 포함시키고 싶을 때 
        - escape 문자 : \
        
        
    3) 여러 줄인 문자열을 변수에 대입하고 싶을 때
        \n : 줄개행문자
    
         """ 
         여러줄
         여러줄
          . . .
         """
         
         또는
         
         ''' 
         여러줄
         여러줄
          . . .
         '''
    
        -> 따옴표3개 
    
    
        (참고) 메타문자
        \t
        \f 폼 피드(줄 바꿈 문자, 커서를 현재 줄의 다음 줄로 이동)
        \b 백 스페이스
        \a 벨 소리
        \000 널 문자
    
    4) 이스케이프 코드란

4. 문자열 연산하기
    
    1) 문자열 더해서 연결하기
    
    2) 문자열 곱하기 : 문자열 반복 
    
    3) 문자열 곱하기를 응용하기
    
    4) 문자열 길이 구하기


5. 문자열 인덱싱과 슬라이싱

    1) 문자열 인덱싱
    2) 문자열 인덱싱 활용하기
    3) 문자열 슬라이싱
    4) 문자열을 슬라이싱 하는 방법
    5) 슬라이싱으로 문자열 나누기
    6) Pithon 문자열을 Python으로 바꾸면?
    
    
        a = "ABC"
        print(type(a)) # <class 'str'>

        a = 'ABC'
        print(type(a)) # <class 'str'>

        str = "I'm a student"
        print(str)  # I'm a student

        str = 'I\'m a student'
        print(str)  # I'm a student \ : escape 문자

        str = "I'm a \"Student\""
        print(str)  # I'm a "Student"

        print()

        str = "TodoList\n1.exercise\n2.breakfast"
        print(str)
        print()

        str = """TodoList
        1.exercise
        2.breakfast"""

        print(str)
        print()

        a = "ABC"
        print(a * 3)  # ABCABCABC    

        b = len(a)
        print(b)  # 3
        
        
        print("=" * 50)
        print("502호 화이팅!")
        print("=" * 50)
        
        