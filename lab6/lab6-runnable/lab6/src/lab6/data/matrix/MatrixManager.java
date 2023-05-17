// ./lab6/src/lab6/data/matrix/MatrixManager.java

package lab6.data.matrix;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lab6.data.DataFinder;
import lab6.data.generators.DoubleArrayGenerator;
import lab6.fs.FileSystem;

public class MatrixManager {
	
	final private Lock accessLock;
	
	final private HashMap<String, Matrix> matrixes;
	
	final private int minVal;
	final private int maxVal;
	final private int minPrecision;
	final private int maxPrecision;
	
	final private FileSystem fs;
	
	final private DataFinder df;
	final private DoubleArrayGenerator dg;
	
	public MatrixManager(int minVal, int maxVal, int minPrecision, int maxPrecision, FileSystem fs) {
		this.accessLock = new ReentrantLock();

		this.matrixes = new HashMap<String, Matrix>();
		
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.minPrecision = minPrecision;
		this.maxPrecision = maxPrecision;
		
		this.fs = fs;
		
		this.df = new DataFinder();
		this.dg = new DoubleArrayGenerator();
	}
		
	private Matrix createNew(String name, int size, String inPath) throws IOException {
		Matrix MA = new Matrix(dg.generate2DDoubleArray(size, minVal, maxVal, minPrecision, maxPrecision));
		fs.write(inPath, name + "\n" + MA.toString());
		return MA;
	}
	
	private Matrix getFromFile(String name, int size, String inPath) throws IOException {
		String contents = fs.read(inPath).trim();
		if (!contents.contains(name + "\n")) {
			return null;
		}
		return Matrix.fromString(df.findMatrix(contents, name, size));
	}

	public Matrix getMatrix(String name, int size, String inPath) throws IOException  {
		accessLock.lock();
		try {			
			Matrix MA = matrixes.get(name);
			if (MA != null) {
				return MA;
			}
			if (fs.exists(inPath)) {
				MA = getFromFile(name, size, inPath);
			}
			if (MA == null) {
				MA = createNew(name, size, inPath);
			}
			matrixes.put(name, MA);
			return MA;
		} finally {
			accessLock.unlock();
		}
	}
}
