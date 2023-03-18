// ./lab2/src/lab2/data/matrix/MatrixIO.java

package lab2.data.matrix;

import java.io.IOException;

import lab2.data.DataFinder;
import lab2.fs.MemFileSystem;

public class MatrixIO {
	
	final private String inPath;
	final private String outPath;
	final MemFileSystem fs;
	final DataFinder df;
	
	public MatrixIO(String inPath, String outPath) {
		this.inPath = inPath;
		this.outPath = outPath;
		this.fs = new MemFileSystem();
		this.df = new DataFinder();
	}
		
	private Matrix createNew(String name, int size) throws IOException {
		Matrix MA = Matrix.generateRandom(size);
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
		
		return Matrix.generateFromString(df.findMatrix(contents, name, size));
	}
	
	public void writeMatrix(String name, Matrix matrix) throws IOException {
		fs.write(outPath, name + "\n" + matrix.toString());
	}
}
