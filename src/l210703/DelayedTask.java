package l210703;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class DelayedTask implements Runnable, Delayed{
	private static int counter = 0;
	private final int id = counter ++;
	private final int delta;
	private final long trigger;
	
	protected static List<DelayedTask> sequence = new ArrayList<>();
	
	public DelayedTask(int delayInMillseconds) {
		this.delta = delayInMillseconds;
		trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
		sequence.add(this);
	}
	

	@Override
	public int compareTo(Delayed arg) {
		DelayedTask that = (DelayedTask) arg;
		if (trigger < that.trigger) {
			return -1;
		}
		
		if (trigger > that.trigger) {
			return 1;
		}
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	@Override
	public void run() {
		System.out.println(this + " ");
	}
	
	@Override
	public String toString() {
		return String.format("[%1$-4d]", delta) + " Task " + id;
	}
	
	public String summary() {
		return "(" + id + ":" + delta + ")";
	}
	
	public static class EndSentinel extends DelayedTask {
		private ExecutorService exec;
		
		public EndSentinel(int delayInMillseconds, ExecutorService e) {
			super(delayInMillseconds);
			exec = e;
		}
		
		@Override
		public void run() {
			for (DelayedTask delayedTask : sequence) {
				System.out.println(delayedTask.summary() + " ");
			}
			System.out.println();
			System.out.println(this + " Calling shutdownNow()");
			exec.shutdown();
		}
	}
	
	
}
