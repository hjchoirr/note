
쓰레드

	쓰레드 : 작업 메서드 + 호출 스택   (작업대) 
	쓰레드들 중에서 메인쓰레드 : Main 메서드 + Main 호출 스택
	
	작업메서드가 main()이라면 : 메인 쓰레드
	작업메서드가 run() 이라면 : 사용자 정의 쓰레드
	
	

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


쓰레드의 생성과 실행 - 사용자 정의 쓰레드

	1. Thread클래스를 상속받는 방법과 Runnable인터페이스를 구현하는 방법
	
	2. Runnable 인터페이스를 구현하는 방법이 일반적
		- Thread클래스를 상속받으면 다른 클래스를 상속받을 수 없기 때문에
		- Thread클래스 생성자 매개변수로 Runnable 매개변수로 

		--잘 안쓰는 쓰레드 생성 방식: 상속---------------------------------
		package exam01;
		public class Ex01 {
			public static void main(String[] args) {
				Ex01_1 th = new Ex01_1();
				th.start();  // 새로운 호출 스택을 만들고 run() 호출함

				for(int i = 0; i <= 5 ; i++) {
					System.out.println("메인 쓰레드 - " + i);

				}
			}
		}
		// Thread 클래스 상속
		class Ex01_1 extends Thread {
			public void run() {
				for( int i = 0 ; i <= 5 ; i++ ) {
					System.out.println("쓰레드 - " + i);
				}
			}
		}
		--많이 쓰는 쓰레드 생성 방식-------------------------------------
		package exam01;
		public class Ex02 {
			public static void main(String[] args) {
				Thread th = new Thread(new Ex02_1());
				th.start();
				for(int i = 0 ; i < 5; i ++) {
					System.out.println("메인쓰레드 - " + i);
				}
			}
		}
		class Ex02_1 implements Runnable {

			@Override
			public void run() {
				for(int i = 0; i < 5; i++) {
					System.out.println("쓰레드 - " + i);
				}
			}
		}			
		-----------------------------------------------------------------
		package exam01;
		public class Ex02 {
			public static void main(String[] args) {
				Thread th = new Thread(new Ex02_1());
				th.start();
				for(int i = 0 ; i < 5; i ++) {
					System.out.println("메인쓰레드 - " + i);
				}
			}
		}
		class Ex02_1 implements Runnable {

			@Override
			public void run() {
				//실행중인 객체?
				Thread th = Thread.currentThread();

				for(int i = 0; i < 5; i++) {
					System.out.println(th.getName() + " - " + i);
				}
			}
		}
		-----------------------------------------------------------------



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

					Ex01_1 th1 = new Ex01_1(); // 쓰레드 생성 방법1. Thread클래스를 상속받는 방법
					Thread th2 = new Thread(r); // 쓰레드 생성 방법2

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
		- 우선순위가 높은 경우 시간을 더 많이 할당해서 실행을 더 많이 하도록 시간 확보
		   1 ~ 10 : 10에 가까울 수록 우선순위가 높다, 1에 가까울 수록 우선 순위가 낮다
		   setPriority(1~10)
		   
			package exam01;
			public class Ex04 {
				public static void main(String[] args) {
					Runnable r = () -> {
					  for(int i = 0; i < 300; i++) {
						  System.out.print("-");
						  for(long j = 0 ; j < 100000000L; j++) ;
					  }
					};

					Runnable r2 = () -> {
					  for(int i = 0; i < 300; i++) {
						  System.out.print("+");
						  for(long j = 0 ; j < 100000000L; j++) ;
					  }
					};

					Thread th1 = new Thread(r);
					Thread th2 = new Thread(r2);
					th1.setPriority(Thread.MAX_PRIORITY);
					th2.setPriority(Thread.MIN_PRIORITY);

					System.out.printf("th1:%d, th2:%d%n", th1.getPriority(), th2.getPriority());
					th1.start();
					th2.start();

				}
			}
		

쓰레드 그룹(thread group)
	- 쓰레드 그룹을 설정하지 않으면 모두 메인 쓰레드 그룹

