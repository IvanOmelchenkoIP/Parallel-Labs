// ./lab6/src/lab6/data/matrix/Matrix.java

package lab6.data.matrix;

import lab6.data.KahanSum;

public class Matrix {
	
	final private int N;
	final private int M;
	final private double[][] valueMA;
	
	public Matrix(double[][] valueMA) {
		this.valueMA = valueMA;
		this.N = valueMA.length;
		this.M = valueMA[0].length;
	}
	
	public int getN() {
		return N;
	}
	
	public int getM() {
		return M;
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
		return new Matrix(valueMA);
	}

	@Override
	public String toString() {
		String matrix = "";
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
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
		double[][] valueMC = new double[N][MB.getM()];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < MB.getM(); j++) {
				double[] products = new double[MB.getN()];
				for (int k = 0; k < MB.getN(); k++) {
					products[k] = valueMA[i][k] * valueMB[k][j];
				}
				valueMC[i][j] = KahanSum.add(products);
			}
		}
		return new Matrix(valueMC);
	}
	
	public Matrix getMatrixSum(Matrix MB) {		
		double[][] valueMB = MB.getValue();
		double[][] valueMC = new double[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				valueMC[i][j] = valueMA[i][j] + valueMB[i][j];
			}
		}
		return new Matrix(valueMC);
	}
	
	public Matrix getMatrixDifference(Matrix MB) {
		double[][] valueMB = MB.getValue();
		double[][] valueMC = new double[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				valueMC[i][j] = valueMA[i][j] - valueMB[i][j];
			}
		}
		return new Matrix(valueMC);
	}
}
