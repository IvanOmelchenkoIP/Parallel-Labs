// ./lab2/src/lab2/data/generators/DoubleArrayGenerator.java

package lab2.data.generators;

import java.util.concurrent.ThreadLocalRandom;

public class DoubleArrayGenerator {
	
	DoublePrecisionGenerator dpg;
	
	public DoubleArrayGenerator() {
		this.dpg = new DoublePrecisionGenerator();
	}
	
	public double[] generateDoubleArray(int size, int min, int max, int minPrecision, int maxPrecision) {
		final double[] arr = new double[size];
		for (int i = 0; i < size; i++) {
			arr[i] = dpg.generateDoubleWithPrecison(min, max, ThreadLocalRandom.current().nextInt(minPrecision, maxPrecision));
		}
		return arr;
	}
			
	public double[][] generate2DDoubleArray(int size, int min, int max, int minPrecision, int maxPrecision) {
		final double[][] arr2D = new double[size][size];
		for (int i = 0; i < size; i++) {
			arr2D[i] = generateDoubleArray(size, min, max, minPrecision, maxPrecision);
		}
		return arr2D;
	}
}
