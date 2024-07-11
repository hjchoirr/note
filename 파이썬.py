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
        - 문자의 위치번호 0 부터 시작
        - 위치번호가 음수이면 오른쪽 부터 시작 ( -1 : 오른쪽 끝)
        
        
    2) 문자열 인덱싱 활용하기
    
    3) 문자열 슬라이싱
        - 문자열을 특정 위치에서 잘라주기
        
    4) 문자열을 슬라이싱 하는 방법
    
        변수[시작위치:종료위치]  -> 종료위치 포함 안함
        
        변수[시작위치:] -> 시작위치 부터 끝까지 
        변수[:종료위치] -> 처음 부터 종료위치 까지 
        
        
    5) 슬라이싱으로 문자열 나누기

    
    
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
        
        
        
        a = "Life is too short, You need to Python"

        print(a[0])     # L
        print(a[5])     # i

        print(a[-1])    # n

        print(a[0:4])   # Life

        print(a[3:])    # e is too short, You need to Python
        print(a[-3:])   # hon
        print(a[:-5])   # Life is too short, You need to P

        b = a[:]
        print(b)  # Life is too short, You need to Python

        a = "2024-07-09 16:30:00"
        year = a[:4]    # 2024
        month = a[5:7]  # 07
        day = a[8:10]   # 09

        hour = a[-8:-6] # 16

        print(year)     
        print(month)    
        print(day)      
        print(hour)             


6) Pithon 문자열을 Python으로 바꾸면?
    
    문자열은 못바꿈, 문자열은 불변 - final ( java도 마찬가지 )

    a = 'Pithon'
    b = a[0] + 'y' + a[2:]   # Python
    print(b)
    
    a = "ABC"
    print(id(a))  # 문자열 a의 주소 140706637937960


    a = a + "1234"  # 문자열은 불변이므로 다른 주소를 가진 새 문자열 생성됨
    print(a)     # ABC1234
    print(id(a)) # 2715684060880


    a = "I eat %d apples." % 10
    print(a)  # I eat 10 apples.

    a = "I eat %s apples." % "ten"
    print(a) # I eat 10 apples.

    a = "%s litte, %s litte indians." % ("one", "two")
    print(a)    # one litte, two litte indians.

    a = "one"
    b = "two"

    c = "%s litte, %s litte indians." % (a, b)
    print(c)   # one litte, two litte indians.
 
    a = 100
    b = "스프링 %d%% 이해" % 100
    print(b)  #  스프링 100% 이해

주석 
  - 한줄주석 #
  - 여러줄 주석 : 따옴표 3개 열고 닫고
  
    C:> python

    >>> import os        
    >>> os.system('cls')

      
    %10s -> 10자리 확보, 남는 공간은 왼쪽 공백 채워진다
    %-10s -> 10자리 확보, 남는 공간은 오른쪽 공백 채워진다    
      
    a = "(%10s)" % "AB"  # (        AB)
    print(a)

    a = "(%-10s)" % "AB" # (AB        )
    print(a)



    %10.2f -> 총 10자리 중 소수점 2자리

    a = "(%10.2f)" % 10.123456789  # (     10.12)
    print(a)

    a = "(%0.2f)" % 10.123456789  # (10.12)
    print(a)

    a = "(%.2f)" % 10.123456789  # (10.12)
    print(a)
    
    
8. format 함수를 사용한 포매팅
    1) 숫자 바로 대입하기
    
    
    2) 문자열 바로 대입하기
    3) 숫자 값을 가진 변수로 대입하기
    4) 2개 이상의 값 넣기
    5) 이름으로 넣기
    
        a = "I love {0} students".format(24)
        print(a)

        a = "I love {0} students".format("스물 넷")
        print(a)

        a = "I like {0} and {1}".format("java","python")
        print(a)

        a = "I have {number} pens and {days} days".format(number=10, days=5)
        print(a)
            
        >>>
            
        I love 24 students
        I love 스물 넷 students
        I like java and python
        I have 10 pens and 5 days    
        
    
    6) 인덱스와 이름을 혼용해서 넣기
    7) 왼쪽 정렬   {0:<10}
    8) 오른쪽 정렬 {0:>10}
    9) 가운데 정렬 {0:^10}
    
        a = "({0:<10})".format("AB")   # (AB        )
        print(a)

        a = "({0:>10})".format("AB")   # (        AB)
        print(a)    

        a = "({0:^10})".format("AB")   # (    AB    )
        print(a)

        a = "({0:*<10})".format("AB")   # (AB********)
        print(a)

    10) 공백 채우기
        11) 소수점 표현하기
        12) { 또는 } 문자 표현하기


        a = "({0:0.4f})".format(10.12345678)   # (10.1235)
        print(a)        

        a = "{{0:0.4f}}".format(10.12345678)   # {0:0.4f}
        print(a)


    9. f 문자열 포매팅

        name = "이이름"
        age = 40
        a = f'이름:{name}, 나이:{age}'   
        print(a)
        >>> 이름:이이름, 나이:40
        
        data = {"name": "이이름", "age": 40 }
        a = f"이름: {data['name']}, 나이 : {data['age']}"  # 딕셔너리
        print(a)

        >>> 이름: 이이름, 나이 : 40
        
        a = "abc1234def"
        b = a.find('2')   # 4  : 위치찾기
        print(b)

        b = a.find('9')   # -1 : 못찾으면
        print(b)

        a = "abc1234def"
        b = a.index('2')   # 4  : 위치찾기
        print(b)

        b = a.index('9')   # error : 못찾으면
        print(b)

        a = ("  abc  ").strip()  # abc
        print(a)


10. 문자열 관련 함수들
    1) 문자 개수 세기 - count
    2) 위치 알려 주기 1 - find
    3) 위치 알려 주기 2 - index
    4) 문자열 삽입 - join
    5) 소문자를 대문자로 바꾸기 - upper
    6) 대문자를 소문자로 바꾸기 - lower
    7) 왼쪽 공백 지우기 - lstrip
    8) 오른쪽 공백 지우기 - rstrip
    9) 양쪽 공백 지우기 - strip
    10) 문자열 바꾸기 - replace
    11) 문자열 나누기 - split    