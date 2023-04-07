// ./lab5/src/lab5/threading/F1
// F1 by variant: MА = MD * MT + MZ - ME * MM   

package lab5.threading;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

import lab5.data.matrix.Matrix;
import lab5.data.matrix.MatrixManager;

public class F1 implements Callable<String> {

	final private int N;
	final private MatrixManager mm;
	private final Lock resLock;

	public F1(int N, MatrixManager mm, Lock resLock) {
		this.N = N;
		this.resLock = resLock;
		this.mm = mm;
	}

	@Override
	public String call() throws IOException, Exception {
		Matrix MD, MT, MZ, ME, MM;
		try {
			MD = mm.getMatrix("MD", N);
			MT = mm.getMatrix("MT", N);
			MZ = mm.getMatrix("MZ", N);
			ME = mm.getMatrix("ME", N);
			MM = mm.getMatrix("MM", N);
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}
		Matrix MA = MD.getMatrixMultiplyProduct(MT).getMatrixSum(MZ).getMatrixDifference(ME.getMatrixMultiplyProduct(MM));
		try {
			resLock.lock();
			System.out.println("F1");
			System.out.println(MA.toString());
			try {
				mm.writeToFile(mm.getOutPath(), "MA", MA);
			} catch (IOException ex) {
				throw ex;
			}
		} finally {
			resLock.unlock();
		}
		return "Потік для функцій F1 успішно завершив обчислення і виведення результату.";
	}
}
