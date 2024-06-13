package com.game.main;



import com.game.view.MainView;



public class GameMain {

	public static void main(String[] args) {

		

		MainView mv = new MainView();

		mv.mainMenu();	

	}

}



[main view]

package com.game.view;

import java.util.Scanner;

import com.game.controller.GameController;



public class MainView {

	

	GameController ct = new GameController();//컨트롤러로 return해야하니까 객체선언함

	

	public void mainMenu() {

	Scanner sc=new Scanner(System.in);

	

	do {

	System.out.println("~~~~~~~~묵찌빠 게임을 시작합니다~~~~~~~~~~");

	System.out.println();

	System.out.println("1.게임시작  2.게임종료");

	System.out.print("입력 : ");

	int su = sc.nextInt();

	

		switch(su) {

		case 1 :gameStart();break;

		case 2 : System.out.println("게임을 종료합니다.");return;

		}

				

	}while(true);



}	

	//게임시작 출력화면 가위바위보 먼저시작

	public void gameStart() {

	Scanner sc=new Scanner(System.in);

	do {

	System.out.println("~~~~~~~~~가위 바위 보~~~~~~~~~~~");

	System.out.println("1.가위 2.바위 3.보");

	System.out.print("입력 : ");

	int num=sc.nextInt();

	

		switch(num){

		case 1 : System.out.println("가위");ct.pare(1);return;//비교하는 controller에 pare로감 

		case 2 : System.out.println("바위");ct.pare(2);return;

		case 3 : System.out.println("보");ct.pare(3);return;

		}		

	}while(true);

}	

	public void draw() {//비겼으니까 가위바위보 다시시작

	Scanner sc=new Scanner(System.in);

	

	do {

	System.out.println("~~~~~~~비겼습니다 다시 가위바위보~~~~~~~~");

	System.out.println("1.가위 2. 바위 3.보");

	System.out.print("입력 : ");

	int num=sc.nextInt();

	

		switch(num){

		case 1 : System.out.println("가위");ct.pare(1);return;

		case 2 : System.out.println("바위");ct.pare(2);return;

		case 3 : System.out.println("보");ct.pare(3);return;

		}		

			}while(true);

	}	

	

	//가위바위보 이겼으니까 내가 먼저 묵찌빠 시작

	public void win() {

	Scanner sc=new Scanner(System.in);

		do {

			System.out.println("================묵 찌 빠================");

			System.out.println("=============플 레 이 어 차 례=============");

			System.out.println("1.묵 2.찌 3.빠");

			System.out.print("입력 : ");

			int num=sc.nextInt();

			switch(num) {

				case 1:System.out.println("묵");ct.winpare(1);return;

				case 2:System.out.println("찌");ct.winpare(2);return;

				case 3:System.out.println("빠");ct.winpare(3);return;

			}

						

		}while(true);

	}	

	

		//가위바위보 졌으니까 컴퓨터가 먼저 묵찌빠 시작

		public void lose() {

			Scanner sc=new Scanner(System.in);

			do {

				System.out.println("================묵 찌 빠================");

				System.out.println("==============컴 퓨 터 차 례==============");

				System.out.println("1.묵 2.찌 3.빠");

				System.out.print("입력 : ");

				int num=sc.nextInt();

				switch(num) {

					case 1:System.out.println("묵");ct.losepare(1);return;

					case 2:System.out.println("찌");ct.losepare(2);return;

					case 3:System.out.println("빠");ct.losepare(3);return;

				}

				

			}while(true);

			

		}

	}



[controller]

package com.game.controller;

import com.game.view.MainView;





public class GameController {

	private int num;

	public GameController() {}

	

	//1~3 난수 만드는 기능 메소드

	public int random() {//랜덤 int num을 리턴하는 메소드

		int s = (int)(Math.random()*3)+1;

		return s;

	}

	

	//가위바위보 비교하는 기능

