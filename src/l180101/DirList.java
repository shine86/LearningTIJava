package l180101;

import java.io.File;
import java.util.Arrays;

public class DirList {
	
	public static void main(String[] args) {
		File path = new File(".");
//		String[] list = path.list();
		String[] list = path.list(new DirFilter("^\\.g.*$"));
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for (String dirItem : list) {
			System.out.println(dirItem);
		}
	}
}
