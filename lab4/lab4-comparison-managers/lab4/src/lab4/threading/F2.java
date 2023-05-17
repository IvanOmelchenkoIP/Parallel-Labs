// ./lab4/src/lab4/threading/F2.java
// F2 by variant: A = D * MT - max(D) * B

package lab4.threading;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

import lab4.data.matrix.Matrix;
import lab4.data.matrix.MatrixManager;
import lab4.data.vector.Vector;
import lab4.data.vector.VectorManager;
import lab4.fs.FileSystem;

public class F2 implements Callable<String> {

	final private int N;
	
	final private MatrixManager mm;
	final private VectorManager vm;
	final private FileSystem fs;
	
	final private String inPath;
	final private String outPath;
	
	final private Lock resLock;

	public F2(int N, MatrixManager mm, VectorManager vm, FileSystem fs, String inPath, String outPath, Lock resLock) {
		this.N = N;
		
		this.mm = mm;
		this.vm = vm;
		this.fs = fs;
		
		this.inPath = inPath;
		this.outPath = outPath;
		
		this.resLock = resLock;
	}

	@Override
	public String call() throws IOException, Exception {
		Vector D, B;
		Matrix MT;
		try {
			D = vm.getVector("D", N, inPath);
			B = vm.getVector("B", N, inPath);
			MT = mm.getMatrix("MT", N, inPath);
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}
		Vector A =  D.getMatrixMultiplyProduct(MT).getVectorDifference(B.getScalarMultiplyProduct(D.max()));
		
		String result = A.toString();
		try {
			resLock.lock();
			System.out.println("F2\n" + result);
			try {
				fs.write(outPath, "A\n" + result);
			} catch (IOException ex) {
				throw ex;
			}
		} finally {
			resLock.unlock();
		}
		return "Потік для функцій F2 успішно завершив обчислення і виведення результату.";
	}
}
