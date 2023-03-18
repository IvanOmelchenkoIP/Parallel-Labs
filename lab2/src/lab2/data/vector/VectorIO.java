// ./lab2/src/lab2/data/vector/VectorIO.java

package lab2.data.vector;

import java.io.IOException;

import lab2.data.DataFinder;
import lab2.fs.MemFileSystem;

public class VectorIO {

	final private String inPath;
	final private String outPath;
	final MemFileSystem fs;
	final DataFinder df;
	
	public VectorIO(String inPath, String outPath) {
		this.inPath = inPath;
		this.outPath = outPath;
		this.fs = new MemFileSystem();
		this.df = new DataFinder();
	}
	
	private Vector createNew(String name, int size) throws IOException {
		Vector A = Vector.generateRandom(size);
		writeVector(name, A);
		return A;
	}

	public Vector generateOrRead(String name, int size) throws IOException {
		if (!fs.exists(inPath)) {
			return createNew(name, size);
		}
		
		String contents = fs.read(inPath).trim();
		if (!contents.contains(name + "\n")) {
			return createNew(name, size);
		}
		
		return Vector.generateFromString(df.findVector(contents, name));
	}
	
	public void writeVector(String name, Vector vector) throws IOException {
		fs.write(outPath, name + "\n" + vector.toString());
	}
}
