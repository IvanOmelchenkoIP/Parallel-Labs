// ./lab5/src/lab5/threading/subtasks/math/VectorScalarProductSubtask.java

package lab5.threading.subtasks.math;

import java.util.concurrent.ForkJoinTask;

import lab5.data.vector.Vector;
import lab5.threading.subtasks.DivisibleSubtask;

public class VectorScalarProductSubtask extends DivisibleSubtask<Vector> {

	private static final long serialVersionUID = 1L;

	private Vector A;
	private double a;

	public VectorScalarProductSubtask(Vector A, double a) {
		this.A = A;
		this.a = a;
	}

	@Override
	protected Vector compute() {
		int N = A.getN();
		return N > DIVIDE_N ? divideCount(N) : count();
	}
	
	@Override
	protected Vector count() {
		return A.getScalarMultiplyProduct(a);
	}
	
	@Override
	protected Vector divideCount(int N) {		
		int H = N / 2;
		ForkJoinTask<Vector> sub1 = new VectorScalarProductSubtask(A.getPartialVector(0, H - 1), a).fork();
		ForkJoinTask<Vector> sub2 = new VectorScalarProductSubtask(A.getPartialVector(H, N - 1), a).fork();
		return Vector.merge(sub1.join(), sub2.join());
	}
}
