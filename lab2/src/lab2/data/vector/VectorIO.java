// ./lab2/src/lab2/data/vector/VectorIO.java

package lab2.data.vector;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import lab2.fs.FileSystem;

public class VectorIO {

	final private String inPath;
	final private String outPath;
	final FileSystem fs;
	
	public VectorIO(String inPath, String outPath) {
		this.inPath = inPath;
		this.outPath = outPath;
		this.fs = new FileSystem();
	}
	
	private Vector createNew(String name, int size) throws IOException {
		Vector A = Vector.generateRandom(size);
		fs.write(inPath, name + "\n" + A.toString());
		return A;
	}

	public Vector generateOrRead(String name, int size) throws IOException {
		if (!new File(inPath).exists()) {
			return createNew(name, size);
		}
		
		String contents = fs.read(inPath).trim();
		if (!contents.contains(name + "\n")) {
			return createNew(name, size);
		}
		
		String[] lines = contents.split("\n");
		int index = Arrays.asList(lines).indexOf(name) + 1;
		String matrix = String.join("\n", Arrays.copyOfRange(lines, index, index + 1));
		
		return Vector.generateFromString(matrix);
	}
	
	public void writeResult(String name, String result) throws IOException {
		fs.write(outPath, name + "\n" + result);
	}
}
