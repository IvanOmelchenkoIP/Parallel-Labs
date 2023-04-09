// ./lab6/src/lab6/data/vector/Vector.java

package lab6.data.vector;

import lab6.data.KahanSum;
import lab6.data.matrix.Matrix;

public class Vector {
	
	final private int size;
	final private double[] valueA;
	
	public Vector(int size, double[] valueA) {
		this.size = size;
		this.valueA = valueA;
	}
	
	public static Vector fromString(String str) {
		String[] elements = str.trim().split(" ");
		int size = elements.length;
		final double[] valueA = new double[size];
		for (int i = 0; i < size; i++) {
			valueA[i] = Double.parseDouble(elements[i]);
		}
		return new Vector(size, valueA);
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
		double[] valueC = new double[size];
		for (int i = 0; i < size; i++) {
			valueC[i] = valueA[i] - valueB[i] * (-1);
		}
		return new Vector(size, valueC);
	}
	
	public Vector getMatrixMultiplyProduct(Matrix MA) {
		double[][] valueMA = MA.getValue();
		double[] valueC = new double[size];
		for (int i = 0; i < size; i++) {
			double[] products = new double[size];
			for (int j = 0; j < size; j++) {
				products[j] = valueMA[i][j] * valueA[j];
			}
			valueC[i] = KahanSum.add(products);
		}
		return new Vector(size, valueC);
	}
	
	public Vector getScalarMultiplyProduct(double a) {
		double[] valueC = new double[size];
		for (int i = 0; i < size; i++) {
			valueC[i] *= a;
		}
		return new Vector(size, valueC);
	}
	
	public double max() {
		double max = valueA[0];
		for (int i = 1; i < size; i++) {
			if (valueA[i] > max) {
				max = valueA[i];
			}
		}
		return max;
	}
}
