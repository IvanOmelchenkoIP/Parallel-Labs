// ./lab6/src/lab6/Lab6.java

package lab6;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import lab6.data.matrix.MatrixManager;
import lab6.data.vector.VectorManager;
import lab6.fs.FileSystem;
import lab6.fs.MemFileSystem;
import lab6.threading.F1;
import lab6.threading.F2;
import lab6.threading.consumer.OutConsumer;
import lab6.threading.subtasks.DivisibleSubtask;

public class Lab6 {

	public static void main(String[] args) {
		final int MIN_VAL = 0;
		final int MAX_VAL = 100;
		final int MIN_PRECISION = 3;
		final int MAX_PRECISION = 15;
		final int N = 100;
		final String INPUT_PATH = "../input/input.txt";
		final String OUTPUT_PATH = "../output/output6-2.txt";
		
		FileSystem fs = new MemFileSystem();
		
		VectorManager vm = new VectorManager(MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION, fs);
		MatrixManager mm = new MatrixManager(MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION, fs);
		
		DivisibleSubtask.setDivideN(N / 10);
		
		final int THREAD_AMOUNT = 2;
		
		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1);
		
		ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		
		long start = System.currentTimeMillis();
		
		ForkJoinTask<?> f1 = new F1(N, mm, INPUT_PATH, queue);
		ForkJoinTask<?> f2 = new F2(N, mm, vm, INPUT_PATH, queue);
		ForkJoinTask<?> consumer = new OutConsumer(THREAD_AMOUNT, queue, fs, OUTPUT_PATH);
		
		forkJoinPool.execute(f1);
		forkJoinPool.execute(f2);
		forkJoinPool.execute(consumer);
		
		consumer.join();
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