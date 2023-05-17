// ./lab5/src/lab5/data/vector/Vector.java

package lab5.data.vector;

import lab5.data.KahanSum;
import lab5.data.matrix.Matrix;

public class Vector {
	
	final private int N;
	final private double[] valueA;
	
	public Vector(double[] valueA) {
		this.N = valueA.length;
		this.valueA = valueA;
	}
	
	public static Vector fromString(String str) {
		String[] elements = str.trim().split(" ");
		int N = elements.length;
		final double[] valueA = new double[N];
		for (int i = 0; i < N; i++) {
			valueA[i] = Double.parseDouble(elements[i]);
		}
		return new Vector(valueA);
	}
	
	@Override
	public String toString() {
		String vector = "";
		for (double a : valueA) {
			vector += a + " ";
		}
		vector += "\n";
		return vector;
	}
	
	public int getN() {
		return N;
	}
	
	public double[] getValue() {
		return valueA;
	}
	
	public Vector getVectorDifference(Vector B) {
		double[] valueB = B.getValue();
		double[] valueC = new double[N];
		for (int i = 0; i < N; i++) {
			valueC[i] = valueA[i] - valueB[i];
		}
		return new Vector(valueC);
	}
	
	public Vector getMatrixMultiplyProduct(Matrix MA) {
		double[][] valueMA = MA.getValue();
		double[] valueB = new double[MA.getN()];
		for (int i = 0; i < MA.getN(); i++) {
			double[] products = new double[N];
			for (int j = 0; j < MA.getM(); j++) {
				products[j] = valueMA[i][j] * valueA[j];
			}
			valueB[i] = KahanSum.add(products);
		}
		return new Vector(valueB);
	}
	
	public Vector getScalarMultiplyProduct(double a) {
		double[] valueB = new double[N];
		for (int i = 0; i < N; i++) {
			valueB[i] = a * valueA[i];
		}
		return new Vector(valueB);
	}
	
	public double max() {
		double max = valueA[0];
		for (int i = 1; i < N; i++) {
			if (valueA[i] > max) {
				max = valueA[i];
			}
		}
		return max;
	}
	
	public Vector getPartialVector(int start, int end) {
		double[] valueB = new double[end - start + 1];
		for (int i = start, j = 0; i <= end; i++, j++) {
			valueB[j] = valueA[i];
		}
		return new Vector(valueB);		
	}
	
	public static Vector merge(Vector A, Vector B) {
		int N = A.getN() + B.getN();
		double[] valueA = A.getValue();
		double[] valueB = B.getValue();
		double[] valueC = new double[N];
		for (int i = 0; i < A.getN(); i++) {
			valueC[i] = valueA[i];
		}
		for (int i = A.getN(), j = 0; i < N; i++, j++) {
			valueC[i] = valueB[j];
		}
		return new Vector(valueC);
	}
}