데몬 쓰레드(daemon thread)
  - 현재 작업중인 쓰레드의  작업이 종료되면 함께 종료되는 쑈레드  : 주로 백그라운드에서 돌아가는 쓰레드
  
		-----------------------------------------------------
		package exam01;
		public class Ex05 {
			private static boolean autoSave = false;

			public static void main(String[] args) {
				Thread th = new Thread(() -> {
					while(true) {
						if(autoSave) {
							System.out.println("저장!");
						}
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {}
					}

				});
				th.setDaemon(true);// 이거 없으면  메인쓰레드 끝나도 쓰레드는 무한 반복함
				th.start();

				for(int i = 1; i < 10; i++) {
					Thread th2 = Thread.currentThread();
					System.out.println(th2.getName() + " - " + i);
					try {
						Thread.sleep(1000);
					}catch(InterruptedException e) {}

					if(i == 3) autoSave = true;
				}
			}
		}
		>>
		-----------------------------------------------------
		
  

쓰레드의 실행제어

	1. 쓰레드와 스케줄링과 관련된 메서드
	2. 쓰레드의 상태
	3. sleep(long millis)
	
	4. interrupt()와 interrupted()
		- interrupt() : 실행정지 상태인 sleep() , join() 을 다시 실행 다시 상태로 변경
		  ( interruptedException 발생시킴, interrupted - true)
		  
		  interrupt()호출 -> isInterruped() 가 true로 변경 
							interrupted() 호출, InterruptedException 도 발생, isInterrupted false로 변경
		  
			package exam01;
			public class Ex06 {
				public static void main(String[] args) {
					Thread th = new Thread(()-> {
						Thread th2 = Thread.currentThread();
						int num = 0;
						while(!th2.isInterrupted()) {
							System.out.println(num++);
							for(long j = 0; j < 1000000000L; j++);
						}
					});

					th.start();
					try {
						Thread.sleep(3000);
					} catch(InterruptedException e) {}

					th.interrupt(); // isInterrupted() -> true
				}
			}
		  
		  
	5. suspend(), resume(), stop()
		- suspend() : 일시정지
		- resume() : 재시작
		- stop()
		-> 교착상태 유발할 가능성 있으므로 사용 지양
		
	6. yield() : 다른 쓰레드에게 작업 양보
	
	7. join() : join한 쓰레드가 완료되면 현재 쓰레드가 종료
	
			package exam01;
			public class Ex04 {
				public static void main(String[] args) {
					Runnable r = () -> {
					  for(int i = 0; i < 300; i++) {
						  System.out.print("-");
						  for(long j = 0 ; j < 100000000L; j++) ;
					  }
					};

					Runnable r2 = () -> {
					  for(int i = 0; i < 300; i++) {
						  System.out.print("+");
						  for(long j = 0 ; j < 100000000L; j++) ;
					  }
					};

					Thread th1 = new Thread(r);
					Thread th2 = new Thread(r2);

					th1.start();
					th2.start();
					
					try {
						th1.join();   // 이거 없으면 th1, th2 끝나지 않아도 메인 쓰레드는 작업종료! 출력하고 끝남
						th2.join();   // 이거 없으면 th1, th2 끝나지 않아도 메인 쓰레드는 작업종료! 출력하고 끝남
					} catch (InterruptedException e) {}
					
					System.out.println("작업종료!"); //메인 쓰레드
				}
			}
	
	

쓰레드의 동기화
1. synchronized를 이용한 동기화
	1) 메서드 전체를 임계영역으로 지정
	2) 특정한 영역을 임계 영역으로 지정

2. volatile


		package exam01;
		public class Ex09 {
			public static void main(String[] args) throws InterruptedException{
				Ex09_1 th1 = new Ex09_1("*");
				Ex09_1 th2 = new Ex09_1("**");
				Ex09_1 th3 = new Ex09_1("***");

				th1.start();
				th2.start();
				th3.start();

				Thread.sleep(2000);
				th1.suspend();
				Thread.sleep(2000);
				th2.suspend();

				Thread.sleep(3000);
				th1.stop();
				th2.stop();

				Thread.sleep(2000);
				th3.stop();
			}
		}
		class Ex09_1 implements Runnable {
			private volatile boolean stopped; // 정지 X
			private volatile boolean suspended = false; //일시정지 X

			private Thread th;

			public Ex09_1(String name) {
				th = new Thread(this, name);
			}

			@Override
			public void run() {
				while(!stopped) {
					if(!suspended) {
						System.out.println(th.getName());
						try {
							Thread.sleep(1000);
						} catch ( InterruptedException e) {}
					}
				}
			}
			public void start() {
				th.start();
			}
			public void suspend() {
				suspended = true;
			}
			public void resume() {
				suspended = false;
			}
			public void stop() {
				stopped = true;
			}

		}
