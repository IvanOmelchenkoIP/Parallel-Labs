// ./lab5/src/lab5/threading/subtasks/math/VectorDiffSubtask.java

package lab5.threading.subtasks.math;

import java.util.concurrent.ForkJoinTask;

import lab5.data.vector.Vector;
import lab5.threading.subtasks.DivisibleSubtask;

public class VectorDiffSubtask extends DivisibleSubtask<Vector> {

	private static final long serialVersionUID = 1L;

	private Vector A;
	private Vector B;

	public VectorDiffSubtask(Vector A, Vector B) {
		this.A = A;
		this.B = B;
	}

	@Override
	protected Vector compute() {
		int N = A.getN();
		return N > DIVIDE_N ? divideCount(N) : count();
	}
	
	@Override
	protected Vector count() {
		return A.getVectorDifference(B);
	}
	
	@Override
	protected Vector divideCount(int N) {		
		int H = N / 2;
		ForkJoinTask<Vector> sub1 = new VectorDiffSubtask(A.getPartialVector(0, H - 1), B.getPartialVector(0, H - 1)).fork();
		ForkJoinTask<Vector> sub2 = new VectorDiffSubtask(A.getPartialVector(H, N - 1), B.getPartialVector(H, N - 1)).fork();
		return Vector.merge(sub1.join(), sub2.join());
	}
}
