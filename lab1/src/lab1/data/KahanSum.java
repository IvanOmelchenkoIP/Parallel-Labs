package lab1.data;

public class KahanSum {

	public static double add(double... nums) {
		double sum = 0;
		double err = 0;
		for (double num : nums) {
			double y = num - err;
			double t = sum + y;
			err = (t - sum) - y;
			sum = t;
		}
		return sum;
	}
}
