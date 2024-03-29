// ./lab3/src/lab3/data/matrix/MatrixManager.java

package lab3.data.matrix;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import lab3.data.DataFinder;
import lab3.data.generators.DoubleArrayGenerator;
import lab3.fs.MemFileSystem;

public class MatrixManager {
	
	final private Semaphore accessSemaphore;
	
	final private HashMap<String, Matrix> matrixes;
	
	final private int minVal;
	final private int maxVal;
	final private int minPrecision;
	final private int maxPrecision;
	
	final private String inPath;
	final private String outPath;
	final private MemFileSystem fs;
	
	final private DataFinder df;
	final private DoubleArrayGenerator dg;
	
	public MatrixManager(int minVal, int maxVal, int minPrecision, int maxPrecision, String inPath, String outPath) {
		this.accessSemaphore = new Semaphore(1);

		this.matrixes = new HashMap<String, Matrix>();
		
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
		
	private Matrix createNew(String name, int size) throws IOException {
		Matrix MA = new Matrix(size, dg.generate2DDoubleArray(size, minVal, maxVal, minPrecision, maxPrecision));
		writeToFile(inPath, name, MA);
		return MA;
	}
	
	private Matrix getFromFile(String name, int size) throws IOException {
		String contents = fs.read(inPath).trim();
		if (!contents.contains(name + "\n")) {
			return null;
		}
		return Matrix.fromString(df.findMatrix(contents, name, size));
	}

	public Matrix getMatrix(String name, int size) throws InterruptedException, IOException  {
		try {
			accessSemaphore.acquire();
			
			Matrix MA = matrixes.get(name);
			if (MA != null) {
				return MA;
			}
			if (fs.exists(inPath)) {
				MA = getFromFile(name, size);
			}
			if (MA == null) {
				MA = createNew(name, size);
			}
			matrixes.put(name, MA);
			return MA;
		} finally {
			accessSemaphore.release();
		}
	}
	
	public void writeToFile(String filepath, String name, Matrix matrix) throws IOException {
		fs.write(filepath, name + "\n" + matrix.toString());
	}
	
	public String getOutPath() {
		return outPath;
	}
}
