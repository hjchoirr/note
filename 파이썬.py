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


7/15

딕셔너리 자료형

    1. 딕셔너리란?
    
    2. 딕셔너리는 어떻게 만들까?
    
    3. 딕셔너리 쌍 추가, 삭제하기
    
        - 없는 키값에 대입하면 추가
        - del 변수['키']
        
        1) 딕셔너리 쌍 추가하기
        2) 딕셔너리 요소 삭제하기

    4. 딕셔너리를 사용하는 방법
    
        1) 딕셔너리에서 Key를 사용해 Value 얻기
        
          - 변수명['이름'] : 없으면 예외 발생
          - 변수명.get("이름") : 값이 없어도 예외발생하지 않음
          
        2) 딕셔너리 만들 때 주의할 사항
        
          - 이미 키값 있으면 변경됨


            >>> person = { "name": "이 이름", "age" : 40, "address" : "주소"}
            >>> person["name"]
            '이 이름'
            >>> 
                
            person = { "name": "이 이름", "age" : 40, "address" : "주소"}

            keys = person.keys()
            print(keys)

            for key in keys :
                print(key)

            values = person.values()

            print(values)

            for value in values :
                print(value)
            >>> 

            dict_keys(['name', 'age', 'address'])

            name
            age
            address

            dict_values(['이 이름', 40, '주소'])

            이 이름
            40
            주소
                
                
            keys2 = list(keys)
            print(keys2)

            >>> ['name', 'age', 'address']

            keys2 = tuple(keys)
            print(keys2)
            >>> ('name', 'age', 'address')




            person = { "name": "이 이름", "age" : 40, "address" : "주소"}
            >>> items = person.items()
            >>> items;
            dict_items([('name', '이 이름'), ('age', 40), ('address', '주소')])
            >>> 
            >>> for item in items :
            ...     print(item)
            ... 
            ('name', '이 이름')
            ('age', 40)
            ('address', '주소')
            >>> 

            >>> for item in items:
            ...     key, value = item
            ...     print(key, value)
            ... 
            name 이 이름
            age 40
            address 주소
            >>> 

            >>> for key, value in items:
            ...     print(key, value)
            ... 
            name 이 이름
            age 40
            address 주소
                
    

    5. 딕셔너리 관련 함수
    
        1) Key 리스트 만들기 - keys
        
          : key 목록만 가져오기
          - 현재 dict_keys(...) : 반복 가능 객체 -> for문을 통해서 반복 가능
          
          
        2) Value 리스트 만들기 - values
        
           : 목록만 가져오기
           
        3) Key, Value 쌍 얻기 - items
        4) Key, Value 쌍 모두 지우기 - clear
        5) Key로 Value 얻기 - get
        6) 해당 Key가 딕셔너리 안에 있는지 조사하기 - in


            >>> person = { "name": "이 이름", "age" : 40, "address" : "주소"}
            >>> "name" in person
            True
            >>> "name2" in person
            False
            >>> 

집합 자료형
    
    1. 집합 자료형은 어떻게 만들까?
        set([.., .., ])
        
        >>> s1 = set([1,2,3,3,4,5])
        >>> s1
        {1, 2, 3, 4, 5}

        >>> s2 = set("python")
        >>> s2
        {'h', 'n', 'o', 't', 'p', 'y'}
        >>> s2 = set("pythonppp")
        >>> s2
        {'h', 'n', 'o', 't', 'p', 'y'}

                
    
    2. 집합 자료형의 특징
    
        1) 중복을 허용하지 않는다.
        2) 순서가 없다(Unordered).
        3) set 자료형은 순서가 없기(unordered) 때문에 인덱싱을 통해 요소값을 얻을 수 없다.
        4) set 자료형에 저장된 값을 인덱싱으로 접근하려면 다음과 같이 리스트나 튜플로 변환한 후에 해야 한다.

    3. 교집합, 합집합, 차집합 구하기
    
        1) 교집합 구하기
            & / intersection(..)
            
        2) 합집합 구하기
            | / union(...)
            
        3) 차집합 구하기
            - / difference(..)

            >>> s1 = set([1,2,3,4,5])
            >>> s2 = set([4,5,6,7,8])
            >>> s1 & s2
            {4, 5}
            >>> s1.intersection(s2)
            {4, 5}
            >>> s1 | s2
            {1, 2, 3, 4, 5, 6, 7, 8}
            >>> s1.union(s2)
            {1, 2, 3, 4, 5, 6, 7, 8}
            >>> s1 - s2
            {1, 2, 3}
            >>> s1.difference(s2)
            {1, 2, 3}
            >>> 



    4. 집합 자료형 관련 함수
    
        1) 값 1개 추가하기 - add
        2) 값 여러 개 추가하기 - update
        3) 특정 값 제거하기 - remove
        

