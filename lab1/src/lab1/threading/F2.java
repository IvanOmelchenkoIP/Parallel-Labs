package lab1.threading;

import lab1.data.Matrix;
import lab1.data.MatrixGenerator;
import lab1.data.Vector;
import lab1.data.VectorGenerator;

public class F2 implements Runnable {

	int N = 100;
	
	@Override
	public void run() {
		Vector D = Vector.generateRandom(N);
		Vector B = Vector.generateRandom(N);
		Matrix MT = Matrix.generateRandom(N);
		
		Vector A = D.getMatrixMultiplyProduct(MT).getVectorDifference(B.getScalarMultiplyProduct(D.max()));
		String result = A.toString();
		System.out.println(result);
	}

}
