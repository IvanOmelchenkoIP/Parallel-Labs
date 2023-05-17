// ./lab5/src/lab5/threading/out/FileOutputSubtask.java

package lab5.threading.out;

import java.io.IOException;
import java.util.concurrent.RecursiveAction;

import lab5.fs.FileSystem;

public class FileOutputSubtask extends RecursiveAction {
	
	private static final long serialVersionUID = 1L;
	
	private FileSystem fs;
	private String result;
	private String filename;
	
	public FileOutputSubtask(FileSystem fs, String result, String filename) {
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
