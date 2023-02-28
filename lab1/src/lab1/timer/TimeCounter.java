package lab1.timer;

import java.util.function.Consumer;

public class TimeCounter<T> {
	
	public long countTime(Consumer<T> method, T arg) {
		long start = System.currentTimeMillis();
		method.accept(arg);
		return System.currentTimeMillis() - start;
	}
}
