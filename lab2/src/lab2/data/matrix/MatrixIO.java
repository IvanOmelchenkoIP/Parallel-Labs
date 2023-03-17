// ./lab2/src/lab2/data/matrix/MatrixIO.java

package lab2.data.matrix;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import lab2.fs.FileSystem;

public class MatrixIO {
	
	final private String inPath;
	final private String outPath;
	final FileSystem fs;
	
	public MatrixIO(String inPath, String outPath) {
		this.inPath = inPath;
		this.outPath = outPath;
		this.fs = new FileSystem();
	}
		
	private Matrix createNew(String name, int size) throws IOException {
		Matrix MA = Matrix.generateRandom(size);
		fs.write(inPath, name + "\n" + MA.toString());
		return MA;
	}

	public Matrix generateOrRead(String name, int size) throws IOException {
		if (!new File(inPath).exists()) {
			return createNew(name, size);
		}
		
		String contents = fs.read(inPath).trim();
		if (!contents.contains(name + "\n")) {
			return createNew(name, size);
		}
		
		String[] lines = contents.split("\n");
		int index = Arrays.asList(lines).indexOf(name) + 1;
		String matrix = String.join("\n", Arrays.copyOfRange(lines, index, index + size));
		
		return Matrix.generateFromString(matrix);
	}
	
	public void writeResult(String name, String result) throws IOException {
		fs.write(outPath, name + "\n" + result);
	}
}
