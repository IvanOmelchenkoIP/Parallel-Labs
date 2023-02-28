package lab1.threading;

import java.io.IOException;

import lab1.data.Matrix;
import lab1.data.MatrixIO;

public class F1 implements Runnable {

	final private int N = 100;
	final private MatrixIO io = new MatrixIO();
	
	@Override
	public void run() {		
		Matrix MD, MT, MZ, ME, MM;
		try {
			MD = io.generateOrRead("MD", N);
			MT = io.generateOrRead("MT", N);
			MZ = io.generateOrRead("MZ", N);
			ME = io.generateOrRead("ME", N);
			MM = io.generateOrRead("MM", N);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Неможливо виконати обчислення - " + ex);
			return;
		} catch (Exception ex) {
			System.out.println("Неможливо виконати обчислення - " + ex);
			return;
		}		
		Matrix MA = MD.getMatrixMultiplyProduct(MT).getMatrixSum(MZ).getMatrixDifference(ME.getMatrixMultiplyProduct(MM));
		String result = MA.toString();
		synchronized(this) {
			System.out.println("F1");
			System.out.println(result);
			try {
				io.writeResult("MA", result);
			} catch (IOException ex) {
				System.out.println("Помилка при записі результату у файл - " + ex);
			}
			return;
		}
	}

}
