package l210901;

public class BaseLine extends Accumulator{
	{
		id = "BaseLine";
	}
	@Override
	public void accumulate() {
		if (index >= SIZE) {
			index = 0;
		}
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
