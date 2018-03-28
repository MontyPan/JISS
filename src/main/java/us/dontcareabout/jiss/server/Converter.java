package us.dontcareabout.jiss.server;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Converter {
	public static void newline(String path) throws Exception {
		newline(new File(path));
	}

	public static void newline(File file) throws Exception {
		List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		StringBuilder result = new StringBuilder();

		for (String line : lines) {
			result.append(line);
			result.append("\r\n");
		}

		Files.write(
			file.toPath(),
			result.toString().getBytes(StandardCharsets.UTF_8),
			StandardOpenOption.TRUNCATE_EXISTING
		);
	}
}
