// ./lab6/src/lab6/threading/F2.java
// F2 by variant: A = D * MT - max(D) * B

package lab6.threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import lab6.data.matrix.Matrix;
import lab6.data.matrix.MatrixManager;
import lab6.data.vector.Vector;
import lab6.data.vector.VectorManager;
import lab6.threading.subtasks.create.MatrixCreationSubtask;
import lab6.threading.subtasks.create.VectorCreationSubtask;
import lab6.threading.subtasks.math.VectorDiffSubtask;
import lab6.threading.subtasks.math.VectorMatrixProductSubtask;
import lab6.threading.subtasks.math.VectorMaxSubtask;
import lab6.threading.subtasks.math.VectorScalarProductSubtask;
import lab6.threading.subtasks.strings.VectorToStringSubtask;

public class F2 extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	
	final private int N;
	final private MatrixManager mm;
	final private VectorManager vm;
	final private String inPath;
	final private BlockingQueue<String> queue;
	
	public F2(int N, MatrixManager mm, VectorManager vm, String inPath, BlockingQueue<String> queue) {
		this.N = N;
		this.mm = mm;
		this.vm = vm;
		this.inPath = inPath;
		this.queue = queue;
	}

	@Override
	protected void compute() {
		ForkJoinTask<Vector> Dsub = new VectorCreationSubtask(vm, N, "D", inPath).fork();
		ForkJoinTask<Vector> Bsub = new VectorCreationSubtask(vm, N, "B", inPath).fork();
		ForkJoinTask<Matrix> MTsub = new MatrixCreationSubtask(mm, N, "MT", inPath).fork();		
	
		ForkJoinTask<Double> maxDSub = new VectorMaxSubtask(Dsub.join()).fork();
		ForkJoinTask<Vector> DxMTsub = new VectorMatrixProductSubtask(Dsub.join(), MTsub.join()).fork();
		ForkJoinTask<Vector> BmaxDsub = new VectorScalarProductSubtask(Bsub.join(), maxDSub.join()).fork();
		Vector A = new VectorDiffSubtask(DxMTsub.join(), BmaxDsub.join()).fork().join();
		
		String result = "A\n" + new VectorToStringSubtask(A).fork().join();
		queue.add(result);
	}
}