// ./lab6/src/lab6/threading/consumer/OutConsumer.java

package lab6.threading.consumer;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import lab6.fs.FileSystem;

public class OutConsumer implements Runnable {

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
	public void run() {
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
		System.out.println(result);
		try {
			fs.write(filename, result);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
