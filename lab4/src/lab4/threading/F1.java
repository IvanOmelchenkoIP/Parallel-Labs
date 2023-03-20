// ./lab4/src/lab4/threading/F1
// F1 by variant: MА = MD * MT + MZ - ME * MM   

package lab4.threading;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import lab4.data.matrix.Matrix;
import lab4.data.matrix.MatrixManager;

public class F1 implements Runnable {

	final private int N;
	final private MatrixManager mm;
	private final Lock lock;
	private final CountDownLatch countDownLatch;
	
	public F1(int N, MatrixManager mm, Lock lock, CountDownLatch countDownLatch) {
		this.N = N;
		this.lock = lock;
		this.countDownLatch = countDownLatch;
		this.mm = mm;
	}
	
	@Override
	public void run() {		
		Matrix MD, MT, MZ, ME, MM;
		try {
			MD = mm.getMatrix("MD", N);
			MT = mm.getMatrix("MT", N);
			MZ = mm.getMatrix("MZ", N);
			ME = mm.getMatrix("ME", N);
			MM = mm.getMatrix("MM", N);
		} catch (IOException ex) {
			System.out.println("Неможливо продовжити роботу потоку F1 (помилка файлової системи) - " + ex);
			countDownLatch.countDown();
			return;
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити роботу потоку F1 - " + ex);
			countDownLatch.countDown();
			return;
		}		
		Matrix MA = MD.getMatrixMultiplyProduct(MT).getMatrixSum(MZ).getMatrixDifference(ME.getMatrixMultiplyProduct(MM));
		try {
			lock.lock();
			System.out.println("F1");
			System.out.println(MA.toString());
			try {
				mm.writeToFile(mm.getOutPath(), "MA", MA);
			} catch (IOException ex) {
				System.out.println("Неможливо продовжити роботу потоку F1 (помилка при записі результату у файл) - " + ex);
			}
		} finally {
			lock.unlock();
			countDownLatch.countDown();
		}
	}
}
