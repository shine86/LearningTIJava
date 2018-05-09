package l210706;

import java.util.concurrent.TimeUnit;

public class CheckoutTask<T> implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private Pool<T> pool;
	
	public CheckoutTask(Pool<T> pool) {
		this.pool = pool;
	}

	@Override
	public void run() {
		try {
			T item = pool.checkOut();
			System.err.println(this + "checked out " + item);;
			TimeUnit.SECONDS.sleep(1);
			System.out.println(this + "checked in " + item);
			pool.checkIn(item);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "CheckoutTask" + id + " ";
	}

}
