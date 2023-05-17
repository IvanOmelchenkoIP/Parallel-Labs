// ./lab5/src/lab5/threading/out/ConsoleOutputSubtask.java

package lab5.threading.out;

import java.util.concurrent.RecursiveAction;

public class ConsoleOutputSubtask extends RecursiveAction {
	
	private static final long serialVersionUID = 1L;
	
	private String result;
	
	public ConsoleOutputSubtask(String result) {
		this.result = result;
	}

	@Override
	protected void compute() {
		System.out.println(result);
	}
}
