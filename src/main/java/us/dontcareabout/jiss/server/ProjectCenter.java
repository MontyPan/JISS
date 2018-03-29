package us.dontcareabout.jiss.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import com.google.gson.Gson;

import us.dontcareabout.jiss.shared.Project;

public class ProjectCenter {
	private static final Gson gson = new Gson();
	private static final Setting SETTING = new Setting();
	private static final File PROJECT_DIR;
	static {
		PROJECT_DIR = new File(SETTING.workspace(), "project");
		if (!PROJECT_DIR.exists()) { PROJECT_DIR.mkdirs(); }
	}

	public static ArrayList<Project> getProjects() {
		ArrayList<Project> result = new ArrayList<>();

		for (File file : PROJECT_DIR.listFiles()) {
			result.add(gson(file, Project.class));
		}

		return result;
	}

	public static void save(Project project) {
		File file = new File(PROJECT_DIR, project.getName());

		try {
			Files.write(file.toPath(), gson.toJson(project).getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Refactory 考慮抽到 GF？
	private static <T> T gson(File file, Class<T> classT) {
		try {
			InputStreamReader fr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
			T result = gson.fromJson(fr, classT);
			fr.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
