package l210902;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SynchronizedHashMapTest extends MapTester{

	public SynchronizedHashMapTest(int nReaders, int nWriters) {
		super("SynchronizedHashMapTest", nReaders, nWriters);
	}

	@Override
	public Map<Integer, Integer> containerInit() {
		Map<Integer, Integer> map = new HashMap<>(containerSize);
		for (int i = 0; i < containerSize; i++) {
			map.put(i, i);
		}
		return Collections.synchronizedMap(map);
	}

}
