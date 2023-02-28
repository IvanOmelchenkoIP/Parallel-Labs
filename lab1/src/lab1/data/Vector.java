package lab1.data;

public class Vector {

	double[] A;
	
	public void substractVector(double B[]) {
		int n = A.length;
		
		double[] C = new double[n];
		for (int i = 0; i < n; i++) {
			C[i] = KahanSum.add(A[i], B[i] * (-1));
		}
		A = C;
	}
	
	public void multiplyByMatrix(double[][] MA) {
		int n = A.length;
		
		double[] B = new double[n];
		for (int i = 0; i < n; i++) {
			double[] products = new double[n];
			for (int j = 0; j < n; j++) {
				products[j] = MA[i][j] * A[j];
			}
			B[i] = KahanSum.add(products);
		}
		A = B;
	}
	
	public double max() {
		double max = A[0];
		for (int i = 0; i < A.length; i++) {
			if (A[i] > max) {
				max = A[i];
			}
		}
		return max;
	}
	
}
