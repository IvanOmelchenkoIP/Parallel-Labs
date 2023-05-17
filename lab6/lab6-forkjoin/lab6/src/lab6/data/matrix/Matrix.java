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
	
	public int getN() {
		return N;
	}
	
	public int getM() {
		return M;
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
	
	public Matrix getPartialMatrixByM(int start, int end) {
		int M = end - start + 1;
		double[][] valueMB = new double[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = start, k = 0; j <= end; j++, k++) {
				valueMB[i][k] = valueMA[i][j];
			}
		}
		return new Matrix(valueMB);
	}
	
	public Matrix getPartialMatrixByN(int start, int end) {
		int N = end - start + 1;
		double[][] valueMB = new double[N][M];
		for (int i = start, j = 0; i <= end; i++, j++) {
			for (int k = 0; k < M; k++) {
				valueMB[j][k] = valueMA[i][k];
			}
		}
		return new Matrix(valueMB);
	}
	
	public static Matrix mergeByM(Matrix MA, Matrix MB) {
		int N = MA.getN();
		int M1 = MA.getM();
		int M = M1 + MB.getM();
		double[][] valueMA = MA.getValue();
		double[][] valueMB = MB.getValue();
		double[][] valueMC = new double[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M1; j++) {
				valueMC[i][j] = valueMA[i][j];
			}
			for (int g = M1, k = 0; g < M; k++, g++) {
				valueMC[i][g] = valueMB[i][k];
			}
		}
		return new Matrix(valueMC);
	}
}
