package lab1.fs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileSystem {

	public void write(String filepath, String data) throws IOException {
		/*DataOutputStream stream = null;
		try {
			stream = new DataOutputStream(new FileOutputStream(filepath));
			stream.write(data.getBytes());
		} finally {
			if (stream != null) {
				stream.close();
			}
		}*/
		if (!new File(filepath).exists()) {
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
}
