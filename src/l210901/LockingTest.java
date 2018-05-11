package l210901;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockingTest extends Incrementable {
	private Lock lock = new ReentrantLock();
	
	@Override
	public void increment() {
		lock.lock();
		try {
			++counter;
		} finally {
			lock.unlock();
		}
		
	}

}
