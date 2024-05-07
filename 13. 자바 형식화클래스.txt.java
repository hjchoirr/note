형식화 클래스
1. DecimalFormat

2. SimpleDateFormat

3. ChoiceFormat

4. MessageFormat


날짜와 시간
1. Date
	JDK 1.0 java.util 패키지
	- 대부분 Deprecated
	- getTime() : 에포크타임 : 1970.1.1 부터 1000분의 1초단위..
	
		package exam02;
		import java.util.Date;

		public class Ex01 {
			public static void main(String[] args) {
				Date date = new Date();
				System.out.println(date);

				int year = date.getYear();
				int month = date.getMonth(); // 0 ~ 11

				System.out.printf("year=%d month=%d%n", year, month);

				long epochTime = date.getTime(); //1970.01.01 자정부터 1000분의 1초 단위
				System.out.println(epochTime);
			}
		}	
		>>
		Tue May 07 16:43:28 KST 2024
		year=124 month=4
		1715067808537	

2. Calendar 
	JDK 1.1
		- java.util.Calendar
		
		- 설계가 빈약
		- 이렇게 생성 못함 : Calendar cal = new Calendar() 
		
		- static Calendar getInstance()
			- 서기 달력외에 다른 체계 달력(불기)을 지역화 설정에 따라 객체 생성
			
		- int get(날짜 시간 필드)  : 날짜, 시간 조회
		- void set(날짜 시간 필드, 값) : 날짜, 시간 변경 
			- 날짜 시간 변경시 원 객체가 변경 : 불변성 유지 X
				: java.time 패키지에서 변경: 날짜 시간의 불변성 유지, 변경 할때마다 새로운 객체 반환 
				
		- add(...) : 
		
		- roll(...) : 
		

package exam02;
import java.util.Calendar;

public class Ex02 {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal);
    }
}
>>
java.util.GregorianCalendar[time=1715068217287,areFieldsSet=true,areAllFieldsSet=true,lenient=true,
zone=sun.util.calendar.ZoneInfo[id="Asia/Seoul",offset=32400000,dstSavings=0,useDaylight=false,transitions=30,lastRule=null],
firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,
YEAR=2024,MONTH=4,WEEK_OF_YEAR=19,WEEK_OF_MONTH=2,DAY_OF_MONTH=7,
DAY_OF_YEAR=128,DAY_OF_WEEK=3,DAY_OF_WEEK_IN_MONTH=1,AM_PM=1,HOUR=4,
HOUR_OF_DAY=16,MINUTE=50,SECOND=17,MILLISECOND=287,ZONE_OFFSET=32400000,DST_OFFSET=0]
	
package exam02;
import java.util.Calendar;

public class Ex02 {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        //System.out.println(cal);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        System.out.printf("year=%d month=%d day=%d%n", year, month + 1, day);
    }
}
>> 
year=2024 month=5 day=7	
	
package exam02;
import java.util.Calendar;

public class Ex02 {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        printDate(cal);

        cal.set(Calendar.MONTH, 0); // 1월 : 원래객체의 값을 변경
        printDate(cal);
    }
    public static void printDate(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        System.out.printf("year=%d month=%d day=%d%n", year, month + 1, day);
    }
}
year=2024 month=5 day=7
year=2024 month=1 day=7

	
java.time 패키지
	JDK8
	1. 설계의 보완
	2. 날짜/시간의 불변성
		- 날짜, 시간의 변경시 -> 새로운 객체 생성
	3. 메서드 명칭에서 오는 모호함을 개선(plus, minus)
	
- java.time : 핵심 클래스 
	1. Temporal, TemporalAccessor, TemporalAdjuster 인터페이스, 
	
	LocalDate : 날짜 
		LocalDateTime atTime(시간....)  : 날짜 + 시간
		
	LocalTime : 시간
		LocalDateTime atTime(LocalDate ...) : 시간 + 날짜
		
	LocalDateTime : 날짜(LocalDate) + 시간(LocalTime)
		ZonedDateTime atZone(ZoneId ...) : 날짜시간 + 시간대
		OffsetDateTime atOffset(ZoneOffset ...) 
	
	ZonedDateTime : 날짜와 시간(LocalDateTime) + 시간대(ZoneId - Asia/Seoul)
		- 섬머 타임제 고려 
		
	OffsetDateTime : 날짜와 시간(LocalDateTime) + 시간대(ZoneOffset - +9)
	
	Instant : EpochTime - 1970. 1. 1 자정부터(UTC+0) 1/1000 단위 카운트
				참고) Timestamp  - 초 단위 카운팅
	
	
	2. TemporalAmount 인터페이스
		Duration : 시간의 차이 (초, 나노 초)
			between
			until
			
		Period : 날짜의 차이
	
	
	
- java.time.format : 형식화 클래스 
				DateTimeFomatter
				
- java.time.temporal : 날짜, 시간의 단위, 필드
		TemporalField 인터페이스 - 필드
			- ChronoField 
			
		TemporalUnit 인터페이스 - 단위 
			- ChronoUnit
			
- java.time.zone : 시간대 관련 클래스 
		ZoneId
		
		ZoneOffset 


1. java.time 패키지의 핵심 클래스

1) LocalDate와 LocalTime
- 특정 필드의 값 가져오기 - get(), getXXX()
	int get(필드 명);
		ChronoField  : 날짜, 시간 필드
		
		
- 필드의 값 변경하기 - with(), plus(), minus()
	LocalDate with() : 날짜/시간 변경
	LocalDate plus() : 날짜/시간 +
	LocalDate minus() : 날짜/시간 -

- 날짜와 시간의 비교 - isAfter(), isBefore(), isEqual()
	- compareTo() : 음수 -  isBefore() 
	- compareTo() : 0  - isEqual()
	- compareTo() : 양수 - isAfter()

2. Period와 Duration

3. 객체 생성하기 - now(), of()
	now() : 현재 날짜,시간 
	of(....)


4. Temporal과 TemporalAmount

5. Instant


파싱과 포맷
java.time.format 

1. parse()
	형식화 문자열 -> 날짜/시간
	- 핵심 클래스 (LocalDate, LocalTime, LocalDateTime ... )
	
2. format() : 날짜/시간 -> 형식화 문자열 
	DateTimeFormatter 
			DateTimeFormatter state ofPattern("패턴")
					.format(TemporalAccessor ...)
					
					