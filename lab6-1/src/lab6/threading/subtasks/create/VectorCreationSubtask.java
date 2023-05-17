// ./lab6/src/lab6/threading/subtasks/create/VectorCreationSubtask.java

package lab6.threading.subtasks.create;

import java.io.IOException;
import java.util.concurrent.RecursiveTask;

import lab6.data.vector.Vector;
import lab6.data.vector.VectorManager;

public class VectorCreationSubtask extends RecursiveTask<Vector> {

	private static final long serialVersionUID = 1L;
	
	private VectorManager vm;
	private int N;
	private String name;
	private String inPath;
	
	public VectorCreationSubtask(VectorManager vm, int N, String name, String inPath) {
		this.vm = vm;
		this.N = N;
		this.name = name;
		this.inPath = inPath;
	}
	
	@Override
	protected Vector compute() {
		try {
			return vm.getVector(name, N, inPath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
