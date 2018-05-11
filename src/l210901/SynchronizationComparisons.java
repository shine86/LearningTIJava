package l210901;

public class SynchronizationComparisons {
	
	static BaseLine baseLine = new BaseLine();
	static SynchronizedTest synch = new SynchronizedTest();
	static LockTest lock = new LockTest();
	static AtomicTest atomic = new AtomicTest();
	
	static void test() {
		System.out.println("===============");
		System.out.printf("%-12s : %13d\n", "Cycles", Accumulator.cycles);
		baseLine.timedTest();
		synch.timedTest();
		lock.timedTest();
		atomic.timedTest();
		Accumulator.report(synch, baseLine);
		Accumulator.report(lock, baseLine);
		Accumulator.report(atomic, baseLine);
		
		Accumulator.report(synch, lock);
		Accumulator.report(synch, atomic);
		Accumulator.report(lock, atomic);
	}
	
	public static void main(String[] args) {
		int iterations = 5;
		System.out.println("warmup");
		baseLine.timedTest();
		for (int i = 0; i < iterations; i++) {
			test();
			Accumulator.cycles *=2;
		}
		Accumulator.exec.shutdown();
	}
}