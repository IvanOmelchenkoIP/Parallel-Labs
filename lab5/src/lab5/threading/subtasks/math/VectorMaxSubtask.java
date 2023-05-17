// ./lab5/src/lab5/threading/subtasks/math/VectorMaxSubtask.java

package lab5.threading.subtasks.math;

import java.util.concurrent.ForkJoinTask;

import lab5.data.vector.Vector;
import lab5.threading.subtasks.DivisibleSubtask;

public class VectorMaxSubtask extends DivisibleSubtask<Double> {

	private static final long serialVersionUID = 1L;

	private Vector A;

	public VectorMaxSubtask(Vector A) {
		this.A = A;
	}

	@Override
	protected Double compute() {
		int N = A.getN();
		return N > DIVIDE_N ? divideCount(N) : count();
	}
	
	@Override
	protected Double count() {
		return A.max();
	}
	
	@Override
	protected Double divideCount(int N) {		
		int H = N / 2;
		ForkJoinTask<Double> sub1 = new VectorMaxSubtask(A.getPartialVector(0, H - 1)).fork();
		ForkJoinTask<Double> sub2 = new VectorMaxSubtask(A.getPartialVector(H, N - 1)).fork();
		return Math.max(sub1.join(), sub2.join());
	}
}
