// ./lab5/src/lab5/fs/MemFileSystem.java

package lab5.fs;

import java.io.IOException;
import java.util.HashMap;

public class MemFileSystem extends FileSystem {
	
	private final static HashMap<String, String> files = new HashMap<String, String>();
	
	@Override
	public String read(String filepath) throws IOException {
		String contents = files.get(filepath);
		if (contents == null) {
			contents = super.read(filepath);
			files.put(filepath, contents);
		}
		return contents;
	}
}