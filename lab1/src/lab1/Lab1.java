package lab1;

import lab1.threading.F1;
import lab1.threading.F2;

public class Lab1 {

	public static void main(String[] args) {
		new Thread(new F1()).start();
		new Thread(new F2()).start();
	}

}
