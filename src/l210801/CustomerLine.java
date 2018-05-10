package l210801;

import java.util.concurrent.ArrayBlockingQueue;

public class CustomerLine extends ArrayBlockingQueue<Customer>{
	private static final long serialVersionUID = 1L;
	
	public CustomerLine(int maxLineSize) {
		super(maxLineSize);
	}
	
	@Override
	public String toString() {
		if (this.size() == 0) {
			return "[Empty]";
		}
		
		StringBuilder result = new StringBuilder();
		for(Customer customer: this) {
			result.append(customer);
		}
		return result.toString();
	}
}
