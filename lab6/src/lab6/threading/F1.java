// ./lab6/src/lab6/threading/F1.java
// F1 by variant: MА = MD * MT + MZ - ME * MM   

package lab6.threading;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import lab6.data.matrix.Matrix;
import lab6.data.matrix.MatrixManager;

public class F1 implements Runnable {
	
	final private int N;
	final private MatrixManager mm;
	final private String inPath;
	final private BlockingQueue<String> queue;
	
	public F1(int N, MatrixManager mm, String inPath, BlockingQueue<String> queue) {
		this.N = N;
		this.mm = mm;
		this.inPath = inPath;
		this.queue = queue;
	}

	@Override
	public void run() {
		Matrix MD, MT, MZ, ME, MM;
		try {
			MD = mm.getMatrix("MD", N, inPath);
			MT = mm.getMatrix("MT", N, inPath);
			MZ = mm.getMatrix("MZ", N, inPath);
			ME = mm.getMatrix("ME", N, inPath);
			MM = mm.getMatrix("MM", N, inPath);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		Matrix MA = MD.getMatrixMultiplyProduct(MT).getMatrixSum(MZ).getMatrixDifference(ME.getMatrixMultiplyProduct(MM));
		queue.add("MA\n" + MA.toString());		
	}
}
