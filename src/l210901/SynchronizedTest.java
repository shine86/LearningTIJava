package l210901;

class SynchronizedTest extends Accumulator {
	{
		id = "synchronized";
	}

	@Override
	public synchronized void accumulate() {
		value += preLoaded[index++];
		if (index >= SIZE) {
			index = 0;
		}
	}

	@Override
	public long read() {
		return value;
	}
}
