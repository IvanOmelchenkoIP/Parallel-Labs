// ./lab5/src/lab5/threading/F1.java
// F1 by variant: MА = MD * MT + MZ - ME * MM   

package lab5.threading;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.Lock;

import lab5.data.matrix.Matrix;
import lab5.data.matrix.MatrixManager;
import lab5.fs.FileSystem;
import lab5.threading.out.ConsoleOutputSubtask;
import lab5.threading.out.FileOutputSubtask;
import lab5.threading.subtasks.create.MatrixCreationSubtask;
import lab5.threading.subtasks.math.MatrixDiffSubtask;
import lab5.threading.subtasks.math.MatrixProductSubtask;
import lab5.threading.subtasks.math.MatrixSumSubtask;
import lab5.threading.subtasks.strings.MatrixToStringSubtask;

public class F1 extends RecursiveAction {

	private static final long serialVersionUID = 1L;

	final private int N;
	
	final private MatrixManager mm;
	final private FileSystem fs;
	
	final private String inPath;
	final private String outPath;
	
	private final Lock resLock;

	public F1(int N, MatrixManager mm, FileSystem fs, String inPath, String outPath, Lock resLock) {
		this.N = N;
		
		this.mm = mm;
		this.fs = fs;
		
		this.inPath = inPath;
		this.outPath = outPath;
		
		this.resLock = resLock;
	}

	@Override
	protected void compute() {

		ForkJoinTask<Matrix> MDsub = new MatrixCreationSubtask(mm, N, "MD", inPath).fork();
		ForkJoinTask<Matrix> MTsub = new MatrixCreationSubtask(mm, N, "MT", inPath).fork();
		ForkJoinTask<Matrix> MZsub = new MatrixCreationSubtask(mm, N, "MZ", inPath).fork();
		ForkJoinTask<Matrix> MEsub = new MatrixCreationSubtask(mm, N, "ME", inPath).fork();
		ForkJoinTask<Matrix> MMsub = new MatrixCreationSubtask(mm, N, "MM", inPath).fork();

		ForkJoinTask<Matrix> MDxMTsub = new MatrixProductSubtask(MDsub.join(), MTsub.join()).fork();
		ForkJoinTask<Matrix> MExMMsub = new MatrixProductSubtask(MEsub.join(), MMsub.join()).fork();
		ForkJoinTask<Matrix> sumSub = new MatrixSumSubtask(MDxMTsub.join(), MZsub.join()).fork();
		Matrix MA = new MatrixDiffSubtask(sumSub.join(), MExMMsub.join()).fork().join();
		
		String result = "MA:\n" + new MatrixToStringSubtask(MA).fork().join();
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