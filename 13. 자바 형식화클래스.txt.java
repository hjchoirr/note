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
			

		ZonedDateTime : 날짜와 시간(LocalDateTime) + 시간대(ZoneId - Asia/Seoul)
			- 섬머 타임제 고려 
			
		OffsetDateTime : 날짜와 시간(LocalDateTime) + 시간대(ZoneOffset - +9)
		
		Instant : EpochTime - 1970. 1. 1 자정부터(UTC+0) 1/1000 단위 카운트
					참고) Timestamp  - 초 단위 카운팅
					
				: Date 클래스로 만들어진 객체 변환할때 사용 : Date클래스에 toInstance()	

			package exam01;
			import java.time.Instant;
			import java.time.Period;

			public class Ex01 {
				public static void main(String[] args) {
					Instant today = Instant.now();
					System.out.println(today);
					long time1 = today.getEpochSecond(); //초단위 timestamp
					long time2 = today.toEpochMilli(); //밀리세컨즈 EpochTime

					System.out.printf("time1=%d time2=%d%n", time1, time2);
				}
			}
			>>
			2024-05-09T05:15:55.604190100Z
			time1=1715231755 time2=1715231755604

			
	2. TemporalAmount 인터페이스
		Duration : 시간의 차이 (초, 나노 초)
			between
			until
			
		Period : 날짜의 차이

			package exam01;
			import java.time.LocalDate;
			import java.time.Period;

			public class Ex02 {
				public static void main(String[] args) {
					LocalDate today = LocalDate.now();
					LocalDate endDate = LocalDate.of(2024,9,30);
					Period period = Period.between(today,endDate); // 날짜 간격

					System.out.println(period);
					int month = period.getMonths();
					int day = period.getDays();

					System.out.printf("남은 수업 %d개월 %d일", month, day);
				}
			}
				
			>>
			P4M21D
			남은 수업 4개월 21일	
			
			-----------------------------------------------------------------
			package exam01;
			import java.time.Duration;
			import java.time.LocalTime;

			public class Ex03 {
				public static void main(String[] args) {
					LocalTime now = LocalTime.now();
					LocalTime endTime = LocalTime.of(17,50);

					Duration duration = Duration.between(now, endTime);
					System.out.println(duration);

					long sec = duration.getSeconds();
					LocalTime time = LocalTime.of(0,0,0);
					LocalTime time2 = time.plusSeconds(sec);

					int hours = time2.getHour();
					int mins = time2.getMinute();
					int secs = time2.getSecond();

					System.out.printf("수업종료까지 %d시간 %d분 %d초 남음", hours, mins, secs);
				}
			}
			>>
			PT3H10M9.6439587S
			수업종료까지 3시간 10분 9초 남음
			
	
- java.time.format : 형식화 클래스 
				DateTimeFomatter
				
- java.time.temporal : 날짜, 시간의 단위, 필드
		TemporalField 인터페이스 - 필드
			- ChronoField 
			
		TemporalUnit 인터페이스 - 단위 
			- ChronoUnit
			
- java.time.zone : 시간대 관련 클래스 
	ZonedDateTime
		- ZoneId : Asisa/Seoul
		- 썸머타임제
		
	OffsetDateTime
		- ZoneOffset : +9
		  UTC :세계 표준 협정시
		  
	Instant
	

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
		형식화된 문자열 -> 날짜/시간 자바객체로 변환
		- 핵심 클래스 (LocalDate, LocalTime, LocalDateTime ... )
		
	2. format() : 날짜/시간 자바객체 -> 형식화된 문자열 로 변환
		DateTimeFormatter 
				DateTimeFormatter state ofPattern("패턴")
						.format(TemporalAccessor ...)
					

			package exam01;
			import java.time.LocalDateTime;
			import java.time.format.DateTimeFormatter;

			public class Ex04 {
				public static void main(String[] args) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss E");
					LocalDateTime startDate = LocalDateTime.of(2024, 3, 19, 9, 0);

					String strDate = formatter.format(startDate);
					System.out.println(strDate);
				}
			}
			>>
			2024.03.19 09:00:00 화	
			-----------------------------------------------------------------
			package exam01;
			import java.time.LocalDateTime;
			import java.time.format.DateTimeFormatter;

			public class Ex05 {
				public static void main(String[] args) {
					String str = "05/09/24 15:16";
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
					LocalDateTime date = LocalDateTime.parse(str,formatter);
					System.out.println(date);
				}
			}
			>>
			2024-05-09T15:16			
			-----------------------------------------------------------------
			

형식화 클래스
1. DecimalFormat
	
	숫자 -> 형식화된 문자열
	10000 -> 10,000
	
	format(..) : 숫자 -> 형식회된 문자열
	parse(..) :  형식화된 문자열 -> 자바 클래스
	
	0패턴
	#패턴

		package exam03;
		import java.text.DecimalFormat;

		public class Ex01 {
			public static void main(String[] args) {
				double num1 = 10000000000.123;

				DecimalFormat df = new DecimalFormat("0,000.0000");
				String num1Str = df.format(num1);
				System.out.println(num1Str);
			}
		}
		>>
		10,000,000,000.1230
		--------------------------------------------------------------
		package exam03;
		import java.text.DecimalFormat;

		public class Ex01 {
			public static void main(String[] args) {
				double num1 = 10000000000.123;
				double num2 = 0.123;

				DecimalFormat df = new DecimalFormat("0,000.0000");
				String num1Str = df.format(num1); // 10,000,000,000.1230
				System.out.println(num1Str);   

				DecimalFormat df2 =  new DecimalFormat("#,###.####");
				String num1Str2 = df2.format(num1);  // 10,000,000,000.123
				String num1Str3 = df2.format(num2);  // 0.123
				System.out.println(num1Str2);
				System.out.println(num1Str3);
			}
		}
		----------------------------------------------------------
		package exam03;
		import java.text.DecimalFormat;
		import java.text.ParseException;

		public class Ex02 {
			public static void main(String[] args) throws ParseException {
				String price = "1,000,000원";

				DecimalFormat df = new DecimalFormat("#,###원");
				Number number = df.parse(price);
				long num = number.longValue();
				System.out.println(num); //1000000
			}
		}
		

