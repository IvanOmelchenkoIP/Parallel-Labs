// ./lab6/src/lab6/threading/F1.java
// F1 by variant: MА = MD * MT + MZ - ME * MM   

package lab6.threading;

import java.io.IOException;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.Lock;

import lab6.data.matrix.Matrix;
import lab6.data.matrix.MatrixManager;

public class F1 extends RecursiveTask<String> {

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
	protected String compute() {
		Matrix MD, MT, MZ, ME, MM;
		try {
			MD = mm.getMatrix("MD", N);
			MT = mm.getMatrix("MT", N);
			MZ = mm.getMatrix("MZ", N);
			ME = mm.getMatrix("ME", N);
			MM = mm.getMatrix("MM", N);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		Matrix MA = MD.getMatrixMultiplyProduct(MT).getMatrixSum(MZ).getMatrixDifference(ME.getMatrixMultiplyProduct(MM));
		try {
			resLock.lock();
			System.out.println("F1");
			System.out.println(MA.toString());
			try {
				mm.writeToFile(mm.getOutPath(), "MA", MA);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		} finally {
			resLock.unlock();
		}
		return "Потік для функцій F1 успішно завершив обчислення і виведення результату.";
	}
}