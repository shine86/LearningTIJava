package l210801;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BankTellerSimulation {
	static final int MAX_LINE_SIZE = 50;
	static final int ADJUSTMENT_PERIOD = 1000;
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
		exec.execute(new CustomerGenerator(customers));
		exec.execute(new TellerManager(exec, customers, ADJUSTMENT_PERIOD));
		TimeUnit.SECONDS.sleep(10);
		exec.shutdownNow();
	}
}
