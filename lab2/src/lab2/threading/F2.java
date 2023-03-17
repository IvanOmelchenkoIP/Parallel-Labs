// ./lab2/src/lab2/threading/F2.java
// F2 by variant: A = D * MT - max(D) * B

package lab2.threading;

import java.io.IOException;

import lab2.data.matrix.Matrix;
import lab2.data.matrix.MatrixIO;
import lab2.data.vector.Vector;
import lab2.data.vector.VectorIO;

public class F2 implements Runnable {

	final private int N = 100;
	final private MatrixIO mio = new MatrixIO();
	final private VectorIO vio = new VectorIO();
	
	@Override
	public void run() {
		Vector D, B;
		Matrix MT;
		try {
			D = vio.generateOrRead("D", N);
			B = vio.generateOrRead("B", N);
			MT = mio.generateOrRead("MT", N);
		} catch (IOException ex) {
			System.out.println("Неможливо виконати обчислення - " + ex);
			return;
		} catch (Exception ex) {
			System.out.println("Неможливо виконати обчислення - " + ex);
			return;
		}		
		Vector A = D.getMatrixMultiplyProduct(MT).getVectorDifference(B.getScalarMultiplyProduct(D.max()));
		String result = A.toString();
		synchronized(this) {
			System.out.println("F2");
			System.out.println(result);
			try {
				vio.writeResult("A", result);
			} catch (IOException ex) {
				System.out.println("Помилка при записі результату у файл - " + ex);
			}
			return;
		}
	}

}
