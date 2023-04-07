// ./lab5/src/lab5/data/matrix/Matrix.java

package lab5.data.matrix;

import lab5.data.KahanSum;

public class Matrix {
	
	final private int size;
	final private double[][] valueMA;
	
	public Matrix(int size, double[][] valueMA) {
		this.size = size;
		this.valueMA = valueMA;
	}
	
	public static Matrix fromString(String str) {
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
				valueMC[i][j] = valueMA[i][j] + valueMB[i][j];
			}
		}
		return new Matrix(size, valueMC);
	}
	
	public Matrix getMatrixDifference(Matrix MB) {
		double[][] valueMB = MB.getValue();
		double[][] valueMC = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				valueMC[i][j] = valueMA[i][j] - valueMB[i][j];
			}
		}
		return new Matrix(size, valueMC);
	}
}
