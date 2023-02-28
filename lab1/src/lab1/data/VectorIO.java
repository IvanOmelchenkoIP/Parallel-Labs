package lab1.data;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import lab1.fs.FileSystem;

public class VectorIO {

	final private String INPUT_PATH = "input.txt";
	final FileSystem fs = new FileSystem();
	
	private Vector createNew(String name, int size) {
		Vector A = Vector.generateRandom(size);
		//fs.write(name + "\n" + MA.toString());
		return A;
	}

	public Vector generateOrRead(String name, int size) throws IOException {
		if (new File(INPUT_PATH).exists()) {
			return createNew(name, size);
		}
		
		String contents = fs.read(INPUT_PATH).trim();
		if (!contents.contains(name + "\n")) {
			return createNew(name, size);
		}
		
		String[] lines = contents.split("\n");
		int index = Arrays.asList(lines).indexOf(name) + 1;
		String matrix = String.join("\n", Arrays.copyOfRange(lines, index, index + size));
		
		return Vector.generateFromString(matrix);
	}
}
