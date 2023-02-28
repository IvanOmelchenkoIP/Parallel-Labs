package lab1.threading;

import lab1.data.MatrixGenerator;
import lab1.data.VectorGenerator;

public class F2 implements Runnable {

	int N = 100;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		double[] D = VectorGenerator.generateRandom(N);
		double[] B = VectorGenerator.generateRandom(N);
		double[][] MT = MatrixGenerator.generateRandom(N);
		
	}

}
