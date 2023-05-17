// ./lab5/src/lab5/threading/subtasks/strings/MatrixToStringSubtask.java

package lab5.threading.subtasks.strings;

import java.util.concurrent.ForkJoinTask;

import lab5.data.matrix.Matrix;
import lab5.threading.subtasks.DivisibleSubtask;

public class MatrixToStringSubtask extends DivisibleSubtask<String> {

	private static final long serialVersionUID = 1L;
	
	public Matrix MA;
	
	public MatrixToStringSubtask(Matrix MA) {
		this.MA = MA;
	}
	
	@Override
	protected String compute() {
		int N = MA.getN();
		return N > DIVIDE_N ? divideCount(N) : count();
	}

	@Override
	protected String count() {
		return MA.toString();
	}
	
	@Override
	protected String divideCount(int N) {
		int H = N / 2;
		ForkJoinTask<String> sub1 = new MatrixToStringSubtask(MA.getPartialMatrixByN(0, H - 1)).fork();
		ForkJoinTask<String> sub2 = new MatrixToStringSubtask(MA.getPartialMatrixByN(H, N - 1)).fork();
		return sub1.join() + sub2.join();
	}
}
