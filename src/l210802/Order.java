package l210802;

public class Order {
	private static int counter = 0;
	private final int id = counter++;
	private final Customer customer;
	private final WaitPerson waitPerson;
	private final Food food;
	public Order(Customer customer, WaitPerson waitPerson, Food food) {
		super();
		this.customer = customer;
		this.waitPerson = waitPerson;
		this.food = food;
	}
	
	public Food item() {
		return food;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public WaitPerson getWaitPerson() {
		return waitPerson;
	}
	
	@Override
	public String toString() {
		return "Order:" + id + " item:" + food + " for: " + customer + " served by: " + waitPerson; 
	}
}
