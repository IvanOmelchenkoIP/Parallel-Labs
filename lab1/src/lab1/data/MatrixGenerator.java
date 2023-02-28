package lab1.data;

import java.util.concurrent.ThreadLocalRandom;

public class MatrixGenerator {

	public static double[][] generateFromString(String str) {
		
		String[] lines = str.trim().split("\n");
		final double[][] MA = new double[lines.length][lines.length];
		for (int i = 0; i < lines.length; i++) {
			String[] elements = lines[i].split(" ");
			for (int j = 0; j < lines.length; j++) {
				MA[i][j] = Double.parseDouble(elements[j]);
			}
		}
		return MA;
	}
	
	public static double[][] generate(int n, int m) {
		final double[][] MA = new double[n][n];
		for (int i = 0; i < n; i ++) {
			for (int j = 0; j < n; j++) {
				MA[i][j] = ThreadLocalRandom.current().nextDouble();
			}
		}
		return MA;
	}
}
