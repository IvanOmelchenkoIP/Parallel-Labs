// ./lab2/src/lab2/threading/F2.java
// F2 by variant: A = D * MT - max(D) * B

package lab2.threading;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import lab2.data.matrix.Matrix;
import lab2.data.matrix.MatrixManager;
import lab2.data.vector.Vector;
import lab2.data.vector.VectorManager;

public class F2 implements Runnable {

	final private int N;
	final private MatrixManager mm;
	final private VectorManager vm;
	private final Semaphore semaphore;
	private final CountDownLatch countDownLatch;
	
	public F2(int N, MatrixManager mm, VectorManager vm, Semaphore semaphore, CountDownLatch countDownLatch) {
		this.N = N;
		this.semaphore = semaphore;
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
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити роботу потоку F2 (потік було перервано) - " + ex);
			countDownLatch.countDown();
			return;
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
			semaphore.acquire();
			System.out.println("F2");
			System.out.println(A.toString());
			try {
				vm.writeToFile(mm.getOutPath(), "A", A);
			} catch (IOException ex) {
				System.out.println("Неможливо продовжити роботу потоку F2 (помилка при записі результату у файл) - " + ex);
			}
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити роботу потоку F2 (потік було перервано) - " + ex);
		} finally {
			semaphore.release();
			countDownLatch.countDown();
		}		
	}
}
