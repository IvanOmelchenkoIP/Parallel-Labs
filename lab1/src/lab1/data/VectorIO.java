// ./lab1/src/lab1/data/VectorIO.java

package lab1.data;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import lab1.fs.FileSystem;

public class VectorIO {

	final private String INPUT_PATH = "../input/input.txt";
	final private String OUTPUT_PATH = "../output/output1-corrected.txt";
	
	final FileSystem fs = new FileSystem();
	
	private Vector createNew(String name, int size) throws IOException {
		Vector A = Vector.generateRandom(size);
		fs.write(INPUT_PATH, name + "\n" + A.toString());
		return A;
	}

	public Vector generateOrRead(String name, int size) throws IOException {
		if (!new File(INPUT_PATH).exists()) {
			return createNew(name, size);
		}
		
		String contents = fs.read(INPUT_PATH).trim();
		if (!contents.contains(name + "\n")) {
			return createNew(name, size);
		}
		
		String[] lines = contents.split("\n");
		int index = Arrays.asList(lines).indexOf(name) + 1;
		String matrix = String.join("\n", Arrays.copyOfRange(lines, index, index + 1));
		
		return Vector.generateFromString(matrix);
	}
	
	public void writeResult(String name, String result) throws IOException {
		fs.write(OUTPUT_PATH, name + "\n" + result);
	}
}
