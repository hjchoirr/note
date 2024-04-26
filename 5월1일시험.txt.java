5/1 7,8교시 시험

서술형 5문항, 작업장 평가(실습형) 5문항

책 연습문제 197p , 278p 2문제

toString 재정의 문제 - 진돗개 멍멍이 책에 있는 문제
singletone 패턴 문제

final 관련 문제

서술형, 단답형, 코드 




1. 클래스내부에서 자기 자신을 가리키는 예약어
2. 클래스에 여러 생성자가 오버로드되어 있을 경우 하나의 생성자에서 다른 생성자를 호출할때 (_)를 사용합니다
3. 클래스 내부에 선언하는 static 변수는 생성되는 인스턴스마다 만들어지는 것이 아닌 여러 인스턴스가 공유하는 변수입니다
따라서 클래스에 기반한 유일한 변수라는 의미로 ()라고도 합니다
4.지역변수는 함수나 매서드 내부에서만 사용할 수 있고 ( ) 메모리에 생성됩니다.
멤버변수 중 static 예약어를 사용하는 static () 메모리에 생성됩니다
5. 아침 출근길에 김씨는 4000원을 내고 별
다방에서아메리카노 사먹었고
이씨는 콩다방에서 4500원 라떼를 사 마셨습니다. 06-2 객체간의 협력을 참고하여 이 과정을 객체 지향으로 프로그래밍 해보세요
6. 카드회사에서 카드를 발급받을 때마다 새로운 카드번호를 부여합니다. 06-3 학번 생성하기 예제를 참조하여 카드가 생성될때마다 카드번호 자동 증가하도록 카드 클래스를 만들고 생성해 보세요
7. 6번에 구현한 카드회사 클ㄹ스 CardCompany를 싱글톤 패턴을 사용하여 구현해 보세요

1.자바에서는 어떤 클래스의 기능을 확장하기 위하여 새로운 클래스를 만들기 위해 상속을 합니다. 이때 사용하는 예약어는 ()입니다
2. 하위클래스가 상위 클래스의 생성자를 호출하거나 클래스의 주소, 즉 참조값을 나타내응 예약어는 (_) 입니다.
3. 클래스를 상속받은 상태에서 상위 클래스에 이미 정의되어 있는 메서드를 하위 클래스에서 사용하기에 적합하지 않은 경우에 해담 매서드를 재정의 할 수 있습니다.
이것을 () 이라고 합니다.
4.다음 코드가 오류를 발생하는 원인



package chapter6.q5;

public class BeanCoffee {

	int money;
	
	public String brewing(int money) {
	
		this.money += money;
		if(money == Menu.BEANAMERICANO) {
			return "콩 다방 아메리카노를 구입했습니다";
		}
		else if(money == Menu.BEANLATTE) {
			return "콩 다방 라떼를 구입했습니다";
		}
		else {
			return null;
		}
	}
}

package chapter6.q5;

public class CoffeeTest {

	public static void main(String[] args) {

		Person kim = new Person("Kim", 10000);
		StarCoffee starCoffee = new StarCoffee();
		BeanCoffee beanCoffee = new BeanCoffee();
		
		kim.buyStarCoffee(starCoffee, 4000);
		kim.buyBeanCoffee(beanCoffee, 4500);

	}
}

package chapter6.q5;

public class Menu {

	public static final int STARAMERICANO = 4000;
	public static final int STARLATTE = 4300;
	
	public static final int BEANAMERICANO = 4100;
	public static final int BEANLATTE = 4500;
}


package chapter6.q5;

public class Person {

	String name;
	int money;
	
	Person(String name, int money ){
		this.name = name;
		this.money = money;
	}
	
	public void buyStarCoffee(StarCoffee sCoffee, int money) {
		String message = sCoffee.brewing(4000);
		if(message != null) {
			this.money -= money;
			System.out.println(name + " ����" + money +"���� "  + message);
		}
	}
	
	public void buyBeanCoffee(BeanCoffee bCoffee, int money) {
		String message = bCoffee.brewing(4500);
		if(message != null) {
			this.money -= money;
			System.out.println(name + " ����" + money +"����"  + message);
		}
	}
}

package chapter6.q5;

public class StarCoffee {

	int money;
	
	public String brewing(int money) {
	
		this.money += money;
		if(money == Menu.STARAMERICANO) {
			return "별다방 아메리카노 구입";
		}
		else if(money == Menu.STARLATTE) {
			return "별다방 라떼 구입";
		}
		else {
			return null;
		}
	}
}