Jupitor Notebook -> colab

불자료형

    s1.add(6)
    print(s1)
    s1.update([7,8,9])
    print(s1)

    s1.remove(8)
    print(s1)


    a = True
    b = False
    type(a)

    >> bool

 True
 False
 
  -> 조건문
  
  2. 자료형의 참과 거짓
  
     - 값으로 참, 거짓 구분
     
        "python"    참
        ""	        거짓
        [1,2,3]	    참
        []	        거짓
        (1,2,3)	    참
        ()	        거짓
        {'a':1}	    참
        1	        참
        0	        거짓
        None	    거짓     



        if []:
            print("참!")
        else:
            print("거짓")
            
        >> 거짓

        if [1,2,3]:
            print("참!")
        else:
            print("거짓")
            
        >> 참!


        nums = [1,2,3,4,5]
        while nums:
            print(nums.pop())

        >>
        5
        4
        3
        2
        1
        
        
    3. 불연산 - 일반 자료형의 값을 논리값으로 변환
    
        a = bool("")
        b = bool((1,2))
        print(a, b)

        >> False True

        type("")
        >> str
        
        
    파이썬의 모든 자료형은 객체이다, 함수도 객체, 변수도 객체
    
        a = [1,2,3,4,5, 6, 7]
        b = a     # 얕은 복사 : 주소 복사됨                 
        print(id(a), id(b))   
            
        >> 134507917401792 134507917401792   
            

        a = [1,2,3,4,5, 6, 7]
        b = a  # 얕은 복사 : 주소 복사됨 
        a.pop()
        print(a, b)  
        
        >>>
        
        [1, 2, 3, 4, 5, 6] [1, 2, 3, 4, 5, 6]  # 얕은 복사의 결과

        c = a[:]      # 깊은 복사 : 값 복사되어 다른 주소에 저장됨 
        a.pop()
        print(a, c)

        >>

        [1, 2, 3, 4, 5] [1, 2, 3, 4, 5, 6]  # 깊은 복사의 결과

        from copy import copy
        
        a = [1,2,3,4,5, 6, 7]
        b = copy(a)    # 깊은 복사  
        a.pop()
        print(a, b) 
        
        >>
        [1, 2, 3, 4, 5, 6] [1, 2, 3, 4, 5, 6, 7]  # 깊은 복사의 결과


        비구조할당
            (a, b) = ("python", "easy")
            c, d = ("python", "easy")
            e, f = "python", "easy"
            print(a, b, c, d, e, f)

            >> python easy python easy python easy

            a = ("python")
            print(a)
            print(type(a))
            b = ("python", )
            print(b)
            print(type(b))

            >>
            python
            <class 'str'>
            ('python',)
            <class 'tuple'>


            a = 10
            b = 20
            a, b = b, a
            print(a, b)

            >> 20, 10


