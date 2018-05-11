package l210901;

public class SimpleMicroBenchmark {
	
	static long test(Incrementable incrementable) {
		long start = System.nanoTime();
		for (int i = 0; i < 10000000l; i++) {
			incrementable.increment();
		}
		return System.nanoTime() - start;
	}
	
	public static void main(String[] args) {
		long synchTime = test(new SynchronizingTest());
		long lockTime = test(new LockingTest());
		System.out.printf("synchronized %1$10d\n", synchTime);
		System.out.printf("lock %1$10d\n", lockTime);
		System.out.printf("lock/synchronized = %1$.3f", lockTime / (double) synchTime);
	}
}
