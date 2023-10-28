package us.dontcareabout.jiss.server.misc;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import us.dontcareabout.jiss.server.git.OverwirteCommitter;

public class ImgDeployer {
	private static final String INDEX_NAME = "README.md";
	private static final String URL_BASE = "https://psmonkey.github.io/img/";

	public static void deploy(String repoPath, String user, String password) throws Exception {
		genIndex(repoPath);

		try {
			new OverwirteCommitter().repoPaht(repoPath)
				.credential(user, password).commit("Auto Commit.");
		} catch (Exception e) {
			throw e;
		}
	}

	private static void genIndex(String path) throws IOException {
		StringBuilder result = new StringBuilder();
		File root = new File(path);

		for (File file : root.listFiles()) {
			if (file.isDirectory()) { continue; }

			String name = file.getName().toLowerCase();

			if (name.endsWith("jpg") || name.endsWith("gif") || name.endsWith("png")) {
				result.append("+ " + URL_BASE + file.getName() + "  \n");
				result.append("\t<img src=\"" + URL_BASE + file.getName() + "\" height=\"250px\" />\n");
			}
		}

		Files.write(result.toString().getBytes(), new File(root, INDEX_NAME));
	}
}
