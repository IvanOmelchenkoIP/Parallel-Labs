// ./lab6/src/lab6/threading/subtasks/out/ConsumerConsoleWriterSubtask.java

package lab6.threading.subtasks.out;

import java.util.concurrent.RecursiveAction;

public class ConsumerConsoleWriterSubtask extends RecursiveAction {
	
	private static final long serialVersionUID = 1L;
	
	private String result;
	
	public ConsumerConsoleWriterSubtask(String result) {
		this.result = result;
	}

	@Override
	protected void compute() {
		System.out.println(result);
	}
}