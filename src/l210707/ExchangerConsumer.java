package l210707;

import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerConsumer<T> implements Runnable {
	private Exchanger<List<T>> exchanger;
	private List<T> holder;
	private volatile T value;

	public ExchangerConsumer(Exchanger<List<T>> e, List<T> h) {
		exchanger = e;
		holder = h;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				holder = exchanger.exchange(holder);
				for(T x : holder) {
					value = x;
					holder.remove(x);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Final value: " + value);
	}

}
