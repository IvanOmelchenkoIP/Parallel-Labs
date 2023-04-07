// ./lab5/src/lab5/threading/F1.java
// F1 by variant: MА = MD * MT + MZ - ME * MM   

package lab5.threading;

import java.io.IOException;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.Lock;

import lab5.data.matrix.Matrix;
import lab5.data.matrix.MatrixManager;

public class F1 extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	
	final private int N;
	final private MatrixManager mm;
	private final Lock resLock;

	public F1(int N, MatrixManager mm, Lock resLock) {
		this.N = N;
		this.resLock = resLock;
		this.mm = mm;
	}

	@Override
	protected void compute() {
		Matrix MD, MT, MZ, ME, MM;
		try {
			MD = mm.getMatrix("MD", N);
			MT = mm.getMatrix("MT", N);
			MZ = mm.getMatrix("MZ", N);
			ME = mm.getMatrix("ME", N);
			MM = mm.getMatrix("MM", N);
		} catch (IOException ex) {
			System.out.println("Неможливо продовжити роботу потоку F1 (помилка файлової системи) - " + ex);			
			return;
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити роботу потоку F1 - " + ex);
			return;
		}
		Matrix MA = MD.getMatrixMultiplyProduct(MT).getMatrixSum(MZ).getMatrixDifference(ME.getMatrixMultiplyProduct(MM));
		try {
			resLock.lock();
			System.out.println("F1");
			System.out.println(MA.toString());
			try {
				mm.writeToFile(mm.getOutPath(), "MA", MA);
			} catch (IOException ex) {
				System.out.println("Неможливо продовжити роботу потоку F1 (помилка при записі результату) - " + ex);
			}
		} finally {
			resLock.unlock();
		}
		
	}
}