if문
    1. if 문의 기본 구조
    2. 들여쓰기 방법 알아보기
    3. 조건문이란 무엇인가?
        1) 비교 연산자
        2) and, or, not
        3) in, not in
        4) 조건문에서 아무 일도 하지 않게 설정하고 싶다면?
            pass


    4. 다양한 조건을 판단하는 elif
    5. if 문을 한 줄로 작성하기

    6. 조건부 표현식
    - message = "success" if score >= 60 else "failure"
    

        if 조건식:
            조건이 참일때 실행되는 코드..
        else:
            조건이 거짓일때 실행되는 코드..
            

        medal = "GOLD"
        if medal == "GOLD" or medal == "SILVER" or medal == "BRONZE":
            print("메달 획득")
        else:
            print("메달 획득 실패")
    

        medal = "GOLD"
        if medal in ["GOLD", "SILVER", "BRONZE"] :
            print("획득")
        else:
            print("획득 실패")


        age = 15
        if age < 8:
            print("유치원")
        elif age < 14:
            print("초등학교")
        elif age < 17:
            print("중학교")
        elif age < 20:
            print("고등학교")
        else:
            print("대학교")
            
        money = 10000
        if money >= 10000:
            pass # 처리하지 않고 넘어가기
        else:
            print("부족하당")

        score = 80
        message = "통과" if score >= 70 else "안통과"
        print(message)
            
            
while문
    1. while 문의 기본 구조
    
        while 조건식:
            조건이 참일때 수행
            
    2. while 문 만들기
    3. while 문 강제로 빠져나가기 - break
    4. while 문의 맨 처음으로 돌아가기 - continue
    5. 무한 루프            
    
    
        sum = 0
        n = 0
        while n <= 100:
            sum += n
            n += 1
        print(sum)
        >> 5050

        sum = 0
        n = 0
        while n <= 100:
            n += 1
            if n % 2 == 0:
                continue
            sum += n
        print(sum)
        >> 2601


input() : 문자열 입력받을때 사용
input("안내문구")


    str = input()
    print(str)

    str = input("아무거나 입력하세요")
    print(str)
    
str(..) : 다른 자료형의 값을 문자로
int(..) : 다른 자료형의 값을 정수로
float(..) : 다른 자료형의 값을 실수로

        prompt = """두수의 연산 
        q : 중지"""

        while True:
            print(prompt)
            num1 = input("수1:")
            if num1 == "q":
                break
            num2 = input("수2:")
            if num2 == "q":
                break

            num1 = int(num1)
            num2 = int(num2)
            result = num1 + num2
            print("%d + %d = %d" % (num1, num2, result))
            
            
for문
    1. for 문의 기본 구조
    
        for 변수 in 리스트, 튜플, 문자열: 
            반복 수행 코드..
            
            
    2. 예제를 통해 for 문 이해하기
        1) 전형적인 for 문
        2) 다양한 for 문의 사용

    3. for 문의 응용
    4. for 문과 continue 문

        fruits = ["Apple", "Melon", "Mango", "Banana"]
        for fruit in fruits:
            print(fruit)
            
        >> 
        Apple
        Melon
        Mango
        Banana
        
        for ch in "ABCDEFG":
            print(ch)
            
        >>
        A
        B
        C
        D
        E
        F
        G
        
        nums = [(10, 20), (30, 40), (50, 60)]
        for num in nums:
            print(num)
        print()
        for n1, n2 in nums:
            print("%d + %d = %d" % (n1, n2, n1 + n2))
            
        >>

        (10, 20)
        (30, 40)
        (50, 60)

        10 + 20 = 30
        30 + 40 = 70
        50 + 60 = 110


    5. for 문과 함께 자주 사용하는 range 함수
        1) range 함수의 예시 살펴보기
        
        2) for와 range를 이용한 구구단
        
        횟수가 있는 반복은 range() 한수와 함께 쓰기
        
            for i in range(10) :   # 10전 까지
                print('%d번 반복' % (i + 1))  
            >>
            1번 반복
            2번 반복
            3번 반복
            4번 반복
            5번 반복
            6번 반복
            7번 반복
            8번 반복
            9번 반복
            10번 반복

        a = range(10)
        print(a)
        >>
        range(0, 10)
            
        for i in range(10):
            print("%d회 반복" % i)
        >>
        0회 반복
        1회 반복
        2회 반복
        3회 반복
        4회 반복
        5회 반복
        6회 반복
        7회 반복
        8회 반복
        9회 반복

        for i in range(2, 10) :
            print()
            for j in range(2, 10) :
                print("%d X %d = %d" % (i, j, i * j))

        구구단

