// ./lab1/src/lab1/data/DoublePrecisionGenerator.java

package lab1.data;

import java.util.concurrent.ThreadLocalRandom;

class DoublePrecisionGenerator {

	public static double generateDoubleWithPrecison(int min, int max, int precision) {
		double num = ThreadLocalRandom.current().nextDouble(min, max);
		long order = Long.parseLong(("1" + "0".repeat(precision)).trim());
		return ((double)Math.round(num * order) / (double)order);
	}
}
