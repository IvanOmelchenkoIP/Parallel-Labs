// ./lab2/src/lab2/data/vector/VectorManager.java

package lab2.data.vector;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import lab2.data.DataFinder;
import lab2.data.generators.DoubleArrayGenerator;
import lab2.fs.MemFileSystem;

public class VectorManager {
	
	final private Semaphore accessSemaphore;
	
	final private HashMap<String, Vector> vectors = new HashMap<String, Vector>();

	final private String inPath;
	final private String outPath;
	final private MemFileSystem fs;
	final private DataFinder df;
	final private DoubleArrayGenerator dg;
	
	public VectorManager(String inPath, String outPath) {
		this.inPath = inPath;
		this.outPath = outPath;
		this.fs = new MemFileSystem();
		this.df = new DataFinder();
		this.dg = new DoubleArrayGenerator();
		this.accessSemaphore = new Semaphore(1);
	}
	
	private Vector createNew(String name, int size) throws IOException {
		Vector A = new Vector(size, dg.generateDoubleArray(size, 0, 100, 3, 15));
		writeVector(name, A);
		return A;
	}
	
	private Vector readFromFile(String name, int size) throws IOException {
		String contents = fs.read(inPath).trim();
		if (!contents.contains(name + "\n")) {
			return null;
		}
		
		return Vector.fromString(df.findVector(contents, name));
	}

	public Vector getVector(String name, int size) throws IOException, InterruptedException {
		accessSemaphore.acquire();
		
		Vector A = vectors.get(name);
		if (A != null) {
			return A;
		}
		if (fs.exists(inPath)) {
			A = readFromFile(name, size);
		}
		A = createNew(name, size);
		vectors.put(name, A);
		
		accessSemaphore.release();
		
		return A;
	}
	
	public void writeVector(String name, Vector vector) throws IOException {
		fs.write(outPath, name + "\n" + vector.toString());
	}
}
