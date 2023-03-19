// ./lab2/src/lab2/data/matrix/MatrixIO.java

package lab2.data.matrix;

import java.io.IOException;

import lab2.data.DataFinder;
import lab2.data.generators.DoubleArrayGenerator;
import lab2.fs.MemFileSystem;

public class MatrixIO {
	
	final private String inPath;
	final private String outPath;
	final private MemFileSystem fs;
	final private DataFinder df;
	final private DoubleArrayGenerator dg;
	
	public MatrixIO(String inPath, String outPath) {
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

	public Matrix generateOrRead(String name, int size) throws IOException {
		if (!fs.exists(inPath)) {
			return createNew(name, size);
		}
		
		String contents = fs.read(inPath).trim();
		if (!contents.contains(name + "\n")) {
			return createNew(name, size);
		}
		
		return Matrix.fromString(df.findMatrix(contents, name, size));
	}
	
	public void writeMatrix(String name, Matrix matrix) throws IOException {
		fs.write(outPath, name + "\n" + matrix.toString());
	}
}
