
쓰레드

	쓰레드 : 작업 메서드 + 호출 스택   (작업대) 
	쓰레드들 중에서 메인쓰레드 : Main 메서드 + Main 호출 스택

프로세스와 쓰레드
	1. 개념 
		1) 실행 중인 프로그램(program)
		2) 프로그램을 수행하는 데 필요한 데이터와 메모리등의 자원 그리고 쓰레드로 구성
		3) 프로세스의 자원을 이용해서 실제로 작업을 수행하는 것이 쓰레드이다
		4) 모든 프로세스에는 최소한 하나 이상의 쓰레드가 존재하며, 둘 이상의 쓰레드를 가진 프로세스를 멀티쓰레드 프로세스(multi-threaded process)라고 한다.
		5) 프로세스의 메모리 한계에 따라 생성할 수 있는 쓰레드의 수가 결정
		
	2. 멀티쓰레딩
		- 하나의 프로세스내에서 여러 쓰레드가 동시에 작업을 수행하는 것

	3. 멀티쓰테딩의 장점
		1) CPU의 사용률을 향상시킨다.
		2) 자원을 보다 효율적으로 사용할 수 있다.
		3) 사용자에 대한 응답성이 향상된다.
		4) 작업이 분리되어 코드가 간결해진다.

	4. 멀티쓰레딩의 단점
	여러 쓰레드가 같은 프로세스 내에서 자원을 공유하면서 작업을 하기 때문에 발생할 수 있는 동기화(synchronization), 교착상태(deadlock)와 같은 문제를 고려해서 신중하게 프로그래밍해야 한다.


쓰레드의 구현과 실행

	1. Thread클래스를 상속받는 방법과 Runnable인터페이스를 구현하는 방법
	2. Thread클래스를 상속받으면 다른 클래스를 상속받을 수 없기 때문에 Runnable 인터페이스를 구현하는 방법이 일반적
	
	3. 쓰레드의 실행 - start()
		1) start()와 run()
		
			start(): 독립적인 호출 스택 + run() 실행 / 병렬적인 작업이 가능
			run() 만 호출하면 -> main 쓰레드에서 순차적으로 실행함 (병렬처리 안함)
	
		2) 실행중인 사용자 쓰레드가 하나도 없을 때 프로그램은 종료된다.
		
		
			public class Ex01 {
				public static void main(String[] args) {
					Runnable r = () -> {
					  for(int i = 0 ; i < 5; i++) {
						  System.out.println("쓰레드2-" + i);
						  for(long j = 0; j < 10000000000L; j++ ) ;
					  }
					};

					Ex01_1 th1 = new Ex01_1();
					Thread th2 = new Thread(r);

					th1.start(); // 호출스택 생성 + run() 실행
					th2.start(); // 호출스택 생성 + run() 실행
				}
			}
			class Ex01_1 extends Thread {
				public void run() {
					for(int i = 0 ; i < 5; i++) {
						System.out.println("쓰레드1-" + i);
						for(long j = 0; j < 10000000000L; j++ ) ;
					}
					System.out.println("작업종료!");
				}
			}
			>>
			쓰레드2-0
			쓰레드1-0
			쓰레드1-1
			쓰레드2-1
			쓰레드1-2
			쓰레드2-2
			쓰레드1-3
			쓰레드2-3
			쓰레드1-4
			쓰레드2-4
			작업종료!			
			------------------------------------------
			package exam01;
			public class Ex02 {
				public static void main(String[] args) {
					Ex02_1 th1 = new Ex02_1();
					th1.start(); // start()으로 Thread를 만들고 RUN 시킴
				}
			}

			class Ex02_1 extends Thread {
				public  void run() {
					throwException();
				}
				private void throwException() {
					try {
						throw new Exception();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			>> // 맨 아래 main 이 없다 start으로 Thread가 만들어짐
			java.lang.Exception
				at exam01.Ex02_1.throwException(Ex02.java:16)
				at exam01.Ex02_1.run(Ex02.java:12)
			----------------------------------------
			package exam01;
			public class Ex02 {
				public static void main(String[] args) {
					Ex02_1 th1 = new Ex02_1();
					th1.run();  // run() 으로는 Thread 가 안만들어짐
				}
			}

			class Ex02_1 extends Thread {
				public  void run() {
					throwException();
				}
				private void throwException() {
					try {
						throw new Exception();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			>> // 맨 아래 main 이 있다 run() 으로는 Thread 가 안만들어짐
			java.lang.Exception
				at exam01.Ex02_1.throwException(Ex02.java:16)
				at exam01.Ex02_1.run(Ex02.java:12)
				at exam01.Ex02.main(Ex02.java:6)
			----------------------------------------
			package exam01;
			import javax.swing.*;

			public class Ex05 {
				public static void main(String[] args) throws InterruptedException {

					Thread th = new Thread(() -> {
						String message = JOptionPane.showInputDialog("메세지입력");
						System.out.println(message);
					});
					th.start();

					for(int i = 0;i <= 10; i++ ){
						System.out.println(i);
						Thread.sleep(1000);
					}

				}
			}			 
			----------------------------------------

싱글쓰레드와 멀티쓰레드

	1. 하나의 쓰레드로 두 작업을 처리하는 경우 한 작업을 마친 후에 다른 작업을 시작한다.
	2. 두 개의 쓰레드로 작업하는 경우에는 짧은 시간동안 2개의 쓰레드가 번갈아 가면서 작업을 수행해서 
		동시에 두 작업이 처리되는 것과 같이 느끼게 한다.
	    - 시분할방식
	
	3. 하나의 쓰레드로 두개의 작업을 수행한 시간과 두개의 쓰레드로 두 개의 작업을 수행한 시간은 거의 같다.
	4. 오히려 두 개의 쓰레드로 작업한 시간이 싱글쓰레드로 작업한 시간보다 더 걸리게 되는데, 
		쓰레드간의 작업 전환(context switching)에 시간이 걸리기 때문이다.

쓰레드의 우선순위

	1. 쓰레드 우선순위 지정하기

쓰레드 그룹(thread group)

데몬 쓰레드(daemon thread)
  - 현재 작업중인 쓰레드의  작업이 종료되면 함께 종료되는 쑈레드  : 주로 백그라운드에서 돌아가는 쓰레드
  
		-----------------------------------------------------
		package exam01;
		public class Ex07 {
			private static boolean autoSave = false;

			public static void main(String[] args) throws InterruptedException {
				Thread th = new Thread(() -> {
					while(true) {
						try {
							Thread.sleep(3000);
						} catch( InterruptedException e) {}
						System.out.println("저장!");
					}
				});
				th.setDaemon(true);  // 데몬쓰레드로 만들기
				th.start();

				for(int i = 1; i <= 10; i++) {
					Thread.sleep(1000);
					System.out.println(i);
					if(i == 3) {
						autoSave = true;
					}
				}
			}
		}
		-----------------------------------------------------
  

쓰레드의 실행제어

	1. 쓰레드와 스케줄링과 관련된 메서드
	2. 쓰레드의 상태
	3. sleep(long millis)
	4. interrupt()와 interrupted()
	5. suspend(), resume(), stop()
	6. yield()
	7. join() 

쓰레드의 동기화
1. synchronized를 이용한 동기화
	1) 메서드 전체를 임계영역으로 지정
	2) 특정한 영역을 임계 영역으로 지정

2. volatile