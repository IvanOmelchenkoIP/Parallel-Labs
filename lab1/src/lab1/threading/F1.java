package lab1.threading;

import java.util.Arrays;

import lab1.data.MatrixGenerator;

public class F1 implements Runnable {

	final int N = 4;
	
	@Override
	public void run() {
		double[][] MD = MatrixGenerator.generateRandom(N);
		double[][] MT = MatrixGenerator.generateRandom(N);
		double[][] MZ = MatrixGenerator.generateRandom(N);
		double[][] ME = MatrixGenerator.generateRandom(N);
		double[][] MM = MatrixGenerator.generateRandom(N);
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(MD[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println(Arrays.deepToString(MT));
	}

}
