// ./lab6/src/lab6/threading/subtasks/math/MatrixProductfSubtask.java

package lab6.threading.subtasks.math;

import java.util.concurrent.ForkJoinTask;

import lab6.data.matrix.Matrix;
import lab6.threading.subtasks.DivisibleSubtask;

public class MatrixProductSubtask extends DivisibleSubtask<Matrix> {

	private static final long serialVersionUID = 1L;

	private Matrix MA;
	private Matrix MB;
	
	public MatrixProductSubtask(Matrix MA, Matrix MB) {
		this.MA = MA;
		this.MB = MB;
	}
	
	@Override
	protected Matrix compute() {
		int M = MB.getM();
		return M > DIVIDE_N ? divideCount(M) : count(); 
	}
	
	@Override
	protected Matrix count() {
		return MA.getMatrixMultiplyProduct(MB);
	}
	
	@Override
	protected Matrix divideCount(int M) {
		int H = M / 2;
		ForkJoinTask<Matrix> sub1 = new MatrixProductSubtask(MA, MB.getPartialMatrixByM(0, H - 1)).fork();
		ForkJoinTask<Matrix> sub2 = new MatrixProductSubtask(MA, MB.getPartialMatrixByM(H, M - 1)).fork();		
		return Matrix.mergeByM(sub1.join(), sub2.join());
	}
}