	public void pare(int su) {

		MainView v = new MainView();

		num=random();

		if(su==1) {//나 가위

			switch(num) {//컴퓨터가 랜덤으로 고른 수

			case 1 : System.out.println("나 : 가위 / 컴퓨터 : 가위  - 비겼습니다.");v.draw();return;

			case 2 : System.out.println("나 : 가위/ 컴퓨터 : 바위 - 졌습니다.");v.lose();return;

			case 3 : System.out.println("나 : 가위/ 컴퓨터 : 보 - 내가 이겼습니다.");	v.win();return;		

			}

		}	

		if(su==2) {//나 바위

			switch(num) {

			case 1 : System.out.println("나 : 바위 / 컴퓨터 : 바위- 비겼습니다.");v.draw();return;

			case 2 : System.out.println("나 : 바위/ 컴퓨터 : 가위 - 내가 이겼습니다.");v.win();return;

			case 3 : System.out.println("나 : 바위/ 컴퓨터 : 보 - 졌습니다.");v.lose();return;			

			}

		}	

		if(su==3) {//나 보

			switch(num) {

			case 1 : System.out.println("나 : 보  / 컴퓨터 : 보- 비겼습니다.");v.draw();return;

			case 2 : System.out.println("나 : 보 / 컴퓨터 : 가위 - 졌습니다.");v.lose();return;

			case 3 : System.out.println("나 : 보 / 컴퓨터 : 바위 - 내가 이겼습니다.");v.win();return;			

			}			

		}

	}

	

	//이겼을때 내가 선 묵찌빠

	public void winpare(int su) { 

	MainView v = new MainView();

	num=random();

	System.out.println();

			if(su==1) {//나 묵

				switch(num) {

					case 1 : System.out.println("나 : 묵 / 컴퓨터 : 묵 - 내가 이겼습니다.");return;//다시 mainMenu로 돌아감

					case 2 : System.out.println("나 : 묵 / 컴퓨터 : 찌");v.win();return;//내가다시 선

					case 3 : System.out.println("나 : 묵 / 컴퓨터 : 빠");v.lose();return;//컴퓨터가 다시 선

				}

			}

			if(su==2) {//나 찌

				switch(num) {

					case 1 : System.out.println("나 : 찌 / 컴퓨터 : 묵");v.lose();return;

					case 2 : System.out.println("나 : 찌 / 컴퓨터 : 찌 - 내가 이겼습니다.");return;

					case 3 : System.out.println("나 : 찌 / 컴퓨터 : 빠");v.win();return;

				}

			}

			if(su==3) {//플레이어 : 빠

				switch(num) {

					case 1 : System.out.println("나 : 빠 / 컴퓨터 : 묵");v.win();return;

					case 2 : System.out.println("나 : 빠 / 컴퓨터 : 찌");v.lose();return;

					case 3 : System.out.println("나 : 빠 / 컴퓨터 : 빠 - 내가 이겼습니다.");return;

				}

			}

		}

		

		//졌을때 컴퓨터 선 묵찌빠

		public void losepare(int su) {

		MainView v = new MainView();

		num=random();

		System.out.println();

			if(su==1) {//나 묵

				switch(num) {

					case 1 : System.out.println("나 : 묵 컴퓨터 : 묵 - 졌습니다.");return;

					case 2 : System.out.println("나 : 묵 컴퓨터 : 찌");v.win();return;

					case 3 : System.out.println("나 : 묵 컴퓨터 : 빠");v.lose();return;

				}

			}

			if(su==2) {//나 찌

				switch(num) {

					case 1 : System.out.println("나 : 찌 / 컴퓨터 : 묵");v.lose();return;

					case 2 : System.out.println("나 : 찌 / 컴퓨터 : 찌 - 졌습니다.");return;

					case 3 : System.out.println("나 : 찌 / 컴퓨터 : 빠");v.win();return;

				}

			}

			if(su==3) {//나 빠

				switch(num) {

					case 1 : System.out.println("나 : 빠 / 컴퓨터 : 묵");v.win();return;

					case 2 : System.out.println("나 : 빠 / 컴퓨터 : 찌");v.lose();return;

					case 3 : System.out.println("나 : 빠 / 컴퓨터 : 빠 - 졌습니다.");return;

				}

			}

		}

	}