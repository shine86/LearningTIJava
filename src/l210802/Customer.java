package l210802;

import java.util.concurrent.SynchronousQueue;

public class Customer implements Runnable{
	private static int counter = 0;
	private final int id = counter++;
	private WaitPerson waitPerson;
	private SynchronousQueue<Plate> placeSetting = new SynchronousQueue<>();
	
	public Customer(WaitPerson waitPerson) {
		this.waitPerson = waitPerson;
	}



	@Override
	public void run() {
		for(Course course : Course.values()) {
			Food food = course.randomSelection();
			try {
				waitPerson.placeOrder(this, food);
				System.out.println(this + " eating " + placeSetting.take());
			} catch(Exception e) {
				System.out.println(this + " waiting for " + course + " interrupted");
				break;
			}
		}
		
		System.out.println(this + "finished meal, leaving");
	}
	
	@Override
	public String toString() {
		return "Customer" + id;
	}



	public void deliver(Plate plate) throws InterruptedException {
		placeSetting.put(plate);
	}

}
