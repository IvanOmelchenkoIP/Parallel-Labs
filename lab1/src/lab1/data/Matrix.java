package lab1.data;

import java.util.concurrent.ThreadLocalRandom;

public class Matrix {

	final private static int MIN_VAL = 0;
	final private static int MAX_VAL = 1000;
	final private static int MIN_PRECISION = 3;
	final private static int MAX_PRECISION = 15;
	
	final private int size;
	final private double[][] valueMA;
	
	private Matrix(int size, double[][] valueMA) {
		this.size = size;
		this.valueMA = valueMA;
	}
	
	public static Matrix generateFromString(String str) {
		String[] lines = str.trim().split("\n");
		int size = lines.length;
		final double[][] valueMA = new double[size][size];
		for (int i = 0; i < size; i++) {
			String[] elements = lines[i].split(" ");
			for (int j = 0; j < size; j++) {
				valueMA[i][j] = Double.parseDouble(elements[j]);
			}
		}
		return new Matrix(size, valueMA);
	}

	public static Matrix generateRandom(int size) {
		final double[][] valueMA = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				valueMA[i][j] = DoublePrecisionGenerator.generateDoubleWithPrecison(MIN_VAL, MAX_VAL, ThreadLocalRandom.current().nextInt(MIN_PRECISION, MAX_PRECISION));
			}
		}
		return new Matrix(size, valueMA);
	}
	
	@Override
	public String toString() {
		String matrix = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix += valueMA[i][j] + " ";
			}
			matrix += "\n";
		}
		return matrix;
	}
	
	public double[][] getValue() {
		return valueMA;
	}
	
	public Matrix getMatrixMultiplyProduct(Matrix MB) {
		double[][] valueMB = MB.getValue();
		double[][] valueMC = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				double[] products = new double[size];
				for (int k = 0; k < size; k++) {
					products[k] = valueMA[i][k] * valueMB[k][j];
				}
				valueMC[i][j] = KahanSum.add(products);
			}
		}
		return new Matrix(size, valueMC);
	}
	
	public Matrix getMatrixSum(Matrix MB) {		
		double[][] valueMB = MB.getValue();
		double[][] valueMC = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				valueMC[i][j] = KahanSum.add(valueMA[i][j], valueMB[i][j]);
			}
		}
		return new Matrix(size, valueMC);

	}
	
	public Matrix getMatrixDifference(Matrix MB) {
		double[][] valueMB = MB.getValue();
		double[][] valueMC = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				valueMC[i][j] = KahanSum.add(valueMA[i][j], valueMB[i][j] * (-1));
			}
		}
		return new Matrix(size, valueMC);

	}
}