참고)
    print
      - end = '\n'  ( 기본값 )
      
    print("ABC", end="|")
    >> ABC|
    print("ABC", end=" ")
    print("DEF")
    >>
    ABC DEF   

    nums = [10,20,30,40,50]
    nums2 = []
    for num in nums:
        nums2.append(num * num)
    print(nums2)

    >> [100, 400, 900, 1600, 2500]



    6. 리스트 컴프리헨션 사용하기
        1) [표현식 for 항목 in 반복_가능_객체 if 조건문]

        2) [표현식 for 항목1 in 반복_가능_객체1 if 조건문1
          for 항목2 in 반복_가능_객체2 if 조건문2
          ...
          for 항목n in 반복_가능_객체n if 조건문n]            
          
# list comprehension 문법


    nums = [10,20,30,40,50]
    nums2 = [num * num for num in nums] # list comprehension 문법
    print(nums2)
        
    >> [100, 400, 900, 1600, 2500]

    nums = [i for i in range(51)]
    print(nums)

    nums = [i for i in range(51) if i % 2 == 0] 
    print(nums)


    nums = [i for i in range(51) if i % 2 == 0 ] 
    print(nums)
        
    >>

    [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50]
    [0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50]
    [0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50]
        

    dan99 = ["%d X %d = %d" % ( i, j, i * j) for i in range(2, 10) for j in range(1, 10)]
    print(dan99)
    >>
    ['2 X 1 = 2', '2 X 2 = 4', '2 X 3 = 6', '2 X 4 = 8', '2 X 5 = 10', '2 X 6 = 12', '2 X 7 = 14', '2 X 8 = 16', '2 X 9 = 18', '3 X 1 = 3', '3 X 2 = 6', ...


함수
    
    def 함수명(매개변수 정의..):
        수행할 코드
        ..
        return 반환값
        
        def add(a, b):
            return a + b
        x = add(10,20)
        print(x)

        def minus(a, b):
            return a - b
        r1 = minus(10, 20)
        r2 = minus(a=10, b=20)
        r3 = minus(b=20, a=10)
        print(r1, r2, r3)
        >> -10 -10 -10

        def minus2(a, b=10):   #  매개변수 b의 기본값 설정 -> 함수 오버로드 필요 없음, 기본값은 오른쪽 끝 매개뱐수 부터
            return a - b

        r4 = minus2(30)
        print(r4)
        >> 20
    
        
    1. 함수란 무엇인가?
    
    
    2. 함수를 사용하는 이유는 무엇일까?
    3. 파이썬 함수의 구조
    4. 매개변수와 인수
    5. 입력값과 리턴값에 따른 함수의 형태
        1) 일반적인 함수 : 입력값이 있고 리턴값이 있는 함수
        2) 입력값이 없는 함수 : 입력값은 없고 리턴값이 있는 함수
        3) 리턴값이 없는 함수
        4) 입력값도, 리턴값도 없는 함수

    6. 매개변수를 지정하여 호출하기
    7. 입력값이 몇 개가 될지 모를 때는 어떻게 해야 할까?
        1) 여러 개의 입력값을 받는 함수 만들기
        
           *변수명 -> 관례적으로 *args

            def calc(opr, *args):
                result = 0
                if opr == 'add':
                    for num in args:
                        result += num
                elif opr == 'minus':
                    for num in args:
                        result -= num
                elif opr == 'mul':
                    result = 1
                    for num in args:
                        result *= num
                return result

            result1 = calc('add', 10, 20, 30, 40, 50)
            result2 = calc('mul', 2,4,6,8)

            print(result1, result2)
            >> 150 384
            
        2) 키워드 매개변수, kwargs
        
            **변수명
            - map 형태로 매개변수가 담겨 있다.
            
            (참고) 관례적으로 **kwargs
                
            def keywords(**kwargs):
                print(kwargs)

            keywords(name="이이름", age=40)
            >> {'name': '이이름', 'age': 40}
                
            def allkeywords(*args, **kwargs):
                print(args)
                print(kwargs)
            allkeywords(10,20,30, name="이이름", age=40)

            >>
            (10, 20, 30)
            {'name': '이이름', 'age': 40}


    8. 함수의 리턴값은 언제나 하나이다.
    
        def calc(a, b):
            return a + b, a - b, a * b

        calc(20, 10)
        >> (30, 10, 200)    # 반환값은 1게임, 단 튜플일 뿐임
        
        def calc(a, b):
            return a + b
            return a - b
            return a * b
        calc(10, 20)
            
        >> 30
        
    
    9. return의 또 다른 쓰임새 
        - 실행 흐름 중지(함수 종료)

    10. 매개변수에 초기값 미리 설정하기
    
    11. 함수 안에서 선언한 변수의 효력 범위
    
    12. 함수 안에서 함수 밖의 변수를 변경하는 방법
    
        1) return 사용하기
        2) global 명령어 사용하기

            a = 10
            def add():     ## 순수하지 않은 함수 -> 이렇게 잘 안씀
                global a   ## 전역변수 a 
                a += 1
            add()
            print(a)

    13. lambda 예약어
    
        nums = [i for i in range(1, 11)]

        def square(num) :
            return num * num

        nums2 = list(map(square, nums))
        print(nums2)

        #위와 동일

        nums3 = list(map(lambda a: a * a, nums))
        print(nums3)
            
        >>

        [1, 4, 9, 16, 25, 36, 49, 64, 81, 100]
    
    
    14. 파이썬은 모든게 객체
    
        - 일등함수, 함수안에 함수
    
            def add(num1):
                def add2(num2):
                    return num1 + num2  
                return add2

            add10 = add(10)
            ret = add10(20)
            print(ret)    
            
            => 클로져
            
