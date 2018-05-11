package l210901;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

class AtomicTest extends Accumulator {
	{
		id = "atomic";
	}
	private AtomicInteger index = new AtomicInteger(0);
	private AtomicLong value = new AtomicLong(0);

	@Override
	public synchronized void accumulate() {
		int i = index.getAndIncrement();
		value.getAndAdd(preLoaded[i]);
		if (++i >= SIZE) {
			index.set(0);
		}
	}

	@Override
	public long read() {
		return value.get();
	}
}
