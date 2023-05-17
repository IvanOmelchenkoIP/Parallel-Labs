// ./lab4/src/lab4/Lab4.java

package lab4;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lab4.data.matrix.MatrixManager;
import lab4.data.vector.VectorManager;
import lab4.fs.FileSystem;
import lab4.fs.MemFileSystem;
import lab4.threading.F1;
import lab4.threading.F2;

public class Lab4 {

	public static void main(String[] args) {
		final int MIN_VAL = 0;
		final int MAX_VAL = 100;
		final int MIN_PRECISION = 3;
		final int MAX_PRECISION = 15;
		final int N = 100;
		final String INPUT_PATH = "../input/input.txt";
		final String OUTPUT_PATH = "../output/output4-3.txt";
		
		FileSystem fs = new MemFileSystem();
		
		VectorManager vm = new VectorManager(MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION, fs);
		MatrixManager mm = new MatrixManager(MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION, fs);
		
		final int THREAD_AMOUNT = 2;
		
		Lock resLock = new ReentrantLock();
				
		ExecutorService execService = Executors.newFixedThreadPool(THREAD_AMOUNT);
		
		long start = System.currentTimeMillis();
		
		Callable<String> f1 = new F1(N, mm, fs, INPUT_PATH, OUTPUT_PATH, resLock);
		Callable<String> f2 = new F2(N, mm, vm, fs, INPUT_PATH, OUTPUT_PATH, resLock);
		Future<String> complete1 = execService.submit(f1);
	    Future<String> complete2 = execService.submit(f2);
		
		try {
			System.out.println(complete1.get());
		} catch (InterruptedException ex) {
			System.out.println("Роботу потоку F1 було перервано - " + ex);
		} catch (ExecutionException ex) {
			System.out.println("Помилка в роботі потоку F1 - " + ex);
		}
		try {
			System.out.println(complete2.get());
		} catch (InterruptedException ex) {
			System.out.println("Роботу потоку F2 було перервано - " + ex);
		} catch (ExecutionException ex) {
			System.out.println("Помилка в роботі потоку F2 - " + ex);
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
