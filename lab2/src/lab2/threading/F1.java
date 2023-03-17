// ./lab2/src/lab2/threading/F1
// F1 by variant: MА = MD * MT + MZ - ME * MM   

package lab2.threading;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import lab2.data.matrix.Matrix;
import lab2.data.matrix.MatrixIO;

public class F1 implements Runnable {

	final private int N;
	final private MatrixIO mio;
	private final Semaphore semaphore;
	private final CountDownLatch countDownLatch;
	
	public F1(int N, String inPath, String outPath, Semaphore semaphore, CountDownLatch countDownLatch) {
		this.N = N;
		this.semaphore = semaphore;
		this.countDownLatch = countDownLatch;
		this.mio = new MatrixIO(inPath, outPath);
	}
	
	@Override
	public void run() {		
		Matrix MD, MT, MZ, ME, MM;
		try {
			MD = mio.generateOrRead("MD", N);
			MT = mio.generateOrRead("MT", N);
			MZ = mio.generateOrRead("MZ", N);
			ME = mio.generateOrRead("ME", N);
			MM = mio.generateOrRead("MM", N);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Неможливо виконати обчислення - " + ex);
			return;
		} catch (Exception ex) {
			System.out.println("Неможливо виконати обчислення - " + ex);
			return;
		}		
		Matrix MA = MD.getMatrixMultiplyProduct(MT).getMatrixSum(MZ).getMatrixDifference(ME.getMatrixMultiplyProduct(MM));
		String result = MA.toString();
		synchronized(this) {
			System.out.println("F1");
			System.out.println(result);
			try {
				mio.writeResult("MA", result);
			} catch (IOException ex) {
				System.out.println("Помилка при записі результату у файл - " + ex);
			}
			return;
		}
	}

}
