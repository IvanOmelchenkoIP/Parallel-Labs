// ./lab3/src/lab3/Lab2.java

package lab3;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lab3.data.matrix.MatrixManager;
import lab3.data.vector.VectorManager;
import lab3.fs.FileSystem;
import lab3.threading.F1;
import lab3.threading.F2;

public class Lab3 {

	public static void main(String[] args) {
		final int MIN_VAL = 0;
		final int MAX_VAL = 100;
		final int MIN_PRECISION = 3;
		final int MAX_PRECISION = 15;
		final int N = 100;
		final String INPUT_PATH = "../input/input.txt";
		final String OUTPUT_PATH = "../output/output3.txt";
		
		VectorManager vm = new VectorManager(MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION, INPUT_PATH, OUTPUT_PATH);
		MatrixManager mm = new MatrixManager(MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION, INPUT_PATH, OUTPUT_PATH);
		
		final int ALLOWED_THREADS = 1;
		final int THREAD_AMOUNT = 2;
		
		Semaphore semaphore = new Semaphore(ALLOWED_THREADS);
		CountDownLatch countDownLatch = new CountDownLatch(THREAD_AMOUNT);
		
		ExecutorService execService = Executors.newFixedThreadPool(THREAD_AMOUNT);
		
		long start = System.currentTimeMillis();
		
		execService.execute(new F1(N, mm, semaphore, countDownLatch));
		execService.execute(new F2(N, mm, vm, semaphore, countDownLatch));
		try {
			countDownLatch.await();
		} catch (InterruptedException ex) {
			System.out.println("Роботу дного з потоків перервано некоректно - " + ex);
		}
		
		long ms = System.currentTimeMillis() - start;
		String msMessage = "Час виконання: " + ms + " мс";
		FileSystem fs = new FileSystem();
		try {
			fs.write(OUTPUT_PATH, msMessage + "\n\n");
		} catch (IOException ex) {
			System.out.println("Помилка при записі часу виконання - " + ex);
		}
		System.out.println(msMessage);
	}
}
