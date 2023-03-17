// ./lab2/src/lab2/data/DoublePrecisionGenerator.java

package lab2.data;

import java.util.concurrent.ThreadLocalRandom;

public class DoublePrecisionGenerator {

	public static double generateDoubleWithPrecison(int min, int max, int precision) {
		double num = ThreadLocalRandom.current().nextDouble(min, max);
		long order = Long.parseLong(("1" + "0".repeat(precision)).trim());
		return ((double)Math.round(num * order) / (double)order);
	}
}