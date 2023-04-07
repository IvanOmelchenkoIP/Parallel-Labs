// ./lab5/src/lab5/fs/FileSystem.java

package lab5.fs;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileSystem {

	public void write(String filepath, String data) throws IOException {
		if (!exists(filepath)) {
			Files.write(Paths.get(filepath), data.getBytes(), StandardOpenOption.CREATE_NEW);
		} else {
			Files.write(Paths.get(filepath), data.getBytes(), StandardOpenOption.APPEND);
		}
	}
	
	public String read(String filepath) throws IOException {
		DataInputStream stream = null;
		try {
			stream = new DataInputStream(new FileInputStream(filepath));
			return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}
	
	public boolean exists(String filepath) {
		return new File(filepath).exists();
	}
}
