// ./lab4/src/lab4/threading/F2.java
// F2 by variant: A = D * MT - max(D) * B

package lab4.threading;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import lab4.data.matrix.Matrix;
import lab4.data.matrix.MatrixManager;
import lab4.data.vector.Vector;
import lab4.data.vector.VectorManager;

public class F2 implements Runnable {

	final private int N;
	final private MatrixManager mm;
	final private VectorManager vm;
	private final Lock lock;
	private final CountDownLatch countDownLatch;
	
	public F2(int N, MatrixManager mm, VectorManager vm, Lock lock, CountDownLatch countDownLatch) {
		this.N = N;
		this.lock = lock;
		this.countDownLatch = countDownLatch;
		this.mm = mm;
		this.vm = vm;
	}
	
	@Override
	public void run() {
		Vector D, B;
		Matrix MT;
		try {
			D = vm.getVector("D", N);
			B = vm.getVector("B", N);
			MT = mm.getMatrix("MT", N);
		} catch (IOException ex) {
			System.out.println("Неможливо продовжити роботу потоку F2 (помилка файлової системи) - " + ex);
			countDownLatch.countDown();
			return;
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити роботу потоку F2 - " + ex);
			countDownLatch.countDown();
			return;
		}		
		Vector A = D.getMatrixMultiplyProduct(MT).getVectorDifference(B.getScalarMultiplyProduct(D.max()));
		try {
			lock.lock();
			System.out.println("F2");
			System.out.println(A.toString());
			try {
				vm.writeToFile(mm.getOutPath(), "A", A);
			} catch (IOException ex) {
				System.out.println("Неможливо продовжити роботу потоку F2 (помилка при записі результату у файл) - " + ex);
			}
		} finally {
			lock.unlock();
			countDownLatch.countDown();
		}	
	}
}
