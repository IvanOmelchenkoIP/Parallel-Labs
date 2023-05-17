// ./lab3/src/lab3/data/generators/DoublePrecisionGenerator.java

package lab3.data.generators;

import java.util.concurrent.ThreadLocalRandom;

public class DoublePrecisionGenerator {

	public double generateDoubleWithPrecison(int min, int max, int precision) {
		double num = ThreadLocalRandom.current().nextDouble(min, max);
		long order = Long.parseLong(("1" + "0".repeat(precision)).trim());
		return ((double)Math.round(num * order) / (double)order);
	}
}
