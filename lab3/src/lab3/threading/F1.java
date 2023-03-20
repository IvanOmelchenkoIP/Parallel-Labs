// ./lab3/src/lab3/threading/F1
// F1 by variant: MА = MD * MT + MZ - ME * MM   

package lab3.threading;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import lab3.data.matrix.Matrix;
import lab3.data.matrix.MatrixManager;

public class F1 implements Runnable {

	final private int N;
	final private MatrixManager mm;
	private final Semaphore semaphore;
	private final CountDownLatch countDownLatch;
	
	public F1(int N, MatrixManager mm, Semaphore semaphore, CountDownLatch countDownLatch) {
		this.N = N;
		this.semaphore = semaphore;
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
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити роботу потоку F1 (потік було перервано) - " + ex);
			countDownLatch.countDown();
			return;
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
			semaphore.acquire();
			System.out.println("F1");
			System.out.println(MA.toString());
			try {
				mm.writeToFile(mm.getOutPath(), "MA", MA);
			} catch (IOException ex) {
				System.out.println("Неможливо продовжити роботу потоку F1 (помилка при записі результату у файл) - " + ex);
			}
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити роботу потоку F1 (потік було перервано) - " + ex);
		} finally {
			semaphore.release();
			countDownLatch.countDown();
		}
	}
}
