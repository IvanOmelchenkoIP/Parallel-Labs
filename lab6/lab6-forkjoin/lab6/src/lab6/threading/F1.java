// ./lab6/src/lab6/threading/F1.java
// F1 by variant: MА = MD * MT + MZ - ME * MM   

package lab6.threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import lab6.data.matrix.Matrix;
import lab6.data.matrix.MatrixManager;
import lab6.threading.subtasks.create.MatrixCreationSubtask;
import lab6.threading.subtasks.math.MatrixDiffSubtask;
import lab6.threading.subtasks.math.MatrixProductSubtask;
import lab6.threading.subtasks.math.MatrixSumSubtask;
import lab6.threading.subtasks.strings.MatrixToStringSubtask;

public class F1 extends RecursiveAction {

	private static final long serialVersionUID = 1L;

	final private int N;
	final private MatrixManager mm;
	final private String inPath;
	final private BlockingQueue<String> queue;


	public F1(int N, MatrixManager mm, String inPath, BlockingQueue<String> queue) {
		this.N = N;
		this.mm = mm;	
		this.inPath = inPath;
		this.queue = queue;
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
		
		String result = "MA\n" + new MatrixToStringSubtask(MA).fork().join();
		queue.add(result);
	}
}