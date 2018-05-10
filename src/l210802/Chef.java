package l210802;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Chef implements Runnable {
	private static int counter = 0;
	private int id = counter++;
	private final Restaurant restaurant;
	private static Random rand = new Random(47);

	public Chef(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Order order = restaurant.orders.take();
				Food requestItem = order.item();
				TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
				Plate plate = new Plate(order, requestItem);
				order.getWaitPerson().filledOrders.put(plate);
			}
		} catch (InterruptedException e) {
			System.out.println(this + " interrupted");
		}
		
		System.out.println(this + " off duty");
	}
	
	@Override
	public String toString() {
		return "Chef " + id;
	}

}
