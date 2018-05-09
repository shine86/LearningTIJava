package l210707;

import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerProducer<T> implements Runnable {
	private Class<T> genClass;
	private Exchanger<List<T>> exchanger;
	private List<T> holder;

	public ExchangerProducer(Exchanger<List<T>> e, Class<T> c, List<T> h) {
		exchanger = e;
		genClass = c;
		holder = h;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				for (int i = 0; i < ExchangerDemo.size; i++) {
					holder.add(genClass.newInstance());
				}
				holder = exchanger.exchange(holder);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
