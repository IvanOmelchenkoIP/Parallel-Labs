// ./lab6/src/lab6/threading/subtasks/math/VectorMatrixProductSubtask.java

package lab6.threading.subtasks.math;

import java.util.concurrent.ForkJoinTask;

import lab6.data.matrix.Matrix;
import lab6.data.vector.Vector;
import lab6.threading.subtasks.DivisibleSubtask;

public class VectorMatrixProductSubtask extends DivisibleSubtask<Vector> {

	private static final long serialVersionUID = 1L;

	private Vector A;
	private Matrix MA;

	public VectorMatrixProductSubtask(Vector A, Matrix MA) {
		this.A = A;
		this.MA = MA;
	}

	@Override
	protected Vector compute() {
		int N = MA.getN();
		return N > DIVIDE_N ? divideCount(N) : count();
	}
	
	@Override
	protected Vector count() {
		return A.getMatrixMultiplyProduct(MA);
	}
	
	@Override
	protected Vector divideCount(int N) {		
		int H = N / 2;
		ForkJoinTask<Vector> sub1 = new VectorMatrixProductSubtask(A, MA.getPartialMatrixByN(0, H - 1)).fork();
		ForkJoinTask<Vector> sub2 = new VectorMatrixProductSubtask(A, MA.getPartialMatrixByN(H, N - 1)).fork();
		return Vector.merge(sub1.join(), sub2.join());
	}
}
