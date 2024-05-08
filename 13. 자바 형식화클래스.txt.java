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
			
				: java.time 패키지에서 변경하면 날짜 시간의 불변성 유지, 변경 할때마다 새로운 객체 반환 
				
		- add(...) : 날짜 시간의 가감 -> 메서드명이 모호 : java.time 패키지에서는 plus() minus() 
			
		- roll(...) : 날짜 시간의 가감
		

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
				Calendar cal = Calendar.getInstance();  //생성자 호출 불가, 현재 날짜
				printDate(cal);

				cal.set(Calendar.MONTH, 0); // 1월 : 원래객체의 값을 변경, 불변성 유지 안됨
				printDate(cal);
				
				cal.add(Calendar.DAY_OF_MONTH, 150); // 100일 후
				printDate(cal);

				cal.add(Calendar.DAY_OF_MONTH, -100); // 다시 100일 전
				printDate(cal);				
			}
			public static void printDate(Calendar cal) {
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				System.out.printf("year=%d month=%d day=%d%n", year, month + 1, day);  // month : 0 ~ 11월
			}
		}
		year=2024 month=5 day=7
		year=2024 month=1 day=7
		---------------------------------------------------------
		package exam01;
		import java.util.Calendar;

		public class Ex02 {
			public static void main(String[] args) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, 30); // 100일 후
				printDate(cal);

				Calendar cal2 = Calendar.getInstance();
				cal2.roll(Calendar.MONTH, 30); // 100일 후
				printDate(cal2);

			}
			public static void printDate(Calendar cal) {
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);  // 0 ~ 11
				int day = cal.get(Calendar.DAY_OF_MONTH);

				System.out.printf("year=%d month=%d day=%d%n", year, month + 1, day);
			}
		}
		>>
		year=2026 month=11 day=8
		year=2024 month=11 day=8
	
	Calendar 문제점 
	
		- 설계(인터페이스 X), 체계 부족(상수, 기능 분리 X)
		- 날짜, 시간의 불변성 X, 변경시에 원래 객체가 변경(변경전 데이터는 확인 불가)
		- 메서드명의 모호함, 날짜 수치(월)의 모호함(월 - 0~11)
			
			
