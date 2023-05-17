// ./lab6/src/lab6/threading/subtasks/out/ConsumerFileWriterSubtask.java

package lab6.threading.subtasks.out;

import java.io.IOException;
import java.util.concurrent.RecursiveAction;

import lab6.fs.FileSystem;

public class ConsumerFileWriterSubtask extends RecursiveAction {
	
	private static final long serialVersionUID = 1L;
	
	private FileSystem fs;
	private String result;
	private String filename;
	
	public ConsumerFileWriterSubtask(FileSystem fs, String result, String filename) {
		this.fs = fs;
		this.result = result;
		this.filename = filename;
	}
	
	@Override
	protected void compute() {
		try {
			fs.write(filename, result);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}		
	}
}
