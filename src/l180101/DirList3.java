package l180101;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class DirList3 {
	public static void main(String[] args) {
		File path = new File(".");
		String regex = "^\\.g.*$";
		String[] list = path.list(new FilenameFilter() {
			private Pattern pattern = Pattern.compile(regex);
			
			@Override
			public boolean accept(File dir, String name) {
				return pattern.matcher(name).matches();
			}
		});
		for (String string : list) {
			System.out.println(string);
		}
	}
}
