// ./lab4/src/lab5/Lab5.java

package lab5;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lab5.data.matrix.MatrixManager;
import lab5.data.vector.VectorManager;
import lab5.fs.FileSystem;
import lab5.threading.F1;
import lab5.threading.F2;

public class Lab5 {

	public static void main(String[] args) {
		final int MIN_VAL = 0;
		final int MAX_VAL = 100;
		final int MIN_PRECISION = 3;
		final int MAX_PRECISION = 15;
		final int N = 100;
		final String INPUT_PATH = "../input/input.txt";
		final String OUTPUT_PATH = "../output/output5.txt";
		
		VectorManager vm = new VectorManager(MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION, INPUT_PATH, OUTPUT_PATH);
		MatrixManager mm = new MatrixManager(MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION, INPUT_PATH, OUTPUT_PATH);
		
		final int THREAD_AMOUNT = 2;
		
		Lock resLock = new ReentrantLock();

		ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_AMOUNT);
		
		long start = System.currentTimeMillis();
		
		ForkJoinTask<?> f1 = new F1(N, mm, resLock);
		ForkJoinTask<?> f2 = new F2(N, mm, vm, resLock);
		
		forkJoinPool.execute(f1);
		forkJoinPool.execute(f2);
		
		f1.join();
		f2.join();
		
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
