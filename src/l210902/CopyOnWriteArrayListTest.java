package l210902;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest extends ListTest{

	public CopyOnWriteArrayListTest(int nReaders, int nWriters) {
		super("CopyOnWriteArrayListTest", nReaders, nWriters);
	}
	
	@Override
	public List<Integer> containerInit() {
		List<Integer> list = new ArrayList<>(containerSize);
		for (int i = 0; i < containerSize; i++) {
			list.add(i);
		}
		return new CopyOnWriteArrayList<Integer>(list);
	}

}
