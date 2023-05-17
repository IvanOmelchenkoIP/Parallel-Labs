// ./lab6/src/lab6/threading/consumer/OutConsumer.java

package lab6.threading.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RecursiveAction;

import lab6.fs.FileSystem;
import lab6.threading.subtasks.out.ConsumerConsoleWriterSubtask;
import lab6.threading.subtasks.out.ConsumerFileWriterSubtask;

public class OutConsumer extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	
	private int threads;
	private BlockingQueue<?> queue;
	private FileSystem fs;
	private String filename;
	
	public OutConsumer(int threads, BlockingQueue<?> queue, FileSystem fs, String filename) {
		this.threads = threads;
		this.queue = queue;
		this.fs = fs;
		this.filename = filename;
	}
	
	@Override
	public void compute() {
		int threadsProcessed = 0;
		while(threadsProcessed < threads) {
			String result;
			try {
				result = (String) queue.take();
			} catch (InterruptedException ex) {
				System.out.println(ex);
				return;
			}
			if (result != null) {
				writeResult(result);
				threadsProcessed += 1;
			}
		}
	}
	
	private void writeResult(String result) {
		RecursiveAction consoleSub = new ConsumerConsoleWriterSubtask(result);
		RecursiveAction fileSub = new ConsumerFileWriterSubtask(fs, result, filename);
		consoleSub.fork();
		fileSub.fork();
		consoleSub.join();
		fileSub.join();
	}
}

