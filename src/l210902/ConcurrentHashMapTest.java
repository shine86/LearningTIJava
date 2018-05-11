package l210902;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest extends MapTester{

	public ConcurrentHashMapTest(int nReaders, int nWriters) {
		super("ConcurrentHashMapTest", nReaders, nWriters);
	}

	@Override
	public Map<Integer, Integer> containerInit() {
		Map<Integer, Integer> map = new HashMap<>(containerSize);
		for (int i = 0; i < containerSize; i++) {
			map.put(i, i);
		}
		return new ConcurrentHashMap<>(map);
	}

}
