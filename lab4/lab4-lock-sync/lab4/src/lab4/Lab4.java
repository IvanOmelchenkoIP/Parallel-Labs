// ./lab4/src/lab4/Lab4.java

package lab4;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lab4.data.matrix.MatrixManager;
import lab4.data.vector.VectorManager;
import lab4.fs.FileSystem;
import lab4.threading.F1;
import lab4.threading.F2;
import lab4.threading.block.CountingThreadBlock;

public class Lab4 {

	public static void main(String[] args) {
		final int MIN_VAL = 0;
		final int MAX_VAL = 100;
		final int MIN_PRECISION = 3;
		final int MAX_PRECISION = 15;
		final int N = 100;
		final String INPUT_PATH = "../input/input.txt";
		final String OUTPUT_PATH = "../output/output4-2.txt";
		
		VectorManager vm = new VectorManager(MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION, INPUT_PATH, OUTPUT_PATH);
		MatrixManager mm = new MatrixManager(MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION, INPUT_PATH, OUTPUT_PATH);
		
		final int THREAD_AMOUNT = 2;
		
		Lock resLock = new ReentrantLock();
		CountingThreadBlock block = new CountingThreadBlock(THREAD_AMOUNT);
				
		ExecutorService execService = Executors.newFixedThreadPool(THREAD_AMOUNT);
		
		long start = System.currentTimeMillis();
		
		Callable<Void> f1 = new F1(N, mm, resLock, block);
		Callable<Void> f2 = new F2(N, mm, vm, resLock, block);
		execService.submit(f1);
		execService.submit(f2);
		
		try {
			block.waitForAll();
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити роботу програми - " + ex);
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
