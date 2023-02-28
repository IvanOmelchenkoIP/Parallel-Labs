package lab1;

import lab1.threading.F1;
import lab1.threading.F2;

public class Lab1 {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Thread t1 = new Thread(new F1());
		Thread t2 = new Thread(new F2());
		t1.start();
		t2.start();
		
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long ms = System.currentTimeMillis() - start;
		System.out.println("Час виконання: " + ms + " мс");
	}

}
