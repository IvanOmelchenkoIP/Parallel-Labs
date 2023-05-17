// ./lab6/src/lab6/data/vector/Vector.java

package lab6.data.vector;

import lab6.data.KahanSum;
import lab6.data.matrix.Matrix;

public class Vector {
	
	final private int N;
	final private double[] valueA;
	
	public Vector(double[] valueA) {
		this.valueA = valueA;
		this.N = valueA.length;
	}
	
	public static Vector fromString(String str) {
		String[] elements = str.trim().split(" ");
		int size = elements.length;
		final double[] valueA = new double[size];
		for (int i = 0; i < size; i++) {
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
		double[] valueB = new double[N];
		for (int i = 0; i < N; i++) {
			double[] products = new double[N];
			for (int j = 0; j < N; j++) {
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
}
