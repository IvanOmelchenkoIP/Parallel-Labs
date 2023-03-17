package lab2.data;

import java.util.HashMap;

import lab2.data.matrix.Matrix;
import lab2.data.vector.Vector;

public class DataStorage {
	
	private final static HashMap<String, Vector> vectors = new HashMap<String, Vector>();
	private final static HashMap<String, Matrix> matrixes = new HashMap<String, Matrix>();
	
	public Vector getVector(String name) {
		return vectors.get(name);
	}
	
	public Matrix getMatrix(String name) {
		return matrixes.get(name);
	}
	
	public void addVector(String name, Vector vector) {
		vectors.put(name, vector);
	}
	
	public void addMatrix(String name, Matrix matrix) {
		matrixes.put(name, matrix);
	}
}
