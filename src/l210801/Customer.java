package l210801;

public class Customer {
	private final int serviceTime;
	
	public Customer(int st) {
		serviceTime = st;
	}
	
	public int getServiceTime() {
		return serviceTime;
	}
	
	@Override
	public String toString() {
		return "[" + serviceTime + "]";
	}
	

}
