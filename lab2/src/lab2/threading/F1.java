// ./lab2/src/lab2/threading/F1
// F1 by variant: MА = MD * MT + MZ - ME * MM   

package lab2.threading;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import lab2.data.matrix.Matrix;
import lab2.data.matrix.MatrixManager;

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
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Неможливо виконати обчислення - " + ex);
			return;
		} catch (Exception ex) {
			System.out.println("Неможливо виконати обчислення - " + ex);
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
				System.out.println("Помилка при записі результату у файл - " + ex);
			}
		} catch (InterruptedException ex) {
			System.out.println("Роботу потоку F1 було перервано - " + ex);
		} finally {
			semaphore.release();
			countDownLatch.countDown();
		}
	}

}
