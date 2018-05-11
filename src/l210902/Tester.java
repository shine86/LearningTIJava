package l210902;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Tester<C> {
	static int testReps = 10;
	static int testCycles = 1000;
	static int containerSize = 1000;
	
	public abstract C containerInit();
	public abstract void startReadersAndWriters();
	
	C testContariner;
	String testId;
	int nReaders;
	int nWriters;
	
	volatile long readResult = 0;
	volatile long readTime = 0;
	volatile long writeTime = 0;
	
	CountDownLatch endLatch;
	static ExecutorService exec = Executors.newCachedThreadPool();
	Integer[] writeData;

	public Tester(String testId, int nReaders, int nWriters) {
		this.testId = testId + " " + nReaders + "r " + nWriters + "w";
		this.nReaders = nReaders;
		this.nWriters = nWriters;
		
		writeData = new Integer[containerSize];
		Random rand = new Random(47);
		for (int i = 0; i < containerSize; i++) {
			writeData[i] = rand.nextInt();
		}
		
		for (int i = 0; i < testReps; i++) {
			runTest();
			readTime = 0;
			writeTime = 0;
		}
	}
	
	private void runTest() {
		endLatch = new CountDownLatch(nReaders + nWriters);
		testContariner = containerInit();
		startReadersAndWriters();
		try {
			endLatch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.printf("%-27s %14d %14d\n", testId, readTime, writeTime);
		
		if (readTime != 0 && writeTime != 0) {
			System.out.printf("%-27s %14d\n", "readTime + writeTime = ", readTime + writeTime);
		}
		
	}
	
	public abstract class TestTask implements Runnable {
		public abstract void test();
		public abstract void putResults();
		long duration;
		
		@Override
		public void run() {
			long startTime =System.nanoTime();
			test();
			duration = System.nanoTime() - startTime;
			synchronized (Tester.this) {
				putResults();
			}
			endLatch.countDown();
		}
	}

	public static void initMain(String[] args) {
		System.out.printf("%-27s %14s %14s\n", "type", "read time", "write time");
	}
}
