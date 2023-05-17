// ./lab6/src/lab6/data/vector/VectorManager.java

package lab6.data.vector;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lab6.data.DataFinder;
import lab6.data.generators.DoubleArrayGenerator;
import lab6.fs.FileSystem;
import lab6.threading.subtasks.strings.VectorToStringSubtask;

public class VectorManager {
	
	final private Lock accessLock;
	
	final private HashMap<String, Vector> vectors;
	
	final private int minVal;
	final private int maxVal;
	final private int minPrecision;
	final private int maxPrecision;
	
	final private FileSystem fs;
	final private DataFinder df;
	final private DoubleArrayGenerator dg;
	
	public VectorManager(int minVal, int maxVal, int minPrecision, int maxPrecision, FileSystem fs) {		
		this.accessLock = new ReentrantLock();
		
		this.vectors = new HashMap<String, Vector>();
		
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.minPrecision = minPrecision;
		this.maxPrecision = maxPrecision;
		
		this.fs = fs;
		
		this.df = new DataFinder();
		this.dg = new DoubleArrayGenerator();
	}
	
	private Vector createNew(String name, int size, String inPath) throws IOException {
		Vector A = new Vector(dg.generateDoubleArray(size, minVal, maxVal, minPrecision, maxPrecision));
		fs.write(inPath, name + "\n" + new VectorToStringSubtask(A).fork().join());
		return A;
	}
	
	private Vector readFromFile(String name, int size, String inPath) throws IOException {
		String contents = fs.read(inPath).trim();
		if (!contents.contains(name + "\n")) {
			return null;
		}
		return Vector.fromString(df.findVector(contents, name));
	}

	public Vector getVector(String name, int size, String inPath) throws IOException  {
		accessLock.lock();
		try {			
			Vector A = vectors.get(name);
			if (A != null) {
				return A;
			}
			if (fs.exists(inPath)) {
				A = readFromFile(name, size, inPath);
			}
			if (A == null) {
				A = createNew(name, size, inPath);
			}
			vectors.put(name, A);
			return A;
		} finally {
			accessLock.unlock();
		}
	}
}