데코레이터 패턴            
        import time
        def elapse(func):
            def wrapper():
                start = time.time()

                result = func()  # 핵심기능

                end = time.time()

                print("걸린시간 : %f" % (end - start))
                return result
            return wrapper


        def myFunc() :
            print("실행!")

        decoratedMyFunc = elapse(myFunc)
        decoratedMyFunc()

        >>

        실행!
        걸린시간 : 0.000499

        아래위 동일

        import time
        def elapse(func):
            def wrapper():
                start = time.time()

                result = func()  # 핵심기능

                end = time.time()

                print("걸린시간 : %f" % (end - start))
                return result
            return wrapper

        @elapse
        def myFunc() :
            print("실행!")

        myFunc()

        >>
        
        실행!
        걸린시간 : 0.001884


    
사용자 입출력

    1. 사용자 입력 활용하기
    
        1) input 사용하기
           input()
           
            a = input("숫자입력:")
            print(a)           
            
                a = input("숫자입력:")
                print(a)
                type(a)   # str
                b = a + 123  # 다른 형 끼리 못더함 -> str(a) + 123
                print(b)
                >>
                    에러
    


            변환함수 
             - str(..)
             - int(..)
             - float(..)
             - bool(..)
             
             - list(..)
             - tuple(..)
             - dict(..)
             - set(...)


                print("AA" "BB" "CC")    >>  AABBCC
                print("AA" + "BB" + "CC")  >>  AABBCC
                print("AA", "BB", "CC") >> AA BB CC


                chars = ['A', 'B', 'C', 'D', 'E', 'F', 'G']
                for char in chars:
                    print(char, end ='')  # end 기본값 : \n
                    
                >> ABCDEFG
            
             
           
        2) 프롬프트를 띄워 사용자 입력받기
           
        

    2. print 자세히 알기
        1) 큰따옴표로 둘러싸인 문자열은 + 연산과 동일하다
        2) 문자열 띄어쓰기는 쉼표로 한다
        3) 한 줄에 결과값 출력하기
        
        
