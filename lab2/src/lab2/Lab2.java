// ./lab2/src/lab2/Lab2.java

package lab2;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import lab2.fs.FileSystem;
import lab2.threading.F1;
import lab2.threading.F2;

public class Lab2 {

	public static void main(String[] args) {
		final int N = 100;
		final String INPUT_PATH = "../input/input.txt";
		final String OUTPUT_PATH = "../output/output2.txt";
		
		final int ALLOWED_THREADS = 1;
		final int THREAD_AMOUNT = 2;
		Semaphore semaphore = new Semaphore(ALLOWED_THREADS);
		CountDownLatch countDownLatch = new CountDownLatch(THREAD_AMOUNT);

		FileSystem fs = new FileSystem();
		long start = System.currentTimeMillis();
		Thread t1 = new Thread(new F1(N, INPUT_PATH, OUTPUT_PATH, semaphore, countDownLatch));
		Thread t2 = new Thread(new F2(N, INPUT_PATH, OUTPUT_PATH, semaphore, countDownLatch));
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
