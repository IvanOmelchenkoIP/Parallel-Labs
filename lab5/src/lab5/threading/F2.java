// ./lab5/src/lab5/threading/F2.java
// F2 by variant: A = D * MT - max(D) * B

package lab5.threading;

import java.io.IOException;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.Lock;

import lab5.data.matrix.Matrix;
import lab5.data.matrix.MatrixManager;
import lab5.data.vector.Vector;
import lab5.data.vector.VectorManager;

public class F2 extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	
	final private int N;
	final private MatrixManager mm;
	final private VectorManager vm;
	private final Lock resLock;

	public F2(int N, MatrixManager mm, VectorManager vm, Lock resLock) {
		this.N = N;
		this.resLock = resLock;
		this.mm = mm;
		this.vm = vm;
	}

	@Override
	protected void compute() {
		Vector D, B;
		Matrix MT;
		try {
			D = vm.getVector("D", N);
			B = vm.getVector("B", N);
			MT = mm.getMatrix("MT", N);
		} catch (IOException ex) {
			System.out.println("Неможливо продовжити роботу потоку F2 (помилка файлової системи) - " + ex);		
			return;
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити роботу потоку F2 - " + ex);
			return;
		}
		Vector A = D.getMatrixMultiplyProduct(MT).getVectorDifference(B.getScalarMultiplyProduct(D.max()));
		try {
			resLock.lock();
			System.out.println("F2");
			System.out.println(A.toString());
			try {
				vm.writeToFile(mm.getOutPath(), "A", A);
			} catch (IOException ex) {
				System.out.println("Неможливо продовжити роботу потоку F2 (помилка при записі результату) - " + ex);
			}
		} finally {
			resLock.unlock();
		}
		
	}
}
