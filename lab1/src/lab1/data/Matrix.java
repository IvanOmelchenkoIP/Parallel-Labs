package lab1.data;

public class Matrix {

	double[][] MA;
	
	public void multiplyByMatrix(double[][] MB) {
		int n = MA.length;
		
		double[][] MC = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double[] products = new double[n];
				for (int k = 0; k < n; k++) {
					products[k] = MA[i][k] * MB[k][j];
				}
				MC[i][j] = KahanSum.add(products);
			}
		}
		MA = MC;
	}
	
	public void addMatrix(double[][] MB) {
		int n = MA.length;
		
		double[][] MC = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				MC[i][j] = KahanSum.add(MA[i][j], MB[i][j]);
			}
		}
		MA = MC;
	}
	
	public void subsractMatrix(double[][] MB) {
		int n = MA.length;
		
		double[][] MC = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				MC[i][j] = KahanSum.add(MA[i][j], MB[i][j] * (-1));
			}
		}
		MA = MC;
	}
}
