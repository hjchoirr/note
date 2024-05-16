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
			
			   : BufferedInputStream : 버퍼 기능 추가
			   : DataInputStream : 기본자료형으로 데이터를 읽을 수 있는 기능 추가 
					- 한가지 자료형으로 사용하는게 좋다
					- 끝까지 다 읽은 후에 또 읽으면 EOFException 발생
					
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
					- 한가지 자료형으로 사용하는게 좋다
					
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
		데코레이터 패턴 - 보조스트림은 데코레이터 패턴 사용됨
		AOP(Aspect Oriented Programming) : 관점지향 프로그래밍
	
		Class BufferedInputStream extends InputStream {
			
			private InputStream in;
			
			public BufferedInputStream(InputStream in) {
				this.in = in;
			}
			
			// read 메서드의 기능은 추가적인 기능과 함께 다른 스트림의 기능을 대신 수행
			public read() {
				
				//버퍼 기능에 대한 코드 .. 추가기능
				
				int byte = in.read();         // 핵심기능 
				
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

		
	DataInputStream : 기본자료형으로 데이터를 읽을 수 있는 기능 추가 
					- 한가지 자료형으로 사용하는게 좋다
					- 끝까지 다 읽은 후에 또 읽으면 EOFException 발생
	DataOutputStream : 기본자료형으로 쓰기기능 제공
					- 한가지 자료형으로 사용하는게 좋다

	
		package exam01;
		import java.io.DataOutputStream;
		import java.io.FileOutputStream;
		import java.io.IOException;

		public class Ex01 {
			public static void main(String[] args) {
				try(FileOutputStream fos = new FileOutputStream("test1.txt");
					DataOutputStream dos = new DataOutputStream(fos)) {
					dos.writeByte(100);
					dos.writeChar('A');
					dos.writeUTF("안녕하세요.");

				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		package exam01;
		import java.io.DataInputStream;
		import java.io.FileInputStream;
		import java.io.IOException;

		public class Ex02 {
			public static void main(String[] args) {
				try(FileInputStream fis = new FileInputStream("test1.txt");
					DataInputStream dis = new DataInputStream(fis)) {
					byte num = dis.readByte();
					char ch = dis.readChar();
					String str = dis.readUTF();

					System.out.printf("num=%d ch=%c str=%s%n", num, ch, str);
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		-------------------------------------------------------------------------
		package exam01;
		import javax.xml.crypto.Data;
		import java.io.DataOutputStream;
		import java.io.FileOutputStream;
		import java.io.IOException;

		public class Ex03 {
			public static void main(String[] args) {
				int[] scores = { 90,88,65, 100, 78,98 };
				try(FileOutputStream fos = new FileOutputStream("score.txt");
					DataOutputStream dos = new DataOutputStream(fos)) {
					for(int score : scores) {
						dos.writeInt(score);
					}

				}catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
			
		package exam01;
		import java.io.DataInputStream;
		import java.io.EOFException;
		import java.io.FileInputStream;
		import java.io.IOException;

		public class Ex04 {
			public static void main(String[] args) {
				try(FileInputStream fis = new FileInputStream("score.txt");
					DataInputStream dis = new DataInputStream(fis)) {
					int tot = 0;
					int cnt = 0;
					try {
						while (true) {
							int score = dis.readInt();
							tot += score;
							cnt ++;
						}
					} catch(EOFException e) { //파일 다 읽은 상태
						System.out.printf("합계=%d 평균=%.1f%n", tot, tot / (double)cnt );
					}
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
			

표준입출력 : 콘솔에 입력, 출력
	1. System.in : InputStream 
	
		터미널입력, 바이트단위 스트림, 문자단위 스트림으로 변환(InputStreamReader)
		
	2. System.out : PrintStream 
	
		문자단위 출력 스트림, print(..) , println(..), printf(..) + 버퍼(8kb)
	
	3. System.err : PrintStream 
	
		표준에러 스트림, 글자색이 빨간색
	
			
		참고)
		printWrite : 문자기반 스트림, 기반 스트림, 버퍼  + 버퍼(8kb)
		- print(), printf(), println() 편의 메서드 포함	
	  
			package exam01;
			public class Ex01 {
				public static void main(String[] args) {
					System.err.println("Error!!!");
				}
			}

			package exam01;
			import java.io.IOException;
			import java.io.PrintStream;
			
			public class Ex02 {
				public static void main(String[] args) throws IOException {
					PrintStream out = new PrintStream("20240516.log");
					System.setErr(out);

					String str = null;
					str.toUpperCase();
				}
			}
File 

	- 파일, 디렉토리를 파일 객체로 생성하여 관리
	- 파일, 디렉토리에 유용한 기능 ...
	
	java.io.file
	 - fields 중 separator, pathSeperator 
	 
		- 윈도우 : \
		- 유닉스, 맥 : /
				
		롼경변수 구분문자 
		 - 윈도우 ;
		 - 유닉스(맥) :
		 
			ackage exam01;
			import java.io.File;

			public class Ex03 {
				public static void main(String[] args) {
					System.out.println("File.seperator : " + File.separator);
					System.out.println("File.pathSeperator : " + File.pathSeparator);
				}
			}
			>>
			File.seperator : \
			File.pathSeperator : ;
 
		읽기 쓰기 실행 권한
			boolean canRead()
			boolean canWrite()
			boolean canExecute() 
			
			void setReadable(..)
			void setWritable(..)
			void setExecutable(..)
			
			void setReadOnly()
			
			getAbsolutePath(); 
			getCanonicalPath(); //정규화된 파일경로 ( 상대경로 -> 절대경로 )
			getName()
			getPath()
			
				-----------------------------------------------
				package exam01;
				import java.io.File;
				import java.io.IOException;

				public class Ex04 {
					public static void main(String[] args) throws IOException {
						//File file = new File("D:/test1.text");
						//file.createNewFile();
						File dir = new File("D:/fstudy");
						if(!dir.exists()) {
							dir.mkdir();
						}
						File file = new File(dir, "text1.txt");
						file.createNewFile();
					}
				}
				
				package exam01;
				import java.io.File;
				import java.io.IOException;

				public class Ex05 {
					public static void main(String[] args) throws IOException {
						File dir = new File("D:/fstudy/sub1/sub2/sub3");
						File file = new File(dir, "test1.txt");

						if(!dir.exists()) {
							dir.mkdirs(); //재귀적으로 폴더들 만들기
						}
						file.createNewFile();
						
						System.out.println("dir 디렉토리 ? " + dir.isDirectory());
						System.out.println("dir 파일 ? " + dir.isFile());
					}
				}
				>>
				dir 디렉토리 ? true
				dir 파일 ? false
				------------------------------------------------------------------------------
				package exam01;
				import java.io.File;
				import java.io.IOException;

				public class Ex06 {
					public static void main(String[] args) throws IOException {
						File  tmpfile = File.createTempFile("tmp", ".log", new File("D:/fstudy"));
					}
				}

				package exam01;
				import java.io.File;
				import java.io.IOException;

				public class Ex06 {
					public static void main(String[] args) throws IOException, InterruptedException {
						File  tmpfile = File.createTempFile("tmp", ".log", new File("D:/fstudy"));
						//tmpfile.delete();
						tmpfile.deleteOnExit();  // 프로그램 끝나면 자동 삭제됨
						Thread.sleep(5000);
					}
				}

				package exam01;
				import java.io.File;
				import java.io.IOException;
				------------------------------------------------------------------------------
				public class Ex07 {
					public static void main(String[] args) throws IOException {
						File file = new File("D:/fstudy/sub1/sub2/sub3/test1.txt");
						String absPath = file.getAbsolutePath();
						System.out.println(absPath);

						File file2 = new File("D:/fstudy/sub1/sub2/../sub2/sub3/test1.txt");
						String absPath2 = file2.getAbsolutePath();
						System.out.println(absPath2);

						String canonPath = file2.getCanonicalPath();
						System.out.println(canonPath);
						
						System.out.println("getName() : " + file.getName());
						System.out.println("getPath() : " + file.getPath());						
					}
				}
				>>
				D:\fstudy\sub1\sub2\sub3\test1.txt
				D:\fstudy\sub1\sub2\..\sub2\sub3\test1.txt
				D:\fstudy\sub1\sub2\sub3\test1.txt
				getName() : test1.txt
				getPath() : D:\fstudy\sub1\sub2\sub3\test1.txt
				
직렬화(Serialization)

	- 객체에 저장된 데이터를 스트림에 쓰기(write)위해 연속적인(serial) 데이터로 변환하는 것을 말한다.
	- 직렬화 -> 데이터 노출 -> 위험한 작업 -> 의사표현 (Serializable 인터페이스 추가 -> 진행하겠음을 표현)
	   Serializable : 마커인터페이스 
	   
	- Serialization 인터페이스 구현
	 -> 변환값 : 다시 객체로 복구할때 필요한 항목만 직렬화
	     -> 객체마다 변경할 수 있는 
	
	1. ObjectInputStream
		: 객체 형태로 읽어오는 것
		
	2. ObjectOutputStream
		: 객체 형태로 저장
		
		-------------------------------------------------------------
		package exam01;
		public class Book  {
			private int isbn;
			private String tile;
			private String author;

			public Book(int isbn, String tile, String author) {
				this.isbn = isbn;
				this.tile = tile;
				this.author = author;
			}

			@Override
			public String toString() {
				return "Book{" +
						"isbn=" + isbn +
						", tile='" + tile + '\'' +
						", author='" + author + '\'' +
						'}';
			}
		}

		package exam01;
		import java.io.FileOutputStream;
		import java.io.IOException;
		import java.io.ObjectOutputStream;

		public class Ex08 {
			public static void main(String[] args) {
				try(FileOutputStream fos = new FileOutputStream("Book.txt");
					ObjectOutputStream oos = new ObjectOutputStream(fos)) {
					Book book1 = new Book(1000, "책1","저자1");
					oos.writeObject(book1);
					
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		>> 에러남
		java.io.NotSerializableException: exam01.Book
			at java.base/java.io.ObjectOutputStream.writeObject0(ObjectOutputStream.java:1197)
			at java.base/java.io.ObjectOutputStream.writeObject(ObjectOutputStream.java:354)
			at exam01.Ex08.main(Ex08.java:13)


		-----Serializable 인터페이스 상속받으면 에러 안남  -> 직렬화--------------------
		package exam01;

		import java.io.Serializable;

		public class Book implements Serializable {   // Serializable : 마커 인터페이스
			private int isbn;
			private String tile;
			private String author;

			public Book(int isbn, String tile, String author) {
				this.isbn = isbn;
				this.tile = tile;
				this.author = author;
			}

			@Override
			public String toString() {
				return "Book{" +
						"isbn=" + isbn +
						", tile='" + tile + '\'' +
						", author='" + author + '\'' +
						'}';
			}
		}
		-----------------------------------------------------------------------
		package exam01;
		import java.io.FileInputStream;
		import java.io.IOException;
		import java.io.ObjectInputStream;

		public class Ex09 {
			public static void main(String[] args) {
				try(FileInputStream fis = new FileInputStream("book.txt");
					ObjectInputStream ois = new ObjectInputStream(fis)) {

					Book book1 = (Book)ois.readObject();

					System.out.println(book1.toString());
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		}
		>>
		Book{isbn=1000, tile='책1', author='저자1'}

		---------------------------------------------------------
		package exam01;
		import java.io.FileOutputStream;
		import java.io.IOException;
		import java.io.ObjectOutputStream;

		public class Ex08 {
			public static void main(String[] args) {
				try(FileOutputStream fos = new FileOutputStream("Book.txt");
					ObjectOutputStream oos = new ObjectOutputStream(fos)) {
					Book book1 = new Book(1000, "책1","저자1");
					Book book2 = new Book(1001, "책2", "저자2");
					oos.writeObject(book1);
					oos.writeObject(book2);
					oos.writeObject("안녕하세요.");

				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}

		package exam01;
		import java.io.FileInputStream;
		import java.io.IOException;
		import java.io.ObjectInputStream;

		public class Ex09 {
			public static void main(String[] args) {
				try(FileInputStream fis = new FileInputStream("book.txt");
					ObjectInputStream ois = new ObjectInputStream(fis)) {

					Book book1 = (Book)ois.readObject();
					System.out.println(book1.toString());

					Book book2 = (Book)ois.readObject();
					System.out.println(book2.toString());

					String str = (String)ois.readObject();
					System.out.println(str);

				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		}
		Book{isbn=1000, title='책1', author='저자1'}
		Book{isbn=1001, title='책2', author='저자2'}
		안녕하세요.
		----------------------------------
		package exam01;
		import java.io.FileOutputStream;
		import java.io.IOException;
		import java.io.ObjectOutputStream;
		import java.util.Arrays;
		import java.util.HashMap;
		import java.util.List;

		public class Ex10 {
			public static void main(String[] args) {
				try(FileOutputStream fos = new FileOutputStream("data.obj");
					ObjectOutputStream oos = new ObjectOutputStream(fos)) {
					HashMap<String, Object> data = new HashMap<>();
					Book book1 = new Book(1000,"책1", "저자1");
					Book book2 = new Book(1001, "책2", "저자2");

					List<Book> books = Arrays.asList( 
							new Book(1002, "저자2","저자3"),
							new Book(1003, "저자4","저자4"),
							new Book(1004, "저자5","저자5")
					);
					data.put("book1", book1);
					data.put("book2", book2);
					data.put("books", books);
					data.put("str", "안녕하세요.");
					
					oos.writeObject(data);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		package exam01;
		import java.io.FileInputStream;
		import java.io.IOException;
		import java.io.ObjectInputStream;
		import java.util.HashMap;
		import java.util.List;

		public class Ex11 {
			public static void main(String[] args) {
				try(FileInputStream fis = new FileInputStream("data.obj");
					ObjectInputStream ois = new ObjectInputStream(fis)) {
					HashMap<String, Object> data = (HashMap<String, Object>) ois.readObject();

					List<Book> books = (List<Book>) data.get("books");
					String str = (String)data.get("str");
					Book book1 = (Book)data.get("book1");
					Book book2 = (Book)data.get("book2");

					books.forEach(System.out::println);
					System.out.println(str);
					System.out.println(book1);
					System.out.println(book2);

				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		>>
		Book{isbn=1002, title='저자2', author='저자3'}
		Book{isbn=1003, title='저자4', author='저자4'}
		Book{isbn=1004, title='저자5', author='저자5'}
		안녕하세요.
		Book{isbn=1000, title='책1', author='저자1'}
		Book{isbn=1001, title='책2', author='저자2'}


참고)
Book class에서 transient 키워드 사용하면 직렬화에서 배제 됨
private transient String author;  // transient :직렬화 배제



쓰레드

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
		2) 실행중인 사용자 쓰레드가 하나도 없을 때 프로그램은 종료된다.


싱글쓰레드와 멀티쓰레드
	1. 하나의 쓰레드로 두 작업을 처리하는 경우 한 작업을 마친 후에 다른 작업을 시작한다.
	2. 두 개의 쓰레드로 작업하는 경우에는 짧은 시간동안 2개의 쓰레드가 번갈아 가면서 작업을 수행해서 동시에 두 작업이 처리되는 것과 같이 느끼게 한다.
	3. 하나의 쓰레드로 두개의 작업을 수행한 시간과 두개의 쓰레드로 두 개의 작업을 수행한 시간은 거의 같다.
	4. 오히려 두 개의 쓰레드로 작업한 시안이 싱글쓰레드로 작업한 시간보다 더 걸리게 되는데, 쓰레드간의 작업 전환(context switching)에 시간이 걸리기 때문이다.

쓰레드의 우선순위
	1. 쓰레드 우선순위 지정하기

쓰레드 그룹(thread group)

데몬 쓰레드(daemon thread)

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