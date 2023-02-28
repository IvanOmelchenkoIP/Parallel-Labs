package lab1.data;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import lab1.fs.FileSystem;

public class MatrixGenerator {
	
	FileSystem fs = new FileSystem();
	
	public static double[][] generateOrRead(String name, int N) {
		String filepath = "input.txt";
		double[][] MA;
		
		if (!new File(filepath).exists()) {
			MA = generateRandom(N);
			//fs.write(name + toString(MA, N));
			return MA;
		}
		
		//String contents = fs.read();
		
		return null;
	}
	
	public static String toString(double[][] MA, int N) {
		String matrix = "";
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				matrix += MA[i][j] + " ";
			}
			matrix += "\n";
		}
		return matrix;
	}

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

	public static double[][] generateRandom(int n) {
		final double[][] MA = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				MA[i][j] = DoublePrecisionGenerator.generateDoubleWithPrecison(0, 1000, ThreadLocalRandom.current().nextInt(3, 15));
			}
		}
		return MA;
	}
}
