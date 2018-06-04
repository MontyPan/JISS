package us.dontcareabout.jiss.server.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import freemarker.template.Configuration;
import us.dontcareabout.jiss.server.Setting;
import us.dontcareabout.jiss.server.Util;
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
			result.add(Util.parseJson(file, Project.class));
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

	// ==== FreeMarker 區 ==== //
	private static final Configuration ftlConfig = new Configuration();
	private static final String[] JAVA_SRC_ROOT = new String[]{"src", "main", "java"};

	static {
		ftlConfig.setClassForTemplateLoading(ProjectCenter.class, "");
	}

	public static void genDataEvent(Project project, String eventName) throws Exception {
		final String subPackage = "client.data";
		HashMap<String, Object> data = new HashMap<>();
		data.put("project", project);
		data.put("subPackage", subPackage);
		data.put("eventName", eventName);
		gen(
			"DataEvent.ftl",
			data,
			new File(
				packageFolder(project, (project.getRootPackage() + "." + subPackage)),
				eventName + "Event.java"
			)
		);
	}

	private static File packageFolder(Project project, String packageName) {
		File result = new File(javaFolder(project), packageName.replace('.', File.separatorChar));
		if (!result.exists()) { result.mkdirs(); }
		return result;
	}

	private static File javaFolder(Project project) {
		return Paths.get(
			project.getPath(),
			JAVA_SRC_ROOT
		).toFile();
	}

	private static void gen(String ftlName, HashMap<String, Object> data, File target) throws Exception {
		ftlConfig.getTemplate(ftlName).process(data, new FileWriter(target));
	}
	// ======== //
}
