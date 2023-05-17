// ./lab6/src/lab6/Lab6.java

package lab6;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import lab6.data.matrix.MatrixManager;
import lab6.data.vector.VectorManager;
import lab6.fs.FileSystem;
import lab6.fs.MemFileSystem;
import lab6.threading.F1;
import lab6.threading.F2;
import lab6.threading.consumer.OutConsumer;

public class Lab6 {

	public static void main(String[] args) {
		final int MIN_VAL = 0;
		final int MAX_VAL = 100;
		final int MIN_PRECISION = 3;
		final int MAX_PRECISION = 15;
		final int N = 100;
		final String INPUT_PATH = "../input/input.txt";
		final String OUTPUT_PATH = "../output/output6.txt";
		
		FileSystem fs = new MemFileSystem();
		
		VectorManager vm = new VectorManager(MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION, fs);
		MatrixManager mm = new MatrixManager(MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION, fs);
		
		final int THREAD_AMOUNT = 2;
		
		long start = System.currentTimeMillis();
		
		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1);
		
		Thread f1 = new Thread(new F1(N, mm, INPUT_PATH, queue));
		Thread f2 = new Thread(new F2(N, mm, vm, INPUT_PATH, queue));
		f1.start();
		f2.start();
		Thread consumer = new Thread(new OutConsumer(THREAD_AMOUNT, queue, fs, OUTPUT_PATH));

		consumer.start();
		
		try {
			consumer.join();
		} catch (InterruptedException e) {
			System.out.println(e);
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
