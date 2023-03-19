// ./lab2/src/lab2/data/matrix/MatrixManager.java

package lab2.data.matrix;

import java.io.IOException;
import java.util.HashMap;

import lab2.data.DataFinder;
import lab2.data.generators.DoubleArrayGenerator;
import lab2.fs.MemFileSystem;

public class MatrixManager {
	
	final private HashMap<String, Matrix> matrixes = new HashMap<String, Matrix>();
	
	final private String inPath;
	final private String outPath;
	final private MemFileSystem fs;
	final private DataFinder df;
	final private DoubleArrayGenerator dg;
	
	public MatrixManager(String inPath, String outPath) {
		this.inPath = inPath;
		this.outPath = outPath;
		this.fs = new MemFileSystem();
		this.df = new DataFinder();
		this.dg = new DoubleArrayGenerator();
	}
		
	private Matrix createNew(String name, int size) throws IOException {
		Matrix MA = new Matrix(size, dg.generate2DDoubleArray(size, 0, 100, 3, 15));
		writeMatrix(name, MA);
		return MA;
	}
	
	private Matrix getFromFile(String name, int size) throws IOException {
		String contents = fs.read(inPath).trim();
		if (!contents.contains(name + "\n")) {
			return null;
		}
		return Matrix.fromString(df.findMatrix(contents, name, size));
	}

	public Matrix getMatrix(String name, int size) throws IOException {
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
	}
	
	public void writeMatrix(String name, Matrix matrix) throws IOException {
		fs.write(outPath, name + "\n" + matrix.toString());
	}
}
