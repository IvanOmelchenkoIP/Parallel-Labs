// ./lab5/src/lab5/threading/subtasks/create/MatrixCreationSubtask.java

package lab5.threading.subtasks.create;

import java.io.IOException;
import java.util.concurrent.RecursiveTask;

import lab5.data.matrix.Matrix;
import lab5.data.matrix.MatrixManager;

public class MatrixCreationSubtask extends RecursiveTask<Matrix> {

	private static final long serialVersionUID = 1L;

	private MatrixManager mm;
	private int N;
	private String name;
	private String outPath;
	
	public MatrixCreationSubtask(MatrixManager vm, int N, String name, String outPath) {
		this.mm = vm;
		this.N = N;
		this.name = name;
		this.outPath = outPath;
	}
	
	@Override
	protected Matrix compute() {
		try {
			return mm.getMatrix(name, N, outPath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}