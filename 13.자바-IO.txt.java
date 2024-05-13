입출력(I/O)
java.io 패키지 

	1. 입출력이란?
		- Input/Output   입력 / 출력 
		- 컴퓨터 내부 또는 외부와 프로그램간의 데이터를 주고받는 것
		
	2. 스트림(stream)
		- 데이터가 이동하는 통로 
		- 입력 통로(입력 스트림)
		- 출력 통로(출력 스트림)


바이트기반 스트림 : 데이터 크기가 바이트 단위 / 1바이트씩 읽어오는 스트림

	1. 입력 스트림 - InputStream : 추상 클래스 
		int read()
	
		기반 스트림 : 직접 데이터에 접근해서 읽어오는 스트림
		 - FileInputStream
		 - ByteArrayInputStream 
			
			참고) Unsigned : 양의정수
			      Unsigned Byte 0 ~ 255
				  입력 스트림의 끝에 도달한 경우 반환값이 -1 / 바이트 범위에서 부족 -> 더 큰 자료형
				  int read()  : 자바는 일단 int
			
		보조 스트림 : 다른 스트림에 추가적인 기능 부여하는 스트림
		
		   생성자 매개변수 : InputStream  ( 보조스트림은 생성자매개변수 InputStream 를 갖는다 )
		    - FilterInputStream : 보조스트림의 체계를 정리하기 위한 클래스
			   : BufferedInputStream : 버퍼 기증 추가
			   : DataInputStream : 기본자료형으로 데이터를 읽을 수 있는 기능 추가
		    - ObjectInputStream : 객체형태로 변환하여 읽어오는 기능 추가
			- InputStreamReader : 바이트단위 스트림 -> 문자단위 스트림으로 변환 기능
		  
	2. 출력 스트림 - OutputStream : 추상 클래스 
		void write(int ..)
		
		기반 스트림 : 직접 데이터에 접근해서 출력하는 스트림
		
		 - FileOutputStream : 파일에 쓰는 스트림
		 - ByteArrayOutputStream : 메모리에 쓰는 스트림
		
		보조 스트림 : 다른 스트림에 추가적인 기능 부여하는 스트림 
					 생성자 매개변수가 OutputStream

		   - FilterOutputStream
			  : BufferedOutputStream : 출력 스트림 + 버퍼기능
			  : DataOutputStream : 기본자료형으로 쓰기기능 제공
		 
		   - ObjectOutputStream : 객체 형태로 데이터를 출력하는 기능 추가
		   - OutputStreamWriter : 바이트단위 스트림 -> 문자단위 스트림으로 변환 기능
		 
		 
			

		//---FileInputStream 예제-------------------------------------
		package exam02;
		import java.io.FileInputStream;
		import java.io.IOException;

		public class Ex02 {
			public static void main(String[] args) {
				try(FileInputStream fis = new FileInputStream("test1.txt")) {
					int ch = 0;

					while ((ch = fis.read()) != -1) {
						System.out.println((char)ch);
					}
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		-------------------------------------
		package exam02;
		import java.io.FileInputStream;
		import java.io.IOException;

		public class Ex03 {
			public static void main(String[] args) {
				try(FileInputStream fis = new FileInputStream("test1.txt")) {
					while(fis.available() > 0) {
						System.out.println((char)fis.read());
					}
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		-------------------------------------
		package exam02;
		import java.io.FileInputStream;
		import java.io.IOException;

		public class Ex04 {
			public static void main(String[] args) {
				// test1.txt 내용 : ABCDEFGHIJKLMNOP
				try(FileInputStream fis = new FileInputStream("test1.txt")) {
					byte[] buffer = new byte[5];
					while(fis.available() > 0) {
						int ch = fis.read(buffer);
						
						for(int i = 0; i < buffer.length; i++) {
							System.out.print((char)buffer[i]);
						}
						System.out.println();
					}
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		>>
		ABCDE
		FGHIJ
		KLMNO
		PLMNO   // LMNO 반복
		-------------------------------------
		package exam02;
		import java.io.FileInputStream;
		import java.io.IOException;

		public class Ex04 {
			public static void main(String[] args) {
				try(FileInputStream fis = new FileInputStream("test1.txt")) {
					byte[] buffer = new byte[5];
					while(fis.available() > 0) {
						int ch = fis.read(buffer);      // read 반환값 : 읽은 바이트 수
						for(int i = 0; i < ch; i++) {   // 읽은 바이트 수 만큼만 돌기 
							System.out.print((char)buffer[i]);
						}
						System.out.println();
					}
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		>>
		ABCDE
		FGHIJ
		KLMNO
		P

		
	
문자기반 스트림 : 데이터 크기가 문자 단위(유니코드 - 2, 3 바이트)

	1. 입력 스트림 - Reader : 추상 클래스 
		
		기반 스트림 : 데이터에 직접 접근하는 스트림
		
			FileReader
			CharArrayReader 
			StringReader
			
		보조 스트림 : 입력 스트림 + 추가기능 - 생성자 매개변수 Reader
			
			FilterReader
			 - BufferedReader : 버퍼기능
			InputStreamReader : 바이트 단위 스트림 -> 문자단위 스트림으로 변환 가능
			  : Reader 못쓰는 경우에 사용
			  : 생성자매개변수 String charSetName, Charset cs : 변환하려고 하는 문자표( 유니코드..)
				2바이트 코드 : ISO8859_1 /EUC-KR, CPC949
				3바이트 유니코드 : UTF8
			  
			  
			
	2. 출력 스트림 - Writer 
	  
			기반스트림 : 데이터에 직접 접근하는 스트림
			  FileWriter
			  CharArrayWriter
			  StringWriter
			보조 스트림 : 출력 스트림 + 추가기능 - 생성자 매개변수 Writer
			  FilterWriter 
			    - BufferdWriter : 버퍼기능
			  OutputStreamWriter : 바이트단위 스트림 - 

		
		-------------------------------------
		package exam01;
		import java.io.FileOutputStream;
		import java.io.IOException;

		public class Ex01 {
			public static void main(String[] args) {
			   try( FileOutputStream fos = new FileOutputStream("test1.txt", true)) {
				   for(char ch = 'A'; ch <= 'Z'; ch ++) {
					   fos.write(ch);
				   }
			   }catch (IOException e) {
				   e.printStackTrace();
			   }
			}
		}
		>>
		깨진 글자 출력
		-------------------------------------
		package exam01;
		import java.io.FileReader;
		import java.io.IOException;

		public class Ex03 {
			public static void main(String[] args) {
				try(FileReader fr = new FileReader("test2.txt")) { // 문자단위 스트림으로 한글 텍스트 읽기
					int ch = 0;
					while ((ch = fr.read()) != -1){
						System.out.print((char)ch);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		>>
		가나다라마바사아자
		-------------------------------------

		package exam01;
		import java.io.IOException;
		import java.io.InputStream;

		public class Ex04 {
			public static void main(String[] args) throws IOException {
				InputStream in = System.in;  //바이트단위 스트림
				char ch = (char)in.read();
				System.out.println(ch);
			}
		}
		>>
		가
		깨진문자
		-------------------------------------

		package exam01;
		import java.io.IOException;
		import java.io.InputStream;
		import java.io.InputStreamReader;

		public class Ex04 {
			public static void main(String[] args) throws IOException {
				InputStream in = System.in;  //바이트단위 스트림
				InputStreamReader isr = new InputStreamReader(in);
				char ch = (char)isr.read();
				System.out.println(ch);
			}
		}
		>> 
		가
		가
		-------------------------------------
		package exam01;
		import java.io.FileInputStream;
		import java.io.IOException;
		import java.io.InputStreamReader;

		public class Ex02 {
			public static void main(String[] args) {
				try (FileInputStream fis = new FileInputStream("test2.txt")) {
					InputStreamReader isr = new InputStreamReader(fis);
					{
						int ch = 0;
						while ((ch = isr.read()) != -1) {
							System.out.print((char) ch);
						}
					}
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		>>
		가나다라마바사아자
		
		-------------------------------------------------------
		package exam01;
		import java.io.FileInputStream;
		import java.io.FileOutputStream;
		import java.io.IOException;

		public class Ex07 {
			public static void main(String[] args) {

				long stime = System.currentTimeMillis(); //작업시작시간

				try(FileInputStream fis = new FileInputStream("specs.zip");
					FileOutputStream fos = new FileOutputStream("specs_copied.zip")) {
					while(fis.available() > 0) {
						fos.write(fis.read()); // 1바이트씩 읽고 쓰기
					}
				}catch(IOException e) {
					e.printStackTrace();
				}

				long etime = System.currentTimeMillis();
				System.out.printf("걸린시간: %d%n", etime - stime);
			}
		}
		>>
		걸린시간: 157225
		-------------------------------------------------------
		package exam01;
		import java.io.*;

		public class Ex08 {
			public static void main(String[] args) {
				long stime = System.currentTimeMillis(); //작업시작시간

				try(FileInputStream fis = new FileInputStream("specs.zip");
					BufferedInputStream bis = new BufferedInputStream(fis);
					FileOutputStream fos = new FileOutputStream("specs_copied2.zip");
					BufferedOutputStream bos = new BufferedOutputStream(fos)) {
						
					while(bis.available() > 0) {
						bos.write(bis.read());   // 8kb 버퍼가 다 차면 비우기-> 출력
						// 마지막 구간 8kb 버퍼는 다 차지 않을 수도 있음
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
				long etime = System.currentTimeMillis();
				System.out.printf("걸린시간: %d%n", etime - stime);

			}
		}
		>>
		걸린시간: 50356

		
	참고)
		데코레이터 패턴
	
		Class BufferedInputStream extends InputStream {
			
			private InputStream in;
			
			public BufferedInputStream(InputStream in) {
				this.in = in;
			}
			
			// read 메서드의 기능은 추가적인 기능과 함께 다른 스트림의 기능을 대신 수행
			public read() {
				
				//버퍼 기능에 대한 코드 .. 추가기능
				int byte = in.read();
				//버퍼 기능에 대한 코드 .. 추가기능
				return byte;
			}
		}
		------------------------------------------------
		package exam02;
		public interface Calculator {
			long factorial(long num);
		}

		package exam02;
		public class ImplCalculator implements Calculator{

			@Override
			public long factorial(long num) {
				long total = 1L;
				for(long i = 1; i <= num; i++) {
					total *= i;
				}
				return total;
			}
		}
		package exam02;
		public class RecCalculator implements Calculator {
			@Override
			public long factorial(long num) {
				if(num < 1L) {
					return 1L;
				}
				return num * factorial(num - 1);
			}
		}
		package exam02;     
		public class Ex01 {
			public static void main(String[] args) {   // 이건 좀....> 데코레이터 패턴으로 바꾸면 
				ImplCalculator cal1 = new ImplCalculator();
				long stime = System.nanoTime();
				long result1 = cal1.factorial(1000L);   //핵심기능
				long etime = System.nanoTime();
				System.out.printf("cal1:%d%n", result1);
				System.out.printf("걸린시간: %d%n",etime - stime);

				RecCalculator cal2 = new RecCalculator();
				stime = System.nanoTime();
				long result2 = cal2.factorial(1000L);  //핵심기능
				etime = System.nanoTime();
				System.out.printf("cal2:%d%n", result2);
				System.out.printf("걸린시간: %d%n",etime - stime);
			}
		}
		package exam02;
		public class DecorateCalculator implements Calculator{

			private Calculator calculator;

			public DecorateCalculator(Calculator calculator) {
				this.calculator = calculator;
			}

			public long factorial(long num) {
				long stime = System.nanoTime();
				try {
					long result = calculator.factorial(num);
					return result;
				} finally {
					long etime = System.nanoTime();
					System.out.printf("걸린시간:%d%n", etime - stime);
				}
			}
		}
		package exam02;
		public class Ex02 {
			public static void main(String[] args) {
				Calculator cal1 = new DecorateCalculator(new ImplCalculator());
				long result1 = cal1.factorial(1000L);
				System.out.println(result1);

				Calculator cal2 = new DecorateCalculator(new RecCalculator());
				long result2 = cal2.factorial(1000L);
				System.out.println(result2);

			}
		}

		

표준입출력 : 콘솔에 입력, 출력
	1. System.in : InputStream 
	2. System.out : PrintStream
	3. System.err : PrintStream 

File 


직렬화(Serialization)
	- 객체에 저장된 데이터를 스트림에 쓰기(write)위해 연속적인(serial) 데이터로 변환하는 것을 말한다.
	1. ObjectInputStream
	2. ObjectOutputStream
