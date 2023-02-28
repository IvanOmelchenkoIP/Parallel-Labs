package lab1.threading;

import java.util.Arrays;

import lab1.data.Matrix;
import lab1.data.MatrixGenerator;

public class F1 implements Runnable {

	final int N = 4;
	
	@Override
	public void run() {
		Matrix MD = Matrix.generateRandom(N);
		Matrix MT = Matrix.generateRandom(N);
		Matrix MZ = Matrix.generateRandom(N);
		Matrix ME = Matrix.generateRandom(N);
		Matrix MM = Matrix.generateRandom(N);
		
		Matrix MA = MD.getMatrixMultiplyProduct(MT).getMatrixSum(MZ).getMatrixDifference(MT.getMatrixMultiplyProduct(MM));
		String result = MA.toString();
		System.out.println(result);
	}

}
