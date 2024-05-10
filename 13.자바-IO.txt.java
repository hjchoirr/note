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
	
		기반 스트림 : 직접 데이터에 접근해서 읽어오는 스트림
		 - FileInputStream
		 - ByteArrayInputStream 
			
			
			참고) Unsigned : 양의정수
			      Unsigned Byte 0 ~ 255
				  입력 스트림의 끝에 도달한 경우 반환값이 -1 / 바이트 범위에서 부족 -> 더 큰 자료형
				  int read()  : 자바는 일단 int
			
		보조 스트림 : 다른 스트림에 추가적인 기능 부여하는 스트림
		
		 생성자 매개변수 : InputStream 
		 - FilterInputStream : 보조스트림의 체계를 정리하기 위한 클래스
			BufferedInputStream
			DataInputStream
		 - ObjectInputStream
		  


	2. 출력 스트림 - OutputStream : 추상 클래스 

		기반 스트림 : 직접 데이터에 접근해서 출력하는 스트림
		
		 - FileOutputStream : 파일에 쓰는 스트림
		 - ByteArrayOutputStream : 메모리에 쓰는 스트림
		
		보조 스트림 : 다른 스트림에 추가적인 기능 부여하는 스트림

		 - FilterOutputStream
			BufferedOutputStream
			DataOutputStream
		 
		 - ObjectOutputStream
		 
		 
			
		
		참고)
			데코레이터 패턴
		
		
	
문자기반 스트림 : 데이터 크기가 문자 단위(유니코드 - 2, 3 바이트)

	1. 입력 스트림 - Reader : 추상 클래스 

		기반 스트림 
		보조 스트림 
			
			
	2. 출력 스트림 - Writer 


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
                int ch = fis.read(buffer);  // ch : 읽은 바이트 수
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


표준입출력 : 콘솔에 입력, 출력
	1. System.in : InputStream 
	2. System.out : PrintStream
	3. System.err : PrintStream 

File 


직렬화(Serialization)
	- 객체에 저장된 데이터를 스트림에 쓰기(write)위해 연속적인(serial) 데이터로 변환하는 것을 말한다.
	1. ObjectInputStream
	2. ObjectOutputStream
