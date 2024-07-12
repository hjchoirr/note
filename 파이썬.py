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
    
    
리스트 자료형
    리스트, 튜플 둘다 순서(index)가 있는 자료형
    
     : 리스트 - [값1, 값2, 값3..]   : 값변경 가능 ->  list(...)
     : 튜플 - (값1, 값2, 값3..)    : 변경 불가능 -> tuple(...)
    
     - 변수명[인덱스번호] 
     
    딕셔너리 - { "키": "값", "키": "값", ...}  -> dict(..)
    
      - 변수명['키']
      
    집합 - 중복X, 순서 X  -> set(리스트, 튜플, 문자열)


1. 리스트를 어떻게 만들고 사용할까?

    odd = [1,3,5,7,9]
    a = odd[0]
    print(a)

    a = odd[3]
    print(a)

    odd[0] = "하나"

    print(odd[0])
    print(odd)

    data = [1,2,['one', 'two', [3,4,5]]]
    print(data[2])
    print(data[2][2])
    print(data[2][-1])
    print(data[-1][-1])

    >>>
    1
    7
    하나
    ['하나', 3, 5, 7, 9]
    ['one', 'two', [3, 4, 5]]
    [3, 4, 5]
    [3, 4, 5]
    [3, 4, 5]


2. 리스트의 인덱싱과 슬라이싱
    1) 리스트의 인덱싱
    2) 삼중 리스트에서 인덱싱 하기
    3) 리스트의 슬라이싱
    4) 중첩된 리스트에서 슬라이싱하기

        b = [1,2,3,4,5,6,7,8,9]
        a = b[0:3]
        print(a)

        a = b[:6]
        print(a)
        
        >>>
        [1, 2, 3]
        [1, 2, 3, 4, 5, 6]        
        

        b = [1,2,3,4,5,6,7, ['a','b', 'c'], 8,9]
        a = b[-3][0:2]
        print(a)

        >>>

        [1, 2, 3]
        [1, 2, 3, 4, 5, 6]
        ['a', 'b']

        a = [1,2,3]
        b = [4,5,6]

        c = a + b
        print(c)

        a = a * 3
        print(a)

        b = len(a)
        print(b)

        a = ["AA", "BB", "CC"]
        b = a    #  주소복사 파이썬의 모든 자료형은 참조형
        b[0] = "AAA"
        print(a)

        print(id(a))
        print(id(b))

        c = ["AA", "BB", "CC"]
        d = c[:]  #깊은 복사 -> 다른 객체로 복사
        c[0] = "AAA"
        print(d)

        print(id(c))
        print(id(d))
        
        >>>
        
        [1, 2, 3, 4, 5, 6]
        [1, 2, 3, 1, 2, 3, 1, 2, 3]
        9
        ['AAA', 'BB', 'CC']
        1883096799424
        1883096799424
        ['AA', 'BB', 'CC']
        1883097119168
        1883097118784


        a = [1,2,3]
        b = a.copy()
        a[0] = 100
        print(b)
        a = ['AA','BB','CC', 'DD', 'EE']
        del a[0]
        print(a)
        print(type(a[0]))
        #b =  a[0] + 100  ## ERROR 같은 자료형끼리만 가능함
        b = a[0] + str(100)
        print(b)
        
        >>>
        
        [1, 2, 3]
        ['BB', 'CC', 'DD', 'EE']
        <class 'str'>
        BB100
        
        
        >>> a = [1,2,3]

        >>> a.append(4)  # 뒤에 추가
        >>> a
        [1, 2, 3, 4]

        >>> a.insert(0,5) # 특정위치에 추가
        >>> a
        [5, 1, 2, 3, 4]

        >>> a.insert(2,6)
        >>> a
        [5, 1, 6, 2, 3, 4]

        >>> a.sort()  # 정렬
        >>> a
        [1, 2, 3, 4, 5, 6]

        >>> a = ['나','다','가','라']

        >>> a.sort()
        >>> a
        ['가', '나', '다', '라']

        >>> a.reverse() # 거꾸로 정렬
        >>> a
        ['라', '다', '나', '가']

        >>> a.index('나') # '나' 의 위치 찾기 
        2

        >>> a.index('마') # 없는거 찾으면 에러남

        Traceback (most recent call last):
          File "<stdin>", line 1, in <module>
        ValueError: '마' is not in list


        >>> a = [1,2,3]
        >>> a = a * 3
        >>> a
        [1, 2, 3, 1, 2, 3, 1, 2, 3]
        >>> a.remove(3)   # 첫번째 3만 제거됨
        >>> a
        [1, 2, 1, 2, 3, 1, 2, 3]
        >>> 


        >>> a
        [1, 2, 1, 2, 3, 1, 2, 3]
        >>> b = a.pop()  # pop : 마지막꺼 꺼내서 반환
        >>> b
        3
        >>> a
        [1, 2, 1, 2, 3, 1, 2]  # pop 은 원본에서 제거


        >>> a
        [1, 2, 1, 2, 3, 1, 2]
        >>> b = a.pop(2);   # 위치지정하여 꺼내기
        >>> b
        1
        >>> a
        [1, 2, 2, 3, 1, 2]
        >>> 

        >>> a = ['가', '나', '가', '라']
        >>> a.count('가')  # '가' 의 갯수
        2

        >>> a.extend(['마','바'])
        >>> a
        ['가', '나', '가', '라', '마', '바']
        >>> a += ['사','아']
        >>> a
        ['가', '나', '가', '라', '마', '바', '사', '아']
        >>> 





        
        