java.time 패키지
	JDK8
	1. 설계의 보완
	
		- 인터페이스
		
			Temporal, TemporalAccessor, TemporalAdjuster 
			 - LocalDate, LocalTime, LocalDateTime, ZonedDateTime, OffsetDateTime, instant
			   ( 날짜, 시간, 날짜와시간,
			
			TemporalAmount
			 - Period, Duration 
			
			TemporalField : 날짜, 시간 필드
			 - ChronoField
			
			TemporalUnit : 날짜, 시간 단위
			 - ChronoUnit
			
			
	2. 날짜/시간의 불변성
		- 날짜, 시간의 변경시 -> 새로운 객체 생성
		
	3. 메서드 명칭에서 오는 모호함을 개선(plus, minus)


		package exam01;
		import java.time.LocalDate;

		public class Ex03 {
			public static void main(String[] args) {
				LocalDate toDay = LocalDate.now();
				System.out.println(toDay);

				// month : 1 ~ 12월
				LocalDate date = LocalDate.of(2023,5,8);
				System.out.println(date);

				
			}
		}
		>>
		2024-05-08
		2023-05-08


	
- java.time : 핵심 클래스 
	1. Temporal, TemporalAccessor, TemporalAdjuster 인터페이스, 
	   (날짜시간, 날짜시간 조회, 날짜시간 적용)
	
		LocalDate : 날짜 
			LocalDateTime atTime(시간....)  : 날짜 + 시간
			
			static LocalDate now() : 현재날짜
			static LocalDate of(..) : 지정한 날짜
			
			조회
			  int/long get(TemporalField..)
			  ChronoField
			  (참고) 지역화 Locale
			  
			변경
			  with(..) : 날짜 변경 withDayOfMonth(..)  ..
			  plus(..) : 날짜 더하기 plusDays(..)  ..
			
		LocalTime : 시간
			LocalDateTime atDate(LocalDate ...) : 시간 + 날짜
			
		LocalDateTime : 날짜(LocalDate) + 시간(LocalTime)
			ZonedDateTime atZone(ZoneId ...) : 날짜시간 + 시간대
			OffsetDateTime atOffset(ZoneOffset ...) 
		
		ZonedDateTime : 날짜와 시간(LocalDateTime) + 시간대(ZoneId - Asia/Seoul)
			- 섬머 타임제 고려 
			
		OffsetDateTime : 날짜와 시간(LocalDateTime) + 시간대(ZoneOffset - +9)
		
		Instant : EpochTime - 1970. 1. 1 자정부터(UTC+0) 1/1000 단위 카운트
					참고) Timestamp  - 초 단위 카운팅
				: Date 클래스로 만들어진 객체 변환할때 사용 : Date클래스에 toInstance()	
					
			package exam01;
			import java.time.LocalDate;
			import java.time.temporal.ChronoField;

			public class Ex04 {
				public static void main(String[] args) {
					LocalDate today = LocalDate.now();

					int year = today.get(ChronoField.YEAR);
					int month = today .get(ChronoField.MONTH_OF_YEAR);
					int day = today.get(ChronoField.DAY_OF_MONTH);
					int yoil = today.get(ChronoField.DAY_OF_WEEK); //요일 1(일) ~ 7(토)

					System.out.printf("year=%d month=%d day=%d yoil=%d%n", year, month, day, yoil);
				}
			}
			>>
			year=2024 month=5 day=8 yoil=3		
			----------------------------------------
			package exam01;
			import java.time.LocalDate;

			public class Ex04 {
				public static void main(String[] args) {
					LocalDate today = LocalDate.now();

					/*
					int year = today.get(ChronoField.YEAR);
					int month = today .get(ChronoField.MONTH_OF_YEAR);
					int day = today.get(ChronoField.DAY_OF_MONTH);
					int yoil = today.get(ChronoField.DAY_OF_WEEK); //요일 1(일) ~ 7(토)
					 */

					int year = today.getYear();
					int month = today.getMonthValue();
					int day = today.getDayOfMonth();
					int yoil = today.getDayOfWeek().getValue();

					System.out.printf("year=%d month=%d day=%d yoil=%d%n", year, month, day, yoil);
				}
			}
			>>
			year=2024 month=5 day=8 yoil=3		
			------------------------------------------------
			package exam01;
			import java.time.LocalDate;
			import java.time.Month;
			import java.time.format.TextStyle;
			import java.util.Locale;

			public class Ex05 {
				public static void main(String[] args) {
					LocalDate today = LocalDate.now();
					Month month = today.getMonth();

					Locale locale = new Locale("ko", "kr");
					String yoilStr = month.getDisplayName(TextStyle.FULL, locale);
					System.out.println(yoilStr);

					String yoilStrEng = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
					System.out.println(yoilStrEng);
				}
			}
			>>
			5월
			May
			------------------------------------------------------------------------------------
			package exam01;
			import java.time.DayOfWeek;
			import java.time.LocalDate;
			import java.time.format.TextStyle;
			import java.util.Locale;

			public class Ex06 {
				public static void main(String[] args) {
					LocalDate today = LocalDate.now();
					DayOfWeek dayOfWeek = today.getDayOfWeek();

					String yoilStr = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
					System.out.println(yoilStr);

					String yoilStr2 = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);
					System.out.println(yoilStr2);
				}
			}
			>>
			수요일
			수
			------------------------------------------------------
			package exam01;
			import java.time.LocalDate;
			import java.time.temporal.ChronoField;

			public class Ex07 {
				public static void main(String[] args) {
					LocalDate today = LocalDate.now();

					LocalDate lastYear = today.with(ChronoField.YEAR, 2023);
					System.out.println(today);    // 불변성 유지 2024-05-08
					System.out.println(lastYear); // 새로운 객체로 생성됨 2023-05-08
				}
			}			
			>>
			2024-05-08
			2023-05-08
			----------------------------------------
			package exam01;
			import java.time.LocalDate;
			import java.time.temporal.ChronoUnit;

			public class Ex08 {
				public static void main(String[] args) {
					LocalDate today = LocalDate.now();

					LocalDate theDay = today.plus(150, ChronoUnit.DAYS);
					LocalDate theDay2 = today.minus(150, ChronoUnit.DAYS);
					System.out.println("오늘 : " + today);
					System.out.println("150일 후 : " + theDay);
					System.out.println("150일 전 : " + theDay2);
				}
			}
			>>
			오늘 : 2024-05-08
			150일 후 : 2024-10-05
			150일 전 : 2023-12-10
			--------------------------------------------------
			package exam01;
			import java.time.LocalTime;

			public class Ex10 {
				public static void main(String[] args) {
					LocalTime today = LocalTime.now();
					LocalTime time = LocalTime.of(23, 30,10);

					System.out.println(today);
					System.out.println(time);
				}
			}
			----------------------------------------------------
			package exam01;
			import java.time.LocalDate;
			import java.time.LocalDateTime;
			import java.time.LocalTime;

			public class Ex11 {
				public static void main(String[] args) {
					LocalDate today = LocalDate.now();
					LocalDateTime today2 = today.atTime(LocalTime.now());
					System.out.println(today2);
				}
			}
			>>
			2024-05-08T16:00:43.105527100
			----------------------------------------------------
			package exam01;
			import java.time.ZoneId;
			import java.time.ZonedDateTime;
			import java.util.Set;

			public class Ex09 {
				public static void main(String[] args) {
					ZonedDateTime zdt1 = ZonedDateTime.now();
					System.out.println(zdt1);  // 2024-05-08T16:33:02.088887200+09:00[Asia/Seoul]

					//ZoneId.getAvailableZoneIds().forEach(System.out::println);

					ZoneId london = ZoneId.of("Europe/London");
					ZonedDateTime londonZdt = zdt1.withZoneSameInstant(london);
					System.out.println(londonZdt); // 2024-05-08T08:33:02.088887200+01:00[Europe/London]

					ZonedDateTime newYorkZdt = zdt1.withZoneSameInstant(ZoneId.of("America/New_York"));
					System.out.println(newYorkZdt); //2024-05-08T03:33:02.088887200-04:00[America/New_York]

				}
			}
			----------------------------------------------------------
			package exam01;
			import java.time.Instant;

			public class Ex13 {
				public static void main(String[] args) {
					Instant today = Instant.now();
					System.out.println(today);   // 2024-05-08T08:00:17.030947500Z
				}
			}
			
			
			
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
					
					