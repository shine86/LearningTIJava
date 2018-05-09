package l210704;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PrioritizedTask implements Runnable, Comparable<PrioritizedTask>{
	private Random rand = new Random();
	private static int counter = 0;
	private final int id = counter++;
	private final int priority;
	
	protected static List<PrioritizedTask> sequence = new 
			ArrayList<>();
	
	public PrioritizedTask(int priority) {
		this.priority = priority;
		sequence.add(this);
	}

	@Override
	public int compareTo(PrioritizedTask o) {
		if (priority < o.priority) {
			return 1;
		} else if(priority > o.priority) {
			return -1;
		}
		return 0;
	}

	@Override
	public void run() {
		try {
			TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(this);
	}
	
	@Override
	public String toString() {
		return String.format("[%1$-3d]", priority) + " Task " + id;
	}
	
	public String summary() {
		return "(" + id + ":" + priority + ")";
	}
	
	public static class EndSentinel extends PrioritizedTask {
		private ExecutorService exec;

		public EndSentinel(ExecutorService e) {
			super(-1);
			exec = e;
		}
		
		@Override
		public void run() {
			int count = 0;
			for (PrioritizedTask pt : sequence) {
				System.out.print(pt.summary());
				if (++count % 5 == 0) {
					System.out.println();
				}
			}
			System.out.println();
			System.out.println(this + " Calling shutdownNow!");
			exec.shutdownNow();
		}
		
	}

}