2. SimpleDateFormat

	- 날짜 형식화 :java.util.Date 객체
	String format(..) :날짜 Date 객체를 형식화된 문자열로 변환
	Date parse(..) : 형식화된 문자열을 날짜 Date 객체로 변환
	
		package exam03;
		import java.text.SimpleDateFormat;
		import java.util.Date;

		public class Ex03 {
			public static void main(String[] args) {
				Date date = new Date();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd a hh:mm");
				String strDate = sdf.format(date);
				System.out.println(strDate);  // 2024.05.09 오후 04:29
			}
		}
		--------------------------------------------------------------	
		package exam03;
		import java.text.ParseException;
		import java.text.SimpleDateFormat;
		import java.util.Date;

		public class Ex04 {
			public static void main(String[] args) throws ParseException {
				String strDate = "31/05/23 00:00";

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
				Date dt = sdf.parse(strDate);

				System.out.println(dt); //Wed May 31 00:00:00 KST 2023
			}
		}
	
3. ChoiceFormat
			package exam03;
			import java.text.ChoiceFormat;

			public class Ex05 {
				public static void main(String[] args) {
					double[] limits = {50, 70, 80, 90};
					String[] grades = {"D", "C", "B", "A"};

					ChoiceFormat cf = new ChoiceFormat(limits, grades);
					int[] scores = { 100, 70, 55, 80, 95, 87};

					for(int score : scores) {
						String grade = cf.format(score);
						System.out.printf("점수:%d  학점:%s  %n", score, grade);
					}
				}
			}
			>>
			점수:100  학점:A  
			점수:70  학점:C  
			점수:55  학점:D  
			점수:80  학점:B  
			점수:95  학점:A  
			점수:87  학점:B  
			--------------------------------------------------
			package exam03;
			import java.text.ChoiceFormat;

			public class Ex05 {
				public static void main(String[] args) {
					/*
					double[] limits = {50, 70, 80, 90};
					String[] grades = {"D", "C", "B", "A"};
					ChoiceFormat cf = new ChoiceFormat(limits, grades);
					 */
					String pattern = "60#D|70#C|80<B|90#A";  // # : 크거나 같다  < 크다
					ChoiceFormat cf = new ChoiceFormat(pattern);
					int[] scores = { 100, 70, 55, 80, 95, 87};

					for(int score : scores) {
						String grade = cf.format(score);
						System.out.printf("점수:%d  학점:%s  %n", score, grade);
					}
				}
			}
			>>
			점수:100  학점:A  
			점수:70  학점:C  
			점수:55  학점:D  
			점수:80  학점:C   // <-- # : 크거나 같다  < 크다
			점수:95  학점:A  
			점수:87  학점:B  



4. MessageFormat

	String format(..) : 형식화된 문자열로 
	Object[] parse(..) : 형식화된 문자열 -> 원래 데이터의 객체로 변환
	
		package exam03;
		import java.text.MessageFormat;

		public class Ex06 {
			public static void main(String[] args) {
				String pattern = "이름:{0}, 전화번호:{1}";
				String[] names = {"이이름", "김이름", "최이름"};
				String[] mobiles = {"010-000-0000", "010-1000-0000", "010-2000-20000"};

				for(int i = 0; i < names.length; i ++) {
					String str = MessageFormat.format(pattern, names[i], mobiles[i]);
					System.out.println(str);
				}
			}
		}
		>>
		이름:이이름, 전화번호:010-000-0000
		이름:김이름, 전화번호:010-1000-0000
		이름:최이름, 전화번호:010-2000-20000
		-------------------------------------------
		package exam03;
		import java.text.MessageFormat;
		import java.text.ParseException;

		public class Ex07 {
			public static void main(String[] args) throws ParseException {
				String str = "이름: 이이름, 전화번호: 010-000-0000";
				String pattern = "이름: {0}, 전화번호: {1}";

				MessageFormat mf = new MessageFormat(pattern);
				Object[] data = mf.parse(str);

				String name = (String)data[0];
				String mobile = (String)data[1];

				System.out.println(name);
				System.out.println(mobile);
			}
		}
		>> 이이름
		010-000-0000	
	
		-------------------------------------------------------------------
		package exam01;
		public class Ex01 {
			public static void main(String[] args) {

				// %d : 정수 , %f : 실수 , %s : 문자열, %c : 문자

				System.out.printf("[%s]", "ab"); // [ab]
				System.out.printf("[%5s]", "ab");  // [   ab]
				System.out.printf("[%-5s]", "ab"); // [ab   ]
				System.out.println();

				System.out.printf("[%5d]", 100);  // [  100]
				System.out.printf("[%05d]", 100); // [00100]
				System.out.println();

				double num = 0.12345678;
				System.out.printf("[%f]", num);    // [0.123457]
				System.out.printf("[%.2f]", num);  // [0.12]
				System.out.printf("[%7.2f]", num); // [   0.12]  %전체자리수.소수점자리수
				System.out.println();

				double num2 = 12.0;
				System.out.printf("[%f]", num2);    // [12.000000]
				System.out.printf("[%.2f]", num2);  // [12.00]
				System.out.printf("[%7.2f]", num2); // [  12.00]

			}
		}
