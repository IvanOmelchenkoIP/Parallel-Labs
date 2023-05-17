// ./lab6/src/lab6/threading/F2.java
// F2 by variant: A = D * MT - max(D) * B

package lab6.threading;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import lab6.data.matrix.Matrix;
import lab6.data.matrix.MatrixManager;
import lab6.data.vector.Vector;
import lab6.data.vector.VectorManager;

public class F2 implements Runnable {
	
	final private int N;
	final private MatrixManager mm;
	final private VectorManager vm;	
	final private String inPath;
	private BlockingQueue<String> queue;

	public F2(int N, MatrixManager mm, VectorManager vm, String inPath, BlockingQueue<String> queue) {
		this.N = N;
		this.mm = mm;
		this.vm = vm;
		this.inPath = inPath;
		this.queue = queue;
	}

	@Override
	public void run() {
		Vector D, B;
		Matrix MT;
		try {
			D = vm.getVector("D", N, inPath);
			B = vm.getVector("B", N, inPath);
			MT = mm.getMatrix("MT", N, inPath);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		Vector A = D.getMatrixMultiplyProduct(MT).getVectorDifference(B.getScalarMultiplyProduct(D.max()));
		queue.add("A\n" + A.toString());		
	}
}
