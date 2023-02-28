package lab1.data;

import java.util.concurrent.ThreadLocalRandom;

public class VectorGenerator {

	public static double[] generateFromString(String str) {
		String[] elements = str.trim().split("");
		final double[] A = new double[elements.length];
		for (int i = 0; i < elements.length; i++) {
			A[i] = Double.parseDouble(elements[i]);
		}
		return A;
	}
	
	public static double[] generateRandom(int n) {
		final double[] A = new double[10];
		for (int i = 0; i < 10; i++) {
			A[i] = ThreadLocalRandom.current().nextDouble();
		}
		return A;
	}
}
