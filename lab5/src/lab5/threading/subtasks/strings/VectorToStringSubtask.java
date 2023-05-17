// ./lab5/src/lab5/threading/subtasks/strings/VectorToStringSubtask.java

package lab5.threading.subtasks.strings;

import java.util.concurrent.ForkJoinTask;

import lab5.data.vector.Vector;
import lab5.threading.subtasks.DivisibleSubtask;

public class VectorToStringSubtask extends DivisibleSubtask<String> {

	private static final long serialVersionUID = 1L;
	
	private Vector A;
	
	public VectorToStringSubtask(Vector A) {
		this.A = A;
	}

	@Override
	protected String compute() {
		int N = A.getN();
		return N > DIVIDE_N ? divideCount(N) : count();
	}

	@Override
	protected String count() {
		return A.toString();
	}
	
	@Override
	protected String divideCount(int N) {
		int H = N / 2;
		ForkJoinTask<String> sub1 = new VectorToStringSubtask(A.getPartialVector(0, H - 1)).fork();
		ForkJoinTask<String> sub2 = new VectorToStringSubtask(A.getPartialVector(H, N - 1)).fork();
		return sub1.join().trim().replace("\n", " ") + sub2.join();
	}
}
