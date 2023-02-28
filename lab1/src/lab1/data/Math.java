package lab1.data;

import java.util.ArrayList;
import java.util.List;

public class Math {
	public static int[][] multiplyMatrixes(int n, int[][] MA, int[][] MB) {
		int[][] MC = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++)
					MC[i][j] += MA[i][k] * MB[k][j];
			}
		}
		return MC;
	}
	
	public static double max(double[] A) {
		double max = A[0];
		for (int i = 0; i < A.length; i++) {
			if (A[i] > max) {
				max = A[i];
			}
		}
		return max;
	}
	
	public static double
}
