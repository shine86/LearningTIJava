package l211001;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ActiveObjectDemo {
	private ExecutorService ex = Executors.newSingleThreadExecutor();
	private Random rand = new Random(47);
	private void pause(int factor) {
		try {
			TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(factor));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Future<Integer> caculateInt(final int x, final int y) {
		return ex.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				System.out.println("starting " + x + " + " + y);
//				pause(500);
				return x + y;
			}
		});
	}
	
	public Future<Float> caculateFloat(final float x, final float y) {
		return ex.submit(new Callable<Float>() {

			@Override
			public Float call() throws Exception {
				System.out.println("starting " + x + " + " + y);
//				pause(2000);
				return x + y;
			}
		});
	}
	
	public void shutdown() {
		ex.shutdown();
	}
	
	public static void main(String[] args) throws InterruptedException {
		ActiveObjectDemo d1 = new ActiveObjectDemo();
		List<Future<?>> results = new CopyOnWriteArrayList<>();
		for(float f = 0.0f; f < 1.0f; f += 0.2f) {
			results.add(d1.caculateFloat(f, f));
		}
		
		for(int i = 0; i < 5; i++) {
			results.add(d1.caculateInt(i, i));
		}
		
		TimeUnit.SECONDS.sleep(1);
		System.out.println("All asynch calls made");
		while(results.size() > 0) {
			for (Future<?> future : results) {
				if (future.isDone()) {
					try {
						System.out.println(future.get());
					} catch (Exception e) {
						e.printStackTrace();
					}
					results.remove(future);
				}
			}
		}
		d1.shutdown();
	}

}
