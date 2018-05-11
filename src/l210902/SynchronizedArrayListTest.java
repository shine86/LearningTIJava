package l210902;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SynchronizedArrayListTest extends ListTest{

	public SynchronizedArrayListTest(int nReaders, int nWriters) {
		super("SynchronizedArrayListTest", nReaders, nWriters);
	}
	
	@Override
	public List<Integer> containerInit() {
		List<Integer> list = new ArrayList<>(containerSize);
		for (int i = 0; i < containerSize; i++) {
			list.add(i);
		}
		return Collections.synchronizedList(list);
	}

}
