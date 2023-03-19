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
	
	final private HashMap<String, Vector> vectors;
	
	final private int minVal;
	final private int maxVal;
	final private int minPrecision;
	final private int maxPrecision;

	final private String inPath;
	final private String outPath;
	
	final private MemFileSystem fs;
	final private DataFinder df;
	final private DoubleArrayGenerator dg;
	
	public VectorManager(int minVal, int maxVal, int minPrecision, int maxPrecision, String inPath, String outPath) {		
		this.accessSemaphore = new Semaphore(1);
		
		this.vectors = new HashMap<String, Vector>();
		
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.minPrecision = minPrecision;
		this.maxPrecision = maxPrecision;
		
		this.inPath = inPath;
		this.outPath = outPath;
		
		this.fs = new MemFileSystem();
		this.df = new DataFinder();
		this.dg = new DoubleArrayGenerator();
	}
	
	private Vector createNew(String name, int size) throws IOException {
		Vector A = new Vector(size, dg.generateDoubleArray(size, minVal, maxVal, minPrecision, maxPrecision));
		writeToFile(inPath, name, A);
		return A;
	}
	
	private Vector readFromFile(String name, int size) throws IOException {
		String contents = fs.read(inPath).trim();
		if (!contents.contains(name + "\n")) {
			return null;
		}
		return Vector.fromString(df.findVector(contents, name));
	}

	public Vector getVector(String name, int size) throws InterruptedException, IOException  {
		accessSemaphore.acquire();
		
		Vector A = vectors.get(name);
		if (A != null) {
			return A;
		}
		if (fs.exists(inPath)) {
			A = readFromFile(name, size);
		}
		if (A == null) {
			A = createNew(name, size);
		}
		vectors.put(name, A);
		
		accessSemaphore.release();
		
		return A;
	}
	
	public void writeToFile(String filepath, String name, Vector vector) throws IOException {
		fs.write(filepath, name + "\n" + vector.toString());
	}
	
	public String getOutputPath() {
		return outPath;
	}
}
