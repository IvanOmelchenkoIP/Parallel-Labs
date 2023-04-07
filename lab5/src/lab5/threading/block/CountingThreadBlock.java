// .lab5/src/lab5/threading/block/CountingThreadBlock.java

package lab5.threading.block;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountingThreadBlock {

	private int tasksComplete = 0;
	private int tasks;
	
	private Lock countLock;
	private Condition countCondition;
	
	public CountingThreadBlock(int tasks) {
		this.tasks = tasks;
		
		this.countLock = new ReentrantLock();
		this.countCondition = countLock.newCondition();
	}
	
	private boolean allComplete() {
		return tasksComplete == tasks;
	}
	
	public void waitForAll() throws InterruptedException {
		countLock.lock();
		try {
			while (!allComplete()) {
				countCondition.await();
			}
		} finally {
			countLock.unlock();
		}
	}
	
	public void completeTask() {
		if (allComplete()) {
			return;
		}
		
		tasksComplete += 1;
		if (allComplete()) {
			countLock.lock();
			try {
				countCondition.signal();
			} finally {
				countLock.unlock();
			}
		}
	}
}
