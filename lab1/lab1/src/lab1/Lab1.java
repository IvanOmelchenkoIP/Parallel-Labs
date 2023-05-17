// ./lab1/src/lab1/Lab1.java

package lab1;

import java.io.IOException;

import lab1.fs.FileSystem;
import lab1.threading.F1;
import lab1.threading.F2;

public class Lab1 {

	public static void main(String[] args) {
		final String OUTPUT_PATH = "../output/output1.txt";

		FileSystem fs = new FileSystem();
		long start = System.currentTimeMillis();
		Thread t1 = new Thread(new F1());
		Thread t2 = new Thread(new F2());
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException ex) {
			System.out.println("Роботу дного з потоків перервано некоректно - " + ex);
		}
		long ms = System.currentTimeMillis() - start;
		String msMessage = "Час виконання: " + ms + " мс";
		try {
			fs.write(OUTPUT_PATH, msMessage + "\n\n");
		} catch (IOException ex) {
			System.out.println("Помилка при записі часу виконання - " + ex);
		}
		System.out.println(msMessage);
	}
}
