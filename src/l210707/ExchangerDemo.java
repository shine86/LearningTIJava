package l210707;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import l210706.Fat;

public class ExchangerDemo {
	static int size = 10;
	static int delay = 5;
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		Exchanger<List<Fat>> xc = new Exchanger<>();
		List<Fat> producerList = new CopyOnWriteArrayList<>(),
				consumerList = new CopyOnWriteArrayList<>();
		exec.execute(new ExchangerProducer<>(xc, Fat.class, producerList));
		exec.execute(new ExchangerConsumer<>(xc, consumerList));
		TimeUnit.SECONDS.sleep(delay);
		exec.shutdownNow();
	}
}