파일 읽고 쓰기
    1. 파일 생성하기
    
        1) 파일 열기 모드  - r(읽기 모드), w(쓰기 모드), a(추가 모드) 
            >>> f = open("D:/python/test1.txt", "w")
            >>> for i in range(1, 11) :              
            ...     f.write("%d행 입니다\n" % i)          
        
            >>> f = open("D:/python/test1.txt", "r")
            >>> data = f.read()
            >>> f.close()
            >>> print(data)

            >>> f = open("D:/python/test1.txt", "r")
            >>> while True:
            ...     line = f.readline()
            ...     if not line: break
            ...     print(line)
            ...
            1행 입니다

            2행 입니다

            3행 입니다

            4행 입니다

            5행 입니다

            6행 입니다

            7행 입니다

            8행 입니다

            9행 입니다

            10행 입니다

        
    2. 파일을 쓰기 모드로 열어 내용 쓰기

    3. 파일을 읽는 여러 가지 방법
    
        1) readline 함수 이용하기 : 한줄씩 읽기
        
        2) readlines 함수 사용하기 
        
        3) 줄 바꿈(\n) 문자 제거하기
        
            공백 , 탭, 줄개행문자 
            
            문자열
              strip() - 양쪽 공백 제거
              lstrip() - 왼쪽 공백 제거됨
              rstrip() - 오른쪽 공백 제거됨
              
                f = open("D:/python/test1.txt", "r")
                while True:
                    line = f.readline()
                    if not line: break
                    line = line.strip()
                    print(line)
                >>

                1행 입니다
                2행 입니다
                3행 입니다
                4행 입니다
                5행 입니다
                6행 입니다
                7행 입니다
                8행 입니다
                9행 입니다
                10행 입니다

                f = open("D:/python/test1.txt", "r")
                lines = f.readlines()
                print(lines)

                for line in lines :
                    print(line, end='')
                >>  
                ['1행 입니다\n', '2행 입니다\n', '3행 입니다\n', '4행 입니다\n', '5행 입니다\n', '6행 입니다\n', '7행 입니다\n', '8행 입니다\n', '9행 입니다\n', '10행 입니다\n']
                1행 입니다
                2행 입니다
                3행 입니다
                4행 입니다
                5행 입니다
                6행 입니다
                7행 입니다
                8행 입니다
                9행 입니다
                10행 입니다    
                
                
                f = open("D:/python/test1.txt", "r")
                for line in f:
                    print(line, end = '')                


                f = open("D:/python/test1.txt", "a")
                for i in range(11, 41) :
                    f.write("%d행 입니당\n" % i)
                f.close()

                with open("D:/python/test2.txt", "w") as f:  ## 자동 close()
                    f.write("오늘 파이썬 수업 총총")

                    
                    
        4) read 함수 사용하기
        
        5) 파일 객체를 for 문과 함께 사용하기

    4. 파일에 새로운 내용 추가하기
    
    5. with 문과 함께 사용하기        
    

프로그램의 입출력
    1. sys 모듈 사용하기

클래스
    1. 계산기 프로그램을 만들며 클래스 알아보기
    2. 클래스와 객체
    3. 사칙 연산 클래스 만들기
        1) 클래스 구조 만들기
        2) 객체에 연산할 숫자 지정하기
        3) 더하기 기능 만들기
        4) 곱하기, 빼기, 나누기 기능 만들기

    4. 생성자
    5. 클래스의 상속
    6. 메서드 오버라이딩
    7. 클래스변수
        1) 클래스 변수 사용하기
        2) 클래스변수와 동일한 이름의 객체변수를 생성하면?