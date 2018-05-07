package l210703;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DelayQueueDemo {
	public static void main(String[] args) {
		Random rand = new Random(47);
		ExecutorService exec = Executors.newCachedThreadPool();
		DelayQueue<DelayedTask> queue = new DelayQueue<>();
		for (int i = 0; i < 20; i++) {
			queue.put(new DelayedTask(rand.nextInt(30000)));
		}
		
		
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		queue.add(new DelayedTask.EndSentinel(30000, exec));
		exec.execute(new DelayedTaskConsumer(queue));
		
	}
}
