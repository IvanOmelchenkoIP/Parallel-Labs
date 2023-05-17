// ./lab6/src/lab6/threading/subtasks/DivisibleSubtask.java

package lab6.threading.subtasks;

import java.util.concurrent.RecursiveTask;

public abstract class DivisibleSubtask<T> extends RecursiveTask<T> {

	private static final long serialVersionUID = 1L;
	
	protected static int DIVIDE_N;

	public static void setDivideN(int userN) {
		DIVIDE_N = userN;
	}

	protected abstract T count();

	protected abstract T divideCount(int N);
}
