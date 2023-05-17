// ./lab4/src/lab4/threading/F1
// F1 by variant: MА = MD * MT + MZ - ME * MM   

package lab4.threading;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

import lab4.data.matrix.Matrix;
import lab4.data.matrix.MatrixManager;
import lab4.fs.FileSystem;

public class F1 implements Callable<String> {

	final private int N;
	
	final private MatrixManager mm;
	final private FileSystem fs;
	
	final private String inPath;
	final private String outPath;
	
	private final Lock resLock;

	public F1(int N, MatrixManager mm, FileSystem fs, String inPath, String outPath, Lock resLock) {
		this.N = N;
		
		this.mm = mm;
		this.fs = fs;
		
		this.inPath = inPath;
		this.outPath = outPath;
		
		this.resLock = resLock;
	}

	@Override
	public String call() throws IOException, Exception {
		Matrix MD, MT, MZ, ME, MM;
		try {
			MD = mm.getMatrix("MD", N, inPath);
			MT = mm.getMatrix("MT", N, inPath);
			MZ = mm.getMatrix("MZ", N, inPath);
			ME = mm.getMatrix("ME", N, inPath);
			MM = mm.getMatrix("MM", N, inPath);
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}
		Matrix MA = MD.getMatrixMultiplyProduct(MT).getMatrixSum(MZ).getMatrixDifference(ME.getMatrixMultiplyProduct(MM));
		
		String result = MA.toString();
		try {
			resLock.lock();
			System.out.println("F1\n" + result);
			try {
				fs.write(outPath, "MA\n" + result);
			} catch (IOException ex) {
				throw ex;
			}
		} finally {
			resLock.unlock();
		}
		return "Потік для функцій F1 успішно завершив обчислення і виведення результату.";
	}
}