3. 리스트 연산하기
    1) 리스트 더하기(+)
    2) 리스트 반복하기(*)
    3) 리스트 길이 구하기
    4) 초보자가 범하기 쉬운 리스트 연산 오류

4. 리스트의 수정과 삭제
    1) 리스트의 값 수정하기
    2) del 함수를 사용해 리스트 요소 삭제하기

5. 리스트 관련 함수
    1) 리스트에 요소 추가하기 - append
    2) 리스트 정렬 - sort
    3) 리스트 뒤집기 - reverse
    4) 인덱스 반환 - index
    5) 리스트에 요소 삽입 - insert
    6) 리스트 요소 제거 - remove
    7) 리스트 요소 끄집어 내기 - pop
    8) 리스트에 포함된 요소 x의 개수 세기 - count
    9) 리스트 확장 - extend    
    
    
튜플 자료형
- 리스트는 [], 튜플은 ()으로 둘러싼다.
- 리스트는 요솟값의 생성, 삭제, 수정이 가능하지만, 튜플은 요솟값을 바꿀 수 없다.

    1. 튜플은 어떻게 만들까?
    
        (값,값,값)
        
    2. 튜플의 요솟값을 지우거나 변경하려고 하면 어떻게 될까?
        1) 튜플 요솟값을 삭제하려 할 때
        2) 튜플 요솟값을 변경하려 할 때

    2. 튜플 다루기
        1) 인덱싱하기
        2) 슬라이싱하기
        3) 튜플 더하기
        4) 튜플 곱하기
        5) 튜플 길이 구하기

    3. 튜플은 요소값을 변경할 수 없기 때문에 sort, insert, remove, pop과 같은 내장 함수가 없다.    
    
            
        >>> a = (1,2,3)
        >>> a
        (1, 2, 3)

        >>> a = (1)  # 한개만 있는 튜플은 튜플이 아님
        >>> a[0]
        Traceback (most recent call last):
          File "<stdin>", line 1, in <module>
        TypeError: 'int' object is not subscriptable

        >>> a = (1,)   # 한개만 있는 튜플은 이렇게 만들기
        >>> a[0] 
        1
        >>> 

        >>> a = 1,2,3,4,5   # 튜플
        >>> a
        (1, 2, 3, 4, 5)
        >>> type(a)
        <class 'tuple'>
        >>> 


        >>> a = (1,2,3,4)  
        >>> a[0]
        1
        >>> a[1] = 22  # 튜플은 값 변경 불가한 순서가 있는 리스트
        Traceback (most recent call last):
          File "<stdin>", line 1, in <module>
        TypeError: 'tuple' object does not support item assignment
        >>> 


        >>> a = ('AA','BB','CC','DD')
        >>> a[1:3]     # 튜플도 슬라이싱 됨
        ('BB', 'CC')
        >>> a[0:-1]
        ('AA', 'BB', 'CC')
        >>> 

        >>> a += ('EE','FF')
        >>> a
        ('AA', 'BB', 'CC', 'DD', 'EE', 'FF')  # 변경되는게 아니라 새로운 튜플로 만들어지는것임
        >>> a *= 2
        >>> a
        ('AA', 'BB', 'CC', 'DD', 'EE', 'FF', 'AA', 'BB', 'CC', 'DD', 'EE', 'FF')
        >>>


        >>> data = ( 100, 200)
        >>> a, b = data
        >>> a  
        100
        >>> b
        200
        >>> a, b = b , a
        >>> a
        200
        >>> b
        100
        >>> 


딕셔너리 - 키,값

        >>> person = {"name": "이이름", "age": 40}
        >>> type(person)
        <class 'dict'>
        >>> person['name']
        '이이름'
        >>> person['name'] = '김이름'
        >>> person
        {'name': '김이름', 'age': 40}
        >>> 
        >>> person['address'] = '주소'   # 없으면 추가됨
        >>> person
        {'name': '김이름', 'age': 40, 'address': '주소'}
        >>> 
        >>> del person['age']  # 삭제하기
        >>> person
        {'name': '김이름', 'address': '주소'}
        >>> 


        >>> person = {"name": "이이름", "age": 40}
        >>> person.get('name')
        '이이름'
        >>> person['name']
        '이이름'
        >>> person['name2']   # 없을때 에러
        Traceback (most recent call last):
          File "<stdin>", line 1, in <module>
        KeyError: 'name2'
        >>> person.get('name2') # 없어도 에러안남
        >>>



