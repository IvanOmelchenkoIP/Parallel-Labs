package lab1.threading;

import lab1.data.MatrixGenerator;

public class F1 implements Runnable {

	final int N = 4;
	
	@Override
	public void run() {
		double[][] MA = MatrixGenerator.generateRandom(N);
		double[][] MT = MatrixGenerator.generateRandom(N);
		double[][] MZ = MatrixGenerator.generateRandom(N);
		double[][] ME = MatrixGenerator.generateRandom(N);
		double[][] MM = MatrixGenerator.generateRandom(N);
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(MA[i][j] + " ");
			}
			System.out.println();
		}
	}

}
