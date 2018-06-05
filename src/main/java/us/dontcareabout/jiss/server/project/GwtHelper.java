package us.dontcareabout.jiss.server.project;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import us.dontcareabout.jiss.shared.Project;

public class GwtHelper {
	private static final String gwtMoudleHeader = "<module rename-to=\"";

	public static String moduleName(Project project) {
		File gwtXml;
		List<String> lines;

		try {
			gwtXml = PathHelper.gwtXml(project);
			lines = Files.readAllLines(gwtXml.toPath(), StandardCharsets.UTF_8);
		} catch (Exception e) {
			return null;
		}

		for (String line : lines) {
			if (line.startsWith(gwtMoudleHeader)) {
				return line.substring(gwtMoudleHeader.length(), line.length() - 2);
			}
		}

		return null;
	}
}
