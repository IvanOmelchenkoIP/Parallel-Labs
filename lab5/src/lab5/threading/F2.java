// ./lab5/src/lab5/threading/F2.java
// F2 by variant: A = D * MT - max(D) * B

package lab5.threading;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.Lock;

import lab5.data.matrix.Matrix;
import lab5.data.matrix.MatrixManager;
import lab5.data.vector.Vector;
import lab5.data.vector.VectorManager;
import lab5.fs.FileSystem;
import lab5.threading.out.ConsoleOutputSubtask;
import lab5.threading.out.FileOutputSubtask;
import lab5.threading.subtasks.create.MatrixCreationSubtask;
import lab5.threading.subtasks.create.VectorCreationSubtask;
import lab5.threading.subtasks.math.VectorDiffSubtask;
import lab5.threading.subtasks.math.VectorMatrixProductSubtask;
import lab5.threading.subtasks.math.VectorMaxSubtask;
import lab5.threading.subtasks.math.VectorScalarProductSubtask;
import lab5.threading.subtasks.strings.VectorToStringSubtask;

public class F2 extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	
	final private int N;
	
	final private MatrixManager mm;
	final private VectorManager vm;
	final private FileSystem fs;
	
	final private String inPath;
	final private String outPath;
	
	private final Lock resLock;

	public F2(int N, MatrixManager mm, VectorManager vm, FileSystem fs, String inPath, String outPath, Lock resLock) {
		this.N = N;
			
		this.mm = mm;
		this.vm = vm;
		this.fs = fs;
		
		this.inPath = inPath;
		this.outPath = outPath;
		
		this.resLock = resLock;
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
		
		String result = "A:\n" + new VectorToStringSubtask(A).fork().join();
		try {
			resLock.lock();
			RecursiveAction consoleSub = new ConsoleOutputSubtask(result);
			RecursiveAction fileSub = new FileOutputSubtask(fs, result, outPath);
			consoleSub.fork();
			fileSub.fork();
			consoleSub.join();
			fileSub.join();
		} finally {
			resLock.unlock();
		}
	}
}