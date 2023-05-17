// ./lab3/src/lab3/threading/exec/ThreadExecutor.java

package lab3.threading.exec;

import java.util.concurrent.Executor;

public class ThreadExecutor implements Executor {
	
	@Override
	public void execute(Runnable command) {
		new Thread(command).start();
	}

}
