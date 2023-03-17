// ./lab1/src/lab1/threading/F1
// F1 by variant: MА = MD * MT + MZ - ME * MM   

package lab1.threading;

import java.io.IOException;

import lab1.data.Matrix;
import lab1.data.MatrixIO;

public class F1 implements Runnable {

	final private int N = 100;
	final private MatrixIO mio = new MatrixIO();
	
	@Override
	public void run() {		
		Matrix MD, MT, MZ, ME, MM;
		try {
			MD = mio.generateOrRead("MD", N);
			MT = mio.generateOrRead("MT", N);
			MZ = mio.generateOrRead("MZ", N);
			ME = mio.generateOrRead("ME", N);
			MM = mio.generateOrRead("MM", N);
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
				mio.writeResult("MA", result);
			} catch (IOException ex) {
				System.out.println("Помилка при записі результату у файл - " + ex);
			}
			return;
		}
	}

}
